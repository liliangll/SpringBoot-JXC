package com.bdqn.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bdqn.entity.Customer;
import com.bdqn.entity.Log;
import com.bdqn.service.CustomerService;
import com.bdqn.service.LogService;

/**
 * @author asus
 * 后台管理客户Controller
 */
@RestController
@RequestMapping("/admin/customer")
public class CustomerAdminController {
	@Resource
	private CustomerService customerService;
	
	@Resource
	private LogService logService;
	
		
	/**
	 * @param q
	 * @return
	 * @throws Exception
	 * 下拉框模糊 查询
	 */ 
	@RequestMapping("/comboList")
	@RequiresPermissions(value= {"销售出库","客户退货","销售单据查询","客户退货查询","客户统计"},logical=Logical.OR)
	public List<Customer> comboList(String name)throws Exception{	
			if (name==null) {
				name="";
			}
			return customerService.findByName("%"+name+"%");
	}
	
	/**
	 * 比如说张三登录进去，只有前面几个模块，登录进去，身份认证通过了，依然可以请求后台地址，这样的话不安全，
	 * 那么我们在方法上进一步的加上菜单权限的认证
	 * 客户管理2出错
	 * @param customer
	 * @param page
	 * @param rows
	 * @return 分頁查詢用戶信息
	 */
	@RequestMapping("/list")
	@RequiresPermissions(value="客户管理")
	public Map<String,Object> list(Customer customer,@RequestParam(value="page",required=false)Integer page
			,@RequestParam(value="rows",required=false)Integer rows)throws Exception{
	{
		Map<String,Object> resultMap=new HashMap<>();
		List<Customer> customerList = customerService.list(customer, page, rows, Direction.ASC, "id");//降序
		Long totalCouns = customerService.getCount(customer);		
		resultMap.put("rows", customerList);
		resultMap.put("total", totalCouns);
		logService.save(new Log(Log.SEARCH_ACTION,"查询客户信息"));
		return resultMap;
		}
	}
	
	/**
	 * @return
	 * @throws Exception
	 * 添加或者修改客户信息
	 */
	@RequestMapping("/save")
	public Map<String,Object> save(Customer customer)throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		customerService.save(customer);
		map.put("success", true);
		return map;	
	}
	/**
	 * 根据id删除客户信息
	 * @param customerId
	 * @return
	 */
	@RequestMapping("/delete")
	@RequiresPermissions(value="客户管理")
	public Map<String,Object> delete(String ids)
	{		
		Map<String,Object> map=new HashMap<String,Object>();
		String[] split = ids.split(",");
		for (int i = 0; i < split.length; i++) {
			int id = Integer.parseInt(split[i]);			
			logService.save(new Log(Log.DELETE_ACTION,"删除客户信息"+customerService.findById(id)));
			customerService.delete(id);
		}
		map.put("success", true);
		return map;
	}
}
