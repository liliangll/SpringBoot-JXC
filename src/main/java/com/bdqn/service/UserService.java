package com.bdqn.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.bdqn.entity.User;

/**
 * 用户Service接口
 * @author Administrator
 *
 */
public interface UserService {
	/**
	 * 根据用户名查找用户实体
	 * @param userName
	 * @return
	 */
	public User findByUserName(String userName);
	
	
	/**
	 * @param user
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param strings
	 * @return
	 * 根据条件分页查询用户信息
	 */
	public List<User> list(User user,Integer page,Integer pageSize,Direction direction,String...properties);
	/**
	 * @param user
	 * @return
	 * 总记录数
	 */
	public Long getCount(User user);
	
	/**
	 * @param user
	 * 添加或者修改用户信息
	 */
	public void save(User user);
	
	/**
	 * @param id
	 * 根据id删除用户
	 */
	public void delete(Integer id);
	
	/**
	 * 根据id查询用户
	 */
	public User findById(Integer id);
	
	
}
