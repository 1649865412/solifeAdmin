/**
 * 使用方法:
 * name:提示框的标题,可以没有此属性也可以为空;
 * source:提示内容的来源,可以是ajax,也可以是text.默认是text的方式.
 * href:当source是ajax的时候,会通过此href来请求显示内容;当有link参数的时候,点击此元素是会连接到此link指定的页面.
 * text:当source是text的时候,会通过此text来指定显示内容，text可以是HTML格式的内容.
 * link:当时ajax形式的时候，点击的连接
 * 
 * @author huangwenmin
 * @copyright Cartmatic 2008-10-15
 */
(function($){
$.fn.PQuickTip = function(config){
	
	function genId(n) {//生成随机ID
	    var res = "";
	    for(var i = 0; i < n ; i ++) {
	        var id = Math.ceil(Math.random()*35);
	        res += id.toString();
	    }
	    return res;
    }

	
	function getElementWidth(e) {
		return e.offsetWidth;
	}
	function getAbsoluteLeft(e) {
		oLeft = $(e).offset().left;           
		return oLeft
	}
	function getAbsoluteTop(e) {
		oTop = $(e).offset().top;         
		if($("#"+rId+"_tip").height()+oTop>$(document).height()){
			oTop = oTop - ($("#"+rId+"_tip").height()+oTop-$(document).height());
		}
		return oTop-11;
	}
	function parseQuery ( query ) {
	   var Params = new Object ();
	   if ( ! query ) return Params;
	   var Pairs = query.split(/[;&]/);
	   for ( var i = 0; i < Pairs.length; i++ ) {
	      var KeyVal = Pairs[i].split('=');
	      if ( ! KeyVal || KeyVal.length != 2 ) continue;
	      var key = unescape( KeyVal[0] );
	      var val = unescape( KeyVal[1] );
	      val = val.replace(/\+/g, ' ');
	      Params[key] = val;
	   }
	   return Params;
	}
	function blockEvents(evt) {
	              if(evt.target){
	              evt.preventDefault();
	              }else{
	              evt.returnValue = false;
	              }
	}
	
	var de = document.documentElement;
	var w = self.innerWidth || (de&&de.clientWidth) || document.body.clientWidth;
	
	function isLeft(e){
	    var hasArea = w - getAbsoluteLeft(e);
	    if(hasArea>=320)
	       return true;
	    else
	       return false;
	}

	var option = $.fn.extend({},$(this).PQuickTip.defaults,config);
	$(this).data("opts",option);
	
	var clickElementx = 0;
	if(!$(this).attr("id"))return;
	var rId = $(this)[0].id==""?genId(5):$(this)[0].id;
	
	this.PQuickTip.removeTip($(this));//假如该元素已经绑定有QuickTip,则删除此QuickTip以防止一个元素绑定多个QuickTip。
	
	var type = $(this).data("opts").type=="error"?"content cRed":"content";
	$(this).data("tipId",rId+"_tip");
	
	if ($("#"+rId+"_tip").length == 0)
	{
		$("body").append("<div class='admin-window' id='"+rId+"_tip' ><div class='"+type+"' id='"+rId+"_tipc'><div class='loader'></div></div></div>");//right side
		$("#"+rId+"_tip").hide();  
	}
	function getY(obj){
	   var y1 = $("#"+rId+"_tip")[0].style.top.substring(0,$("#"+rId+"_tip")[0].style.top.length-2);
	   var y2 = obj.offset().top;
	   return parseInt(y2)-parseInt(y1)-5;
	}
	/**  
	 * 显示提示框
	 */
	function show(e){
		    el = e.data.obj[0]
		    obj = e.data.obj
			if($(obj).data("opts").source=="text"){
				$('#'+rId+'_tipc').html($(obj).data("opts").text);
				var clickElementy = getAbsoluteTop($(obj)[0]); 
				if(isLeft(el)){		
					var arrowOffset = getElementWidth(obj[0]);
					clickElementx = getAbsoluteLeft(obj[0]) + arrowOffset; 
					if($("#"+rId+"_tip").children("#icon").children(".photo-left").length==0){
						$("#"+rId+"_tip").children("#icon").remove();
						$("#"+rId+"_tipc").before("<div id='icon'><div class='photo-left'></div></div>");
					}  
				}else{
					clickElementx = getAbsoluteLeft(obj[0]) - 350 + 70;
					if($("#"+rId+"_tip").children("#icon").children(".photo-right").length==0){
						$("#"+rId+"_tip").children("#icon").remove();
						$("#"+rId+"_tipc").after("<div id='icon'><div class='photo-right'></div></div>");
					}
				}
			    $("#"+rId+"_tip").css({left: clickElementx+"px", top: clickElementy+"px",visibility:'visible'});
			    $("#"+rId+"_tip").find("#icon").css({paddingTop:getY(obj)+"px"});	
			    $("#"+rId+"_tip").show();
			}
			else{
			  try{
			  	var link = $(obj).data("opts").link
				if(link !== undefined){
				  $(el).bind('click',function(){window.location.href = link});
				  $(el).css('cursor','pointer');
				} 
				var clickElementy = getAbsoluteTop($(obj)[0]);  
				if(isLeft(el)){		
					var arrowOffset = getElementWidth(obj[0]);
					clickElementx = getAbsoluteLeft(obj[0]) + arrowOffset; 
					if($("#"+rId+"_tip").children("#icon").children(".photo-left").length==0){
						$("#"+rId+"_tip").children("#icon").remove();
						$("#"+rId+"_tipc").before("<div id='icon'><div class='photo-left'></div></div>");
					}  
				}else{
					clickElementx = getAbsoluteLeft(obj[0]) - 350 + 70;
					if($("#"+rId+"_tip").children("#icon").children(".photo-right").length==0){
						$("#"+rId+"_tip").children("#icon").remove();
						$("#"+rId+"_tipc").after("<div id='icon'><div class='photo-right'></div></div>");
					}
				}				
			    
			    $.ajax({
			      	url:$(obj).data("opts").href,
			      	type:'get',
			      	dataType:'html',
			      	success:			      	
			      	function(data){
			      		$('#'+rId+'_tipc').html(data);
						var clickElementy = getAbsoluteTop($(obj)[0]); 
						if(isLeft(el)){		
							var arrowOffset = getElementWidth(obj[0]);
							clickElementx = getAbsoluteLeft(obj[0]) + arrowOffset; 
							if($("#"+rId+"_tip").children("#icon").children(".photo-left").length==0){
								$("#"+rId+"_tip").children("#icon").remove();
								$("#"+rId+"_tipc").before("<div id='icon'><div class='photo-left'></div></div>");
							}  
						}else{
							clickElementx = getAbsoluteLeft(obj[0]) - 350 + 70;
							if($("#"+rId+"_tip").children("#icon").children(".photo-right").length==0){
								$("#"+rId+"_tip").children("#icon").remove();
								$("#"+rId+"_tipc").after("<div id='icon'><div class='photo-right'></div></div>");
							}
						}				      		
			            $("#"+rId+"_tip").css({left: clickElementx+"px", top: clickElementy+"px",visibility:'visible'});
			            $("#"+rId+"_tip").find("#icon").css({paddingTop:getY(obj)+"px"});	
			            $("#"+rId+"_tip").show();	
			      	}
			    });
			  }catch(eee){	  	
			    $('#'+rId+'_tipc').html("<h3>error:" + eee.message + "</h3>");
				$('.JT_loader').hide();
			  }
			}
	   }
    $(this).bind("mouseover",{obj:$(this)},
       function(e){
       	e.preventDefault();
       	 show(e);
       	// setTimeout(function(){$("#"+rId+'_tip').hide()},4000)
       }
     )//over时触发
     $(this).bind("mouseout",  function(e){
       	 $("#"+rId+'_tip').hide()
       }
      ) //out时触发
     return this;
}

/**
 * 默认配置信息
 */
$.fn.PQuickTip.defaults = {
	title:'',
	href:'',
	source:'text',
	text:'',
	type:'error' 
}

$.fn.PQuickTip.removeTip = function(obj){
		var rId = $(obj).data("tipId");
		if($('#'+rId).size()!=0){
		   $('#'+rId).remove()
		}
}
})(jQuery)