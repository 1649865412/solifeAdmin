
<%@tag import="com.cartmatic.estore.supplier.service.TbCatPropReferManager"%>
<%@tag import="com.cartmatic.estore.common.model.supplier.TbCatPropRefer"%><%@tag import="com.cartmatic.estore.core.util.ContextUtil"%><%@ tag body-content="scriptless"%>
<%@ tag pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ attribute name="tbCatPropId" required="true" rtexprvalue="true" type="java.lang.Long" %>
<%@ attribute name="var" required="true" type="java.lang.String"%>
<%
TbCatPropRefer tbCatPropRefer=null;
if(tbCId!=null&&tbCId>0){
	TbCatPropReferManager tbCatPropReferManager=(TbCatPropReferManager)ContextUtil.getSpringBeanById("tbCatPropReferManager");
	tbCatPropRefer=tbCatPropReferManager.getTbCatPropReferByTbCatPropId(tbCatPropId);
}
request.setAttribute(var,tbCatPropRefer);
%>