package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 */
public class Purchasedetail extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private Integer purchaseid; //采购单ID
	private String purchasecode; //采购单编号
	private String type; //类型(0-元器件；1-材料）
	private String materialcode; //物料编码
	private String materialname;//物料名称
	private String model;       //规格型号
	private Double price;      //采购单价
	private Double amount;     //采购数量
	private Double money;      //采购金额
	private String remark;     //备注

	private Purchasedetail purchasedetail;
	private List<Purchasedetail> purchasedetaillist;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPurchaseid() {
		return purchaseid;
	}
	public void setPurchaseid(Integer purchaseid) {
		this.purchaseid = purchaseid;
	}
	public String getPurchasecode() {
		return purchasecode;
	}
	public void setPurchasecode(String purchasecode) {
		this.purchasecode = purchasecode;
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
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Purchasedetail getPurchasedetail() {
		return purchasedetail;
	}
	public void setPurchasedetail(Purchasedetail purchasedetail) {
		this.purchasedetail = purchasedetail;
	}
	public List<Purchasedetail> getPurchasedetaillist() {
		return purchasedetaillist;
	}
	public void setPurchasedetaillist(List<Purchasedetail> purchasedetaillist) {
		this.purchasedetaillist = purchasedetaillist;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getType() {
		return type;
	}
	public String getTypename() {
		if("0".equals(type)){
			return "元器件";
		}else if("1".equals(type)){
			return "材料";
		}else {
			return "";
		}
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	
	

}
