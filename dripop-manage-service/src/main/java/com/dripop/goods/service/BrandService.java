package com.dripop.goods.service;

import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.entity.TBrand;

import java.util.List;

public interface BrandService {
	/**
	 * 新增品牌
	 * @param brand
	 */
    void saveBrand(TBrand brand);

	/**
	 * 修改品牌
	 * @param brand
	 */
	void updateBrand(TBrand brand);

	TBrand findById(Long id);

	/**
	 * 删除品牌
	 * @param id
	 */
	void deleteBrand(Long id);

	Pagination<TBrand> brandPage(String name, Pageable pageable);

	List<TBrand> brandList(Long typeId);

}