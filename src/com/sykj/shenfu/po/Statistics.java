package com.sykj.shenfu.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 用户实体类
 */
public class Statistics extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;
    
	
	//材料供需统计
	private Integer materialid; // 材料ID
	private String materialcode;  // 材料编码
	private String materialname; // 材料名称
	private BigDecimal orderamount; // 订单需求量
	private BigDecimal purchaseamount; //采购量
	private BigDecimal depotamount;   //库存量
	
	private Statistics statistics;
	private List<Statistics> statisticslist;
	
	public Integer getMaterialid() {
		return materialid;
	}
	public void setMaterialid(Integer materialid) {
		this.materialid = materialid;
	}
	public String getMaterialcode() {
		return materialcode;
	}
	public void setMaterialcode(String materialcode) {
		this.materialcode = materialcode;
	}
	public String getMaterialname() {
		return materialname;
	}
	public void setMaterialname(String materialname) {
		this.materialname = materialname;
	}
	public Statistics getStatistics() {
		return statistics;
	}
	public void setStatistics(Statistics statistics) {
		this.statistics = statistics;
	}
	public List<Statistics> getStatisticslist() {
		return statisticslist;
	}
	public void setStatisticslist(List<Statistics> statisticslist) {
		this.statisticslist = statisticslist;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public BigDecimal getPurchaseamount() {
		return purchaseamount;
	}
	public void setPurchaseamount(BigDecimal purchaseamount) {
		this.purchaseamount = purchaseamount;
	}
	public BigDecimal getDepotamount() {
		return depotamount;
	}
	public void setDepotamount(BigDecimal depotamount) {
		this.depotamount = depotamount;
	}
	public BigDecimal getOrderamount() {
		return orderamount;
	}
	public void setOrderamount(BigDecimal orderamount) {
		this.orderamount = orderamount;
	}
	
}
