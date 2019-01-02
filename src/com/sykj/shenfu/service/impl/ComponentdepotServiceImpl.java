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
import com.sykj.shenfu.dao.IComponentdepotDao;
import com.sykj.shenfu.dao.IComponentinoutDao;
import com.sykj.shenfu.po.Coderule;
import com.sykj.shenfu.po.Componentdepot;
import com.sykj.shenfu.po.Componentinout;
import com.sykj.shenfu.service.ICoderuleService;
import com.sykj.shenfu.service.IComponentdepotService;

/**
 * @Title ComponentdepotServiceImpl.java
 * @version 1.0 元器件库存实现类
 */
@Service("componentdepotService")
public class ComponentdepotServiceImpl implements IComponentdepotService {
	@Autowired
	private IComponentdepotDao componentdepotDao;
	@Autowired
	private IComponentinoutDao componentinoutDao;
	@Autowired
	private ICoderuleService coderuleService;
	
	//定义锁对象
	private Lock lock = new ReentrantLock();
	
	/**
	 * 元器件入库
	 */
	public void saveComponentdepot_instor(Componentdepot componentdepot) {
		
		try{
			
			//加锁
			lock.lock();
			
			//材料仓库ID
			Integer depotid = 0;
			
			//查询元器件库存存不存在此种元器件
			Componentdepot depot = componentdepotDao.findByBatchno(componentdepot.getBatchno());
			
			//表示库存里有此种材料数据，修改库存量就行
			if(depot != null){
				Integer depotamount = depot.getDepotamount() + componentdepot.getDepotamount();
				depot.setDepotamount(depotamount);
				componentdepotDao.updateDepotamount(depot);
				depotid = depot.getId();
			}else{//表示此次入库为新材料，新增加库存
				componentdepotDao.save(componentdepot);
				depotid = componentdepot.getId();
			}
			
			//增加材料入库记录
			Componentinout componentinout = new Componentinout();
			componentinout.setComponentcode(componentdepot.getComponentcode());
			componentinout.setComponentname(componentdepot.getComponentname());
			componentinout.setComponentmodel(componentdepot.getComponentmodel());
			componentinout.setOperatorcode(componentdepot.getOperatorcode());
			componentinout.setBatchno(componentdepot.getBatchno());
			componentinout.setType("0");
			componentinout.setInoutamount(componentdepot.getDepotamount());
			componentinout.setAddtime(Tools.getCurrentTime());
			
			componentinoutDao.save(componentinout);
			
		} finally {
			
			//释放锁
			lock.unlock();
		}
	}
	
	/**
	 * 元器件入库
	 * return  0：无此元器件库存
	 *        -1：库存量不够
	 *         1：出库成功
	 */
	public String saveComponentdepot_outstor(Componentdepot componentdepot) {
		
		try{
			
			//加锁
			lock.lock();
			
			//材料仓库ID
			Integer depotid = 0;
			
			//查询元器件库存存不存在此种元器件
			Componentdepot depot = componentdepotDao.findByBatchno(componentdepot.getBatchno());
			
			//表示库存里有此种材料数据，修改库存量就行
			if(depot != null){
				//将库存与出库量进行比较
				int flag = depot.getDepotamount().compareTo(componentdepot.getDepotamount());
				
				if(flag >= 0){//库存够这次出库量
					Integer depotamount = depot.getDepotamount() - componentdepot.getDepotamount();
					depot.setDepotamount(depotamount);
					//修改库存量
					componentdepotDao.updateDepotamount(depot);
					depotid = depot.getId();
					
					//增加材料出库记录
					Componentinout componentinout = new Componentinout();
					componentinout.setComponentcode(depot.getComponentcode());
					componentinout.setComponentname(depot.getComponentname());
					componentinout.setComponentmodel(depot.getComponentmodel());
					componentinout.setOperatorcode(componentdepot.getOperatorcode());
					componentinout.setBatchno(depot.getBatchno());
					componentinout.setType("1");
					componentinout.setInoutamount(componentdepot.getDepotamount());
					componentinout.setAddtime(Tools.getCurrentTime());
					
					componentinoutDao.save(componentinout);
					
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
	public String getComponentbatchno(String componentcode){
		//材料批次号
		String batchno = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_componentidentfy);
		String startBatchno = StringUtils.substring(batchno, 0, batchno.length()-4);
		String endBatchno = StringUtils.substring(batchno, batchno.length()-4);
		//组装材料批次号
		return (startBatchno + componentcode + endBatchno);
		
	}
	
	/**
	 * 查询数据库里有库存的各种材料批次号
	 * 
	 * @param Componentdepot
	 * @return
	 */
	public HashMap<String,ArrayList<Componentdepot>> findComponentdepotHashmap(Componentdepot form){
		HashMap<String,ArrayList<Componentdepot>> componentdepotHp = new HashMap<String,ArrayList<Componentdepot>>();
		
		List<Componentdepot> matedepotlist = componentdepotDao.queryByList(form);
		for (Componentdepot componentdepot : matedepotlist) {
			//从Hashmap中查找该编码
			ArrayList<Componentdepot> componentdepotList = componentdepotHp.get(componentdepot.getComponentcode());
			if(componentdepotList == null){
				componentdepotList = new ArrayList<Componentdepot>();
			}
			componentdepotList.add(componentdepot);
			componentdepotHp.put(componentdepot.getComponentcode(), componentdepotList);
		}
		return componentdepotHp;
	}
	
	/**
	 * 从数据库里各种材料批次号HashMap中，获取符合条件的最早的那个批次号
	 * 
	 * @param Componentdepot
	 * @return
	 */
	public Componentdepot findComponentdepotOldest(HashMap<String,ArrayList<Componentdepot>> componentdepotHp, Componentdepot form){
		//无任何库存
		if(componentdepotHp == null){
			return null;
		}
		//库存中没有该元器件编码
		ArrayList<Componentdepot> componentdepotlist =  componentdepotHp.get(form.getComponentcode());
		if(componentdepotlist == null || componentdepotlist.size() == 0){
			return null;
		}
		
		Componentdepot componentdepotOldest = new Componentdepot();
		
		for (Componentdepot componentdepot : componentdepotlist) {
			if(StringUtils.isNotEmpty(componentdepotOldest.getBatchno())){
				if(componentdepotOldest.getBatchno().compareTo(componentdepot.getBatchno()) > 0){//找到库存中最老的库存
					if(componentdepot.getDepotamount() >= form.getInoutamount()){//库存中足够量出库
						Tools.setVOToVO(componentdepot, componentdepotOldest);
					}
				}
			}else{
				if(componentdepot.getDepotamount() >= form.getInoutamount()){//库存中足够量出库
					Tools.setVOToVO(componentdepot, componentdepotOldest);
				}
			}
		}
		
		return componentdepotOldest;
		
	}
}
