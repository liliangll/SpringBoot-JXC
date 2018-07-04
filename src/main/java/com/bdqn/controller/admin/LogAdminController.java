package com.bdqn.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bdqn.entity.Log;
import com.bdqn.service.LogService;

/**
 * @author asus
 * 后台管理角色Controller
 */
@RestController
@RequestMapping("/admin/log")
public class LogAdminController {
	
	@Resource
	private LogService logService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
	}
	
	/**
	 * 比如说张三登录进去，只有前面几个模块，登录进去，身份认证通过了，依然可以请求后台地址，这样的话不安全，
	 * 那么我们在方法上进一步的加上菜单权限的认证
	 * 用户管理2出错
	 * @param user
	 * @param page
	 * @param rows
	 * @return 根据条件分頁查詢日志信息
	 */

	@RequestMapping("/list")
	@RequiresPermissions(value="系统日志")
	public Map<String,Object> list(Log log,@RequestParam(value="page",required=false)Integer page,@RequestParam(value="rows",required=false)Integer rows)throws Exception{
		Map<String,Object> resultMap=new HashMap<>();
		List<Log> logList=logService.list(log, page, rows, Direction.DESC, "id");//降序
		Long total=logService.getCount(log);
		resultMap.put("rows", logList);
		resultMap.put("total", total);
		logService.save(new Log(Log.SEARCH_ACTION,"查询角色信息"));
		return resultMap;
	}
	
}
