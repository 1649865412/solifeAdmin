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
<%@page import="com.cartmatic.estore.system.service.PaymentMethodManager"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%

	//民生银行	退款请求
	String baseServiceUrl = "https://epay.cmbc.com.cn/ipad/service.html";
	
	Store store = ConfigUtil.getInstance().getStore();
	SalesOrder salesOrder=(SalesOrder)request.getAttribute("salesOrder");
	
	WebApplicationContext webAppContext=WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
	PaymentMethodManager paymentMethodManager=(PaymentMethodManager)webAppContext.getBean("paymentMethodManager");
	PaymentMethod paymentMethod=paymentMethodManager.getPaymentMethodByCode(request.getAttribute("paymentMethodCode"));
	
	//1. 参数准备
	String service = "refund_goods_by_buyer";
	String partner_id = paymentMethod.getConfigData().get("partner_id"); //商户机构号 1002201305272380
	String input_charset = "utf-8";
	String sign_type = "MD5";
	String out_trade_no = salesOrder.getOrderNo();
	String amount = request.getAttribute("payAmount");
	String refund_priv_no = salesOrder.getOrderNo();
	String return_url = store.getSiteUrl()+"/order/bank/cmbc_refund_response.html";
	
	//请联系客户经理索要商户签约密钥key
	String key = paymentMethod.getConfigData().get("key");
	
	String sign = "";

	Map<String, String> map = new HashMap<String, String>();
	map.put("partner_id", partner_id);
	map.put("return_url", return_url);
	map.put("input_charset", input_charset);
	map.put("out_trade_no", out_trade_no);
	map.put("refund_priv_no", refund_priv_no);
	map.put("service", service);
	map.put("amount", amount);
	map.put("sign_type", sign_type);
	
	//2. 获得签名字符串
	if (StringUtils.isNotEmpty(key)) {
		sign = SignHelper.sign(map, key);
		map.put("sign", sign);
	}else{
		System.out.println("key不能为空");
		return;
	}
	
	System.out.println("签名结果为：" + sign);
	
	//3.生成支付serviceUrl
	Set entrySet = map.entrySet();
	Iterator it = entrySet.iterator();
	StringBuffer tailString = new StringBuffer();
	while(it.hasNext()){
		Entry<String, String> entry = (Entry<String, String>)it.next();
		tailString.append(entry.getKey());
		tailString.append("=");
		tailString.append(URLEncoder.encode(entry.getValue()));
		tailString.append("&");
	}
	
	tailString.deleteCharAt(tailString.length() - 1);//去掉最后一个&符号	
	String serviceUrl = baseServiceUrl + "?" + tailString.toString();
	System.out.println("订单退款URL:" + serviceUrl);

%>
<html>
	<head>
		<script type="text/javascript">
			window.onload=function(){
				document.forms["paymentGatewayForm"].submit();
			}
		</script>
	</head>
	<body>

	<form name="paymentGatewayForm" action="<%=serviceUrl %>" method="post">
    </form>
    
	</body>
</html>