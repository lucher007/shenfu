package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 */
public class Userdispatchfile extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String usercode; // 客户编号
	private String ordercode; // 订单编号
	private String dispatchcode; //工单编号
	private String filename; //原名文件名
	private String preservefilename; //服务器保存文件名
	private String preserveurl; //存放地址
	private String addtime;     //上传时间
	private String remark; // 备注

	private Userdispatchfile userdispatchfile;
	private List<Userdispatchfile> userdispatchfilelist;
	
    private User user;
    private Userorder userorder;
    private Userdispatch userdispatch;
    
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
	public String getDispatchcode() {
		return dispatchcode;
	}
	public void setDispatchcode(String dispatchcode) {
		this.dispatchcode = dispatchcode;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Userdispatchfile getUserdispatchfile() {
		return userdispatchfile;
	}
	public void setUserdispatchfile(Userdispatchfile userdispatchfile) {
		this.userdispatchfile = userdispatchfile;
	}
	public List<Userdispatchfile> getUserdispatchfilelist() {
		return userdispatchfilelist;
	}
	public void setUserdispatchfilelist(List<Userdispatchfile> userdispatchfilelist) {
		this.userdispatchfilelist = userdispatchfilelist;
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
	public Userdispatch getUserdispatch() {
		return userdispatch;
	}
	public void setUserdispatch(Userdispatch userdispatch) {
		this.userdispatch = userdispatch;
	}
    
    

}
