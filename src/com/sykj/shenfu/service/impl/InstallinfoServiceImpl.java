package com.sykj.shenfu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.sykj.shenfu.dao.IComponentdepotDao;
import com.sykj.shenfu.dao.IComponentinoutDao;
import com.sykj.shenfu.dao.IInstallinfoDao;
import com.sykj.shenfu.dao.IInstallcomponentDao;
import com.sykj.shenfu.dao.IInstallmaterialDao;
import com.sykj.shenfu.dao.IMaterialDao;
import com.sykj.shenfu.po.Coderule;
import com.sykj.shenfu.po.Componentdepot;
import com.sykj.shenfu.po.Componentinout;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Installinfo;
import com.sykj.shenfu.po.Installcomponent;
import com.sykj.shenfu.po.Installmaterial;
import com.sykj.shenfu.po.Material;
import com.sykj.shenfu.service.ICoderuleService;
import com.sykj.shenfu.service.IOperatorlogService;
import com.sykj.shenfu.service.IInstallinfoService;
import com.sykj.shenfu.service.IMaterialdepotService;

/**
 * @Title MaterialdepotServiceImpl.java
 * @version 1.0 编码规则类
 */
@Service("installinfoService")
public class InstallinfoServiceImpl extends BaseController implements IInstallinfoService {
	@Autowired
    private DataSourceTransactionManager txManager;
	@Autowired
	private IMaterialDao materialDao;
	@Autowired
	private ICoderuleService coderuleService;
	@Autowired
	private IInstallinfoDao installinfoDao;
	@Autowired
	private IInstallmaterialDao installmaterialDao;
	@Autowired
	private IInstallcomponentDao installcomponentDao;
	@Autowired
	private IMaterialdepotService materialdepotService;
	@Autowired
	private IComponentdepotDao componentdepotDao;
	@Autowired
	private IComponentinoutDao componentinoutDao;
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
	public Map<String, Object> saveInstallinfo(Installinfo form) {
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
				
				//保存型号关联的元器件信息
				String componentJson = getRequest().getParameter("componentJson");
				if(StringUtils.isEmpty(componentJson)){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "请选择关联的元器件信息");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//增加关联的元器件信息
				JSONArray componentJsonArry = JSONArray.fromObject(componentJson);
				
				if(StringUtils.isEmpty(form.getMaterialcode())){
					
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "请选择材料编码");
					//回滚
					txManager.rollback(status);
					return responseMap;
					
				}
				
				Material material = materialDao.findByMaterialcodeStr(form.getMaterialcode());
				if(material == null){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "材料编码不存在");
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
				form.setMaterialname(material.getMaterialname());
				form.setOperatorcode(operator.getEmployeecode());
				form.setAddtime(currenttime);
				
				//保存生产信息
				installinfoDao.save(form);
				
				//生产材料列表
				//材料批次号
				String materialbatchno = materialdepotService.getMaterialbatchno(form.getMaterialcode());
				ArrayList<Installmaterial> installmateriallist = new ArrayList<Installmaterial>();
				for(int i=0; i<form.getAmount(); i++){
					Installmaterial installmaterial = new Installmaterial();
					installmaterial.setProducecode(form.getProducecode());
					installmaterial.setMaterialcode(form.getMaterialcode());
					installmaterial.setMaterialname(material.getMaterialname());
					installmaterial.setBatchno(materialbatchno);
					//材料唯一识别号 = 材料批次号 + 4位顺序号
					String materialidentfy = materialbatchno + StringUtils.leftPad(String.valueOf(i+1), 4, "0");
					installmaterial.setMaterialidentfy(materialidentfy);
					installmaterial.setDepotamount(1);
					installmaterial.setMaterialstatus("1");//组装中
					installmaterial.setPrintflag("0");//未打印
					installmaterial.setAddtime(currenttime);
					
					installmateriallist.add(installmaterial);
					
				}
				
				//生产元器件列表
				ArrayList<Installcomponent> installcomponentlist = new ArrayList<Installcomponent>();
				for (int i = 0; i < componentJsonArry.size(); i++) {
					JSONObject componentJsonObject = componentJsonArry.getJSONObject(i);
					String componentcode = Tools.getStr(componentJsonObject.getString("componentcode"));
					String componentname = Tools.getStr(componentJsonObject.getString("componentname"));
					String componentmodel = Tools.getStr(componentJsonObject.getString("componentmodel"));
					String amount = Tools.getStr(componentJsonObject.getString("amount"));
					String batchno = Tools.getStr(componentJsonObject.getString("batchno"));
					String totalamount = Tools.getStr(componentJsonObject.getString("totalamount"));
					String depotrackcode = Tools.getStr(componentJsonObject.getString("depotrackcode"));
					
					//应该需要的元器件总数
					Integer totalamount_need = Integer.valueOf(amount) * form.getAmount();
					
					//验证应该需要的元器件总数与页面计算的元器件总数对比
					if(totalamount_need != Integer.parseInt(totalamount)){//不相等，计算错误
						responseMap.put("status", "0");//保存失败
						responseMap.put("result", componentname + "_" + componentmodel + " 总数计算有误，请重新刷新计算");
						//回滚
						txManager.rollback(status);
						return responseMap;
					}
					
					Componentdepot componentdepot = componentdepotDao.findByBatchno(batchno);
					
					if(componentdepot == null ){
						responseMap.put("status", "0");//保存失败
						responseMap.put("result", "生产失败，库存中不存在该批次号 " + batchno + " 的元器件。");
						//回滚
						txManager.rollback(status);
						return responseMap;
					}
					
					if(!"1".equals(componentdepot.getDepotstatus())){
						responseMap.put("status", "0");//保存失败
						responseMap.put("result", "生产失败，批次号 " + batchno  +" 元器件不是库存状态 ");
						//回滚
						txManager.rollback(status);
						return responseMap;
					}
				
					if(componentdepot.getDepotamount() < totalamount_need){ //库存量不够
						
						responseMap.put("status", "0");//保存失败
						responseMap.put("result", "生产失败，批次号 "+ batchno +" 库存量不够本次生产所需元器件数量");
						//回滚
						txManager.rollback(status);
						return responseMap;
					}
					
					//修改该元器件库存量
					//已有库存量
					Integer depotamount = componentdepot.getDepotamount();
					//本次出库量
					componentdepot.setDepotamount(depotamount-totalamount_need);
					
					if(depotamount-totalamount_need == 0){
						componentdepot.setDepotstatus("0"); //无库存
					}
					componentdepotDao.update(componentdepot);
					
					//增加元器件出库记录
					Componentinout componentinout = new Componentinout();
					componentinout.setComponentcode(componentcode);
					componentinout.setComponentname(componentname);
					componentinout.setOperatorcode(operator.getEmployeecode());
					componentinout.setBatchno(batchno);
					componentinout.setType("1");
					componentinout.setInoutamount(totalamount_need);
					componentinout.setAddtime(currenttime);
					componentinout.setInoutercode(form.getProducercode());
					componentinout.setInoutstatus("1");
					componentinout.setDepotrackcode(componentdepot.getDepotrackcode());
					componentinoutDao.save(componentinout);
						
					for (Installmaterial installmaterial : installmateriallist) {
						//初始化生产元器件表
						Installcomponent installcomponent = new Installcomponent();
						installcomponent.setProducecode(producecode);
						installcomponent.setMaterialcode(form.getMaterialcode());
						installcomponent.setMaterialname(material.getMaterialname());
						installcomponent.setMaterialname(installmaterial.getBatchno());
						installcomponent.setMaterialbatchno(installmaterial.getBatchno());
						installcomponent.setMaterialidentfy(installmaterial.getMaterialidentfy());
						installcomponent.setComponentcode(componentcode);
						installcomponent.setComponentname(componentname);
						installcomponent.setComponentmodel(componentmodel);
						installcomponent.setBatchno(batchno);
						installcomponent.setAmount(Integer.valueOf(amount));
						installcomponent.setDepotrackcode(depotrackcode);
						installcomponent.setAddtime(currenttime);
						installcomponentlist.add(installcomponent);
						
					}
				}
				
				//批量保存生产材料信息
				installmaterialDao.saveBatch(installmateriallist);
				
				//批量保存生产元器件信息
				installcomponentDao.saveBatch(installcomponentlist);
				
				//增加操作日记
				String content = "添加材料生产组装， 材料生产批次号:"+form.getProducecode() + " 材料名称:" + material.getMaterialname();
				operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
				
				//事务提交
				txManager.commit(status);
				
				responseMap.put("status", "1");//保存失败
				responseMap.put("result", "材料生产组装成功");//
				return responseMap;
			}catch(Exception e){
				
				e.printStackTrace();
				
				//回滚
				txManager.rollback(status);
				
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，材料生产组装失败");//
				return responseMap;
			}
		} 	
	}   
	
}
