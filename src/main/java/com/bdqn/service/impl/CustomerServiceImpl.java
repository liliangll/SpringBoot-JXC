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

import com.bdqn.entity.Customer;
import com.bdqn.repository.CustomerRepository;
import com.bdqn.service.CustomerService;
import com.bdqn.util.StringUtil;

/**
 * 客户Service实现类
 * @author Administrator
 *
 */
@Service("customerService")
public class CustomerServiceImpl implements CustomerService{

	@Resource
	private CustomerRepository customerRepository;


	/* (non-Javadoc)
	 * @see com.bdqn.service.CustomerService#list(com.bdqn.entity.Customer, java.lang.Integer, java.lang.Integer, org.springframework.data.domain.Sort.Direction, java.lang.String[])
	 * 根据条件分页查询客户信息
	 */
	@Override
	public List<Customer> list(Customer customer, Integer page, Integer pageSize, Direction direction, String... properties) {
		Pageable pageable=new PageRequest(page-1,pageSize,direction,properties);
		 Page<Customer> pageCustomer = customerRepository.findAll(new Specification<Customer>() {
			
			//root获取字段信息，criteriaBuilder用来封装一些条件
			@Override
			public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if (customer!=null) {
					if (StringUtil.isNotEmpty(customer.getName())) {
						predicate.getExpressions().add(cb.like(root.get("name"),"%"+customer.getName()+"%"));
					}
					predicate.getExpressions().add(cb.notEqual(root.get("id"), 1));
				}
				return predicate;
				}
			},pageable);
		return pageCustomer.getContent();
	}

	/* (non-Javadoc)
	 * @see com.bdqn.service.CustomerService#getCount(com.bdqn.entity.Customer)
	 * 获取总记录数
	 */
	@Override
	public Long getCount(Customer customer) {
			long count = customerRepository.count(new Specification<Customer>() {			
			@Override
			public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if (customer!=null) {
					if (StringUtil.isNotEmpty(customer.getName())) {
						predicate.getExpressions().add(cb.like(root.get("name"),"%"+customer.getName()+"%"));
					}
					predicate.getExpressions().add(cb.notEqual(root.get("id"), 1));
				}
				return predicate;
			}
		});
		return count;
	}
	@Override
	public void save(Customer customer) {
		customerRepository.save(customer);
		
	}
	@Override
	public void delete(Integer id) {
		customerRepository.delete(id);
		
	}
	@Override
	public Customer findById(Integer id) {
		// TODO Auto-generated method stub
		return customerRepository.findOne(id);
	}  


	@Override
	public List<Customer> findByName(String name) {
		// TODO Auto-generated method stub
		return customerRepository.findByName(name);
	}

	


}
