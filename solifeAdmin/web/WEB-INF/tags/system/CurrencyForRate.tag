<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="value" required="true"%>
<%@ attribute name="currencyCode" required="false"%>
<fmt:formatNumber type="currency" value="${value}" currencySymbol="${appConfig.defaultCurrencySymbol}"/>