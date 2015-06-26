var orientedTabs = null;
$j(document).ready(function(){
	//是否为添加导航页面还是tab编辑页面
	if ($j("#isOriented").val() == "true") {
		//初始化导航流程,产品包与非产品包的添加导航流程不一致
		if ($j("#isPackage").val() == "true") {
			orientedTabs = new Array("", "productInfo", "productSkuInfo", "packageItemInfo", "descriptionInfo", "productMediaInfo", "seoInfo");
		} else {
			orientedTabs = new Array("", "productInfo", "productSkuInfo", "featureInfo", "descriptionInfo", "productMediaInfo", "seoInfo","supplierInfo");
		}
		//导航流程页，当orientedTab为空时，直接显示第一个导航流程
		var orientedTab = $j("#orientedTab").val();
		if (orientedTab == "") 
			orientedTab = 1;
		fnTabOriented(orientedTab);
	} else {
		//初始化产品编辑的tab显示
		$j("#productTab").appTabs({type:2});
	}
	//产品编辑页面内容初始化完毕时才显示，（默认为隐藏）
	$j("#productTab").show();
	fnInitProductSkuInfoForm();
	//添加验证
	applyValidate($("mainCategoryId"), "required");
	applyValidate($("productTypeId"), "required");
	applyValidate($("preOrBackOrderLimit"), "required,integer,minValue=0");
	applyValidate($("expectedReleaseDate"), "required");
	
	//更新 tab lable
	updateTabLable("#"+$j("#productCode").val(),$("productId").value);
	
	if($("productId").value!=""&&$("isPackage").value!='true'){
		fnInitProductAccesssSelect();
	}
	$j("#productMoreImages").sortable();
	$j("#productMoreImages_d").sortable();
	$j("#productAccessories").sortable();
	btnToP();
});
/**
 * 初始化SkuForm页面
 */
function fnInitProductSkuInfoForm(){
	$j("#productSkuForm").appTabs();
	$j("#productSkuForm").show();
	initValidateSkuForm();
}

/**
 * 初始化SkuForm页面的验证
 */
function initValidateSkuForm(){
	fnInitValidateWholesaPrice();
}

/**
 * 保存产品所有数据的回调
 * @param {Object} data
 */
function fnSaveProductHandler(result){
	fnSaveCallbackShowError(result);
	if (result.status == 1) {
		var data=result.data;
		$j("#productId").val(data.productId);
		$j("#origAvailabilityRule").val($j("#availabilityRule").val());
		updateTabLable("#"+data.productCode,data.productId);
		if (data.productSkuId) {
			$j("#productSkuId").val(data.productSkuId);
		}
		if (data.newProductMediaIds) {
			for (var i = 0; i < data.newProductMediaIds.length; i++) {
				var newProductMediaId = data.newProductMediaIds[i].split("_");
				$j("#" + newProductMediaId[0]).val(newProductMediaId[1]);
			}
		}
		//保存成功后，刷新父窗口
		//fnRefurbishOpener();
		sysMsg4p(result.msg);
	}
}

//新增产品时，最后完成保存时，重新刷新编辑页面
function fnLastSaveProductHandler(reslut){
	fnSaveCallbackShowError(reslut);
	if (reslut.status == 1) {
		//fnRefurbishOpener();
		location.href = __ctxPath + '/catalog/product/window.html?doAction=edit&productId=' + reslut.data.productId+"&tid="+$j("#tid").val();
	}
}

/**
 * 保存产品主要数据后的回调，主要是跳到下一步
 * @param {Object} data
 */
function fnSaveProductInfoHandler(result){
	fnSaveCallbackShowError(result);
	if (result.status == 1) {
		location.href = __ctxPath + '/catalog/product/window.html?doAction=edit&orientedTab=2&productId=' + result.data.productId+"&tid="+$j("#tid").val();
	}
	
}


/**
 * 保存产品，没有指定保存Form时，默认保存productForm
 * @param {Object} fnHandler 保存后的回调
 * @param {Object} saveForm 需保持的Form
 */
function saveProduct(fnHandler, saveForm){
	if (!saveForm) {
		saveForm = "productForm";
		var fullDescription=tinyMCE.get("fullDescription").getContent();
		$j("#fullDescription").val(fullDescription);
		//hqm
		if (($("planStartTime").value !="") &&(fnCompareDate($("planStartTime").value,$("planEndTime").value) >=0) ){
	   		$j('#planEndTime').focus();
	   		alert(__FMT. productDetail_validate_endTimeGtStartTime);
	   		return;
   		}	
		if (!validateProduct($j('#' + saveForm).get(0))) {
			return;
		}
	}
	var entityName = $j("#productName").val();
	if (confirm(__FMT.common_message_confirmSaveThis + entityName + "?")) {
		//获取产品目录信息
		var categoryIds = $j("select[name2='categoryIds'] option");
		var postData = "";
		$j.each(categoryIds, function(){
			postData += "categoryIds=" + this.value + "&";
		});
		//首次创建产品时，需要将主商品分类添加到categoryIds
		if ($j("#productId").val() == '') {
			var mainCategoryId = "categoryIds=" + $j("#mainCategoryId").val() + "&";
			if (postData.indexOf(mainCategoryId) == -1) {
				postData += mainCategoryId;
			}
		} 
		var supplierIds = $j("#supplierIds option");
		$j.each(supplierIds, function(){
			postData += "supplierIds=" + this.value + "&";
		});
		postData += $j('#' + saveForm + ' :input').serialize();
		//__testShowParam(postData);
		$j.post(__ctxPath + "/catalog/product.html?doAction=save", postData, fnHandler, "json");
	}
	return false;
}

function saveProduct2(){
	saveProduct(fnSaveProductHandler);
}

/**
 * 添加产品时，选择产品类型后的回调；主要检查当前产品是否可以选择设置多个Sku
 */
function fnSelectProductTypeHandler(){
	var productTypeId = $j("#productTypeId").val();
	var hasActiveSkuOptionProductTypeIds = $j("#hasActiveSkuOptionProductTypeIds").val();
	if (hasActiveSkuOptionProductTypeIds.indexOf("," + productTypeId + ",") == -1) {
		$j("#isVariationSpan").hide();
	} else {
		$j("#isVariationSpan").show();
	}
	var productTypeJson=null;
	for(i in productTypeJSONList){
		if(productTypeId==productTypeJSONList[i].productTypeId){
			productTypeJson=productTypeJSONList[i];
			break;
		}
	}
	if(productTypeJson){
		$j("#minOrderQuantity").val(productTypeJson.minOrderQuantity);
		$j("#templatePath").val(productTypeJson.templatePath);
	}else{
		$j("#minOrderQuantity").val("");
		$j("#templatePath").val("");
	}
}

/**
 * 删除当前编辑的产品
 */
function fnDeleteProduct(){
	var entityName = $j("#productName").val();
	if (confirm(__FMT.common_message_confirmDeleteThis + " " + entityName + "?")) {
		var paraDate = {
			productId: $j("#productId").val()
		};
		$j.post(__ctxPath + "/catalog/product/dialog.html?doAction=delete", paraDate, fnDeleteProductHandler,"json");
	}
}

/**
 * 成功删除产品后的回调
 */
function fnDeleteProductHandler(result){
	if(result.status==1){
		sysMsg4p(result.msg);
		fnRefurbishOpener();
		if(parent&&opener){
			window.close();
		}else if(parent&&parent!=self){
			parent.fnRemoveTab($j("#tid").val());
		}else{
			window.close();
		}
	}else{
		sysMsg4p(result.msg);
	}
}

/**
 * 编辑产品时，刷新父窗口的商品列表
 */
function fnRefurbishOpener(){
	if (opener && !opener.closed) {
		if (opener.fnRefurbishProductList) 
			opener.fnRefurbishProductList();
	}else if(parent){
		if (parent.fnRefurbishProductList) 
			parent.fnRefurbishProductList();
	}
}

/**
 * 设置编辑产品的导向显示
 * @param {Object} tabId_index，需进入的tab的下标
 * @param {Object} validateFormId_index 需验证的tab的下标
 */
function fnTabOriented(tabId_index, validateFormId_index){
	var tabId = orientedTabs[tabId_index];
	if (validateFormId_index) {
		var validateFormId = orientedTabs[validateFormId_index];
		var result = validateProduct($j("#" + validateFormId).get(0));
		if (result && validateFormId == 'packageItemInfo') {
			var productPackageItemIds = $j("input[name='productPackage_productPackageItemId']", $("#packageItemInfo"));
			if (productPackageItemIds.length < 2) {
				alert(__FMT.productDetail_productPackage_item_less2);
				result = false;
			}
			if (result) {
				result = confirm(__FMT.productDetail_productPackage_saved_message);
			}
		}
		if (!result) 
			return;
		if (validateFormId == 'productInfo') {
			//首次创建产品
			if ($j("#productId").val() == '') {
				saveProduct(fnSaveProductInfoHandler, 'productInfo');
				return;
			}
		} else if (validateFormId == 'productSkuInfo') {
			if (fnIsExistSkuCode($j("#productSkuCode").val(), $j("#productSkuId").val())) 
				return;
		} else if (validateFormId == 'supplierInfo') {
			saveProduct(fnLastSaveProductHandler);
		}
	}
	$j("#productTab").children("div[id!=" + tabId + "]").hide();
	$j("#" + tabId).show();
	$j("span[name=" + tabId + "]", $j("#productOriented")).attr("class", "selected");
	$j("span[name=" + tabId + "]", $j("#productOriented")).prevAll("span").attr("class", "over");
	$j("span[name=" + tabId + "]", $j("#productOriented")).nextAll("span").removeAttr("class");
	
}

/**
 * 对产品媒体添加验证
 */
function fnInitValidProductMedia(){
	$j("input[name=productMedia_urls]").attr("validconf", "maxlength=255");
	$j("input[name=productMedia_urls_d]").attr("validconf", "maxlength=255");
	$j("input[name=productMedia_descs]").attr("validconf", "maxlength=256");
	$j("input[name=productMedia_urlsUp]").attr("validconf", "maxlength=255");
}

/**
 * 添加产品包Item，插入到ProductPackageForm内
 * （选择Sku后的回调）
 * @param {Object} productSku
 */
function fnAddProductPackageItem(productSku){
	var productPackageItemIds = $j("input[name='productPackage_productPackageItemId'][value='" + productSku.productSkuId + "']", $("#packageItemInfo"));
	if (productPackageItemIds.length > 0) {
		alert(__FMT.productDetail_productPackage_item_added);
		return;
	}
	var productPackageItemFormTR = '';
	productPackageItemFormTR += '<tr>';
	productPackageItemFormTR += '<td>' + productSku.productSkuCode + '<input type="hidden" name="productPackage_productPackageItemId" value="' + productSku.productSkuId + '"/></td>';
	productPackageItemFormTR += '<td>' + productSku.product.productName + '</td>';
	productPackageItemFormTR += '<td>' + (productSku.product.brand ? productSku.product.brand.brandName : '') + '</td>';
	productPackageItemFormTR += '<td>' + productSku.price + '</td>';
	productPackageItemFormTR += '<td><input name="productPackage_quantity" type="text" value="1"/></td>';
	productPackageItemFormTR += '<td><input value="' + __FMT.button_small_delete + '" type="button" class="btn-back" onclick="$j(this).parent().parent().remove();"/></td>';
	productPackageItemFormTR += '</tr>';
	$j("#productPackageItemForm").append(productPackageItemFormTR);
	fnInitValidateProductPackage();
	productSkuSelector_close();
}

/**
 * 初始化产品包Form的验证，主要是添加产品包Item时（插入Tr），对数量输入框加上验证
 */
function fnInitValidateProductPackage(){
	var productPackage_quantitys = $j("#productPackageItemForm [name='productPackage_quantity']");
	$j.each(productPackage_quantitys, function(){
		applyValidate(this, "required,integer,minValue=1");
	});
}

/**
 * 添加目录时，在目录选择器选择了目录后的回调，
 * @param {Object} tree
 */
function fnAddCategoryHandler(category,catalogId){
	var mainCategoryOption = $j("#mainCategoryId_"+catalogId+" option:selected").get(0);
	//添加产品时，判断所选的额外目录是否已经是当前的主目录
	if (!mainCategoryOption) {
		if (category.categoryId == $j("#mainCategoryId_"+catalogId).val()) {
			alert(__FMT.productDetail_category_existed);
			return;
		}
	}
	var selectOption = $j("#categoryIds_"+catalogId+" option[value=" + category.categoryId + "]");
	if (selectOption.length == 0) {
		var options = document.getElementById("categoryIds_"+catalogId).options;
		var option = new Option(category.categoryName, category.categoryId);
		options[options.length] = option;
		if ($j("#productId").val() != '') {
			var mainCategoryOptions = document.getElementById("mainCategoryId_"+catalogId).options;
			var option2 = new Option(category.categoryName, category.categoryId);
			mainCategoryOptions[mainCategoryOptions.length] = option2;
		}
	} else {
		alert(__FMT.productDetail_category_existed);
	}
	
}

/**
 * 删除所选的目录
 */
function fnDeleteCategory(catalogId){
	var selectOption = $j("#categoryIds_"+catalogId+" option:selected");
	if (selectOption.length > 0) {
		if ($j("#productId").val() != '') {
			var mainCategoryOption = $j("#mainCategoryId_"+catalogId+" option:selected").get(0);
			//主目录不能删除
			for (var i = 0; i < selectOption.length; i++) {
				if (selectOption[i].value == mainCategoryOption.value) {
					alert(__FMT.productDetail_mainCategory_cannot_delete);
					return;
				}
			}
			for (var i = 0; i < selectOption.length; i++) {
				$j("#mainCategoryId_"+catalogId+" option[value=" + selectOption[i].value + "]").remove();
			}
		}
		selectOption.remove();
	}
}

/**
 * 选择销售方式时，判断是否需要设置可预订数量及预售产品的到货日期
 * @param {Object} availabilityRule
 */
function fnSelectAvailabilityRuleHandler(availabilityRule){
	var origAvailabilityRule=$j("#origAvailabilityRule").val();
	if(origAvailabilityRule!=""&&((origAvailabilityRule==4&&availabilityRule!=4)||(origAvailabilityRule!=4&&availabilityRule==4)||(origAvailabilityRule==5&&availabilityRule!=5)||(origAvailabilityRule!=5&&availabilityRule==5))){
		var msg4="对产品销售规则进行永远可售和非永远可售之间的切换，会影响本产品库存管理的准确性，确定要切换销售规则?";
		var msg5="对产品销售规则进行无库存销售和非无库存销售之间的切换，会影响本产品库存管理的准确性，确定要切换销售规则?";
		if(!confirm(availabilityRule==4?msg4:msg5)){
			$j("#availabilityRule").val(origAvailabilityRule);
			return false;
		}
	}
	if (availabilityRule == 2) {
		$j("#preOrBackOrderLimit").removeAttr("disabled");
		$j("#expectedReleaseDate").removeAttr("disabled").next().show();
	} else if (availabilityRule == 3) {
		$j("#preOrBackOrderLimit").removeAttr("disabled");
		$j("#expectedReleaseDate").attr("disabled", "disabled").next().hide();
	} else {
		$j("#preOrBackOrderLimit").attr("disabled", "disabled");
		$j("#expectedReleaseDate").attr("disabled", "disabled").next().hide();
	}
	validateField($("preOrBackOrderLimit"));
	validateField($("expectedReleaseDate"));
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
function fnInitValidateWholesaPrice(){
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
 * 删除表格当前行
 * @param {Object} btnDel
 */
function fnDeleleTableTR(btnDel){
	$j($j(btnDel).parents("tr")[0]).remove();
}

/**
 * 保存产品SkuInfoForm数据
 * （Sku列表）
 */
function fnSaveProductSkuInfo(){
	if (!validateProduct($j("#productSkuForm").get(0))) {
		return;
	}
	var postData = "";
	var productId = $j("#productId").val();
	postData += "productId=" + productId + "&";
	postData += $j('#productSkuForm :input').serialize();
	var url = __ctxPath + "/catalog/productSku/dialog.html?doAction=save";
	$j.post(url, postData, fnSaveProductSkuHandler, "json");
	return false;
}

/**
 * 保存产品SkuForm数据后的回调
 * （Sku列表）
 * @param {Object} data
 */
function fnSaveProductSkuHandler(result){
	fnSaveCallbackShowError(result);
	if (result.status == 1) {
		$j("#productSkuId").val(result.data.productSkuId);
		dlgProductSkuEditor_close();
	}
	fnRefurbishSkuList();
}

/**
 * 刷新Sku列表
 * （Sku列表）
 */
function fnRefurbishSkuList(){
	var productId = $j("#productId").val();
	$j("#productSkuList").load(__ctxPath + "/catalog/product/dialog.html?doAction=refurbishSkuList&productId=" + productId);
	$j("#skuListSelect").load(__ctxPath + "/catalog/product/dialog.html?doAction=reloadSkuSelect&productId=" + productId);
}

/**
 * 更新设置默认Sku
 * （Sku列表）
 * @param {Object} defaultProductSkuId要设置为默认Sku的skuID
 */
function fnSetProductDefaultSku(defaultProductSkuId){
	if (confirm(__FMT.productDetail_setDefaultProductSkuId_message)) {
		$j.get(__ctxPath + "/catalog/productSku.html?doAction=setProductDefaultSku", {
			productSkuId: defaultProductSkuId
		}, function(){
			$j("#old_defaultProductSkuId").val(defaultProductSkuId);
			fnRefurbishSkuList();
		})
	} else {
		var old_defaultProductSkuId = $j("#old_defaultProductSkuId").val();
		$j("#productSkuList [name='defaultProductSkuId'][value=" + old_defaultProductSkuId + "]").attr("checked", "checked");
	}
}
/**
 * 检查SkuCode是否已经被使用
 * @param {Object} skuCode
 * @param {Object} skuId
 */
function fnIsExistSkuCode(skuCode, skuId){
	var url = __ctxPath + "/catalog/productSku/dialog.html?doAction=isExistSkuCode";
	var postData = "productSkuCode=" + skuCode + "&productSkuId=" + skuId;
	var result = false;
	$j.ajax({
		type: "Post",
		url: url,
		data: postData,
		success: function(result){
			if (result.status == 2) {
				alert(result.msg);
				result = true;
			}
		},
		dataType: "json",
		async: false
	});
	return result;
}

/**
 * 编辑库存，从Sku编辑Form跳到该产品的
 * @param {Object} productId
 * @param {Object} productSkuId
 */
function fnEditInventory(productId, productSkuId){
	if (confirm(__FMT.productSkuDetail_enter_inventory_confirm)) {
		location.href = __ctxPath + "/inventory/inventory/window.html?productId=" + productId + "&productSkuId=" + productSkuId;
	}
}

/**
 * 打印提交数据，（截取打印，测试中）
 * @param {Object} postData
 */
function __testShowParam(postData){
	var j = 0;
	for (var i = 0; i < postData.length; i += 130) {
		j += 130;
		if (i > postData.length) {
			j = postData.length;
		}
		log(postData.substring(i, j));
	}
}

function fnInitProductAccesssSelect(){
	var accessorySelect=$j("#accessorySelect");
	accessorySelect.children().remove();
	for(i in accessoryGroupJSONList){
		var accessoryGroup=accessoryGroupJSONList[i];
		if($j("#accessoryGroupId_"+accessoryGroup.accessoryGroupId).length==0)
			accessorySelect.append('<option value="'+accessoryGroup.accessoryGroupId+'">'+accessoryGroup.groupName+'   ('+accessoryGroup.groupDesc+')</option>');
	}
}
function fnAddAccessionGroup(){
	var accessorySelect=$j("#accessorySelect");
	var selectedOption=accessorySelect.children("[selected]");
	if(selectedOption.length>0){
		for(i in accessoryGroupJSONList){
			var accessoryGroup=accessoryGroupJSONList[i];
			if(accessoryGroup.accessoryGroupId==selectedOption.val()){
				var content='<tr id="accessoryGroupId_'+accessoryGroup.accessoryGroupId+'">'+
						'<td class="FieldLabel">'+
						'<a onclick="fnDelAccessionGroup('+accessoryGroup.accessoryGroupId+');" href="javascript:void(false)" class="button negative"><span class="cross icon"></span>删除</a>'+
						'&nbsp;&nbsp;<input type="checkbox" onclick="fnSelectAllGroupItem(this)"/>&nbsp;&nbsp;'+
							accessoryGroup.groupName
						+':</td>'+
						'<td>';
						for(j in accessoryGroup.accessorys){
							var accessory=accessoryGroup.accessorys[j];
							content+='<input name="accessoryIds" id="accessoryIds_'+accessory.accessoryId+'" type="checkbox" value="'+accessory.accessoryId+'"/>';
							content+='<label for="accessoryIds_'+accessory.accessoryId+'">'+accessory.accessoryName;
							if(accessory.price>0){
								content+='('+__defaultCurrencySymbol+accessory.price+')';
							}
							content+='</label>';
							content+='&nbsp;&nbsp;';
						}
						content+='</td>'+'</tr>';
				$j(accessorySelect.parents("tr")[0]).before(content);
				selectedOption.remove();
			}
		}
	}
}
function fnDelAccessionGroup(groupId){
	$j("#accessoryGroupId_"+groupId).remove();
	fnInitProductAccesssSelect();
}

function fnSelectAllGroupItem(c){
	$j(c).parent().siblings().children("[type='checkbox']").attr('checked',c.checked);
}
/**
 * 添加供应商
 */
function fnAddSupplierHandler(supplier){
	var selectOption = $j("#supplierIds option[value=" + supplier.supplierId + "]");
	if (selectOption.length == 0) {
		var options = document.getElementById("supplierIds").options;
		var option = new Option(supplier.supplierName, supplier.supplierId);
		options[options.length] = option;
		var defaultSupplierOptions = document.getElementById("defaultSupplierId").options;
		var option2 = new Option(supplier.supplierName, supplier.supplierId);
		defaultSupplierOptions[defaultSupplierOptions.length] = option2;
	} else {
		alert("供应商'"+supplier.supplierName+"'已存在!");
	}
}

/**
 * 删除所选的供应商
 */
function fnDeleteSupplier(){
	var selectOption = $j("#supplierIds option:selected");
	if (selectOption.length > 0) {
		for (var i = 0; i < selectOption.length; i++) {
			var defaultOption = $j("#defaultSupplierId option[value=" + selectOption[i].value + "]");
			defaultOption.remove();
		}
		selectOption.remove();
	}
}

function fnUploadMoreImageHandler(file){
	showUploadProudctMedia('productMoreImages',0,file);
}

/**
 * 上传产品大图
 * @param file
 * @return
 */
function fnUploadMoreImage_d_Handler(file){
	showUploadProudctMedia_d('productMoreImages_d',0,file);
}

function showUploadProudctMedia_d(divId,uploadInputMediaType,file){
	var id = "1" + new Date().getTime().toString().substr(6);
	var inputUploadHtml = "";
	var productMedia_img = "media_noPhoto.gif";
	var productDetail_media_url_desc = "";
	inputUploadHtml += '<div class="product-media" id="productMedia_div_' + id + '">';
	inputUploadHtml += '<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="no-border">';
	inputUploadHtml += '<tr>';
	inputUploadHtml += '<td class="list" width="15%" align="center"><input type="hidden" id="-' + id + '" name="productMediaIds" value="-' + id + '""><input type="hidden" name="productMediaTypes" value="' + uploadInputMediaType + '">';
	if (uploadInputMediaType == 0) {
		inputUploadHtml += '<img id="productMedia_img_' + id + '" src="' + __mediaPath +file.previewUrl+'" width="60" height="60" />';
	} else {
		inputUploadHtml += '<img id="productMedia_img_' + id + '" src="' + __ctxPath + '/images/accessorie_hight_light.gif" width="60" height="60" />';
	}
	inputUploadHtml += '<input type="hidden" id="productMedia_deleteds_' + id + '" name="productMedia_deleteds" value="0">';
	inputUploadHtml += '</td><td class="list" width="24%">';
	inputUploadHtml += '<input id="productMedia_url_' + id + '" name="productMedia_urls_d" type="text" style="width:400px;" value="'+file.url+'"/></span>';
	inputUploadHtml += '<br />';
	inputUploadHtml += __FMT.productMedia_mediaDescription;
	inputUploadHtml += '<br />';
	inputUploadHtml += '<input id="productMedia_desc_' + id + '" name="productMedia_descs" type="text" style="width:400px;"/>';
	inputUploadHtml += $j("#skuListSelect").html();
	inputUploadHtml += '</td><td class="list">';
	inputUploadHtml += '&nbsp;&nbsp;<input name="remove_empty_item" type="image" src="' + __ctxPath + '/images/icon/icon_del.gif" onclick="fnRemoveUploadMedia('+ id+',this);return false;" title="' + __FMT.productDetail_moreImage_removeThisImage + '"/>';
	inputUploadHtml += '</td>';
	inputUploadHtml += '</tr>';
	inputUploadHtml += '</table>';
	inputUploadHtml += '</div>';
	$j("#" + divId).append(inputUploadHtml);
	fnInitValidProductMedia();
}
/**
 * 上传产品额外大图
 * @param file
 * @return
 */
function fnUploadMoreImage_up_Handler(file){
	showUploadProudctMedia_up('productMoreImages_up',0,file);
}

function showUploadProudctMedia_up(divId,uploadInputMediaType,file){
	var id = "1" + new Date().getTime().toString().substr(6);
	var inputUploadHtml = "";
	var productMedia_img = "media_noPhoto.gif";
	var productDetail_media_url_desc = "";
	inputUploadHtml += '<div class="product-media" id="productMedia_div_' + id + '">';
	inputUploadHtml += '<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="no-border">';
	inputUploadHtml += '<tr>';
	inputUploadHtml += '<td class="list" width="15%" align="center"><input type="hidden" id="-' + id + '" name="productMediaUpIds" value="-' + id + '""><input type="hidden" name="productMediaTypesUp" value="' + uploadInputMediaType + '">';
	if (uploadInputMediaType == 0) {
		inputUploadHtml += '<img id="productMedia_img_' + id + '" src="' + __mediaPath +file.previewUrl+'" width="60" height="60" />';
	} else {
		inputUploadHtml += '<img id="productMedia_img_' + id + '" src="' + __ctxPath + '/images/accessorie_hight_light.gif" width="60" height="60" />';
	}
	inputUploadHtml += '<input type="hidden" id="productMedia_deleteds_' + id + '" name="productMedia_deletedsUp" value="0">';
	inputUploadHtml += '</td><td class="list" width="24%">';
	inputUploadHtml += '<input id="productMedia_url_' + id + '" name="productMedia_urlsUp" type="text" style="width:400px;" value="'+file.url+'"/></span>';
	inputUploadHtml += '<br />';
	inputUploadHtml += __FMT.productMedia_mediaDescription;
	inputUploadHtml += '<br />';
	inputUploadHtml += '<input id="productMedia_desc_' + id + '" name="productMedia_descsUp" type="text" style="width:400px;"/>';
	inputUploadHtml += '</td><td class="list">';
	inputUploadHtml += '&nbsp;&nbsp;<input name="remove_empty_item" type="image" src="' + __ctxPath + '/images/icon/icon_del.gif" onclick="fnRemoveUploadMedia('+ id+',this);return false;" title="' + __FMT.productDetail_moreImage_removeThisImage + '"/>';
	inputUploadHtml += '</td>';
	inputUploadHtml += '</tr>';
	inputUploadHtml += '</table>';
	inputUploadHtml += '</div>';
	$j("#" + divId).append(inputUploadHtml);
	fnInitValidProductMedia();
}

/**
 * 上传产品手绘图
 * @param file
 * @return
 */
function fnUploadHandDrawHandler(file){
	showUploadHandDraw('handDrawMediaDiv',0,file);
}

function showUploadHandDraw(divId,uploadInputMediaType,file){
	var id = "1" + new Date().getTime().toString().substr(6);
	var inputUploadHtml = "";
	var productMedia_img = "media_noPhoto.gif";

	if (uploadInputMediaType == 0) {
		inputUploadHtml += '<img src="' + __mediaPath +file.previewUrl+'" width="120" height="120" />';
		inputUploadHtml += '<input name="handDrawUrl" type="text" style="width:400px;" value="'+file.url+'"/></span>';
	} 
	//inputUploadHtml += '&nbsp;&nbsp;<input name="remove_empty_item" type="image" src="' + __ctxPath + '/images/icon/icon_del.gif" onclick="fnRemoveUploadMedia('+ id+',this);return false;" title="' + __FMT.productDetail_moreImage_removeThisImage + '"/>';
	$j("#" + divId).html(inputUploadHtml);
	//fnInitValidProductMedia();
}

/**
 * 上传产品附件
 * @param file
 * @return
 */
function fnUploadAccessoryHandler(file){
	showUploadProudctMedia('productAccessories',1,file);
}

function showUploadProudctMedia(divId,uploadInputMediaType,file){
	var id = "1" + new Date().getTime().toString().substr(6);
	var inputUploadHtml = "";
	var productMedia_img = "media_noPhoto.gif";
	var productDetail_media_url_desc = "";
	inputUploadHtml += '<div class="product-media" id="productMedia_div_' + id + '">';
	inputUploadHtml += '<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="no-border">';
	inputUploadHtml += '<tr>';
	inputUploadHtml += '<td class="list" width="15%" align="center"><input type="hidden" id="-' + id + '" name="productMediaIds" value="-' + id + '""><input type="hidden" name="productMediaTypes" value="' + uploadInputMediaType + '">';
	if (uploadInputMediaType == 0 || uploadInputMediaType == 2) {
		inputUploadHtml += '<img id="productMedia_img_' + id + '" src="' + __mediaPath +file.previewUrl+'" width="60" height="60" />';
	} else {
		inputUploadHtml += '<img id="productMedia_img_' + id + '" src="' + __ctxPath + '/images/accessorie_hight_light.gif" width="60" height="60" />';
	}
	inputUploadHtml += '<input type="hidden" id="productMedia_deleteds_' + id + '" name="productMedia_deleteds" value="0">';
	inputUploadHtml += '</td><td class="list" width="24%">';
	inputUploadHtml += '<input id="productMedia_url_' + id + '" name="productMedia_urls" type="text" style="width:400px;" value="'+file.url+'"/></span>';
	inputUploadHtml += '<br />';
	inputUploadHtml += __FMT.productMedia_mediaDescription;
	inputUploadHtml += '<br />';
	inputUploadHtml += '<input id="productMedia_desc_' + id + '" name="productMedia_descs" type="text" style="width:400px;"/>';
	inputUploadHtml += '</td><td class="list">';
	inputUploadHtml += '&nbsp;&nbsp;<input name="remove_empty_item" type="image" src="' + __ctxPath + '/images/icon/icon_del.gif" onclick="fnRemoveUploadMedia('+ id+',this);return false;" title="' + __FMT.productDetail_moreImage_removeThisImage + '"/>';
	inputUploadHtml += '</td>';
	inputUploadHtml += '</tr>';
	inputUploadHtml += '</table>';
	inputUploadHtml += '</div>';
	$j("#" + divId).append(inputUploadHtml);
	fnInitValidProductMedia();
}

/**
 * 删除产品媒体的某上传Input
 * @param {Object} id 需删除的上传Input所在的DIV
 * @param {Object} parentDivId 上传控制所在的Form的ID
 */
function fnRemoveUploadMedia(id,obj){
	//$j('#productMedia_div_' + id).remove();
	var $this = $j(obj);
	$this.parents(".product-media").remove();
}

function fnUpdateProductPublishTime(){
	var postData = "";
	var publishTime=$("input_publishTime").value;
	var productId = $j("#productId").val();
	postData += "productId=" + productId + "&";
	postData += "publishTime="+publishTime;
	var url = __ctxPath + "/catalog/product/dialog.html?doAction=updateProductPublishTime";
	$j.ajax({
		type: "GET",
		url: url,
		data: postData,
		success: function(result){
			if (result.status == 1) {
				var data=result.data;
				$j("#publishTime_span").html(data.publishTime);
				$j("#input_publishTime").val(data.publishTime);
				$j('#update_publishTime_form').hide();
				$j('#u_publishTime_btn').show();
				sysMsg4p("最新发布时间修改成功!");
			}else{
				sysMsg4p("时间格式错误!",false);
			}
		},
		dataType: "json",
		async: false
	});
}
function updateTabLable(lable,nTId){
	if(parent&&lable&&lable.replace("#","")){
		parent.fnUpdateTab($j("#tid").val(),lable,nTId);
		if(nTId){
			$j("#tid").val(nTId);
		}
	}
}

function removeAllProductImg(id){
	$j("#" + id).html("");
}

