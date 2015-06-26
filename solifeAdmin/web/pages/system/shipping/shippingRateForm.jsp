<%@ include file="/common/taglibs.jsp"%>
<style>
<!--
table-content table{border:0px solid #ccc;border-collapse:collapse;margin:0px;}
.table-content table td{margin-top:0px;vertical-align:center;border:0; padding:0px};
.table-content table td input{margin-top:0px;}
.table-content table span{ font-weight:normal;color:#000;}
-->
</style>
<link rel="stylesheet" href="${ctxPath}/scripts/jquery/plugins/quicktip/quicktip.css" type="text/css" />
<script type="text/javascript" src="${ctxPath}/scripts/jquery/plugins/quicktip/quicktip.js"></script>

<spring:bind path="shippingRate.*">
	<c:if test="${not empty status.errorMessages}">
		<div class="error">
			<c:forEach var="error" items="${status.errorMessages}">
				<img src="<c:url value="/images/iconWarning.gif"/>" alt="<fmt:message key="icon.warning"/>" class="icon" />
				<c:out value="${error}" escapeXml="false" /> <br />
			</c:forEach>
		</div>
	</c:if>
</spring:bind>
<form class="mainForm" method="post" target="_parent" action="<c:url value="/system/editShippingRateBean/dialog.html"/>" id="shippingRateForm">
	<spring:bind path="shippingRate.version">
		<input type="hidden" name="version" value="<c:out value="${status.value}"/>" />
	</spring:bind>
	<spring:bind path="shippingRate.shippingRateId">
		<input type="hidden" name="shippingRateId" value="<c:out value="${status.value}"/>" />
	</spring:bind>
	<%--<spring:bind path="shippingRate.baseOn">
		<input type="hidden" name="${status.expression}" value="0" />
	</spring:bind>--%>

	
		<table class="table-content" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="FieldLabel">
					basePrice:
				</td>
				<td>
					<spring:bind path="shippingRate.basePrice">
						<input type="text" class="Field100" name="<c:out value="${status.expression}"/>" id="${status.expression}" value="${shippingRate.basePrice}" />
					</spring:bind>
				</td>
			</tr>
			<tr>
				<td class="FieldLabel"><StoreAdmin:label key="shippingRate.baseOn"/></td>
				<td>
					<select id="baseOn" onchange="typeChange();" name="baseOn"  class="Field200"  > 
                  	<option value="0" <c:if test="${empty shippingRate.baseOn || shippingRate.baseOn==1}">selected</c:if>>
                  		<fmt:message key="shippingRate.byweight"/></option> 
				  	<option value="2" <c:if test="${shippingRate.baseOn==2}">selected</c:if>>
				  		<fmt:message key="shippingRate.byitem"/></option> 
		            </select>
				</td>
			</tr>
			<tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="shippingRate.shippingMethodId" />
				</td>
				<td>
					<c:choose>
						<c:when test="${empty shippingRate.shippingRateId}">
							<spring:bind path="shippingRate.shippingMethod.shippingMethodId">
								<select name="shippingMethodId" id="shippingMethodId"
									 class="Field200">
									<c:forEach var="sm" items="${shippingMethodList}">
										<option value="<c:out value="${sm.shippingMethodId}"/>">${sm.shippingMethodName}</option>
									</c:forEach>
								</select>
								<span class="fieldError"><c:out value="${status.errorMessage}" /></span>
								<script type="text/javascript">
									document.forms["shippingRateForm"].elements["shippingMethodId"].value="<c:out value="${status.value}"/>";
								</script>
							</spring:bind>
						</c:when>
						<c:when test="${not empty shippingRate.shippingMethod}">
							<input type="hidden"  name="shippingMethodId" id="shippingMethodId" value="${shippingRate.shippingMethod.shippingMethodId}" />
							<input type="text" class="Field200" readonly="true" id="shippingMethodName" name="shippingMethodName" value="${shippingRate.shippingMethod.shippingMethodName}" />
						</c:when>
						<c:otherwise>
							<input type="hidden" name="shippingMethodId" id="shippingMethodId"/>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td class="FieldLabel">
					<StoreAdmin:label styleClass="required" key="shippingRate.regionId" />
				</td>
				<td >
					<spring:bind path="shippingRate.regionId">
						<input type="hidden" name="<c:out value="${status.expression}"/>" id="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>" />
						<input type="text" class="Field200" readonly="true"  name="regionName" id="regionName" value="<c:if test="${not empty shippingRate.region.regionName}" >${shippingRate.region.regionName}</c:if>" />
						<span class="fieldError"><c:out value="${status.errorMessage}" /></span>
						<cartmatic:iconBtn icon="magnifier" id="btnSelectRegion" textKey="region.parent.select"/>
						<app:region id="regionSeclector" showSelectorBtnId="btnSelectRegion" regionId="${status.expression}" regionName="regionName" regionType="all" />
					</spring:bind>
				</td>
			</tr>
			<%-- 基于项的计费 --%>
			<tr class="baseOn_1">
				<td class="FieldLabel"><fmt:message key="shippingRate.itemPerRate"/>(*):</td>
				<td>
					<spring:bind path="shippingRate.itemPerRate">
            		<input type="text" class="Field200" name="<c:out value="${status.expression}"/>" 
                    id="${status.expression}" value="${shippingRate.itemPerRate}" />
            		<span class="fieldError"><c:out value="${status.errorMessage}"/></span>
            		</spring:bind>
				</td>
			</tr>
			<%-- 基于重量的计费 --%>
			<tr class="baseOn_0">
				<td class="FieldLabel"><StoreAdmin:label key="shippingRate.metricUnitCode" /></td>
				<td>
					<select name="metricUnitCode" onchange="metricUnitChange();"  class="Field200">
						<option value="g" <c:if test="${shippingRate.metricUnitCode eq 'g'}">selected="true"</c:if>><fmt:message key="shippingRate.g"/></option>
						<option value="kg"<c:if test="${shippingRate.metricUnitCode eq 'kg'}">selected="true"</c:if>><fmt:message key="shippingRate.kg"/></option>
					</select>
					
				</td>
			</tr>
			<tr class="baseOn_0">
				<td class="FieldLabel"><fmt:message key="shippingRate.baseWeight"/>(*):</td>
				<td>
					<table id="tableOne" style="border:0px;border-collapse:collapse;margin:0px;padding:0px;"><tr>
					  <td>
						<spring:bind path="shippingRate.basePrice">
						<input type="text" class="Field100" name="<c:out value="${status.expression}"/>" id="${status.expression}" value="${shippingRate.basePrice}" />
						<fmt:message key="shippingRate.currency"/><span class="fieldError"><c:out value="${status.errorMessage}" /></span>
						</spring:bind>
					  </td>
					  <td>&nbsp;/&nbsp;</td>
					  <td>
						<spring:bind path="shippingRate.baseWeight">
						<input type="text" class="Field100" name="<c:out value="${status.expression}"/>" id="${status.expression}" value="${shippingRate.baseWeight}" />
						<span id="metricUnitLabel1"></span>
						</spring:bind>
					  </td>					  
					</tr>
					<tr>
						<td colspan="3"><fmt:message key="shippingRate.baseWeightTip" /></td>
					</tr>
					</table>
				</td>
			</tr>
			<tr class="baseOn_0">
				<td class="FieldLabel"><StoreAdmin:label key="shippingRate.overWeight"/></td>
				<td >
					<table id="tableTwo" style="border:0px;border-collapse:collapse;margin:0px;padding:0px;"><tr>
					  <td>
						<spring:bind path="shippingRate.weightPerRate">
							<input type="text" class="Field100" name="<c:out value="${status.expression}"/>" id="${status.expression}" value="${shippingRate.weightPerRate}" />
							<fmt:message key="shippingRate.currency"/>
							<span class="fieldError"><c:out value="${status.errorMessage}" />
							</span>
						</spring:bind>
					  </td>
					  <td>&nbsp;/&nbsp;</td>
					  <td>
						<spring:bind path="shippingRate.increaseUnit">
							<input type="text" class="Field100" name="<c:out value="${status.expression}"/>" id="${status.expression}" value="${shippingRate.increaseUnit}" />
							<span id="metricUnitLabel2"></span>
						</spring:bind>
					  </td>					  
					</tr>
					<tr>
						<td colspan="3"><fmt:message key="shippingRate.overWeightTip" /></td>
					</tr>
					</table>
				</td>
			</tr>
			<tr class="baseOn_0">
				<td class="FieldLabel"><StoreAdmin:label key="shippingRate.maxWeight"/></td>
				<td >
					<spring:bind path="shippingRate.maxWeight">
						<input type="text" class="Field200" name="<c:out value="${status.expression}"/>" id="${status.expression}" value="${shippingRate.maxWeight}" /><span id="metricUnitLabel3"></span>
						<span class="fieldError"><c:out value="${status.errorMessage}" />
						</span>
					</spring:bind>
				</td>
			</tr>
			<tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="shippingRate.description" />
				</td>
				<td >
					<spring:bind path="shippingRate.description">
						<textarea name="${status.expression}" id="${status.expression}"
							class="Field200" rows="3" cols="60">${status.value}</textarea>
						<span class="fieldError">${status.errorMessage}</span>
					</spring:bind>
				</td>
			</tr>
			<%-- <tr>
				<th colspan="2" style="padding-left:43px;text-align:left"><fmt:message key="shippingRate.formula" /></td>
			</tr>--%>
		</table>
	
</form>
<v:javascript formName="shippingRate" staticJavascript="false" />
<script type="text/javascript" defer>
	function typeChange()
	{
		if ($j('#baseOn').val() == 0)
		{
			$j(".baseOn_1").hide();
			$j(".baseOn_0").show();
			applyValidate($("basePrice"), "required,price");
			applyValidate($("baseWeight"), "required,double4,minValue=0,maxValue=1000");
			removeValidate($("itemPerRate"), "required");
		}
		else
		{
			$j(".baseOn_1").show();
			$j(".baseOn_0").hide();
			applyValidate($("itemPerRate"), "required,price");
			removeValidate($("basePrice"), "required");
			removeValidate($("baseWeight"), "required");
		}
	}
	
	function setMetricUnitLabel(metricUnitValue){
		if(metricUnitValue){
			$("metricUnitLabel2").innerHTML=$("metricUnitLabel1").innerHTML=metricUnitValue;
			$("metricUnitLabel3").innerHTML=$("metricUnitLabel1").innerHTML=metricUnitValue;
		}
	}
	function metricUnitChange(){
		for(i=0,o=document.forms["shippingRateForm"].elements["metricUnitCode"];i<o.options.length;i++){
			if(o.options[i].selected) setMetricUnitLabel(o.options[i].text);
		}
	}
	metricUnitChange();
	typeChange();
</script>
<!-- 区域选择器树的UI Style引用 -->
<%@ include file="/decorators/include/uiResource.jspf"%>