package com.sykj.shenfu.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sykj.shenfu.po.Cellextend;
import com.sykj.shenfu.po.Cellstation;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Giftcard;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.User;
import com.sykj.shenfu.po.Userdispatch;
import com.sykj.shenfu.po.Userdoor;
import com.sykj.shenfu.po.Userdoordata;
import com.sykj.shenfu.po.Userorder;

/**
 * 操作员编码
 */
public interface IMobilebusinessService {
	
	/**
	 * 移动端小区扫楼抢购
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveBuyCellextend(Cellextend cellextend, HttpServletRequest request,Employee mobileoperator);
	
	/**
	 * 移动端订单录入-公司派人上门
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveUserorderForVisittype0(Userorder form, HttpServletRequest request,HttpSession session);
	
	/**
	 * 移动端订单录入-亲自上门
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveUserorderForVisittype1(Userorder form, HttpServletRequest request,HttpSession session);
	
	 /**
	 * 修改门锁数据
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> updateUserdoordata(Userdoordata form, HttpServletRequest request,Employee mobileoperator);
	
	 /**
	 * 修改产品价格
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> updateProductfee(Userorder form, HttpServletRequest request,Employee mobileoperator);
	
	/**
	 * 修改门锁图片-上传新的门锁图片
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveUserdoorInfo(Userorder form, HttpServletRequest request,HttpSession session);
	
	/**
	 * 保存订单-处理任务单
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveUserorderForUsertask(Userorder form, HttpServletRequest request,HttpSession session);
	
	
	 /**
	 * 保存扫楼驻点申请
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveCellstation(Cellstation form, HttpServletRequest request,Employee mobileoperator);
	
	/**
	 * 保存优惠卡生成
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveGiftcard(Giftcard form, HttpServletRequest request,Employee mobileoperator);
	
	/**
	 * 工单处理
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveDispatchdata(Userdispatch form, HttpServletRequest request,Employee mobileoperator);
	
	/**
	 * 修改安装图片-上传新的安装图片
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveUserdispatchfileInfo(Userdispatch form, HttpServletRequest request,HttpSession session);
}
