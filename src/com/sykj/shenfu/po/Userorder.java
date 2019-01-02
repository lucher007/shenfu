package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import fr.opensagres.xdocreport.core.utils.StringUtils;

/**
 * 用户实体类
 */
public class Userorder extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private String ordercode; // 订单号
	private String usercode; // 客户ID
	private String username; //客户姓名
	private String usersex; // 客户性别(0-女；1-男)
	private String phone; // 联系电话
	private String address; //安装地址
	private String source;   //客户来源（0-销售APP；1-400客服；2-小区驻点）
	private String visittype; //上门类型（0.公司派人讲解测量，1.销售员亲自讲解测量; 2-已讲解，公司派人只需测量）;
	private String salercode; //销售员编号
	private String talkercode; //讲解员编号
	private String visitorcode; //上门人员
	private String visitorstatus; //是否已派任务单(0-否；1-已派）
	private Integer productfee; //总金额
	private Integer otherfee; //总金额
	private Integer gaintotalmoney; //获取提成的总金额
	private Integer totalmoney; //总金额
	private Integer discountfee; //折扣金额
	private Integer firstpayment;  //首款金额
	private String firstarrivalflag;  //首付款到账标识（0-未到账；1-已到账）
	private Integer finalpayment;//尾款金额
	private String finalarrivalflag;// 尾款到账标识（0-未到账；1-已到账）
	private String paytype;     //支付类型(0-支付定金；1-货到付款)
	private String addtime; //添加时间
	private String status;   //状态（0-新生成；1-首款到账；2-发货；3-已派工；4-安装完成；5-尾款到账；6-审核结单）
	private String operatetime; //操作时间
	private String filingflag; //是否归档（0-未归档；1-已归档）
	private String installercode; //安装人编号
	private String stationcode;  //驻点编号
	private String lockcoreflag; //是否带机械锁心(0-不需要；1-需要)
	private String productcolor; //产品颜色(0101-摩卡棕；0102-石英灰；0103-香槟金)
	private String visitorflag; //是否需要派人测量(0-不需要；1-需要)
	private String productconfirm; //产品是否确认完成（0-未确认；1-已经确认）
	private String modelcode; //产品型号编码
	private String modelname; //产品型号名称
	private Integer discountgain; //优惠权限
	private Integer fixedgain; //固定返利
	private Integer managergain; //销售管家提成 
	private String managercode; //销售管家编号
	private String orderinfo;   //订单详情
	private String remark; // 备注

	private Userorder userorder;
	private List<Userorder> userorderlist;
	
    private User user;
    private Employee visitor;
    private Employee saler;
    private Employee talker;;
    private Usertask usertask;
    private Userproduct userproduct;
    private List<Userproduct> userproductList;
    private Productdepot productdepot;
    private Operator operator;
    
    private List<Productcolor> productcolorList;
    private List<Productmodel> productmodelList;
    private List<Product> productList;
    
    private List<Userdispatch> userdispatchlist;
    private Userdispatch userdispatch;
    private Userdelivery userdelivery;
    private List<Userdelivery> userdeliverylist;
    
    private String userproductJson;
    
    //页面查询操作时效性需要参数
    private String timelevel; //操作时效性等级
    private Integer minopteratetime;//最小时效
    private Integer maxopteratetime;//最大时效
    
    private String deliverytime;//快递发货时间
    private String deliveryname;//快递名称
    private String deliverycode; //快递编号
    private String content;     //快递内容
    private String deliverercode;//送货人编号
	private String deliverername; //送货人姓名
	private String delivererphone; //送货人电话
    private Employee installer; //安装人对象
    private String installername; //安装人姓名
    private String installerphone; //安装人姓名
    
    private String taskcode;
    private List<Userorderinfo> userorderinfolist; //订单详情信息列表
    private String payitem;  //付款项目(1-首付款；2-尾款)
    
    private String queryForPack; //打包查询条件
    private String queryForReplace; //产品替换条件查询
    
    private String locklength;  //锁侧板长度
    private String lockwidth;   //锁侧板宽度
    private MultipartFile[] files;  //页面传递参数文件
    private String giftcardno; //优惠卡号
    private Giftcard giftcard;
    private Employee manager;
    private String managename;
    
    //---页面查询条件
  	private String begintime;
  	private String endtime;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsersex() {
		return usersex;
	}
	public String getUsersexname() {
		if("0".equals(this.usersex)){
			return "女";
		}else if("1".equals(this.usersex)){
			return "男";
		} else {
			return "";
		}
	}
	public void setUsersex(String usersex) {
		this.usersex = usersex;
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
	public Integer getTotalmoney() {
		return totalmoney;
	}
	public void setTotalmoney(Integer totalmoney) {
		this.totalmoney = totalmoney;
	}
	public Integer getFirstpayment() {
		return firstpayment;
	}
	public void setFirstpayment(Integer firstpayment) {
		this.firstpayment = firstpayment;
	}
	public Integer getFinalpayment() {
		return finalpayment;
	}
	public void setFinalpayment(Integer finalpayment) {
		this.finalpayment = finalpayment;
	}
	public String getPaytype() {
		return paytype;
	}
	public String getPaytypename() {
		if("0".equals(paytype)){
			return "支付定金";
		}else if("1".equals(paytype)){
			return "货到付款";
		}else{
			return "";
		}
	}
	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getStatus() {
		return status;
	}
	public String getStatusname() {
		if("0".equals(this.status)){
			return "新生成订单";
		}else if("1".equals(this.status)){
			return "已打包";
		}else if("2".equals(this.status)){
			return "已发货";
		}else if("3".equals(this.status)){
			return "已派工";
		}else if("4".equals(this.status)){
			return "派工接收";
		}else if("5".equals(this.status)){
			return "安装完成";
		}else if("6".equals(this.status)){
			return "审核结单";
		}else if("10".equals(this.status)){
			return "安装失败";
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
	public Userorder getUserorder() {
		return userorder;
	}
	public void setUserorder(Userorder userorder) {
		this.userorder = userorder;
	}
	public List<Userorder> getUserorderlist() {
		return userorderlist;
	}
	public void setUserorderlist(List<Userorder> userorderlist) {
		this.userorderlist = userorderlist;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Userproduct getUserproduct() {
		return userproduct;
	}
	public void setUserproduct(Userproduct userproduct) {
		this.userproduct = userproduct;
	}
	public List<Userproduct> getUserproductList() {
		return userproductList;
	}
	public void setUserproductList(List<Userproduct> userproductList) {
		this.userproductList = userproductList;
	}
	public Productdepot getProductdepot() {
		return productdepot;
	}
	public Employee getVisitor() {
		return visitor;
	}
	public void setVisitor(Employee visitor) {
		this.visitor = visitor;
	}
	public void setProductdepot(Productdepot productdepot) {
		this.productdepot = productdepot;
	}
	public Operator getOperator() {
		return operator;
	}
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getSource() {
		return source;
	}
	public String getSourcename() {
		if("0".equals(this.source)){
			return "销售";
		}else if("1".equals(this.source)){
			return "400客服";
		}else if("2".equals(this.source)){
			return "小区驻点";
		} else {
			return "";
		}
	}
	public void setSource(String source) {
		this.source = source;
	}
	public List<Productcolor> getProductcolorList() {
		return productcolorList;
	}
	public void setProductcolorList(List<Productcolor> productcolorList) {
		this.productcolorList = productcolorList;
	}
	public List<Productmodel> getProductmodelList() {
		return productmodelList;
	}
	public void setProductmodelList(List<Productmodel> productmodelList) {
		this.productmodelList = productmodelList;
	}
	public List<Product> getProductList() {
		return productList;
	}
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	public String getFirstarrivalflag() {
		return firstarrivalflag;
	}
	public String getFirstarrivalflagname() {
		if("0".equals(this.firstarrivalflag)){
			return "未支付";
		}else if("1".equals(this.firstarrivalflag)){
			return "已支付";
		}else if("2".equals(this.firstarrivalflag)){
			return "确认到账";
		}else{
			return "";
		}
	}
	public void setFirstarrivalflag(String firstarrivalflag) {
		this.firstarrivalflag = firstarrivalflag;
	}
	public String getFinalarrivalflag() {
		return finalarrivalflag;
	}
	public String getFinalarrivalflagname() {
		if("0".equals(this.finalarrivalflag)){
			return "未支付";
		}else if("1".equals(this.finalarrivalflag)){
			return "已支付";
		}else if("2".equals(this.finalarrivalflag)){
			return "确认到账";
		}else{
			return "";
		}
	}
	public void setFinalarrivalflag(String finalarrivalflag) {
		this.finalarrivalflag = finalarrivalflag;
	}
	public String getOperatetime() {
		return operatetime;
	}
	public void setOperatetime(String operatetime) {
		this.operatetime = operatetime;
	}
	public String getUserproductJson() {
		return userproductJson;
	}
	public void setUserproductJson(String userproductJson) {
		this.userproductJson = userproductJson;
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
	public String getTimelevel() {
		return timelevel;
	}
	public void setTimelevel(String timelevel) {
		this.timelevel = timelevel;
	}
	public String getFilingflag() {
		return filingflag;
	}
	public String getFilingflagname() {
		if("0".equals(this.filingflag)){
			return "未归档";
		}else if("1".equals(this.filingflag)){
			return "已归档";
		}else{
			return "";
		}
	}
	public void setFilingflag(String filingflag) {
		this.filingflag = filingflag;
	}
	public Integer getMinopteratetime() {
		return minopteratetime;
	}
	public void setMinopteratetime(Integer minopteratetime) {
		this.minopteratetime = minopteratetime;
	}
	public Integer getMaxopteratetime() {
		return maxopteratetime;
	}
	public void setMaxopteratetime(Integer maxopteratetime) {
		this.maxopteratetime = maxopteratetime;
	}
	public Usertask getUsertask() {
		return usertask;
	}
	public void setUsertask(Usertask usertask) {
		this.usertask = usertask;
	}
	public List<Userdispatch> getUserdispatchlist() {
		return userdispatchlist;
	}
	public void setUserdispatchlist(List<Userdispatch> userdispatchlist) {
		this.userdispatchlist = userdispatchlist;
	}
	public Userdispatch getUserdispatch() {
		return userdispatch;
	}
	public String getOrdercode() {
		return ordercode;
	}
	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getVisitorcode() {
		return visitorcode;
	}
	public void setVisitorcode(String visitorcode) {
		this.visitorcode = visitorcode;
	}
	public void setUserdispatch(Userdispatch userdispatch) {
		this.userdispatch = userdispatch;
	}
	public Integer getProductfee() {
		return productfee;
	}
	public void setProductfee(Integer productfee) {
		this.productfee = productfee;
	}
	public Integer getOtherfee() {
		return otherfee;
	}
	public void setOtherfee(Integer otherfee) {
		this.otherfee = otherfee;
	}
	public String getModelcode() {
		return modelcode;
	}
	public void setModelcode(String modelcode) {
		this.modelcode = modelcode;
	}
	public String getDeliverytime() {
		return deliverytime;
	}
	public void setDeliverytime(String deliverytime) {
		this.deliverytime = deliverytime;
	}
	public String getDeliveryname() {
		return deliveryname;
	}
	public void setDeliveryname(String deliveryname) {
		this.deliveryname = deliveryname;
	}
	public String getDeliverycode() {
		return deliverycode;
	}
	public void setDeliverycode(String deliverycode) {
		this.deliverycode = deliverycode;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getInstallercode() {
		return installercode;
	}
	public void setInstallercode(String installercode) {
		this.installercode = installercode;
	}
	public String getInstallername() {
		return installername;
	}
	public void setInstallername(String installername) {
		this.installername = installername;
	}
	public String getInstallerphone() {
		return installerphone;
	}
	public void setInstallerphone(String installerphone) {
		this.installerphone = installerphone;
	}
	public String getVisittype() {
		return visittype;
	}
	public String getVisittypename() {
		if("0".equals(this.visittype)){
			return "公司派人讲解";
		} else if("1".equals(this.visittype)){
			return "亲自讲解";
		} else if("2".equals(this.visittype)){
			return "已讲解，公司派人测量";
		} else {
			return "";
		}
	}
	public void setVisittype(String visittype) {
		this.visittype = visittype;
	}
	public String getSalercode() {
		return salercode;
	}
	public void setSalercode(String salercode) {
		this.salercode = salercode;
	}
	public Employee getSaler() {
		return saler;
	}
	public void setSaler(Employee saler) {
		this.saler = saler;
	}
	public String getTalkercode() {
		return talkercode;
	}
	public void setTalkercode(String talkercode) {
		this.talkercode = talkercode;
	}
	public String getVisitorstatus() {
		return visitorstatus;
	}
	public void setVisitorstatus(String visitorstatus) {
		this.visitorstatus = visitorstatus;
	}
	public String getVisitorstatusname() {
		if("0".equals(visitorstatus)){
			return "否";
		}else if("1".equals(visitorstatus)){
			return "已派";
		}else{
			return "";
		}
	}
	public String getTaskcode() {
		return taskcode;
	}
	public void setTaskcode(String taskcode) {
		this.taskcode = taskcode;
	}
	public String getModelname() {
		return modelname;
	}
	public void setModelname(String modelname) {
		this.modelname = modelname;
	}
	public List<Userorderinfo> getUserorderinfolist() {
		return userorderinfolist;
	}
	public void setUserorderinfolist(List<Userorderinfo> userorderinfolist) {
		this.userorderinfolist = userorderinfolist;
	}
	public String getPayitem() {
		return payitem;
	}
	public void setPayitem(String payitem) {
		this.payitem = payitem;
	}
	public Employee getInstaller() {
		return installer;
	}
	public void setInstaller(Employee installer) {
		this.installer = installer;
	}
	public String getStationcode() {
		return stationcode;
	}
	public void setStationcode(String stationcode) {
		this.stationcode = stationcode;
	}
	public Employee getTalker() {
		return talker;
	}
	public void setTalker(Employee talker) {
		this.talker = talker;
	}
	public String getQueryForPack() {
		return queryForPack;
	}
	public void setQueryForPack(String queryForPack) {
		this.queryForPack = queryForPack;
	}
    
	
	//重写父类方法
	public String getSort() {
		if(StringUtils.isNotEmpty(sort)){
			return sort;
		}else{
			return "ordercode";
		}
	}
	
	public String getOrder() {
		if(StringUtils.isNotEmpty(order)){
			return order;
		}else{
			return "desc";
		}
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
	public String getLockcoreflag() {
		return lockcoreflag;
	}
	public String getLockcoreflagname() {
		if("0".equals(this.lockcoreflag)){
			return "不需要";
		} else if("1".equals(this.lockcoreflag)){
			return "需要";
		} else {
			return "";
		}
	}
	public void setLockcoreflag(String lockcoreflag) {
		this.lockcoreflag = lockcoreflag;
	}
	public String getProductcolor() {
		return productcolor;
	}
	public String getProductcolorname() {
		if("0101".equals(this.productcolor) || "0104".equals(this.productcolor)){
			return "摩卡棕";
		} else if("0102".equals(this.productcolor) || "0105".equals(this.productcolor)){
			return "石英灰";
		} else if("0103".equals(this.productcolor) || "0106".equals(this.productcolor)){
			return "香槟金";
		} else {
			return "";
		}
	}
	public void setProductcolor(String productcolor) {
		this.productcolor = productcolor;
	}
	public Integer getDiscountfee() {
		return discountfee;
	}
	public void setDiscountfee(Integer discountfee) {
		this.discountfee = discountfee;
	}
	public Integer getGaintotalmoney() {
		return gaintotalmoney;
	}
	public void setGaintotalmoney(Integer gaintotalmoney) {
		this.gaintotalmoney = gaintotalmoney;
	}
	public String getVisitorflag() {
		return visitorflag;
	}
	public String getVisitorflagname() {
		if("0".equals(visitorflag)){
			return "不需要";
		}else if("1".equals(visitorflag)){
			return "需要";
		}else{
			return "";
		}
	}
	public void setVisitorflag(String visitorflag) {
		this.visitorflag = visitorflag;
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
	public String getProductconfirm() {
		return productconfirm;
	}
	public String getProductconfirmname() {
		if("0".equals(productconfirm)){
			return "未确定";
		}else if("1".equals(productconfirm)){
			return "已确定";
		}else{
			return "";
		}
	}
	public void setProductconfirm(String productconfirm) {
		this.productconfirm = productconfirm;
	}
	public String getQueryForReplace() {
		return queryForReplace;
	}
	public void setQueryForReplace(String queryForReplace) {
		this.queryForReplace = queryForReplace;
	}
	public MultipartFile[] getFiles() {
		return files;
	}
	public void setFiles(MultipartFile[] files) {
		this.files = files;
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
	public Integer getDiscountgain() {
		return discountgain;
	}
	public void setDiscountgain(Integer discountgain) {
		this.discountgain = discountgain;
	}
	public Integer getFixedgain() {
		return fixedgain;
	}
	public void setFixedgain(Integer fixedgain) {
		this.fixedgain = fixedgain;
	}
	public Integer getManagergain() {
		return managergain;
	}
	public void setManagergain(Integer managergain) {
		this.managergain = managergain;
	}
	public String getManagercode() {
		return managercode;
	}
	public void setManagercode(String managercode) {
		this.managercode = managercode;
	}
	public String getGiftcardno() {
		return giftcardno;
	}
	public void setGiftcardno(String giftcardno) {
		this.giftcardno = giftcardno;
	}
	public Giftcard getGiftcard() {
		return giftcard;
	}
	public void setGiftcard(Giftcard giftcard) {
		this.giftcard = giftcard;
	}
	public Employee getManager() {
		return manager;
	}
	public void setManager(Employee manager) {
		this.manager = manager;
	}
	public String getManagename() {
		return managename;
	}
	public void setManagename(String managename) {
		this.managename = managename;
	}
	public String getOrderinfo() {
		return orderinfo;
	}
	public void setOrderinfo(String orderinfo) {
		this.orderinfo = orderinfo;
	}
}
