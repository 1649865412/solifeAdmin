<%@ include file="/common/taglibs.jsp"%>
<app:pageHeading pageHeadingKey="salesOrderGeoipList.heading" />

<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="create" inputType="button" onclick="return fnDoAdd(this);" />
	<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoMultiDelete(this);" />
	<cartmatic:cartmaticBtn btnType="cancel" inputType="button" onclick="return fnDoUpToParent(this);" />
</content>
<app:ui_leftMenu>
	<form method="post" action="${ctxPath}/order/salesOrderGeoip.html" onsubmit="return false;">
	    <div class="sidebar-left">
			<app:ui_tabs tabsId="left_menu_tabs"/>
		    <div class="tab" id="left_menu_tabs">
			    <ul>
					<li><a href="#glSearchBar"><fmt:message key="yourposition.search"/></a></li>
				</ul>
				<search:searchBox>
					<search:filter />
					<search:input attrPath="s.salesOrderGeoipName" attrNameKey="salesOrderGeoip.salesOrderGeoipName" datatype="String"
						operator="LIKE" classes="form-inputbox" />
					<search:orderby showOrderDirection="true">
						<option value="salesOrderGeoipId">
							<fmt:message key="search.order.by.default" />
						</option>
					</search:orderby>
				</search:searchBox>
			</div>
		</div>
	</form>
</app:ui_leftMenu>
<form class="mainForm" name="salesOrderGeoipListForm" method="post" action="${ctxPath}/order/salesOrderGeoip.html">
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/order/salesOrderGeoip.html?doAction=edit&from=list" scope="page" />
	
		<c:set var="checkAll"><input type="checkbox" name="allbox" onclick="checkAll(this.form)" class="checkbox"/></c:set>
		<display:table name="${salesOrderGeoipList}" cellspacing="0" cellpadding="0" uid="salesOrderGeoipItem"
			class="table-list" export="false" requestURI="">
			<display:column style="width: 3%" title="${checkAll}" media="html">
				<input type="checkbox" name="multiIds" value="${salesOrderGeoipItem.salesOrderGeoipId}" class="checkbox" title="${salesOrderGeoipItem.salesOrderGeoipName}"/>
			</display:column>
			<display:column sortable="false" url="${editURLPath}" paramId="salesOrderGeoipId" paramProperty="salesOrderGeoipId"
				decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="salesOrderGeoip.salesOrderGeoipName">
				${salesOrderGeoipItem.salesOrderGeoipName}
			</display:column>
		    <display:column property="orderNo" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="salesOrderGeoip.orderNo"/>
		    <display:column property="customerIp" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="salesOrderGeoip.customerIp"/>
		    <display:column property="lon" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="salesOrderGeoip.lon"/>
		    <display:column property="lat" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="salesOrderGeoip.lat"/>
		    <display:column property="actionType" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="salesOrderGeoip.actionType"/>
		    <display:column property="createTime" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="salesOrderGeoip.createTime"/>
		</display:table>
		<%@include file="/common/pagingOnlyNew.jsp"%>
	
</form>
<script type="text/javascript">
highlightTableRows("salesOrderGeoipItem");
</script>