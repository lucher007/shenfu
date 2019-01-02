package com.sykj.shenfu.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 用户实体类
 */
public class Producttype extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String typecode; // 类别编号
	private String typename; // 类别名称
	private String remark; // 备注

	private Producttype producttype;
	private List<Producttype> producttypelist;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTypecode() {
		return typecode;
	}
	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Producttype getProducttype() {
		return producttype;
	}
	public void setProducttype(Producttype producttype) {
		this.producttype = producttype;
	}
	public List<Producttype> getProducttypelist() {
		return producttypelist;
	}
	public void setProducttypelist(List<Producttype> producttypelist) {
		this.producttypelist = producttypelist;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
