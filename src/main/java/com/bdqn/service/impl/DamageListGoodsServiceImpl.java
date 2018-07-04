package com.bdqn.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bdqn.entity.DamageListGoods;
import com.bdqn.repository.DamageListGoodsRepository;
import com.bdqn.service.DamageListGoodsService;

/**
 * 商品报损单商品Service实现类
 * @author Administrator
 *
 */
@Service("damageListGoodsService")
public class DamageListGoodsServiceImpl implements DamageListGoodsService{

	@Resource
	private DamageListGoodsRepository damageListGoodsRepository;

	@Override
	public List<DamageListGoods> listByDamageListId(Integer damageListId) {
		return damageListGoodsRepository.listByDamageListId(damageListId);
	}

	
	
}
