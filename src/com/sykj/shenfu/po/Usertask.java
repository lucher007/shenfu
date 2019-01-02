package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 */
public class Usertask extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String taskcode; // 任务单号
	private String ordercode; //订单编号
	private String usercode; // 客户编号
	private String username; //客户姓名
	private String usersex; // 客户性别(0-女；1-男)
	private String phone; // 联系电话
	private String address; //安装地址
	private String source;  //客户来源（0-销售APP；1-400客服；2-小区驻点）
	private String visittype; //上门类型（0.公司派人讲解测量，1.销售员亲自讲解测量; 2-已讲解，公司派人只需测量）;
	private String salercode;  //销售单编号
	private String talkercode; //讲解员编号
	private String visitorcode;  //上门员编号
	private String operatorcode; //操作员
	private String visitstate;//访问状态（默认为0，若拒绝安装则改为1）
	private String visittime; //上门时间
	private String intention;  //安装意向
	private String status; //状态(0-未处理；1:成功处理；10-处理失败)
	private String addtime; //添加时间
	private String dealdate;//处理时间
	private String dealresult;//处理结果
	private String tasktype;  //上门类型(0-讲解；1-测量)
	private String remark; // 备注

	private Usertask usertask;
	private List<Usertask> usertasklist;
	
    private User user;
    private Employee saler;
    private String salername;  //销售员姓名
    private String salerphone;  //销售员电话
    private Employee talker;
    private String talkername; //讲解员姓名
    private String talkerphone;//讲解员电话
    private Employee visitor;
    private String visitorname;  //上门员姓名
    private String visitorphone;  //上门员电话
    private Operator operator;
    
    
    private String statusformps;
    private String jumpurl; //跳转页面
    private String querystateflag; //0-未处理；1-已处理
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTaskcode() {
		return taskcode;
	}
	public void setTaskcode(String taskcode) {
		this.taskcode = taskcode;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsersex() {
		return usersex;
	}
	public String getUsersexname() {
		if("0".equals(this.usersex)){
			return "女";
		}else if("1".equals(this.usersex)){
			return "男";
		} else {
			return "";
		}
	}
	public void setUsersex(String usersex) {
		this.usersex = usersex;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSource() {
		return source;
	}
	public String getSourcename() {
		if("0".equals(this.source)){
			return "销售";
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
	public String getSalercode() {
		return salercode;
	}
	public void setSalercode(String salercode) {
		this.salercode = salercode;
	}
	public String getVisitorcode() {
		return visitorcode;
	}
	public void setVisitorcode(String visitorcode) {
		this.visitorcode = visitorcode;
	}
	public String getVisitstate() {
		return visitstate;
	}
	public String getVisitstatename() {
		if("1".equals(this.visitstate)){
			return "拒绝安装";
		} else if("0".equals(this.visitstate)){
			return "同意安装";
		}else{
			return "";
		}
	}
	public void setVisitstate(String visitstate) {
		this.visitstate = visitstate;
	}
	public String getIntention() {
		return intention;
	}
	public void setIntention(String intention) {
		this.intention = intention;
	}
	public String getStatus() {
		return status;
	}
	public String getStatusname() {
		if("0".equals(this.status)){
			return "未处理";
		}else if("1".equals(this.status)){
			return "处理成功";
		}else if("10".equals(this.status)){
			return "处理失败";
		}else{
			return "";
		}
	}
	public void setStatus(String status) {
		this.status = status;
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Employee getSaler() {
		return saler;
	}
	public void setSaler(Employee saler) {
		this.saler = saler;
	}
	public Employee getVisitor() {
		return visitor;
	}
	public void setVisitor(Employee visitor) {
		this.visitor = visitor;
	}
	public Operator getOperator() {
		return operator;
	}
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Usertask getUsertask() {
		return usertask;
	}
	public void setUsertask(Usertask usertask) {
		this.usertask = usertask;
	}
	public List<Usertask> getUsertasklist() {
		return usertasklist;
	}
	public void setUsertasklist(List<Usertask> usertasklist) {
		this.usertasklist = usertasklist;
	}
	public String getOperatorcode() {
		return operatorcode;
	}
	public void setOperatorcode(String operatorcode) {
		this.operatorcode = operatorcode;
	}
	public String getStatusformps() {
		return statusformps;
	}
	public void setStatusformps(String statusformps) {
		this.statusformps = statusformps;
	}
	public String getJumpurl() {
		return jumpurl;
	}
	public void setJumpurl(String jumpurl) {
		this.jumpurl = jumpurl;
	}
	public String getVisitorname() {
		return visitorname;
	}
	public void setVisitorname(String visitorname) {
		this.visitorname = visitorname;
	}
	public String getVisitorphone() {
		return visitorphone;
	}
	public void setVisitorphone(String visitorphone) {
		this.visitorphone = visitorphone;
	}
	public String getSalername() {
		return salername;
	}
	public void setSalername(String salername) {
		this.salername = salername;
	}
	public String getSalerphone() {
		return salerphone;
	}
	public void setSalerphone(String salerphone) {
		this.salerphone = salerphone;
	}
	public String getVisittype() {
		return visittype;
	}
	public String getVisittypename() {
		if("0".equals(this.visittype)){
			return "公司派人讲解";
		} else if("1".equals(this.visittype)){
			return "亲自讲解";
		} else if("2".equals(this.visittype)){
			return "已讲解，公司派人测量";
		} else {
			return "";
		}
	}
	public void setVisittype(String visittype) {
		this.visittype = visittype;
	}
	public String getDealdate() {
		return dealdate;
	}
	public void setDealdate(String dealdate) {
		this.dealdate = dealdate;
	}
	public String getTalkercode() {
		return talkercode;
	}
	public void setTalkercode(String talkercode) {
		this.talkercode = talkercode;
	}
	public String getOrdercode() {
		return ordercode;
	}
	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}
	public Employee getTalker() {
		return talker;
	}
	public void setTalker(Employee talker) {
		this.talker = talker;
	}
	public String getTalkername() {
		return talkername;
	}
	public void setTalkername(String talkername) {
		this.talkername = talkername;
	}
	public String getTalkerphone() {
		return talkerphone;
	}
	public void setTalkerphone(String talkerphone) {
		this.talkerphone = talkerphone;
	}
	public String getDealresult() {
		return dealresult;
	}
	public void setDealresult(String dealresult) {
		this.dealresult = dealresult;
	}
	public String getVisittime() {
		return visittime;
	}
	public void setVisittime(String visittime) {
		this.visittime = visittime;
	}
	public String getTasktype() {
		return tasktype;
	}
	public String getTasktypename() {
		if("0".equals(tasktype)){
			return "讲解单";
		}else if("1".equals(tasktype)){
			return "测量单";
		}else{
			return "";
		}
	}
	public void setTasktype(String tasktype) {
		this.tasktype = tasktype;
	}
	public String getQuerystateflag() {
		return querystateflag;
	}
	public void setQuerystateflag(String querystateflag) {
		this.querystateflag = querystateflag;
	}

}
