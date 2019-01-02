package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 */
public class Productinout extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String productcode;        // 产品编码
	private String productname;      // 产品名称
	private String productidentfy;   //产品唯一标识
	private String type;            //类型（0-入库；1-出库）
	private String inoutercode;      //出入库人ID
	private Integer inoutamount;     // 出入库数量
	private String addtime;         // 操作时间
	private String operatorcode;   //操作员编号
	private String inoutstatus;    //领取状态（0-未领；1-已领）
	private String depotrackcode;   //货柜存放位置
	private String remark; // 备注

	private Productinout productinout;
	private List<Productinout> productinoutlist;
	private Operator operator; //操作员对象
	private Employee inouter;
	
	//---页面查询条件
	private String begintime;
	private String endtime;
	private Integer totalinoutamount;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getProductcode() {
		return productcode;
	}
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String getProductidentfy() {
		return productidentfy;
	}
	public void setProductidentfy(String productidentfy) {
		this.productidentfy = productidentfy;
	}
	public String getType() {
		return type;
	}
	public String getTypename() {
		if("0".equals(type)){
			return "入库";
		}else if("1".equals(type)){
			return "出库";
		}else{
			return "";
		}
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getInoutercode() {
		return inoutercode;
	}
	public void setInoutercode(String inoutercode) {
		this.inoutercode = inoutercode;
	}
	public Integer getInoutamount() {
		return inoutamount;
	}
	public void setInoutamount(Integer inoutamount) {
		this.inoutamount = inoutamount;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Productinout getProductinout() {
		return productinout;
	}
	public void setProductinout(Productinout productinout) {
		this.productinout = productinout;
	}
	public List<Productinout> getProductinoutlist() {
		return productinoutlist;
	}
	public void setProductinoutlist(List<Productinout> productinoutlist) {
		this.productinoutlist = productinoutlist;
	}
	public Operator getOperator() {
		return operator;
	}
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	public Employee getInouter() {
		return inouter;
	}
	public void setInouter(Employee inouter) {
		this.inouter = inouter;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getOperatorcode() {
		return operatorcode;
	}
	public void setOperatorcode(String operatorcode) {
		this.operatorcode = operatorcode;
	}
	public String getInoutstatus() {
		return inoutstatus;
	}
	public String getInoutstatusname() {
		if("0".equals(inoutstatus)){
			return "未领";
		}else if("1".equals(inoutstatus)){
			return "已领";
		}else{
			return "";
		}
	}
	public void setInoutstatus(String inoutstatus) {
		this.inoutstatus = inoutstatus;
	}
	public String getDepotrackcode() {
		return depotrackcode;
	}
	public void setDepotrackcode(String depotrackcode) {
		this.depotrackcode = depotrackcode;
	}
	public String getBegintime() {
		return begintime;
	}
	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public Integer getTotalinoutamount() {
		return totalinoutamount;
	}
	public void setTotalinoutamount(Integer totalinoutamount) {
		this.totalinoutamount = totalinoutamount;
	}
}
