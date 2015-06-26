<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Testing page</title>
		<%@ include file="/decorators/include/styles.jspf"%>
		<%@ include file="/decorators/include/javascripts.jspf"%>
		<script type="text/javascript">
			var __fctxPath="http://192.168.1.123:8080/eStore";
			function fnGetPassword(){
				var data=$j("#getPasswordForm").serialize();
				$j.post(__fctxPath+"/customer/forgetPassword.html",data);
			}

			function fnRegister(){
				log($j("#registerForm").serialize());
				log($j("#registerForm").serializeArray());
				var data=$j("#registerForm").serializeArray();
				$j.post(__fctxPath+"/customer/register.html",data);
			}
			
		</script>
	</head>
	<body>
		<form id="getPasswordForm">
			email:
			<input type="text" name="email" />
			<input type="hidden" name="doAction" value="requestEmailAction" />
			<input id="b2" type="button" class="admin-btn" value="取回密码" onclick="fnGetPassword();" />
		</form>
		<hr />
		<form id="registerForm">
			帐号:
			<input type="text" name="username" />
			<br/>
			电子邮件:
			<input type="text" name="email" />
			<br/>
			确认邮箱 :
			<input type="text" name="reEmail" />
			<br/>
			密码:
			<input type="text" name="password" />
			<br/>
			确认密码:
			<input type="text" name="rePassword" />
			<br/>
			<input type="button" class="admin-btn" value="注册" onclick="fnRegister();" />
		</form>
		<hr />
	</body>
</html>