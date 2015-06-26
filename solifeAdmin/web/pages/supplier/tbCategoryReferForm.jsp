<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>
<app:pageHeading entityName="${tbCategoryRefer.tbCategoryReferName}" entityHeadingKey="tbCategoryReferDetail.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this);" />
	<%--
	<cartmatic:cartmaticBtn btnType="saveAndNext" onclick="return fnDoSaveAndNext(this);" />
	--%>
    <c:if test="${tbCategoryRefer.tbCategoryReferId!=null}">
		<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoDelete(this);" />
	</c:if>
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="tbCategoryRefer.*" />
	<form:form method="post" cssClass="mainForm" id="tbCategoryRefer" commandName="tbCategoryRefer" action="${ctxPath}/supplier/tbCategoryRefer.html" onsubmit="return validateTbCategoryRefer(this);">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
			<tr>
				<th colspan="2">
					<fmt:message key="tbCategoryReferDetail.heading" /><input type="hidden" name="tbCategoryReferId" value="${tbCategoryRefer.tbCategoryReferId}"/> 
				</th>
			</tr>
			<tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="tbCategoryRefer.tbCategoryId" />
				</td>
				<td>
					<input class="Field400" type="text" name="tbCategoryId" id="tbCategoryId" value="<c:out value="${tbCategoryRefer.tbCategoryId}"/>" readonly="readonly"/>
				</td>
		    </tr>
		    <tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="tbCategoryRefer.tbCategoryName" />
				</td>
				<td>
					<input class="Field400" type="text" name="tbCategoryName" id="tbCategoryName" value="${tbCategoryRefer.tbCategoryName}" />
				</td>
		    </tr>
		    <tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="tbCategoryRefer.categoryId" />
				</td>
				<td>
					<input class="Field400" type="text" id="categoryName" value="${tbCategoryRefer.category.categoryPathName}" readonly="readonly"/>
					<input type="hidden" name="categoryId" id="categoryId" value="${tbCategoryRefer.categoryId}" />
					<cartmatic:iconBtn icon="pen" text="选择目录" onclick="try{CategorySelector_show();}catch(e){alert(e.message);}return false;"/>
				</td>
		    </tr>
		</table>
</form:form>
<product:categorySelector id="CategorySelector" ondblclick="fnAddCategoryHandler" canSelectRoot="false"></product:categorySelector>
<v:javascript formName="tbCategoryRefer" staticJavascript="false" />
<script type="text/javascript">
function fnAddCategoryHandler(category){
	$j("#categoryId").val(category.categoryId);
	$j("#categoryName").val(category.categoryName);
}
</script>