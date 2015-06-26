<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="/common/taglibs.jsp"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<head>
	<TITLE><fmt:message key="system.pay.ips"/></TITLE>
</head>
<body id="mine">

	<table class="table-content" width="100%" border="0" cellspacing="0" cellpadding="0">
	  <tr>
		<td class="FieldLabel"><fmt:message key="system.pay.ips"/></td>
	    <td width="71%" align="right">
			<IMG alt="IPS" src="<c:url value="/pages/system/${paymentMethod.paymentMethodIcon}"/>"/>	</td>
	  </tr>
	  <tr>
	    <td align="left" colspan="2">
			<span class="fB"><fmt:message key="system.pay.shuo"/></span>
			<ul style="padding-left:25px">
				<li><fmt:message key="system.pay.please"/><a href="http://www.ips.com.cn" target="_blank" style="color:red;text-decoration: underline"><fmt:message key="system.pay.IPS"/></a><fmt:message key="system.pay.notice"/></li>
				<li><fmt:message key="system.pay.ipstitle1"/></li>
				<li><fmt:message key="system.pay.ipstitle2"/></li>
			</ul>
		</td>
	  </tr>
	</table>

				<form class="mainForm" name="configForm" method="post" action="paymentMethod.html">
					<table class="table-content" cellspacing="0" cellpadding="0" width="100%" border="0">
						<tr>
							<td class="FieldLabel"><fmt:message key="system.pay.saleser"/></td>
							<td>
								<INPUT class="Field400"  name="Mer_Code" value="${paymentMethod.configData.Mer_Code}"/>
							</td>
						</tr>
						<tr>
							<td class="FieldLabel"><fmt:message key="system.pay.ipszhen"/></td>
							<td>
								<input class="Field400"  name="Mer_Cert" value="${paymentMethod.configData.Mer_Cert}"/>
							</td>
						</tr>
				
						<tr>
							<td class="FieldLabel"><fmt:message key="system.pay.ipsyes"/></td>
							<td>
								<select name="RetType" class="Field400">
									<option value="0" <c:if test="${paymentMethod.configData.RetType==0}">selected</c:if>><fmt:message key="system.pay.noserver"/></option>
									<option value="1" <c:if test="${paymentMethod.configData.RetType==1}">selected</c:if>><fmt:message key="system.pay.yeserver"/></option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="FieldLabel"><fmt:message key="system.pay.bizhong"/></td>
							<td>	
								<select name="Currency_Type"  class="Field400">
									<option value="RMB" <c:if test="${paymentMethod.configData.Currency_Type=='RMB'}">selected</c:if>><fmt:message key="system.pay.ren"/></option>
									<option value="HKD" <c:if test="${paymentMethod.configData.Currency_Type=='HKD'}">selected</c:if>><fmt:message key="system.pay.gang"/></option>
									<option value="ADSL" <c:if test="${paymentMethod.configData.Currency_Type=='ASDL'}">selected</c:if>><fmt:message key="system.pay.adsl"/></option>
								</select>
							</td>
						<tr>
							<td class="FieldLabel"><fmt:message key="system.pay.language"/></td> 
							<td>
								<select name="Lang" class="Field400">
									<option value="GB" <c:if test="${paymentMethod.configData.Lang=='GB'}">selected</c:if>><fmt:message key="system.pay.font1"/></option>
									<option value="EN" <c:if test="${paymentMethod.configData.Lang=='EN'}">selected</c:if>><fmt:message key="system.pay.font3"/></option>
									<option value="BIG5" <c:if test="${paymentMethod.configData.Lang=='BIG5'}">selected</c:if>><fmt:message key="system.pay.font2"/></option>
									<option value="JP" <c:if test="${paymentMethod.configData.Lang=='JP'}">selected</c:if>><fmt:message key="system.pay.font4"/></option>
									<option value="FR" <c:if test="${paymentMethod.configData.Lang=='FR'}">selected</c:if>><fmt:message key="system.pay.font5"/></option>
								</select>
							</td>
						</tr>
						<tr> 
							<th><span class="fB"><fmt:message key="system.pay.title3"/></span></th>
							<td>
								<select name="testModel" class="Field400">
									<option value="Y" <c:if test="${paymentMethod.configData.testModel=='Y'}">selected</c:if>><fmt:message key="system.pay.ceshi"/></option>
									<option value="N" <c:if test="${paymentMethod.configData.testModel=='N'}">selected</c:if>><fmt:message key="status.active"/></option>
								</select>
							</td>
						</tr> 
						<tr>
						  <th>&nbsp;</th>
						  <td colspan="2">
						  		<input type="hidden" name="paymentMethodId" value="${paymentMethod.paymentMethodId}"/>
								<input type="hidden" name="doAction" value="saveConfigAction"/>
						  		<input class="btn-back" type="submit" value="<fmt:message key="button.save"/>" />
						  		&nbsp;
								<input class="btn-back" type="button" value="<fmt:message key="button.cancel"/>" onclick="location.href='<c:url value="/system/paymentMethod.html"/>';"/>
						  </td>
					  </tr> 
					</table>
				</form>
			
</body>