<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ui_QuickTip" value="true" scope="request"/>
<%@ attribute  name="id" type="java.lang.String" required="true" description="please define the ID" %>
<%@ attribute name="type" type="java.lang.String" description="the value can be 'help' or 'error'" %>
<input type="hidden" id="${id}_text" value="<jsp:doBody/>" />
<script type="text/javascript" defer>
$j(document).ready(function(){
     var __type = '<c:if test="${not empty type}">${type}</c:if>';
     if(type='')
       type='help';
      var config = {
         source:'text',
         title:'Help',
         text:$j("#${id}_text").val(),
         type:__type
      }
      $j("#${id}").PQuickTip(config);     	
});
</script>
<img id="${id }" width="14" height="14" src="${ctxPath }/images/validate/help.png"/>