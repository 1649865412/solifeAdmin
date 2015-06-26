<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="content" tagdir="/WEB-INF/tags/content"%>
<app:pageHeading pageHeadingKey="advertisementList.heading" />

<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="create" inputType="button" onclick="return fnDoAdd(this);" />
	<!--<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoMultiSave(this);" />-->
	<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoMultiDelete(this);" />
	<cartmatic:cartmaticBtn btnType="cancel" inputType="button" onclick="return fnDoUpToParent(this);" />
</content>
<app:ui_leftMenu>
	<div class="sidebar-left">
	    <form method="post" action="${ctxPath}/content/advertisement.html" onsubmit="return false;">
			<app:ui_tabs tabsId="left_menu_tabs"/>
		    <div class="tab" id="left_menu_tabs">
			    <ul>
					<li><a href="#listSelectContent"><fmt:message key="yourposition.search"/></a></li>
				</ul>
				<div class="content" id="listSelectContent">
					<search:searchBox>
						<search:filter />
						<search:input attrPath="s.advertisementName" attrNameKey="advertisement.advertisementName" datatype="String"
							operator="LIKE" classes="form-inputbox" />
						<div class="title"><fmt:message key="adPositionType.store"/></div>
						<div>
						<select name="COL@s.adPositionType.store.storeId@Integer@EQ" id="storeId" style="width:150px">
						<option value=""></option>
						<c:forEach items="${appConfig.storeMap}" var="storeItem">
						<option value="${storeItem.value.storeId}" 
							<c:if test="${param['COL@s.adPositionType.store.storeId@Integer@EQ'] == storeItem.value.storeId}">selected="selected" </c:if>>${storeItem.value.name}</option>
						</c:forEach>
						</select>
						<search:orderby showOrderDirection="true">
							<option value="advertisementId">
								<fmt:message key="search.order.by.default" />
							</option>
						</search:orderby>
					</search:searchBox>
				</div>
			</div>
		</form>
	</div>
</app:ui_leftMenu>
<form class="mainForm" name="advertisementListForm" method="post" action="${ctxPath}/content/advertisement.html">
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/content/advertisement.html?doAction=edit&from=list" scope="page" />
		<c:set var="checkAll"><input type="checkbox" name="allbox" onclick="checkAll(this.form)" class="checkbox"/></c:set>
		<display:table name="${advertisementList}" cellspacing="0" cellpadding="0" uid="advertisementItem"
			class="table-list"  style="table-layout:fixed;" export="false" requestURI="">
			<display:column headerClass="w30" class="w30" title="${checkAll}" media="html">
				<input type="checkbox" name="multiIds" value="${advertisementItem.advertisementId}" class="checkbox" title="${advertisementItem.advertisementName}"/>
			</display:column>

		<display:column headerClass="w100" class="w100" sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="advertisement.view" media="html">
			<div onmouseout="$j('#preview${advertisementItem.advertisementId}').hide();">
				<a href="javascript:;" onclick="$j('#preview${advertisementItem.advertisementId}').show();"><fmt:message key="advertisement.view" /> </a>
			</div>
			<div style="position: absolute; display: none; z-index: 150;" id="preview${advertisementItem.advertisementId}">
				<c:choose>
					<c:when test="${advertisementItem.contentType==2}">
						<OBJECT height="90" width="550" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000">
							<PARAM NAME="_cx" VALUE="14552">
							<PARAM NAME="_cy" VALUE="2381">
							<PARAM NAME="FlashVars" VALUE="">
							<PARAM NAME="Movie" VALUE="${mediaPath}${advertisementItem.url}">
							<PARAM NAME="Src" VALUE="${mediaPath}${advertisementItem.url}">
							<PARAM NAME="WMode" VALUE="Window">
							<PARAM NAME="Play" VALUE="-1">
							<PARAM NAME="Loop" VALUE="-1">
							<PARAM NAME="Quality" VALUE="High">
							<PARAM NAME="SAlign" VALUE="">
							<PARAM NAME="Menu" VALUE="-1">
							<PARAM NAME="Base" VALUE="">
							<PARAM NAME="AllowScriptAccess" VALUE="">
							<PARAM NAME="Scale" VALUE="ShowAll">
							<PARAM NAME="DeviceFont" VALUE="0">
							<PARAM NAME="EmbedMovie" VALUE="0">
							<PARAM NAME="BGColor" VALUE="">
							<PARAM NAME="SWRemote" VALUE="">
							<PARAM NAME="MovieData" VALUE="">
							<PARAM NAME="SeamlessTabbing" VALUE="1">
							<PARAM NAME="Profile" VALUE="0">
							<PARAM NAME="ProfileAddress" VALUE="">
							<PARAM NAME="ProfilePort" VALUE="0">
							<EMBED src="${mediaPath}${advertisementItem.url}" WIDTH="${advertisementItem.width}" HEIGHT="${advertisementItem.height}" TYPE="application/x-shockwave-flash" PLUGINSPAGE="http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash">
							</EMBED>
						</OBJECT>
					</c:when>
					<c:otherwise>
						<cartmatic:img url="${advertisementItem.url}" mediaType="a_and_d"></cartmatic:img>
					</c:otherwise>
				</c:choose>
			</div>
		</display:column>

		<display:column headerClass="wauto" class="wauto" sortable="false" url="${editURLPath}" paramId="advertisementId" paramProperty="advertisementId"
				decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="advertisement.advertisementName">
				${advertisementItem.advertisementName}
			</display:column>
			<display:column headerClass="w150" class="w150" property="adPositionType.store.name" sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="adPositionType.store"/>
			<display:column headerClass="w200" class="w200" titleKey="advertisement.productCategory" sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator">
				<content:adpdFillCategoryPathName advertisement="${advertisementItem}"></content:adpdFillCategoryPathName>
				<c:forEach items="${advertisementItem.productAdvertisements}" var="productAdvertisement">
					${productAdvertisement.categoryPathName}<br />
				</c:forEach>
			</display:column>
			<display:column headerClass="w100" class="w100" titleKey="advertisement.sortOrder" sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator">
				<input type="text" class="Field40" name="sortOrder_${advertisementItem.advertisementId }" value="${advertisementItem.sortOrder }" />
			</display:column>
			<display:column headerClass="w100" class="w100" sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="advertisement.contentType">
				<content:adType viewType="0" defaultValue="${advertisementItem.contentType}" areaName="no" />
			</display:column>
	</display:table>
		<%@include file="/common/pagingOnlyNew.jsp"%>
</form>
<script type="text/javascript">
highlightTableRows("advertisementItem");
</script>