/*package com.bdqn.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.bdqn.entity.Menu;
import com.bdqn.entity.Role;
import com.bdqn.entity.User;
import com.bdqn.repository.MenuRepository;
import com.bdqn.repository.RoleRepository;
import com.bdqn.repository.UserRepository;

*//**
 * 自定义Realm
 * @author Administrator
 *	Authorizing 授权
 *//*
public class MyRealm extends AuthorizingRealm{

	@Resource
	private UserRepository userRepository;
	
	@Resource
	private RoleRepository  roleRepository;
	
	@Resource
	private MenuRepository  menuRepository;
	
	*//**
	 * 授权
	 * 把当前用户对应的角色和对应的用户菜单全部设置进去，属于认证通过，否则报错
	 *//*
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String userName=(String)SecurityUtils.getSubject().getPrincipal();//获取当前用户名
		User user = userRepository.findByUserName(userName);
		SimpleAuthorizationInfo info =new SimpleAuthorizationInfo();
		List<Role> roleList = roleRepository.findByUserId(user.getId());
		Set<String> roles=new HashSet<String>();
		for (Role role : roleList) {
			roles.add(role.getName());
			List<Menu> menuList=menuRepository.findByRoleId(role.getId());
			for (Menu menu : menuList) {
				info.addStringPermission(menu.getName());
			}
		}
		info.setRoles(roles);
		return info;
	}

	*//**
	 * 身份权限认证
	 *//*
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//即可获得对象 pricipal是一个Object，获得username属性的实体对象
		String userName=(String) token.getPrincipal();
		User user=userRepository.findByUserName(userName);
		if(user!=null){
			AuthenticationInfo authcInfo=new SimpleAuthenticationInfo(user.getUserName(),user.getPassword(),"xxx");
			return authcInfo;
		}else{
			return null;			
		}
	}

}
*/