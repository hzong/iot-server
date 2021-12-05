package com.hz.yd.iot;

import com.hz.yd.component.netty.server.NettyServer;
import com.hz.yd.iot.netty.client.handles.ClientHandler;
import com.hz.yd.iot.netty.server.handles.ServerHandler;
import io.netty.channel.ChannelInboundHandler;

import java.util.Collections;

/**
 * @Classname IOTAppliaction
 * @Description TODO
 * @Date 2021/12/1 23:14
 * @Created by hzong
 */
public class IOTServerAppliaction {
    public static void main(String[] args) {
        NettyServer server = new NettyServer(822);
        server.setHandlers(Collections.singletonList((ChannelInboundHandler)new ServerHandler()));
        try{
            server.start();
        }finally {
            server.stop();
        }
    }
}
