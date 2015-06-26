<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div id="productMediaInfo" style="display:none;">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
		<tr>
			<td>
				<input type="checkbox" id="mediaCheck" name="mediaCheck" value="1" />
			</td>
			<td>
				<fmt:message key="product.tab.media" />
			</td>
			<td>
				<fmt:message key="productDetail.moreImage" />
				:
				<span id="productMoreImages"> </span>
				<br />
				<fmt:message key="productDetail.accessories" />
				:
				<span id="productAccessories"> </span>
			</td>
		</tr>
	</table>
</div>

