<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- 发票打印表格 -->
<div class="printInfo">
    	<table border=1 cellSpacing=1 cellPadding=1 width="100%" style="border-collapse:collapse;bordercolor:#333333;font-size:9pt;">
        	<caption><b><font face="黑体" size="4">材料出入库清单</font></b><br></br></caption>
        	<tr height="30">
    			<td align="center"><b>材料名称</b></td>
          		<td align="center"><b>出入库量</b></td>
	          	<td align="center"><b>负责人</b></td>
		        <td align="center"><b>操作时间</b></td>
        	</tr>
        	<c:forEach items="${materialinout.materialinoutlist }" var="dataList">
		        	<tr height="30" >
		        		<td width="40%" align="center">${dataList.materialname }</td>
		        		<td width="20%" align="center">${dataList.inoutamount }</td>
		        		<td width="20%" align="center">${dataList.inouter.employeename }</td>
		        		<td width="20%" align="center">${fn:substring(dataList.addtime, 0, 19)}</td>
		        	</tr>
	         </c:forEach>
        </table>
</div>



