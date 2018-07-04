package com.bdqn.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bdqn.entity.Log;
import com.bdqn.entity.Role;
import com.bdqn.entity.User;
import com.bdqn.entity.UserRole;
import com.bdqn.service.LogService;
import com.bdqn.service.RoleService;
import com.bdqn.service.UserRoleService;
import com.bdqn.service.UserService;
import com.bdqn.util.StringUtil;

/**
 * @author asus
 * 后台管理用户Controller
 */
@Controller
@RequestMapping("/admin/user")
public class UserAdminController {
	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;
	@Resource
	private UserRoleService userRoleService;
	@Resource
	private LogService logService;
	/**
	 * 比如说张三登录进去，只有前面几个模块，登录进去，身份认证通过了，依然可以请求后台地址，这样的话不安全，
	 * 那么我们在方法上进一步的加上菜单权限的认证
	 * 用户管理2出错
	 * @param user
	 * @param page
	 * @param rows
	 * @return 分頁查詢用戶信息
	 */

	@RequestMapping("/list")
	@ResponseBody
	@RequiresPermissions(value="用户管理")
	public Map<String,Object> list(User user,@RequestParam(value="page",required=false)Integer page
			,@RequestParam(value="rows",required=false)Integer rows)throws Exception{
	{
		Map<String,Object> resultMap=new HashMap<>();
		List<User> userList = userService.list(user, page, rows, Direction.ASC, "id");//降序
		for (User u:userList) {
			 List<Role> roleList = roleService.findByUserId(u.getId());
			 StringBuffer sb=new StringBuffer();
			 for(Role r:roleList)
			 {
				 sb.append(","+r.getName());
			 }
			 u.setRoles(sb.toString().replaceFirst(",", ""));//把第一个,替换为空格			
		}
		Long totalCouns = userService.getCount(user);		
		resultMap.put("rows", userList);
		resultMap.put("total", totalCouns);
		logService.save(new Log(Log.SEARCH_ACTION,"查询用户信息"));
		return resultMap;
		}
	}
	
	/**
	 * @return
	 * @throws Exception
	 * 添加或者修改用户信息
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Map<String,Object> save(User user)throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		//判断添加用户名是否存在，存在提示信息
		if (user.getId()==null) {
			if (userService.findByUserName(user.getUserName())!=null) {
				map.put("success", false);
				map.put("errorInfo", "用户名已经存在了");
				return 	map;
			}
		}
		if (user.getId()!=null) {
			logService.save(new Log(Log.UPDATE_ACTION,"更新用户信息"+user));
		}else {
			logService.save(new Log(Log.ADD_ACTION,"添加用户信息"+user));
		}
		userService.save(user);
		map.put("success", true);
		return map;	
	}
	/**
	 * 根据id删除用户信息
	 * @param userId
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String,Object> delete(Integer id)
	{
		logService.save(new Log(Log.DELETE_ACTION,"删除用户信息"+userService.findById(id)));
		Map<String,Object> map=new HashMap<String,Object>();
		userRoleService.deleteByUserId(id);
		userService.delete(id);
		map.put("success", true);
		return map;
	}
	
	/**
	 * @param roleIds
	 * @param userId
	 * @return
	 * @throws Exception
	 * 保存用戶角色設置
	 */
	@RequestMapping("/saveRoleSet")
	@ResponseBody
	public	Map<String,Object> saveRoleSet(String roleIds,Integer userId) throws Exception{
		Map<String,Object> resultMap=new HashMap<String,Object>();
		userRoleService.deleteByUserId(userId);
		if (StringUtil.isNotEmpty(roleIds)) {
			String roleIdStr[]=roleIds.split(",");
			for (int i = 0; i < roleIdStr.length; i++) {
				UserRole userRole=new UserRole();
				userRole.setUser(userService.findById(userId));
				userRole.setRole(roleService.findById(Integer.parseInt(roleIdStr[i])));
				userRoleService.save(userRole);
				
			}
		}
		resultMap.put("success", true);
		logService.save(new Log(Log.UPDATE_ACTION,"保存用户角色信息"));
		return resultMap;
	}
	/**
	 * @param newPassword
	 * @param session
	 * @return
	 * 修改密码
	 */
	@RequestMapping("/modifyPassword")
	@RequiresPermissions(value="修改密码")
	@ResponseBody
	public Map<String,Object> modifyPassword(String newPassword,HttpSession session)
	{
		Map<String,Object> map=new HashMap<String,Object>();
		User currentUser=(User) session.getAttribute("currentUser");
		User user=userService.findById(currentUser.getId());
		user.setPassword(newPassword);
		userService.save(user);
		map.put("success", true);
		logService.save(new Log(Log.UPDATE_ACTION,"修改密码"));
		return map;
	}
	
	/**
	 * @param session
	 * @return
	 * @throws Exception
	 * 安全退出
	 */
	@GetMapping("/logout")
	@RequiresPermissions(value="安全退出")
	public String logout(HttpSession session)throws Exception{
		logService.save(new Log(Log.LOGOUT_ACTION,"用户注销"));
		SecurityUtils.getSubject().logout();//清空用户信息
		return "redirect:/login.html";
	}
	
}
