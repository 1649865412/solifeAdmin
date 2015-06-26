<%@ include file="/common/taglibs.jsp"%>
<title><fmt:message key="shippingMethodList.title"/></title>
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="create" inputType="button" onclick="return fnDoAdd(this);" />
	<cartmatic:cartmaticBtn btnType="cancel" inputType="button" onclick="return fnDoUpToParent(this);" />
</content>
<app:ui_leftMenu>
	<form method="post" action="${ctxPath}/system/carriers.html">
	    <div class="sidebar-left">
			<app:ui_tabs tabsId="left_menu_tabs" />
		    <div class="tab" id="left_menu_tabs">
			    <ul>
					<li><a href="#glSearchBar"><fmt:message key="shippingMethod.companyName"/></a></li>
				</ul>
				<div class="content">
					<ul>
						<c:forEach items="${allCarrier}" var="carrier" varStatus="status">
							<c:if test="${carrier.status==1}" ><li class="ulList">
								<span id="carrier${status.index}">
									<c:choose>
									  <c:when test="${fn:length(carrier.shippingMethods)>0}">
										<a href="shippingMethod.html?showingCarrierId=${carrier.carrierId}">${carrier.carrierName}</a>
										[${fn:length(carrier.shippingMethods)}]
									  </c:when>
									  <c:otherwise>
									  	<a style="color:gray" href="shippingMethod.html?showingCarrierId=${carrier.carrierId}">${carrier.carrierName}</a> [0]
									  </c:otherwise>
									</c:choose>
								</span>
								<c:if test="${not empty param.showingCarrierId && param.showingCarrierId==carrier.carrierId}">
									<script type="text/javascript" defer>
										$j("#carrier${status.index}").css("font-weight","bold");
										$j("div[class='position']>div>span").append("[ ${carrier.carrierName} ]");
									</script>
								</c:if>
								<c:if test="${fn:length(carrier.shippingMethods)==0}">
									<script type="text/javascript" defer>
										$j("#carrier${status.index}").css("color","gray");
									</script>
								</c:if>
							</li>
							</c:if>
						</c:forEach>
						<li><br></li>
						<li class="ulList"> <a href="shippingMethod.html">全部</a></li>
					</ul>
				</div>
			</div>
		</div>
	</form>
</app:ui_leftMenu>
<form class="mainForm" name="shippingMethodListFrm" method="post" action="<c:url value="/system/shippingMethod.html"/>">
<!-- form开始 -->
	<div id="shippingMethodListTable">
		<%@include file="include/shippingMethodListTable.jsp" %>
	</div>
<!-- form结束 -->
</form>
<!-- *************** shippingRateForm.jsp引用：Begin *************** -->
<script type="text/javascript" defer>
	function fnSaveShippingRate(){
    	var form=$j("#shippingRateForm");
    	var isPass=validateShippingRate(form.get(0));
    	if(isPass){
    		var paramData=form.serializeArray();
    		$j.post("${ctxPath}/system/shippingRate/dialog.html?doAction=save",paramData,fnCloseDlgShippingRate,"json");
    	}
    }
    function fnDeleteShippingRate(){
        var entityName=$j("#shippingMethodName").val();
        if(confirm(__FMT.common_message_confirmDeleteThis+ " " + entityName + " ?")){
        	var form=$j("#shippingRateForm");
        	var paramData=form.serializeArray();
        	$j.post("${ctxPath}/system/shippingRate/dialog.html?doAction=delete",paramData,fnCloseDlgShippingRate,"json");
        }
    }

    function fnCloseDlgShippingRate(result){
        if(result.status==1){
        	window.location.reload();
	   		dlgEditShippingRate_close();
        }else if(result.status==0){
        	alert("子区域已经存在!");
        }else{
			alert(result.msg);
        }
    }
    //log("${fn:length(carrierList)}");
</script>
<fmt:message key="button.save" var="buttonSave"/>
<fmt:message key="button.cancel" var="buttonCancel"/>
<fmt:message key="button.delete" var="buttonDelete"/>
<fmt:message key="shippingRateDetail.title" var="shippingRateDetail" />
<app:ui_dialog id="EditShippingRate" width="640" height="390" title="${shippingRateDetail}" url="${ctxPath}/system/shippingRate/dialog.html?doAction=edit" buttons="'${buttonSave}':fnSaveShippingRate,'${buttonDelete}':fnDeleteShippingRate,'${buttonCancel}':dlgEditShippingRate_close" />
<!-- *************** shippingRateForm.jsp引用：End *************** -->