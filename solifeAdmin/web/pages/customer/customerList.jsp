<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="customer" tagdir="/WEB-INF/tags/customer"%>
<app:pageHeading pageHeadingKey="appUserList.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="create" inputType="button" onclick="return fnDoAdd(this);" />
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoMultiSave(this);" />
	<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoMultiDelete(this);" />
	<cartmatic:cartmaticBtn btnType="cancel" inputType="button" onclick="return fnDoUpToParent(this);" />
</content>
<app:ui_leftMenu>
	<form method="post" action="${ctxPath}/customer/customer.html" onsubmit="return false;">
	    <div class="sidebar-left">
			<app:ui_tabs tabsId="left_menu_tabs"/>
		    <div class="tab" id="left_menu_tabs">
			    <ul>
					<li><a href="#glSearchBar"><fmt:message key="yourposition.search"/></a></li>
				</ul>
				<search:searchBox>
					<search:input attrPath="s.username" attrNameKey="customer.username" datatype="String" operator="LIKE" classes="form-inputbox" />
					<search:input attrPath="s.firstname" attrNameKey="customer.firstname" datatype="String" operator="LIKE" classes="form-inputbox" />
					<search:input attrPath="s.lastname" attrNameKey="customer.lastname" datatype="String" operator="LIKE" classes="form-inputbox" />
					<search:input attrPath="s.email" attrNameKey="customer.email" datatype="String" operator="LIKE" classes="form-inputbox" />
					<div class="title">
						<fmt:message key="customer.store" />
					</div>
					<select name="COL@s.store.storeId@Integer@EQ" id="COL@s.store.storeId@Integer@EQ" class="form-inputbox">
						<option value=""></option>
						<c:forEach items="${appConfig.storeMap}" var="storeItem">
						<option value="${storeItem.value.storeId}" 
							<c:set var="attrName">COL@s.store.storeId@Integer@EQ</c:set>
							<c:if test="${sc==null ? (storeItem.value.storeId==requestScope[attrName]) : (storeItem.value.storeId==sc.param[attrName])}">selected="selected" </c:if>>${storeItem.value.name}</option>
						</c:forEach>
					</select>
				</search:searchBox>
			</div>
		</div>
	</form>
</app:ui_leftMenu>
<form class="mainForm" name="appUserListForm" method="post" action="${ctxPath}/customer/customer.html">
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/customer/customer.html?doAction=edit&from=list" scope="page" />
		<c:set var="checkAll"><input type="checkbox" name="allbox" onclick="checkAll(this.form)" class="checkbox"/></c:set>
		<display:table name="${customerList}" cellspacing="0" cellpadding="0" uid="customerItem" class="table-list" style="table-layout:fixed;" export="false" requestURI="">
			<display:column headerClass="w30" class="w30" title="${checkAll}"  decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" media="html">
			    <input type="checkbox" name="multiIds" value="${customerItem.customerId}" title="${customerItem.username}" class="checkbox" />
			</display:column>
			 <display:column headerClass="w200" class="w200" property="username" sortable="false"  
				 url="${editURLPath}" paramId="customerId" paramProperty="customerId"
		        decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="customer.username"/>
		    <display:column headerClass="wauto" class="wauto" sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="customer.fullname">
		    	${customerItem.firstname}&nbsp;${customerItem.lastname}<c:if test="${customerItem.isSupplier}">[供应商用户]</c:if>
		    </display:column>
		    <display:column headerClass="w200" class="w200" sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="customer.email">
		    	${customerItem.email}
		    </display:column> 
		    <display:column headerClass="w150" class="w150" sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="customer.store">
		    	${customerItem.store.name}
		    </display:column>
		    <display:column headerClass="w150" class="w150" sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="customer.membershipId">
		    	<customer:membershipName membershipId="${customerItem.membershipId}"/>
		    </display:column>
		    <display:column headerClass="w100" class="w100" property="createTime" sortable="false"  
		        decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="customer.registerTime"/>
		    <display:column headerClass="w60" class="w60" sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="shopPoint.total">
				<customer:shopPoint var="shopPoint" customerId="${customerItem.id}"></customer:shopPoint>
				${shopPoint.total}
			</display:column>
			<display:column headerClass="w60" class="w60" titleKey="appUser.status" media="html" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator">
				<c:choose>
		        	<c:when test="${customerItem.status==1}">
		        		<fmt:message key="status.active" />
		        	</c:when>
		        	<c:otherwise>
		        		<fmt:message key="status.inactive" />
		        	</c:otherwise>
		        </c:choose>
			</display:column>
			<display:setProperty name="export.csv.filename" value="customer.csv"/>
			<display:setProperty name="export.excel.filename" value="customer.xls"/>
			<display:setProperty name="export.pdf.filename" value="customer.pdf"/>
			<display:setProperty name="export.xml.filename" value="customer.xml"/>
		    <display:setProperty name="paging.banner.item_name" value="customer"/>
		    <display:setProperty name="paging.banner.items_name" value="customers"/>
		</display:table>
		<%@include file="/common/pagingNew.jsp"%>
</form>
<script type="text/javascript">
highlightTableRows("customerItem");
</script>