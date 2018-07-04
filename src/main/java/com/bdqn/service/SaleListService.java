package com.bdqn.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.bdqn.entity.SaleList;
import com.bdqn.entity.SaleListGoods;

/**
 * 销售单Service接口
 * @author Administrator
 *
 */
public interface SaleListService {
	
	/**
	 * @param id
	 * @return
	 * 根据id查询实体
	 */
	public 	SaleList findById(Integer id);
	/**
	 * @param id
	 * 根据id删除销售单信息，包括销售单里的所有商品
	 */
	public void delete(Integer id);
	/**
	 * @return
	 * 获取当前最大销售单号
	 */
	public String getTodayMaxSaleNumber();
	
	/**
	 * @param saleList
	 * @param saleListGoodsList
	 * 添加销售单，以及所有销售单商品，以及修改商品成本价，库存数量 上次进价
	 */
	public void save(SaleList saleList,List<SaleListGoods> saleListGoodsList);
	
	
	/**
	 * @param saleList
	 * @param Direction
	 * @param prop
	 * @return
	 * 根据条件查询销售单信息
	 */
	public List<SaleList> list(SaleList saleList,Direction direction,String...properties);
	
	/**
	 * 
	 * 更新销售信息
	 */
	public void update(SaleList saleList);
	/**
	 * 按天统计某个日期范围内的销售信息
	 * @param begin
	 * @param end
	 * @return
	 */
	public List<Object> countSaleByDay(String begin,String end);
	
	/**
	 * 按月统计某个日期范围内的销售信息
	 * @param begin
	 * @param end
	 * @return
	 */
	public List<Object> countSaleByMonth(String begin,String end);
}
