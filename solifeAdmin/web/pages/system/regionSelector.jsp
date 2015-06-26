<%@ include file="/common/taglibs.jsp"%>
<c:if test="${!empty param.regionId && param.regionId eq '0'}">
<%@ include file="/decorators/include/styles.jspf"%>
<div id="retionSelector_tree_${param.id}" style="width:${param.width}px;">
<ul class="simpleTree">
	<li class="root" id='0'>
		<span><fmt:message key="regionSelector.region"/></span>
		<ul>
</c:if>

		 <c:forEach items="${childJTreeNodes}" var="region">
		   	<c:choose>                                    
				<c:when test="${region.leaf}">
					<li id="${region.id}"><span class="text">${region.text}</span></li>
				</c:when>
		       <c:otherwise>
		       		<li id="${region.id}"><span class="text">${region.text}</span>
						<ul class="ajax">
							<li >
							<c:choose>
								<c:when test="${!empty param.regionId && param.regionId eq '0'}">
									{url:${ctxPath}/selector/regionSelector.html?regionId=${region.id}&amp;regionType=${param.regionType}}
								</c:when>
								<c:otherwise>
									{url:${ctxPath}/selector/regionSelector.html?regionId=${region.id}}
								</c:otherwise>
							</c:choose>
							</li>
						</ul>
					</li>
		       </c:otherwise>
		    </c:choose>
		 </c:forEach>
<c:if test="${!empty param.regionId&& param.regionId eq '0'}">
		</ul>
	</li>	
</ul>
</div>
</c:if>