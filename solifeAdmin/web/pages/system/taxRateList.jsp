<%@ include file="/common/taglibs.jsp"%>
<app:pageHeading pageHeadingKey="taxRateList.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="create" inputType="button" onclick="return fnDoAdd(this);" />
	<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoMultiDelete(this);" />
	<cartmatic:cartmaticBtn btnType="cancel" inputType="button" onclick="return fnDoUpToParent(this);" />
</content>
<app:ui_leftMenu>
	<div class="sidebar-left">
	    <form method="post" action="${ctxPath}/system/taxRate.html" onsubmit="return false;">
			<app:ui_tabs tabsId="left_menu_tabs"/>
		    <div class="tab" id="left_menu_tabs">
			    <ul>
					<li><a href="#listSelectContent"><fmt:message key="yourposition.search"/></a></li>
				</ul>
				<div class="content" id="listSelectContent">
					<search:searchBox>
						<search:filter />
						<search:input attrPath="s.tax.taxName" attrNameKey="taxRate.taxId" datatype="String"
							operator="LIKE" classes="form-inputbox" />
						<search:orderby showOrderDirection="true">
							<option value="taxRateId">
								<fmt:message key="search.order.by.default" />
							</option>
						</search:orderby>
					</search:searchBox>
				</div>
			</div>
		</form>
	</div>
</app:ui_leftMenu>
<form class="mainForm" name="taxRateListForm" method="post" action="${ctxPath}/system/taxRate.html">
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/system/taxRate.html?doAction=edit&from=list" scope="page" />
		<c:set var="checkAll"><input type="checkbox" name="allbox" onclick="checkAll(this.form)" class="checkbox"/></c:set>
		<display:table name="${taxRateList}" cellspacing="0" cellpadding="0"
			uid="taxRateItem" class="table-list" 
			export="false" requestURI="" >
			<display:column title="${checkAll}"  media="html">
			    <input  type="checkbox" name="multiIds" value="<c:out value="${taxRateItem.taxRateId}"/>" title="${taxRateItem.productType.productTypeName}"/>
			</display:column>
		    <display:column sortable="false" 
		        decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="taxRate.taxId">
				${taxRateItem.tax.taxName}
				</display:column>	
		    <display:column sortable="false" 
		        decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="taxRate.productTypeId">${taxRateItem.productType.productTypeName}</display:column>
		
		    <display:column sortable="false" 
		        decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="taxRate.regionId">
		        &nbsp;
				<c:if test="${not empty taxRateItem.region }">
					${taxRateItem.region.regionName}
				</c:if>
				</display:column>
		    <display:column sortable="false" 
		        decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="taxRate.rateValue">${taxRateItem.rateValue}%</display:column>
		    <display:column sortable="false" 
		        url="${editURLPath}" paramId="taxRateId" paramProperty="taxRateId"
		        decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="taxRate.edit"><fmt:message key="taxRate.edit"/></display:column>
		    <display:setProperty name="paging.banner.item_name" value="taxRate"/>
		    <display:setProperty name="paging.banner.items_name" value="taxRates"/>
		</display:table>
		<%@include file="/common/pagingOnlyNew.jsp"%>
</form>
<script type="text/javascript">
highlightTableRows("taxRateItem");
</script>