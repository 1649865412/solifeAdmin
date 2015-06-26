<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ui_QuickTip" value="true" scope="request"/>
<script type="text/javascript" defer>
$j(document).ready(function(){
   $j(".PTips").each(function(i){
      var config = {                     
         source:this.attributes["source"].value||'text',
         href:this.attributes["href"]?this.attributes["href"].value:'',
         text:this.attributes["text"]?this.attributes["text"].value:'',
         type:this.attributes["type"]?this.attributes["type"].value:'error',
         link:this.attributes["link"]?this.attributes["link"].value:undefined
      }
      $j(this).PQuickTip(config);     
   })	
});
</script>