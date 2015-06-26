<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib prefix="supplier" tagdir="/WEB-INF/tags/supplier"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags/app"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="otherInfo" style="display:none">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
		<tr>
			<th colspan="2">
				基本信息
			</th>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="product.createTime" />
			</td>
			<td>
				<fmt:formatDate value="${product.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
			</td>
	    </tr>
	    <tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="product.updateTime" />
			</td>
			<td>
				<fmt:formatDate value="${product.updateTime}" pattern="yyyy-MM-dd HH:mm:ss" />
			</td>
	    </tr>
	    <tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="product.createBy" />
			</td>
			<td>
				<app:userName userId="${product.createBy}" noFormat="true"></app:userName>
			</td>
	    </tr>
	    <tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="product.updateBy" />
			</td>
			<td>
				<app:userName userId="${product.updateBy}" noFormat="true"></app:userName>
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
</div>