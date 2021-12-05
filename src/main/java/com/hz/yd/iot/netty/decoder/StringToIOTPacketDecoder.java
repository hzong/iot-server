package com.hz.yd.iot.netty.decoder;

import com.hz.yd.iot.netty.listener.IPacektListener;
import com.hz.yd.iot.netty.protocol.packet.*;
import com.hz.yd.iot.netty.protocol.packet.payload.Payload35;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @Classname StringToIOTPacket
 * @Description TODO
 * @Date 2021/12/1 21:32
 * @Created by hzong
 */
public class StringToIOTPacketDecoder extends MessageToMessageDecoder<String> {
    private IPacektListener listener;

    public StringToIOTPacketDecoder(IPacektListener listener) {
        this.listener = listener;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx
            , String msg, List<Object> out) throws Exception {
        if (msg == null) {
            listener.onDecoderFail(ctx.channel(),msg);
            return ;
        }

        String[] arrayMsg = StringUtils.split(msg,IOTPacket.HASHTAG);
        if (ArrayUtils.isEmpty(arrayMsg)) {
            listener.onDecoderFail(ctx.channel(),msg);
            return;
        }

        //开始
        String stx = arrayMsg[0];
        //结束
        String etx = arrayMsg[arrayMsg.length-1];
        if(!(IOTPacket.STX.equals(stx) && IOTPacket.ETX.equals(etx))){
            listener.onDecoderFail(ctx.channel(),msg);
            return;
        }
        //序号
        String sn = arrayMsg[1];
        //长度
        String len = arrayMsg[2];
        //头部
        Head head = Head.newBuilder().len(len).sn(sn).build();
        //主体
        String[] bodyArray =  ArrayUtils.subarray(arrayMsg,3,arrayMsg.length-1);
        if(bodyArray == null){
            listener.onDecoderFail(ctx.channel(),msg);
            return;
        }
        LinkedList slots = new LinkedList();
        for (String slot : bodyArray) {
            slots.add(Slot.newBuilder().symbol(IOTPacket.HASHTAG).value(slot).build());
        }

        Body body = Body.newBuilder().slots(slots).build();
        //长度不一致
        if (!(NumberUtils.toInt(len) == body.len())) {
            listener.onDecoderFail(ctx.channel(),msg);
            return;
        }

        IOTPacket pck = IOTPacket.newBuilder().channelId(ctx.channel().id())
                .stx(stx).etx(etx).head(head).body(body).build();

        listener.onDecoderSuccess(ctx.channel(),pck);
        out.add(pck);
    }


    /**
     * 功能码转换BODY
     * @param fn
     * @param msg
     * @return
     */
    private Body fnToBody(EFunCode fn ,String[] msg){
        if (EFunCode.FN_35 == fn) {
        }

        return null;
    }

}
