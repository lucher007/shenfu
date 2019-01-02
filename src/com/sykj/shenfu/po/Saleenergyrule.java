package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 */
public class Saleenergyrule extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;         //数据库ID
	private String energycode;  //行动力编码
	private String energyname;  //行动力名称
	private Integer energyrate; //行动力兑换值
	private String remark;      //备注

	private Saleenergyrule saleenergyrule;
	private List<Saleenergyrule> saleenergyrulelist;
	
	//销售渠道行动力
	public static final String energycode_sale_channel = "sale_channel";
	//上一级销售行动力
	public static final String energycode_upper_one = "upper_one";
	//上上级销售行动力
	public static final String energycode_upper_two = "upper_two";
	//当季合格行动力分
	public static final String energycode_saleenergy_standard = "saleenergy_standard";
	//行动力兑换提成比例
    public static final String energycode_energy_gain = "energy_gain";
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEnergycode() {
		return energycode;
	}
	public void setEnergycode(String energycode) {
		this.energycode = energycode;
	}
	public String getEnergyname() {
		return energyname;
	}
	public void setEnergyname(String energyname) {
		this.energyname = energyname;
	}
	public Integer getEnergyrate() {
		return energyrate;
	}
	public void setEnergyrate(Integer energyrate) {
		this.energyrate = energyrate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Saleenergyrule getSaleenergyrule() {
		return saleenergyrule;
	}
	public void setSaleenergyrule(Saleenergyrule saleenergyrule) {
		this.saleenergyrule = saleenergyrule;
	}
	public List<Saleenergyrule> getSaleenergyrulelist() {
		return saleenergyrulelist;
	}
	public void setSaleenergyrulelist(List<Saleenergyrule> saleenergyrulelist) {
		this.saleenergyrulelist = saleenergyrulelist;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public static String getEnergycodeSaleChannel() {
		return energycode_sale_channel;
	}
	public static String getEnergycodeUpperOne() {
		return energycode_upper_one;
	}
	public static String getEnergycodeUpperTwo() {
		return energycode_upper_two;
	}

}
