package com.bdqn.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bdqn.entity.GoodsUnit;
import com.bdqn.repository.GoodsUnitRepository;
import com.bdqn.service.GoodsUnitService;

/**
 * 商品单位Service实现类
 * @author Administrator
 *
 */
@Service("goodsUnitService")
public class GoodsUnitServiceImpl implements GoodsUnitService{

	@Resource
	private GoodsUnitRepository goodsUnitRepository;

	@Override
	public List<GoodsUnit> listAll() {
		// TODO Auto-generated method stub
		return goodsUnitRepository.findAll();
	}

	@Override
	public void save(GoodsUnit goodsUnit) {
		goodsUnitRepository.save(goodsUnit);
	}

	@Override
	public void delete(Integer id) {
		goodsUnitRepository.delete(id);
	}

	@Override
	public GoodsUnit findById(Integer id) {
		return goodsUnitRepository.findOne(id);
	}

	




}
