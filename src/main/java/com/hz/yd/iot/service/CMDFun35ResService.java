package com.hz.yd.iot.service;

import com.hz.yd.component.netty.server.session.Session;
import com.hz.yd.component.netty.server.session.SessionManager;
import com.hz.yd.iot.netty.protocol.packet.*;
import com.hz.yd.iot.netty.server.message.PacketServerMappingMessage;
import com.hz.yd.iot.utils.FormatUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
public class CMDFun35ResService implements ICMDService<IOTPacket> {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CMDFun35ResService.class);


    public EFunCode code() {
        return EFunCode.FN_35;
    }

    public void handle(IOTPacket msg) {
        List<Slot> slots = msg.getBody().getSlots();
        if (CollectionUtils.isEmpty(slots)) {
            return;
        } else if (slots.size() != 3) {
            return;
        }

        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("client-收到序号【{}】功能码【{}】消息-handle() called with: msg = 【" + msg.toMsg() + "】"
                    ,msg.getHead().getSn(), code().getCode());
        }


        Slot slot = slots.get(1);
        String[] array = StringUtils.split(slot.getValue(), IOTPacket.DOLLAR);
        if (array.length != 6) {
            return;
        }
        //机组号
        String slot01 = array[0];
        if (slot01 != null) {
            LOGGER.info("handle() called with: msg = 【" + msg + " 】机组号：" + slot01);
        }
        //作业车编号
        String slot02 = array[1];
        if (slot02 != null) {
            LOGGER.info("handle() called with: msg = 【" + msg + " 】作业车编号：" + slot02);
        }
        //司机号
        String slot03 = array[2];
        if (slot03 != null) {
            LOGGER.info("handle() called with: msg = 【" + msg + " 】司机号：" + slot03);
        }
        //经度
        String slot04 = array[3];
        if (slot04 != null) {
            LOGGER.info("handle() called with: msg = 【" + msg + " 】经度：" + slot04);
        }
        //纬度
        String slot05 = array[4];
        if (slot05 != null) {
            LOGGER.info("handle() called with: msg = 【" + msg + " 】纬度：" + slot05);
        }
        //状态
        String slot06 = array[5];
        if (slot06 != null) {
            LOGGER.info("handle() called with: msg = 【" + msg + " 】状态：" + slot06);
        }
        //时间信息
        Slot slot07 = slots.get(slots.size()-1);
        if (slot07 != null) {
            LOGGER.info("handle() called with: msg = 【" + msg + " 】时间信息：" + slot07.getValue());
        }
    }


}
