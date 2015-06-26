<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>
<div id="productSkuForm" style="display: none;width:100%;*width:97.8%">
	<c:if test="${product.productKind==2}">
		<ul id="tabs-nav2">
			<li>
				<a href="#productSkuBasicInfo"><fmt:message key="productSku.tab.basic" /> </a><span id="productSkuBasicInfoTabFlag"></span>
			</li>
			<li>
				<a href="#productSkuSkuOptionInfo"><fmt:message key="productSku.tab.skuOption" /> </a><span id="productSkuSkuOptionInfoTabFlag"></span>
			</li>
		</ul>
		<div class="clear"></div>
	</c:if>
	<div id="productSkuBasicInfo">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
			<tr>
				<th colspan="2">
					<fmt:message key="productDetail.basis.attrubute" />
					<input type="hidden" id="productSkuId" name="productSkuId" value="${productSku.productSkuId}" />
				</th>
			</tr>
			<tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="productSku.productSkuCode" />
				</td>
				<td>
					<input type="text" class="Field200" name="productSkuCode" id="productSkuCode" value="${not empty productSku.id ? productSku.productSkuCode : (product.productKind != 2 ? product.productCode : productSku.productSkuCode)}" />
					<%--条码
					<c:if test="${product.status!=0}">
						<cartmatic:iconBtn icon="tag" textKey="common.link.printBarCode"  onclick="fnPrintBarCode('${productSku.id}');" />
					</c:if>
					 --%>
				</td>
             </tr>
             <tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="productSku.image" />
				</td>
				<td>
					<div style="float: left;">
						<cartmatic:img url="${productSku.image}" id="productImage" mediaType="product" size="b" width="72" height="72"></cartmatic:img>
					</div>
					<div style="float: left; margin: 20px;">
						<input type="hidden" name="image" id="image" value="${productSku.image}" />
						<span id="skuImgBtnPlaceHolderId"></span>
						<br/>
						(<fmt:message key="productSku.image.desc" />)
					</div>
				</td>
             </tr>
             <tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="productSku.skuKind" />
				</td>
				<td>
					<product:skuKind name="skuKind" value="${productSku.skuKind}" />
				</td>
             </tr>
			<c:if test="${product.productKind==2}">
             <tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="productSku.status" />
				</td>
				<td>
					<select class="Field200" id="productSkuStatus" name="productSkuStatus">
						<option value="1" <c:if test="${productSku.status==1}">selected="selected"</c:if>>
							<fmt:message key="status.active" />
						</option>
						<option value="2" <c:if test="${productSku.status==2}">selected="selected"</c:if>>
							<fmt:message key="status.inactive" />
						</option>
					</select>
				</td>
            </tr>
            </c:if>
		</table>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
			<tr>
				<th colspan="2">
					<fmt:message key="productSkuDetail.basic.pricing" />
				</th>
			</tr>
			<tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="productSku.costPrice" />
				</td>
				<td>
					<input type="text" class="Field200" name="costPrice" id="costPrice" value="${productSku.costPrice}" />
				</td>
             </tr>
             <tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="productSku.price" />
				</td>
				<td>
					<input type="text" class="Field200" name="price" id="price" value="${productSku.price}"/>
				</td>
             </tr>
             <tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="productSku.salePrice" />
				</td>
				<td>
					<input type="text" class="Field200" name="salePrice" id="salePrice" value="${productSku.salePrice}"/>
				</td>
             </tr>
             <tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="productSku.listPrice" />
				</td>
				<td>
					<input type="text" class="Field200" name="listPrice" id="listPrice" value="${productSku.listPrice}"/>
				</td>
             </tr>
       	</table>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
			<tr>
				<th>
					<fmt:message key="wholesalePrice.wholesalePriceId" />
				</th>
			</tr>
			<tr>
				<td>
					<table cellspacing="0" cellpadding="0" id="productSkuWholesalePricesForm">
						<tr>
							<th width="50"><fmt:message key="wholesalePrice.minQuantity" /></th>
							<th width="100"><fmt:message key="wholesalePrice.price" /></th>
							<th><cartmatic:iconBtn icon="plus" textKey="common.link.add" onclick="fnAddWholesalePrice();" /></th>
						</tr>
						<c:forEach items="${productSku.wholesalePrices}" var="wholesalePrice">
							<tr>
								<td><input class="Field40" name="wholesalePrice_minQuantity" id="height" type="text" value="${wholesalePrice.minQuantity}"/></td>
								<td><input class="Field100" name="wholesalePrice_price" id="height" type="text" value="${wholesalePrice.price}"/></td>
								<td><cartmatic:iconBtn icon="cross" extraCss="negative" textKey="common.link.delete" onclick="fnDeleleTableTR(this);" /></td>
							</tr>
						</c:forEach>
					</table>
				</td>
             </tr>
		</table>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
			<tr>
				<th colspan="2">
					<fmt:message key="productDetail.product.volume" />
				</th>
			</tr>
             <tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="productSku.weight" />
				</td>
				<td>
					<input type="text" class="Field200" name="weight" id="weight" value="${productSku.weight}" /><c:if test="${not empty appConfig.weightUnit}">&nbsp;(${appConfig.weightUnit})</c:if>
				</td>
             </tr>
             <tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="productSku.length" />
				</td>
				<td>
					<input type="text" class="Field200" name="length" id="length" value="${productSku.length}" /><c:if test="${not empty appConfig.lengthUnit}">&nbsp;(${appConfig.lengthUnit})</c:if>
				</td>
             </tr>
             <tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="productSku.width" />
				</td>
				<td>
					<input type="text" class="Field200" name="width" id="width" value="${productSku.width}" /><c:if test="${not empty appConfig.lengthUnit}">&nbsp;(${appConfig.lengthUnit})</c:if>
				</td>
             </tr>
             <tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="productSku.height" />
				</td>
				<td>
					<input type="text" class="Field200" name="height" id="height" value="${productSku.height}" /><c:if test="${not empty appConfig.lengthUnit}">&nbsp;(${appConfig.lengthUnit})</c:if>
				</td>
             </tr>
       	</table>
       	<c:if test="${not empty inventory}">
       	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
			<tr>
				<th colspan="2">
					<fmt:message key="productSkuDetail.inventory.details" />
				</th>
			</tr>
             <tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="inventory.quantityOnHand" ignoreValidation="true"/>
				</td>
				<td>
					${inventory.quantityOnHand}
				</td>
             </tr>
             <tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="inventory.availableQuantity" />
				</td>
				<td>
					${inventory.availableQuantity}
				</td>
             </tr>
             <tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="inventory.allocatedQuantity" ignoreValidation="true"/>
				</td>
				<td>
					${inventory.allocatedQuantity}
				</td>
             </tr>
             <tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="inventory.reservedQuantity" ignoreValidation="true"/>
				</td>
				<td>
					${inventory.reservedQuantity}
				</td>
             </tr>
             <tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="inventory.reorderMinimnm" ignoreValidation="true"/>
				</td>
				<td>
					${inventory.reorderMinimnm}
				</td>
             </tr>
             <tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="inventory.reorderQuantity" />
				</td>
				<td>
					${inventory.reorderQuantity}
				</td>
             </tr>
             <tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="inventory.expectedRestockDate" />
				</td>
				<td>
					<fmt:formatDate value="${inventory.expectedRestockDate}" pattern="yyyy-MM-dd" />
				</td>
             </tr>
             <c:if test="${product.availabilityRule==3&&not empty inventory.expectedRestockDate}">
             <tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="inventory.expectedRestockDate" />
				</td>
				<td>
					<fmt:formatDate value="${inventory.expectedRestockDate}" pattern="yyyy-MM-dd" />
				</td>
             </tr>
             </c:if>
             <authz:authorize url="/inventory/">
			 <tr>
				 <td colspan="2">
				 	<cartmatic:iconBtn icon="cog" textKey="productSkuDetail.inventory.setting" onclick="fnEditInventory(${product.productId},${productSku.productSkuId});" />
				 </td>
			 </tr>
			 </authz:authorize>
       	</table>
       	</c:if>
	</div>
	<c:if test="${product.productKind==2}">
		<div id="productSkuSkuOptionInfo">
			<table id="skuOptionValueIdsForm" width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
				<tr>
					<th>
						<fmt:message key="productDetail.skuskuOptionForm.currentSkuOption" /> 
					</th>
				</tr>
				<tr>
					<td>
						<table cellspacing="0" cellpadding="0">
							<tr>
								<th><fmt:message key="skuOptionValue.skuOptionValueName" /></th>
								<th><fmt:message key="skuOptionValue.skuOptionValue" /></th>
								<th><fmt:message key="skuOptionValue.skuOptionValue" /></th>
							</tr>
							<c:forEach items="${skuOptionAndValues}" var="skuOptionAndValue">
								<tr>
									<td>
										${skuOptionAndValue.key.skuOptionName}<c:if test="${!skuOptionAndValue.key.refProductType}"> *<c:set var="skuNotRefProductType" value="true"/></c:if>
									</td>
									<td>
										<select onchange="$j(this).next().val($j(this).val())">
											<option value=""></option>
											<c:forEach items="${skuOptionAndValue.key.skuOptionValues}" var="skuOptionValue">
												<c:if test="${skuOptionValue.status==1||skuOptionValue.skuOptionValueId==skuOptionAndValue.value.skuOptionValueId}">
													<option value="${skuOptionValue.skuOptionValueId}" <c:if test="${skuOptionValue.skuOptionValueId==skuOptionAndValue.value.skuOptionValueId}">selected="selected"</c:if>>
														${skuOptionValue.skuOptionValueName}<c:if test="${skuOptionValue.status!=1}"> *<c:set var="skuWithInactiveOptionValue" value="true"/></c:if>
													</option>
												</c:if>
											</c:forEach>
										</select>
										<input type="hidden" name="skuOptionValueIds" value="${skuOptionAndValue.value.skuOptionValueId}">
									</td>
									<td>
										值/图片/颜色
									</td>
								</tr>
							</c:forEach>
			
							<c:forEach items="${otherSkuOptions}" var="otherSkuOption">
								<tr>
									<td>
										${otherSkuOption.skuOptionName}
									</td>
									<td>
										<select onchange="$j(this).next().val($j(this).val())">
											<option value=""></option>
											<c:forEach items="${otherSkuOption.skuOptionValues}" var="skuOptionValue">
												<c:if test="${skuOptionValue.status==1}">
													<option value="${skuOptionValue.skuOptionValueId}">
														${skuOptionValue.skuOptionValueName}
													</option>
												</c:if>
											</c:forEach>
										</select>
										<input type="hidden" name="skuOptionValueIds">
									</td>
									<td>
										值/图片/颜色
									</td>
								</tr>
							</c:forEach>
							<tr>
								<td colspan="3" >
									<c:if test="${not empty skuNotRefProductType||not empty skuWithInactiveOptionValue}">
									<fmt:message key="productDetail.skuskuOptionForm.notice" />:<br/>
									</c:if>
									<c:if test="${not empty skuWithInactiveOptionValue}">
									<div><fmt:message key="productDetail.skuskuOptionForm.skuOptionValue.inActiveSku.setting.desc" /></div>
									</c:if>
									<c:if test="${not empty skuNotRefProductType}">
									<div><fmt:message key="productDetail.skuskuOptionForm.skuOption.notRefProductType.setting.desc" /></div>
									</c:if>
								</td>
							</tr>
						</table>
					</td>
	            </tr>
			</table>
		</div>
	</c:if>
	<c:if test="${isOriented}">
		<div class="add-btn">
			<cartmatic:cartmaticBtn btnType="common" onclick="fnTabOriented(1)" commonBtnValueKey="productDetail.back"/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<cartmatic:cartmaticBtn btnType="common" onclick="fnTabOriented(3,2)" commonBtnValueKey="productDetail.next"/>
		</div>
	</c:if>
</div>
<v:javascript formName="productSku" staticJavascript="false" />
<cartmatic:swf_upload btnPlaceHolderId="skuImgBtnPlaceHolderId" uploadCategory="product" uploadFileTypes="*.jpg" previewImg="productImage" objId="${product.productId}" previewSize="b" fileInputId="image" button_text="上传图片"></cartmatic:swf_upload>
