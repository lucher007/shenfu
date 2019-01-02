package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 用户实体类
 */
public class Operator extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String employeecode; //员工编号
	private String loginname; // 登录账号
	private String password; // 登录密码
	private String operatortype; // 操作员类型（0-超级管理人员；1-操作员;2-施工人员）
	private String addtime; // 添加时间
	private String status; // 操作员状态(0:无效、1:有效)
	private String remark; // 备注

	private Operator operator;
	private List<Operator> operatorlist;
	
	private Employee employee; //对应的员工

	private Map<Integer, String> rolemap;

	/****************** 页面查询条件辅助字段 *************************/
	private int rember; // 是否记住密码
	private String logincode; // 登录验证码
	private String confirmpassword; // 确认密码
	private String jumping; // 跳转标识符
	private Integer roleid;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOperatortype() {
		return operatortype;
	}
	public String getOperatortypename() {
		if("0".equals(operatortype)){
			return "系统超级管理员";
		}else if("1".equals(operatortype)){
			return "操作员";
		}else{
			return "";
		}
	}
	public void setOperatortype(String operatortype) {
		this.operatortype = operatortype;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getStatus() {
		return status;
	}
	public String getStatusname() {
		if("0".equals(status)){
			return "无效";
		}else if("1".equals(status)){
			return "有效";
		}else{
			return "";
		}
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
	public Operator getOperator() {
		return operator;
	}
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	public List<Operator> getOperatorlist() {
		return operatorlist;
	}
	public void setOperatorlist(List<Operator> operatorlist) {
		this.operatorlist = operatorlist;
	}
	public Employee getEmployee() {
		if(employee == null ){
			employee = new Employee();
		}
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Map<Integer, String> getRolemap() {
		return rolemap;
	}
	public void setRolemap(Map<Integer, String> rolemap) {
		this.rolemap = rolemap;
	}
	public int getRember() {
		return rember;
	}
	public void setRember(int rember) {
		this.rember = rember;
	}
	public String getLogincode() {
		return logincode;
	}
	public void setLogincode(String logincode) {
		this.logincode = logincode;
	}
	public String getConfirmpassword() {
		return confirmpassword;
	}
	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}
	public String getJumping() {
		return jumping;
	}
	public void setJumping(String jumping) {
		this.jumping = jumping;
	}
	public Integer getRoleid() {
		return roleid;
	}
	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getEmployeecode() {
		return employeecode;
	}
	public void setEmployeecode(String employeecode) {
		this.employeecode = employeecode;
	}
}
