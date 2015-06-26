<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib prefix="search" tagdir="/WEB-INF/tags/search"%>
<app:pageHeading pageHeadingKey="menu.viewShoppingcartItem.itemStat" />
<app:ui_leftMenu>
	<div class="sidebar-left">
		<form method="post" onsubmit="return false;">
			<app:ui_tabs tabsId="left_menu_tabs" />
			<div class="tab" id="left_menu_tabs">
				<ul>
					<li>
						<a href="#listSelectContent"><fmt:message key="yourposition.search" /> </a>
					</li>
				</ul>
				<div class="content" id="listSelectContent">
					<search:searchBox>
					<div class="title"><fmt:message key="search.filter" /></div>
					<div class="content">
						<select onchange="fnChangeFilter(this.selectedIndex);" id="SRH@filter" name="SRH@filter">
						<option value="statItem"><fmt:message key="menu.viewShoppingcartItem.itemStat"/></option>	
						<option value="statSaved"><fmt:message key="menu.viewShoppingcartItem.savedStat"/></option>
						</select>
					</div>
						<search:input attrPath="s.productSku.product.productName" attrNameKey="product.productName" datatype="String" operator="LIKE" classes="form-inputbox" onenter="true"/>
						<search:input attrPath="s.productSku.productSkuCode" attrNameKey="productSku.productSkuCode" datatype="String" operator="EQ" classes="form-inputbox" onenter="true"/>
						<search:orderby showOrderDirection="true">
							<option value="quantity">
								<fmt:message key="search.order.by.default" />
							</option>
						</search:orderby>
					</search:searchBox>
				</div>
			</div>
		</form>
	</div>
</app:ui_leftMenu>
<form class="mainForm" name="shoppingcartItem" method="post" action="${ctxPath}/customer/shoppingcartItemStat.html">
	<!--listing box start-->
		<display:table name="${shoppingcartItemList}" cellspacing="0" cellpadding="0" uid="shoppingcartItemItem" class="table-list" export="false" requestURI="">
			<display:column style="width: 1%" title="" media="html">
				<input type="checkbox" name="multiIds" value="" class="checkbox" title="" />
			</display:column>
			<display:column property="quantity" sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="shoppingcartItem.quantity">
			</display:column>
			<display:column property="productSku.product.productName" sortable="false" 
				decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="product.productName"/>
			<display:column property="productSku.productSkuCode" sortable="false" 
				decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="productSku.productSkuCode"/>
			
		</display:table>
		<div class="blank10"></div>
		<%@include file="/common/pagingNew.jsp"%>
</form>