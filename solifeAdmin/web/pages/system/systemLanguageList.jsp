<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib prefix="search" tagdir="/WEB-INF/tags/search"%>
<app:pageHeading pageHeadingKey="systemLanguageList.heading" />
<c:set var="checkAll">
	<input type="checkbox" name="allbox" onclick="checkAll(this.form)" style="margin: 0 0 0 0px" />
</c:set>
<content tag="buttons">
<cartmatic:cartmaticBtn btnType="create" onclick="return fnDoAdd(this);" />
<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoMultiSave(this);" />
<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoMultiDelete(this);" />
<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoUpToParent(this);" />
</content>
<form class="mainForm" name="${entityClassName}ListForm" method="post" action="${ctxPath}/system/systemLanguage.html" onsubmit="return false;">
	<search:searchBox>
		<search:filter />
		<search:input attrPath="s.languageNameKey" attrNameKey="systemLanguage.languageNameKey" datatype="String"
			operator="I18N" classes="Field400" />
		<search:input attrPath="s.localeCode" attrNameKey="systemLanguage.localeCode" datatype="String" operator="LIKE"
			class="Field400" />
		<search:orderby showOrderDirection="true">
			<option value="">
				<fmt:message key="search.order.by.default" />
			</option>
			<option value="s.sortOrder">
				<fmt:message key="search.order.by.sortOrder" />
			</option>
			<option value="s.status">
				<fmt:message key="search.order.by.status" />
			</option>
		</search:orderby>
	</search:searchBox>
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/system/systemLanguage.html?doAction=edit&from=list" scope="page" />
	
		<display:table name="${systemLanguageList}" cellspacing="0" cellpadding="0" uid="systemLanguageItem"
			class="table-list" export="false" requestURI="">
			<!--checkall column ycm 2006-04-28-->
			<display:column style="width: 5%" title="${checkAll}" media="html">
				<c:if test="${systemLanguageItem.isDefault != 1}">
					<input type="checkbox" name="multiIds" value="<c:out value="${systemLanguageItem.systemLanguageId}"/>"
						class="checkbox" />
				</c:if>
				&nbsp;
			</display:column>
			<!--checkall column ycm 2006-04-28 end-->
			<display:column sortable="false" url="${editURLPath}" paramId="systemLanguageId" paramProperty="systemLanguageId"
				decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="systemLanguage.languageNameKey">
				<fmt:message key="${systemLanguageItem.languageNameKey}" />
			</display:column>
			<display:column property="localeCode" sortable="false"
				decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="systemLanguage.localeCode" />
			<display:column sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator"
				titleKey="systemLanguage.isDefault">
				${systemLanguageItem.isDefault==1 ? 'Y' : 'N'}
			</display:column>
			<display:column sortable="true" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator"
				titleKey="systemLanguage.status" media="html">
				<input type="checkbox" name="status_${systemLanguageItem.systemLanguageId}" value="1" ${systemLanguageItem.status==1?
					'checked':''}
							onchange="fnToggleMutliIds('${systemLanguageItem.systemLanguageId}');" />
			</display:column>
			<display:column sortable="true" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator"
				titleKey="systemLanguage.status" media="pdf excel xml csv">${systemLanguageItem.status==1 ? 'enabled' : 'disabled'}
			</display:column>
		</display:table>
		<%@include file="/common/pagingOnlyNew.jsp"%>
	
</form>
<script type="text/javascript">
highlightTableRows("systemLanguageItem");
</script>
