<%
	//Description: money booker configuration page
	//CreateTime: 2006-07-31
	//Author: chenshangxuan
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
	<TITLE>Money Bookers Payment Gateway Configuration</TITLE>
	<script type="text/javascript">
	function loadData(){
		var currency=document.getElementById("currency");
		var language=document.getElementById("language");
		for(var i=0;i<currency.options.length;i++){
			if(currency.options[i].value=="${paymentMethod.configData.currency}"){
				currency.options[i].selected=true;
				break;
			}
		}
		for(var j=0;j<language.options.length;j++){
			if(language.options[j].value=="${paymentMethod.configData.language}"){
				language.options[j].selected=true;
				break;	
			}
		}
	}
	</script>
</head>
<body id="me" onLoad="loadData()">

	<table class="table-content" width="100%" border="0" cellspacing="0" cellpadding="0">
	  <tr>
		<td class="FieldLabel">Money Bookers Payment Configuration</td>
	    <td width="71%" align="right">
			<IMG alt="moneybookers" src="<c:url value="/pages/system/${paymentMethod.paymentMethodIcon}"/>"/>
		</td>
	  </tr>
	  <tr>
	    <td align="left" colspan="2">
			<span class="fB">Note: </span>
			<ul style="padding-left:25px">
				<li>Please Regiseter a merchant account from <a href="http://www.moneybookers.com" target="_blank" style="color:red;text-decoration: underline">Money Bookers</a></li>
				<li>Fill the information below</li>
			</ul>
		</td>
	  </tr>
	</table>
</content>

				<form class="mainForm" name="configForm" method="post" action="paymentMethod.html">
					<table class="table-content" cellspacing="0" cellpadding="0" width="100%" border="0">
						<tr>
							<td class="FieldLabel">Pay to email:</td>
							<td>
								<INPUT class="Field400"  name="payToEmail" value="${paymentMethod.configData.payToEmail}"/>
							</td>
						</tr>
						<tr> 
							<td class="FieldLabel">Currency:</th>
							<td>
								<select id="currency" name="currency" class="Field400">
									<option value="AUD">Australian Dollar</option>
									<option value="BGN">Bulgarian Lev</option>
									<option value="CAD">Canadian Dollar</option>
									<option value="CHF">Swiss Franc</option>
									<option value="CZK">Czech Koruna</option>
									<option value="DKK">Danish Krone</option>
									<option value="EEK">Estonian Koruna</option>
									<option value="EUR">Euro</option>
									<option value="GBP">Pound Sterling</option>
									<option value="HKD">Hong Kong Dollar</option>
									<option value="HUF">Forint</option>
									<option value="ILS">Shekel</option>
									<option value="ISK">Iceland Krona</option>
									<option value="JPY">Yen</option>
									<option value="KRW">South-Korean Won</option>
									<option value="LVL">Latvian Lat</option>
									<option value="MYR">Malaysian Ringgit</option>
									<option value="NOK">Norwegian Krone</option>
									<option value="NZD">New Zealand Dollar</option>
									<option value="PLN">Zloty</option>
									<option value="SEK">Swedish Krona</option>
									<option value="SGD">Singapore Dollar</option>
									<option value="SKK">Slovak Koruna</option>
									<option value="THB">Baht</option>
									<option value="TWD">New Taiwan Dollar</option>
									<option value="USD">US Dollar</option>
									<option value="ZAR">South-African Rand</option>
									<option value="AFA">Afghani</option>
								</select>
							</td>
						</tr>
						<tr> 
							<td class="FieldLabel">Language:</td>
							<td>
								<select name="language" id="language" class="Field400">
								 <option value="EN">EN</option>
								 <option value="DE">DE</option>
								 <option value="ES">ES</option>
								 <option value="FR">FR</option>
								</select>
							</td>
						</tr>
				
						<tr> 
							<td class="FieldLabel">Order Prefix:</td>
							<td>
								<input type="text" name="orderpre" value="${paymentMethod.configData.orderpre}" class="Field400" >
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
