package com.bdqn.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bdqn.entity.RoleMenu;
import com.bdqn.repository.RoleMenuRepository;
import com.bdqn.service.RoleMenuService;


/**
 * 角色菜单关联Service实现类
 * @author Administrator
 *
 */
@Service("roleMenuService")
@Transactional
public class RoleMenuServiceImpl implements RoleMenuService{

	@Resource
	private RoleMenuRepository roleMenuRepository;
	
	@Override
	public void deleteByRoleId(Integer roleId) {
		roleMenuRepository.deleteByRoleId(roleId);
	}

	@Override
	public void save(RoleMenu roleMenu) {
		// TODO Auto-generated method stub
		roleMenuRepository.save(roleMenu);
	}



}
