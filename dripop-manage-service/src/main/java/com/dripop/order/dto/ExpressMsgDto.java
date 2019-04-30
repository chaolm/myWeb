package com.dripop.order.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * 类名及用途
 *
 * @author dq
 * @date 2018/4/16 9:22
 */

public class ExpressMsgDto implements Serializable {
    /**
     * 承运快递名称
     */
    private String expressName;

    /**
     * 承运快递号
     */
    private String expressNo;

    /**
     * 承运快递联系方式
     */
    private String expressPhone;

    @JSONField(serialize = false)
    private String data;

    /**
     * 物流信息
     */
    private List<ExpressDetailDto> expressDetailDto;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getExpressPhone() {
        return expressPhone;
    }

    public void setExpressPhone(String expressPhone) {
        this.expressPhone = expressPhone;
    }

    public List<ExpressDetailDto> getExpressDetailDto() {
        return JSON.parseArray(data, ExpressDetailDto.class);
    }

    public void setExpressDetailDto(List<ExpressDetailDto> expressDetailDto) {
        this.expressDetailDto = expressDetailDto;
    }
}