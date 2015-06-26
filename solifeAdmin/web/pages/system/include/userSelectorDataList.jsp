<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>
<!-- ========== 列表内容开始 ========= -->
<display:table name="${userList}" cellspacing="0" cellpadding="0" uid="userItem" class="table-list" export="false" requestURI="">
	<c:if test="${param.multiSelect}">
	<display:column style="width: 1%"  media="html">
		<input type="checkbox" name="multiIds" id="sel_ch_${param.id}_${userItem.id}" value="${userItem.id}" class="checkbox" title="${userItem.username}" onclick="fuSelectUser${param.id}(${userItem.id},this)"/>
	</display:column>
	</c:if>
	<display:column sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="appAdmin.username">
		<a href="javascript:void%200" ondblclick='fuSelectUser${param.id}(${userItem.id})'>${userItem.username}</a>
	</display:column>
	<display:column sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="appAdmin.email">
		<a href="javascript:void%200" ondblclick='fuSelectUser${param.id}(${userItem.id})'>${userItem.email}</a>
	</display:column>
	<display:column sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="appAdmin.firstname">
		<a href="javascript:void%200" ondblclick='fuSelectUser${param.id}(${userItem.id})'>${userItem.firstname}</a>
	</display:column>
	<display:column sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="appAdmin.lastname">
		<a href="javascript:void%200" ondblclick='fuSelectUser${param.id}(${userItem.id})'>${userItem.lastname}</a>
	</display:column>
</display:table>
<input type="hidden" name="multiSelect" value="${param.multiSelect}">
<input type="hidden" name="id" value="${param.id}">
<div style="display:none">
	<c:forEach items="${userList}" var="userItem">
	<span id="jsonDataList_${param.id}_${userItem.id}">${userItem.jsonObject}</span>
	</c:forEach>
</div>
<!-- ========== 列表内容结束 ========= -->
<div class="blank10"></div>
<c:if test="${not empty userList}">
	<!-- ========== 分页开始 ========= -->
	<div class="box-paging">
		<%@include file="/common/pagingOnlyNew.jsp"%>
	</div>
	<!-- ========== 分页结束 ========= -->
</c:if>