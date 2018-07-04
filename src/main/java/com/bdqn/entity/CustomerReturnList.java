package com.bdqn.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 客户退货单实体
 * @author Administrator
 */

@Entity
@Table(name="t_customerReturnList")
public class CustomerReturnList{
	
	@Id
	@GeneratedValue
	private Integer id; // 编号
	
	@Column(length=100)
	private String customerReturnNumber; // 客户退货单号

	@ManyToOne
	@JoinColumn(name="customerId")
	private Customer customer; // 客户
	
	@Transient
	private List<CustomerReturnListGoods> customerReturnListGoods=null;//客户单商品进货商品集合
	/**
	 * @Temporal格式化时间日期，页面直接得到格式化类型的值
	 * @Temporal(TemporalType.TIME)
	 * 在页面端取值：22:50:30
	 * TIMESTAMP：为默认 2011-04-12 22:51:34
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date customerReturnDate; // 客户退货日期
	
	@Transient
	private Date bCustomerReturnDate; // 起始日期 搜索用到
	
	@Transient
	private Date eCustomerReturnDate; // 结束日期 搜索用到
	
	private float amountPayable; // 应付金额
	
	private float amountPaid; // 实付金额
	
	private Integer state; // 交易状态 1 已付 2 未付
	
	@ManyToOne
	@JoinColumn(name="userId")
	private User user; // 操作员
	
	@Column(length=1000)
	private String remarks; // 备注
	


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public List<CustomerReturnListGoods> getCustomerReturnListGoods() {
		return customerReturnListGoods;
	}

	public void setCustomerReturnListGoods(List<CustomerReturnListGoods> customerReturnListGoods) {
		this.customerReturnListGoods = customerReturnListGoods;
	}

	public String getCustomerReturnNumber() {
		return customerReturnNumber;
	}

	public void setCustomerReturnNumber(String customerReturnNumber) {
		this.customerReturnNumber = customerReturnNumber;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@JsonSerialize(using=CustomDateSerializer.class)
	public Date getCustomerReturnDate() {
		return customerReturnDate;
	}

	public void setCustomerReturnDate(Date customerReturnDate) {
		this.customerReturnDate = customerReturnDate;
	}

	public float getAmountPayable() {
		return amountPayable;
	}

	public void setAmountPayable(float amountPayable) {
		this.amountPayable = amountPayable;
	}

	public float getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(float amountPaid) {
		this.amountPaid = amountPaid;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	

	public Date getbCustomerReturnDate() {
		return bCustomerReturnDate;
	}

	public void setbCustomerReturnDate(Date bCustomerReturnDate) {
		this.bCustomerReturnDate = bCustomerReturnDate;
	}

	public Date geteCustomerReturnDate() {
		return eCustomerReturnDate;
	}

	public void seteCustomerReturnDate(Date eCustomerReturnDate) {
		this.eCustomerReturnDate = eCustomerReturnDate;
	}
	
	

	

	@Override
	public String toString() {
		return "CustomerReturnList [id=" + id + ", customerReturnNumber=" + customerReturnNumber + ", customer=" + customer
				+ ", customerReturnDate=" + customerReturnDate + ", amountPayable=" + amountPayable + ", amountPaid=" + amountPaid
				+ ", state=" + state + ", user=" + user + ", remarks=" + remarks + "]";
	}
	
	
}