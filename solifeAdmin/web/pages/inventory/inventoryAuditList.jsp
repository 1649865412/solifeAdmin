<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib prefix="search" tagdir="/WEB-INF/tags/search"%>
<app:pageHeading pageHeadingKey="inventoryAuditList.heading" />
<%--
<content tag="buttons">
<cartmatic:cartmaticBtn btnType="create" onclick="return fnDoAdd(this);" />
<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoMultiSave(this);" />
<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoMultiDelete(this);" />
<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoUpToParent(this);" />
</content>
--%>
<app:ui_leftMenu>
	<div class="sidebar-left">
		<form method="post" action="${ctxPath}/inventory/inventoryAudit.html" onsubmit="return false;">
			<app:ui_tabs tabsId="left_menu_tabs" />
			<div class="tab" id="left_menu_tabs">
				<ul>
					<li>
						<a href="#listSelectContent"><fmt:message key="yourposition.search" /> </a>
					</li>
				</ul>
				<div class="content" id="listSelectContent">
					<search:searchBox>
						<search:filter />
						<search:input attrPath="s.productSku.productSkuCode" attrNameKey="productSku.productSkuCode" datatype="String" operator="EQ" classes="form-inputbox" />
						<div class="title"><fmt:message key="inventoryAudit.eventType"/></div>
						<div>
							<select name="COL@s.eventType@Short@EQ" id="COL@s.eventType@Short@EQ">
								<option value=""></option>
								<c:forEach var="item" begin="1" end="4">
									<option value="${item}" ${(sc==null ? (item==requestScope['COL@s.eventType@Short@EQ']) : (item==sc.param['COL@s.eventType@Short@EQ'])) ? 'selected' : ''}>
								      <fmt:message key="inventoryAudit.eventType_${item}"/>
									</option>
								</c:forEach>
							</select>
							<div class="blank10"></div>
						</div>
						<div class="title"><fmt:message key="inventoryAudit.createTime"/></div>
						<div>
							从
							<input name="COL@s.createTime@Date_Begin@GTE" id="createTimeGTE" type="text" readonly="true" value="${sc==null ? requestScope["COL@s.createTime@Date@GTE"] : sc.param["COL@s.createTime@Date_Begin@GTE"]}"/>
							<app:ui_datePicker outPut="createTimeGTE" />
							<br/>
							到
							<input name="COL@s.createTime@Date_End@LTE" id="createTimeLTE" type="text" readonly="true" value="${sc==null ? requestScope["COL@s.createTime@Date@LTE"] : sc.param["COL@s.createTime@Date_End@LTE"]}"/>
							<app:ui_datePicker outPut="createTimeLTE" />
							<div class="blank10"></div>
						</div>
						<search:orderby showOrderDirection="true">
							<option value="inventoryAuditId">
								<fmt:message key="search.order.by.default" />
							</option>
						</search:orderby>
					</search:searchBox>
				</div>
			</div>
		</form>
	</div>
</app:ui_leftMenu>
<form class="mainForm" name="inventoryAuditListForm" method="post" action="${ctxPath}/inventory/inventoryAudit.html">
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	
		<%@include file="include/inventoryAuditListContent.jsp"%>
		<div class="blank10"></div>
		<%@include file="/common/pagingOnlyNew.jsp"%>
	
</form>
<script type="text/javascript">
highlightTableRows("inventoryAuditItem");
</script>