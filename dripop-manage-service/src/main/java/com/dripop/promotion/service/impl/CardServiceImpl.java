package com.dripop.promotion.service.impl;

import com.bean.ActivityStatus;
import com.bean.CardReceiveType;
import com.bean.IsUsed;
import com.bean.LogicDelete;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.util.DateUtil;
import com.dripop.core.util.StringUtil;
import com.dripop.dao.*;
import com.dripop.entity.TCard;
import com.dripop.entity.TCustomer;
import com.dripop.entity.TCustomerCard;
import com.dripop.entity.TGoodsCard;
import com.dripop.promotion.dto.*;
import com.dripop.promotion.service.CardService;
import com.dripop.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liyou on 2018/2/5.
 */
@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardDao cardDao;
    @Autowired
    private GoodsCardDao goodsCardDao;

    @Autowired
    private GoodsOnlineDao goodsOnlineDao;

    @Autowired
    private CustomerCardDao customerCardDao;

    @Autowired
    private CustomerDao customerDao;

    public Pagination<CardPageDto> cardPage(CardPageReq reqDto, Pageable pageable) {
        StringBuffer sb = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sb.append("select t.id," +
                "t.name," +
                "t.card_val," +
                "t.receive_type," +
                "t.start_time," +
                "t.end_time," +
                "t.use_end_time," +
                "t.cardStatus," +
                "t.card_type," +
                "t.min_use_price  FROM (select tc.*, " +
                "(case when tc.receive_type = 4 then 4 " +
                "when tc.start_time > now() then 1 " +
                "when tc.end_time < now() then 4 " +
                "when tc.status = 2 then 3 " +
                "else 2 end) cardStatus " +
                "from t_card tc ORDER BY tc.create_time DESC) t where t.logic_delete = :logicDelete ");
        params.add(LogicDelete.NOT_DELETE.getValue());
        if (reqDto.getStartTime() != null) {
            sb.append(" and t.start_time >= :startTime");
            params.add(reqDto.getStartTime());
        }
        if (reqDto.getEndTime() != null) {
            sb.append(" and t.end_time <= :endTime ");
            params.add(reqDto.getEndTime());
        }

        if (reqDto.getReceiveType() != null) {
            sb.append(" and t.receive_type = :receiveType ");
            params.add(reqDto.getReceiveType());
        }

        if (StringUtil.isNotBlank(reqDto.getName())) {
            sb.append(" and t.name like:name ");
            params.add("%" + reqDto.getName() + "%");
        }

        if (reqDto.getStatus() != null) {
            sb.append(" and t.cardStatus =:status ");
            params.add(reqDto.getStatus());
        }

        return cardDao.findPageBySql(sb.toString(), pageable, CardPageDto.class, params.toArray());
    }

    @Transactional
    public void setting(Long id, Integer status) {
        TCard card = cardDao.findOne(id);
        card.setStatus(status);
        card.setUpdateTime(new Date());
        card.setModifier(UserUtil.currentAdminUser().getId());
        cardDao.update(card);
    }

    @Transactional
    public void delete(Long id) {
        TCard card = cardDao.get(id);
        card.setLogicDelete(LogicDelete.DELETE.getValue());
        card.setUpdateTime(new Date());
        card.setModifier(UserUtil.currentAdminUser().getId());
        cardDao.update(card);
    }

    @Transactional
    public void savaOrUpdateCard(CardDetailDto dto) {
        TCard card = dto.getCard();
        if (card.getId() == null) {
            addCard(dto);
        } else {
            updateCard(dto);
        }
    }


    public void addCard(CardDetailDto dto) {
        TCard card = dto.getCard();

        if (card.getCardType() == 1) {
            if (card.getCardVal() != null) {
                card.setCardVal(card.getCardVal().multiply(new BigDecimal(100)));
            }
        }

        if(card.getUseTimeType()==2){
            card.setUseEndTime(null);
            card.setUseStartTime(null);
        }

        if(card.getUseTimeType()==1){
            card.setUseDays(null);
        }

        card.setCreateTime(new Date());
        //card.setCreator(UserUtil.currentAdminUser().getId());
        card.setCreator(2l);
        card.setUpdateTime(new Date());
   //     card.setModifier(UserUtil.currentAdminUser().getId());
        card.setModifier(2l);
        card.setLogicDelete(LogicDelete.NOT_DELETE.getValue());
        card.setCardRemainNum(card.getCardNum());
        card.setStatus(1);
        cardDao.insert(card);

        forAddAndUpdateCard(dto, card.getId());
    }


    public void updateCard(CardDetailDto dto) {
        TCard card = dto.getCard();

        if (card.getCardType() == 1) {
            if (card.getCardVal() != null) {
                card.setCardVal(card.getCardVal().multiply(new BigDecimal(100)));
            }
        }


        if(card.getUseTimeType()==2){
            card.setUseEndTime(null);
            card.setUseStartTime(null);
        }

        if(card.getUseTimeType()==1){
            card.setUseDays(null);
        }

        card.setUpdateTime(new Date());
        card.setModifier(UserUtil.currentAdminUser().getId());
        cardDao.update(card);

        List<Object> parms = new ArrayList<>();
        parms.add(card.getId());
        StringBuffer sb = new StringBuffer();
        sb.append("select id from t_goods_card where card_id=:cardId");
        List<Long> tgcIds = goodsCardDao.findManyBySql(sb.toString(), Long.class, parms.toArray());
        for (Long id : tgcIds) {
            goodsCardDao.delete(id);
        }

        if (card.getReceiveType() == CardReceiveType.PHONE.getValue()) {
            sb.setLength(0);
            sb.append("select id from t_customer_card where card_id=:cardId");
            List<Long> ccIds = customerCardDao.findManyBySql(sb.toString(), Long.class, parms.toArray());
            for (Long id : ccIds) {
                customerCardDao.delete(id);
            }
        }

        forAddAndUpdateCard(dto, card.getId());

    }


    @Transactional
    public List<TCustomer> selectCustomer(SelectCustomerDto dto) {
        List<Object> parms = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        sb.append("select id,nick_name,phone_no,create_time from t_customer where 1=1 ");
        if (dto.getRegisterDate() != null) {
            sb.append("and create_time>=:registerDate ");
            parms.add(dto.getRegisterDate());
        }
        if (dto.getRegisterDateTwo() != null) {
            sb.append("and create_time<=:registerDateTwo ");
            parms.add(dto.getRegisterDateTwo());
        }
        if (dto.getPhoneNo() != null) {
            sb.append("and phone_no like :phoneNo ");
            parms.add("%" + dto.getPhoneNo() + "%");
        }

        List<TCustomer> customerList = customerDao.findManyBySql(sb.toString(), TCustomer.class, parms.toArray());

        return customerList;
    }


    public void forAddAndUpdateCard(CardDetailDto dto, Long cardId) {
        TCard card = dto.getCard();
        List<CardGoodsDto> cardGoodsDtos = dto.getCardGoodsDtos();
        List<CardGoodsDto> businessGoodsDtos = dto.getBusinessGoodsDtos();
        // 添加 卡券商品关联表
        if (cardGoodsDtos != null && !cardGoodsDtos.isEmpty()) {
            for (CardGoodsDto goodsDto : cardGoodsDtos) {
                TGoodsCard goodsCard = new TGoodsCard();
                goodsCard.setType(1);
                goodsCard.setCardId(cardId);
                goodsCard.setOnlineId(goodsDto.getOnlineId());
                goodsCardDao.insert(goodsCard);
            }

        }

        if (businessGoodsDtos != null && !businessGoodsDtos.isEmpty()) {
            for (CardGoodsDto goodsDto : businessGoodsDtos) {
                TGoodsCard goodsCard = new TGoodsCard();
                goodsCard.setType(2);
                goodsCard.setCardId(cardId);
                goodsCard.setOnlineId(goodsDto.getOnlineId());
                goodsCardDao.insert(goodsCard);
            }

        }


        //指定用户发放 4 --添加用户卡券表
        if (card.getReceiveType() == CardReceiveType.PHONE.getValue()) {
            List<Long> custIds = dto.getCustomerIds();
            if (custIds != null && !custIds.isEmpty()) {
                for (Long custId : custIds) {
                    TCustomerCard cd = new TCustomerCard();
                    cd.setCustId(custId);
                    cd.setCardId(cardId);
                    cd.setCreateTime(new Date());
                    if(card.getUseTimeType()==2){
                        card.setUseStartTime(new Date());
                        card.setUseEndTime(DateUtil.dateAdd(1,new Date(),card.getUseDays()));
                    }
                    cd.setEndTime(card.getUseEndTime());
                    cd.setStartTime(card.getUseStartTime());
                    cd.setStatus(IsUsed.USED.getValue());
                    customerCardDao.insert(cd);
                }
            }
        }
    }





    public List<Long> getCustIdList(List<String> phoneList){
        int totalNum=phoneList.size();
        List<Long> custIdList=new ArrayList<>();
        for (int i=0;i<=totalNum/500;i++){
            int startNum=i*500;
            int endNum=startNum+500;
            if(i==totalNum/500){
               endNum=phoneList.size();
            }
            if(endNum==startNum){
               break;
            }
            List<String> subPhoneList=phoneList.subList(startNum,endNum);
            String str="SELECT id FROM t_customer WHERE phone_no in(:phoneList)";
            List<Long> subCustIds=customerDao.findManyBySql(str,Long.class,subPhoneList);
            custIdList.addAll(subCustIds);
        }
        return custIdList;
    }



    @Transactional
    public CardDetailDto findCardDetail(Long cardId) {

        CardDetailDto dto = new CardDetailDto();

        TCard card = cardDao.get(cardId);

        dto.setCard(card);

        List<Object> parms = new ArrayList<Object>();
        parms.add(cardId);
        StringBuffer sb = new StringBuffer();
        List<CardGoodsDto> cardGoodsDtos = new ArrayList<>();

        /*优惠券详情中--在线商品列表*/
        sb.append("select tgc.online_id,tgc.type, tgo.sale_price as salePrice, tgt.name as typeName, " +
                "tb.name as brandName, tg.full_name,tg.stock as stock, " +
                "tg.small_pic as picUrl from t_goods_card tgc " +
                "left join t_goods_online tgo on tgc.online_id=tgo.online_id " +
                "left join t_goods tg on tgo.spu_id=tg.id " +
                "left join t_brand tb on tg.brand_id=tb.id " +
                "left join t_goods_type tgt on tg.type_id=tgt.id where tgc.card_id=:cardId ");
        if (card.getReceiveType() != 3) {
            sb.append("and tgc.type=1");
            cardGoodsDtos = goodsCardDao.findManyBySql(sb.toString(), CardGoodsDto.class, parms.toArray());
        }


        List<CardGoodsDto> businessDtos = new ArrayList<>();
        if (card.getReceiveType() == 3) {
            cardGoodsDtos = goodsCardDao.findManyBySql(sb.toString(), CardGoodsDto.class, parms.toArray());
            for (int i = 0; i < cardGoodsDtos.size(); i++) {
                CardGoodsDto cardGoodsDto = cardGoodsDtos.get(i);
                if (cardGoodsDto.getType() == 2) {
                    businessDtos.add(cardGoodsDto);
                    cardGoodsDtos.remove(cardGoodsDto);
                }
            }

        }
        dto.setBusinessGoodsDtos(businessDtos);
        dto.setCardGoodsDtos(cardGoodsDtos);

        /*优惠券详情中--用户领取记录列表*/
        List<UserClaimRecordDto> recordDtoList = getUCRList(cardId);
        dto.setUserClaimRecordDtoList(recordDtoList);

        return dto;
    }


    /**
     * @param cardId
     * @return
     */
    public List<UserClaimRecordDto> getUCRList(Long cardId) {

        StringBuffer sb = new StringBuffer();
        sb.append("select tcc.cust_id , tc.phone_no, tc.nick_name as name, tc.create_time as register_time, tcc.create_time as receive_time, \n" +
                "count(tcc.id) as receive_num , count(case when tcc.status = 2 then 1 else null end) as use_num from t_customer_card tcc \n" +
                "left join t_customer tc on tc.id = tcc.cust_id \n" +
                "where tcc.card_id =:cardId group by tcc.cust_id\n");

        List<Object> parms = new ArrayList<>();
        parms.add(cardId);
        List<UserClaimRecordDto> ucrList = cardDao.findManyBySql(sb.toString(), UserClaimRecordDto.class, parms.toArray());
        return ucrList;
    }


    public Integer judgeStatus(TCard tcard) {
        Date now = DateUtil.getNowTime();
        if (tcard.getStartTime().getTime() > now.getTime()) {
            return ActivityStatus.UN_START.getValue();
        } else if (tcard.getEndTime().getTime() < now.getTime()) {
            return ActivityStatus.FINISH.getValue();
        } else if (tcard.getStatus() == 2) {
            return ActivityStatus.SUSPEND.getValue();
        } else {
            return ActivityStatus.WORKING.getValue();
        }

    }


}
