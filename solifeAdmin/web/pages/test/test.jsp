<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Testing page</title>
		<%@ include file="/decorators/include/javascripts.jspf"%>
		<%@ include file="/decorators/include/styles.jspf"%>
	</head>
	<body>
		<form class="mainForm" action="${ctxPath}/test/test2.html" method="POST"
			id="shoppingcart" name="shoppingcart">
			<a href="javascript:void(0);"
				onclick="fnSaveForLater(${item.shoppingCartItemId});return false;"
				title="<fmt:message key='shoppingcart.saveForLater'/>"> <img
					src="${ctxPath}/images/btn/btn_move.gif"
					alt="<fmt:message key='shoppingcart.saveForLater'/>" /> </a> …
		</form>

		<script type="text/javascript">
		function fnSaveForLater(id){
			document.shoppingcart.submit();
		}
</script>
<%
String paraDesc="options#select#Integer#{1|苹果~2|香蕉~3|桔子}";
int index1 = paraDesc.indexOf("#{");
 if(paraDesc.startsWith("options#mutibox"))
                    	System.out.println("options#mutibox");
                    else if(paraDesc.startsWith("options#select")){
                    	System.out.println("options#select");
                    }
                    System.out.println("index1="+index1);
                    int typeIndex=paraDesc.lastIndexOf('#', index1-1);
                    System.out.println("typeIndex="+typeIndex);
                    System.out.println("index1-2="+(index1-2));
                    if(typeIndex+1<index1-2){
                    	System.out.println("result====="+paraDesc.substring(typeIndex+1,index1-1));
                    }
 %>
	</body>
</html>
