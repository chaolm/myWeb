package com.dripop.goods.service.impl;

import com.bean.IsUsed;
import com.dripop.constant.RedisKey;
import com.dripop.core.bean.Filter;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.bean.QLBuilder;
import com.dripop.core.exception.ServiceException;
import com.dripop.core.util.BeanUtils;
import com.dripop.core.util.RedisUtil;
import com.dripop.core.util.StringUtil;
import com.dripop.dao.GoodsDao;
import com.dripop.dao.GoodsTypeDao;
import com.dripop.dao.TypeBrandDao;
import com.dripop.entity.TGoods;
import com.dripop.entity.TGoodsType;
import com.dripop.entity.TTypeBrand;
import com.dripop.goods.dto.OpTypeDto;
import com.dripop.goods.service.GoodsTypeService;
import com.dripop.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class GoodsTypeServiceImpl implements GoodsTypeService {

    @Autowired
    private GoodsTypeDao goodsTypeDao;

    @Autowired
    private TypeBrandDao typeBrandDao;

    @Autowired
    private GoodsDao goodsDao;

    @Transactional
    public void saveType(TGoodsType goodsType, String brandIds) {
        QLBuilder ql = new QLBuilder();
        ql.and(Filter.eq("name", goodsType.getName()));
        ql.and(Filter.eq("isUsed", IsUsed.USED.getValue()));
        TGoodsType tGoodsType = goodsTypeDao.findOneByJpql(ql);
        if (tGoodsType != null) {
            throw new ServiceException("已存在该类目");
        }
        goodsType.setCreateTime(new Date());
        goodsType.setCreateUserId(UserUtil.currentAdminUser().getId());
        goodsType.setModifyTime(new Date());
        goodsType.setModifyUserId(UserUtil.currentAdminUser().getId());
        goodsType.setIsUsed(IsUsed.USED.getValue());
        goodsTypeDao.insert(goodsType);
        TGoodsType parent = goodsTypeDao.get(goodsType.getParentId());
        goodsType.setFullPath(parent.getFullPath() + goodsType.getId() + "/");
        goodsTypeDao.update(goodsType);
        saveTypeBrandRelation(brandIds, goodsType.getId());
    }

    @Transactional
    public void updateType(TGoodsType goodsType, String brandIds) {
        TGoodsType t = goodsTypeDao.get(goodsType.getId());
        BeanUtils.copyProperties(goodsType, t);
        t.setModifyTime(new Date());
        t.setModifyUserId(UserUtil.currentAdminUser().getId());
        goodsTypeDao.update(t);

        TGoodsType newParent = goodsTypeDao.get(goodsType.getParentId());

        String sql = "update t_goods_type t set t.full_path = REPLACE(t.full_path, :formStr, :toStr) where t.full_path like :likeFormStr";
        goodsTypeDao.executeBySql(sql, t.getFullPath(), newParent.getFullPath() + t.getId() + "/", "%" + t.getFullPath() + "%");

        QLBuilder ql = new QLBuilder();
        ql.and(Filter.eq("typeId", goodsType.getId()));
        typeBrandDao.deleteByJpql(ql);
        saveTypeBrandRelation(brandIds, goodsType.getId());
    }

    public TGoodsType findById(Long id) {
        return goodsTypeDao.get(id);
    }

    @Transactional
    public void deleteType(Long id) {
        QLBuilder ql = new QLBuilder();
        ql.and(Filter.eq("typeId", id));
        ql.and(Filter.eq("isUsed", IsUsed.USED.getValue()));
        List<TGoods> list = goodsDao.findManyByJpql(ql);
        if (!CollectionUtils.isEmpty(list)) {
            throw new ServiceException("该类目下存在商品，不能删除");
        }

        goodsTypeDao.delete(id);
        ql = new QLBuilder();
        ql.and(Filter.eq("typeId", id));
        typeBrandDao.deleteByJpql(ql);
        RedisUtil.INSTANCE.delete(RedisKey.GOODS_CLASS_KEY);
    }

    public Pagination<OpTypeDto> pageType(String name, Pageable pageable) {
        StringBuffer sb = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sb.append("select tgt.id, tgt2.id parentId, tgt2.name parentName, tgt.is_practice, tgt.name, tgt.price_range, " +
                "GROUP_CONCAT(tb.name order by ttb.sort) brandNames, GROUP_CONCAT(tb.id order by ttb.sort) brandIds, tgt.remark FROM t_goods_type tgt \n" +
                "left join t_goods_type tgt2 on tgt.parent_id = tgt2.id \n" +
                "left join t_type_brand ttb on tgt.id = ttb.type_id \n" +
                "left join t_brand tb on tb.id = ttb.brand_id where 1=1 ");
        if (StringUtil.isNotBlank(name)) {
            sb.append("and tgt.name like :name ");
            params.add("%" + name + "%");
        }
        sb.append("group by tgt.id ");
        return goodsTypeDao.findPageBySql(sb.toString(), pageable, OpTypeDto.class, params.toArray());
    }

    public List<TGoodsType> listType(Long parentId) {
        QLBuilder ql = new QLBuilder();
        if (parentId == null) {
            ql.and(Filter.isNull("parentId"));
            TGoodsType goodsType = goodsTypeDao.findOneByJpql(ql);
            ql = new QLBuilder();
            ql.and(Filter.eq("parentId", goodsType.getId()));
        } else {
            ql.and(Filter.eq("parentId", parentId));
        }
        return goodsTypeDao.findManyByJpql(ql);
    }

    public List<TGoodsType> listTypeSort(Integer isFull) {

        String sql = "select tgt.id typeId, tgt.id,tgt.name,tgt.img_url imgUrl,tgt.parent_id parentId,tgt.full_path fullPath from t_goods_type tgt where not exists (select a.id from t_goods_type a where a.parent_id = tgt.id) and tgt.is_used=1 order by tgt.full_path";
        String rootSql = "select tgt.id typeId, tgt.id,tgt.name,tgt.img_url imgUrl,tgt.parent_id parentId,tgt.full_path fullPath from t_goods_type tgt where tgt.is_used=1 order by tgt.full_path";

        List<TGoodsType> rootList = goodsTypeDao.findManyBySql(rootSql, TGoodsType.class);
        List<TGoodsType> fullList = new ArrayList<TGoodsType>();

        Map<Long, TGoodsType> goodsTypeMap = new HashMap<Long, TGoodsType>();
        StringBuffer sb = null;
        Long parentId = null;
        for (TGoodsType goodsType : rootList) {
            parentId = goodsType.getParentId();
            if (parentId != null && goodsTypeMap.containsKey(parentId)) {
                sb = new StringBuffer();
                if (goodsTypeMap.get(parentId).getName() != null) {
                    sb.append(goodsTypeMap.get(parentId).getName()).append("--");
                }
                sb.append(goodsType.getName());
                goodsType.setName(sb.toString());
                fullList.add(goodsType);
            } else if (parentId == null) {
                goodsType.setName(null);
            }
            goodsTypeMap.put(goodsType.getId(), goodsType);
        }
        if (isFull != null && isFull == 1) {
            QLBuilder ql = new QLBuilder();
            ql.and(Filter.isNull("parentId"));
            TGoodsType goodsType = goodsTypeDao.findOneByJpql(ql);
            goodsType.setName(goodsType.getName() + "--顶级节点");
            fullList.add(0, goodsType);
            return fullList;
        }
        List<TGoodsType> list = goodsTypeDao.findManyBySql(sql, TGoodsType.class);
        for (TGoodsType goodsType : list) {
            goodsType.setName(goodsTypeMap.get(goodsType.getId()).getName());
        }

        return list;
    }

    /**
     * 保存分类和品牌关联关系
     *
     * @param brandIds
     * @param typeId
     */
    private void saveTypeBrandRelation(String brandIds, Long typeId) {
        if (StringUtil.isBlank(brandIds)) {
            return;
        }
        TTypeBrand typeBrand = null;
        int num = 0;
        for (String brandId : brandIds.split(",")) {
            num++;
            typeBrand = new TTypeBrand();
            typeBrand.setTypeId(typeId);
            typeBrand.setBrandId(Long.parseLong(brandId));
            typeBrand.setCreateTime(new Date());
            typeBrand.setSort(num);
            typeBrandDao.insert(typeBrand);
        }
    }
}
