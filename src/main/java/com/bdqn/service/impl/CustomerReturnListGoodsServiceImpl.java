package com.bdqn.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bdqn.entity.CustomerReturnListGoods;
import com.bdqn.repository.CustomerReturnListGoodsRepository;
import com.bdqn.service.CustomerReturnListGoodsService;
import com.bdqn.util.StringUtil;

/**
 * @author asus
 *客户退货单商品实现类
 */
@Service("customerReturnListGoodsService")
public class CustomerReturnListGoodsServiceImpl implements CustomerReturnListGoodsService {
	@Resource
	private CustomerReturnListGoodsRepository customerReturnListGoodsRepository;

	@Override
	public List<CustomerReturnListGoods> ListByCustomerReturnListId(Integer customerReturnListId) {
		// TODO Auto-generated method stub
		return customerReturnListGoodsRepository.ListByCustomerReturnListId(customerReturnListId);
	}

	@Override
	public Integer getTotalByGoodsId(Integer goodsId) {
		// TODO Auto-generated method stub
		return customerReturnListGoodsRepository.getTotalByGoodsId(goodsId)==null?0:customerReturnListGoodsRepository.getTotalByGoodsId(goodsId);
		
	}

	@Override
	public List<CustomerReturnListGoods> list(CustomerReturnListGoods customerReturnListGoods) {
		return customerReturnListGoodsRepository.findAll(new Specification<CustomerReturnListGoods>() {			
			@Override
			public Predicate toPredicate(Root<CustomerReturnListGoods> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if(customerReturnListGoods!=null){
					if(StringUtil.isNotEmpty(customerReturnListGoods.getName())){
						predicate.getExpressions().add(cb.like(root.get("name"), "%"+customerReturnListGoods.getName()+"%"));
					}
					if(customerReturnListGoods.getType()!=null && customerReturnListGoods.getType().getId()!=null && customerReturnListGoods.getType().getId()!=1){
						predicate.getExpressions().add(cb.equal(root.get("type").get("id"), customerReturnListGoods.getType().getId()));
					}
					if(StringUtil.isNotEmpty(customerReturnListGoods.getCodeOrName())){
						predicate.getExpressions().add(cb.or(cb.like(root.get("code"), "%"+customerReturnListGoods.getCodeOrName()+"%"), cb.like(root.get("name"), "%"+customerReturnListGoods.getCodeOrName()+"%")));
					}
				}
				return predicate;
			}
		});
	}
}
