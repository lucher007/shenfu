package com.sykj.shenfu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.sykj.shenfu.controller.BaseController;
import com.sykj.shenfu.dao.IMaterialdepotDao;
import com.sykj.shenfu.dao.IMaterialinoutDao;
import com.sykj.shenfu.dao.IProduceinfoDao;
import com.sykj.shenfu.dao.IProducematerialDao;
import com.sykj.shenfu.dao.IProduceproductDao;
import com.sykj.shenfu.dao.IProductDao;
import com.sykj.shenfu.po.Coderule;
import com.sykj.shenfu.po.Materialdepot;
import com.sykj.shenfu.po.Materialinout;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Produceinfo;
import com.sykj.shenfu.po.Producematerial;
import com.sykj.shenfu.po.Produceproduct;
import com.sykj.shenfu.po.Product;
import com.sykj.shenfu.service.ICoderuleService;
import com.sykj.shenfu.service.IOperatorlogService;
import com.sykj.shenfu.service.IProduceinfoService;
import com.sykj.shenfu.service.IProductdepotService;

/**
 * @Title ProductdepotServiceImpl.java
 * @version 1.0 编码规则类
 */
@Service("produceinfoService")
public class ProduceinfoServiceImpl extends BaseController implements IProduceinfoService {
	@Autowired
    private DataSourceTransactionManager txManager;
	@Autowired
	private IProductDao productDao;
	@Autowired
	private ICoderuleService coderuleService;
	@Autowired
	private IProduceinfoDao produceinfoDao;
	@Autowired
	private IProduceproductDao produceproductDao;
	@Autowired
	private IProducematerialDao producematerialDao;
	@Autowired
	private IProductdepotService productdepotService;
	@Autowired
	private IMaterialdepotDao materialdepotDao;
	@Autowired
	private IMaterialinoutDao materialinoutDao;
	@Autowired
	private IOperatorlogService operatorlogService;
	
	/*
     * 特殊的instance变量
     * 注：零长度的byte数组对象创建起来将比任何对象都经济――查看编译后的字节码：
     * 生成零长度的byte[]对象只需3条操作码，而Object lock = new Object()则需要7行操作码。
     */
    private static byte[] lock = new byte[0];   
	
    /**
	 * 保存生产信息
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveProduceinfo(Produceinfo form) {
		//加锁
		synchronized(lock) {
				
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			
			Map<String, Object> responseMap = new HashMap<String, Object>();
			try{
				responseMap.put("status", "0");//保存失败
				
				//操作员
				Operator operator = (Operator)getSession().getAttribute("Operator");
				
				form.setOperator(operator);
				
				//保存型号关联的材料信息
				String materialJson = getRequest().getParameter("materialJson");
				if(StringUtils.isEmpty(materialJson)){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "请选择关联的材料信息");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//增加关联的材料信息
				JSONArray materialJsonArry = JSONArray.fromObject(materialJson);
				
				if(StringUtils.isEmpty(form.getProductcode())){
					
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "请选择产品编码");
					//回滚
					txManager.rollback(status);
					return responseMap;
					
				}
				
				Product product = productDao.findByProductcode(form.getProductcode());
				if(product == null){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "产品编码不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if(form.getAmount() < 1){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "生产数量必须大于1");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//生产批次号
				String producecode = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_producecode);
				form.setProducecode(producecode);
				
				//当前时间
				String currenttime = Tools.getCurrentTime();
				//订户信息
				form.setProductname(product.getProductname());
				form.setOperatorcode(operator.getEmployeecode());
				form.setAddtime(currenttime);
				
				//保存生产信息
				produceinfoDao.save(form);
				
				//生产产品列表
				ArrayList<Produceproduct> produceproductlist = new ArrayList<Produceproduct>();
				//需要材料列表
				ArrayList<Producematerial> producemateriallist = new ArrayList<Producematerial>();
				
				//循环产品生产个数
				for(int i=0; i<form.getAmount(); i++){
					Produceproduct produceproduct = new Produceproduct();
					produceproduct.setProducecode(form.getProducecode());
					produceproduct.setProductcode(form.getProductcode());
					produceproduct.setProductname(product.getProductname());
					//产品唯一识别号
					String productidentfy = productdepotService.getProductidentfy(form.getProductcode());
					produceproduct.setProductidentfy(productidentfy);
					produceproduct.setDepotamount(1);
					produceproduct.setProductstatus("1");//组装中
					produceproduct.setPrintflag("0");//未打印
					produceproduct.setAddtime(currenttime);
					
					//保存生产产品信息
					//produceproductDao.save(produceproduct);
					
					produceproductlist.add(produceproduct);
					

				    //每个产品需要的材料列表
					for (int j = 0; j < materialJsonArry.size(); j++) {
						JSONObject materialJsonObject = materialJsonArry.getJSONObject(j);
						String materialcode = Tools.getStr(materialJsonObject.getString("materialcode"));
						String materialname = Tools.getStr(materialJsonObject.getString("materialname"));
						String amount = Tools.getStr(materialJsonObject.getString("amount"));
						String batchno = Tools.getStr(materialJsonObject.getString("batchno"));
						String totalamount = Tools.getStr(materialJsonObject.getString("totalamount"));
						String depotrackcode = Tools.getStr(materialJsonObject.getString("depotrackcode"));
						
						//应该需要的材料总数
						Integer totalamount_need = Integer.valueOf(amount) * form.getAmount();
						
						//验证应该需要的材料总数与页面计算的材料总数对比
						if(totalamount_need.intValue() != Integer.parseInt(totalamount)){//不相等，计算错误
							responseMap.put("status", "0");//保存失败
							responseMap.put("result", materialname +" 总数计算有误，请重新刷新计算");
							//回滚
							txManager.rollback(status);
							return responseMap;
						}
						
						//查询页面选中的批次号是否带有唯一的SN码
						Materialdepot materialdepotform = new Materialdepot();
						materialdepotform.setMaterialcode(materialcode);
						materialdepotform.setDepotstatus("1");
						materialdepotform.setMaterialstatus("1");
						materialdepotform.setBatchno(batchno);
						//查询SN码最小的批次号库存
						Materialdepot materialdepot = materialdepotDao.findMinIdentfyByBatchno(materialdepotform);
						if(materialdepot == null){ //表示该批次号没有SN码，只有批次号
							materialdepot = materialdepotDao.findByBatchno(batchno);
						}
						
						if(materialdepot == null ){
							responseMap.put("status", "0");//保存失败
							responseMap.put("result", "生产失败，库存中不存在" + materialname);
							//回滚
							txManager.rollback(status);
							return responseMap;
						}
						
						if(!"1".equals(materialdepot.getDepotstatus())){
							responseMap.put("status", "0");//保存失败
							responseMap.put("result", "生产失败，" + materialname +" 材料不是库存状态 ");
							//回滚
							txManager.rollback(status);
							return responseMap;
						}
					
						if(materialdepot.getDepotamount() < Integer.valueOf(amount)){ //库存量不够
							responseMap.put("status", "0");//保存失败
							responseMap.put("result", "生产失败，"+ materialname +" 库存量不够本次生产所需材料数量");
							//回滚
							txManager.rollback(status);
							return responseMap;
						}
						
						//修改该材料库存量
						//已有库存量
						int depotamount = materialdepot.getDepotamount();
						//本次出库量
						materialdepot.setDepotamount(depotamount-Integer.valueOf(amount));
						
						if(depotamount-Integer.valueOf(amount) == 0){
							materialdepot.setDepotstatus("0"); //无库存
						}
						materialdepotDao.update(materialdepot);
						//增加材料出库记录
						Materialinout materialinout = new Materialinout();
						materialinout.setMaterialcode(materialcode);
						materialinout.setMaterialname(materialname);
						materialinout.setOperatorcode(operator.getEmployeecode());
						materialinout.setBatchno(batchno);
						materialinout.setMaterialidentfy(materialdepot.getMaterialidentfy());
						materialinout.setMaterialidentfy(materialdepot.getMaterialidentfy());
						materialinout.setType("1");
						materialinout.setInoutamount(Integer.valueOf(amount));
						materialinout.setAddtime(currenttime);
						materialinout.setInoutercode(form.getProducercode());
						materialinout.setInoutstatus("1");
						materialinout.setDepotrackcode(materialdepot.getDepotrackcode());
						materialinoutDao.save(materialinout);
						
						//初始化生产材料表
						Producematerial producematerial = new Producematerial();
						producematerial.setProducecode(producecode);
						producematerial.setProductcode(form.getProductcode());
						producematerial.setProductname(product.getProductname());
						producematerial.setProductidentfy(produceproduct.getProductidentfy());
						producematerial.setMaterialcode(materialcode);
						producematerial.setMaterialname(materialname);
						producematerial.setBatchno(batchno);
						producematerial.setMaterialidentfy(materialdepot.getMaterialidentfy());
						producematerial.setAmount(Integer.valueOf(amount));
						producematerial.setDepotrackcode(depotrackcode);
						producematerial.setAddtime(currenttime);
						producemateriallist.add(producematerial);
					}
				}
				
				//批量保存生产产品信息
				produceproductDao.saveBatch(produceproductlist);
				
				//批量保存生产材料信息
				producematerialDao.saveBatch(producemateriallist);
				
				//增加操作日记
				String content = "添加 生产组装， 生产批次号:"+form.getProducecode() + " 产品名称:" + product.getProductname();
				operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
				
				//事务提交
				txManager.commit(status);
				
				responseMap.put("status", "1");//保存失败
				responseMap.put("result", "生产组装成功");//
				return responseMap;
			}catch(Exception e){
				
				e.printStackTrace();
				
				//回滚
				txManager.rollback(status);
				
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，生产组装失败");//
				return responseMap;
			}
		} 	
	}   
	
}
