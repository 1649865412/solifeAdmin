<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<app:pageHeading entityName="${inventoryAudit.inventoryAuditName}"
	entityHeadingKey="inventoryAuditDetail.heading" />
<content tag="buttons">
<cartmatic:cartmaticBtn btnType="cancel"
	onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="inventoryAudit.*" />
	<form:form cssClass="mainForm" method="post" id="inventoryAudit"
				commandName="inventoryAudit" action="">
				<input type="hidden" name="inventoryAuditId"
					value="${inventoryAudit.inventoryAuditId}" />
				<table class="table-content" cellSpacing="0" cellPadding="0"
					width="100%" border="0">
					<tr>
						<td class="FieldLabel">
							<StoreAdmin:label key="productSku.productSkuCode" />
						</td>
						<td>
							<span>${inventoryAudit.productSku.productSkuCode}</span>
						</td>
					</tr>
					<tr>
						<td class="FieldLabel">
							<StoreAdmin:label key="inventoryAudit.eventType" />
						</td>
						<td>
							<span><fmt:message
									key="inventoryAudit.eventType_${inventoryAudit.eventType}" />
							</span>
						</td>
					</tr>
					<tr>
						<td class="FieldLabel">
							<StoreAdmin:label key="inventoryAudit.quantity" />
						</td>
						<td>
							<span><c:if
									test="${inventoryAudit.eventType==1&&inventoryAudit.quantity>0}">+</c:if>${inventoryAudit.quantity}
							</span>
						</td>
					</tr>
					<tr>
						<td class="FieldLabel">
							<StoreAdmin:label key="inventoryAudit.title.quantityOnHand" />
						</td>
						<td>
							<span>${inventoryAudit.quantityOnHand} </span>
						</td>
					</tr>
					<tr>
						<td class="FieldLabel">
							<StoreAdmin:label key="inventoryAudit.title.allocatedQuantity" />
						</td>
						<td>
							<span>${inventoryAudit.allocatedQuantity} </span>
						</td>
					</tr>
					<tr>
						<td class="FieldLabel">
							<StoreAdmin:label key="inventoryAudit.reason" />
						</td>
						<td>
							<span>${inventoryAudit.reason} </span>
						</td>
					</tr>
					<tr>
						<td class="FieldLabel">
							<StoreAdmin:label key="inventoryAudit.orderId" />
						</td>
						<td>
							<span>${inventoryAudit.salesOrder.orderNo} </span>
						</td>
					</tr>
					<tr>
						<td class="FieldLabel">
							<StoreAdmin:label key="inventoryAudit.eventHandler" />
						</td>
						<td>
							<span>${inventoryAudit.eventHandler} </span>
						</td>
					</tr>
					<tr>
						<td class="FieldLabel">
							<StoreAdmin:label key="inventoryAudit.eventHandler" />
						</td>
						<td>
							<span>${inventoryAudit.eventHandler} </span>
						</td>
					</tr>
					<tr>
						<td class="FieldLabel">
							<StoreAdmin:label key="inventoryAudit.comment" />
						</td>
						<td>
							<span>${inventoryAudit.comment} </span>
						</td>
					</tr>
					<tr>
						<td class="FieldLabel">
							<StoreAdmin:label key="inventoryAudit.createTime" />
						</td>
						<td>
							<span><fmt:formatDate
									value="${inventoryAudit.createTime}"
									pattern="yyyy-MM-dd HH:mm:ss" /> </span>
						</td>
					</tr>


				</table>
	</form:form>
		


<v:javascript formName="inventoryAudit" staticJavascript="false" />
<script type="text/javascript">
    document.forms["inventoryAudit"].elements["inventoryId"].focus();
</script>
