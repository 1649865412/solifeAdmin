<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="system" tagdir="/WEB-INF/tags/system"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib prefix="sales" tagdir="/WEB-INF/tags/sales"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>
<html>
	<head>
		<title>订单明细</title>
		<style type="text/css">
		#wrap,.notPrint {
			width: 600px;
			margin: 0 auto;
		}
		.wrap-print{padding:20px;text-align:center; ;}
		.table-list,#table-list2{border-left:1px solid #CDCDCD;border-top:1px solid #CDCDCD;margin-top:20px;font-size:16px}
		.table-list th{border-bottom:1px solid #CDCDCD;font-size:16px}
		#table-list2 td{height:26px;text-align:left; padding-left:20px;}
		#table-list2 td.left-title{text-align:right; padding-right:20px; font-weight:bold;}
		</style>
	</head>
	<body>
		<div id="wrap">
			<table class="table-list" id="table-list2" cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<td class="left-title">
						Order No.:
					</td>
					<td>
						${orderShipment.salesOrder.orderNo}
					</td>
				</tr>
				<tr>
					<td class="left-title">
						Shipping Address:
					</td>
					<td>
							${orderShipment.orderAddress.firstname }&nbsp;${orderShipment.orderAddress.lastname }
							<br/>
							${orderShipment.orderAddress.address1 }
							<br/>
							<c:if test="${not empty orderShipment.orderAddress.address2 }">
							${orderShipment.orderAddress.address2 }<br/>
							</c:if>
							${orderShipment.orderAddress.section }, ${orderShipment.orderAddress.city }, ${orderShipment.orderAddress.state }&nbsp;${orderShipment.orderAddress.postalcode}
							<br/>
							${orderShipment.orderAddress.country }
							<br />
							${orderShipment.orderAddress.phoneNumber }
							<br />
					</td>
				</tr>
				<tr>
					<td class="left-title">
						客户备注:
					</td>
					<td>
						<div style="width:400px;white-space: normal;text-overflow: visible;overflow: hidden;">
							<c:forEach items="${orderShipment.salesOrder.orderAudits}" var="orderAudit">
								<c:if test="${orderAudit.transactionType!='SYSTEM'}">
									<fmt:formatDate value="${orderAudit.createTime}" pattern="yyyy-MM-dd HH:mm"/>&nbsp;${orderAudit.addedBy}:<br/>${orderAudit.detail}<br/>
								</c:if>
							</c:forEach>
						</div>
					</td>
				</tr>				
			</table>
		
			<display:table name="${orderShipment.orderSkus}" cellspacing="0"
				cellpadding="0" uid="orderSku" class="table-list" export="false" 
				requestURI="">
				<display:column title="NO." headerClass="data-table-title">
					${orderSku_rowNum}<b>.</b> </a>
				</display:column>
				<display:column sortable="false" headerClass="data-table-title"
					titleKey="product.productName">
					<div style="width:99%; text-align:left;">
						产品名称: 
						${orderSku.productName}
						<br/>
						产品编码: 
						${orderSku.productSkuCode}
						<br/>
						产品数量: 
						${orderSku.quantity}
						<br/>
						<c:if test="${not empty orderSku.accessories}">附加属性:<product:showAccessories accessories="${orderSku.accessories}"/><br/></c:if>
						<c:if test="${not empty orderSku.productSku.product.extra1}">ChineseItems：${orderSku.productSku.product.extra1}</c:if>
					</div>
				</display:column>
				<display:column sortable="false" headerClass="data-table-title"
					titleKey="productSku.image">
					<cartmatic:img url="${orderSku.productSku.image}" id="productImage" mediaType="product" size="c" width="130" height="130"></cartmatic:img>
				</display:column>
			</display:table>
		</div>	
	</body>
</html>
