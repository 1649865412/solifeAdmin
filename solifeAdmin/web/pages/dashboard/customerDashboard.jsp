<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="customer" tagdir="/WEB-INF/tags/customer"%>
<div class="list">
   <h2><fmt:message key="dashboard.customer.title" /></h2>
   <div class="blank10"></div>
&nbsp;&nbsp;&nbsp;最近7日的用户走注册势:
<div id="last7DayCustomerRegisterChart"></div>
&nbsp;&nbsp;&nbsp;用户情况:
<ul>
	<li>
		激活：
		<span class="num">${customerTotal_ACTIVE}</span>
	</li>
	<li>
		未激活：
		<span class="num">${customerTotal_INACTIVE}</span>
	</li>
	<c:forEach items="${customerMembershipList}" var="customerMemberships" varStatus="varStatus">
	<li>
		<customer:membershipName membershipId="${customerMemberships[1] }"></customer:membershipName>：
		<span class="num">${customerMemberships[0] }</span>
	</li>
	</c:forEach>
</ul>
</div>