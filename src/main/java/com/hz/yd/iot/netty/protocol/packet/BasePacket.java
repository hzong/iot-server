package com.hz.yd.iot.netty.protocol.packet;

import io.netty.channel.ChannelId;

/**
 * @Classname BasePacket
 * @Description TODO
 * @Date 2021/12/1 21:19
 * @Created by hzong
 */
public abstract class BasePacket implements IPacket {
    /**
     * 是否是坏标识
     */

    protected ChannelId channelId;

    public ChannelId getChannelId() {
        return channelId;
    }


}
