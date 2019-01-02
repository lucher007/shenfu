package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 */
public class User extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String usercode; //客户编号
	private String username; // 客户姓名
	private String usersex; // 客户性别(0-女；1-男)
	private String phone; // 联系电话
	private String address; //安装地址
	private String source;  //客户来源（0-销售APP；1-400客服；2-小区驻点）
	private String addtime; //添加时间
	private String status; //状态(0-未处理；1-派讲解单 ;2-生成订单；3-订单结单；10-拒绝安装)
	private String visittype; //上门类型（0.公司派人讲解测量，1.亲自讲解测量；2-已讲解-公司派人测量）
	private String checkstatus; //审核状态（0-未审核；1-已审核）
	private String checktime;   //审核时间
	private String salercode;    //销售员编号
	private String dealresult;   //处理结果
	private String needtalker;  //是否需要体验(0-不需要；1-需要)
	private String lockcoreflag; //是否带机械锁心(0-不需要；1-需要)
	private String productcolor; //产品颜色(0101-摩卡棕；0102-石英灰；0103-香槟金)
	private String remark; // 备注
	
	private User user;
	private List<User> userlist;
	private String jumpurl; //跳转页面
	private String salername; //销售员名称
	private Employee saler; //销售员
	private String modelcode;  //产品型号编码
	private String modelname;  //产品型号名称
	
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
	public List<User> getUserlist() {
		return userlist;
	}
	public void setUserlist(List<User> userlist) {
		this.userlist = userlist;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getSource() {
		return source;
	}
	public String getSourcename() {
		if("0".equals(this.source)){
			return "销售APP";
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
	public String getStatus() {
		return status;
	}
	public String getStatusname() {
		if("0".equals(this.status)){
			return "未处理";
		} else if("1".equals(this.status)){
			return "已派讲解单";
		}else if("2".equals(this.status)){
			return "已生成订单";
		}else if("3".equals(this.status)){
			return "订单已结单";
		}else if("10".equals(this.status)){
			return "安装失败";
		} else {
			return "";
		}
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getCheckstatus() {
		return checkstatus;
	}
	public void setCheckstatus(String checkstatus) {
		this.checkstatus = checkstatus;
	}
	public String getChecktime() {
		return checktime;
	}
	public void setChecktime(String checktime) {
		this.checktime = checktime;
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
	public String getJumpurl() {
		return jumpurl;
	}
	public void setJumpurl(String jumpurl) {
		this.jumpurl = jumpurl;
	}
	public String getSalername() {
		return salername;
	}
	public void setSalername(String salername) {
		this.salername = salername;
	}
	public Employee getSaler() {
		return saler;
	}
	public void setSaler(Employee saler) {
		this.saler = saler;
	}
	public String getDealresult() {
		return dealresult;
	}
	public void setDealresult(String dealresult) {
		this.dealresult = dealresult;
	}
	public String getProductcolor() {
		return productcolor;
	}
	public String getProductcolorname() {
		if("0101".equals(this.productcolor)){
			return "摩卡棕";
		} else if("0102".equals(this.productcolor)){
			return "石英灰";
		} else if("0103".equals(this.productcolor)){
			return "香槟金";
		} else {
			return "";
		}
	}
	public void setProductcolor(String productcolor) {
		this.productcolor = productcolor;
	}
	public String getModelcode() {
		return modelcode;
	}
	public void setModelcode(String modelcode) {
		this.modelcode = modelcode;
	}
	public String getModelname() {
		return modelname;
	}
	public void setModelname(String modelname) {
		this.modelname = modelname;
	}
	public String getLockcoreflag() {
		return lockcoreflag;
	}
	public String getLockcoreflagname() {
		if("0".equals(this.lockcoreflag)){
			return "不需要";
		} else if("1".equals(this.lockcoreflag)){
			return "需要";
		} else {
			return "";
		}
	}
	public void setLockcoreflag(String lockcoreflag) {
		this.lockcoreflag = lockcoreflag;
	}
	public String getNeedtalker() {
		return needtalker;
	}
	public void setNeedtalker(String needtalker) {
		this.needtalker = needtalker;
	}
	public String getNeedtalkername() {
		if("0".equals(needtalker)){
			return "不需要";
		} else if("1".equals(needtalker)){
			return "需要";
		} else {
			return "";
		}
	}
}
