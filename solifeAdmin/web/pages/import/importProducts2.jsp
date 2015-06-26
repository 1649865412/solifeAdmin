<%@ include file="/common/taglibs.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="catalog" tagdir="/WEB-INF/tags/catalog"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<head>
	<title><fmt:message key="product.import.title2" />
	</title>
</head>
<c:set var="buttons">
	<cartmatic:cartmaticBtn btnType="cancel" onclick="location.href='${ctxPath}/catalog/categorys.html'" />
	<cartmatic:cartmaticBtn btnType="nextStep" />
</c:set>
<content tag="form-start">
<form name="form" method="post" action="product.html">
	</content>
	<input type="hidden" name="doAction" value="importProduct" />

	<content tag="buttons">
	<c:out value="${buttons}" escapeXml="false" />
	</content>



	<div class="box-content-wrap">
		<div class="box-content">
			<div class="top">
				<div class="content-title">
					<fmt:message key="import.importInfo" />
				</div>
			</div>
			<div class="content">
				<fmt:message key="import.import.file.rowCount" />
				:${rowCount}
			</div>
		</div>
	</div>
</form>

