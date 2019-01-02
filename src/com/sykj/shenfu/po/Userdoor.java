package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 */
public class Userdoor extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String usercode; // 客户编号
	private String ordercode;//订单编号
	private String filename; //原名文件名
	private String preservefilename; //服务器保存文件名
	private String preserveurl; //存放地址
	private String addtime;     //上传时间
	private String addercode; //添加人员编号
	private String remark; // 备注

	private Userdoor userdoor;
	private List<Userdoor> userdoorlist;
	
    private User user;
    private Userorder userorder;
    private Userdoordata userdoordata;
    
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
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getPreservefilename() {
		return preservefilename;
	}
	public void setPreservefilename(String preservefilename) {
		this.preservefilename = preservefilename;
	}
	public String getPreserveurl() {
		return preserveurl;
	}
	public void setPreserveurl(String preserveurl) {
		this.preserveurl = preserveurl;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getAddercode() {
		return addercode;
	}
	public void setAddercode(String addercode) {
		this.addercode = addercode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Userdoor getUserdoor() {
		return userdoor;
	}
	public void setUserdoor(Userdoor userdoor) {
		this.userdoor = userdoor;
	}
	public List<Userdoor> getUserdoorlist() {
		return userdoorlist;
	}
	public void setUserdoorlist(List<Userdoor> userdoorlist) {
		this.userdoorlist = userdoorlist;
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
	public String getOrdercode() {
		return ordercode;
	}
	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}
	public Userdoordata getUserdoordata() {
		return userdoordata;
	}
	public void setUserdoordata(Userdoordata userdoordata) {
		this.userdoordata = userdoordata;
	}
    

}
