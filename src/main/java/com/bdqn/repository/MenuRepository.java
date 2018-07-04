package com.bdqn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bdqn.entity.Menu;


/**
 * @author asus
 *用户Repository接口
 */
public interface MenuRepository extends JpaRepository<Menu,Integer> {	
	
	
	/**
	 * @param roleId
	 * @return
	 * 根据角色id获取菜单集合 
	 */
	@Query(value="SELECT m.* FROM t_role r,t_role_menu rm,t_menu m WHERE rm.`role_id`=r.`id` AND rm.`menu_id`=m.`id` AND r.`id`=?1",nativeQuery=true)
	public List<Menu> findByRoleId(int roleId);
	/**
	 * 根据父节点以及用户角色id查询子节点
	 * @param id
	 * @return
	 */
	@Query(value="SELECT * FROM t_menu WHERE p_id=?1 AND id IN (SELECT menu_id FROM t_role_menu WHERE role_id=?2)",nativeQuery=true)
	public List<Menu> findByparentIdAndRoleId(int parentId,int roleId);

	
	/**
	 * @param parentId
	 * @return
	 * 根据父节点查询所有子节点
	 */
	@Query(value="select * from t_menu where p_id=?1",nativeQuery=true)
	public List<Menu> findByParentId(int parentId);

}
