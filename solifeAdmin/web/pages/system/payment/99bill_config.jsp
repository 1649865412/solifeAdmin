<%
	//Description:99Bill payment gateway configuration
	//Author:csx
	//CreateTime: 2006-08-16
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
	<TITLE><fmt:message key="system.pay.kuai"/></TITLE>
</head>
<body>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
	  <tr>
		<td class="FieldLabel"><fmt:message key="system.pay.kuai"/></td>
	    <td width="71%" align="right">
			<IMG alt="99bill" src="<c:url value="/pages/system/${paymentMethod.paymentMethodIcon}"/>"/></td>
	  </tr>
	  <tr>
	    <td colspan="2">
			<span class="fB">Note: </span>
			<ul style="padding-left:25px">
				<li><fmt:message key="system.pay.please"/><a target="_blank" href="http://www.99bill.com" style="color:red;text-decoration: underline"><fmt:message key="system.pay.zkuai"/></a><fmt:message key="system.pay.kait"/></li>
				<li><fmt:message key="system.pay.guan"/></li>
				<li><fmt:message key="system.pay.furl"/>${appConfig.store.siteUrl}/system/payment/99bill_response.html</li>
			</ul>
		</td>
	  </tr>
	</table>
	<form class="mainForm" name="configForm" method="post" action="paymentMethod.html">
		<table class="table-content" cellspacing="0" cellpadding="0" width="100%" border="0">
						<tr>
							<td class="FieldLabel">外卡账户</td>
							<td>
								<INPUT class="Field400"  name="merchantAcctId" value="${paymentMethod.configData.merchantAcctId}"/>
							</td>
						</tr>
						<tr> 
							<td class="FieldLabel">termID</th>
							<td>
								<INPUT class="Field400"  name="termId" value="${paymentMethod.configData.termId}"/>
							</td>
						</tr>
						<tr> 
							<td class="FieldLabel">密码</th>
							<td>
								<INPUT class="Field400"  name="merchantAcctPassword" value="${paymentMethod.configData.merchantAcctPassword}"/>
							</td>
						</tr>
						<tr>
						  <td>&nbsp;</td>
						  <td align="left">
						  		<input type="hidden" name="paymentMethodId" value="${paymentMethod.paymentMethodId}"/>
								<input type="hidden" name="doAction" value="saveConfigAction"/>
						  		<input class="btn-back" type="submit" value="<fmt:message key="button.save"/>" onclick="save()"/>
						  		&nbsp;
								<input class="btn-back" type="button" value="<fmt:message  key="button.cancel"/>" onclick="location.href='<c:url value="/system/paymentMethod.html"/>';">
						  </td>
					  	</tr> 
					</table>
		</form>
</body>
</html>
