package com.hz.yd.iot.service;

import com.hz.yd.iot.netty.protocol.packet.EFunCode;
import com.hz.yd.iot.netty.protocol.packet.IOTPacket;
import com.hz.yd.iot.netty.server.message.PacketServerMappingMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname FunServer
 * @Description 失败ACK
 * @Date 2021/12/1 21:14
 * @Created by hzong
 */
public class CMDFunE1Service implements ICMDService<IOTPacket> {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CMDFunE1Service.class);

    public EFunCode code() {
        return EFunCode.FN_E1;
    }

    public void handle(IOTPacket msg) {
        // TODO: 2021/12/1 处理35代码模块并发送应答指令：02#2A1C#27#36#0B$E4$0E08$CB$E5$B7#20210524131643
        //存储消息
        IOTPacket packet = PacketServerMappingMessage.get(msg.getHead().getSn());
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("server-收到序号【{}】功能码【{}】消息-handle() called with: msg = 【" + msg.toMsg() + "】"
                    ,packet.getHead().getSn(), code().getCode());
        }



    }
}
