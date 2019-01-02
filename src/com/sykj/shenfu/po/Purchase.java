package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 */
public class Purchase extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String purchasecode; //采购单号
	private Integer supplierid; // 供应商ID
	private String suppliername; //供应商名称
	private String supplierinfo; //供应商信息
	private Double materailmoney; //材料总费用
	private Double deliverymoney; //快递费用
	private Double discountmoney; //优惠金额
	private Double totalmoney; //总金额
	private String plandelivertime; // 预计发货日期
	private String planarrivaltime; // 预计到货日期
	private String actualdelivertime; // 实际发货时间
	private String actualarrivaltime; // 实际到货时间
	private String status; // 状态（0-未到货；1-已到货；2-已审核入库）
	private String operatorcode; //操作员编号
	private String addtime; // 采购日期
	private String billtype; //票据类型(0-不开票；1-开票)
	private String billstatus; //票据状态(0-票据未到；1-票据已到)
	private String invoicecode; //票据号码
	private String remark; // 备注

	private Purchase purchase;
	private List<Purchase> purchaselist;
	
	//供货商对象
	private Supplier supplier;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPurchasecode() {
		return purchasecode;
	}

	public void setPurchasecode(String purchasecode) {
		this.purchasecode = purchasecode;
	}

	public Integer getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(Integer supplierid) {
		this.supplierid = supplierid;
	}

	public String getSuppliername() {
		return suppliername;
	}

	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}

	public String getSupplierinfo() {
		return supplierinfo;
	}

	public void setSupplierinfo(String supplierinfo) {
		this.supplierinfo = supplierinfo;
	}

	public Double getMaterailmoney() {
		return materailmoney;
	}

	public void setMaterailmoney(Double materailmoney) {
		this.materailmoney = materailmoney;
	}

	public Double getDeliverymoney() {
		return deliverymoney;
	}

	public void setDeliverymoney(Double deliverymoney) {
		this.deliverymoney = deliverymoney;
	}

	public Double getDiscountmoney() {
		return discountmoney;
	}

	public void setDiscountmoney(Double discountmoney) {
		this.discountmoney = discountmoney;
	}

	public Double getTotalmoney() {
		return totalmoney;
	}

	public void setTotalmoney(Double totalmoney) {
		this.totalmoney = totalmoney;
	}

	public String getPlandelivertime() {
		return plandelivertime;
	}

	public void setPlandelivertime(String plandelivertime) {
		this.plandelivertime = plandelivertime;
	}

	public String getPlanarrivaltime() {
		return planarrivaltime;
	}

	public void setPlanarrivaltime(String planarrivaltime) {
		this.planarrivaltime = planarrivaltime;
	}

	public String getActualdelivertime() {
		return actualdelivertime;
	}

	public void setActualdelivertime(String actualdelivertime) {
		this.actualdelivertime = actualdelivertime;
	}

	public String getActualarrivaltime() {
		return actualarrivaltime;
	}

	public void setActualarrivaltime(String actualarrivaltime) {
		this.actualarrivaltime = actualarrivaltime;
	}

	public String getStatus() {
		return status;
	}
	
	public String getStatusname() {
		if("0".equals(status)){
			return "未到货";
		}else if("1".equals(status)){
			return "已到货";
		}else if("2".equals(status)){
			return "已审核入库";
		}else{
			return "";
		}
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperatorcode() {
		return operatorcode;
	}

	public void setOperatorcode(String operatorcode) {
		this.operatorcode = operatorcode;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getBilltype() {
		return billtype;
	}
	
	public String getBilltypename() {
		if("0".equals(billtype)){
			return "不开票";
		}else if("1".equals(billtype)){
			return "开票";
		}else{
			return "";
		}
	}

	public void setBilltype(String billtype) {
		this.billtype = billtype;
	}

	public String getBillstatus() {
		return billstatus;
	}
	
	public String getBillstatusname() {
		if("0".equals(billstatus)){
			return "票据未到";
		}else if("1".equals(billstatus)){
			return "票据已到";
		}else{
			return "";
		}
	}

	public void setBillstatus(String billstatus) {
		this.billstatus = billstatus;
	}

	public String getInvoicecode() {
		return invoicecode;
	}

	public void setInvoicecode(String invoicecode) {
		this.invoicecode = invoicecode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Purchase getPurchase() {
		return purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}

	public List<Purchase> getPurchaselist() {
		return purchaselist;
	}

	public void setPurchaselist(List<Purchase> purchaselist) {
		this.purchaselist = purchaselist;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}
