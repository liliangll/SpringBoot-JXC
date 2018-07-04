package com.bdqn.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bdqn.entity.Goods;
import com.bdqn.entity.Log;
import com.bdqn.service.CustomerReturnListGoodsService;
import com.bdqn.service.GoodsService;
import com.bdqn.service.LogService;
import com.bdqn.service.SaleListGoodsService;
import com.bdqn.util.StringUtil;

/**
 * @author asus
 * 后台管理用户Controller
 */
/**
 * @author asus
 *
 */
@Controller
@RestController
@RequestMapping("/admin/goods")
public class GoodsAdminController {
	@Resource
	private GoodsService goodsService;
	
	@Resource
	private LogService logService;
	
	@Resource
	private SaleListGoodsService saleListGoodsService;
	
	@Resource
	private CustomerReturnListGoodsService customerReturnListGoodsService;
	/**
	 * 比如说张三登录进去，只有前面几个模块，登录进去，身份认证通过了，依然可以请求后台地址，这样的话不安全，
	 * 那么我们在方法上进一步的加上菜单权限的认证
	 * 用户管理2出错
	 * @param goods
	 * @param page
	 * @param rows
	 * @return 分頁查詢商品信息
	 */
	@RequestMapping("/list")
	@RequiresPermissions(value={"商品管理","进货入库","退货出库","销售出库","客户退货","商品报损","商品报溢"},logical=Logical.OR)
	public Map<String,Object> list(Goods goods,@RequestParam(value="page",required=false)Integer page,@RequestParam(value="rows",required=false)Integer rows)throws Exception{
		Map<String,Object> resultMap=new HashMap<>();
		List<Goods> goodsList=goodsService.list(goods, page, rows, Direction.ASC, "id");
		Long total=goodsService.getCount(goods);
		resultMap.put("rows", goodsList);
		resultMap.put("total", total);
		logService.save(new Log(Log.SEARCH_ACTION,"查询商品库存信息"));
		return resultMap;
	}
	
	
	
	
	/**
	 * 根据条件分页查询商品库存信息
	 * @param goods
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listInventory")
	@RequiresPermissions(value="当前库存查询")
	public Map<String,Object> listInventory(Goods goods,@RequestParam(value="page",required=false)Integer page,@RequestParam(value="rows",required=false)Integer rows)throws Exception{
		Map<String,Object> resultMap=new HashMap<>();
		List<Goods> goodsList=goodsService.list(goods, page, rows, Direction.ASC, "id");
		for(Goods g:goodsList){
			g.setSaleTotal(saleListGoodsService.getTotalByGoodsId(g.getId())-customerReturnListGoodsService.getTotalByGoodsId(g.getId())); // 设置销售总量
		}
		Long total=goodsService.getCount(goods);
		resultMap.put("rows", goodsList);
		resultMap.put("total", total);
		logService.save(new Log(Log.SEARCH_ACTION,"查询商品信息"));
		return resultMap;
	}
	/**
	 * @param page
	 * @param rows
	 * @return根据条件分页查询没有库存的商品信息
	 * @throws Exception
	 */
	@RequestMapping("/listNoInventoryQuantity")
	@RequiresPermissions(value="期初库存")
	public Map<String,Object> listNoInventoryQuantity(@RequestParam(value="codeOrName",required=false) String codeOrName,
			@RequestParam(value="page",required=false)Integer page
			,@RequestParam(value="rows",required=false)Integer rows)throws Exception{
		{
			Map<String,Object> resultMap=new HashMap<>();
			List<Goods> goodsList = goodsService.listNoInventoryQuantityByCodeOrName(codeOrName,page, rows, Direction.ASC, "id");//降序
			Long totalCouns = goodsService.getCountNoInventoryQuantityByCodeOrName(codeOrName);		
			resultMap.put("rows", goodsList);
			resultMap.put("total", totalCouns);
			logService.save(new Log(Log.SEARCH_ACTION,"查询商品信息(无库存)"));
			return resultMap;
		} 
	}	
	
	/**
	 * @param page
	 * @param rows
	 * @return根据条件分页查询有库存的商品信息
	 * @throws Exception
	 */
	@RequestMapping("/listHasInventoryQuantity")
	@RequiresPermissions(value="期初库存")
	public Map<String,Object> listHasInventoryQuantity(@RequestParam(value="codeOrName",required=false) String codeOrName,
			@RequestParam(value="page",required=false)Integer page
			,@RequestParam(value="rows",required=false)Integer rows)throws Exception{
	{
		Map<String,Object> resultMap=new HashMap<>();
		List<Goods> goodsList = goodsService.listHasInventoryQuantityByCodeOrName(codeOrName,page, rows, Direction.ASC, "id");//降序
		Long totalCouns = goodsService.getCountHasInventoryQuantityByCodeOrName(codeOrName);		
		resultMap.put("rows", goodsList);
		resultMap.put("total", totalCouns);
		logService.save(new Log(Log.SEARCH_ACTION,"查询商品信息(无库存)"));
		return resultMap;
		} 
	}	
	
	/**
	 * @return
	 * @throws Exception
	 * 生产商品编码
	 */
	@RequestMapping("/genGoodsCode")
	@RequiresPermissions(value="商品管理")
	public String genGoodsCode() throws Exception{
		String maxGoodsCode = goodsService.getMaxGoodsCode();
		if (StringUtil.isNotEmpty(maxGoodsCode)) {
			Integer code = Integer.parseInt(maxGoodsCode)+1;
			String codes=code.toString();
			int length=codes.length();
			for (int i = 4; i > length; i--) {
				codes="0"+codes;
			}
			return codes;
		}else {
			return "0001";
		}
	}
	
	/**
	 * @return
	 * @throws Exception
	 * 添加或者修改商品信息
	 */
	@RequestMapping("/save")
	@RequiresPermissions(value="商品管理")
	public Map<String,Object> save(Goods goods)throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		if (goods.getId()!=null) {
			logService.save(new Log(Log.UPDATE_ACTION,"更新商品信息"+goods));
		}else {
			logService.save(new Log(Log.ADD_ACTION,"添加商品信息"+goods));
			goods.setPurchasingPrice(goods.getPurchasingPrice());//设置上次进价为当前价格
		}
		goodsService.save(goods);
		map.put("success", true);
		return map;	
	}
	/**
	 * 根据id删除商品信息
	 * @param userId
	 * @return
	 */
	@RequestMapping("/delete")
	@RequiresPermissions(value="商品管理")
	public Map<String,Object> delete(Integer id)
	{

		Map<String,Object> map=new HashMap<String,Object>();
		Goods goods=goodsService.findById(id);
		if (goods.getState()==1) {
			map.put("success", false);
			map.put("errorInfo", "该商品已经期初入库，不能删除");
		}else if(goods.getState()==2)
		{
			map.put("success", false);
			map.put("errorInfo", "该商品已经发生单据，不能删除");
		}else {
			logService.save(new Log(Log.DELETE_ACTION,"删除商品信息"+goods));
			goodsService.delete(id);
			map.put("success", true);
		}	
		return map;
	}
	
	/**
	 * @param id
	 * @param num
	 * @param price
	 * @return
	 * 修改商品信息
	 * @throws Exception
	 */
	@RequestMapping("saveStore")
	@RequiresPermissions(value="期初库存")
	public Map<String,Object> saveStore(Integer id,Integer num,Float price) throws Exception{
		 Map<String,Object> map=new HashMap<String,Object>();
		 Goods goods=goodsService.findById(id);
		 goods.setInventoryQuantity(num);
		 goods.setPurchasingPrice(price);
		 goods.setLastPurchasingPrice(price);
		 goodsService.save(goods);
		 logService.save(new Log(Log.UPDATE_ACTION,"修改商品信息"+goods+"价格:"+price+"库存"+num));
		 map.put("success",true);
		 return map;
	}
	
	/**
	 * 删除库存，把商品的库存设置为0
	 * @param userId
	 * @return
	 */
	@RequestMapping("/deleteStock")
	@RequiresPermissions(value="期初库存")
	public Map<String,Object> deleteStock(Integer id)throws Exception
	{
		Map<String,Object> map=new HashMap<String,Object>();
		Goods goods=goodsService.findById(id);
		if(goods.getState()==2)
		{
			map.put("success", false);
			map.put("errorInfo", "该商品已经发生单据，不能删除");
		}else {
			goods.setInventoryQuantity(0);//库存数量
			goodsService.save(goods);
			logService.save(new Log(Log.UPDATE_ACTION,"修改商品信息"+goods));
			map.put("success", true);
		}	
		return map;
	}
	
	/**
	 * 查询库存报警商品
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listAlarm")
	@RequiresPermissions(value="库存报警")
	public Map<String,Object> listAlarm() throws Exception{
		 Map<String,Object> map=new HashMap<String,Object>();
		 map.put("rows", goodsService.listAlarm());
		 logService.save(new Log(Log.SEARCH_ACTION, "查询库存报警商品信息"));
		 return map;
	}
}
