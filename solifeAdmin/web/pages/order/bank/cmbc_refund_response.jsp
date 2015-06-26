<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.cartmatic.estore.common.model.order.SalesOrder,
com.cartmatic.estore.common.model.system.Store,
com.cartmatic.estore.common.helper.ConfigUtil,
com.cartmatic.estore.common.model.system.PaymentMethod,
java.text.DecimalFormat,
java.text.SimpleDateFormat,
java.net.URLEncoder,
java.util.HashMap,
java.util.Iterator,
java.util.Map,
java.util.Map.Entry,
java.util.Set,
org.apache.commons.lang.StringUtils,
com.cmbc.SignHelper"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%

	//	民生银行 订单查询 返回结果
	String partner_id = request.getParameter("partner_id");
	String orderNo = request.getParameter("out_trade_no");
	String date = request.getParameter("process_date");
	String isSuccess = request.getParameter("is_success");
	String amount = request.getParameter("total_fee");

%>
<html>
	<head>
		<script type="text/javascript">
		</script>
	</head>
	<body>

	<table>
		<tr>
			<td>交易订单号：</td>
			<td>商户号：</td>
			<td>交易结果：</td>
			<td>交易时间：</td>
			<td>交易金额：</td>
		</tr>
		<tr>
			<td><%=orderNo %></td>
			<td><%=partner_id %></td>
			<td><%=(isSuccess=='T') ? '退款成功' : '退款失败' %></td>
			<td><%=date %></td>
			<td><%=amount %></td>
		</tr>
	</table>
    
	</body>
</html>