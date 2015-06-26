<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title>Log4j Manager</title>
    </head>
    <body>
		<content tag="buttons">
			<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave()" />
			<fmt:message key="log4jMgr.confirm.reloadAll" var="confirmReloadAllMsg"/>
			<cartmatic:cartmaticBtn btnType="reloadAll" onclick="fnDoAction(this,'resetAll','${confirmReloadAllMsg}')" />
			<cartmatic:cartmaticBtn btnType="download" onclick="fnDoAction(this,'downloadLog')" />
		</content>
		<form class="mainForm" method="post">
            <c:if test="${not empty extraLoggerList}">
               <hr />
				<display:table name="${extraLoggerList}" cellspacing="0" cellpadding="0" uid="loggerItem" class="table-list" export="false" requestURI="">
					<!--checkall column ycm 2006-04-28-->
					<display:column style="width: 5" title="${checkAll}" headerClass="data-table-title" media="html">&nbsp;</display:column>
					<!--checkall column ycm 2006-04-28 end-->
					<display:column sortable="false" headerClass="data-table-title" title="Pattern" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator">${loggerItem}
					</display:column>
					<display:column sortable="false" headerClass="data-table-title" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" title="Current Level">
						<select name="extra_${loggerItem}" id="extra_${loggerItem}">
							<option value="DEBUG" <c:if test="${applicationScope.extra_log4j_config[loggerItem] == 'DEBUG'}">selected</c:if>>
								DEBUG
							</option>
							<option value="INFO" <c:if test="${applicationScope.extra_log4j_config[loggerItem] == 'INFO'}">selected</c:if>>
								INFO
							</option>
							<option value="WARN" <c:if test="${applicationScope.extra_log4j_config[loggerItem] == 'WARN'}">selected</c:if>>
								WARN
							</option>
							<option value="ERROR" <c:if test="${applicationScope.extra_log4j_config[loggerItem] == 'ERROR'}">selected</c:if>>
								ERROR
							</option>
							<option value="FATAL" <c:if test="${applicationScope.extra_log4j_config[loggerItem] == 'FATAL'}">selected</c:if>>
								FATAL
							</option>
							<option value="OFF" <c:if test="${applicationScope.extra_log4j_config[loggerItem] == 'OFF'}">selected</c:if>>
								OFF
							</option>
						</select>
					</display:column>
				</display:table>
			</c:if>
			<table class="table-list" cellSpacing="0" cellPadding="0" width="100%" border="0">
				<tr>
					<td>
						&nbsp;Add a temp log4j Pattern
						<input type="text" name="newPattern" id="newPattern" class="Field400" />
						&nbsp;&nbsp; Level
						<select name="newLevel" id="newLevel" class="Field400">
							<option value="DEBUG" selected>
								DEBUG
							</option>
							<option value="INFO">
								INFO
							</option>
							<option value="WARN">
								WARN
							</option>
							<option value="ERROR">
								ERROR
							</option>
							<option value="FATAL">
								FATAL
							</option>
							<option value="OFF">
								OFF
							</option>
						</select>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<fmt:message key="log4jMgr.confirm.addExtraConfig" var="confirmAddExtraConfigMsg"/>
						<fmt:message key="log4jMgr.confirm.mutilSaveExtraConfig" var="confirmMutilSaveExtraConfigMsg"/>
						<cartmatic:iconBtn icon="plus" textKey="common.link.add" onclick="fnDoAction(this,'addExtraConfig','${confirmAddExtraConfigMsg}'.replace('{0}',$j('#newPattern').val()));doAction_old('addExtraConfig');" />
						<c:if test="${not empty extraLoggerList}">
							<cartmatic:iconBtn icon="pen" textKey="log4jMgr.updateTemp" onclick="fnDoAction(this,'mutilSaveExtraConfig','${confirmMutilSaveExtraConfigMsg}');" />
						</c:if>
					</td>
				</tr>
			</table>
			<hr />
            <div id="list-btn-wrap" class="alignleft">
                <c:out value="${buttons}" escapeXml="false" />
            </div>
			<display:table name="${loggerList}" cellspacing="0" cellpadding="0" uid="loggerItem" class="table-list" export="false" requestURI="">
				<display:column style="width: 5" title="${checkAll}" headerClass="data-table-title" media="html">&nbsp;</display:column>
				<display:column sortable="false" headerClass="data-table-title" title="Pattern" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator">
			       ${loggerItem}
				</display:column>
				<display:column sortable="false" headerClass="data-table-title" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" title="Original Setting">
					${configProp[loggerItem]}
				</display:column>
				<display:column sortable="false" headerClass="data-table-title" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" title="Current Level">
					<select name="${loggerItem}" id="${loggerItem}">
						<option value="DEBUG" <c:if test="${currentProp[loggerItem] == 'DEBUG'}">selected</c:if>>
							DEBUG
						</option>
						<option value="INFO" <c:if test="${currentProp[loggerItem] == 'INFO'}">selected</c:if>>
							INFO
						</option>
						<option value="WARN" <c:if test="${currentProp[loggerItem] == 'WARN'}">selected</c:if>>
							WARN
						</option>
						<option value="ERROR" <c:if test="${currentProp[loggerItem] == 'ERROR'}">selected</c:if>>
							ERROR
						</option>
						<option value="FATAL" <c:if test="${currentProp[loggerItem] == 'FATAL'}">selected</c:if>>
							FATAL
						</option>
						<option value="OFF" <c:if test="${currentProp[loggerItem] == 'OFF'}">selected</c:if>>
							OFF
						</option>
					</select>
				</display:column>
			</display:table>
			<input type="hidden" name="doAction" value="" />
        </form>
    </body>
</html>
