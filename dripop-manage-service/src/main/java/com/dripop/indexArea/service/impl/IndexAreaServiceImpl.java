package com.dripop.indexArea.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bean.IndexAreaType;
import com.bean.LinkType;
import com.bean.LogicDelete;
import com.dripop.constant.RedisKey;
import com.dripop.constant.StaticCodeConstant;
import com.dripop.core.bean.Filter;
import com.dripop.core.bean.QLBuilder;
import com.dripop.core.exception.ServiceException;
import com.dripop.core.util.BeanUtils;
import com.dripop.core.util.JsonUtil;
import com.dripop.core.util.RedisUtil;
import com.dripop.core.util.StringUtil;
import com.dripop.dao.GoodsDao;
import com.dripop.dao.IndexAreaDao;
import com.dripop.dao.StaticDataDao;
import com.dripop.entity.TIndexArea;
import com.dripop.entity.TStaticData;
import com.dripop.indexArea.dto.*;
import com.dripop.indexArea.service.IndexAreaService;
import com.dripop.service.CommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 首页 专区 service实现类
 *
 * @author dq
 * @date 2018/3/12 14:13
 */
@Service
public class IndexAreaServiceImpl implements IndexAreaService {

    static final Logger logger = LoggerFactory.getLogger(IndexAreaServiceImpl.class);
    static final Integer expireTime = 2 * 60 * 60;//两小时

    private final int totalApp = 3;//APP首页区块数量
    private final int totalPc = 9;//pc首页区块数量

    @Autowired
    private IndexAreaDao indexAreaDao;
    @Autowired
    private StaticDataDao staticDataDao;
    @Autowired
    private CommonService commonService;
    @Autowired
    private GoodsDao goodsDao;

    @Transactional
    public void saveStaticDate(TStaticData tStaticData) {
        TStaticData staticData = commonService.getDataByCode(StaticCodeConstant.INDEX_STARTUP_AD_IMG);
        if (staticData == null) {
            //新增
            tStaticData.setCode(StaticCodeConstant.INDEX_STARTUP_AD_IMG);
            tStaticData.setCreateTime(new Date());
            tStaticData.setLogicDelete(LogicDelete.NOT_DELETE.getValue());
            staticDataDao.insert(tStaticData);
        } else {
            //编辑
            staticData.setExtendParams(tStaticData.getExtendParams());
            staticDataDao.update(staticData);
            //缓存
            String key = RedisKey.STATIC_DATE_IMG_KEY + StaticCodeConstant.INDEX_STARTUP_AD_IMG;
            RedisUtil.INSTANCE.delete(key);
        }

    }

    @Transactional
    public void saveBackPicture(String imgUrl) {
        TStaticData staticData = commonService.getDataByCode(StaticCodeConstant.LOGIN_BACKGROUND_IMG);

        if (staticData == null) {
            staticData = new TStaticData();
            //新增
            staticData.setCode(StaticCodeConstant.LOGIN_BACKGROUND_IMG);
            staticData.setVal(imgUrl);
            staticData.setName("PC商城登陆背景图");
            staticData.setCreateTime(new Date());
            staticData.setSort(1);
            staticData.setLogicDelete(LogicDelete.NOT_DELETE.getValue());
            staticDataDao.insert(staticData);
        } else {
            //编辑
            staticData.setVal(imgUrl);
            staticData.setCreateTime(new Date());
            staticDataDao.update(staticData);
            //缓存
            String key = RedisKey.STATIC_DATE_IMG_KEY + StaticCodeConstant.INDEX_STARTUP_AD_IMG;
            RedisUtil.INSTANCE.delete(key);
        }

    }

    @Override
    public String getLoginBackPic() {
        TStaticData staticData = commonService.getDataByCode(StaticCodeConstant.LOGIN_BACKGROUND_IMG);
        return staticData.getVal();
    }

    @Transactional
    public void deleteStaticDate(Integer id) {
        TStaticData tStaticData = staticDataDao.get(id);
        tStaticData.setLogicDelete(LogicDelete.DELETE.getValue());
        staticDataDao.update(tStaticData);
        //清除缓存
        String key = RedisKey.STATIC_DATE_IMG_KEY + tStaticData.getId();
        RedisUtil.INSTANCE.delete(key);
    }

    @Transactional
    public void saveArea(IndexAreaDetailDto indexAreaDetailDto) {
        if (indexAreaDetailDto.getId() == null) {
            //新增
            TIndexArea fatherArea = checkAndSaveParent(indexAreaDetailDto);
            //子模块添加
            save(indexAreaDetailDto, fatherArea);
        } else {
            //编辑
            //删除子模块信息，重新保存
            if (indexAreaDetailDto.getEditType() == null) {
                deleteSonArea(indexAreaDetailDto.getId());
            }
            //重新关联保存

            TIndexArea fatherArea = indexAreaDao.get(indexAreaDetailDto.getId());
            if (indexAreaDetailDto.getAreaType().equals(2)) {
                if (StringUtil.isBlank(indexAreaDetailDto.getAreaName())) {
                    throw new ServiceException("专区名不能为空");
                }
                fatherArea.setSort(indexAreaDetailDto.getSort());
                fatherArea.setName(indexAreaDetailDto.getAreaName());
                indexAreaDao.update(fatherArea);
            }
            //子模块添加
            if (indexAreaDetailDto.getEditType() == null) {
                save(indexAreaDetailDto, fatherArea);
            }
        }
    }

    @Transactional
    public void deleteArea(Long id) {
        deleteSonArea(id);
        indexAreaDao.delete(id);
        String key = RedisKey.INDEX_KEY + id;
        RedisUtil.INSTANCE.delete(key);

    }

    public StaticDataDetail staticDateDetail() {
        StaticDataDetail staticDataDetail = new StaticDataDetail();
        TStaticData staticData = commonService.getDataByCode(StaticCodeConstant.INDEX_STARTUP_AD_IMG);
        if (staticData == null) {
            staticDataDetail.setLinkList(new ArrayList<IndexAreaFullDto>());
            return staticDataDetail;
        }
        IndexAreaFullDto linkDto = null;
        staticDataDetail.setLinkList(new ArrayList<IndexAreaFullDto>());
        String extendParams = staticData.getExtendParams();
        if (StringUtil.isNotBlank(extendParams)) {
            JSONArray jsonArray = JSONArray.parseArray(extendParams);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                linkDto = JsonUtil.fromJson(jsonObject, IndexAreaFullDto.class);
                if (linkDto.getLinkType().equals(LinkType.GOODS.getValue())) {
                    String hql = "select tg.full_name goodsName  from t_goods_online tgo " +
                            "left join t_goods tg on tg.id = tgo.spu_id where tgo.online_id = :id";
                    IndexAreaFullDto index = indexAreaDao.findOneBySql(hql, IndexAreaFullDto.class, linkDto.getRefVal());
                    if (index != null) {
                        linkDto.setGoodsName(index.getGoodsName());
                    }
                }
                staticDataDetail.getLinkList().add(linkDto);
            }
        }
        return staticDataDetail;
    }

    public List<IndexAreaDto> areaList(Integer dataType) {
        StringBuffer sb = new StringBuffer();
        sb.append("select tia.id areaId, tia.name,tia.sort,tia.content,tia.img_url imgUrl from t_index_area tia where tia.type = :type ");
        if(dataType == null){
            sb.append(" and tia.data_type is null ");
        }
        if(dataType != null && dataType == 1 ){
            sb.append(" and tia.data_type = 1 ");
        }
        sb.append(" order by tia.sort ");
        return indexAreaDao.findManyBySql(sb.toString(), IndexAreaDto.class, IndexAreaType.AREA.getValue());
    }

    @SuppressWarnings("unchecked")
    public IndexAreaDetailDto areaDetail(Long areaId,Integer dataType) {
        int type = 1;//专区 首页区分字段 1 专区 2 首页
        IndexAreaFullDto index = null;
        if (areaId == null) {
            if(dataType == null){
                String hql = "select tia.id  from t_index_area tia where type = :type and data_type is null";
                 index = indexAreaDao.findOneBySql(hql, IndexAreaFullDto.class, IndexAreaType.INDEX.getValue());
            }
            if(dataType != null && dataType == 1){
                String hql = "select tia.id  from t_index_area tia where type = :type and data_type = :dataType ";
                index = indexAreaDao.findOneBySql(hql, IndexAreaFullDto.class, IndexAreaType.INDEX.getValue(),dataType);
            }

            if (index == null) {
                /*IndexAreaDetailDto indexAreaDetailDto = new IndexAreaDetailDto();
                if(dataType == null){
                    indexCompiete(totalApp, 0, indexAreaDetailDto);
                }else if(dataType != null && dataType == 1){
                    indexCompiete(totalPc, 0, indexAreaDetailDto);
                }*/
                return null;
            }
            areaId = index.getId();
            type = 2;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("select tia.*, tg.full_name goodsName, tgo.sale_price,tc.card_type cardType," +
                "tc.card_val cardVal,tc.min_use_price minUsePrice,tgo.online_id onlineId FROM t_index_area tia " +
                "left join t_goods_online tgo on tia.ref_val = tgo.online_id and tia.link_type = :linkType " +
                "left join t_goods tg on tg.id = tgo.spu_id " +
                "left join v_goods_card  vgc on vgc.online_id = tgo.online_id " +
                "left join t_card tc on tc.id = vgc.card_id and tc.status=1 and tc.start_time<now() and tc.end_time>now()" +
                "where tia.root_id = :rootId order by tia.type, tia.sort");
        List<IndexAreaFullDto> list = indexAreaDao.findManyBySql(sb.toString(), IndexAreaFullDto.class, LinkType.GOODS.getValue(), areaId);
        String sql = null;
        for(IndexAreaFullDto indexAreaFullDto:list){
            if(indexAreaFullDto.getLinkType() != null && indexAreaFullDto.getLinkType() == 2 && indexAreaFullDto.getImgUrl() == null){
                sql = "select tg.small_pic from t_goods_online tgo LEFT JOIN t_goods tg on tg.id = tgo.spu_id  where tgo.online_id = :onlineId";
                indexAreaFullDto.setImgUrl(goodsDao.findOneBySql(sql.toString(),String.class,indexAreaFullDto.getOnlineId()));

            }
        }
        IndexAreaDetailDto detailDto = new IndexAreaDetailDto();
        detailDto.setBannerList(new ArrayList<IndexAreaFullDto>());
        detailDto.setBlockList(new ArrayList<BlockDto>());
        BlockDto blockDto = null;
        IndexAreaFullDto linkDto = null;
        Map<Long, BlockDto> blockMap = new LinkedHashMap<Long, BlockDto>();
        int num = 0;//区块计数器
        if (list != null && !list.isEmpty()) {
            for (IndexAreaFullDto indexArea : list) {
                if (indexArea.getType().equals(IndexAreaType.BANNER.getValue())) {
                    linkDto = new IndexAreaFullDto();
                    BeanUtils.copyProperties(indexArea, linkDto);
                    linkDto.setImgUrl(indexArea.getImgUrl());
                    linkDto.setLinkType(indexArea.getLinkType());
                    detailDto.getBannerList().add(linkDto);
                } else if (indexArea.getType().equals(IndexAreaType.BLOCK.getValue())) {
                    blockDto = new BlockDto();
                    BeanUtils.copyProperties(indexArea, blockDto);
                    if (type == 1) {
                        blockDto.setKeyword(StringUtil.isBlank(blockDto.getKeyword()) ? " " : blockDto.getKeyword());
                    }
                    blockDto.setGoodsList(new ArrayList<IndexAreaFullDto>());
                    blockDto.setChannelList(new ArrayList<IndexAreaFullDto>());
                    blockMap.put(indexArea.getId(), blockDto);
                    num++;
                } else if (indexArea.getType().equals(IndexAreaType.GOODS.getValue())) {
                    linkDto = new IndexAreaFullDto();
                    BeanUtils.copyProperties(indexArea, linkDto);
                    linkDto.setImgUrl(indexArea.getImgUrl());
                    blockMap.get(indexArea.getParentId()).getGoodsList().add(linkDto);
                } else if (indexArea.getType().equals(IndexAreaType.CHANNEL.getValue())) {
                    linkDto = new IndexAreaFullDto();
                    BeanUtils.copyProperties(indexArea, linkDto);
                    linkDto.setImgUrl(indexArea.getImgUrl());
                    blockMap.get(indexArea.getParentId()).getChannelList().add(linkDto);
                }
            }
            for (Long parentId : blockMap.keySet()) {
                detailDto.getBlockList().add(blockMap.get(parentId));
            }
        }
        TIndexArea tIndexArea = indexAreaDao.get(areaId);
        detailDto.setId(tIndexArea.getId());
        if (tIndexArea.getType().equals(IndexAreaType.AREA.getValue())) {
            detailDto.setAreaName(tIndexArea.getName());
            detailDto.setSort(tIndexArea.getSort());

        }
        if (type == 2) {
            int remainder = 0;
            //首页详情，保证数据结构完整
            if(dataType == null){
                 remainder = totalApp - num;//需要补充区块的数量
            }else if(dataType != null && dataType == 1){
                remainder = totalPc - num;//需要补充区块的数量
            }

            indexCompiete(remainder, num, detailDto);
        }
        return detailDto;
    }

    /**
     * 首页详情，保证数据结构完整
     *
     * @param remainder
     * @param num
     * @param detailDto
     * @return
     */
    private IndexAreaDetailDto indexCompiete(int remainder, int num, IndexAreaDetailDto detailDto) {
        BlockDto bd = null;
        for (int i = 0; i < remainder; i++) {
            bd = new BlockDto();
            bd.setSort(++num);
            bd.setName("默认区块名");
            bd.setChannelList(new ArrayList<IndexAreaFullDto>());
            bd.setGoodsList(new ArrayList<IndexAreaFullDto>());
            IndexAreaFullDto indexAreaFull = null;
            if(remainder == 3){
                if (bd.getSort().equals(1) || bd.getSort().equals(2)) {
                    for (int j = 0; j < 5; j++) {
                        indexAreaFull = new IndexAreaFullDto();
                        indexAreaFull.setSort(j + 1);
                        bd.getGoodsList().add(indexAreaFull);
                    }
                } else if (bd.getSort().equals(3)) {
                    for (int j = 0; j < 8; j++) {
                        indexAreaFull = new IndexAreaFullDto();
                        indexAreaFull.setSort(j + 1);
                        bd.getGoodsList().add(indexAreaFull);
                    }
                }
            }else if(remainder == 9){
                if (bd.getSort().equals(1) || bd.getSort().equals(2)||bd.getSort().equals(3)) {
                    for (int j = 0; j < 5; j++) {
                        indexAreaFull = new IndexAreaFullDto();
                        indexAreaFull.setSort(j + 1);
                        bd.getGoodsList().add(indexAreaFull);
                    }
                } else if (bd.getSort().equals(4)||bd.getSort().equals(5)||bd.getSort().equals(6)||bd.getSort().equals(7)||bd.getSort().equals(8)||bd.getSort().equals(9)) {
                    for (int j = 0; j < 8; j++) {
                        indexAreaFull = new IndexAreaFullDto();
                        indexAreaFull.setSort(j + 1);
                        bd.getGoodsList().add(indexAreaFull);
                    }
                }
            }

            detailDto.getBlockList().add(bd);
        }
        return detailDto;
    }

    @Override
    @Transactional
    public void areaSort(Long id, Integer sort) {
        TIndexArea tIndexArea = new TIndexArea();
        tIndexArea.setId(id);
        tIndexArea.setSort(sort);
        indexAreaDao.update(tIndexArea);
    }


    /**
     * 根据根节点ID删除
     *
     * @param id
     */
    private void deleteSonArea(Long id) {
        QLBuilder ql = new QLBuilder();
        ql.and(Filter.eq("parent_id", id));
        List<TIndexArea> secondAreaList = indexAreaDao.findManyByJpql(ql);
        List<TIndexArea> list = new ArrayList<TIndexArea>();
        List<TIndexArea> thirdAreaList = null;
        list.addAll(secondAreaList);
        if (secondAreaList != null && !secondAreaList.isEmpty()) {
            for (TIndexArea secondArea : secondAreaList) {
                ql = new QLBuilder();
                ql.and(Filter.eq("parent_id", secondArea.getId()));
                thirdAreaList = indexAreaDao.findManyByJpql(ql);
                list.addAll(thirdAreaList);
            }
        }
        if (list != null && !list.isEmpty()) {
            for (TIndexArea area : list) {
                indexAreaDao.delete(area.getId());
            }
        }
    }

    /**
     * 整体保存
     *
     * @param indexAreaDetailDto
     * @param fatherArea
     */
    private void save(IndexAreaDetailDto indexAreaDetailDto, TIndexArea fatherArea) {
        //banner子模块添加
        List<IndexAreaFullDto> bannerList = indexAreaDetailDto.getBannerList();
        if (bannerList != null && !bannerList.isEmpty()) {
            if (bannerList.size() > 5) {
                throw new ServiceException("Banner最多支持上传5张广告图");
            }
            TIndexArea bannerArea = null;//banner模块
            IndexAreaFullDto linkDto = null;
            int bannerSort = 0;
            for (int i = 0; i < bannerList.size(); i++) {
                bannerArea = new TIndexArea();
                linkDto = bannerList.get(i);
                //保存banner子模块
                bannerSort++;
                saveBannerArea(linkDto, bannerArea, fatherArea, bannerSort);
            }
        }
        //其他子模块新增
        List<BlockDto> blockList = indexAreaDetailDto.getBlockList();
        if (blockList != null && !blockList.isEmpty()) {
            if (indexAreaDetailDto.getDataType() == null && indexAreaDetailDto.getAreaType().equals(1) && blockList.size() > 3) {
                throw new ServiceException("首页模块超过上线");
            }
            if (indexAreaDetailDto.getDataType() != null && indexAreaDetailDto.getDataType() == 1 && indexAreaDetailDto.getAreaType().equals(1) && blockList.size() > 9) {
                throw new ServiceException("首页模块超过上线");
            }

            TIndexArea sonArea = null; //二级子模块
            TIndexArea channelArea = null;//通栏模块
            int sonAreaSort = 0;
            for (BlockDto block : blockList) {
                sonArea = new TIndexArea();
                //保存二级子区块
                sonAreaSort++;
                saveSonArea(block, sonArea, fatherArea, sonAreaSort);

                channelArea = new TIndexArea();
                //保存通栏
                saveChannelArea(block, sonArea, fatherArea, channelArea);

                //保存商品
                List<IndexAreaFullDto> goodsList = block.getGoodsList();
                saveGoods(goodsList, sonArea, fatherArea, indexAreaDetailDto.getAreaType(),indexAreaDetailDto);
            }
        }
        //缓存
        String key = RedisKey.INDEX_KEY + fatherArea.getId();
        RedisUtil.INSTANCE.delete(key);
    }

    /**
     * 保存banner子模块
     *
     * @param linkDto
     * @param bannerArea
     * @param fatherArea
     * @param bannerSort
     */
    private void saveBannerArea(IndexAreaFullDto linkDto, TIndexArea bannerArea, TIndexArea fatherArea, int bannerSort) {
        if (StringUtil.isBlank(linkDto.getImgUrl())) {
            throw new ServiceException("背景图不能为空");
        }
        if (linkDto.getLinkType() == null) {
            throw new ServiceException("跳转类型不能为空");
        }
        bannerArea.setRefVal(linkDto.getRefVal());
        bannerArea.setImgUrl(linkDto.getImgUrl());
        bannerArea.setLinkType(linkDto.getLinkType());
        bannerArea.setSort(bannerSort);
        bannerArea.setType(IndexAreaType.BANNER.getValue());
        bannerArea.setCreateTime(new Date());
        bannerArea.setParentId(fatherArea.getId());
        bannerArea.setRootId(fatherArea.getId());
        indexAreaDao.insert(bannerArea);
    }

    /**
     * 保存商品栏
     *
     * @param goodsList
     * @param sonArea
     * @param fatherArea
     * @param areaType
     */
    private void saveGoods(List<IndexAreaFullDto> goodsList, TIndexArea sonArea, TIndexArea fatherArea, Integer areaType,IndexAreaDetailDto indexAreaDetailDto) {
        //校验商品位数量
        checkNum(areaType, goodsList, sonArea,indexAreaDetailDto);
        TIndexArea goodsArea = null;
        int goodsSort = 0;
        for (IndexAreaFullDto goods : goodsList) {
           /* if (StringUtil.isBlank(goods.getImgUrl())) {
                throw new ServiceException("商品位背景图不能为空");
            }*/
            if (goods.getLinkType() == null) {
                throw new ServiceException("跳转类型不能为空");
            }
            if (goods.getLinkType().equals(LinkType.GOODS.getValue())) {
                if (StringUtil.isBlank(goods.getName())) {
                    throw new ServiceException("商品展示名称不能为空");
                }
            }
            goodsSort++;
            goodsArea = new TIndexArea();
            goodsArea.setName(goods.getName());
            goodsArea.setSort(goodsSort);
            goodsArea.setLinkType(goods.getLinkType());
            goodsArea.setRefVal(goods.getRefVal());
            goodsArea.setImgUrl(goods.getImgUrl() == null ? null : goods.getImgUrl());
            goodsArea.setType(IndexAreaType.GOODS.getValue());
            goodsArea.setCreateTime(new Date());
            goodsArea.setParentId(sonArea.getId());
            goodsArea.setRootId(fatherArea.getId());
            goodsArea.setHide(goods.getHide());
            if (goodsArea.getHide() == null) {
                goodsArea.setHide(0);
            }
            indexAreaDao.insert(goodsArea);
        }
    }

    /**
     * 校验商品位数量
     *
     * @param areaType
     * @param goodsList
     * @param sonArea
     */
    private void checkNum(Integer areaType, List<IndexAreaFullDto> goodsList, TIndexArea sonArea,IndexAreaDetailDto indexAreaDetailDto) {
        if(indexAreaDetailDto.getDataType() == null ){
            if (areaType.equals(1) && (sonArea.getSort().equals(1) || sonArea.getSort().equals(2))
                    && goodsList.size() != 5) {
                throw new ServiceException("首页1、2排序位需配置5个商品位");
            }
            if (areaType.equals(1) && sonArea.getSort().equals(3) && goodsList.size() != 8) {
                throw new ServiceException("首页3排序位需配置8个商品位");
            }
        }else if(indexAreaDetailDto.getDataType() == 1){
            if (areaType.equals(1) && (sonArea.getSort().equals(1) || sonArea.getSort().equals(2)||sonArea.getSort().equals(3))
                    && goodsList.size() != 5) {
                throw new ServiceException("首页1、2排序位需配置5个商品位");
            }
            if (areaType.equals(1) && (sonArea.getSort().equals(4)||sonArea.getSort().equals(5)||sonArea.getSort().equals(6)||sonArea.getSort().equals(7)||sonArea.getSort().equals(8)||sonArea.getSort().equals(9)) && goodsList.size() != 8) {
                throw new ServiceException("首页3排序位需配置8个商品位");
            }
        }
        if (areaType.equals(2) && goodsList.size() % 2 != 1) {
            throw new ServiceException("专区商品位数量需为奇数");
        }
    }


    /**
     * 保存通栏
     *
     * @param block
     * @param sonArea
     * @param fatherArea
     * @param channelArea
     */
    private void saveChannelArea(BlockDto block, TIndexArea sonArea, TIndexArea fatherArea, TIndexArea channelArea) {
        List<IndexAreaFullDto> channelList = block.getChannelList();
        if (channelList != null && !channelList.isEmpty()) {

            String link = channelList.get(0).getRefVal();
            String imgUrl = channelList.get(0).getImgUrl();
            Integer linkType = channelList.get(0).getLinkType();
            if (StringUtil.isBlank(imgUrl)) {
                throw new ServiceException("banner广告图不能为空");
            }
            if (linkType == null) {
                throw new ServiceException("跳转类型不能为空");
            }
            channelArea.setRefVal(link);
            channelArea.setImgUrl(imgUrl);
            channelArea.setLinkType(linkType);
            channelArea.setType(IndexAreaType.CHANNEL.getValue());
            channelArea.setCreateTime(new Date());
            channelArea.setParentId(sonArea.getId());
            channelArea.setRootId(fatherArea.getId());
            indexAreaDao.insert(channelArea);
        }

    }

    /**
     * 保存二级子区块
     *
     * @param block
     * @param sonArea
     * @param fatherArea
     * @param sonAreaSort
     */
    private void saveSonArea(BlockDto block, TIndexArea sonArea, TIndexArea fatherArea, int sonAreaSort) {
        if (StringUtil.isBlank(block.getName())) {
            throw new ServiceException("模块名称不能为空");
        }
        sonArea.setName(block.getName());
        sonArea.setJumpCategory(block.getJumpCategory());
        sonArea.setHotSearch(block.getHotSearch());
        sonArea.setKeyword(block.getKeyword());
        sonArea.setEnglishName(block.getEnglishName());
        sonArea.setSort(sonAreaSort);
        sonArea.setType(IndexAreaType.BLOCK.getValue());
        sonArea.setCreateTime(new Date());
        sonArea.setParentId(fatherArea.getId());
        sonArea.setRootId(fatherArea.getId());
        indexAreaDao.insert(sonArea);
    }

    /**
     * 首页新增重复校验及根节点模版save
     *
     * @param indexAreaDetailDto
     * @return
     */
    private TIndexArea checkAndSaveParent(IndexAreaDetailDto indexAreaDetailDto) {
        TIndexArea fatherArea = null;
        if (indexAreaDetailDto.getAreaType().equals(1)) {
            QLBuilder sql = null;
            if(indexAreaDetailDto.getDataType() == null){
                String string = "select * from t_index_area tia  where tia.type = :typeType and tia.data_type is null";
                fatherArea = indexAreaDao.findOneBySql(string,TIndexArea.class,IndexAreaType.INDEX.getValue());
            }else if(indexAreaDetailDto.getDataType() == 1){
                sql = new QLBuilder();
                sql.and(Filter.eq("type", IndexAreaType.INDEX.getValue()));
                sql.and(Filter.eq("data_type", 1));
                fatherArea = indexAreaDao.findOneByJpql(sql);
            }

            //首页新增根节点模版
            if (fatherArea == null) {
                fatherArea = new TIndexArea();
                fatherArea.setName("首页");
                fatherArea.setType(IndexAreaType.INDEX.getValue());
                fatherArea.setCreateTime(new Date());
                fatherArea.setDataType(indexAreaDetailDto.getDataType());
                indexAreaDao.insert(fatherArea);

            } else {
                //删除子模块信息，重新保存
                deleteSonArea(fatherArea.getId());
            }

        } else {
            if (StringUtil.isBlank(indexAreaDetailDto.getAreaName())) {
                throw new ServiceException("专区名不能为空");
            }
            if (indexAreaDetailDto.getSort() == null) {
                throw new ServiceException("专区排名不能为空");
            }
            //专区新增根节点模版
            fatherArea = new TIndexArea();
            fatherArea.setName(indexAreaDetailDto.getAreaName());
            fatherArea.setEnglishName(indexAreaDetailDto.getEnglishName());
            fatherArea.setSort(indexAreaDetailDto.getSort());
            fatherArea.setType(IndexAreaType.AREA.getValue());
            fatherArea.setCreateTime(new Date());
            fatherArea.setDataType(indexAreaDetailDto.getDataType());
            if(indexAreaDetailDto.getDataType() != null && indexAreaDetailDto.getDataType() == 1){
                fatherArea.setImgUrl(indexAreaDetailDto.getImgUrl());
                fatherArea.setContent(indexAreaDetailDto.getContent());
            }
            indexAreaDao.insert(fatherArea);
        }
        return fatherArea;
    }

}
