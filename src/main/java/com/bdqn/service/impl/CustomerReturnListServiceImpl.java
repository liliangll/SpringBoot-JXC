 package com.bdqn.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bdqn.entity.CustomerReturnList;
import com.bdqn.entity.CustomerReturnListGoods;
import com.bdqn.entity.Goods;
import com.bdqn.repository.CustomerReturnListGoodsRepository;
import com.bdqn.repository.CustomerReturnListRepository;
import com.bdqn.repository.GoodsRepository;
import com.bdqn.repository.GoodsTypeRepository;
import com.bdqn.service.CustomerReturnListService;
import com.bdqn.util.StringUtil;

/**
 * @author asus
 *客户退货单实现类
 */
@Service("customerReturnListService")
public class CustomerReturnListServiceImpl implements CustomerReturnListService {
	@Resource
	private CustomerReturnListRepository customerReturnListRepository;	
	@Resource
	private GoodsTypeRepository goodsTypeRepository;
	@Resource
	private GoodsRepository goodsRepository;
	@Resource
	private CustomerReturnListGoodsRepository customerReturnListGoodsRepository;

	@Override
	public String getTodayMaxCustomerReturnNumber() {
		// TODO Auto-generated method stub
		return customerReturnListRepository.getTodayMaxCustomerReturnNumber();
	}

	@Transactional
	public void save(CustomerReturnList customerReturnList, List<CustomerReturnListGoods> customerReturnListGoodsList) {
		// TODO Auto-generated method stub
		for (CustomerReturnListGoods customerReturnListGoods : customerReturnListGoodsList) {
			customerReturnListGoods.setType(goodsTypeRepository.findOne(customerReturnListGoods.getTypeId()));//设置类别
			customerReturnListGoods.setCustomerReturnList(customerReturnList);//设置进货单
			customerReturnListGoodsRepository.save(customerReturnListGoods);
			//修改商品库存，成本价以及上次进价
			Goods goods=goodsRepository.findOne(customerReturnListGoods.getGoodsId());			
			goods.setInventoryQuantity((goods.getInventoryQuantity()+customerReturnListGoods.getNum()));
			goods.setLastPurchasingPrice(customerReturnListGoods.getPrice());
			goods.setState(2);
			goodsRepository.save(goods);
		}
		customerReturnListRepository.save(customerReturnList);//保存进货单
	}

	@Override
	public List<CustomerReturnList> list(CustomerReturnList customerReturnList, Direction direction, String... properties) {
		
		 return  customerReturnListRepository.findAll(new Specification<CustomerReturnList>() {
			
			//root获取字段信息，criteriaBuilder用来封装一些条件
			@Override
			public Predicate toPredicate(Root<CustomerReturnList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if (customerReturnList!=null) {
					if (StringUtil.isNotEmpty(customerReturnList.getCustomerReturnNumber())) {
						predicate.getExpressions().add(cb.like(root.get("customerReturnNumber"),"%"+customerReturnList.getCustomerReturnNumber().trim()+"%"));
					}
					if (customerReturnList.getCustomer()!=null&&customerReturnList.getCustomer().getId()!=null) {
						predicate.getExpressions().add(cb.equal(root.get("customer").get("id"),customerReturnList.getCustomer().getId()));
					}				
					if (customerReturnList.getState()!=null) {
						predicate.getExpressions().add(cb.equal(root.get("state"), customerReturnList.getState()));
					}
					if (customerReturnList.getbCustomerReturnDate()!=null) {
						predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("customerReturnDate"),customerReturnList.getbCustomerReturnDate()));
					}
					if (customerReturnList.geteCustomerReturnDate()!=null) {
						predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("customerReturnDate"),customerReturnList.geteCustomerReturnDate()));
					}
				}
				return predicate;
				}
			},new Sort(direction,properties));
	}

	@Override
	public CustomerReturnList findById(Integer id) {
		// TODO Auto-generated method stub
		return customerReturnListRepository.findOne(id);
	}

	@Transactional
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		customerReturnListGoodsRepository.deleteByCustomerReturnListId(id);
		customerReturnListRepository.delete(id);
	}

	@Override
	public void update(CustomerReturnList customerReturnList) {
		// TODO Auto-generated method stub
		customerReturnListRepository.save(customerReturnList);
	}

}
