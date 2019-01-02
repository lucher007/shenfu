package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 */
public class Produceinfo extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String producecode;  //生产编号
	private String productcode; // 产品编号
	private String productname; // 产品名称
	private Integer amount;     //生产数量
	private String installinfo; //组装信息
	private String productversion; //产品版本号
	private String producercode; // 生产负责人
	private String operatorcode;  //操作员
	private String addtime;     //添加时间
	private String remark; // 备注

	private Produceinfo produceinfo;
	private List<Produceinfo> produceinfolist;
	
	private String producername; //生产负责人
	
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
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getInstallinfo() {
		return installinfo;
	}
	public void setInstallinfo(String installinfo) {
		this.installinfo = installinfo;
	}
	public String getProducercode() {
		return producercode;
	}
	public void setProducercode(String producercode) {
		this.producercode = producercode;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Produceinfo getProduceinfo() {
		return produceinfo;
	}
	public void setProduceinfo(Produceinfo produceinfo) {
		this.produceinfo = produceinfo;
	}
	public List<Produceinfo> getProduceinfolist() {
		return produceinfolist;
	}
	public void setProduceinfolist(List<Produceinfo> produceinfolist) {
		this.produceinfolist = produceinfolist;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getProducername() {
		return producername;
	}
	public void setProducername(String producername) {
		this.producername = producername;
	}
	public String getProductversion() {
		return productversion;
	}
	public void setProductversion(String productversion) {
		this.productversion = productversion;
	}
	
	
}
