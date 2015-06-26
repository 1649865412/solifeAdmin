<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
	<head>
		<title>Authorize.net(AIM) Gateway Parameters Settings</title>
	</head>
	<body>
		
		<table class="table-content" cellspacing="0" cellpadding="0" width="100%" border="0">
			<tr>
				<td class="FieldLabel">
					<span class="fB" style="float: left; width: 50%">Authorize.net(AIM)
						Gateway Parameters Settings</span>
				</td>
				<td>
				<IMG alt="paypal"
					src="${ctxPath}/pages/system/${paymentMethod.paymentMethodIcon}" />
				</td>
			</tr>
			<tr>
				<td align="left" colspan="2">
					<span class="fB">Note: </span>
					<ul style="padding-left: 25px">
						<li>
							Please get a new account from
							<a href="http://www.authorize.net/" target="_blank"
								style="color: red; text-decoration: underline">Authorize.net</a>
						</li>
						<li>
							Please fill the form below accord with the authorize.net account.
						</li>
					</ul>
				</td>
			</tr>
		</table>
		

					<form class="mainForm" name="configForm" method="post" action="paymentMethod.html">
						<table class="table-content" cellspacing="0" cellpadding="0"
							width="100%" border="0">
							<tr>
								<td class="FieldLabel">
									Login
								</td>
								<td>
									<INPUT class="Field400" style="WIDTH: 50%" name="x_login"
										value="${paymentMethod.configData.x_login}" />
								</td>
							</tr>
							<tr>
								<td class="FieldLabel">
									Transaction Key
								</td>
								<td>
									<INPUT class="Field400" style="WIDTH: 50%"
										name="x_tran_key"
										value="${paymentMethod.configData.x_tran_key}" />
								</td>
							</tr>
							
							<tr>
								<td class="FieldLabel">
									MD5 Hash
								</td>
								<td>
									<INPUT class="Field400" style="WIDTH: 50%"
										name="x_md5_hash"
										value="${paymentMethod.configData.x_md5_hash}" />
								</td>
							</tr>
							<tr>
								<td>
									Currency
								</td>
								<td>
									<select class="Field400" name="x_currency_code">
										<option value="AFA"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'AFA'}"> selected="selected"</c:if>>
											Afghani (Afghanistan)
										</option>
										<option value="DZD"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'DZD'}"> selected="selected"</c:if>>
											Algerian Dinar (Algeria)
										</option>
										<option value="ADP"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'ADP'}"> selected="selected"</c:if>>
											Andorran Peseta (Andorra)
										</option>
										<option value="ARS"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'ARS'}"> selected="selected"</c:if>>
											Argentine Peso (Argentina)
										</option>
										<option value="AMD"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'AMD'}"> selected="selected"</c:if>>
											Armenian Dram (Armenia)
										</option>
										<option value="AWG"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'AWG'}"> selected="selected"</c:if>>
											Aruban Guilder (Aruba)
										</option>
										<option value="AUD"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'AUD'}"> selected="selected"</c:if>>
											Australian Dollar (Australia)
										</option>
										<option value="AZM"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'AZM'}"> selected="selected"</c:if>>
											Azerbaijanian Manat (Azerbaijan)
										</option>
										<option value="BSD"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'BSD'}"> selected="selected"</c:if>>
											Bahamian Dollar (Bahamas)
										</option>
										<option value="BHD"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'BHD'}"> selected="selected"</c:if>>
											Bahraini Dinar (Bahrain)
										</option>
										<option value="THB"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'THB'}"> selected="selected"</c:if>>
											Baht (Thailand)
										</option>
										<option value="PAB"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'PAB'}"> selected="selected"</c:if>>
											Balboa (Panama)
										</option>
										<option value="BBD"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'BBD'}"> selected="selected"</c:if>>
											Barbados Dollar (Barbados)
										</option>
										<option value="BYB"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'BYB'}"> selected="selected"</c:if>>
											Belarussian Ruble (Belarus)
										</option>
										<option value="BEF"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'BEF'}"> selected="selected"</c:if>>
											Belgian Franc (Belgium)
										</option>
										<option value="BZD"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'BZD'}"> selected="selected"</c:if>>
											Belize Dollar (Belize)
										</option>
										<option value="BMD"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'BMD'}"> selected="selected"</c:if>>
											Bermudian Dollar (Bermuda)
										</option>
										<option value="VEB"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'VEB'}"> selected="selected"</c:if>>
											Bolivar (Venezuela)
										</option>
										<option value="BOB"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'BOB'}"> selected="selected"</c:if>>
											Boliviano (Bolivia)
										</option>
										<option value="BRL"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'BRL'}"> selected="selected"</c:if>>
											Brazilian Real (Brazil)
										</option>
										<option value="BND"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'BND'}"> selected="selected"</c:if>>
											Brunei Dollar (Brunei Darussalam)
										</option>
										<option value="BGN"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'BGN'}"> selected="selected"</c:if>>
											Bulgarian Lev (Bulgaria)
										</option>
										<option value="BIF"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'BIF'}"> selected="selected"</c:if>>
											Burundi Franc (Burundi)
										</option>
										<option value="CAD"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'CAD'}"> selected="selected"</c:if>>
											Canadian Dollar (Canada)
										</option>
										<option value="CVE"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'CVE'}"> selected="selected"</c:if>>
											Cape Verde Escudo (Cape Verde)
										</option>
										<option value="KYD"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'KYD'}"> selected="selected"</c:if>>
											Cayman Islands Dollar (Cayman Islands)
										</option>
										<option value="GHC"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'GHC'}"> selected="selected"</c:if>>
											Cedi (Ghana)
										</option>
										<option value="XOF"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'XOF'}"> selected="selected"</c:if>>
											CFA Franc BCEAO (Guinea-Bissau)
										</option>
										<option value="XAF"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'XAF'}"> selected="selected"</c:if>>
											CFA Franc BEAC (Central African Republic)
										</option>
										<option value="XPF"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'XPF'}"> selected="selected"</c:if>>
											CFP Franc (New Caledonia)
										</option>
										<option value="CLP"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'CLP'}"> selected="selected"</c:if>>
											Chilean Peso (Chile)
										</option>
										<option value="COP"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'COP'}"> selected="selected"</c:if>>
											Colombian Peso (Colombia)
										</option>
										<option value="KMF"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'KMF'}"> selected="selected"</c:if>>
											Comoro Franc (Comoros)
										</option>
										<option value="BAM"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'BAM'}"> selected="selected"</c:if>>
											Convertible Marks (Bosnia And Herzegovina)
										</option>
										<option value="NIO"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'NIO'}"> selected="selected"</c:if>>
											Cordoba Oro (Nicaragua)
										</option>
										<option value="CRC"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'CRC'}"> selected="selected"</c:if>>
											Costa Rican Colon (Costa Rica)
										</option>
										<option value="CUP"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'CUP'}"> selected="selected"</c:if>>
											Cuban Peso (Cuba)
										</option>
										<option value="CYP"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'CYP'}"> selected="selected"</c:if>>
											Cyprus Pound (Cyprus)
										</option>
										<option value="CZK"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'CZK'}"> selected="selected"</c:if>>
											Czech Koruna (Czech Republic)
										</option>
										<option value="GMD"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'GMD'}"> selected="selected"</c:if>>
											Dalasi (Gambia)
										</option>
										<option value="DKK"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'DKK'}"> selected="selected"</c:if>>
											Danish Krone (Denmark)
										</option>
										<option value="MKD"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'MKD'}"> selected="selected"</c:if>>
											Denar (The Former Yugoslav Republic Of Macedonia)
										</option>
										<option value="DEM"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'DEM'}"> selected="selected"</c:if>>
											Deutsche Mark (Germany)
										</option>
										<option value="AED"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'AED'}"> selected="selected"</c:if>>
											Dirham (United Arab Emirates)
										</option>
										<option value="DJF"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'DJF'}"> selected="selected"</c:if>>
											Djibouti Franc (Djibouti)
										</option>
										<option value="STD"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'STD'}"> selected="selected"</c:if>>
											Dobra (Sao Tome And Principe)
										</option>
										<option value="DOP"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'DOP'}"> selected="selected"</c:if>>
											Dominican Peso (Dominican Republic)
										</option>
										<option value="VND"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'VND'}"> selected="selected"</c:if>>
											Dong (Vietnam)
										</option>
										<option value="GRD"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'GRD'}"> selected="selected"</c:if>>
											Drachma (Greece)
										</option>
										<option value="XCD"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'XCD'}"> selected="selected"</c:if>>
											East Caribbean Dollar (Grenada)
										</option>
										<option value="EGP"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'EGP'}"> selected="selected"</c:if>>
											Egyptian Pound (Egypt)
										</option>
										<option value="SVC"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'SVC'}"> selected="selected"</c:if>>
											El Salvador Colon (El Salvador)
										</option>
										<option value="ETB"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'ETB'}"> selected="selected"</c:if>>
											Ethiopian Birr (Ethiopia)
										</option>
										<option value="EUR"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'EUR'}"> selected="selected"</c:if>>
											Euro (Europe)
										</option>
										<option value="FKP"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'FKP'}"> selected="selected"</c:if>>
											Falkland Islands Pound (Falkland Islands)
										</option>
										<option value="FJD"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'FJD'}"> selected="selected"</c:if>>
											Fiji Dollar (Fiji)
										</option>
										<option value="HUF"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'HUF'}"> selected="selected"</c:if>>
											Forint (Hungary)
										</option>
										<option value="CDF"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'CDF'}"> selected="selected"</c:if>>
											Franc Congolais (The Democratic Republic Of Congo)
										</option>
										<option value="FRF"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'FRF'}"> selected="selected"</c:if>>
											French Franc (France)
										</option>
										<option value="GIP"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'GIP'}"> selected="selected"</c:if>>
											Gibraltar Pound (Gibraltar)
										</option>
										<option value="XAU"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'XAU'}"> selected="selected"</c:if>>
											Gold
										</option>
										<option value="HTG"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'HTG'}"> selected="selected"</c:if>>
											Gourde (Haiti)
										</option>
										<option value="PYG"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'PYG'}"> selected="selected"</c:if>>
											Guarani (Paraguay)
										</option>
										<option value="GNF"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'GNF'}"> selected="selected"</c:if>>
											Guinea Franc (Guinea)
										</option>
										<option value="GWP"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'GWP'}"> selected="selected"</c:if>>
											Guinea-Bissau Peso (Guinea-Bissau)
										</option>
										<option value="GYD"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'GYD'}"> selected="selected"</c:if>>
											Guyana Dollar (Guyana)
										</option>
										<option value="HKD"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'HKD'}"> selected="selected"</c:if>>
											Hong Kong Dollar (Hong Kong)
										</option>
										<option value="UAH"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'UAH'}"> selected="selected"</c:if>>
											Hryvnia (Ukraine)
										</option>
										<option value="ISK"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'ISK'}"> selected="selected"</c:if>>
											Iceland Krona (Iceland)
										</option>
										<option value="INR"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'INR'}"> selected="selected"</c:if>>
											Indian Rupee (India)
										</option>
										<option value="IRR"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'IRR'}"> selected="selected"</c:if>>
											Iranian Rial (Islamic Republic Of Iran)
										</option>
										<option value="IQD"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'IQD'}"> selected="selected"</c:if>>
											Iraqi Dinar (Iraq)
										</option>
										<option value="IEP"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'IEP'}"> selected="selected"</c:if>>
											Irish Pound (Ireland)
										</option>
										<option value="ITL"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'ITL'}"> selected="selected"</c:if>>
											Italian Lira (Italy)
										</option>
										<option value="JMD"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'JMD'}"> selected="selected"</c:if>>
											Jamaican Dollar (Jamaica)
										</option>
										<option value="JOD"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'JOD'}"> selected="selected"</c:if>>
											Jordanian Dinar (Jordan)
										</option>
										<option value="KES"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'KES'}"> selected="selected"</c:if>>
											Kenyan Shilling (Kenya)
										</option>
										<option value="PGK"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'PGK'}"> selected="selected"</c:if>>
											Kina (Papua New Guinea)
										</option>
										<option value="LAK"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'LAK'}"> selected="selected"</c:if>>
											Kip (Lao People's Democratic Republic)
										</option>
										<option value="EEK"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'EEK'}"> selected="selected"</c:if>>
											Kroon (Estonia)
										</option>
										<option value="HRK"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'HRK'}"> selected="selected"</c:if>>
											Kuna (Croatia)
										</option>
										<option value="KWD"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'KWD'}"> selected="selected"</c:if>>
											Kuwaiti Dinar (Kuwait)
										</option>
										<option value="MWK"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'MWK'}"> selected="selected"</c:if>>
											Kwacha (Malawi)
										</option>
										<option value="ZMK"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'ZMK'}"> selected="selected"</c:if>>
											Kwacha (Zambia)
										</option>
										<option value="AOR"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'AOR'}"> selected="selected"</c:if>>
											Kwanza Reajustado (Angola)
										</option>
										<option value="MMK"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'MMK'}"> selected="selected"</c:if>>
											Kyat (Myanmar)
										</option>
										<option value="GEL"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'GEL'}"> selected="selected"</c:if>>
											Lari (Georgia)
										</option>
										<option value="LVL"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'LVL'}"> selected="selected"</c:if>>
											Latvian Lats (Latvia)
										</option>
										<option value="LBP"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'LBP'}"> selected="selected"</c:if>>
											Lebanese Pound (Lebanon)
										</option>
										<option value="ALL"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'ALL'}"> selected="selected"</c:if>>
											Lek (Albania)
										</option>
										<option value="HNL"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'HNL'}"> selected="selected"</c:if>>
											Lempira (Honduras)
										</option>
										<option value="SLL"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'SLL'}"> selected="selected"</c:if>>
											Leone (Sierra Leone)
										</option>
										<option value="ROL"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'ROL'}"> selected="selected"</c:if>>
											Leu (Romania)
										</option>
										<option value="BGL"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'BGL'}"> selected="selected"</c:if>>
											Lev (Bulgaria)
										</option>
										<option value="LRD"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'LRD'}"> selected="selected"</c:if>>
											Liberian Dollar (Liberia)
										</option>
										<option value="LYD"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'LYD'}"> selected="selected"</c:if>>
											Libyan Dinar (Libyan Arab Jamahiriya)
										</option>
										<option value="SZL"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'SZL'}"> selected="selected"</c:if>>
											Lilangeni (Swaziland)
										</option>
										<option value="LTL"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'LTL'}"> selected="selected"</c:if>>
											Lithuanian Litas (Lithuania)
										</option>
										<option value="LSL"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'LSL'}"> selected="selected"</c:if>>
											Loti (Lesotho)
										</option>
										<option value="LUF"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'LUF'}"> selected="selected"</c:if>>
											Luxembourg Franc (Luxembourg)
										</option>
										<option value="MGF"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'MGF'}"> selected="selected"</c:if>>
											Malagasy Franc (Madagascar)
										</option>
										<option value="MYR"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'MYR'}"> selected="selected"</c:if>>
											Malaysian Ringgit (Malaysia)
										</option>
										<option value="MTL"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'MTL'}"> selected="selected"</c:if>>
											Maltese Lira (Malta)
										</option>
										<option value="TMM"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'TMM'}"> selected="selected"</c:if>>
											Manat (Turkmenistan)
										</option>
										<option value="FIM"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'FIM'}"> selected="selected"</c:if>>
											Markka (Finland)
										</option>
										<option value="MUR"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'MUR'}"> selected="selected"</c:if>>
											Mauritius Rupee (Mauritius)
										</option>
										<option value="MZM"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'MZM'}"> selected="selected"</c:if>>
											Metical (Mozambique)
										</option>
										<option value="MXN"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'MXN'}"> selected="selected"</c:if>>
											Mexican Peso (Mexico)
										</option>
										<option value="MXV"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'MXV'}"> selected="selected"</c:if>>
											Mexican Unidad de Inversion (Mexico)
										</option>
										<option value="MDL"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'MDL'}"> selected="selected"</c:if>>
											Moldovan Leu (Republic Of Moldova)
										</option>
										<option value="MAD"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'MAD'}"> selected="selected"</c:if>>
											Moroccan Dirham (Morocco)
										</option>
										<option value="BOV"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'BOV'}"> selected="selected"</c:if>>
											Mvdol (Bolivia)
										</option>
										<option value="NGN"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'NGN'}"> selected="selected"</c:if>>
											Naira (Nigeria)
										</option>
										<option value="ERN"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'ERN'}"> selected="selected"</c:if>>
											Nakfa (Eritrea)
										</option>
										<option value="NAD"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'NAD'}"> selected="selected"</c:if>>
											Namibia Dollar (Namibia)
										</option>
										<option value="NPR"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'NPR'}"> selected="selected"</c:if>>
											Nepalese Rupee (Nepal)
										</option>
										<option value="ANG"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'ANG'}"> selected="selected"</c:if>>
											Netherlands (Netherlands)
										</option>
										<option value="NLG"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'NLG'}"> selected="selected"</c:if>>
											Netherlands Guilder (Netherlands)
										</option>
										<option value="YUM"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'YUM'}"> selected="selected"</c:if>>
											New Dinar (Yugoslavia)
										</option>
										<option value="ILS"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'ILS'}"> selected="selected"</c:if>>
											New Israeli Sheqel (Israel)
										</option>
										<option value="AON"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'AON'}"> selected="selected"</c:if>>
											New Kwanza (Angola)
										</option>
										<option value="TWD"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'TWD'}"> selected="selected"</c:if>>
											New Taiwan Dollar (Province Of China Taiwan)
										</option>
										<option value="ZRN"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'ZRN'}"> selected="selected"</c:if>>
											New Zaire (Zaire)
										</option>
										<option value="NZD"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'NZD'}"> selected="selected"</c:if>>
											New Zealand Dollar (New Zealand)
										</option>
										<option value="BTN"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'BTN'}"> selected="selected"</c:if>>
											Ngultrum (Bhutan)
										</option>
										<option value="KPW"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'KPW'}"> selected="selected"</c:if>>
											North Korean Won (Democratic People's Republic Of Korea)
										</option>
										<option value="NOK"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'NOK'}"> selected="selected"</c:if>>
											Norwegian Krone (Norway)
										</option>
										<option value="PEN"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'PEN'}"> selected="selected"</c:if>>
											Nuevo Sol (Peru)
										</option>
										<option value="MRO"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'MRO'}"> selected="selected"</c:if>>
											Ouguiya (Mauritania)
										</option>
										<option value="TOP"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'TOP'}"> selected="selected"</c:if>>
											Pa'anga (Tonga)
										</option>
										<option value="PKR"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'PKR'}"> selected="selected"</c:if>>
											Pakistan Rupee (Pakistan)
										</option>
										<option value="XPD"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'XPD'}"> selected="selected"</c:if>>
											Palladium
										</option>
										<option value="MOP"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'MOP'}"> selected="selected"</c:if>>
											Pataca (Macau)
										</option>
										<option value="UYU"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'UYU'}"> selected="selected"</c:if>>
											Peso Uruguayo (Uruguay)
										</option>
										<option value="PHP"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'PHP'}"> selected="selected"</c:if>>
											Philippine Peso (Philippines)
										</option>
										<option value="XPT"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'XPT'}"> selected="selected"</c:if>>
											Platinum
										</option>
										<option value="PTE"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'PTE'}"> selected="selected"</c:if>>
											Portuguese Escudo (Portugal)
										</option>
										<option value="GBP"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'GBP'}"> selected="selected"</c:if>>
											Pound Sterling (United Kingdom)
										</option>
										<option value="BWP"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'BWP'}"> selected="selected"</c:if>>
											Pula (Botswana)
										</option>
										<option value="QAR"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'QAR'}"> selected="selected"</c:if>>
											Qatari Rial (Qatar)
										</option>
										<option value="GTQ"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'GTQ'}"> selected="selected"</c:if>>
											Quetzal (Guatemala)
										</option>
										<option value="ZAL"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'ZAL'}"> selected="selected"</c:if>>
											Rand (Financial) (Lesotho)
										</option>
										<option value="ZAR"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'ZAR'}"> selected="selected"</c:if>>
											Rand (South Africa)
										</option>
										<option value="OMR"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'OMR'}"> selected="selected"</c:if>>
											Rial Omani (Oman)
										</option>
										<option value="KHR"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'KHR'}"> selected="selected"</c:if>>
											Riel (Cambodia)
										</option>
										<option value="MVR"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'MVR'}"> selected="selected"</c:if>>
											Rufiyaa (Maldives)
										</option>
										<option value="IDR"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'IDR'}"> selected="selected"</c:if>>
											Rupiah (Indonesia)
										</option>
										<option value="RUB"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'RUB'}"> selected="selected"</c:if>>
											Russian Ruble (Russian Federation)
										</option>
										<option value="RUR"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'RUR'}"> selected="selected"</c:if>>
											Russian Ruble (Russian Federation)
										</option>
										<option value="RWF"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'RWF'}"> selected="selected"</c:if>>
											Rwanda Franc (Rwanda)
										</option>
										<option value="SAR"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'SAR'}"> selected="selected"</c:if>>
											Saudi Riyal (Saudi Arabia)
										</option>
										<option value="ATS"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'ATS'}"> selected="selected"</c:if>>
											Schilling (Austria)
										</option>
										<option value="SCR"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'SCR'}"> selected="selected"</c:if>>
											Seychelles Rupee (Seychelles)
										</option>
										<option value="XAG"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'XAG'}"> selected="selected"</c:if>>
											Silver
										</option>
										<option value="SGD"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'SGD'}"> selected="selected"</c:if>>
											Singapore Dollar (Singapore)
										</option>
										<option value="SKK"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'SKK'}"> selected="selected"</c:if>>
											Slovak Koruna (Slovakia)
										</option>
										<option value="SBD"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'SBD'}"> selected="selected"</c:if>>
											Solomon Islands Dollar (Solomon Islands)
										</option>
										<option value="KGS"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'KGS'}"> selected="selected"</c:if>>
											Som (Kyrgyzstan)
										</option>
										<option value="SOS"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'SOS'}"> selected="selected"</c:if>>
											Somali Shilling (Somalia)
										</option>
										<option value="ESP"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'ESP'}"> selected="selected"</c:if>>
											Spanish Peseta (Spain)
										</option>
										<option value="LKR"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'LKR'}"> selected="selected"</c:if>>
											Sri Lanka Rupee (Sri Lanka)
										</option>
										<option value="SHP"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'SHP'}"> selected="selected"</c:if>>
											St Helena Pound (St Helena)
										</option>
										<option value="ECS"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'ECS'}"> selected="selected"</c:if>>
											Sucre (Ecuador)
										</option>
										<option value="SDD"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'SDD'}"> selected="selected"</c:if>>
											Sudanese Dinar (Sudan)
										</option>
										<option value="SRG"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'SRG'}"> selected="selected"</c:if>>
											Surinam Guilder (Suriname)
										</option>
										<option value="SEK"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'SEK'}"> selected="selected"</c:if>>
											Swedish Krona (Sweden)
										</option>
										<option value="CHF"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'CHF'}"> selected="selected"</c:if>>
											Swiss Franc (Switzerland)
										</option>
										<option value="SYP"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'SYP'}"> selected="selected"</c:if>>
											Syrian Pound (Syrian Arab Republic)
										</option>
										<option value="TJR"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'TJR'}"> selected="selected"</c:if>>
											Tajik Ruble (Tajikistan)
										</option>
										<option value="BDT"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'BDT'}"> selected="selected"</c:if>>
											Taka (Bangladesh)
										</option>
										<option value="WST"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'WST'}"> selected="selected"</c:if>>
											Tala (Samoa)
										</option>
										<option value="TZS"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'TZS'}"> selected="selected"</c:if>>
											Tanzanian Shilling (United Republic Of Tanzania)
										</option>
										<option value="KZT"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'KZT'}"> selected="selected"</c:if>>
											Tenge (Kazakhstan)
										</option>
										<option value="TPE"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'TPE'}"> selected="selected"</c:if>>
											Timor Escudo (East Timor)
										</option>
										<option value="SIT"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'SIT'}"> selected="selected"</c:if>>
											Tolar (Slovenia)
										</option>
										<option value="TTD"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'TTD'}"> selected="selected"</c:if>>
											Trinidad and Tobago Dollar (Trinidad And Tobago)
										</option>
										<option value="MNT"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'MNT'}"> selected="selected"</c:if>>
											Tugrik (Mongolia)
										</option>
										<option value="TND"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'TND'}"> selected="selected"</c:if>>
											Tunisian Dinar (Tunisia)
										</option>
										<option value="TRL"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'TRL'}"> selected="selected"</c:if>>
											Turkish Lira (Turkey)
										</option>
										<option value="UGX"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'UGX'}"> selected="selected"</c:if>>
											Uganda Shilling (Uganda)
										</option>
										<option value="ECV"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'ECV'}"> selected="selected"</c:if>>
											Unidad de Valor Constante (Ecuador)
										</option>
										<option value="CLF"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'CLF'}"> selected="selected"</c:if>>
											Unidades de fomento (Chile)
										</option>
										<option value="USN"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'USN'}"> selected="selected"</c:if>>
											US Dollar (Next day) (United States)
										</option>
										<option value="USS"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'USS'}"> selected="selected"</c:if>>
											US Dollar (Same day) (United States)
										</option>
										<option value="USD"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'USD'}"> selected="selected"</c:if>>
											US Dollar (United States)
										</option>
										<option value="UZS"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'UZS'}"> selected="selected"</c:if>>
											Uzbekistan Sum (Uzbekistan)
										</option>
										<option value="VUV"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'VUV'}"> selected="selected"</c:if>>
											Vatu (Vanuatu)
										</option>
										<option value="KRW"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'KRW'}"> selected="selected"</c:if>>
											Won (Republic Of Korea)
										</option>
										<option value="YER"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'YER'}"> selected="selected"</c:if>>
											Yemeni Rial (Yemen)
										</option>
										<option value="JPY"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'JPY'}"> selected="selected"</c:if>>
											Yen (Japan)
										</option>
										<option value="CNY"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'CNY'}"> selected="selected"</c:if>>
											Yuan Renminbi (China)
										</option>
										<option value="ZWD"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'ZWD'}"> selected="selected"</c:if>>
											Zimbabwe Dollar (Zimbabwe)
										</option>
										<option value="PLN"
											<c:if test="${ paymentMethod.configData.x_currency_code eq 'PLN'}"> selected="selected"</c:if>>
											Zloty (Poland)
										</option>
									</select>
								</td>
							</tr>

							<tr>
								<td class="FieldLabel">
									Select Transaction Type
								</td>
								<td>
									<input type="radio" checked="" value="AUTH_CAPTURE" name="x_type" />
									Authorize and Capture
									<br />
									<input type="radio"	value="AUTH_ONLY" name="x_type" />
									Authorize Only
									<br />
									<input type="radio"	value="CAPTURE_ONLY" name="x_type" />
									Capture Only
									<br />
								</td>
							</tr>

							<tr>
								<td>
									Test/Live Mode
								</td>
								<td>
									<select name="testModel"
										class="Field400">
										<option value="1"
											<c:if test="${paymentMethod.configData.testModel==1}">selected</c:if>>
											Test
										</option>
										<option value="0"
											<c:if test="${paymentMethod.configData.testModel==0}">selected</c:if>>
											Live
										</option>
									</select>
								</td>
							</tr>
							<tr>
								<td>&nbsp;
									
								</td>
								<td align="left">
									<input type="hidden" name="paymentMethodId"
										value="${paymentMethod.paymentMethodId}" />
									<input type="hidden" name="doAction" value="saveConfigAction" />
									<input class="btn-back" type="submit"
										value="<fmt:message key="button.save"/>" />
									&nbsp;
									<input class="btn-back" type="button"
										value="<fmt:message  key="button.cancel"/>"
										onclick="location.href='${ctxPath}/system/paymentMethod.html';">
								</td>
							</tr>
						</table>
				</form>
				
	</body>
	</hmtl>