<%@ include file="/common/taglibs.jsp"%>
<title><fmt:message key="menu.systemInfo"/></title>
<table cellspacing="0" cellpadding="0" id="loggerItem" class="table-list">
	<tr>
		<th colspan="2" class="data-table-title">
			Java System Properties
		</th>
	</tr>
	<%
	    java.util.Properties p = System.getProperties();
	    java.util.Set set = p.keySet();
	    for (Object a : set)
	    {
	        out.print("<tr><td>"+a + "</td><td>" + p.getProperty((String) a)+"</td></tr>");
	        
	    }
	%>
</table>
