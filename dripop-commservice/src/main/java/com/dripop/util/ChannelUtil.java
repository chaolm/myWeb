package com.dripop.util;

import com.bean.ChannelType;
import com.bean.SourceChannel;
import com.dripop.core.util.SessionUtil;
import com.dripop.core.util.StringUtil;

/**
 * 访问渠道工具类
 * Created by liyou on 2018/3/22.
 */
public class ChannelUtil {

    /**
     * 获取访问渠道类型
     * @return
     */
    public static ChannelType getChannel() {
        String channel = SessionUtil.getRequest().getParameter("channel");
        ChannelType channelType = null;
        if(StringUtil.isNotBlank(channel)) {
            channelType = ChannelType.getInstance(Integer.parseInt(channel));
        }
        if(channelType != null) {
            return channelType;
        }
        Boolean isMobile = SessionUtil.isMobile();
        return isMobile ? ChannelType.H5 : ChannelType.PC;
    }

    /**
     * 获取app pc 渠道类型
     * @return
     */
    public static Integer getHelpChannel() {
        Integer value = getChannel().getValue();
        if (value.equals(ChannelType.PC.getValue())) {
           return  SourceChannel.PC.getValue();
        }else{
            return  SourceChannel.APP.getValue();
        }
    }
}
