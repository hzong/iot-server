package com.hz.yd.component.netty.server;

import com.hz.yd.component.netty.listener.DefPacektListener;
import com.hz.yd.component.netty.IServer;
import com.hz.yd.iot.netty.decoder.StringToIOTPacketDecoder;
import com.hz.yd.iot.netty.encode.IOTPacketToStringEncode;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
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
public class NettyServer implements IServer {
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServer.class);

    private List<ChannelInboundHandler> handlers;
    private int port;
    private ChannelFuture future = null;
    EventLoopGroup bossGroup; //主线程
    EventLoopGroup workGroup;//工作线程


    public NettyServer(int port) {
        this.port = port;
    }

    public void setHandlers(List<ChannelInboundHandler> handlers) {
        this.handlers = handlers;
    }

    public void start() {
        // 首先，netty通过ServerBootstrap启动服务端
        ServerBootstrap server = new ServerBootstrap();
        this.bossGroup = new NioEventLoopGroup();//主线程
        this.workGroup = new NioEventLoopGroup();//工作线程
        //第1步定义两个线程组，用来处理客户端通道的accept和读写事件
        //parentGroup用来处理accept事件，childgroup用来处理通道的读写事件
        //parentGroup获取客户端连接，连接接收到之后再将连接转发给childgroup去处理
        server.group(bossGroup, workGroup);

        //用于构造服务端套接字ServerSocket对象，标识当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度。
        //用来初始化服务端可连接队列
        //服务端处理客户端连接请求是按顺序处理的，所以同一时间只能处理一个客户端连接，多个客户端来的时候，服务端将不能处理的客户端连接请求放在队列中等待处理，backlog参数指定了队列的大小。
        server.option(ChannelOption.SO_BACKLOG, 128);

        //第2步绑定服务端通道
        server.channel(NioServerSocketChannel.class);

        //第3步绑定handler，处理读写事件，ChannelInitializer是给通道初始化
        server.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
//                pipeline.addLast(new LineBasedFrameDecoder(0));//减少报文大小
                pipeline.addLast(new StringDecoder());//解码
                pipeline.addLast(new StringToIOTPacketDecoder(new DefPacektListener()));

                pipeline.addLast(new IOTPacketToStringEncode(new DefPacektListener()));
                pipeline.addLast(new StringEncoder());//编码
                if (handlers == null) {
                    throw new NullPointerException("server handle is null");
                }
                for (ChannelInboundHandler handler : handlers) {
                    pipeline.addLast(handler);
                }

                //                ch.pipeline().addLast(new SimpleServerHandler());
            }
        });

        //第4步绑定0822端口

        try {
            future = server.bind(822).sync();
        } catch (Exception e) {
            LOGGER.error("server error：" + "start() called", e);
        }
    }

    public void stop() {
        if (future != null) {
            try {
                future.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                LOGGER.error("server error：" + "stop() called", e);
            }
        }
        if (bossGroup != null) {
            try {
                bossGroup.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                LOGGER.error("server error：" + "stop() called", e);
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
