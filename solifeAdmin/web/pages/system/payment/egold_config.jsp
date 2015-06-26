<%
	//Desc: e-gold Payment Configuration
	//Author: chenshangxuan
	//CreateTime:  2007-01-24
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
	<TITLE>e-Gold Payment Gateway Configuration</TITLE>
</head>
<body>

	<table class="table-content" cellspacing="0" cellpadding="0" width="100%" border="0">
	  <tr>
		<td class="FieldLabel">eGold Payment Gateway Configuration</td>
	    <td width="71%" align="right">
			<IMG alt="egold" src="<c:url value="/pages/system/${paymentMethod.paymentMethodIcon}"/>"/>	
		</td>
	  </tr>
	  <tr>
	    <td align="left" colspan="2">
			<span class="fB">Note: </span>
			<ul style="padding-left:25px">
				<li>Please get a account number from <a href="http://www.e-gold.com" target="_blank" style="color:red;text-decoration: underline">e-Gold</a></li>
				<li>Fill the information below</li>
			</ul>
		</td>
	  </tr>
	</table>

			<form class="mainForm" name="configForm" method="post" action="paymentMethod.html">
				<table class="table-content" cellspacing="0" cellpadding="0" width="100%" border="0">
					<tr>
						<td class="FieldLabel">Account Number:</td>
						<td>
							<INPUT class="Field400"  name="account" value="${paymentMethod.configData.account}"/>
						</td>
					</tr>
					<tr>
						<td class="FieldLabel">Merchant Name:</td>
						<td>
							<INPUT class="Field400"  name="payee_name" value="${paymentMethod.configData.payee_name}"/>
						</td>
					</tr>
					<tr>
						<td class="FieldLabel">Payment Units:</td>
						<td>
							<select name="payment_units" class="Field400">
								<option value="1" <c:if test="${paymentMethod.configData.payment_units==1}">selected</c:if>>US Dollar (USD)</option>
								<option value="2" <c:if test="${paymentMethod.configData.payment_units==2}">selected</c:if>>Canadian Dollar (CAD)</option>
								<option value="33" <c:if test="${paymentMethod.configData.payment_units==33}">selected</c:if>>French Franc (FRF)</option>
								<option value="41" <c:if test="${paymentMethod.configData.payment_units==41}">selected</c:if>>Swiss Francs (CHF)</option>
								<option value="44" <c:if test="${paymentMethod.configData.payment_units==44}">selected</c:if>>Gt. Britain Pound (GBP)</option>
								<option value="49" <c:if test="${paymentMethod.configData.payment_units==49}">selected</c:if>>Deutschemark (DEM)</option>
								<option value="61" <c:if test="${paymentMethod.configData.payment_units==61}">selected</c:if>>Australian Dollar (AUD)</option>
								<option value="81" <c:if test="${paymentMethod.configData.payment_units==81}">selected</c:if>>Japanese Yen (JPY)</option>
								<option value="85" <c:if test="${paymentMethod.configData.payment_units==85}">selected</c:if>>Euro (EUR)</option>
								<option value="86" <c:if test="${paymentMethod.configData.payment_units==86}">selected</c:if>>Belgian Franc (BEF)</option>
								<option value="87" <c:if test="${paymentMethod.configData.payment_units==87}">selected</c:if>>Austrian Schilling (ATS)</option>
								<option value="88" <c:if test="${paymentMethod.configData.payment_units==88}">selected</c:if>>Greek Drachma (GRD)</option>
								<option value="89" <c:if test="${paymentMethod.configData.payment_units==89}">selected</c:if>>Spanish Peseta (ESP)</option>
								<option value="90" <c:if test="${paymentMethod.configData.payment_units==90}">selected</c:if>>Irish Pound (IEP)</option>
								<option value="91" <c:if test="${paymentMethod.configData.payment_units==91}">selected</c:if>>Italian Lira (ITL)</option>
								<option value="92" <c:if test="${paymentMethod.configData.payment_units==92}">selected</c:if>>Luxembourg Franc (LUF)</option>
								<option value="93" <c:if test="${paymentMethod.configData.payment_units==93}">selected</c:if>>Dutch Guilder (NLG)</option>
								<option value="94" <c:if test="${paymentMethod.configData.payment_units==94}">selected</c:if>>Portuguese Escudo (PTE)</option>
								<option value="95" <c:if test="${paymentMethod.configData.payment_units==95}">selected</c:if>>Finnish Markka (FIM)</option>
								<option value="96" <c:if test="${paymentMethod.configData.payment_units==96}">selected</c:if>>Estonian Kroon (EEK)</option>
								<option value="97" <c:if test="${paymentMethod.configData.payment_units==97}">selected</c:if>>Lithuanian Litas (LTL)</option>
								<option value="8888" <c:if test="${paymentMethod.configData.payment_units==8888}">selected</c:if>>Gram (g)</option>
								<option value="9999" <c:if test="${paymentMethod.configData.payment_units==9999}">selected</c:if>>Troy Ounce (oz)</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="FieldLabel">Metal for Payment:</td>
						<td>
							<select name="payment_metal_id" class="Field400">
								<option value="0" <c:if test="${paymentMethod.configData.payment_metal_id==0}">selected</c:if>>Buyer's Choice</option>
								<option value="1" <c:if test="${paymentMethod.configData.payment_metal_id==1}">selected</c:if>>Gold</option>
								<option value="2" <c:if test="${paymentMethod.configData.payment_metal_id==2}">selected</c:if>>Silver</option>
								<option value="3" <c:if test="${paymentMethod.configData.payment_metal_id==3}">selected</c:if>>Platinum</option>
								<option value="4" <c:if test="${paymentMethod.configData.payment_metal_id==4}">selected</c:if>>Palladium</option>
							</select>
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
						<td class="FieldLabel">Payment Status URL:</td>
						<td>
							<input type="text" class="Field400"  name="status_url" value="${paymentMethod.configData.status_url}">
							<span>(Optional) Controls whether and how payment status is returned by the egold
			server to the merchant. example:merchant@foo.com
							</span>
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
