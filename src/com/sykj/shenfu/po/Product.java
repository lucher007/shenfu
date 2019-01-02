package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

import fr.opensagres.xdocreport.core.utils.StringUtils;

/**
 * 用户实体类
 */
public class Product extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String productcode; // 产品编号
	private String productname; // 产品名称
	private String typecode;    //类别编号
	private String typename;    //类别名称
	private Integer price; //单价
	private String productsource;//产品来源（0-自产；1-外购；）
	private Integer depotamount; // 警戒量
	private String productunit; // 产品计算单位
	private String imageurl;  //图片存放地址
	private String remark; // 备注

	private Product product;
	private List<Product> productlist;
	
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
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
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
	public Integer getDepotamount() {
		return depotamount;
	}
	public void setDepotamount(Integer depotamount) {
		this.depotamount = depotamount;
	}
	public String getProductunit() {
		return productunit;
	}
	public void setProductunit(String productunit) {
		this.productunit = productunit;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public List<Product> getProductlist() {
		return productlist;
	}
	public void setProductlist(List<Product> productlist) {
		this.productlist = productlist;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getImageurl() {
		return imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
    
	//重写父类方法
		public String getSort() {
			if(StringUtils.isNotEmpty(sort)){
				return sort;
			}else{
				return "productcode";
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
}
