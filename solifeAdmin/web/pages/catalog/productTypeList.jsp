<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib prefix="search" tagdir="/WEB-INF/tags/search"%>
<%@ taglib prefix="catalog" tagdir="/WEB-INF/tags/catalog"%>
<app:pageHeading pageHeadingKey="productTypeList.heading" />
<content tag="buttons">
<cartmatic:cartmaticBtn btnType="create" inputType="button" onclick="return fnDoAdd(this);" />
<%--<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoMultiSave(this);" />--%>
<cartmatic:cartmaticBtn btnType="cancel" inputType="button" onclick="return fnDoUpToParent(this);" />
</content>
<app:ui_leftMenu>
	<div class="sidebar-left">
		<form method="post" action="${ctxPath}/catalog/productType.html" onsubmit="return false;">
			<app:ui_tabs tabsId="left_menu_tabs" />
			<div class="tab" id="left_menu_tabs">
				<ul>
					<li>
						<a href="#glSearchBar"><fmt:message key="yourposition.search" /> </a>
					</li>
				</ul>
				<search:searchBox>
					<search:filter />
					<search:input attrPath="s.productTypeName" attrNameKey="productType.productTypeName" datatype="String" operator="LIKE" classes="form-inputbox" onenter="true"/>
					<search:orderby showOrderDirection="true">
						<option value="productTypeId">
							<fmt:message key="search.order.by.default" />
						</option>
					</search:orderby>
				</search:searchBox>
			</div>
		</form>
	</div>
</app:ui_leftMenu>
<form class="mainForm" name="productTypeListForm" method="post" action="${ctxPath}/catalog/productType.html">
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/catalog/productType.html?doAction=edit&from=list" scope="page" />
		<c:set var="checkAll">
			<input type="checkbox" name="allbox" onclick="checkAll(this.form)" style="margin: 0 0 0 0px" />
		</c:set>
		<display:table name="${productTypeList}" cellspacing="0" cellpadding="0" uid="productTypeItem" class="table-list" export="false" requestURI="">
			<display:column sortable="false" url="${editURLPath}" paramId="productTypeId" paramProperty="productTypeId" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="productType.productTypeName">
				${productTypeItem.productTypeName}
			</display:column>
			<display:column property="productTypeDescription" sortable="false" headerClass="data-table-title" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="productType.productTypeDescription" />
			<display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="productReview.status">
        		<fmt:message key="productType.status_${productTypeItem.status}" />
        	</display:column>
		</display:table>
		<div class="blank10"></div>
		<%@ include file="/common/pagingOnlyNew.jsp"%>
</form>
<script type="text/javascript">
highlightTableRows("productTypeItem");
</script>