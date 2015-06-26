
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<html>
<head>
	<TITLE><fmt:message key="system.pay.yun"/></TITLE>
</head>
<body>
	<table class="table-content" cellspacing="0" cellpadding="0" width="100%" border="0">
	  <tr>
		<tclass="FieldLabel"><fmt:message key="system.pay.yun"/></td>
	    <td width="71%" align="right">
			<IMG alt="<fmt:message key="system.pay.yunw"/>" src="<c:url value="/pages/system/${paymentMethod.paymentMethodIcon}"/>"/>	</td>
	  </tr>
	  <tr>
	    <td align="left" colspan="2">
			<span class="fB"><fmt:message key="system.pay.shuo"/></span>
			<ul style="padding-left:25px">
				<li><fmt:message key="system.pay.please"/><a target="_blank" href="http://www.cncard.net" style="color:red;text-decoration: underline"><fmt:message key="system.pay.yunw"/></a><fmt:message key="system.pay.kait"/></li>
				<li><fmt:message key="system.pay.go"/><a target="_blank" href="https://www.cncard.net/cnpayment/admin/" style="color:red;text-decoration: underline"><fmt:message key="system.pay.later"/></a><fmt:message key="system.pay.hoiu"/>
				<li><fmt:message key="system.pay.guan"/></li>
				<li><fmt:message key="system.pay.furl"/>${appConfig.store.siteUrl}/system/payment/cncard_response.html,<fmt:message key="system.pay.diao"/></li>
			</ul>
		</td>
	  </tr>
	</table>

			<form class="mainForm" name="configForm" method="post" action="paymentMethod.html">
				<table class="table-content" cellspacing="0" cellpadding="0" width="100%" border="0">
					<tr>
						<td class="FieldLabel"><fmt:message key="system.pay.saleser"/></td>
						<td>
							<INPUT class="Field400"  name="c_mid" value="${paymentMethod.configData.c_mid}"/>
						</td>
					</tr>
					<tr> 
						<td class="FieldLabel"><fmt:message key="system.pay.michi"/></td>
						<td>
							<INPUT class="Field400"  name="c_pass" value="${paymentMethod.configData.c_pass}"/>
						</td>
					</tr>
					<tr> 
						<td class="FieldLabel"><fmt:message key="system.pay.bizhong"/></td>
						<td>
							<select name="c_moneytype" class="Field400">
								<option value="0" <c:if test="${paymentMethod.configData.c_moneytype==0}">selected</c:if>><fmt:message key="system.pay.ren"/></option>
								<option value="1" <c:if test="${paymentMethod.configData.c_moneytype==1}">selected</c:if>><fmt:message key="system.pay.doller"/></option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="FieldLabel"><fmt:message key="system.pay.ffurl"/></th>
						<td>
							<INPUT class="Field400"  name="c_returl" value="${paymentMethod.configData.c_returl}"/>
						</td>
					</tr> 
					<tr> 
						<td class="FieldLabel"><fmt:message key="system.pay.ce"/></td>
						<td>
							<select name="c_language"  class="Field400">
								<option value="0" <c:if test="${paymentMethod.configData.c_language==0}">selected</c:if>><fmt:message key="system.pay.china"/></option>
								<option value="1" <c:if test="${paymentMethod.configData.c_language==1}">selected</c:if>><fmt:message key="system.pay.font3"/></option>
							</select>
						</td>
					</tr>
					<tr> 
						<td class="FieldLabel"><fmt:message key="system.pay.title3"/></td>
						<td>
							<select name="testModel" class="Field400">
								<option value="Y" <c:if test="${paymentMethod.configData.testModel=='Y'}">selected</c:if>><fmt:message key="system.pay.ceshi"/></option>
								<option value="N" <c:if test="${paymentMethod.configData.testModel=='N'}">selected</c:if>><fmt:message key="status.active"/></option>
							</select>
						</td>
					</tr> 
					<tr>
					  <td>&nbsp;</td>
					  <td align="left" height="32px">
					  		<input type="hidden" name="paymentMethodId" value="${paymentMethod.paymentMethodId}"/>
							<input type="hidden" name="doAction" value="saveConfigAction"/>
					  		<input class="btn-back" type="submit"  value="<fmt:message key="button.save"/>" onclick="save()"/>
					  		&nbsp;
							<input class="btn-back" type="button"  value="<fmt:message  key="button.cancel"/>" onclick="location.href='<c:url value="/system/paymentMethod.html"/>';">
					  </td>
				  	</tr> 
				</table>
			</form>			
</body>
</html>
