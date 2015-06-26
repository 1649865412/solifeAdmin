<%@ include file="/common/taglibs.jsp"%>
<table class="table-list" width="100%" border="0" cellspacing="0" cellpadding="0" id="tblCarrierList">
<thead>
<tr>
	<th width="15%" class="data-table-title" ><fmt:message key="shippingMethod.shippingMethod"/></th>
	<th width="33%" class="data-table-title" ><fmt:message key="shippingMethod.shippingRegion"/></th>
	<th width="26%" class="data-table-title" ><fmt:message key="shippingMethod.desc"/></th>
	<th width="12%" class="data-table-title" ><fmt:message key="shippingMethod.belong2company"/></th>
	<th width="4%" class="data-table-title" ><fmt:message key="carrier.status"/></th>
	<th width="10%" class="data-table-title" ><fmt:message key="shippingMethod.operation"/></th>
	</tr> 
</thead>
<!-- 列表核心部份table开始 -->
<c:forEach items="${carrierList}" var="carrier">
	<c:forEach items="${carrier.shippingMethods}" var="shippingMethod" varStatus="varStatus">
	<tr>
		<td class="text-left">
		<a href="shippingMethod.html?doAction=edit&shippingMethodId=${shippingMethod.shippingMethodId}">${shippingMethod.shippingMethodName}</a>
		</td>
		<td>
			<c:forEach items="${shippingMethod.shippingRates}" var="shippingRate" varStatus="shippingRateStatus">
				<a href="javascript:;" onclick="dlgEditShippingRate_show({shippingRateId:${shippingRate.shippingRateId}},'GET');">
					${(not empty shippingRate.region.regionName) ? shippingRate.region.regionName : 'x'}
				</a>
				<c:if test="${!shippingRateStatus.last}">|</c:if>
			</c:forEach>
		</td>
		<td>${shippingMethod.shippingMethodDetail}</td>
		<c:if test="${varStatus.first}">
			<td rowspan="${fn:length(carrier.shippingMethods)}">
			<a href="${ctxPath}/system/carrier.html?doAction=edit&carrierId=${carrier.carrierId}&from=list">${carrier.carrierName}</a>
			</td>
		</c:if>
		<td>
			<c:if test="${shippingMethod.status==1}" ><fmt:message key="status.active"/></c:if>
			<c:if test="${shippingMethod.status==2}" ><fmt:message key="status.inactive"/></c:if>
		</td>
		<td>
			<a href="javascript:;" onclick="dlgEditShippingRate_show({shippingMethodId:${shippingMethod.shippingMethodId}},'GET');"><fmt:message key="shippingMethod.addRegion"/></a>
		</td>
	</tr>
	</c:forEach>
</c:forEach>
<!-- 列表核心部份table结束 -->
</table>