package com.bean;

/**
 * 渠道类型
 * Created by liyou on 2017/9/27.
 */
public enum ChannelType {

    H5(3),

    IOS(4),

    ANDROID(5),

    PC(6),

    UNKWON(9);

    ChannelType(Integer value) {
        this.value = value;
    }

    private Integer value;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }


    /**
     * 返回对应value的枚举类实例
     * @param value 目标值
     * @return
     */
    public static ChannelType getInstance(Integer value) {
        ChannelType[] array = ChannelType.values();
        for (ChannelType channelType : array) {
            if(channelType.getValue().equals(value)) {
                return channelType;
            }
        }
        return ChannelType.UNKWON;
    }
}
