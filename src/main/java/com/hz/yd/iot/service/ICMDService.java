package com.hz.yd.iot.service;

import com.hz.yd.iot.netty.protocol.packet.EFunCode;

/**
 * @Classname IServer
 * @Description TODO
 * @Date 2021/12/1 21:17
 * @Created by hzong
 */
public interface ICMDService<M> {

    EFunCode code();

    void handle(M m);

}
