<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<app:pageHeading pageHeadingKey="taxList.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn inputType="button" btnType="create" onclick="fnDoAdd(this);"/>
	<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoMultiDelete(this);"/>
	<cartmatic:cartmaticBtn btnType="cancel" onclick="location.href='${ctxPath}/system/index.html;'"/>	
</content>
<app:ui_leftMenu>
	<form method="post" action="${ctxPath}/system/tax.html" onsubmit="return false;">
		<div class="sidebar-left">
			<app:ui_tabs tabsId="left_menu_tabs"/>
		    <div class="tab" id="left_menu_tabs">
			    <ul>
					<li><a href="#glSearchBar"><fmt:message key="yourposition.search"/></a></li>
				</ul>
				<search:searchBox>
					<search:filter />
					<search:input attrPath="taxName" attrNameKey="tax.taxName" datatype="String"
						operator="LIKE" classes="form-inputbox" />
					<search:orderby showOrderDirection="true">
						<option value="taxId">
							<fmt:message key="search.order.by.default" />
						</option>
					</search:orderby>
				</search:searchBox>
			</div>
		</div>
<%-- <div id="glSearchBar" style="display:none;" title="<fmt:message key="button.search" />">
    <div class="box-search" id="SearchBarContent">
				<input type="hidden" name="doAction" value="searchTax" />
				<table class="table-search" border="0" cellpadding="0" align="center"
					cellspacing="0">
					<tr>
						<td height="10" class="fB" nowrap>
							<StoreAdmin:label key="tax.taxName" />
						</td>
						<td height="10">
							<input class="Field400" type="text"
								name="COL@t.taxName@String@LIKE"
								value="${param['COL@t.taxName@String@LIKE']}" />
						</td>
					</tr>
					<tr>
						<td height="10" width="20%" class="fB" nowrap>
							<StoreAdmin:label key="tax.taxRegisterNo" />
						</td>
						<td height="10" width="80%">
							<input type="text" class="Field400" 
								name="COL@t.taxRegisterNo@String@LIKE"
								value="${param['COL@t.taxRegisterNo@String@LIKE']}">
						</td>
					</tr>
				</table>
				<input type="hidden" name="SRH@selectEntity" value="t" />
		</div>
	</div>--%>
	</form>
</app:ui_leftMenu>
	
<form class="mainForm" name="taxListForm" method="post" action="${ctxPath}/system/tax.html">
	<c:set var="checkAll"><input type="checkbox" name="allbox" onclick="checkAll(this.form)" class="checkbox"/></c:set>
	<display:table name="${taxList}" cellspacing="0" cellpadding="0"
		uid="taxItem" class="table-list" export="false" requestURI="">
		<display:column style="width: 3%" title="${checkAll}"
			headerClass="data-table-title" media="html">
			<input type="checkbox" name="multiIds" value="${taxItem.taxId}" class="checkbox" title="${taxItem.taxName}"/>
		</display:column>
		<display:column sortable="false" 
			url="/system/tax.html?doAction=edit" paramId="taxId"
			paramProperty="taxId"
			decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator"
			titleKey="tax.taxName">
			${taxItem.taxName}
		</display:column>
		<display:column property="taxRegisterNo" sortable="false"			
			decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator"
			titleKey="tax.taxRegisterNo" />
		<display:setProperty name="paging.banner.item_name" value="tax" />
		<display:setProperty name="paging.banner.items_name" value="taxs" />
	</display:table>
	<%@ include file="/common/pagingOnlyNew.jsp"%>
</form>
<script type="text/javascript">
<!--
highlightTableRows("taxItem");
//-->
</script>

