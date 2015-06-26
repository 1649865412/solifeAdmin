<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<app:pageHeading entityName="${store.storeName}" entityHeadingKey="storeDetail.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this);" />
	<%--
	<cartmatic:cartmaticBtn btnType="saveAndNext" onclick="return fnDoSaveAndNext(this);" />
	--%>
    <c:if test="${store.storeId!=null}">
		<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoDelete(this);" />
	</c:if>
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="store.*" />
	<form:form method="post" cssClass="mainForm" id="store" commandName="store"
			action="${ctxPath}/system/store.html" onsubmit="return validateStore(this);">
		<form:hidden path="version" />
		<input type="hidden" name="storeId" value="${store.storeId}"/> 
		<table class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="${entityClassName}.catalogId" />
			</td>
			<td>
				<select name="catalogId" id="catalogId">
					<option value=""></option>
					<c:forEach items="${catalogList}" var="catalog">
						<option value="${catalog.id}"<c:if test="${catalog.id==store.catalogId}"> selected="selected"</c:if>>${catalog.name}</option>
					</c:forEach>
				</select>
				<form:errors path="catalogId" cssClass="fieldError" />
			</td>
		</tr>
 		<app:input property="name" />
 		<app:input property="code" />
 		<app:input property="siteUrl" />
 		<app:input property="description" />
 		<tr>
		<td class="FieldLabel">
			<StoreAdmin:label key="${entityClassName}.status" />
		</td>
		<td>
			<app:statusCheckbox name="status" value="${store.status}"/>
			<form:errors path="status" cssClass="fieldError" />
		</td>
		</tr>
		<tr>
		<td class="FieldLabel">
			<StoreAdmin:label key="${entityClassName}.paymentMethod" />
		</td>
		<td>
			<c:forEach items="${paymentMethodList}" var="paymentMethod" varStatus="varStatus">
			<input type="checkbox" id="pm_${paymentMethod.paymentMethodId}" value="${paymentMethod.paymentMethodId}" 
			<c:forEach items="${store.paymentMethods}" var="pm">
			<c:if test="${pm.paymentMethodId == paymentMethod.paymentMethodId}">checked="checked"</c:if>
			</c:forEach> name="paymentMethodIds"> 
			<label for="pm_${paymentMethod.paymentMethodId}">${paymentMethod.paymentMethodName}&nbsp;(${paymentMethod.paymentMethodCode})</label><br/>
			<c:if test="${varStatus.last}">
				<script type="text/javascript">
					applyValidate($("pm_${paymentMethod.paymentMethodId}"), "required");
				</script>
			</c:if>
			</c:forEach>			
		</td>
		</tr>
		<tr>
		<td class="FieldLabel">
			<StoreAdmin:label key="${entityClassName}.shippingMethod" />
		</td>
		<td>
			<c:forEach items="${shippingMethodList}" var="shippingMethod" varStatus="varStatus">
			<input type="checkbox" id="sm_${shippingMethod.shippingMethodId}" value="${shippingMethod.shippingMethodId}" 
			<c:forEach items="${store.shippingMethods}" var="pm">
			<c:if test="${pm.shippingMethodId == shippingMethod.shippingMethodId}">checked="checked"</c:if>
			</c:forEach> name="shippingMethodIds"> 
			<label for="sm_${shippingMethod.shippingMethodId}">${shippingMethod.shippingMethodName}</label><br/>
			<c:if test="${varStatus.last}">
				<script type="text/javascript">
					applyValidate($("sm_${shippingMethod.shippingMethodId}"), "required");
				</script>
			</c:if>
			</c:forEach>
		</td>
		</tr>
  	</table>
</form:form>

<v:javascript formName="store" staticJavascript="false" />
<script type="text/javascript">
    document.forms["store"].elements["catalogId"].focus();
</script>
