package com.hz.yd.iot.netty.client.listener;

import com.hz.yd.iot.netty.listener.IPacektListener;
import com.hz.yd.iot.netty.protocol.packet.Body;
import com.hz.yd.iot.netty.protocol.packet.Head;
import com.hz.yd.iot.netty.protocol.packet.IOTPacket;
import com.hz.yd.iot.netty.protocol.packet.Slot;
import com.hz.yd.iot.utils.FormatUtils;
import io.netty.channel.Channel;

import java.util.LinkedList;
import java.util.List;

/**
 * @Classname ClientPacektListener
 * @Description TODO
 * @Date 2021/12/3 00:11
 * @Created by hzong
 */
public class ClientPacektListener implements IPacektListener {
    public void onDecoderFail(Channel channel, Object msg) {
        Body body = failBody();
        Head head = failHead(body);
        IOTPacket pck =
                IOTPacket.newBuilder()
                        .head(head)
                        .body(body).build();
        //发送消息
        channel.writeAndFlush(pck);
    }



    public void onDecoderSuccess(Channel channel, Object msg) {
        Body body = successBody();
        Head head = successHead(body);
        IOTPacket pck =
                IOTPacket.newBuilder()
                        .head(head)
                        .body(body).build();
        //发送消息
        channel.writeAndFlush(pck);
    }

    public void onEncoderFail(Channel channel, Object msg) {

    }

    public void onEncoderSuccess(Channel channel, Object msg) {

    }


    private static Body successBody() {
        List<Slot> slots = new LinkedList<Slot>();
        slots.add(Slot.newBuilder().symbol(IOTPacket.HASHTAG).value("A1").build());
        slots.add(Slot.newBuilder().symbol(IOTPacket.HASHTAG).value("0B").build());
        return  Body.newBuilder().slots(slots).build();
    }

    private static Head successHead(Body body) {
        return Head.newBuilder().len(FormatUtils.formatNumToStr(body.len())).sn("2A1C").build();
    }

    private static Body failBody() {
        List<Slot> slots = new LinkedList<Slot>();
        slots.add(Slot.newBuilder().symbol(IOTPacket.HASHTAG).value("E1").build());
        slots.add(Slot.newBuilder().symbol(IOTPacket.HASHTAG).value("0C").build());
        return  Body.newBuilder().slots(slots).build();
    }

    private static Head failHead(Body body) {
        return Head.newBuilder().len(FormatUtils.formatNumToStr(body.len())).sn("2A1C").build();
    }
}
