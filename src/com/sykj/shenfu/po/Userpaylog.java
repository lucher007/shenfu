package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 */
public class Userpaylog extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;      // 数据库ID
	private String usercode; // 订户编号
	private String username;  // 客户姓名
	private String phone; //联系电话
	private String address; //安装地址
	private String source; //客户来源（0-销售；1-400）
	private String ordercode; //开发商
	private String paytype;  //付款方式(0-现金;1-微信在线;2-支付宝在线；3-微信静态码；4-支付宝静态码；5-POS机刷卡)
	private String payitem;      //(1-首付款；2-尾款)
	private Integer paymoney;      //付款金额
	private String receivercode;  //付款接收人
	private String paytime;   //付款时间
	private String checkercode; //到账审核人
	private String checktime; //审核时间
	private String checkstatus; //审核状态
	private String paysource; //支付来源
	private String remark;    //备注
	

	private Userpaylog userpaylog;
	private List<Userpaylog> userpayloglist;
	
	//页面查询条件
	private String beginpaytime;
	private String endpaytime;
	private String beginchecktime;
	private String endchecktime;
	private Integer totalmoney; //总金额
	private Integer totalcount; //总次数
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
		} else {
			return "";
		}
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getOrdercode() {
		return ordercode;
	}
	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}
	public String getPaytype() {
		return paytype;
	}
	public String getPaytypename() {
		if("0".equals(this.paytype)){
			return "现金";
		}else if("1".equals(this.paytype)){
			return "微信在线";
		}else if("2".equals(this.paytype)){
			return "支付宝在线";
		}else if("3".equals(this.paytype)){
			return "微信静态码";
		}else if("4".equals(this.paytype)){
			return "支付宝静态码";
		}else if("5".equals(this.paytype)){
			return "POS机刷卡";
		} else {
			return "";
		}
	}
	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}
	public String getPayitem() {
		return payitem;
	}
	public String getPayitemname() {
		if("1".equals(this.payitem)){
			return "首付款";
		}else if("2".equals(this.payitem)){
			return "尾款";
		} else {
			return "";
		}
	}
	public Integer getTotalcount() {
		return totalcount;
	}
	public void setTotalcount(Integer totalcount) {
		this.totalcount = totalcount;
	}
	public void setPayitem(String payitem) {
		this.payitem = payitem;
	}
	public Integer getPaymoney() {
		return paymoney;
	}
	public void setPaymoney(Integer paymoney) {
		this.paymoney = paymoney;
	}
	public String getPaytime() {
		return paytime;
	}
	public void setPaytime(String paytime) {
		this.paytime = paytime;
	}
	public String getChecktime() {
		return checktime;
	}
	public void setChecktime(String checktime) {
		this.checktime = checktime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Userpaylog getUserpaylog() {
		return userpaylog;
	}
	public void setUserpaylog(Userpaylog userpaylog) {
		this.userpaylog = userpaylog;
	}
	public List<Userpaylog> getUserpayloglist() {
		return userpayloglist;
	}
	public void setUserpayloglist(List<Userpaylog> userpayloglist) {
		this.userpayloglist = userpayloglist;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getReceivercode() {
		return receivercode;
	}
	public void setReceivercode(String receivercode) {
		this.receivercode = receivercode;
	}
	public String getCheckercode() {
		return checkercode;
	}
	public void setCheckercode(String checkercode) {
		this.checkercode = checkercode;
	}
	public String getCheckstatus() {
		return checkstatus;
	}
	public String getCheckstatusname() {
		if("0".equals(this.checkstatus)){
			return "已审核";
		}else if("1".equals(this.checkstatus)){
			return "未审核";
		} else {
			return "";
		}
	}
	public void setCheckstatus(String checkstatus) {
		this.checkstatus = checkstatus;
	}
	public String getBeginpaytime() {
		return beginpaytime;
	}
	public void setBeginpaytime(String beginpaytime) {
		this.beginpaytime = beginpaytime;
	}
	public String getEndpaytime() {
		return endpaytime;
	}
	public void setEndpaytime(String endpaytime) {
		this.endpaytime = endpaytime;
	}
	public String getBeginchecktime() {
		return beginchecktime;
	}
	public void setBeginchecktime(String beginchecktime) {
		this.beginchecktime = beginchecktime;
	}
	public String getEndchecktime() {
		return endchecktime;
	}
	public void setEndchecktime(String endchecktime) {
		this.endchecktime = endchecktime;
	}
	public Integer getTotalmoney() {
		return totalmoney;
	}
	public void setTotalmoney(Integer totalmoney) {
		this.totalmoney = totalmoney;
	}
	public String getPaysource() {
		return paysource;
	}
	public String getPaysourcename() {
		if("0".equals(paysource)){
			return "APP端支付";
		}else if("1".equals(paysource)){
			return "微信端支付";
		}else{
			return "";
		}
	}
	public void setPaysource(String paysource) {
		this.paysource = paysource;
	}
	
	

}
