<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%--
Note: to use this file, include this JSP inside a form.
Then in the controller, use getPagingBean() to pass to the BO and then DAO.
--%>
<c:if test="${empty pagingId}"><c:set var="pagingId" value="${param.pagingId}"></c:set></c:if>
<script type="text/javascript" defer>
function gotoPage${pagingId}(pageNo) {
	$j("#PrmPageNo${pagingId}").attr("name","PrmPageNo").val(pageNo);
	<%-- 当页面存在fnOnGoToPage时，不自动提交表单，调用fnOnGoToPage --%>
	try{
		fnOnGoToPage${pagingId}(pageNo);
	}catch(e){
		document.getElementById("PrmItemsPerPage${pagingId}").form.submit();
	}
	return false;
}
</script>
<c:if test="${sc.totalPageCount>0}">
<div class="box-paging">
<TABLE class=table-paging cellSpacing="1" cellPadding="0" border="0">
	<TBODY>
		<TR align=middle>
			<TD class=paging-total
				title="<fmt:message key="list.paging.totalItems"/>">
				${sc.totalCount}
			</TD>
			<TD class=paging-total
				title="<fmt:message key="list.paging.currPage"/>/<fmt:message key="list.paging.totalPage"/>">
				${sc.pageNo}/${sc.totalPageCount}
			</TD>
			<c:if test="${sc.totalPageCount>1}">
			<TD class=paging-noselected>
				<A title=<fmt:message key="list.paging.goto1"/>
					<fmt:message key="list.paging.gotoN"><fmt:param>1</fmt:param></fmt:message>
					href="javascript:gotoPage${pagingId}(1);void(0)"><img
						src="${ctxPath }/images/back1.gif" width="12" height="12" /> </A>
			</TD>
			<TD class=paging-noselected>
				<SPAN style="WIDTH: 6px"><a
					title="<fmt:message key="list.record.prev"/>"
					href="javascript:gotoPage${pagingId}(<c:choose><c:when test="${(sc.pageNo-1) lt 1}">1</c:when><c:otherwise>${sc.pageNo-1}</c:otherwise></c:choose>);void(0)"><img
							src="${ctxPath }/images/back2.gif" width="12" height="12" /> </a>
				</SPAN>
			</TD>
				<c:set var="minPager"
					value="${(sc.totalPageCount-sc.pageNo>=4)?(sc.pageNo >= 5 ? sc.pageNo - 4
                      : 1):(sc.pageNo >= (5 + 4 - sc.totalPageCount + sc.pageNo) ? (sc.pageNo
                              - 8 + sc.totalPageCount - sc.pageNo) : 1)}" />
				<c:set var="maxPager"
					value="${(minPager + 8 > sc.totalPageCount) ? sc.totalPageCount : (minPager + 8)}" />
				<c:forEach var="idx" begin="${minPager}" end="${maxPager}"
					step="1">
					<c:if test="${sc.pageNo!=idx}">
						<TD class="paging-noselected">
							<A href="javascript:gotoPage${pagingId}(${idx});void(0)"
								title="<fmt:message key="list.paging.gotoN"><fmt:param>${idx}</fmt:param></fmt:message>">${idx}
							</A>
						</TD>
					</c:if>
					<c:if test="${sc.pageNo==idx}">
						<TD class="paging-selected">
							${idx}
						</TD>
					</c:if>
				</c:forEach>
			<TD class="paging-noselected">
				<SPAN style="WIDTH: 6px"><a
					title="<fmt:message key="list.record.next"/>"
					href="javascript:gotoPage${pagingId}(<c:choose><c:when test="${(sc.pageNo+1) gt sc.totalPageCount}">${sc.totalPageCount}</c:when><c:otherwise>${sc.pageNo+1}</c:otherwise></c:choose>);void(0)"><img
							src="${ctxPath }/images/next1.gif" width="12" height="12" /> </a>
				</SPAN>
			</TD>
			<TD class=paging-noselected>
				<a title="<fmt:message key="list.paging.gotoEnd"/>"
					href="javascript:gotoPage${pagingId}(${sc.totalPageCount});void(0)"><img
						src="${ctxPath }/images/next2.gif" width="12" height="12" /> </a>
			</TD>
			</c:if>
			<TD class="paging-noselected">
				<select class="form-inputbox" name="PrmItemsPerPage" id="PrmItemsPerPage${pagingId}" onchange="return gotoPage${pagingId}(1);">
					<option value="10"
						<c:if test="${sc.pageSize==10}">selected</c:if>>
						10
					</option>
					<option value="20"
						<c:if test="${sc.pageSize==20}">selected</c:if>>
						20
					</option>
					<option value="30"
						<c:if test="${sc.pageSize==30}">selected</c:if>>
						30
					</option>
					<option value="50"
						<c:if test="${sc.pageSize==50}">selected</c:if>>
						50
					</option>
					<option value="100"
						<c:if test="${sc.pageSize==100}">selected</c:if>>
						100
					</option>
				</select>
				<INPUT style="WIDTH: 25px" value="${sc.pageNo}" id="pager_input${pagingId}" />
				<a href="javascript:gotoPage${pagingId}($('pager_input${pagingId}').value);void(0);">Go</a>
				<input type="hidden" id="PrmPageNo${pagingId}" value="${sc.pageNo}" name="PrmPageNo">
			</TD>
		</TR>
	</TBODY>
</TABLE>
<input type="button" id="paging_nav_btn" name="paging_nav_btn"
	style="width:0px;height:0px;visibility:hidden">
</div>
</c:if>