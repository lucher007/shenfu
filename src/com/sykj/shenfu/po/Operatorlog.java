package com.sykj.shenfu.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 用户实体类
 */
public class Operatorlog extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String operatorcode; // 操作员ID
	private String content; // 操作内容
	private String addtime; // 操作时间
	private String remark; // 备注
   
	
	//---页面查询条件
	private String begintime;
	private String endtime;
	
	private Operatorlog operatorlog;
	private List<Operatorlog> operatorloglist;
	
	private Operator operator;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public Operatorlog getOperatorlog() {
		return operatorlog;
	}

	public void setOperatorlog(Operatorlog operatorlog) {
		this.operatorlog = operatorlog;
	}

	public List<Operatorlog> getOperatorloglist() {
		return operatorloglist;
	}

	public void setOperatorloglist(List<Operatorlog> operatorloglist) {
		this.operatorloglist = operatorloglist;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getBegintime() {
		return begintime;
	}

	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getOperatorcode() {
		return operatorcode;
	}

	public void setOperatorcode(String operatorcode) {
		this.operatorcode = operatorcode;
	}
	
	

}
