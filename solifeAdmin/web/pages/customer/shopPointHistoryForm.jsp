<%@ include file="/common/taglibs.jsp"%>
<form id="shopPointForm" >
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
		<tr>
			<th colspan="2">
				<fmt:message key="shopPointHistory.detail">
            		<fmt:param value="${customer.username }"/>
            	</fmt:message>
				<input type="hidden" name="customerId" value="${customer.customerId}"/> 
				<input type="hidden" name="shopPointHistoryId" value="${shopPointHistory.shopPointHistoryId}" /> 
			</th>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="shopPointHistory.shopPointType"/>
			</td>
			<td>
				<spring:bind path="shopPointHistory.shopPointType">
	            	<select name="shopPointType" class="form-inputbox" style="width:120px" >
						<option value="90" <c:if test="${status.value==90}">selected</c:if>><fmt:message key="shopPoint.shopPointType_90"/></option>
						<option value="40" <c:if test="${status.value==40||not empty param.feedbackId}">selected</c:if>><fmt:message key="shopPoint.shopPointType_40"/></option>
					</select>
	            	<span class="fieldError">${status.errorMessage}</span>
	            </spring:bind>
			</td>
	    </tr>
	    <tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="shopPointHistory.amount" />
			</td>
			<td>
	        	<spring:bind path="shopPointHistory.amount">
	            	<input type="text" name="${status.expression}"  id="${status.expression}" size="20" value="${status.value}" class="form-inputbox"/>
	            	<span class="fieldError">${status.errorMessage}</span>
	            </spring:bind>
	            <cartmatic:ui_tip id="shopPointHistoryTip"><fmt:message key="shopPointHistory.amount.tips"/></cartmatic:ui_tip>
			</td>
	    </tr>
	    <tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="shopPointHistory.description"/>
			</td>
			<td>
				<spring:bind path="shopPointHistory.description">
	            	<textarea rows="8" cols="48" class="form-inputbox" id="${status.expression}"  name="${status.expression}" >${status.value}<c:if test="${shopPointHistory.shopPointType==40&&empty status.value}"><fmt:message key="shopPoint.shopPointType_40_Desc"/></c:if></textarea>
	            	<span class="fieldError">${status.errorMessage}</span>
	            </spring:bind>
			</td>
	    </tr>
	</table>
</form>