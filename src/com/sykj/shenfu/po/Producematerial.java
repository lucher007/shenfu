package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 */
public class Producematerial extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String producecode;  //生产编号
	private String productcode; // 产品编号
	private String productname; // 产品名称
	private String productidentfy;//产品唯一标识(SN/PN)
	private String materialcode; //材料编号
	private String materialname; //材料名称
	private String batchno;  //材料批次号
	private String materialidentfy; //材料唯一标识SN码
	private Integer amount; //需要材料数量
	private String depotrackcode; //货柜存放位置
	private String addtime;     //添加时间
	private String remark; // 备注

	private Producematerial producematerial;
	private List<Producematerial> producemateriallist;
	
	private Integer totalamount;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getProducecode() {
		return producecode;
	}
	public void setProducecode(String producecode) {
		this.producecode = producecode;
	}
	public String getProductcode() {
		return productcode;
	}
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String getProductidentfy() {
		return productidentfy;
	}
	public void setProductidentfy(String productidentfy) {
		this.productidentfy = productidentfy;
	}
	public String getBatchno() {
		return batchno;
	}
	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getDepotrackcode() {
		return depotrackcode;
	}
	public void setDepotrackcode(String depotrackcode) {
		this.depotrackcode = depotrackcode;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Producematerial getProducematerial() {
		return producematerial;
	}
	public void setProducematerial(Producematerial producematerial) {
		this.producematerial = producematerial;
	}
	public List<Producematerial> getProducemateriallist() {
		return producemateriallist;
	}
	public void setProducemateriallist(List<Producematerial> producemateriallist) {
		this.producemateriallist = producemateriallist;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getTotalamount() {
		return totalamount;
	}
	public void setTotalamount(Integer totalamount) {
		this.totalamount = totalamount;
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
	public String getMaterialidentfy() {
		return materialidentfy;
	}
	public void setMaterialidentfy(String materialidentfy) {
		this.materialidentfy = materialidentfy;
	}
	
	
}
