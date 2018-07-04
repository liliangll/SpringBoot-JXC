package com.bdqn.service.impl;



import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bdqn.entity.Supplier;
import com.bdqn.repository.SupplierRepository;
import com.bdqn.service.SupplierService;
import com.bdqn.util.StringUtil;

/**
 * 供应商Service实现类
 * @author Administrator
 *
 */
@Service("supplierService")
public class SupplierServiceImpl implements SupplierService{

	@Resource
	private SupplierRepository supplierRepository;


	/* (non-Javadoc)
	 * @see com.bdqn.service.SupplierService#list(com.bdqn.entity.Supplier, java.lang.Integer, java.lang.Integer, org.springframework.data.domain.Sort.Direction, java.lang.String[])
	 * 根据条件分页查询供应商信息
	 */
	@Override
	public List<Supplier> list(Supplier supplier, Integer page, Integer pageSize, Direction direction, String... properties) {
		Pageable pageable=new PageRequest(page-1,pageSize,direction,properties);
		 Page<Supplier> pageSupplier = supplierRepository.findAll(new Specification<Supplier>() {
			
			//root获取字段信息，criteriaBuilder用来封装一些条件
			@Override
			public Predicate toPredicate(Root<Supplier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if (supplier!=null) {
					if (StringUtil.isNotEmpty(supplier.getName())) {
						predicate.getExpressions().add(cb.like(root.get("name"),"%"+supplier.getName()+"%"));
					}
					predicate.getExpressions().add(cb.notEqual(root.get("id"), 1));
				}
				return predicate;
				}
			},pageable);
		return pageSupplier.getContent();
	}

	/* (non-Javadoc)
	 * @see com.bdqn.service.SupplierService#getCount(com.bdqn.entity.Supplier)
	 * 获取总记录数
	 */
	@Override
	public Long getCount(Supplier supplier) {
			long count = supplierRepository.count(new Specification<Supplier>() {			
			@Override
			public Predicate toPredicate(Root<Supplier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if (supplier!=null) {
					if (StringUtil.isNotEmpty(supplier.getName())) {
						predicate.getExpressions().add(cb.like(root.get("name"),"%"+supplier.getName()+"%"));
					}
					predicate.getExpressions().add(cb.notEqual(root.get("id"), 1));
				}
				return predicate;
			}
		});
		return count;
	}
	@Override
	public void save(Supplier supplier) {
		supplierRepository.save(supplier);
		
	}
	@Override
	public void delete(Integer id) {
		supplierRepository.delete(id);
		
	}
	@Override
	public Supplier findById(Integer id) {
		// TODO Auto-generated method stub
		return supplierRepository.findOne(id);
	}

	@Override
	public List<Supplier> findByName(String name) {
		// TODO Auto-generated method stub
		return supplierRepository.findByName(name);
	}

	
	


}
