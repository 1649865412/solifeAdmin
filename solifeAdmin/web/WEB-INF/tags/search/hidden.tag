<%@ tag body-content="empty"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ tag import="java.util.*"%>
<%@ tag import="java.lang.reflect.*"%>
<%@ attribute name="attrPath" required="true" rtexprvalue="false" type="java.lang.String" description="attribute filter on"%>
<%@ attribute name="datatype" required="true" rtexprvalue="false" type="java.lang.String" description="supported datatype:String,Integer,Short,Date"%>
<%@ attribute name="operator" required="true" rtexprvalue="false" type="java.lang.String" description="supported operator:LIKE,LIKEEND,LIKEBEFORE,NOT,GTE,GT,LTE,LT,I18N"%>
<c:set var="attrName">COL@${attrPath}@${datatype}@${operator}</c:set>
<input type="hidden" id="${attrName}" name="${attrName}" value="${sc==null ? requestScope[attrName] : sc.param[attrName]}" />