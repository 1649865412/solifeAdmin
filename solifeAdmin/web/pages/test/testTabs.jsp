<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Testing page</title>
		<%@ include file="/decorators/include/javascripts.jspf"%>
		<%@ include file="/decorators/include/styles.jspf"%>
	</head>
	<body>
	<div id="productTab">
	<ul>
    	<li><a href="#productInfo">基本信息</a></li>
        <li><a href="#productSkuInfo">productSkuInfo</a></li>
        <li class="selected"><a href="#featureInfo">featureInfo</a><input type="button" value="" class="close" /></li>
         <li><a href="#seoInfo" >搜索引擎333</a><input type="button" value="" class="close" /></li>
    </ul>
    <div class="blank10"></div>
    <div id="productInfo" style="display:none" class="content">
    	基本信息 AAAAAAAAAAAAA
    </div>
    <div id="productSkuInfo" style="display:none" class="content">
    	productSkuInfoBBBBBBBBBB
    </div>
    <div id="featureInfo" style="display:none" class="content">
    	featureInfoCCCCCCCCCCCC
    </div>
    <div id="seoInfo" style="display:none" class="content">
    	seoInfoDDDDDDDDDDDDDDDDDDDD
    </div>
</div>
<div class="blank10"></div>
<div id="productTab2">
    <ul>
    	<li><a href="#productInfo">基本信息</a></li>
        <li><a href="#productSkuInfo">productSkuInfo</a></li>
        <li class="selected"><a href="#featureInfo">featureInfo</a></li>
         <li><a href="#seoInfo">搜索引擎333</a></li>
    </ul>
    <div class="blank10"></div>
    <div id="productInfo" style="display:none">
    	基本信息 AAAAAAAAAAAAA
    </div>
    <div id="productSkuInfo" style="display:none">
    	productSkuInfoBBBBBBBBBB
    </div>
    <div id="featureInfo" style="display:none">
    	featureInfoCCCCCCCCCCCC
    </div>
    <div id="seoInfo" style="display:none">
    	seoInfoDDDDDDDDDDDDDDDDDDDD
    </div>
</div>
<div id="testBtn">
<input type="button" value="test"/>
</div>
<input type="button" value="add tab" onclick="addtab()"/>
<input type="button" value="add btn" onclick="addbtn()"/>
<input type="button" value="add btn2" onclick="test1111();"/>
<input type="button" value="add btn2" onclick="addbtn2();"/>
<div id="test_tab11" style="display:none">
    	test_tab11 test_tab11  test_tab11
</div>
		<script type="text/javascript">
	var t1 = $j("#productTab").appTabs();
	var t2 = t1.appTabs("remove", 1);
	//$j("#productTab2").appTabs({type:2});
	//$j("#productTab").appTabs({selected:1});//默认显示productSkuInfo
	//log($j("#productTab input.close[type='button']" ));
	$j("ul:first li input.close[type='button']", t1).live("click", function() {
		var index = $j("ul:first li", t1).index($j(this).parent());
		t1.appTabs("remove", index);
	});
	t1.appTabs("add", "#test_tab11", "label test11");
	t1.appTabs("select", 2);
	//log(t1.appTabs( "option", "selected" ));
	//t1.appTabs( "remove", index );
	//log($j("input[type='button']",$j("#testBtn")));
	$j("input[type='button']", $j("#testBtn")).live("click", function() {
		log(111)
	});
	var i = 0;
	function addbtn() {
		i++;
		$j("#testBtn").append('<input type="button" value="test' + i + '"/>');
	}
	function addbtn2() {
		$j("input[type='button']", $j("#testBtn")).bind("click", function() {
			log(222)
		});
	}
	var k = i
	function addtab() {
		k++;
		t1.appTabs("add", "#test_tab11", "label test" + k);
		$j("#test_tab11").html(k + "\t" + k + "\t" + k);
	}
</script>
		<%--
<app:ui_tabs tabsId="productTab"/>--%>
		<app:ui_tabs tabsId="productTab2" type="2" selected="1" />
	</body>
</html>
