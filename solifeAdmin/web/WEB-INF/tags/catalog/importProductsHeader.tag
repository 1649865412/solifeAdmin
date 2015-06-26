<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="name" %>
<%@ attribute name="value" %>
<select name="${name}" style="width:100%" class="form-inputbox" onchange="checkRepeatItem(this)">	
	<option value=""></option>
	<option value="productCode" <c:if test="${value=='productCode'}">selected</c:if>>product code(*)</option>
	<option value="categoryCode" <c:if test="${value=='categoryCode'}">selected</c:if>>category code(*)</option>
	<option value="productName" <c:if test="${value=='productName'}">selected</c:if>>product name(*)</option>
	<option value="manufacturerProductCode" <c:if test="${value=='manufacturerProductCode'}">selected</c:if>> manufacturer Product Code</option>
	<option value="listPrice" <c:if test="${value=='listPrice'}">selected</c:if>>list price</option>
	<option value="price" <c:if test="${value=='price'}">selected</c:if>>price(*)</option>
	<option value="costPrice" <c:if test="${value=='costPrice'}">selected</c:if>>cost price(*)</option>
	<option value="minOrderQuantity" <c:if test="${value=='minOrderQuantity'}">selected</c:if>>minimal order quantity</option>
	<option value="length" <c:if test="${value=='length'}">selected</c:if>>length</option>
	<option value="width" <c:if test="${value=='width'}">selected</c:if>>width</option>
	<option value="weight" <c:if test="${value=='weight'}">selected</c:if>>weight</option>
	<option value="height" <c:if test="${value=='height'}">selected</c:if>>height</option>
	<option value="quantityUnitCode" <c:if test="${value=='quantityUnitCode'}">selected</c:if>>quantity unit code</option>
	<option value="lengthUnitCode" <c:if test="${value=='lengthUnitCode'}">selected</c:if>>length unit code</option>
	<option value="weightUnitCode" <c:if test="${value=='weightUnitCode'}">selected</c:if>>weight unit code</option>
	<option value="stockQuantity" <c:if test="${value=='stockQuantity'}">selected</c:if>>stock quantity(*)</option>
	<option value="stockMinimalQuantity" <c:if test="${value=='stockMinimalQuantity'}">selected</c:if>>stock minimal quantity</option>
	<option value="shortDescription" <c:if test="${value=='shortDescription'}">selected</c:if>>short description</option>
	<option value="metaKeyword" <c:if test="${value=='metaKeyword'}">selected</c:if>>meta keyword</option>
	<option value="metaDescription" <c:if test="${value=='metaDescription'}">selected</c:if>>meta description</option>
</select>