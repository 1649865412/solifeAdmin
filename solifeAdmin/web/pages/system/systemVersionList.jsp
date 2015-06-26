<%@ include file="/common/taglibs.jsp"%>
<app:pageHeading pageHeadingKey="systemVersionList.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="edit" inputType="button" onclick="location.href='${ctxPath}/system/systemVersion.html?doAction=edit&from=list&systemVersionId=${systemVersion.systemVersionId}';return false;" />
	<cartmatic:cartmaticBtn btnType="cancel" inputType="button" onclick="return fnDoUpToParent(this);" />
</content>
<form class="mainForm" name="systemVersionListForm" method="post" action="${ctxPath}/system/systemVersion.html">
</form>
<table class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
	<tr>
		<td class="FieldLabel">
			<StoreAdmin:label key="systemVersion.sysVersion" />
		</td>
		<td>
			${systemVersion.sysVersion}
		</td>
	</tr>
	<tr>
		<td class="FieldLabel">
			<StoreAdmin:label key="systemVersion.productCode" />
		</td>
		<td>
			${systemVersion.productCode}
		</td>
	</tr>
	<tr>
		<td class="FieldLabel">
			<StoreAdmin:label key="systemVersion.domainName" />
		</td>
		<td>
			${systemVersion.domainName}
		</td>
	</tr>
	<tr>
		<td class="FieldLabel">
			<StoreAdmin:label key="systemVersion.licenseKey" />
		</td>
		<td>
			${systemVersion.licenseKey}
		</td>
	</tr>
</table>
<display:table name="${patchHistorys}" cellspacing="0" cellpadding="0" uid="patchHistorys" class="table-list" export="false" requestURI="">
	<!--checkall column ycm 2006-04-28 end-->
	<display:column property="fromVersion" sortable="true" headerClass="data-table-title" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="patchHistorys.fromVersion" />
	<display:column property="toVersion" sortable="true" headerClass="data-table-title" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="patchHistorys.toVersion" />
	<display:column property="executeTime" sortable="true" headerClass="data-table-title" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="patchHistorys.executeTime" />
</display:table>