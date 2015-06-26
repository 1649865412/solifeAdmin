<%@ include file="/common/taglibs.jsp"%>
<app:pageHeading pageHeadingKey="giftCertificateList.heading" />
<content tag="buttons">
<cartmatic:cartmaticBtn btnType="create" onclick="return fnDoAdd(this);" />
	<%-- 
<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoMultiDelete(this);" />
	 --%>
<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoUpToParent(this);" />
</content>
<app:ui_leftMenu>
		<div class="sidebar-left">
			<app:ui_tabs tabsId="left_menu_tabs" />
			<form name="searchGiftCertificateForm" method="post" action="${ctxPath}/sales/giftCertificate.html" onsubmit="return false;">
				<div class="tab" id="left_menu_tabs">
					<ul>
						<li>
							<a href="#glSearchBar"><fmt:message key="yourposition.search" />
							</a>
						</li>
					</ul>
					<search:searchBox>
						<search:input attrPath="s.giftCertificateNo" attrNameKey="giftCertificate.giftCertificateNo" datatype="String" operator="LIKE" classes="form-inputbox" onenter="true"/>
						<search:input attrPath="s.recipientEmail" attrNameKey="giftCertificate.recipientEmail" datatype="String" operator="LIKE" classes="form-inputbox" onenter="true"/>
						<search:input attrPath="s.remainedAmt" attrNameKey="giftCertificate.remainedAmt" datatype="BigDecimal" operator="EQ" classes="form-inputbox" onenter="true"/>
						<div class="blank10"></div>
						<div class="title">
							<fmt:message key="giftCertificate.sa_state" />
						</div>
						<select name="state" id="state">
							<option> </option>
							<option value="1" <c:if test="${param.state==1}">selected</c:if>><fmt:message key="giftCertificate.sa_state.s1"/> </option>
							<option value="2" <c:if test="${param.state==2}">selected</c:if>><fmt:message key="giftCertificate.sa_state.s2"/> </option>
							<option value="0" <c:if test="${param.state==0}">selected</c:if>><fmt:message key="giftCertificate.sa_state.s0"/> </option>
							<option value="3" <c:if test="${param.state==3}">selected</c:if>><fmt:message key="giftCertificate.sa_state.s3"/> </option>
						</select>
						<div class="blank10"></div>
					</search:searchBox>
				</div>
			</form>
		</div>
</app:ui_leftMenu>
<form class="mainForm" action="${ctxPath}/sales/giftCertificate.html"	name="mainForm" method="post">
	<!--listing box start-->	
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath"
		value="/sales/giftCertificate.html?doAction=edit&from=list" scope="page" />
	
		<c:set var="checkAll">
			<input type="checkbox" name="allbox" onclick="checkAll(this.form)"
				style="margin: 0 0 0 0px" />
		</c:set>
		<display:table name="${giftCertificateList}" cellspacing="0"
			cellpadding="0" uid="giftCertificateItem" class="table-list"
			export="false" requestURI="">
			<%--
			<display:column style="width: 3%" title="${checkAll}" media="html">
				<input type="checkbox" name="multiIds"
					value="${giftCertificateItem.giftCertificateId}" class="checkbox"
					title="${giftCertificateItem.giftCertificateNo}" />
			</display:column>
			<display:column sortable="false" headerClass="data-table-title"
				titleKey="giftCertificateList.isSentByEmail">
				<c:if test="${giftCertificateItem.isSentByEmail=='1'}">
					<fmt:message key="giftCertificateList.sentByEmail" />
				</c:if>
				<c:if test="${giftCertificateItem.isSentByEmail=='0'}">
					<fmt:message key="giftCertificateList.sentByPostMail" />
				</c:if>
			</display:column>
			 --%>
			<display:column property="giftCertificateNo" sortable="false"
				headerClass="data-table-title"
				url="/sales/giftCertificate.html?doAction=edit"
				paramId="giftCertificateId" paramProperty="giftCertificateId"
				decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator"
				titleKey="giftCertificate.giftCertificateNo" />
			
			<display:column sortable="false" headerClass="data-table-title"
				titleKey="giftCertificateList.giftCertAmt">
			<system:CurrencyForRate value="${giftCertificateItem.remainedAmt}"/>/<system:CurrencyForRate value="${giftCertificateItem.giftCertAmt}"/>
			</display:column>
			<display:column property="expireTime" sortable="false"
				headerClass="data-table-title"
				decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator"
				titleKey="giftCertificate.expireTime" />
			<display:column sortable="false" headerClass="data-table-title" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator"
				titleKey="giftCertificate.giftType">
				<fmt:message key="giftCertificate.giftType_${giftCertificateItem.giftType}"/>
			</display:column>
			<display:column sortable="false" headerClass="data-table-title"
				decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator"
				titleKey="giftCertificate.status">					
					<span class="${stateClass}"><fmt:message key="giftCertificate.sa_state.s${giftCertificateItem.status}" /></span>
			</display:column>
				
			<display:column sortable="false" headerClass="data-table-title"
				titleKey="giftCertificate.createBy">
				<app:userName userId="${giftCertificateItem.createBy}"
					noFormat="true" />
			</display:column>
		</display:table>
		<%@ include file="/common/pagingOnlyNew.jsp"%>
</form>