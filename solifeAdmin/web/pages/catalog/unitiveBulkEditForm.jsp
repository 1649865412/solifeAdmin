<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<head>
	<app:pageHeading pageHeadingKey="unitiveBulkEdit.heading" />
</head>
<body>
<div id="btn-space" style="display: none;">
	<cartmatic:cartmaticBtn btnType="save" onclick="ps('tabIframeId_${param.tid}','fnDoSave();');" />
</div>
<div class="blank10"></div>
<form class="mainForm" method="post" id="productForm" name="productForm" action="${ctxPath}/catalog/product.html" onsubmit="return false;">
	<input type="hidden" name="productIds" id="productIds" value="${productIds}" />
	<input type="hidden" name="productSkuIds" id="productSkuIds" value="${productSkuIds}" />
	<input type="hidden" id="tid" value="${param.tid}" />
	<fmt:message key="unitiveBulkEdit.product.list">
		<fmt:param value="${fn:length(productList)}"></fmt:param>
	</fmt:message>
	:
	<c:forEach items="${productList}" var="product" varStatus="status">
		${product.productName}
		<c:if test="${!status.last}">,
		</c:if>
	</c:forEach>
	
	<div id="productTab">
		<ul class="sub-tab">
	    	<li>
				<a href="#productBasisInfo"><fmt:message key="product.tab.basis" /> </a><span id="productInfoTabFlag"></span>
			</li>
			<li>
				<a href="#productSkuInfo"><fmt:message key="product.tab.sku" /> </a><span id="productSkuInfoTabFlag"></span>
			</li>
			<li>
				<a href="#descriptionInfo"><fmt:message key="product.tab.description" /> </a>
			</li>
			<li>
				<a href="#productMediaInfo"><fmt:message key="product.tab.media" /> </a>
			</li>
			<li>
				<a href="#seoInfo"><fmt:message key="product.tab.seo" /> </a>
			</li>
			<li>
				<a href="#supplierInfo"><fmt:message key="product.tab.supplier" /></a>
			</li>
	    </ul>
	    <div class="blank10"></div>
	    <%@ include file="./include/unitiveBulkEditFormBasicInfo.jsp"%>
		<%@ include file="./include/unitiveBulkEditFormSku.jsp"%>
		<%@ include file="./include/unitiveBulkEditFormDescription.jsp"%>
		<%@ include file="./include/unitiveBulkEditFormMedia.jsp"%>
		<%@ include file="./include/unitiveBulkEditFormSeo.jsp"%>
		<%@ include file="./include/unitiveBulkEditFormSupplier.jsp"%>
	</div>
</form>
<script type="text/javascript" defer="true">
$j(document).ready(function () {
	$j("#productTab").appTabs({type:2});
	fnCreateUploadInput("productMoreImages",0);
	fnCreateUploadInput("productAccessories",1);
	btnToP();
});	

function fnDoSave(){
	//clear the message
	$j('#message').empty();
	var confirmMsg = __FMT.common_message_confirmSaveThis+"?";
	if(validateAction()){
    	if (!confirmMsg || confirm(confirmMsg)) {
	    	var url = document.productForm.action+"?doAction=unitiveBulkEditSave";
	    	var postData = $j('#productForm :input').serialize();
	    	
	    	var catalogIds=$j("input[name='catalogId']");
	    	for(var i=0;i<catalogIds.length;i++){
	    		var categoryIds = $j("#otherCategory_"+catalogIds.get(i).value+" option");
				$j.each(categoryIds, function(){
					postData += "&otherCategory_"+catalogIds.get(i).value+"=" + this.value ;
				});
	    	}
			
			var supplierIds = $j("#otherSupplier option");
			$j.each(supplierIds, function(){
				postData += "&otherSupplier=" + this.value ;
			});
	    	$j('#message').append("<div class='message'><img src='${ctxPath}/images/icon/loading.gif'/>"+"<fmt:message key='bulkEdit.commit_wait'/>"+"</div>");
			$j.post(url,postData,fnDoSaveCallback,"json");
	    }
    }
}

function fnDoSaveCallback(result){
	sysMsg4p(result.msg,result.status!=1);
	if(result.status==1){
	   	$j("#productMoreImages").empty();
	   	$j("#productAccessories").empty(); 
   	   	fnCreateUploadInput("productMoreImages",0);
   		fnCreateUploadInput("productAccessories",1);
   	}
}

function checkPlanEndTime(){
	if (($("planStartTime").value !="") &&(fnCompareDate($("planStartTime").value,$("planEndTime").value) >=0) ){
	   	$j('#planEndTime').focus();
	   	alert(__FMT. productDetail_validate_endTimeGtStartTime);
	   	return false;
   	}
	
   	return true;
}
function validateAction(){
	//set the realvalue
	$j('#fullDescription').val(tinyMCE.get('fullDescription').getContent());
	//remove all the red
	$j(".x-form-invalid + img").remove();	
	$j(".x-form-invalid").attr("class","");	
	
	var flag = true;
	
	if($("nameCheck").checked && $("name_method_2").checked)applyValidate($("name_prefix"),"maxlength=128");
	if($("nameCheck").checked && $("name_method_2").checked)applyValidate($("name_suffix"),"maxlength=128");
	if($("nameCheck").checked && $("name_method_3").checked)applyValidate($("name_findAndReplaceOldString"),"required,maxlength=128");
	if($("nameCheck").checked && $("name_method_3").checked)applyValidate($("name_findAndReplaceNewString"),"maxlength=128");
	
	if($("templatePathCheck").checked)applyValidate($("templatePath"),"maxlength=128");
	if($("planStartTimeCheck").checked)applyValidate($("planStartTime"),"date,maxlength=20");
	if($("planEndTimeCheck").checked)applyValidate($("planEndTime"),"date,maxlength=20");
	if($("minOrderQuantityCheck").checked)applyValidate($("minOrderQuantity"),"required,integer");
	if($("shortDescriptionCheck").checked && $("shortDescription_method_1").checked)applyValidate($("shortDescription"),"maxlength=512");
	if($("shortDescriptionCheck").checked && $("shortDescription_method_2").checked)applyValidate($("shortDescription_prefix"),"maxlength=128");
	if($("shortDescriptionCheck").checked && $("shortDescription_method_2").checked)applyValidate($("shortDescription_suffix"),"maxlength=128");
	if($("shortDescriptionCheck").checked && $("shortDescription_method_3").checked)applyValidate($("shortDescription_findAndReplaceOldString"),"required,maxlength=128");
	if($("shortDescriptionCheck").checked && $("shortDescription_method_3").checked)applyValidate($("shortDescription_findAndReplaceNewString"),"maxlength=128");
	
	if($("fullDescriptionCheck").checked && $("fullDescription_method_1").checked)applyValidate($("fullDescription"),"maxlength=20000");
	if($("fullDescriptionCheck").checked && $("fullDescription_method_2").checked)applyValidate($("fullDescription_prefix"),"maxlength=128");
	if($("fullDescriptionCheck").checked && $("fullDescription_method_2").checked)applyValidate($("fullDescription_suffix"),"maxlength=128");
	if($("fullDescriptionCheck").checked && $("fullDescription_method_3").checked)applyValidate($("fullDescription_findAndReplaceOldString"),"required,maxlength=128");
	if($("fullDescriptionCheck").checked && $("fullDescription_method_3").checked)applyValidate($("fullDescription_findAndReplaceNewString"),"maxlength=128");
	
	if($("titleCheck").checked && $("title_method_1").checked)applyValidate($("title"),"maxlength=128");
	if($("titleCheck").checked && $("title_method_2").checked)applyValidate($("title_prefix"),"maxlength=128");
	if($("titleCheck").checked && $("title_method_2").checked)applyValidate($("title_suffix"),"maxlength=128");
	if($("titleCheck").checked && $("title_method_3").checked)applyValidate($("title_findAndReplaceOldString"),"required,maxlength=128");
	if($("titleCheck").checked && $("title_method_3").checked)applyValidate($("title_findAndReplaceNewString"),"maxlength=128");
	
	if($("urlCheck").checked && $("url_method_1").checked)applyValidate($("url"),"maxlength=255");
	if($("urlCheck").checked && $("url_method_2").checked)applyValidate($("url_prefix"),"maxlength=128");
	if($("urlCheck").checked && $("url_method_2").checked)applyValidate($("url_suffix"),"maxlength=128");
	if($("urlCheck").checked && $("url_method_3").checked)applyValidate($("url_findAndReplaceOldString"),"required,maxlength=128");
	if($("urlCheck").checked && $("url_method_3").checked)applyValidate($("url_findAndReplaceNewString"),"maxlength=128");
	
	if($("metaKeywordCheck").checked && $("metaKeyword_method_1").checked)applyValidate($("metaKeyword"),"maxlength=256");
	if($("metaKeywordCheck").checked && $("metaKeyword_method_2").checked)applyValidate($("metaKeyword_prefix"),"maxlength=128");
	if($("metaKeywordCheck").checked && $("metaKeyword_method_2").checked)applyValidate($("metaKeyword_suffix"),"maxlength=128");
	if($("metaKeywordCheck").checked && $("metaKeyword_method_3").checked)applyValidate($("metaKeyword_findAndReplaceOldString"),"required,maxlength=128");
	if($("metaKeywordCheck").checked && $("metaKeyword_method_3").checked)applyValidate($("metaKeyword_findAndReplaceNewString"),"maxlength=128");
	
	if($("metaDescriptionCheck").checked && $("metaDescription_method_1").checked)applyValidate($("metaDescription"),"maxlength=256");
	if($("metaDescriptionCheck").checked && $("metaDescription_method_2").checked)applyValidate($("metaDescription_prefix"),"maxlength=128");
	if($("metaDescriptionCheck").checked && $("metaDescription_method_2").checked)applyValidate($("metaDescription_suffix"),"maxlength=128");
	if($("metaDescriptionCheck").checked && $("metaDescription_method_3").checked)applyValidate($("metaDescription_findAndReplaceOldString"),"required,maxlength=128");
	if($("metaDescriptionCheck").checked && $("metaDescription_method_3").checked)applyValidate($("metaDescription_findAndReplaceNewString"),"maxlength=128");
	
	if($("priceCheck").checked && $("price_method_1").checked)applyValidate($("price"),"required,price,floatRange=[0-9999999999]");
	if($("priceCheck").checked && $("price_method_4").checked)applyValidate($("price_numeric"),"price,floatRange=[0-1000]");
	if($("salePriceCheck").checked && $("salePrice_method_1").checked)applyValidate($("salePrice"),"price,floatRange=[0-9999999999]");
	if($("salePriceCheck").checked && $("salePrice_method_4").checked)applyValidate($("salePrice_numeric"),"price,floatRange=[0-1000]");
	if($("listPriceCheck").checked && $("listPrice_method_1").checked)applyValidate($("listPrice"),"price,floatRange=[0-9999999999]");
	if($("listPriceCheck").checked && $("listPrice_method_4").checked)applyValidate($("listPrice_numeric"),"price,floatRange=[0-1000]");

	if($("wholeSalePriceCheck").checked)applyValidateWholeSalePrice();
	
	if($("weightCheck").checked)applyValidate($("weight"),"double,floatRange=[0-10000]");
	if($("lengthCheck").checked)applyValidate($("length"),"double");
	if($("widthCheck").checked)applyValidate($("width"),"double");
	if($("heightCheck").checked)applyValidate($("height"),"double");
		
	if($("mediaCheck").checked)applyValidatateProductMedia();
		
	if(!validateForm()){
    	flag = false;
    	alert('<fmt:message key="validate.notPass"/>');
    	setFocusOnTab($j('.x-form-invalid:first').parents('.ui-tabs-panel')[0].id);
   	}
   	if($("planStartTimeCheck").checked && $("planEndTimeCheck").checked){
   		if(!checkPlanEndTime()){
   			flag = false;
   		}
   	}
   	
   	removeValidate($("name_prefix"),"maxlength=128");
	removeValidate($("name_suffix"),"maxlength=128");
	removeValidate($("name_findAndReplaceOldString"),"required,maxlength=128");
	removeValidate($("name_findAndReplaceNewString"),"maxlength=128");

	removeValidate($("templatePath"),"maxlength=128");
	removeValidate($("templatePath"),"maxlength=128");
	removeValidate($("planStartTime"),"date,maxlength=20");
	removeValidate($("planEndTime"),"date,maxlength=20");
	
	removeValidate($("minOrderQuantity"),"required,integer");
	removeValidate($("shortDescription"),"maxlength=512");
	removeValidate($("shortDescription_suffix"),"maxlength=128");
	removeValidate($("shortDescription_findAndReplaceOldString"),"required,maxlength=128");
	removeValidate($("shortDescription_findAndReplaceNewString"),"maxlength=128");
	
	removeValidate($("fullDescription"),"maxlength=20000");
	removeValidate($("fullDescription_prefix"),"maxlength=128");
	removeValidate($("fullDescription_suffix"),"maxlength=128");
	removeValidate($("fullDescription_findAndReplaceOldString"),"required,maxlength=128");
	removeValidate($("fullDescription_findAndReplaceNewString"),"maxlength=128");
	
	
	removeValidate($("title"),"maxlength=128");
	removeValidate($("title_prefix"),"maxlength=128");
	removeValidate($("title_suffix"),"maxlength=128");
	removeValidate($("title_findAndReplaceOldString"),"required,maxlength=128");
	removeValidate($("title_findAndReplaceNewString"),"maxlength=128");
	
	removeValidate($("url"),"maxlength=255");
	removeValidate($("url_prefix"),"maxlength=128");
	removeValidate($("url_suffix"),"maxlength=128");
	removeValidate($("url_findAndReplaceOldString"),"required,maxlength=128");
	removeValidate($("url_findAndReplaceNewString"),"maxlength=128");
	
	removeValidate($("metaKeyword"),"maxlength=256");
	removeValidate($("metaKeyword_prefix"),"maxlength=128");
	removeValidate($("metaKeyword_suffix"),"maxlength=128");
	removeValidate($("metaKeyword_findAndReplaceOldString"),"required,maxlength=128");
	removeValidate($("metaKeyword_findAndReplaceNewString"),"maxlength=128");
	
	removeValidate($("metaDescription"),"maxlength=256");
	removeValidate($("metaDescription_prefix"),"maxlength=128");
	removeValidate($("metaDescription_suffix"),"maxlength=128");
	removeValidate($("metaDescription_findAndReplaceOldString"),"required,maxlength=128");
	removeValidate($("metaDescription_findAndReplaceNewString"),"maxlength=128");
	
	
	removeValidate($("price"),"required,price,floatRange=[0-9999999999]");
	removeValidate($("price_numeric"),"price,floatRange=[0-1000]");
	removeValidate($("salePrice"),"price,floatRange=[0-9999999999]");
	removeValidate($("salePrice_numeric"),"price,floatRange=[0-1000]");
	removeValidate($("listPrice"),"price,floatRange=[0-9999999999]");
	removeValidate($("listPrice_numeric"),"price,floatRange=[0-1000]");
	
	removeValidateWholeSalePrice();
	
	removeValidate($("weight"),"double,floatRange=[0-10000]");
	removeValidate($("length"),"double");
	removeValidate($("width"),"double");
	removeValidate($("height"),"double");
	
	removeValidatateProductMedia();
   	   
    return flag;
}

function fnModifyCategoryHandler(category,param){
	if(param.name.indexOf("mainCategory")!=-1){
		$("mainCategory_"+param.id).value = category.categoryName;
		$("mainCategoryId_"+param.id).value = category.categoryId;
	}
	if(param.name.indexOf("otherCategory")!=-1){
		var selectOption = $j("#otherCategory_"+param.id+" option[value=" + category.categoryId + "]");
		if (selectOption.length == 0) {
			var optionsInput = document.getElementById("otherCategory_"+param.id).options;
			var option = new Option(category.categoryName, category.categoryId);
			optionsInput[optionsInput.length] = option;
		}else{
			alert(__FMT.productDetail_category_existed);
		}
	}
}

function fnDeleteCategory(selectId){
	var selectOption = $j("#otherCategory_"+selectId+" option:selected");
	if (selectOption.length > 0) {
		selectOption.remove();
	}
}

function fnModifySupplierHandler(supplier,id){
	if(id == "mainSupplier"){
		$('mainSupplier').value = supplier.supplierName;
		$('mainSupplierId').value = supplier.supplierId;
	}
	if(id == "otherSupplier"){
		var selectOption = $j("#otherSupplier option[value=" + supplier.supplierId + "]");
		if (selectOption.length == 0) {
			var optionsInput = document.getElementById("otherSupplier").options;
			var option = new Option(supplier.supplierName, supplier.supplierId);
			optionsInput[optionsInput.length] = option;
		}else{
			alert('"'+supplier.supplierName+'"已选择!');
		}
	}
}

function fnDeleteSupplier(){
	var selectOption = $j("#otherSupplier option:selected");
	if (selectOption.length > 0) {
		selectOption.remove();
	}
}


/**
 * 新增批发价
 */
function fnAddWholesalePrice(){
	var productSkuWholesalePricesFormTR = '';
	productSkuWholesalePricesFormTR += '<tr>';
	productSkuWholesalePricesFormTR += '<td><input class="Field40" name="wholesalePrice_minQuantity"  type="text"/></td>';
	productSkuWholesalePricesFormTR += '<td><input class="Field100" name="wholesalePrice_price" type="text"/></td>';
	productSkuWholesalePricesFormTR += '<td><a id="btnDelWholesalePrice" onclick="fnDeleleTableTR(this);" href="javascript:void(false)" class="button negative"><span class="cross icon"></span>' + __FMT.button_small_delete + '</a></td>';
	productSkuWholesalePricesFormTR += '</tr>';
	$j("#productSkuWholesalePricesForm").append(productSkuWholesalePricesFormTR);
	fnInitValidateWholesaPrice();
}

/**
 * 初始化批发价验证
 */
function applyValidateWholeSalePrice(){
	var wholesalePrice_minQuantitys = $j("#productSkuWholesalePricesForm [name='wholesalePrice_minQuantity']");
	$j.each(wholesalePrice_minQuantitys, function(){
		applyValidate(this, "required,integer,minValue=2");
	});
	var wholesalePrice_prices = $j("#productSkuWholesalePricesForm [name='wholesalePrice_price']");
	$j.each(wholesalePrice_prices, function(){
		applyValidate(this, "required,price");
	});
}

/**
 * 去除批发价验证
 */
function removeValidateWholeSalePrice(){
	var wholesalePrice_minQuantitys = $j("#productSkuWholesalePricesForm [name='wholesalePrice_minQuantity']");
	$j.each(wholesalePrice_minQuantitys, function(){
		removeValidate(this, "required,integer,minValue=2");
	});
	var wholesalePrice_prices = $j("#productSkuWholesalePricesForm [name='wholesalePrice_price']");
	$j.each(wholesalePrice_prices, function(){
		removeValidate(this, "required,price");
	});
}

/**
 * 删除表格当前行
 * @param {Object} btnDel
 */
function fnDeleleTableTR(btnDel){
	$j($j(btnDel).parents("tr")[0]).remove();
}
 
 /**
  * 产品媒体初始化一个input（创建）
  * @param {Object} divId要添加的Div
  * @param {Object} uploadInputMediaType （0表示更多图片，非0表示附件）
  */
 function fnCreateUploadInput(divId, uploadInputMediaType){
 	var id = "1" + new Date().getTime().toString().substr(6);
 	var inputUploadHtml = "";
 	var productMedia_img = "media_noPhoto.gif";
 	var productDetail_media_url_desc = "";
 	inputUploadHtml += '<div class="product-media" id="productMedia_div_' + id + '">';
 	inputUploadHtml += '<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="no-border">';
 	inputUploadHtml += '<tr>';
 	inputUploadHtml += '<td class="list" width="15%" rowspan="2" align="center"><input type="hidden" id="-' + id + '" name="productMediaIds" value="-' + id + '""><input type="hidden" name="productMediaTypes" value="' + uploadInputMediaType + '">';
 	if (uploadInputMediaType == 0) {
 		inputUploadHtml += '<img id="productMedia_img_' + id + '" src="' + __ctxPath + '/images/media_img.gif" width="60" height="60" />';
 		productDetail_media_url_desc = __FMT.productDetail_moreImage_desc;
 	} else {
 		inputUploadHtml += '<img id="productMedia_img_' + id + '" src="' + __ctxPath + '/images/accessorie_ashen.gif" width="60" height="60" />';
 		inputUploadHtml += '<br/><div class="txt" id="productMedia_img_txt_' + id + '">上传附件</div>';
 		productDetail_media_url_desc = __FMT.productDetail_accessories_desc;
 	}
 	inputUploadHtml += '<input type="hidden" id="productMedia_deleteds_' + id + '" name="productMedia_deleteds" value="1">';
 	inputUploadHtml += '</td><td class="list" width="42%">';
 	inputUploadHtml += '<input id="productMedia_url_' + id + '" name="productMedia_urls" type="text" style="width:330px;"/><span id="productMediaBtnPlaceHolderId_'+id+'"></span>';
 	inputUploadHtml += '</td><td class="list" width="8%">';
 	inputUploadHtml += '<input name="add_empty_item" type="image" src="' + __ctxPath + '/images/icon/icon_add.gif" onclick="fnCreateUploadInput(\'' + divId + '\',' + uploadInputMediaType + ');return false;" title="' + __FMT.productDetail_moreImage_addEmptyImage + '"/>&nbsp;&nbsp;';
 	inputUploadHtml += '<input name="remove_empty_item" type="image" src="' + __ctxPath + '/images/icon/icon_del.gif" onclick="fnRemoveUploadInput(' + id + ',\'' + divId + '\');return false;" title="' + __FMT.productDetail_moreImage_removeThisImage + '"/>';
 	inputUploadHtml += '</td>';
 	inputUploadHtml += '<td class="list" width="35%" rowspan="2">' + productDetail_media_url_desc + '</td>';
 	inputUploadHtml += '</tr>';
 	inputUploadHtml += '<tr>';
 	inputUploadHtml += '<td class="list" colspan="2">' + __FMT.productMedia_mediaDescription + '：<br/>';
 	inputUploadHtml += '<input id="productMedia_desc_' + id + '" name="productMedia_descs" type="text" style="width:400px;"/>';
 	inputUploadHtml += '</td>';
 	inputUploadHtml += '</tr>';
 	inputUploadHtml += '</table>';
 	inputUploadHtml += '</div>';
 	$j("#" + divId).append(inputUploadHtml);
 	if (uploadInputMediaType == 0) {
 		initSwfUploadInstance({uploadCategory:"productMedia",fileInputId:"productMedia_url_"+id,btnPlaceHolderId:"productMediaBtnPlaceHolderId_"+id,onComplete:fnUploadMoreImgHandler,uploadFileTypes:"*.jpg",previewSize:"a"});
 	} else {
 		initSwfUploadInstance({uploadCategory:"productMedia",fileInputId:"productMedia_url_"+id,btnPlaceHolderId:"productMediaBtnPlaceHolderId_"+id,onComplete:fnUploadAccessorieHandler,uploadFileTypes:"*.*"});
 	}
 	fnShowOrHideProductMediaBtn(divId);
 }

/**
 * 删除产品媒体的某上传Input
 * @param {Object} id 需删除的上传Input所在的DIV
 * @param {Object} parentDivId 上传控制所在的Form的ID
 */
function fnRemoveUploadInput(id, parentDivId){
	$j('#productMedia_div_' + id).remove();
	if($j(".product-media","#"+parentDivId).size() ==0){
		if(parentDivId == "productMoreImages"){
			fnCreateUploadInput(parentDivId,0);		
		}else{
			fnCreateUploadInput(parentDivId,1);		
		}
	}
	fnShowOrHideProductMediaBtn(parentDivId);
}

/**
 * 处理产品媒体按钮添加、删除按钮显示或隐藏
 * @param {Object} divId 需要处理的产品媒体Form ID
 */
function fnShowOrHideProductMediaBtn(divId){
	var parentDiv = $j("#" + divId);
	$j("input[name='add_empty_item']", parentDiv).not(":last").hide();
	$j("input[name='add_empty_item']:last", $j("#" + divId)).show();
	
}

function fnUploadMoreImgHandler(file){
	var moreImgId = file.fileInputId.replace("productMedia_url_", "productMedia_img_");
	$j("#" + moreImgId).get(0).src = __mediaPath + file.previewUrl;
	$j("#productMedia_deleteds_" + fileInputId.replace("productMedia_url_", "")).get(0).value = 0;
	fnShowOrHideProductMediaBtn("productMoreImages");
}

function fnUploadAccessorieHandler(file){
	var fileInputId = file.fileInputId.replace("productMedia_url_", "");
	$j("#productMedia_img_" + fileInputId).get(0).src = __ctxPath + "/images/accessorie_hight_light.gif";
	$j("#productMedia_img_txt_" + fileInputId).html(file.url.substring(file.url.lastIndexOf("/") + 1, file.url.lastIndexOf(".")));
	fnShowOrHideProductMediaBtn("productAccessories");
}
 
/**
 * 对产品媒体添加验证
 */
function applyValidatateProductMedia(){
	$j("input[name=productMedia_urls]").attr("validconf", "required,maxlength=255");
	$j("input[name=productMedia_descs]").attr("validconf", "maxlength=256");
}


function removeValidatateProductMedia(){
	$j("input[name=productMedia_urls]").attr("validconf", "");
	$j("input[name=productMedia_descs]").attr("validconf", "");
}


function setFocusOnTab(id){
	$j('#'+id+'-tab').addClass("ui-tabs-selected");
	$j('#'+id+'-tab').siblings().removeClass("ui-tabs-selected");
	$j('#'+id).removeClass("ui-tabs-hide");
	$j('#'+id).siblings('.ui-tabs-panel').addClass("ui-tabs-hide");
}
</script>
</body>