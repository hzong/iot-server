package com.hz.yd.iot.netty.client.handles;

import com.hz.yd.component.netty.server.session.Session;
import com.hz.yd.component.netty.server.session.SessionManager;
import com.hz.yd.iot.netty.client.message.PacketClientMappingMessage;
import com.hz.yd.iot.netty.protocol.tools.ProtocolTool;
import com.hz.yd.iot.netty.protocol.packet.IOTPacket;
import com.hz.yd.iot.service.CMDFunManager;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname ServerHandler
 * @Description TODO
 * @Date 2021/12/1 21:13
 * @Created by hzong
 */
@ChannelHandler.Sharable
public class ClientHandler extends SimpleChannelInboundHandler<IOTPacket> {
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientHandler.class);
    private CMDFunManager manager = CMDFunManager.INST;
    private SessionManager sessionManager = SessionManager.INST;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        sessionManager.reg(ctx.channel().id(),new Session(ctx.channel(),"online"));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        sessionManager.rem(ctx.channel().id());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, IOTPacket packet) throws Exception {
        Session session = sessionManager.get(packet.getChannelId());
        if(session == null){
            LOGGER.warn("server warn："+"channelRead0() called with: channelHandlerContext = 【" + channelHandlerContext + "】, packet = 【" + packet + "】未获取到session");
            return;
        }
        IOTPacket reqPacket = PacketClientMappingMessage.get(packet.getHead().getSn());
        //功能调用逻辑
        manager.handle( ProtocolTool.funCodeRes(reqPacket),packet);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
