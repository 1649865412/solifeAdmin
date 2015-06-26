<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib prefix="search" tagdir="/WEB-INF/tags/search"%>
<app:pageHeading pageHeadingKey="menu.viewShoppingcartItems" />
<app:ui_leftMenu>
	<div class="sidebar-left">
		<form method="post" action="${ctxPath}/customer/shoppingcartItem.html" onsubmit="return false;">
			<app:ui_tabs tabsId="left_menu_tabs" />
			<div class="tab" id="left_menu_tabs">
				<ul>
					<li>
						<a href="#listSelectContent"><fmt:message key="yourposition.search" /> </a>
					</li>
				</ul>
				<div class="content" id="listSelectContent">
					<search:searchBox>
						<search:input attrPath="s.productSku.product.productName" attrNameKey="product.productName" datatype="String" operator="LIKE" classes="form-inputbox" onenter="true"/>
						<search:input attrPath="s.productSku.productSkuCode" attrNameKey="productSku.productSkuCode" datatype="String" operator="EQ" classes="form-inputbox" onenter="true"/>
						<search:orderby showOrderDirection="true">
							<option value="shoppingcartItemId">
								<fmt:message key="search.order.by.default" />
							</option>
						</search:orderby>
					</search:searchBox>
				</div>
			</div>
		</form>
	</div>
</app:ui_leftMenu>
<form class="mainForm" name="shoppingcartItem" method="post" action="${ctxPath}/customer/shoppingcartItem.html">
	<!--listing box start-->
		<display:table name="${shoppingcartItemList}" cellspacing="0" cellpadding="0" uid="shoppingcartItemItem" class="table-list" export="false" requestURI="">
			<display:column style="width: 1%" title="" media="html">
				<input type="checkbox" name="multiIds" value="" class="checkbox" title="" />
			</display:column>
			<display:column sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="shoppingcartItem.addTime">
			${shoppingcartItemItem.addTime}
			</display:column>
			<display:column property="productSku.product.productName" sortable="false" 
				decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="product.productName"/>
			<display:column property="productSku.productSkuCode" sortable="false" 
				decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="productSku.productSkuCode"/>
			<display:column sortable="false" 
				decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="shoppingcartItem.store">
				${shoppingcartItemItem.shoppingcart.store.name}
			</display:column>
			<display:column sortable="false" 
				decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="shoppingcartItem.customer">
				<c:choose>
					<c:when test="${empty shoppingcartItemItem.shoppingcart.customerId}">
						<fmt:message key="shoppingcartItem.anonymous" />
					</c:when>
					<c:otherwise>
						<app:userName userId="${shoppingcartItemItem.shoppingcart.customerId}"/>
					</c:otherwise>
				</c:choose>
			</display:column>
		</display:table>
		<div class="blank10"></div>
		<%@ include file="/common/pagingOnlyNew.jsp"%>
</form>