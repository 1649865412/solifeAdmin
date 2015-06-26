<%@ include file="/common/taglibs.jsp"%><head>
	<title><fmt:message key="mainMenu.title" /></title>
</head>
<script src="${ctxPath}/scripts/jquery/plugins/jqChart/jquery.jqChart.min.js" type="text/javascript"></script>
<!--[if IE]><script lang="javascript" type="text/javascript" src="${ctxPath}/scripts/jquery/plugins/jqChart/excanvas.js"></script><![endif]-->
<div class="blank24"></div>
<div class="stat">
    <div id="container">
	    <div id="orderDashboardContent"></div>
        <div id="productReviewDashboardContent"></div>
        <div id="feedbacksDashboardContent"></div>
		<div id="customerDashboardContent"></div>
		<div id="inventoryDashboardContent"></div>
		<div id="catalogDashboardContent"></div>
		<div id="salesPromotionDashboardContent"></div>
</div>
</div>
<script type="text/javascript" defer>
	function fnLast7DayOrderCreated(){
		$j.getJSON(__ctxPath+"/order/last7DayOrderCreated/dashboard.html",function (result){
			$j('#last7DayOrderCreatedChart').jqChart({
		      legend: {
		          visible : false
		      },
		      border: { lineWidth: 0, padding: 0 },
		      axes: [
		             {
		                 location: 'left',
		                 majorGridLines: {
		                     strokeStyle: '#c0bcbc'
		                 },
		                 minorGridLines: {
		                     strokeStyle: '#c0bcbc'
		                 }
		             }
		             ],
	            series: [
	                        {
	                            type: 'column',
	                            strokeStyle : 'black',
	                            data: result.data,
	                            labels: { font: '11px sans-serif', fillStyle : 'red' }
	                        }
	                    ]
	        });
		});
	}

	function fnLast7DayCustomerRegister(){
		$j.getJSON(__ctxPath+"/customer/last7DayCustomerRegister/dashboard.html",function (result){
			$j('#last7DayCustomerRegisterChart').jqChart({
		      legend: {
		          visible : false
		      },
		      border: { lineWidth: 0, padding: 0 },
		      axes: [
		             {
		                 location: 'left',
		                 majorGridLines: {
		                     strokeStyle: '#c0bcbc'
		                 },
		                 minorGridLines: {
		                     strokeStyle: '#c0bcbc'
		                 }
		             }
		             ],
	            series: [
	                        {
	                            type: 'line',
	                            data: result.data,
	                            labels: { font: '11px sans-serif', fillStyle : 'red' }
	                        }
	                    ]
	        });
		});
	}
	$j(document).ready(function(){
		$j("#catalogDashboardContent").load(__ctxPath+"/catalog/product/dashboard.html");
		$j("#productReviewDashboardContent").load(__ctxPath+"/catalog/productReview/dashboard.html");
		$j("#inventoryDashboardContent").load(__ctxPath+"/inventory/inventory/dashboard.html");
		$j("#customerDashboardContent").load(__ctxPath+"/customer/customer/dashboard.html",function(){
				fnLast7DayCustomerRegister();
			});
		$j("#orderDashboardContent").load(__ctxPath+"/order/salesOrder/dashboard.html",function(){
				fnLast7DayOrderCreated();
			});
		$j("#salesPromotionDashboardContent").load(__ctxPath+"/sales/promoRule/dashboard.html");
		$j("#feedbacksDashboardContent").load(__ctxPath+"/customer/feedback/dashboard.html");
		$j("#feedbacksDashboardContent").load(__ctxPath+"/customer/feedback/dashboard.html");
		//由于仪表板不需要完全加载都可以让用户操作的,所以关闭loading销屏功能.
		fnHideLoading();
	});
</script>