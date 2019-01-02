package com.sykj.shenfu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.dao.ICellstationDao;
import com.sykj.shenfu.dao.ICellstationemployeeDao;
import com.sykj.shenfu.dao.IEmployeeDao;
import com.sykj.shenfu.po.Cellstation;
import com.sykj.shenfu.po.Cellstationemployee;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.service.ICellstationemployeeService;
import com.sykj.shenfu.service.IOperatorlogService;

/**
 * @Title ProductdepotServiceImpl.java
 * @version 1.0 编码规则类
 */
@Service("cellstationemployeeService")
public class CellstationemployeeServiceImpl implements ICellstationemployeeService {
	@Autowired
    private DataSourceTransactionManager txManager;
	@Autowired
	private ICellstationDao cellstationDao;
	@Autowired
	private ICellstationemployeeDao cellstationemployeeDao;
	@Autowired
	private IEmployeeDao employeeDao;
	@Autowired
	private IOperatorlogService operatorlogService;
	
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
	public Map<String, Object> saveCellstationemployee(Cellstationemployee cellstationemployeeform, HttpServletRequest request,Operator operator){
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("status", "0");//默认保存失败
		responseMap.put("result", "操作失败");//
		//加锁
		synchronized(lock) {
			//当前时间
			String currenttime =  Tools.getCurrentTime();	
			
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			try{
				
				//选择的员工ID
				String ids = Tools.getStr(request.getParameter("ids"));
				//驻点编号
				String stationcode = Tools.getStr(request.getParameter("stationcode"));
				//驻点工作人员类型
				String employeetype = Tools.getStr(request.getParameter("employeetype"));
				
				if(StringUtils.isEmpty(ids)){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "操作失败，请选择员工");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				String[] idArray = ids.split(",");
				if (idArray == null || idArray.length < 1) {
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "操作失败，请选择员工");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if(StringUtils.isEmpty(stationcode)){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "操作失败，请选择驻点编号");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				Cellstation cellstation = cellstationDao.findByStationcode(stationcode);
				if(cellstation == null){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "操作失败，此驻点信息不存在");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if(StringUtils.isEmpty(employeetype)){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "操作失败，请先刷新驻点信息，再来添加驻点人员");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				for (int i = 0; i < idArray.length; i++) {
					Employee employee = employeeDao.findById(Integer.parseInt(idArray[i]));
					if(employee == null){ 
						responseMap.put("status", "0");//电话号码不能为空
						responseMap.put("result", "操作失败，请选择员工");//
						//回滚
						txManager.rollback(status);
						return responseMap;
					}
					
					if("0".equals(employee.getStatus())){
						responseMap.put("status", "0");//电话号码不能为空
						responseMap.put("result", "操作失败," + "姓名为：" +employee.getEmployeename() +"的员工已经离职");//
						//回滚
						txManager.rollback(status);
						return responseMap;
					}
					
					//通过驻点单和工作员编号查询
					Cellstationemployee cellstationemployeeOld = cellstationemployeeDao.findByStationcodeAndEmployeecode(stationcode,employee.getEmployeecode(),employeetype);
					if(cellstationemployeeOld != null){
						responseMap.put("status", "0");//电话号码不能为空
						responseMap.put("result", "操作失败," + "姓名为：" +employee.getEmployeename() +" 的员工已经添加了，不能再次添加");//
						//回滚
						txManager.rollback(status);
						return responseMap;
					}
					
					//判断此驻点人员在这个时间段内是否有驻点任务
					List<Cellstationemployee> cellstationemployeelist = cellstationemployeeDao.findByEmployeecodeAndStratEndtime(employee.getEmployeecode(),cellstation.getStarttime(),cellstation.getEndtime());
					if(cellstationemployeelist != null && cellstationemployeelist.size() > 0){
						responseMap.put("status", "0");
						responseMap.put("result", "操作失败," + "姓名为：" +employee.getEmployeename() +" 的员工在这段时间内已经分配有驻点信息");//
						//回滚
						txManager.rollback(status);
						return responseMap;
					}
					
					Cellstationemployee cellstationemployee = new Cellstationemployee();
					//获取小区发布编号
					cellstationemployee.setStationcode(stationcode);
					cellstationemployee.setExtendcode(cellstation.getExtendcode());
					cellstationemployee.setCellcode(cellstation.getCellcode());
					cellstationemployee.setCellname(cellstation.getCellname());
					cellstationemployee.setAddress(cellstation.getAddress());
					cellstationemployee.setStarttime(cellstation.getStarttime());
					cellstationemployee.setEndtime(cellstation.getEndtime());
					cellstationemployee.setEmployeetype(employeetype);
					cellstationemployee.setEmployeecode(employee.getEmployeecode());
					cellstationemployee.setEmployeename(employee.getEmployeename());
					cellstationemployee.setPhone(employee.getPhone());
					cellstationemployee.setRemark("");
					cellstationemployeeDao.save(cellstationemployee);
					
					//增加操作日记
					String content = "选择驻点工作人员，姓名为:"+employee.getEmployeename();
					operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
				}
				
				//事务提交
				txManager.commit(status);
				
				responseMap.put("status", "1");
				responseMap.put("result", "操作成功");
				
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
