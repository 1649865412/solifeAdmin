<%@ include file="/common/taglibs.jsp"%>
<app:pageHeading pageHeadingKey="currencyList.heading" />

<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="create" inputType="button" onclick="return fnDoAdd(this);" />
	<%--<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoMultiSave(this);" />--%>
	<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoMultiDelete(this);" />
	<cartmatic:cartmaticBtn btnType="cancel" inputType="button" onclick="return fnDoUpToParent(this);" />
</content>
<app:ui_leftMenu>
	<form method="post" action="${ctxPath}/system/currency.html" onsubmit="return false;">
	    <div class="sidebar-left">
			<app:ui_tabs tabsId="left_menu_tabs"/>
		    <div class="tab" id="left_menu_tabs">
			    <ul>
					<li><a href="#glSearchBar"><fmt:message key="yourposition.search"/></a></li>
				</ul>
				<search:searchBox>
					<search:filter />
					<search:input attrPath="s.currencyName" attrNameKey="currency.currencyName" datatype="String"
						operator="LIKE" classes="form-inputbox" />
					<search:orderby showOrderDirection="true">
						<option value="currencyId">
							<fmt:message key="search.order.by.default" />
						</option>
					</search:orderby>
				</search:searchBox>
			</div>
		</div>
	</form>
</app:ui_leftMenu>
<form class="mainForm" name="currencyListForm" method="post" action="${ctxPath}/system/currency.html">
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/system/currency.html?doAction=edit&from=list" scope="page" />
	
		<c:set var="checkAll"><input type="checkbox" name="allbox" onclick="checkAll(this.form)" class="checkbox"/></c:set>
		<display:table name="${currencyList}" cellspacing="0" cellpadding="0" uid="currencyItem"
			class="table-list" export="false" requestURI="">
			<display:column style="width: 3%" title="${checkAll}" media="html">
				<input type="checkbox" name="multiIds" value="${currencyItem.currencyId}" class="checkbox" title="${currencyItem.currencyName}"/>
			</display:column>
			<display:column sortable="false" url="${editURLPath}" paramId="currencyId" paramProperty="currencyId"
				decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="currency.currencyName">
				${currencyItem.currencyName}
			</display:column>
		    <display:column property="currencyCode" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="currency.currencyCode"/>
		    <display:column property="currencySymbol" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="currency.currencySymbol"/>
		    <display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="currency.isDefault">
        		<fmt:message key="common.message.isDefault${currencyItem.isDefault}"/>
        	</display:column>
		    <display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="currency.status">
        		<fmt:message key="common.message.status${currencyItem.status}"/>
        	</display:column>
		    <display:column property="sortOrder" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="currency.sortOrder"/>
		    <display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="currency.exchangeRate">
        		${currencyItem.exchangeRate}
        	</display:column>
		</display:table>
		<%@include file="/common/pagingOnlyNew.jsp"%>
	
</form>
<script type="text/javascript">
highlightTableRows("currencyItem");
</script>