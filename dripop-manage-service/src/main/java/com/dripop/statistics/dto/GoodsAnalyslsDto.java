package com.dripop.statistics.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 用户访问商品次数统计实体类
 *
 * @author dq
 * @date 2018/3/17 11:19
 */

public class GoodsAnalyslsDto implements Serializable {
    /*时间*/
    private String time;
    /*访问总次数*/
    private Integer total;
    /*访问总人数*/
    private Integer peopleNum;
    /*用户访问人数*/
    private Integer customerNum;
    /*最近几天访问数据集*/
    private List<GoodsAnalyslsDto> list;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }

    public Integer getCustomerNum() {
        return customerNum;
    }

    public void setCustomerNum(Integer customerNum) {
        this.customerNum = customerNum;
    }

    public List<GoodsAnalyslsDto> getList() {
        return list;
    }

    public void setList(List<GoodsAnalyslsDto> list) {
        this.list = list;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
