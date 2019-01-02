package com.sykj.shenfu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;

import com.sykj.shenfu.common.PrimaryGenerater;
import com.sykj.shenfu.dao.ICoderuleDao;
import com.sykj.shenfu.po.Coderule;
import com.sykj.shenfu.service.ICoderuleService;

/**
 * @Title ProductdepotServiceImpl.java
 * @version 1.0 编码规则类
 */
@Service("coderuleService")
public class CoderuleServiceImpl implements ICoderuleService {
	@Autowired
	private ICoderuleDao coderuleDao;
	@Autowired
    private DataSourceTransactionManager txManager;
	/*
     * 特殊的instance变量
     * 注：零长度的byte数组对象创建起来将比任何对象都经济――查看编译后的字节码：
     * 生成零长度的byte[]对象只需3条操作码，而Object lock = new Object()则需要7行操作码。
     */
    private static byte[] lock = new byte[0];   
	
    /**
	 * 获取系统编码
	 * 
	 * @param String
	 * @return
	 */
	public String getSystemcodenoByCodetypeStr(String codetype) {
			//加锁
			synchronized(lock) {
				
				DefaultTransactionDefinition def = new DefaultTransactionDefinition();

				   def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务

				   TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
				try{
				    //逻辑代码，可以写上你的逻辑处理代码
					//数据库最大的编码
					Coderule coderule = coderuleDao.findByCodetypeStr(codetype);
					//获取最新的编码
					String codeno_new = PrimaryGenerater.generaterNextNumber(coderule.getCodevalue(),coderule.getPrecode(),coderule.getSufcode());
					coderule.setCodevalue(codeno_new);
					//修改最新的编码保存入库
					coderuleDao.update(coderule);
					//事务提交
					txManager.commit(status);
				    return codeno_new;
				}catch(Exception e){
					txManager.rollback(status);
					return null;
				}
				
			} 
	}   
	
}
