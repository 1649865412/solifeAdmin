<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<app:pageHeading entityName="${catalog.catalogName}" entityHeadingKey="catalogDetail.heading" />
<body>
	<div id="btn-space" style="display: none;">
		<cartmatic:cartmaticBtn btnType="save" onclick="ps('tabIframeId_${param.tid}','fnSaveCatalog();');" />
	    <c:if test="${catalog.catalogId!=null}">
			<cartmatic:cartmaticBtn btnType="delete" onclick="ps('tabIframeId_${param.tid}','fnDoDelete();');" />
		</c:if>
	</div>
	<div class="top-showMsg-close" id="showMsg_id" style="display: none;" ></div>
	<div class="blank10"></div>
	<app:showBindErrors bindPath="catalog.*" />
	<form:form method="post" cssClass="mainForm" id="catalogForm" commandName="catalog" >
			<form:hidden path="version" />
			<input type="hidden" id="catalogId" name="catalogId" value="${catalog.catalogId}"/> 
			<input type="hidden" id="nId" value="${param.nId}"/>
			<input type="hidden" id="tid" value="${param.tid}" />
			<table class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
				<app:input property="name" />
		 		<app:input property="code" />
		 		<tr>
					<td class="FieldLabel">
						<StoreAdmin:label key="catalog.isVirtual" />
					</td>
					<td>
						<fmt:message key="catalog.isVirtual.${catalog.isVirtual}"/>
						<input type="hidden" name="isVirtual" value="${catalog.isVirtual}" />
					</td>
				</tr>
				<tr>
					<td class="FieldLabel">
						<StoreAdmin:label key="catalog.status" />
					</td>
					<td>
						<app:statusCheckbox name="status" style="form-inputbox" value="${catalog.status}" />
					</td>
			    </tr>
				<c:if test="${catalog.isVirtual!=1}">
			    <tr>
					<td class="FieldLabel">
						<label class="required" for="availabilityRule"><fmt:message key="catalog.availabilityRule"/> (*):</label>
					</td>
					<td>
						 <select class="Field400" id="availabilityRule" name="availabilityRule" onchange="fnSelectAvailabilityRuleHandler(this.value);">
							<option value=""></option>
							<c:forEach begin="1" end="5" var="availabilityRule" varStatus="varStatus">
								<option value="${availabilityRule}" <c:if test="${catalog.availabilityRule==availabilityRule}">selected="selected"</c:if>>
									<fmt:message key="product.availabilityRule_${availabilityRule}" />
								</option>
							</c:forEach>
						</select>
						<script type="text/javascript" defer>
							$j(document).ready(function(){
								applyValidate($("availabilityRule"), "required");
							});
						</script>
					</td>
			    </tr>
				</c:if>
		 		<tr>
					<td class="FieldLabel">
						<StoreAdmin:label key="catalog.description" />
					</td>
					<td>
						<textarea id="description" name="description" style="width: 90%; height: 100px">${catalog.description}</textarea>
					</td>
				</tr>
		  	</table>
	</form:form>
	<%--推荐页面：sales/recommendedTypeForCatalog--%>
	<c:if test="${not empty catalog.category.categoryId}">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
		<tr>
		<th><fmt:message key="category.recommendProduct.manage" /></th>
		</tr>
		<tr>
		<td>
			<form name="recommendedProductForm" action="">
			<jsp:include flush="true" page="/sales/recommendedTypeForCatalog.html">
	            <jsp:param name="sourceId" value="${catalog.category.categoryId}" />
	            <jsp:param name="catalogId" value="${catalog.id}" />
	            <jsp:param name="sourceType" value="CATEGORY" />
	            <jsp:param name="doAction" value="defaultAction" />
	        </jsp:include>
	        </form>
		</td>
		</tr>
	</table>
	<script type="text/javascript" defer>
		$j(document).ready(function(){
			fnInitRecommmended();
		});
	</script>
	</c:if>
	<v:javascript formName="catalog" staticJavascript="false" />
	<script type="text/javascript">
		$j(document).ready(function(){
			btnToP();
		});
		//保存Catalog
		function fnSaveCatalog(){
			if (validateCatalog($j('#catalogForm').get(0))) {
				var entityName = $j("#name").val();
				if (confirm(__FMT.common_message_confirmSaveThis + entityName + "?")) {
					var postData = $j('#catalogForm :input').serialize();
					$j.post(__ctxPath + "/catalog/catalog.html?doAction=save", postData, fnSaveCatalogHandler, "json");
				}
			}
			return false;
		}
		function fnSaveCatalogHandler(result){
			fnSaveCallbackShowError(result);
			if (result.status == 1) {
				var data=result.data;
				$j("#catalogId").val(data.cId);
				if(parent){
					var node=parent.fnUpdateNode({id:data.id,tId:$j("#nId").val(),name:data.name,cId:data.cId,isVirtual:data.isVirtual,icon:data.icon});
					$j("#nId").val(node.tId);
				}
				updateTabLable("#"+data.code,"catalog_tab_"+data.id);
				//保存成功后，刷新父窗口
				sysMsg4p(result.msg);
			}
		}
		
		function updateTabLable(lable,newId){
			if(parent&&lable&&lable.replace("#","")){
				parent.fnUpdateTab($j("#tid").val(),lable,newId);
				$j("#tid").val(newId);
			}
		}
		
	</script>
</body>