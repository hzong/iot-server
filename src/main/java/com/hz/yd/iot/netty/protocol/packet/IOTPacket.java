package com.hz.yd.iot.netty.protocol.packet;

import io.netty.channel.ChannelId;

/**
 * @Classname IOTPacket
 * @Description TODO
 * @Date 2021/12/1 21:20
 * @Created by hzong
 */
public class IOTPacket extends BasePacket {
    public static final String STX="02";
    public static final String ETX="03";
    public static final String HASHTAG="#";
    public static final String DOLLAR="$";
    public static final String RES="_RES";
    //起始位
    private String stx = STX;
    private Head head;
    private Body body;
    //结束位
    private String etx = ETX;

    private IOTPacket(Builder builder) {
        channelId = builder.channelId;
        stx = builder.stx;
        head = builder.head;
        body = builder.body;
        etx = builder.etx;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static String getSTX() {
        return STX;
    }

    public static String getETX() {
        return ETX;
    }

    public String getStx() {
        return stx;
    }

    public Head getHead() {
        return head;
    }

    public Body getBody() {
        return body;
    }

    public String getEtx() {
        return etx;
    }

    public static final class Builder {
        private ChannelId channelId;
        private String stx = STX;
        private Head head;
        private Body body;
        private String etx = ETX;

        private Builder() {
        }

        public Builder channelId(ChannelId val) {
            channelId = val;
            return this;
        }

        public Builder stx(String val) {
            stx = val;
            return this;
        }

        public Builder head(Head val) {
            head = val;
            return this;
        }

        public Builder body(Body val) {
            body = val;
            return this;
        }

        public Builder etx(String val) {
            etx = val;
            return this;
        }

        public IOTPacket build() {
            return new IOTPacket(this);
        }
    }


    public String toMsg(){
        StringBuffer sb = new StringBuffer();
        sb.append(STX);
        sb.append(HASHTAG);
        sb.append(head.getSn());
        sb.append(HASHTAG);
        sb.append(head.getLen());
        sb.append(body.toMsg());
        sb.append(ETX);
        return sb.toString();
    }
}
