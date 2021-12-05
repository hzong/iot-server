package com.hz.yd.iot.netty.listener;


import io.netty.channel.Channel;

/**
 * @Classname PacektListener
 * @Description TODO
 * @Date 2021/12/3 00:04
 * @Created by hzong
 */
public interface IPacektListener {

    void onDecoderFail(Channel channel, Object msg);

    void onDecoderSuccess(Channel channel,Object msg);
    void onEncoderFail(Channel channel,Object msg);
    void onEncoderSuccess(Channel channel,Object msg);
}
