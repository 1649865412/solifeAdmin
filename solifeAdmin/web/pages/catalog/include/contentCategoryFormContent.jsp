<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div id="btn-space" style="display: none;">
	<cartmatic:cartmaticBtn btnType="save" inputType="button" onclick="ps('tabIframeId_${param.tid}','fnSaveCategory();');" />
</div>
<div class="top-showMsg-close" id="showMsg_id" style="display: none;" ></div>
<div class="blank10"></div>
<form id="contentCategoryForm" action="${ctxPath}/catalog/contentCategory.html">
<input type="hidden" name="categoryId" id="categoryId" value="${category.categoryId}">
<input type="hidden" id="nId" value="${param.nId}"/>
<input type="hidden" id="pnId" value="${param.pnId}"/>
<input type="hidden" id="tid" value="${param.tid}" />
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
	<tr>
		<th colspan="2">
			<c:choose>
				<c:when test="${empty category.categoryId}">
					<c:choose>
						<c:when test="${!(param.isLinkCategory==1||category.isLinkCategory==1)}"><fmt:message key="categoryDetail.content.add" /></c:when>
						<c:otherwise><fmt:message key="categoryDetail.content.link.add" /></c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${!(param.isLinkCategory==1||category.isLinkCategory==1)}"><fmt:message key="categoryDetail.content.edit" /></c:when>
						<c:otherwise><fmt:message key="categoryDetail.content.link.edit" /></c:otherwise>
					</c:choose>
					"<span id="titleCategoryName">${category.categoryName}</span>"
				</c:otherwise>
			</c:choose>
		</th>
	</tr>
	<tr>
		<td class="FieldLabel">
			<StoreAdmin:label key="category.store" />
		</td>
		<td>
			${store.name}
		</td>
	</tr>
	<tr>
		<td class="FieldLabel">
			<StoreAdmin:label key="category.parentCategory.content" />
		</td>
		<td>
			<c:choose>
				<c:when test="${empty category.categoryId}">
					${parentCategory.categoryName}
					<input type="hidden" id="parentId" name="parentId" value="${parentCategory.categoryId}" />
				</c:when>
				<c:otherwise>${category.category.categoryName}</c:otherwise>
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
	<tr>
		<td class="FieldLabel">
			<StoreAdmin:label key="category.status" />
		</td>
		<td>
			<app:statusCheckbox name="status" style="form-inputbox" value="${category.status}" />
		</td>
    </tr>
    <c:choose>
    	<c:when test="${!(param.isLinkCategory==1||category.isLinkCategory==1)}">
    		<tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="category.imageUrl" />
				</td>
				<td>
					<div style="float: left;">
						<cartmatic:img url="${category.imageUrl}" id="categoryImage" mediaType="category" size="c" height="70" width="70"></cartmatic:img>
					</div>
					<div style="float: left; margin: 20px;">
						<span id="imageUrlBtnPlaceHolderId"></span>
						<input type="hidden" id="imageUrl" name="imageUrl" value="${category.imageUrl}" />
						<br/>
						(<fmt:message key="category.imageUrl.desc" />)
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
					<app:templateSelect templateList="${appConfig.contentTemplates}" name="templatePath" classes="Field400" value="${category.templatePath}"></app:templateSelect>
					<input type="hidden" name="isLinkCategory" id="isLinkCategory" value="0" />
				</td>
		    </tr>
    	</c:when>
    	<c:otherwise>
    		<tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="category.isLinkCategory" />
				</td>
				<td>
					<fmt:message key="category.isLinkCategory.yes"/>
					<input type="hidden" name="isLinkCategory" id="isLinkCategory" value="1" />
				</td>
		    </tr>
		    <tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="category.linkUrl" />
				</td>
				<td>
					<input type="text" id="linkUrl" name="linkUrl" value="${category.linkUrl}" class="Field400" validconf="required"/>
				</td>
		    </tr>
    	</c:otherwise>
    </c:choose>
</table>
</form>
<v:javascript formName="category" staticJavascript="false" />
<script type="text/javascript">
$j(document).ready(function(){
	btnToP();
});
function fnSaveCategory(){
	if(validateCategory($j("#contentCategoryForm").get(0))){
    	var entityName=$j("#categoryName").val();
	   	if(confirm( __FMT.common_message_confirmSaveThis+entityName+"?")){
	    	var postData=$j('#contentCategoryForm :input').serialize();
	    	$j.post(__ctxPath+"/catalog/contentCategory.html?doAction=save",postData,fnSaveCategoryHandler,"json");
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
			var node=parent.fnUpdateNode({id:data.id,tId:$j("#nId").val(),name:data.name,code:data.code,sId:data.sId,pId:$j("#pnId").val(),isLinkCategory:data.isLinkCategory,iconSkin:data.iconSkin});
			$j("#nId").val(node.tId);
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

//persist the value of status
function ChangeValueOfIsLinkCategory(checkbox,persistInput){
    if (checkbox.checked==true){
      	$(persistInput).value = 1;
      	$j('#linkUrlLabel').show();
      	$j('#linkUrlDiv').show();
    }else{
      	$(persistInput).value = 0;
      	$j('#linkUrlLabel').hide();
      	$j('#linkUrlDiv').hide();
    }
}
</script>
<c:if test="${!(param.isLinkCategory==1||category.isLinkCategory==1)}">
<cartmatic:swf_upload btnPlaceHolderId="imageUrlBtnPlaceHolderId" uploadCategory="category" uploadFileTypes="*.jpg" previewImg="categoryImage"  previewSize="c" fileInputId="imageUrl" ></cartmatic:swf_upload>
</c:if>