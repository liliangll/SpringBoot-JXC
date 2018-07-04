package com.bdqn.service;

import com.bdqn.entity.RoleMenu;

/**
 * 角色菜单关联service接口
 * @author Administrator
 *
 */
public interface RoleMenuService {
	
	/**
	 * 根据角色id删除所有关联信息
	 * @param userId
	 */
	public void deleteByRoleId(Integer roleId);
	
	/**
	 * @param roleMenu
	 * 根据角色菜单查询
	 */
	public void save(RoleMenu roleMenu);
}
