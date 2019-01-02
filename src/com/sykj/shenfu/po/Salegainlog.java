package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 */
public class Salegainlog extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String gainlogcode; //提成提取编号
	private String gainercode; //领取提成员工编号
	private Integer gainmoney; //总金额
	private String gaintime; //提取时间
	private String operatorcode;  //操作员
	private String gaintype;  //提成方式
	private String status;   //状态(0-未到账；1-已到账)
	private String gaincontent; //提现信息
	private String remark; // 备注

	private Salegainlog salegainlog;
	private List<Salegainlog> salegainloglist;
    private Employee gainer;
    
   //---页面查询条件
    private String begintime;
  	private String endtime;
  	private String gaincontentflag;//是否有提现信息(0-无信息；1-有信息)
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGainlogcode() {
		return gainlogcode;
	}
	public void setGainlogcode(String gainlogcode) {
		this.gainlogcode = gainlogcode;
	}
	public String getGainercode() {
		return gainercode;
	}
	public void setGainercode(String gainercode) {
		this.gainercode = gainercode;
	}
	public Integer getGainmoney() {
		return gainmoney;
	}
	public void setGainmoney(Integer gainmoney) {
		this.gainmoney = gainmoney;
	}
	public String getGaintime() {
		return gaintime;
	}
	public void setGaintime(String gaintime) {
		this.gaintime = gaintime;
	}
	public String getOperatorcode() {
		return operatorcode;
	}
	public void setOperatorcode(String operatorcode) {
		this.operatorcode = operatorcode;
	}
	public String getGaintype() {
		return gaintype;
	}
	public void setGaintype(String gaintype) {
		this.gaintype = gaintype;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Salegainlog getSalegainlog() {
		return salegainlog;
	}
	public void setSalegainlog(Salegainlog salegainlog) {
		this.salegainlog = salegainlog;
	}
	public List<Salegainlog> getSalegainloglist() {
		return salegainloglist;
	}
	public void setSalegainloglist(List<Salegainlog> salegainloglist) {
		this.salegainloglist = salegainloglist;
	}
	public Employee getGainer() {
		return gainer;
	}
	public void setGainer(Employee gainer) {
		this.gainer = gainer;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getStatus() {
		return status;
	}
	public String getStatusname() {
		if("0".equals(status)){
			return "未到账";
		}else if("1".equals(status)){
			return "已到账";
		}else{
			return "";
		}
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getGaincontent() {
		return gaincontent;
	}
	public void setGaincontent(String gaincontent) {
		this.gaincontent = gaincontent;
	}
	public String getGaincontentflag() {
		return gaincontentflag;
	}
	public void setGaincontentflag(String gaincontentflag) {
		this.gaincontentflag = gaincontentflag;
	}
    

}
