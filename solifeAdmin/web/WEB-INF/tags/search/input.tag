<%@ tag body-content="empty"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="attrPath" required="true" rtexprvalue="false" type="java.lang.String" description="attribute filter on"%>
<%@ attribute name="attrNameKey" required="true" rtexprvalue="false" type="java.lang.String" description="name key of attribute"%>
<%@ attribute name="datatype" required="true" rtexprvalue="false" type="java.lang.String" description="supported datatype:String,Integer,Short,Date"%>
<%@ attribute name="operator" required="true" rtexprvalue="false" type="java.lang.String" description="supported operator:EQ,LIKE,NOT,GTE,GT,LTE,LT,I18N"%>
<%@ attribute name="style" required="false" rtexprvalue="false" type="java.lang.String" description="style"%>
<%@ attribute name="classes" required="false" description="class"%>
<%@ attribute name="size" required="false" rtexprvalue="false" type="java.lang.String" description="style"%>
<%@ attribute name="validConf" required="false" type="java.lang.String" description="Validation configurations."%>
<%@ attribute name="onenter" required="false" type="java.lang.String" description="是否回车触发搜索事件,默认是true"%>
<c:set var="attrName">COL@${attrPath}@${datatype}@${operator}</c:set>
<c:if test="${empty validConf}">
	<c:if test="${datatype=='Integer'}"><c:set var="validConf">integer</c:set></c:if>
	<c:if test="${datatype=='Short'}"><c:set var="validConf">short</c:set></c:if>
</c:if>
<div class="title"><fmt:message key="${attrNameKey}"/></div>
<div>
	<input type="text" id="${attrName}" name="${attrName}" value="${sc==null ? requestScope[attrName] : sc.param[attrName]}" <c:if test="${not empty style}">style="${style}"</c:if> <c:if test="${not empty classes}">class="${classes}"</c:if> <c:if test="${not empty size}">size="${size}"</c:if><c:if test="${not empty validConf}"> validConf="${validConf}" onblur="validateEventHandler();"</c:if>/>
</div>