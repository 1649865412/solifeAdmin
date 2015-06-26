function countrySelector(id,value){
	__fillToSelector(__ctxPath+"/system/region/byType.html?regionType=1",id,value);
}

function stateSelector(pid,id,value){
	if(pid){
		__fillToSelector(__ctxPath+"/system/region/byParentIdAndType.html?regionType=2&pid="+pid,id,value);
	}else{
		$j("#"+id).empty();
		$j("#"+id).append('<option value="">'+__FMT.select+'</option>');
	}
}
function __fillToSelector(url,id,value){
	$j.ajax({
   type: "GET",
   dataType:"json",
   async: false,
   url: url,
   success:function (result){
   		if (result.status == 1) {
			var regionList = result.data;
			$j("#"+id).empty();
			$j("#"+id).append('<option value="">'+__FMT.select+'</option>');
			for(var i=0;i<regionList.length;i++){
				var region=regionList[i];
				var selectStr="";
				if(region.id==value||region.name==value){
					selectStr=' selected="selected"';
				}
				$j("#"+id).append('<option value="'+region.id+'"'+selectStr+'>'+region.name+'</option>');
			}
		}else{
			alert(result.msg);
		}
		}
	});
	
}


