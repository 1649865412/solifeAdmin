<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ tag import="java.util.*"%>
<%@ tag import="java.lang.reflect.*"%>
<%@ attribute name="attrPath" required="true" rtexprvalue="false" type="java.lang.String"
	description="attribute filter on"%>
<%@ attribute name="attrOptions" required="false" rtexprvalue="true" type="java.lang.Object"
	description="attribute value"%>
<%@ attribute name="datatype" required="true" rtexprvalue="false" type="java.lang.String"
	description="supported datatype:String,Integer,Short,Date"%>
<%@ attribute name="operator" required="true" rtexprvalue="false" type="java.lang.String"
	description="supported operator:LIKE,NOT,GTE,GT,LTE,LT,I18N"%>
<%@ attribute name="style" required="false" rtexprvalue="false" type="java.lang.String" description="style"%>
<%@ attribute name="selectKey" required="false" type="java.lang.String" description="select value"%>
<%@ attribute name="selectName" required="false" type="java.lang.String" description="select name"%>
<%@ attribute name="isI18n" required="false" type="java.lang.Boolean" description="select name need i18n"%>
<c:set var="attrName">COL@${attrPath}@${datatype}@${operator}</c:set>
<c:if test="${not empty attrOptions}">
	<%
		List list = (ArrayList) attrOptions;
		List results = new ArrayList();
		for (int i = 0; i < list.size(); i++) {

			Object t = list.get(i);
			try {
				String selectKeyM = "get" + selectKey;
				String selectNameM = "get" + selectName;

				Method[] ms = t.getClass().getMethods();
				Method methodK = null;
				Method methodV = null;
				for (int k = 0; k < ms.length; k++) {
			if (ms[k].getName().toLowerCase().equals(selectKeyM.toLowerCase())) {
				methodK = ms[k];

			}
			if (ms[k].getName().toLowerCase().equals(selectNameM.toLowerCase())) {
				methodV = ms[k];
			}
				}
				Object obj = methodK.invoke(t, null);
				Object obj2 = methodV.invoke(t, null);
				Hashtable h = new Hashtable();
				h.put("key", obj);
				h.put("value", obj2);
				results.add(h);

			} catch (Exception e) {
				out.println(e.toString());
			}
			request.setAttribute("CartmaticListProc", results);

		}
	%>
</c:if>
<select name="${attrName}" id="${attrName}" <c:if test="${not empty style}">class="${style}"</c:if>>
	<c:forEach var="item" items="${CartmaticListProc}">
		<option value="${item.key}" ${(sc==null ? (item.key==requestScope[attrName]) : (item.key==sc.param[attrName])) ? 'selected' : ''}>
	      ${item.value}
		</option>
	</c:forEach>
</select>
