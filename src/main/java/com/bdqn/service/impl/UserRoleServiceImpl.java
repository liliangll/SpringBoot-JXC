package com.bdqn.service.impl;



import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bdqn.entity.UserRole;
import com.bdqn.repository.UserRoleRepository;
import com.bdqn.service.UserRoleService;
/**
 * @author asus
 *用户角色关联UserRoleServiceImpl
 */
@Service("userRoleService")
@Transactional
public class UserRoleServiceImpl implements UserRoleService {
	@Resource
	private UserRoleRepository userRoleRepository;
	
	@Override
	public void deleteByUserId(Integer userId) {
		userRoleRepository.deleteByUserId(userId);
	}

	@Override
	public void save(UserRole userRole) {
		userRoleRepository.save(userRole);
	}

	@Override
	public void deleteByRoleId(Integer roleId) {
		userRoleRepository.deleteByRoleId(roleId);
	}

}
