<%@ include file="/common/taglibs.jsp"%>
<app:pageHeading pageHeadingKey="orderPromotionList.heading" />

<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="create" onclick="return fnDoAdd(this);" />
	<%--<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoMultiSave(this);" />--%>
	<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoMultiDelete(this);" />
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoUpToParent(this);" />
</content>
<app:ui_leftMenu>
	<form method="post" action="${ctxPath}/order/orderPromotion.html" onsubmit="return false;">
	    <div class="sidebar-left">
			<app:ui_tabs tabsId="left_menu_tabs"/>
		    <div class="tab" id="left_menu_tabs">
			    <ul>
					<li><a href="#glSearchBar"><fmt:message key="yourposition.search"/></a></li>
				</ul>
				<search:searchBox>
					<search:filter />
					<search:input attrPath="s.orderPromotionName" attrNameKey="orderPromotion.orderPromotionName" datatype="String"
						operator="LIKE" classes="form-inputbox" />
					<search:orderby showOrderDirection="true">
						<option value="orderPromotionId">
							<fmt:message key="search.order.by.default" />
						</option>
					</search:orderby>
				</search:searchBox>
			</div>
		</div>
	</form>
</app:ui_leftMenu>
<form class="mainForm" name="orderPromotionListForm" method="post" action="${ctxPath}/order/orderPromotion.html">
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/order/orderPromotion.html?doAction=edit&from=list" scope="page" />
	
		<c:set var="checkAll"><input type="checkbox" name="allbox" onclick="checkAll(this.form)" style="margin: 0 0 0 0px" /></c:set>
		<display:table name="${orderPromotionList}" cellspacing="0" cellpadding="0" uid="orderPromotionItem"
			class="table-list" export="false" requestURI="">
			<display:column style="width: 3%" title="${checkAll}" media="html">
				<input type="checkbox" name="multiIds" value="${orderPromotionItem.orderPromotionId}" class="checkbox" title="${orderPromotionItem.orderPromotionName}"/>
			</display:column>
			<display:column sortable="false" url="${editURLPath}" paramId="orderPromotionId" paramProperty="orderPromotionId"
				decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderPromotion.orderPromotionName">
				${orderPromotionItem.orderPromotionName}
			</display:column>
		    <display:column property="salesOrderId" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderPromotion.salesOrderId"/>
		    <display:column property="promoRuleId" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderPromotion.promoRuleId"/>
		    <display:column property="couponNo" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderPromotion.couponNo"/>
		    <display:column property="promotionName" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderPromotion.promotionName"/>
		</display:table>
		<%@include file="/common/pagingOnlyNew.jsp"%>
	
</form>
<script type="text/javascript">
highlightTableRows("orderPromotionItem");
</script>