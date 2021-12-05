package com.hz.yd.iot.netty.protocol.packet;

/**
 * @Classname Hread
 * @Description TODO
 * @Date 2021/12/1 21:23
 * @Created by hzong
 */
public class Head {
    private String sn;//序列
    private String len;//长度

    private Head(Builder builder) {
        sn = builder.sn;
        len = builder.len;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getSn() {
        return sn;
    }

    public String getLen() {
        return len;
    }

    public static final class Builder {
        private String sn;
        private String len;

        private Builder() {
        }

        public Builder sn(String val) {
            sn = val;
            return this;
        }

        public Builder len(String val) {
            len = val;
            return this;
        }

        public Head build() {
            return new Head(this);
        }
    }
}
