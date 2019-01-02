package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 */
public class Productmodel extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String modelname; // 产品型号
	private String modelcode; //产品型号编码
	private Integer price;    //产品单价
	private String lockcoreflag; //是否有锁芯(0-无；1-有)
	private String content; //简介
	private Integer sellprice; //销售价格
	private String starttime; //有效开始时间
	private String endtime;   //有效结束时间
	private String status;    //状态(0-未发布；1-正式发布)
	private Integer maxprice;  //上浮最大价格
	private Integer minprice;  //下浮最小价格
	private Integer discountgain; //优惠权限
	private Integer fixedgain; //固定返利
	private Integer managergain; //销售管家提成
	private String remark; // 备注

	private Productmodel productmodel;
	private List<Productmodel> productmodellist;
	
    private String producttypeJson;//订户级别关联的产品类别信息JSON
	
	private List<Product> productlist;

	private String effectivetime;  //有效时间
	private String effectivetimeflag; //查询有效时间标志 1-表示查询当前时间有效产品
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getModelname() {
		return modelname;
	}

	public void setModelname(String modelname) {
		this.modelname = modelname;
	}

	public String getModelcode() {
		return modelcode;
	}

	public void setModelcode(String modelcode) {
		this.modelcode = modelcode;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getLockcoreflag() {
		return lockcoreflag;
	}
	
	public String getLockcoreflagname() {
		if("0".equals(lockcoreflag)){
			return "无锁芯";
		}else if("1".equals(lockcoreflag)){
			return "有锁芯";
		}else{
			return "";
		}
	}

	public void setLockcoreflag(String lockcoreflag) {
		this.lockcoreflag = lockcoreflag;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Productmodel getProductmodel() {
		return productmodel;
	}

	public void setProductmodel(Productmodel productmodel) {
		this.productmodel = productmodel;
	}

	public List<Productmodel> getProductmodellist() {
		return productmodellist;
	}

	public void setProductmodellist(List<Productmodel> productmodellist) {
		this.productmodellist = productmodellist;
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

	public String getProducttypeJson() {
		return producttypeJson;
	}

	public void setProducttypeJson(String producttypeJson) {
		this.producttypeJson = producttypeJson;
	}

	public Integer getSellprice() {
		return sellprice;
	}

	public void setSellprice(Integer sellprice) {
		this.sellprice = sellprice;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getStatus() {
		return status;
	}
	
	public String getStatusname() {
		if("0".equals(status)){
			return "未发布";
		}else if("1".equals(status)){
			return "正式发布";
		}else {
			return "";
		}
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEffectivetime() {
		return effectivetime;
	}

	public void setEffectivetime(String effectivetime) {
		this.effectivetime = effectivetime;
	}

	public String getEffectivetimeflag() {
		return effectivetimeflag;
	}

	public void setEffectivetimeflag(String effectivetimeflag) {
		this.effectivetimeflag = effectivetimeflag;
	}

	public Integer getMaxprice() {
		return maxprice;
	}

	public void setMaxprice(Integer maxprice) {
		this.maxprice = maxprice;
	}

	public Integer getMinprice() {
		return minprice;
	}

	public void setMinprice(Integer minprice) {
		this.minprice = minprice;
	}

	public Integer getDiscountgain() {
		return discountgain;
	}

	public void setDiscountgain(Integer discountgain) {
		this.discountgain = discountgain;
	}

	public Integer getFixedgain() {
		return fixedgain;
	}

	public void setFixedgain(Integer fixedgain) {
		this.fixedgain = fixedgain;
	}

	public Integer getManagergain() {
		return managergain;
	}

	public void setManagergain(Integer managergain) {
		this.managergain = managergain;
	}
	
	
	
}
