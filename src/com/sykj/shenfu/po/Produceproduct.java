package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 */
public class Produceproduct extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String producecode;  //生产编号
	private String productcode; // 产品编号
	private String productname; // 产品名称
	private String productidentfy; //产品唯一标识(SN/PN)
	private Integer depotamount;   //产品数量
	private String productstatus;  //产品状态（1-组装中；2-组装完成；3-已经入库；0-组装失败）
	private String printflag;     //是否打印条码（0-未打印；1-已打印）
	private String addtime;      //添加时间
	private String remark; // 备注

	private Produceproduct produceproduct;
	private List<Produceproduct> produceproductlist;
	private String depotrackcode; //库存存放位置
	private String inoutercode; //出入库负责人编号
	private String inoutername; //出入库负责人姓名
	
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
	public Integer getDepotamount() {
		return depotamount;
	}
	public void setDepotamount(Integer depotamount) {
		this.depotamount = depotamount;
	}
	public String getProductstatus() {
		return productstatus;
	}
	public String getProductstatusname() {
		if("0".equals(productstatus)){
			return "组装失败";
		}else if("1".equals(productstatus)){
			return "组装中";
		}else if("2".equals(productstatus)){
			return "组装完成";
		}else if("3".equals(productstatus)){
			return "已经入库";
		}else{
			return "";
		}
	}
	public void setProductstatus(String productstatus) {
		this.productstatus = productstatus;
	}
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
	public Produceproduct getProduceproduct() {
		return produceproduct;
	}
	public void setProduceproduct(Produceproduct produceproduct) {
		this.produceproduct = produceproduct;
	}
	public List<Produceproduct> getProduceproductlist() {
		return produceproductlist;
	}
	public void setProduceproductlist(List<Produceproduct> produceproductlist) {
		this.produceproductlist = produceproductlist;
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
	
}
