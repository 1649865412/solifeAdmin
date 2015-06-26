<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Testing page</title>
		<%@ include file="/decorators/include/styles.jspf"%>
		<%@ include file="/decorators/include/javascripts.jspf"%>
		<style type="text/css">
.attribute_description {
	background-color: green;
}
</style>

	</head>
	<body>
			<table>
				<c:forEach var="item" items="${customer.customerAttrValues}">
					<tr>
						<td>
							${item.attributeName}:
						</td>
						<td>
						    <attribute:display item="${item}"/>
						</td>
				</c:forEach>
			</table>

			<br />

		</form>
	</body>
</html>
