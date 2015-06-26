<%@ include file="/common/taglibs.jsp"%>
<app:pageHeading pageHeadingKey="orderReturnSkuList.heading" />

<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="create" onclick="return fnDoAdd(this);" />
	<%--<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoMultiSave(this);" />--%>
	<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoMultiDelete(this);" />
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoUpToParent(this);" />
</content>
<app:ui_leftMenu>
	<form method="post" action="${ctxPath}/order/orderReturnSku.html" onsubmit="return false;">
	    <div class="sidebar-left">
			<app:ui_tabs tabsId="left_menu_tabs"/>
		    <div class="tab" id="left_menu_tabs">
			    <ul>
					<li><a href="#glSearchBar"><fmt:message key="yourposition.search"/></a></li>
				</ul>
				<search:searchBox>
					<search:filter />
					<search:input attrPath="s.orderReturnSkuName" attrNameKey="orderReturnSku.orderReturnSkuName" datatype="String"
						operator="LIKE" classes="form-inputbox" />
					<search:orderby showOrderDirection="true">
						<option value="orderReturnSkuId">
							<fmt:message key="search.order.by.default" />
						</option>
					</search:orderby>
				</search:searchBox>
			</div>
		</div>
	</form>
</app:ui_leftMenu>
<form class="mainForm" name="orderReturnSkuListForm" method="post" action="${ctxPath}/order/orderReturnSku.html">
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/order/orderReturnSku.html?doAction=edit&from=list" scope="page" />
	
		<c:set var="checkAll"><input type="checkbox" name="allbox" onclick="checkAll(this.form)" style="margin: 0 0 0 0px" /></c:set>
		<display:table name="${orderReturnSkuList}" cellspacing="0" cellpadding="0" uid="orderReturnSkuItem"
			class="table-list" export="false" requestURI="">
			<display:column style="width: 3%" title="${checkAll}" media="html">
				<input type="checkbox" name="multiIds" value="${orderReturnSkuItem.orderReturnSkuId}" class="checkbox" title="${orderReturnSkuItem.orderReturnSkuName}"/>
			</display:column>
			<display:column sortable="false" url="${editURLPath}" paramId="orderReturnSkuId" paramProperty="orderReturnSkuId"
				decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderReturnSku.orderReturnSkuName">
				${orderReturnSkuItem.orderReturnSkuName}
			</display:column>
		    <display:column property="orderReturnId" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderReturnSku.orderReturnId"/>
		    <display:column property="orderSkuId" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderReturnSku.orderSkuId"/>
		    <display:column property="quantity" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderReturnSku.quantity"/>
		    <display:column property="returnAmount" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderReturnSku.returnAmount"/>
		    <display:column property="receivedQuantity" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderReturnSku.receivedQuantity"/>
		    <display:column property="reasonType" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderReturnSku.reasonType"/>
		</display:table>
		<%@include file="/common/pagingOnlyNew.jsp"%>
	
</form>
<script type="text/javascript">
highlightTableRows("orderReturnSkuItem");
</script>