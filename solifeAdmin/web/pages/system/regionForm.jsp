<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<app:pageHeading entityName="${region.regionName}" entityHeadingKey="regionDetail.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this,'parentRegionName');" />
    <c:if test="${region.regionId!=null}">
		<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoDelete(this,'parentRegionName');" />
	</c:if>
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="region.*" />
	<form:form method="post" cssClass="mainForm" id="region" commandName="region" action="${ctxPath}/system/region.html" onsubmit="return validateRegion(this);">
		<form:hidden path="version" />
		<input type="hidden" name="regionId" value="${region.regionId}"/> 
		<input type="hidden" name="regionType" id="regionType" value="${region.regionType}" />
		<table class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
 		<app:input property="regionName" />
		<app:input property="regionCode" />
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="region.parentRegionId" />
			</td>
			<td>
				<spring:bind path="region.parentRegionId">
					<c:choose>
						<c:when test="${not empty region.regionId and not empty region.parentRegionName}">
							<c:set var="parentRegionName" value="${region.parentRegionName}" />
						</c:when>
						<c:when test="${empty region.regionId and not empty region.parentRegionName}">
							<c:set var="parentRegionName" value="${region.parentRegionName}" scope="page" />
						</c:when>
					</c:choose>
					<input class="Field400" type="text" name="parentRegionName" id="parentRegionName" value="${parentRegionName}<c:if test="${status.value == 0}">根目录</c:if>" readonly="readonly" />
					<input type="hidden" name="parentRegionId" id="parentRegionId" value="${status.value}" /> &nbsp;
			  		<cartmatic:iconBtn icon="cog" textKey="region.parent.select" id="btnSelectParentRegion" />	
					<app:region id="parentRegionSeclector" showSelectorBtnId="btnSelectParentRegion" regionId="parentRegionId" regionName="parentRegionName"  ondblclick="setSelectRegion" regionType="system"/>
					&nbsp;&nbsp;
					<input id="isRootInput" type="checkbox" <c:if test="${status.value == 0}">checked="checked"</c:if> 
						onclick="if(this.checked){$j('#parentRegionId').val('0');$j('#parentRegionName').val('根目录')}else{$j('#parentRegionId').val('');$j('#parentRegionName').val('')}"/>
					<label for="isRootInput">上级目录为根目录</label>
					<span class="fieldError"><c:out value="${status.errorMessage}" /> </span>
				</spring:bind>
			</td>
		</tr>
		<c:if test="${not empty region.regionId}">
			<tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="region.regionItem" />
					<cartmatic:iconBtn icon="plus" textKey="common.link.add" id="btnSelectSubRegion" />
					<app:region id="subRegionSeclector" showSelectorBtnId="btnSelectSubRegion" ondblclick="fnAddRegionItem" regionType="system"/>
				</td>
				<td style="padding:0" id="divRegionItems">
					<table id="divRegionItems" style="cellSpacing:0;cellPadding:0;border:0"></table>
				</td>
			</tr>
			
			<tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="region.description" />
				</td>
				<td>
					<spring:bind path="region.description">
		           	<textarea name="${status.expression}" id="${status.expression}" 
	                	class="Field400"  rows="6" cols="80">${status.value}</textarea>
	            	<span class="fieldError">${status.errorMessage}</span>
					</spring:bind>
				</td>
			</tr>
			<app:formText label="common.message.createTime" value="${region.createTime}" />
  			<app:formText label="common.message.updateTime" value="${region.updateTime}" />
		</c:if>
   	</table>
</form:form>
<!--region items-->
<script type="text/javascript">
var regionSelectedValue,regionSelectedId;
function setSelectRegion(treeNode) {
	regionSelectedId=treeNode.id;
	regionSelectedValue=treeNode.name;
	$j("#isRootInput").attr("checked", false); 
}
</script>
<c:if test="${not empty region.regionId}">
<script type="text/javascript">
 function fnDelItem(regionItemId,itemId){
   $j.post("${ctxPath}/system/regionItem/dialog.html?doAction=delete&regionItemId="+regionItemId+"&itemId="+itemId,function(result){
		sysMsg(result.msg,result.status!=1);
		if(result.status==1){
	  		fnDisplayItems(${region.regionId});
		}
	},"json");
 }

function fnDisplayItems(regionId){
   fillDivWithPage($j("#divRegionItems"),"${ctxPath}/system/regionItem/dialog.html?doAction=getRegionItemsByRegionId&regionId="+regionId);
}


function fnAddRegionItem(treeNode){
	  var itemId=treeNode.id;
	  $j.post("${ctxPath}/system/regionItem/dialog.html?doAction=save&regionId=${region.regionId}&itemId="+itemId,function(result){
		  	sysMsg(result.msg,result.status!=1);
		  	if(result.status==1){
		  		fnDisplayItems(${region.regionId});
			}
		},"json");
	}
 $j(function() {
	 fnDisplayItems(${region.regionId});
 });
</script>
</c:if>
<v:javascript formName="region" staticJavascript="false" />
<script type="text/javascript">
    document.forms["region"].elements["regionCode"].focus();
</script>