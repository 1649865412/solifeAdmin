<div id="ruleDetail-summary" >
	<input type="hidden" name="promoRuleId" id="promoRuleId" value="${promoRule.promoRuleId}" />
	<input type="hidden" name="version" id="version" value="${promoRule.version}" />
	<table class="table-content" >
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="${entityClassName}.name" />
			</td>
			<td>
				<form:input path="name" size="64" cssClass="Field400" />
				<form:errors path="name" cssClass="fieldError" />
				<cartmatic:ui_tip id="nameDesc">
					<fmt:message key="promoRule.name.tip" />
				</cartmatic:ui_tip>
				&nbsp;&nbsp;
				<b>
					<span id="state"><c:if test="${not empty promoRule.state}"><fmt:message key="promoRule.state.s${promoRule.state}" /></c:if> </span> 
				</b>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="${entityClassName}.store" />
			</td>
			<td>
				${promoRule.store.name}
				<input type="hidden" name="storeId" value="${promoRule.storeId}"/>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<fmt:message key="promoRule.status" />:
			</td>
			<td>
				<label for="checkbox_status">
					<fmt:message key="status.active" />
				</label>
				<input type="checkbox" name="checkbox_status" id="checkbox_status" onclick="ChangeValueOfStatus(this,'status')" <c:if test="${promoRule.status == 1||promoRule.promoRuleId == null}"> checked="checked"</c:if> />
				<cartmatic:ui_tip id="statusTip"><fmt:message key="promoRule.status.tip" /></cartmatic:ui_tip>
				<c:choose>
					<c:when test="${promoRule.promoRuleId == null}">
						<input type="hidden" name="status" id="status" value="1" />
					</c:when>
					<c:otherwise>
						<form:hidden path="status"/>
					</c:otherwise>
				</c:choose>
				&nbsp;&nbsp;
				<label for="checkbox_enableDiscountAgain">
					<fmt:message key="promoRule.enableDiscountAgain.yes" />
				</label>
				<input type="checkbox" name="checkbox_enableDiscountAgain"
					 id="checkbox_enableDiscountAgain" 
					 onclick="ChangeValueOfEnableDiscountAgain(this,'enableDiscountAgain')"
					 <c:if test="${promoRule.enableDiscountAgain == 1||promoRule.promoRuleId == null}"> checked="checked"</c:if>/>
				<cartmatic:ui_tip id="enableDiscountAgainTip"><fmt:message key="promoRule.enableDiscountAgain.tip" /></cartmatic:ui_tip>	
				<c:choose>
					<c:when test="${promoRule.enableDiscountAgain == null}">
						<input type="hidden" name="enableDiscountAgain" id="enableDiscountAgain" value="1" />
					</c:when>
					<c:otherwise>
						<form:hidden path="enableDiscountAgain"/>
					</c:otherwise>
				</c:choose>
			</td>			
		</tr>

		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="promoRule.promoType" />
			</td>
			<td>
				<c:if test="${promoRule.promoType == 'catalogPromotion'}">
					<c:set var="promoTypeImgSrc" value="ico_catalog.gif"></c:set>
				</c:if>
				<c:if test="${promoRule.promoType == 'shoppingcartPromotion'}">
					<c:set var="promoTypeImgSrc" value="ico_shoppingcart.gif"></c:set>
				</c:if>
				<c:if test="${promoRule.promoType == 'couponPromotion'}">
					<c:set var="promoTypeImgSrc" value="ico_coupon.gif"></c:set>
				</c:if>
				<img src="../images/icon/${promoTypeImgSrc}"/>
				<fmt:message key="promoRule.promoType.${promoRule.promoType}" />
				<form:hidden path="promoType" />
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="${entityClassName}.priority" />
			</td>
			<td>
				<input class="Field100" id="priority" name="priority"  value="${empty promoRule.priority?10:promoRule.priority}"/> 
				<form:errors path="priority" cssClass="fieldError" />				
				<span>
					<cartmatic:ui_tip id="priorityTip"><fmt:message key="promoRule.priority.tip" /></cartmatic:ui_tip>
				</span>
			</td>
		</tr>
		<c:if test='${promoRule.promoType == "couponPromotion" }'>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="${entityClassName}.availableCount" />
			</td>
			<td>
				<form:input path="availableCount" size="10" cssClass="Field100"/> <form:errors path="availableCount" cssClass="fieldError" />
				<%--保存原来的使用次数 --%>		
				<input type="hidden" value="${promoRule.availableCount}" id="availableCountRecord" name="availableCountRecord"/>		
				<cartmatic:ui_tip id="availableCountTip"><fmt:message key="promoRule.availableCount.tip" /></cartmatic:ui_tip>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">特定用途：</td>
			<td>
				<form:select  path="triggerType" id="triggerType" name="triggerType">
					<form:option value="0">无特定用途</form:option>
					<form:option value="1">用户注册时使用此优惠劵促销为用户发送优惠劵</form:option>
					<form:option value="2">用户邀请好友时使用此优惠劵促销为用户发送优惠劵</form:option>
				</form:select>
			</td>
		</tr>
		</c:if>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="${entityClassName}.startTime" />
			</td>
			<td>
				<div style="float:left;">
				<form:input id="startTime" path="startTime"
						cssClass="Field100" size="10"/> <form:errors path="startTime"	cssClass="fieldError" /> 							
					<app:ui_datePicker outPut="startTime" />
				</div>					
				<div id="startHourSpan" style="float:left; margin:3px 0 0 0;">
					<select id="startHour" name="startHour">
						<c:forEach begin="0" end="23" var="num">
							<option value="${num}"<c:if test="${num==promoRule.startHourShow}"> selected="selected"</c:if>>${num}</option>
						</c:forEach>
					</select>						
				</div>
				<div style="float:left; padding:2px 0 0 2px;">
					<fmt:message key="promoRule.hour"/>
					<cartmatic:ui_tip id="startTimeTip"><fmt:message key="promoRule.startTime.tip" /></cartmatic:ui_tip>
				</div>
			</td>
		</tr>
		
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="${entityClassName}.endTime" />
			</td>
			<td>
				<div style="float:left;">					
					<form:input id="endTime" path="endTime"
							cssClass="Field100" size="10"/> <form:errors path="endTime"
							cssClass="fieldError" /> 
					<app:ui_datePicker outPut="endTime" />
				</div>
				<div id="endHourSpan" style="float:left; margin:3px 0 0 0;">
					<select id="endHour" name="endHour">
						<c:forEach begin="0" end="23" var="num">
							<option value="${num}"<c:if test="${num==promoRule.endHourShow}"> selected="selected"</c:if>>${num}</option>
						</c:forEach>
					</select>
				</div>
				<div style="float:left; padding:2px 0 0 2px;">
						<fmt:message key="promoRule.hour"/>
						<cartmatic:ui_tip id="endTimeTip"><fmt:message key="promoRule.endTime.tip" /></cartmatic:ui_tip>
				</div>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<fmt:message key="promoRule.description"/>:
			</td>
			<td>
				<span class="descpanel"><textarea cols="80" rows="20" id="description" name="description">${promoRule.description}</textarea>
					<form:errors path="description" cssClass="fieldError" /> </span>
				<app:ui_htmlEditor textareaIds="description" />
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="common.message.createTime" />
			</td>
			<td>
				<SPAN id="createTimeShow"><fmt:formatDate value="${promoRule.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></SPAN>
				<input type="hidden" id="createTime" name="createTime" value="<fmt:formatDate value='${promoRule.createTime}' pattern='yyyy-MM-dd HH:mm:ss'/>" />
				<input type="hidden" id="createBy" name="createBy" value="${promoRule.createBy}" />
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="common.message.updateTime" />
			</td>
			<td>
				<SPAN id="updateTimeShow"><fmt:formatDate value="${promoRule.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></SPAN>
				<input type="hidden" id="updateTime" name="updateTime" value="<fmt:formatDate value='${promoRule.updateTime}' pattern='yyyy-MM-dd HH:mm:ss'/>" />
				<input type="hidden" id="updateBy" name="updateBy" value="${promoRule.updateBy}" />
			</td>
		</tr>
	</table>
</div>