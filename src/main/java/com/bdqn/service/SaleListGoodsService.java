package com.bdqn.service;

import java.util.List;

import com.bdqn.entity.SaleListGoods;

/**
 * @author asus
 *销售单商品Service接口
 */
public interface SaleListGoodsService {
	/**
	 * @param 
	 * purchaseListId
	 * @return
	 * 根据销售单id查询所有销售单商品
	 */
	public List<SaleListGoods> ListBySaleListId(Integer purchaseListId);
	
	/**
	 * @param goodsId
	 * @return
	 * 统计某个商品的销售总数
	 */
	public Integer getTotalByGoodsId(Integer goodsId);
	/**
	 * @param purchaseListGoods
	 * @return
	 * 根据条件查询销售单商品
	 */
	public  List<SaleListGoods>  list(SaleListGoods saleListGoods);
}
