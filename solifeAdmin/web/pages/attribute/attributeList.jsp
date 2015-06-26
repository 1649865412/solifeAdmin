<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="attribute" tagdir="/WEB-INF/tags/attribute"%>
<%@ taglib prefix="search" tagdir="/WEB-INF/tags/search"%>
<app:pageHeading pageHeadingKey="attributeSystemList.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="create" inputType="button" onclick="window.location.href='attribute.html?doAction=add&from=list';" />
	<%--<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoMultiSave(this);" />--%>
	<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoMultiDelete(this);" />
	<cartmatic:cartmaticBtn btnType="cancel" inputType="button" onclick="return fnDoUpToParent(this);" />
</content>
<app:ui_leftMenu>
	<form method="post" action="${ctxPath}/system/attribute.html" onsubmit="return false;">
	    <div class="sidebar-left">
	        <app:ui_tabs tabsId="left_menu_tabs"/>
	    	<div class="tab" id="left_menu_tabs">
			    <ul>
					<li><a href="#glSearchBar"><fmt:message key="yourposition.search"/></a></li>
				</ul>
				<search:searchBox>
					<div class="title"><fmt:message key="attribute.search.filter" /></div>
					<div class="">
					    <c:set var="attrName">COL@attributeType@Short@=</c:set>
						<select name="COL@attributeType@Short@=" >
		                  <option value="1" ${(sc==null ? (1==requestScope[attrName]) : (1==sc.param[attrName])) ? 'selected' : ''}><fmt:message key="attribute.attributeType.product"/></option>
				          <option value="2" ${(sc==null ? (2==requestScope[attrName]) : (2==sc.param[attrName])) ? 'selected' : ''}><fmt:message key="attribute.attributeType.content"/></option>
				          <option value="3" ${(sc==null ? (3==requestScope[attrName]) : (3==sc.param[attrName])) ? 'selected' : ''}><fmt:message key="attribute.attributeType.customer"/></option>
				          <option value="5" ${(sc==null ? (5==requestScope[attrName]) : (5==sc.param[attrName])) ? 'selected' : ''}><fmt:message key="attribute.attributeType.productcategory"/></option>				   
						</select>
					</div>
					<search:input attrPath="s.attributeName" attrNameKey="attribute.attributeName" datatype="String"
						operator="LIKE" classes="form-inputbox" />
					<search:input operator="LIKE" datatype="String" attrNameKey="attribute.attributeCode" attrPath="s.attributeCode"/>
					<search:orderby showOrderDirection="true">
						<option value="attributeId">
							<fmt:message key="search.order.by.default" />
						</option>
						<option value="attributeCode">
							<fmt:message key="attribute.attributeCode" />
						</option>
					</search:orderby>
				</search:searchBox>
		    </div>
		</div>
	</form>
</app:ui_leftMenu>

<form class="mainForm" name="attributeListForm" method="post" action="${ctxPath}/system/attribute.html">
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/system/attribute.html?doAction=edit&from=list" scope="page" />
	
		<c:set var="checkAll"><input type="checkbox" name="allbox" onclick="checkAll(this.form)" style="margin: 0 0 0 0px" /></c:set>
		<display:table name="${attributeList}" cellspacing="0" cellpadding="0" uid="attributeItem"
			class="table-list" export="false" requestURI="">
			<display:column style="width: 3%" title="${checkAll}" media="html">
				<input type="checkbox" name="multiIds" value="${attributeItem.attributeId}"	class="checkbox" title="${attributeItem.attributeName}"/>
				<input type="hidden" name="attrIds" value="${attributeItem.attributeId}">
			</display:column>
			
        	<display:column property="attributeCode" url="${editURLPath}" paramId="attributeId" paramProperty="attributeId" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="attribute.attributeCode"/>
		    <display:column property="attributeName" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="attribute.attributeName"/>
		    <display:column 
		        sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="attribute.attributeType">
        		<c:choose>                                    
		          <c:when test="${attributeItem.attributeType == 1}"><fmt:message key="attribute.attributeType.product"/></c:when>
		          <c:when test="${attributeItem.attributeType == 2}"><fmt:message key="attribute.attributeType.content"/></c:when>
		          <c:when test="${attributeItem.attributeType == 3}"><fmt:message key="attribute.attributeType.customer"/></c:when>
		          <c:when test="${attributeItem.attributeType == 4}"><fmt:message key="attribute.attributeType.saleorder"/></c:when>
		          <c:otherwise><fmt:message key="attribute.attributeType.productcategory"/></c:otherwise>
		        </c:choose>        		
        	</display:column>        		
		    <display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="attribute.attributeDataType">
        		<attribute:datatype item="${attributeItem}"/>
        	</display:column>		    
		</display:table>
		<%@ include file="/common/pagingOnlyNew.jsp"%>
</form>
<script type="text/javascript">
highlightTableRows("attributeItem");
</script>