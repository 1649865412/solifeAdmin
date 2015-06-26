<%@ include file="/common/taglibs.jsp"%>
<app:pageHeading pageHeadingKey="feedbackList.heading" />

<content tag="buttons">
	<%--
	<cartmatic:cartmaticBtn btnType="create" inputType="button" onclick="return fnDoAdd(this);" />
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoMultiSave(this);" />
	--%>
	<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoMultiDelete(this);" />
	<cartmatic:cartmaticBtn btnType="cancel" inputType="button" onclick="return fnDoUpToParent(this);" />
</content>
<app:ui_leftMenu>
	<form method="post" action="${ctxPath}/customer/feedback.html" onsubmit="return false;">
	    <div class="sidebar-left">
			<app:ui_tabs tabsId="left_menu_tabs"/>
		    <div class="tab" id="left_menu_tabs">
			    <ul>
					<li><a href="#glSearchBar"><fmt:message key="yourposition.search"/></a></li>
				</ul>
				<search:searchBox>
					<search:input attrPath="s.appUser.username" attrNameKey="appUser.username" datatype="String" operator="LIKE" classes="form-inputbox" />
					<search:input attrPath="s.subject" attrNameKey="feedback.subject" datatype="String" operator="LIKE" classes="form-inputbox" />
					<div class="title">
						<fmt:message key="feedback.replyType"/>
					</div>
					<div>
						<select name="COL@s.replyType@Short@EQL" style="width:50%" class="Field400">
							<option value="">  </option>
							<option value="0" <c:if test="${param['COL@s.replyType@Short@EQL'] eq '0'}">selected</c:if>><fmt:message key="feedback.replyType${0}"/></option>
							<option value="1" <c:if test="${param['COL@s.replyType@Short@EQL'] eq '1'}">selected</c:if>><fmt:message key="feedback.replyType${1}"/></option>
							<option value="2" <c:if test="${param['COL@s.replyType@Short@EQL'] eq '2'}">selected</c:if>><fmt:message key="feedback.replyType${2}"/></option>
							<option value="3" <c:if test="${param['COL@s.replyType@Short@EQL'] eq '3'}">selected</c:if>><fmt:message key="feedback.replyType${3}"/></option>
						</select>
					</div>
					<div class="title"><fmt:message key="feedback.feedbackType"/></div>
					<div>
						<select name="COL@s.feedbackType@Short@EQL" style="width:50%" class="Field400">
							<option value=""></option>
							<option value="0" <c:if test="${param['COL@s.feedbackType@Short@EQL'] =='0'}">selected</c:if>><fmt:message key="feedback.feedbackType0"/></option>
							<option value="1" <c:if test="${param['COL@s.feedbackType@Short@EQL'] =='1'}">selected</c:if>><fmt:message key="feedback.feedbackType1"/></option>	
						</select>
					</div>
				</search:searchBox>
			</div>
		</div>
	</form>
</app:ui_leftMenu>
<form class="mainForm" name="feedbackListForm" method="post" action="${ctxPath}/customer/feedback.html">
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/customer/feedback.html?doAction=edit&from=list" scope="page" />
		<c:set var="checkAll"><input type="checkbox" name="allbox" onclick="checkAll(this.form)" class="checkbox"/></c:set>
		<display:table name="${feedbackList}" cellspacing="0" cellpadding="0" uid="feedbackItem"
			class="table-list" export="false" requestURI="">
			<display:column style="width: 3%" title="${checkAll}" media="html">
				<input type="checkbox" name="multiIds" value="${feedbackItem.feedbackId}" class="checkbox" title="${feedbackItem.feedbackName}"/>
			</display:column>
			<display:column  sortable="false" 
		        decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="feedback.appuserId">
				<c:out value="${feedbackItem.appUser.username}"/>
			</display:column>
		    <display:column  escapeXml="true" sortable="false"  style="width:30%"
				 url="/customer/feedback.html?doAction=replysAction&btnSearch=true" paramId="feedbackId" paramProperty="feedbackId"
		        decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="feedback.subject">
		        <c:out value="${feedbackItem.subject}" escapeXml="true"></c:out>
		        </display:column>
		    <display:column sortable="false" 
		        decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="feedback.replyType">
				<fmt:message key="feedback.replyType${feedbackItem.replyType}"/>
			</display:column>
		    <display:column sortable="false" 
		        decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="feedback.feedbackType">
				<fmt:message key="feedback.feedbackType${feedbackItem.feedbackType}"/>
			</display:column>
			
		    <display:column property="updateTime" sortable="false" 
		        decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="feedback.updateTime"/>
		    <display:column property="createTime" sortable="false" 
		        decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="feedback.createTime"/>
			<display:column  sortable="false" 
		        decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="feedback.status">
				<fmt:message key="feedback.status${feedbackItem.status}"/>
			</display:column>
			
			<display:column  sortable="false"  decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator"
				titleKey="public.management" media="html">
				<a href="feedbacks.html?doAction=deleteAction&feedbackId=${feedbackItem.feedbackId}" onclick="return confirm('<fmt:message key="feedback.delete.confirm"/>');"><fmt:message key="button.delete"/></a>
			</display:column>
		</display:table>
		<%@include file="/common/pagingOnlyNew.jsp"%>
</form>
<script type="text/javascript">
highlightTableRows("feedbackItem");
</script>