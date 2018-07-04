package com.bdqn.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bdqn.entity.SaleListGoods;
import com.bdqn.repository.SaleListGoodsRepository;
import com.bdqn.service.SaleListGoodsService;
import com.bdqn.util.StringUtil;

/**
 * @author asus
 *销售单商品实现类
 */
@Service("saleListGoodsService")
public class SaleListGoodsServiceImpl implements SaleListGoodsService {
	@Resource
	private SaleListGoodsRepository saleListGoodsRepository;

	@Override
	public List<SaleListGoods> ListBySaleListId(Integer saleListId) {
		// TODO Auto-generated method stub
		return saleListGoodsRepository.ListBySaleListId(saleListId);
	}

	@Override
	public Integer getTotalByGoodsId(Integer goodsId) {
		// TODO Auto-generated method stub
		return saleListGoodsRepository.getTotalByGoodsId(goodsId)==null?0:saleListGoodsRepository.getTotalByGoodsId(goodsId);
	}

	@Override
	public List<SaleListGoods> list(SaleListGoods saleListGoods) {
		return saleListGoodsRepository.findAll(new Specification<SaleListGoods>() {			
			@Override
			public Predicate toPredicate(Root<SaleListGoods> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if(saleListGoods!=null){
					if(StringUtil.isNotEmpty(saleListGoods.getName())){
						predicate.getExpressions().add(cb.like(root.get("name"), "%"+saleListGoods.getName()+"%"));
					}
					if(saleListGoods.getType()!=null && saleListGoods.getType().getId()!=null && saleListGoods.getType().getId()!=1){
						predicate.getExpressions().add(cb.equal(root.get("type").get("id"), saleListGoods.getType().getId()));
					}
					if(StringUtil.isNotEmpty(saleListGoods.getCodeOrName())){
						predicate.getExpressions().add(cb.or(cb.like(root.get("code"), "%"+saleListGoods.getCodeOrName()+"%"), cb.like(root.get("name"), "%"+saleListGoods.getCodeOrName()+"%")));
					}
				}
				return predicate;
			}
		});
	}
}
