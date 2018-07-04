package com.bdqn.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bdqn.entity.PurchaseListGoods;
import com.bdqn.repository.PurchaseListGoodsRepository;
import com.bdqn.service.PurchaseListGoodsService;
import com.bdqn.util.StringUtil;

/**
 * @author asus
 *进货单商品实现类
 */
@Service("purchaseListGoodsService")
public class PurchaseListGoodsServiceImpl implements PurchaseListGoodsService {
	@Resource
	private PurchaseListGoodsRepository purchaseListGoodsRepository;

	@Override
	public List<PurchaseListGoods> ListByPurchaseListId(Integer purchaseListId) {
		// TODO Auto-generated method stub
		return purchaseListGoodsRepository.ListByPurchaseListId(purchaseListId);
	}

	@Override
	public List<PurchaseListGoods> list(PurchaseListGoods purchaseListGoods) {
	
		return purchaseListGoodsRepository.findAll(new Specification<PurchaseListGoods>() {
			
			@Override
			public Predicate toPredicate(Root<PurchaseListGoods> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if(purchaseListGoods!=null){
					if(StringUtil.isNotEmpty(purchaseListGoods.getName())){
						predicate.getExpressions().add(cb.like(root.get("name"), "%"+purchaseListGoods.getName()+"%"));
					}
					if(purchaseListGoods.getType()!=null && purchaseListGoods.getType().getId()!=null && purchaseListGoods.getType().getId()!=1){
						predicate.getExpressions().add(cb.equal(root.get("type").get("id"), purchaseListGoods.getType().getId()));
					}
					if(StringUtil.isNotEmpty(purchaseListGoods.getCodeOrName())){
						predicate.getExpressions().add(cb.or(cb.like(root.get("code"), "%"+purchaseListGoods.getCodeOrName()+"%"), cb.like(root.get("name"), "%"+purchaseListGoods.getCodeOrName()+"%")));
					}
				}
				return predicate;
			}
		});
	}
}
