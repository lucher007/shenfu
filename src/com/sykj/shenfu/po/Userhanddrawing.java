package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 */
public class Userhanddrawing extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String usercode; // 客户ID
	private String taskcode; // 任务单号
	private String filename; //原名文件名
	private String preservefilename; //服务器保存文件名
	private String preserveurl; //存放地址
	private String addtime;     //上传时间
	private String remark; // 备注

	private Userhanddrawing userhanddrawing;
	private List<Userhanddrawing> userhanddrawinglist;
    private User user;
    private Usertask usertask;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
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
	public String getTaskcode() {
		return taskcode;
	}
	public void setTaskcode(String taskcode) {
		this.taskcode = taskcode;
	}
	public Usertask getUsertask() {
		return usertask;
	}
	public void setUsertask(Usertask usertask) {
		this.usertask = usertask;
	}
	public Userhanddrawing getUserhanddrawing() {
		return userhanddrawing;
	}
	public void setUserhanddrawing(Userhanddrawing userhanddrawing) {
		this.userhanddrawing = userhanddrawing;
	}
	public List<Userhanddrawing> getUserhanddrawinglist() {
		return userhanddrawinglist;
	}
	public void setUserhanddrawinglist(List<Userhanddrawing> userhanddrawinglist) {
		this.userhanddrawinglist = userhanddrawinglist;
	}
    
    

}
