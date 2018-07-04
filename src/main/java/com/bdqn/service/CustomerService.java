package com.bdqn.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.bdqn.entity.Customer;

/**
 * 客户Service接口
 * @author Administrator
 *
 */
public interface CustomerService {

	/**
	 * @param Customer
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param strings
	 * @return
	 * 根据条件分页查询客户信息
	 */
	public List<Customer> list(Customer customer,Integer page,Integer pageSize,Direction direction,String...properties);
	/**
	 * @param Customer
	 * @return
	 * 总记录数
	 */
	public Long getCount(Customer customer);
	
	/**
	 * @param Customer	
	 * 添加或者修改客户信息
	 */
	public void save(Customer customer);
	
	/**
	 * @param id
	 * 根据id删除客户
	 */
	public void delete(Integer id);
	
	/**
	 * 根据id查询客户
	 */
	public Customer findById(Integer id);
	/**
	 * @param name
	 * @return
	 * 根据名称查询供应商信息
	 */
	public List<Customer> findByName(String name);
	
	
}
