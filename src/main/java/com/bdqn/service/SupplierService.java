package com.bdqn.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.bdqn.entity.Supplier;

/**
 * 供应商Service接口
 * @author Administrator
 *
 */
public interface SupplierService {

	/**
	 * @param name
	 * @return
	 * 根据名称查询供应商信息
	 */
	public List<Supplier> findByName(String name);
	/**
	 * @param Supplier
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param strings
	 * @return
	 * 根据条件分页查询供应商信息
	 */
	public List<Supplier> list(Supplier supplier,Integer page,Integer pageSize,Direction direction,String...properties);
	/**
	 * @param Supplier
	 * @return
	 * 总记录数
	 */
	public Long getCount(Supplier supplier);	
	/**
	 * @param Supplier
	 * 添加或者修改供应商信息
	 */
	public void save(Supplier supplier);
	
	/**
	 * @param id
	 * 根据id删除供应商
	 */
	public void delete(Integer id);
	
	/**
	 * 根据id查询供应商
	 */
	public Supplier findById(Integer id);
	
	
}
