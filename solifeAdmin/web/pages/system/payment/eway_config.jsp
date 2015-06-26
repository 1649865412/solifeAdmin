<%
	//Desc: eWAY.Shared or eWAY.Xml Payment Configuration
	//Author: chenshangxuan
	//CreateTime:  2006-07-25
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="/common/taglibs.jsp"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
	<TITLE>eWAY Shared Payment Gateway Configuration</TITLE>
</head>
<body>

	<table class="table-content" cellspacing="0" cellpadding="0" width="100%" border="0">
	  <tr>
		<td class="FieldLabel">eWAY Shared Payment Configuration</td>
	    <td width="50%">
			<IMG alt="eWay" src="<c:url value="/pages/system/${paymentMethod.paymentMethodIcon}"/>"/>	</td>
	  </tr>
	  <tr>
	    <td align="left" colspan="2">
			<span class="fB">Note: </span>
			<ul style="padding-left:25px">
				<li>Please get a customer Id from <a target="_blank" href="http://www.eway.com.au" style="color:red;text-decoration: underline">eWay</a></li>
				<li>Fill the information below</li>
			</ul>
		</td>
	  </tr>
	</table>

	    	<form class="mainForm" name="configForm" method="post" action="paymentMethod.html">
				<table class="table-content" cellspacing="0" cellpadding="0" width="100%" border="0">
					<tr>
						<td class="FieldLabel">eWay CustomerId:</td>
						<td>
							<INPUT class="Field400"  name="customerId" value="${paymentMethod.configData.customerId}"/>
						</td>
					</tr>
					<tr> 
						<td class="FieldLabel">Test/Live Mode:</td>
						<td>
							<select name="testModel" class="Field400">
								<option value="Y" <c:if test="${paymentMethod.configData.testModel=='Y'}">selected</c:if>>Test</option>
								<option value="N" <c:if test="${paymentMethod.configData.testModel=='N'}">selected</c:if>>Live</option>
							</select>
						</td>
					</tr>
					<tr>
						  <td>&nbsp;</td>
						  <td align="left">
						  		<input type="hidden" name="paymentMethodId" value="${paymentMethod.paymentMethodId}"/>
								<input type="hidden" name="doAction" value="saveConfigAction"/>
						  		<input class="btn-back" type="submit" value="<fmt:message key="button.save"/>" />
						  		&nbsp;
								<input class="btn-back" type="button" value="<fmt:message  key="button.cancel"/>" onclick="location.href='<c:url value="/system/paymentMethod.html"/>';">
						  </td>
					 </tr>
				</table>
			</form>
	   	

</body>
</html>
