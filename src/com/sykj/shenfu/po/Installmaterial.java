package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 */
public class Installmaterial extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String producecode;  //生产编号
	private String materialcode; //材料编号
	private String materialname; //材料名称
	private String batchno;      //材料批次号
	private String materialidentfy; //材料唯一标识(SN/PN)
	private Integer depotamount;   //材料数量
	private String materialstatus;  //材料状态（1-组装中；2-组装完成；3-已经入库；0-组装失败）
	private String printflag;     //是否打印条码（0-未打印；1-已打印）
	private String addtime;       //添加时间
	private String remark; // 备注

	private Installmaterial installmaterial;
	private List<Installmaterial> installmateriallist;
	private String depotrackcode; //库存存放位置
	private String inoutercode; //出入库负责人编号
	private String inoutername; //出入库负责人姓名
	
	public String getPrintflag() {
		return printflag;
	}
	public String getPrintflagname() {
		if("0".equals(printflag)){
			return "未打印";
		}else if("1".equals(printflag)){
			return "已打印";
		}else{
			return "";
		}
	}
	public void setPrintflag(String printflag) {
		this.printflag = printflag;
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

	public String getMaterialidentfy() {
		return materialidentfy;
	}

	public void setMaterialidentfy(String materialidentfy) {
		this.materialidentfy = materialidentfy;
	}

	public Integer getDepotamount() {
		return depotamount;
	}

	public void setDepotamount(Integer depotamount) {
		this.depotamount = depotamount;
	}

	public String getMaterialstatus() {
		return materialstatus;
	}

	public String getMaterialstatusname() {
		if("0".equals(materialstatus)){
			return "组装失败";
		}else if("1".equals(materialstatus)){
			return "组装中";
		}else if("2".equals(materialstatus)){
			return "组装完成";
		}else if("3".equals(materialstatus)){
			return "已经入库";
		}else{
			return "";
		}
	}
	
	public void setMaterialstatus(String materialstatus) {
		this.materialstatus = materialstatus;
	}

	public Installmaterial getInstallmaterial() {
		return installmaterial;
	}
	
	public void setInstallmaterial(Installmaterial installmaterial) {
		this.installmaterial = installmaterial;
	}

	public List<Installmaterial> getInstallmateriallist() {
		return installmateriallist;
	}

	public void setInstallmateriallist(List<Installmaterial> installmateriallist) {
		this.installmateriallist = installmateriallist;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getDepotrackcode() {
		return depotrackcode;
	}
	public void setDepotrackcode(String depotrackcode) {
		this.depotrackcode = depotrackcode;
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
	public String getBatchno() {
		return batchno;
	}
	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}
	
}
