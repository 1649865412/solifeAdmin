<%@ include file="/common/taglibs.jsp"%>
<app:pageHeading pageHeadingKey="orderMessageList.heading" />

<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="create" onclick="return fnDoAdd(this);" />
	<%--<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoMultiSave(this);" />--%>
	<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoMultiDelete(this);" />
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoUpToParent(this);" />
</content>
<app:ui_leftMenu>
	<form method="post" action="${ctxPath}/order/orderMessage.html"  onsubmit="return false;">
	    <div class="sidebar-left">
			<app:ui_tabs tabsId="left_menu_tabs"/>
		    <div class="tab" id="left_menu_tabs">
			    <ul>
					<li><a href="#glSearchBar"><fmt:message key="yourposition.search"/></a></li>
				</ul>
				<search:searchBox>
					<search:filter />
					<search:input attrPath="s.orderMessageName" attrNameKey="orderMessage.orderMessageName" datatype="String"
						operator="LIKE" classes="form-inputbox" />
					<search:orderby showOrderDirection="true">
						<option value="orderMessageId">
							<fmt:message key="search.order.by.default" />
						</option>
					</search:orderby>
				</search:searchBox>
			</div>
		</div>
	</form>
</app:ui_leftMenu>
<form class="mainForm" name="orderMessageListForm" method="post" action="${ctxPath}/order/orderMessage.html">
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/order/orderMessage.html?doAction=edit&from=list" scope="page" />
	
		<c:set var="checkAll"><input type="checkbox" name="allbox" onclick="checkAll(this.form)" class="checkbox"/></c:set>
		<display:table name="${orderMessageList}" cellspacing="0" cellpadding="0" uid="orderMessageItem"
			class="table-list" export="false" requestURI="">
			<display:column style="width: 3%" title="${checkAll}" media="html">
				<input type="checkbox" name="multiIds" value="${orderMessageItem.orderMessageId}" class="checkbox" title="${orderMessageItem.orderMessageName}"/>
			</display:column>
			<display:column sortable="false" url="${editURLPath}" paramId="orderMessageId" paramProperty="orderMessageId"
				decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderMessage.orderMessageName">
				${orderMessageItem.orderMessageName}
			</display:column>
		    <display:column property="subject" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderMessage.subject"/>
		    <display:column property="message" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderMessage.message"/>
		    <display:column property="customerId" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderMessage.customerId"/>
		    <display:column property="salesOrderId" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderMessage.salesOrderId"/>
		    <display:column property="status" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderMessage.status"/>
		    <display:column property="createTime" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderMessage.createTime"/>
		    <display:column property="createBy" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderMessage.createBy"/>
		</display:table>
		<%@include file="/common/pagingOnlyNew.jsp"%>
	
</form>
<script type="text/javascript">
highlightTableRows("orderMessageItem");
</script>