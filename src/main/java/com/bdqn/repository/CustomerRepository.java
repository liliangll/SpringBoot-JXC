package com.bdqn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.bdqn.entity.Customer;

/**
 * @author asus
 *客户Repository接口
 */
public interface CustomerRepository extends JpaRepository<Customer,Integer>,JpaSpecificationExecutor<Customer>{	
	
	/**
	 * @param name
	 * @return
	 * 根据名称查询客户信息
	 */ 
	@Query(value="select * from t_customer where name like ?1",nativeQuery=true)
	public List<Customer> findByName(String name);
}
