package com.bdqn.service;


import java.util.List;

import com.bdqn.entity.Menu;


/**
 * 权限菜单Service接口
 * @author Administrator
 *
 */
public interface MenuService {
	/**
	 * @param id
	 * @return
	 * 根据id查询菜单实体
	 */
	public Menu findById(Integer id);
	/**
	 * 根据父节点以及用户角色id查询子节点
	 * @param id
	 * @return
	 */
	public List<Menu> findByParentIdAndRoleId(int parentId,int roleId);
	
	
	/**
	 * @param roleId
	 * @return
	 * 根据角色id获取菜单信息
	 */
	public List<Menu> findByRoleId(int roleId);
	
	/**
	 * @param parentId
	 * @return
	 * 根据父节点查询所有子节点
	 */
	public List<Menu> findByParentId(int parentId);
}
