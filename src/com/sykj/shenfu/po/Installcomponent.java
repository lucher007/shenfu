package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 */
public class Installcomponent extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String producecode;  //生产编号
	private String materialcode; //材料编号
	private String materialname; //材料名称
	private String materialbatchno; //材料批次号
	private String materialidentfy;//材料唯一标识(SN/PN)
	private String componentcode; //元器件编号
	private String componentname; //元器件名称
	private String componentmodel; //元器件规格
	private String batchno;  //元器件批次号
	private Integer amount; //需要元器件数量
	private String depotrackcode; //货柜存放位置
	private String addtime;     //添加时间
	private String remark; // 备注

	private Installcomponent installcomponent;
	private List<Installcomponent> installcomponentlist;
	
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

	public String getMaterialcode() {
		return materialcode;
	}

	public void setMaterialcode(String materialcode) {
		this.materialcode = materialcode;
	}

	public String getMaterialidentfy() {
		return materialidentfy;
	}

	public void setMaterialidentfy(String materialidentfy) {
		this.materialidentfy = materialidentfy;
	}

	public String getComponentcode() {
		return componentcode;
	}

	public void setComponentcode(String componentcode) {
		this.componentcode = componentcode;
	}

	public String getComponentname() {
		return componentname;
	}

	public void setComponentname(String componentname) {
		this.componentname = componentname;
	}

	public String getComponentmodel() {
		return componentmodel;
	}

	public void setComponentmodel(String componentmodel) {
		this.componentmodel = componentmodel;
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

	public Installcomponent getInstallcomponent() {
		return installcomponent;
	}

	public void setInstallcomponent(Installcomponent installcomponent) {
		this.installcomponent = installcomponent;
	}

	public List<Installcomponent> getInstallcomponentlist() {
		return installcomponentlist;
	}

	public void setInstallcomponentlist(List<Installcomponent> installcomponentlist) {
		this.installcomponentlist = installcomponentlist;
	}

	public Integer getTotalamount() {
		return totalamount;
	}

	public void setTotalamount(Integer totalamount) {
		this.totalamount = totalamount;
	}

	public String getMaterialname() {
		return materialname;
	}

	public void setMaterialname(String materialname) {
		this.materialname = materialname;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getMaterialbatchno() {
		return materialbatchno;
	}

	public void setMaterialbatchno(String materialbatchno) {
		this.materialbatchno = materialbatchno;
	}
	
	
}
