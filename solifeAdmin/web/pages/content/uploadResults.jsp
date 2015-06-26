<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="seperator" value=""/>
<script type="text/javascript">
<c:forEach items="${uploadedFiles}" var="filePath">
	<c:if test="${not empty onCompleteHandler}">
	parent.${onCompleteHandler}("${filePath}","${fileInputId}");
	</c:if>
	<c:if test="${not empty fileInputId}">
	parent.document.getElementById("${fileInputId}").value="${filePath}";
	</c:if>
	setTimeout('alert("<fmt:message key="upload.success"/>" + "${filePath}")',0);
</c:forEach>
<c:if test="${not empty uploadMsg}">
	setTimeout('alert("<fmt:message key="error.upload.${uploadMsg}"/>")',0);
</c:if>
</script>