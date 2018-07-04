package com.bdqn.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 客户退货单商品实体
 * @author Administrator
 *
 */

@Entity
@Table(name="t_customerReturnListGoods")
public class CustomerReturnListGoods{
	
	@Id
	@GeneratedValue
	private Integer id; // 编号
	/*@JsonIgnore作用是json序列化时将java bean中的一些属性忽略掉,序列化和反序列化都受影响。
	customerReturnList属性就在getCustomerReturnList属性上加了  @JsonIgnore,
	这个时候从后台推数据到前台的时候,就会把customerReturnList这个引用属性给忽略掉。
	*/
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="customerReturnListId")//注释本表中指向另一个表的外键。
	private CustomerReturnList customerReturnList; // 客户退货单
	
	@Column(length=50)
	private String code; // 商品编码
	
	@Column(length=50)
	private String name; // 商品名称
	
	@Column(length=50)
	private String model; // 商品型号
	
	@ManyToOne
	@JoinColumn(name="typeId")
	private GoodsType type; // 商品类别
	
	@Transient
	private Integer typeId; // 类别id
	
	private Integer goodsId; // 商品id
	
	@Column(length=10)
	private String unit; // 商品单位
	
	private float price; // 单价
	
	private int num; // 数量
	
	private float total; // 总金额
	/**
	 * 表示该属性并非一个到数据库表的字段的映射,ORM框架将忽略该属性。
	 * 如果一个属性并非数据库表的字段映射,就务必将其标示为@Transient
	 */
	@Transient
	private String codeOrName; // 查询用到 根据商品编码或者商品名称查询

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CustomerReturnList getCustomerReturnList() {
		return customerReturnList;
	}

	public void setCustomerReturnList(CustomerReturnList customerReturnList) {
		this.customerReturnList = customerReturnList;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public GoodsType getType() {
		return type;
	}

	public void setType(GoodsType type) {
		this.type = type;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}


	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}
	
	

	public String getCodeOrName() {
		return codeOrName;
	}

	public void setCodeOrName(String codeOrName) {
		this.codeOrName = codeOrName;
	}

	@Override
	public String toString() {
		return "CustomerReturnListGoods [id=" + id + ", customerReturnList=" + customerReturnList + ", code=" + code + ", name=" + name
				+ ", model=" + model + ", type=" + type + ", typeId=" + typeId + ", goodsId=" + goodsId + ", unit="
				+ unit + ", price=" + price + ", num=" + num + ", total=" + total + "]";
	}
	
	
	
}
