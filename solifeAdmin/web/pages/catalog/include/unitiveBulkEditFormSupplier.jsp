<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="supplier" tagdir="/WEB-INF/tags/supplier"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div id="supplierInfo"  class="border01" style="display:none;">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
<%--==================主供应商(默认)=================--%>
<tr>
	<td width="20">
		<input type="checkbox" id="mainSupplierCheck" name="mainSupplierCheck" value="1"/>
	</td>
	<td width="120">
		主供应商(默认)
	</td>
	<td>
		<input id="mainSupplier" name="mainSupplier" class="Field400" readonly="readonly"/>
		<input id="mainSupplierId" name="mainSupplierId"  type="hidden"/>
		<input type="image" src="${ctxPath}/images/select.gif" onclick="try{SupplierSelector_show('mainSupplier');}catch(e){alert(e.message);}return false;" title="<fmt:message key="productDetail.msg.addCategory" />" />
	</td>
</tr>
<%--==================额外供应商=================--%>
<tr>
	<td>
		<input type="checkbox" id="otherSupplierCheck" name="otherSupplierCheck" value="1"/>
	</td>
	<td>
		额外供应商
	</td>
	<td>
		<select id="otherSupplier" name="otherSupplier" class="Field400" size="5" multiple="multiple">					
		</select>
		&nbsp;&nbsp;
		<input type="image" src="${ctxPath}/images/select.gif" onclick="try{SupplierSelector_show('otherSupplier');}catch(e){alert(e.message);}return false;" title="<fmt:message key="productDetail.msg.addCategory" />" />
		<input type="image" src="${ctxPath}/images/clear_field.gif" onclick="try{fnDeleteSupplier();}catch(e){alert(e.message);}return false;" title="<fmt:message key="productDetail.msg.removeCategory" />" />
	</td>
</tr>
</table>
<supplier:supplierSelector id="SupplierSelector" ondblclick="fnModifySupplierHandler" autoClose="true"></supplier:supplierSelector>
</div>