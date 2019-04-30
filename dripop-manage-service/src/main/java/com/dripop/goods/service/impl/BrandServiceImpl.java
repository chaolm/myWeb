package com.dripop.goods.service.impl;

import com.bean.IsUsed;
import com.dripop.core.bean.Filter;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.bean.QLBuilder;
import com.dripop.core.exception.ServiceException;
import com.dripop.dao.BrandDao;
import com.dripop.entity.TBrand;
import com.dripop.goods.service.BrandService;
import com.dripop.util.UserUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

	@Autowired
	private BrandDao brandDao;

	@Transactional
	public void saveBrand(TBrand brand) {
		QLBuilder ql = new QLBuilder();
		ql.and(Filter.eq("name", brand.getName()));
		ql.and(Filter.eq("isUsed", IsUsed.USED.getValue()));
		TBrand tBrand = brandDao.findOneByJpql(ql);
		if(tBrand != null) {
			throw new ServiceException("已存在该品牌");
		}
		brand.setCreateTime(new Date());
		brand.setCreateUserId(UserUtil.currentAdminUser().getId());
		brand.setIsUsed(IsUsed.USED.getValue());
		brandDao.insert(brand);
	}

	@Transactional
	public void updateBrand(TBrand brand) {
		QLBuilder ql = new QLBuilder();
		ql.and(Filter.eq("name", brand.getName()));
		ql.and(Filter.eq("isUsed", IsUsed.USED.getValue()));
		ql.and(Filter.ne("id", brand.getId()));
		TBrand tBrand = brandDao.findOneByJpql(ql);
		if(tBrand != null) {
			throw new ServiceException("已存在该品牌");
		}
		TBrand t = brandDao.get(brand.getId());
		BeanUtils.copyProperties(brand, t);
		t.setModifyTime(new Date());
		brandDao.update(t);
	}

	public TBrand findById(Long id) {
		return brandDao.get(id);
	}

	@Transactional
	public void deleteBrand(Long id) {
		brandDao.delete(id);
	}

	public Pagination<TBrand> brandPage(String name, Pageable pageable) {
		QLBuilder ql = new QLBuilder();
		ql.and(Filter.like("name", "%"+name+"%"));
		return brandDao.findPageByJpql(ql, pageable);
	}

	public List<TBrand> brandList(Long typeId) {
		StringBuffer sb = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		if(typeId != null) {
			sb.append("select tb.id brandId, tb.is_used, tb.name from t_type_brand ttb " +
					"left join t_brand tb on ttb.brand_id = tb.id " +
					"where ttb.type_id = :typeId");
			params.add(typeId);
		}else {
			sb.append("select tb.id brandId, tb.is_used, tb.name from t_brand tb");
		}
		return brandDao.findManyBySql(sb.toString(), TBrand.class, params.toArray());
	}
}