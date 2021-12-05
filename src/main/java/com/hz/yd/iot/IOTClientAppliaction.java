package com.hz.yd.iot;

import com.hz.yd.component.netty.client.NettyClient;
import com.hz.yd.iot.netty.client.handles.ClientHandler;
import com.hz.yd.iot.netty.client.message.PacketClientMappingMessage;
import com.hz.yd.iot.netty.protocol.packet.*;
import com.hz.yd.iot.utils.FormatUtils;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInboundHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * @Classname IOTAppliaction
 * @Description TODO
 * @Date 2021/12/1 23:14
 * @Created by hzong
 */
public class IOTClientAppliaction {
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(IOTClientAppliaction.class);
    public static void main(String[] args) {
        NettyClient client = new NettyClient("127.0.0.1",822);

        client.setHandlers(Collections.singletonList((ChannelInboundHandler)new ClientHandler()));
        client.start();





        //发送消息
        if (client.channel() != null) {
            send(args, client);

        }
    }

    private static void send(String[] args, NettyClient client) {
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.print("发送指令次数：");
            int c = NumberUtils.toInt(sc.nextLine()) ;
            for (int i = 0; i < c; i++) {
                final int j = i;
                IOTPacket pck = packet(null);
                client.channel().writeAndFlush(pck).addListener(new ChannelFutureListener() {
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        if (channelFuture.isSuccess()) {
                            if(LOGGER.isDebugEnabled()){
                                LOGGER.debug("已发送"+(j+1)+"main() called ");
                            }
                        }else {
                            channelFuture.cause().printStackTrace();
                        }
                    }
                });

            }
        }
    }

    private static IOTPacket packet(String value) {
        Body body = body(value);
        Head head = head(body);

        IOTPacket pck =
                IOTPacket.newBuilder()
                        .head(head)
                        .body(body).build();
        //注册消息
        PacketClientMappingMessage.put(head.getSn(),pck);
        return pck;
    }

    private static Body body(String value) {
        if(StringUtils.isBlank(value)){
            value = "35";
        }
        List<Slot> slots = new LinkedList<Slot>();
        slots.add(Slot.newBuilder().symbol(IOTPacket.HASHTAG).value(value).build());
        slots.add(Slot.newBuilder().symbol(IOTPacket.HASHTAG).value("0A").build());
        return  Body.newBuilder().slots(slots).build();
    }

    private static Head head(Body body) {
        return Head.newBuilder().len(FormatUtils.formatNumToStr(body.len())).sn("2A1C").build();
    }
}
