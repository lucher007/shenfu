package com.sykj.shenfu.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.sykj.shenfu.po.Componentdepot;

/**
 * 元器件库存接口
 */
public interface IComponentdepotService {
	
	/**
	 * 元器件入库
	 * 
	 * @param Componentdepot
	 * @return
	 */
	public void saveComponentdepot_instor(Componentdepot componentdepot);
	
	/**
	 * 元器件出库
	 * 
	 * @param Componentdepot
	 * @return
	 */
	public String saveComponentdepot_outstor(Componentdepot componentdepot);
	
	/**
	 * 获取元器件批次号
	 * 
	 * @param Productdepot
	 * @return
	 */
	public String getComponentbatchno(String componentcode);
	
	/**
	 * 查询数据库里元器件批次号HashMap
	 * 
	 * @param Componentdepot
	 * @return
	 */
	public HashMap<String,ArrayList<Componentdepot>> findComponentdepotHashmap(Componentdepot form);
	
	/**
	 * 从数据库里各种元器件批次号HashMap中，获取符合条件的最早的那个批次号
	 * 
	 * @param Componentdepot
	 * @return
	 */
	public Componentdepot findComponentdepotOldest(HashMap<String,ArrayList<Componentdepot>> componentdepotHp, Componentdepot form);
	
}
