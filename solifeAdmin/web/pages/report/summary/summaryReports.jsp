<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib prefix="report" tagdir="/WEB-INF/tags/report"%>
<html>
	<head>
		<title><fmt:message key="menu.summaryReports" />
		</title>
	</head>
	<body>
		<content tag="heading">
		<fmt:message key="menu.summaryReports" />
		</content>
		<c:if test="${appConfig.isAlertLicense==true}">
			<content tag="description">
			<fmt:message key="license.alert" />
			</content>
		</c:if>
		<content tag="buttons">
		<ul>
			<li>
				<cartmatic:cartmaticBtn btnType="cancel" onclick="return history.back();" />
			</li>
		</ul>
		</content>
		<div id="reports">
			<div class="box-content-wrap" id="salesStats">
			</div>
			<div class="box-content-wrap" id="noStockProduct">
			</div>
			<div class="box-content-wrap" id="notProcessedOrders">
			</div>
			<div class="box-content-wrap" id="storeStats">
			</div>
			<div class="box-content-wrap" id="storeStatsTimed">
			</div>
			<div class="box-content-wrap" id="hotSalesProduct">
			</div>
			<div class="box-content-wrap" id="hotSalesCategory">
			</div>
		</div>
		<script type="text/javascript"
			src="${ctxPath}/scripts/cartmatic/report/summaryReport.js"></script>
	</body>
</html>
