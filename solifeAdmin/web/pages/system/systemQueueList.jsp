<%@ include file="/common/taglibs.jsp"%>
<app:pageHeading pageHeadingKey="systemQueueList.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoMultiDelete(this);" />
	<cartmatic:cartmaticBtn btnType="cancel" inputType="button" onclick="return fnDoUpToParent(this);" />
	<cartmatic:cartmaticBtn btnType="common" commonBtnValueKey="menu.imageRebuild" inputType="button" onclick="return fnRebuildImage();" />
	<cartmatic:cartmaticBtn btnType="common" commonBtnValueKey="menu.cleanImage" inputType="button" onclick="return fnCleanImage();" />
</content>
<app:ui_leftMenu>
	<form method="post" action="${ctxPath}/system/systemQueue.html" onsubmit="return false;">
	    <div class="sidebar-left">
			<app:ui_tabs tabsId="left_menu_tabs"/>
		    <div class="tab" id="left_menu_tabs">
			    <ul>
					<li><a href="#glSearchBar"><fmt:message key="yourposition.search"/></a></li>
				</ul>
				<search:searchBox>
					<search:filter />
					<search:input attrPath="s.title" attrNameKey="systemQueue.title" datatype="String"
						operator="LIKE" classes="Field200" />
					<div class="title"><fmt:message key="systemQueue.queueStatus"/></div>
					<c:set var="attrName">COL@s.queueStatus@Short@EQ</c:set>
					<c:set var="selectedQueueStatus" value="${sc==null ? requestScope[attrName] : sc.param[attrName]}"/>
					<select id="${attrName}" name="${attrName}" class="Field200">
						<option value=""><fmt:message key="search.filter.all"/></option>
						<option value="0" <c:if test="${selectedQueueStatus==0}">selected="selected"</c:if>><fmt:message key="systemQueue.squeueStatus0"/></option>
						<option value="1" <c:if test="${selectedQueueStatus==1}">selected="selected"</c:if>><fmt:message key="systemQueue.squeueStatus1"/></option>
						<option value="2" <c:if test="${selectedQueueStatus==2}">selected="selected"</c:if>><fmt:message key="systemQueue.squeueStatus2"/></option>
						<option value="9" <c:if test="${selectedQueueStatus==9}">selected="selected"</c:if>><fmt:message key="systemQueue.squeueStatus9"/></option>
					</select>
					<search:orderby showOrderDirection="true">
						<option value="systemQueueId">
							<fmt:message key="search.order.by.default" />
						</option>
					</search:orderby>
				</search:searchBox>
			</div>
		</div>
	</form>
</app:ui_leftMenu>
<form class="mainForm" name="systemQueueListForm" method="post" action="${ctxPath}/system/systemQueue.html">
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/system/systemQueue.html?doAction=edit&from=list" scope="page" />
	
		<c:set var="checkAll"><input type="checkbox" name="allbox" onclick="checkAll(this.form)" class="checkbox"/></c:set>
		<display:table name="${systemQueueList}" cellspacing="0" cellpadding="0" uid="systemQueueItem"
			class="table-list" export="false" requestURI="">
			<display:column style="width: 3%" title="${checkAll}" media="html">
				<input type="checkbox" name="multiIds" value="${systemQueueItem.systemQueueId}" class="checkbox" title="${systemQueueItem.systemQueueName}"/>
			</display:column>
		    <display:column property="title" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="systemQueue.title"/>
		    <display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="systemQueue.queueType">
        		<fmt:message key="systemQueue.type${systemQueueItem.queueType}"/>
        	</display:column>
		    <display:column property="execTimes" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="systemQueue.execTimes"/>
		    <display:column property="errorMsg" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="systemQueue.errorMsg"/>
		    <display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="systemQueue.queueStatus">
        		<fmt:message key="systemQueue.squeueStatus${systemQueueItem.queueStatus}"/>
        	</display:column>
		    <display:column property="createTime" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="systemQueue.createTime"/>
		</display:table>
		<%@include file="/common/pagingOnlyNew.jsp"%>
</form>
<script type="text/javascript">
highlightTableRows("systemQueueItem");

function fnRebuildImage()
{
	if (confirm("确认要进行图片重构(生成各个尺寸的图片)?"))
	{
		window.location = "${ctxPath}/system/systemQueue.html?doAction=imageRebuild";
	}
}
function fnCleanImage()
{
	if (confirm("确认要删除没有引用的图片文件(只处理产品主图及更多图片)?"))
	{
		window.location = "${ctxPath}/system/systemQueue.html?doAction=cleanUnusefulImage";
	}
}
</script>