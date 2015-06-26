<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="customer" tagdir="/WEB-INF/tags/customer"%>
<table class="table-content" cellpadding="0" cellspacing="0" border="0" width="100%">
    <tr>
        <th>
       		 优惠劵
        </th> 
    </tr>
    <tr>
        <td>
				<display:table name="${customer.userCoupons}" cellspacing="0" cellpadding="0" uid="couponItem" export="false" requestURI="">
					<display:column style="width: 5%" title="&nbsp;" >
					   <c:out value="${couponItem_rowNum}"/>
					</display:column>
				    <display:column property="couponNo" sortable="false" 
		        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="coupon.couponNo"/>
				    <display:column property="remainedTimes" sortable="false" 
		        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="coupon.remainedTimes"/>
				</display:table>
		</td>
	</tr>
</table>
<%@include file="/common/pagingOnlyNew.jsp"%>