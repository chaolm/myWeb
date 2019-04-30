package com.dripop.sys.service.impl;


import com.bean.IsUsed;
import com.bean.SysOperType;
import com.dripop.constant.RegexConstant;
import com.dripop.core.bean.Filter;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.bean.QLBuilder;
import com.dripop.core.exception.ServiceException;
import com.dripop.core.util.BeanUtils;
import com.dripop.core.util.StringUtil;
import com.dripop.dao.PhotoBookDao;
import com.dripop.dao.SysOperDao;
import com.dripop.dao.SysOrgDao;
import com.dripop.entity.TPhotoBook;
import com.dripop.entity.TSysOper;
import com.dripop.entity.TSysOrg;
import com.dripop.sys.dto.OpStoreSearchDto;
import com.dripop.sys.dto.OpStoreSearchReq;
import com.dripop.sys.service.SysOrgService;
import com.dripop.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SysOrgServiceImpl implements SysOrgService {

	@Autowired
	public SysOrgDao sysOrgDao;

	@Autowired
	private PhotoBookDao photoBookDao;

	@Autowired
	private SysOperDao sysOperDao;

	public TSysOrg findById(Long id) {
		return sysOrgDao.get(id);
	}

	public void update(TSysOrg sysOrg) {
		sysOrgDao.update(sysOrg);
	}

	public Pagination<OpStoreSearchDto> pageStore(OpStoreSearchReq reqDto, Pageable pageable) {
		StringBuffer sb = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sb.append("select tso.*, count(tsop.oper_id) personNum FROM t_sys_org tso " +
				"left join t_sys_oper tsop on tso.org_id = tsop.org_id where tso.is_used = :isUsed ");
		params.add(IsUsed.USED.getValue());
		if(StringUtil.isNotBlank(reqDto.getProvince())) {
			sb.append("and tso.province = :province ");
			params.add(reqDto.getProvince());
		}
		if(StringUtil.isNotBlank(reqDto.getCity())) {
			sb.append("and tso.city = :city ");
			params.add(reqDto.getCity());
		}
		if(StringUtil.isNotBlank(reqDto.getCounty())) {
			sb.append("and tso.county = :county ");
			params.add(reqDto.getCounty());
		}
		if(reqDto.getStatus() != null) {
			sb.append("and tso.status = :status ");
			params.add(reqDto.getStatus());
		}
		if(StringUtil.isNotBlank(reqDto.getName())) {
			sb.append("and tso.name like :name ");
			params.add("%"+reqDto.getName()+"%");
		}
		sb.append("group by tso.org_id ");
		return sysOrgDao.findPageBySql(sb.toString(), pageable, OpStoreSearchDto.class, params.toArray());
	}

	@Transactional
	public void businessSetting(Long orgId, Integer status) {
		TSysOrg sysOrg = sysOrgDao.get(orgId);
		sysOrg.setStatus(status);
		sysOrg.setModifyTime(new Date());
		sysOrg.setModifyUserId(UserUtil.currentAdminUser().getId());
		sysOrgDao.update(sysOrg);
		if(status == 2) {
			TSysOper sysOper = getOperByOrgId(orgId);
			UserUtil.removeSaleUser(sysOper.getOperId());
		}
	}

	@Transactional
	public void deleteStore(Long orgId) {
		TSysOrg sysOrg = sysOrgDao.get(orgId);
		sysOrg.setIsUsed(IsUsed.NOT_USED.getValue());
		sysOrg.setModifyTime(new Date());
		sysOrg.setModifyUserId(UserUtil.currentAdminUser().getId());
		TSysOper sysOper = getOperByOrgId(orgId);
		if(sysOper != null){
			UserUtil.removeSaleUser(sysOper.getOperId());
		}
		sysOrgDao.update(sysOrg);
	}

	@Transactional
	public void addStore(TSysOrg sysOrg,String logoStr, String password) {
		String [] logoArray=logoStr.split(",");
		sysOrg.setCreateTime(new Date());
		sysOrg.setCreateUserId(UserUtil.currentAdminUser().getId());
		sysOrg.setIsUsed(IsUsed.USED.getValue());
		sysOrg.setStatus(1);
		sysOrg.setIsVisible(1);
		sysOrg.setOrgType(3);
		sysOrg.setLogo(logoArray[0]);
		sysOrg.setShortName(sysOrg.getName());
		sysOrgDao.insert(sysOrg);

		TPhotoBook photoBook=null;

		for(String logoItem : logoArray){
			photoBook = new TPhotoBook();
			photoBook.setRefId(sysOrg.getId());
			photoBook.setType(2);
			photoBook.setModify_date(new Date());
			photoBook.setImgUrl(logoItem);
			photoBookDao.insert(photoBook);
		}
		//自动新建门店用户
		createSeller(sysOrg, password);
	}

	@Transactional
	public void updateStore(TSysOrg sysOrg,String logoStr,String password) {
		String [] logoArray=logoStr.split(",");

		TSysOrg t = sysOrgDao.get(sysOrg.getId());
		BeanUtils.copyProperties(sysOrg, t);
		t.setShortName(sysOrg.getName());
		t.setModifyTime(new Date());
		t.setModifyUserId(UserUtil.currentAdminUser().getId());
		t.setLogo(logoArray[0]);
		sysOrgDao.update(t);

		//删除旧的门店风采图片
		QLBuilder ql = new QLBuilder();
		ql.and(Filter.eq("refId", sysOrg.getId()));
		photoBookDao.deleteByJpql(ql);

		//更新门店风采图片
		TPhotoBook photoBook=null;

		for(String logoItem : logoArray){
			photoBook = new TPhotoBook();
			photoBook.setRefId(sysOrg.getId());
			photoBook.setType(2);
			photoBook.setModify_date(new Date());
			photoBook.setImgUrl(logoItem);
			photoBookDao.insert(photoBook);
		}
		if(StringUtil.isNotBlank(password)){
			TSysOper sysOper = getOperByOrgId(sysOrg.getId());
			if(sysOper == null) {
				createSeller(sysOrg, password);
			}else {
				RegexConstant.matches(RegexConstant.PWD, password);
				sysOper.setPassword(StringUtil.toMD5(password+sysOper.getSalt()));
				UserUtil.removeSaleUser(sysOper.getOperId());
				sysOperDao.update(sysOper);
			}
		}
	}

	public List<OpStoreSearchDto> listStore(OpStoreSearchReq reqDto) {

		StringBuffer sb=new StringBuffer();

		List<Object> params = new ArrayList<Object>();

		sb.append("SELECT org_id,name FROM t_sys_org WHERE is_used =1 ");

		if(StringUtil.isNotBlank(reqDto.getProvince())) {
			sb.append("and province = :province ");
			params.add(reqDto.getProvince());
		}
		if(StringUtil.isNotBlank(reqDto.getCity())) {
			sb.append("and city = :city ");
			params.add(reqDto.getCity());
		}
		if(StringUtil.isNotBlank(reqDto.getCounty())) {
			sb.append("and county = :county ");
			params.add(reqDto.getCounty());
		}

		if(StringUtil.isNotBlank(reqDto.getName())) {
			sb.append("and name like :name ");
			params.add("%"+reqDto.getName()+"%");
		}

		return sysOrgDao.findManyBySql(sb.toString(),OpStoreSearchDto.class,params.toArray());
	}

	public TSysOrg storeDetail(Long orgId) {

		TSysOrg sysOrg=sysOrgDao.get(orgId);

		StringBuffer sb=new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		params.add(orgId);
		sb.append("select * from t_photo_book where ref_id= :orgId and type=2");
		List<TPhotoBook> logoList=photoBookDao.findManyBySql(sb.toString(), TPhotoBook.class, params.toArray());

		StringBuilder logoSb=new StringBuilder();

		for(TPhotoBook photoBook : logoList) {
			logoSb.append(photoBook.getImgUrl());
			logoSb.append(",");
		}
		if(logoSb.length() > 0) {
			logoSb.deleteCharAt(logoSb.length()-1);
			sysOrg.setLogo(logoSb.toString());
		}

		return sysOrg;
	}

	/**
	 * 创建门店用户
	 * @param sysOrg
	 * @param password
	 */
	private void createSeller(TSysOrg sysOrg, String password) {
		RegexConstant.matches(RegexConstant.PWD, password);
		TSysOper sysOper= new TSysOper();
		sysOper.setSalt(StringUtil.getRadomString(8, 2));
		sysOper.setPassword(StringUtil.toMD5(password+sysOper.getSalt()));
		String sql = "select count(1) from t_sys_oper t where t.type = :type";
		Long orgUserCount = sysOperDao.findOneBySql(sql, Long.class, SysOperType.SELLER.getValue());
		orgUserCount += 10001L;
		sysOper.setLoginName("jj"+(orgUserCount.toString().substring(1)));
		sysOper.setType(SysOperType.SELLER.getValue());
		sysOper.setIsUsed(IsUsed.USED.getValue());
		sysOper.setCreateTime(new Date());
		sysOper.setCreateUserId(UserUtil.currentAdminUser().getId());
		sysOper.setModifyTime(new Date());
		sysOper.setModifyUserId(UserUtil.currentAdminUser().getId());
		sysOper.setStatus(1);
		sysOper.setOperName(sysOrg.getShortName());
		sysOper.setOrgId(sysOrg.getId());
		sysOperDao.insert(sysOper);
	}

	/**
	 * 根据门店获取用户
	 * @param orgId
	 * @return
	 */
	private TSysOper getOperByOrgId(Long orgId) {
		StringBuffer sb = new StringBuffer();
		sb.append("select tso.* from t_sys_oper tso where tso.org_id = :orgId ");
		return sysOperDao.findOneBySql(sb.toString(), TSysOper.class, orgId);
	}
}