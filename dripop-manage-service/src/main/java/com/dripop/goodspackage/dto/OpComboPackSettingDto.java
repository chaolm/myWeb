package com.dripop.goodspackage.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liyou on 2017/8/4.
 */
public class OpComboPackSettingDto implements Serializable {

    public interface TYPE_CONSTANT{
        int ADD = 1;//新增
        int COVER = 2;//覆盖
    }

    private String onlineIds;

    private Long uid;

    /**
     * 业务类型 1 新增 2 覆盖
     */
    private Integer type;

    private List<OpComboPackSettingDetailDto> detailList;

    public String getOnlineIds() {
        return onlineIds;
    }

    public void setOnlineIds(String onlineIds) {
        this.onlineIds = onlineIds;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<OpComboPackSettingDetailDto> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<OpComboPackSettingDetailDto> detailList) {
        this.detailList = detailList;
    }
}
