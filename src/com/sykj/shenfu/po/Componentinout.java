package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import fr.opensagres.xdocreport.core.utils.StringUtils;

/**
 * 用户实体类
 */
public class Componentinout extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String componentcode;     //材料编号
	private String componentname;    // 材料名称
	private String componentmodel;   //原器件规格
	private String operatorcode;      // 操作员编号
	private String batchno;         //批次号
	private String type;            //类型（0-入库；1-出库）
	private Integer inoutamount; // 出入库数量
	private String addtime;         // 操作时间
	private String inoutercode;   //出入库领取人
	private String inoutstatus; //领取状态（0-未领；1-已领）
	private String depotrackcode; //货柜存放位置
	private String remark; // 备注

	private Componentinout componentinout;
	private List<Componentinout> componentinoutlist;
	private Operator operator; //操作员对象
	private Map<String, String> excelMap;
	private Employee inouter; //出入库负责人
	
	//---页面查询条件
	private String begintime;
	private String endtime;
	private Double totalinoutamount;
	
	public String getTypename() {
		if("0".equals(type)){
			return "入库";
		}else if("1".equals(type)){
			return "出库";
		}else{
			return "";
		}
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

	public String getOperatorcode() {
		return operatorcode;
	}

	public void setOperatorcode(String operatorcode) {
		this.operatorcode = operatorcode;
	}

	public String getBatchno() {
		return batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getInoutercode() {
		return inoutercode;
	}

	public void setInoutercode(String inoutercode) {
		this.inoutercode = inoutercode;
	}

	public String getInoutstatus() {
		return inoutstatus;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Componentinout getComponentinout() {
		return componentinout;
	}

	public void setComponentinout(Componentinout componentinout) {
		this.componentinout = componentinout;
	}

	public List<Componentinout> getComponentinoutlist() {
		return componentinoutlist;
	}

	public void setComponentinoutlist(List<Componentinout> componentinoutlist) {
		this.componentinoutlist = componentinoutlist;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public Map<String, String> getExcelMap() {
		return excelMap;
	}

	public void setExcelMap(Map<String, String> excelMap) {
		this.excelMap = excelMap;
	}

	public Employee getInouter() {
		return inouter;
	}

	public void setInouter(Employee inouter) {
		this.inouter = inouter;
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

	public Double getTotalinoutamount() {
		return totalinoutamount;
	}

	public void setTotalinoutamount(Double totalinoutamount) {
		this.totalinoutamount = totalinoutamount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
