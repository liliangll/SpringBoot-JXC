package com.bdqn.service;

import java.util.List;

import com.bdqn.entity.CustomerReturnListGoods;

/**
 * @author asus
 *客户退货单商品Service接口
 */
public interface CustomerReturnListGoodsService {
	/**
	 * @param 
	 * purchaseListId
	 * @return
	 * 根据客户退货单id查询所有客户退货单商品
	 */
	public List<CustomerReturnListGoods> ListByCustomerReturnListId(Integer purchaseListId);
	
	/**
	 * @param goodsId
	 * @return
	 * 统计某个商品的退货总数
	 */
	public Integer getTotalByGoodsId(Integer goodsId);
	/**
	 * @param purchaseListGoods
	 * @return
	 * 根据条件查询客户退货单商品
	 */
	public  List<CustomerReturnListGoods>  list(CustomerReturnListGoods customerReturnListGoods);
}
