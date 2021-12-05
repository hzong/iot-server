package com.hz.yd.component.netty.server.session;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Classname SessionManager
 * @Description 通道管理
 * @Date 2021/12/1 23:19
 * @Created by hzong
 */
public class SessionManager {
    public static final SessionManager INST = new SessionManager();
    private static final Map<ChannelId, Session> channelMap = new ConcurrentHashMap<ChannelId, Session>();


    public void reg(ChannelId id, Session channel) {
        channelMap.put(id, channel);
    }

    public Session get(ChannelId id) {
        return channelMap.get(id);
    }

    public Session rem(ChannelId id) {
        return channelMap.remove(id);
    }
}
