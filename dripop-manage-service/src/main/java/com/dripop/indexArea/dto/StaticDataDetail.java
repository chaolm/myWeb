package com.dripop.indexArea.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 广告图详情实体类
 *
 * @author dq
 * @date 2018/3/12 16:07
 */

public class StaticDataDetail implements Serializable {
    /*广告图集*/
    private List<IndexAreaFullDto> linkList;


    public List<IndexAreaFullDto> getLinkList() {
        return linkList;
    }
    public void setLinkList(List<IndexAreaFullDto> linkList) {
        this.linkList = linkList;
    }
}
