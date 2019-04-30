package com.dripop.goods.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bean.IsUsed;
import com.bean.LogicDelete;
import com.dripop.bean.GoodsSolrSeachDto;
import com.dripop.constant.RedisKey;
import com.dripop.core.bean.Filter;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.bean.QLBuilder;
import com.dripop.core.exception.ServiceException;
import com.dripop.core.util.DateUtil;
import com.dripop.core.util.RedisUtil;
import com.dripop.core.util.StringUtil;
import com.dripop.dao.*;
import com.dripop.entity.*;
import com.dripop.goods.dto.*;
import com.dripop.goods.service.GoodsService;
import com.dripop.solr.SolrjServer;
import com.dripop.util.UserUtil;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by liyou on 2018/1/10.
 */
@Service
public class GoodsServiceImpl implements GoodsService {
    private Logger logger = LoggerFactory.getLogger(GoodsServiceImpl.class);

    static final Integer expireTime = 2 * 60 * 60;//两小时

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private GoodsOnlineDao goodsOnlineDao;

    @Autowired
    private GoodsModelDao goodsModelDao;

    @Autowired
    private PhotoBookDao photoBookDao;

    @Autowired
    private GoodsParamDao goodsParamDao;

    @Autowired
    private GoodsTypeDao goodsTypeDao;

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private VisitDao visitDao;
    @Autowired
    private PackageSaleDao packageSaleDao;

    public Pagination<OpGoodsSearchDto> pageQuery(OpGoodsSearchReq req, Pageable pageable) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sb = new StringBuffer();
        sb.append("select tg.id goodsId, tgo.online_id,tg.small_pic smallPic,tgo.is_used status, tgo.stock,tg.full_name, tgo.sale_price,tgo.goods_sell_type,tgo.presell_money, tgt.name typeName, tb.name brandName " +
                "FROM t_goods tg left join t_goods_online tgo on tg.id = tgo.spu_id " +
                "left join t_goods_type tgt on tgt.id = tg.type_id " +
                "left join t_brand tb on tb.id = tg.brand_id " +
                "where tg.is_used = :isUsed and (tg.logic_delete is null or tg.logic_delete = :logicDelete) ");
        params.add(IsUsed.USED.getValue());
        params.add(LogicDelete.NOT_DELETE.getValue());
        if (req.getTypeId() != null) {
            TGoodsType goodsType = goodsTypeDao.get(req.getTypeId());
            sb.append("and tgt.full_path like :typeId ");
            params.add(goodsType.getFullPath() + "%");
        }
        if (req.getBrandId() != null) {
            sb.append("and tb.id = :brandId ");
            params.add(req.getBrandId());
        }
        if (req.getStatus() != null) {
            sb.append(req.getStatus().equals(IsUsed.USED.getValue()) ? "and tgo.is_used = :status " : "and (tgo.is_used = :status or tgo.is_used is null) ");
            params.add(req.getStatus());
        }
        if (StringUtil.isNotBlank(req.getFullName())) {
            sb.append("and tg.full_name like :fullName ");
            params.add("%" + req.getFullName() + "%");
        }
        if(req.getGoodsSellType() != null && req.getGoodsSellType() == 1){
            sb.append(" and tgo.goods_sell_type = :goodsSellType ");
            params.add(req.getGoodsSellType());
        }
        if(req.getGoodsSellType() != null && req.getGoodsSellType() == 2){
            sb.append(" and tgo.goods_sell_type in (2,3,4) ");
        }
        if(req.getFilterPresell() != null && req.getFilterPresell() == 1){
            sb.append(" and tgo.goods_sell_type = :goodsSellType ");
            params.add(req.getFilterPresell());
        }
        sb.append("order by tg.model_id desc, tg.id desc");
        return goodsDao.findPageBySql(sb.toString(), pageable, OpGoodsSearchDto.class, params.toArray());
    }

    public Pagination<OpGoodsSearchDtoForModel> pageQueryForModel(OpGoodsSearchReq req, Pageable pageable) {
        List<Object> spuParams = new ArrayList<Object>();
        List<Object> SkuParams = new ArrayList<Object>();
        StringBuffer sqlForSku = new StringBuffer();
        StringBuffer sqlForSpu = new StringBuffer("select tgm.id as modelId, tgm.brand_id brandId, " +
                " tgm.type_id typeId, tgm.name as model, tgt.name typeName, tb.name brandName" +
                " FROM t_goods_model tgm  " +
                "left join t_goods_type tgt on tgt.id = tgm.type_id " +
                "left join t_brand tb on tb.id = tgm.brand_id " +
                "where tgm.is_used = :isUsed ");
        spuParams.add(IsUsed.USED.getValue());
        if(StringUtil.isNotBlank(req.getFullName())) {
            sqlForSpu.append("and exists (select id from t_goods tg left join t_goods_online tgo on tg.id = tgo.spu_id " +
                    "where tg.model_id = tgm.id and (REPLACE(tg.full_name, ' ', '') like :fullName or REPLACE(tgm.name, ' ', '') like :fullName)) ");
            spuParams.add("%" + req.getFullName() + "%");
        }
        if(req.getStatus() != null) {
            sqlForSpu.append("and exists (select id from t_goods tg left join t_goods_online tgo on tg.id = tgo.spu_id " +
                    "where tg.model_id = tgm.id and tg.is_used = 1 and tgo.is_used = :status) ");
            spuParams.add(req.getStatus());
        }
        if(req.getGoodsSellType() != null && req.getGoodsSellType() == 1){
            sqlForSpu.append("and exists (select id from t_goods tg left join t_goods_online tgo on tg.id = tgo.spu_id " +
                    "where tg.model_id = tgm.id and tg.is_used = 1 and tgo.goods_sell_type = :goodsSellType) ");
            spuParams.add(req.getGoodsSellType());
        }
        if(req.getGoodsSellType() != null && req.getGoodsSellType() == 2){
            sqlForSpu.append("and exists (select id from t_goods tg left join t_goods_online tgo on tg.id = tgo.spu_id " +
                    "where tg.model_id = tgm.id and tg.is_used = 1 and (tgo.goods_sell_type in (2,3,4))) ");
        }
        if (req.getTypeId() != null) {
            TGoodsType goodsType = goodsTypeDao.get(req.getTypeId());
            sqlForSpu.append("and tgt.full_path like :typeId ");
            spuParams.add(goodsType.getFullPath() + "%");
        }
        if (req.getBrandId() != null) {
            sqlForSpu.append("and tb.id = :brandId ");
            spuParams.add(req.getBrandId());
        }

        sqlForSpu.append("order by tgm.id desc");
        Pagination<OpGoodsSearchDtoForModel> spuPage = goodsDao.findPageBySql(sqlForSpu.toString(), pageable, OpGoodsSearchDtoForModel.class, spuParams.toArray());

        sqlForSku.append("select tg.id goodsId, tg.model, tg.model_id modelId, tgo.online_id, tgo.is_used status, tgo.stock,tg.full_name, tgo.sale_price, " +
                "tgo.goods_sell_type,tgo.presell_money " +
                "FROM t_goods tg left join t_goods_online tgo on tg.id = tgo.spu_id " +
                "left join t_goods_type tgt on tgt.id = tg.type_id " +
                "left join t_brand tb on tb.id = tg.brand_id " +
                "where tg.is_used = :isUsed and (tg.logic_delete is null or tg.logic_delete = :logicDelete) ");
        SkuParams.add(IsUsed.USED.getValue());
        SkuParams.add(LogicDelete.NOT_DELETE.getValue());
        if (req.getTypeId() != null) {
            TGoodsType goodsType = goodsTypeDao.get(req.getTypeId());
            sqlForSku.append("and tgt.full_path like :typeId ");
            SkuParams.add(goodsType.getFullPath() + "%");
        }
        if (req.getBrandId() != null) {
            sqlForSku.append("and tb.id = :brandId ");
            SkuParams.add(req.getBrandId());
        }
        if (req.getStatus() != null) {
            sqlForSku.append( "and tgo.is_used = :status ");
            SkuParams.add(req.getStatus());
        }
        if (StringUtil.isNotBlank(req.getFullName())) {
            sqlForSku.append("and REPLACE(tg.full_name, ' ', '') like :fullName ");
            SkuParams.add("%" + req.getFullName() + "%");
        }
        if(req.getGoodsSellType() != null && req.getGoodsSellType() == 1){
            sqlForSku.append(" and tgo.goods_sell_type = :goodsSellType ");
            SkuParams.add(req.getGoodsSellType());
        }
        if(req.getGoodsSellType() != null && req.getGoodsSellType() == 2){
            sqlForSku.append(" and tgo.goods_sell_type in (2,3,4) ");
        }
        sqlForSku.append("order by tg.model_id desc, tg.id desc");
        List<OpGoodsSkuDto> skuList = goodsDao.findManyBySql(sqlForSku.toString(), OpGoodsSkuDto.class, SkuParams.toArray());
        List<OpGoodsSkuDto> spuSkuList = null;
        for (OpGoodsSearchDtoForModel spuDto: spuPage.getItems()) {
            spuSkuList = new ArrayList<>();
            for (OpGoodsSkuDto skuDto: skuList) {
                if (skuDto.getModelId().equals(spuDto.getModelId())) {
                    spuSkuList.add(skuDto);
                }
            }
            spuDto.setSkuList(spuSkuList);
        }
        return spuPage;
    }

    @Transactional
    public void addOrEdit(TGoodsOnline goodsOnline) {
//        List<TGoodsOnline> list = getListByGoodsId(goodsOnline.getGoodsId(), goodsOnline.getGoodsSellType());
//        if(!CollectionUtils.isEmpty(list)) {
//            throw new ServiceException("该商品已存在上架信息");
//        }
        CheckPrice(goodsOnline);
        CheckTime(goodsOnline);
        TGoods goods = goodsDao.get(goodsOnline.getGoodsId());
        goods.setModifyTime(new Date());
        goods.setModifyUserId(UserUtil.currentAdminUser().getId());
        goodsDao.update(goods);
        String string = null;
        if (goodsOnline.getId() != null) {
            if(goodsOnline.getGoodsSellType() != null && goodsOnline.getGoodsSellType() != 1){
                string = "select tcpr.id from  t_combo_package tcp\n" +
                        "LEFT JOIN t_combo_package_rel  tcpr on tcpr.package_id = tcp.id\n" +
                        "where tcp.is_used = :isUsed and tcpr.online_id =:onLineId ";
                List<Long> list = packageSaleDao.findManyBySql(string,Long.class,IsUsed.USED.getValue(),goodsOnline.getId());
                for(Long param : list){
                    if(param != null){
                        throw new ServiceException("该商品目前处于套餐中");
                    }
                }
            }
            goodsOnline.setIsUsed(IsUsed.USED.getValue());
            goodsOnlineDao.update(goodsOnline);
        } else {
            goodsOnline.setIsUsed(IsUsed.USED.getValue());
            goodsOnlineDao.insert(goodsOnline);
        }
    }

    private void CheckTime(TGoodsOnline goodsOnline) {
        if(goodsOnline.getGoodsSellType() == 2){
            if(1 == DateUtil.compareDate(goodsOnline.getPresellStartTime(),goodsOnline.getPresellEndTime())){
                throw new ServiceException("请填写正确预售时间");
            }
            if(1 == DateUtil.compareDate(goodsOnline.getBalancePaymentStartTime(),goodsOnline.getBalancePaymentEndTime())){
                throw new ServiceException("请填写正确尾款时间");
            }
        }
    }

    private void CheckPrice(TGoodsOnline goodsOnline) {
        if(goodsOnline.getGoodsSellType() == 2){
            if(goodsOnline.getPresellMoney() != null && goodsOnline.getPresellMoney() <= 0){
                throw new ServiceException("请填写正确售价");
            }
            if(goodsOnline.getDeposit() <= 0){
                throw new ServiceException("请填写正确定金");
            }
            if(goodsOnline.getDepositDiscountAmount() != null && goodsOnline.getDepositDiscountAmount() <= 0){
                throw new ServiceException("请填写正确定金抵用金额");
            }
        }else if(goodsOnline.getGoodsSellType() == 3 || goodsOnline.getGoodsSellType() == 4){
            if(goodsOnline.getPresellMoney() != null && goodsOnline.getPresellMoney() <= 0){
                throw new ServiceException("请填写正确售价");
            }
        }else {
            if(goodsOnline.getSalePrice() <= 0){
                throw new ServiceException("请填写正确售价");
            }
        }
    }

    public TGoodsModel getGoodsSpu(Long modelId) {
        TGoodsModel model = goodsModelDao.get(modelId);
        TGoodsType type = goodsTypeDao.get(model.getTypeId());
        model.setParentId(type.getParentId());
        return model;
    }

    @Transactional
    public void deleteGoodsSpu(Long modelId) {

        TGoodsModel model = goodsModelDao.get(modelId);
        QLBuilder ql = new QLBuilder();
        ql.and(Filter.eq("modelId", model.getId()));
        List<TGoods> goodsList = goodsDao.findManyByJpql(ql);
        if (!CollectionUtils.isEmpty(goodsList)) {
            for (TGoods goods : goodsList) {
                QLBuilder qlForOnline = new QLBuilder();
                qlForOnline.and(Filter.eq("goodsId", goods.getId()));
                qlForOnline.and(Filter.eq("isUsed", IsUsed.USED.getValue()));
                List<TGoodsOnline> list = goodsOnlineDao.findManyByJpql(qlForOnline);
                if (!CollectionUtils.isEmpty(list)) {
                    throw new ServiceException("该商品已上架，不能删除");
                }
                goods.setIsUsed(IsUsed.NOT_USED.getValue());
                goods.setModifyTime(new Date());
                goods.setModifyUserId(UserUtil.currentAdminUser().getId());
                goodsDao.update(goods);
            }
        }
        model.setIsUsed(IsUsed.NOT_USED.getValue());
        model.setModifyUserId(UserUtil.currentAdminUser().getId());
        model.setModifyTime(new Date());
        goodsModelDao.update(model);
    }

    @Transactional
    public void saveGoodsSpu(TGoodsModel model, Long modelId) {
        if (StringUtil.isBlank(model.getSellArea())) {
            throw new ServiceException("请选择选择销售区域");
        }
        TGoodsModel  goodsModel = null;
        if (modelId == null) {
            QLBuilder ql = new QLBuilder();
            ql.and(Filter.eq("name", model.getName()));
            ql.and(Filter.eq("typeId", model.getTypeId()));
            ql.and(Filter.eq("brandId", model.getBrandId()));
            goodsModel = goodsModelDao.findOneByJpql(ql);
            if (goodsModel == null) {
                goodsModel = new TGoodsModel();
                goodsModel.setTypeId(model.getTypeId());
                goodsModel.setBrandId(model.getBrandId());
                goodsModel.setName(model.getName());
                goodsModel.setSellArea(model.getSellArea());
                goodsModel.setStatus(1);
                goodsModel.setIsUsed(IsUsed.USED.getValue());
                goodsModel.setModifyUserId(UserUtil.currentAdminUser().getId());
                goodsModel.setModifyTime(new Date());
                goodsModelDao.insert(goodsModel);
            } else {
                throw new ServiceException("该SPU已存在");
            }
        } else {
            goodsModel = goodsModelDao.get(modelId);
            goodsModel.setBrandId(model.getBrandId());
            goodsModel.setTypeId(model.getTypeId());
            goodsModel.setName(model.getName());
            goodsModel.setSellArea(model.getSellArea());
            goodsModelDao.update(goodsModel);
        }
    }

    @Transactional
    public TGoods saveGoods(TGoods goods, String images, List<TGoodsParam> goodsParamList) {
        goods.setSmallPic(goods.getBigPic());
        goods.setStatus(1);
        goods.setIsUsed(1);
        String spec = "";
        if (StringUtil.isNotBlank(goods.getColor())) {
            spec += goods.getColor() + " ";
        }
        if (StringUtil.isNotBlank(goods.getMemory())) {
            spec += goods.getMemory() + " ";
        }
        if (StringUtil.isNotBlank(goods.getStandard())) {
            spec += goods.getStandard() + " ";
        }
        spec = spec.substring(0, spec.length() > 0 ? spec.length() - 1 : 0);
        TBrand brand = brandDao.get(goods.getBrandId());
        TGoodsModel goodsModel = goodsModelDao.get(goods.getModelId());
        if (StringUtil.isNotBlank(spec)) {
            goods.setFullName(brand.getName() + " " + goodsModel.getName() + " " + spec);
        } else {
            goods.setFullName(brand.getName() + " " + goodsModel.getName());
        }
        goods.setSpecs(spec);

        // 校验名称是否重复
        QLBuilder ql = new QLBuilder();
        ql.and(Filter.eq("fullName", goods.getFullName()));
        ql.and(Filter.eq("isUsed", IsUsed.USED.getValue()));
        if (goods.getId() != null) {
            ql.and(Filter.ne("id", goods.getId()));
        }
        List<TGoods> rows = goodsDao.findManyByJpql(ql);
        if (!CollectionUtils.isEmpty(rows)) {
            throw new ServiceException("商品重复，请检查");
        }
        // 保存商品
        if (goods.getId() == null) {
            goods.setCreateUserId(UserUtil.currentAdminUser().getId());
            goods.setCreateTime(new Date());
            goods.setModifyUserId(UserUtil.currentAdminUser().getId());
            goods.setModifyTime(new Date());
            goodsDao.insert(goods);
        } else {
            goods.setModifyUserId(UserUtil.currentAdminUser().getId());
            goods.setModifyTime(new Date());
            goodsDao.update(goods);
        }

        this.saveGoodsParam(goodsParamList, goods.getId());

        // 保存商品对应的图片:先删除原图
        ql = new QLBuilder();
        ql.and(Filter.eq("type", 1));
        ql.and(Filter.eq("refId", goods.getId()));
        photoBookDao.deleteByJpql(ql);
        // 先保存主图
        photoBookDao.save(getPhotoBook(goods.getBigPic(), goods.getId()));
        // 保存其它图片
        String[] other = images.split(",");
        for (String str : other) {
            if(StringUtil.isBlank(str.trim())) {
                continue;
            }
            photoBookDao.save(getPhotoBook(str, goods.getId()));
        }

        return goods;
    }

    public OpGoodsDetailDto getOpGoodsDetail(Long goodsId) {
        StringBuffer sb = new StringBuffer("select a.id goodId, a.model_id modelId, a.type_id typeId,b.name typeName,a.brand_id brandId,c.name brandName,a.color,a.memory,a.standard,");
        sb.append(" a.model, a.small_pic imageUrl,(select group_concat(d.img_url) from t_photo_book d where d.type=1 and d.ref_id=a.id and d.img_url<>a.small_pic) images,")
                .append(" a.pro_descrip proDescrip, a.description, a.maintain_desction maintainDesction,a.spec_description specDescription,a.keyword, a.publish_time ")
                .append(" from t_goods a,t_goods_type b, t_brand c")
                .append(" where a.type_id=b.id and a.brand_id=c.id and a.id= :goodsId");
        List<OpGoodsDetailDto> list = goodsDao.findManyBySql(sb.toString(), OpGoodsDetailDto.class, goodsId);
        if (CollectionUtils.isEmpty(list)) {
            throw new ServiceException("商品不存在!");
        }
        OpGoodsDetailDto opGoodsDetailDto = list.get(0);

        /*String sql = "select b.code propertyCode,b.name propertyName,a.property_value propertyValue from t_goods_property a, t_property b " +
                "where a.property_code=b.code and a.good_id = :goodsId";
        List<Map<String, Object>> datas = goodsDao.findMapBySql(sql, goodsId);
        opGoodsDetailDto.setProperytys(datas);*/
        return opGoodsDetailDto;
    }

    public List<OpParamChannelDto> getOpGoodsParam(Long goodsId, Long typeId) {
        StringBuffer sb = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        if (goodsId != null) {
            sb.append("select tg.id, tpc.id channelId, tpc.`name` channelName, tp.id paramId, tp.name paramName, tp.is_enum isEnum, tp.is_mult isMult, tp.is_required isRequired, tpd.param_val paramVal, tgp.param_val goodsParamVal from t_goods tg \n" +
                    "left join t_goods_type tgt on tgt.id = tg.type_id\n" +
                    "left join t_param_channel tpc on tgt.id = tpc.type_id\n" +
                    "left join t_param tp on tp.channel_id = tpc.id \n" +
                    "left join t_param_data tpd on tpd.param_id = tp.id \n" +
                    "left join t_goods_model tgm on tgm.id = tg.model_id \n" +
                    "left join t_goods_param tgp on tgp.param_id = tp.id and tgp.goods_id = tg.id  " +
                    "where tpc.id is not null ");
            sb.append("and tg.id = :goodsId ");
            params.add(goodsId);
        } else {
            sb.append("select tpc.id channelId, tpc.`name` channelName, tp.id paramId, tp.name paramName, tp.is_enum isEnum, tp.is_mult isMult, tp.is_required isRequired, tpd.param_val paramVal from t_goods_type tgt \n" +
                    "left join t_param_channel tpc on tgt.id = tpc.type_id\n" +
                    "left join t_param tp on tp.channel_id = tpc.id \n" +
                    "left join t_param_data tpd on tpd.param_id = tp.id ");
            sb.append("where tgt.id = :typeId ");
            params.add(typeId);
        }
        sb.append("order by tpc.sort, tp.sort, tpd.sort");

        List<OpGoodsParamFullDetailDto> goodsParams = goodsDao.findManyBySql(sb.toString(), OpGoodsParamFullDetailDto.class, params.toArray());
        List<OpParamChannelDto> paramChannelList = new ArrayList<OpParamChannelDto>();
        Long paramChannelId = null;
        Long paramId = null;
        String paramVal = null;
        OpParamChannelDto opParamChannelDto = null;
        OpGoodsParamDetailDto opGoodsParamDetailDto = null;
        OpParamDataDto opParamDataDto = null;
        for (OpGoodsParamFullDetailDto opGoodsParamFullDetailDto : goodsParams) {
            if (opGoodsParamFullDetailDto.getChannelId() == null) {
                continue;
            }
            if (paramChannelId == null || !paramChannelId.equals(opGoodsParamFullDetailDto.getChannelId())) {
                paramChannelId = opGoodsParamFullDetailDto.getChannelId();
                opParamChannelDto = new OpParamChannelDto();
                opParamChannelDto.setChannelName(opGoodsParamFullDetailDto.getChannelName());
                opParamChannelDto.setGoodsParams(new ArrayList<OpGoodsParamDetailDto>());
                paramChannelList.add(opParamChannelDto);
            }
            if (paramId == null || !paramId.equals(opGoodsParamFullDetailDto.getParamId())) {
                paramId = opGoodsParamFullDetailDto.getParamId();
                paramVal = null;
                opGoodsParamDetailDto = new OpGoodsParamDetailDto();
                org.springframework.beans.BeanUtils.copyProperties(opGoodsParamFullDetailDto, opGoodsParamDetailDto);
                opGoodsParamDetailDto.setParamDatas(new ArrayList<OpParamDataDto>());
                opParamChannelDto.getGoodsParams().add(opGoodsParamDetailDto);
            }
            if (opGoodsParamFullDetailDto.getParamVal() == null) {
                continue;
            }
            if (paramVal == null || !paramVal.equals(opGoodsParamFullDetailDto.getParamVal())) {
                paramVal = opGoodsParamFullDetailDto.getParamVal();
                opParamDataDto = new OpParamDataDto();
                opParamDataDto.setParamVal(opGoodsParamFullDetailDto.getParamVal());
                opGoodsParamDetailDto.getParamDatas().add(opParamDataDto);
            }
        }

        return paramChannelList;
    }

    @Transactional
    public void downGoodsOnlineWeb(Long onlineId) {
        TGoodsOnline goodsOnline = goodsOnlineDao.get(onlineId);
        TGoods goods = goodsDao.get(goodsOnline.getGoodsId());
        goods.setModifyTime(new Date());
        goods.setModifyUserId(UserUtil.currentAdminUser().getId());
        goodsDao.update(goods);
        goodsOnline.setIsUsed(IsUsed.NOT_USED.getValue());
        goodsOnlineDao.update(goodsOnline);
    }


    /**
     * 查询商品的上架信息
     *
     * @param goodsId
     * @param goodsSellType
     * @return
     * @throws Exception
     */
    private List<TGoodsOnline> getListByGoodsId(Long goodsId, Integer goodsSellType) {
        QLBuilder ql = new QLBuilder();
        ql.and(Filter.eq("isUsed", IsUsed.USED.getValue()));
        ql.and(Filter.eq("goodsId", goodsId));
        ql.and(Filter.eq("goodsSellType", goodsSellType));
        return goodsOnlineDao.findManyByJpql(ql);
    }

    private TPhotoBook getPhotoBook(String url, Long refId) {
        TPhotoBook en = new TPhotoBook();
        en.setImgUrl(url.trim());
        en.setType(1);
        en.setRefId(refId);
        en.setModify_date(new Date());
        return en;
    }

    /**
     * 批量保存商品参数
     *
     * @param goodsParamList
     */
    private void saveGoodsParam(List<TGoodsParam> goodsParamList, Long goodsId) {
        if (CollectionUtils.isEmpty(goodsParamList) || goodsId == null) {
            return;
        }
        QLBuilder ql = new QLBuilder();
        ql.and(Filter.eq("goodsId", goodsId));
        goodsParamDao.deleteByJpql(ql);
        for (TGoodsParam goodsParam : goodsParamList) {
            goodsParam.setGoodsId(goodsId);
            goodsParamDao.insert(goodsParam);
        }
    }

    @Transactional
    public void deleteGoods(Long goodsId) {
        QLBuilder ql = new QLBuilder();
        ql.and(Filter.eq("goodsId", goodsId));
        ql.and(Filter.eq("isUsed", IsUsed.USED.getValue()));
        List<TGoodsOnline> list = goodsOnlineDao.findManyByJpql(ql);
        if (!CollectionUtils.isEmpty(list)) {
            throw new ServiceException("该商品已上架，不能删除");
        }
        TGoods goods = goodsDao.get(goodsId);
        goods.setIsUsed(IsUsed.NOT_USED.getValue());
        goods.setModifyTime(new Date());
        goods.setModifyUserId(UserUtil.currentAdminUser().getId());
        goodsDao.update(goods);
    }

    public TGoodsOnline findGoodsOnline(Long onlineId) {
        return goodsOnlineDao.get(onlineId);
    }

    @Transactional
    public void editGoodsType(JSONArray goodTypeList) {
        TGoodsType tGoodsType = null;
        JSONObject jsonGoodsType = null;
        JSONArray itemsList = new JSONArray();
        for (int i = 0; i < goodTypeList.size(); i++) {
            jsonGoodsType = goodTypeList.getJSONObject(i);
            Long id = jsonGoodsType.getLong("id");
            if (id <= 0) {
                throw new ServiceException("参数错误");
            }
            tGoodsType = goodsTypeDao.get(id);
            tGoodsType.setName(jsonGoodsType.getString("name"));
            tGoodsType.setImgUrl(jsonGoodsType.getString("imgUrl"));
            tGoodsType.setLinkType(jsonGoodsType.getInteger("linkType"));
            tGoodsType.setRefVal(jsonGoodsType.getString("linkUrl"));
            tGoodsType.setSort(jsonGoodsType.getInteger("sort"));
            goodsTypeDao.update(tGoodsType);
            itemsList = jsonGoodsType.getJSONArray("items");

            if (itemsList != null && !itemsList.isEmpty()) {
                for (int j = 0; j < itemsList.size(); j++) {
                    jsonGoodsType = itemsList.getJSONObject(j);
                    Long idl = jsonGoodsType.getLong("id");
                    if (idl == null) {
                        throw new ServiceException("参数错误");
                    }
                    tGoodsType = goodsTypeDao.get(idl);
                    tGoodsType.setName(jsonGoodsType.getString("name"));
                    tGoodsType.setImgUrl(jsonGoodsType.getString("imgUrl"));
                    tGoodsType.setSort(jsonGoodsType.getInteger("sort"));
                    goodsTypeDao.update(tGoodsType);
                }
            }
        }
        //缓存
        String key = RedisKey.GOODS_CLASS_KEY;
        RedisUtil.INSTANCE.delete(key);
    }

    public List<GoodsClassDto> goodsTypeDetail() {
        String hql = "select tgt.id,tgt.id classId, tgt.name,tgt.sort, tgt.img_url,  " +
                "tgt.link_type, tgt.ref_val,tgt.parent_id from t_goods_type tgt " +
                "where tgt.parent_id is not null order by tgt.sort";
        List<GoodsClassDto> goodsTypeList = goodsTypeDao.findManyBySql(hql, GoodsClassDto.class);

        String sql = "select tgt.id from t_goods_type tgt where tgt.parent_id is null ";
        TGoodsType tGoodsType = goodsTypeDao.findOneBySql(sql, TGoodsType.class);
        List<GoodsClassDto> list = new ArrayList<GoodsClassDto>();
        GoodsClassDto firstItem = null;
        GoodsClassDto secondItem = null;
        Long parentId = tGoodsType.getId();
        if (goodsTypeList == null || goodsTypeList.isEmpty() || tGoodsType == null) {
            firstItem = new GoodsClassDto();
            secondItem = new GoodsClassDto();
            firstItem.setItems(new ArrayList<GoodsClassDto>());
            firstItem.getItems().add(secondItem);
            list.add(firstItem);
            return list;
        }
        LinkedHashMap<Long, ArrayList<GoodsClassDto>> map =
                new LinkedHashMap<Long, ArrayList<GoodsClassDto>>();
        for (GoodsClassDto goodsTypeDto : goodsTypeList) {
            if (goodsTypeDto.getParentId().equals(parentId)) {
                firstItem = new GoodsClassDto();
                firstItem.setId(goodsTypeDto.getId());
                firstItem.setName(goodsTypeDto.getName());
                firstItem.setImgUrl(StringUtil.isBlank(goodsTypeDto.getImgUrl()) ? " " : goodsTypeDto.getImgUrl());
                firstItem.setRefVal(goodsTypeDto.getRefVal());
                firstItem.setSort(goodsTypeDto.getSort());
                firstItem.setLinkType(goodsTypeDto.getLinkType() == null ? 1 : goodsTypeDto.getLinkType());
                firstItem.setItems(new ArrayList<GoodsClassDto>());
                list.add(firstItem);
            } else {
                secondItem = new GoodsClassDto();
                secondItem.setId(goodsTypeDto.getId());
                secondItem.setName(goodsTypeDto.getName());
                secondItem.setImgUrl(goodsTypeDto.getImgUrl());
                secondItem.setRefVal(goodsTypeDto.getRefVal());
                secondItem.setLinkType(goodsTypeDto.getLinkType() == null ? 1 : goodsTypeDto.getLinkType());
                secondItem.setSort(goodsTypeDto.getSort());
                if (map.get(goodsTypeDto.getParentId()) == null) {
                    map.put(goodsTypeDto.getParentId(), new ArrayList<GoodsClassDto>());
                }
                map.get(goodsTypeDto.getParentId()).add(secondItem);
            }
        }
        for (GoodsClassDto goodsTypeDto : list) {
            if (!map.containsKey(goodsTypeDto.getId())) {
                map.put(goodsTypeDto.getId(), new ArrayList<GoodsClassDto>());
            }
            goodsTypeDto.getItems().addAll(map.get(goodsTypeDto.getId()));
        }
        return list;
    }

    @Override
    public void createVisit(TVisit tVisit) {
        visitDao.insert(tVisit);
    }

    @Override
    public void buildIndex() {
        logger.info("buidAll: {} start");
        //删除所有索引
        SolrjServer.deleteAllIndex();
        buildGoods();
        logger.info("buidAll: {} end");
    }

    /**
     * 新建索引
     */
    private void buildGoods() {
        List<GoodsSolrSeachDto> goodsList = goodsSolrSeach();
        List<SolrInputDocument> doclist = new ArrayList<SolrInputDocument>();
        SolrInputDocument inputdoc = null;
        for (GoodsSolrSeachDto solrSeachDto : goodsList) {
            inputdoc = new SolrInputDocument();
            inputdoc.addField("id",String.valueOf(solrSeachDto.getOnlineId()));
            inputdoc.addField("goodsName",solrSeachDto.getGoodsName());
            inputdoc.addField("keyword",solrSeachDto.getKeyword());
            inputdoc.addField("brandName",solrSeachDto.getBrandName());
            inputdoc.addField("typeName",solrSeachDto.getTypeName());
            inputdoc.addField("remark",solrSeachDto.getRemark());
            inputdoc.addField("imgUrl",solrSeachDto.getImgUrl());
            inputdoc.addField("salePrice",solrSeachDto.getSalePrice());
            inputdoc.addField("reviewNum",solrSeachDto.getReviewNum());
            inputdoc.addField("praiseRate",solrSeachDto.getPraiseRate());
            inputdoc.addField("cardType",solrSeachDto.getCardType());
            inputdoc.addField("cardVal",solrSeachDto.getCardVal()==null?null:solrSeachDto.getCardVal().doubleValue());
            inputdoc.addField("minUsePrice",solrSeachDto.getMinUsePrice());
            inputdoc.addField("goodsSellType",solrSeachDto.getGoodsSellType());
            inputdoc.addField("deposit",solrSeachDto.getDeposit());
            inputdoc.addField("presellMoney",solrSeachDto.getPresellMoney());
            inputdoc.addField("presellStartTime",solrSeachDto.getPresellStartTime());
            inputdoc.addField("presellEndTime",solrSeachDto.getPresellEndTime());
            inputdoc.addField("publishTime",solrSeachDto.getPublishTime());
            inputdoc.addField("stock",solrSeachDto.getStock());
            inputdoc.addField("saleNum",solrSeachDto.getSaleNum());
            inputdoc.addField("goodsLevel",solrSeachDto.getGoodsLevel());
            inputdoc.addField("brandId",solrSeachDto.getBrandId());
            inputdoc.addField("typeId",solrSeachDto.getTypeId());
            inputdoc.addField("fullPath",solrSeachDto.getFullPath());
            inputdoc.addField("paramVal",solrSeachDto.getParamVal()==null?null: Arrays.asList(solrSeachDto.getParamVal().split(",")));
            doclist.add(inputdoc);
        }
        SolrjServer.buildIndex(doclist);
    }

    /**
     * 查询所有上架商品
     * @return
     */
    private List<GoodsSolrSeachDto> goodsSolrSeach() {
       String sql = "select tgo.online_id, tg.full_name goodsName,tg.small_pic imgUrl,tgo.sale_price, vgt.reviewNum, vgt.praiseRate, tc.card_type cardType, \n" +
               "tc.card_val cardVal,tc.min_use_price minUsePrice,tgo.goods_sell_type,tgo.deposit,tgo.presell_money , \n" +
               "tgo.presell_start_time,tgo.presell_end_time,tgo.stock  ,tbc.id brandId,  tbc.`name` brandName ,tg.keyword, tgt.`name` typeName,\n" +
               "tgt.id typeId,tgt.full_path fullPath, tgo.remark ,vgt.saleNum , vgt.goodsLevel ,GROUP_CONCAT(tgp.param_id,'_',tgp.param_val) paramVal\n" +
               "FROM (select tgo.* from t_goods_online tgo left join t_goods tg on tg.id = tgo.spu_id order by tg.publish_time desc, tgo.sale_price, tgo.online_id desc) tgo \n" +
               "left join t_goods tg on tg.id = tgo.spu_id  \n" +
               "left join t_brand tbc on tbc.id = tg.brand_id  \n" +
               "left join t_goods_model tgm on tgm.id = tg.model_id \n" +
               "left join t_goods_type tgt on tgt.id = tg.type_id  \n" +
               "left join v_goods_total vgt on vgt.online_id = tgo.online_id  \n" +
               "left join v_goods_card  vgc on vgc.online_id = tgo.online_id  \n" +
               "left join t_card tc on tc.id = vgc.card_id and tc.status=1 and tc.start_time<now() and tc.end_time>now() \n" +
               "LEFT JOIN (SELECT tgp.* FROM t_goods_param tgp LEFT JOIN t_param tp on tgp.param_id = tp.id WHERE tp.is_search = 1 AND  tgp.param_val is NOT null AND  tgp.param_val != '' ) tgp ON tgp.goods_id = tg.id \n" +
               "where tgo.start_time < NOW() and tgo.is_used = 1  GROUP BY tgo.online_id  ";
       return goodsDao.findManyBySql(sql,GoodsSolrSeachDto.class);
    }
}
