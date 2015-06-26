<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="system" tagdir="/WEB-INF/tags/system"%>
<title><fmt:message key="appAdminList.title"/></title>
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<content tag="heading"><fmt:message key="appAdminList.heading"/></content>
<content tag="description"><fmt:message key="appAdmin.list.header"/></content>
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="create" onclick="return fnDoAdd(this);" />
	<%-- <cartmatic:cartmaticBtn btnType="save" onclick="return fnDoMultiSave(this);" />--%>
	<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoMultiDelete(this);" />
</content>
<app:ui_leftMenu>
	<form method="post" action="${ctxPath}/system/appAdmin.html" onsubmit="return false;">
	    <div class="sidebar-left">
			<app:ui_tabs tabsId="left_menu_tabs"/>
		    <div class="tab" id="left_menu_tabs">
			    <ul>
					<li><a href="#glSearchBar"><fmt:message key="yourposition.search"/></a></li>
				</ul>
				<search:searchBox>
					<search:input attrPath="s.username" attrNameKey="appAdmin.username" datatype="String" operator="LIKE" classes="Field200" />
					<search:input attrPath="s.firstname" attrNameKey="appAdmin.firstname" datatype="String" operator="LIKE" classes="Field200" />
					<search:input attrPath="s.lastname" attrNameKey="appAdmin.lastname" datatype="String" operator="LIKE" classes="Field200" />
					<search:input attrPath="s.email" attrNameKey="appAdmin.email" datatype="String" operator="LIKE" classes="Field200" />
				</search:searchBox>
			</div>
		</div>
	</form>
</app:ui_leftMenu>
<form class="mainForm" name="appAdminListForm" method="post" action="${ctxPath}/system/appAdmin.html">
<c:set var="editURLPath" value="/system/appAdmin.html?doAction=edit&from=list" scope="page" />
<c:set var="checkAll">
	<input type="checkbox" name="allbox" onclick="checkAll(this.form)" style="margin: 0 0 0 0px" />
</c:set>
<display:table name="${appAdminList}" cellspacing="0" cellpadding="0"
	uid="appAdminItem" class="table-list" 
	export="false" requestURI="" >
	<display:column style="width: 5%" title="${checkAll}" media="html"> 
	    <input type="checkbox" name="multiIds" value="${appAdminItem.appuserId}" title="${appAdminItem.username}" class="checkbox" />
	</display:column>
	<!--checkall column ycm 2006-04-28 end-->  
    <display:column property="username" sortable="false"  
        url="${editURLPath}" paramId="appAdminId" paramProperty="appuserId"
        decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="appUser.username"/>
	<display:column sortable="false"  style="width:20%"
        decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="appAdmin.roles">
		<system:roles appAdmin="${appAdminItem}"/> 
	</display:column>
	<display:column property="createTime" sortable="false"  
        decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="appUser.createTime">
	</display:column>
	<display:column sortable="false"  
        decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="appUser.createBy">
		<app:userName userId="${appAdminItem.createBy}"  noFormat="true"/>
	</display:column>
    <display:column titleKey="appUser.status" sortable="false"  decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator">
        <c:choose>
        	<c:when test="${appAdminItem.status==1}">
        		<fmt:message key="status.active" />
        	</c:when>
        	<c:otherwise>
        		<fmt:message key="status.inactive" />
        	</c:otherwise>
        </c:choose>
	</display:column>
    <display:setProperty name="paging.banner.item_name" value="appAdmin"/>
    <display:setProperty name="paging.banner.items_name" value="appAdmins"/>
</display:table>
<%@include file="/common/pagingOnlyNew.jsp"%>
</form>
<script type="text/javascript">
<!--
highlightTableRows("appAdminItem");
//-->
</script>