package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 */
public class Productcolor extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String colorcode; //产品颜色编码
	private String colorname; // 产品颜色名称
	private String remark; // 备注

	private Productcolor productcolor;
	private List<Productcolor> productcolorlist;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Productcolor getProductcolor() {
		return productcolor;
	}
	public void setProductcolor(Productcolor productcolor) {
		this.productcolor = productcolor;
	}
	public List<Productcolor> getProductcolorlist() {
		return productcolorlist;
	}
	public void setProductcolorlist(List<Productcolor> productcolorlist) {
		this.productcolorlist = productcolorlist;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getColorcode() {
		return colorcode;
	}
	public void setColorcode(String colorcode) {
		this.colorcode = colorcode;
	}
	public String getColorname() {
		return colorname;
	}
	public void setColorname(String colorname) {
		this.colorname = colorname;
	}
	
	

}
