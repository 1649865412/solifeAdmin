<%@ include file="/common/taglibs.jsp"%>
<app:pageHeading pageHeadingKey="userAwardList.heading" />

<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoMultiDelete(this);" />
</content>

	<app:ui_leftMenu>
		<div class="sidebar-left">
			<app:ui_tabs tabsId="left_menu_tabs" />
			<form name="searchUserAwardForm" method="post" action="${ctxPath}/activity/userAward.html" onsubmit="return false;">
				<div class="tab" id="left_menu_tabs">
					<ul>
						<li>
							<a href="#glSearchBar"><fmt:message key="yourposition.search" />
							</a>
						</li>
					</ul>
					<search:searchBox>
						<search:input attrNameKey="salesOrder.customerEmail" attrPath="s.customer.email" datatype="String" operator="LIKE"/>
						<div class="title">中奖等级</div>
						<div>
							<select name="COL@s.awardLevel@Integer@EQ" id="awardLevel" style="width:150px" >
								<option value=""></option>
								<c:forEach items="${awardList}" var="award">
									<option value="${award.level }"  <c:if test="${sc.param['COL@s.awardLevel@Integer@EQ'] == award.level }">selected</c:if>>${award.title }
								</c:forEach>
								<option value="-1">没有中奖</option>
							</select>
						</div>
					</search:searchBox>
					
				</div>
			</form>
		</div>
	</app:ui_leftMenu>
	<!--listing box start-->
<form class="mainForm" name="mainForm" method="post" action="${ctxPath}/activity/userAward.html">
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath"
		value="/activity/userAward.html?doAction=edit&from=list" scope="page" />
	
		<c:set var="checkAll">
			<input type="checkbox" name="allbox" onclick="checkAll(this.form)"
				style="margin: 0 0 0 0px" />
		</c:set>
		<display:table name="${userAwardList}" cellspacing="0" cellpadding="0"
			uid="item" class="table-list" export="false" requestURI="">
			<display:column style="width: 3%" title="${checkAll}" media="html">
				<input type="checkbox" name="multiIds"
					value="${item.id}" class="checkbox"
					title="${item.customer.email }的中奖信息" />
			</display:column>
			
		<display:column sortable="false" headerClass="w150" class="w150" title="中奖客戶" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator">
			${item.customer.email}
		</display:column>
		<display:column sortable="false" headerClass="w150" class="w150" title="中奖等級" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator">
			${item.awardTitle}
		</display:column>
		<display:column sortable="false" headerClass="w150" class="w150" title="奖品名称" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator">
			${item.prize}
		</display:column>
		<display:column sortable="false" headerClass="w150" class="w150" title="中奖时间" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator">
			<fmt:formatDate value="${item.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
		</display:column>
		
		</display:table>
		<%@ include file="/common/pagingOnlyNew.jsp"%>
</form>

<script type="text/javascript">
</script>
