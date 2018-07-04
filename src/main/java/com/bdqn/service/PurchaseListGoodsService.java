package com.bdqn.service;

import java.util.List;

import com.bdqn.entity.PurchaseListGoods;

/**
 * @author asus
 *进货单商品Service接口
 */
public interface PurchaseListGoodsService {
	/**
	 * @param 
	 * purchaseListId
	 * @return
	 * 根据进货单id查询所有进货单商品
	 */
	public List<PurchaseListGoods> ListByPurchaseListId(Integer purchaseListId);
	
	/**
	 * @param purchaseListGoods
	 * @return
	 * 根据条件查询进货单商品
	 */
	public  List<PurchaseListGoods>  list(PurchaseListGoods purchaseListGoods);
}
