<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="customer" tagdir="/WEB-INF/tags/customer"%>
<table class="table-content" cellpadding="0" cellspacing="0" border="0" width="100%">
    <tr>
        <th>
       		 地址
        </th> 
    </tr>
    <tr>
        <td>
				<display:table name="${addressList}" cellspacing="0" cellpadding="0" uid="addressItem"  export="false" requestURI="" >
					<display:column style="width: 5%" title="&nbsp;" >
					   <c:out value="${addressItem_rowNum}"/>
					</display:column>
				    <display:column property="address"  sortable="false"  maxLength="20"
				        decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="address.address">
					</display:column>
				    <display:column  sortable="false"  
				        decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="appUser.username">
						<c:out value="${addressItem.appUser.username}" />
					</display:column>
				    <display:column  sortable="false"  
				        decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="address.addressType">
						<fmt:message key="address.type${addressItem.addressType}"/>
					</display:column>
				    <display:column property="title" sortable="false"  
				        decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="address.title"/>
				    <display:column property="firstname" sortable="false"  
				        decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="address.firstname"/>
				    <display:column property="lastname" sortable="false"  
				        decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="address.lastname"/>
				    <display:setProperty name="paging.banner.item_name" value="address"/>
				    <display:setProperty name="paging.banner.items_name" value="addresss"/>
				</display:table>
		</td>
	</tr>
</table>
<%@include file="/common/pagingOnlyNew.jsp"%>