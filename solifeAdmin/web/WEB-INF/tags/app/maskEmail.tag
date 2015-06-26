<%@tag import="com.cartmatic.estore.common.model.system.AppUser"%><%@
	taglib prefix="sec" uri="http://www.springframework.org/security/tags" %><%@ 
	taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@ 
	attribute name="email" required="true" type="java.lang.String"%>
   
  
<%
	if (com.cartmatic.estore.webapp.util.RequestContext.authorizeUsingUrlCheck("GRANT_EMAIL"))
		out.write(email);
	else
	{
		StringBuffer sb = new StringBuffer();
		for (int i =0; i < email.length(); i++)
		{
			if (i == 0)
			{
				sb.append(email.charAt(i));
				sb.append("***");
			}
			else if (i == email.length()  -1 )
				sb.append(email.charAt(i));
		}
		out.write(sb.toString());
	}
%>