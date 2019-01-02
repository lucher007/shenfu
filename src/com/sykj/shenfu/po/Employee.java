package com.sykj.shenfu.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 用户实体类
 */
public class Employee extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String employeecode; //员工编号
	private String employeename; // 员工姓名
	private String employeesex;  // 员工性别(0-女；1-男)
	private String birthday;     // 员工生日
	private String identification; // 身份证号
	private String email; // 电子邮件
	private String address; // 地址
	private String phone;   // 电话
	private String level;   // 员工等级
	private String status;  // 员工状态(0-离职；1-在职)
	private String addtime; // 添加时间
	private String department; //员工部门（0-管理部；1-系统部；2-销售部；3-装维部；4-仓管部；5-400客服；6-财务部；
	private Integer salescore; //销售积分
	private String apppassword;  //登录APP密码
	private String employeetype; //员工类型(0-兼职；1-全职)
	private String bankcardno;     //用户银行账户卡号
	private String bankcardname;   //用户银行账户名称
	private String parentemployeecode;  //上级介绍人编号
	private String rechargevipcode; //充值VIP编码
	private String extendcode; //充值VIP编码
	private String managercode; //销售管家编号
	private String remark; // 备注

	private Employee employee;
	private List<Employee> employeelist;
	
	private String selecttype;
	private String confirmpassword;
	
	private String parentemployeename;//上级介绍人名称
	private Employee parentemployee;//上级介绍人对象
	private String jumpurl; //跳转页面
	
	private Integer totalmoney; //总金额
	private Integer totalcount; //总次数
	private Integer ranking;    //排名
	private String managername;//销售管家姓名
	private Employee manager;//销售管家
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmployeename() {
		return employeename;
	}
	public void setEmployeename(String employeename) {
		this.employeename = employeename;
	}
	public String getEmployeesex() {
		return employeesex;
	}
	public String getEmployeesexname() {
		if("0".equals(this.employeesex)){
			return "女";
		}else if("1".equals(this.employeesex)){
			return "男";
		} else {
			return "";
		}
	}
	public void setEmployeesex(String employeesex) {
		this.employeesex = employeesex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getIdentification() {
		return identification;
	}
	public void setIdentification(String identification) {
		this.identification = identification;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusname() {
		if("0".equals(this.status)){
			return "离职";
		}else if("1".equals(this.status)){
			return "在职";
		} else {
			return "";
		}
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
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public List<Employee> getEmployeelist() {
		return employeelist;
	}
	public void setEmployeelist(List<Employee> employeelist) {
		this.employeelist = employeelist;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getSelecttype() {
		return selecttype;
	}
	public void setSelecttype(String selecttype) {
		this.selecttype = selecttype;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDepartmentname() {
		if("0".equals(this.department)){
			return "管理部";
		}else if("1".equals(this.department)){
			return "系统部";
		}else if("2".equals(this.department)){
			return "销售部";
		}else if("3".equals(this.department)){
			return "装维部";
		}else if("4".equals(this.department)){
			return "生产部";
		}else if("5".equals(this.department)){
			return "400客服";
		}else if("6".equals(this.department)){
			return "财务部";
		}else{
			return "";
		}
	}
	public Integer getSalescore() {
		return salescore;
	}
	public void setSalescore(Integer salescore) {
		this.salescore = salescore;
	}
	public String getApppassword() {
		return apppassword;
	}
	public void setApppassword(String apppassword) {
		this.apppassword = apppassword;
	}
	public String getEmployeetype() {
		return employeetype;
	}
	public void setEmployeetype(String employeetype) {
		this.employeetype = employeetype;
	}
	public String getEmployeetypename() {
		if("0".equals(this.employeetype)){
			return "兼职";
		}else if("1".equals(this.employeetype)){
			return "全职";
		}else{
			return "";
		}
	}
	public String getBankcardno() {
		return bankcardno;
	}
	public void setBankcardno(String bankcardno) {
		this.bankcardno = bankcardno;
	}
	public String getBankcardname() {
		return bankcardname;
	}
	public void setBankcardname(String bankcardname) {
		this.bankcardname = bankcardname;
	}
	public String getConfirmpassword() {
		return confirmpassword;
	}
	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}
	public Employee getParentemployee() {
		return parentemployee;
	}
	public void setParentemployee(Employee parentemployee) {
		this.parentemployee = parentemployee;
	}
	public String getJumpurl() {
		return jumpurl;
	}
	public void setJumpurl(String jumpurl) {
		this.jumpurl = jumpurl;
	}
	public String getEmployeecode() {
		return employeecode;
	}
	public void setEmployeecode(String employeecode) {
		this.employeecode = employeecode;
	}
	public String getParentemployeecode() {
		return parentemployeecode;
	}
	public void setParentemployeecode(String parentemployeecode) {
		this.parentemployeecode = parentemployeecode;
	}
	public String getParentemployeename() {
		return parentemployeename;
	}
	public void setParentemployeename(String parentemployeename) {
		this.parentemployeename = parentemployeename;
	}
	public String getRechargevipcode() {
		return rechargevipcode;
	}
	public void setRechargevipcode(String rechargevipcode) {
		this.rechargevipcode = rechargevipcode;
	}
	public Integer getTotalmoney() {
		return totalmoney;
	}
	public void setTotalmoney(Integer totalmoney) {
		this.totalmoney = totalmoney;
	}
	public Integer getTotalcount() {
		return totalcount;
	}
	public void setTotalcount(Integer totalcount) {
		this.totalcount = totalcount;
	}
	public Integer getRanking() {
		return ranking;
	}
	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}
	public String getExtendcode() {
		return extendcode;
	}
	public void setExtendcode(String extendcode) {
		this.extendcode = extendcode;
	}
	public String getManagercode() {
		return managercode;
	}
	public void setManagercode(String managercode) {
		this.managercode = managercode;
	}
	public String getManagername() {
		return managername;
	}
	public void setManagername(String managername) {
		this.managername = managername;
	}
	public Employee getManager() {
		return manager;
	}
	public void setManager(Employee manager) {
		this.manager = manager;
	}
}
