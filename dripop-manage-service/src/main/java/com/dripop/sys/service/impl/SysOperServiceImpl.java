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
import com.dripop.dao.SysOperDao;
import com.dripop.dao.SysOperRoleGranteDao;
import com.dripop.dao.SysOperRoleRelDao;
import com.dripop.dao.SysRoleDao;
import com.dripop.entity.TSysOper;
import com.dripop.entity.TSysOperRoleRel;
import com.dripop.entity.TSysRole;
import com.dripop.entity.TSysRoleGrante;
import com.dripop.sys.dto.*;
import com.dripop.sys.service.SysOperService;
import com.dripop.util.UserUtil;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SysOperServiceImpl implements SysOperService {
	
	@Autowired
	private SysOperDao sysOperDao;

	@Autowired
	private SysRoleDao sysRoleDao;

	@Autowired
	private SysOperRoleRelDao sysOperRoleRelDao;

	@Autowired
	private SysOperRoleGranteDao sysOperRoleGranteDao;


	public TSysOper findById(Long id) {
		return sysOperDao.get(id);
	}

	public Pagination<OpOperSearchDto> pageOper(OpOperSearchReq reqDto, Pageable pageable) {
		StringBuffer sb = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sb.append("select tsop.*, tsr.role_id, tsr.name roleName, tso.name orgName FROM t_sys_oper tsop " +
				"left join t_sys_oper_role_rel tsor on tsor.oper_id = tsop.oper_id " +
				"left join t_sys_role tsr on tsr.role_id = tsor.role_id " +
				"left join t_sys_org tso on tso.org_id = tsop.org_id " +
				"where tsop.is_used = :isUsed and tsop.type = :type ");
		params.add(IsUsed.USED.getValue());
		params.add(SysOperType.SELLER.getValue());
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
		if(reqDto.getOrgId() != null) {
			sb.append("and tsop.org_id = :orgId ");
			params.add(reqDto.getOrgId());
		}
		if(reqDto.getRoleId() != null) {
			sb.append("and tsr.role_id = :roleId ");
			params.add(reqDto.getRoleId());
		}
		if(StringUtil.isNotBlank(reqDto.getName())) {
			sb.append("and (tsop.oper_name like :operName and tsop.phone_no like :phoneNo) ");
			params.add("%"+reqDto.getName()+"%");
		}
		if(reqDto.getStatus()!=null){
			sb.append("and tsop.status= :status ");
			params.add(reqDto.getStatus());
		}
		return sysOperDao.findPageBySql(sb.toString(), pageable, OpOperSearchDto.class, params.toArray());
	}

	@Transactional
	public void operStatusSetting(Long operId, Integer status) {
		TSysOper sysOper = sysOperDao.get(operId);
		sysOper.setStatus(status);
		sysOper.setModifyUserId(UserUtil.currentAdminUser().getId());
		sysOper.setModifyTime(new Date());
		sysOperDao.update(sysOper);
	}

	@Transactional
	public void deleteOper(Long operId) {
		TSysOper sysOper = sysOperDao.get(operId);
		sysOper.setIsUsed(IsUsed.NOT_USED.getValue());
		sysOper.setModifyUserId(UserUtil.currentAdminUser().getId());
		sysOper.setModifyTime(new Date());
		sysOperDao.update(sysOper);
	}

	@Transactional
	public void addOper(TSysOper sysOper, Long roleId, Long orgId) {
		QLBuilder ql = new QLBuilder();
		ql.and(Filter.eq("orgId", orgId));
		ql.and(Filter.eq("loginName", sysOper.getPhoneNo()));
		ql.and(Filter.eq("isUsed", IsUsed.USED.getValue()));
		TSysOper t = sysOperDao.findOneByJpql(ql);
		if(t != null) {
			throw new ServiceException("该门店下已存在该店员");
		}
		sysOper.setType(SysOperType.SELLER.getValue());
		sysOper.setIsUsed(IsUsed.USED.getValue());
		sysOper.setCreateTime(new Date());
		sysOper.setCreateUserId(UserUtil.currentAdminUser().getId());
		sysOper.setStatus(1);
		sysOperDao.insert(sysOper);
		TSysOperRoleRel operRoleRel = new TSysOperRoleRel();
		operRoleRel.setOperId(sysOper.getOperId());
		operRoleRel.setRoleId(roleId);
		operRoleRel.setIsUsed(IsUsed.USED.getValue());
		operRoleRel.setCreateTime(new Date());
		operRoleRel.setCreateUserId(UserUtil.currentAdminUser().getId());
		sysOperRoleRelDao.insert(operRoleRel);
	}

	@Transactional
	public void deleteRole(Long roleId) {
		sysRoleDao.delete(roleId);
		String sql = "DELETE from t_sys_role_grante  where role_id = :roleId";
		sysOperRoleGranteDao.executeBySql(sql,roleId);
	}

	@Transactional
	public void createRole(String roleName, String remark) {
		TSysRole tSysRole = new TSysRole();
		tSysRole.setName(roleName);
		tSysRole.setRemark(remark);
		tSysRole.setIsUsed(IsUsed.USED.getValue());
		tSysRole.setCreateTime(new Date());
		tSysRole.setCreateUserId(UserUtil.currentAdminUser().getId());
		tSysRole.setModifyTime(new Date());
		tSysRole.setModifyUserId(UserUtil.currentAdminUser().getId());
		tSysRole.setIsInit(0);
		sysRoleDao.insert(tSysRole);
	}

	@Transactional
	public void updateOper(TSysOper sysOper, Long roleId, Long orgId) {
		QLBuilder ql = new QLBuilder();
		ql.and(Filter.eq("orgId", orgId));
		ql.and(Filter.eq("loginName", sysOper.getPhoneNo()));
		ql.and(Filter.eq("isUsed", IsUsed.USED.getValue()));
		ql.and(Filter.ne("operId", sysOper.getOperId()));
		TSysOper t = sysOperDao.findOneByJpql(ql);
		if(t != null) {
			throw new ServiceException("该门店下已存在该店员");
		}
		t = sysOperDao.get(sysOper.getOperId());
		BeanUtils.copyProperties(sysOper, t);
		t.setModifyTime(new Date());
		t.setModifyUserId(UserUtil.currentAdminUser().getId());
		sysOperDao.update(t);
	}

	public List<TSysRole> listRole() {
		QLBuilder ql = new QLBuilder();
		return sysRoleDao.findManyByJpql(ql);
	}

	public List<OpOperSearchDto> listAdminOper() {
		StringBuffer sb = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sb.append("select tsop.*, tsr.role_id, tsr.name roleName from t_sys_oper tsop " +
				"left join t_sys_oper_role_rel tsor on tsor.oper_id = tsop.oper_id " +
				"left join t_sys_role tsr on tsr.role_id = tsor.role_id " +
				"where tsop.is_used = :isUsed and tsop.type = :type ");
		params.add(IsUsed.USED.getValue());
		params.add(SysOperType.ADMIN.getValue());
		return sysOperDao.findManyBySql(sb.toString(), OpOperSearchDto.class, params.toArray());
	}

	public List<Long> getUserRoleManageList(Long operId) {
		StringBuffer sb = new StringBuffer();
		List<Object> param = new ArrayList<>();
		sb.append("\n" +
				"select tsrg.ent_id from t_sys_role_grante tsrg \n" +
				"where \n" +
				"tsrg.role_id in ( SELECT role_id from  t_sys_oper_role_rel tsorr where tsorr.oper_id = :operId )");
		param.add(operId);
		List<Long> lists = sysOperDao.findManyBySql(sb.toString(),Long.class,param.toArray());
		return lists;
	}

	@Transactional
	public void addAdminOper(TSysOper sysOper, Long roleId) {
		QLBuilder ql = new QLBuilder();
		ql.and(Filter.eq("loginName", sysOper.getLoginName()));
		ql.and(Filter.eq("isUsed", IsUsed.USED.getValue()));
		ql.and(Filter.eq("type", SysOperType.ADMIN.getValue()));
		TSysOper t = sysOperDao.findOneByJpql(ql);
		if(t != null) {
			throw new ServiceException("已存在该管理员账号");
		}
		RegexConstant.matches(RegexConstant.PWD, sysOper.getPassword());
		sysOper.setSalt(StringUtil.getRadomString(8, 2));
		sysOper.setLoginName(sysOper.getLoginName());
		sysOper.setPhoneNo(sysOper.getLoginName());
		if(sysOper.getOperName() == null){
			throw new ServiceException("请填写操作人员");
		}
		sysOper.setOperName(sysOper.getOperName());
		sysOper.setSalt(StringUtil.getRadomString(8, 2));
		sysOper.setPassword(StringUtil.toMD5(sysOper.getPassword()+sysOper.getSalt()));
		sysOper.setType(SysOperType.ADMIN.getValue());
		sysOper.setIsUsed(IsUsed.USED.getValue());
		sysOper.setCreateTime(new Date());
		sysOper.setCreateUserId(UserUtil.currentAdminUser().getId());
		sysOperDao.insert(sysOper);

		TSysOperRoleRel sysOperRoleRel=new TSysOperRoleRel();
		sysOperRoleRel.setOperId(sysOper.getOperId());
		sysOperRoleRel.setRoleId(roleId);
		sysOperRoleRel.setCreateUserId(UserUtil.currentAdminUser().getId());
		sysOperRoleRel.setCreateTime(new Date());
		sysOperRoleRel.setIsUsed(IsUsed.USED.getValue());

		sysOperRoleRelDao.insert(sysOperRoleRel);
	}

	@Transactional
	public void updateAdminOper(TSysOper sysOper, Long roleId) {
		QLBuilder ql = new QLBuilder();
		ql.and(Filter.eq("loginName", sysOper.getLoginName()));
		ql.and(Filter.eq("isUsed", IsUsed.USED.getValue()));
		ql.and(Filter.eq("type", SysOperType.ADMIN.getValue()));
		ql.and(Filter.ne("operId", sysOper.getOperId()));
		TSysOper t = sysOperDao.findOneByJpql(ql);
		if(t != null) {
			throw new ServiceException("已存在该管理员账号");
		}
		t = sysOperDao.get(sysOper.getOperId());
		sysOper.setSalt(null);
		if(StringUtil.isNotBlank(sysOper.getPassword())) {
			sysOper.setPassword(StringUtil.toMD5(sysOper.getPassword()+t.getSalt()));
		}else {
			sysOper.setPassword(null);
		}
		BeanUtils.copyProperties(sysOper, t);
		t.setModifyTime(new Date());
		t.setModifyUserId(UserUtil.currentAdminUser().getId());

		ql = new QLBuilder();
		ql.and(Filter.eq("operId", sysOper.getOperId()));
		sysOperRoleRelDao.deleteByJpql(ql);

		TSysOperRoleRel sysOperRoleRel=new TSysOperRoleRel();
		sysOperRoleRel.setOperId(sysOper.getOperId());
		sysOperRoleRel.setRoleId(roleId);
		sysOperRoleRel.setCreateUserId(UserUtil.currentAdminUser().getId());
		sysOperRoleRel.setCreateTime(new Date());
		sysOperRoleRel.setIsUsed(IsUsed.USED.getValue());
		sysOperRoleRelDao.insert(sysOperRoleRel);

		UserUtil.removeAdminUser(sysOper.getOperId());
		sysOperDao.update(t);
	}

	@Transactional
	public void roleManage(Long roleId, MenuIdsReq menuIds) {
		TSysRoleGrante tSysRoleGrante = null;
		List<Long> list = menuIds.getLongs();
		String sql = "DELETE FROM t_sys_role_grante where role_id = :roleId";
		sysOperRoleGranteDao.executeBySql(sql,roleId);
		for(Long result:list){
			tSysRoleGrante = new TSysRoleGrante();
			tSysRoleGrante.setCreateTime(new Date());
			tSysRoleGrante.setCreateUserId(UserUtil.currentAdminUser().getId());
			tSysRoleGrante.setRoleId(roleId);
			tSysRoleGrante.setEntId(result);
			tSysRoleGrante.setGranteRel(1);
			tSysRoleGrante.setDomainId(1);
			tSysRoleGrante.setIsUsed(IsUsed.USED.getValue());
			tSysRoleGrante.setModifyTime(new Date());
			tSysRoleGrante.setModifyUserId(UserUtil.currentAdminUser().getId());
			sysOperRoleGranteDao.insert(tSysRoleGrante);
		}
		UserUtil.removeAdminByRole(roleId);
	}

	@Transactional
	public void deleteAdminOper(Long operId) {
		TSysOper sysOper = sysOperDao.get(operId);
		sysOper.setIsUsed(IsUsed.NOT_USED.getValue());
		UserUtil.removeAdminUser(sysOper.getOperId());
		sysOperDao.update(sysOper);
	}

	public List getPermission(Long roleId) {
		StringBuffer sb = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sb.append("select menu.menu_id, menu.`name` title, menu.parentId, rel.grante_id permissionId from t_sys_menu menu " +
				"left join t_sys_role_grante rel on rel.ent_id = menu.menu_id and rel.is_used = :isUsed and rel.grante_rel = 1 and rel.role_id = :roleId " +
				"left join t_sys_role role on role.role_id = rel.role_id " +
				"where menu.`level` != 0 and menu.is_used = :isUsed and menu.type != 2 " +
				"order by menu.parentId, menu.menu_id");
		params.add(IsUsed.USED.getValue());
		params.add(roleId);
		List<OpPermissionMenuDto> list = sysRoleDao.findManyBySql(sb.toString(), OpPermissionMenuDto.class, params.toArray());

		List<OpPermissionMenuDto> menuList = new ArrayList<OpPermissionMenuDto>();
		Map<Long, OpPermissionMenuDto> menuMap = new LinkedHashMap<Long, OpPermissionMenuDto>();
		for (OpPermissionMenuDto permissionMenuDto : list) {
			if(permissionMenuDto.getParentId() == -1) {
				permissionMenuDto.setChildren(new ArrayList<OpPermissionMenuDto>());
				menuMap.put(permissionMenuDto.getMenuId(), permissionMenuDto);
			}else if(menuMap.containsKey(permissionMenuDto.getParentId())) {
				menuMap.get(permissionMenuDto.getParentId()).getChildren().add(permissionMenuDto);
			}
		}

		for (Long menuId : menuMap.keySet()) {
			menuList.add(menuMap.get(menuId));
		}

		return menuList;
	}

	public OpOperDetailDto operDetail(Long operId, Long roleId) {

		StringBuffer sb = new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		sb.append("SELECT tsop.oper_id,tsop.oper_name,tsop.photo,tsop.phone_no,tsr.role_id," +
				"tsr.name roleName,tso.org_id, tso.name orgName,tsop.sex,tsop.birthday FROM t_sys_oper tsop "+
				"left join t_sys_oper_role_rel tsor on tsor.oper_id = tsop.oper_id " +
				"left join t_sys_role tsr on tsr.role_id = tsor.role_id " +
				"left join t_sys_org tso on tso.org_id = tsop.org_id " +
				"where tsr.is_used =1 and tsop.oper_id = :operId and tsr.role_id= :roleId");

		params.add(operId);
		params.add(roleId);
		return sysOperDao.findOneBySql(sb.toString(),OpOperDetailDto.class,params.toArray());
	}

	public TSysOper login(String loginName, String password) {
		QLBuilder ql = new QLBuilder();
		ql.and(Filter.eq("loginName", loginName));
		ql.and(Filter.eq("type", SysOperType.ADMIN.getValue()));
		ql.and(Filter.eq("isUsed", IsUsed.USED.getValue()));
		TSysOper sysOper = sysOperDao.findOneByJpql(ql);
		if(sysOper == null) {
			throw new ServiceException("用户不存在");
		}
		if(sysOper.getStatus() == 3) {
			throw new ServiceException("该用户已停用");
		}
		if(!StringUtil.toMD5(password+sysOper.getSalt()).equals(sysOper.getPassword())) {
			throw new ServiceException("用户名或密码不正确");
		}
		List<Object> param = new ArrayList<>();
		String sql = "select tsm.resUrl FROM t_sys_menu tsm where tsm.menu_id in \n" +
				"(select tsrg.ent_id from t_sys_role_grante tsrg \n" +
				"where tsrg.role_id in \n" +
				"(SELECT role_id from  t_sys_oper_role_rel tsorr where tsorr.oper_id = :operId))";
		param.add(sysOper.getOperId());
		List<String> permissions = sysOperDao.findManyBySql(sql, String.class, param.toArray());
		sysOper.setPermissions(permissions);
		return sysOper;
	}

	public TSysRole getRoleByOperId(Long operId) {
		String sql = "select tsr.* from t_sys_role tsr " +
				"left join t_sys_oper_role_rel tsrr on tsrr.role_id = tsr.role_id " +
				"where tsrr.oper_id = :operId";
		return sysRoleDao.findOneBySql(sql, TSysRole.class, operId);
	}

}
