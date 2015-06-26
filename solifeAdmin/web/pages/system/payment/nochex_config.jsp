<%
	//Description: Nochex Payment Gateway Configuration Page
	//CreateTime:  2006-07-25
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="/common/taglibs.jsp"%>
<head>
	<TITLE>NoChex Payment Gateway Configuration</TITLE>
</head>
<body>

	<table class="table-content" width="100%" border="0" cellspacing="0" cellpadding="0">
	  <tr>
		<td class="FieldLabel"><span class="fB">NoChex Configuration</span></td>
	    <td width="79%" align="right">
			<IMG alt="nochex" src="<c:url value="/pages/system/${paymentMethod.paymentMethodIcon}"/>"/>	</td>
	  </tr>
	  <tr>
	    <td align="left" colspan="2">
			<span class="fB">Note: </span>
			<ul style="padding-left:25px">
				<li>Please get a merchant account from <a href="http://www.nochex.com" target="_blank" style="color:red;text-decoration: underline">NoChex</a></li>
				<li>Fill the information below</li>
			</ul>
		</td>
	  </tr>
	</table>

				<form class="mainForm" name="configForm" method="post" action="paymentMethod.html">
					<table class="table-content" cellspacing="0" cellpadding="0" width="100%" border="0">
						<tr>
							<td class="FieldLabel">NOCHEX Account:</td>
							<td>
								<INPUT class="Field400"  name="account" value="${paymentMethod.configData.account}"/>
							</td>
						</tr>
						<tr>
							<td class="FieldLabel">Company Logo URL:</td>
							<td>
								<INPUT class="Field400"  name="logoUrl" value="${paymentMethod.configData.logoUrl}"/>
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
						  <td >
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