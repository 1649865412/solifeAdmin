<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
org.apache.solr.common.util.NamedList productCore = (org.apache.solr.common.util.NamedList)request.getAttribute("productCore");
org.apache.solr.common.util.NamedList productIndexInfo = (org.apache.solr.common.util.NamedList)request.getAttribute("productIndexInfo");
org.apache.solr.common.util.NamedList salesOrderCore = (org.apache.solr.common.util.NamedList)request.getAttribute("salesOrderCore");
org.apache.solr.common.util.NamedList salesOrderIndexInfo = (org.apache.solr.common.util.NamedList)request.getAttribute("salesOrderIndexInfo");
org.apache.solr.common.util.NamedList contentCore = (org.apache.solr.common.util.NamedList)request.getAttribute("contentCore");
org.apache.solr.common.util.NamedList contentIndexInfo = (org.apache.solr.common.util.NamedList)request.getAttribute("contentIndexInfo");
%>
<html>
    <head>
        <title>Search Server Manager</title>
    </head>
    <body>
        <content tag="buttons">
        <ul>
            <li>
            	<fmt:message key="searchServer.confirm.rebuild.product.index" var="confirmRebuildProduct"/>
            	<fmt:message key="searchServer.confirm.rebuild.order.index" var="confirmRebuildOrder"/>
            	<fmt:message key="searchServer.confirm.rebuild.content.index" var="confirmRebuildContent"/>
            	<fmt:message key="searchServer.confirm.restart" var="confirmRestart"/>
                <cartmatic:cartmaticBtn btnType="common" commonBtnValueKey="searchServer.rebuild.product.index" onclick="fnDoAction(this,'rebuildProductIndex','${confirmRebuildProduct}')" />
                <cartmatic:cartmaticBtn btnType="common" commonBtnValueKey="searchServer.rebuild.order.index"  onclick="fnDoAction(this,'rebuildSalesOrderIndex','${confirmRebuildOrder}')" />
                <cartmatic:cartmaticBtn btnType="common" commonBtnValueKey="searchServer.rebuild.content.index"  onclick="fnDoAction(this,'rebuildContentIndex','${confirmRebuildContent}')" />
                <cartmatic:cartmaticBtn btnType="common" commonBtnValue="Restart" onclick="fnDoAction(this,'restart','${confirmRestart}')" />
                <cartmatic:cartmaticBtn btnType="common" commonBtnValue="Save 同义词" onclick="doAction_old('saveSynonym')" />
                <cartmatic:cartmaticBtn btnType="common" commonBtnValue="Save 专用名词" onclick="doAction_old('saveProtected')" />
            </li>
        </ul>
        </content>
        <form class="mainForm" method="post">
        	<c:if test="${empty productCore}">
        	<b>Search Server is not available.</b>
        	</c:if>
        	<c:if test="${not empty salesOrderCore}">
        	<table width="100%">
        	<tr>
        	 <td class="FieldLabel">订单索引</td>
        	<td>      
        	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
           		<tr>
					 <td class="FieldLabel">
						启动时间(startTime)
					</td>
					<td>
						<%=salesOrderCore.get("startTime")%>
					</td>
					
				</tr>
				<tr>
					 <td class="FieldLabel">
						在线时间(uptime)
					</td>
					<td>
						<%=salesOrderCore.get("uptime")%> (单位毫秒=1/1000秒)
					</td>
				</tr>
				<tr>
					 <td class="FieldLabel">
						实例目录(instanceDir)
					</td>
					<td>
						<%=salesOrderCore.get("instanceDir")%>
					</td>
				</tr>
				<tr>
					 <td class="FieldLabel">
						数据目录(dataDir)
					</td>
					<td>
						<%=salesOrderCore.get("dataDir")%>
					</td>
				</tr>
				<tr>
					 <td class="FieldLabel">&nbsp;</td>
					<td></td>
				</tr>
				<tr>
					 <td class="FieldLabel">&nbsp;</td>
					<td></td>
				</tr>
				<tr>
					 <td class="FieldLabel">&nbsp;</td>
					<td></td>
				</tr>				
			</table>
			</td>
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
				<tr>
					 <td class="FieldLabel">
						索引数(numDocs)
					</td>
					<td>
						<%=salesOrderIndexInfo.get("numDocs")%>
					</td>
				</tr>
				<tr>
					 <td class="FieldLabel">
						最大文档的索引(maxDoc)
					</td>
					<td>
						<%=salesOrderIndexInfo.get("maxDoc")%>
					</td>
				</tr>
				<tr>
					 <td class="FieldLabel">
						version
					</td>
					<td>
						<%=salesOrderIndexInfo.get("version")%>
					</td>
				</tr>
				<tr>
					 <td class="FieldLabel">
						是否已优化(optimized)
					</td>
					<td>
						<%=salesOrderIndexInfo.get("optimized")%>
					</td>
				</tr>
				<tr>
					 <td class="FieldLabel">
						current
					</td>
					<td>
						<%=salesOrderIndexInfo.get("current")%>
					</td>
				</tr>
				<tr>
					 <td class="FieldLabel">
						是否有删除过(hasDeletions)
					</td>
					<td>
						<%=salesOrderIndexInfo.get("hasDeletions")%>
					</td>
				</tr>
				<tr>
					 <td class="FieldLabel">
						最后更新(lastModified)
					</td>
					<td>
						<%=salesOrderIndexInfo.get("lastModified")%>
					</td>
				</tr>
			</table>
			</td>
			</tr>			
			</table>
			</c:if>
			<c:if test="${not empty contentCore}">
        	<table width="100%">
        	<tr>
        	 <td class="FieldLabel">内容索引</td>
        	<td>      
        	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
           		<tr>
					 <td class="FieldLabel">
						启动时间(startTime)
					</td>
					<td>
						<%=contentCore.get("startTime")%>
					</td>
					
				</tr>
				<tr>
					 <td class="FieldLabel">
						在线时间(uptime)
					</td>
					<td>
						<%=contentCore.get("uptime")%> (单位毫秒=1/1000秒)
					</td>
				</tr>
				<tr>
					 <td class="FieldLabel">
						实例目录(instanceDir)
					</td>
					<td>
						<%=contentCore.get("instanceDir")%>
					</td>
				</tr>
				<tr>
					 <td class="FieldLabel">
						数据目录(dataDir)
					</td>
					<td>
						<%=contentCore.get("dataDir")%>
					</td>
				</tr>
				<tr>
					 <td class="FieldLabel">&nbsp;</td>
					<td></td>
				</tr>
				<tr>
					 <td class="FieldLabel">&nbsp;</td>
					<td></td>
				</tr>
				<tr>
					 <td class="FieldLabel">&nbsp;</td>
					<td></td>
				</tr>				
			</table>
			</td>
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
				<tr>
					 <td class="FieldLabel">
						索引数(numDocs)
					</td>
					<td>
						<%=contentIndexInfo.get("numDocs")%>
					</td>
				</tr>
				<tr>
					 <td class="FieldLabel">
						最大文档的索引(maxDoc)
					</td>
					<td>
						<%=contentIndexInfo.get("maxDoc")%>
					</td>
				</tr>
				<tr>
					 <td class="FieldLabel">
						version
					</td>
					<td>
						<%=contentIndexInfo.get("version")%>
					</td>
				</tr>
				<tr>
					 <td class="FieldLabel">
						是否已优化(optimized)
					</td>
					<td>
						<%=contentIndexInfo.get("optimized")%>
					</td>
				</tr>
				<tr>
					 <td class="FieldLabel">
						current
					</td>
					<td>
						<%=contentIndexInfo.get("current")%>
					</td>
				</tr>
				<tr>
					 <td class="FieldLabel">
						是否有删除过(hasDeletions)
					</td>
					<td>
						<%=contentIndexInfo.get("hasDeletions")%>
					</td>
				</tr>
				<tr>
					 <td class="FieldLabel">
						最后更新(lastModified)
					</td>
					<td>
						<%=contentIndexInfo.get("lastModified")%>
					</td>
				</tr>
			</table>
			</td>
			</tr>			
			</table>
			</c:if>
        	<c:if test="${not empty productCore}">
        	<table width="100%">
        	<tr>
        	 <td class="FieldLabel">产品索引</td>
        	<td>      
        	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
           		<tr>
					 <td class="FieldLabel">
						启动时间(startTime)
					</td>
					<td>
						<%=productCore.get("startTime")%>
					</td>
					
				</tr>
				<tr>
					 <td class="FieldLabel">
						在线时间(uptime)
					</td>
					<td>
						<%=productCore.get("uptime")%> (单位毫秒=1/1000秒)
					</td>
				</tr>
				<tr>
					 <td class="FieldLabel">
						实例目录(instanceDir)
					</td>
					<td>
						<%=productCore.get("instanceDir")%>
					</td>
				</tr>
				<tr>
					 <td class="FieldLabel">
						数据目录(dataDir)
					</td>
					<td>
						<%=productCore.get("dataDir")%>
					</td>
				</tr>
				<tr>
					 <td class="FieldLabel">&nbsp;</td>
					<td></td>
				</tr>
				<tr>
					 <td class="FieldLabel">&nbsp;</td>
					<td></td>
				</tr>
				<tr>
					 <td class="FieldLabel">&nbsp;</td>
					<td></td>
				</tr>				
			</table>
			</td>
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
				<tr>
					 <td class="FieldLabel">
						索引数(numDocs)
					</td>
					<td>
						<%=productIndexInfo.get("numDocs")%>
					</td>
				</tr>
				<tr>
					 <td class="FieldLabel">
						最大文档的索引(maxDoc)
					</td>
					<td>
						<%=productIndexInfo.get("maxDoc")%>
					</td>
				</tr>
				<tr>
					 <td class="FieldLabel">
						version
					</td>
					<td>
						<%=productIndexInfo.get("version")%>
					</td>
				</tr>
				<tr>
					 <td class="FieldLabel">
						是否已优化(optimized)
					</td>
					<td>
						<%=productIndexInfo.get("optimized")%>
					</td>
				</tr>
				<tr>
					 <td class="FieldLabel">
						current
					</td>
					<td>
						<%=productIndexInfo.get("current")%>
					</td>
				</tr>
				<tr>
					 <td class="FieldLabel">
						是否有删除过(hasDeletions)
					</td>
					<td>
						<%=productIndexInfo.get("hasDeletions")%>
					</td>
				</tr>
				<tr>
					 <td class="FieldLabel">
						最后更新(lastModified)
					</td>
					<td>
						<%=productIndexInfo.get("lastModified")%>
					</td>
				</tr>
			</table>
			</td>
			</tr>			
			</table>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
				<tr>
					 <td class="FieldLabel">
						同义词(synonym)
					</td>
					<td>
						<textarea name="synonym" rows="30" cols="80">${synonyms}</textarea>
					</td>
					<td>
						<pre>
# blank lines and lines starting with pound are comments.

#Explicit mappings match any token sequence on the LHS of "=>"
#and replace with all alternatives on the RHS.  These types of mappings
#ignore the expand parameter in the schema.
#Examples:
i-pod, i pod => ipod,
sea biscuit, sea biscit => seabiscuit

#Equivalent synonyms may be separated with commas and give
#no explicit mapping.  In this case the mapping behavior will
#be taken from the expand parameter in the schema.  This allows
#the same synonym file to be used in different synonym handling strategies.
#Examples:
ipod, i-pod, i pod
foozball , foosball
universe , cosmos

#multiple synonym mapping entries are merged.
foo => foo bar
foo => baz
#is equivalent to
foo => foo bar, baz
						</pre>  
					</td>
				</tr>
				<tr>
					 <td class="FieldLabel">
						专用名词(protected words)
					</td>
					<td>
						<textarea name="protectedWords" rows="30" cols="80">${protwords}</textarea>
					</td>
					<td>
					# Use a protected word file to protect against the stemmer reducing two<br/>
					# unrelated words to the same base word.
					</td>
				</tr>				
			</table>
			</c:if>
            <input type="hidden" name="doAction" value="" />
        </form>
    </body>
</html>