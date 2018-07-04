package com.bdqn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.bdqn.entity.ReturnList;

/**
 * 退货单Repository接口
 * @author Administrator
 *
 */
public interface ReturnListRepository extends JpaRepository<ReturnList, Integer>,JpaSpecificationExecutor<ReturnList>{
	
	/**
	 * @return
	 * 获取当前最大退货单号
	 */
	@Query(value="SELECT MAX(return_number) FROM t_return_list WHERE TO_DAYS(return_date)=TO_DAYS(NOW())",nativeQuery=true)
	public String getTodayMaxReturnNumber();
}
