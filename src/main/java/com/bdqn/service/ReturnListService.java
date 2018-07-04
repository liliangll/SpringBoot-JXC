package com.bdqn.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.bdqn.entity.ReturnList;
import com.bdqn.entity.ReturnListGoods;

/**
 * 退货单Service接口
 * @author Administrator
 *
 */
public interface ReturnListService {
	/**
	 * @return
	 * 获取当前最大退货单号
	 */
	public String getTodayMaxReturnNumber();
	
	/**
	 * @param returnList
	 * @param returnListGoodsList
	 * 添加退货单，以及所有退货单商品，以及修改商品成本价，
	 */
	public void save(ReturnList returnList,List<ReturnListGoods> returnListGoodsList);
	/**
	 * @param id
	 * @return
	 * 根据id查询实体
	 */
	public 	ReturnList findById(Integer id);
	/**
	 * @param returnList
	 * @param Direction
	 * @param prop
	 * @return
	 * 根据条件查询退货单信息
	 */
	public List<ReturnList> list(ReturnList returnList,Direction direction,String...properties);
	/**
	 * @param id
	 * 根据id删除退货单信息，包括退货单里的所有商品
	 */
	public void delete(Integer id);
	/**
	 * @param purchaseList
	 * 进货单的更新操作
	 */
	public void update(ReturnList returnList);
	
	
	
}
