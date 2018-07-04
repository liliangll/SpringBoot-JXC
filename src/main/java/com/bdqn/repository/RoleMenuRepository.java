package com.bdqn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.bdqn.entity.RoleMenu;

/**
 * 角色菜单关联Repository接口
 * @author Administrator
 *
 */
public interface RoleMenuRepository extends JpaRepository<RoleMenu, Integer>,JpaSpecificationExecutor<RoleMenu>{

	
	/**
	 * 根据角色id删除所有关联信息
	 * @param userId
	 * 所谓本地查询，就是使用原生的sql语句（根据数据库的不同，在sql的语法或结构方面可能有所区别）进行查询数据库的操作
	 *因为指定了nativeQuery = true，即使用原生的sql语句查询。使用java对象'T_role_menu'作为表名来查自然是不对的。
	 *只需将T_role_menu替换为表名t_role_menu。
	 */
	@Query(value="delete from t_role_menu where role_id=?1",nativeQuery=true)
	@Modifying
	public void deleteByRoleId(Integer roleId);
}
