package com.sykj.shenfu.dao;

import java.util.List;

import com.sykj.shenfu.po.Salegainrule;

/**
 * 用户数据层接口
 */
public interface ISalegainruleDao {
	/**
	 * 添加
	 * 
	 * @param Salegainrule
	 * @return
	 */
	public Integer save(Salegainrule form);
	
	/**
	 * 更新
	 * 
	 * @param Salegainrule
	 * @return
	 */
	public Integer update(Salegainrule form);
	
	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 分页查询
	 * 
	 * @param Salegainrule
	 * @return
	 */
	public List<Salegainrule> findByList(Salegainrule form);
	
	/**
	 * 全部查询
	 * 
	 * @param Salegainrule
	 * @return
	 */
	public List<Salegainrule> queryByList(Salegainrule form);
	
	/**
	 * 分页总数
	 * 
	 * @param Salegainrule
	 * @return
	 */
	public Integer findByCount(Salegainrule form);
	
	/**
	 * 查询根据ID
	 * 
	 * @param Salegainrule
	 * @return
	 */
	public Salegainrule findById(Integer id);
	
	/**
	 * 查询根据编码
	 * 
	 * @param Salegainrule
	 * @return
	 */
	public Salegainrule findByGaincode(String gaincode);

}
