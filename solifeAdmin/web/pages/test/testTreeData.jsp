<%@ include file="/common/taglibs.jsp"%>
<c:if test="${param.tree_id == '1'}">
	<li id="a11"><span class="text">Ajax Node tree_id[1]</span></li>
	<li id="a12"><span class="text">Ajax Node tree_id[1]</span></li>
	<li id="a13"><span class="text">Has more...</span>
		<ul class="ajax">
		<li >
		{url:testTreeData.html?tree_id=3}
		</li>
		</ul>
	</li>
</c:if>
<c:if test="${param.tree_id == '2'}">
	<li id="a21"><span class="text">中草药 tree_id[2]</span></li>
	<li id="a22"><span class="text">中草药 tree_id[2]</span></li>
	<li id="a23"><span class="text">Hase more...</span>
	<ul class="ajax">
		<li >
		{url:testTreeData.html?tree_id=2}
		</li>
	</ul>
	</li>
</c:if>
<c:if test="${param.tree_id == '3'}">
	<li id="a31"><span class="text">Ajax Node tree_id[3]</span></li>
	<li id="a32"><span class="text">Ajax Node tree_id[3]</span></li>
	<li id="a33"><span class="text">Has more...</span>
		<ul class="ajax">
		<li >
		{url:testTreeData.html?tree_id=4}
		</li>
		</ul>
	</li>
</c:if>
<c:if test="${param.tree_id == '4'}">
	<li id="a41"><span class="text">Ajax Node tree_id[4]</span></li>
	<li id="a42"><span class="text">Ajax Node tree_id[4]</span></li>
	<li id="a43"><span class="text">Has more...</span>
		<ul class="ajax">
		<li >
		{url:testTreeData.html?tree_id=5}
		</li>
		</ul>
	</li>
</c:if>
<c:if test="${param.tree_id == '5'}">
	<li id="a51"><span class="text">Ajax Node tree_id[5]</span></li>
	<li id="a52"><span class="text">Ajax Node tree_id[5]</span></li>
	<li id="a53"><span class="text">Has more...</span>
		<ul class="ajax">
		<li >
		{url:testTreeData.html?tree_id=1}
		</li>
		</ul>
	</li>
</c:if>