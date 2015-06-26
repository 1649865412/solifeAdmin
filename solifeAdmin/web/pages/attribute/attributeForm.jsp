<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<app:pageHeading  entityName="${attribute.attributeName}" entityHeadingKey="attributeDetail.heading" />
<title><fmt:message key="attribute.attributeName"/></title>
<content tag="heading"><fmt:message key="attributeDetail.heading"/></content>
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this,'attributeName');" />
	<c:if test="${attribute.attributeId!=null}">
		<cartmatic:cartmaticBtn btnType="delete" onclick="return deleteSubmit();" />
	</c:if>
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<script>
  var selectDes = '<fmt:message key="attribute.attributeDataType.selectDescription"/>';
  var max255 = '<fmt:message key="maxChars255"/>'
  var maxChars32 = '<fmt:message key="maxChars32"/>'
  var dateForm = '<fmt:message key="dateFormat"/>'
  var start = '<fmt:message key="validate.startwith"><fmt:param value="-"/></fmt:message>'
  var end = '<fmt:message key="validate.endwith"><fmt:param value="_"/></fmt:message>'
  
  var actionPath = '<c:choose><c:when test="${not empty attribute.attributeId}">&from=edit</c:when><c:otherwise>&from=new</c:otherwise></c:choose>'
  function fnSaveAttribute(){
     var attrform = $j("form.mainForm").get(0);
     //alert("xxx");
     if(validateForm(attrform)){        
        attrform.submit();
     }
     else{
        return false;
     }
  }
  
  function changeDescription(value){
    if(value == 1 || value == 9 || value == 8){
       $j('#description').html(selectDes);
       
    }
    else if(value == 10){
       $j('#description').html(maxChars32);
    }
    else if(value==7){
        $j('#description').html(dateForm);
    }
    else{
       $j('#description').html(max255);
    }
  }
  
  $j(document).ready(function(){
     
     $j("input[name='attributeDataType']").each(function(i){
        if($j(this).val()==${attribute.attributeDataType==null ? 10 : attribute.attributeDataType}){
           $j(this)[0].checked=true;
        }
        $j(this).attr('validConf','required')
     })
     $j("input[name='attributeName']").attr("validConf","required");
     $j("input[name='attributeCode']").attr('validConf','required,code');
     
     <c:if test="${attributeCode_repeat}">
        __handleVaErrMsg($j("#attributeCode")[0],"<fmt:message key="attribute.attributeCode.repeated"/>");
     </c:if>
     
     autoApplyValidate();
     var attrId = "${attribute.attributeId}";
     var array = $j("input[name='attributeDataType']");
     array.each(function(i){
        if(attrId != null && attrId != ""){
              $j(this).attr('disabled','true');
        } 
        $j(this).bind('click',function(){
           changeDescription($j(this).val());
        })
     })
     
     var attributeCodeInput = $j('#attributeCode');
     attributeCodeInput.bind('blur',function(){
        checkAttributeCode($j(this).val());
      }
     )
     
     if(attrId != null && attrId != ""){
        $j('#attributeType').attr('disabled','true');
        $j('#attributeCode').attr('disabled','true');
     }
     
     var point = '${attribute.attributeDataType}';
     if(point!=''){
        changeDescription(point);
     }
  });
  
  function checkAttributeCode(){
     var value = $j("#attributeCode").val();
     if(endWith(value,"_")){
       __handleVaErrMsg($j("#attributeCode")[0],end);
       $j('#attributeCode').val("");
       $j('#attributeCode').focus();
       return false;
     }
     if(startWith(value,"-")){
       __handleVaErrMsg($j("#attributeCode")[0],start);
       $j('#attributeCode').val("");
       $j('#attributeCode').focus();
       return false;
     }
     $j.post(__ctxPath+"/system/attribute.html?doAction=getAttribute",{code:value},callBack,"json");
  }
  
  function endWith(str,testStr){
     if(str=="")return false;
     var len = str.length;
     if((len-1) == str.lastIndexOf(testStr)){
       return true
     }
     else
       return false
  }
  function startWith(str,testStr){
  if(str=="")return false;
    var len = str.length;
    if(str.indexOf(testStr)==0){
      return true;;
    }
    else 
      return false;
  }
  
  function callBack(result){
	 var arrribute=result.data;
     if(arrribute != null&& arrribute.arrributeId !="${attribute.attributeId}"){
		__handleVaErrMsg($j("#attributeCode")[0],"<fmt:message key="attribute.attributeCode.repeated"/>");
		$j('#attributeCode').val('');
		$j('#attributeCode').focus();
		return false;
     }
     else{
    	__handleVaErrMsg($j("#attributeCode")[0],"");
       	return true;
     }
  }
  
  function deleteSubmit(){
	  var msg="<fmt:message key="attribute.delete.alertMsg"/>".replace('{0}',$j("#attributeName").val());
	  return fnDoAction(null, "delete", msg);
  }

  function bulkUpdateAttrValueSubmit(){
	  return fnDoAction(null, "bulkUpdateAttrValue", "<fmt:message key="attribute.bulkUpdateAttrValue.alertMsg"/>");
 }
</script>

<form:form cssClass="mainForm" method="post" id="attribute" commandName="attribute" action="${ctxPath}/system/attribute.html" onsubmit="return validateForm(this);">
	<input type="hidden" name="attributeId" value="${attribute.attributeId}"/> 
	<input type="hidden" name="doAction" value="save"/>

<!-- attributeDataType starts -->
				<table class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
					<tr>
					   <th colspan="2"><fmt:message key="attribute.attributeDataType" /></th>
				    </tr>
					<tr>
					   <td class="FieldLabel">
					     <form:radiobutton value="1" path="attributeDataType"/> 
					     </td>
					   <td>
					    <fmt:message key="attribute.attributeDataType.multicheckboxes"/> <fmt:message key="attribute.attributeDataType.multicheckboxes.description"/>
					   </td>
                    </tr>
                    <tr>
					   <td class="FieldLabel">
					     <form:radiobutton value="2" path="attributeDataType"/> 
					    </td>
					   <td>
					    <fmt:message key="attribute.attributeDataType.url"/> <fmt:message key="attribute.attributeDataType.url.description"/>
					   </td>
                    </tr>
					<tr>
					   <td class="FieldLabel">
					     <form:radiobutton value="3" path="attributeDataType"/> 
					     </td>
					   <td>
					    <fmt:message key="attribute.attributeDataType.image"/> <fmt:message key="attribute.attributeDataType.image.description"/>
					   </td>
					</tr>
					<tr>
					   <td class="FieldLabel">
					     <form:radiobutton value="4" path="attributeDataType"/> 
					     </td>
					   <td>
					     <fmt:message key="attribute.attributeDataType.int"/>  <fmt:message key="attribute.attributeDataType.int.description"/>
					   </td>
					</tr>
					<tr>
					   <td class="FieldLabel">
					     <form:radiobutton value="5" path="attributeDataType"/> 
					     </td>
					   <td>
					    <fmt:message key="attribute.attributeDataType.float"/> <fmt:message key="attribute.attributeDataType.float.description"/>
					   </td>
                        </tr>
					<tr>
					   <td class="FieldLabel">
					     <form:radiobutton value="6" path="attributeDataType"/> 
					    </td>
					   <td>
					    <fmt:message key="attribute.attributeDataType.boolean"/> <fmt:message key="attribute.attributeDataType.boolean.description"/>
					   </td>
					</tr>
					<tr>
					   <td class="FieldLabel">
					     <form:radiobutton value="7" path="attributeDataType"/> 
					    </td>
					   <td>
					    <fmt:message key="attribute.attributeDataType.date"/> <fmt:message key="attribute.attributeDataType.date.description"/>
					   </td>
					</tr>
					<tr>
					   <td class="FieldLabel">
					     <form:radiobutton value="8" path="attributeDataType"/> 
					     </td>
					   <td>
					    <fmt:message key="attribute.attributeDataType.selectlist"/> <fmt:message key="attribute.attributeDataType.selectlist.description"/>
					   </td>
                        </tr>
					<tr>
					   <td class="FieldLabel">
					     <form:radiobutton value="9" path="attributeDataType"/> 
					     </td>
					   <td>
					    <fmt:message key="attribute.attributeDataType.radiobuttons"/> <fmt:message key="attribute.attributeDataType.radiobuttons.description"/>
					   </td>
					</tr>
					<tr>
                       <td class="FieldLabel">
					     <form:radiobutton  value="10" path="attributeDataType"/> 
					    </td>
					   <td>
					    <fmt:message key="attribute.attributeDataType.shorttext"/> <fmt:message key="attribute.attributeDataType.shorttext.description"/>
					   </td>
					</tr>
					<tr>
					   <td class="FieldLabel">
							<form:radiobutton value="11" path="attributeDataType" />
					   </td>
					   <td>
					     <fmt:message key="attribute.attributeDataType.longtext"/> <fmt:message key="attribute.attributeDataType.longtext.description"/>
					   </td>
					 </tr>
                    </table>						
<!-- attributeDataType ends -->
					
<!-- attribute info starts -->
					<table class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
                    <tr>
					   <th colspan="2"><fmt:message key="attribute.info" /></th>
				    </tr>
					<tr>
						<td class="FieldLabel">
							<StoreAdmin:label key="attribute.attributeName" />
						</td>
						<td>
							<form:input path="attributeName" />
						</td>
                         </tr>
					<tr>
						<td class="FieldLabel"><fmt:message key="attribute.attributeType" /></td>
						<td >
 					  	<form:select path="attributeType">
 					    <form:option value="1"><fmt:message key="attribute.attributeType.product"/></form:option>
 					    <form:option value="2"><fmt:message key="attribute.attributeType.content"/></form:option>
 					    <%--<form:option value="3"><fmt:message key="attribute.attributeType.customer"/></form:option>--%>
 					    <form:option value="5"><fmt:message key="attribute.attributeType.productcategory"/></form:option>
 						</form:select>
 						</td>
 					</tr>
 					<tr>
 					  <td class="FieldLabel"><fmt:message key="attribute.attributeUnit"/></td>
 					  <td>
 					     <form:input path="attributeUnit" />
 					  </td>
                       </tr>
					<tr> 					
 					  <td class="FieldLabel"><fmt:message key="attribute.defaultValue"/></td>
 					  <td>
 					     <form:input maxlength="255" size="60" path="defaultValue" /><span style="width: 200px;color:#666;margin-left:4px;" id="description"></span>
 					  </td>
 					  </tr>
 					<tr>
 					  <td class="FieldLabel"><fmt:message key="attribute.isRequired"/></td>
 					  <td>
 					     <form:checkbox  path="isRequired"  value=""/>
 					  </td> 
                       </tr>
					<tr>					
 					    <td class="FieldLabel">
							<StoreAdmin:label key="attribute.attributeCode" />
						</td>
 					    <td>
							<form:input path="attributeCode" />
						</td>
				    </tr>
 					<tr>
						 <td class="FieldLabel">
							<StoreAdmin:label key="attribute.description" />
						</td>
 					    <td>
							<form:textarea path="description" /><span class="icon_help"><cartmatic:ui_tip id="descriptTip"><fmt:message key="attribute.attributeType.descript"/></cartmatic:ui_tip></span>
						</td>
                         </tr>
                    <c:if test="${not empty attribute.attributeId}">
	 					<tr>
	 					  <td class="FieldLabel">
	 					  		批量修改属性
	 					  </td>
	 					  <td >
	 					     From<input id="fromValue" name="fromValue" value="${param.fromValue}" type="text" class="Field400">--> 
	 					     To<input id="toValue" name="toValue" value="${param.toValue}" type="text" class="Field400">
	 					     <cartmatic:iconBtn icon="check" textKey="button.small.update" onclick="bulkUpdateAttrValueSubmit();"/>
	 					     <br/>
	 					     <span style="width: 200px;color:#666;margin-left:4px;">针对已经存在于数据库中的属性记录,
	 					              可以通过这个功能批量修改属性值. 在From输入原来的值性值, 在To中输入想修改的值,点击"修改"就可以.</span>
	 					  </td>
	 					</tr>
 					</c:if>
  				</table>			
<!-- attribute info ends -->
</form:form>
