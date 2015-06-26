<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="/common/taglibs.jsp"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<head>
	<TITLE><fmt:message key="system.pay.ips"/></TITLE>
</head>
<body id="mine">
<content tag="heading">Payment Method</content>
<div class="blank8"></div>
<div class="box-content-wrap">
		<div class="box-content">
			<div class="top">
			    <div class="content-title">
			    	RealPay 配置
			    </div>
		    </div>
			<div class="content">
				<form class="mainForm" name="configForm" method="post" action="paymentMethod.html">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
						<tr>
							<td class="FieldLabel"><span class="fB">SiteID</span></td>
							<td>
								<INPUT class="form-inputbox" size="40" name="merNo" value="${paymentMethod.configData.merNo}"/>
							</td>
						</tr>
						<tr>
							<td class="FieldLabel"><span class="fB">MD5Key</span></td>
							<td>
								<input class="form-inputbox" size="40" name="md5key" value="${paymentMethod.configData.md5key}"/>
							</td>
						</tr>
			
						<tr>
						  <td colspan="2">
						  		<input type="hidden" name="paymentMethodId" value="${paymentMethod.paymentMethodId}"/>
								<input type="hidden" name="doAction" value="saveConfigAction"/>
						  		<input class="btn-common" type="submit" size="15" value="<fmt:message key="button.save"/>" />
						  		&nbsp;
								<input class="btn-common" type="button" size="15" value="<fmt:message key="button.cancel"/>" onclick="location.href='${ctxPath}/system/paymentMethod.html';"/>
						  </td>
					  </tr> 
					</table>
				</form>
			</div>
</body>