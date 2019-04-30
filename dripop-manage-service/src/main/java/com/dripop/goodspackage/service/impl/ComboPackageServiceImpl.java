package com.dripop.goodspackage.service.impl;

import com.bean.IsUsed;
import com.dripop.core.bean.Filter;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.bean.QLBuilder;
import com.dripop.core.exception.ServiceException;
import com.dripop.core.util.StringUtil;
import com.dripop.dao.ComboPackageDao;
import com.dripop.dao.ComboPackageRelDao;
import com.dripop.dao.GoodsOnlineDao;
import com.dripop.entity.TComboPackage;
import com.dripop.entity.TComboPackageRel;
import com.dripop.entity.TGoodsOnline;
import com.dripop.goodspackage.dto.*;
import com.dripop.goodspackage.service.ComboPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by liyou on 2017/8/4.
 */

@Service
public class ComboPackageServiceImpl implements ComboPackageService {
	
	@Autowired
    private ComboPackageDao comboPackageDao;

    @Autowired
    private ComboPackageRelDao comboPackageRelDao;

	@Autowired
	private GoodsOnlineDao goodsOnlineDao;

	@Transactional
	public void setting(OpComboPackSettingDto comboPackSettingDto) {
		String[] mainOnlineIds = comboPackSettingDto.getOnlineIds().split(",");
		TComboPackage comboPackage = null;
		TComboPackageRel comboPackageRel = null;
		TGoodsOnline goodsOnline = null;
		int i = 0;
		Integer totalMoney = 0;
		String sql = null;
		for (String mainOnlineId : mainOnlineIds) {
			if(StringUtil.isBlank(mainOnlineId)) {
				continue;
			}
			i++;
			if(comboPackSettingDto.getType() != null && comboPackSettingDto.getType().equals(OpComboPackSettingDto.TYPE_CONSTANT.COVER)) {
				sql = "update t_combo_package set is_used = :isUsed where online_id = :onlineId";
				comboPackageDao.executeBySql(sql, IsUsed.NOT_USED.getValue(), mainOnlineId);
			}
			for (OpComboPackSettingDetailDto detailDto : comboPackSettingDto.getDetailList()) {
				int j = 0;
				totalMoney = 0;
				comboPackage = new TComboPackage();
				comboPackage.setName(detailDto.getPackageName());
				comboPackage.setDiscountMoney(detailDto.getDiscountMoney().multiply(new BigDecimal(100)).intValue());
				comboPackage.setStock(detailDto.getStock());
				comboPackage.setRemainingStock(detailDto.getStock());
				comboPackage.setOnlineId(Long.parseLong(mainOnlineId));
				comboPackage.setSort(i);
				comboPackage.setCreator(comboPackSettingDto.getUid());
				comboPackage.setCreateTime(new Date());
				comboPackage.setIsUsed(IsUsed.USED.getValue());
				comboPackageDao.insert(comboPackage);

				comboPackageRel = new TComboPackageRel();
				comboPackageRel.setPackageId(comboPackage.getId());
				comboPackageRel.setOnlineId(Long.parseLong(mainOnlineId));
				comboPackageRel.setSort(0);
				comboPackageRel.setType(TComboPackageRel.TYPE_CONSTANT.MAIN_GOODS);
				comboPackageRelDao.insert(comboPackageRel);
				goodsOnline = goodsOnlineDao.get(comboPackageRel.getOnlineId());
				totalMoney += goodsOnline.getSalePrice();

				String[] onlineIds = detailDto.getOnlineIds().split(",");
				for (String onlineId : onlineIds) {
					j++;
					comboPackageRel = new TComboPackageRel();
					comboPackageRel.setPackageId(comboPackage.getId());
					comboPackageRel.setOnlineId(Long.parseLong(onlineId));
					comboPackageRel.setSort(j);
					comboPackageRel.setType(TComboPackageRel.TYPE_CONSTANT.NOT_MAIN_GOODS);
					comboPackageRelDao.insert(comboPackageRel);
					goodsOnline = goodsOnlineDao.get(comboPackageRel.getOnlineId());
					totalMoney += goodsOnline.getSalePrice();
				}
				if(totalMoney < comboPackage.getDiscountMoney()) {
					throw new ServiceException("优惠金额不能大于商品总金额");
				}
			}
		}
	}

	public Pagination<OpComboGoodsDto> opComboPackageGoodsPage(Pageable pageable, OpComboPackageGoodsReq condition) {
		StringBuffer sb = new StringBuffer();
		sb.append("select tgo.online_id, tgt.`name` typeName, tb.`name` brandName, tg.full_name, tgo.sale_price price FROM t_combo_package tgcp \n" +
				"left join t_goods_online tgo on tgo.online_id = tgcp.online_id \n" +
				"left join t_goods tg on tgo.spu_id = tg.id \n" +
				"left join t_goods_type tgt on tgt.id = tg.type_id \n" +
				"left join t_brand tb on tb.id = tg.brand_id \n" +
				"where tgcp.is_used = :isUsed ");
		List<Object> params = new ArrayList<Object>();
		params.add(IsUsed.USED.getValue());

		if(condition.getTypeId() != null) {
			sb.append("and tgt.id = :typeId ");
			params.add(condition.getTypeId());
		}

		if(condition.getBrandId() != null) {
			sb.append("and tb.id = :brandId ");
			params.add(condition.getBrandId());
		}

		if(StringUtil.isNotBlank(condition.getGoodsName())) {
			sb.append("and tg.full_name like :goodsName ");
			params.add("%"+condition.getGoodsName()+"%");
		}

		sb.append("group by tgo.online_id order by tgcp.id desc");
		return comboPackageDao.findPageBySql(sb.toString(), pageable, OpComboGoodsDto.class, params.toArray());
	}

	public List<ComboPackageDto> getComboPackageByOnlineId(Long onlineId) {
		String sql = "select tgo.online_id onlineId, tgog.content gift, tgcp.id packageId, tgcp.name packageName, tgcp.remaining_stock stock, tgcp.discount_money discountMoney, " +
				"tg.full_name goodsName, tg.small_pic imgUrl, tgo.sale_price salePrice, tgt.is_practice isPractice from t_combo_package tgcp \n" +
				"left join t_combo_package_rel tgcpr on tgcpr.package_id = tgcp.id \n" +
				"left join t_goods_online tgo on tgo.online_id = tgcpr.online_id \n" +
				"left join t_goods tg on tg.id = tgo.spu_id \n" +
				"left join t_goods_type tgt on tgt.id = tg.type_id \n" +
				"left join t_goods_online_gift tgog on tgog.goods_online_id = tgcpr.online_id \n" +
				"where tgcp.online_id = :onlineId and tgcp.is_used = :isUsed " +
				"and not exists (select tc.id from t_combo_package_rel tc, t_goods_online tgo where tc.online_id = tgo.online_id and tc.package_id = tgcp.id and (tgo.start_time > now() or tgo.is_used = "+IsUsed.NOT_USED.getValue()+")) \n " +
				"order by tgcp.id, tgcp.sort, tgcpr.sort";
		List<Object> params = new ArrayList<Object>();
		params.add(onlineId);
		params.add(IsUsed.USED.getValue());
		List<ComboGoodsFullDto> comboGoodsFullDtoList = comboPackageDao.findManyBySql(sql, ComboGoodsFullDto.class, params.toArray());

		StringBuffer sb = new StringBuffer();
		sql = "select GROUP_CONCAT(tgp.property_value) propertyVal from t_goods_property tgp left join t_goods_online tgo on tgp.good_id = tgo.spu_id where tgo.online_id = ?";
		List<Map<String, String>> mapList = comboPackageDao.findMapBySql(sql, onlineId);
		String propertyVal = mapList.get(0).get("propertyVal");
		if(StringUtil.isNotBlank(propertyVal)) {
			String[] selectTexts = propertyVal.split(",");
			for (String text : selectTexts) {
				if(StringUtil.isBlank(text)) {
					continue;
				}
				sb.append(text).append("  ");
			}
			if(sb.length() > 0) {
				sb.delete(sb.length() - 2, sb.length());
				sb.insert(0, "已选：");
			}
		}
		return convertComboPackageList(comboGoodsFullDtoList, sb.toString());
	}

	public List<ComboPackageDto> getComboPackageById(Long id) {
		String sql = "select tgo.online_id onlineId, tgog.content gift, tgcp.id packageId, tgcp.name packageName, tgcp.stock, tgcp.discount_money discountMoney, " +
				"tg.full_name goodsName, tg.small_pic imgUrl, tgo.sale_price salePrice, tgt.is_practice isPractice from t_combo_package tgcp \n" +
				"left join t_combo_package_rel tgcpr on tgcpr.package_id = tgcp.id \n" +
				"left join t_goods_online tgo on tgo.online_id = tgcpr.online_id \n" +
				"left join t_goods tg on tg.id = tgo.spu_id \n" +
				"left join t_goods_type tgt on tgt.id = tg.type_id \n" +
				"left join t_goods_online_gift tgog on tgog.goods_online_id = tgcpr.online_id \n" +
				"where tgcp.id = :id and tgcp.is_used = :isUsed " +
				"and not exists (select tc.id from t_combo_package_rel tc, t_goods_online tgo where tc.online_id = tgo.online_id and tc.package_id = tgcp.id and (tgo.start_time > now() or tgo.is_used = "+IsUsed.NOT_USED.getValue()+")) \n " +
				"order by tgcp.id, tgcp.sort, tgcpr.sort";
		List<Object> params = new ArrayList<Object>();
		params.add(id);
		params.add(IsUsed.USED.getValue());
		List<ComboGoodsFullDto> comboGoodsFullDtoList = comboPackageDao.findManyBySql(sql, ComboGoodsFullDto.class, params.toArray());
		return convertComboPackageList(comboGoodsFullDtoList, null);
	}

	public List<OpComboPackageDto> opComboPackageList(Long onlineId) {
		StringBuffer sb = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sb.append("select tgcp.id packageId, tgcp.name packageName, tgcp.remaining_stock stock, tgcp.discount_money from t_combo_package tgcp where tgcp.online_id = :onlineId and tgcp.is_used = :isUsed");
		params.add(onlineId);
		params.add(IsUsed.USED.getValue());
		List<OpComboPackageDto> packageList = comboPackageDao.findManyBySql(sb.toString(), OpComboPackageDto.class, params.toArray());
		List<OpComboGoodsDto> goodsList = null;
		TGoodsOnline goodsOnline = goodsOnlineDao.get(onlineId);
		for (OpComboPackageDto comboPackageDto : packageList) {
			sb.setLength(0);
			sb.append("select tgo.online_id, tgt.`name` typeName, tb.`name` brandName, tg.full_name, tgo.sale_price price from t_combo_package_rel tgcpr \n" +
					"left join t_goods_online tgo on tgo.online_id = tgcpr.online_id \n" +
					"left join t_goods tg on tgo.spu_id = tg.id \n" +
					"left join t_goods_type tgt on tgt.id = tg.type_id \n" +
					"left join t_brand tb on tb.id = tg.brand_id \n" +
					"where tgcpr.package_id = :packageId and tgcpr.type = :type");
			params.clear();
			params.add(comboPackageDto.getPackageId());
			params.add(TComboPackageRel.TYPE_CONSTANT.NOT_MAIN_GOODS);
			goodsList = comboPackageDao.findManyBySql(sb.toString(), OpComboGoodsDto.class, params.toArray());
			comboPackageDto.setGoodsList(goodsList);
			comboPackageDto.setPrice(goodsOnline.getSalePrice() - comboPackageDto.getDiscountMoney());
			for (OpComboGoodsDto goodsDto : goodsList) {
				comboPackageDto.setPrice(comboPackageDto.getPrice() + goodsDto.getPrice());
			}
		}
		return packageList;
	}

	@Transactional
	public void deteleComboPackage(Long onlineId) {
		QLBuilder ql = new QLBuilder();
		ql.and(Filter.eq("onlineId", onlineId));
		List<TComboPackage> list = comboPackageDao.findManyByJpql(ql);
		for (TComboPackage comboPackage : list) {
			comboPackage.setIsUsed(IsUsed.NOT_USED.getValue());
			comboPackageDao.update(comboPackage);
		}
	}

	public Map opComboPackageInfo(Long onlineId) {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sb.append("select tgo.online_id, tgt.`name` typeName, tb.`name` brandName, tg.full_name, tgo.sale_price price from t_goods_online tgo " +
				"left join t_goods tg on tgo.spu_id = tg.id \n" +
				"left join t_goods_type tgt on tgt.id = tg.type_id \n" +
				"left join t_brand tb on tb.id = tg.brand_id \n" +
				"where tgo.online_id = :onlineId");
		params.add(onlineId);
		map.put("goodsInfo", comboPackageDao.findOneBySql(sb.toString(), OpComboGoodsDto.class, params.toArray()));
		map.put("packageInfo", this.opComboPackageList(onlineId));
		return map;
	}

	/**
	 * 封装转换成套装集合
	 * @param comboGoodsFullDtoList
	 * @param selectText 主商品已选文本说明，不需要传null
	 * @return
	 */
	private List<ComboPackageDto> convertComboPackageList(List<ComboGoodsFullDto> comboGoodsFullDtoList, String selectText) {
		List<ComboPackageDto> comboPackageDtoList = new ArrayList<ComboPackageDto>();
		if(!CollectionUtils.isEmpty(comboGoodsFullDtoList)) {
			Long packageId = null;
			ComboPackageDto comboPackageDto = null;
			ComboGoodsDto comboGoodsDto = null;
			for (ComboGoodsFullDto comboGoodsFullDto : comboGoodsFullDtoList) {
				comboGoodsDto = new ComboGoodsDto();
				if(packageId == null || !packageId.equals(comboGoodsFullDto.getPackageId())) {
					packageId = comboGoodsFullDto.getPackageId();
					comboPackageDto = new ComboPackageDto();
					comboPackageDto.setPackageId(comboGoodsFullDto.getPackageId());
					comboPackageDto.setPackageName(comboGoodsFullDto.getPackageName());
					comboPackageDto.setHasStock(comboGoodsFullDto.getStock() > 0 ? TComboPackage.STOCK_CONSTANT.HAS_STOCK : TComboPackage.STOCK_CONSTANT.NO_STOCK);
					comboPackageDto.setPrice(-comboGoodsFullDto.getDiscountMoney());
					comboPackageDto.setGoodsList(new ArrayList<ComboGoodsDto>());
					comboPackageDtoList.add(comboPackageDto);
					comboGoodsDto.setSelectText(selectText);
				}
				comboGoodsDto.setGoodsName(comboGoodsFullDto.getGoodsName());
				comboGoodsDto.setImgUrl(comboGoodsFullDto.getImgUrl());
				comboGoodsDto.setGift(comboGoodsFullDto.getGift());
				comboGoodsDto.setOnlineId(comboGoodsFullDto.getOnlineId());
				comboGoodsDto.setIsPractice(comboGoodsFullDto.getIsPractice());
				comboPackageDto.setPrice(comboPackageDto.getPrice() + comboGoodsFullDto.getSalePrice());
				comboPackageDto.getGoodsList().add(comboGoodsDto);
			}
		}
		return comboPackageDtoList;
	}
}
