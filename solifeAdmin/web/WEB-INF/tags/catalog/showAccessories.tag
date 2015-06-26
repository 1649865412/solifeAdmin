<%@ tag pageEncoding="UTF-8"%><%@ 
	taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@ 
	taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %><%@ 
	taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%@ 
	attribute name="accessories" required="true" rtexprvalue="true" type="java.lang.String" description="accessories info"%>
<%
	if (accessories != null && accessories.length() > 0)
	{
		net.sf.json.JSONObject obj = net.sf.json.JSONObject.fromObject(accessories);
		net.sf.json.JSONArray array = obj.names();
		for (Object name: array)
		{
			String strName = (String) name;
			if (!strName.endsWith("_price"))
			{
		    	out.print(strName+" - "+obj.get(name)+"&nbsp;&nbsp;");
			}				
		}
	}
%>