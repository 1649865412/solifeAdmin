<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>
<app:pageHeading pageHeadingKey="productCategoryList.heading" />
<body>
	<div id="btn-space" style="display: none;">
		<cartmatic:cartmaticBtn btnType="save" inputType="button" onclick="ps('tabIframeId_${param.tid}','fnSaveCategory();');"/>
	</div>
	<div class="top-showMsg-close" id="showMsg_id" style="display: none;" ></div>
	<div class="blank10"></div>
	<form method="post" class="mainForm" id="productCategoryForm">
	<input type="hidden" name="categoryId" id="categoryId" value="${category.categoryId}"/>
	<input type="hidden" id="nId" value="${param.nId}"/>
	<input type="hidden" id="pnId" value="${param.pnId}"/>
	<input type="hidden" id="tid" value="${param.tid}" />
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
		<tr>
			<th colspan="2">
				<c:choose>
					<c:when test="${empty category.categoryId}">
						<fmt:message key="categoryDetail.add" />
					</c:when>
					<c:otherwise>
						<fmt:message key="categoryDetail.edit" />:"<span id="titleCategoryName">${category.categoryName}</span>"
				</c:otherwise>
				</c:choose>
			</th>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="category.catalog" />
			</td>
			<td>
				${catalog.name}
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="category.parentCategory" />
			</td>
			<td>
				<c:choose>
					<c:when test="${empty category.categoryId}">
						<c:if test="${not empty parentCategory.category.categoryId}">${parentCategory.categoryName}</c:if>
						<input type="hidden" id="parentId" name="parentId" value="${parentCategory.categoryId}" />
					</c:when>
					<c:when test="${category.category.catalog==catalog}">${category.category.categoryName}</c:when>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="category.categoryName" />
			</td>
			<td>
				<input type="text" id="categoryName" name="categoryName" value="<c:out value="${category.categoryName}"/>" class="Field400" />
			</td>
	    </tr>
	    <tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="category.categoryCode" />
			</td>
			<td>
				<input type="text" id="categoryCode" name="categoryCode" value="${category.categoryCode}" class="Field400" />
			</td>
		</tr>
		<c:if test="${catalog.isVirtual==1}">
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="category.linkedCategory" />
			</td>
			<td>
				<input name="linkedCategoryName" id="linkedCategoryName" class="Field400" type="text" value="${category.linkedCategory.categoryName}" readonly="readonly" />
				<input type="hidden" id="linkedCategoryId" name="linkedCategoryId" value="${category.linkedCategoryId}" />
				&nbsp;&nbsp;
				<input type="image" src="${ctxPath}/images/select.gif" onclick="try{CategorySelector_show();}catch(e){alert(e.message);}return false;" title="<fmt:message key="productDetail.msg.addCategory" />" />
				<input type="image" src="${ctxPath}/images/clear_field.gif" onclick="try{$j('#linkedCategoryName').val('');$j('#linkedCategoryId').val('');}catch(e){alert(e.message);}return false;" title="<fmt:message key="productDetail.msg.removeCategory" />" />
			</td>
		</tr>
		<c:if test="${empty category.categoryId}">
		<tr>
			<td class="FieldLabel">
				&nbsp;
			</td>
			<td>
				<input type="checkbox" name="isCreateSubLinkedCategory" id="isCreateSubLinkedCategory" value="1"> <label for="isCreateSubLinkedCategory">复制链接目录下所有的子目录</label>
			</td>
		</tr>
		</c:if>
		</c:if>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="category.status" />
			</td>
			<td>
				<app:statusCheckbox name="status" style="form-inputbox" value="${category.status}" />
			</td>
	    </tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="category.imageUrl" />
			</td>
			<td>
				<div style="float: left;">
					<cartmatic:img url="${category.imageUrl}" id="categoryImage" mediaType="category" size="v" height="70" width="70"></cartmatic:img>
				</div>
				<div style="float: left; margin: 20px;">
					<span id="imageUrlBtnPlaceHolderId"></span>
					<input type="hidden" id="imageUrl" name="imageUrl" value="${category.imageUrl}" />
					<br/>
					<cartmatic:iconBtn icon="cross" extraCss="negative" text="清空图片" onclick="$('categoryImage').src='${ctxPath}/images/default/00.jpg';$j('#imageUrl').val('');" />	
				</div>
			</td>
	    </tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="category.metaKeyword" />
			</td>
			<td>
				<textarea id="metaKeyword" name="metaKeyword" style="width: 90%; height: 100px">${category.metaKeyword}</textarea>
			</td>
		</tr>
	    <tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="category.metaDescription" />
			</td>
			<td>
				<textarea id="metaDescription" name="metaDescription" style="width: 90%; height: 100px">${category.metaDescription}</textarea>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="category.categoryDescription" />
			</td>
			<td>
				<textarea id="categoryDescription" name="categoryDescription" style="width: 90%; height: 100px">${category.categoryDescription}</textarea>
			</td>
	    </tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="category.templatePath" />
			</td>
			<td>
				<app:templateSelect templateList="${appConfig.categoryTemplates}" name="templatePath" classes="Field400" value="${category.templatePath}"></app:templateSelect>
			</td>
	    </tr>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
		<c:if test="${not empty attributeList}">
			<tr>
				<th colspan="2">
					<fmt:message key="categoryDetail.attrubutes" />
				</th>
			</tr>
			<c:forEach items="${attributeList}" var="attribute" varStatus="varStatus" step="2">
				<tr>
					<td class="FieldLabel">
						${attribute.attributeName}:
					</td>
					<td>
						<attribute:input cssName="Field400" isdisplayHelp="true" attribute="${attribute}" entity="${category}"></attribute:input>
					</td>
		        </tr>
			</c:forEach>
		</c:if>
	</table>
</form>
<%--推荐页面：sales/recommendedTypeForCatalog--%>
<c:if test="${not empty category.categoryId}">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
	<tr>
	<th><fmt:message key="category.recommendProduct.manage" /></th>
	</tr>
	<tr>
	<td>
		<form name="recommendedProductForm" action="">
		<jsp:include flush="true" page="/sales/recommendedTypeForCatalog.html">
            <jsp:param name="sourceId" value="${category.categoryId}" />
            <jsp:param name="catalogId" value="${category.catalog.id}" />
            <jsp:param name="sourceType" value="CATEGORY" />
            <jsp:param name="doAction" value="defaultAction" />
        </jsp:include>
        </form>
	</td>
	</tr>
</table>
<script type="text/javascript" defer>
	$j(document).ready(function(){
		fnInitRecommmended();
	});
</script>
</c:if>
<v:javascript formName="category" staticJavascript="false" />
<cartmatic:swf_upload btnPlaceHolderId="imageUrlBtnPlaceHolderId" uploadCategory="category" uploadFileTypes="*.jpg" previewImg="categoryImage"  previewSize="e" fileInputId="imageUrl" button_text="上传图片"></cartmatic:swf_upload>
<script type="text/javascript">
	$j(document).ready(function(){
		btnToP();
	});
	function fnSaveCategory(){
		if(validateCategory($j("#productCategoryForm").get(0))){
	    	var entityName=$j("#categoryName").val();
		   	if(confirm( __FMT.common_message_confirmSaveThis+entityName+"?")){
		    	var postData=$j('#productCategoryForm :input').serialize();
		    	$j.post(__ctxPath+"/catalog/productCategory.html?doAction=save",postData,fnSaveCategoryHandler,"json");
		   	}
	    }
	}
	var errors=new Array();
	function fnSaveCategoryHandler(result){
		fnSaveCallbackShowError(result);
		if (result.status == 1) {
			var data=result.data;
			$j("#categoryId").val(data.id);
			if(parent){
				var node=parent.fnUpdateNode({subTreeItemJsonList:data.subTreeItemJsonList,id:data.id,tId:$j("#nId").val(),name:data.name,code:data.code,cId:data.cId,pId:$j("#pnId").val(),isVirtual:data.isVirtual,iconSkin:data.iconSkin});
				$j("#nId").val(node.tId);
				parent.fnAddNodes(node,data.subTreeItemJsonList);
			}
			updateTabLable("#"+data.code,"cate_tab_"+data.id);
			//保存成功后，刷新父窗口
			sysMsg4p(result.msg);
		}
	}
	function updateTabLable(lable,newId){
		if(parent&&lable&&lable.replace("#","")){
			parent.fnUpdateTab($j("#tid").val(),lable,newId);
			$j("#tid").val(newId);
		}
	}
</script>
<c:if test="${catalog.isVirtual==1}">
<script type="text/javascript">
	function fnSelectCategoryHandler(category){
		$j("#linkedCategoryName").val(category.name);
		$j("#linkedCategoryId").val(category.id);
	}
</script>
<product:categorySelector id="CategorySelector" ondblclick="fnSelectCategoryHandler" virtual="0" canSelectRoot="false"></product:categorySelector>
</c:if>
</body>

