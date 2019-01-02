package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 */
public class Depotrackinfo extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;           // 数据库ID
	private String depotrackcode; //货柜编号
	private String depotrackname; // 货架名称
	private Integer rownum;       // 所在行数
	private Integer columnnum;    // 所在列数
	private String codeinfo;      // 显示信息
	private String remark;        // 备注

	private Depotrackinfo depotrackinfo;
	private List<Depotrackinfo> depotrackinfolist;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRownum() {
		return rownum;
	}
	public void setRownum(Integer rownum) {
		this.rownum = rownum;
	}
	public Integer getColumnnum() {
		return columnnum;
	}
	public void setColumnnum(Integer columnnum) {
		this.columnnum = columnnum;
	}
	public String getCodeinfo() {
		return codeinfo;
	}
	public void setCodeinfo(String codeinfo) {
		this.codeinfo = codeinfo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Depotrackinfo getDepotrackinfo() {
		return depotrackinfo;
	}
	public void setDepotrackinfo(Depotrackinfo depotrackinfo) {
		this.depotrackinfo = depotrackinfo;
	}
	public List<Depotrackinfo> getDepotrackinfolist() {
		return depotrackinfolist;
	}
	public void setDepotrackinfolist(List<Depotrackinfo> depotrackinfolist) {
		this.depotrackinfolist = depotrackinfolist;
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
	

}
