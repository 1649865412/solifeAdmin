<%
	//Description: WorldPay Configuration
	//CreateTime : 2006-07-24 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
	<TITLE>WorldPay Payment Gateway Configuration</TITLE>
	<script type="text/javascript">
	function loadData(){
		var currency=document.getElementById("currency");
		var testMode=document.getElementById("testMode");
	
		for(var i=0;i<currency.length;i++){
			if(currency.options[i].value=="${paymentMethod.configData.currency}"){
				currency.options[i].selected=true;
				break;
			}
		}
		for(var j=0;j<testMode.length;j++){
			if(testMode.options[j].value=="${paymentMethod.configData.testMode}"){
				testMode.options[j].selected=true;
				break;
			}
		}
	}

	</script>
</head>
<body id="mine" onLoad="loadData()">

	<table class="table-content" width="100%" border="0" cellspacing="0" cellpadding="0">
	  <tr>
		<td class="FieldLabel">WorldPay Configuration</td>
	    <td width="71%" align="right">
			<IMG alt="WorldPay" src="<c:url value="/pages/system/${paymentMethod.paymentMethodIcon}"/>"/>
		</td>
	  </tr>
	  <tr>
	    <td align="left" colspan="2">
			<span class="fB">Note: </span>
			<ul style="padding-left:25px">
				<li>Please get a customer account from <a target="_blank" href="http://www.worldpay.com" style="color:red;text-decoration: underline">WorldPay</a></li>
				<li>Fill the information below</li>
			</ul>
		</td>
	  </tr>
	</table>

			<form class="mainForm" name="configForm" method="post" action="paymentMethod.html">
				<table class="table-content" cellspacing="0" cellpadding="0" width="100%" border="0">
					<tr>
						<td class="FieldLabel">Installation ID:</td>
						<td>
							<INPUT class="Field400"  name="instId" value="${paymentMethod.configData.instId}"/>
						</td>
					</tr>
					<tr>
						<td class="FieldLabel">Currency:</td>
						<td>
							<select id="currency" name="currency" class="Field400">
								<option value="AFA" selected>Afghani</option>
								<option value="ALL">Lek</option>
								<option value="DZD">Algerian Dinar</option>
								<option value="AON">New Kwanza</option>
								<option value="ARS">Argentine Peso</option>
								<option value="AWG">Aruban Guilder</option>
								<option value="AUD">Australian Dollar</option>
								<option value="ATS">Schilling</option>
								<option value="BSD">Bahamian Dollar</option>
								<option value="BHD">Bahraini Dinar</option>
								<option value="BDT">Taka</option>
								<option value="BBD">Barbados Dollar</option>
								<option value="BEF">Belgian Franc</option>
								<option value="BZD">Belize Dollar</option>
								<option value="BMD">Bermudian Dollar</option>
								<option value="BOB">Boliviano</option>
								<option value="BAD">Bosnian Dinar</option>
								<option value="BWP">Pula</option>
								<option value="BRL">Real</option>
								<option value="BND">Brunei Dollar</option>
								<option value="BGL">Lev</option>
								<option value="XOF">CFA Franc BCEAO</option>
								<option value="BIF">Burundi Franc</option>
								<option value="KHR">Cambodia Riel</option>
								<option value="XAF">CFA Franc BEAC</option>
								<option value="CAD">Canadian Dollar</option>
								<option value="CVE">Cape Verde Escudo</option>
								<option value="KYD">Cayman Islands Dollar</option>
								<option value="CLP">Chilean Peso</option>
								<option value="CNY">Yuan Renminbi</option>
								<option value="COP">Colombian Peso</option>
								<option value="KMF">Comoro Franc</option>
								<option value="CRC">Costa Rican Colon</option>
								<option value="HRK">Croatian Kuna</option>
								<option value="CUP">Cuban Peso</option>
								<option value="CYP">Cyprus Pound</option>
								<option value="CZK">Czech Koruna</option>
								<option value="DKK">Danish Krone</option>
								<option value="DJF">Djibouti Franc</option>
								<option value="XCD">East Caribbean Dollar</option>
								<option value="DOP">Dominican Peso</option>
								<option value="TPE">Timor Escudo</option>
								<option value="ECS">Ecuador Sucre</option>
								<option value="EGP">Egyptian Pound</option>
								<option value="SVC">El Salvador Colon</option>
								<option value="EEK">Kroon</option>
								<option value="ETB">Ethiopian Birr</option>
								<option value="XEU">ECU</option>
								<option value="EUR">European EURO</option>
								<option value="FKP">Falkland Islands Pound</option>
								<option value="FJD">Fiji Dollar</option>
								<option value="FIM">Markka</option>
								<option value="FRF">French Franc</option>
								<option value="XPF">CFP Franc</option>
								<option value="GMD">Dalasi</option>
								<option value="DEM">Deutsche Mark</option>
								<option value="GHC">Cedi</option>
								<option value="GIP">Gibraltar Pound</option>
								<option value="GRD">Drachma</option>
								<option value="GTQ">Quetzal</option>
								<option value="GNF">Guinea Franc</option>
								<option value="GWP">Guinea - Bissau Peso</option>
								<option value="GYD">Guyana Dollar</option>
								<option value="HTG">Gourde</option>
								<option value="HNL">Lempira</option>
								<option value="HKD">Hong Kong Dollar</option>
								<option value="HUF">Forint</option>
								<option value="ISK">Iceland Krona</option>
								<option value="INR">Indian Rupee</option>
								<option value="IDR">Rupiah</option>
								<option value="IRR">Iranian Rial</option>
								<option value="IQD">Iraqi Dinar</option>
								<option value="IEP">Irish Pound</option>
								<option value="ILS">Shekel</option>
								<option value="ITL">Italian Lira</option>
								<option value="JMD">Jamaican Dollar</option>
								<option value="JPY">Yen</option>
								<option value="JOD">Jordanian Dinar</option>
								<option value="KZT">Tenge</option>
								<option value="KES">Kenyan Shilling</option>
								<option value="KRW">Won</option>
								<option value="KPW">North Korean Won</option>
								<option value="KWD">Kuwaiti Dinar</option>
								<option value="KGS">Som</option>
								<option value="LAK">Kip</option>
								<option value="LVL">Latvian Lats</option>
								<option value="LBP">Lebanese Pound</option>
								<option value="LSL">Loti</option>
								<option value="LRD">Liberian Dollar</option>
								<option value="LYD">Libyan Dinar</option>
								<option value="LTL">Lithuanian Litas</option>
								<option value="LUF">Luxembourg Franc</option>
								<option value="MOP">Pataca</option>
								<option value="MKD">Denar</option>
								<option value="MGF">Malagasy Franc</option>
								<option value="MWK">Kwacha</option>
								<option value="MYR">Malaysian Ringitt</option>
								<option value="MVR">Rufiyaa</option>
								<option value="MTL">Maltese Lira</option>
								<option value="MRO">Ouguiya</option>
								<option value="MUR">Mauritius Rupee</option>
								<option value="MXN">Mexico Peso</option>
								<option value="MNT">Mongolia Tugrik</option>
								<option value="MAD">Moroccan Dirham</option>
								<option value="MZM">Metical</option>
								<option value="MMK">Myanmar Kyat</option>
								<option value="NAD">Namibian Dollar</option>
								<option value="NPR">Nepalese Rupee</option>
								<option value="ANG">Netherlands Antilles Guilder</option>
								<option value="NLG">Netherlands Guilder</option>
								<option value="NZD">New Zealand Dollar</option>
								<option value="NIO">Cordoba Oro</option>
								<option value="NGN">Naira</option>
								<option value="NOK">Norwegian Krone</option>
								<option value="OMR">Rial Omani</option>
								<option value="PKR">Pakistan Rupee</option>
								<option value="PAB">Balboa</option>
								<option value="PGK">New Guinea Kina</option>
								<option value="PYG">Guarani</option>
								<option value="PEN">Nuevo Sol</option>
								<option value="PHP">Philippine Peso</option>
								<option value="PLN">New Zloty</option>
								<option value="PTE">Portugese Escudo</option>
								<option value="QAR">Qatari Rial</option>
								<option value="ROL">Leu</option>
								<option value="RUR">Russian Ruble</option>
								<option value="RWF">Rwanda Franc</option>
								<option value="WST">Tala</option>
								<option value="STD">Dobra</option>
								<option value="SAR">Saudi Riyal</option>
								<option value="SCR">Seychelles Rupee</option>
								<option value="SLL">Leone</option>
								<option value="SGD">Singapore Dollar</option>
								<option value="SKK">Slovak Koruna</option>
								<option value="SIT">Tolar</option>
								<option value="SBD">Solomon Islands Dollar</option>
								<option value="SOS">Somalia Shilling</option>
								<option value="ZAR">Rand</option>
								<option value="ESP">Spanish Peseta</option>
								<option value="LKR">Sri Lanka Rupee</option>
								<option value="SHP">St Helena Pound</option>
								<option value="SDP">Sudanese Pound</option>
								<option value="SRG">Suriname Guilder</option>
								<option value="SZL">Swaziland Lilangeni</option>
								<option value="SEK">Sweden Krona</option>
								<option value="CHF">Swiss Franc</option>
								<option value="SYP">Syrian Pound</option>
								<option value="TWD">New Taiwan Dollar</option>
								<option value="TJR">Tajik Ruble</option>
								<option value="TZS">Tanzanian Shilling</option>
								<option value="THB">Baht</option>
								<option value="TOP">Tonga Pa'anga</option>
								<option value="TTD">Trinidad &amp; Tobago Dollar</option>
								<option value="TND">Tunisian Dinar</option>
								<option value="TRL">Turkish Lira</option>
								<option value="UGX">Uganda Shilling</option>
								<option value="UAH">Ukrainian Hryvnia</option>
								<option value="AED">United Arab Emirates Dirham</option>
								<option value="GBP">Pounds Sterling</option>
								<option value="USD">US Dollar</option>
								<option value="UYU">Uruguayan Peso</option>
								<option value="VUV">Vanuatu Vatu</option>
								<option value="VEB">Venezuela Bolivar</option>
								<option value="VND">Viet Nam Dong</option>
								<option value="YER">Yemeni Rial</option>
								<option value="YUM">Yugoslavian New Dinar</option>
								<option value="ZRN">New Zaire</option>
								<option value="ZMK">Zambian Kwacha</option>
								<option value="ZWD">Zimbabwe Dollar</option>
							</select>
						</td>
					</tr>
					<tr> 
						<td class="FieldLabel">Test/Live Mode:</td>
						<td>
							<SELECT id="testMode" name="testMode" class="Field400"> 
								<OPTION value="100">Test:Approved</OPTION> 
								<OPTION value="101">Test:Declined</OPTION> 
								<OPTION value="0">Live</OPTION>
							</SELECT>
						</td>
					</tr> 
					<tr>
					  <td>&nbsp;</td>
					  <td>
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