package com.hz.yd.iot.netty.client.message;

import com.hz.yd.iot.netty.protocol.packet.IOTPacket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Classname PacketMappingQueue
 * @Description 因为发送报文会有唯一标示ID，来对应消息ID映射
 * @Date 2021/12/2 23:59
 * @Created by hzong
 */
public class PacketClientMappingMessage {

    private static final Map<String, IOTPacket> mapping = new ConcurrentHashMap<String, IOTPacket>(1024);


    public static void put(String msgId,IOTPacket packet){
         mapping.put(msgId,packet);
    }

    public static void remove(String msgId){
        mapping.remove(msgId);
    }

    public static IOTPacket get(String msgId){
      return  mapping.get(msgId);
    }


}
