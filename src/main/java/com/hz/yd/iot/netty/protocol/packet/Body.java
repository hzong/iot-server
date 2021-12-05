package com.hz.yd.iot.netty.protocol.packet;

import java.util.List;

/**
 * @Classname Body
 * @Description TODO
 * @Date 2021/12/1 21:26
 * @Created by hzong
 */
public class Body implements IBody {

    private List<Slot> slots;

    private Body(Builder builder) {
        slots = builder.slots;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public static final class Builder {
        private List<Slot> slots;

        private Builder() {
        }

        public Builder slots(List<Slot> val) {
            slots = val;
            return this;
        }


        public Body build() {
            return new Body(this);
        }
    }


    public int size() {
        return slots.size();
    }

    public int len() {
        int c = 0;

        for (Slot slot : slots) {
            c +=+(slot.getValue().length() + slot.getSymbol().length());
        }
        //+1原因是1个#分隔符
        return c + 1;
    }

    public String toMsg() {
        StringBuffer sb = new StringBuffer();
        sb.append(IOTPacket.HASHTAG);
        for (int i = 0; i < slots.size(); i++) {
            Slot slot = slots.get(i);
            if (i != 0) {
                sb.append(slot.getSymbol());
            }
            sb.append(slot.getValue());
        }
        sb.append(IOTPacket.HASHTAG);
        return sb.toString();
    }
}


