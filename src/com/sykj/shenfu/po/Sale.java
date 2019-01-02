package com.sykj.shenfu.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 用户实体类
 */
public class Sale extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String salecode; // 销售单号
	private String usercode; // 客户ID
	private String username; //客户姓名
	private String usersex; // 客户性别(0-女；1-男)
	private String phone; // 联系电话
	private String address; //安装地址
	private String source;  //客户来源（0-销售；1-400）
	private String salercode;  //销售人员ID
	private String operatorcode; //操作员ID
	private String intention;  //安装意向
	private String status; //状态(0-洽谈中；1-完成；2-生成任务单；3-销售失败)
	private String addtasktime; //生成任务单时间
	private String content; //内容
	private String addtime; //添加时间
	private BigDecimal totalmoney; //总金额
	private BigDecimal firstpayment;  //已付金额
	private BigDecimal finalpayment;//未付金额
	private String remark; // 备注

	private Sale sale;
	private List<Sale> saleklist;
	
    private User user;
    private Employee saler;
    private Operator operator;
    //销售总量统计
    private String querystarttime;
    private String queryendtime;
    private Integer saletotal; //个人销售总量
    private BigDecimal saletotalmoney; //个人销售总量
    private String opertime;   //统计时间方式
    //每月统计MAP
    private Map<String,Integer> personaltotalmap;
    private Map<String,BigDecimal> personaltotalmoneymap;
    private String salername;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public void setSource(String source) {
		this.source = source;
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
	public void setStatus(String status) {
		this.status = status;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public Sale getSale() {
		return sale;
	}
	public void setSale(Sale sale) {
		this.sale = sale;
	}
	public List<Sale> getSaleklist() {
		return saleklist;
	}
	public void setSaleklist(List<Sale> saleklist) {
		this.saleklist = saleklist;
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
	public Operator getOperator() {
		return operator;
	}
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getAddtasktime() {
		return addtasktime;
	}
	public void setAddtasktime(String addtasktime) {
		this.addtasktime = addtasktime;
	}
	public Integer getSaletotal() {
		return saletotal;
	}
	public void setSaletotal(Integer saletotal) {
		this.saletotal = saletotal;
	}
	public String getQuerystarttime() {
		return querystarttime;
	}
	public void setQuerystarttime(String querystarttime) {
		this.querystarttime = querystarttime;
	}
	public String getQueryendtime() {
		return queryendtime;
	}
	public void setQueryendtime(String queryendtime) {
		this.queryendtime = queryendtime;
	}
	public String getOpertime() {
		return opertime;
	}
	public void setOpertime(String opertime) {
		this.opertime = opertime;
	}
	public Map<String, Integer> getPersonaltotalmap() {
		return personaltotalmap;
	}
	public void setPersonaltotalmap(Map<String, Integer> personaltotalmap) {
		this.personaltotalmap = personaltotalmap;
	}
	public String getSalername() {
		return salername;
	}
	public void setSalername(String salername) {
		this.salername = salername;
	}
	public BigDecimal getTotalmoney() {
		return totalmoney;
	}
	public void setTotalmoney(BigDecimal totalmoney) {
		this.totalmoney = totalmoney;
	}
	public BigDecimal getFirstpayment() {
		return firstpayment;
	}
	public void setFirstpayment(BigDecimal firstpayment) {
		this.firstpayment = firstpayment;
	}
	public BigDecimal getFinalpayment() {
		return finalpayment;
	}
	public void setFinalpayment(BigDecimal finalpayment) {
		this.finalpayment = finalpayment;
	}
	public BigDecimal getSaletotalmoney() {
		return saletotalmoney;
	}
	public void setSaletotalmoney(BigDecimal saletotalmoney) {
		this.saletotalmoney = saletotalmoney;
	}
	public Map<String, BigDecimal> getPersonaltotalmoneymap() {
		return personaltotalmoneymap;
	}
	public void setPersonaltotalmoneymap(
			Map<String, BigDecimal> personaltotalmoneymap) {
		this.personaltotalmoneymap = personaltotalmoneymap;
	}
	public String getSalecode() {
		return salecode;
	}
	public void setSalecode(String salecode) {
		this.salecode = salecode;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getSalercode() {
		return salercode;
	}
	public void setSalercode(String salercode) {
		this.salercode = salercode;
	}
	public String getOperatorcode() {
		return operatorcode;
	}
	public void setOperatorcode(String operatorcode) {
		this.operatorcode = operatorcode;
	}
    
	

}
