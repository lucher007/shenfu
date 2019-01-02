package com.sykj.shenfu.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 用户实体类
 */
public class Usercomplaint extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String complaintcode; //投诉单号
	private String usercode; // 客户编号
	private String complaintercode; // 投诉人工号
    private String operatorcode; //操作员ID
    private String addtime;     //投诉时间
    private String content;     //投诉内容
    private String dealercode;   //处理人工号
    private String dealdate;   //处理时间
    private String dealresult;  //处理结果
    private String status;      //状态（0-未受理；1-受理中；2-已处理；3-处理失败； 4-结单）
	private String remark; // 备注
	
	private Usercomplaint usercomplaint;
	private List<Usercomplaint> usercomplaintlist;
	
    private User user;            //订户
    private Employee complainter; //投诉人
    private Employee dealer;      //处理人
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getDealdate() {
		return dealdate;
	}
	public void setDealdate(String dealdate) {
		this.dealdate = dealdate;
	}
	public String getDealresult() {
		return dealresult;
	}
	public void setDealresult(String dealresult) {
		this.dealresult = dealresult;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Usercomplaint getUsercomplaint() {
		return usercomplaint;
	}
	public void setUsercomplaint(Usercomplaint usercomplaint) {
		this.usercomplaint = usercomplaint;
	}
	public List<Usercomplaint> getUsercomplaintlist() {
		return usercomplaintlist;
	}
	public void setUsercomplaintlist(List<Usercomplaint> usercomplaintlist) {
		this.usercomplaintlist = usercomplaintlist;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Employee getComplainter() {
		return complainter;
	}
	public void setComplainter(Employee complainter) {
		this.complainter = complainter;
	}
	public Employee getDealer() {
		return dealer;
	}
	public void setDealer(Employee dealer) {
		this.dealer = dealer;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getComplaintcode() {
		return complaintcode;
	}
	public void setComplaintcode(String complaintcode) {
		this.complaintcode = complaintcode;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getComplaintercode() {
		return complaintercode;
	}
	public void setComplaintercode(String complaintercode) {
		this.complaintercode = complaintercode;
	}
	public String getOperatorcode() {
		return operatorcode;
	}
	public void setOperatorcode(String operatorcode) {
		this.operatorcode = operatorcode;
	}
	public String getDealercode() {
		return dealercode;
	}
	public void setDealercode(String dealercode) {
		this.dealercode = dealercode;
	}
	
	
    

}
