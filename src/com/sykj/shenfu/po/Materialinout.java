package com.sykj.shenfu.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import fr.opensagres.xdocreport.core.utils.StringUtils;

/**
 * 用户实体类
 */
public class Materialinout extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String materialcode;     //材料编号
	private String materialname;    // 材料名称
	private String operatorcode;      // 操作员编号
	private String batchno;         //批次号
	private String type;            //类型（0-入库；1-出库）
	private Integer inoutamount; // 出入库数量
	private String addtime;         // 操作时间
	private String inoutercode;   //出入库领取人
	private String inoutstatus; //领取状态（0-未领；1-已领）
	private String depotrackcode; //货柜存放位置
	private String materialidentfy; //材料唯一标识SN码
	private String remark; // 备注

	private Materialinout materialinout;
	private List<Materialinout> materialinoutlist;
	private Operator operator; //操作员对象
	private Map<String, String> excelMap;
	private Employee inouter; //出入库负责人
	
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
	public String getMaterialname() {
		return materialname;
	}
	public void setMaterialname(String materialname) {
		this.materialname = materialname;
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
	public Materialinout getMaterialinout() {
		return materialinout;
	}
	public void setMaterialinout(Materialinout materialinout) {
		this.materialinout = materialinout;
	}
	public List<Materialinout> getMaterialinoutlist() {
		return materialinoutlist;
	}
	public void setMaterialinoutlist(List<Materialinout> materialinoutlist) {
		this.materialinoutlist = materialinoutlist;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Operator getOperator() {
		return operator;
	}
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	public String getMaterialcode() {
		return materialcode;
	}
	public void setMaterialcode(String materialcode) {
		this.materialcode = materialcode;
	}
	public String getOperatorcode() {
		return operatorcode;
	}
	public void setOperatorcode(String operatorcode) {
		this.operatorcode = operatorcode;
	}
	public Integer getInoutamount() {
		return inoutamount;
	}
	public void setInoutamount(Integer inoutamount) {
		this.inoutamount = inoutamount;
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
	public Integer getTotalinoutamount() {
		return totalinoutamount;
	}
	public void setTotalinoutamount(Integer totalinoutamount) {
		this.totalinoutamount = totalinoutamount;
	}
	
	//重写父类方法
	public String getSort() {
		if(StringUtils.isNotEmpty(sort)){
			return sort;
		}else{
			return "materialcode";
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
	public String getMaterialidentfy() {
		return materialidentfy;
	}
	public void setMaterialidentfy(String materialidentfy) {
		this.materialidentfy = materialidentfy;
	}

}
