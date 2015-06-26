<%@ tag body-content="empty"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ attribute name="attrPath" required="true" rtexprvalue="false" type="java.lang.String" description="attribute filter on"%>
<%@ attribute name="datatype" required="true" rtexprvalue="false" type="java.lang.String" description="supported datatype:String,Integer,Short,Date"%>
<%@ attribute name="operator" required="true" rtexprvalue="false" type="java.lang.String" description="supported operator:LIKE,NOT,GTE,GT,LTE,LT,I18N"%>
<%@ attribute name="style" required="false" rtexprvalue="false" type="java.lang.String" description="style"%>
<%@ attribute name="classes" required="false" description="class"%>
<%@ attribute name="size" required="false" rtexprvalue="false" type="java.lang.String" description="style"%>
<c:set var="attrName">COL@${attrPath}@${datatype}@${operator}</c:set>
<input type="text" id="${attrName}" name="${attrName}" value="${sc==null ? requestScope[attrName] : sc.param[attrName]}" <c:if test="${not empty style}">style="${style}"</c:if> <c:if test="${not empty classes}">class="${classes}"</c:if> <c:if test="${not empty size}">size="${size}"</c:if>/>