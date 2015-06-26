<div id="descriptionInfo" style="display:none">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
		<tr>
			<th>
				<StoreAdmin:label key="productDescription.shortDescription" />
			</th>
		</tr>
		<tr>
			<td>
				<textarea id="shortDescription" name="shortDescription" rows="4" cols="80" style="width: 99.5%">${product.productDescription.shortDescription}</textarea>
			</td>
        </tr>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
		<tr>
			<th>
				<StoreAdmin:label key="productDescription.imageDescription" />
			</th>
		</tr>
		<tr>
			<td>
				<textarea id="imageDescription" name="imageDescription" rows="4" cols="80" style="width: 99.5%">${product.productDescription.imageDescription}</textarea>
			</td>
        </tr>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
		<tr>
			<th>
				<StoreAdmin:label key="productDescription.fullDescription" />
			</th>
		</tr>
		<tr>
			<td>
				<textarea id="fullDescription" name="fullDescription" rows="12" cols="80" style="width: 100%; height: 500px;">${product.productDescription.fullDescription}</textarea>
			</td>
		</tr>
	</table>
	<c:if test="${isOriented}">
		<div class="add-btn">
			<cartmatic:cartmaticBtn btnType="common" onclick="fnTabOriented(3)" commonBtnValueKey="productDetail.back"/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<cartmatic:cartmaticBtn btnType="common" onclick="fnTabOriented(5,4)" commonBtnValueKey="productDetail.next"/>
		</div>
	</c:if>
	<app:ui_htmlEditor textareaIds="fullDescription" />
</div>