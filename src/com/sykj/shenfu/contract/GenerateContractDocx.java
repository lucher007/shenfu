package com.sykj.shenfu.contract;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.po.Userorder;
import com.sykj.shenfu.po.Userproduct;

import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

public class GenerateContractDocx {

	public static void main(String[] args) {
		//generateDocx();
	}
	
	public static void generateDocx(String docxSourceFilePath,String docxTargetFilePath,Userorder userorder){
		//路径自定义
		//String docxSourceFilePath = "C:\\申亚科技门锁销售合同书.docx";
		//String docxTargetFilePath = "c:\\test1234.docx";
		 try {

	        InputStream in =new FileInputStream(new File(docxSourceFilePath));
	        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity );

	        IContext context = report.createContext();
			Map<String,Object> map= new HashMap<String,Object>();
			Map<String,Object> mutilMap= new HashMap<String,Object>();
			//获取通知书类路径
			map.put(ContractConstants.USERNAME, userorder.getUsername());
			//map.put(ContractConstants.CONTRACTNO,userorder.getContractcode());
			map.put(ContractConstants.SALENAME, "申亚科技");
			map.put(ContractConstants.FIRSTPATMENT, userorder.getFirstpayment());
			map.put(ContractConstants.FINALPAYMENT, userorder.getFinalpayment());
			//合同信息
			map.put(ContractConstants.USERADDRESS, userorder.getAddress());
			map.put(ContractConstants.SALEADDRESS, "四川省成都市天府新区天府五街13号");
			
			map.put(ContractConstants.YEAR, Tools.getCurrentTimeByFormat("yyyy"));
			map.put(ContractConstants.MONTH,Tools.getCurrentTimeByFormat("MM"));
			map.put(ContractConstants.DAY, Tools.getCurrentTimeByFormat("dd"));
			map.put(ContractConstants.TOTALMONET,userorder.getTotalmoney());
			
			//产品列表信息
			List<ContractBean>  ContractBeanList = new ArrayList<ContractBean>();
			
			List<Userproduct> userproductlist = userorder.getUserproductList();
			for (Userproduct userproduct : userproductlist) {
				ContractBean productBean = new ContractBean();
				productBean.setProductname(userproduct.getProductname());
				productBean.setModelname(userproduct.getModelname());
				//productBean.setProductcolor(userproduct.getProductcolor());
				productBean.setSaleprice(String.valueOf(userproduct.getSaleprice()));
				ContractBeanList.add(productBean);
			}
			
			mutilMap.put("contractlist", ContractBeanList);
			
			 if(map!=null){
				 replaceSingleDateTemplate(context,map);
			 }
			 if(mutilMap!=null){
				 FieldsMetadata metadata = report.createFieldsMetadata();
		            loadMetadata(metadata,mutilMap);
				 replaceListDataTemplate(context,mutilMap);
			 }
			 

			//保存数据填充后生成新的文档
	        OutputStream out = new FileOutputStream(new File(docxTargetFilePath));
	        report.process(context, out );
		 }catch(Exception e) {
			    e.printStackTrace();
		 }
		 
		//将目标DOCX文件转换成PDF文件
	}
	
	/**
     * DOCX
     * 针对单个文本占位符的数据自动填充
     * @param template
     * @param singleMap
     * @throws CommonException
     */
    public static void  replaceSingleDateTemplate(IContext context,Map  singleMap) {
		if(singleMap!=null){
			Set  set = singleMap.entrySet();
		    try {
		        for (Iterator  it = set.iterator(); it.hasNext();) {
		            Map.Entry  entry = (Map.Entry) it.next();
		            	//StringUtils.strToRtf 专为解决中文编码在rtf模版中乱码问题。
		            context.put(entry.getKey().toString(),entry.getValue()!=null?entry.getValue().toString():"");
		        }
		    } catch (Exception e) {
				e.printStackTrace();
			}
		}
    }


    /**
     * DOCX
     * 针对List的数据自动填充
     * @param mutilList
     * @param tablesList
     * @throws CommonException
     */
    public static void  replaceListDataTemplate(IContext context,Map map){
		if(map!=null){
		    try {
				Set  set = map.entrySet();
		        for (Iterator  it = set.iterator(); it.hasNext();) {
		                Map.Entry  entry = (Map.Entry) it.next();
		                context.put(entry.getKey().toString(),entry.getValue());
		         }
	        }catch (Exception e) {
				e.printStackTrace();
			}
		}
    }
    
    /**
     * DOCX
     * metadata取得类class
     * @param metadata
     * @param mutilMap
     * @throws CommonException
     */
    public static void  loadMetadata(FieldsMetadata metadata ,Map mutilMap) {
		if(mutilMap!=null){
		    try {
				Set  set = mutilMap.entrySet();
		        for (Iterator  it = set.iterator(); it.hasNext();) {
		                Map.Entry  entry = (Map.Entry) it.next();
		                metadata.load(entry.getKey().toString(),((List) entry.getValue()).get(0).getClass(), true );
		         }
	        }catch (Exception e) {
				e.printStackTrace();
			}
		}
    }
}
