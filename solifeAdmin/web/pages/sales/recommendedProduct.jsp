<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<table cellpadding="0" cellspacing="0" class="table-recommend">

<c:forEach items="${recommendedProductList}" var="recommendedProduct" varStatus="status">
    <tr>
    <td width="200"><span title="id:${recommendedProduct.product.productId},code:${recommendedProduct.product.productCode}">${recommendedProduct.product.productName}</span></td>
    <td width="100">
        <fmt:message key="recommendedProduct.sortOrder"/>
        <input id="sortOrder_${recommendedProduct.recommendedProductId}" 
        name="sortOrder_${recommendedProduct.recommendedProductId}" 
        type="text" value="${recommendedProduct.sortOrder}" size="4"/>
    </td>
    <td width="250">
        <input id="startTime_${recommendedProduct.recommendedProductId}" 
        name="startTime_${recommendedProduct.recommendedProductId}" 
        type="text" value="${recommendedProduct.displayStartTime}" size="20" style="width:80px;"/>
        <app:ui_datePicker outPut="startTime_${recommendedProduct.recommendedProductId}" />
       <fmt:message key="recommendedProduct.to"/>
       
      <input id="expireTime_${recommendedProduct.recommendedProductId}" 
       name="expireTime_${recommendedProduct.recommendedProductId}" 
       type="text" value="${recommendedProduct.displayExpireTime}" size="20" style="width:80px;"/>
       <app:ui_datePicker outPut="expireTime_${recommendedProduct.recommendedProductId}" />
    </td>
  	<td>
       <app:statusCheckbox name = "status_${recommendedProduct.recommendedProductId}" value="${recommendedProduct.status}"/>
   	</td>
    <td>
       <a href="javascript:fnSaveRecommendedProduct('${recommendedProduct.recommendedProductId}','${recommendedProduct.sourceId}','${recommendedProduct.recommendedType.recommendedTypeId}')">
         <img src="${ctxPath}/images/icon-small-save.gif" title="<fmt:message key="button.save"/>"/></a>
       <a href="javascript:fnDeleteRecommendedProduct('${recommendedProduct.recommendedProductId}','${recommendedProduct.recommendedType.recommendedTypeId}')">
         <img src="${ctxPath}/images/icon-small-delete.gif"/ title="<fmt:message key="button.delete"/>"></a>
    </td>
     <td>
    	<c:if test="${recommendedProduct.state == 1}">
			<c:set var="stateClass" value="txt-process"></c:set>
		</c:if>
		<c:if test="${recommendedProduct.state == 2}">
			<c:set var="stateClass" value="txt-future"></c:set>
		</c:if>
		<c:if test="${recommendedProduct.state == 3}">
			<c:set var="stateClass" value="txt-preterite"></c:set>
		</c:if>
		<c:if test="${recommendedProduct.state == 0}">
			<c:set var="stateClass" value="txt-disabled"></c:set>
		</c:if>
		<span class="${stateClass}"><fmt:message key="recommendedProduct.state.s${recommendedProduct.state}" /></span>
    </td>
    </tr>
</c:forEach>
</table>