<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
	<TITLE>gleepay网关配置</TITLE>
</head>
<body id="mine">
<content tag="buttons">
<cartmatic:cartmaticBtn btnType="save" onclick="$j('#configForm').submit();return false;" />
<cartmatic:cartmaticBtn btnType="cancel" onclick="location.href='${ctxPath}/system/paymentMethod.html';" />
</content>
<body>
<form class="mainForm" id="configForm" name="configForm" method="post" action="paymentMethod.html">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
	<tr>
		<th colspan="2">
			gleepay支付网关配置
	  		<input type="hidden" name="paymentMethodId" value="${paymentMethod.paymentMethodId}"/>
			<input type="hidden" name="doAction" value="saveConfigAction"/>
		</th>
	</tr>
	<tr>
		<td class="FieldLabel">
			商户号
		</td>
		<td>
			<INPUT class="form-inputbox" size="40" name="merNo" value="${paymentMethod.configData.merNo}"/>
		</td>
   	</tr>
   	<tr>
		<td class="FieldLabel">
			网关接入号
		</td>
		<td>
			<INPUT class="form-inputbox" size="40" name="gatewayNo" value="${paymentMethod.configData.gatewayNo}"/>
		</td>
   	</tr>
   	<tr>
		<td class="FieldLabel">
			key值
		</td>
		<td>
			<INPUT class="form-inputbox" size="40" name="signkey" value="${paymentMethod.configData.signkey}"/>
		</td>
   	</tr>
   	<tr>
		<td class="FieldLabel">
			POST URL
		</td>
		<td>
			<INPUT class="form-inputbox" size="40" name="postUrl" value="${paymentMethod.configData.postUrl}"/>
			<br/>
			https://pay.gleepay.com/Interface
			<br/>
			(TEST)https://pay.gleepay.com/TestInterface
		</td>
   	</tr>
   	
</table>
</form>
</body>
</html>