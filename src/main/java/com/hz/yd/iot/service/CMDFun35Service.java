package com.hz.yd.iot.service;

import com.hz.yd.component.netty.server.session.Session;
import com.hz.yd.component.netty.server.session.SessionManager;
import com.hz.yd.iot.netty.protocol.packet.*;
import com.hz.yd.iot.netty.server.message.PacketServerMappingMessage;
import com.hz.yd.iot.utils.FormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * @Classname FunServer
 * @Description TODO
 * @Date 2021/12/1 21:14
 * @Created by hzong
 */
public class CMDFun35Service implements ICMDService<IOTPacket> {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CMDFun35Service.class);

    private SessionManager sessionManager = SessionManager.INST;


    public EFunCode code() {
        return EFunCode.FN_35;
    }

    public void handle(IOTPacket msg) {
        Session session = sessionManager.get(msg.getChannelId());
        if(session == null){
            return;
        }
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("server-收到序号【{}】功能码【{}】消息-handle() called with: msg = 【" + msg.toMsg() + "】"
                    ,msg.getHead().getSn(), code().getCode());
        }


        Body body = body();
        Head head = head(body);

        IOTPacket pck = IOTPacket.newBuilder().channelId(msg.getChannelId()).head(head).body(body).build();
        //存储消息
        PacketServerMappingMessage.put(head.getSn(),pck);
        //发送
        session.getChannel().writeAndFlush(pck);
    }

    private Head head(Body body) {
        return Head.newBuilder().sn("2A1C").len(FormatUtils.formatNumToStr(body.len())).build();
    }

    private Body body() {
        List<Slot> slots = new LinkedList<Slot>();
        slots.add(Slot.newBuilder().symbol(IOTPacket.HASHTAG).value("36").build());
        slots.add(Slot.newBuilder().symbol(IOTPacket.HASHTAG).value("0B").build());
        slots.add(Slot.newBuilder().symbol(IOTPacket.DOLLAR).value("E4").build());
        slots.add(Slot.newBuilder().symbol(IOTPacket.DOLLAR).value("0E08").build());
        slots.add(Slot.newBuilder().symbol(IOTPacket.DOLLAR).value("CB").build());
        slots.add(Slot.newBuilder().symbol(IOTPacket.DOLLAR).value("E5").build());
        slots.add(Slot.newBuilder().symbol(IOTPacket.DOLLAR).value("B7").build());
        slots.add(Slot.newBuilder().symbol(IOTPacket.HASHTAG).value("20210524131643").build());

        return Body.newBuilder().slots(slots).build();
    }
}
