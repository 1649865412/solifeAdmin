<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="customer" tagdir="/WEB-INF/tags/customer"%>
<app:pageHeading entityName="${customer.username}" entityHeadingKey="customerDetail.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this);" />
    <c:if test="${customer.customerId!=null}">
		<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoDelete(this);" />
	</c:if>
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="customer.*" />
<c:if test="${not empty customer.appuserId}">
</c:if>
<div class="tab" id="customerTab">
    <ul class="sub-tab">
	    <li><a href="#customerInfo"><fmt:message key="customer.tab.customerInfo" /></a></li>
	    <li><a href="#orderInfo"><fmt:message key="customer.tab.order" /></a></li>
		<li><a href="#addressInfo"><fmt:message key="customer.tab.address" /></a></li>
		<li><a href="#wishlistInfo"><fmt:message key="customer.tab.wishlist" /></a></li>
		<li><a href="#shopPointInfo"><fmt:message key="customer.tab.shopPoint" /></a></li>
		<li><a href="#couponInfo">优惠劵</a></li>
	</ul>
	<div class="blank10"></div>
	<div id="customerInfo">
		<%@ include file="include/customerInfo.jsp"%>
	</div>
	<div id="orderInfo">
	</div>
	<div id="addressInfo">
	</div>
	<div id="wishlistInfo">
	</div>			
	<div id="shopPointInfo">
	</div>
	<div id="couponInfo">
		<%@ include file="couponList.jsp"%>
	</div>
</div>
<app:ui_tabs tabsId="customerTab" type="2"/>
<c:if test="${not empty customer.appuserId}">
<script type="text/javascript" defer>
fnRefurbishOrderInfo();
fnRefurbishAddressInfo();
fnRefurbishWishlistInfo();
fnRefurbishShopPointy();

function fnInitAddShopPointForm(){
}

function fnAddShopPoint(){
	var form = $j("#shopPointForm");
	var paramData = form.serializeArray();
	$j.post("${ctxPath}/customer/shopPointHistory.html?doAction=save", paramData, fnSaveSkuOptionHandler,"json");
}

function fnSaveSkuOptionHandler(result) {
	if(result.status==1){
		dlgAddShopPoint_close();
		fnRefurbishShopPointy();
	}
	fnSaveCallbackShowError(result);
	if (result.status == 1) {
		sysMsg(result.msg,result.status!=1);
	}
}

function fnRefurbishShopPointy(paraData){
	if(!paraData){
		paraData=$j("#shopPointInfo :input").serializeArray();
	}
	fillDivWithPage($j("#shopPointInfo"),"${ctxPath}/customer/shopPointHistory.html?customerId=${customer.appuserId}&decorator=selecter&pagingId=shopPoint",paraData);
}
function fnRefurbishWishlistInfo(paraData){
	if(!paraData){
		paraData=$j("#wishlistInfo :input").serializeArray();
	}
	fillDivWithPage($j("#wishlistInfo"),"${ctxPath}/customer/wishlist.html?customerId=${customer.appuserId}&decorator=selecter&pagingId=wishlist",paraData);
}
function fnRefurbishAddressInfo(paraData){
	if(!paraData){
		paraData=$j("#addressInfo :input").serializeArray();
	}
	fillDivWithPage($j("#addressInfo"),"${ctxPath}/customer/address.html?customerId=${customer.appuserId}&decorator=selecter&pagingId=address",paraData);
}
function fnRefurbishOrderInfo(paraData){
	if(!paraData){
		paraData=$j("#orderInfo :input").serializeArray();
	}
	fillDivWithPage($j("#orderInfo"),"${ctxPath}/customer/customer.html?doAction=viewCustomerOrder&customerId=${customer.appuserId}&decorator=selecter&pagingId=order",paraData);
}

function fnOnGoToPageorder(){
	fnRefurbishOrderInfo();
}
function fnOnGoToPageaddress(){
	fnRefurbishAddressInfo();
}
function fnOnGoToPagewishlist(){
	fnRefurbishWishlistInfo();
}
function fnOnGoToPageshopPoint(){
	fnRefurbishShopPointy();
}
</script>
<fmt:message key="shopPointHistory.addShopPoint.title" var="addShopPointTitle" />
<fmt:message key="button.save" var="buttonSave" />
<app:ui_dialog id="AddShopPoint" title="${addShopPointTitle}" width="580" height="470" url="${ctxPath}/customer/shopPointHistory.html?doAction=add&customerId=${customer.appuserId}&decorator=selecter" buttons="'${buttonSave}':fnAddShopPoint" callback="fnInitAddShopPointForm">
</app:ui_dialog>
</c:if>