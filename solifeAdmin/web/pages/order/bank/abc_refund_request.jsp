<%
/*
 * @(#)MerchantOvrdueRefund.jsp	V1.0	2009/05/12
 *
 *
 * 
*/
%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import = "com.cartmatic.estore.common.model.order.SalesOrder"%>
<%@ page import = "com.hitrust.trustpay.client.b2c.*" %>
<%@ page import = "com.hitrust.trustpay.client.*" %>
<%@ page import = "java.util.ArrayList"%>

<% response.setHeader("Cache-Control", "no-cache"); %>
<HTML>
<HEAD><TITLE>农行网上支付平台-商户接口范例-批量退款</TITLE></HEAD>
<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
<CENTER>超期退款<br>
<%
//1、取得超期退款所需要的信息
SalesOrder order = (SalesOrder)request.getAttribute("salesOrder");
String payAmount = request.getAttribute("payAmount");
 String orderno_arr[] = null ;
 String orderamount_arr[] = null ;
  int iTotalCount    = 1;
  double  iTotalAmount   = order.getPaidAmount().doubleValue();

  ArrayList tOrderList = new ArrayList() ;
  
  if(iTotalCount == 1) {
    String orderno = order.getOrderNo();
	String orderamount = payAmount ;
	orderno_arr = new String[]{orderno} ;
	orderamount_arr = new String[]{orderamount} ;
	String [] torder = new String[2];
    torder[0] = orderno_arr[0];
    torder[1] = orderamount_arr[0];
    tOrderList.add(torder);
   }
   
   

 //2、生成超期退款请求对象
 OverdueRefundRequest tOverdueRequest = new OverdueRefundRequest();
 tOverdueRequest.setTotalCount  (iTotalCount  );  //总笔数  （必要信息）
 tOverdueRequest.setTotalAmount(iTotalAmount);  //总金额 （必要信息）
 tOverdueRequest.setRemark("订单退款");//备注
 tOverdueRequest.setOrderDital(tOrderList);

 //3、传送超期退款请求并取得结果
 TrxResponse tResponse = tOverdueRequest.postRequest();

//4、判断超期退款结果状态，进行后续操作
if (tResponse.isSuccess()) {
  //5、超期退款成功
  
  out.println("TrxType   = [" + tResponse.getValue("TrxType"  ) + "]<br>");
  out.println("TotalCount  = [" + tResponse.getValue("TotalCount"  ) + "]<br>");
  out.println("TotalAmount = [" + tResponse.getValue("TotalAmount") + "]<br>");
  out.println("SerialNumber  = [" + tResponse.getValue("SerialNumber"  ) + "]<br>");
  out.println("HostDate  = [" + tResponse.getValue("HostDate" ) + "]<br>");
  out.println("HostTime  = [" + tResponse.getValue("HostTime" ) + "]<br>");
  out.println("ResultMessage   = [" + tResponse.getErrorMessage() + "]<br>");
}
else {
  //6超期退款失败
  out.println("ReturnCode   = [" + tResponse.getReturnCode  () + "]<br>");
  out.println("ErrorMessage = [" + tResponse.getErrorMessage() + "]<br>");
}
%>
</BODY></HTML>