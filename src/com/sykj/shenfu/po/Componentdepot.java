package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 */
public class Componentdepot extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String componentcode;    //元器件编号
	private String componentname;    //元器件封装
	private String componentmodel; //原器件规格
	private String operatorcode;    // 操作员编号
	private String batchno;         //批次号
	private Integer depotamount;    // 库存数量
	private String addtime;         // 操作时间
	private String inoutercode;     //出入库负责人
	private String depotstatus;     //库存状态（0-有库存；1-无库存）
	private String depotrackcode;   //存放货柜编码
	private String suppliername;    //供货商简称
	private String remark; // 备注

	private Componentdepot componentdepot;
	private List<Componentdepot> componentdepotlist;
	
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
	public String getOperatorcode() {
		return operatorcode;
	}
	public void setOperatorcode(String operatorcode) {
		this.operatorcode = operatorcode;
	}
	public String getBatchno() {
		return batchno;
	}
	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}
	public Integer getDepotamount() {
		return depotamount;
	}
	public void setDepotamount(Integer depotamount) {
		this.depotamount = depotamount;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getInoutercode() {
		return inoutercode;
	}
	public void setInoutercode(String inoutercode) {
		this.inoutercode = inoutercode;
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
	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Componentdepot getComponentdepot() {
		return componentdepot;
	}
	public void setComponentdepot(Componentdepot componentdepot) {
		this.componentdepot = componentdepot;
	}
	public List<Componentdepot> getComponentdepotlist() {
		return componentdepotlist;
	}
	public void setComponentdepotlist(List<Componentdepot> componentdepotlist) {
		this.componentdepotlist = componentdepotlist;
	}
	public String getAlarmdepotamount() {
		return alarmdepotamount;
	}
	public void setAlarmdepotamount(String alarmdepotamount) {
		this.alarmdepotamount = alarmdepotamount;
	}
	public String getDepotamountlevel() {
		return depotamountlevel;
	}
	public void setDepotamountlevel(String depotamountlevel) {
		this.depotamountlevel = depotamountlevel;
	}
	public String getInoutername() {
		return inoutername;
	}
	public void setInoutername(String inoutername) {
		this.inoutername = inoutername;
	}
	public Integer getInoutamount() {
		return inoutamount;
	}
	public void setInoutamount(Integer inoutamount) {
		this.inoutamount = inoutamount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
