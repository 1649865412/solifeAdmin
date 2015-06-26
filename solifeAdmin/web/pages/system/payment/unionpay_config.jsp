<%
	//Description:ChinaBank payment gateway configuration
	//Author:csx
	//CreateTime: 2006-08-16
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
	<TITLE><fmt:message key="system.pay.wy"/></TITLE>
</head>
<body>
		<table class="table-content" cellspacing="0" cellpadding="0" width="100%" border="0">
		  <tr>
			<td class="FieldLabel"><span class="fB"><fmt:message key="system.pay.wy"/></span></td>
		    <td width="71%" align="right">
				<IMG alt="chinabank" src="<c:url value="/pages/system/${paymentMethod.paymentMethodIcon}"/>"/>	</td>
		  </tr>
		  <tr>
		    <td align="left" colspan="2">
				<span class="fB">Note: </span>
				<ul style="padding-left:25px">
					<li><fmt:message key="system.pay.please"/><a target="_blank" href="http://www.chinabank.com.cn" style="color:red;text-decoration: underline"><fmt:message key="system.pay.wyj"/></a><fmt:message key="system.pay.kait"/></li>
					<li><fmt:message key="system.pay.guan"/></li>
					<li><fmt:message key="system.pay.furl"/>${appConfig.store.siteUrl}/system/payment/boc_response.html </li>
				</ul>
			</td>
		  </tr>
		</table>

				<form class="mainForm" name="configForm" method="post" action="paymentMethod.html">
					<table class="table-content" cellspacing="0" cellpadding="0" width="100%" border="0">
						<tr>
							<td class="FieldLabel"><span class="fB">商户号</span></td>
							<td>
								<INPUT class="Field400"  name="merchantNo" value="${paymentMethod.configData.merchantNo}"/>
							</td>
						</tr>
						<tr>
							<td class="FieldLabel">提交URL</td>
							<td>
								<INPUT class="Field400"  name="v_url" value="${paymentMethod.configData.v_url}"/>
								测试：http://180.168.146.75:81/PGWPortal/RecvOrder.do
							</td>
						</tr> 
						<tr>
							<td class="FieldLabel">签名证书库路径</td>
							<td>
								<INPUT class="Field400"  name="keyStorePath" value="${paymentMethod.configData.keyStorePath}"/> jks文件路径
							</td>
						</tr>
						<tr>
							<td class="FieldLabel">签名证书库口令</td>
							<td>
								<INPUT class="Field400"  name="keyStorePassword" value="${paymentMethod.configData.keyStorePassword}"/> 
							</td>
						</tr>
						<tr>
							<td class="FieldLabel">签名私钥口令</td>
							<td>
								<INPUT class="Field400"  name="keyPassword" value="${paymentMethod.configData.keyPassword}"/> 一般与证书库口令相同
							</td>
						</tr>
						<tr>
							<td class="FieldLabel">根证书路径</td>
							<td>
								<INPUT class="Field400"  name="rootCertificatePath" value="${paymentMethod.configData.rootCertificatePath}"/> cer文件
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
</html>
