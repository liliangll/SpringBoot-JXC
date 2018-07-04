package com.bdqn.entity;



import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 用户实体
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="t_user")
public class User implements Serializable{
	
	@Id
	@GeneratedValue
	private Integer id; // 编号
	@NotEmpty(message="請輸入用戶名")
	@Column(length=50)
	private String userName; // 用户名
	@NotEmpty(message="請輸入密碼")
	@Column(length=50)
	private String password; // 密码
	private String redisKey;//redis中的key
	@Column(length=50)
	private String trueName; // 真实姓名
	
	@Column(length=1000)
	private String remarks; // 备注

	@Transient  //不映射到表里面
	private String roles;
	
	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
	
	

	public String getRedisKey() {
		return redisKey;
	}

	public void setRedisKey(String redisKey) {
		this.redisKey = "user_"+redisKey;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", redisKey=" + redisKey
				+ ", trueName=" + trueName + ", remarks=" + remarks + ", roles=" + roles + "]";
	}

	
	
	
	
	
}
