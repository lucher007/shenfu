package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 */
public class Productdepot extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String typecode;    //类别编码
	private String typename;    //类别名称
	private String productcode;    //产品编号
	private String productname;     //产品名称
	private String productsource;   //产品来源（0-自产；1-外购）
	private String productidentfy;  //产品SN/PN
	private Integer depotamount;    //库存数量
	private String checkercode;      //产品质检员编号
	private String producercode;      //产品生产员编号
	private String productstatus;    //产品状态产品状态（1-正常；2-损坏；3-维修）
	private String checkstatus;      //检验状态:0未检,1已检
	private String addtime;         // 操作时间
	private String depotstatus;     //库存状态（0-无库存；1-有库存）
	private String depotrackcode;   //存放货柜编码
	private String suppliername;   //供货商简称
	private String installinfo;    //组装信息
	private String operatorcode;  //操作员编号
	private String inoutercode;   //出入库负责人
	private String productversion; //产品软件版本
	private String remark; // 备注

	private Productdepot productdepot;
	private List<Productdepot> productdepotlist;
	
	private Product product;     //产品对象
	private Operator producer;   //生产员
	private String producername;  //负责人姓名
	private Integer inoutamount; //出入库数量
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getProductsource() {
		return productsource;
	}
	public String getProductsourcename() {
		if("0".equals(productsource)){
			return "自产";
		}else if("1".equals(productsource)){
			return "外购";
		}else{
			return "";
		}
	}
	public void setProductsource(String productsource) {
		this.productsource = productsource;
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
	public String getCheckercode() {
		return checkercode;
	}
	public void setCheckercode(String checkercode) {
		this.checkercode = checkercode;
	}
	public String getProducercode() {
		return producercode;
	}
	public void setProducercode(String producercode) {
		this.producercode = producercode;
	}
	public String getProductstatus() {
		return productstatus;
	}
	public String getProductstatusname() {
		if("1".equals(productstatus)){
			return "正常";
		}else if("2".equals(productstatus)){
			return "损坏";
		}else if("3".equals(productstatus)){
			return "维修";
		}else{
			return "";
		}
	}
	public void setProductstatus(String productstatus) {
		this.productstatus = productstatus;
	}
	public String getCheckstatus() {
		return checkstatus;
	}
	public void setCheckstatus(String checkstatus) {
		this.checkstatus = checkstatus;
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
	public Productdepot getProductdepot() {
		return productdepot;
	}
	public void setProductdepot(Productdepot productdepot) {
		this.productdepot = productdepot;
	}
	public List<Productdepot> getProductdepotlist() {
		return productdepotlist;
	}
	public void setProductdepotlist(List<Productdepot> productdepotlist) {
		this.productdepotlist = productdepotlist;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Operator getProducer() {
		return producer;
	}
	public void setProducer(Operator producer) {
		this.producer = producer;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getTypecode() {
		return typecode;
	}
	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public String getProducername() {
		return producername;
	}
	public void setProducername(String producername) {
		this.producername = producername;
	}
	public String getOperatorcode() {
		return operatorcode;
	}
	public void setOperatorcode(String operatorcode) {
		this.operatorcode = operatorcode;
	}
	public Integer getInoutamount() {
		return inoutamount;
	}
	public void setInoutamount(Integer inoutamount) {
		this.inoutamount = inoutamount;
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
	public String getInoutercode() {
		return inoutercode;
	}
	public void setInoutercode(String inoutercode) {
		this.inoutercode = inoutercode;
	}
	public String getInstallinfo() {
		return installinfo;
	}
	public void setInstallinfo(String installinfo) {
		this.installinfo = installinfo;
	}
	public String getProductversion() {
		return productversion;
	}
	public void setProductversion(String productversion) {
		this.productversion = productversion;
	}

	

}
