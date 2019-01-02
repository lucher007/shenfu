package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * 用户实体类
 */
public class Userdispatch extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String dispatchcode; //工单号
	private String usercode; // 客户编号
	private String username; //客户姓名
	private String usersex; // 客户性别(0-女；1-男)
	private String phone; // 联系电话
	private String address; //安装地址
	private String source;   //客户来源（0-销售；1-400）
	private String ordercode;   //订单号
	private String installercode; //安装人编号
	private String operatorcode; //操作员
	private String addtime;    //派单日期
	private String installtime; //安装日期
	private String content;                   //派工内容
	private String dealdate;                  //处理时间
	private String dealresult;                //处理结果
	private String status;                    //状态（0-未派单；1-已派单；2-处理中；3-处理成功；4-处理失败；5-工单完成）
	private String checkstatus;               //审核状态（0-未审核；1-已审核）
	private String checkresult;               //审核结果
	private String validstatus;               //是否有效(1-有效；0-无效)
	private String remark; // 备注

	private Userdispatch userdispatch;
	private List<Userdispatch> userdispatchlist;
	
    private User user;
    private Employee installer;
    private Userorder userorder;
    private Operator operator;
    private String color;
    private String model;
    private String userproductJson;
    private String installername;
    
    private String paytype; //支付方式
    private MultipartFile[] files;  //页面传递参数文件

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

	public String getInstalltime() {
		return installtime;
	}

	public void setInstalltime(String installtime) {
		this.installtime = installtime;
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
	
	public String getStatusname() {
		if("0".equals(status)){
			return "未派工";
		}else if("1".equals(status)){
			return "已派工";
		}else if("2".equals(status)){
			return "已接收";
		}else if("3".equals(status)){
			return "处理成功";
		}else if("4".equals(status)){
			return "处理失败";
		}else if("5".equals(status)){
			return "审核通过";
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Employee getInstaller() {
		return installer;
	}

	public void setInstaller(Employee installer) {
		this.installer = installer;
	}

	public Userorder getUserorder() {
		return userorder;
	}

	public void setUserorder(Userorder userorder) {
		this.userorder = userorder;
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getUserproductJson() {
		return userproductJson;
	}

	public void setUserproductJson(String userproductJson) {
		this.userproductJson = userproductJson;
	}

	public String getCheckresult() {
		return checkresult;
	}

	public void setCheckresult(String checkresult) {
		this.checkresult = checkresult;
	}

	public String getInstallername() {
		return installername;
	}

	public void setInstallername(String installername) {
		this.installername = installername;
	}

	public String getCheckstatus() {
		return checkstatus;
	}
	
	public String getCheckstatusname() {
		if("0".equals(checkstatus)){
			return "未审核";
		}else if("1".equals(checkstatus)){
			return "已审核";
		}else{
			return "";
		}
	}

	public void setCheckstatus(String checkstatus) {
		this.checkstatus = checkstatus;
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

	public String getDispatchcode() {
		return dispatchcode;
	}

	public void setDispatchcode(String dispatchcode) {
		this.dispatchcode = dispatchcode;
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getOrdercode() {
		return ordercode;
	}

	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}

	public String getInstallercode() {
		return installercode;
	}

	public void setInstallercode(String installercode) {
		this.installercode = installercode;
	}

	public String getOperatorcode() {
		return operatorcode;
	}

	public void setOperatorcode(String operatorcode) {
		this.operatorcode = operatorcode;
	}

	public Userdispatch getUserdispatch() {
		return userdispatch;
	}

	public void setUserdispatch(Userdispatch userdispatch) {
		this.userdispatch = userdispatch;
	}

	public List<Userdispatch> getUserdispatchlist() {
		return userdispatchlist;
	}

	public void setUserdispatchlist(List<Userdispatch> userdispatchlist) {
		this.userdispatchlist = userdispatchlist;
	}

	public String getValidstatus() {
		return validstatus;
	}

	public void setValidstatus(String validstatus) {
		this.validstatus = validstatus;
	}

	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}
    
	
	

}
