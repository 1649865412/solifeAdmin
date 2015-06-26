<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ attribute name="var" type="java.util.HashMap"%>
<c:if test="${not empty var}">
	  (<c:forEach items="${var}" var="pa">
	 	<c:choose>
	 		<c:when test="${fn:indexOf(pa.key,'########')!=-1}"><%//兼容2.1以前的版本的写法 %>
	 			<c:set var="attrNameKey" value="${fn:substringAfter(pa.key,'########')}"/>
			 	<c:set var="optionNameKey" value="${fn:substringAfter(pa.value,'########')}"/>
			    <b>${attrNameKey}</b>:${optionNameKey}&nbsp;
	 		</c:when>
	 		<c:otherwise>
	 			<b>${pa.key} : </b>  ${pa.value}&nbsp;	
	 		</c:otherwise>
	 	</c:choose>
	 </c:forEach>)
</c:if>