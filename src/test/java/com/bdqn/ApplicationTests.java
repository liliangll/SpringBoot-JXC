package com.bdqn;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import com.bdqn.entity.Menu;
import com.bdqn.entity.Role;
import com.bdqn.entity.User;
import com.bdqn.service.MenuService;
import com.bdqn.service.UserService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
	public static Logger logger= LoggerFactory.getLogger(ApplicationTests.class);
	@Resource
    UserService userService;
	@Autowired
    private JavaMailSender mailSender;
	@Autowired
	private MenuService  menuService;
	@Test  
	public void testRedis(){  
		 User findByUserName = userService.findById(2);
		 System.out.println("第一次执行："+findByUserName.getUserName());  
	}
	@Test
    public void sendSimpleMail() throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("569833732@qq.com");
        message.setTo("569833732@qq.com");
        message.setSubject("1111");
        message.setText("1111");
        mailSender.send(message);
        
    }
	
	@Test
	public void xx() {
		JsonArray jsonArray=this.getMenuByParentId(1, 1);
		for(int i=0;i<jsonArray.size();i++){
			JsonObject jsonObject=(JsonObject) jsonArray.get(i);
			if("open".equals(jsonObject.get("state").getAsString())){
				continue;
			}else{
				jsonObject.add("children", getAllMenuByParentId(jsonObject.get("id").getAsInt(), 1));
			}
	}
		System.out.println(jsonArray);
}

	/**
	 * 获取所有菜单信息
	 * @param parentId
	 * @param roleId
	 * @return
	 * 
	 */
	public JsonArray getAllMenuByParentId(Integer parentId,Integer roleId){
		JsonArray jsonArray=this.getMenuByParentId(parentId, roleId);
		for(int i=0;i<jsonArray.size();i++){
			JsonObject jsonObject=(JsonObject) jsonArray.get(i);
			if("open".equals(jsonObject.get("state").getAsString())){
				continue;
			}else{
				jsonObject.add("children", getAllMenuByParentId(jsonObject.get("id").getAsInt(), roleId));
			}
		}
		return jsonArray;
	}
	
	/**
	 * 根据父节点和用户角色Id查询菜单
	 * @param parentId
	 * @param roleId
	 * @return 迭代  递归
	 */
	public JsonArray getMenuByParentId(Integer parentId,Integer roleId){
		List<Menu> menuList=menuService.findByParentIdAndRoleId(parentId, roleId);
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
			JsonObject attributeObject=new JsonObject(); // 扩展属性
			attributeObject.addProperty("url", menu.getUrl()); // 菜单请求地址
			jsonObject.add("attributes", attributeObject); 
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}
	@Test
	public void aa() {
			List<Menu> menuList=menuService.findByParentIdAndRoleId(1, 1);
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
				JsonObject attributeObject=new JsonObject(); // 扩展属性
				attributeObject.addProperty("url", menu.getUrl()); // 菜单请求地址
				jsonObject.add("attributes", attributeObject); 
				jsonArray.add(jsonObject);
				System.out.println(jsonArray);
			}
			
		}
}
