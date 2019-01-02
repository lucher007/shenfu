package com.sykj.shenfu.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 用户实体类
 */
public class Userproduct extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String usercode; // 客户编号
	private String ordercode; // 订单编号
	private String productidentfy; //产品标识
	private String modelname; //产品型号
	private String modelcode; //型号编码
	private String typecode; //产品类别编码
	private String typename; //产品类别名称
	private String productname;//产品名称
	private String productcode;//产品名称编码
	private String productunit; //计量单位
	private Integer price; //价格
	private Integer saleprice;//销售价格
	private String addtime;     //添加时间
	private String inoutercode;  //出入库人工号
	private String buytype; //购买方式（0-型号整体购买；1-配件零售购买）
	private String depotrackcode; //货柜存放位置
	private String productsource; //产品来源（0-系统型号中设置的产品；1-选择的产品）
	private String productversion; //产品软件版本
	private String remark; // 备注

	private Userproduct userproduct;
	private List<Userproduct> userproductlist;
	
    private User user;
    private Userorder userorder;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getProductidentfy() {
		return productidentfy;
	}
	public void setProductidentfy(String productidentfy) {
		this.productidentfy = productidentfy;
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
	public Userproduct getUserproduct() {
		return userproduct;
	}
	public void setUserproduct(Userproduct userproduct) {
		this.userproduct = userproduct;
	}
	public List<Userproduct> getUserproductlist() {
		return userproductlist;
	}
	public void setUserproductlist(List<Userproduct> userproductlist) {
		this.userproductlist = userproductlist;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Userorder getUserorder() {
		return userorder;
	}
	public void setUserorder(Userorder userorder) {
		this.userorder = userorder;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String getProductunit() {
		return productunit;
	}
	public void setProductunit(String productunit) {
		this.productunit = productunit;
	}
	public String getModelcode() {
		return modelcode;
	}
	public void setModelcode(String modelcode) {
		this.modelcode = modelcode;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getOrdercode() {
		return ordercode;
	}
	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}
	public String getModelname() {
		return modelname;
	}
	public void setModelname(String modelname) {
		this.modelname = modelname;
	}
	public String getProductcode() {
		return productcode;
	}
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getSaleprice() {
		return saleprice;
	}
	public void setSaleprice(Integer saleprice) {
		this.saleprice = saleprice;
	}
	public String getInoutercode() {
		return inoutercode;
	}
	public void setInoutercode(String inoutercode) {
		this.inoutercode = inoutercode;
	}
	public String getBuytype() {
		return buytype;
	}
	public String getBuytypename() {
		if("0".equals(buytype)){
			return "整体型号购买";
		}else if("1".equals(buytype)){
			return "产品单件购买";
		}else{
			return "";
		}
	}
	public void setBuytype(String buytype) {
		this.buytype = buytype;
	}
	public String getDepotrackcode() {
		return depotrackcode;
	}
	public void setDepotrackcode(String depotrackcode) {
		this.depotrackcode = depotrackcode;
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
	public String getProductsource() {
		return productsource;
	}
	public void setProductsource(String productsource) {
		this.productsource = productsource;
	}
	public String getProductversion() {
		return productversion;
	}
	public void setProductversion(String productversion) {
		this.productversion = productversion;
	}
    
    

}
