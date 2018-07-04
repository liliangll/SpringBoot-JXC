package com.bdqn.service;

import java.util.List;

import com.bdqn.entity.GoodsType;

/**
 * 商品類別Service接口
 * @author Administrator
 *
 */
public interface GoodsTypeService {
	/**
	 * @param parentId
	 * @return
	 * 根据父节点查询所有子节点
	 */
	public List<GoodsType> findByParentId(int parentId);
	/**
	 * @param goodsType
	 * 添加或者修改商品类别信息
	 */
	public void save(GoodsType goodsType);
	
	/**
	 * @param id
	 * 根据id删除商品类别
	 */
	public void delete(Integer id);
	
	/**
	 * 根据id查询商品类别
	 */
	public GoodsType findById(Integer id);
	
	
}
