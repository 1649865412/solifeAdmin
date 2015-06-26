<%@ include file="/common/taglibs.jsp"%>
<app:pageHeading pageHeadingKey="orderSettlementList.heading" />
<fmt:message key="orderPayment.action.createPayment" var="buttonCreatePayment"/>
<content tag="buttons">
</content>
<app:ui_leftMenu>
	<form method="post" action="${ctxPath}/order/orderSettlement.html" onsubmit="return false;">
	    <div class="sidebar-left">
			<app:ui_tabs tabsId="left_menu_tabs"/>
		    <div class="tab" id="left_menu_tabs">
			    <ul>
					<li><a href="#glSearchBar"><fmt:message key="yourposition.search"/></a></li>
				</ul>
				<search:searchBox>
					<search:input attrPath="s.shipmentNo" attrNameKey="orderShipment.shipmentNo" datatype="String"
						operator="LIKE" classes="form-inputbox" />
					<search:input attrPath="s.orderNo" attrNameKey="salesOrder.orderNo" datatype="String"
						operator="LIKE" classes="form-inputbox" />
					<search:input attrPath="s.trackingNo" attrNameKey="orderShipment.trackingNo" datatype="String"
						operator="LIKE" classes="form-inputbox" />
				</search:searchBox>
			</div>
		</div>
	</form>
</app:ui_leftMenu>
<form class="mainForm" name="orderSettlementListForm" method="post" action="${ctxPath}/order/orderSettlement.html">
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/order/orderSettlement.html?doAction=edit&from=list" scope="page" />
	
		<display:table name="${orderSettlementList}" cellspacing="0" cellpadding="0" uid="orderSettlementItem"
			class="table-list" export="false" requestURI="">
		    <display:column style="width:12%" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderShipment.shipmentNo">
        		<a onclick="fnNewWindow('${ctxPath}/order/window.html?doAction=edit&salesOrderId=${orderSettlementItem.orderId}&showShipmentId=${orderSettlementItem.shipmentId}&tabIndex=1',650,1300);" href="javascript:void(false);" title="<fmt:message key="orderShipment.view"/>">${orderSettlementItem.shipmentNo}</a>
        	</display:column>
		    <display:column style="width:12%" property="carrierName" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderShipment.carrierName"/>
		    <display:column style="width:12%" property="trackingNo" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderShipment.trackingNo"/>
		    <display:column style="width:14%" property="originalTotal" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderShipment.totalAfterTax"/>
		    <display:column style="width:8%" property="settlementAmount" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderSettlement.settlementAmount"/>
		    <display:column style="width:10%" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderSettlement.isComplete">
        		<c:choose>
					<c:when test="${orderSettlementItem.isComplete eq 1}"><fmt:message key="common.message.yes"/></c:when>
					<c:otherwise><fmt:message key="common.message.no"/></c:otherwise>
				</c:choose>
        	</display:column>
		    <display:column style="width:8%" property="addedBy" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderAudit.addedBy"/>
        	<display:column style="text-align:center" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="common.action">
        			<c:if test="${orderSettlementItem.isComplete ne 1}">
        				<a href="${ctxPath}/order/orderSettlement.html?doAction=completeSettlement&orderSettlementId=${orderSettlementItem.orderSettlementId}" title="<fmt:message key="orderSettlement.action.settleDown" />"><fmt:message key="orderSettlement.action.settleDown" /></a>
        			</c:if>
        	</display:column>
		</display:table>
		<%@include file="/common/pagingOnlyNew.jsp"%>
	
</form>