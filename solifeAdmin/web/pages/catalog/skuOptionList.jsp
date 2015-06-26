<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib prefix="search" tagdir="/WEB-INF/tags/search"%>
<app:pageHeading pageHeadingKey="skuOptionList.heading" />

<content tag="buttons">
<cartmatic:cartmaticBtn btnType="create" inputType="button" onclick="return fnDoAdd(this);" />
<%--<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoMultiSave(this);" />--%>
<cartmatic:cartmaticBtn btnType="cancel" inputType="button" onclick="return fnDoUpToParent(this);" />
</content>
<app:ui_leftMenu>
	<div class="sidebar-left">
		<form method="post" action="${ctxPath}/catalog/skuOption.html" onsubmit="return false;">
			<app:ui_tabs tabsId="left_menu_tabs" />
			<div class="tab" id="left_menu_tabs">
				<ul>
					<li>
						<a href="#listSelectContent"><fmt:message key="yourposition.search" /> </a>
					</li>
				</ul>
				<div class="content" id="listSelectContent">
					<search:searchBox>
						<search:filter />
						<search:input attrPath="s.skuOptionName" attrNameKey="skuOption.skuOptionName" datatype="String" operator="LIKE" classes="form-inputbox" onenter="true"/>
						<search:input attrPath="s.skuOptionCode" attrNameKey="skuOption.skuOptionCode" datatype="String" operator="LIKE" classes="form-inputbox" onenter="true"/>
						<search:orderby showOrderDirection="true">
							<option value="skuOptionId">
								<fmt:message key="search.order.by.default" />
							</option>
						</search:orderby>
					</search:searchBox>
				</div>
			</div>
		</form>
	</div>
</app:ui_leftMenu>
<form name="skuOptionListForm" class="mainForm" method="post" action="${ctxPath}/catalog/skuOption.html">
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/catalog/skuOption.html?doAction=edit&from=list" scope="page" />
	
		<display:table name="${skuOptionList}" cellspacing="0" cellpadding="0" uid="skuOptionItem" class="table-list" export="false" requestURI="">
			<display:column sortable="false" url="${editURLPath}" paramId="skuOptionId" paramProperty="skuOptionId" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="skuOption.skuOptionName">
				${skuOptionItem.skuOptionName}
			</display:column>
			<display:column property="skuOptionCode" sortable="false" url="${editURLPath}" paramId="skuOptionId" paramProperty="skuOptionId" headerClass="data-table-title" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="skuOption.skuOptionCode" />
			<display:column sortable="false" headerClass="data-table-title" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="skuOption.status">
				<fmt:message key="skuOption.status_${skuOptionItem.status}" />
			</display:column>
		</display:table>
		<div class="blank10"></div>
		<%@ include file="/common/pagingOnlyNew.jsp"%>
</form>
<script type="text/javascript">
highlightTableRows("skuOptionItem");
</script>