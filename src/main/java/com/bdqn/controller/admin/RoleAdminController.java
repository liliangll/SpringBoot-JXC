package com.bdqn.controller.admin;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bdqn.entity.Log;
import com.bdqn.entity.Menu;
import com.bdqn.entity.Role;
import com.bdqn.entity.RoleMenu;
import com.bdqn.service.LogService;
import com.bdqn.service.MenuService;
import com.bdqn.service.RoleMenuService;
import com.bdqn.service.RoleService;
import com.bdqn.service.UserRoleService;
import com.bdqn.util.StringUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @author asus
 * 后台管理角色Controller
 */
@RestController
@RequestMapping("/admin/role")
public class RoleAdminController {
	
	@Resource
	private RoleService roleService;
	
	@Resource
	private UserRoleService userRoleService;
	
	@Resource
	private RoleMenuService roleMenuService;
	
	@Resource
	private MenuService menuService;
	@Resource
	private LogService logService;

	/**
	 * @return
	 * @throws Exception
	 * 查詢所有角色
	 */
	@RequestMapping("/listAll")
	@RequiresPermissions(value="用户管理",logical=Logical.OR)
	public Map<String,Object> listAll() throws Exception{
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("rows", roleService.listAll());
		logService.save(new Log(Log.SEARCH_ACTION,"查询所有角色信息"));
		return resultMap;
	}
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
	@RequiresPermissions(value="角色管理")
	public Map<String,Object> list(Role role,@RequestParam(value="page",required=false)Integer page,@RequestParam(value="rows",required=false)Integer rows)throws Exception{
		Map<String,Object> resultMap=new HashMap<>();
		List<Role> roleList=roleService.list(role, page, rows, Direction.ASC, "id");
		Long total=roleService.getCount(role);
		resultMap.put("rows", roleList);
		resultMap.put("total", total);
		logService.save(new Log(Log.SEARCH_ACTION,"查询角色信息"));
		return resultMap;
	}
	
	/**
	 * 添加或者修改角色信息
	 * @param role
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	@RequiresPermissions(value="角色管理")
	public Map<String,Object> save(Role role)throws Exception{
		if (role.getId()!=null) {
			logService.save(new Log(Log.UPDATE_ACTION,"修改角色信息"));
		}else {
			logService.save(new Log(Log.ADD_ACTION,"添加角色信息"));
		}
		Map<String,Object> resultMap=new HashMap<>();
		if(role.getId()==null){
			if(roleService.findByRoleName(role.getName())!=null){
				resultMap.put("success", false);
				resultMap.put("errorInfo", "角色名已经存在！");
				return resultMap;
			}
		}
		roleService.save(role);
		resultMap.put("success", true);
		return resultMap;
	}
	
	/**
	 * 删除角色信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	@RequiresPermissions(value="角色管理")
	public Map<String,Object> delete(Integer id)throws Exception{
		logService.save(new Log(Log.DELETE_ACTION,"删除角色信息"+roleService.findById(id)));
		Map<String,Object> resultMap=new HashMap<>();
		roleMenuService.deleteByRoleId(id);
		userRoleService.deleteByRoleId(id);
		roleService.delete(id);
		resultMap.put("success", true);
		return resultMap;
	}
	
	/**
	 * @param parentId
	 * @param roleId
	 * @return
	 * 根据父节点获取所有复选框权限菜单
	 */
	@RequestMapping("/loadCheckMenuInfo")
	@RequiresPermissions(value="角色管理")
	public String loadCheckMenuInfo(Integer parentId,Integer roleId)
	{
		List<Menu> menuList = menuService.findByRoleId(roleId);
		List<Integer> menuIdList=new LinkedList<Integer>();
		for (Menu menu : menuList) {
			menuIdList.add(menu.getId());
		}
		return getAllCheckMenuByParentId(parentId, menuIdList).toString();
	}
	/**
	 * @param parentId
	 * @return
	 * 根据父节点id和权限菜单id集合获取所有复选框菜单集合
	 */
	public JsonArray getAllCheckMenuByParentId(Integer parentId,List<Integer> menuIdList)
	{
		JsonArray jsonArray = this.getCheckMenuById(parentId, menuIdList);
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonObject jsonObject=(JsonObject) jsonArray.get(i);
			if("open".equals(jsonObject.get("state").getAsString())){
				continue;
			}else{
				jsonObject.add("children", getAllCheckMenuByParentId(jsonObject.get("id").getAsInt(), menuIdList));
			}
		}
		return jsonArray;
	}
	/**
	 * @param parentId
	 * @return
	 * 根据父节点id和权限菜单id集合获取一层复选框菜单集合
	 */
	public JsonArray getCheckMenuById(Integer parentId,List<Integer> menuIdList)
	{
		List<Menu> menuList = menuService.findByParentId(parentId);
		JsonArray jsonArray=new JsonArray();
		for(Menu menu:menuList){
			JsonObject jsonObject=new JsonObject();
			jsonObject.addProperty("id", menu.getId()); // 节点Id
			jsonObject.addProperty("text", menu.getName()); // 节点名称
			if(menu.getState()==1){
				jsonObject.addProperty("state", "closed"); // 根节点
			}else{
				jsonObject.addProperty("state", "open"); // 叶子节点
			}
			jsonObject.addProperty("iconCls", menu.getIcon()); // 节点图标
			if (menuIdList.contains(menu.getId())) {//判断menu.getId()是否存在
				jsonObject.addProperty("checked", "true"); // 根节点
			}
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}
	@RequestMapping("/saveMenuSet")
	public Map<String,Object> saveMenuSet(String menuIds,Integer roleId)throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		roleMenuService.deleteByRoleId(roleId);//根据角色id删除所有角色权限关联实体
		if (StringUtil.isNotEmpty(menuIds)) {
			String idsStr[]=menuIds.split(",");
			for (int i = 0; i < idsStr.length; i++) {
				RoleMenu roleMenu=new RoleMenu();
				roleMenu.setRole(roleService.findById(roleId));
				roleMenu.setMenu(menuService.findById(Integer.parseInt(idsStr[i])));
				roleMenuService.save(roleMenu);
			}
		}
		map.put("success", true);
		return map;
	}
}
