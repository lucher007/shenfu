package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 */
public class Salegainrule extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;      // 数据库ID
	private String gaincode; //提成编码
	private String gainname; //提成名称
	private Integer gainrate;//提成百分比
	private String gainform;  //提成方式(0-百分比；1-直接数值）
	private String gaintype; //提成方式(0-销售提成；1-安装提成)
	private String status;   //状态(0无效；1-有效)
	private String remark;   // 备注

	private Salegainrule salegainrule;
	private List<Salegainrule> salegainrulelist;
	
	//销售渠道提成
	public static final String gaincode_sale_channel = "sale_channel";
	//讲解提成
	public static final String gaincode_explain_sell = "explain_sell";
	//测量计算提成
	public static final String gaincode_measure_calculation = "measure_calculation";
	//上一级销售提成
	public static final String gaincode_upper_one = "upper_one";
	//上上级销售提成
	public static final String gaincode_upper_two = "upper_two";
	//充值VIP提成
	public static final String gaincode_recharge_vip = "recharge_vip";
	//智能锁安装
	public static final String gaincode_install_lock = "install_lock";
	//客服服务费
	public static final String gaincode_customer_service = "customer_service";
	//价格利润渠道提成
	public static final String sale_channel_profit = "sale_channel_profit";
	//价格利润讲解提成
	public static final String explain_sell_profit = "explain_sell_profit";
	//优惠权限提成
	public static final String discount_gain = "discount_gain";
	//固定返利提成
	public static final String fixed_gain = "fixed_gain";
	//销售管家提成
	public static final String manager_gain = "manager_gain";
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGaincode() {
		return gaincode;
	}
	public void setGaincode(String gaincode) {
		this.gaincode = gaincode;
	}
	public String getGainname() {
		return gainname;
	}
	public void setGainname(String gainname) {
		this.gainname = gainname;
	}
	public Integer getGainrate() {
		return gainrate;
	}
	public void setGainrate(Integer gainrate) {
		this.gainrate = gainrate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Salegainrule getSalegainrule() {
		return salegainrule;
	}
	public void setSalegainrule(Salegainrule salegainrule) {
		this.salegainrule = salegainrule;
	}
	public List<Salegainrule> getSalegainrulelist() {
		return salegainrulelist;
	}
	public void setSalegainrulelist(List<Salegainrule> salegainrulelist) {
		this.salegainrulelist = salegainrulelist;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getGainform() {
		return gainform;
	}
	public String getGainformname() {
		if("0".equals(gainform)){
			return "百分比";
		}else if("1".equals(gainform)){
			return "绝对数值";
		}else{
			return "";
		}
	}
	public void setGainform(String gainform) {
		this.gainform = gainform;
	}
	public String getGaintype() {
		return gaintype;
	}
	public String getGaintypemname() {
		if("0".equals(gaintype)){
			return "销售提成";
		}else if("1".equals(gaintype)){
			return "安装提成";
		}else{
			return "";
		}
	}
	public void setGaintype(String gaintype) {
		this.gaintype = gaintype;
	}
	public String getStatus() {
		return status;
	}
	public String getStatusname() {
		if("0".equals(status)){
			return "无效";
		}else if("1".equals(status)){
			return "有效";
		}else{
			return "";
		}
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
