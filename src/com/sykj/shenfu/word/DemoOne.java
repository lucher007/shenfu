package com.sykj.shenfu.word;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import fr.opensagres.xdocreport.core.document.SyntaxKind;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.images.FileImageProvider;
import fr.opensagres.xdocreport.document.images.IImageProvider;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import fr.opensagres.xdocreport.template.formatter.NullImageBehaviour;

public class DemoOne {

	public static void main(String args[]) throws Exception {
		String docxSourceFilePath = "c:\\template.docx";
		String docxTargetFilePath = "c:\\test.docx";
		InputStream in =new FileInputStream(new File(docxSourceFilePath));
	    IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity );

	    IContext context = report.createContext();
	    Map<String,Object> map= new HashMap<String,Object>();
	    
	    map.put("value1", "王小波");
	    map.put("value2", "申亚科技");
	    map.put("value3", "a333");
	    //map.put("value4", "四川省成都市金牛区交 <b>大路266号</b>");//address1
	    //map.put("value5", "四川省成都市天府新区天府五街13号");//address2

	    if(map!=null){
	    	 GenerateDocx.replaceSingleDateTemplate(context,map);
	  
	    	 FieldsMetadata metadata = new FieldsMetadata();
	    	 //插入图片
			 metadata.addFieldAsImage("value6",NullImageBehaviour.RemoveImageTemplate);
			 report.setFieldsMetadata(metadata);
			 IImageProvider value6 = new FileImageProvider(new File("c:/111.png"));
			 value6.setSize(400f, 400f);
	    	 context.put("value6", value6);
 
		 }
	    
	  //保存数据填充后生成新的文档
        OutputStream out = new FileOutputStream(new File(docxTargetFilePath));
        report.process(context, out );
	}
	
	 public static void loadImageMetaData(FieldsMetadata metadata,String fieldName) {
	    	metadata.addFieldAsImage(fieldName);
	 }
}
