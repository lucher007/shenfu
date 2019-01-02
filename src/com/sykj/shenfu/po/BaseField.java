package com.sykj.shenfu.po;

import java.io.Serializable;

import fr.opensagres.xdocreport.core.utils.StringUtils;

/**
 * 基础实体类
 * @author tangsh
 *
 */
public class BaseField implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String returninfo;
	private String returnurl;
	private String flag;
	private int returncode;
	private String jumpurl; //页面跳转
	private Operator operator;
	
	
	//页面分页参数
	private int pager_count;    //总数
	private int pager_offset;   //每次查询开始数
	private int pager_openset;  //每页显示行数
	private int pager_visit;
	
	//easyui 分页参数
	private int page; // 当前页数  
	private int rows; // 每页显示行数  
	private int page_start; //每次查询开始数
	private int total;  //总数
	public String sort; //排序列字段名
	public String order; //排序方式，可以是 'asc' 或者 'desc'，默认值是 'desc'
	
	public String getReturninfo() {
		return returninfo;
	}
	public void setReturninfo(String returninfo) {
		this.returninfo = returninfo;
	}
	public String getReturnurl() {
		return returnurl;
	}
	public void setReturnurl(String returnurl) {
		this.returnurl = returnurl;
	}
	public int getReturncode() {
		return returncode;
	}
	public void setReturncode(int returncode) {
		this.returncode = returncode;
	}
	public int getPager_count() {
		return pager_count;
	}
	public void setPager_count(int pager_count) {
		this.pager_count = pager_count;
	}
	public int getPager_offset() {
		return pager_offset;
	}
	public void setPager_offset(int pager_offset) {
		this.pager_offset = pager_offset;
	}
	public int getPager_openset() {
		return pager_openset;
	}
	public void setPager_openset(int pager_openset) {
		this.pager_openset = pager_openset;
	}
	public int getPager_visit() {
		return pager_visit;
	}
	public void setPager_visit(int pager_visit) {
		this.pager_visit = pager_visit;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getPage_start() {
		return (page - 1) * rows;
	}
	public void setPage_start(int page_start) {
		this.page_start = page_start;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getJumpurl() {
		return jumpurl;
	}
	public void setJumpurl(String jumpurl) {
		this.jumpurl = jumpurl;
	}
	public String getSort() {
		if(StringUtils.isNotEmpty(sort)){
			return sort;
		}else{
			return "id";
		}
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		if(StringUtils.isNotEmpty(order)){
			return order;
		}else{
			return "desc";
		}
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public Operator getOperator() {
		return operator;
	}
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
}
