package com.hz.yd.iot.netty.encode;

import com.hz.yd.iot.netty.listener.IPacektListener;
import com.hz.yd.iot.netty.protocol.packet.IOTPacket;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @Classname MessageToMessageEncode
 * @Description TODO
 * @Date 2021/12/2 22:42
 * @Created by hzong
 */
public class IOTPacketToStringEncode extends MessageToMessageEncoder<IOTPacket> {

    private IPacektListener listener;

    public IOTPacketToStringEncode(IPacektListener listener) {
        this.listener = listener;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx,
                          IOTPacket msg, List<Object> out) throws Exception {
        if (msg == null) {
            return;
        }

        out.add(ByteBufUtil.encodeString(ctx.alloc(), CharBuffer.wrap(msg.toMsg()), Charset.defaultCharset()));
    }
}
