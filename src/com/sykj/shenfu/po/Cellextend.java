package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 */
public class Cellextend extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;      // 数据库ID
	private String extendcode; //小区发布编号
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
	private String acceptflag; //接收状态
	private String acceptercode; //接收人编号
	private String acceptertime;  //接收时间
	private String payflag;      //是否已经支付(0-未支付；1-已支付)
	private String paytime;      //支付时间
	private String stationflag;  //是否驻点(0-未驻点;1-已驻点)
	private String remark;    //备注

	private Cellextend cellextend;
	private List<Cellextend> cellextendlist;
	
	private Employee extender; //发布人
	private Cell cell;//小区信息
	
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
	public String getPayflag() {
		return payflag;
	}
	public String getPayflagname() {
		if("0".equals(payflag)){
			return "未支付";
		}else if("1".equals(payflag)){
			return "已支付";
		}else{
			return "";
		}
	}
	public void setPayflag(String payflag) {
		this.payflag = payflag;
	}
	public String getPaytime() {
		return paytime;
	}
	public void setPaytime(String paytime) {
		this.paytime = paytime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Cellextend getCellextend() {
		return cellextend;
	}
	public void setCellextend(Cellextend cellextend) {
		this.cellextend = cellextend;
	}
	public List<Cellextend> getCellextendlist() {
		return cellextendlist;
	}
	public void setCellextendlist(List<Cellextend> cellextendlist) {
		this.cellextendlist = cellextendlist;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Employee getExtender() {
		return extender;
	}
	public void setExtender(Employee extender) {
		this.extender = extender;
	}
	public String getAcceptflag() {
		return acceptflag;
	}
	public String getAcceptflagname() {
		if("0".equals(acceptflag)){
			return "未抢购";
		}else if("1".equals(acceptflag)){
			return "已抢购";
		}else{
			return "";
		}
	}
	public void setAcceptflag(String acceptflag) {
		this.acceptflag = acceptflag;
	}
	public String getExtendcode() {
		return extendcode;
	}
	public void setExtendcode(String extendcode) {
		this.extendcode = extendcode;
	}
	public String getStationflag() {
		return stationflag;
	}
	public String getStationflagname() {
		if("0".equals(stationflag)){
			return "未申请驻点";
		}else if("1".equals(stationflag)){
			return "已申请驻点";
		}else{
			return "";
		}
	}
	
	public void setStationflag(String stationflag) {
		this.stationflag = stationflag;
	}
	public Cell getCell() {
		return cell;
	}
	public void setCell(Cell cell) {
		this.cell = cell;
	}
	
	

}
