package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 */
public class Userorderinfo extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String ordercode; // 订单号
	private String usercode; // 客户ID
	private String username; //客户姓名
	private String usersex; // 客户性别(0-女；1-男)
	private String phone; // 联系电话
	private String address; //安装地址
	private String source;   //客户来源（0-销售；1-400）
	private String visittype; //上门类型（0.公司派人讲解测量，1.销售员亲自讲解测量; 2-已讲解，公司派人只需测量）;
	private String salercode; //销售员编号
	private String talkercode; //讲解员编号
	private String visitorcode; //上门人员
	private String addtime; //添加时间
	private Integer price; //原价格
	private Integer sellprice;//优惠价
	private String buytype; //购买方式（0-型号整体购买；1-配件零售购买）
	private String modelcode; //型号编码
	private String modelname; //型号名称
	private String productcode; //产品编码
	private String productname; //产品名称
	private String remark; // 备注

	private Userorderinfo userorderinfo;
	private List<Userorderinfo> userorderinfolist;
	
    private User user;
    private Employee visitor;
    private Usertask usertask;
    private Userproduct userproduct;
    private List<Userproduct> userproductList;
    private Productdepot productdepot;
    private Operator operator;
    
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOrdercode() {
		return ordercode;
	}
	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsersex() {
		return usersex;
	}
	public void setUsersex(String usersex) {
		this.usersex = usersex;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getVisitorcode() {
		return visitorcode;
	}
	public void setVisitorcode(String visitorcode) {
		this.visitorcode = visitorcode;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public String getBuytype() {
		return buytype;
	}
	public void setBuytype(String buytype) {
		this.buytype = buytype;
	}
	public String getModelcode() {
		return modelcode;
	}
	public void setModelcode(String modelcode) {
		this.modelcode = modelcode;
	}
	public String getProductcode() {
		return productcode;
	}
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Userorderinfo getUserorderinfo() {
		return userorderinfo;
	}
	public void setUserorderinfo(Userorderinfo userorderinfo) {
		this.userorderinfo = userorderinfo;
	}
	public List<Userorderinfo> getUserorderinfolist() {
		return userorderinfolist;
	}
	public void setUserorderinfolist(List<Userorderinfo> userorderinfolist) {
		this.userorderinfolist = userorderinfolist;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Employee getVisitor() {
		return visitor;
	}
	public void setVisitor(Employee visitor) {
		this.visitor = visitor;
	}
	public Usertask getUsertask() {
		return usertask;
	}
	public void setUsertask(Usertask usertask) {
		this.usertask = usertask;
	}
	public Userproduct getUserproduct() {
		return userproduct;
	}
	public void setUserproduct(Userproduct userproduct) {
		this.userproduct = userproduct;
	}
	public List<Userproduct> getUserproductList() {
		return userproductList;
	}
	public void setUserproductList(List<Userproduct> userproductList) {
		this.userproductList = userproductList;
	}
	public Productdepot getProductdepot() {
		return productdepot;
	}
	public void setProductdepot(Productdepot productdepot) {
		this.productdepot = productdepot;
	}
	public Operator getOperator() {
		return operator;
	}
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getModelname() {
		return modelname;
	}
	public void setModelname(String modelname) {
		this.modelname = modelname;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String getVisittype() {
		return visittype;
	}
	public String getVisittypename() {
		if("0".equals(this.visittype)){
			return "公司派人讲解";
		} else if("1".equals(this.visittype)){
			return "亲自讲解";
		} else if("2".equals(this.visittype)){
			return "已讲解，公司派人测量";
		} else {
			return "";
		}
	}
	public void setVisittype(String visittype) {
		this.visittype = visittype;
	}
	public String getSalercode() {
		return salercode;
	}
	public void setSalercode(String salercode) {
		this.salercode = salercode;
	}
	public String getTalkercode() {
		return talkercode;
	}
	public void setTalkercode(String talkercode) {
		this.talkercode = talkercode;
	}
	public Integer getSellprice() {
		return sellprice;
	}
	public void setSellprice(Integer sellprice) {
		this.sellprice = sellprice;
	}

}
