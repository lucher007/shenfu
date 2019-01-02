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
import com.sykj.shenfu.dao.IComponentDao;
import com.sykj.shenfu.dao.IComponentdepotDao;
import com.sykj.shenfu.dao.IComponentinoutDao;
import com.sykj.shenfu.dao.IEmployeeDao;
import com.sykj.shenfu.dao.IMaterialDao;
import com.sykj.shenfu.dao.IMaterialdepotDao;
import com.sykj.shenfu.dao.IMaterialinoutDao;
import com.sykj.shenfu.dao.IProductDao;
import com.sykj.shenfu.dao.IProductdepotDao;
import com.sykj.shenfu.dao.IProductinoutDao;
import com.sykj.shenfu.dao.IProductmodelDao;
import com.sykj.shenfu.dao.IProductmodelrefDao;
import com.sykj.shenfu.dao.IPurchaseDao;
import com.sykj.shenfu.dao.IRechargevipDao;
import com.sykj.shenfu.dao.ISaleenergyruleDao;
import com.sykj.shenfu.dao.ISalegaininfoDao;
import com.sykj.shenfu.dao.ISalegainruleDao;
import com.sykj.shenfu.dao.ISalerrechargevipinfoDao;
import com.sykj.shenfu.dao.IUserDao;
import com.sykj.shenfu.dao.IUserbusinessDao;
import com.sykj.shenfu.dao.IUserdeliveryDao;
import com.sykj.shenfu.dao.IUserdispatchDao;
import com.sykj.shenfu.dao.IUserorderDao;
import com.sykj.shenfu.dao.IUserorderinfoDao;
import com.sykj.shenfu.dao.IUserproductDao;
import com.sykj.shenfu.dao.IUsertaskDao;
import com.sykj.shenfu.po.Coderule;
import com.sykj.shenfu.po.Component;
import com.sykj.shenfu.po.Componentdepot;
import com.sykj.shenfu.po.Componentinout;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Material;
import com.sykj.shenfu.po.Materialdepot;
import com.sykj.shenfu.po.Materialinout;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Product;
import com.sykj.shenfu.po.Productdepot;
import com.sykj.shenfu.po.Productinout;
import com.sykj.shenfu.po.Productmodel;
import com.sykj.shenfu.po.Productmodelref;
import com.sykj.shenfu.po.Purchase;
import com.sykj.shenfu.po.Rechargevip;
import com.sykj.shenfu.po.Saleenergyrule;
import com.sykj.shenfu.po.Salegaininfo;
import com.sykj.shenfu.po.Salegainrule;
import com.sykj.shenfu.po.Salerrechargevipinfo;
import com.sykj.shenfu.po.User;
import com.sykj.shenfu.po.Userbusiness;
import com.sykj.shenfu.po.Userdelivery;
import com.sykj.shenfu.po.Userdispatch;
import com.sykj.shenfu.po.Userorder;
import com.sykj.shenfu.po.Userorderinfo;
import com.sykj.shenfu.po.Userproduct;
import com.sykj.shenfu.po.Usertask;
import com.sykj.shenfu.service.ICoderuleService;
import com.sykj.shenfu.service.IComponentdepotService;
import com.sykj.shenfu.service.IMaterialdepotService;
import com.sykj.shenfu.service.IOperatorlogService;
import com.sykj.shenfu.service.IProductdepotService;
import com.sykj.shenfu.service.IPurchaseService;
import com.sykj.shenfu.service.IUserorderService;


/**
 * @Title ProductdepotServiceImpl.java
 * @version 1.0 产品库存实现类
 */
@Service("purchaseService")
public class PurchaseServiceImpl implements IPurchaseService {
	@Autowired
	private IPurchaseDao purchaseDao;
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
    private DataSourceTransactionManager txManager;
	@Autowired
	private IProductDao productDao;
	@Autowired
	private IProductdepotDao productdepotDao;
	@Autowired
	private IProductinoutDao productinoutDao;
	@Autowired
	private IComponentDao componentDao;
	@Autowired
	private IComponentdepotService componentdepotService;
	@Autowired
	private IComponentdepotDao componentdepotDao;
	@Autowired
	private IMaterialinoutDao materialinoutDao;
	@Autowired
	private IComponentinoutDao componentinoutDao;
	@Autowired
	private IMaterialDao materialDao;
	@Autowired
	private IMaterialdepotService materialdepotService;
	@Autowired
	private IMaterialdepotDao materialdepotDao;
	@Autowired
	private IProductdepotService productdepotService;
	
	/*
     * 特殊的instance变量
     * 注：零长度的byte数组对象创建起来将比任何对象都经济――查看编译后的字节码：
     * 生成零长度的byte[]对象只需3条操作码，而Object lock = new Object()则需要7行操作码。
     */
    private static byte[] lock = new byte[0];   
	
	/**
	 * 采购单入库
	 */
	public Map<String, Object> saveIndepot(Purchase purchaseform, HttpServletRequest request, Operator operator) {
		//加锁
		synchronized(lock) {
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			
			Map<String, Object> responseMap = new HashMap<String, Object>();
			
			//当前时间
			String currenttime = Tools.getCurrentTime();
			
			try{
				
				//出入库负责人
				String inoutercode  = Tools.getStr(request.getParameter("inoutercode"));
				
				Purchase purchase = purchaseDao.findById(purchaseform.getId());
				if(purchase == null){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，采购单不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				purchase.setStatus("2");
				//修改采购单状态为已入库
				purchaseDao.update(purchase);
				
				//保存型号关联的产品类别信息
				String purchasedetailJson = Tools.getStr(request.getParameter("purchasedetailJson"));
				if(StringUtils.isEmpty(purchasedetailJson)){
					//回滚
					txManager.rollback(status);
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，此采购单未有任何采购产品信息");//
					return responseMap;
				}
				
				//增加关联的产品信息
				JSONArray purchasedetailArray = JSONArray.fromObject(purchasedetailJson);
				
				//从传过来的数据中获取该产品类别信息
				for (int i = 0; i < purchasedetailArray.size(); i++) {
					JSONObject productJsonObject = purchasedetailArray.getJSONObject(i);
					String type = productJsonObject.getString("type");
					String materialcode = productJsonObject.getString("materialcode");
					String amount = productJsonObject.getString("amount");
					String depotrackcode = productJsonObject.getString("depotrackcode");
					if("0".equals(type)){//元器件入库
						Component component = componentDao.findByComponentcode(materialcode);
						if(component == null){
							//回滚
							txManager.rollback(status);
							responseMap.put("status", "0");//保存失败
							responseMap.put("result", "入库失败，该元器件不存在，编号为"+materialcode);//
							return responseMap;
						}
						
						//元器件入库
						Componentdepot componentdepot = new Componentdepot();
						componentdepot.setComponentcode(component.getComponentcode());
						componentdepot.setComponentname(component.getComponentname());
						componentdepot.setComponentmodel(component.getComponentmodel());
						//操作员
						componentdepot.setOperatorcode(operator.getEmployeecode());
						//获取元器件批次号
						String batchno = componentdepotService.getComponentbatchno(component.getComponentcode());
						//封装元器件批次号
						componentdepot.setBatchno(batchno);
						//入库量
						componentdepot.setDepotamount(Integer.parseInt(amount));
						//入库时间
						componentdepot.setAddtime(currenttime);
						//出入库负责人
						componentdepot.setInoutercode(inoutercode);
						//默认有库存
						componentdepot.setDepotstatus("1");
						//存放货柜位置
						componentdepot.setDepotrackcode(depotrackcode);
						//供货商
						componentdepot.setSuppliername(purchaseform.getSuppliername());
						//元器件入库保存
						componentdepotDao.save(componentdepot);
						
						//增加元器件入库记录
						Componentinout componentinout = new Componentinout();
						componentinout.setComponentcode(componentdepot.getComponentcode());
						componentinout.setComponentname(componentdepot.getComponentname());
						componentinout.setComponentmodel(componentdepot.getComponentmodel());
						componentinout.setOperatorcode(operator.getEmployeecode());
						componentinout.setBatchno(componentdepot.getBatchno());
						componentinout.setType("0");
						componentinout.setInoutamount(componentdepot.getDepotamount());
						componentinout.setAddtime(currenttime);
						componentinout.setInoutercode(componentdepot.getInoutercode());
						componentinout.setInoutstatus("1");
						componentinout.setDepotrackcode(componentdepot.getDepotrackcode());
						//入库记录保存
						componentinoutDao.save(componentinout);
						
					}else if("1".equals(type)){//材料入库
						//材料入库
						Materialdepot materialdepot = new Materialdepot();
						Material material = materialDao.findByMaterialcodeStr(materialcode);
						if(material == null){
							//回滚
							txManager.rollback(status);
							responseMap.put("status", "0");//保存失败
							responseMap.put("result", "入库失败，该材料不存在，材料编号为：" +materialcode);//
							return responseMap;
						}
						materialdepot.setMaterialcode(material.getMaterialcode());
						materialdepot.setMaterialname(material.getMaterialname());
						//操作员
						materialdepot.setOperatorcode(operator.getEmployeecode());
						//获取材料批次号
						String batchno = materialdepotService.getMaterialbatchno(material.getMaterialcode());
						//封装材料批次号
						materialdepot.setBatchno(batchno);
						//入库量
						materialdepot.setDepotamount(Integer.parseInt(amount));
						//入库时间
						materialdepot.setAddtime(currenttime);
						//出入库负责人
						materialdepot.setInoutercode(inoutercode);
						//默认有库存
						materialdepot.setDepotstatus("1");
						//存放货柜位置
						materialdepot.setDepotrackcode(depotrackcode);
						//供货商
						materialdepot.setSuppliername(purchaseform.getSuppliername());
						//入库保存
						materialdepotDao.save(materialdepot);
						
						//增加材料入库记录
						Materialinout materialinout = new Materialinout();
						materialinout.setMaterialcode(materialdepot.getMaterialcode());
						materialinout.setMaterialname(materialdepot.getMaterialname());
						materialinout.setOperatorcode(operator.getEmployeecode());
						materialinout.setBatchno(materialdepot.getBatchno());
						materialinout.setType("0");
						materialinout.setInoutamount(materialdepot.getDepotamount());
						materialinout.setAddtime(currenttime);
						materialinout.setInoutercode(materialdepot.getInoutercode());
						materialinout.setInoutstatus("1");
						materialinout.setDepotrackcode(materialdepot.getDepotrackcode());
						//入库记录保存
						materialinoutDao.save(materialinout);
						
					}else if("2".equals(type)){//产品入库
						Product product = productDao.findByProductcode(materialcode);
						if(product == null){
							//回滚
							txManager.rollback(status);
							responseMap.put("status", "0");//保存失败
							responseMap.put("result", "入库失败，该产品不存在，编号为"+materialcode);//
							return responseMap;
						}
						//元器件入库
						Productdepot productdepot = new Productdepot();
						productdepot.setTypecode(product.getTypecode());
						productdepot.setTypename(product.getTypename());
						productdepot.setProductcode(product.getProductcode());
						productdepot.setProductname(product.getProductname());
						productdepot.setProductsource(product.getProductsource());
						//产品识别号
						String productidentfy = productdepotService.getProductidentfy(product.getProductcode());
						//组装产品唯一标识
						productdepot.setProductidentfy(productidentfy);
						//入库量
						productdepot.setDepotamount(Integer.parseInt(amount));
						productdepot.setProductstatus("1");
						productdepot.setAddtime(currenttime);
						productdepot.setDepotstatus("1");
						//存放货柜位置
						productdepot.setDepotrackcode(depotrackcode);
						//供货商
						productdepot.setSuppliername(purchaseform.getSuppliername());
						//操作员
						productdepot.setOperatorcode(operator.getEmployeecode());
						//出入库负责人
						productdepot.setInoutercode(inoutercode);
						
						//元器件入库保存
						productdepotDao.save(productdepot);
						
						//增加产品入库记录
						Productinout productinout = new Productinout();
						productinout.setProductcode(productdepot.getProductcode());
						productinout.setProductname(productdepot.getProductname());
						productinout.setProductidentfy(productdepot.getProductidentfy());
						productinout.setType("0");
						productinout.setInoutercode(productdepot.getOperatorcode());
						productinout.setInoutamount(productdepot.getDepotamount());
						productinout.setAddtime(productdepot.getAddtime());
						productinout.setOperatorcode(productdepot.getOperatorcode());
						productinout.setInoutstatus("1");
						productinout.setDepotrackcode(productdepot.getDepotrackcode());
						productinoutDao.save(productinout);
						
					}else{
						//回滚
						txManager.rollback(status);
						responseMap.put("status", "0");//保存失败
						responseMap.put("result", "提交失败，采购的内容不能入库");//
						return responseMap;
					}
				}
					

				//增加操作日记
				String content = "采购单入库， 采购单号:"+purchase.getPurchasecode();
				operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
				
				//事务提交
				txManager.commit(status);
				responseMap.put("status", "1");//注册成功
				responseMap.put("result", "处理成功");
				return responseMap;
			}catch(Exception e){
			    e.printStackTrace();
				//回滚
				txManager.rollback(status);
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，处理失败");//
				return responseMap;
			}
		} 
	}   
	
}
