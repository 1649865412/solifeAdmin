<%@ include file="/common/taglibs.jsp"%>
<title><fmt:message key="wishlistList.title"/></title>
<content tag="heading"><fmt:message key="wishlistList.heading"/></content>
<content tag="buttons">
    <cartmatic:cartmaticBtn btnType="cancel" onclick="location.href='${ctxPath}/customer/customer.html';"/>
</content>
<c:if test="${not empty param['tabIndex']}">
	<%@include file="./include/customerTabHeader.jspf" %>
</c:if>
<table class="table-content" cellpadding="0" cellspacing="0" border="0" width="100%">
    <tr>
        <th>
        	意愿清单
        </th> 
    </tr>
    <tr>
        <td>
			<form class="mainForm" name="wishlistListFrm" method="post">
				<display:table name="${wishlistList}" cellspacing="0" cellpadding="0" uid="wishlistItem" export="false" requestURI="" >
					<%-- 
					<display:column style="width: 3%" title="&nbsp;" >
							   <input  type="checkbox" name="ids" value="<c:out value="${wishlistItem.wishListId}"/>" />
					</display:column>
					--%>
					<display:column style="width: 3%;vertical-align:middle" title="&nbsp;" >
					   <c:out value="${wishlistItem_rowNum}"/>
					   <c:if test="${wishlistItem_rowNum eq '1'}">
							<script type="text/javascript" defer>
							$j("div[class='position']>div>span").append("[ ${wishlistItem.customer.username} ]");
							</script>
					   </c:if>
					</display:column>
				    <display:column style="vertical-align:middle" property="wishListTitle" sortable="false" 
				        decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="wishlist.wishListTitle"/>
				    <display:column style="vertical-align:middle" sortable="false" titleKey="wishlist.itemDetail">
				    	<ul style="list-style-type: disc">
				    	<c:forEach items="${wishlistItem.wishlistItems}" var="subItem">
				    		<li>${subItem.product.productName}</li>	    		
				    	</c:forEach>
				    	</ul>
				    </display:column>
				    <display:column style="vertical-align:middle" sortable="false" titleKey="wishlist.shippingAddressId">
				    	${wishlistItem.address.address}
				    </display:column>
				    <display:setProperty name="paging.banner.item_name" value="wishlist"/>
				    <display:setProperty name="paging.banner.items_name" value="wishlists"/>
				    <%@include file="/common/pagingOnlyNew.jsp"%>
				</display:table>
			</form>
		</td>
	</tr>
</table>
<script type="text/javascript">
<!--
	highlightTableRows("wishlistItem");
//-->
</script>