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
 * 退货单实体
 * @author Administrator
 *
 */

@Entity
@Table(name="t_returnList")
public class ReturnList {
	
	@Id
	@GeneratedValue
	private Integer id; // 编号
	
	@Column(length=100)
	private String returnNumber; // 退货单号
	
	@ManyToOne
	@JoinColumn(name="supplierId")
	private Supplier supplier; // 供应商
	
	@Transient
	private Date bReturnDate;//起始日期，搜索用到
	@Transient
	private Date eReturnDate;//结束日期，搜索用到
	@Transient
	private List<ReturnListGoods> returnListGoods=null;//采购单退货商品集合
	
	

	/**
	 * 2.时间：
	 * @Temporal(TemporalType.TIME)
		在页面端取值：22:50:30
		3.日期和时间(默认)：
		@Temporal(TemporalType.TIMESTAMP)
		在页面端取值：2011-07-05 22:51:34:000
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date returnDate; // 退货日期
	
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

	public String getReturnNumber() {
		return returnNumber;
	}

	public void setReturnNumber(String returnNumber) {
		this.returnNumber = returnNumber;
	}

	public Supplier getSupplier() {
		return supplier;
	}
	public Date getbReturnDate() {
		return bReturnDate;
	}
	public List<ReturnListGoods> getReturnListGoods() {
		return returnListGoods;
	}

	public void setReturnListGoods(List<ReturnListGoods> returnListGoods) {
		this.returnListGoods = returnListGoods;
	}
	public void setbReturnDate(Date bReturnDate) {
		this.bReturnDate = bReturnDate;
	}

	public Date geteReturnDate() {
		return eReturnDate;
	}

	public void seteReturnDate(Date eReturnDate) {
		this.eReturnDate = eReturnDate;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	@JsonSerialize(using=CustomDateSerializer.class)
	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
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

	@Override
	public String toString() {
		return "ReturnList [id=" + id + ", returnNumber=" + returnNumber + ", supplier=" + supplier
				+ ", returnDate=" + returnDate + ", amountPayable=" + amountPayable + ", amountPaid=" + amountPaid
				+ ", state=" + state + ", user=" + user + ", remarks=" + remarks + "]";
	}
	
	
}
