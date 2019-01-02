package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;
/**
 * 用户实体类
 */
public class Giftcard extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;                //数据库ID
	private String giftcardno;         //充值卡号
	private String modelcode;	       //产品型号名称
	private String modelname;		   //批次号
	private Integer amount;            //金额
	private String status;             //状态（0-未使用；1-已使用）
	private String employeecode;       //销售员编号
	private String usercode;            //客户编号
	private String ordercode;          //订单编号
	private String addtime;             //生成时间
	private String starttime;          //有效开始时间
	private String endtime;            //有效结束日期
	private String usetime;             //使用时间
	private String remark;             //备注
	
	/******************数据库辅助字段*************************/
	private Giftcard giftcard;
	private List<Giftcard> giftcardlist;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGiftcardno() {
		return giftcardno;
	}
	public void setGiftcardno(String giftcardno) {
		this.giftcardno = giftcardno;
	}
	public String getModelcode() {
		return modelcode;
	}
	public void setModelcode(String modelcode) {
		this.modelcode = modelcode;
	}
	public String getModelname() {
		return modelname;
	}
	public void setModelname(String modelname) {
		this.modelname = modelname;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getOrdercode() {
		return ordercode;
	}
	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getUsetime() {
		return usetime;
	}
	public void setUsetime(String usetime) {
		this.usetime = usetime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Giftcard getGiftcard() {
		return giftcard;
	}
	public void setGiftcard(Giftcard giftcard) {
		this.giftcard = giftcard;
	}
	public List<Giftcard> getGiftcardlist() {
		return giftcardlist;
	}
	public void setGiftcardlist(List<Giftcard> giftcardlist) {
		this.giftcardlist = giftcardlist;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getEmployeecode() {
		return employeecode;
	}
	public void setEmployeecode(String employeecode) {
		this.employeecode = employeecode;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	@Override
	public String toString() {
		return "Giftcard [id=" + id + ", giftcardno=" + giftcardno
				+ ", modelcode=" + modelcode + ", modelname=" + modelname
				+ ", amount=" + amount + ", status=" + status
				+ ", employeecode=" + employeecode + ", usercode=" + usercode
				+ ", ordercode=" + ordercode + ", addtime=" + addtime
				+ ", starttime=" + starttime + ", endtime=" + endtime
				+ ", usetime=" + usetime + ", remark=" + remark + ", giftcard="
				+ giftcard + ", giftcardlist=" + giftcardlist + "]";
	}
	
}
