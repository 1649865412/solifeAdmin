<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title><fmt:message key="system.pay.way"/></title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
  </head>
  <body>
  	
		<table class="table-content" width="100%" border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td class="FieldLabel"><span class="fB"><fmt:message key="system.pay.way"/></span></td>
		    <td width="71%" align="right">
				<IMG alt="XPay" src="<c:url value="/pages/system/${paymentMethod.paymentMethodIcon}"/>"/>	</td>
		  </tr>
		  <tr>
		    <td align="left" colspan="2">
				<span class="fB"><fmt:message key="system.pay.shuo"/></span>
				<ul style="padding-left:25px">
					<li><fmt:message key="system.pay.please"/><a target="_blank" href="http://www.xpay.cn" style="color:red;text-decoration: underline"><fmt:message key="system.pay.yitong"/></a><fmt:message key="system.pay.notice"/></li>
					<li><fmt:message key="system.pay.she"/></li>
				</ul>
			</td>
		  </tr>
		</table>
	
				<form class="mainForm" name="configForm" method="post" action="paymentMethod.html">
					<table class="table-content" cellspacing="0" cellpadding="0" width="100%" border="0">
						
						<tr>
							<td class="FieldLabel"><fmt:message key="system.pay.title1"/></td>
							<td>
								<INPUT class="Field400"  name="tid" value="${paymentMethod.configData.tid}"/>
							</td>
						</tr>
						<tr>
							<td class="FieldLabel"><fmt:message key="system.pay.title2"/></td>
							<td>
								<INPUT class="Field400"  name="payKey" value="${paymentMethod.configData.payKey}"/>
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
						  <td>
						  		<input type="hidden" name="paymentMethodId" value="${paymentMethod.paymentMethodId}"/>
								<input type="hidden" name="doAction" value="saveConfigAction"/>
						  		<input class="btn-back" type="submit" value="<fmt:message key="button.save"/>"/>
						  		&nbsp;
								<input class="btn-back" type="button" value="<fmt:message  key="button.cancel"/>" onclick="location.href='<c:url value="/system/paymentMethod.html"/>';">
						  </td>
					  	</tr> 
					</table>
				</form>
				
  </body>
</html>
