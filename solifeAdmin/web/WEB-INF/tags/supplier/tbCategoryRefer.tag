<%@tag import="com.cartmatic.estore.common.model.supplier.TbCategoryRefer"%>
<%@tag import="com.cartmatic.estore.core.util.ContextUtil"%>
<%@tag import="com.cartmatic.estore.supplier.service.TbCategoryReferManager"%><%@ tag body-content="scriptless"%>
<%@ tag pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ attribute name="tbCId" required="true" rtexprvalue="true" type="java.lang.Long" %>
<%@ attribute name="var" required="true" type="java.lang.String"%>
<%
TbCategoryRefer tbCategoryRefer=null;
if(tbCId!=null&&tbCId>0){
	TbCategoryReferManager tbCategoryReferManager=(TbCategoryReferManager)ContextUtil.getSpringBeanById("tbCategoryReferManager");
	tbCategoryRefer=tbCategoryReferManager.getTbCategoryReferByTbCId(tbCId);
}
request.setAttribute(var,tbCategoryRefer);
%>