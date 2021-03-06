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

import com.bdqn.entity.Goods;
import com.bdqn.entity.ReturnList;
import com.bdqn.entity.ReturnListGoods;
import com.bdqn.repository.GoodsRepository;
import com.bdqn.repository.GoodsTypeRepository;
import com.bdqn.repository.ReturnListGoodsRepository;
import com.bdqn.repository.ReturnListRepository;
import com.bdqn.service.ReturnListService;
import com.bdqn.util.StringUtil;


/**
 * @author asus
 *退货单实现类
 */
@Service("returnListService")
public class ReturnListServiceImpl implements ReturnListService {
	@Resource
	private ReturnListRepository returnListRepository;	
	@Resource
	private GoodsTypeRepository goodsTypeRepository;
	@Resource
	private GoodsRepository goodsRepository;
	@Resource
	private ReturnListGoodsRepository returnListGoodsRepository;

	@Override
	public String getTodayMaxReturnNumber() {
		// TODO Auto-generated method stub
		return returnListRepository.getTodayMaxReturnNumber();
	}

	@Transactional
	public void save(ReturnList returnList, List<ReturnListGoods> returnListGoodsList) {
		// TODO Auto-generated method stub
		for (ReturnListGoods returnListGoods : returnListGoodsList) {
			returnListGoods.setType(goodsTypeRepository.findOne(returnListGoods.getTypeId()));//设置类别
			returnListGoods.setReturnList(returnList);//设置退货单
			returnListGoodsRepository.save(returnListGoods);
			//修改商品库存，成本价以及上次进价
			Goods goods=goodsRepository.findOne(returnListGoods.getGoodsId());
			
			goods.setInventoryQuantity((goods.getInventoryQuantity()-returnListGoods.getNum()));
			goods.setLastPurchasingPrice(returnListGoods.getPrice());
			goods.setState(2);
			goodsRepository.save(goods);
		}
		returnListRepository.save(returnList);//保存退货单
	}

	@Override
	public ReturnList findById(Integer id) {
		// TODO Auto-generated method stub
		return returnListRepository.findOne(id);
	}

	@Override
	public List<ReturnList> list(ReturnList returnList, Direction direction, String... properties) {
		return  returnListRepository.findAll(new Specification<ReturnList>() {
			
			//root获取字段信息，criteriaBuilder用来封装一些条件
			@Override
			public Predicate toPredicate(Root<ReturnList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if (returnList!=null) {
					if (StringUtil.isNotEmpty(returnList.getReturnNumber())) {
						predicate.getExpressions().add(cb.like(root.get("returnNumber"),"%"+returnList.getReturnNumber().trim()+"%"));
					}
					if (returnList.getSupplier()!=null&&returnList.getSupplier().getId()!=null) {
						predicate.getExpressions().add(cb.equal(root.get("supplier").get("id"),returnList.getSupplier().getId()));
					}				
					if (returnList.getState()!=null) {
						predicate.getExpressions().add(cb.equal(root.get("state"), returnList.getState()));
					}
					if (returnList.getbReturnDate()!=null) {
						predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("returnDate"),returnList.getbReturnDate()));
					}
					if (returnList.geteReturnDate()!=null) {
						predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("returnDate"),returnList.geteReturnDate()));
					}
				}
				return predicate;
				}
			},new Sort(direction,properties));
	}

	@Transactional
	public void delete(Integer id) {
		returnListGoodsRepository.deleteByReturnListId(id);
		returnListRepository.delete(id);
	}

	@Override
	public void update(ReturnList returnList) {
		// TODO Auto-generated method stub
		returnListRepository.save(returnList);
	}
	
}
