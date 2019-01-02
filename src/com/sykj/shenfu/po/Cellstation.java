package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 */
public class Cellstation extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;      // 数据库ID
	private String stationcode; //驻点单编号
	private String extendcode; //小区发布编号
	private String cellcode; //小区编号
	private String cellname; // 小区名称
	private String address;  // 小区地址
	private Integer totalhouse;  //总户数
	private Integer price; //扫楼单价(以分为单位)
	private Integer totalmoney; //扫楼总价（以分为单位）
	private String extendercode;//发布人
	private String extendtime; //发布时间
	private String acceptercode; //接收人编号
	private String acceptertime;  //接收时间
	private String paytime;      //支付时间
	private String starttime; //驻点开始时间
	private String endtime;   //驻点结束时间
	private Integer talkernumber; //讲解人员数量
	private Integer visitornumber; //测量人员数量
	private String addtime; //申请时间
	private String remark;    //备注

	private Cellstation cellstation;
	private List<Cellstation> cellstationlist;
	
	private Employee extender; //发布人

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStationcode() {
		return stationcode;
	}

	public void setStationcode(String stationcode) {
		this.stationcode = stationcode;
	}

	public String getExtendcode() {
		return extendcode;
	}

	public void setExtendcode(String extendcode) {
		this.extendcode = extendcode;
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

	public Integer getTalkernumber() {
		return talkernumber;
	}

	public void setTalkernumber(Integer talkernumber) {
		this.talkernumber = talkernumber;
	}

	public Integer getVisitornumber() {
		return visitornumber;
	}

	public void setVisitornumber(Integer visitornumber) {
		this.visitornumber = visitornumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Cellstation getCellstation() {
		return cellstation;
	}

	public void setCellstation(Cellstation cellstation) {
		this.cellstation = cellstation;
	}

	public List<Cellstation> getCellstationlist() {
		return cellstationlist;
	}

	public void setCellstationlist(List<Cellstation> cellstationlist) {
		this.cellstationlist = cellstationlist;
	}

	public Employee getExtender() {
		return extender;
	}

	public void setExtender(Employee extender) {
		this.extender = extender;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	

}
