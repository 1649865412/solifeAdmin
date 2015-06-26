<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="inputType" %>
<%--EL内必须去掉所有空格(一行写),否则ie会存在空格--%>
<c:choose><c:when test="${inputType eq 0}"><fmt:message key="attributeOld.inputType.variationOption"/></c:when><c:when test="${inputType eq 1}"><fmt:message key="attributeOld.inputType.input"/></c:when><c:when test="${inputType eq 2}"><fmt:message key="attributeOld.inputType.mulInput"/></c:when><c:when test="${inputType eq 3}"><fmt:message key="attributeOld.inputType.list"/></c:when><c:otherwise>other</c:otherwise></c:choose>