package com.dripop.sys.service;

import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.entity.TSysOper;
import com.dripop.entity.TSysRole;
import com.dripop.sys.dto.*;

import java.util.List;

public interface SysOperService {

	TSysOper findById(Long id);

    Pagination<OpOperSearchDto> pageOper(OpOperSearchReq reqDto, Pageable pageable);

	/**
	 * 设置店员状态启用、停用、审核通过、拒绝
	 * @param operId
	 * @param status
	 */
	void operStatusSetting(Long operId, Integer status);

	void deleteOper(Long operId);

	void addOper(TSysOper sysOper, Long roleId, Long orgId);

	void updateOper(TSysOper sysOper, Long roleId, Long orgId);

	/**
	 * 新增角色
	 * @param roleName
	 * @param remark
	 */
	void createRole(String roleName,String remark);

	/**
	 * 删除角色
	 * @param roleId
	 */
	void deleteRole(Long roleId);
	/**
	 * 权限设置
	 * @param roleId
	 * @param menuIds
	 */
	void roleManage(Long roleId,MenuIdsReq menuIds);

	/**
	 * 获取用户的权限列表
	 * @return
	 */
	List<Long> getUserRoleManageList(Long operId);

	List<TSysRole> listRole();

	List<OpOperSearchDto> listAdminOper();

	void addAdminOper(TSysOper sysOper, Long roleId);

	void updateAdminOper(TSysOper sysOper, Long roleId);

	void deleteAdminOper(Long operId);

	/**
	 * 获取角色权限
	 * @param roleId
	 * @return
	 */
	List getPermission(Long roleId);

	OpOperDetailDto operDetail(Long operId, Long roleId);

    TSysOper login(String loginName, String password);

    TSysRole getRoleByOperId(Long operId);

}
