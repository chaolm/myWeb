/**
 * 文件名: Pagination.java    2012-7-9
 * ©Copyright 2012 北京广信智远信息技术有限责任公司.  All rights reserved.
 * 如果需要使用此文件，请参考北京广信智远信息技术有限责任公司的授权协议文件
 */
package com.dripop.core.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结构
 * @作者 AMY
 * @版本 1.0
 */
public class Pagination<T> implements Serializable
{
    /**
     * serialVersionUID TODO
     */
    private static final long serialVersionUID = -7498877508026528638L;

    /**
     * limit 最大记录数
     */
    private int limit=25;
    
    /**
     * page 当前页面
     */
    private int page=1;
    
    /**
     * total 总记录条数
     */
    private long total = 0;
    
    /**
     * items 符合记录的分页数据
     */
    private List<T> items;
    
    private Pageable pageable;

    /**
     * 获取属性{@link #limit limit}的值
     */
    public int getLimit()
    {
        return limit;
    }

    /**
     * 给属性{@link #limit limit}设置新值
     */
    public void setLimit(int limit)
    {
        this.limit = limit;
    }

    /**
     * 获取属性{@link #page page}的值
     */
    public int getPage()
    {
        return page;
    }

    /**
     * 给属性{@link #page page}设置新值
     */
    public void setPage(int page)
    {
        this.page = page;
    }

    /**
     * 获取属性{@link #total total}的值
     */
    public long getTotal()
    {
        return total;
    }

    /**
     * 给属性{@link #total total}设置新值
     */
    public void setTotal(long total)
    {
        this.total = total;
    }

    /**
     * 获取属性{@link #items items}的值
     */
    public List<T> getItems()
    {
        return items;
    }

    /**
     * 给属性{@link #items items}设置新值
     */
    public void setItems(List<T> items)
    {
        this.items = items;
    }
    
    /**
     * 获取起始位置
     * @return   起始位置
     */
    public Integer getStart(){
        return (page-1)*limit;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }
}
