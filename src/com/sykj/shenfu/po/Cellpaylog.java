package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 */
public class Cellpaylog extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;      // 数据库ID
	private String extendcode;  //小区发布编号
	private String cellcode; //小区编号
	private String cellname; // 小区名称
	private String address;  // 小区地址
	private Integer totalhouse;  //总户数
	private Integer price; //扫楼单价(以分为单位)
	private Integer totalmoney; //扫楼总价（以分为单位）
	private String extendercode;//发布人
	private String extendtime; //发布时间
	private String starttime; //开始有效时间
	private String endtime;   //结束有效时间
	private String acceptercode; //接收人编号
	private String acceptertime;  //接收时间
	private String payercode;     //付款人编号
	private String paytime;      //支付时间
	private String paytype;      //付款方式(0-现金;1-微信在线;2-支付宝在线；3-微信静态码；4-支付宝静态码)
	private Integer paymoney; //支付金额
	private String paysource;    //支付来源(0-APP端支付;1-微信端支付)
	private String checkercode; //到账审核人
	private String checktime; //审核时间
	private String checkstatus; //审核状态
	private String remark;    //备注

	private Cellpaylog cellpaylog;
	private List<Cellpaylog> cellpayloglist;
	
	private Employee extender; //发布人
	private Employee accepter; //接收者
	
	//页面查询条件
	private String beginpaytime;
	private String endpaytime;
	private String beginchecktime;
	private String endchecktime;
	private Integer paytotalmoney; //支付总金额
	private Integer totalcount; //总次数
	
	private String payername;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCellcode() {
		return cellcode;
	}
	public void setCellcode(String cellcode) {
		this.cellcode = cellcode;
	}
	public String getCellname() {
		return cellname;
	}
	public void setCellname(String cellname) {
		this.cellname = cellname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getTotalhouse() {
		return totalhouse;
	}
	public void setTotalhouse(Integer totalhouse) {
		this.totalhouse = totalhouse;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getTotalmoney() {
		return totalmoney;
	}
	public void setTotalmoney(Integer totalmoney) {
		this.totalmoney = totalmoney;
	}
	public String getExtendercode() {
		return extendercode;
	}
	public void setExtendercode(String extendercode) {
		this.extendercode = extendercode;
	}
	public String getExtendtime() {
		return extendtime;
	}
	public void setExtendtime(String extendtime) {
		this.extendtime = extendtime;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getAcceptercode() {
		return acceptercode;
	}
	public void setAcceptercode(String acceptercode) {
		this.acceptercode = acceptercode;
	}
	public String getAcceptertime() {
		return acceptertime;
	}
	public void setAcceptertime(String acceptertime) {
		this.acceptertime = acceptertime;
	}
	public String getPaytime() {
		return paytime;
	}
	public void setPaytime(String paytime) {
		this.paytime = paytime;
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
		} else {
			return "";
		}
	}
	public void setPaytype(String paytype) {
		this.paytype = paytype;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Cellpaylog getCellpaylog() {
		return cellpaylog;
	}
	public void setCellpaylog(Cellpaylog cellpaylog) {
		this.cellpaylog = cellpaylog;
	}
	public List<Cellpaylog> getCellpayloglist() {
		return cellpayloglist;
	}
	public void setCellpayloglist(List<Cellpaylog> cellpayloglist) {
		this.cellpayloglist = cellpayloglist;
	}
	public Employee getExtender() {
		return extender;
	}
	public void setExtender(Employee extender) {
		this.extender = extender;
	}
	public Employee getAccepter() {
		return accepter;
	}
	public void setAccepter(Employee accepter) {
		this.accepter = accepter;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getCheckercode() {
		return checkercode;
	}
	public void setCheckercode(String checkercode) {
		this.checkercode = checkercode;
	}
	public String getChecktime() {
		return checktime;
	}
	public void setChecktime(String checktime) {
		this.checktime = checktime;
	}
	public String getCheckstatus() {
		return checkstatus;
	}
	public void setCheckstatus(String checkstatus) {
		this.checkstatus = checkstatus;
	}
	public Integer getPaymoney() {
		return paymoney;
	}
	public void setPaymoney(Integer paymoney) {
		this.paymoney = paymoney;
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
	public Integer getPaytotalmoney() {
		return paytotalmoney;
	}
	public void setPaytotalmoney(Integer paytotalmoney) {
		this.paytotalmoney = paytotalmoney;
	}
	public Integer getTotalcount() {
		return totalcount;
	}
	public void setTotalcount(Integer totalcount) {
		this.totalcount = totalcount;
	}
	public String getExtendcode() {
		return extendcode;
	}
	public void setExtendcode(String extendcode) {
		this.extendcode = extendcode;
	}
	public String getPayercode() {
		return payercode;
	}
	public void setPayercode(String payercode) {
		this.payercode = payercode;
	}
	public String getPayername() {
		return payername;
	}
	public void setPayername(String payername) {
		this.payername = payername;
	}
	
	
	

}
