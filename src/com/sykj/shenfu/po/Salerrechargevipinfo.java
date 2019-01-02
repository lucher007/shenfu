package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 */
public class Salerrechargevipinfo extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String employeecode; //员工编号
	private String phone; //电话号码
	private String gainmonth; //提成月份
	private Integer gaintimes; //本月已获得提成次数
	private String remark; // 备注

	private Salerrechargevipinfo salerrechargevipinfo;
	private List<Salerrechargevipinfo> salerrechargevipinfolist;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmployeecode() {
		return employeecode;
	}
	public void setEmployeecode(String employeecode) {
		this.employeecode = employeecode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getGainmonth() {
		return gainmonth;
	}
	public void setGainmonth(String gainmonth) {
		this.gainmonth = gainmonth;
	}
	public Integer getGaintimes() {
		return gaintimes;
	}
	public void setGaintimes(Integer gaintimes) {
		this.gaintimes = gaintimes;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Salerrechargevipinfo getSalerrechargevipinfo() {
		return salerrechargevipinfo;
	}
	public void setSalerrechargevipinfo(Salerrechargevipinfo salerrechargevipinfo) {
		this.salerrechargevipinfo = salerrechargevipinfo;
	}
	public List<Salerrechargevipinfo> getSalerrechargevipinfolist() {
		return salerrechargevipinfolist;
	}
	public void setSalerrechargevipinfolist(
			List<Salerrechargevipinfo> salerrechargevipinfolist) {
		this.salerrechargevipinfolist = salerrechargevipinfolist;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
