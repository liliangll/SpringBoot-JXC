package com.bdqn.service;

import java.util.List;

import com.bdqn.entity.GoodsUnit;

/**
 * 商品单位Service接口
 * @author Administrator
 *
 */
public interface GoodsUnitService {
	/**
	 * @return
	 * 查询所有商品单位
	 */
	public List<GoodsUnit> listAll();

	/**
	 * @param goodsUnit
	 * 添加或者修改商品类别信息
	 */
	public void save(GoodsUnit goodsUnit);
	
	/**
	 * @param id
	 * 根据id删除商品类别
	 */
	public void delete(Integer id);
	
	/**
	 * 根据id查询商品类别
	 */
	public GoodsUnit findById(Integer id);
	
	
}
