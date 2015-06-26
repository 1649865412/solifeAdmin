<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Testing page</title>
		<%@ include file="/decorators/include/styles.jspf"%>
		<%@ include file="/decorators/include/javascripts.jspf"%>
	</head>
	<body>
		<div id="testTree" style="width:200px;overflow: auto;">
		<ul class="simpleTree">
			<li class="root" id='1'>
				<span>产品目录</span>
				<ul>
					<li class="open" id='2'>
						<span>Tree Node 1</span>
						<ul>
							<li id='3'>
								<span>Tree Node 1-1</span>
								<ul class="ajax">
									<li id='4'>
										{url:testTreeData.html?tree_id=1}
									</li>
								</ul>
							</li>
						</ul>
					</li>

					<li id='5'>
						<span>Tree Node 2</span>
						<ul>

							<li id='6'>
								<span>Tree Node 2-1</span>
								<ul>

									<li id='7'>
										<span>Tree Node 2-1-1</span>
									</li>

									<li id='8'>
										<span>Tree Node 2-1-2</span>
									</li>

									<li id='9'>
										<span>Tree Node 2-1-3</span>
									</li>

									<li id='10'>
										<span>Tree Node 2-1-4</span>
										<ul class="ajax">
											<li id='11'>
												{url:testTreeData.html?tree_id=2}
											</li>
										</ul>
									</li>

								</ul>
							</li>

							<li id='12'>
								<span>Tree Node 2-2</span>
								<ul>

									<li id='13'>
										<span>Tree Node 2-2-1</span>
									</li>

								</ul>
							</li>


							<li id='14'>
								<span>Tree Node 2-3</span>
								<ul>

									<li id='15'>
										<span>Tree Node 2-3-1</span>
										<ul>

											<li id='16'>
												<span>Tree Node 2-3-1-1</span>
											</li>

											<li id='17'>
												<span>Tree Node 2-3-1-2</span>
											</li>
										</ul>
									</li>

								</ul>
							</li>
						</ul>
					</li>
				</ul>
			</li>
		</ul>
	</div>
	<app:ui_tree treeId="testTree"/>
	<%@ include file="/decorators/include/uiResource.jspf"%>
	</body>
</html>