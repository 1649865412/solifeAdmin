<%@ include file="/common/taglibs.jsp"%>
	<form method="post" cssClass="mainForm" id="moveItemForm">
		<input type="hidden" name="orderSkuId" value="${param['orderSkuId']}"/> 
		<div class="blank24"></div>
		<table cellSpacing="0" cellPadding="0" border="0">
 		<tr>
			<td>
				<span style="padding:20px"></span><fmt:message key="moveItem.quantityAvailable"/>(1-${quantityMax})
			</td>
			<td>
				<select id="quantity" name="quantity">
					<c:forEach begin="1" end="${quantityMax}" var="num">
					<option value="${num}">${num}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr><td colspan="2">&nbsp;</td></tr>
 		<tr height="50px">
			<td>
				<span style="padding:20px"></span><input type="radio" id="moveItemType" name="moveItemType" value="move" <c:if test="${moveToMapSize eq 0}">disabled="true"</c:if>/>&nbsp;&nbsp;
				<fmt:message key="moveItem.moveTo"/>
			</td>
			<td>
				<select name="toOrderShipmentId" id="toOrderShipmentId" <c:if test="${moveToMapSize eq 0}">disabled="true"</c:if> style="width:200px">
					<c:choose>
						<c:when test="${moveToMapSize eq 0}">
							<option value="0"><span style="padding:35px"></option>
						</c:when>
						<c:otherwise>
							<c:forEach var="item" items="${moveToMap}">
								<option value="${item.key}">${item.value}</option>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</select>
			</td>
		</tr>
		<tr height="50px">
			<td>
				<span style="padding:20px"></span><input type="radio" name="moveItemType" value="new" <c:if test="${moveToMapSize eq 0}">checked="true"</c:if> class="form-inputbox"/>&nbsp;&nbsp;
				<fmt:message key="moveItem.createNew"/>
			</td>
			<td></td>
		</tr>
  	</table>
</form>
<script type="text/javascript">
 	 if(!$j("#moveItemType").attr("disabled"))
 	 	$j("#moveItemType").attr("checked", "true");
 	 applyValidate($("moveItemType"),"required");
</script>

