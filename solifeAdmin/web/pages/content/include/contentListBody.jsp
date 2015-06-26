<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>
<c:choose>
	<c:when test="${not empty category && category.isLinkCategory == 1}">
		<script type="text/javascript">
			hideContentListBtns();
		</script>
		<div>
		<fmt:message key="contentList.isLinkCategory.alert"/>
		</div>
	</c:when>
	<c:otherwise>
		<form class="mainForm" name="contentListForm" method="post" action="${ctxPath}/content/content.html">
			<!--listing box start-->
			<!--editURLPath is used in TblDecorator-->
			<c:set var="editURLPath" value="/content/content.html?doAction=edit&from=list" scope="page" />
				<c:set var="checkAll"><input type="checkbox" name="allbox" onclick="checkAll(this.form);fnChangeBtnStatus();" style="margin: 0 0 0 0px" /></c:set>
				<display:table name="${contentList}" cellspacing="0" cellpadding="0" uid="contentItem"
					class="table-list" style="table-layout:fixed;" export="false" requestURI="">
					<display:column headerClass="w30" class="w30" title="${checkAll}" media="html">
						<input type="checkbox" name="multiIds" value="${contentItem.contentId}"	class="checkbox" 
						  title="${contentItem.contentTitle}" onchange="fnChangeBtnStatus();"/>
					</display:column>
				    <display:column url="${editURLPath}" 
				    	sortable="false" headerClass="wauto" class="wauto"
		        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="content.contentTitle">
		        		<a href="javascript:void%20${contentItem.contentId}" onclick="fnOpenContentForm(${contentItem.contentId},'${contentItem.contentCode}');">${contentItem.contentTitle}</a>
				   	</display:column>
				   	
				   	<display:column  url="${editURLPath}" 
				    	sortable="false" headerClass="w200" class="w200"
		        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="content.contentCode">
		        		<a href="javascript:void%200" onclick="fnOpenContentForm(${contentItem.contentId},'${contentItem.contentCode}');">${contentItem.contentCode}</a>
				   	</display:column>
				   	
				   	<display:column  url="${editURLPath}" 
				    	sortable="false" headerClass="w60" class="w60"
		        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="content.sortOrder" property="sortOrder">
				   	</display:column>
				    
				    <display:column sortable="false" headerClass="w60" class="w60"
							decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator"
							titleKey="content.state">
					<c:if test="${contentItem.state == 1}">
						<c:set var="stateClass" value="txt-process"></c:set>
					</c:if>
					<c:if test="${contentItem.state == 2}">
						<c:set var="stateClass" value="txt-future"></c:set>
					</c:if>
					<c:if test="${contentItem.state == 3}">
						<c:set var="stateClass" value="txt-preterite"></c:set>
					</c:if>
					<c:if test="${contentItem.state == 0}">
						<c:set var="stateClass" value="txt-disabled"></c:set>
					</c:if>
					<span class="${stateClass}"><fmt:message key="content.state.s${contentItem.state}" /></span>
					
			</display:column>			    
			</display:table>
			<c:if test="${not empty contentList}">
				<%@include file="/common/pagingNew.jsp"%>
			</c:if>
			<%--搜索条件--%>
			<input type="hidden" name="contentTitle" value="${productSearchCriteria.contentTitle}" />
			<input type="hidden" name="categoryId" value="${contentSearchCriteria.categoryId}" />
			<input type="hidden" id="btnSearch" name="btnSearch" value="Search" />
		</form>
	</c:otherwise>
</c:choose>
<script type="text/javascript">
highlightTableRows("couponItem");
</script>