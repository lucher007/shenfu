package com.sykj.shenfu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.dao.ICellDao;
import com.sykj.shenfu.dao.ICellextendDao;
import com.sykj.shenfu.dao.IProductDao;
import com.sykj.shenfu.dao.IProductmodelDao;
import com.sykj.shenfu.dao.IProductmodelrefDao;
import com.sykj.shenfu.dao.IUserDao;
import com.sykj.shenfu.dao.IUserbusinessDao;
import com.sykj.shenfu.dao.IUserorderDao;
import com.sykj.shenfu.dao.IUserorderinfoDao;
import com.sykj.shenfu.dao.IUserproductDao;
import com.sykj.shenfu.dao.IUsertaskDao;
import com.sykj.shenfu.po.Cell;
import com.sykj.shenfu.po.Cellextend;
import com.sykj.shenfu.po.Coderule;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Product;
import com.sykj.shenfu.po.Productmodel;
import com.sykj.shenfu.po.Productmodelref;
import com.sykj.shenfu.po.User;
import com.sykj.shenfu.po.Userbusiness;
import com.sykj.shenfu.po.Userorder;
import com.sykj.shenfu.po.Userorderinfo;
import com.sykj.shenfu.po.Userproduct;
import com.sykj.shenfu.po.Usertask;
import com.sykj.shenfu.service.ICellextendService;
import com.sykj.shenfu.service.ICoderuleService;
import com.sykj.shenfu.service.IOperatorlogService;
import com.sykj.shenfu.service.ISystemparaService;
import com.sykj.shenfu.service.IUserService;

/**
 * @Title ProductdepotServiceImpl.java
 * @version 1.0 编码规则类
 */
@Service("cellextendService")
public class CellextendServiceImpl implements ICellextendService {
	@Autowired
    private DataSourceTransactionManager txManager;
	@Autowired
	private ICellDao cellDao;
	@Autowired
	private ICellextendDao cellextendDao;
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private ISystemparaService systemparaService;
	@Autowired
	private ICoderuleService coderuleService;
	
	/*
     * 特殊的instance变量
     * 注：零长度的byte数组对象创建起来将比任何对象都经济――查看编译后的字节码：
     * 生成零长度的byte[]对象只需3条操作码，而Object lock = new Object()则需要7行操作码。
     */
    private static byte[] lock = new byte[0];   
	
    /**
	 * 小区发布
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveCellextend(Cellextend cellextendform, HttpServletRequest request,Operator operator){
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("status", "0");//默认保存失败
		//加锁
		synchronized(lock) {
			//当前时间
			String currenttime =  Tools.getCurrentTime();	
			
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			try{
				
				String ids = Tools.getStr(request.getParameter("ids"));
				if(StringUtils.isEmpty(ids)){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "发布失败，请选择发布的小区");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				String[] idArray = ids.split(",");
				if (idArray == null || idArray.length < 1) {
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "发布失败，请选择发布的小区");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				
				for (int i = 0; i < idArray.length; i++) {
					Cell cell = cellDao.findById(Integer.parseInt(idArray[i]));
					if(cell == null){ 
						responseMap.put("status", "0");//电话号码不能为空
						responseMap.put("result", "发布失败，请重新选择需要发布的小区");//
						//回滚
						txManager.rollback(status);
						return responseMap;
					}
					
					if(cell.getTotalhouse() == null || cell.getTotalhouse() < 1){
						responseMap.put("status", "0");//电话号码不能为空
						responseMap.put("result", "发布失败," + cell.getCellname()+ " 的总户数不正确");//
						//回滚
						txManager.rollback(status);
						return responseMap;
					}
					
					cell.setExtendflag("1");//已发布
					//修改小区
					cellDao.update(cell);
					
					//查找出系统设置中的扫楼价格，单位为分
					String cell_extend_price = systemparaService.findSystemParaByCodeStr("cell_extend_price");
					if(StringUtils.isEmpty(cell_extend_price)){
						responseMap.put("status", "0");//电话号码不能为空
						responseMap.put("result", "发布失败," + "请先设置扫楼单价");//
						//回滚
						txManager.rollback(status);
						return responseMap;
					}
					
					Cellextend cellextend = new Cellextend();
					//获取小区发布编号
					String cellextendcode = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_cellextendcode);
					cellextend.setExtendcode("SL"+cellextendcode);
					cellextend.setCellcode(cell.getCellcode());
					cellextend.setCellname(cell.getCellname());
					cellextend.setAddress(cell.getAddress());
					cellextend.setTotalhouse(cell.getTotalhouse());
					cellextend.setPrice(Integer.valueOf(cell_extend_price));
					cellextend.setTotalmoney(cellextend.getPrice() * cellextend.getTotalhouse());
					cellextend.setExtendercode(operator.getEmployeecode());
					cellextend.setExtendtime(currenttime);
					cellextend.setAcceptflag("0");//未接收
					cellextend.setPayflag("0"); //未支付
					cellextend.setStationflag("0");//未驻点
					cellextendDao.save(cellextend);
					
					//增加操作日记
					String content = "小区发布，小区名称:"+cell.getCellname();
					operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
					
				}
				
				//事务提交
				txManager.commit(status);
				
				responseMap.put("status", "1");
				responseMap.put("result", "小区发布成功");
				
				return responseMap;
				
			}catch(Exception e){
				e.printStackTrace();
				//回滚
				txManager.rollback(status);
				
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，操作失败");//
				return responseMap;
			}
		} 
	}
    
}
