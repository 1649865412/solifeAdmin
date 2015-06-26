<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="supplier" tagdir="/WEB-INF/tags/supplier"%>
<app:pageHeading entityName="${supplier.supplierName}" entityHeadingKey="supplierDetail.heading" />
<content tag="buttons">
	<c:if test="${supplier.supplierId!=-1}">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this,'supplierName');" />
	</c:if>
	<%-- 
    <c:if test="${supplier.supplierId!=null}">
		<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoDelete(this);" />
	</c:if>
	--%>
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="supplier.*" />
	<form id="supplier" class="mainForm" action="${ctxPath}/supplier/supplier.html" method="post" onsubmit="return (validateSupplier(this)&&setContacts());">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
			<tr>
				<th colspan="2">
					<fmt:message key="supplierDetail.heading" />
					<input type="hidden" name="version" value="${supplier.version}"/> 
					<input type="hidden" name="supplierId" value="${supplier.supplierId}"/> 
				</th>
			</tr>
			<tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="supplier.supplierName" />
				</td>
				<td>
					<spring:bind path="supplier.supplierName">
						<input type="text" class="Field400"	name="${status.expression}" id="${status.expression}" value="${status.value}"/>
					</spring:bind>
				</td>
             </tr>
             <tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="supplier.supplierCode" />
				</td>
				<td>
					<spring:bind path="supplier.supplierCode">
						<input type="text" class="Field400"	name="${status.expression}" id="${status.expression}" value="${status.value}"/>
					</spring:bind>
				</td>
			</tr>
			<tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="supplier.email" />
				</td>
				<td >
					<spring:bind path="supplier.email">
						<input type="text" class="Field400"	name="${status.expression}" id="${status.expression}" value="${status.value}"/>
					</spring:bind>
				</td>
            </tr>
            <tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="supplier.status" />
				</td>
				<td >
					<spring:bind path="supplier.status">
						<input type="checkbox" onclick="if(this.checked){$j('#status').val(1);}else{$j('#status').val(2);}"
						<c:if test="${supplier.status == 1}">checked</c:if>
						<c:if test="${supplier.supplierId == null}">checked</c:if> />
						<input type="hidden" name="${status.expression}" id="${status.expression}" value="${status.value == null ? 1 : status.value}" />
					</spring:bind>
					<fmt:message key="supplier.status_1"/>
				</td>
			</tr>
			<tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="supplier.webSite" />
				</td>
				<td >
					<spring:bind path="supplier.webSite">
						<input type="text" class="Field400"	name="${status.expression}" id="${status.expression}" value="${status.value}"/>
					</spring:bind>
				</td>
            </tr>
            <tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="supplier.fax" />
				</td>
				<td >
					<spring:bind path="supplier.fax">
						<input type="text" class="Field400"	name="${status.expression}" id="${status.expression}" value="${status.value}"/>
					</spring:bind>
				</td>
			</tr>
			<tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="supplier.address" />
				</td>
				<td>
					<spring:bind path="supplier.address">
						<input type="text" class="Field400"	name="${status.expression}" id="${status.expression}" value="${status.value}"/>
					</spring:bind>
				</td>
            </tr>
            <tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="supplier.zip" />
				</td>
				<td>
					<spring:bind path="supplier.zip">
						<input type="text" class="Field400"	name="${status.expression}" id="${status.expression}" value="${status.value}"/>
					</spring:bind>
				</td>
			</tr>
			<tr>
				<td class="FieldLabel">
					<c:if test="${not empty supplier.id}">
					<StoreAdmin:label key="supplier.createTime" />
					</c:if>&nbsp;
				</td>
				<td>
					<c:if test="${not empty supplier.id}">
					<fmt:formatDate value="${supplier.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</c:if>&nbsp;
				</td>
            </tr>
            <tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="supplier.adminId" />
				</td>
				<td>
					<c:choose>
						<c:when test="${not empty supplier.id}">
							<select class="Field400" name="adminId" id="adminId">
								<c:forEach items="${supplierUserList}" var="supplierUser">
									<option value="${supplierUser.id}"<c:if test="${supplier.adminId==supplierUser.id}"> selected="selected"</c:if>>${supplierUser.username}<c:if test="${not empty supplierUser.firstname&&not empty supplierUser.lastname}">&nbsp;&nbsp;[${supplierUser.firstname}&nbsp;&nbsp;${supplierUser.lastname}]</c:if></option>
								</c:forEach>
							</select>
						</c:when>
						<c:otherwise>
							<input type="hidden" name="adminId" id="adminId">
							<span id="supplierAdminName"></span>
							<cartmatic:iconBtn icon="plus" text="选择超级管理员" onclick="userSelector_show();" />
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<c:if test="${not empty supplier.id}">
			<tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="supplier.updateTime" />
				</td>
				<td>
					<fmt:formatDate value="${supplier.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
			</c:if>
			<tr>
				<td class="FieldLabel">
					银行帐号:
				</td>
				<td >
					<textarea name="bankAccount" class="Field400" id="bankAccount" rows="2" cols="61">${supplier.bankAccount}</textarea>
					<span class="icon_help">
					<cartmatic:ui_tip id="bankAccountTip" >格式:陈某 招商银行:4521 5421 5562 ...</cartmatic:ui_tip>
					</span>
				</td>
             </tr>
            <tr>
				<td class="FieldLabel">
					联系人:
				</td>
				<td>
				  	<div id="contacts_td">
					  	<c:if test="${empty supplier.contactList}">
					  		<div>
				  				姓名:<input type="text"  id="contactName_1" class="Field100">
							  	电话:<input type="text" id="contactPhone_1" class="Field100">
							  	QQ:<input type="text"  id="contactQQ_1" class="Field100">
							  	手机号:<input type="text" id="contactMSN_1" class="Field100">
							  	<cartmatic:iconBtn icon="plus" text="添加" onclick="addContact(this);" />&nbsp;&nbsp;
						  		<cartmatic:iconBtn icon="cross" extraCss="negative" text="删除" onclick="delContact(this);" extraStyle="display: none;"/>
				  			</div>
					  	</c:if>
					  	<c:forEach items="${supplier.contactList}" var="contact" varStatus="varStatus">
					  		<div>
				  				姓名:<input type="text"  id="contactName_${varStatus.count}" class="Field100" value="${contact[0]}">
							  	电话:<input type="text" id="contactPhone_${varStatus.count}" class="Field100" value="${contact[1]}">
							  	QQ:<input type="text"  id="contactQQ_${varStatus.count}" class="Field100" value="${contact[2]}">
							  	手机号:<input type="text" id="contactMSN_${varStatus.count}" class="Field100" value="${contact[3]}">
							  	<cartmatic:iconBtn icon="plus" text="添加" onclick="addContact(this);" />&nbsp;&nbsp;
						  		<cartmatic:iconBtn icon="cross" extraCss="negative" text="删除" onclick="delContact(this);" />							  	
				  			</div>
					  	</c:forEach>
				  	</div>
				  	<textarea id="contactDetailTemplate" style="display: none;" >
				  		<div>
			  				姓名:<input type="text"  id="contactName_{0}" class="Field100">
						  	电话:<input type="text" id="contactPhone_{0}" class="Field100">
						  	QQ:<input type="text"  id="contactQQ_{0}" class="Field100">
						  	手机号:<input type="text" id="contactMSN_{0}" class="Field100">
						  	<cartmatic:iconBtn icon="plus" textKey="common.link.add" onclick="addContact(this);" />&nbsp;&nbsp;
						  	<cartmatic:iconBtn icon="cross" extraCss="negative" text="删除" onclick="delContact(this);" />						  	
					  	</div>
		  			</textarea>
				  	<input type="hidden" name="contacts" id="contacts" value="${supplier.contacts}" />
			    </td>
			</tr>
			<tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="supplier.comments" />
				</td>
				<td>
					<textarea name="comments" id="comments" rows="10" cols="61">${supplier.comments}</textarea>
				</td>				
			</tr>
		</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
			<tr>
				<th>
					产品描述转换
				</th>
			</tr>
			<tr>
				<td class="FieldLabel">
					<div id="prodDescConverts">
						<c:if test="${empty prodDescConvertList}">
							<div name="oneItem">
				  				<input type="text" name="prodDescConvert_regex" class="Field300"> >> 
							  	<input type="text" name="prodDescConvert_replacement" class="Field500">
							  	<cartmatic:iconBtn icon="plus" text="添加" onclick="addProdDescConvert(this);" />&nbsp;&nbsp;
						  		<cartmatic:iconBtn icon="cross" extraCss="negative" text="删除" onclick="removeItem(this);" extraStyle="display: none;"/>
				  			</div>
						</c:if>
						<c:forEach items="${prodDescConvertList}" var="prodDescConvert">
							<div name="oneItem">
								<c:forEach items="${prodDescConvert}" var="prodDescConvert2">
				  				<input type="text" name="prodDescConvert_regex" value="${prodDescConvert2.key}" class="Field300"> >> 
							  	<input type="text" name="prodDescConvert_replacement" value="${prodDescConvert2.value}" class="Field500">
							  	</c:forEach>
							  	<cartmatic:iconBtn icon="plus" text="添加" onclick="addProdDescConvert(this);" extraStyle="display: none;"/>&nbsp;&nbsp;
						  		<cartmatic:iconBtn icon="cross" extraCss="negative" text="删除" onclick="removeItem(this);" extraStyle="display: none;"/>
				  			</div>
			  			</c:forEach>
					</div>
	  				<textarea id="prodDescConvertTemplate" style="display: none;" >
				  		<div name="oneItem">
			  				<input type="text" name="prodDescConvert_regex" class="Field300"> >> 
					  		<input type="text" name="prodDescConvert_replacement" class="Field500">
						  	<cartmatic:iconBtn icon="plus" textKey="common.link.add" onclick="addProdDescConvert(this);" />&nbsp;&nbsp;
						  	<cartmatic:iconBtn icon="cross" extraCss="negative" text="删除" onclick="removeItem(this);" />						  	
					  	</div>
		  			</textarea>
				</td>
             </tr>
    </table>
	</form>
<c:if test="${supplier.supplierId!=null}">
<div class="blank10"></div>
<div class="blank10"></div>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
	<tr>
		<th>
			供应商用户列表
			
		</th>
	</tr>
	
	<tr>
		<td >
			<c:if test="${fn:length(supplierUserList)<5}">
				<cartmatic:iconBtn icon="user" text="增加用户" onclick="return userSelector_show();" />
			</c:if>
			<table cellspacing="0" cellpadding="0">
				<tr>
					<th>用户名</th>
					<th>姓名</th>
					<th>创建时间</th>
					<th>状态</th>
					<th>管理</th>
				</tr>
				<c:forEach items="${supplierUserList}" var="user">
					<tr>
						<td><a href="${ctxPath}/customer/customer.html?doAction=edit&customerId=${user.id}" >${user.username}</a></td>
						<td><a href="${ctxPath}/customer/customer.html?doAction=edit&customerId=${user.id}" >${user.firstname}&nbsp;&nbsp;${user.lastname}</a></td>
						<td><fmt:formatDate value="${user.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
						<td><fmt:message key="supplier.status_${user.status}" /></td>
						<td>
						<cartmatic:iconBtn icon="pen" textKey="common.link.edit" 
						hrefUrl="${ctxPath}/customer/customer.html?doAction=edit&customerId=${user.id}" />&nbsp;&nbsp;
						<cartmatic:iconBtn icon="cross" extraCss="negative" textKey="common.link.delete" 
						hrefUrl="${ctxPath}/supplier/supplier.html?doAction=removeUser&supplierId=${supplier.supplierId}&supplierUserId=${user.id}" />
						</td>
					</tr>
				</c:forEach>
			</table>
		</td>
	</tr>
</table>
</c:if>
<v:javascript formName="supplier" staticJavascript="false" />
<script type="text/javascript">
    document.forms["supplier"].elements["supplierName"].focus();
    $j(document).ready(function(){
    	fn_showBtns($j("#prodDescConverts"));
	});
    function fnSelectSupplierUser(supplierUser){
    	var supplierAdminNameStr=supplierUser.username;
		if(supplierUser.firstname||supplierUser.lastname)
			supplierAdminNameStr+="[";
			if(supplierUser.firstname)
    		supplierAdminNameStr+=supplierUser.firstname;
    	if(supplierUser.lastname)
    	    supplierAdminNameStr+=supplierUser.lastname;
			if(supplierUser.firstname||supplierUser.lastname)
   			supplierAdminNameStr+="]";
    	if(supplierUser.supplierId){
            alert(supplierAdminNameStr+"已经有所属的供应商!");
            return;
        }
    	<c:choose>
	    	<c:when test="${empty supplier.supplierId}">
	    		$j("#adminId").val(supplierUser.appuserId);
	            $j("#supplierAdminName").html(supplierAdminNameStr);
	    	</c:when>
	    	<c:otherwise>
	    		$j.post("${ctxPath}/supplier/supplier.html?doAction=addSupplierUser",{supplierId:${supplier.supplierId},supplierUserId:supplierUser.appuserId},function(result){
	    			if(result.status==1){
	    				window.location.href="${ctxPath}/supplier/supplier.html?doAction=edit&supplierId=${supplier.supplierId}";
	    		   	}else{
	    		   		sysMsg(result.msg,true);
	    		   	}
		    		},"json");
	    	</c:otherwise>
	    </c:choose>
        userSelector_close();
    }
    function addContact(){
    	$j("#contacts_td").append(format($j("#contactDetailTemplate").val(),new Date().getTime().toString().substr(6)));
    	fn_showContactDeleteBtn();
   	}
    function delContact(delBtn){
    	$j(delBtn).parent().remove();
    	fn_showContactDeleteBtn();
   	}
   	function fn_showContactDeleteBtn(){
   		var deleteBtns=$j("a[onclick='delContact(this);']",$j("#contacts_td"));
   		deleteBtns.toggle(deleteBtns.length>1);
   	}
   	fn_showContactDeleteBtn();

    function setContacts(){
	   	var isOk=true;
   	   	var contactValue="";
   		var contacts=$j("#contacts_td").children();
   		var pattern=/^.*[;|,]{1}.*$/; 
   		for(var i=0;i<contacts.length;i++){
   	   		var name=$j(contacts[i]).find("input[id^='contactName_']").val();
   	   		var phone=$j(contacts[i]).find("input[id^='contactPhone_']").val();
   			var qq=$j(contacts[i]).find("input[id^='contactQQ_']").val();
   			var msn=$j(contacts[i]).find("input[id^='contactMSN_']").val();
   			if(pattern.test(name)||pattern.test(phone)||pattern.test(qq)||pattern.test(msn)){
				isOk=false;
				break;
	   		}
   			contactValue+=(name.trim()+","+phone.trim()+","+qq.trim()+","+msn.trim()+";");
   		}
   		if(isOk){
	   		$j("#contacts").val(contactValue);
   		}else{
	   		alert("请输入正确的联系人信息!");
   		}
   		return isOk;
   	}

    function addProdDescConvert(){
    	$j("#prodDescConverts").append(format($j("#prodDescConvertTemplate").val()));
    	fn_showBtns($j("#prodDescConverts"));
   	}

    function removeItem(delBtn){
        var prodDescConv=$j(delBtn).closest("[id='prodDescConverts']");
    	$j(delBtn).parent().remove();
    	fn_showBtns(prodDescConv);
   	}

    function fn_showBtns(cont){
    	if(cont.find("div[name='oneItem']").length>=2){
            var addBtns=cont.find("a[onclick^='add']");
            addBtns.filter(":not(:last)").hide();
            addBtns.filter(":last").show();
            cont.find("a[onclick^='remove']").show();
		}else{
			cont.find("a[onclick^='add']").show();
			cont.find("a[onclick^='remove']").hide();
		}
    }
</script>
<system:userSelector id="userSelector" ondblclick="fnSelectSupplierUser" autoClose="false"></system:userSelector>