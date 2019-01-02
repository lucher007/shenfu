package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 */
public class Cellstationemployee extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;      // 数据库ID
	private String stationcode; //驻点单编号
	private String extendcode; //小区发布编号
	private String cellcode; //小区编号
	private String cellname; // 小区名称
	private String address;  // 小区地址
	private String starttime; //驻点开始时间
	private String endtime;    //驻点结束时间
	private String employeetype;//员工类型(talker-讲解人员；visitor-测量人员)
	private String employeecode; //员工编号
	private String employeename; //员工姓名
	private String phone;  //联系电话
	private String remark;    //备注

	private Cellstationemployee cellstationemployee;
	private List<Cellstationemployee> cellstationemployeelist;
	
	private Employee stationer; //驻点人员

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStationcode() {
		return stationcode;
	}

	public void setStationcode(String stationcode) {
		this.stationcode = stationcode;
	}

	public String getExtendcode() {
		return extendcode;
	}

	public void setExtendcode(String extendcode) {
		this.extendcode = extendcode;
	}

	public String getCellcode() {
		return cellcode;
	}

	public void setCellcode(String cellcode) {
		this.cellcode = cellcode;
	}

	public String getCellname() {
		return cellname;
	}

	public void setCellname(String cellname) {
		this.cellname = cellname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmployeetype() {
		return employeetype;
	}
	
	public String getEmployeetypename() {
		if("talker".equals(employeetype)){
			return "讲解人员";
		}else if("visitor".equals(employeetype)){
			return "测量人员";
		}else{
			return "";
		}
	}

	public void setEmployeetype(String employeetype) {
		this.employeetype = employeetype;
	}

	public String getEmployeecode() {
		return employeecode;
	}

	public void setEmployeecode(String employeecode) {
		this.employeecode = employeecode;
	}

	public String getEmployeename() {
		return employeename;
	}

	public void setEmployeename(String employeename) {
		this.employeename = employeename;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Cellstationemployee getCellstationemployee() {
		return cellstationemployee;
	}

	public void setCellstationemployee(Cellstationemployee cellstationemployee) {
		this.cellstationemployee = cellstationemployee;
	}

	public List<Cellstationemployee> getCellstationemployeelist() {
		return cellstationemployeelist;
	}

	public void setCellstationemployeelist(
			List<Cellstationemployee> cellstationemployeelist) {
		this.cellstationemployeelist = cellstationemployeelist;
	}

	public Employee getStationer() {
		return stationer;
	}

	public void setStationer(Employee stationer) {
		this.stationer = stationer;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

}
