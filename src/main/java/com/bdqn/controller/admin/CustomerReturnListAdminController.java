package com.bdqn.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bdqn.entity.CustomerReturnList;
import com.bdqn.entity.CustomerReturnListGoods;
import com.bdqn.entity.Log;
import com.bdqn.service.CustomerReturnListGoodsService;
import com.bdqn.service.CustomerReturnListService;
import com.bdqn.service.LogService;
import com.bdqn.service.UserService;
import com.bdqn.util.DateUtil;
import com.bdqn.util.StringUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 后台管理客户退货单Controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/admin/customerReturnList")
public class CustomerReturnListAdminController {

	@Resource
	private CustomerReturnListService customerReturnListService;
	
	@Resource
	private CustomerReturnListGoodsService customerReturnListGoodsService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private LogService logService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
	}
	
	/**
	 * 获取客户退货单号
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/genCode")
	@RequiresPermissions(value="客户退货")
	public String genCode()throws Exception{
		StringBuffer code=new StringBuffer("XT");
		code.append(DateUtil.getCurrentDateStr());
		String customerReturnNumber=customerReturnListService.getTodayMaxCustomerReturnNumber();
		if(customerReturnNumber!=null){
			code.append(StringUtil.formatCode(customerReturnNumber));
		}else{
			code.append("0001");
		}
		return code.toString();
	}
	
	/**
	 * 添加客户退货单 以及所有客户退货单商品
	 * @param customerReturnList
	 * @param goodsJson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	@RequiresPermissions(value="客户退货")
	public Map<String,Object> save(CustomerReturnList customerReturnList,String goodsJson)throws Exception{
		Map<String,Object> resultMap=new HashMap<>();
		customerReturnList.setUser(userService.findByUserName((String) SecurityUtils.getSubject().getPrincipal())); // 设置操作用户
		Gson gson=new Gson();
		List<CustomerReturnListGoods> plgList=gson.fromJson(goodsJson,new TypeToken<List<CustomerReturnListGoods>>(){}.getType());
		customerReturnListService.save(customerReturnList, plgList);
		logService.save(new Log(Log.ADD_ACTION,"添加客户退货单"));
		resultMap.put("success", true);
		return resultMap;
	}
	
	/**
	 * 根据条件查询所有客户退货单信息
	 * @param customerReturnList
	 * @param goodsJson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	@RequiresPermissions(value= {"客户退货查询","客户统计"},logical=Logical.OR)
	public Map<String,Object> list(CustomerReturnList customerReturnList)throws Exception{
		Map<String,Object> resultMap=new HashMap<>();		
		List<CustomerReturnList> 	CustomerReturnList=customerReturnListService.list(customerReturnList, Direction.DESC, "customerReturnDate");	
		resultMap.put("rows", CustomerReturnList);
		logService.save(new Log(Log.SEARCH_ACTION, "客户退货单查询"));
		return resultMap;
	}
	/**
	 * 根据条件查询所有客户退货单信息
	 * @param customerReturnList
	 * @param goodsJson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listGoods")
	@RequiresPermissions(value="客户退货查询")
	public Map<String,Object> listGoods(Integer customerReturnListId)throws Exception{
		if (customerReturnListId==null) {
			return null;
		}
		Map<String,Object> resultMap=new HashMap<>();		
		resultMap.put("rows", customerReturnListGoodsService.ListByCustomerReturnListId(customerReturnListId));
		logService.save(new Log(Log.SEARCH_ACTION, "客户退货单商品查询"));
		return resultMap;
	}

	/**
	 * @param id
	 * @return
	 * @throws Exception
	 * 删除客户退货单信息
	 */
	@RequestMapping("/delete")
	@RequiresPermissions(value="客户退货查询")
	public Map<String,Object> delete(Integer id)throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		customerReturnListService.delete(id);
		logService.save(new Log(Log.DELETE_ACTION,"删除客户退货单信息，"+customerReturnListService.findById(id)));
		map.put("success", true);
		return map;
	}
	
	/**
	 * @return
	 * 更新客户退货单
	 */
	@RequestMapping("/update")
	@RequiresPermissions(value="客户统计")
	public Map<String,Object> update(Integer id)
	{
		 Map<String,Object>  map=new HashMap<String,Object>();
		 CustomerReturnList customerReturnList  = customerReturnListService.findById(id);
		 customerReturnList.setState(1);
		 customerReturnListService.update(customerReturnList);
		 map.put("success", true);
		 return map;
		
	}
	
	/**
	 * 根据条件获取商品客户信息
	 * @param customerReturnList
	 * @param customerReturnListGoods
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listCount")
	@RequiresPermissions(value="商品销售统计")
	public Map<String,Object> listCount(CustomerReturnList customerReturnList,CustomerReturnListGoods customerReturnListGoods)throws Exception{
		Map<String,Object> resultMap=new HashMap<>();
		List<CustomerReturnList> customerReturnListList=customerReturnListService.list(customerReturnList, Direction.DESC, "customerReturnDate");
		for(CustomerReturnList cl:customerReturnListList){
			customerReturnListGoods.setCustomerReturnList(cl);
			List<CustomerReturnListGoods> clgList=customerReturnListGoodsService.list(customerReturnListGoods);
			
			cl.setCustomerReturnListGoods(clgList);
		}
		resultMap.put("rows", customerReturnListList);
		logService.save(new Log(Log.SEARCH_ACTION,"商品销售统计查询"));
		return resultMap;
	}
	 
}
