<%@ include file="/common/taglibs.jsp"%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Testing page</title>
	<%@ include file="/decorators/include/styles.jspf"%>
	<%@ include file="/decorators/include/javascripts.jspf"%>
	<script type="text/javascript" src="${ctxPath}/scripts/jquery/plugins/form/jquery.form.js"></script>
	
	<script type="text/javascript" src="${ctxPath}/scripts/jquery/ui/ui.core.js"></script>
	<script type="text/javascript" src="${ctxPath}/scripts/jquery/ui/ui.draggable.js"></script>
	<script type="text/javascript" src="${ctxPath}/scripts/jquery/ui/ui.droppable.js"></script>
	<style>
  .block { 
    border: 2px solid #0090DF;
    background-color: #68BFEF;
    width: 100px; 
    height: 30px;
    margin: 10px; 
  }
  </style>
	
</head>
<script type="text/javascript">
<!--


var $=$j;




var fn1=function(){
$('#myForm1').ajaxForm({dataType:'json',success: fncallback});
}
function fn2(){
var postData = $('#myForm1 :input').serialize();
	jQuery.post('${ctxPath}/test/formplugin.html?doAction=formplugin',postData,fncallback,"json");
}

$(document).ready(
fn1
);
$(document).ready(
function (){
	log($("#submitButton").attr("onclick"));
	$("#submitButton").removeAttr("onclick");
	$("#submitButton").click(fncallback);
alert("aaaaaaaaaaaa");
	$("form").submit(function(){return false;});
	var nnnn="Check";
	var ccc=$(':input[name='+nnnn+']:last');
	ccc.after("<span class=\"fieldError\" id=\"productCode.errors\">产品代码重复!</span>");
	log(ccc.parent());
	var pre=ccc.parent().prev();
	log(pre);
	pre.append('<img src="${ctxPath}/images/iconWarning.gif" alt="警告" class="validationWarning"/>');
}
);
function fncallback(aaaa) {
$("#branding").html("aaaaaaaaaaaaaa");
	$.each(aaaa,function(i,n){
     log(i+"___"+n);
     });
     
     log(aaaa.product.productName);
     log(aaaa.product.defaultProductSku.productSkuCode);
    log(aaaa.product.productSkus);
    log("_______________");
    var productSkus=aaaa.product.productSkus;
    $.each(productSkus,function(j,m){
    log(m.productSkuCode);
    });
     	var postData = $('#myForm1 :input').serialize();
     	log(postData);
     	log("______________________________");
        var data = $("#myForm1").formSerialize();
        log(data);
        log("========================");
        postData = unescape(postData).replace(/&/g,'<br />').replace(/_/g,' ');
        $('div.log').append('<p>You submitted the following information 1:</p><p>' + data + '</p>');
        $('div.log').append('<p>You submitted the following information 2:</p><p>' + postData + '</p>');
        //$('#test-form').resetForm();
     }
     
     
     $(document).ready(function(){
    $(".block").draggable({opacity: 0.55,stop:draggableHandler});
  });
     
   function draggableHandler(e,ui){
   
   log("draggable"+e.pageX+"__"+e.pageY+"___"+e.target+"___"+ui.helper);
   $(e.target).remove();
   var ddd=$("#ddddd");
   }
//-->
</script>
<form method="post" action="${ctxPath}/test/formplugin.html?doAction=formplugin" id="myForm1"><div>
        <input type="hidden" value="hiddenValue" name="Hidden"/>
        <table>
        <tbody><tr><td>Name:</td><td><input type="text" value="MyName1" name="Name"/></td></tr>
        <tr><td>Password:</td><td><input type="password" name="Password"/></td></tr>
        <tr><td>Multiple:</td><td><select multiple="multiple" name="Multiple">

            <optgroup label="Group 1">
                <option selected="selected" value="one">One</option>
                <option value="two">Two</option>
                <option value="three">Three</option>
            </optgroup>
            <optgroup label="Group 2">
                <option value="four">Four</option>

                <option value="five">Five</option>
                <option value="six">Six</option>
            </optgroup>
        </select></td></tr>
        <tr><td>Single:</td><td><select name="Single">
            <option selected="selected" value="one">One</option>
            <option value="two">Two</option>

            <option value="three">Three</option>
        </select></td></tr>
        <tr><td>Single2:</td><td><select name="Single2">
            <optgroup label="Group 1">
                <option selected="selected" value="A">A</option>
                <option value="B">B</option>
                <option value="C">C</option>
            </optgroup>
            <optgroup label="Group 2">
                <option value="D">D</option>
                <option value="E">E</option>
                <option value="F">F</option>
            </optgroup>
        </select></td></tr>

        <tr><td>Check:</td><td>
            <input type="checkbox" value="1" name="Check"/>
            <input type="checkbox" value="2" name="Check"/>
            <input type="checkbox" value="3" name="Check"/>
        </td></tr>
        <tr><td>Radio:</td><td>
            <input type="radio" value="1" name="Radio"/>
            <input type="radio" value="2" name="Radio"/>

            <input type="radio" value="3" name="Radio"/>
        </td></tr>
        <tr><td>Text:</td><td><textarea cols="20" rows="2" name="Text">This is Form1</textarea></td></tr>
        </tbody></table>
        <input type="reset" value="Reset" name="resetButton"/>
        <input type="submit" value="Submit1" name="submitButton" id="submitButton" onclick="alert('clicked')"/>
        <input type="image" src="submit.gif" value="Submit2" name="submitButton"/>
</div></form>
<div class="log"></div>
<div id="ddddd">
<div class="block">1</div>
<div class="block">2</div>
<div class="block">3</div>
<div class="block">4</div>
<div class="block">5</div>
<div class="block">6</div>
</div>