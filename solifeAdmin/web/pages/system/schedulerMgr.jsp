<%@ include file="/common/taglibs.jsp"%>
<html>
    <head>
        <title><fmt:message key="menu.viewSchedulerMgr"/></title>
    </head>
    <body>
    	<content tag="buttons">
			<c:if test="${!scheduler.inStandbyMode and scheduler.started}">
                 <cartmatic:cartmaticBtn btnType="common" inputName="save" commonBtnValueKey="button.stop" onclick="fnDoAction(this.form,'stop');;" />
            </c:if>
            <c:if test="${scheduler.inStandbyMode or !scheduler.started}">
                 <cartmatic:cartmaticBtn btnType="common" inputName="save" commonBtnValueKey="button.start" onclick="fnDoAction(this.form,'run');" />
            </c:if>
		</content>
        <form class="mainForm" method="post">
                <TABLE class="table-list" cellSpacing="0" cellPadding="0" width="100%" border="0">
                    <tr>
                        <td>
                        	<fmt:message key="scheduler.serverStatus"/>:
	                        <c:if test="${!scheduler.inStandbyMode and scheduler.started}">
								<fmt:message key="scheduler.running"/>                        
	                        </c:if>
	                        <c:if test="${scheduler.inStandbyMode or !scheduler.started}">
	                        	<fmt:message key="scheduler.stopped"/> 
	                        </c:if>
                        </td>
                    </tr>
                </table>
                <br />
                <c:forEach begin="0" end="${(groupSize - 1) < 0 ? 0 : (groupSize - 1)}" var="i">
                    <c:set var="groupName_param">groupName${i}</c:set>
                    <c:set var="triggers_param">groupTriggers${i}</c:set>
                    <b><fmt:message key="scheduler.grounpName"/>:${requestScope[groupName_param]}</b>
                    <br />
                    <display:table name="${requestScope[triggers_param]}" cellspacing="0" cellpadding="0" uid="item"
                        class="table-list" export="false" requestURI="">
                        <display:column sortable="false" headerClass="data-table-title" titleKey="scheduler.taskName"
                            decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator">
                            ${item.trigger.name}</display:column>
                        <display:column sortable="false" headerClass="data-table-title" titleKey="scheduler.lastRunTime"
                            decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator">
                            <fmt:formatDate value="${item.trigger.previousFireTime}" pattern="yyyy-MM-dd HH:mm:ss" />&nbsp;</display:column>
                        <display:column sortable="false" headerClass="data-table-title" titleKey="scheduler.nextRunTime"
                            decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator">
                            <fmt:formatDate value="${item.trigger.nextFireTime}" pattern="yyyy-MM-dd HH:mm:ss" />&nbsp;</display:column>
                        <display:column sortable="false" headerClass="data-table-title" titleKey="scheduler.desc" maxLength="60"
                            decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator">
                            ${item.trigger.description}</display:column>
                        <display:column sortable="false" headerClass="data-table-title" titleKey="common.message.status"
                            decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator">
                        <c:if test="${item.status == 0}"><font color="green"><fmt:message key="scheduler.itemStatus0"/></font></c:if>
                        <c:if test="${item.status == 1}"><fmt:message key="scheduler.itemStatus1"/></c:if>
                        <c:if test="${item.status == 2}"><fmt:message key="scheduler.itemStatus2"/></c:if>
                        <c:if test="${item.status == 3}"><font color="red"><fmt:message key="scheduler.itemStatus3"/></font></c:if>
                        <c:if test="${item.status == 4}"><font color="red"><fmt:message key="scheduler.itemStatus4"/></font></c:if>
                        <c:if test="${item.status == -1}"><fmt:message key="scheduler.itemStatus5"/></c:if>
                        </display:column>
                        <display:column sortable="false" headerClass="data-table-title" titleKey="productReview.action"
                            decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator">
                            <c:if test="${item.status != 1}">
                            <a href="<c:url value="/system/schedulers.html"/>?doAction=pausedTrigger&trigger=${item.trigger.name}&group=${requestScope[groupName_param]}"><fmt:message key="scheduler.itemStatus1"/></a></c:if>
                            <c:if test="${item.status == 1}">
                                <a href="<c:url value="/system/schedulers.html"/>?doAction=resumeTrigger&trigger=${item.trigger.name}&group=${requestScope[groupName_param]}"><fmt:message key="scheduler.start"/></a></c:if>
                            |<a href="<c:url value="/system/scheduler_trigger.html"/>?trigger=${item.trigger.name}&group=${requestScope[groupName_param]}"><fmt:message key="common.link.edit"/></a>
                        </display:column>
                    </display:table>
                </c:forEach>
            
            <input type="hidden" name="doAction" value="" />
        </form>
    </body>
</html>
