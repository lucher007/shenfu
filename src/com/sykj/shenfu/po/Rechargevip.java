package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 */
public class Rechargevip extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String rechargevipcode; //充值VIP编码
	private String rechargevipname; //充值VIP名称
	private Integer rechargemoney; //充值金额
	private Integer gaintimes; //提成次数
	private String remark; // 备注

	private Rechargevip rechargevip;
	private List<Rechargevip> rechargeviplist;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRechargevipname() {
		return rechargevipname;
	}
	public void setRechargevipname(String rechargevipname) {
		this.rechargevipname = rechargevipname;
	}
	public Integer getRechargemoney() {
		return rechargemoney;
	}
	public void setRechargemoney(Integer rechargemoney) {
		this.rechargemoney = rechargemoney;
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
	public Rechargevip getRechargevip() {
		return rechargevip;
	}
	public void setRechargevip(Rechargevip rechargevip) {
		this.rechargevip = rechargevip;
	}
	public List<Rechargevip> getRechargeviplist() {
		return rechargeviplist;
	}
	public void setRechargeviplist(List<Rechargevip> rechargeviplist) {
		this.rechargeviplist = rechargeviplist;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getRechargevipcode() {
		return rechargevipcode;
	}
	public void setRechargevipcode(String rechargevipcode) {
		this.rechargevipcode = rechargevipcode;
	}
	
	

}
