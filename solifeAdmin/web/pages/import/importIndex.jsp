<%@ include file="/common/taglibs.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="catalog" tagdir="/WEB-INF/tags/catalog"%>
<title><fmt:message key="import.importInfo" /></title>
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="cancel" onclick="location.href='${ctxPath}/system/index.html'" />
	<cartmatic:cartmaticBtn btnType="import" onclick="location.href='${ctxPath}/system/import.html?doAction=importType'" />
</content>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
		<tr>
			<th colspan="2">
				<fmt:message key="import.importInfo" />
			</th>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="import.importInfo.startImportTime" />
			</td>
			<td>
				<c:set var="dateTimePattern"><fmt:message key="date.detail.format"/></c:set>
				<span id="startImportTime"><fmt:formatDate value="${importInfo.startImportTime}" pattern="${dateTimePattern}"/></span>
			</td>
           </tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="import.importInfo.endImportTime" />
			</td>
			<td>
				<span id="endImportTime">
					<c:choose>
						<c:when test="${empty importInfo.endImportTime&& not empty importInfo.startImportTime}">
							<fmt:message key="import.importing" />
						</c:when>
						<c:when test="${not empty importInfo.endImportTime&& not empty importInfo.startImportTime}">
							<fmt:formatDate value="${importInfo.endImportTime}" pattern="yyyy-MM-dd hh:mm"/>											
						</c:when>
						<c:otherwise>
							
						</c:otherwise>
					</c:choose>
				</span>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="import.importInfo.successInsertCount" />
			</td>
			<td>
				<span id="successInsertCount">${importInfo.successInsertCount}</span>
			</td>
            </tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="import.importInfo.successUpdateCount" />
			</td>
			<td>
				<span id="successUpdateCount">${importInfo.successUpdateCount}</span>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="import.importInfo.failInsertCount" />
			</td>
			<td>
				<font color="red">
				<span id="failInsertCount">${importInfo.failInsertCount}
				</span>
				</font>
			</td>
            </tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="import.importInfo.failUpdateCount" />
			</td>
			<td>
				<font color="red">
				<span id="failUpdateCount" style="overflow: scroll">${importInfo.failUpdateCount}</span>
				</font>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="import.importInfo.ignoreCount" />
			</td>
			<td>
				<span id="ignoreCount">${importInfo.ignoreCount}</span>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="import.importInfo.failData" />
			</td>
			<td>
				<div id="failRowNum" style="color: red;overflow: scroll">	<c:forEach items="${importInfo.failData}" var="failData" varStatus="varStatus">
						<c:if test="${varStatus.first}">,</c:if>${failData}
					</c:forEach>
					<c:if test="${empty importInfo.failData}"><fmt:message key="import.importInfo.failData.none" /></c:if>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="2">
			<cartmatic:iconBtn id="stopImportBnt" extraStyle="${(empty importInfo.endImportTime&&empty importInfo.startImportTime)||(not empty importInfo.endImportTime)?'display:none':''}" icon="cross" text="Stop" onclick="fnStopImport();" />
			</td>
		</tr>
	</table>
<script type='text/javascript'>
	function fnGetImportInfo(){
		$j.post(__ctxPath+"/system/import.html?doAction=getImportInfo",fnGetImportInfoHandler,"json");
	}
	var refCount=0;
	function fnGetImportInfoHandler(result){
		var importInfo=result.data;
		if((!(importInfo.endImportTimeStr&&importInfo.startImportTimeStr))||importInfo.startImportTimeStr){
			$("stopImportBnt").style.display="none";
			$("endImportTime").innerHTML=importInfo.endImportTimeStr;
		}else{
			$("stopImportBnt").style.display="";
			$("endImportTime").innerHTML="<fmt:message key="import.importing" />";
		}
		$j("#startImportTime").text(importInfo.startImportTimeStr);
		$j("#successInsertCount").text(importInfo.successInsertCount);
		$j("#successUpdateCount").text(importInfo.successUpdateCount);
		$j("#ignoreCount").text(importInfo.ignoreCount);
		$j("#failInsertCount").text(importInfo.failInsertCount);
		$j("#failUpdateCount").text(importInfo.failUpdateCount);
		var failData=importInfo.failData;
		var failRowNum="";
		if(failData.length>0){
			for(var i=0;i<failData.length;i++){
				failRowNum=failRowNum+failData[i]+" ";
			}
		}else{
			failRowNum="<fmt:message key="import.importInfo.failData.none" />";
		}
		$j("#failRowNum").text(failRowNum);
		refCount++;
		if(refCount>2&&!importInfo.running){//refCount>2是保证当ajax启动请求时，后台的线程已经进入startImport方法体
		   clearInterval(importInfoShow);
		}
		if(importInfo.endImportTimeStr.length>0){
		  $j("#stopImportBnt").hide()
		}
		else{
		  $j("#stopImportBnt").show();
		}
	}
	function fnStopImport(){
		$j.post(__ctxPath+"/system/import.html?doAction=stopImport","json");
	}
	var importInfoShow=setInterval("fnGetImportInfo();",2000);
	function fnStopGetInfo(data){
		clearInterval(importInfoShow);
	}
	$j(function(){
		$j("#stopImportBnt").ajaxError(fnStopGetInfo);
	});
	
</script>