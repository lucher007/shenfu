package com.sykj.shenfu.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 用户实体类
 */
public class Userproductreplace extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String usercode; // 客户编号
	private String ordercode; // 订单编号
	private String modelname; //产品型号
	private String modelcode; //型号编码
	private String typecode; //产品类别编码
	private String typename; //产品类别名称
	private String productname;//产品名称
	private String productcode;//产品名称编码
	private String addtime;     //添加时间
	private String operatorcode;  //操作员编号
	private String visittime;  //上门售后时间
	private String visitreasons; //上门售后原因
	private String replacetype;  //更换类型(0-产品更换；1-软件升级)
	private String oldproductidentfy; //产品标识SN
	private String oldproductversion; //旧产品版本
	private String newproductidentfy; //新产品SN
	private String newproductversion; //新产品版本
	private String oldproductstatus; //旧产品回收状态（1-正常；2-损坏；3-维修）
	private String oldproductproblem; //旧产品问题原因
	private String rebackremark;     //回收备注信息
	private String remark; // 备注

	private Userproductreplace userproductreplace;
	private List<Userproductreplace> userproductreplacelist;
	
    private User user;
    private Userorder userorder;
    private String productidentfy;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getOrdercode() {
		return ordercode;
	}
	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}
	public String getModelname() {
		return modelname;
	}
	public void setModelname(String modelname) {
		this.modelname = modelname;
	}
	public String getModelcode() {
		return modelcode;
	}
	public void setModelcode(String modelcode) {
		this.modelcode = modelcode;
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
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String getProductcode() {
		return productcode;
	}
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getOperatorcode() {
		return operatorcode;
	}
	public void setOperatorcode(String operatorcode) {
		this.operatorcode = operatorcode;
	}
	public String getVisittime() {
		return visittime;
	}
	public void setVisittime(String visittime) {
		this.visittime = visittime;
	}
	public String getVisitreasons() {
		return visitreasons;
	}
	public void setVisitreasons(String visitreasons) {
		this.visitreasons = visitreasons;
	}
	public String getReplacetypename() {
		if("0".equals(replacetype)){
			return "产品更换";
		}else if("1".equals(replacetype)){
			return "软件升级";
		}else{
			return "";
		}
	}
	public String getReplacetype() {
		return replacetype;
	}
	public void setReplacetype(String replacetype) {
		this.replacetype = replacetype;
	}
	public String getOldproductidentfy() {
		return oldproductidentfy;
	}
	public void setOldproductidentfy(String oldproductidentfy) {
		this.oldproductidentfy = oldproductidentfy;
	}
	public String getOldproductversion() {
		return oldproductversion;
	}
	public void setOldproductversion(String oldproductversion) {
		this.oldproductversion = oldproductversion;
	}
	public String getNewproductidentfy() {
		return newproductidentfy;
	}
	public void setNewproductidentfy(String newproductidentfy) {
		this.newproductidentfy = newproductidentfy;
	}
	public String getNewproductversion() {
		return newproductversion;
	}
	public void setNewproductversion(String newproductversion) {
		this.newproductversion = newproductversion;
	}
	public String getOldproductstatusname() {
		if("1".equals(oldproductstatus)){
			return "正常";
		}else if("2".equals(oldproductstatus)){
			return "损坏";
		}else if("3".equals(oldproductstatus)){
			return "维修";
		}else{
			return "";
		}
	}
	public String getOldproductstatus() {
		return oldproductstatus;
	}
	public void setOldproductstatus(String oldproductstatus) {
		this.oldproductstatus = oldproductstatus;
	}
	public String getOldproductproblem() {
		return oldproductproblem;
	}
	public void setOldproductproblem(String oldproductproblem) {
		this.oldproductproblem = oldproductproblem;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Userproductreplace getUserproductreplace() {
		return userproductreplace;
	}
	public void setUserproductreplace(Userproductreplace userproductreplace) {
		this.userproductreplace = userproductreplace;
	}
	public List<Userproductreplace> getUserproductreplacelist() {
		return userproductreplacelist;
	}
	public void setUserproductreplacelist(
			List<Userproductreplace> userproductreplacelist) {
		this.userproductreplacelist = userproductreplacelist;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Userorder getUserorder() {
		return userorder;
	}
	public void setUserorder(Userorder userorder) {
		this.userorder = userorder;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getRebackremark() {
		return rebackremark;
	}
	public void setRebackremark(String rebackremark) {
		this.rebackremark = rebackremark;
	}
	public String getProductidentfy() {
		return productidentfy;
	}
	public void setProductidentfy(String productidentfy) {
		this.productidentfy = productidentfy;
	}

}
