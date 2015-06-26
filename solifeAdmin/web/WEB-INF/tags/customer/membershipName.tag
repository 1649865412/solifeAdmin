
<%@tag import="com.cartmatic.estore.common.model.customer.Membership"%>
<%@tag import="com.cartmatic.estore.core.util.ContextUtil"%>
<%@tag import="com.cartmatic.estore.customer.service.MembershipManager"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="membershipId" %>
<%
		MembershipManager membershipManager=(MembershipManager)ContextUtil.getSpringBeanById("membershipManager");
		try{
			Membership membership=null;
			if(membershipId==null){
				membership=membershipManager.getAnonymousMembership();
			}else{
				membership=membershipManager.getById(new Integer(membershipId));				
			}
			request.setAttribute("membership",membership);
		}catch(Exception ex){}
%>
${membership.membershipName}