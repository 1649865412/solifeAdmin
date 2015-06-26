<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Testing page</title>
		<%@ include file="/decorators/include/styles.jspf"%>
		<%@ include file="/decorators/include/javascripts.jspf"%>

		<style type="text/css">
.attribute_description {
	background-color: green;
}
</style>
	</head>
	<body >
        <app:ui_quickTip/>
		<form class="mainForm" action="testAttribute.html?doAction=editAttribute" method="post"
			onsubmit="return validateForm(this);">
			<input type="submit" value="Submit">
			<attribute:list var="attr" type="Product" />
			<table >

				<c:forEach var="item1" items="${attr}">
					<tr>
						<td>
							${item1.attributeName}:
						</td>
						<td>
								<attribute:input isdisplayHelp="true" entity="${mo}" attribute="${item1}" />
							
						</td>
				</c:forEach>
                 <tr>
                   <td colspan="2"><cartmatic:ui_tip id="testId">测试，<h2>测试</h2></cartmatic:ui_tip> </td>
                 </tr>
                 

			</table>

			<br />

		</form>

		
		<table align="right" width="100%">
                 <tr>                                                                                        
                   <td align="right"><cartmatic:ui_tip id="testId2"><h2>测试</h2><p>这是用ui_tip标签做的</p></cartmatic:ui_tip></td>
                 </tr> 
                 <tr>
                 <td align="right"><span source="text" type="help" text='${ctxPath }/pages/test/ajax5.htm' class="PTips">QuickTip Test Text形式</span></td>
                 </tr> 
                  <tr>
                 <td align="left"><span source="ajax" type="help" link="http://www.baidu.com"  href='${ctxPath }/pages/test/ajax5.htm' class="PTips">QuickTip Test Ajax形式</span></td>
                 </tr>
                 <tr><td height="50px"></td></tr>
                    
		</table>
		
		
		<script type="text/javascript" defer>           
			autoApplyValidate();
		</script>
		<%@ include file="../../decorators/include/uiResource.jspf"%>
	</body>
</html>
