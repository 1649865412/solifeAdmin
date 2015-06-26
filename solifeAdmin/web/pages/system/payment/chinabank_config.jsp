<%
	//Description:ChinaBank payment gateway configuration
	//Author:csx
	//CreateTime: 2006-08-16
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
	<TITLE><fmt:message key="system.pay.wy"/></TITLE>
</head>
<body>
		<table class="table-content" cellspacing="0" cellpadding="0" width="100%" border="0">
		  <tr>
			<td class="FieldLabel"><span class="fB"><fmt:message key="system.pay.wy"/></span></td>
		    <td width="71%" align="right">
				<IMG alt="chinabank" src="<c:url value="/pages/system/${paymentMethod.paymentMethodIcon}"/>"/>	</td>
		  </tr>
		  <tr>
		    <td align="left" colspan="2">
				<span class="fB">Note: </span>
				<ul style="padding-left:25px">
					<li><fmt:message key="system.pay.please"/><a target="_blank" href="http://www.chinabank.com.cn" style="color:red;text-decoration: underline"><fmt:message key="system.pay.wyj"/></a><fmt:message key="system.pay.kait"/></li>
					<li><fmt:message key="system.pay.guan"/></li>
					<li><fmt:message key="system.pay.furl"/>${appConfig.store.siteUrl}/system/payment/chinabank_response.html </li>
				</ul>
			</td>
		  </tr>
		</table>

				<form class="mainForm" name="configForm" method="post" action="paymentMethod.html">
					<table class="table-content" cellspacing="0" cellpadding="0" width="100%" border="0">
						<tr>
							<td class="FieldLabel"><span class="fB"><fmt:message key="system.pay.saleser"/></span></td>
							<td>
								<INPUT class="Field400"  name="v_mid" value="${paymentMethod.configData.v_mid}"/>
							</td>
						</tr>
						<tr> 
							<td class="FieldLabel"><fmt:message key="system.pay.md5"/></td>
							<td>
								<INPUT class="Field400"  name="key" value="${paymentMethod.configData.key}"/>
							</td>
						</tr>
						<tr> 
							<td class="FieldLabel"><fmt:message key="system.pay.bizhong"/></td>
							<td>
								<select name="v_moneytype"  class="Field400">
									<option value="CNY" <c:if test="${paymentMethod.configData.v_moneytype=='CNY'}">selected</c:if>><fmt:message key="system.pay.ren"/></option>
									<option value="USD" <c:if test="${paymentMethod.configData.v_moneytype=='USD'}">selected</c:if>><fmt:message key="system.pay.doller"/></option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="FieldLabel"><fmt:message key="system.pay.ffurl"/></td>
							<td>
								<INPUT class="Field400"  name="v_url" value="${paymentMethod.configData.v_url}"/>
							</td>
						</tr> 
						<tr>
						  <td>&nbsp;</td>
						  <td  align="left">
						  		<input type="hidden" name="paymentMethodId" value="${paymentMethod.paymentMethodId}"/>
								<input type="hidden" name="doAction" value="saveConfigAction"/>
						  		<input class="btn-back" type="submit" value="<fmt:message key="button.save"/>" onclick="save()"/>
						  		&nbsp;
								<input class="btn-back" type="button" value="<fmt:message  key="button.cancel"/>" onclick="location.href='<c:url value="/system/paymentMethod.html"/>';">
						  </td>
					  	</tr> 
					</table>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
