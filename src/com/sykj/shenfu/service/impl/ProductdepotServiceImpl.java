package com.sykj.shenfu.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.dao.IProductdepotDao;
import com.sykj.shenfu.dao.IProductinoutDao;
import com.sykj.shenfu.po.Coderule;
import com.sykj.shenfu.po.Productdepot;
import com.sykj.shenfu.po.Productinout;
import com.sykj.shenfu.service.ICoderuleService;
import com.sykj.shenfu.service.IProductdepotService;


/**
 * @Title ProductdepotServiceImpl.java
 * @version 1.0 产品库存实现类
 */
@Service("productdepotService")
public class ProductdepotServiceImpl implements IProductdepotService {
	@Autowired
	private IProductdepotDao productdepotDao;
	@Autowired
	private IProductinoutDao productinoutDao;
	@Autowired
	private ICoderuleService coderuleService;
	
	//定义锁对象
	private Lock lock = new ReentrantLock();
	
	/**
	 * 产品入库
	 */
	public void saveProductdepot_instor(Productdepot productdepot) {
		
		try{
			
			//加锁
			lock.lock();
			
			productdepotDao.save(productdepot);
				
			//增加材料入库记录
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
			
		} finally {
			
			//释放锁
			lock.unlock();
		}
	}
	
	/**
	 * 产品出库
	 * return  0：无此产品库存
	 *        -1：产品已经出库
	 *         1：出库成功
	 */
	public String saveProductdepot_outstor(Productdepot productdepot) {
		
		try{
			
			//加锁
			lock.lock();
			
			//查询产品库存不存在此种产品
			Productdepot depot = productdepotDao.findByProductidentfy(productdepot.getProductidentfy());
			if(depot != null){//有库存
				if("0".equals(depot.getProductstatus())){
					//修改该产品
					depot.setDepotamount(0);
					depot.setProductstatus("1"); //已售
					productdepotDao.update(depot);
					
					//增加产品出库记录
					Productinout productinout = new Productinout();
					productinout.setProductcode(depot.getProductcode());
					productinout.setProductname(depot.getProductname());
					productinout.setProductidentfy(depot.getProductidentfy());
					productinout.setType("1");
					productinout.setInoutercode(productdepot.getOperatorcode());
					productinout.setInoutamount(new Integer("1"));
					productinout.setAddtime(Tools.getCurrentTime());
					
					productinoutDao.save(productinout);
					
					return "1";//出库成功
					
				}else{ 
					return "-1";//此产品已经出售
				}
				
			}else{
				return "0";//无此产品记录
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
	public String getProductidentfy(String productcode){
		//产品识别号
		String productidentfy = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_productidentfy);
		
		String startProductidentfy = StringUtils.substring(productidentfy, 0, productidentfy.length()-4);
		String endProductidentfy = StringUtils.substring(productidentfy, productidentfy.length()-4);
		//组装产品唯一标识
		return (startProductidentfy + productcode + endProductidentfy);
	}
	
	
	/**
	 * 查询数据库里有库存的各种产品批次号
	 * 
	 * @param Materialdepot
	 * @return
	 */
	public HashMap<String,ArrayList<Productdepot>> findProductdepotHashmap(Productdepot form){
		HashMap<String,ArrayList<Productdepot>> productdepotHp = new HashMap<String,ArrayList<Productdepot>>();
		
		List<Productdepot> productdepotlist = productdepotDao.queryByList(form);
		for (Productdepot productdepot : productdepotlist) {
			//从Hashmap中查找该编码
			ArrayList<Productdepot> productdepotList = productdepotHp.get(productdepot.getProductcode());
			if(productdepotList == null){
				productdepotList = new ArrayList<Productdepot>();
			}
			productdepotList.add(productdepot);
			productdepotHp.put(productdepot.getProductcode(), productdepotList);
		}
		return productdepotHp;
	}
	
	/**
	 * 从数据库里各种产品批次号HashMap中，获取符合条件的最早的那个批次号
	 * 
	 * @param Productdepot
	 * @return
	 */
	public Productdepot findProductdepotOldest(HashMap<String,ArrayList<Productdepot>> productdepotHp, Productdepot form){
		//无任何库存
		if(productdepotHp == null){
			return null;
		}
		//库存中没有该材料编码
		ArrayList<Productdepot> productdepotlist =  productdepotHp.get(form.getProductcode());
		if(productdepotlist == null || productdepotlist.size() == 0){
			return null;
		}
		
		Productdepot productdepotOldest = new Productdepot();
		
		for (Productdepot productdepot : productdepotlist) {
			if(StringUtils.isNotEmpty(productdepotOldest.getProductidentfy())){
				if(productdepotOldest.getProductidentfy().compareTo(productdepot.getProductidentfy()) > 0){//找到库存中最老的库存
					if(productdepot.getDepotamount() >= form.getInoutamount()){//库存中足够量出库
						Tools.setVOToVO(productdepot, productdepotOldest);
					}
				}
			}else{
				if(productdepot.getDepotamount() >= form.getInoutamount()){//库存中足够量出库
					Tools.setVOToVO(productdepot, productdepotOldest);
				}
			}
		}
		
		return productdepotOldest;
		
	}
}
