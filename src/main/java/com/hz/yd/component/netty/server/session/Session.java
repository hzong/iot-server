package com.hz.yd.component.netty.server.session;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

/**
 * @Classname Session
 * @Description TODO
 * @Date 2021/12/1 23:22
 * @Created by hzong
 */
public class Session {

    private Channel channel;//通道
    private long lastTime = System.currentTimeMillis();//最后登录时间
    private String state;//状态

    public Session(Channel channel, String state) {
        this.channel = channel;
        this.state = state;
    }

    public Channel getChannel() {
        return channel;
    }

    public long getLastTime() {
        return lastTime;
    }

    public String getState() {
        return state;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }
}
