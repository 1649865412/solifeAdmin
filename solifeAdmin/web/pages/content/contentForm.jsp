<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="content" tagdir="/WEB-INF/tags/content"%>
<title><fmt:message key="contentDetail.title" /></title>
<app:pageHeading entityName="${content.contentTitle}" entityHeadingKey="contentDetail.heading" />
<app:showBindErrors bindPath="content.*" />
<div id="btn-space" style="display: none;">
	<cartmatic:cartmaticBtn btnType="save" onclick="ps('tabIframeId_${param.tid}','fnDoSave();');"  />
	<c:if test="${content.contentId!=null}">
		<cartmatic:cartmaticBtn btnType="delete" onclick="ps('tabIframeId_${param.tid}','fnDoDelete(this);');" />
	</c:if>
</div>
<div class="blank10"></div>
<form:form method="post" cssClass="mainForm" id="contentForm" name="contentForm"
	commandName="content" action="${ctxPath}/content/content.html">
	<input type="hidden" name="contentId" id="contentId" value="${content.contentId}" />
	<input type="hidden" name="version" id="version" value="${content.version}" />
	<input type="hidden" id="tid" value="${param.tid}" />
	<table class="table-content" width="100%">						
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="${entityClassName}.contentTitle" />
			</td>
			<td>				 
				<span> <form:input path="contentTitle" class="Field400" /> </span>
				<cartmatic:ui_tip id="contentTitleDesc"><fmt:message key="content.contentTitle.tip" /></cartmatic:ui_tip>
			</td>			
		</tr>
		<tr>
			<td class="FieldLabel"><StoreAdmin:label key="content.status" /></td>
			<td>
				<b>
				<span id="state">
				<c:if test="${not empty content.state}">
				<fmt:message key="content.state.s${content.state}"/></c:if></span></b>
				&nbsp;&nbsp;
				<label for="checkbox_status">
					<fmt:message key="status.active" />
				</label>
				<input type="checkbox" name="checkbox_status" id="checkbox_status"
					onclick="ChangeValueOfStatus(this,'status')"
					<c:if test="${content.status == 1}">checked</c:if>
					<c:if test="${content.contentId == null}">checked</c:if> />
				<cartmatic:ui_tip id="statusTip"><fmt:message key="content.status.tip" /></cartmatic:ui_tip>
				<c:choose>
					<c:when test="${content.contentId == null}">
						<input type="hidden" name="status" id="status" value="1" />
					</c:when>
					<c:otherwise>
						<form:hidden path="status"/>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>			
			<td class="FieldLabel">
				<StoreAdmin:label key="content.category" />
			</td>
			<td>
				<SPAN id="categoryName">${content.category.categoryPathName}</SPAN>
				<form:hidden path="categoryId"/>
				<cartmatic:iconBtn icon="magnifier" textKey="button.selectCategory" 
					onclick="try{CategorySelector_show();}catch(e){alert(e.message);}return false;"/>
				&nbsp;&nbsp;				
				<StoreAdmin:label key="${entityClassName}.sortOrder" />
				<span> <c:choose>
					<c:when test="${content.contentId == null}">
						<input  name="sortOrder" id="sortOrder" value="10" size="10"/>
					</c:when>
					<c:otherwise>
						<form:input path="sortOrder" size="10"/>
					</c:otherwise>
				</c:choose></span>
				<cartmatic:ui_tip id="sortOrderTip"><fmt:message key="content.sortOrder.tip" /></cartmatic:ui_tip>		
			</td>			
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="${entityClassName}.publishTime" />
			</td>
			<td>
				<span> <form:input id="publishTime" path="publishTime" cssClass="Field150" /> </span> 
				<cartmatic:ui_tip id="publishTimeTip"><fmt:message key="content.publishTime.tip" /></cartmatic:ui_tip>		
				&nbsp;&nbsp;
				<StoreAdmin:label key="${entityClassName}.expiredTime" />
				
				<span> <form:input id="expiredTime" path="expiredTime" cssClass="Field150" /> </span>
				<cartmatic:ui_tip id="expiredTimeTip"><fmt:message key="content.expiredTime.tip" /></cartmatic:ui_tip>		
				<app:ui_datePicker outPut="publishTime" />
				<app:ui_datePicker outPut="expiredTime" />

			</td>			
		</tr>
		<tr>			
			<td class="FieldLabel">
				<StoreAdmin:label key="${entityClassName}.metaKeyword" />
			</td>
			<td>
				 <span><input class="Field400" type="text" id="metaKeyword" name="metaKeyword"  value="${content.metaKeyword}"/></span>
				 <cartmatic:ui_tip id="metaKeywordTip"><fmt:message key="content.metaKeyword.tip" /></cartmatic:ui_tip>
			</td>
		</tr>		
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="${entityClassName}.metaDescription" />
			</td>	
			<td>
				<span><input class="Field400" type="text" id="metaDescription" name="metaDescription" value="${content.metaDescription}"/></span>
				<cartmatic:ui_tip id="metaDescriptionTip"><fmt:message key="content.metaDescription.tip" /></cartmatic:ui_tip>
			</td>
		</tr>
		<tr>			
			<td class="FieldLabel">
				<StoreAdmin:label key="${entityClassName}.contentCode" />
			</td>
			<td>
				<span><input class="Field400" type="text" id="contentCode" name="contentCode" value="${content.contentCode}"/></span>
				<cartmatic:ui_tip id="contentCodeTip"><fmt:message key="content.contentCode.tip" /></cartmatic:ui_tip>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<fmt:message key="content.contentBody"/>
			</td>
			<td>
				<textarea id="contentBody" name="contentBody" rows="8" cols="80" style="width: 99.5%">${content.contentBody}</textarea>
				<app:ui_htmlEditor textareaIds="contentBody" />
			</td>
	    </tr>
	    <tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="common.message.createTime" />
			</td>
			<td>
				<SPAN id="createTimeShow"><fmt:formatDate value="${content.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></SPAN>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="common.message.updateTime" />
			</td>
			<td>
				<SPAN id="updateTimeShow"><fmt:formatDate value="${content.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></SPAN>
			</td>
		</tr>
	</table>
	<c:if test="${not empty attributeList}">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
		<tr>
			<th colspan="2">
				<fmt:message key="contentDetail.attrubutes" />
			</th>
		</tr>
		<c:forEach items="${attributeList}" var="attribute" varStatus="varStatus" >
		<tr>
			<td class="FieldLabel">
				${attribute.attributeName}:
			</td>
			<td>
				<attribute:input isdisplayHelp="true" attribute="${attribute}" entity="${content}"></attribute:input>
			</td>
        </tr>
        </c:forEach>
	</table>
	</c:if>
</form:form>
<div id="viewUrlContent" <c:if test="${empty content.id}">style="display: none"</c:if>>
<div class="blank10"></div>
<fmt:message key="content.content.viewUrl"/>：<input type="text" id="viewUrl" value="${viewUrl}" readonly="readonly" class="Field400" />
<div class="blank10"></div>
<fmt:message key="content.content.localRedirectUrl"/>：<input type="text" id="localRedirectUrl" value="${localRedirectUrl}" readonly="readonly" class="Field400" />
</div>
<product:categorySelector id="CategorySelector" ondblclick="fnSelectCategoryHandler" canSelectRoot="false" storeId="${content.category.store.storeId}" categoryType="2"></product:categorySelector>
<v:javascript formName="content" staticJavascript="false" />
<script type="text/javascript">
$j(document).ready(function(){
	btnToP();
});
function fnSelectCategoryHandler(category){
	$("categoryName").innerHTML=category.categoryName;
	$("categoryId").value=category.categoryId;
}
//persist the value of status
function ChangeValueOfStatus(checkbox,persistInput)
{
    if (checkbox.checked==true)
    {
      	$(persistInput).value = 1;
    }
    else
    {
      	$(persistInput).value = 2;
    }
}
function validateAction(url,callbackFunction){
	//validate the form	  	
	$j('#contentBody').val(tinyMCE.get('contentBody').getContent());
    if(!validateContent($j('#content').get(0))){
    	return false;
   	}
   	if(!checkExpiredTime()){
   		return false;
   	}
   
    return true;
    
}

function fnDoSave(callbackFunction){
    if(validateAction()){
    	var entityName=$j("#contentTitle").val();
    	if(confirm(__FMT.common_message_confirmSaveThis+" "+entityName+"?")){
    		var paraDate=$j("#contentForm :input,textarea").serializeArray();
       		$j.post("${ctxPath}/content/content/dialog.html?doAction=save",paraDate,fnDoSaveCallback,"json");
        }
    }
}

function fnDoDelete(callbackFunction){
	var entityName=$j("#contentTitle").val();
	if(confirm(__FMT.common_message_confirmDeleteThis+" "+entityName+"?")){
		var paraDate={contentId:$j("#contentId").val()};
		$j.post(__ctxPath+"/content/content/dialog.html?doAction=delete",paraDate,fnDoDeleteCallback,"json");
	}
}

function fnDoDeleteCallback(result){
	if(result.status==1){
		sysMsg4p(result.msg);
	   	fnRefreshOpener();
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

function fnDoSaveCallback(result){
    var data=result.data;
    fnSaveCallbackShowError(result);
   	if(result.status==1){
   	   	$j('#contentId').val(data.contentId);
   	 	$j('#contentCode').val(data.contentCode);
   	 	updateTabLable("#"+data.contentCode,data.contentId);
   	   	setState(data.state);
   	   	$j('#createTimeShow').empty().append(data.createTime);
   	   	$j('#updateTimeShow').empty().append(data.updateTime);
   	   	$j('#viewUrl').val(data.viewUrl);
   	   	$j('#localRedirectUrl').val(data.localRedirectUrl);
   	   	$j("#viewUrlContent").show();
   	   	setButtons(data.contentId);
   	   	btnToP();
   	   	fnRefreshOpener();
   	 	sysMsg4p(result.msg);
   	 	
   	}
}

function updateTabLable(lable,nTId){
	if(parent&&lable&&lable.replace("#","")){
		parent.fnUpdateTab($j("#tid").val(),lable,nTId);
		if(nTId){
			$j("#tid").val(nTId);
		}
	}
}
function fnRefreshOpener(){
	if(opener&&!opener.closed){
		if (opener.fnRefreshContentList){
			var activeItem = opener.fnFindActiveItem();
			opener.fnRefreshContentList();
		}
	}else if(parent){
		if (parent.fnRefreshContentList){
			var activeItem = parent.fnFindActiveItem();
			parent.fnRefreshContentList();
		}
	}
}

function setState(state){
	if(state == "0"){
   		$j('#state').html("<fmt:message key="content.state.s0"/>");
   	}else if(state == "1"){
   		$j('#state').html("<fmt:message key="content.state.s1"/>");
   	}else if(state == "2"){
   		$j('#state').html("<fmt:message key="content.state.s2"/>");
   	}else if(state == "3"){
   		$j('#state').html("<fmt:message key="content.state.s3"/>");
   	}else{
   	
   	}

}

//return true if the 'expiredTime' is later than 'publishTime'    
function checkExpiredTime(){
   	if (fnCompareDate($("publishTime").value,$("expiredTime").value) >= 0 ){
   		$j('#expiredTime').focus();
   		alert("<fmt:message key="contentDetail.expiredTime.error"/>");
   		return false;
   	}else{
   		return true;
   	}
}
 
function setButtons(id){
	var delBtn=$j("#btn-space").find("input[name=delete]");
	if(delBtn&&delBtn.length==0){
		$j("#btn-space").append('<input type="button" onclick="ps(\'tabIframeId_'+id+'\',\'fnDoDelete(this);\');" id="delete" title="<fmt:message key="button.delete"/>" value="<fmt:message key="button.delete"/>" name="delete" class="btn-action">');
	}
	var saveBtn=$j("#btn-space").find("input[name=save]");
	saveBtn.attr("onclick",'ps(\'tabIframeId_'+id+'\',\'fnDoSave();\');');
}
</script>