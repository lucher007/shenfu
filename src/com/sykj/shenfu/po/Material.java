package com.sykj.shenfu.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import fr.opensagres.xdocreport.core.utils.StringUtils;

/**
 * 用户实体类
 */
public class Material extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String materialcode; // 材料编号
	private String materialname; // 材料名称
	private String materialunit; // 材料计算单位
	private Integer depotamount; // 报警量
	private String materialstatus; // 材料状态
	private String materialsource;//材料来源（0-自产；1-外购；）
	private String remark; // 备注

	private Material material;
	private List<Material> materiallist;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getMaterialunit() {
		return materialunit;
	}
	public void setMaterialunit(String materialunit) {
		this.materialunit = materialunit;
	}
	public String getMaterialstatus() {
		return materialstatus;
	}
	public void setMaterialstatus(String materialstatus) {
		this.materialstatus = materialstatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	public List<Material> getMateriallist() {
		return materiallist;
	}
	public void setMateriallist(List<Material> materiallist) {
		this.materiallist = materiallist;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getDepotamount() {
		return depotamount;
	}
	public void setDepotamount(Integer depotamount) {
		this.depotamount = depotamount;
	}

	//重写父类方法
	public String getSort() {
		if(StringUtils.isNotEmpty(sort)){
			return sort;
		}else{
			return "materialcode";
		}
	}
	//重写父类方法
	public String getOrder() {
		if(StringUtils.isNotEmpty(order)){
			return order;
		}else{
			return "asc";
		}
	}
	public String getMaterialsource() {
		return materialsource;
	}
	public String getMaterialsourcename() {
		if("0".equals(materialsource)){
			return "自产";
		}else if("1".equals(materialsource)){
			return "外购";
		}else{
			return "";
		}
	}
	public void setMaterialsource(String materialsource) {
		this.materialsource = materialsource;
	}
}
