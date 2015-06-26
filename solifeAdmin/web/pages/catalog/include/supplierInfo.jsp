<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib prefix="supplier" tagdir="/WEB-INF/tags/supplier"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="supplierInfo" style="display:none;min-height: 520px;">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
		<tr>
			<th colspan="2">
				关联供应商
			</th>
		</tr>
		<tr>
			<td class="FieldLabel">
				主供应商(默认):
			</td>
			<td>
				<select id="defaultSupplierId" name="defaultSupplierId" class="Field400">
					<c:forEach items="${supplierList}" var="supplier">
						<option value="${supplier.supplierId}" <c:if test="${product.supplier.supplierId==supplier.supplierId}">selected="selected"</c:if>>
							${supplier.supplierName}
						</option>
					</c:forEach>
				</select>
			</td>
        </tr>
        <tr>
			<td class="FieldLabel">
				供应商:
			</td>
			<td>
				<select id="supplierIds" name="supplierIds" class="Field400" size="8" multiple="multiple">
					<c:forEach items="${supplierList}" var="supplier">
						<option value="${supplier.supplierId}">
							${supplier.supplierName}
						</option>
					</c:forEach>
				</select>
				&nbsp;&nbsp;
				<input type="image" src="${ctxPath}/images/select.gif" onclick="try{SupplierSelector_show();}catch(e){alert(e.message);}return false;" title="<fmt:message key="productDetail.msg.addCategory" />" />
				<input type="image" src="${ctxPath}/images/clear_field.gif" onclick="try{fnDeleteSupplier();}catch(e){alert(e.message);}return false;" title="<fmt:message key="productDetail.msg.removeCategory" />" />
			</td>
        </tr>
	</table>
	<c:if test="${isOriented}">
		<div class="add-btn">
			<cartmatic:cartmaticBtn btnType="common" onclick="fnTabOriented(6)" commonBtnValueKey="productDetail.back"/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<cartmatic:cartmaticBtn btnType="common" onclick="fnTabOriented(7,7)" commonBtnValueKey="productDetail.finish"/>
		</div>
	</c:if>
	<supplier:supplierSelector id="SupplierSelector" ondblclick="fnAddSupplierHandler" autoClose="true"></supplier:supplierSelector>
</div>