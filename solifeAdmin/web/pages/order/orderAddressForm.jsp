<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript"	src="${ctxPath}/scripts/cartmatic/common/region.js"></script>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<form method="post" class="mainForm" id="orderAddress${orderShipmentId}" action="${ctxPath}/order/orderAddress.html">
		<input type="hidden" id="salesOrderId" name="salesOrderId" value="${salesOrderId}"/>
		<input type="hidden" name="orderAddressId" value="${orderAddressId}"/> 
		<table cellSpacing="10" cellPadding="0" width="100%" border="0">
		<c:if test="${empty orderShipmentId}">
		<tr>
			<th align="right"><StoreAdmin:label key="salesOrder.invoiceTitle"/></th>
			<td>
				<input type="text" id="invoiceTitle" name="invoiceTitle" value="${invoiceTitle}" class="Field200"/>
			</td>
		</tr>
		</c:if>
		<c:if test="${not empty orderShipmentId}">
	 		<tr>
				<th align="right"><StoreAdmin:label key="orderAddress.firstname"/></th>
				<td>
					<input type="hidden" id="orderShipmentId" name="orderShipmentId" value="${orderShipmentId}"/>
					<input type="text" id="firstname${orderShipmentId}" name="firstname${orderShipmentId}" value="${orderAddress.firstname}" class="Field200"/>
				</td>
			</tr>
			<tr>
				<th align="right"><StoreAdmin:label key="orderAddress.lastname"/></th>
				<td>
					<input type="text" id="lastname${orderShipmentId}" name="lastname${orderShipmentId}" value="${orderAddress.lastname}" class="Field200"/>
				</td>
			</tr>
			<tr>
				<th align="right"><StoreAdmin:label key="orderAddress.phoneNumber"/></th>
				<td>
					<input type="text" id="phoneNumber${orderShipmentId}" name="phoneNumber${orderShipmentId}" value="${orderAddress.phoneNumber}" class="Field200"/>
				</td>
			</tr>
		</c:if>
 		<tr>
			<th align="right"><StoreAdmin:label key="orderAddress.postalcode"/></th>
			<td>
				<input type="text" id="postalcode${orderShipmentId}" name="postalcode${orderShipmentId}" value="${orderAddress.postalcode}" class="Field200"/>
			</td>
		</tr>
 		<tr>
			<th align="right"><StoreAdmin:label key="orderAddress.country"/></th>
			<td>
				<input id="country${orderShipmentId}" name="country${orderShipmentId}" type="hidden" value="${orderAddress.country}"/>
				<select name="countryId${orderShipmentId}" validConf="required" id="countryId${orderShipmentId}" class="Field200" onchange="countryChange(this,'stateId${orderShipmentId}');">
				</select>
			</td>
		</tr>
		<tr>
			<th align="right"><StoreAdmin:label key="orderAddress.state"/></th>
			<td>
				<input id="state${orderShipmentId}" name="state${orderShipmentId}" type="hidden" value="${orderAddress.state}"/>
				<select name="stateId${orderShipmentId}" validConf="required" id="stateId${orderShipmentId}" class="Field200" onchange="stateChange(this)">
				</select>
			</td>
		</tr>
		<tr>
			<th align="right"><StoreAdmin:label key="orderAddress.city"/></th>
			<td>
				<input id="city${orderShipmentId}" name="city${orderShipmentId}" type="text" value="${orderAddress.city}" class="Field200"/>
			</td>
		</tr>
 		<tr>
			<th align="right"><StoreAdmin:label key="orderAddress.address1"/></th>
			<td>
				<input type="text" id="address1${orderShipmentId}" name="address1${orderShipmentId}" value="${orderAddress.address1}" class="Field300" />
			</td>
		</tr>
		<tr>
			<th align="right"><StoreAdmin:label key="orderAddress.address2"/></th>
			<td>
				<input type="text" id="address2${orderShipmentId}" name="address2${orderShipmentId}" value="${orderAddress.address2}" class="Field300"/>
			</td>
		</tr>
  	</table>
</form>
<c:choose>
	<c:when test="${not empty orderShipmentId}">
		<script type="text/javascript">
			applyValidate($("firstname${orderShipmentId}"),"required");
			applyValidate($("lastname${orderShipmentId}"),"required");
			applyValidate($("postalcode${orderShipmentId}"),"maxlength=6");
			applyValidate($("country${orderShipmentId}"),"required");
			applyValidate($("state${orderShipmentId}"),"required");
			applyValidate($("city${orderShipmentId}"),"required");
			applyValidate($("address1${orderShipmentId}"),"required");
		</script>
	</c:when>
	<c:otherwise>
		<script type="text/javascript">
			applyValidate($("invoiceTitle"),"required");
			applyValidate($("postalcode"),"maxlength=6");
			applyValidate($("country"),"required");
			applyValidate($("state"),"required");
			applyValidate($("city"),"required");
			applyValidate($("address1"),"required");
		</script>
	</c:otherwise>
</c:choose>
<script>
countrySelector("countryId${orderShipmentId}","${orderAddress.country}");
stateSelector($j("#countryId${orderShipmentId}").val(),"stateId${orderShipmentId}","${orderAddress.state}");
function countryChange(sel,stateId){
	var countryId=$j("#"+sel.id).val();
	$j("#country"+_orderShipmentId).val($j("#"+sel.id+" option[value="+countryId+"]").text());
	stateSelector(countryId,"stateId${orderShipmentId}");
	$j("#state"+_orderShipmentId).val();
}

function stateChange(sel){
	var stateId=$j("#"+sel.id).val();
	$j("#state"+_orderShipmentId).val($j("#"+sel.id+" option[value="+stateId+"]").text());
}
</script>
