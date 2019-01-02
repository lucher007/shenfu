package com.sykj.shenfu.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.sykj.shenfu.po.Materialdepot;
import com.sykj.shenfu.po.Productdepot;

/**
 * 产品库存接口
 */
public interface IProductdepotService {
	
	/**
	 * 产品入库
	 * 
	 * @param Productdepot
	 * @return
	 */
	public void saveProductdepot_instor(Productdepot productdepot);
	
	/**
	 * 产品出库
	 * 
	 * @param Productdepot
	 * @return
	 */
	public String saveProductdepot_outstor(Productdepot productdepot);
	
	/**
	 * 获取产品唯一识别号
	 * 
	 * @param Productdepot
	 * @return
	 */
	public String getProductidentfy(String productcode);
	
	/**
	 * 查询数据库里产品批次号HashMap
	 * 
	 * @param Materialdepot
	 * @return
	 */
	public HashMap<String,ArrayList<Productdepot>> findProductdepotHashmap(Productdepot form);
	
	/**
	 * 从数据库里各种产品批次号HashMap中，获取符合条件的最早的那个批次号
	 * 
	 * @param Materialdepot
	 * @return
	 */
	public Productdepot findProductdepotOldest(HashMap<String,ArrayList<Productdepot>> productdepotHp, Productdepot form);
	
	
}
