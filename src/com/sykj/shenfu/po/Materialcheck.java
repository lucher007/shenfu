package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 */
public class Materialcheck extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String materialcode; //材料编号
	private String materialname; //材料名称
	private String batchno; //批次号
	private String materialidentfy; //材料唯一标识SN码
	private String checkinfo;     //检验信息
	private String repairinfo;   //维修信息
	private String checkercode; //检验人员编号
	private String producercode; // 生产负责人
	private String checktime;  //检验时间
	private String checkstatus; //测试结果（0-不合格维修；1-合格入库）
	private String hightemp; //高温老练(0-不合格；1-合格)
	private String currentvoltage; //电流电压(0-不合格；1-合格)
	private String displayscreen; //显示屏是否正常点亮（0-不合格；1-合格）
	private String touchscreen; //触屏是否灵敏(0-合格；1-不合格）
	private String messageconnect; //通讯是否连接(0-合格；1-不合格)
	private String openclosedoor; //开关门是否顺畅(0-不合格；1-合格)
	private String keystrokesound; //按键声音是否正常(0-合格；1-不合格）
	private String fingerprint; //内外指纹是否灵敏(0-合格；1-不合格)
	private String remark; // 备注

	private Materialcheck materialcheck;
	private List<Materialcheck> materialchecklist;
	
	private String checkername; //检验负责人

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMaterialcode() {
		return materialcode;
	}

	public void setMaterialcode(String materialcode) {
		this.materialcode = materialcode;
	}

	public String getMaterialname() {
		return materialname;
	}

	public void setMaterialname(String materialname) {
		this.materialname = materialname;
	}

	public String getCheckinfo() {
		return checkinfo;
	}

	public void setCheckinfo(String checkinfo) {
		this.checkinfo = checkinfo;
	}

	public String getRepairinfo() {
		return repairinfo;
	}

	public void setRepairinfo(String repairinfo) {
		this.repairinfo = repairinfo;
	}

	public String getCheckercode() {
		return checkercode;
	}

	public void setCheckercode(String checkercode) {
		this.checkercode = checkercode;
	}

	public String getProducercode() {
		return producercode;
	}

	public void setProducercode(String producercode) {
		this.producercode = producercode;
	}

	public String getChecktime() {
		return checktime;
	}

	public void setChecktime(String checktime) {
		this.checktime = checktime;
	}

	public String getCheckstatus() {
		return checkstatus;
	}
	
	public String getCheckstatusname() {
		if("0".equals(checkstatus)){
			return "不合格维修";
		}else if("1".equals(checkstatus)){
			return "合格入库";
		}else{
			return "";
		}
	}

	public void setCheckstatus(String checkstatus) {
		this.checkstatus = checkstatus;
	}

	public String getHightemp() {
		return hightemp;
	}
	
	public String getHightempname() {
		if("0".equals(hightemp)){
			return "不合格";
		}else if("1".equals(hightemp)){
			return "合格";
		}else{
			return "";
		}
	}

	public void setHightemp(String hightemp) {
		this.hightemp = hightemp;
	}

	public String getCurrentvoltage() {
		return currentvoltage;
	}
	
	public String getCurrentvoltagename() {
		if("0".equals(currentvoltage)){
			return "不合格";
		}else if("1".equals(currentvoltage)){
			return "合格";
		}else{
			return "";
		}
	}

	public void setCurrentvoltage(String currentvoltage) {
		this.currentvoltage = currentvoltage;
	}

	public String getDisplayscreen() {
		return displayscreen;
	}
	
	public String getDisplayscreenname() {
		if("0".equals(displayscreen)){
			return "不合格";
		}else if("1".equals(displayscreen)){
			return "合格";
		}else{
			return "";
		}
	}

	public void setDisplayscreen(String displayscreen) {
		this.displayscreen = displayscreen;
	}

	public String getTouchscreen() {
		return touchscreen;
	}
	
	public String getTouchscreenname() {
		if("0".equals(touchscreen)){
			return "不合格";
		}else if("1".equals(touchscreen)){
			return "合格";
		}else{
			return "";
		}
	}

	public void setTouchscreen(String touchscreen) {
		this.touchscreen = touchscreen;
	}

	public String getMessageconnect() {
		return messageconnect;
	}
	
	public String getMessageconnectname() {
		if("0".equals(messageconnect)){
			return "不合格";
		}else if("1".equals(messageconnect)){
			return "合格";
		}else{
			return "";
		}
	}

	public void setMessageconnect(String messageconnect) {
		this.messageconnect = messageconnect;
	}

	public String getOpenclosedoor() {
		return openclosedoor;
	}
	
	public String getOpenclosedoorname() {
		if("0".equals(openclosedoor)){
			return "不合格";
		}else if("1".equals(openclosedoor)){
			return "合格";
		}else{
			return "";
		}
	}

	public void setOpenclosedoor(String openclosedoor) {
		this.openclosedoor = openclosedoor;
	}

	public String getKeystrokesound() {
		return keystrokesound;
	}
	
	public String getKeystrokesoundname() {
		if("0".equals(keystrokesound)){
			return "不合格";
		}else if("1".equals(keystrokesound)){
			return "合格";
		}else{
			return "";
		}
	}

	public void setKeystrokesound(String keystrokesound) {
		this.keystrokesound = keystrokesound;
	}

	public String getFingerprint() {
		return fingerprint;
	}
	
	public String getFingerprintname() {
		if("0".equals(fingerprint)){
			return "不合格";
		}else if("1".equals(fingerprint)){
			return "合格";
		}else{
			return "";
		}
	}

	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Materialcheck getMaterialcheck() {
		return materialcheck;
	}

	public void setMaterialcheck(Materialcheck materialcheck) {
		this.materialcheck = materialcheck;
	}

	public List<Materialcheck> getMaterialchecklist() {
		return materialchecklist;
	}

	public void setMaterialchecklist(List<Materialcheck> materialchecklist) {
		this.materialchecklist = materialchecklist;
	}

	public String getCheckername() {
		return checkername;
	}

	public void setCheckername(String checkername) {
		this.checkername = checkername;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getBatchno() {
		return batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}

	public String getMaterialidentfy() {
		return materialidentfy;
	}

	public void setMaterialidentfy(String materialidentfy) {
		this.materialidentfy = materialidentfy;
	}

	
	
	
}
