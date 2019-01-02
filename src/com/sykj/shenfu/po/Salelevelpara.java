package com.sykj.shenfu.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 用户实体类
 */
public class Salelevelpara extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String level; //级别（正常-绿色；稍微延迟-蓝色；严重滞后-红色）
	private Integer minscore; //最小对应积分（包含）
	private Integer maxscore; //最大对应积分（不包含）
	private Integer reduce; //每天减少量
	private String state;  //状态（0-无效；1-有效）
	private String remark; // 备注

	private Salelevelpara salelevelpara;
	private List<Salelevelpara> salelevelparalist;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Salelevelpara getSalelevelpara() {
		return salelevelpara;
	}
	public void setSalelevelpara(Salelevelpara salelevelpara) {
		this.salelevelpara = salelevelpara;
	}
	public List<Salelevelpara> getSalelevelparalist() {
		return salelevelparalist;
	}
	public void setSalelevelparalist(List<Salelevelpara> salelevelparalist) {
		this.salelevelparalist = salelevelparalist;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getReduce() {
		return reduce;
	}
	public void setReduce(Integer reduce) {
		this.reduce = reduce;
	}
	public Integer getMinscore() {
		return minscore;
	}
	public void setMinscore(Integer minscore) {
		this.minscore = minscore;
	}
	public Integer getMaxscore() {
		return maxscore;
	}
	public void setMaxscore(Integer maxscore) {
		this.maxscore = maxscore;
	}

}
