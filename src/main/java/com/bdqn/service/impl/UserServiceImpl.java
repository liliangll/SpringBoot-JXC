package com.bdqn.service.impl;



import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bdqn.entity.User;
import com.bdqn.repository.UserRepository;
import com.bdqn.service.UserService;
import com.bdqn.util.StringUtil;

/**
 * 用户Service实现类
 * @author Administrator
 *
 */
@Service("userService")
public class UserServiceImpl implements UserService{

	@Resource
	private UserRepository userRepository;
	
	@Cacheable(value="userCache")
	@Override
	public User findByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}
	/* (non-Javadoc)
	 * @see com.bdqn.service.UserService#list(com.bdqn.entity.User, java.lang.Integer, java.lang.Integer, org.springframework.data.domain.Sort.Direction, java.lang.String[])
	 * 根据条件分页查询用户信息
	 */
	@Cacheable(value="userCache")
	@Override
	public List<User> list(User user, Integer page, Integer pageSize, Direction direction, String... properties) {
		Pageable pageable=new PageRequest(page-1,pageSize,direction,properties);
		 Page<User> pageUser = userRepository.findAll(new Specification<User>() {
			
			//root获取字段信息，criteriaBuilder用来封装一些条件
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if (user!=null) {
					if (StringUtil.isNotEmpty(user.getUserName())) {
						predicate.getExpressions().add(cb.like(root.get("userName"),"%"+user.getUserName()+"%"));
					}
					predicate.getExpressions().add(cb.notEqual(root.get("id"), 1));
				}
				return predicate;
				}
			},pageable);
		return pageUser.getContent();
	}

	/* (non-Javadoc)
	 * @see com.bdqn.service.UserService#getCount(com.bdqn.entity.User)
	 * 获取总记录数
	 */
	@CacheEvict(value="userCache")
	@Override
	public Long getCount(User user) {
			long count = userRepository.count(new Specification<User>() {			
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if (user!=null) {
					if (StringUtil.isNotEmpty(user.getUserName())) {
						predicate.getExpressions().add(cb.like(root.get("userName"),"%"+user.getUserName()+"%"));
					}
					predicate.getExpressions().add(cb.notEqual(root.get("id"), 1));
				}
				return predicate;
			}
		});
		return count;
	}
	//allEntries 清空缓存所有属性 确保更新后缓存刷新
    @CacheEvict(value="userCache", allEntries=true)
	@Override
	public void save(User user) {
		userRepository.save(user);		
	}
    //allEntries 清空缓存所有属性 确保更新后缓存刷新
    @CacheEvict(value="userCache", allEntries=true)
	@Override
	public void delete(Integer id) {
		userRepository.delete(id);
		
	}
	@Cacheable(value="userCache") //缓存,这里没有指定key.
	@Override
	public User findById(Integer id) {
		// TODO Auto-generated method stub
		return userRepository.findOne(id);
	}

	
	


}
