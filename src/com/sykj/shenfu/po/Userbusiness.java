package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;
/**
 * 用户实体类
 */
public class Userbusiness extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;                //数据库ID
	private String operatorcode;       //操作员编号
	private String businesstypekey;   //业务类型KEY
	private String businesstypename;  //业务类型名称
	private String usercode;           //客户ID
	private String taskcode;            //任务单号
	private String ordercode;           //订单号
	private String dispatchcode;        //工单号
	private String addtime;           //添加时间
	private String content;           //操作内容
	private String source;            //来源(0-PC;1-APP)
	private String remark;             //备注
	
	/******************数据库辅助字段*************************/
	private Userbusiness userbusiness;
	private List<Userbusiness> Userbusinesslist;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOperatorcode() {
		return operatorcode;
	}
	public void setOperatorcode(String operatorcode) {
		this.operatorcode = operatorcode;
	}
	public String getBusinesstypekey() {
		return businesstypekey;
	}
	public void setBusinesstypekey(String businesstypekey) {
		this.businesstypekey = businesstypekey;
	}
	public String getBusinesstypename() {
		return businesstypename;
	}
	public void setBusinesstypename(String businesstypename) {
		this.businesstypename = businesstypename;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getTaskcode() {
		return taskcode;
	}
	public void setTaskcode(String taskcode) {
		this.taskcode = taskcode;
	}
	public String getOrdercode() {
		return ordercode;
	}
	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}
	public String getDispatchcode() {
		return dispatchcode;
	}
	public void setDispatchcode(String dispatchcode) {
		this.dispatchcode = dispatchcode;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSource() {
		return source;
	}
	public String getSourcename() {
		if("0".equals(this.source)){
			return "销售APP";
		}else if("1".equals(this.source)){
			return "400客服";
		}else if("2".equals(this.source)){
			return "小区驻点";
		} else {
			return "";
		}
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Userbusiness getUserbusiness() {
		return userbusiness;
	}
	public void setUserbusiness(Userbusiness userbusiness) {
		this.userbusiness = userbusiness;
	}
	public List<Userbusiness> getUserbusinesslist() {
		return Userbusinesslist;
	}
	public void setUserbusinesslist(List<Userbusiness> userbusinesslist) {
		Userbusinesslist = userbusinesslist;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
}
