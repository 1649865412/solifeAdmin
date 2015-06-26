<%@ include file="/common/taglibs.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="catalog" tagdir="/WEB-INF/tags/catalog"%>
<title><fmt:message key="product.import.title3" />
</title>
<c:set var="buttons">
	<cartmatic:cartmaticBtn btnType="cancel" onclick="location.href='${ctxPath}/catalog/categorys.html'" />
</c:set>
<content tag="form-start">
<form name="form"">
	</content>
	<content tag="buttons">
		<c:out value="${buttons}" escapeXml="false" />
	</content>
	<div class="box-content-wrap">
		<div class="box-content">
			<div class="top">
				<div class="content-title">
					<fmt:message key="product.import.title3" />
				</div>
			</div>
			<div class="content">
				<table border="0" cellpadding="0" cellspacing="0"
					class="table-content">
					<tr>
						<TD>
							<SPAN class="fB"><fmt:message key="product.import3.desc1" />
							</SPAN>
						<td>
					</tr>
					<tr>
						<td>
							<SPAN class="fB"><b><fmt:message
										key="product.import3.desc2" />:</b>(${successRows})</SPAN>
						</td>
					</tr>
					<tr>
						<td>
							<font color="red"><b><fmt:message
										key="product.import3.desc3" />:</b>(${failRows})</font>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</form>aaaaaaaaaaaa${importInfo}
<input type="button" name="" value="show" onclick="fnGetImportInfo()"/>
<div style="width:500px;word-break:break-all;">
startImportTime:<span id="startImportTime">${importInfo.startImportTime}</span><br/>
endImportTime:<span id="endImportTime">${importInfo.startImportTime}</span><br/>
successInsertCount:<span id="successInsertCount">${importInfo.startImportTime}</span><br/>
successUpdateCount:<span id="successUpdateCount">${importInfo.startImportTime}</span><br/>
failInsertCount:<span id="failInsertCount">${importInfo.startImportTime}</span><br/>
failUpdateCount:<span id="failUpdateCount">${importInfo.startImportTime}</span><br/>
failRowNum:<span id="failRowNum">${importInfo.startImportTime}</span><br/>
</div>
<script type='text/javascript'>
	function fnGetImportInfo(){
		$j.post(__ctxPath+"/system/import.html?doAction=getImportInfo",getImportInfoHandler,"json");
	}
	function getImportInfoHandler(result){
		var importInfo=result.data;
		$("startImportTime").innerHTML=importInfo.startImportTime;
		$("endImportTime").innerHTML=importInfo.endImportTime;
		$("successInsertCount").innerHTML=importInfo.successInsertCount;
		$("successUpdateCount").innerHTML=importInfo.successUpdateCount;
		$("failInsertCount").innerHTML=importInfo.failInsertCount;
		$("failUpdateCount").innerHTML=importInfo.failUpdateCount;
		var failData=importInfo.failData;
		var failRowNum="";
		if(failData.length>0){
			for(var i=0;i<failData.length;i++){
				failRowNum=failRowNum+failData[i]+",";
			}
		}else{
			failRowNum="none";
		}
		$("failRowNum").innerHTML=failRowNum;
	}
	function fnStopImport(){
		$j.post(__ctxPath+"/system/import.html?doAction=stopImport","json");
	}
	setInterval("fnGetImportInfo();",2000);
</script>
<input type="button" name="" value="stop" onclick="fnStopImport()"/>