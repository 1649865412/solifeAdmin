<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<app:pageHeading entityName="${skuOption.skuOptionName}" entityHeadingKey="skuOptionDetail.heading" />
<content tag="buttons">
<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this,'skuOptionName');" />
<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="skuOption.*" />
<form class="mainForm" method="post" action="${ctxPath}/catalog/skuOption.html" id="skuOptionForm" onsubmit="return validateSkuOption(this);">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
		<tr>
			<th colspan="2">
				<fmt:message key="skuOption.skuOptionId" />
			</th>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="skuOption.skuOptionName" />
			</td>
			<td>
				<input class="Field400" type="text" name="skuOptionName" id="skuOptionName" value="${skuOption.skuOptionName}" />
			</td>
	    </tr>
	    <tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="skuOption.skuOptionCode" />
			</td>
			<td>
				<input class="Field400" type="text" name="skuOptionCode" id="skuOptionCode" value="${skuOption.skuOptionCode}" />
			</td>
	    </tr>
	    <tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="skuOption.status" />
			</td>
			<td>
				<app:statusCheckbox name="status" style="form-inputbox" value="${skuOption.status}" />
			</td>
	    </tr>
	</table>
	<c:if test="${skuOption.skuOptionId!=null}">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
		<tr>
			<th>
				<fmt:message key="skuOptionDetail.sku.option" />
			</th>
		</tr>
		<tr>
			<td id="skuOptionValueListContent">
				<%@include file="include/skuOptionValueListContent.jsp"%>
			</td>
        </tr>
	</table>
	</c:if>
	<input type="hidden" name="skuOptionId" id="skuOptionId" value="${skuOption.skuOptionId}" />
</form>
<v:javascript formName="skuOption" staticJavascript="false" />
<fmt:message key="skuOptionDetail.edit.sku.option.value.title" var="editSkuOptionValueTitle" />
<fmt:message key="button.save" var="buttonSave" />
<app:ui_dialog id="SkuOptionValueEditor" title="${editSkuOptionValueTitle}" width="580" height="470" url="${ctxPath}/catalog/skuOptionValue/dialog.html?doAction=edit&from=list" buttons="'${buttonSave}':fnSaveSkuOption" callback="fnInitSkuOptionValueForm">
</app:ui_dialog>
<c:set var="swf_upload" value="true" scope="request"/>
<script type="text/javascript">
	function fnSaveSkuOption() {
		var form = $j("#skuOptionValueForm");
		if (validateSkuOptionValue(form.get(0))) {
			var paramData = form.serializeArray();
			var skuOptionId = $j("#skuOptionId").val();
			$j.post("${ctxPath}/catalog/skuOptionValue.html?doAction=save&skuOptionId="+ skuOptionId, paramData, fnSaveSkuOptionHandler,"json");
		}
	}
	function fnSaveSkuOptionHandler(result) {
		if(result.status==1){
			dlgSkuOptionValueEditor_close();
			$j("#skuOptionValueListContent")
					.load(
							"${ctxPath}/catalog/skuOption/dialog.html?doAction=refurbishSkuOptionValueList&skuOptionId=${skuOption.skuOptionId}");
		}
		sysMsg(result.msg,result.status!=1);
	}
	function fnInitSkuOptionValueForm() {
		$j("#skuOptionValueType_temp").remove();
		fnInitColor();
	}
	var __valueType=null;
	function fnSelectValueTypeHandler(valueType) {
		if(__valueType==valueType)return;
		__valueType=valueType;
		if (valueType == 2) {
			$j("#skuOptionValue_color").show();
			$j("#skuOptionValue_image").hide();
		} else if (valueType == 3) {
			$j("#skuOptionValue_color").hide();
			$j("#skuOptionValue_image").show();
		} else {
			$j("#skuOptionValue_color").hide();
			$j("#skuOptionValue_image").hide();
		}
	}

	var ColorHex = new Array('00', '33', '66', '99', 'CC', 'FF');
	var SpColorHex = new Array('FF0000', '00FF00', '0000FF', 'FFFF00',
			'00FFFF', 'FF00FF');
	var current = null;
	function fnInitColor() {
		var colorTable = '';
		for (i = 0; i < 2; i++) {
			for (j = 0; j < 6; j++) {
				colorTable = colorTable + '<tr height=12>'
				if (i == 0) {
					colorTable += getColorTd('' + ColorHex[j] + ColorHex[j]
							+ ColorHex[j]);
				} else {
					colorTable += getColorTd('' + SpColorHex[j]);
				}
				for (k = 0; k < 3; k++) {
					for (l = 0; l < 6; l++) {
						colorTable += getColorTd('' + ColorHex[k + i * 3]
								+ ColorHex[l] + ColorHex[j]);
					}
				}
			}
		}
		var color = $j("#skuOptionValue").val();
		colorTable = '<table border="1" cellspacing="0" cellpadding="0" style="border-collapse: collapse" bordercolor="000000" style="cursor:pointer;" id="selectColor" width="260">'
				+ '<tr bgcolor=#cccccc><td colspan=21>&nbsp;<input type="text" name="DisColor" id="DisColor" size="5" disabled style="border:solid 1px #000000;background-color:#000000"><input type="text" name="HexColor" id="HexColor" size="4" style="border:inset 1px;font-family:Arial;" value="#000000" readonly="true">>><input type="text" id="selectedColor" size="5" disabled style="border:solid 1px #000000;background-color:'
				+ color
				+ '"><input type="text" id="selectedHexColor" size="4" style="border:inset 1px;font-family:Arial;" value="'
				+ color
				+ '" readonly="true"></td></tr>'
				+ colorTable
				+ '</table>';
		document.getElementById("colorpanel").innerHTML = colorTable;
	}
	function getColorTd(color) {
		var td = '<td width=11 style="cursor:pointer;background-color:#'
				+ color
				+ '" id="#'
				+ color
				+ '" onmouseover="this.style.backgroundColor = \'#FFFFFF\';document.getElementById(\'DisColor\').style.backgroundColor=this.id;document.getElementById(\'HexColor\').value=this.id;" onmouseout="this.style.backgroundColor =this.id" onclick="document.getElementById(\'selectedColor\').style.backgroundColor=this.id;document.getElementById(\'selectedHexColor\').value=this.id;selectColor(this.id);">';
		return td;
	}
	function selectColor(color) {
		$j("#skuOptionValue").val(color);
	}
	function fnUploadSkuOptionValueImagehandler(file) {
		$j("#skuOptionValue_img_input").val(file.url);
	}
</script>
