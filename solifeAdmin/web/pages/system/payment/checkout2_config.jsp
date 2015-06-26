<%
	//Description: Checkout Version 2 Payment Gateway Configuration
	//CreateTime:  2006-07-26
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="/common/taglibs.jsp"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
	<TITLE>Checkout2 Payment Gateway Configuration</TITLE>
</head>
<body id="mine" >

	<table class="table-content" cellspacing="0" cellpadding="0" width="100%" border="0">
	  <tr>
		<td class="FieldLabel">Checkout 2 Configuration</td>
	    <td >
			<IMG alt="checkout2" src="<c:url value="/pages/system/${paymentMethod.paymentMethodIcon}"/>"/>	</td>
	  </tr>
	  <tr>
	    <td align="left" colspan="2">
			<span class="fB">Note: </span>
			<ul style="padding-left:25px">
				<li>Please get a merchant account from <a href="http://www.2checkout.com/" target="_blank" style="color:red;text-decoration: underline">Check Out</a></li>
				<li>Fill the information below</li>
			</ul>
		</td>
	  </tr>
	</table>

				<form class="mainForm" name="configForm" method="post" action="paymentMethod.html">
					<table class="table-content" cellspacing="0" cellpadding="0" width="100%" border="0">
						<tr>
							<td class="FieldLabel">2checkout vendor Id:</td>
							<td>
								<INPUT class="Field400"  name="vendorId" value="${paymentMethod.configData.vendorId}"/>
							</td>
						</tr>
						<tr>
							<td class="FieldLabel">Secret word:</td>
							<td>
								<INPUT class="Field400"  name="secretword" value="${paymentMethod.configData.secretword}"/>
							</td>
						</tr>
						<tr> 
							<td>Test/Live Mode:</td>
							<td>
								<select name="testModel" class="Field400">
									<option value="Y" <c:if test="${paymentMethod.configData.testModel=='Y'}">selected</c:if>>Test</option>
									<option value="N" <c:if test="${paymentMethod.configData.testModel=='N'}">selected</c:if>>Live</option>
								</select>
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