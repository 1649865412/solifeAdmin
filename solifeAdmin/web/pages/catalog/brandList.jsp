<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib prefix="search" tagdir="/WEB-INF/tags/search"%>
<app:pageHeading pageHeadingKey="brandList.heading" />
<content tag="buttons">
<cartmatic:cartmaticBtn btnType="create" inputType="button" onclick="return fnDoAdd(this);" />
<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoMultiDelete(this);" />
<cartmatic:cartmaticBtn btnType="cancel" inputType="button" onclick="return fnDoUpToParent(this);" />
</content>
<app:ui_leftMenu>
	<div class="sidebar-left">
		<form method="post" action="${ctxPath}/catalog/brand.html" onsubmit="return false;">
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
						<search:input attrPath="s.brandName" attrNameKey="brand.brandName" datatype="String" operator="LIKE" classes="form-inputbox" onenter="true"/>
						<search:input attrPath="s.brandCode" attrNameKey="brand.brandCode" datatype="String" operator="LIKE" classes="form-inputbox" onenter="true"/>
						<search:orderby showOrderDirection="true">
							<option value="brandId">
								<fmt:message key="search.order.by.default" />
							</option>
						</search:orderby>
					</search:searchBox>
				</div>
			</div>
		</form>
	</div>
</app:ui_leftMenu>
<form class="mainForm" name="brandListForm" method="post" action="${ctxPath}/catalog/brand.html">
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/catalog/brand.html?doAction=edit&from=list" scope="page" />
	
		<c:set var="checkAll">
			<input type="checkbox" name="allbox" onclick="checkAll(this.form)" class="checkbox" />
		</c:set>
		<display:table name="${brandList}" cellspacing="0" cellpadding="0" uid="brandItem" class="table-list" export="false" requestURI="">
			<display:column style="width: 1%" title="${checkAll}" media="html">
				<input type="checkbox" name="multiIds" value="${brandItem.brandId}" class="checkbox" title="${brandItem.brandName}" />
			</display:column>
			<display:column sortable="false" url="${editURLPath}" paramId="brandId" paramProperty="brandId" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="brand.brandName">
				${brandItem.brandName}
			</display:column>
			<display:column property="brandCode" sortable="false" url="${editURLPath}" paramId="brandId" paramProperty="brandId" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="brand.brandCode">
			</display:column>
			<display:column property="sortOrder" sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="brand.sortOrder" />
			<display:column property="website" sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="brand.website" />
			<display:column sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="brand.logo" media="html">
				<cartmatic:img url="${brandItem.logo}" mediaType="other" id="logoImage" height="80" width="80" onLoadHandler="fnResizeImage(this, 180, 60);"></cartmatic:img>
			</display:column>
		</display:table>
		<div class="blank10"></div>
		<%@ include file="/common/pagingOnlyNew.jsp"%>
</form>
<script type="text/javascript">
highlightTableRows("brandItem");
</script>