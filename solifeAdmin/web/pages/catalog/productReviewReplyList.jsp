<%@ include file="/common/taglibs.jsp"%>

<display:table name="${reviewReplyList}" cellspacing="0" cellpadding="0" uid="productReviewItem"
            class="table-list" export="false" requestURI="">
   <display:column sortable="false" headerClass="data-table-title" style="width:70%" 
                decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="productReview.reply.message">
				<c:out value="${productReviewItem.message}" />
    </display:column>
    <display:column sortable="false" headerClass="data-table-title"
                decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" 
                titleKey="productReview.reply.userName">
                ${productReviewItem.reviewUser.username}
    </display:column>
    <display:column sortable="false" headerClass="data-table-title" property="createTime"
                decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" 
                titleKey="productReview.createTime"/>
    <display:column sortable="false" headerClass="data-table-title"
                decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" 
                titleKey="productReview.action">
       <cartmatic:iconBtn icon="cross" extraCss="negative" textKey="common.link.delete" onclick="fnDeleteReply('${productReviewItem.productReviewId}');" />
    </display:column>
</display:table>
