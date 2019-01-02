package com.sykj.shenfu.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 用户实体类
 */
public class Materialdepot extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String materialcode;    //材料编号
	private String materialname;    // 材料名称
	private String operatorcode;    // 操作员编号
	private String batchno;         //批次号
	private Integer depotamount;    // 库存数量
	private String addtime;         // 操作时间
	private String inoutercode;     //出入库负责人
	private String depotstatus;     //库存状态（0-有库存；1-无库存）
	private String depotrackcode;   //存放货柜编码
	private String suppliername;    //供货商简称
	private String materialidentfy; //材料唯一标识SN码
	private String materialsource;  //材料来源（0-自产；1-外购）
	private String producercode;   //产品生产员编码
	private String materialstatus;  //材料状态
	private String installinfo;  //组装信息
	private String materialversion; //材料版本
	private String remark; // 备注

	private Materialdepot materialdepot;
	private List<Materialdepot> materialdepotlist;
	
	private Material material;     //物料对象
	
	private String alarmdepotamount; //报警量
	
	//库存量查询状态
	private String depotamountlevel; //0-库存量正常(白色显示);1-库存量不足(蓝色显示);2-无库存量(红色显示)
	
	private String inoutername;  //入库负责人姓名
	private Integer inoutamount; //出入库数量
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getMaterialname() {
		return materialname;
	}
	public void setMaterialname(String materialname) {
		this.materialname = materialname;
	}
	public String getBatchno() {
		return batchno;
	}
	public void setBatchno(String batchno) {
		this.batchno = batchno;
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
	public Materialdepot getMaterialdepot() {
		return materialdepot;
	}
	public void setMaterialdepot(Materialdepot materialdepot) {
		this.materialdepot = materialdepot;
	}
	public List<Materialdepot> getMaterialdepotlist() {
		return materialdepotlist;
	}
	public void setMaterialdepotlist(List<Materialdepot> materialdepotlist) {
		this.materialdepotlist = materialdepotlist;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	public String getMaterialcode() {
		return materialcode;
	}
	public void setMaterialcode(String materialcode) {
		this.materialcode = materialcode;
	}
	public String getDepotamountlevel() {
		return depotamountlevel;
	}
	public void setDepotamountlevel(String depotamountlevel) {
		this.depotamountlevel = depotamountlevel;
	}
	public String getOperatorcode() {
		return operatorcode;
	}
	public void setOperatorcode(String operatorcode) {
		this.operatorcode = operatorcode;
	}
	
	public Integer getDepotamount() {
		return depotamount;
	}
	public void setDepotamount(Integer depotamount) {
		this.depotamount = depotamount;
	}
	public String getDepotstatus() {
		return depotstatus;
	}
	public String getDepotstatusname() {
		if("0".equals(depotstatus)){
			return "无库存";
		}else if("1".equals(depotstatus)){
			return "有库存";
		}else{
			return "";
		}
	}
	public void setDepotstatus(String depotstatus) {
		this.depotstatus = depotstatus;
	}
	public String getDepotrackcode() {
		return depotrackcode;
	}
	public void setDepotrackcode(String depotrackcode) {
		this.depotrackcode = depotrackcode;
	}
	public String getSuppliername() {
		return suppliername;
	}
	public Integer getInoutamount() {
		return inoutamount;
	}
	public void setInoutamount(Integer inoutamount) {
		this.inoutamount = inoutamount;
	}
	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}
	public String getAlarmdepotamount() {
		return alarmdepotamount;
	}
	public void setAlarmdepotamount(String alarmdepotamount) {
		this.alarmdepotamount = alarmdepotamount;
	}
	public String getInoutercode() {
		return inoutercode;
	}
	public void setInoutercode(String inoutercode) {
		this.inoutercode = inoutercode;
	}
	public String getInoutername() {
		return inoutername;
	}
	public void setInoutername(String inoutername) {
		this.inoutername = inoutername;
	}
	public String getMaterialidentfy() {
		return materialidentfy;
	}
	public void setMaterialidentfy(String materialidentfy) {
		this.materialidentfy = materialidentfy;
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
	public String getProducercode() {
		return producercode;
	}
	public void setProducercode(String producercode) {
		this.producercode = producercode;
	}
	public String getMaterialstatus() {
		return materialstatus;
	}
	public String getMaterialstatusname() {
		if("1".equals(materialstatus)){
			return "正常";
		}else if("2".equals(materialstatus)){
			return "损坏";
		}else if("3".equals(materialstatus)){
			return "维修中";
		}else{
			return "";
		}
	}
	public void setMaterialstatus(String materialstatus) {
		this.materialstatus = materialstatus;
	}
	public String getInstallinfo() {
		return installinfo;
	}
	public void setInstallinfo(String installinfo) {
		this.installinfo = installinfo;
	}
	public String getMaterialversion() {
		return materialversion;
	}
	public void setMaterialversion(String materialversion) {
		this.materialversion = materialversion;
	}

}
