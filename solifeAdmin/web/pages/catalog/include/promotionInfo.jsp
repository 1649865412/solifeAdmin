<div id="promotionInfo" style="display:none">
	<div id="promotionArea" >
		<%-- 
		<jsp:include flush="true" page="/catalog/product/promotion/window.html">
			<jsp:param name="doAction" value="defaultAction" />
			<jsp:param name="productId" value="${product.productId}" />
		</jsp:include>
		--%>
	</div>
	<script type="text/javascript" defer="defer">
	var productId = "${product.productId}";
	$j(document).ready(function () {
		refresh();
	});
	function refresh(){
		var paraData = new Array();
		fillDivWithPage($j("#promotionArea"),"${ctxPath}/catalog/product/promotion/dialog.html?doAction=defaultAction&productId="+productId,paraData);
	}
	
	function editPromotion(url){
		fnNewWindow(url,600,800);
	}
	</script>
</div>