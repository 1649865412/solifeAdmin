<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="system" tagdir="/WEB-INF/tags/system"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib prefix="sales" tagdir="/WEB-INF/tags/sales"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/order"%>
<html>
	<head>
		<title>order receipt</title>
	</head>
	<body>
		<c:if test="${not empty orderShipment}">
			<div class="blank24"></div>
			<h3>
				Your Order# is ${orderShipment.salesOrder.orderNo }
			</h3>
			<div class="indent10">
				Placed on
				<fmt:formatDate pattern="MM/dd/yyyy hh:mm:ss"
					value="${orderShipment.salesOrder.createTime}" />
			</div>
			<div class="blank24"></div>
			<div class="left halfwidth" style="margin-right: 20px;">
				<h4>
					Billing Information:
				</h4>
				<div style="padding-left: 8px;">
					${orderShipment.salesOrder.orderAddress.firstname}&nbsp;${orderShipment.salesOrder.orderAddress.lastname }
					<br />
					${orderShipment.salesOrder.orderAddress.address1 }
					<br />
					<c:if test="${not empty orderShipment.salesOrder.orderAddress.address2 }">
					${orderShipment.salesOrder.orderAddress.address2 }
					<br />
					</c:if>
					${orderShipment.salesOrder.orderAddress.section }, ${orderShipment.salesOrder.orderAddress.city }, ${orderShipment.salesOrder.orderAddress.state}
					&nbsp;${orderShipment.salesOrder.orderAddress.postalcode }
					<br />
					${orderShipment.salesOrder.orderAddress.country }
				</div>
			</div>
			<div class="blank10"></div>
			<div class="right halfwidth">
				<h4>
					Shipped to:
				</h4>
				<div style="padding-left: 8px;">
					${orderShipment.orderAddress.firstname}&nbsp;${ship.orderAddress.lastname}
					<br />
					${orderShipment.orderAddress.address1 }
					<br />
					<c:if test="${not empty orderShipment.orderAddress.address2 }">
					${orderShipment.orderAddress.address2 }
					<br />
					</c:if>
					${orderShipment.orderAddress.section },
					${orderShipment.orderAddress.city },
					${orderShipment.orderAddress.state
					}&nbsp;${orderShipment.orderAddress.postalcode}
					<br />
					${orderShipment.orderAddress.country }
					<br />
					${orderShipment.orderAddress.phoneNumber }
				</div>
			</div>
			<div class="blank10"></div>
			<h4>Items:</h4>
			<table width="550" border="0" cellspacing="0" cellpadding="0"
				class="table-review-total" align="left">
					<c:forEach items="${orderShipment.orderSkus}" var="sku">
						<tr>
							<td style="padding-left: 10px;">
								${sku.productName} 
								<br />
								ItemNo:${sku.productSkuCode }<br />
								<product:orderSkuOption displaySkuOptions="${orderSku.displaySkuOptions}"/>
								<product:showAccessories accessories="${orderSku.accessories}"/>
							</td>							
							<td align="center" valign="top" nowrap="nowrap">
								&nbsp;${sku.quantity} item(s)&nbsp;
							</td>
							<td class="order-class" valign="top" nowrap="nowrap">
								<system:CurrencyForRate value="${sku.subTotalAmount}" />
							</td>
						</tr>
					</c:forEach>
					<c:set var="sum_shippingCost"
						value="${orderShipment.shippingCost}" />
					<c:set var="sum_wrapUnitPrice"
						value="${orderShipment.wrapUnitPrice}" />
					<c:set var="sum_itemTax" value="${ship.itemTax+sum_itemTax}" />
					<c:set var="sum_discountAmount"
						value="${orderShipment.discountAmount}" />					
				<tr>
					<th colspan="3" height="20"></th>
				</tr>
				<tr style="font-weight:bold;">
					<td colspan="2" class="title" align="right">
						Items Total : 
					</td>
					<td class="order-class">
						<system:CurrencyForRate value="${orderShipment.itemSubTotal}" />
					</td>
				</tr>
				<c:if test="${sum_shippingCost>0}">
					<tr style="font-weight:bold;">
						<td colspan="2" class="title" align="right">
							Shipping Charge : 
						</td>
						<td class="order-class">
							<system:CurrencyForRate value="${sum_shippingCost}" />
						</td>
					</tr>
				</c:if>
				<c:if test="${sum_wrapUnitPrice>0}">
					<tr style="font-weight:bold;">
						<td colspan="2" class="title" align="right">
							Wrap : 
						</td>
						<td class="order-class">
							<system:CurrencyForRate value="${sum_wrapUnitPrice }" />
						</td>
					</tr>
				</c:if>
				<c:if test="${sum_itemTax>0}">
					<tr style="font-weight:bold;">
						<%-- 税是永远都显示的，只是多数为0  --%>
						<td colspan="2" class="title" align="right">
							Tax : 
						</td>
						<td class="order-class">
							<system:CurrencyForRate value="${sum_itemTax}" />
						</td>
					</tr>
				</c:if>
				<c:if test="${sum_discountAmount>0}">
					<tr style="font-weight:bold;">
						<td colspan="2" class="title" align="right">
							Saved : 
						</td>
						<td class="order-class">
							-
							<system:CurrencyForRate value="${sum_discountAmount }" />
						</td>
					</tr>
				</c:if>	
				<tr style="font-weight:bold;">
					<td colspan="2" class="title" align="right">
						Gross Total :
					</td>
					<td class="order-class">
						<system:CurrencyForRate value="${orderShipment.total}" />
					</td>
				</tr>			
			</table>
			<div class="blank10"></div>
		</c:if>
	</body>
</html>