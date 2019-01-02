package com.sykj.shenfu.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
/**
 * 用户实体类
 */
public class Businesstype extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;                //数据库ID
	private String typekey;           //业务类型编码
	private String typename;          //业务类型名称
	private Integer pageorder;		  //页面显示顺序
	private String status;		       //状态（0-无效；1-有效）
	private String remark;             //备注
	
	/******************数据库辅助字段*************************/
	private Businesstype businesstype;
	private List<Businesstype> businesstypelist;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTypekey() {
		return typekey;
	}
	public void setTypekey(String typekey) {
		this.typekey = typekey;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Businesstype getBusinesstype() {
		return businesstype;
	}
	public void setBusinesstype(Businesstype businesstype) {
		this.businesstype = businesstype;
	}
	public List<Businesstype> getBusinesstypelist() {
		return businesstypelist;
	}
	public void setBusinesstypelist(List<Businesstype> businesstypelist) {
		this.businesstypelist = businesstypelist;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getPageorder() {
		return pageorder;
	}
	public void setPageorder(Integer pageorder) {
		this.pageorder = pageorder;
	}
	
	
	
	
}
