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
import com.bdqn.entity.SaleList;
import com.bdqn.entity.SaleListGoods;
import com.bdqn.repository.GoodsRepository;
import com.bdqn.repository.GoodsTypeRepository;
import com.bdqn.repository.SaleListGoodsRepository;
import com.bdqn.repository.SaleListRepository;
import com.bdqn.service.SaleListService;
import com.bdqn.util.MathUtil;
import com.bdqn.util.StringUtil;

/**
 * @author asus
 *销售单实现类
 */
@Service("saleListService")
public class SaleListServiceImpl implements SaleListService {
	@Resource
	private SaleListRepository saleListRepository;	
	@Resource
	private GoodsTypeRepository goodsTypeRepository;
	@Resource
	private GoodsRepository goodsRepository;
	@Resource
	private SaleListGoodsRepository saleListGoodsRepository;

	@Override
	public String getTodayMaxSaleNumber() {
		// TODO Auto-generated method stub
		return saleListRepository.getTodayMaxSaleNumber();
	}

	@Transactional
	public void save(SaleList saleList, List<SaleListGoods> saleListGoodsList) {
		// TODO Auto-generated method stub
		for (SaleListGoods saleListGoods : saleListGoodsList) {
			saleListGoods.setType(goodsTypeRepository.findOne(saleListGoods.getTypeId()));//设置类别
			saleListGoods.setSaleList(saleList);//设置进货单
			saleListGoodsRepository.save(saleListGoods);
			//修改商品库存，成本价以及上次进价
			Goods goods=goodsRepository.findOne(saleListGoods.getGoodsId());
			float sveSalePrice=(goods.getPurchasingPrice()*goods.getInventoryQuantity()+saleListGoods.getNum())/(goods.getInventoryQuantity()+saleListGoods.getNum());			
			goods.setPurchasingPrice(MathUtil.format2Bit(sveSalePrice));
			goods.setInventoryQuantity((goods.getInventoryQuantity()+saleListGoods.getNum()));
			goods.setLastPurchasingPrice(saleListGoods.getPrice());
			goods.setState(2);
			goodsRepository.save(goods);
		}
		saleListRepository.save(saleList);//保存进货单
	}

	@Override
	public List<SaleList> list(SaleList saleList, Direction direction, String... properties) {
		
		 return  saleListRepository.findAll(new Specification<SaleList>() {
			
			//root获取字段信息，criteriaBuilder用来封装一些条件
			@Override
			public Predicate toPredicate(Root<SaleList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if (saleList!=null) {
					if (StringUtil.isNotEmpty(saleList.getSaleNumber())) {
						predicate.getExpressions().add(cb.like(root.get("saleNumber"),"%"+saleList.getSaleNumber().trim()+"%"));
					}
					if (saleList.getCustomer()!=null&&saleList.getCustomer().getId()!=null) {
						predicate.getExpressions().add(cb.equal(root.get("customer").get("id"),saleList.getCustomer().getId()));
					}				
					if (saleList.getState()!=null) {
						predicate.getExpressions().add(cb.equal(root.get("state"), saleList.getState()));
					}
					if (saleList.getbSaleDate()!=null) {
						predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("saleDate"),saleList.getbSaleDate()));
					}
					if (saleList.geteSaleDate()!=null) {
						predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("saleDate"),saleList.geteSaleDate()));
					}
				}
				return predicate;
				}
			},new Sort(direction,properties));
	}

	@Override
	public SaleList findById(Integer id) {
		// TODO Auto-generated method stub
		return saleListRepository.findOne(id);
	}

	@Transactional
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		saleListGoodsRepository.deleteBySaleListId(id);
		saleListRepository.delete(id);
	}

	@Override
	public void update(SaleList saleList) {
		// TODO Auto-generated method stub
		saleListRepository.save(saleList);
	}

	@Override
	public List<Object> countSaleByDay(String begin, String end) {
		return saleListRepository.countSaleByDay(begin, end);
	}

	@Override
	public List<Object> countSaleByMonth(String begin, String end) {
		return saleListRepository.countSaleByMonth(begin, end);
	}
	
}
