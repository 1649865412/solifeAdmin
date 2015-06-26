<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>
<%@ tag import="com.cartmatic.estore.core.util.ContextUtil" %>
<%@tag import="com.cartmatic.estore.common.service.AttributeService"%>
<%@tag import="java.util.List"%>

<%@ attribute name="type" required="true" type="java.lang.String" description="该属性的值必须为以下以模块命名的字符串(不区分大小写):Product（产品模块）,Content（内容模块）,Customer（客户模块）,Sale（销售模块）,Category（目录模块）"%>
<%@ attribute name="var" required="true" type="java.lang.String" %>
<%
  
   AttributeService attributeService = (AttributeService)ContextUtil.getSpringBeanById("attributeService");
   short tt = 0 ;
   if(type.toLowerCase().equals("product"))
      tt = 1;
   else if(type.toLowerCase().equals("content"))
      tt = 2;
   else if(type.toLowerCase().equals("customer"))
      tt = 3;
   else if(type.toLowerCase().equals("sale"))
      tt = 4;
   else if(type.toLowerCase().equals("category"))
      tt = 5;
   List list = attributeService.getAttributes(tt);
   request.setAttribute(var,list);
 %>
  