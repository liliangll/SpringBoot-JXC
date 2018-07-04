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

import com.bdqn.entity.Role;
import com.bdqn.repository.RoleRepository;
import com.bdqn.service.RoleService;
import com.bdqn.util.StringUtil;

/**
 * 角色Service实现类
 * @author Administrator
 *
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService{

	@Resource
	private RoleRepository roleRepository;
	
	@Override
	public List<Role> findByUserId(Integer id) {
		return roleRepository.findByUserId(id);
	}

	@Override
	public Role findById(Integer id) {
		return roleRepository.findOne(id);
	}

	@Override
	public List<Role> listAll() {
		// TODO Auto-generated method stub
		return roleRepository.findAll();
	}

	
	/* (non-Javadoc)
	 *  String...
	 * 可变的参数类型，也称为不定参数类型。英文缩写是varargus，
	 * 还原一下就是variable argument type。
	 * 通过它的名字可以很直接地看出来，这个方法在接收参数的时候，个数是不定的
	 * 其实这种定义就类似一个数据的定义，可以不用给它的长度加以限制，可以传入任意多个参数。
	 * 比用数据更灵活一些，不会出现一些数组越界等的异常。
	 * 
	 */
	@Override
	public List<Role> list(Role Role, Integer page, Integer pageSize, Direction direction, String... properties) {
		Pageable pageable=new PageRequest(page-1,pageSize,direction,properties);
		 Page<Role> pageRole = roleRepository.findAll(new Specification<Role>() {
			
			//root获取字段信息，criteriaBuilder用来封装一些条件
			@Override
			public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if (Role!=null) {
					if (StringUtil.isNotEmpty(Role.getName())) {
						predicate.getExpressions().add(cb.like(root.get("name"),"%"+Role.getName()+"%"));
					}
					predicate.getExpressions().add(cb.notEqual(root.get("id"), 1));
				}
				return predicate;
				}
			},pageable);
		return pageRole.getContent();
	}

	@Override
	public Long getCount(Role Role) {
		long count = roleRepository.count(new Specification<Role>() {			
			@Override
			public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if (Role!=null) {
					if (StringUtil.isNotEmpty(Role.getName())) {
						predicate.getExpressions().add(cb.like(root.get("name"),"%"+Role.getName()+"%"));
					}
					predicate.getExpressions().add(cb.notEqual(root.get("id"), 1));
				}
				return predicate;
			}
		});
		return count;
	}

	@Override
	public void save(Role Role) {
		roleRepository.save(Role);
		
	}
	@Override
	public void delete(Integer id) {
		roleRepository.delete(id);
		
	}

	@Override
	public Role findByRoleName(String roleName) {
		// TODO Auto-generated method stub
		return roleRepository.findByRoleName(roleName);
	}

	

}
