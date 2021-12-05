package com.hz.yd.iot.netty.protocol.tools;

import com.hz.yd.iot.netty.protocol.packet.Body;
import com.hz.yd.iot.netty.protocol.packet.EFunCode;
import com.hz.yd.iot.netty.protocol.packet.IOTPacket;
import com.hz.yd.iot.netty.protocol.packet.Slot;
import com.hz.yd.iot.netty.protocol.packet.payload.Payload35;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * @Classname ProtocolTool
 * @Description TODO
 * @Date 2021/12/2 21:15
 * @Created by hzong
 */
public class ProtocolTool {

    public static Payload35 payload35(Body body){

        return null;
    }
    public static EFunCode funCode(Body body){
        return funCode(body.getSlots());
    }
    /**
     * 功能码
     * @param solts
     * @return
     */
    public static EFunCode funCode(List<Slot> solts){

        if(CollectionUtils.isEmpty(solts)){
            return  EFunCode.NONE;
        }
        return EFunCode.parseCode(solts.get(0).getValue());
    }

    /**
     * 应答功能
     * @param packet
     * @return
     */
    public static EFunCode funCodeRes(IOTPacket packet){
        return EFunCode.parseCode(funCode(packet.getBody()).getCode()+IOTPacket.RES);
    }
}
