package com.bdqn.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bdqn.entity.OverflowListGoods;
import com.bdqn.repository.OverflowListGoodsRepository;
import com.bdqn.service.OverflowListGoodsService;

/**
 * 商品报溢单商品Service实现类
 * @author Administrator
 *
 */
@Service("overflowListGoodsService")
public class OverflowListGoodsServiceImpl implements OverflowListGoodsService{

	@Resource
	private OverflowListGoodsRepository overflowListGoodsRepository;

	@Override
	public List<OverflowListGoods> listByOverflowListId(Integer overflowListId) {
		return overflowListGoodsRepository.listByOverflowListId(overflowListId);
	}

	
	
}
