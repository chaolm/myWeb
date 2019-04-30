package com.dripop.promotion.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.bean.ActivityStatus;
import com.dripop.core.util.StringUtil;
import com.dripop.util.ImgUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liyou on 2018/2/5.
 */
public class GiftPromotionPageDto implements Serializable {

    private Long id;

    private String name;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    private List<GiftDto> giftList;

    @JSONField(serialize = false)
    private String imgUrls;

    @JSONField(serialize = false)
    private String fullNames;

    private Integer giftStatus;

    private String statusText;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<GiftDto> getGiftList() {
        if(StringUtil.isBlank(fullNames)) {
            return giftList;
        }
        giftList = new ArrayList<>();
        GiftDto giftDto = null;
        String[] imgUrlArr = imgUrls.split(",", -1);
        String[] fullNameArr = fullNames.split(",", -1);
        for (int i = 0; i < fullNameArr.length; i++) {
            giftDto = new GiftDto();
            giftDto.setGoodsName(fullNameArr[i]);
            if(StringUtil.isNotBlank(imgUrlArr[i])) {
                giftDto.setImgUrl(ImgUtil.getFh200(imgUrlArr[i]));
            }else {
                giftDto.setImgUrl(imgUrlArr[i]);
            }
            giftList.add(giftDto);
        }
        return giftList;
    }

    public void setGiftList(List<GiftDto> giftList) {
        this.giftList = giftList;
    }

    public Integer getGiftStatus() {
        return giftStatus;
    }

    public void setGiftStatus(Integer giftStatus) {
        this.giftStatus = giftStatus;
    }

    public String getStatusText() {
        return ActivityStatus.getName(this.giftStatus);
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls;
    }

    public String getFullNames() {
        return fullNames;
    }

    public void setFullNames(String fullNames) {
        this.fullNames = fullNames;
    }
}
