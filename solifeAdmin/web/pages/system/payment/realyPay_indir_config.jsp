<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="/common/taglibs.jsp"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<head>
	<TITLE><fmt:message key="system.pay.ips"/></TITLE>
</head>
<body id="mine">
<content tag="heading">Payment Gateways</content>
<div class="blank8"></div>
<div class="box-content-wrap">
		<div class="box-content">
			<div class="top">
			    <div class="content-title">
			    	RealPay 配置(第三方跳转)
			    </div>
		    </div>
			<div class="content">
				<form class="mainForm" name="configForm" method="post" action="paymentMethods.html">
					<table class="table-content" cellspacing="0" cellpadding="0" width="100%" border="0">
						<tr>
							<th width="180px"><span class="fB">SiteID</span></th>
							<td>
								<INPUT class="form-inputbox" size="40" name="merNo" value="${paymentMethod.configData.merNo}"/>
							</td>
						</tr>
						<tr>
							<th><span class="fB">MD5Key</span></th>
							<td>
								<input class="form-inputbox" size="40" name="md5key" value="${paymentMethod.configData.md5key}"/>
							</td>
						</tr>
				<%-- 
						<tr>
							<th><span class="fB"><fmt:message key="system.pay.ipsyes"/></span></th>
							<td>
								<select name="RetType" class="form-inputbox">
									<option value="0" <c:if test="${paymentMethod.configData.RetType==0}">selected</c:if>><fmt:message key="system.pay.noserver"/></option>
									<option value="1" <c:if test="${paymentMethod.configData.RetType==1}">selected</c:if>><fmt:message key="system.pay.yeserver"/></option>
								</select>
							</td>
						</tr>
						<tr>
							<th><span class="fB"><fmt:message key="system.pay.bizhong"/></span></th>
							<td>	
								<select name="Currency_Type" style="width:115px">
									<option value="RMB" <c:if test="${paymentMethod.configData.Currency_Type=='RMB'}">selected</c:if>><fmt:message key="system.pay.ren"/></option>
									<option value="HKD" <c:if test="${paymentMethod.configData.Currency_Type=='HKD'}">selected</c:if>><fmt:message key="system.pay.gang"/></option>
									<option value="ADSL" <c:if test="${paymentMethod.configData.Currency_Type=='ASDL'}">selected</c:if>><fmt:message key="system.pay.adsl"/></option>
								</select>
							</td>
						<tr>
							<th><span class="fB"><fmt:message key="system.pay.language"/></span></th> 
							<td>
								<select name="Lang" style="width:200px" class="form-inputbox">
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
								<select name="testModel" style="width:200px" class="form-inputbox">
									<option value="Y" <c:if test="${paymentMethod.configData.testModel=='Y'}">selected</c:if>><fmt:message key="system.pay.ceshi"/></option>
									<option value="N" <c:if test="${paymentMethod.configData.testModel=='N'}">selected</c:if>><fmt:message key="status.active"/></option>
								</select>
							</td>
						</tr>--%> 
						<tr>
						  <th>&nbsp;</th>
						  <td colspan="2">
						  		<input type="hidden" name="paymentMethodId" value="${paymentMethod.paymentMethodId}"/>
								<input type="hidden" name="doAction" value="saveConfigAction"/>
						  		<input class="btn-common" type="submit" size="15" value="<fmt:message key="button.save"/>" />
						  		&nbsp;
								<input class="btn-common" type="button" size="15" value="<fmt:message key="button.cancel"/>" onclick="location.href='<c:url value="/system/paymentMethods.html"/>';"/>
						  </td>
					  </tr> 
					</table>
				</form>
			</div>
</body>