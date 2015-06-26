<%@ page language="java" contentType="text/javascript" %>
<%@page import="com.cartmatic.estore.common.helper.ConfigUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/I18n.tld" prefix="i18n" %>
<%!
protected long getLastModified(HttpServletRequest req) {
	return System.currentTimeMillis();
}
%>
<%
int seconds=((ConfigUtil)application.getAttribute("appConfig")).getBrowserSidePageCacheSeconds();
if (seconds>0){
	//HTTP 1.0 header
	response.setDateHeader("Expires", System.currentTimeMillis() + seconds * 1000L);
	// HTTP 1.1 header
	String headerValue = "max-age=" + seconds+", must-revalidate";
	response.setHeader("Cache-Control", headerValue);
}
%>
var HOMEPAGE="${ctxPath}/index.html";
var __ctxPath = "${ctxPath}";
var __mediaPath = "${ctxPath}/media/";
var __defaultImage = "${ctxPath}/images/default/00.jpg";
var __defDatePattern='<i18n:msg key="date.format"/>';
var __defaultCurrencySymbol = "${appConfig.defaultCurrencySymbol}";
//var __storeFrontMediaPath = "${appConfig.store.siteUrl}/media/";
var __storeFrontMediaPath = "/media/";

var __vaMsg = {
	notPass:"<i18n:msg key="validate.notPass"/>",
	required:"<i18n:msg key="validate.required"/>",
	number:"<i18n:msg key="validate.number" />",
	integer:"<i18n:msg key="validate.integer"/>",
	short:"<i18n:msg key="validate.short"/>",
	double:"<i18n:msg key="validate.double"/>",
	long:"<i18n:msg key="validate.long"/>",
	maxValue:"<i18n:msg key="validate.maxValue"/>",
	minValue:"<i18n:msg key="validate.minValue"/>",
	maxlength:"<i18n:msg key="validate.maxlength"/>",
	minlength:"<i18n:msg key="validate.minlength"/>",
	price:"<i18n:msg key="validate.price"/>",
	money:"<i18n:msg key="validate.money"/>",
	code:"<i18n:msg key="validate.code"/>",
	phone:"<i18n:msg key="validate.phone"/>",
	mobile:"<i18n:msg key="validate.mobile"/>",
	email:"<i18n:msg key="validate.email"/>",
	positiveInteger:"<i18n:msg key="validate.positiveInteger"/>",
	double4:"<i18n:msg key="validate.double4"/>",
	noHtml:"<i18n:msg key="validate.noHtml"/>",
	radio:"<i18n:msg key="validate.radio"/>",
	url:"<i18n:msg key="validate.url"/>",
	mask:"<i18n:msg key="validate.mask"/>",
	date:"<i18n:msg key="validate.date"/>",
	floatRange:"<i18n:msg key="validate.floatRange"/>",
	equalTo:"<i18n:msg key="validate.equalTo"/>",
	intRange:"<i18n:msg key="validate.intRange"/>"
};
var __FMT = {
	common_message_deleteOne:"<i18n:msg key="common.message.deleteOne"/>",
	common_message_confirmDelete:"<i18n:msg key="common.message.confirmDelete"/>",
	common_message_confirmSave:"<i18n:msg key="common.message.confirmSave"/>",
	common_message_confirmDeleteThis:"<i18n:msg key="common.message.confirmDeleteThis"/>",
	common_message_confirmDeleteSelected:"<i18n:msg key="common.message.confirmDeleteSelected"/>",
	common_message_confirmSaveThis:"<i18n:msg key="common.message.confirmSaveThis"/>",
	common_message_tabConfirmSave:"<i18n:msg key="common.message.tabConfirmSave"/>",
	common_message_confirmAction:"<i18n:msg key="common.message.confirmAction"/>",
    button_add:"<i18n:msg key="button.add"/>",
    button_edit:"<i18n:msg key="button.edit"/>",
    button_close:"<i18n:msg key="i18nEditor.close"/>",
    button_clear:"<i18n:msg key="button.clear"/>",
    button_cancel:"<i18n:msg key="button.cancel"/>",
    button_save:"<i18n:msg key="button.save"/>",
    button_select:"<i18n:msg key="button.select"/>",
    button_small_delete:"<i18n:msg key="button.small.delete"/>",
    button_uploadFile:"<i18n:msg key="button.uploadFile"/>",
    grant_role_success:"<i18n:msg key="grantRole.success"/>",
    add_productAttribute_success:"<i18n:msg key="productType.addAttributeComment"/>",
	common_message_confirmRemoveProduct:"<i18n:msg key="common.message.confirmRemoveProduct"/>",
    
    productType_selectedAttributes:"<i18n:msg key="productType.selectedAttributes"/>",
    order_search:"<i18n:msg key="button.search"/>",
    order_close:"<i18n:msg key="feedback.close"/>",
    user_selector:"<i18n:msg key="button.selectCustomer"/>",
    icon_warning:"<i18n:msg key="icon.warning"/>",
    error_upload_unsupport:"<i18n:msg key="error.upload.unsupport"/>",
    productMedia_mediaDescription:"<i18n:msg key="productMedia.mediaDescription"/>",
    productDetail_moreImage_desc:"<i18n:msg key="productDetail.moreImage.desc"/>",
    productDetail_accessories_desc:"<i18n:msg key="productDetail.accessories.desc"/>",
    productDetail_productPackage_item_less2:"<i18n:msg key="productDetail.productPackage.item.less2"/>",
    productDetail_productPackage_item_added:"<i18n:msg key="productDetail.productPackage.item.added"/>",
    productDetail_productPackage_saved_message:"<i18n:msg key="productDetail.productPackage.saved.message"/>",
    productSelector_title:"<i18n:msg key="productSelector.title"/>",
    productSkuSelector_title:"<i18n:msg key="productSkuSelector.title"/>",
    productDetail_category_existed:"<i18n:msg key="productDetail.category.existed"/>",
    productDetail_mainCategory_cannot_delete:"<i18n:msg key="productDetail.mainCategory.cannot.delete"/>",
    productDetail_save_successed:"<i18n:msg key="productDetail.save.successed"/>",
    productDetail_setDefaultProductSkuId_message:"<i18n:msg key="productDetail.setDefaultProductSkuId.message"/>",
    productDetail_moreImage_addEmptyImage:"<i18n:msg key="productDetail.moreImage.addEmptyImage"/>",
    productDetail_moreImage_removeThisImage:"<i18n:msg key="productDetail.moreImage.removeThisImage"/>",
    productSkuDetail_enter_inventory_confirm:"<i18n:msg key="productSkuDetail.enter.inventory.confirm"/>",
    productDetail_validate_endTimeGtStartTime:"<i18n:msg key="productDetail.validate_endTimeGtStartTime"/>",
    contentListBody_wait:"<i18n:msg key="contentListBody.wait"/>",
    select:"<i18n:msg key="form.select.all"/>"
};