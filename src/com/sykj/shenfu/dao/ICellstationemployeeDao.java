package com.sykj.shenfu.dao;


import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sykj.shenfu.po.Cellstationemployee;

/**
 * 用户数据层接口
 */
public interface ICellstationemployeeDao {
	/**
	 * 小区驻点添加
	 * 
	 * @param Cell
	 * @return
	 */
	public Integer save(Cellstationemployee form);
	
	/**
	 * 小区驻点更新
	 * 
	 * @param Cell
	 * @return
	 */
	public Integer update(Cellstationemployee form);
	
	/**
	 * 小区驻点删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 小区驻点删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer deleteByStationcode(String stationcode);
	
	/**
	 * 小区驻点分页查询
	 * 
	 * @param Cell
	 * @return
	 */
	public List<Cellstationemployee> findByList(Cellstationemployee form);
	
	/**
	 * 小区驻点全部查询
	 * 
	 * @param Cell
	 * @return
	 */
	public List<Cellstationemployee> queryByList(Cellstationemployee form);
	
	/**
	 * 小区驻点分页总数
	 * 
	 * @param Cell
	 * @return
	 */
	public Integer findByCount(Cellstationemployee form);
	
	/**
	 * 小区驻点查询根据ID
	 * 
	 * @param Cell
	 * @return
	 */
	public Cellstationemployee findById(Integer id);
	
	/**
	 * 小区驻点查询根据小区驻点编号
	 * 
	 * @param Cell
	 * @return
	 */
	public List<Cellstationemployee> findByStationcode(String stationcode);
	
	/**
	 * 小区驻点查询根据小区驻点编号和工作人员
	 * 
	 * @param Cell
	 * @return
	 */
	public Cellstationemployee findByStationcodeAndEmployeecode(@Param("stationcode")String stationcode,@Param("employeecode")String employeecode,@Param("employeetype")String employeetype);
	
	/**
	 * 小区驻点信息查询，根据驻点人员，驻点开始结束时间有交叉的
	 * 
	 * @param Cell
	 * @return
	 */
	public List<Cellstationemployee> findByEmployeecodeAndStratEndtime(@Param("employeecode")String employeecode,@Param("starttime")String starttime,@Param("endtime")String endtime);
}
