<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<app:pageHeading entityName="${productType.productTypeName}" entityHeadingKey="productTypeDetail.heading" />
<content tag="buttons">
<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this);" />
<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="productType.*" />
<script type="text/javascript">
<!--
//Select基本操作方法
function fnMove(sourceId,destId){
	var selectOption=$j("#"+sourceId+" option:selected").get(0);
	if(selectOption&&selectOption.id==""){
		var index=selectOption.index;
		var options=document.getElementById(destId).options;
		var option=new Option(selectOption.text,selectOption.value);
		option.setAttribute("id",selectOption.id);
		options[options.length]=option;
		//$j(selectOption).remove();
		document.getElementById(sourceId).options[index]=null;
		var sourceOptions=document.getElementById(sourceId).options;
		if(index>=sourceOptions.length)
			index--;
		if(sourceOptions.length>0)		
			sourceOptions[index].selected="selected";
	}
}
function fnDeleteOption(selectId){
	var selectOption=$j("#"+selectId+" option:selected").get(0);
	if(selectOption)
		$j(selectOption).remove();
}

function fnUpdateOption(selectId,text,value){
	var selectOption=$j("#"+selectId+" option:selected").get(0);
	if(selectOption){
		selectOption.text=text;
		selectOption.value=value;
	}
}

function fnMoveOptionUp(sId){
	var selectOption=$j("#"+sId+" option:selected").get(0);
	if(selectOption&&selectOption.index!=0){
		$j(selectOption).insertBefore($j(selectOption).prev());
	}
}

function moveOptionDown(sId){
	var selectOption=$j("#"+sId+" option:selected").get(0);
	var options=$j("#"+sId+" option");
	if(selectOption&&selectOption.index!=options.length-1){
		$j(selectOption).insertAfter($j(selectOption).next());
	}
}

//为Btn注册相应的事件
$j(document).ready(function(){
	$j("#btnMoveRight").click(fnMoveAttributeToRight);
	$j("#btnMoveLeft").click(fnMoveAttributeToLeft);
	$j("#btnGroupItemMoveUp").click(fnGroupItemMoveUp);
	$j("#btnGroupItemMoveDown").click(fnGroupItemMoveDown);
	$j("#btnAddGroup").click(fnAddGroup);
	$j("#btnUpdateGroup").click(fnUpdateGroupOption);
	$j("#btnDeleteGroup").click(fnDeleteGroupOption);
	
	$j("#btnSkuOptionMoveRight").click(fnMoveSkuOptionToRight);
	$j("#btnSkuOptionMoveLeft").click(fnMoveSkuOptionToLeft);
	$j("#btnSkuOptionMoveUp").click(fnSkuOptionMoveUp);
	$j("#btnSkuOptionMoveDown").click(fnSkuOptionMoveDown);
	
	$j("#btnAddRateItem").click(fnAddRateItem);
	$j("#btnUpdateRateItem").click(fnUpdateRateItemOption);
	$j("#btnDeleteRateItem").click(fnDeleteRateItemOption);
	$j("#btnRateItemMoveUp").click(fnRateItemMoveUp);
	$j("#btnRateItemMoveDown").click(fnRateItemMoveDown)
	
	$j("#productTypeGroupAttributes").change(fnShowGroupName);
	$j("#productTypeRateItems").change(fnShowRateItemName);
	
	//注册双击事件
	$j("#otherAttributes").dblclick(fnMoveAttributeToRight);
	$j("#productTypeGroupAttributes").dblclick(fnMoveAttributeToLeft);
	$j("#otherSkuOptions").dblclick(fnMoveSkuOptionToRight);
	$j("#productTypeSkuOptions").dblclick(fnMoveSkuOptionToLeft);
});

function fnMoveAttributeToRight(){
	var options=$j("#productTypeGroupAttributes option");
	if(options.length==0){
		alert("<fmt:message key="productTypeDetail.input.mustAddItemtGroup" />");
	}else if(options.get(0).id.indexOf("group_")==-1){
		alert("<fmt:message key="productTypeDetail.input.firstItemMustGroupToAdd" />");
	}else{
		fnMove('otherAttributes','productTypeGroupAttributes');
	}
	return false;
}

function fnMoveAttributeToLeft(){
	var selectOption=$j("#productTypeGroupAttributes option:selected").get(0);
	if(selectOption&&confirm("<fmt:message key="productTypeDetail.moveOptionsToLeftMessage" />".replace("{0}","\""+selectOption.text.trim()+"\""))){
		fnMove('productTypeGroupAttributes','otherAttributes');
		fnShowGroupName();
	}
	return false;
}

function fnAddGroup(){
	var	groupValue=$j("#groupName").val();
	if($j.trim(groupValue)==""){
		alert("<fmt:message key="productTypeDetail.input.groupName.message" />");
		return;
	}
	var selectOption=$j("#productTypeGroupAttributes option[value="+groupValue+"]");
	if(selectOption.length==0){
		var options=document.getElementById("productTypeGroupAttributes").options;
		var option=new Option("["+groupValue+"]",groupValue);
		option.setAttribute("id","group_0");
		options[options.length]=option;
		$j("#groupName").val("");
	}else{
		alert("<fmt:message key="productTypeDetail.input.groupName.existed.message" />");
	}
}

function fnDeleteGroupOption(){
	fnDeleteOption("productTypeGroupAttributes");
}

function fnUpdateGroupOption(){
	var groupName=$j("#groupName").val();
	fnUpdateOption("productTypeGroupAttributes","["+groupName+"]",groupName);
}

function fnGroupItemMoveUp(){
	fnMoveOptionUp('productTypeGroupAttributes');
	return false;
}

function fnGroupItemMoveDown(){
	moveOptionDown('productTypeGroupAttributes');
	return false;
}

function fnShowGroupName(){
	var selectOption=$j("#productTypeGroupAttributes option:selected").get(0);
	if(selectOption&&selectOption.id.indexOf("group_")!=-1){
		$j("#groupName").val(selectOption.value);
		$j("#btnUpdateGroup").show();
		$j("#btnDeleteGroup").show();
	}else{
		$j("#groupName").val("");
		$j("#btnUpdateGroup").hide();
		$j("#btnDeleteGroup").hide();
	}
}

function fnShowRateItemName(){
	var selectOption=$j("#productTypeRateItems option:selected").get(0);
	$j("#rateItemName").val(selectOption.value);
	$j("#btnUpdateRateItem").show();
	$j("#btnDeleteRateItem").show();
}

function fnMoveSkuOptionToRight(){
	fnMove('otherSkuOptions','productTypeSkuOptions');
	return false;
}

function fnMoveSkuOptionToLeft(){
	var selectOption=$j("#productTypeSkuOptions option:selected").get(0);
	if(selectOption&&confirm('<fmt:message key="productTypeDetail.moveOptionsToLeftMessage" />'.replace("{0}","\""+selectOption.text.trim()+"\""))){
		fnMove('productTypeSkuOptions','otherSkuOptions');
	}
	return false;
}
function fnSkuOptionMoveUp(){
	fnMoveOptionUp('productTypeSkuOptions');
	return false;
}

function fnSkuOptionMoveDown(){
	moveOptionDown('productTypeSkuOptions');
	return false;
}

function fnAddRateItem(){
	var	rateItemName=$j("#rateItemName").val();
	if($j.trim(rateItemName)==""){
		alert("<fmt:message key="productTypeDetail.input.rateItemName.message" />");
		return;
	}
	var selectOption=$j("#productTypeRateItems option[value="+rateItemName+"]");
	if(selectOption.length==0){
		var options=document.getElementById("productTypeRateItems").options;
		var option=new Option(rateItemName,rateItemName);
		option.setAttribute("id","0");
		options[options.length]=option;
		$j("#rateItemName").val("");
	}else{
		alert('<fmt:message key="productTypeDetail.input.rateItemName.existed.message" />');
	}
	var options=$j("#productTypeRateItems option");
	options.click(function(){
		var selectOption=$j("#productTypeRateItems option:selected").get(0);
		$j("#rateItemName").val(selectOption.value);
	});
}

function fnRateItemMoveUp(){
	fnMoveOptionUp('productTypeRateItems');
	return false;
}

function fnRateItemMoveDown(){
	moveOptionDown('productTypeRateItems');
	return false;
}
function fnUpdateRateItemOption(){
	var rateItemName=$j("#rateItemName").val();
	fnUpdateOption("productTypeRateItems",rateItemName,rateItemName);
}

function fnDeleteRateItemOption(){
	fnDeleteOption("productTypeRateItems");
}

function onSubmitAttGroupAndRate(){
	$j("#productTypeGroupAttributes_div").empty();
	var options=$j("#productTypeGroupAttributes option");
	if(options.length>0){
		if(options.get(0).id.indexOf("group_")==-1){
			alert("<fmt:message key="productTypeDetail.input.firstItemMustGroup" />");
			return false;
		}else{
			var attGroupItems="";
			$j.each(options,function(index){
				if(this.id.indexOf("group_")!=-1){					
					if(index!=0)
						$j("#productTypeGroupAttributes_div").append('<input type="hidden" name="attGroupItems" value="'+attGroupItems+'" />');
					$j("#productTypeGroupAttributes_div").append('<input type="hidden" name="attGroupNames" value="'+this.value+'" />');
					$j("#productTypeGroupAttributes_div").append('<input type="hidden" name="attGroupIds" value="'+this.id.substring("group_".length)+'" />');
					attGroupItems="";
				}else{
					attGroupItems+=this.value+",";
				}
			});
			$j("#productTypeGroupAttributes_div").append('<input type="hidden" name="attGroupItems" value="'+attGroupItems+'" />');
		}
	}
	var productTypeRateItems=$j("#productTypeRateItems option");
	$j.each(productTypeRateItems,function(index){
		$j("#productTypeGroupAttributes_div").append('<input type="hidden" name="rateItemNames" value="'+this.value+'" />');
		$j("#productTypeGroupAttributes_div").append('<input type="hidden" name="rateItemIds" value="'+this.id+'" />');
	});
	
	var productTypeSkuOptions=$j("#productTypeSkuOptions option");
	$j.each(productTypeSkuOptions,function(index){
		$j("#productTypeGroupAttributes_div").append('<input type="hidden" name="skuOptionIds" value="'+this.value+'" />');
	});
	return true;
}
//-->
</script>
<form id="productType" class="mainForm" action="${ctxPath}/catalog/productType.html" method="post" onsubmit="return (onSubmitAttGroupAndRate()&&validateProductType(this));">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
		<tr>
			<th colspan="2">
				<fmt:message key="productType.productTypeId" />
			</th>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="productType.productTypeName" />
			</td>
			<td>
				<input class="Field400"	type="text" name="productTypeName" id="productTypeName" value="${productType.productTypeName}" />
			</td>
	   	</tr>
	   	<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="productType.minOrderQuantity" />
			</td>
			<td>
				<input class="Field400"	type="text" name="minOrderQuantity" id="minOrderQuantity" value="${productType.minOrderQuantity}" />
			</td>
	   	</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="productType.status" />
			</td>
			<td>
				<app:statusCheckbox name="status"  value="${productType.status}" />
			</td>
	   	</tr>
	   	<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="productType.templatePath" />
			</td>
			<td>
				<app:templateSelect templateList="${appConfig.productTemplates}" name="templatePath" classes="Field400" value="${productType.templatePath}"></app:templateSelect>
			</td>
	   	</tr>
	   	<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="productType.productTypeDescription" />
			</td>
			<td>
				<textarea id="productTypeDescription" name="productTypeDescription" class="Field600">${productType.productTypeDescription}</textarea>
			</td>
	   	</tr>
	</table>
	
	<div id="productTab2">
	    <ul>
	    	<li><a href="#productAttributeInfo"><fmt:message key="productTypeDetail.product.attribute.title" /></a></li>
	        <li><a href="#productSkuOptionInfo"><fmt:message key="productTypeDetail.productSku.option.title" /></a></li>
	        <li><a href="#productRateItemInfo"><fmt:message key="productTypeDetail.product.rateItem.title" /></a></li>
	    </ul>
	    <div class="blank10"></div>
	    <div id="productAttributeInfo" style="display:none">
	    	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
				<tr>
					<%--产品自定义属性 start--%>
					<td width="230" style="padding-left:25px">
						可用的自定义属性：
						<cartmatic:ui_tip id="productTypeGroupAttributesTip"><fmt:message key="productTypeDetail.productTypeGroupAttributes.tip" /></cartmatic:ui_tip>
						<br />
						<select id="otherAttributes" multiple="multiple" style="height: 360px; " class="Field200">
							<c:forEach items="${otherAttributes}" var="attribute">
								<option value="${attribute.attributeId}">
									&nbsp;&nbsp;${attribute.attributeName}
								</option>
							</c:forEach>
						</select>
					</td>
					<td align="center" width="38">
						<div class="icon_help"></div>
						<input id="btnMoveRight" type="image" src="${ctxPath}/images/btn_right.gif" />
						<div class="blank6"></div>
						<input id="btnMoveLeft" type="image" src="${ctxPath}/images/btn_left.gif" />
					</td>
					<td width="230" style="padding-left:25px">
						属性组:
						<input type="text" id="groupName" class="Field200" />
						<div class="blank6"></div>
						<cartmatic:iconBtn icon="plus" textKey="common.link.add" id="btnAddGroup" />
						&nbsp;
						<cartmatic:iconBtn icon="pen" textKey="common.link.edit" id="btnUpdateGroup" extraStyle="display: none"/>
						&nbsp;
						<cartmatic:iconBtn icon="cross" textKey="common.link.delete" id="btnDeleteGroup" extraCss="negative" extraStyle="display: none"/>
						<cartmatic:ui_tip id="addGroup">首先添加属性组，然后再添加可选属性到属性组内。</cartmatic:ui_tip>
						<div class="blank6"></div>
						<select id="productTypeGroupAttributes" multiple="multiple" style="height: 300px;" class="Field200">
							<c:forEach items="${productType.productAttGroups}" var="productAttGroup">
								<option id="group_${productAttGroup.productAttGroupId}" value="${productAttGroup.productAttGroupName}">
									[${productAttGroup.productAttGroupName}]
								</option>
								<c:forEach items="${productAttGroup.productAttGroupItems}" var="productAttGroupItem">
									<option value="${productAttGroupItem.attribute.attributeId}">
										&nbsp;&nbsp;${productAttGroupItem.attribute.attributeName}
									</option>
								</c:forEach>
							</c:forEach>
						</select>
					</td>
					<td>
						<div class="icon_help"></div>
						<input id="btnGroupItemMoveUp" type="image" src="${ctxPath}/images/btn_up.gif" />
						<p />
						<input id="btnGroupItemMoveDown" type="image" src="${ctxPath}/images/btn_down.gif" />
					</td>
				</tr>
			</table>
	    </div>
	    <div id="productSkuOptionInfo" style="display:none">
	    	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
				<tr>
					<%--skuoption   SKU选项属性 start--%>
					<td width="230" style="padding-left:25px">
						可用的SKU选项属性：
						<br />
						<select id="otherSkuOptions" multiple="multiple" style="height: 360px;" class="Field200">
							<c:forEach items="${otherSkuOptions}" var="skuOption">
								<option value="${skuOption.skuOptionId}">
									${skuOption.skuOptionName}
								</option>
							</c:forEach>
						</select>
					</td>
					<td align="center" width="38">
						<div class="icon_help"></div>
						<input id="btnSkuOptionMoveRight" type="image" src="${ctxPath}/images/btn_right.gif" />
						<div class="blank6"></div>
						<input id="btnSkuOptionMoveLeft" type="image" src="${ctxPath}/images/btn_left.gif" />
					</td>
					<td style="padding-left:25px">
						当前的SKU选项属性:
						<br>
						<select id="productTypeSkuOptions" multiple="multiple" style="height: 360px; width:" class="Field200">
							<c:forEach items="${productTypeSkuOptions}" var="skuOption">
								<option value="${skuOption.skuOptionId}">
									${skuOption.skuOptionName}
								</option>
							</c:forEach>
						</select>
					</td>
					<td align="center" width="38">
						<div class="icon_help">
							<cartmatic:ui_tip id="productTypeSkuOptionsTip"><fmt:message key="productTypeDetail.productTypeSkuOptions.tip" /></cartmatic:ui_tip>
						</div>
						<input id="btnSkuOptionMoveUp" type="image" src="${ctxPath}/images/btn_up.gif" />
						<div class="blank6"></div>
						<input id="btnSkuOptionMoveDown" type="image" src="${ctxPath}/images/btn_down.gif" />
					</td>
					<%--SKU选项属性 end--%>
				</tr>
			</table>
	    </div>
	    <div id="productRateItemInfo" style="display:none">
	    	<%--关联属性（可选）--%>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
				<tr>
					<%--产品评论项 start--%>
					<td width="250" style="padding-left:25px">
						<span><fmt:message key="productTypeDetail.product.rateItem.title" />：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
						<div class="blank6"></div>
						<input type="text" id="rateItemName" class="Field200"/>
                        <div class="blank6"></div>
						<cartmatic:iconBtn icon="plus" textKey="common.link.add" id="btnAddRateItem" />
						&nbsp;
						<cartmatic:iconBtn icon="pen" textKey="common.link.edit" id="btnUpdateRateItem" extraStyle="display: none"/>
						&nbsp;
						<cartmatic:iconBtn icon="cross" textKey="common.link.delete" id="btnDeleteRateItem" extraCss="negative" extraStyle="display: none"/>
						<cartmatic:ui_tip id="productTypeRateItemsTip"><fmt:message key="productTypeDetail.productTypeRateItems.tip" /></cartmatic:ui_tip>
						<div class="blank6"></div>
						<select id="productTypeRateItems" multiple="multiple" style="height: 315px;" class="Field200">
							<c:forEach items="${productType.productRateItems}" var="productRateItem">
								<option id="${productRateItem.productRateItemId}" value="${productRateItem.rateName}">
									${productRateItem.rateName}
								</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<div style="margin:25px 0px 120px 0px">
							
						</div>
						<input id="btnRateItemMoveUp" type="image" src="${ctxPath}/images/btn_up.gif" />
						<p />
						<input id="btnRateItemMoveDown" type="image" src="${ctxPath}/images/btn_up.gif" />
					</td>
					<%--产品评论项 end--%>
				</tr>
			</table>
	    </div>
	</div>
	<app:ui_tabs tabsId="productTab2" type="2" selected="0"/>
	<input type="hidden" name="productTypeId" value="${productType.productTypeId}" />
	<div id="productTypeGroupAttributes_div"></div>
</form>
<v:javascript formName="productType" staticJavascript="false" />
<script type="text/javascript">
    document.forms["productType"].elements["productTypeName"].focus();
</script>