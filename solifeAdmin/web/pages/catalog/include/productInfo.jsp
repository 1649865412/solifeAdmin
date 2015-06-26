<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib prefix="customer" tagdir="/WEB-INF/tags/customer"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="productInfo" >
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
		<tr>
			<th colspan="2">
				<fmt:message key="productDetail.catalog" />
				<input type="hidden" name="productId" id="productId" value="${product.productId}" />
				<input type="hidden" id="isOriented" value="${isOriented}" />
				<input type="hidden" id="orientedTab" value="${param.orientedTab}" />
				<input type="hidden" id="isPackage" name="isPackage" value="${isPackage}" />
				<input type="hidden" id="tid" value="${param.tid}" />
			</th>
		</tr>
		<%--产品目录 设置    start --%>
		<c:choose>
			<c:when test="${empty product.productId}">
				<tr>
					<td class="FieldLabel">
						<StoreAdmin:label key="productDetail.mainCategory" />
					</td>
					<td>
						<input name="mainCategoryName" id="mainCategoryName" class="Field400" type="text" value="${category.categoryName}" readonly="readonly" />
						<input name="mainCategoryId" id="mainCategoryId" type="hidden" value="${category.categoryId}" />
					</td>
	            </tr>
	            <tr>
					<td class="FieldLabel">
						<StoreAdmin:label key="productDetail.additionalCategories" />
					</td>
					<td>
						<select id="categoryIds_${category.catalog.id}" name2="categoryIds" size="5" multiple="multiple" class="Field400" >
							<c:forEach items="${product.productCategorys}" var="productCategory">
								<option value="${productCategory.category.categoryId}" >
									${productCategory.category.categoryName}----[${productCategory.category.categoryCode}]
								</option>
							</c:forEach>
						</select>
						&nbsp;&nbsp;
						<input type="image" src="${ctxPath}/images/select.gif" onclick="try{CategorySelector_${category.catalog.id}_show(${category.catalog.id});}catch(e){alert(e.message);}return false;" title="<fmt:message key="productDetail.msg.addCategory" />" />
						<input type="image" src="${ctxPath}/images/clear_field.gif" onclick="try{fnDeleteCategory(${category.catalog.id});}catch(e){alert(e.message);}return false;" title="<fmt:message key="productDetail.msg.removeCategory" />" />
					</td>
	            </tr>
			</c:when>
			<c:otherwise>
				<c:forEach items="${catalogList}" var="catalog">
					<tr name="catalog_content_${catalog.id}">
						<td class="FieldLabel">
							${catalog.name}
						</td>
						<td>
							&nbsp;<c:if test="${catalog.isVirtual==1}"><input type="button" title="Remove" class="close" value="" onclick="fnRemoveCatalogContent('catalog_content_${catalog.id}');"></c:if>
						</td>
		            </tr>
					<tr name="catalog_content_${catalog.id}">
						<td class="FieldLabel">
							<StoreAdmin:label key="productDetail.mainCategory" />
						</td>
						<td>
							<select id="mainCategoryId_${catalog.id}" name="mainCategoryId" class="Field400">
								<c:forEach items="${product.productCategorys}" var="productCategory">
									<c:if test="${productCategory.category.catalog==catalog}">
									<option value="${productCategory.category.categoryId}" <c:if test="${productCategory.isMainCategory==1}">selected="selected"</c:if>>
										${productCategory.category.categoryName}----[${productCategory.category.categoryCode}]
									</option>
									</c:if>
								</c:forEach>
							</select>
						</td>
		            </tr>
		            <tr name="catalog_content_${catalog.id}">
						<td class="FieldLabel">
							<StoreAdmin:label key="productDetail.category" />
						</td>
						<td>
							<select id="categoryIds_${catalog.id}" name2="categoryIds" size="5" multiple="multiple" class="Field400">
								<c:forEach items="${product.productCategorys}" var="productCategory">
									<c:if test="${productCategory.category.catalog==catalog}">
									<option value="${productCategory.category.categoryId}">
										${productCategory.category.categoryName}----[${productCategory.category.categoryCode}]
									</option>
									</c:if>
								</c:forEach>
							</select>
							&nbsp;&nbsp;
							<input type="image" src="${ctxPath}/images/select.gif" onclick="try{CategorySelector_${catalog.id}_show(${catalog.id});}catch(e){alert(e.message);}return false;" title="<fmt:message key="productDetail.msg.addCategory" />" />
							<input type="image" src="${ctxPath}/images/clear_field.gif" onclick="try{fnDeleteCategory(${catalog.id});}catch(e){alert(e.message);}return false;" title="<fmt:message key="productDetail.msg.removeCategory" />" />
						</td>
		            </tr>
	            </c:forEach>
			</c:otherwise>
		</c:choose>
		<%--产品目录 设置    end --%>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
		<tr>
			<th colspan="2">
				<fmt:message key="productDetail.required.attrubute" />
			</th>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="product.productName" />
			</td>
			<td>
				<input type="text" class="Field400" name="productName" id="productName" value="<c:out value="${product.productName}"/>"/>
			</td>
        </tr>
        <tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="product.productCode" />
			</td>
			<td>
				<input type="text" class="Field400" name="productCode" id="productCode" value="${product.productCode}" />
				(<fmt:message key="product.productCode.tips" />)
			</td>
        </tr>
        <tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="product.productTypeId" />
			</td>
			<td>
				<select class="Field400" id="productTypeId" name="productTypeId" onchange="fnSelectProductTypeHandler();" <c:if test="${not empty product.productId}">disabled="disabled"</c:if>>
					<option value=""></option>
					<c:forEach items="${productTypeList}" var="productType">
						<option value="${productType.productTypeId}" <c:if test="${product.productType.productTypeId==productType.productTypeId}">selected="selected"</c:if>>
							${productType.productTypeName}
						</option>
					</c:forEach>
				</select>
				&nbsp;&nbsp;
				<input type="hidden" id="hasActiveSkuOptionProductTypeIds" value="${hasActiveSkuOptionProductTypeIds}" />
				<span id="isVariationSpan" <c:if test="${product.productKind!=2}">style="display:none"</c:if>> <input type="checkbox" onclick="if(this.checked){$j('#isVariation').val('true');}else{$j('#isVariation').val('false');}" <c:if test="${product.productKind==2}">checked="checked" disabled="disabled"</c:if> /> <input type="hidden" name="isVariation" id="isVariation" /> <fmt:message key="product.isVariation" /> </span>
			</td>
        </tr>
        <tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="product.status" />
			</td>
			<td>
				<c:choose>
					<c:when test="${(not empty product.productId)&&product.status==1||product.status==2}">
						<product:productStatus name="productStatus" value="${product.status}" />
					</c:when>
					<c:otherwise>
						<product:productStatus value="${empty product.status ? 0 : product.status}" />
					</c:otherwise>
				</c:choose>
			</td>
        </tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="product.availabilityRule" />
			</td>
			<td>
				<select class="Field400" id="availabilityRule" name="availabilityRule" onchange="fnSelectAvailabilityRuleHandler(this.value);">
						<c:if test="${empty product.productId}"><option value=""></option></c:if>
						<c:forEach begin="1" end="5" var="availabilityRule" varStatus="varStatus">
						<option value="${availabilityRule}" <c:if test="${product.availabilityRule==availabilityRule||(empty product.productId&&catalogEntity.availabilityRule==availabilityRule)}">selected="selected"</c:if>>
							<fmt:message key="product.availabilityRule_${availabilityRule}" />
						</option>
					</c:forEach>
				</select>
				<input type="hidden" id="origAvailabilityRule" value="${product.origAvailabilityRule}">
			</td>
        </tr>
        <tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="product.minOrderQuantity" />
			</td>
			<td>
				<input class="Field400" type="text" name="minOrderQuantity" id="minOrderQuantity" value="${empty product.minOrderQuantity ? 1 : product.minOrderQuantity}" />
			</td>
        </tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="product.preOrBackOrderLimit" />
			</td>
			<td>
				<input class="Field400" name="preOrBackOrderLimit" id="preOrBackOrderLimit" type="text" value="${product.preOrBackOrderLimit}" <c:if test="${empty product.availabilityRule||product.availabilityRule==1||product.availabilityRule==4||product.availabilityRule==5}">disabled="disabled"</c:if> /><fmt:message key="product.preOrBackOrderLimit.desc" />
			</td>
        </tr>
        <tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="product.expectedReleaseDate" />
			</td>
			<td>
				<input class="Field400" name="expectedReleaseDate" id="expectedReleaseDate" type="text" readonly="true" <c:if test="${product.availabilityRule==2}">value="<fmt:formatDate value="${product.expectedReleaseDate}" pattern="yyyy-MM-dd" />"</c:if> <c:if test="${empty product.availabilityRule||product.availabilityRule==1||product.availabilityRule==3||product.availabilityRule==4||product.availabilityRule==5}">disabled="disabled"</c:if> />
				<app:ui_datePicker outPut="expectedReleaseDate" isHide="${empty product.availabilityRule||product.availabilityRule==1||product.availabilityRule==3||product.availabilityRule==4||product.availabilityRule==5}" />
			</td>
        </tr>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
		<tr>
			<th colspan="2">
				<fmt:message key="productDetail.optional.attrubute" />
			</th>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="product.brandId" />
			</td>
			<td>
				<select class="Field400" name="brandId" id="brandId">
					<option value=""></option>
					<c:forEach items="${brandList}" var="brand">
						<option value="${brand.brandId}" <c:if test="${product.brand.brandId==brand.brandId}">selected="selected"</c:if>>
							${brand.brandName}
						</option>
					</c:forEach>
				</select>
			</td>
        </tr>
        <tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="product.isAllowReview" />
			</td>
			<td>
				<input type="checkbox" onclick="if(this.checked){$j('#isAllowReview').val(1);}else{$j('#isAllowReview').val(0);}" <c:if test="${not empty product.isAllowReview ? (product.isAllowReview==1) : appConfig.isCreateProductDefaultAllowReviewEnabled}">checked="checked"</c:if> />
				<input type="hidden" name="isAllowReview" id="isAllowReview" value="${not empty product.isAllowReview ? product.isAllowReview : (appConfig.isCreateProductDefaultAllowReviewEnabled ? 1 : 0)}" />
			</td>
        </tr>
        <tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="product.templatePath" />
			</td>
			<td>
				<app:templateSelect templateList="${appConfig.productTemplates}" name="templatePath" classes="Field400" value="${product.templatePath}"></app:templateSelect>
			</td>
        </tr>
        <tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="product.planStartTime" />
			</td>
			<td>
				<input class="Field400" name="planStartTime" id="planStartTime" type="text" readonly="true" value="<fmt:formatDate value="${product.planStartTime}" pattern="yyyy-MM-dd" />" />
				<app:ui_datePicker outPut="planStartTime" />
			</td>
        </tr>
        <tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="product.planEndTime" />
			</td>
			<td>
				<input class="Field400" name="planEndTime" id="planEndTime" type="text" readonly="true" value="<fmt:formatDate value="${product.planEndTime}" pattern="yyyy-MM-dd" />" />
				<app:ui_datePicker outPut="planEndTime" />
			</td>
        </tr>
        <c:if test="${not empty product.productId}">
        <tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="product.publishTime" />
			</td>
			<td>
				<span id="publishTime_span"><fmt:formatDate value="${product.publishTime}" pattern="yyyy-MM-dd HH:mm:ss" /></span><cartmatic:iconBtn icon="pen" textKey="common.link.edit" id="u_publishTime_btn" onclick="$j('#update_publishTime_form').show();$j(this).hide();"/><br/>
				<fieldset id="update_publishTime_form" style="display: none;width: 65%;">
					<legend>修改发布时间</legend>
					<input id="input_publishTime" value="<fmt:formatDate value="${product.publishTime}" pattern="yyyy-MM-dd HH:mm:ss" />" type="text">
					<cartmatic:iconBtn icon="check" textKey="common.link.save" onclick="fnUpdateProductPublishTime();"/>
					<cartmatic:iconBtn icon="cross" textKey="common.link.cancel" onclick="$j('#update_publishTime_form').hide();$j('#u_publishTime_btn').show();"/>
				</fieldset>
			</td>
        </tr>
        </c:if>
	</table>
	<c:if test="${isOriented}">
		<div class="add-btn">
			<cartmatic:cartmaticBtn btnType="common" onclick="fnTabOriented(2,1)" commonBtnValueKey="productDetail.next"/>
		</div>
	</c:if>
	<c:forEach items="${catalogList}" var="catalog">
		<product:categorySelector id="CategorySelector_${catalog.id}" catalogId="${catalog.id}" ondblclick="fnAddCategoryHandler" canSelectRoot="false"></product:categorySelector>
	</c:forEach>
	<c:if test="${empty catalogList}">
		<product:categorySelector id="CategorySelector_${category.catalog.id}" catalogId="${category.catalog.id}" ondblclick="fnAddCategoryHandler" canSelectRoot="false"></product:categorySelector>
	</c:if>
	<c:if test="${empty product.productId}">
	<script type="text/javascript">
	<%--设置产品类型关联的相关数据岛,新增产品时选择产品类型时，自动产品类型设置的默认显示模板及最少订购量--%>
	var productTypeJSONList=eval(${productTypeJSONList});
	</script>
	</c:if>
	<script type="text/javascript">
	function fnRemoveCatalogContent(name){
		$j("tr[name='"+name+"']").remove();
	}
	</script>
</div>
