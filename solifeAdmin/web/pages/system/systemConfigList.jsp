<%@ include file="/common/taglibs.jsp"%>
<%@taglib prefix="system" tagdir="/WEB-INF/tags/system"%>
<title><fmt:message key="systemConfigList.title" /></title>
<content tag="heading">
<fmt:message key="systemConfigList.heading" />
</content>
<content tag="buttons">
<cartmatic:cartmaticBtn btnType="save" onclick="fnMainSubmit()" />
<cartmatic:cartmaticBtn btnType="cancel" onclick="location.href='${ctxPath }/system/index.html'" />
<cartmatic:cartmaticBtn btnType="refreshRerverCache" onclick="return fnDoSimpleAction(this,'clearAllServerSideCache');" />
<!-- set current method todo for multiActionCotroller -->
</content>

<form class="mainForm" name="systemConfigListFrm" id="systemConfigListFrm" action="${ctxPath}/system/systemConfig.html" method="post">
	<input type="hidden" name="configCategory" value="${configCategory}" />
	<div id="systemconfig_tab">
    <ul>
    	<c:forEach var="item" items="${catalogs}" varStatus="vs">
			<c:if test="${item==configCategory}">
				<c:set var="selectedId" value="${vs.index}"/>
			</c:if>
			<li>
				<a href="?configCategory=${item}" target="_self"><fmt:message key="conf.cate.${item}" /></a>
			</li>
		</c:forEach>    	
    </ul>
    <div class="clear"></div>
    <div id="configContent" >
    	<table width="100%" border="0" cellspacing="0" cellpadding="0" id="systemConfigs" class="table-content">
					<tr>
						<td>
							<table cellspacing="0" cellpadding="0" width="100%" border="0">
								<tr>
									<th width="20%" align="left">
										<fmt:message key="systemConfig.name" />
									</th>
									<th width="30%" align="left">
										<fmt:message key="systemConfig.configValue" />
									</th>
									<th align="left">
										<fmt:message key="systemConfig.description" />
									</th>
								</tr>
								<c:forEach var="store" items="${storeList}" >
								<%--<tr>
									<th align="center">
										${store.name}
									</th>
									<th width="30%" align="center">
										
									</th>
									<th align="center">
										
									</th>
								</tr>--%>
								<c:forEach var="item" items="${store.systemConfigs}" varStatus="loopStatus" >
									<c:if test="${item.isSystem!=1}">
										<tr class="${(loopStatus.index mod 2==0) ? 'odd' : 'even'}">
											<td style="text-align:left;">
												<fmt:message key="conf.name.${item.configKey}" />
											</td>
											<td id="conf${item.systemConfigId}" style="text-align:left;">
												<c:choose>
													<c:when test="${item.isSystem==4}">
														<span>${item.configValue}</span>
													</c:when>
													<c:when test="${item.dataType==5}">
														<input type="text"  name="configValue_${item.systemConfigId}" id="configValue_${item.systemConfigId}"
															value="${item.configValue}" readonly="readonly" />
														<cartmatic:cartmaticBtn btnType="common" commonBtnValueKey="systemConfig.mediaPath"
															onclick="fnAppMediaSelect(this,null,null,null,'configValue_${item.systemConfigId}')" />
													</c:when>
													<c:when test="${item.dataType==6}">
														<span>No more supported config type (which is I18n).</span>
													</c:when>
													<c:when test="${item.configType==1}">
														<input type="text" class="Field400"  name="configValue_${item.systemConfigId}"
															value="<c:out value="${item.configValue}"/>" valueType="${item.dataType}" />
													</c:when>
													<c:when test="${item.configType==7}">
														<input type="password" class="Field400"  name="configValue_${item.systemConfigId}"
															value="<c:out value="${item.configValue}"/>" valueType="${item.dataType}" />
													</c:when>
													<c:when test="${item.configType==2}">
														<select name="configValue_${item.systemConfigId}" 
															valueType="${item.dataType}" class="Field400">
															<c:forTokens items="${item.options}" var="opt" delims="[,]">
															<c:set var="v" value="${fn:split(opt,'=')}"/>
															  <option value="${v[1] }" <c:if test="${item.configValue == v[1]}">selected</c:if>>
															    <fmt:message key="${v[0]}"/>
															  </option>
															</c:forTokens>
														</select>
													</c:when>
													<c:when test="${item.configType==3}">
														<input type="checkbox" class="Field400" name="configValue_${item.systemConfigId}" value="true"
															<c:if test="${item.configValue=='true'}"> checked="checked"</c:if>
															 valueType="${item.dataType}" />
													</c:when>
												</c:choose>

											</td>
											<td style="text-align:left;">
												<c:if test="${item.dataType==2}">
														(<fmt:message key="system.config.datatype.number" />)
												</c:if>
												<i18n:msg key="conf.desc.${item.configKey}" emtpyWhenNull="true" />
											</td>
									</c:if>
								</c:forEach>
								</c:forEach>
								<c:forEach var="item" items="${systemConfigList}" varStatus="loopStatus" >
									<c:if test="${item.isSystem!=1}">
										<tr class="${(loopStatus.index mod 2==0) ? 'odd' : 'even'}">
											<td style="text-align:left;">
												<fmt:message key="conf.name.${item.configKey}" />
											</td>
											<td id="conf${item.systemConfigId}" style="text-align:left;">
												<c:choose>
													<c:when test="${item.isSystem==4}">
														<span>${item.configValue}</span>
													</c:when>
													<c:when test="${item.dataType==5}">
														<input type="text"  name="configValue_${item.systemConfigId}" id="configValue_${item.systemConfigId}"
															value="${item.configValue}" readonly="readonly" />
														<cartmatic:cartmaticBtn btnType="common" commonBtnValueKey="systemConfig.mediaPath"
															onclick="fnAppMediaSelect(this,null,null,null,'configValue_${item.systemConfigId}')" />
													</c:when>
													<c:when test="${item.dataType==6}">
														<span>No more supported config type (which is I18n).</span>
													</c:when>
													<c:when test="${item.configType==1}">
														<input type="text" class="Field400"  name="configValue_${item.systemConfigId}"
															value="<c:out value="${item.configValue}"/>" valueType="${item.dataType}" />
													</c:when>
													<c:when test="${item.configType==7}">
														<input type="password" class="Field400"  name="configValue_${item.systemConfigId}"
															value="<c:out value="${item.configValue}"/>" valueType="${item.dataType}" />
													</c:when>
													<c:when test="${item.configType==2}">
														<select name="configValue_${item.systemConfigId}" 
															valueType="${item.dataType}" class="Field400">
															<c:forTokens items="${item.options}" var="opt" delims="[,]">
															<c:set var="v" value="${fn:split(opt,'=')}"/>
															  <option value="${v[1] }" <c:if test="${item.configValue == v[1]}">selected</c:if>>
															    <fmt:message key="${v[0]}"/>
															  </option>
															</c:forTokens>
														</select>
													</c:when>
													<c:when test="${item.configType==3}">
														<input type="checkbox" class="Field400" name="configValue_${item.systemConfigId}" value="true"
															<c:if test="${item.configValue=='true'}"> checked="checked"</c:if>
															 valueType="${item.dataType}" />
													</c:when>
													<c:when test="${item.configType==8}">
														<input type="text" class=""  id="configValue_${item.systemConfigId}" name="configValue_${item.systemConfigId}" readonly="true" value="${item.configValue}" 
															 />
															 <app:ui_datePicker outPut="configValue_${item.systemConfigId}" pattern="mm/dd/yy"/>
													</c:when>
												</c:choose>

											</td>
											<td style="text-align:left;">
												<c:if test="${item.dataType==2}">
														(<fmt:message key="system.config.datatype.number" />)
												</c:if>
												<i18n:msg key="conf.desc.${item.configKey}" emtpyWhenNull="true" />
											</td>
									</c:if>
								</c:forEach>
							</table>
						</td>
					</tr>
				</table>
    </div>    
</div>
<app:ui_tabs tabsId="systemconfig_tab" type="2" selected="${selectedId}"/>
<input type="hidden" name="doAction" value="multiSave" />
</form>
<script type="text/javascript">
 function fnMainSubmit(c){
	 if(confirm(__FMT.common_message_confirmSave)){
	 	var frm=$("systemConfigListFrm");
	 	frm.submit();
 	}
 }
</script>