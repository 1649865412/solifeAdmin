<%@ tag body-content="scriptless"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ attribute name="showOrderDirection" required="false" rtexprvalue="false"
	description="width300 width400 width500 width600"%>
<div class="title"><fmt:message key="search.orderby" /></div>
<div>
	<select name="SRH@orderBy" id="SRH@orderBy">
			<jsp:doBody />
		</select>
		<c:if test="${showOrderDirection=='true'}">
			<select name="SRH@orderDirection" id="SRH@orderDirection">
				<option value="desc">
					<fmt:message key="search.order.by.desc" />
				</option>
				<option value="asc">
					<fmt:message key="search.order.by.asc" />
				</option>
			</select>
		</c:if>
</div>