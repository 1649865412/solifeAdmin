<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
	<TITLE>双乾e支付网关配置</TITLE>
</head>
<body>
<content tag="heading">双乾e支付网关配置</content>
<content tag="description">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	  <tr>
		<td width="29%" align="left"><span class="fB">双乾e支付网关配置</span></td>
	    <td width="71%" align="right">
			<IMG alt="99bill" src="<c:url value="/pages/system/${paymentMethod.paymentMethodIcon}"/>"/>
		</td>
	  </tr>
	</table>
</content>
<div class="blank8"></div>
<div class="box-content-wrap">
		<div class="box-content">
		    <div class="top">
			    <div class="content-title">
			    	双乾e支付网关配置--采用iframe方式提交
			    </div>
		    </div>
			<div class="content">
				<form class="mainForm" name="configForm" method="post" action="paymentMethods.html">
					<table class="table-content" cellspacing="0" cellpadding="0" width="100%" border="0">
						<tr>
							<th width="180px"><span class="fB">商户ID</span></th>
							<td>
								<INPUT class="form-inputbox" size="40" name="merNo" value="${paymentMethod.configData.merNo}"/>
							</td>
						</tr>
						<tr> 
							<th><span class="fB">MD5key值</span></th>
							<td>
								<INPUT class="form-inputbox" size="40" name="md5key" value="${paymentMethod.configData.md5key}"/>
							</td>
						</tr>
						<tr>
						  <th>&nbsp;</th>
						  <td colspan="2" align="left">
						  		<input type="hidden" name="paymentMethodId" value="${paymentMethod.paymentMethodId}"/>
								<input type="hidden" name="doAction" value="saveConfigAction"/>
						  		<input class="btn-common" type="submit" value="<fmt:message key="button.save"/>" onclick="save()"/>
						  		&nbsp;
								<input class="btn-common" type="button" value="<fmt:message  key="button.cancel"/>" onclick="location.href='<c:url value="/system/paymentMethods.html"/>';">
						  </td>
					  	</tr> 
					</table>
				</form>
						
			</div>
		</div>
</div>
</body>
</html>
