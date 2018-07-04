package com.bdqn.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bdqn.entity.Menu;
import com.bdqn.repository.MenuRepository;
import com.bdqn.service.MenuService;

/**
 * 权限菜单Service实现类
 * @author Administrator
 *
 */
@Service("menuService")
public class MenuServiceImpl implements MenuService{

	@Resource
	private MenuRepository menuRepository;
	
	@Override
	public List<Menu> findByParentIdAndRoleId(int parentId, int roleId) {
		return menuRepository.findByparentIdAndRoleId(parentId, roleId);
	}

	@Override
	public List<Menu> findByRoleId(int roleId) {
		// TODO Auto-generated method stub
		return menuRepository.findByRoleId(roleId);
	}

	@Override
	public List<Menu> findByParentId(int parentId) {
		// TODO Auto-generated method stub
		return menuRepository.findByParentId(parentId);
	}

	@Override
	public Menu findById(Integer id) {
		// TODO Auto-generated method stub
		return menuRepository.findOne(id);
	}

}
