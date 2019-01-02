package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 */
public class Salegaininfo extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String gainercode; //领取提成员工编号
	private String usercode; // 客户ID
	private String username; //客户姓名
	private String phone; // 联系电话
	private String address; //安装地址
	private String source;   //客户来源（0-销售；1-400）
	private String visittype; //上门类型（0.公司派人讲解测量，1.销售员亲自讲解测量; 2-已讲解，公司派人只需测量）;
	private String taskcode; //任务单ID
	private String salercode; //销售员编号
	private String visitorcode; //上门人员
	private String ordercode; // 订单号
	private Integer totalmoney; //总金额
	
	private String gaincode;  //提成编码
	private String gainname;  //提成名称
	private Integer gainrate; //提成百分比
	private Integer gainmoney; //提成金额
	private Integer energyscore; //行动力分
	private Integer energyinfoid; //行动力记录ID
	private String addtime; //添加时间
	private String status;   //状态(0未到账；1-已到账)
	private String gaintime; //领取时间
	private String gainlogcode;//提取记录编号
	private String remark; // 备注

	private Salegaininfo salegaininfo;
	private List<Salegaininfo> salegaininfolist;
	
    private User user;
    private Employee gainer;
    private Employee visitor;
    private Employee saler;
    private Usertask usertask;
    
    private Integer gaintotalmoney;
    
    //---页面查询条件
  	private String begintime;
  	private String endtime;
  	private String ids;
  	private String salegaininfoStatJson;//页面选中的需要提现的汇总
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGainercode() {
		return gainercode;
	}
	public void setGainercode(String gainercode) {
		this.gainercode = gainercode;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSource() {
		return source;
	}
	public String getSourcename() {
		if("0".equals(this.source)){
			return "销售";
		}else if("1".equals(this.source)){
			return "400客服";
		} else {
			return "";
		}
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getVisittype() {
		return visittype;
	}
	public String getVisittypename() {
		if("0".equals(this.visittype)){
			return "公司派人讲解测量";
		} else if("1".equals(this.visittype)){
			return "销售员亲自讲解测量";
		} else if("2".equals(this.visittype)){
			return "已讲解，公司派人只需测量";
		} else {
			return "";
		}
	}
	public void setVisittype(String visittype) {
		this.visittype = visittype;
	}
	public String getTaskcode() {
		return taskcode;
	}
	public void setTaskcode(String taskcode) {
		this.taskcode = taskcode;
	}
	public String getSalercode() {
		return salercode;
	}
	public void setSalercode(String salercode) {
		this.salercode = salercode;
	}
	public String getVisitorcode() {
		return visitorcode;
	}
	public void setVisitorcode(String visitorcode) {
		this.visitorcode = visitorcode;
	}
	public String getOrdercode() {
		return ordercode;
	}
	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}
	public Integer getTotalmoney() {
		return totalmoney;
	}
	public void setTotalmoney(Integer totalmoney) {
		this.totalmoney = totalmoney;
	}
	public String getGaincode() {
		return gaincode;
	}
	public void setGaincode(String gaincode) {
		this.gaincode = gaincode;
	}
	public String getGainname() {
		return gainname;
	}
	public void setGainname(String gainname) {
		this.gainname = gainname;
	}
	public Integer getGainrate() {
		return gainrate;
	}
	public void setGainrate(Integer gainrate) {
		this.gainrate = gainrate;
	}
	public Integer getGainmoney() {
		return gainmoney;
	}
	public void setGainmoney(Integer gainmoney) {
		this.gainmoney = gainmoney;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getGaintime() {
		return gaintime;
	}
	public void setGaintime(String gaintime) {
		this.gaintime = gaintime;
	}
	public String getStatus() {
		return status;
	}
	public String getStatusname() {
		if("0".equals(status)){
			return "未提取";
		}else if("1".equals(status)){
			return "已提取";
		}else{
			return "";
		}
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Salegaininfo getSalegaininfo() {
		return salegaininfo;
	}
	public void setSalegaininfo(Salegaininfo salegaininfo) {
		this.salegaininfo = salegaininfo;
	}
	public List<Salegaininfo> getSalegaininfolist() {
		return salegaininfolist;
	}
	public void setSalegaininfolist(List<Salegaininfo> salegaininfolist) {
		this.salegaininfolist = salegaininfolist;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Employee getGainer() {
		return gainer;
	}
	public void setGainer(Employee gainer) {
		this.gainer = gainer;
	}
	public Employee getVisitor() {
		return visitor;
	}
	public void setVisitor(Employee visitor) {
		this.visitor = visitor;
	}
	public Employee getSaler() {
		return saler;
	}
	public void setSaler(Employee saler) {
		this.saler = saler;
	}
	public Usertask getUsertask() {
		return usertask;
	}
	public void setUsertask(Usertask usertask) {
		this.usertask = usertask;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getGaintotalmoney() {
		return gaintotalmoney;
	}
	public void setGaintotalmoney(Integer gaintotalmoney) {
		this.gaintotalmoney = gaintotalmoney;
	}
	public String getGainlogcode() {
		return gainlogcode;
	}
	public void setGainlogcode(String gainlogcode) {
		this.gainlogcode = gainlogcode;
	}
	public Integer getEnergyscore() {
		return energyscore;
	}
	public void setEnergyscore(Integer energyscore) {
		this.energyscore = energyscore;
	}
	public Integer getEnergyinfoid() {
		return energyinfoid;
	}
	public void setEnergyinfoid(Integer energyinfoid) {
		this.energyinfoid = energyinfoid;
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
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getSalegaininfoStatJson() {
		return salegaininfoStatJson;
	}
	public void setSalegaininfoStatJson(String salegaininfoStatJson) {
		this.salegaininfoStatJson = salegaininfoStatJson;
	}
   
	
	

}
