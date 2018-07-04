package com.bdqn.service;

import java.util.List;

import com.bdqn.entity.ReturnListGoods;

/**
 * @author asus
 *退货单商品Service接口
 */
public interface ReturnListGoodsService {
	/**
	 * @param 
	 * purchaseListId
	 * @return
	 * 根据退货单id查询所有退货单商品
	 */
	public List<ReturnListGoods> ListByReturnListId(Integer returnListId);
	/**
	 * @return
	 * 根据条件查询退货单商品
	 */
	public List<ReturnListGoods> list(ReturnListGoods returnListGoods);
}
