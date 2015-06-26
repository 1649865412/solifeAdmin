<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>
<div id="productMediaInfo" style="display:none">

	<!--  产品大图  -->
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
		<tr>
			<th colspan="2">
				<fmt:message key="productDetail.moreImage" />
			</th>
		</tr>
		<tr>
			<%--<td width="50%">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="no-border">
					<tr>
						<td width="15%" align="center">
							<span id="productMediaImageBtnPlaceHolderId"></span>
						</td>
						<td>
							<fmt:message key="productDetail.moreImage.desc.v" />（<a href="#" onclick="removeAllProductImg('productMoreImages');">移除所有产品中图</a>）
						</td>
					</tr>
				</table>
			</td>
			--%><td width="50%">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="no-border">
					<tr>
						<td width="15%" align="center">
							<span id="productMediaImageBtnPlaceHolderId_d"></span>
						</td>
						<td>
							<fmt:message key="productDetail.moreImage.desc.d" />（<a href="#" onclick="removeAllProductImg('productMoreImages_d');">移除所有产品大图</a>）
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<%--<td>
				<div id="productMoreImages">
					<c:forEach items="${productMoreImages}" var="productMoreImage">
						<div class="product-media" id="productMedia_div_${productMoreImage.productMediaId}">
							<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="no-border">
								<tr>
									<td class="list" width="15%" align="center">
										<input type="hidden" name="productMediaIds" value="${productMoreImage.productMediaId}">
										<input type="hidden" name="productMediaTypes" value="${productMoreImage.mediaType}">
										<cartmatic:img url="${productMoreImage.mediaUrl}" mediaType="productMedia" size="d" width="60" height="60" id="productMedia_img_${productMoreImage.productMediaId}"></cartmatic:img>
										<input type="hidden" id="productMedia_deleteds_${productMoreImage.productMediaId}" name="productMedia_deleteds" value="0">
									</td>
									<td class="list" width="24%">
										<input type="text" id="productMedia_url_${productMoreImage.productMediaId}" name="productMedia_urls" value="${productMoreImage.mediaUrl}" style="width: 400px;" />
										<br />
										<fmt:message key="productMedia.mediaDescription" />
										<br />
										<input type="text" id="productMedia_desc_${productMoreImage.productMediaId}" name="productMedia_descs" value="${productMoreImage.mediaDescription}" style="width: 400px;" />
									</td>
									<td class="list">
										&nbsp;&nbsp;
										<input name="remove_empty_item" type="image" src="${ctxPath}/images/icon/icon_del.gif" onclick="fnRemoveUploadMedia(${productMoreImage.productMediaId},this);return false;" title="<fmt:message key="productDetail.moreImage.removeThisImage" />" />
									</td>
								</tr>
							</table>
						</div>
					</c:forEach>
				</div>
			</td>
			--%><td>
				<div id="productMoreImages_d">
					<c:forEach items="${productMoreImages}" var="productMoreImage">
						<div class="product-media" id="productMedia_div_${productMoreImage.productMediaId}">
							<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="no-border">
								<tr>
									<td class="list" width="15%" align="center">
										<input type="hidden" name="productMediaIds" value="${productMoreImage.productMediaId}">
										<input type="hidden" name="productMediaTypes" value="${productMoreImage.mediaType}">
										<cartmatic:img url="${productMoreImage.mediaUrlLarge}" mediaType="productMedia" size="v" width="60" height="60" id="productMedia_img_${productMoreImage.productMediaId}"></cartmatic:img>
										<input type="hidden" id="productMedia_deleteds_${productMoreImage.productMediaId}" name="productMedia_deleteds" value="0">
									</td>
									<td class="list" width="24%">
										<input type="text" id="productMedia_url_${productMoreImage.productMediaId}" name="productMedia_urls_d" value="${productMoreImage.mediaUrlLarge}" style="width: 400px;" />
										<br />
										<fmt:message key="productMedia.mediaDescription" />
										<br />
										<input type="text" id="productMedia_desc_${productMoreImage.productMediaId}"  name="productMedia_descs" value="${productMoreImage.mediaDescription}" style="width: 400px;" />
										<%@include file="productSkuSelect.jsp" %>
									</td>
									<td class="list">
										&nbsp;&nbsp;
										<input name="remove_empty_item" type="image" src="${ctxPath}/images/icon/icon_del.gif" onclick="fnRemoveUploadMedia(${productMoreImage.productMediaId},this);return false;" title="<fmt:message key="productDetail.moreImage.removeThisImage" />" />
									</td>
								</tr>
							</table>
						</div>
					</c:forEach>
			</td>
        </tr>
        
	</table>
	
	<!--  上部分 额外图  -->
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
		<tr>
			<th colspan="2">
				上传额外图
			</th>
		</tr>
		<tr>
			<td width="50%">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="no-border">
					<tr>
						<td width="15%" align="center">
							<span id="productMediaImageBtnPlaceHolderId_up"></span>
						</td>
						<td>
							<fmt:message key="productDetail.moreImage.desc.d" />（<a href="#" onclick="removeAllProductImg('productMoreImages_up');">移除所有产品额外图</a>）
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<div id="productMoreImages_up">
					<c:forEach items="${productMoreImagesUp}" var="productMoreImage">
						<div class="product-media" id="productMedia_div_${productMoreImage.productMediaUpId}">
							<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="no-border">
								<tr>
									<td class="list" width="15%" align="center">
										<input type="hidden" name="productMediaUpIds" value="${productMoreImage.productMediaUpId}">
										<input type="hidden" name="productMediaTypesUp" value="${productMoreImage.mediaType}">
										<cartmatic:img url="${productMoreImage.mediaUrl}" mediaType="productMedia" size="v" width="60" height="60" id="productMedia_img_${productMoreImage.productMediaUpId}"></cartmatic:img>
										<input type="hidden" id="productMedia_deleteds_${productMoreImage.productMediaUpId}" name="productMedia_deletedsUp" value="0">
									</td>
									<td class="list" width="24%">
										<input type="text" id="productMedia_url_${productMoreImage.productMediaUpId}" name="productMedia_urlsUp" value="${productMoreImage.mediaUrl}" style="width: 400px;" />
										<br />
										<fmt:message key="productMedia.mediaDescription" />
										<br />
										<input type="text" id="productMedia_desc_${productMoreImage.productMediaUpId}"  name="productMedia_descsUp" value="${productMoreImage.mediaDescription}" style="width: 400px;" />
									</td>
									<td class="list">
										&nbsp;&nbsp;
										<input name="remove_empty_item" type="image" src="${ctxPath}/images/icon/icon_del.gif" onclick="fnRemoveUploadMedia(${productMoreImage.productMediaUpId},this);return false;" title="<fmt:message key="productDetail.moreImage.removeThisImage" />" />
									</td>
								</tr>
							</table>
						</div>
					</c:forEach>
				</div>
			</td>
        </tr>
        
	</table>
	
	<!--  手绘图  -->
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
		<tr>
			<th>
				产品手绘图
			</th>
		</tr>
		<tr>
			<td>
				<span id="productHandDrawBtnPlaceHolderId"></span>
			</td>
		</tr>
		<tr>
			<td>
				<input type="hidden" id="handDrawId" name="handDrawId" value="${product.productHandDraw.id }"/>
				<div class="product-media" id="handDrawMediaDiv">
					<cartmatic:img url="${product.productHandDraw.mediaUrl}" mediaType="productMedia" size="hd" width="120" height="120" ></cartmatic:img>
					<input type="hidden" name="handDrawUrl" value="${product.productHandDraw.mediaUrl }"> 
				</div>
			</td>
	    </tr>
	</table>
	
	<!--  附件  -->
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
		<tr>
			<th>
				<fmt:message key="productDetail.accessories" />
			</th>
		</tr>
		<tr>
			<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="no-border">
					<tr>
						<td width="15%" align="center">
							<span id="productAccessoryBtnPlaceHolderId"></span>
						</td>
						<td>
							<fmt:message key="productDetail.accessories.desc" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<div id="productAccessories">
					<c:forEach items="${productAccessoriesAndMultdMedias}" var="productMoreImage">
						<div class="product-media" id="productMedia_div_${productMoreImage.productMediaId}">
							<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="no-border">
								<tr>
									<td class="list" width="15%" align="center">
										<input type="hidden" name="productMediaIds" value="${productMoreImage.productMediaId}">
										<input type="hidden" name="productMediaTypes" value="${productMoreImage.mediaType}">
										<img src="${ctxPath}/images/accessorie_hight_light.gif" width="60" height="60" onerror="$j(this).attr('originsrc',this.src);this.onerror='';this.src=__defaultImage" originsrc="" id="productMedia_img_${productMoreImage.productMediaId}"/>
										<input type="hidden" id="productMedia_deleteds_${productMoreImage.productMediaId}" name="productMedia_deleteds" value="0">
									</td>
									<td class="list" width="24%">
										<input type="text" id="productMedia_url_${productMoreImage.productMediaId}" name="productMedia_urls" value="${productMoreImage.mediaUrl}" style="width: 400px;" />
										<br />
										<fmt:message key="productMedia.mediaDescription" />
										<br />
										<input type="text" id="productMedia_desc_${productMoreImage.productMediaId}" name="productMedia_descs" value="${productMoreImage.mediaDescription}" style="width: 400px;" />
									</td>
									<td class="list">
										&nbsp;&nbsp;
										<input name="remove_empty_item" type="image" src="${ctxPath}/images/icon/icon_del.gif" onclick="fnRemoveUploadMedia(${productMoreImage.productMediaId},this);return false;" title="<fmt:message key="productDetail.moreImage.removeThisImage" />" />
									</td>
								</tr>
							</table>
						</div>
					</c:forEach>
				</div>
			</td>
	    </tr>
	</table>
	<%--
	<cartmatic:swf_upload btnPlaceHolderId="productMediaImageBtnPlaceHolderId" uploadCategory="productMedia" uploadFileTypes="*.jpg" onComplete="fnUploadMoreImageHandler" objId="${product.productId}" previewSize="d" isMultiFiles="true" button_text="上传产品中图" fileImageSize="d"></cartmatic:swf_upload>
	--%>
	<!-- 上传大图 -->
	<cartmatic:swf_upload btnPlaceHolderId="productMediaImageBtnPlaceHolderId_d" uploadCategory="productMedia" uploadFileTypes="*.jpg; *.jpeg; *.png; *.gif" onComplete="fnUploadMoreImage_d_Handler" objId="${product.productId}" previewSize="v" isMultiFiles="true" button_text="上传产品大图" fileImageSize="v"  fileSizeLimit="5MB"></cartmatic:swf_upload>
	<!-- 上传 上部分的额外图 -->
	<cartmatic:swf_upload btnPlaceHolderId="productMediaImageBtnPlaceHolderId_up" uploadCategory="productMedia" uploadFileTypes="*.jpg; *.jpeg; *.png; *.gif" onComplete="fnUploadMoreImage_up_Handler" objId="${product.productId}" previewSize="v" isMultiFiles="true" button_text="上传产品额外图" fileImageSize="v"  fileSizeLimit="5MB"></cartmatic:swf_upload>
	<!-- 上传手绘图 -->
	<cartmatic:swf_upload btnPlaceHolderId="productHandDrawBtnPlaceHolderId" uploadCategory="productMedia" uploadFileTypes="*.jpg; *.jpeg; *.png; *.gif" onComplete="fnUploadHandDrawHandler" objId="${product.productId}" previewSize="hd" isMultiFiles="false" button_text="上传产品手绘图" fileImageSize="hd"  fileSizeLimit="5MB"></cartmatic:swf_upload>
	<!-- 上传附件 -->
	<cartmatic:swf_upload btnPlaceHolderId="productAccessoryBtnPlaceHolderId" uploadCategory="productMedia" uploadFileTypes="*.*" onComplete="fnUploadAccessoryHandler" objId="${product.productId}"  isMultiFiles="true" button_text="上传产品附件"  fileSizeLimit="5MB"></cartmatic:swf_upload>
	
	<c:if test="${isOriented}">
		<div class="add-btn">
			<cartmatic:cartmaticBtn btnType="common" onclick="fnTabOriented(4)" commonBtnValueKey="productDetail.back"/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<cartmatic:cartmaticBtn btnType="common" onclick="fnTabOriented(6,5)" commonBtnValueKey="productDetail.next"/>
		</div>
	</c:if>
</div>

<div id="skuListSelect" style="display: none;">
</div>
