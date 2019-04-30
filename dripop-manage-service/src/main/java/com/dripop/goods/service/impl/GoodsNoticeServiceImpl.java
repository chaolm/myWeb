package com.dripop.goods.service.impl;

import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.util.StringUtil;
import com.dripop.dao.GoodsNoticeDao;
import com.dripop.goods.dto.GoodsNoticeDto;
import com.dripop.goods.dto.GoodsNoticePageReq;
import com.dripop.goods.service.GoodsNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyou on 2018/3/24.
 */
@Service
public class GoodsNoticeServiceImpl implements GoodsNoticeService {

    @Autowired
    private GoodsNoticeDao goodsNoticeDao;

    public Pagination<GoodsNoticeDto> page(GoodsNoticePageReq reqDto, Pageable pageable) {
        StringBuffer sb = new StringBuffer();
        List<Object> params = new ArrayList<>();
        sb.append("select tgn.*, tg.small_pic imgUrl, tgn.cust_phone phoneNo, tgn.goods_online_name goodsName, " +
                "tgn.is_used status, tc.nick_name FROM t_goods_notice tgn " +
                "left join t_customer tc on tc.id = tgn.cust_id " +
                "left join t_goods_online tgo on tgo.online_id = tgn.online_id " +
                "left join t_goods tg on tg.id = tgo.spu_id where 1=1 ");
        if(StringUtil.isNotBlank(reqDto.getPhoneNo())) {
            sb.append("and tgn.cust_phone = :phoneNo ");
            params.add(reqDto.getPhoneNo());
        }
        if(StringUtil.isNotBlank(reqDto.getGoodsName())) {
            sb.append("and tgn.goods_online_name like :goodsName ");
            params.add("%"+reqDto.getGoodsName()+"%");
        }
        if(reqDto.getStartTime() != null) {
            sb.append("and tgn.record_time >= :startTime ");
            params.add(reqDto.getStartTime());
        }
        if(reqDto.getEndTime() != null) {
            sb.append("and tgn.record_time <= :endTIme ");
            params.add(reqDto.getEndTime());
        }
        if(reqDto.getStatus() != null) {
            sb.append("and tgn.is_used = :isUsed ");
            params.add(reqDto.getStatus());
        }
        if(reqDto.getNoticeType() != null){
            sb.append(" and tgn.notice_type = :noticeType ");
            params.add(reqDto.getNoticeType());
        }
        sb.append("order by tgn.id desc");
        return goodsNoticeDao.findPageBySql(sb.toString(), pageable, GoodsNoticeDto.class, params.toArray());
    }
}
