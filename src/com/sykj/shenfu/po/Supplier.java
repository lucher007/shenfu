package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 */
public class Supplier extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String name; // 供应商简称
	private String fullname; // 供应商全称
	private String address; // 供应商地址
	private String phone; // 供应商联系电话
	private String contactname; // 供应商联系人
	private String contactphone; // 供应商联系人电话
	private String contactmail; // 供应商联系人邮件
	private String status;   //供应商状态(0-无效; 1-有效)
	private String creatercode; //创建人
	private String createdate; //创建日期
	private String modifiercode;  //修改人
	private String modifydate; //修改日期
	private String introduceinfo; //信息介绍
	private String remark; // 备注

	private Supplier supplier;
	private List<Supplier> supplierlist;
	private Operator creater; //创建人
	private Operator modifier; //修改人

	/*******************************************/
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getContactname() {
		return contactname;
	}

	public void setContactname(String contactname) {
		this.contactname = contactname;
	}

	public String getContactphone() {
		return contactphone;
	}

	public void setContactphone(String contactphone) {
		this.contactphone = contactphone;
	}

	public String getContactmail() {
		return contactmail;
	}

	public void setContactmail(String contactmail) {
		this.contactmail = contactmail;
	}

	public String getStatus() {
		return status;
	}
	
	public String getStatusname() {
		if("0".equals(status)){
			return "无效";
		}else if("1".equals(status)){
			return "有效";
		}else{
			return "";
		}
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

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public List<Supplier> getSupplierlist() {
		return supplierlist;
	}

	public void setSupplierlist(List<Supplier> supplierlist) {
		this.supplierlist = supplierlist;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	
	public String getModifydate() {
		return modifydate;
	}

	public void setModifydate(String modifydate) {
		this.modifydate = modifydate;
	}

	public Operator getCreater() {
		if(creater == null ){
			creater = new Operator();
		}
		return creater;
	}

	public void setCreater(Operator creater) {
		this.creater = creater;
	}

	public Operator getModifier() {
		if(modifier == null ){
			modifier = new Operator();
		}
		return modifier;
	}

	public void setModifier(Operator modifier) {
		this.modifier = modifier;
	}

	public String getIntroduceinfo() {
		return introduceinfo;
	}

	public String getCreatercode() {
		return creatercode;
	}

	public void setCreatercode(String creatercode) {
		this.creatercode = creatercode;
	}

	public String getModifiercode() {
		return modifiercode;
	}

	public void setModifiercode(String modifiercode) {
		this.modifiercode = modifiercode;
	}

	public void setIntroduceinfo(String introduceinfo) {
		this.introduceinfo = introduceinfo;
	}

	
	

}
