<%@ include file="/common/taglibs.jsp"%>
          <div class="box-content">
            <div class="top">
              <div class="content-title"><fmt:message key="report.hotsalproduct" /></div>  
              <div class="btn">
				  <fmt:message key="report.daterange" />
&nbsp;
<c:set var="timePeriod" value="${param.prm_timePeriod==null2 ? '3' : param.prm_timePeriod}"/>
<select id="prm_timePeriod" onchange='fnRefreshReport(this);'>
	<option value='1' ${timePeriod==1 ? " selected" : ""}>
		<fmt:message key="report.range.today" />
	</option>
	<option value='2' ${timePeriod==2 ? " selected" : ""}>
		<fmt:message key="report.range.thisweek" />
	</option>
	<option value='3' ${timePeriod==3 ? " selected" : ""}>
		<fmt:message key="report.range.thismonth" />
	</option>
	<option value='4' ${timePeriod==4 ? " selected" : ""}>
		<fmt:message key="report.range.thisquarter" />
	</option>
	<option value='5' ${timePeriod==5 ? " selected" : ""}>
		<fmt:message key="report.range.thisfy" />
	</option>
	<option value='6' ${timePeriod==6 ? " selected" : ""}>
		<fmt:message key="report.range.alltime" />
	</option>
</select>

              </div>            
            </div>
            <div class="content">
  

<display:table name="${hotSalesProduct}" cellspacing="0" cellpadding="0" uid="items" class="table-list"
	export="false" requestURI="" length="5">
	<display:column style="width: 25%" titleKey="product.productCode" headerClass="data-table-title" media="html">
		&nbsp;<a href="<c:url value="/catalog/editProduct.html?from=list&productId=${items[0]}"/>">	
	    ${items[1]}
	    </a>
	</display:column>
	<display:column style="width: 30%" titleKey="product.productName" headerClass="data-table-title"
		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator">
		&nbsp;${items[2]}
	</display:column>
	<display:column style="width: 20%" titleKey="report.hotsalproductCount" headerClass="data-table-title" media="html">
	    &nbsp;${items[3]}
	</display:column>
	<display:column style="width: 25%" titleKey="report.summoney" headerClass="data-table-title" media="html">
		&nbsp;<system:CurrencyForRate value="${items[4]}" />
	</display:column>
</display:table>
</div>
</div>