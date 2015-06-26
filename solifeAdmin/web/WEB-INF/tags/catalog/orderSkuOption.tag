<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@ 
	taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %><%@ 
	taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%@ 
	attribute name="displaySkuOptions" required="true" type="java.lang.String" %>
<c:if test="${not empty displaySkuOptions}">
<c:forEach var="item" items='${fn:split(displaySkuOptions,"###")}'>
   ${item}<br/>
</c:forEach>
</c:if>