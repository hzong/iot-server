package com.hz.yd.component.netty.client;

import com.hz.yd.component.netty.IServer;
import com.hz.yd.component.netty.listener.DefPacektListener;
import com.hz.yd.iot.netty.client.listener.ClientPacektListener;
import com.hz.yd.iot.netty.decoder.StringToIOTPacketDecoder;
import com.hz.yd.iot.netty.encode.IOTPacketToStringEncode;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Classname NettyServer
 * @Description TODO
 * @Date 2021/12/1 19:37
 * @Created by hzong
 */
public class NettyClient implements IServer {
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyClient.class);

    private List<ChannelInboundHandler> handlers;
    private String ip;
    private int port;
    private ChannelFuture future = null;
    private EventLoopGroup workGroup;//工作线程
    private Channel channel;


    public NettyClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void setHandlers(List<ChannelInboundHandler> handlers) {
        this.handlers = handlers;
    }

    public void start() {
        // 首先，netty通过ServerBootstrap启动服务端
        Bootstrap client = new Bootstrap();
        this.workGroup = new NioEventLoopGroup();//工作线程
        //第1步定义两个线程组，用来处理客户端通道的accept和读写事件
        //parentGroup用来处理accept事件，childgroup用来处理通道的读写事件
        //parentGroup获取客户端连接，连接接收到之后再将连接转发给childgroup去处理
        client.group(workGroup);

        //第2步绑定服务端通道
        client.channel(NioSocketChannel.class);

        //第3步绑定handler，处理读写事件，ChannelInitializer是给通道初始化
        client.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                //解码
                ch.pipeline().addLast(new StringDecoder());
                pipeline.addLast(new StringToIOTPacketDecoder(new ClientPacektListener()));
                //编码
                pipeline.addLast(new IOTPacketToStringEncode(new DefPacektListener()));
                ch.pipeline().addLast(new StringEncoder());
//                ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                if (handlers == null) {
                    throw new NullPointerException("server handle is null");
                }
                for (ChannelInboundHandler handler : handlers) {
                    pipeline.addLast(handler);
                }
            }
        });

        //第4步连接端口

        try {
            future = client.connect(ip, port).sync();
        } catch (Exception e) {
            LOGGER.error("server error：" + "start() called", e);
        }
        if (future != null) {
            future.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (future.isSuccess()) {
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("operationComplete() called with: channelFuture = 【" + channelFuture + "】连接成功");
                        }
                    } else {
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("operationComplete() called with: channelFuture = 【" + channelFuture + "】连接失败");
                        }
                        future.cause().printStackTrace();
                        workGroup.shutdownGracefully();
                    }
                }
            });
        }
        if (future != null) {
            channel = future.channel();

        }
    }

    public Channel channel() {
        return channel;
    }

    public void stop() {
        if (channel != null) {
            try {
                channel.closeFuture().sync();
            } catch (InterruptedException e) {
                LOGGER.error("server error：" + "start() called", e);
            }
        }
        if (workGroup != null) {
            try {
                workGroup.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                LOGGER.error("server error：" + "stop() called", e);
            }
        }
    }
}
