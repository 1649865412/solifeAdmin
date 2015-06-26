<%@ include file="/common/taglibs.jsp"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
<title><fmt:message key="system.pay.nps"/></title>
</head>
<body>

		<table class="table-content" width="100%" border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td class="FieldLabel"><span class="fB"><fmt:message key="system.pay.lei"/></span></td>
		    <td width="71%" align="right">
				<IMG alt="NPS" src="<c:url value="/pages/system/${paymentMethod.paymentMethodIcon}"/>"/>	</td>
		  </tr>
		  <tr>
		    <td align="left" colspan="2">
				<span class="fB"><fmt:message key="system.pay.shuo"/></span>
				<ul style="padding-left:25px">
					<li><fmt:message key="system.pay.please"/><a href="http://www.nps.cn" target="_blank" style="color:red;text-decoration: underline"><fmt:message key="system.pay.NPS"/></a><fmt:message key="system.pay.notice"/></li>
					<li><fmt:message key="system.pay.npsstart"/></li>
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
								<td class="FieldLabel"><fmt:message key="system.pay.michi"/></td>
								<td>
									<INPUT class="Field400"  name="Mer_Key" value="${paymentMethod.configData.Mer_Key}"/>
									
								</td>
							</tr>
							<tr>
								<td class="FieldLabel"><fmt:message key="system.pay.bizhong"/></td>
								<td>
									<select name="MOCurrency" class="Field400">
										<option value="001" <c:if test="${paymentMethod.configData.MOCurrency=='001'}">selected="selected"</c:if>><fmt:message key="system.pay.ren"/></option>
										<option value="002" <c:if test="${paymentMethod.configData.MOCurrency=='002'}">selected="selected"</c:if>><fmt:message key="system.pay.doller"/></option>
										<option value="003" <c:if test="${paymentMethod.configData.MOCurrency=='003'}">selected="selected"</c:if>><fmt:message key="system.pay.gang"/></option>
									</select>
									
									<fmt:message key="system.pay.zhichi"/>
								</td>
							</tr>
							<tr>
								<td class="FieldLabel"><fmt:message key="system.pay.language"/></td>
								<td>
									<select name="M_Language" class="Field400">
										<option value="01" <c:if test="${paymentMethod.configData.M_Language=='01'}">selected="selected"</c:if>><fmt:message key="system.pay.font1"/></option>
										<option value="02" <c:if test="${paymentMethod.configData.M_Language=='02'}">selected="selected"</c:if>><fmt:message key="system.pay.font2"/></option>
										<option value="03" <c:if test="${paymentMethod.configData.M_Language=='03'}">selected="selected"</c:if>><fmt:message key="system.pay.font3"/></option>
									</select>
								</td>
							</tr>
							<tr> 
								<td class="FieldLabel"><fmt:message key="system.pay.title3"/></td>
								<td>
									<select name="testModel" style="width:200px" class="Field400">
										<option value="Y" <c:if test="${paymentMethod.configData.testModel=='Y'}">selected</c:if>><fmt:message key="system.pay.ceshi"/></option>
										<option value="N" <c:if test="${paymentMethod.configData.testModel=='N'}">selected</c:if>><fmt:message key="status.active"/></option>
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
</html>