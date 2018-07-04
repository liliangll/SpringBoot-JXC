package com.bdqn.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.bdqn.entity.Role;


/**
 * 角色Service接口
 * @author Administrator
 *
 */
public interface RoleService {
	/**
	 * 根据角色名查找角色实体
	 * @param roleName
	 * @return
	 */
	public Role findByRoleName(String roleName);

	/**
	 * 根据用户id查角色集合
	 * @param id
	 * @return
	 */
	public List<Role> findByUserId(Integer id);
	
	/**
	 * 根据id查询实体
	 * @param id
	 * @return
	 */
	public Role findById(Integer id);
	/**
	 * 查询所有角色信息
	 */
	public List<Role> listAll();	
	
	/**
	 * @param Role
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param strings
	 * @return
	 * 根据条件分页查询用户信息
	 */
	public List<Role> list(Role Role,Integer page,Integer pageSize,Direction direction,String...properties);
	/**
	 * @param Role
	 * @return
	 * 总记录数
	 */
	public Long getCount(Role Role);
	
	/**
	 * @param Role
	 * 添加或者修改用户信息
	 */
	public void save(Role Role);
	
	/**
	 * @param id
	 * 根据id删除用户
	 */
	public void delete(Integer id);
	

}
