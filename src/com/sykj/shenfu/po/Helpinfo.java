package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

import fr.opensagres.xdocreport.core.utils.StringUtils;
/**
 * 用户实体类
 */
public class Helpinfo extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;              //数据库ID
	private String type;             //类型(0-行动力)
	private String question;         //问题
	private String answer;           //答案
	private String status;           //状态(0-无效;1-有效)
	private String addtime;          //添加时间
	private Double showorder;        //显示顺序
	private String remark;           //备注
	
	/******************数据库辅助字段*************************/
	private Helpinfo helpinfo;
	private List<Helpinfo> helpinfolist;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public String getTypename() {
		if("0".equals(type)){
			return "行动力";
		}else{
			return "";
		}
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
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
	public Helpinfo getHelpinfo() {
		return helpinfo;
	}
	public void setHelpinfo(Helpinfo helpinfo) {
		this.helpinfo = helpinfo;
	}
	public List<Helpinfo> getHelpinfolist() {
		return helpinfolist;
	}
	public void setHelpinfolist(List<Helpinfo> helpinfolist) {
		this.helpinfolist = helpinfolist;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Double getShoworder() {
		return showorder;
	}
	public void setShoworder(Double showorder) {
		this.showorder = showorder;
	}
	
	//重写父类方法
	public String getSort() {
		if(StringUtils.isNotEmpty(sort)){
			return sort;
		}else{
			return "showorder";
		}
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		if(StringUtils.isNotEmpty(order)){
			return order;
		}else{
			return "asc";
		}
	}
	public void setOrder(String order) {
		this.order = order;
	}
	
	
}
