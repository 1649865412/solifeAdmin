<sec:authorize ifAnyGranted="admin">
<c:set var="maxRow" value="1000" />
<%-- 为了兼容新旧框架，所以要对totalItems进行特殊处理 --%>
<c:set var="_PrmTotalItems">${PrmTotalItems != null ? PrmTotalItems : sc.totalCount}</c:set>
<input type="hidden" name="d-export-e" id="d-export-e" value="1" disabled="disabled" />
<input type="hidden" name="6578706f7274" id="6578706f7274" value="1" disabled="disabled" />
<script type="text/javascript">
function fnExport($frm,$type)
{
	var param2 = document.getElementById("d-export-e");
	var param3 = document.getElementById("6578706f7274");
	param2.disabled=false;
	param3.disabled=false;
	param2.value=$type;
	param3.value=$type;
	$frm.submit();
	param2.disabled=true;
	param3.disabled=true;
}
function fnExportPartition($first,$max,$partIndex)
{
	var firstR = document.getElementById("PrmPageFirstResult");
	var partIndex = document.getElementById("PrmPagePart");
	var maxR = document.getElementById("PrmPageMaxResults");
	firstR.value = $first;
	maxR.value = $max;
	partIndex.value = $partIndex;
}
function fnShowFiles($checkbox)
{
<c:if test="${_PrmTotalItems+0 > maxRow}">
	var files = document.getElementById("export-files");
	if ($checkbox.checked)
	{
        files.style.display="";
	}
	else
	{
	    files.style.display="none";
	}
</c:if>
}
</script>
<c:if test="${_PrmTotalItems+0 > maxRow}">
	<DIV class=exportlinks id="export-files" style="display: none">
		Files:&nbsp;
		<c:set var="count" value="1" />
		<c:set var="previous" value="0" />
		<c:forEach var="current" begin="1" end="${_PrmTotalItems}" step="${maxRow}">
			<c:if test="${previous > 0}">
				<c:if test="${count == 1}">
					<input type="hidden" name="PrmPageFirstResult" id="PrmPageFirstResult" value="${previous-1}" />
					<input type="hidden" name="PrmPageMaxResults" id="PrmPageMaxResults" value="${maxRow}" />
					<input type="hidden" name="PrmPagePart" id="PrmPagePart" value="${count}" />
				</c:if>
				<input name="radiobutton" type="radio" value="radiobutton" onclick="fnExportPartition('${previous-1}','${maxRow}','${count}')" <c:if test="${count == 1}">checked="checked"</c:if> />
				<fmt:message key="list.paging.file" />${count}
				<c:set var="count" value="${count+1}" />
			</c:if>
			<c:set var="previous" value="${current}" />
		</c:forEach>
		<c:if test="${previous < _PrmTotalItems}">
			<input name="radiobutton" type="radio" value="radiobutton" onclick="fnExportPartition('${previous-1}','${maxRow}','${count}')" />
			<fmt:message key="list.paging.file" />${count}
		</c:if>
	</DIV>
</c:if>
<div class="btn-export">
	<div class="blank6"></div>
	<a href="javascript:fnExport($('export-all').form,2);">Excel</a>
	&nbsp;&nbsp;
	<a href="javascript:fnExport($('export-all').form,1);">CSV</a>
	&nbsp;&nbsp;
	<a href="javascript:fnExport($('export-all').form,3);">XML</a>
	&nbsp;&nbsp;
	<a href="javascript:fnExport($('export-all').form,5);">PDF</a>
	&nbsp;&nbsp;
	<input type="checkbox" name="export-all" id="export-all" value="true" onclick="fnShowFiles(this)" />
	&nbsp;
	<fmt:message key="list.paging.exportAll" />
</div>
</sec:authorize>