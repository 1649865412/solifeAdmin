<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<app:pageHeading entityName="${transferRecord.transferRecordName}" entityHeadingKey="transferRecordDetail.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this);" />
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="transferRecord.*" />
<form id="transferRecord" class="mainForm" action="${ctxPath}/order/transferRecord.html" method="post" onsubmit="return validateTransferRecord(this);">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
		<tr>
			<th colspan="2">
				<fmt:message key="transferRecordDetail.heading" /><input type="hidden" name="transferRecordId" value="${transferRecord.transferRecordId}" />
			</th>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="transferRecord.type" />
			</td>
			<td>
				<fmt:message key="transferRecord.type_${transferRecord.type}" />
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="transferRecord.orderNo" />
			</td>
			<td>
				${transferRecord.orderNo}
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="transferRecord.mtcnNo" />
			</td>
			<td>
				${transferRecord.mtcnNo}
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="transferRecord.amount" />
			</td>
			<td>
				${transferRecord.amount}
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="transferRecord.name" />
			</td>
			<td>
				${transferRecord.firstname}${emptySpace}${transferRecord.middlename}${emptySpace}${transferRecord.lastname}
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="transferRecord.address" />
			</td>
			<td>
				${transferRecord.address}
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<fmt:message key="transferRecord.section" />/<fmt:message key="transferRecord.city" />/<fmt:message key="transferRecord.state" />/<fmt:message key="transferRecord.country" />:
			</td>
			<td>
				${transferRecord.section},${transferRecord.city},${transferRecord.state},${transferRecord.country}
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="transferRecord.zip" />:
			</td>
			<td>
				${transferRecord.zip}
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="transferRecord.phone" />:
			</td>
			<td>
				${transferRecord.phone}
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="transferRecord.question" />:
			</td>
			<td>
				${transferRecord.question}
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="transferRecord.answer" />:
			</td>
			<td>
				${transferRecord.answer}
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="transferRecord.status" />:
			</td>
			<td>
				<select name="status" id="status">
					<c:forEach begin="0" end="2" var="var_status">
						<option value="${var_status}" <c:if test="${var_status==transferRecord.status}"> selected="selected"</c:if>><fmt:message key="transferRecord.status_${var_status}" /></option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="transferRecord.createTime" />:
			</td>
			<td>
				<fmt:formatDate value="${transferRecord.createTime}" pattern="yyyy-MM-dd hh:mm:ss"/>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="transferRecord.updateBy" />:
			</td>
			<td>
				<app:userName userId="${transferRecord.updateBy}" noFormat="true"></app:userName>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="transferRecord.updateTime" />:
			</td>
			<td>
				<fmt:formatDate value="${transferRecord.updateTime}" pattern="yyyy-MM-dd hh:mm:ss"/>
			</td>
		</tr>
	</table>
</form>
<v:javascript formName="transferRecord" staticJavascript="false" />