<%
/*
 * @(#)MerchantTrxSettle.jsp	V1.5.1	2004/11/10
 *
 * Project: BJP03004
 *
 * Description:
 *    商户交易对账单下载范例程序。
 *
 * Modify Information:
 *    Author        Date        Description
 *    ============  ==========  =======================================
 *    HiTRUST       2004/01/05  V1.0 Release
 *    HiTRUST       2004/01/07  V1.0.1 Release
 *    HiTRUST       2004/04/16  V1.0.3 Release
 *    HiTRUST       2004/06/01  V1.0.4 Release
 *    HiTRUST       2004/08/30  V1.2 Release
 *    HiTRUST       2004/09/27  V1.5 Release
 *    HiTRUST       2004/11/10  V1.5.1 Release
 *
 * Copyright Notice:
 *   Copyright (c) 2001-2004 Beijing HiTRUST Technology Co., Ltd.
 *   1808 Room, Science & Technology Building, No. 9 South Street,
 *   Zhong Guan Cun, Haidian District, Beijing ,100081, China
 *   All rights reserved.
 *
 *   This software is the confidential and proprietary information of
 *   HiTRUST.COM, Inc. ("Confidential Information"). You shall not
 *   disclose such Confidential Information and shall use it only in
 *   accordance with the terms of the license agreement you entered
 *   into with HiTRUST.
 */
%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import = "com.hitrust.trustpay.client.b2c.*" %>
<%@ page import = "com.hitrust.trustpay.client.*" %>
<%@ page import = "java.text.NumberFormat"%>
<% response.setHeader("Cache-Control", "no-cache"); %>
<HTML>
<HEAD><TITLE>农行网上支付平台-商户接口范例-交易对账单下载</TITLE></HEAD>
<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
<CENTER>交易对账单下载<br>
<%

NumberFormat tFormat = NumberFormat.getInstance();
tFormat.setMaximumFractionDigits(2);
tFormat.setGroupingUsed(false);
tFormat.setMinimumFractionDigits(2);

//1、取得商户对账单下载所需要的信息
String tSettleDate = "2014/05/28";//request.getParameter("SettleDate");
    
//2、生成商户对账单下载请求对象
SettleRequest tRequest = new SettleRequest();
tRequest.setSettleDate(tSettleDate);               //对账日期YYYY/MM/DD （必要信息）
tRequest.setSettleType(SettleFile.SETTLE_TYPE_TRX);//对账类型 （必要信息）
                     //SettleFile.SETTLE_TYPE_TRX：交易对账单

//3、传送商户对账单下载请求并取得对账单
TrxResponse tResponse = tRequest.postRequest();

//4、判断商户对账单下载结果状态，进行后续操作
if (tResponse.isSuccess()) {
  //5、商户对账单下载成功，生成对账单对象
  SettleFile tSettleFile = new SettleFile(tResponse);
  out.println("SettleDate        = [" + tSettleFile.getSettleDate       () + "]<br>");
  out.println("SettleType        = [" + tSettleFile.getSettleType       () + "]<br>");
  out.println("NumOfPayments     = [" + tSettleFile.getNumOfPayments    () + "]<br>");
  out.println("SumOfPayAmount    = [" + tFormat.format(tSettleFile.getSumOfPayAmount   ()) + "]<br>");
  out.println("NumOfRefunds      = [" + tSettleFile.getNumOfRefunds     () + "]<br>");
  out.println("SumOfRefundAmount = [" + tSettleFile.getSumOfRefundAmount() + "]<br>");

  //6、取得对账单明细
  String[] tRecords = tSettleFile.getDetailRecords();
  for(int i = 0; i < tRecords.length; i++) {
    out.println("Record-" + i + " = [" + tRecords[i] + "]<br>");
  }
}
else {
  //7、商户账单下载失败
  out.println("ReturnCode   = [" + tResponse.getReturnCode  () + "]<br>");
  out.println("ErrorMessage = [" + tResponse.getErrorMessage() + "]<br>");
}
%>
</BODY></HTML>