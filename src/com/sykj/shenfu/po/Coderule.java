package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 */
public class Coderule extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String codetype; //编码类型
	private String precode; // 前缀编码
	private String sufcode; //后缀编码
	private String codevalue; //最新编码值
	private String remark; // 备注

	private Coderule coderule;
	private List<Coderule> coderulelist;
	
	//员工编号
	public static final String CODETYPE_employeecode = "employeecode";
	//客户编号
	public static final String CODETYPE_usercode = "usercode";
	//任务编号
	public static final String CODETYPE_taskcode = "taskcode";
	//订单编号
	public static final String CODETYPE_ordercode = "ordercode";
	//工单号
	public static final String CODETYPE_dispatchcode = "dispatchcode";
	//投诉号
	public static final String CODETYPE_complaintcode = "complaintcode";
	//报修号
	public static final String CODETYPE_repaircode = "repaircode";
	//合同编号
	public static final String CODETYPE_contractcode = "contractcode";
	//产品唯一识别号
	public static final String CODETYPE_productidentfy = "productidentfy";
	//材料唯一识别号
	public static final String CODETYPE_materialidentfy = "materialidentfy";
	//提成提取编号
	public static final String CODETYPE_gainlogcode = "gainlogcode";
	//生产批次号
	public static final String CODETYPE_producecode = "producecode";
	//小区编号
	public static final String CODETYPE_cellcode = "cellcode";
	//小区发布编号
    public static final String CODETYPE_cellextendcode = "cellextendcode";
    //小区驻点编号
    public static final String CODETYPE_cellstationcode = "cellstationcode";
    //元器件批次号
    public static final String CODETYPE_componentidentfy = "componentidentfy";
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCodetype() {
		return codetype;
	}
	public void setCodetype(String codetype) {
		this.codetype = codetype;
	}
	public String getPrecode() {
		return precode;
	}
	public void setPrecode(String precode) {
		this.precode = precode;
	}
	public String getSufcode() {
		return sufcode;
	}
	public void setSufcode(String sufcode) {
		this.sufcode = sufcode;
	}
	public String getCodevalue() {
		return codevalue;
	}
	public void setCodevalue(String codevalue) {
		this.codevalue = codevalue;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Coderule getCoderule() {
		return coderule;
	}
	public void setCoderule(Coderule coderule) {
		this.coderule = coderule;
	}
	public List<Coderule> getCoderulelist() {
		return coderulelist;
	}
	public void setCoderulelist(List<Coderule> coderulelist) {
		this.coderulelist = coderulelist;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
