package com.sykj.shenfu.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.sykj.shenfu.po.Materialdepot;

/**
 * 材料库存接口
 */
public interface IMaterialdepotService {
	
	/**
	 * 材料入库
	 * 
	 * @param Materialdepot
	 * @return
	 */
	public void saveMaterialdepot_instor(Materialdepot materialdepot);
	
	/**
	 * 材料生产入库
	 * 
	 * @param Materialdepot
	 * @return
	 */
	public void saveMaterialdepot_instorForInstall(Materialdepot materialdepot);
	
	
	/**
	 * 材料出库
	 * 
	 * @param Materialdepot
	 * @return
	 */
	public String saveMaterialdepot_outstor(Materialdepot materialdepot);
	
	/**
	 * 获取材料批次号
	 * 
	 * @param Productdepot
	 * @return
	 */
	public String getMaterialbatchno(String materialcode);
	
	/**
	 * 查询数据库里材料批次号HashMap
	 * 
	 * @param Materialdepot
	 * @return
	 */
	public HashMap<String,ArrayList<Materialdepot>> findMaterialdepotHashmap(Materialdepot form);
	
	/**
	 * 从数据库里各种材料批次号HashMap中，获取符合条件的最早的那个批次号
	 * 
	 * @param Materialdepot
	 * @return
	 */
	public Materialdepot findMaterialdepotOldest(HashMap<String,ArrayList<Materialdepot>> materialdepotHp, Materialdepot form);
	
}
