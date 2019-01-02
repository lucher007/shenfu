package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 用户实体类
 */
public class Bom extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;                //数据库ID
	private Integer pid;               //父级BOM
	private String productcode;        //产品编码
	private String productname;        //产品名称
	private String bomcode;            //规则编号（有规则的编号，方便管理拓扑结构，举例：001，002,001-001等。
	private String materialcode;	   //物料编码
	private String materialname;	   //物料名称
	private Integer amount;            //所需数量
	private String remark;             //备注
	
	/******************数据库辅助字段*************************/
	private Bom bom;
	private List<Bom> bomlist;
	private Bom parentbom; //父级Bom
	private Product product;
	private Map<String, String> productmap;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
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
	public String getBomcode() {
		return bomcode;
	}
	public void setBomcode(String bomcode) {
		this.bomcode = bomcode;
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
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Bom getBom() {
		return bom;
	}
	public void setBom(Bom bom) {
		this.bom = bom;
	}
	public List<Bom> getBomlist() {
		return bomlist;
	}
	public void setBomlist(List<Bom> bomlist) {
		this.bomlist = bomlist;
	}
	public Bom getParentbom() {
		return parentbom;
	}
	public void setParentbom(Bom parentbom) {
		this.parentbom = parentbom;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Map<String, String> getProductmap() {
		return productmap;
	}
	public void setProductmap(Map<String, String> productmap) {
		this.productmap = productmap;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
}
