package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 */
public class Depotrack extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;                  // 数据库ID
	private String depotrackcode;        // 货柜编号
	private String depotrackname;        // 货柜名称
	private Integer rownums;    // 总层数
	private Integer columnnums; //总列数
	private Integer racknum;    //仓位数
	private String remark;     //备注

	private Depotrack depotrack;
	private List<Depotrack> depotracklist;
	
	private List<Depotrackinfo> depotrackinfolist;
	
	//页面显示用
	private List<String> strList;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRownums() {
		return rownums;
	}
	public void setRownums(Integer rownums) {
		this.rownums = rownums;
	}
	public Integer getColumnnums() {
		return columnnums;
	}
	public void setColumnnums(Integer columnnums) {
		this.columnnums = columnnums;
	}
	public Integer getRacknum() {
		return racknum;
	}
	public void setRacknum(Integer racknum) {
		this.racknum = racknum;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Depotrack getDepotrack() {
		return depotrack;
	}
	public void setDepotrack(Depotrack depotrack) {
		this.depotrack = depotrack;
	}
	public List<Depotrack> getDepotracklist() {
		return depotracklist;
	}
	public void setDepotracklist(List<Depotrack> depotracklist) {
		this.depotracklist = depotracklist;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getDepotrackcode() {
		return depotrackcode;
	}
	public void setDepotrackcode(String depotrackcode) {
		this.depotrackcode = depotrackcode;
	}
	public String getDepotrackname() {
		return depotrackname;
	}
	public void setDepotrackname(String depotrackname) {
		this.depotrackname = depotrackname;
	}
	public List<String> getStrList() {
		return strList;
	}
	public void setStrList(List<String> strList) {
		this.strList = strList;
	}
	public List<Depotrackinfo> getDepotrackinfolist() {
		return depotrackinfolist;
	}
	public void setDepotrackinfolist(List<Depotrackinfo> depotrackinfolist) {
		this.depotrackinfolist = depotrackinfolist;
	}

}
