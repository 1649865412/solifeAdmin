<%@ page language="java" errorPage="/error.jsp" contentType="text/html; charset=UTF-8" isELIgnored="false" buffer="24kb" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%><%@ 
	taglib uri="http://www.springframework.org/tags" prefix="spring" %><%@ 
	taglib prefix="sec" uri="http://www.springframework.org/security/tags" %><%@ 
	taglib uri="http://displaytag.sf.net" prefix="display" %><%@ 
	taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@ 
	taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %><%@ 
	taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%@ 
	taglib uri="/WEB-INF/StoreAdmin.tld" prefix="StoreAdmin" %><%@ 
	taglib uri="/WEB-INF/StoreAdmin.tld" prefix="v" %><%@ 
	taglib uri="/WEB-INF/I18n.tld" prefix="i18n" %><%@ 
	taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%><%@ 
	taglib prefix="app" tagdir="/WEB-INF/tags/app"%><%@ 
	taglib prefix="search" tagdir="/WEB-INF/tags/search"%><%@ 
	taglib prefix="system" tagdir="/WEB-INF/tags/system"%><%@ 
	taglib prefix="attribute" tagdir="/WEB-INF/tags/attribute"%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" scope="application"/>
<c:set var="mediaPath" value="${ctxPath}/media/" scope="request"/>
<c:set var="emptySpace" value=" " scope="request"/>