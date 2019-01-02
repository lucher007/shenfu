package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

import fr.opensagres.xdocreport.core.utils.StringUtils;

/**
 * 用户实体类
 */
public class Wechatpay extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;      // 数据库ID
	private String mchid;    //商户号
	private String openid;   // 用户标识
	private String issubscribe;  //是否关注公众账号
	private String outtradeno; //商户订单号
	private String tradetype; //交易类型
	private String totalfee; //订单金额
	private String transactionid; //微信支付订单号
	private String attach;  //商家数据包
	private String timeend;      //支付完成时间
	private String ordercode;      //订单编号
	private String payitem;  //(1-首付款；2-尾款)
	private String operatorcode;   //操作员编号
	private String paytype;   //付款方式(0-现金;1-微信在线;2-支付宝在线；3-微信静态码；4-支付宝静态码；5-POS机刷卡)
	private String paysource;     //支付来源(0-APP端支付;1-微信公众号支付)
	private String remark;    //备注

	private Wechatpay wechatpay;
	private List<Wechatpay> wechatpaylist;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMchid() {
		return mchid;
	}
	public void setMchid(String mchid) {
		this.mchid = mchid;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getIssubscribe() {
		return issubscribe;
	}
	public void setIssubscribe(String issubscribe) {
		this.issubscribe = issubscribe;
	}
	public String getOuttradeno() {
		return outtradeno;
	}
	public void setOuttradeno(String outtradeno) {
		this.outtradeno = outtradeno;
	}
	public String getTradetype() {
		return tradetype;
	}
	public void setTradetype(String tradetype) {
		this.tradetype = tradetype;
	}
	public String getTotalfee() {
		return totalfee;
	}
	public void setTotalfee(String totalfee) {
		this.totalfee = totalfee;
	}
	public String getTransactionid() {
		return transactionid;
	}
	public void setTransactionid(String transactionid) {
		this.transactionid = transactionid;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getTimeend() {
		return timeend;
	}
	public void setTimeend(String timeend) {
		this.timeend = timeend;
	}
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
	public String getOperatorcode() {
		return operatorcode;
	}
	public void setOperatorcode(String operatorcode) {
		this.operatorcode = operatorcode;
	}
	public String getPaytype() {
		return paytype;
	}
	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}
	public String getPaysource() {
		return paysource;
	}
	public void setPaysource(String paysource) {
		this.paysource = paysource;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Wechatpay getWechatpay() {
		return wechatpay;
	}
	public void setWechatpay(Wechatpay wechatpay) {
		this.wechatpay = wechatpay;
	}
	public List<Wechatpay> getWechatpaylist() {
		return wechatpaylist;
	}
	public void setWechatpaylist(List<Wechatpay> wechatpaylist) {
		this.wechatpaylist = wechatpaylist;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
