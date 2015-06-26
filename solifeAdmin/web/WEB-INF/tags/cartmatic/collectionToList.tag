
<%@tag import="java.util.List"%>
<%@tag import="java.util.ArrayList"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@tag import="java.util.Collections"%><%@ attribute name="itmes" type="java.util.Collection" required="true"%>
<%@attribute name="var" type="java.lang.String" required="true"%>
<%@attribute name="size" type="java.lang.Integer" description="指定List大小，当数据集合少于size的，补上相应属性的null"%>
<%
List<Object> objs = null;
if (itmes != null) {
	objs = new ArrayList<Object>();
	for (Object item : itmes) {
		objs.add(item);
	}
	if(size!=null&&size.intValue()>objs.size()){
		int addNullCount=size.intValue()-objs.size();
		for(int i=0;i<addNullCount;i++){
			objs.add(null);
		}
	}
}
request.setAttribute(var, objs);
%>