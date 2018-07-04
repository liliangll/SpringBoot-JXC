package com.bdqn.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.bdqn.entity.CustomerReturnList;
import com.bdqn.entity.CustomerReturnListGoods;

/**
 * 客户退货单Service接口
 * @author Administrator
 *
 */
public interface CustomerReturnListService {
	
	/**
	 * @param id
	 * @return
	 * 根据id查询实体
	 */
	public 	CustomerReturnList findById(Integer id);
	/**
	 * @param id
	 * 根据id删除客户退货单信息，包括客户退货单里的所有商品
	 */
	public void delete(Integer id);
	/**
	 * @return
	 * 获取当前最大客户退货单号
	 */
	public String getTodayMaxCustomerReturnNumber();
	
	/**
	 * @param customerReturnList
	 * @param customerReturnListGoodsList
	 * 添加客户退货单，以及所有客户退货单商品，以及修改商品成本价，库存数量 上次进价
	 */
	public void save(CustomerReturnList customerReturnList,List<CustomerReturnListGoods> customerReturnListGoodsList);
	
	
	/**
	 * @param customerReturnList
	 * @param Direction
	 * @param prop
	 * @return
	 * 根据条件查询客户退货单信息
	 */
	public List<CustomerReturnList> list(CustomerReturnList customerReturnList,Direction direction,String...properties);
	/**
	 * 
	 * 更新客户退货信息
	 */
	public void update(CustomerReturnList customerReturnList);
}
