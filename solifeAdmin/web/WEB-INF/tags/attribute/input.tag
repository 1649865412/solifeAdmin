<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>

<%@tag import="com.cartmatic.estore.common.model.customer.Customer"%>
<%@tag import="com.cartmatic.estore.common.model.content.Content"%>
<%@tag import="com.cartmatic.estore.common.model.catalog.Category"%>
<%@tag import="com.cartmatic.estore.common.model.catalog.Product"%>

<%@ attribute name="attribute" required="true" description="en entity of Attribute in any of the scope" type="com.cartmatic.estore.common.model.attribute.Attribute"%>
<%@ attribute name="entity" description="one entity of Product、Customer、Content、Category、Salesorder" type="java.lang.Object" %>
<%@ attribute name="isdisplayHelp" description="setting the value of this attribute to 'true' to display a div content ico" type="java.lang.Boolean"%>
<%@ attribute name="cssName" type="java.lang.String" description="input类型的css"%>
<%@ attribute name="cssNameForTextarea" type="java.lang.String" description="textarea类型的css"%>
<%@ attribute name="id" type="java.lang.String" description="可以通过此ID来获取该元素" %>
<%
if(entity != null){
   if(entity instanceof Customer){
       Customer value = (Customer) entity;
       request.setAttribute("attrValueSet",value.getCustomerAttrValues());
   }
   else if(entity instanceof Content){
       Content value = (Content) entity;
       request.setAttribute("attrValueSet",value.getContentAttrValues());
   }
   else if(entity instanceof Category){
       Category value = (Category) entity;
       request.setAttribute("attrValueSet",value.getCategoryAttrValues());
   }
   else if(entity instanceof Product){
       Product value = (Product) entity;
       request.setAttribute("attrValueSet",value.getProductAttrValues());
   }
}   
%>
<c:if test="${not empty entity}">
<c:forEach var="attr_value_set"  items="${attrValueSet}">
  <c:if test="${attr_value_set.attributeId eq attribute.attributeId}">
       <c:set var="attr_value" value="${attr_value_set.attributeValue}"/>
       <c:set var="attr_name" value="cartmaticAttr--${attribute.attributeCode}__${attribute.attributeType }__${attribute.attributeDataType }__${attr_value_set.id }"/>
  </c:if>
</c:forEach>
</c:if>
<c:if test="${not empty id}">
   <c:set var="aid" value="${id}"/>
</c:if>
<c:if test="${empty id}">
   <c:set var="aid" value="${attribute.attributeCode }_id"/>
</c:if>
<c:if test="${empty attr_name}">
<c:set var="attr_name" value="cartmaticAttr--${attribute.attributeCode}__${attribute.attributeType }__${attribute.attributeDataType }"/>
</c:if>
<c:set var="default_value" value="${attribute.defaultValue}"/>
<c:choose>
  <c:when test="${attribute.attributeDataType == 1}">
     <c:set var="array" value='${fn:split(default_value,",")}'/>
     <c:set var="value_array" value='${fn:split(attr_value,",")}'/>
     <c:forEach var="item0" items="${array}" varStatus="s">
         <span <c:if test="${not empty cssName }">class="${cssName }"</c:if>><input value="${item0 }" type="checkbox" id="${aid}${s.index}"
          <c:forEach var="v" items="${value_array}">
             <c:if test="${v eq item0 }">checked="checked"</c:if>
          </c:forEach>
           name="${attr_name }" <c:if test="${attribute.isRequired}">validConf="required"</c:if>/><label for="${aid}${s.index}"><c:out value="${item0}"/></label>&nbsp;
          <input type="hidden" value="" name="_${attr_name }"/>
          </span>
     </c:forEach>
  </c:when>
  <c:when test="${attribute.attributeDataType == 2}">
       <input type="text" id="${aid}" <c:if test="${not empty cssName }">class="${cssName }"</c:if> name="${attr_name }" value="<c:out value="${attr_value==null ?default_value:attr_value }"></c:out>"
         validConf="<c:if test="${attribute.isRequired}">required,</c:if>url"
        />
  </c:when>
  <c:when test="${attribute.attributeDataType == 3}">
       <input type="text" id="${aid }" name="${attr_name }" value="<c:out value="${attr_value==null ? default_value : attr_value }"></c:out>"
         validConf="<c:if test='${attribute.isRequired}'>required</c:if>"/>
          <span id="aimg_${aid}_btnPlaceHolderId"></span>
         <cartmatic:img url="${attr_value}" mediaType="other" width="128px" height="128px" id="aimg_${aid}"></cartmatic:img>
         <cartmatic:swf_upload btnPlaceHolderId="aimg_${aid}_btnPlaceHolderId" fileInputId="${aid}" previewImg="aimg_${aid}"></cartmatic:swf_upload>
  </c:when>
  <c:when test="${attribute.attributeDataType == 4}">
      <input type="text" id="${aid}" <c:if test="${not empty cssName }">class="${cssName }"</c:if> name="${attr_name }" 
         value="<c:out value="${attr_value==null ? default_value : attr_value }"></c:out>"
         validConf="<c:if test="${attribute.isRequired}">required,</c:if>integer"
       />
  </c:when>
  <c:when test="${attribute.attributeDataType == 5}">
     <input type="text" id="${aid}" <c:if test="${not empty cssName }">class="${cssName }"</c:if> name="${attr_name }" value="<c:out value="${attr_value==null ? default_value : attr_value }"></c:out>"
       validConf="<c:if test="${attribute.isRequired}">required,</c:if>double"
     />
  </c:when>
  <c:when test="${attribute.attributeDataType == 6}">
     <input type="checkbox" id="${aid}"
     <c:if test="${attr_value == 1 }">checked="checked"</c:if>
      <c:if test="${not empty cssName }">class="${cssName }"</c:if> name="${attr_name}"/>&nbsp;
     
     <input type="hidden" value="0" name="_${attr_name }"/>
  </c:when>  
  <c:when test="${attribute.attributeDataType == 7}">
     <input name="${attr_name }" id="${aid}" <c:if test="${not empty cssName }">class="${cssName }"</c:if> value="<c:out value="${attr_value==null ? default_value : attr_value }"></c:out>" type="text"
      validConf="date<c:if test="${attribute.isRequired}">,required</c:if>">    
     <app:ui_datePicker outPut="${aid}" />  
  </c:when> 
  <c:when test="${attribute.attributeDataType == 8}">
     <c:set var="array" value='${fn:split(default_value,",")}'/>
     <c:set var="value_array" value='${fn:split(attr_value,",")}'/>
     <c:if test=""></c:if>
     <select name="${attr_name }" id="${aid}" <c:if test="${not empty cssName }">class="${cssName }"</c:if>  <c:if test="${attribute.isRequired }">validConf="required"</c:if>>
       <option value=""><fmt:message key="form.select.all"/> </option>
       <c:forEach var="item0" items="${array}">
             <option
             <c:forEach var="item1" items="${value_array }">
              <c:if test="${item1 eq item0}">selected="selected"</c:if>
              </c:forEach>
             value="${item0}">${item0}</option>
       </c:forEach>
     </select>
  </c:when>  
  <c:when test="${attribute.attributeDataType == 9}">
     <c:set var="array" value='${fn:split(default_value,",")}'/>
     <c:set var="value_array" value='${fn:split(attr_value,",")}'/>
     <c:forEach var="item1" items="${array}" varStatus="s">
        <span <c:if test="${not empty cssName }">class="${cssName }"</c:if>><input id="${aid}${s.index}"
           <c:forEach var="item0" items="${value_array }">
             <c:if test="${item0 eq item1 }">checked="checked"</c:if>
           </c:forEach>
         name="${attr_name}" value="${item1}" type="radio"
           <c:if test="${attribute.isRequired }">validConf="required"</c:if>
        /><label for="${aid}${s.index}">${item1}</label></span>&nbsp;
     </c:forEach>
  </c:when>
  <c:when test="${attribute.attributeDataType == 10}">
      <input type="text" id="${aid}" <c:if test="${not empty cssName }">class="${cssName }"</c:if> name="${attr_name }" value="<c:out value="${attr_value==null ? default_value : attr_value }"></c:out>"
        validConf="<c:if test="${attribute.isRequired}">required,</c:if>maxlength=128"
       />
  </c:when>
  <c:otherwise>
      <textarea name="${attr_name }" id="${aid}" <c:if test="${not empty cssNameForTextarea }">class="${cssNameForTextarea }"</c:if> <c:if test="${attribute.isRequired }">validConf="required"</c:if> ><c:out value="${attr_value==null ? default_value : attr_value }"></c:out></textarea>
  </c:otherwise>
</c:choose>
<c:if test="${isdisplayHelp and ! empty attribute.description}">
      <cartmatic:ui_tip id="${aid}_tip" type="help">${attribute.description}</cartmatic:ui_tip>
</c:if>