<%
	//Description : post payment parameters to payment gateway;
	//author: mansan
%>
<script type="text/javascript">
	window.onload=function(){	
		document.form["paymentForm"].submit();
	}
</script>
<%
	String form=request.getAttribute("form").toString();
%>
<%=form%>