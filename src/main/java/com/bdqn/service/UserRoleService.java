package com.bdqn.service;

import com.bdqn.entity.UserRole;

/**
 * @author asus
 *用户角色关联Service
 */
public interface UserRoleService {
	/**
	 * 根据用户id删除所有关联信息
	 * @param userId
	 */
	public void deleteByUserId(Integer userId);
	
	/**
	 * 添加或者修改用户角色关联
	 * @param userRole
	 */
	public void save(UserRole userRole);
	
	/**
	 * 根据角色id删除所有关联信息
	 * @param userId
	 */
	public void deleteByRoleId(Integer roleId);
}
