<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="/common/taglibs.jsp"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<head>
	<TITLE>CTOPay payment gateway setting</TITLE>
</head>
<body id="mine">

	<table class="table-content" cellspacing="0" cellpadding="0" width="100%" border="0">
	  <tr>
		<td class="FieldLabel">CTOPay payment gateway setting</td>
	    <td width="71%" align="right">
			<IMG alt="IPS" src="<c:url value="/pages/system/${paymentMethod.paymentMethodIcon}"/>"/>	</td>
	  </tr>
	  <tr>
	    <td align="left" colspan="2">
			<span class="fB">Description: </span>
			<ul style="padding-left:25px">
				<li>Please Go<a href="http://www.ctopay.com/" target="_blank" style="color:red;text-decoration: underline">to CTOPay</a> to register a merchant account</li>
				<li>set MD5key in merchant console</li>
			</ul>
		</td>
	  </tr>
	</table>

				<form class="mainForm" name="configForm" method="post" action="paymentMethod.html">
					<table class="table-content" cellspacing="0" cellpadding="0" width="100%" border="0">
						<tr>
							<td class="FieldLabel">Merchant No：</td>
							<td>
								<INPUT class="Field400"  name="MerNo" value="${paymentMethod.configData.MerNo}"/>
							</td>
						</tr>
						<tr>
							<td class="FieldLabel">MD5Key：</td>
							<td>
								<input class="Field400"  name="MD5key" value="${paymentMethod.configData.MD5key}"/>
							</td>
						</tr>
						<tr>
						  <td>&nbsp;</td>
						  <td>
						  		<input type="hidden" name="paymentMethodId" value="${paymentMethod.paymentMethodId}"/>
								<input type="hidden" name="doAction" value="saveConfigAction"/>
						  		<input class="btn-back" type="submit"  value="<fmt:message key="button.save"/>" />
						  		&nbsp;
								<input class="btn-back" type="button"  value="<fmt:message key="button.cancel"/>" onclick="location.href='<c:url value="/system/paymentMethod.html"/>';"/>
						  </td>
					  </tr> 
					</table>
				</form>
		
</body>