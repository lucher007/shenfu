package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

import fr.opensagres.xdocreport.core.utils.StringUtils;

/**
 * 用户实体类
 */
public class Component extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String componentcode; // 元器件编号
	private String componentname; // 元器件名称
	private String componentmodel; //元器件规格
	private String componentunit; // 元器件计算单位
	private Integer depotamount; // 报警量
	private String componentstatus; // 元器件状态
	private String content;    //元器件详情
	private String remark; // 备注

	private Component component;
	private List<Component> componentlist;

	//重写父类方法
	public String getSort() {
		if(StringUtils.isNotEmpty(sort)){
			return sort;
		}else{
			return "componentcode";
		}
	}
	//重写父类方法
	public String getOrder() {
		if(StringUtils.isNotEmpty(order)){
			return order;
		}else{
			return "asc";
		}
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getComponentcode() {
		return componentcode;
	}
	public void setComponentcode(String componentcode) {
		this.componentcode = componentcode;
	}
	public String getComponentname() {
		return componentname;
	}
	public void setComponentname(String componentname) {
		this.componentname = componentname;
	}
	public String getComponentmodel() {
		return componentmodel;
	}
	public void setComponentmodel(String componentmodel) {
		this.componentmodel = componentmodel;
	}
	public String getComponentunit() {
		return componentunit;
	}
	public void setComponentunit(String componentunit) {
		this.componentunit = componentunit;
	}
	public Integer getDepotamount() {
		return depotamount;
	}
	public void setDepotamount(Integer depotamount) {
		this.depotamount = depotamount;
	}
	public String getComponentstatus() {
		return componentstatus;
	}
	public void setComponentstatus(String componentstatus) {
		this.componentstatus = componentstatus;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Component getComponent() {
		return component;
	}
	public void setComponent(Component component) {
		this.component = component;
	}
	public List<Component> getComponentlist() {
		return componentlist;
	}
	public void setComponentlist(List<Component> componentlist) {
		this.componentlist = componentlist;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
