<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@ 
	attribute name="appAdmin" type="com.cartmatic.estore.common.model.system.AppAdmin"%>
<c:forEach items="${appAdmin.roleList}" var="userRole" varStatus="i">
	${userRole.label}<c:if test="${not i.last}">,</c:if>
</c:forEach>&nbsp;