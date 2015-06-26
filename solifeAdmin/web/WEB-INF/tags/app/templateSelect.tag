
<%@tag import="com.cartmatic.estore.Constants"%>
<%@tag import="com.cartmatic.estore.common.util.FileUtil"%>
<%@tag import="java.util.List"%>
<%@tag import="java.util.ArrayList"%>
<%@tag import="org.apache.commons.lang.StringUtils"%>
<%@tag import="java.io.FileFilter"%>
<%@tag import="java.io.File"%>
<%@tag import="com.cartmatic.estore.common.helper.ConfigUtil"%>
<%@ tag pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ attribute name="name" required="true" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="value" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="classes" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="templateList" required="true" rtexprvalue="true" type="java.util.List" description="jsp还是velocity?"%>
<select name="${name}" id="${name}" class="${classes}">
    <option value="" ></option>
	<c:forEach var="templateName" items="${templateList}">
		<option value="${templateName}" <c:if test="${templateName==value}">selected="selected"</c:if>>
			${templateName}
		</option>
	</c:forEach>
</select>