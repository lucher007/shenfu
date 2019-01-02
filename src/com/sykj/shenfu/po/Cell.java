package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

import fr.opensagres.xdocreport.core.utils.StringUtils;

/**
 * 用户实体类
 */
public class Cell extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;      // 数据库ID
	private String cellcode; //小区编号
	private String cellname; // 小区名称
	private String address;  // 小区地址
	private String opentime; //开盘时间
	private String handtime; //交房时间
	private String safelevel; //安防级别(1-高级；2-中级；3-低级）
	private String developer; //开发商
	private String province;  //省
	private String city;      //市
	private String area;      //区县
	private String longitude;  //经度
	private String latitude;   //纬度
	private String building;   //楼栋
	private String floor;     //楼层信息
	private Integer totalhouse;  //总户数
	private String layout;     //户型
	private String extendflag; //发布状态（0-未发布；1-已发布）
	private String userate;  //入住率
	private String highlow;//高低层信息
	private String property; //物业公司
	private String doorinfo; //门锁数据
	private String researchercode; //调查人员
	private String advertisement;  //小区广告
	private String allowstation;  //物业是否允许驻点(0-不允许驻点；1-允许驻点；2-未知)
	private String remark;    //备注

	private Cell cell;
	private List<Cell> celllist;
	
	private String beginopentime; //查询开盘开始时间
	private String endopentime;   //查询开盘结束时间
	private String beginhandtime; //查询交房开始时间
	private String endhandtime;   //查询交房结束时间
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCellname() {
		return cellname;
	}
	public void setCellname(String cellname) {
		this.cellname = cellname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getOpentime() {
		if(StringUtils.isNotEmpty(opentime)){
			return opentime;
		}else{
			return null;
		}
	}
	public void setOpentime(String opentime) {
		this.opentime = opentime;
	}
	public String getHandtime() {
		if(StringUtils.isNotEmpty(handtime)){
			return handtime;
		}else{
			return null;
		}
	}
	public void setHandtime(String handtime) {
		this.handtime = handtime;
	}
	public String getSafelevel() {
		return safelevel;
	}
	public String getSafelevelname() {
		if("1".equals(safelevel)){
			return "高级";
		}else if("2".equals(safelevel)){
			return "中级";
		}else if("3".equals(safelevel)){
			return "低级";
		}else{
			return "未知";
		}
	}
	public void setSafelevel(String safelevel) {
		this.safelevel = safelevel;
	}
	public String getDeveloper() {
		return developer;
	}
	public void setDeveloper(String developer) {
		this.developer = developer;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Cell getCell() {
		return cell;
	}
	public void setCell(Cell cell) {
		this.cell = cell;
	}
	public List<Cell> getCelllist() {
		return celllist;
	}
	public void setCelllist(List<Cell> celllist) {
		this.celllist = celllist;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getBeginopentime() {
		return beginopentime;
	}
	public void setBeginopentime(String beginopentime) {
		this.beginopentime = beginopentime;
	}
	public String getEndopentime() {
		return endopentime;
	}
	public void setEndopentime(String endopentime) {
		this.endopentime = endopentime;
	}
	public String getBeginhandtime() {
		return beginhandtime;
	}
	public void setBeginhandtime(String beginhandtime) {
		this.beginhandtime = beginhandtime;
	}
	public String getEndhandtime() {
		return endhandtime;
	}
	public void setEndhandtime(String endhandtime) {
		this.endhandtime = endhandtime;
	}
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public Integer getTotalhouse() {
		return totalhouse;
	}
	public void setTotalhouse(Integer totalhouse) {
		this.totalhouse = totalhouse;
	}
	public String getLayout() {
		return layout;
	}
	public void setLayout(String layout) {
		this.layout = layout;
	}
	public String getCellcode() {
		return cellcode;
	}
	public void setCellcode(String cellcode) {
		this.cellcode = cellcode;
	}
	public String getExtendflag() {
		return extendflag;
	}
	public String getExtendflagname() {
		if("0".equals(extendflag)){
			return "未发布";
		}else if("1".equals(extendflag)){
			return "已发布";
		}else{
			return "";
		}
	}
	public void setExtendflag(String extendflag) {
		this.extendflag = extendflag;
	}
	public String getUserate() {
		return userate;
	}
	public void setUserate(String userate) {
		this.userate = userate;
	}
	public String getHighlow() {
		return highlow;
	}
	public void setHighlow(String highlow) {
		this.highlow = highlow;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getDoorinfo() {
		return doorinfo;
	}
	public void setDoorinfo(String doorinfo) {
		this.doorinfo = doorinfo;
	}
	public String getResearchercode() {
		return researchercode;
	}
	public void setResearchercode(String researchercode) {
		this.researchercode = researchercode;
	}
	public String getAdvertisement() {
		return advertisement;
	}
	public void setAdvertisement(String advertisement) {
		this.advertisement = advertisement;
	}
	public String getAllowstation() {
		return allowstation;
	}
	public String getAllowstationname() {
		if("0".equals(allowstation)){
			return "不允许";
		}else if("1".equals(allowstation)){
			return "允许";
		}else if("2".equals(allowstation)){
			return "未知";
		}else{
			return "";
		}
	}
	public void setAllowstation(String allowstation) {
		this.allowstation = allowstation;
	}
	

}
