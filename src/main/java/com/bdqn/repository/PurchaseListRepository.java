package com.bdqn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.bdqn.entity.PurchaseList;

/**
 * 进货单Repository接口2
 * @author Administrator
 *
 */
public interface PurchaseListRepository extends JpaRepository<PurchaseList, Integer>,JpaSpecificationExecutor<PurchaseList>{
	
	/**
	 * @return
	 * 获取当前最大进货单号
	 */
	@Query(value="SELECT MAX(purchase_number) FROM t_purchase_list WHERE TO_DAYS(purchase_date)=TO_DAYS(NOW())",nativeQuery=true)
	public String getTodayMaxPurchaseNumber();
}
