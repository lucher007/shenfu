package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 */
public class Installinfo extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String producecode;  //生产编号
	private String materialcode; //材料编号
	private String materialname; //材料名称
	private Integer amount;     //生产数量
	private String installinformation; //组装信息
	private String materialversion; //版本号
	private String producercode; // 生产负责人
	private String operatorcode;  //操作员
	private String addtime;     //添加时间
	private String remark; // 备注

	private Installinfo installinfo;
	private List<Installinfo> installinfolist;
	
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

	public String getInstallinformation() {
		return installinformation;
	}

	public void setInstallinformation(String installinformation) {
		this.installinformation = installinformation;
	}

	public String getMaterialversion() {
		return materialversion;
	}

	public void setMaterialversion(String materialversion) {
		this.materialversion = materialversion;
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

	public Installinfo getInstallinfo() {
		return installinfo;
	}

	public void setInstallinfo(Installinfo installinfo) {
		this.installinfo = installinfo;
	}

	public List<Installinfo> getInstallinfolist() {
		return installinfolist;
	}

	public void setInstallinfolist(List<Installinfo> installinfolist) {
		this.installinfolist = installinfolist;
	}

	public String getProducername() {
		return producername;
	}

	public void setProducername(String producername) {
		this.producername = producername;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
