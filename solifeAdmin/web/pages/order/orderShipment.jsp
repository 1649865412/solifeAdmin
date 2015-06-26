<%@ include file="/common/taglibs.jsp"%>
<app:pageHeading pageHeadingKey="menu.viewOrderShipment" />
<app:ui_leftMenu>
	<form method="post" action="${ctxPath}/inventory/orderShipment.html" onsubmit="return false;">
	    <div class="sidebar-left">
			<app:ui_tabs tabsId="left_menu_tabs"/>
		    <div class="tab" id="left_menu_tabs">
				<ul>
					<li>
						<a href="#listSelectContent"><fmt:message key="yourposition.search" /> </a>
					</li>
				</ul>
				<div class="content" id="listSelectContent">
					<search:searchBox>
						<search:input attrNameKey="salesOrder.orderNo" attrPath="s.salesOrder.orderNo" datatype="String" operator="LIKE"/>
						<search:input attrNameKey="orderShipment.trackingNo" attrPath="s.trackingNo" datatype="String" operator="LIKE"/>
					</search:searchBox>
				</div>
			</div>
		</div>
	</form>
</app:ui_leftMenu>
<form class="mainForm" id="orderPickListForm" name="orderPickListForm" method="post" action="${ctxPath}/inventory/orderShipment.html">
	<!--listing box start-->
	
		<display:table name="${orderShipmentList}" cellspacing="0" cellpadding="0" uid="orderShipment"
			class="table-list" export="false" requestURI="">
			<display:column sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderShipment.shipmentNo">
				<a onclick="NewWindow('${ctxPath}/order/window.html?doAction=edit&salesOrderId=${orderShipment.salesOrder.salesOrderId}&showShipmentId=${orderShipment.orderShipmentId}&tabIndex=1',650,1300);" href="javascript:void(false);" title="<fmt:message key="orderShipment.view"/>">${orderShipment.shipmentNo}</a>
				<c:if test="${not empty orderShipment.trackingNo}"><br><fmt:message key="orderShipment.trackingNo"/>:${orderShipment.trackingNo}</c:if>
			</display:column>
			<display:column sortable="false" headerClass="data-table-title" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderPickList.startTime">
        		<c:if test="${not empty orderShipment.orderPickList}">
        			<a href="${ctxPath}/inventory/orderPickList.html?orderPickListId=${orderShipment.orderPickList.orderPickListId}"><fmt:formatDate value="${orderShipment.orderPickList.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></a>
        		</c:if>
        	</display:column>
		    <display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderShipment.carrierName">
        		${orderShipment.carrierName}<br/>
        		${orderShipment.orderAddress.address1}${emptySpace}${orderShipment.orderAddress.address2}<br/>
        		${orderShipment.orderAddress.section }, ${orderShipment.orderAddress.city }, ${orderShipment.orderAddress.state }&nbsp;${orderShipment.orderAddress.postalcode}<br/>
        		${orderShipment.orderAddress.country }        		
        	</display:column>
		    <display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="salesOrder.customerName">
        		${orderShipment.orderAddress.firstname}${emptySpace}${orderShipment.orderAddress.lastname}
        		<br/>${orderShipment.orderAddress.phoneNumber }
        	</display:column>        	  		
        	<display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="common.action">
        		<span style="padding:4px"></span>
        		<a href="${ctxPath}/inventory/orderPickList.html?doAction=printPackingSlip&orderShipmentId=${orderShipment.orderShipmentId}" target="_blank">查看明细</a><br/>
        		<span style="padding:4px"></span>
        		<a href="javascript:fnPrintReceipt('${orderShipment.orderShipmentId}');"><fmt:message key="orderPickList.action.printShipmentSlip" /></a><br/>
        		<span style="padding:4px"></span>
        		<a href="${ctxPath}/inventory/orderPickList.html?doAction=printEMS&orderShipmentId=${orderShipment.orderShipmentId}&printable=true" target="_blank"><fmt:message key="orderPickList.action.printEMS"/></a><br/>
        		<span style="padding:4px"></span>        		   		
        	</display:column>
		</display:table>
		<div class="blank10"></div>
		<%@ include file="/common/pagingOnlyNew.jsp"%>
	
</form>
<c:set var="ui_tabs" value="true" scope="request"/>