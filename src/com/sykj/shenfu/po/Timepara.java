package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 */
public class Timepara extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String level; //级别（正常-绿色；稍微延迟-蓝色；严重滞后-红色）
	private Integer time; //间隔时间（以小时计算）
	private String remark; // 备注

	private Timepara timepara;
	private List<Timepara> timeparalist;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLevel() {
		return level;
	}
	public String getLevelname() {
		if("1".equals(level)){
	 		return "稍微延迟(绿色显示)";
	 	}else if("2".equals(level)){
	 		return "延迟较多(蓝色显示)";
	 	}else if("3".equals(level)){
	 		return "严重滞后(红色显示)";
	 	}else{
	 		return "";
	 	}
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public Integer getTime() {
		return time;
	}
	public void setTime(Integer time) {
		this.time = time;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Timepara getTimepara() {
		return timepara;
	}
	public void setTimepara(Timepara timepara) {
		this.timepara = timepara;
	}
	public List<Timepara> getTimeparalist() {
		return timeparalist;
	}
	public void setTimeparalist(List<Timepara> timeparalist) {
		this.timeparalist = timeparalist;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
