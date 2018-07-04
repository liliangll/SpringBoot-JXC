package com.bdqn.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.bdqn.entity.PurchaseList;
import com.bdqn.entity.PurchaseListGoods;

/**
 * 进货单Service接口
 * @author Administrator
 *
 */
public interface PurchaseListService {
	
	/**
	 * @param id
	 * @return
	 * 根据id查询实体
	 */
	public 	PurchaseList findById(Integer id);
	/**
	 * @param id
	 * 根据id删除进货单信息，包括进货单里的所有商品
	 */
	public void delete(Integer id);
	/**
	 * @return
	 * 获取当前最大进货单号
	 */
	public String getTodayMaxPurchaseNumber();
	
	/**
	 * @param purchaseList
	 * @param purchaseListGoodsList
	 * 添加进货单，以及所有进货单商品，以及修改商品成本价，库存数量 上次进价
	 */
	public void save(PurchaseList purchaseList,List<PurchaseListGoods> purchaseListGoodsList);
	
	
	/**
	 * @param purchaseList
	 * @param Direction
	 * @param prop
	 * @return
	 * 根据条件查询进货单信息
	 */
	public List<PurchaseList> list(PurchaseList purchaseList,Direction direction,String...properties);
	
	/**
	 * @param purchaseList
	 * 进货单的更新操作
	 */
	public void update(PurchaseList purchaseList);
}
