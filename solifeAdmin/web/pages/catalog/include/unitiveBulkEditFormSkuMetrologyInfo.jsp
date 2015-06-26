<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
	<tr>
		<th colspan="3">
			<fmt:message key="product.sku.tab.metrology" />
		</th>
	</tr>
	
	<%--==================重量=================--%>
	<tr>
		<td width="20">
			<input type="checkbox" id="weightCheck" name="weightCheck" value="1"/>
		</td>
		<td width="80">
			<fmt:message key="productSku.weight" />
		</td>
		<td>
			<span><input name="weight" id="weight" type="text" class="Field200"/>
			<c:if test="${not empty appConfig.weightUnit}">&nbsp;(${appConfig.weightUnit})</c:if></span>
		</td>
	</tr>
	<%--==================长=================--%>
	<tr>
		<td>
			<input type="checkbox" id="lengthCheck" name="lengthCheck" value="1"/>
		</td>
		<td>
			<fmt:message key="productSku.length" />
		</td>
		<td>
			<span><input name="length" id="length" type="text" class="Field200"/>
			<c:if test="${not empty appConfig.lengthUnit}">&nbsp;(${appConfig.lengthUnit})</c:if></span>
		</td>
	</tr>
	<%--==================宽=================--%>
	<tr>
		<td>
			<input type="checkbox" id="widthCheck" name="widthCheck" value="1"/>
		</td>
		<td>
			<fmt:message key="productSku.width" />
		</td>
		<td>
			<span><input name="width" id="width" type="text" class="Field200"/>
			<c:if test="${not empty appConfig.lengthUnit}">&nbsp;(${appConfig.lengthUnit})</c:if></span>
		</td>
	</tr>
	<%--==================高=================--%>
	<tr>
		<td>
			<input type="checkbox" id="heightCheck" name="heightCheck" value="1"/>
		</td>
		<td>
			<fmt:message key="productSku.height" />
		</td>
		<td>
			<span><input name="height" id="height" type="text" class="Field200"/>
			<c:if test="${not empty appConfig.lengthUnit}">&nbsp;(${appConfig.lengthUnit})</c:if></span>
		</td>
	</tr>
</table>