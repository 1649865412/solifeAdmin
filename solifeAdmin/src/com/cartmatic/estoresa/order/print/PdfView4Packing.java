/**
 * 
 */
package com.cartmatic.estoresa.order.print;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cartmatic.estore.common.model.order.OrderAddress;
import com.cartmatic.estore.common.model.order.OrderShipment;
import com.cartmatic.estore.common.model.order.OrderSku;
import com.cartmatic.estore.common.util.DateUtil;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

/**
 * 发货单打印
 * 
 */
public class PdfView4Packing extends PdfAbstractView {    
	
	public void buildPdfDocument(Map model, Document document,    
            PdfWriter writer, HttpServletRequest request,    
            HttpServletResponse response) throws Exception {  
		
		BaseFont bf = BaseFont.createFont("STSong-Light",	"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		fontChinese = new Font(bf, 12, Font.NORMAL); 
		
		Set<OrderShipment> orderShipments = (Set<OrderShipment>)model.get("orderShipments");
		int index = 0;
		int size = orderShipments.size();
		for(OrderShipment orderShipment: orderShipments){
			Paragraph p = new Paragraph();
			PdfPTable t1 = new PdfPTable(new float[]{12, 44, 44});
			t1.getDefaultCell().setBorderWidth(0);
			t1.setWidthPercentage(98);
			OrderAddress shippingAddress = orderShipment.getOrderAddress();
			String colon = getMessage("common.message.colon");
			t1.addCell( new Phrase(this.getMessage("order.shipment.receiver")+colon, fontChinese) );
			t1.addCell(new Phrase(orderShipment.getOrderAddress().getFirstname() + " " + orderShipment.getOrderAddress().getLastname(), fontChinese));
			t1.addCell(new Phrase(this.getMessage("order.pdf.orderTime")+DateUtil.getDateTime(orderShipment.getSalesOrder().getCreateTime()), fontChinese));
			
			t1.addCell(new Phrase(this.getMessage("order.pdf.address"), fontChinese));
			t1.addCell(new Phrase(shippingAddress.getState()+" "+shippingAddress.getCity()+" "+shippingAddress.getAddress1()+" "+shippingAddress.getAddress2(), fontChinese));
			t1.addCell(new Phrase(this.getMessage("salesOrder.orderNo")+colon+orderShipment.getSalesOrder().getOrderNo(), fontChinese));
			
			if(shippingAddress.getPostalcode()!=null){
				t1.addCell(new Phrase(this.getMessage("orderAddress.postalcode")+colon, fontChinese));
				t1.addCell(new Phrase(shippingAddress.getPostalcode(), fontChinese));
				t1.addCell("\n");
			}else{
				t1.addCell("\n");
				t1.addCell("\n");
				t1.addCell("\n");
			}
			
			t1.addCell("\n");
			t1.addCell("\n");
			t1.addCell("\n");
			
			p.add(t1);
			
			PdfPTable table = new PdfPTable(new float[]{8, 44, 8, 20, 20});
			table.getDefaultCell().setLeading(2f, 0.1f);
			table.setHeaderRows(1);
			table.setWidthPercentage(98);
			//table.setSpacingBefore(5f);
			
    		table.addCell(getThCell(this.getMessage("common.message.ordinalNo"))); 
    		table.addCell(getThCell(this.getMessage("orderSku.product"))); 
    		table.addCell(getThCell(this.getMessage("orderSku.quantity"))); 
    		table.addCell(getThCell(this.getMessage("orderSku.price"))); 
    		table.addCell(getThCell(this.getMessage("orderSku.subTotalAmount"))); 
    		
    		Set<OrderSku> orderSkus = orderShipment.getOrderSkus();
    		int i = 1;
    		int skuSize = orderSkus.size();
    		for(OrderSku orderSku: orderSkus){
    			boolean lastOne = (i==skuSize);
    			table.addCell(getTdCell(String.valueOf(i++), lastOne)); 
    			
    			String productInfo = "";
    			if(orderSku.getProductId()!=null){
    				productInfo += orderSku.getProductName();
					if (orderSku.getDisplaySkuOptions()!=null){
						//参见orderSkuOption.tag
						String [] options = orderSku.getDisplaySkuOptions().split("###");
						for(String opt : options){
							productInfo += "\n" + opt;
						}
					}
					productInfo += "\n"+ this.getMessage("orderSku.productSkuCode")+this.getMessage("common.message.colon")+orderSku.getProductSkuCode();
					String extra = orderSku.getProductSku().getProduct().getExtra1();
					if (extra != null)
						productInfo += "\n"+ extra;
    			}else{
					productInfo += this.getMessage("orderSku.giftCert")+"\n"+orderSku.getGiftCertificate().getGiftCertificateNo();
				}
				
    			table.addCell(getTdCell(productInfo, lastOne)); 
    			table.addCell(getTdCell(orderSku.getQuantity().toString(), lastOne)); 
	    		
	    		String priceInfo = orderSku.getDiscountPrice().toPlainString();
	    		if(orderSku.getDiscountPrice().compareTo(orderSku.getPrice())==-1){
	    			priceInfo += "\n( 原价：" + orderSku.getPrice()+")";
	    		}
	    		table.addCell(getTdCell(priceInfo, lastOne)); 
	    		table.addCell(getTdCell(orderSku.getSubTotalAmount().toPlainString(), lastOne)); 
    		}
    		
    		p.add(table);
    		
    		PdfPTable panel = new PdfPTable(new float[]{60, 40});
    		panel.getDefaultCell().setBorderWidth(0);
    		panel.getDefaultCell().setBorderWidthBottom(1f);
    		panel.setWidthPercentage(98);
    		panel.addCell(" ");
    		//总计部分
    		PdfPTable table1 = new PdfPTable(new float[]{50, 50});
    		table1.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			table1.getDefaultCell().setBorderWidth(0);
			table1.addCell(getTdCell(this.getMessage("orderShipment.itemSubTotal"), true, false));
			table1.addCell(getTdCell(orderShipment.getItemSubTotal().toPlainString(), false));
			
			table1.addCell(getTdCell(this.getMessage("orderShipment.shippingCost"), true, false));
			table1.addCell(getTdCell(orderShipment.getShippingCost().toPlainString(), false));
			if(orderShipment.getWrapTotal().compareTo(BigDecimal.ZERO)==1){
				table1.addCell(getTdCell(this.getMessage("orderShipment.wrapTotal"), true, false));
				table1.addCell(getTdCell(orderShipment.getWrapTotal().toPlainString(), false));
			}
			if(orderShipment.getDiscountAmount().compareTo(BigDecimal.ZERO)==1){
				table1.addCell(getTdCell(this.getMessage("salesOrder.hyphen.full.shape")+this.getMessage("orderShipment.discountAmount"), true, false));
				table1.addCell(getTdCell(orderShipment.getDiscountAmount().toPlainString(), false));
			}
			if(orderShipment.getItemTax().compareTo(BigDecimal.ZERO)==1){
				table1.addCell(getTdCell(this.getMessage("orderShipment.totalBeforeTax"), true, false));
				table1.addCell(getTdCell(orderShipment.getTotalBeforeTax().toPlainString(), false));
				
				table1.addCell(getTdCell(this.getMessage("orderShipment.itemTax"), true, false));
				table1.addCell(getTdCell(orderShipment.getItemTax().toPlainString(), false));
			}
			table1.addCell(getTdCell(this.getMessage("orderShipment.totalAfterTax"), true, false));
			table1.addCell(getTdCell(orderShipment.getTotalAfterTax().toPlainString(), false));
			
			panel.addCell(table1);
			p.add(panel);
			
    		document.add(p);
    		
    		if(index<size-1){
    			document.newPage();
    			//document.resetPageCount();
    			document.setPageCount(1);
    		}
    		index++;
		}
    }
	
	private PdfPCell getTdCell(String tdText, boolean alignRight, boolean lastOne) throws BadElementException{
		PdfPCell cell = new PdfPCell();
		cell.setBorderWidth(0);
		if(lastOne)
			cell.setBorderWidthBottom(1f);
		if(alignRight)
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		else
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		
		cell.addElement(new Phrase(tdText, fontChinese));
		return cell;
	}
	
    protected Document newDocument() {
		return new Document(PageSize.A4, 15f, 15f, 50f, 70f);
	}
    
    protected void addPageEventOnDocumentOpen(){
		PdfPageEvent4PackingSlip pageEvent = new PdfPageEvent4PackingSlip();
    	writer.setPageEvent(pageEvent);
	}

}