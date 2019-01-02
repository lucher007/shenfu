package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

import fr.opensagres.xdocreport.core.utils.StringUtils;

/**
 * 用户实体类
 */
public class Userdelivery extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String usercode; // 客户编号
	private String ordercode; // 订单号
	private String deliverycode; //快递单号
	private String deliveryname; //发货快递名称
	private String deliverytime; //发货时间
	private String content; //快递内容
	private String deliverercode;//送货人编号
	private String deliverername; //送货人姓名
	private String delivererphone; //送货人电话
	private String status;   //送货单状态(0-未到货；1-已到货）
	private String remark; // 备注

	private Userdelivery userdelivery;
	private List<Userdelivery> userdeliverylist;
	
    private User user;
    private Userorder userorder;
    
    //---页面查询条件
    private String begintime;
  	private String endtime;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDeliveryname() {
		return deliveryname;
	}
	public void setDeliveryname(String deliveryname) {
		this.deliveryname = deliveryname;
	}
	public String getDeliverytime() {
		return deliverytime;
	}
	public void setDeliverytime(String deliverytime) {
		this.deliverytime = deliverytime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Userdelivery getUserdelivery() {
		return userdelivery;
	}
	public void setUserdelivery(Userdelivery userdelivery) {
		this.userdelivery = userdelivery;
	}
	public List<Userdelivery> getUserdeliverylist() {
		return userdeliverylist;
	}
	public void setUserdeliverylist(List<Userdelivery> userdeliverylist) {
		this.userdeliverylist = userdeliverylist;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Userorder getUserorder() {
		return userorder;
	}
	public void setUserorder(Userorder userorder) {
		this.userorder = userorder;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	public String getDeliverycode() {
		return deliverycode;
	}
	public void setDeliverycode(String deliverycode) {
		this.deliverycode = deliverycode;
	}
	public String getDeliverercode() {
		return deliverercode;
	}
	public void setDeliverercode(String deliverercode) {
		this.deliverercode = deliverercode;
	}
	public String getDeliverername() {
		return deliverername;
	}
	public void setDeliverername(String deliverername) {
		this.deliverername = deliverername;
	}
	public String getDelivererphone() {
		return delivererphone;
	}
	public void setDelivererphone(String delivererphone) {
		this.delivererphone = delivererphone;
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
    
	//重写父类方法
	public String getSort() {
		if(StringUtils.isNotEmpty(sort)){
			return sort;
		}else{
			return "deliverytime";
		}
	}
	
	public String getOrder() {
		if(StringUtils.isNotEmpty(order)){
			return order;
		}else{
			return "desc";
		}
	}
	public String getStatus() {
		return status;
	}
	
	public String getStatusname() {
		if("0".equals(status)){
			return "未到货";
		}else if("1".equals(status)){
			return "已到货";
		}else{
			return "";
		}
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

}
