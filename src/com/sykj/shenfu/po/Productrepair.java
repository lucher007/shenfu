package com.sykj.shenfu.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 用户实体类
 */
public class Productrepair extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String typecode; //产品类别编码
	private String typename; //产品类别名称
	private String productname;//产品名称
	private String productcode;//产品名称编码
	private String productidentfy; //产品SN
	private String addtime;     //添加时间
	private String operatorcode;  //操作员编号
	private String productproblem; //产品问题描述
	private String repairinfo;     //维修信息
	private String repairstatus; // 维修状态(0-未维修好；1-已经维修好)
	private String remark; // 备注

	private Productrepair uroductrepair;
	private List<Productrepair> uroductrepairlist;
	
	
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
	public String getProductidentfy() {
		return productidentfy;
	}
	public void setProductidentfy(String productidentfy) {
		this.productidentfy = productidentfy;
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
	public String getProductproblem() {
		return productproblem;
	}
	public void setProductproblem(String productproblem) {
		this.productproblem = productproblem;
	}
	public String getRepairinfo() {
		return repairinfo;
	}
	public void setRepairinfo(String repairinfo) {
		this.repairinfo = repairinfo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Productrepair getUroductrepair() {
		return uroductrepair;
	}
	public void setUroductrepair(Productrepair uroductrepair) {
		this.uroductrepair = uroductrepair;
	}
	public List<Productrepair> getUroductrepairlist() {
		return uroductrepairlist;
	}
	public void setUroductrepairlist(List<Productrepair> uroductrepairlist) {
		this.uroductrepairlist = uroductrepairlist;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getRepairstatus() {
		return repairstatus;
	}
	public String getRepairstatusname() {
		if("0".equals(repairstatus)){
			return "未维修好";
		}else if("1".equals(repairstatus)){
			return "已维修好";
		}else {
			return "";
		}
	}
	public void setRepairstatus(String repairstatus) {
		this.repairstatus = repairstatus;
	}
	

}
