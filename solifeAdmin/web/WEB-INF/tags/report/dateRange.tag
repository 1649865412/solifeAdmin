
<%@tag import="com.cartmatic.estore.common.util.DateUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ tag import="java.util.Date" %>
<%@ attribute name="startDateId" %>
<%@ attribute name="endDateId" %>
<%@ attribute name="rangeType" %>
<%@ attribute name="rangeName" %>

<select class="form-inputbox" name="${rangeName}" id="${rangeName}" onchange='showDateRange( this.options[ this.selectedIndex ].value )'>
<option selected value='custom' <c:if test="${empty rangeType or rangeType =='custom'}">selected</c:if> ><fmt:message key="report.range.custom"/></option>
<option value='prevfy' <c:if test="${rangeType =='prevfy'}">selected</c:if>><fmt:message key="report.range.prevfy"/></option>
<option value='thisfy' <c:if test="${rangeType =='thisfy'}">selected</c:if>><fmt:message key="report.range.thisfy"/></option>
<option value='yesterday' <c:if test="${rangeType =='yesterday'}">selected</c:if>><fmt:message key="report.range.yesterday"/></option>
<option value='today' <c:if test="${rangeType =='today'}">selected</c:if>><fmt:message key="report.range.today"/></option>
<option value='lastweek' <c:if test="${rangeType =='lastweek'}">selected</c:if>><fmt:message key="report.range.lastweek"/></option>
<option value='thisweek' <c:if test="${rangeType =='thisweek'}">selected</c:if>><fmt:message key="report.range.thisweek"/></option>
<option value='lastmonth' <c:if test="${rangeType =='lastmonth'}">selected</c:if>><fmt:message key="report.range.lastmonth"/></option>
<option value='thismonth' <c:if test="${rangeType =='thismonth'}">selected</c:if>><fmt:message key="report.range.thismonth"/></option>
<option value='last7days' <c:if test="${rangeType =='last7days'}">selected</c:if>><fmt:message key="report.range.last7days"/></option>
<option value='last30days' <c:if test="${rangeType =='last30days'}">selected</c:if>><fmt:message key="report.range.last30days"/></option>
<option value='last60days' <c:if test="${rangeType =='last60days'}">selected</c:if>><fmt:message key="report.range.last60days"/></option>
<%--
<option value='nextfy' <c:if test="${rangeType =='nextfy'}">selected</c:if>><fmt:message key="report.range.nextfy"/></option>
<option value='tomorrow' <c:if test="${rangeType =='tomorrow'}">selected</c:if>><fmt:message key="report.range.tomorrow"/></option>
<option value='nextweek' <c:if test="${rangeType =='nextweek'}">selected</c:if>><fmt:message key="report.range.nextweek"/></option>
<option value='nextmonth' <c:if test="${rangeType =='nextmonth'}">selected</c:if>><fmt:message key="report.range.nextmonth"/></option>
<option value='next7days' <c:if test="${rangeType =='next7days'}">selected</c:if>><fmt:message key="report.range.next7days"/></option>
<option value='next30days' <c:if test="${rangeType =='next30days'}">selected</c:if>><fmt:message key="report.range.next30days"/></option>
<option value='next60days' <c:if test="${rangeType =='next60days'}">selected</c:if>><fmt:message key="report.range.next60days"/></option>
--%>
</select>

<script language="JavaScript" type="text/javaScript">
function showDateRange( type )
{
	var startDate = document.getElementById("${startDateId}");
	var endDate = document.getElementById("${endDateId}");
	if (type!="custom")
	{
		startDate.readOnly=true;
		endDate.readOnly=true;
	}
	else
	{
		startDate.readOnly=false;
		endDate.readOnly=false;
	}
	if( type == "today" )
	{
		startDate.value = "<%=DateUtil.convertDateToString(new Date())%>";
		endDate.value = "<%=DateUtil.convertDateToString(new Date())%>";
	}
	else if( type == "yesterday" )
	{
		startDate.value = "<%=DateUtil.convertDateToString(DateUtil.getYesterday())%>";
		endDate.value = "<%=DateUtil.convertDateToString(DateUtil.getYesterday())%>";
	}
	else if( type == "tomorrow" )
	{
		startDate.value = "<%=DateUtil.convertDateToString(DateUtil.getTomorrow())%>";
		endDate.value = "<%=DateUtil.convertDateToString(DateUtil.getTomorrow())%>";
	}
	else if( type == "thisweek" )
	{
		startDate.value = "<%=DateUtil.convertDateToString(DateUtil.getStartOfThisWeek())%>";
		endDate.value = "<%=DateUtil.convertDateToString(DateUtil.getEndOfThisWeek())%>";
	}
	else if( type == "lastweek" )
	{
		startDate.value = "<%=DateUtil.convertDateToString(DateUtil.getStartOfLastWeek())%>";
		endDate.value = "<%=DateUtil.convertDateToString(DateUtil.getEndOfLastWeek())%>";
	}
	else if( type == "nextweek" )
	{
		startDate.value = "<%=DateUtil.convertDateToString(DateUtil.getStartOfNextWeek())%>";
		endDate.value = "<%=DateUtil.convertDateToString(DateUtil.getEndOfNextWeek())%>";
	}
	else if( type == "thismonth" )
	{
		startDate.value = "<%=DateUtil.convertDateToString(DateUtil.getStartOfThisMonth())%>";
		endDate.value = "<%=DateUtil.convertDateToString(DateUtil.getEndOfThisMonth())%>";
	}
	else if( type == "lastmonth" )
	{
		startDate.value = "<%=DateUtil.convertDateToString(DateUtil.getStartOfLastMonth())%>";
		endDate.value = "<%=DateUtil.convertDateToString(DateUtil.getEndOfLastMonth())%>";
	}
	else if( type == "nextmonth" )
	{
		startDate.value = "<%=DateUtil.convertDateToString(DateUtil.getStartOfNextMonth())%>";
		endDate.value = "<%=DateUtil.convertDateToString(DateUtil.getEndOfNextMonth())%>";
	}
	else if( type == "next7days" )
	{
		startDate.value = "<%=DateUtil.convertDateToString(DateUtil.getAddDay(new Date(),1))%>";
		endDate.value = "<%=DateUtil.convertDateToString(DateUtil.getEndOfNext7days())%>";
	}
	else if( type == "next30days" )
	{
		startDate.value = "<%=DateUtil.convertDateToString(DateUtil.getAddDay(new Date(),1))%>";
		endDate.value = "<%=DateUtil.convertDateToString(DateUtil.getEndOfNext30days())%>";
	}
	else if( type == "next60days" )
	{
		startDate.value = "<%=DateUtil.convertDateToString(DateUtil.getAddDay(new Date(),1))%>";
		endDate.value = "<%=DateUtil.convertDateToString(DateUtil.getEndOfNext60days())%>";
	}
	else if( type == "last7days" )
	{
		startDate.value = "<%=DateUtil.convertDateToString(DateUtil.getStartOfLast7days())%>";
		endDate.value =  "<%=DateUtil.convertDateToString(new Date())%>";
	}
	else if( type == "last30days" )
	{
		startDate.value = "<%=DateUtil.convertDateToString(DateUtil.getStartOfLast30days())%>";
		endDate.value = "<%=DateUtil.convertDateToString(new Date())%>";
	}
	else if( type == "last60days" )
	{
		startDate.value = "<%=DateUtil.convertDateToString(DateUtil.getStartOfLast60days())%>";
		endDate.value = "<%=DateUtil.convertDateToString(new Date())%>";
	}
	else if( type == "thisfy" )
	{
		startDate.value = "<%=DateUtil.convertDateToString(DateUtil.getStartOfThisYear())%>";
		endDate.value = "<%=DateUtil.convertDateToString(DateUtil.getEndOfThisYear())%>";
	}
	else if( type == "prevfy" )
	{
		startDate.value = "<%=DateUtil.convertDateToString(DateUtil.getStartOfLastYear())%>";
		endDate.value = "<%=DateUtil.convertDateToString(DateUtil.getEndOfLastYear())%>";
	}
	else if( type == "nextfy" )
	{
		startDate.value = "<%=DateUtil.convertDateToString(DateUtil.getStartOfNextYear())%>";
		endDate.value = "<%=DateUtil.convertDateToString(DateUtil.getEndOfNextYear())%>";
	}
	else
	{
		startDate.value = "";
		endDate.value = "";
	}
}
//applyDatePicker("${startDateId}",true);
//applyDatePicker("${endDateId}",true);
//run the showDateRange
showDateRange(document.getElementById("${rangeName}").value);
</script>