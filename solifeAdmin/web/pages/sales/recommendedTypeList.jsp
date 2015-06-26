<%@ include file="/common/taglibs.jsp"%>
<app:pageHeading pageHeadingKey="recommendedTypeList.heading" />

<content tag="buttons">

<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoUpToParent(this);" />
</content>
<form class="mainForm" action="${ctxPath}/sales/recommendedType.html"
	name="mainForm" method="post">
		<display:table name="${recommendedTypeList}" cellspacing="0"
			cellpadding="0" uid="recommendedTypeItem" class="table-list"
			export="false" requestURI="">
			<display:column property="recommendTitle" url="/sales/recommendedType.html?doAction=edit"
				paramId="recommendedTypeId" paramProperty="recommendedTypeId" sortable="false" headerClass="data-table-title"
				decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator"
				titleKey="recommendedType.recommendTitle"/>
			<display:column property="typeName" sortable="false"
				headerClass="data-table-title" 
				decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator"
				titleKey="recommendedType.typeName" />
			<display:column sortable="false"
				headerClass="data-table-title" 
				decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator"
				titleKey="recommendedType.autoEval" >
				<fmt:message key="recommendedType.autoEval.s${recommendedTypeItem.autoEval}"/>
			</display:column>
			<display:column sortable="false"
				headerClass="data-table-title" 
				decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator"
				titleKey="recommendedType.status" >
				<fmt:message key="recommendedType.status.s${recommendedTypeItem.status}"/>
			</display:column>
		</display:table>
		<%@ include file="/common/pagingOnlyNew.jsp"%>
</form>

