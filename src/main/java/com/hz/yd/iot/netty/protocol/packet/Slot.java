package com.hz.yd.iot.netty.protocol.packet;

/**
 * @Classname Slots
 * @Description TODO
 * @Date 2021/12/2 23:13
 * @Created by hzong
 */
public class Slot {
    private String symbol;
    private String value;

    private Slot(Builder builder) {
        symbol = builder.symbol;
        value = builder.value;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getSymbol() {
        return symbol;
    }

    public String getValue() {
        return value;
    }

    public static final class Builder {
        private String symbol;
        private String value;

        private Builder() {
        }

        public Builder symbol(String val) {
            symbol = val;
            return this;
        }

        public Builder value(String val) {
            value = val;
            return this;
        }

        public Slot build() {
            return new Slot(this);
        }
    }
}
