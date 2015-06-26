<%@ include file="/common/taglibs.jsp"%>
<app:pageHeading pageHeadingKey="supplierList.heading" />

<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="create" inputType="button" onclick="return fnDoAdd(this);" />
	<%--
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoMultiSave(this);" />
	<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoMultiDelete(this);" />--%>
	<cartmatic:cartmaticBtn btnType="cancel" inputType="button" onclick="return fnDoUpToParent(this);" />
</content>
<app:ui_leftMenu>
	<form method="post" action="${ctxPath}/supplier/supplier.html" onsubmit="return false;">
	    <div class="sidebar-left">
			<app:ui_tabs tabsId="left_menu_tabs"/>
		    <div class="tab" id="left_menu_tabs">
			    <ul>
					<li><a href="#glSearchBar"><fmt:message key="yourposition.search"/></a></li>
				</ul>
				<search:searchBox>
					<search:filter />
					<search:input attrPath="s.supplierName" attrNameKey="supplier.supplierName" datatype="String"
						operator="LIKE" classes="form-inputbox" />
					<search:input attrPath="s.supplierCode" attrNameKey="supplier.supplierCode" datatype="String"
						operator="LIKE" classes="form-inputbox" />
					<search:orderby showOrderDirection="true">
						<option value="supplierId">
							<fmt:message key="search.order.by.default" />
						</option>
					</search:orderby>
				</search:searchBox>
			</div>
		</div>
	</form>
</app:ui_leftMenu>
<form class="mainForm" name="supplierListForm" method="post" action="${ctxPath}/supplier/supplier.html">
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/supplier/supplier.html?doAction=edit&from=list" scope="page" />
	
		<c:set var="checkAll"><input type="checkbox" name="allbox" onclick="checkAll(this.form)" class="checkbox"/></c:set>
		<display:table name="${supplierList}" cellspacing="0" cellpadding="0" uid="supplierItem"
			class="table-list" export="false" requestURI="">
			<display:column style="width: 3%" title="${checkAll}" media="html">
				<input type="checkbox" name="multiIds" value="${supplierItem.supplierId}" class="checkbox" title="${supplierItem.supplierName}"/>
			</display:column>
			<display:column sortable="false" url="${editURLPath}" paramId="supplierId" paramProperty="supplierId"
				decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="supplier.supplierName">
				${supplierItem.supplierName}
			</display:column>
		    <display:column property="supplierCode" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="supplier.supplierCode"/>
		    <display:column property="createTime" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="supplier.createTime"/>
		    <display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="supplier.status">
        		<fmt:message key="supplier.status_${supplierItem.status}" />
        	</display:column>
		</display:table>
		<%@include file="/common/pagingOnlyNew.jsp"%>
	
</form>
<script type="text/javascript">
highlightTableRows("supplierItem");
</script>