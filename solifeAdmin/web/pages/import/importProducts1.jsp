<%@ include file="/common/taglibs.jsp"%>
<title><fmt:message key="product.import.title1" />
</title>
<%@ page contentType="text/html; charset=UTF-8"%>
<content tag="heading">
<fmt:message key="product.import.title1" />
</content>
<spring:bind path="fileUpload.*">
	<c:if test="${not empty status.errorMessages}">
		<div class="error">
			<c:forEach var="error" items="${status.errorMessages}">
				<img src="<c:url value="/images/iconWarning.gif"/>"
					alt="<fmt:message key="icon.warning"/>" class="icon" />
				<c:out value="${error}" escapeXml="false" />
				<br />
			</c:forEach>
		</div>
	</c:if>
</spring:bind>
<c:set var="buttons">
	<cartmatic:cartmaticBtn btnType="cancel" onclick="bCancel=true;" />
	<cartmatic:cartmaticBtn btnType="next" />
</c:set>
<content tag="form-start">
<form id="fileUpload" name="fileUpload"
	action="<c:url value="/catalog/importProducts1.html"/>" method="post"
	enctype="multipart/form-data">
	</content>
	<content tag="buttons">
		<c:out value="${buttons}" escapeXml="false" />
	</content>
	<div class="box-content-wrap">
		<div class="box-content">
			<div class="top">
				<div class="content-title">
					<fmt:message key="product.import1.desc1" />
					:&nbsp;&nbsp;
					<b><fmt:message key="product.import1.desc12" />
					</b>
				</div>
			</div>

			<div class="content">
				<table class="table-content" width="100%" border="0"
					cellspacing="0
				cellpadding="0">


					<tr>
						<td>
							<fmt:message key="product.import1.desc13">
								<fmt:param>
									<c:url value="/download/import/productSample.rar" />
								</fmt:param>
							</fmt:message>
						</td>
					</tr>
					<tr>
						<td>
							<input type="file" name="file" id="file" size="50"
								class="form-inputbox" />
						</td>
					</tr>

				</table>
			</div>
		</div>
	</div>

</form>
