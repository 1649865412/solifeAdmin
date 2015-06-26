<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<c:if test="${param.multiSelect}">
已选择的用户：<span id="selectedUser_${param.id}"></span>
<div><a onclick="fnRemoveAll${param.id}();">移除所有已选择用户</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick="fnConfirmSelectedUser${param.id}();">确认</a></div>
</c:if>
<table width="98%" border="0" cellspacing="0" cellpadding="0" align="center" style="font-size: 12px;">
	<tr>
		<td valign="top" width="25%">
			<div class="content" id="userSelectorSearch_${param.id}">
				<div class="title">
					<fmt:message key="appAdmin.username" />
				</div>
				<input name="COL@s.username@String@LIKE" type="text"  class="Field200" />
				<div class="blank10"></div>
				<div class="title">
					<fmt:message key="appAdmin.email" />
				</div>
				<input name="COL@s.email@String@LIKE" type="text"  class="Field200" />
				<div class="blank10"></div>
				<input type="button" id="SearchButton" name="SearchButton" onclick="fn${param.id}GetData();" value="<fmt:message key="button.search"/>" class="btn-search"/>
				<div class="blank10"></div>
				需选择用户的请搜索后在右侧列表双击相应编码或名称！
				<input type="hidden" name="multiSelect" id="multiSelect" value="${param.multiSelect}">
				<input type="hidden" name="id" value="${param.id}">
			</div>
		</td>
		<td valign="top">
			<div id="userSelectorDataList_${param.id}">
				<%--@ include file="include/userSelectorDataList.jsp"--%>
			</div>
		</td>
	</tr>
</table>