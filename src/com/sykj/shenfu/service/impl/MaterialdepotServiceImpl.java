package com.sykj.shenfu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.dao.IMaterialdepotDao;
import com.sykj.shenfu.dao.IMaterialinoutDao;
import com.sykj.shenfu.po.Coderule;
import com.sykj.shenfu.po.Materialdepot;
import com.sykj.shenfu.po.Materialinout;
import com.sykj.shenfu.service.ICoderuleService;
import com.sykj.shenfu.service.IMaterialdepotService;


/**
 * @Title MaterialdepotServiceImpl.java
 * @version 1.0 药材库存实现类
 */
@Service("materialdepotService")
public class MaterialdepotServiceImpl implements IMaterialdepotService {
	@Autowired
	private IMaterialdepotDao materialdepotDao;
	@Autowired
	private IMaterialinoutDao materialinoutDao;
	@Autowired
	private ICoderuleService coderuleService;
	
	//定义锁对象
	private Lock lock = new ReentrantLock();
	
	/**
	 * 药材入库
	 */
	public void saveMaterialdepot_instor(Materialdepot materialdepot) {
		
		try{
			
			//加锁
			lock.lock();
			
			//材料仓库ID
			Integer depotid = 0;
			
			//查询药材库存存不存在此种药材
			Materialdepot depot = materialdepotDao.findByBatchno(materialdepot.getBatchno());
			
			//表示库存里有此种材料数据，修改库存量就行
			if(depot != null){
				Integer depotamount = depot.getDepotamount() + materialdepot.getDepotamount();
				depot.setDepotamount(depotamount);
				materialdepotDao.updateDepotamount(depot);
				depotid = depot.getId();
			}else{//表示此次入库为新材料，新增加库存
				materialdepotDao.save(materialdepot);
				depotid = materialdepot.getId();
			}
			
			//增加材料入库记录
			Materialinout materialinout = new Materialinout();
			materialinout.setMaterialcode(materialdepot.getMaterialcode());
			materialinout.setMaterialname(materialdepot.getMaterialname());
			materialinout.setOperatorcode(materialdepot.getOperatorcode());
			materialinout.setBatchno(materialdepot.getBatchno());
			materialinout.setType("0");
			materialinout.setInoutamount(materialdepot.getDepotamount());
			materialinout.setAddtime(Tools.getCurrentTime());
			
			materialinoutDao.save(materialinout);
			
		} finally {
			
			//释放锁
			lock.unlock();
		}
	}
	
	/**
	 * 药材入库
	 */
	public void saveMaterialdepot_instorForInstall(Materialdepot materialdepot) {
		
		try{
			
			//加锁
			lock.lock();
			
			//入库
			materialdepotDao.save(materialdepot);
			
			//增加材料入库记录
			Materialinout materialinout = new Materialinout();
			materialinout.setMaterialcode(materialdepot.getMaterialcode());
			materialinout.setMaterialname(materialdepot.getMaterialname());
			materialinout.setOperatorcode(materialdepot.getOperatorcode());
			materialinout.setBatchno(materialdepot.getBatchno());
			materialinout.setType("0");
			materialinout.setInoutamount(materialdepot.getDepotamount());
			materialinout.setAddtime(Tools.getCurrentTime());
			materialinout.setMaterialidentfy(materialdepot.getMaterialidentfy());
			
			materialinoutDao.save(materialinout);
			
		} finally {
			
			//释放锁
			lock.unlock();
		}
	}
	
	/**
	 * 药材入库
	 * return  0：无此药材库存
	 *        -1：库存量不够
	 *         1：出库成功
	 */
	public String saveMaterialdepot_outstor(Materialdepot materialdepot) {
		
		try{
			
			//加锁
			lock.lock();
			
			//材料仓库ID
			Integer depotid = 0;
			
			//查询药材库存存不存在此种药材
			Materialdepot depot = materialdepotDao.findByBatchno(materialdepot.getBatchno());
			
			//表示库存里有此种材料数据，修改库存量就行
			if(depot != null){
				//将库存与出库量进行比较
				int flag = depot.getDepotamount().compareTo(materialdepot.getDepotamount());
				
				if(flag >= 0){//库存够这次出库量
					Integer depotamount = depot.getDepotamount() - materialdepot.getDepotamount();
					depot.setDepotamount(depotamount);
					//修改库存量
					materialdepotDao.updateDepotamount(depot);
					depotid = depot.getId();
					
					//增加材料出库记录
					Materialinout materialinout = new Materialinout();
					materialinout.setMaterialcode(depot.getMaterialcode());
					materialinout.setMaterialname(depot.getMaterialname());
					materialinout.setOperatorcode(materialdepot.getOperatorcode());
					materialinout.setBatchno(depot.getBatchno());
					materialinout.setType("1");
					materialinout.setInoutamount(materialdepot.getDepotamount());
					materialinout.setAddtime(Tools.getCurrentTime());
					
					materialinoutDao.save(materialinout);
					
					return "1";
					
				}else{
					return "-1";
				}
				
			}else{//表示此次入库为新材料，新增加库存
				return "0";
			}
			
		} finally {
			
			//释放锁
			lock.unlock();
		}
	}
	
	/**
	 * 获取产品唯一识别号
	 * 
	 * @param Productdepot
	 * @return
	 */
	public String getMaterialbatchno(String materialcode){
		//材料批次号
		String batchno = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_materialidentfy);
		String startBatchno = StringUtils.substring(batchno, 0, batchno.length()-4);
		String endBatchno = StringUtils.substring(batchno, batchno.length()-4);
		//组装材料批次号
		return (startBatchno + materialcode + endBatchno);
		
	}
	
	/**
	 * 查询数据库里有库存的各种材料批次号
	 * 
	 * @param Materialdepot
	 * @return
	 */
	public HashMap<String,ArrayList<Materialdepot>> findMaterialdepotHashmap(Materialdepot form){
		HashMap<String,ArrayList<Materialdepot>> materialdepotHp = new HashMap<String,ArrayList<Materialdepot>>();
		
		//查询材料各批次号的信息
		List<Materialdepot> matedepotlist = materialdepotDao.queryMatarialListGroupByBatchno(form);
		for (Materialdepot materialdepot : matedepotlist) {
			//从Hashmap中查找该编码
			ArrayList<Materialdepot> materialdepotList = materialdepotHp.get(materialdepot.getMaterialcode());
			if(materialdepotList == null){
				materialdepotList = new ArrayList<Materialdepot>();
			}
			materialdepotList.add(materialdepot);
			materialdepotHp.put(materialdepot.getMaterialcode(), materialdepotList);
		}
		return materialdepotHp;
	}
	
	/**
	 * 从数据库里各种材料批次号HashMap中，获取符合条件的最早的那个批次号
	 * 
	 * @param Materialdepot
	 * @return
	 */
	public Materialdepot findMaterialdepotOldest(HashMap<String,ArrayList<Materialdepot>> materialdepotHp, Materialdepot form){
		//无任何库存
		if(materialdepotHp == null){
			return null;
		}
		//库存中没有该材料编码
		ArrayList<Materialdepot> materialdepotlist =  materialdepotHp.get(form.getMaterialcode());
		if(materialdepotlist == null || materialdepotlist.size() == 0){
			return null;
		}
		
		Materialdepot materialdepotOldest = new Materialdepot();
		
		for (Materialdepot materialdepot : materialdepotlist) {
			if(StringUtils.isNotEmpty(materialdepotOldest.getBatchno())){
				if(materialdepotOldest.getBatchno().compareTo(materialdepot.getBatchno()) > 0){//找到库存中最老的库存
					if(materialdepot.getDepotamount() >= form.getInoutamount()){//库存中足够量出库
						Tools.setVOToVO(materialdepot, materialdepotOldest);
					}
				}
			}else{
				if(materialdepot.getDepotamount() >= form.getInoutamount()){//库存中足够量出库
					Tools.setVOToVO(materialdepot, materialdepotOldest);
				}
			}
		}
		
		return materialdepotOldest;
		
	}
}
