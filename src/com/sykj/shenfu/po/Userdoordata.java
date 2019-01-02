package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 */
public class Userdoordata extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String usercode; // 客户编号
	private String ordercode;//订单编号
	private String locklength; //锁侧板长度
	private String lockwidth; //锁侧板宽度
	private String doorlength; //门侧板长度
	private String doorwidth; //门侧板宽度
	private String addercode; //添加人员编号
	private String addtime;   //添加时间
	private String remark; // 备注

	private Userdoordata userdoordata;
	private List<Userdoordata> userdoordatalist;
	
    private User user;
    private Userorder userorder;
    
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getLocklength() {
		return locklength;
	}
	public void setLocklength(String locklength) {
		this.locklength = locklength;
	}
	public String getLockwidth() {
		return lockwidth;
	}
	public void setLockwidth(String lockwidth) {
		this.lockwidth = lockwidth;
	}
	public String getDoorlength() {
		return doorlength;
	}
	public void setDoorlength(String doorlength) {
		this.doorlength = doorlength;
	}
	public String getDoorwidth() {
		return doorwidth;
	}
	public void setDoorwidth(String doorwidth) {
		this.doorwidth = doorwidth;
	}
	public String getAddercode() {
		return addercode;
	}
	public void setAddercode(String addercode) {
		this.addercode = addercode;
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
	public Userdoordata getUserdoordata() {
		return userdoordata;
	}
	public void setUserdoordata(Userdoordata userdoordata) {
		this.userdoordata = userdoordata;
	}
	public List<Userdoordata> getUserdoordatalist() {
		return userdoordatalist;
	}
	public void setUserdoordatalist(List<Userdoordata> userdoordatalist) {
		this.userdoordatalist = userdoordatalist;
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
    
    

}
