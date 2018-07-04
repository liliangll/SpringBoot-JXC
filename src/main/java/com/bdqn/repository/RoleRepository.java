package com.bdqn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.bdqn.entity.Role;

/**
 * @author asus
 *用户Repository接口
 */
public interface RoleRepository extends JpaRepository<Role,Integer>,JpaSpecificationExecutor<Role>{	
	/**
	 * 根据用户id查角色集合
	 * @param id
	 * @return
	 */
	@Query(value="SELECT r.* FROM t_user u,t_role r,t_user_role ur WHERE ur.`user_id`=u.`id` AND ur.`role_id`=r.`id` AND u.`id`=?1",nativeQuery=true)
	public List<Role> findByUserId(Integer id);
	
	/**
	 * 根据用户名查找用户实体
	 * @param userName
	 * @return
	 */
	@Query(value="select * from t_role where name=?1",nativeQuery=true)
	public Role findByRoleName(String roleName);
	
	
}
