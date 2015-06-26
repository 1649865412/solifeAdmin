<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
	<head>
		<title>Paypal Gateway Parameters Settings</title>
	</head>
	<body>
		<content tag="buttons">
			<fmt:message key="common.message.confirmSaveThis" var="confirmSaveThisMsg"/>
			<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoAction($j('form.mainForm').get(0),'saveConfigAction','${confirmSaveThisMsg} ${paymentMethod.paymentMethodName}');" />
			<%-- 
		    <c:if test="${supplier.supplierId!=null}">
			</c:if>
			--%>
			<cartmatic:cartmaticBtn btnType="cancel" onclick="location.href='${ctxPath}/system/paymentMethod.html';" />
		</content>
		<form class="mainForm" name="configForm" method="post" action="paymentMethod.html">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
			<tr>
				<th colspan="2">
					Paypal Gateway Parameters Settings
					<input type="hidden" name="paymentMethodId" value="${paymentMethod.paymentMethodId}"/>
					<input type="hidden" name="doAction" value="saveConfigAction"/>
				</th>
			</tr>
			<tr>
				<td class="FieldLabel">
					图标:
				</td>
				<td>
					<IMG alt="paypal" src="<c:url value="/pages/system/${paymentMethod.paymentMethodIcon}"/>"/>
				</td>
             </tr>
             <tr>
				<td class="FieldLabel">
					Note:
				</td>
				<td>
					<ul>
						<li>Please get a new account from <a href="http://www.paypal.com.cn" target="_blank" style="color:red;text-decoration: underline">Paypal</a></li>
						<li>Please fill the form below accord with the paypal account.</li>
					</ul>
				</td>
             </tr>
             <tr>
				<td class="FieldLabel">
					Merchant Email:
				</td>
				<td>
					<INPUT class="Field400" name="business" value="${paymentMethod.configData.business}"/>
				</td>
             </tr>
             <tr>
				<td class="FieldLabel">
					Currency:
				</td>
				<td>
					<select class="Field400" name="currency_code">
						<option value="USD" <c:if test="${paymentMethod.configData.currency_code=='USD'}">selected</c:if>>U.S. Dollars (USD)</option>
						<option value="CAD" <c:if test="${paymentMethod.configData.currency_code=='CAD'}">selected</c:if>>Canadian Dollars (CAD)</option>
						<option value="EUR" <c:if test="${paymentMethod.configData.currency_code=='EUR'}">selected</c:if>>Euros (EUR)</option>
						<option value="GBP" <c:if test="${paymentMethod.configData.currency_code=='GBP'}">selected</c:if>>Pounds Sterling (GBP)</option>
						<option value="JPY" <c:if test="${paymentMethod.configData.currency_code=='JPY'}">selected</c:if>>Yen (JPY)</option>
						<option value="AUD" <c:if test="${paymentMethod.configData.currency_code=='AUD'}">selected</c:if>>Australian Dollars (AUD)</option>
					</select>
				</td>
             </tr>
             <tr>
				<td class="FieldLabel">
					Test/Live Mode:
				</td>
				<td>
					<select name="testModel" class="Field400">
						<option value="Y" <c:if test="${paymentMethod.configData.testModel=='Y'}">selected</c:if>>Test</option>
						<option value="N" <c:if test="${paymentMethod.configData.testModel=='N'}">selected</c:if>>Live</option>
					</select>
				</td>
             </tr>
   	     </table>
   	     </form>
	</body>
</hmtl>