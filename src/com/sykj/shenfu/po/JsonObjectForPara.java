package com.sykj.shenfu.po;

public class JsonObjectForPara {
	private String ordercode;
	private String payitem;
	private String paymoney;
	private String paytype;
	private String payitemname;
	private String operatorcode;
	private String receivercode;
	private String jumpurl; //页面跳转 
	
	private String usercode;
	
	public String getOrdercode() {
		return ordercode;
	}
	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}
	public String getPayitem() {
		return payitem;
	}
	public void setPayitem(String payitem) {
		this.payitem = payitem;
	}
	public String getPaymoney() {
		return paymoney;
	}
	public void setPaymoney(String paymoney) {
		this.paymoney = paymoney;
	}
	public String getPaytype() {
		return paytype;
	}
	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}
	public String getPayitemname() {
		return payitemname;
	}
	public void setPayitemname(String payitemname) {
		this.payitemname = payitemname;
	}
	public String getOperatorcode() {
		return operatorcode;
	}
	public void setOperatorcode(String operatorcode) {
		this.operatorcode = operatorcode;
	}
	public String getJumpurl() {
		return jumpurl;
	}
	public void setJumpurl(String jumpurl) {
		this.jumpurl = jumpurl;
	}
	public String getReceivercode() {
		return receivercode;
	}
	public void setReceivercode(String receivercode) {
		this.receivercode = receivercode;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	@Override
	public String toString() {
		return "JsonObjectForPara [ordercode=" + ordercode + ", payitem="
				+ payitem + ", paymoney=" + paymoney + ", paytype=" + paytype
				+ ", payitemname=" + payitemname + ", operatorcode="
				+ operatorcode + ", receivercode=" + receivercode
				+ ", jumpurl=" + jumpurl + ", usercode=" + usercode + "]";
	}
	
	
}
