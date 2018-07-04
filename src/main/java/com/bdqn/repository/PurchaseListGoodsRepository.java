package com.bdqn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.bdqn.entity.PurchaseListGoods;

/**
 * 进货单商品Repository接口
 * 
 * @author Administrator
 *
 */
public interface PurchaseListGoodsRepository
		extends JpaRepository<PurchaseListGoods, Integer>, JpaSpecificationExecutor<PurchaseListGoods> {
	/**
	 * @param purchaseListId
	 * @return 根据进货单id查询所有进货单商品
	 */
	@Query(value = "SELECT * FROM t_purchase_list_goods WHERE purchase_list_id=?1", nativeQuery = true)
	public List<PurchaseListGoods> ListByPurchaseListId(Integer purchaseListId);

	/**
	 * @param purchaseListId
	 * @return 根据进货单id删除所有进货单商品
	 */
	@Query(value = "delete from t_purchase_list_goods WHERE purchase_list_id=?1", nativeQuery = true)
	@Modifying
	public void deleteByPurchaseListId(Integer purchaseListId);

}
