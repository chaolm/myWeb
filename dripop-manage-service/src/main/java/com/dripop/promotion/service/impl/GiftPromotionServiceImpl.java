package com.dripop.promotion.service.impl;

import com.bean.ActivityStatus;
import com.bean.LogicDelete;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.exception.ServiceException;
import com.dripop.core.util.DateUtil;
import com.dripop.core.util.StringUtil;
import com.dripop.dao.GiftDao;
import com.dripop.dao.GiftPromotionDao;
import com.dripop.dao.GoodsGiftDao;
import com.dripop.dao.GoodsOnlineDao;
import com.dripop.entity.TGift;
import com.dripop.entity.TGiftPromotion;
import com.dripop.entity.TGoodsGift;
import com.dripop.promotion.dto.*;
import com.dripop.promotion.service.GiftPromotionService;
import com.dripop.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liyou on 2018/2/5.
 */
@Service
public class GiftPromotionServiceImpl implements GiftPromotionService {

    @Autowired
    private GiftPromotionDao giftPromotionDao;

    @Autowired
    private GiftDao giftDao;

    @Autowired
    private GoodsGiftDao goodsGiftDao;

    @Autowired
    private GoodsOnlineDao goodsOnlineDao;


    public Pagination<GiftPromotionPageDto> page(GiftPromotionPageReq reqDto, Pageable pageable) {
        StringBuffer sb = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sb.append("select * FROM (select tgp.*, " +
                "GROUP_CONCAT(case when tgd.id is not null then tgd.small_pic else '' end) imgUrls, " +
                "GROUP_CONCAT(case when tgd.id is not null then tgd.full_name else tg.ref_val end) fullNames, " +
                "(case when tgp.start_time > now() then 1 " +
                "when tgp.end_time < now() then 4 " +
                "when tgp.status = 2 then 3 " +
                "else 2 end) giftStatus " +
                "from t_gift_promotion tgp " +
                "left join t_gift tg on tg.promotion_id = tgp.id " +
                "left join t_goods_online tgo on tgo.online_id = tg.ref_val and tg.type = 1 " +
                "left join t_goods tgd on tgd.id = tgo.spu_id group by tgp.id) t where t.logic_delete = :logicDelete ");
        params.add(LogicDelete.NOT_DELETE.getValue());
        if (reqDto.getStartTime() != null) {
            sb.append("and t.start_time >= :startTime ");
            params.add(reqDto.getStartTime());
        }
        if (reqDto.getEndTime() != null) {
            sb.append("and t.end_time <= :endTime ");
            params.add(reqDto.getEndTime());
        }

        if (StringUtil.isNotBlank(reqDto.getName())) {
            sb.append("and t.name like :name ");
            params.add("%" + reqDto.getName() + "%");
        }

        if (reqDto.getStatus() != null) {
            sb.append("and t.giftStatus = :status ");
            params.add(reqDto.getStatus());
        }
        sb.append("order by t.create_time desc");
        return giftPromotionDao.findPageBySql(sb.toString(), pageable, GiftPromotionPageDto.class, params.toArray());
    }

    @Transactional
    public void setting(Long id, Integer status) {
        TGiftPromotion giftPromotion = giftPromotionDao.get(id);
        giftPromotion.setStatus(status);
        giftPromotion.setUpdateTime(new Date());
        giftPromotion.setModifier(UserUtil.currentAdminUser().getId());
        giftPromotionDao.update(giftPromotion);
    }

    @Transactional
    public void delete(Long id) {
        TGiftPromotion giftPromotion = giftPromotionDao.get(id);
        giftPromotion.setLogicDelete(LogicDelete.DELETE.getValue());
        giftPromotion.setUpdateTime(new Date());
        giftPromotion.setModifier(UserUtil.currentAdminUser().getId());
        giftPromotionDao.update(giftPromotion);
    }


    @Transactional
    public void saveOrUpdateGift(GiftDetailDto dto) {
        GiftPromotionDto promotionDto = dto.getGiftPromotionDto();
        if (promotionDto.getId() == null) {
            saveGiftConfugure(dto);
        } else {
            updateConfigure(dto);
        }
    }


    public void saveGiftConfugure(GiftDetailDto dto) {

        GiftPromotionDto promotionDto = dto.getGiftPromotionDto();
        TGiftPromotion promotion = new TGiftPromotion();

        promotion.setStartTime(promotionDto.getStartTime());
        promotion.setEndTime(promotionDto.getEndTime());
        promotion.setRemark(promotionDto.getRemark());
        promotion.setName(promotionDto.getName());

        promotion.setCreateTime(new Date());
        promotion.setCreator(UserUtil.currentAdminUser().getId());
        promotion.setUpdateTime(new Date());
        promotion.setModifier(UserUtil.currentAdminUser().getId());
        promotion.setLogicDelete(LogicDelete.NOT_DELETE.getValue());

        promotion.setStatus(1);
        giftPromotionDao.insert(promotion);

        addGift(dto, promotion.getId());
    }


    public void updateConfigure(GiftDetailDto dto) {
        GiftPromotionDto promotionDto = dto.getGiftPromotionDto();
        TGiftPromotion promotion = giftPromotionDao.get(promotionDto.getId());

        if (promotion != null) {
            promotion.setName(promotionDto.getName());
            promotion.setRemark(promotionDto.getRemark());
            promotion.setEndTime(promotionDto.getEndTime());
            promotion.setStartTime(promotionDto.getStartTime());
            promotion.setUpdateTime(new Date());
            promotion.setModifier(UserUtil.currentAdminUser().getId());
            giftPromotionDao.update(promotion);
        }

        StringBuffer sb = new StringBuffer();
        List<Object> parms = new ArrayList<>();
        parms.add(promotion.getId());
        sb.append("select id from t_gift where promotion_id=:promotionId");
        List<Long> giftIds = giftDao.findManyBySql(sb.toString(), Long.class, parms.toArray());
        for (Long id : giftIds) {
            giftDao.delete(id);
        }

        sb.setLength(0);
        sb.append("select id from t_goods_gift where promotion_id=:promotionId");
        List<Long> goodsIds = goodsGiftDao.findManyBySql(sb.toString(), Long.class, parms.toArray());
        for (Long id : goodsIds) {
            goodsGiftDao.delete(id);
        }

        addGift(dto, promotion.getId());
    }

    public void addGift(GiftDetailDto dto, Long promotionId) {

        List<GiftGoodsDto> giftGoodsDtos = dto.getGiftGoodsDtos();
        for (GiftGoodsDto giftGoodsDto : giftGoodsDtos) {
            if (giftGoodsDto.getNum() == null || giftGoodsDto.getTotalNum() == null) {
                throw new ServiceException("赠品总量或曾赠送数量不能为空");
            }
            if (giftGoodsDto.getOnlineId() != null) {
                TGift gift = new TGift();
                gift.setPromotionId(promotionId);
                gift.setTotalNum(giftGoodsDto.getTotalNum());
                gift.setStock(giftGoodsDto.getTotalNum());
                gift.setRefVal(giftGoodsDto.getOnlineId().toString());
                gift.setNum(giftGoodsDto.getNum());
                gift.setType(1);
                gift.setStock(giftGoodsDto.getTotalNum());
                giftDao.insert(gift);
            } else {
                TGift gift = new TGift();
                gift.setNum(giftGoodsDto.getNum());
                gift.setRefVal(giftGoodsDto.getFullName());
                gift.setTotalNum(giftGoodsDto.getTotalNum());
                gift.setStock(giftGoodsDto.getTotalNum());
                gift.setPromotionId(promotionId);
                gift.setType(2);
                giftDao.insert(gift);
            }
        }


        List<GiftMainGoodsDto> giftMainGoodsDtos = dto.getGiftMainGoodsDtos();
        for (GiftMainGoodsDto mainGoodsDto : giftMainGoodsDtos) {
            TGoodsGift goodsGift = new TGoodsGift();
            goodsGift.setPromotionId(promotionId);
            goodsGift.setOnlineId(mainGoodsDto.getOnlineId());
            goodsGiftDao.insert(goodsGift);
        }


    }


    @Transactional
    public GiftDetailDto findGiftDetail(Long promotionId) {
        List<Object> parms = new ArrayList<Object>();
        parms.add(promotionId);

        /*查出 促销品 的列表*/
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT tgc.stock,tgc.num,tgc.total_num,tgc.type,tgo.sale_price AS salePrice," +
                "tgt. NAME AS typeName,tb. NAME AS brandName," +
                "tg.full_name,tgo.stock goodStock," +
                "tg.small_pic AS picUrl FROM t_gift tgc " +
                "LEFT JOIN t_goods_online tgo ON tgc.ref_val= tgo.online_id " +
                "LEFT JOIN t_goods tg ON tgo.spu_id = tg.id " +
                "LEFT JOIN t_brand tb ON tg.brand_id = tb.id " +
                "LEFT JOIN t_goods_type tgt ON tg.type_id = tgt.id WHERE tgc.promotion_id=:promotionId and tgc.type=1 GROUP BY tgo.online_id ");
        List<GiftGoodsDto> giftGoodsDtos = giftDao.findManyBySql(sb.toString(), GiftGoodsDto.class, parms.toArray());

        sb.setLength(0);
        sb.append("select ref_val as fullName,type,stock,num,total_num from t_gift where promotion_id =:promotionId and type=2");
        List<GiftGoodsDto> giftGoodsDtos2 = giftDao.findManyBySql(sb.toString(), GiftGoodsDto.class, parms.toArray());

        giftGoodsDtos.addAll(giftGoodsDtos2);


        GiftDetailDto dto = new GiftDetailDto();
        dto.setGiftGoodsDtos(giftGoodsDtos);

        /*查出促销主商品的列表*/
        sb.setLength(0);
        sb.append("SELECT tgg.online_id as onlineId,tgo.sale_price AS salePrice,tgt. NAME AS typeName," +
                "tb. NAME AS brandName,tg.full_name," +
                "tgo.stock goodStock,tg.small_pic AS picUrl FROM t_goods_gift tgg " +
                "LEFT JOIN t_goods_online tgo ON tgg.online_id = tgo.online_id " +
                "LEFT JOIN t_goods tg ON tgo.spu_id = tg.id " +
                "LEFT JOIN t_brand tb ON tg.brand_id = tb.id " +
                "LEFT JOIN t_goods_type tgt ON tg.type_id = tgt.id WHERE  tgg.promotion_id =:promotionId  GROUP BY tgg.online_id");
        List<GiftMainGoodsDto> giftMainGoodsDtos = goodsGiftDao.findManyBySql(sb.toString(), GiftMainGoodsDto.class, parms.toArray());
        dto.setGiftMainGoodsDtos(giftMainGoodsDtos);


        TGiftPromotion promotion = giftPromotionDao.get(promotionId);
        GiftPromotionDto promotionDto = new GiftPromotionDto();
        promotionDto.setId(promotion.getId());
        promotionDto.setName(promotion.getName());
        promotionDto.setRemark(promotion.getRemark());
        promotionDto.setStartTime(promotion.getStartTime());
        promotionDto.setEndTime(promotion.getEndTime());
        Integer status = judgeStatus(promotion);


        promotionDto.setGiftStatus(status);
        dto.setGiftPromotionDto(promotionDto);
        return dto;
    }


    public Integer judgeStatus(TGiftPromotion promotion) {
        Date now = DateUtil.getNowTime();
        if (promotion.getStartTime().getTime() > now.getTime()) {
            return ActivityStatus.UN_START.getValue();
        } else if (promotion.getEndTime().getTime() < now.getTime()) {
            return ActivityStatus.FINISH.getValue();
        }
         else if (promotion.getStatus() == 2) {
            return ActivityStatus.SUSPEND.getValue();
        } else {
            return ActivityStatus.WORKING.getValue();
        }


    }

}