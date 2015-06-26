<%@ include file="/common/taglibs.jsp"%>
	<form class="mainForm" id="createPickListForm" name="createPickListForm" method="post" action="${ctxPath}/order/orderPickList.html">
	<div class="table-list">
		<c:set var="checkAll"><input type="checkbox" name="allbox" onclick="checkAll(this.form)" style="margin: 0 0 0 0px" /></c:set>
		<display:table name="${availableOrderShipments}" cellspacing="0" cellpadding="0" uid="orderShipment" style="border-collapse:collapse;width:100%"
			export="false">
			<display:column style="width: 3%" title="${checkAll}" media="html">
				<input type="checkbox" name="multiIds" value="${orderShipment.orderShipmentId}_${orderShipment.version}"	class="checkbox" title="${orderShipment.orderShipmentName}"/>
			</display:column>
			<display:column style="width: 22%" sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderShipment.shipmentNo">
				${orderShipment.shipmentNo}
			</display:column>
		    <display:column style="width: 18%" property="carrierName" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderShipment.carrierName"/>
		    <display:column style="width: 20%" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="salesOrder.customerName">
        		${orderShipment.customerFirstname}${emptySpace}${orderShipment.customerLastname}
        	</display:column>
		    <display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="common.message.updateTime">
        		<fmt:formatDate value="${orderShipment.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
        	</display:column>
		</display:table>
	</div>
</form>
