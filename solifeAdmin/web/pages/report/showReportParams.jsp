<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="report" tagdir="/WEB-INF/tags/report"%>
<c:set var="startDate" value="false"/>
<c:set var="endDate" value="false"/>
<table class="table-content" cellspacing="0" cellpadding="0" width="100%" border="0">
	<tr><th colspan="2"><fmt:message key="report.param.title" /></th></tr>
	<c:forEach var="prm" items="${reportParamList}">
		<c:if test="${prm.inputType!='date' and prm.inputType!='sqlstr'}">
		<tr>
			<td class="FieldLabel"><LABEL title='${prm.helpTips}' ><fmt:message key="report.param.${prm.name}"/></LABEL></td>
			<td>
			<c:if test="${prm.inputType=='checkbox'}">
				<input type="checkbox" name='${prm.name}' value="true" <c:if test="${prm.defaultExp=='Boolean.TRUE'}">checked</c:if>>
			</c:if>
			<c:if test="${prm.inputType=='select'}">
				<SELECT id="${prm.name}" name='${prm.name}'>
					<c:if test="nullAllow"><OPTION value=""></OPTION></c:if>
					<c:forEach var="opt" items="${prm.optionList}">
						<OPTION value='${opt[0]}'>${opt[1]}</OPTION>
					</c:forEach>
				</SELECT>
			</c:if>
			<c:if test="${fn:startsWith(prm.inputType,'input')}">
				<input type="text" name='${prm.name}' class="form-inputbox">
			</c:if>
			<c:if test="${prm.inputType=='mutibox'}">
				<c:forEach var="opt" items="${prm.optionList}">
					<input type="checkbox" name='${prm.name}' value="${opt[0]}">${opt[1]}&nbsp;
				</c:forEach>
			</c:if>
			</td>
		</tr>
		</c:if>
	</c:forEach>
			
	<c:forEach var="prm" items="${reportParamList}">
		<c:if test="${prm.inputType=='date'}">
		<tr>
			<td class="FieldLabel"><LABEL title='${prm.helpTips}' ><fmt:message key="report.param.${prm.name}"/></LABEL></td>
			<td>
				<input type="text" name='${prm.name}' id='${prm.name}' class="form-inputbox" />
				<app:ui_datePicker outPut="${prm.name}"/>
				<c:if test="${fn:containsIgnoreCase(prm.name,'startDate')}">
					<c:set var="startDate" value="true"/>
					<c:set var="startDateName" value="${prm.name}"/>
				</c:if>
				<c:if test="${fn:containsIgnoreCase(prm.name,'endDate') and startDate}">
					<report:dateRange startDateId="${startDateName}" endDateId="${prm.name}" rangeName="dateRange" />
				</c:if>
			</td>
		</tr>
		</c:if>
	</c:forEach>


	<tr>
		<td align="center" colspan="2">
		<INPUT type="hidden" name="_REPORT_FORMAT" id="_REPORT_FORMAT" value=""/>
		<INPUT type="button" onclick="javascript:fnGetReport(this);" value="HTML" class="btn-back">
		<INPUT type="submit" onclick="javascript:fnGetReport(this);"  value="PDF"  class="btn-back">
		<INPUT type="submit" onclick="javascript:fnGetReport(this);" value="EXCEL"  class="btn-back">
		<INPUT type="submit" onclick="javascript:fnGetReport(this);" value="CSV"  style="display:none;" class="btn-back">
		<INPUT type="submit" onclick="javascript:fnGetReport(this);" value="XML" style="display:none;"  class="btn-back">
		<INPUT type="button" onclick="printHtml();" value="Print" class="btn-back">
		</td>
	</tr>
</table>

