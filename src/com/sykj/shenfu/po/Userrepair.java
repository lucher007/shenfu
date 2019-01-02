package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 */
public class Userrepair extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String repaircode; //维修单号
	private String usercode; // 客户ID
	private String ordercode; // 订单号
	private String productidentfy; //产品标识
	private String repairercode; //维修人编号
    private String operatorcode; //操作员编号
    private String repairtime;    //维修时间
    private String addtime;     //添加时间
    private String content;    //维修问题
    private String dealdate;   //处理时间
    private String dealresult;  //处理结果
    private String status;      //状态（0-未受理；1-受理中；2-已处理；3-处理失败； 4-结单）
	private String remark; // 备注
	
	private Userrepair userrepair;
	private List<Userrepair> userrepairlist;
	
    private User user;
    private Userorder userorder;
    private Employee repairer;  //维修人员
    private Userproduct userproduct; //维修产品
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getProductidentfy() {
		return productidentfy;
	}
	public void setProductidentfy(String productidentfy) {
		this.productidentfy = productidentfy;
	}
	public String getRepairtime() {
		return repairtime;
	}
	public void setRepairtime(String repairtime) {
		this.repairtime = repairtime;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDealdate() {
		return dealdate;
	}
	public void setDealdate(String dealdate) {
		this.dealdate = dealdate;
	}
	public String getDealresult() {
		return dealresult;
	}
	public void setDealresult(String dealresult) {
		this.dealresult = dealresult;
	}
	public String getStatus() {
		return status;
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
	public Userrepair getUserrepair() {
		return userrepair;
	}
	public void setUserrepair(Userrepair userrepair) {
		this.userrepair = userrepair;
	}
	public List<Userrepair> getUserrepairlist() {
		return userrepairlist;
	}
	public void setUserrepairlist(List<Userrepair> userrepairlist) {
		this.userrepairlist = userrepairlist;
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
	public Employee getRepairer() {
		return repairer;
	}
	public void setRepairer(Employee repairer) {
		this.repairer = repairer;
	}
	public Userproduct getUserproduct() {
		return userproduct;
	}
	public void setUserproduct(Userproduct userproduct) {
		this.userproduct = userproduct;
	}
	public String getRepaircode() {
		return repaircode;
	}
	public void setRepaircode(String repaircode) {
		this.repaircode = repaircode;
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
	public String getRepairercode() {
		return repairercode;
	}
	public void setRepairercode(String repairercode) {
		this.repairercode = repairercode;
	}
	public String getOperatorcode() {
		return operatorcode;
	}
	public void setOperatorcode(String operatorcode) {
		this.operatorcode = operatorcode;
	}
	
	
    

}
