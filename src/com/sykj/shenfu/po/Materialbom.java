package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 用户实体类
 */
public class Materialbom extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;                //数据库ID
	private Integer pid;               //父级BOM
	private String materialcode;        //材料编码
	private String materialname;        //材料名称
	private String bomcode;            //规则编号（有规则的编号，方便管理拓扑结构，举例：001，002,001-001等。
	private String componentcode;	   //元器件编号
	private String componentname;	   //元器件名称
	private String componentmodel;	   //规格
	private Integer amount;            //所需数量
	private String remark;             //备注
	
	/******************数据库辅助字段*************************/
	private Materialbom materialbom;
	private List<Materialbom> bomlist;
	private Materialbom parentbom; //父级Bom
	private Material material;
	private Map<String, String> materialmap;
	
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
	public String getBomcode() {
		return bomcode;
	}
	public void setBomcode(String bomcode) {
		this.bomcode = bomcode;
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
	public Materialbom getMaterialbom() {
		return materialbom;
	}
	public void setMaterialbom(Materialbom materialbom) {
		this.materialbom = materialbom;
	}
	public List<Materialbom> getBomlist() {
		return bomlist;
	}
	public void setBomlist(List<Materialbom> bomlist) {
		this.bomlist = bomlist;
	}
	public Materialbom getParentbom() {
		return parentbom;
	}
	public void setParentbom(Materialbom parentbom) {
		this.parentbom = parentbom;
	}
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	public Map<String, String> getMaterialmap() {
		return materialmap;
	}
	public void setMaterialmap(Map<String, String> materialmap) {
		this.materialmap = materialmap;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
