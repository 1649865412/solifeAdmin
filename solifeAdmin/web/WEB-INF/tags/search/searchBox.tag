<%@ tag body-content="scriptless"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 
<div id="glSearchBar">
	 <%--这个input重要，不能被删掉，它用于防止在ff下表单自动提交 --%>
	<input style="display:none">
	<jsp:doBody />
	<div class="blank10"/></div>
	<input type="submit" id="SearchButton" name="SearchButton" onclick="doSearchHandler();" value="<fmt:message key="button.search"/>" class="btn-search"/>
	<input type="RESET" id="SearchReset" name="SearchReset" value="<fmt:message key="button.clear"/>" class="btn-search"/>
</div>
<script type="text/javascript">
	if ($("SRH@orderBy")) $("SRH@orderBy").value="${sc.param['SRH@orderBy']}";
	if ($("SRH@orderDirection")) $("SRH@orderDirection").value="${sc.param['SRH@orderDirection']}";
	<c:forEach items="${requestScope.sc.searchFilters}" var="filter">filterConditions.push("${filter.allow}");</c:forEach>
	if ($("SRH@filter")) {
		$("SRH@filter").value="${sc.param['SRH@filter']}";
		fnChangeFilter($("SRH@filter").selectedIndex);
	}
</script>
