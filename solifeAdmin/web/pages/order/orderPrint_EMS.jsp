<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html; charset=UTF-8"%>
<style type="text/css">
<!--
* {
	margin: 0;
	padding: 0;
}

body {
	font-family: Tahoma, Verdana, Arial, Helvetica, sans-serif;
	font-size: 12px;
}

#wrap {
	background: url(${ctxPath}/images/ems.gif) no-repeat -12mm -3mm;
	width: 770px;
	height: 485px;;
	position: relative;
	font-size:18px;
}

#Layer3 {
	position: absolute;
	width: 321px;
	height: 214px;
	z-index: 1;
	left: 18px;
	top: 96px;
}

#Layer4 {
	position: absolute;
	width: 338px;
	height: 223px;
	z-index: 1;
	left:385px;
	top: 70px;
}

-->
</style>
<div class="notPrint">
	======================
	<span style="color: #f00;">寄件人信息不会被打印，背景图片只用作参考，并非实际效果。</span>=======================
	<br />
	========================
	<span style="color: #f00;">请注意：本页面只针对EMS“全球邮政特快专递”！,打印时需要进行特殊的设置。</span>========================
	<br />
	<br />
	<br />
</div>

<div id="wrap">
	<div id="Layer3" class="notPrint">
		<table width="100%" bgcolor="#eee">
		<tr>
			<td>FullName</td>
			<td>Left:<input id="Layer10_l" size="8" value="3.175mm" type="text">
				 Top:<input id="Layer10_t" size="8" value="5mm" type="text">
		    </td>
	    </tr>
	    <tr>
	    	<td>City</td>
	    	<td>Left:<input id="Layer11_l" size="8" value="3.175mm" type="text" >
			 Top:<input id="Layer11_t" size="8" value="16.6mm" type="text" >
			</td>
		</tr>
		<tr>
	    	<td>Country</td>
	    	<td>Left:<input id="Layer12_l" size="8" value="38.629mm" type="text" >
			 Top:<input id="Layer12_t" size="8" value="16.6mm" type="text" ">
			</td>
		</tr>
		<tr>
	    	<td>Address</td>
	    	<td>Left:<input id="Layer13_l" size="8" value="3.175mm" type="text" >
			 Top:<input id="Layer13_t" size="8" value="36.5mm" type="text" ">
			</td>
		</tr>
		<tr>
	    	<td>Postalcode</td>
	    	<td>Left:<input id="Layer14_l" size="8" value="15.292mm" type="text" >
			 Top:<input id="Layer14_t" size="8" value="63mm" type="text" ">
			</td>
		</tr>
		<tr>
	    	<td>Phone</td>
	    	<td>Left:<input id="Layer15_l" size="8" value="58.419mm" type="text" >
			 Top:<input id="Layer15_t" size="8" value="63mm" type="text" ">
			</td>
		</tr>
		<tr>
			<td></td>
			<td><a href="javascript:fnSaveSetting()">Click here to save setting.</a></td>
		</tr>
		</table>
	</div>
	<div id="Layer4">
		<div id="Layer10" style="position: absolute; width: 36mm; height: 10mm;z-index: 1;">
			${ship.orderAddress.firstname}&nbsp;${ship.orderAddress.lastname}
		</div>
		<div id="Layer11" style="position: absolute; width: 36mm; height: 10mm;	z-index: 2;">
			${ship.orderAddress.city},${ship.orderAddress.section}
		</div>
		<div id="Layer12" style="position: absolute; width: 100mm; height: 10mm; z-index: 3;">
			${ship.orderAddress.country}
		</div>
		<div id="Layer13" style="position: absolute; width: 100mm; height: 50mm; z-index: 3;">
		${ship.orderAddress.address1}<br/>
		${ship.orderAddress.address2},${ship.orderAddress.state}
		</div>
		<div id="Layer14" style="position: absolute; width: 100mm; height: 10mm; z-index: 3;">
			${ship.orderAddress.postalcode}
		</div>
		<div id="Layer15" style="position: absolute; width: 100mm; height: 10mm; z-index: 3;">
			${ship.orderAddress.phoneNumber}
		</div>
	</div>
</div>
<script type="text/javascript">
function fnSaveSetting()
{
	if (confirm("Comfirm Save?"))
	for (i = 0; i< 6; i++)
	{
		setCookie("print-Layer1"+i+"_l", $("Layer1"+i+"_l").value);
		setCookie("print-Layer1"+i+"_t", $("Layer1"+i+"_t").value);
		$("Layer1"+i).style.left=$("Layer1"+i+"_l").value;
		$("Layer1"+i).style.top =$("Layer1"+i+"_t").value;
	}
}
function fnInit()
{
	for (i = 0; i< 6; i++)
	{
		if (getCookie("print-Layer1"+i+"_l") && getCookie("print-Layer1"+i+"_t"))
		{
			//log("Has cookie:"+getCookie("print-Layer1"+i+"_l"));
			$("Layer1"+i+"_l").value = getCookie("print-Layer1"+i+"_l");
			$("Layer1"+i+"_t").value = getCookie("print-Layer1"+i+"_t");
		}
		else
		{
			setCookie("print-Layer1"+i+"_l", $("Layer1"+i+"_l").value);
			setCookie("print-Layer1"+i+"_t", $("Layer1"+i+"_t").value);
		}
		$("Layer1"+i).style.left=$("Layer1"+i+"_l").value;
		$("Layer1"+i).style.top =$("Layer1"+i+"_t").value;
	}
}
fnInit();
</script>