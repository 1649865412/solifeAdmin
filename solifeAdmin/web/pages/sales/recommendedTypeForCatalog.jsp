<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>
<script type="text/javascript" defer>
/** 初始化 */
function fnInitRecommmended(){
	<c:forEach items="${recommendedTypeList}" var="recommendedType">
	fillDivWithPage("re_div_${recommendedType.recommendedTypeId}","${ctxPath}/catalog/recommendedProduct/dialog.html?recommendedTypeId=${recommendedType.recommendedTypeId}&sourceId=${param.sourceId}&decorator=selecter");
	</c:forEach>
}

function validateRecommendedProductAction($rp_id){
	
	applyValidate($("sortOrder_"+$rp_id),"integer,minValue=0,maxValue=1000");
	applyValidate($("startTime_"+$rp_id),"required,date");
	applyValidate($("expireTime_"+$rp_id),"date");
	if (validateForm(document.recommendedProductForm) && checkExpireTime($rp_id) ) {
  		removeValidate($("sortOrder_"+$rp_id),true);
  		removeValidate($("startTime_"+$rp_id),true);
  		removeValidate($("expireTime_"+$rp_id),true);
  		return true;
  	} 
  	else {
  		alert(__vaMsg.notPass); 
  		removeValidate($("sortOrder_"+$rp_id),true);
  		removeValidate($("startTime_"+$rp_id),true);
  		removeValidate($("expireTime_"+$rp_id),true);
  		return false; 
  	}
	
	
}

function checkExpireTime($rp_id){
   	if (fnCompareDate($("startTime_"+$rp_id).value,$("expireTime_"+$rp_id).value) >=0){
   		$j('#expireTime_'+$rp_id).focus();
   		alert('<fmt:message key="recommendedProduct.expireTimeGtStartTime"/>');
   		return false;
   	}else{
   		return true;
   	}
}


function fnAddRecommendedProduct(product,$typeId)
{
	if(product != null && product.produectId != ""){
		//检查是不是自己推荐自己
		if("${sourceType}" != 'CATEGORY'){
			if(product.productId == "${param.sourceId}"){
				alert('<fmt:message key="recommendedProduct.cannotRecommendItself"/>');
				return null;
			}
		}
	    $($typeId+'_productId').value = product.productId;
	    var url = "${ctxPath}/catalog/recommendedProduct/dialog.html";
	    var param = "doAction=add&bind_pix="+ $typeId +"&" + $j("#frm_"+$typeId+" > input").serialize();
	     //clear the message
		$j('#message').empty();
		$j('#message').append('<fmt:message key="recommendedProduct.commitWait"/>');
 	
   		$j.post(url,param,fnAddRecommendedProductCallback,"json");
    }
   
}
function fnAddRecommendedProductCallback(result){
	if(result.status!=-500){
		sysMsg4p(result.msg,result.status==2);
		refreshPage(result.data.recommendedTypeId);
	}else{
		sysMsg4p(result.msg,true);
	}
}

function fnSaveRecommendedProduct($rp_id, $sourceId, $typeId)
{
	
    if(!validateRecommendedProductAction($rp_id)){
    	return;
    }
    if (!confirm("<fmt:message key="recommendedProduct.confirmSave"/>?"))
    {
        return;
    }
    var url = "${ctxPath}/catalog/recommendedProduct/dialog.html";
    var param = "doAction=save&recommendedProductId="+$rp_id;
    if ($('sortOrder_'+$rp_id))  param += "&sortOrder="+$('sortOrder_'+$rp_id).value;
    if ($('startTime_'+$rp_id))  param += "&startTime="+$('startTime_'+$rp_id).value;
    if ($('expireTime_'+$rp_id)) param += "&expireTime="+$('expireTime_'+$rp_id).value;
    if ($('status_'+$rp_id))     param += "&status="+$('status_'+$rp_id).value;
    
    //clear the message
	$j('#message').empty();
	$j('#message').append('<fmt:message key="recommendedProduct.commitWait"/>');
 	
   	$j.post(url,param,fnSaveRecommendedProductCallback,"json");
    	
}
function fnSaveRecommendedProductCallback(result){
	//update message
   	if(result.status==1){
   	   	sysMsg4p(result.msg);
   		refreshPage(result.data.recommendedTypeId);
   	}else{
   		sysMsg4p(result.msg,true);
   	}
}

function fnDeleteRecommendedProduct($rp_id, $typeId)
{
    if (!confirm("<fmt:message key="recommendedProduct.confirmDelete"/>?"))
    {
        return;
    }
    var url = "${ctxPath}/catalog/recommendedProduct/dialog.html";
    var param = "doAction=delete"+
                "&recommendedProductId="+$rp_id+
                "&recommendedTypeId="+$typeId;
    //clear the message
	$j('#message').empty();
	$j('#message').append('<fmt:message key="recommendedProduct.commitWait"/>');
 	
   	$j.post(url,param,fnDeleteRecommendedProductCallback,"json");
}
function fnDeleteRecommendedProductCallback(result){
	if(result.status==1){
   	   	sysMsg4p(result.msg);
   	 	refreshPage(result.data.recommendedTypeId);
   	}else{
   		sysMsg4p(result.msg,true);
   	}
}



function refreshPage($typeId){
	var reloadURL = "${ctxPath}/catalog/recommendedProduct/dialog.html?recommendedTypeId="+ $typeId +"&sourceId=${param.sourceId}&decorator=selecter";
    var reloadDiv = "re_div_" + $typeId;
	fillDivWithPage(reloadDiv, reloadURL,null,refreshPageCallback,"POST");
}

function refreshPageCallback(){
	$j('#message').append("<b>~~~~"+'<fmt:message key="recommendedProduct.refreshPage.success"/>'+"~~~~</b>");
}
</script>
<c:forEach items="${recommendedTypeList}" var="recommendedType">
    <div class="commend">
        ${recommendedType.recommendTitle}
        <c:if test="${recommendedType.autoEval==1 and recommendedType.ruleCode!=0}">
            [<fmt:message key="recommendedType.autoEval" />]
        </c:if>
        <a href="javascript:productSelector_show('${recommendedType.recommendedTypeId}');"> 
            <img src="${ctxPath}/images/select.gif" title="<fmt:message key="recommendedProduct.openAdd"/>"  align="absmiddle" />
        </a>
        <span id="frm_${recommendedType.recommendedTypeId}"> 
            <input type="hidden" name="${recommendedType.recommendedTypeId}_productId" 
                   id="${recommendedType.recommendedTypeId}_productId" value="" /> 
            <input type="hidden" name="${recommendedType.recommendedTypeId}_sourceId"
                   id="${recommendedType.recommendedTypeId}_sourceId" value="${param.sourceId}" /> 
            <input type="hidden" name="${recommendedType.recommendedTypeId}_recommendedTypeId" 
                   id="${recommendedType.recommendedTypeId}_recommendedTypeId" value="${recommendedType.recommendedTypeId}" /> 
        </span>        
        <div class="blank8"></div>
        <div id="re_div_${recommendedType.recommendedTypeId}" class="recommend-list"></div>
    </div>
    <div class="clear"></div>
</c:forEach>
<product:productSelector id="productSelector" ondblclick="fnAddRecommendedProduct" catalogId="${param.catalogId}"></product:productSelector>
<div id="message" style="visibility:hidden"></div>