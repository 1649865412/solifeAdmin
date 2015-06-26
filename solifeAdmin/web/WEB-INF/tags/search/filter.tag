<%@ tag body-content="empty"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div class="title"><fmt:message key="search.filter" /></div>
<div>
	<select name="SRH@filter" id="SRH@filter" onchange="fnChangeFilter(this.selectedIndex);">
		<c:forEach items="${requestScope.sc.searchFilters}" var="filter">
			<option value="${filter.filterName}">
				<fmt:message key="search.filter.${filter.filterName}" />
			</option>
		</c:forEach>
	</select>
</div>