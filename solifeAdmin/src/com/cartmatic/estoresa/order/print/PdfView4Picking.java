/**
 * 
 */
package com.cartmatic.estoresa.order.print;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cartmatic.estore.common.model.order.OrderShipment;
import com.cartmatic.estore.common.model.order.OrderSku;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.opensymphony.oscache.util.StringUtil;

/**
 * 备货单打印
 * 
 */
public class PdfView4Picking extends PdfAbstractView {    
	
	public void buildPdfDocument(Map model, Document document,    
            PdfWriter writer, HttpServletRequest request,    
            HttpServletResponse response) throws Exception {  
		
		BaseFont bf = BaseFont.createFont("STSong-Light",	"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		fontChinese = new Font(bf, 12, Font.NORMAL); 
		
		Set<OrderShipment> orderShipments = (Set<OrderShipment>)model.get("orderShipments");
		int curIndex = 0;
		int size = orderShipments.size();
		for(OrderShipment orderShipment: orderShipments){
			Paragraph p = new Paragraph();
			PdfPTable table1 = new PdfPTable(new float[]{30, 70});
			table1.getDefaultCell().setBorderWidth(0);
			table1.setWidthPercentage(100);
			table1.addCell(new Phrase(this.getMessage("order.shipment.title")+this.getMessage("common.message.colon")+orderShipment.getShipmentNo(), fontChinese));
			table1.addCell(new Phrase(this.getMessage("order.shipment.receiver")+this.getMessage("common.message.colon")+orderShipment.getOrderAddress().getFirstname() + " " + orderShipment.getOrderAddress().getLastname(), fontChinese));
			table1.addCell("\n");
			table1.addCell("\n");
			p.add(table1);
			
			PdfPTable table = new PdfPTable(new float[]{8, 19, 8, 32, 33});
			table.setHeaderRows(1);
			table.setWidthPercentage(98);
    		
    		table.addCell(getThCell(this.getMessage("common.message.ordinalNo"))); 
    		table.addCell(getThCell(this.getMessage("orderSku.productSkuCode"))); 
    		table.addCell(getThCell(this.getMessage("orderSku.quantity"))); 
    		table.addCell(getThCell(this.getMessage("orderSku.productName"))); 
    		table.addCell(getThCell(this.getMessage("orderSku.displaySkuOptions"))); 
    		
    		Set<OrderSku> orderSkus = orderShipment.getOrderSkus();
    		int i = 1;
    		int skuSize = orderSkus.size();
    		for(OrderSku orderSku: orderSkus){
    			boolean lastOne = (i==skuSize);
    			table.addCell(getTdCell(String.valueOf(i++), lastOne)); 
    			String extra = orderSku.getProductSku().getProduct().getExtra1();
    			if (extra == null)
    			{
    				extra = "";
    			}
    			else
    				extra = "\n"+extra;
	    		table.addCell(getTdCell(orderSku.getProductSkuCode()+extra , lastOne)); 
	    		table.addCell(getTdCell(orderSku.getQuantity().toString(), lastOne)); 
	    		table.addCell(getTdCell(orderSku.getProductName(), lastOne));
	    		//处理skuOption和accessory的显示.
	    		String skuopt = "";
	    		if (!StringUtil.isEmpty(orderSku.getDisplaySkuOptions()))
	    		{
	    			String[] opts = orderSku.getDisplaySkuOptions().split("###");
	    			for (String opt: opts)
	    				skuopt += opt +" ";
	    		}
	    		if (!StringUtil.isEmpty(orderSku.getAccessories()))
	    		{
	    			net.sf.json.JSONObject obj = net.sf.json.JSONObject.fromObject(orderSku.getAccessories());
	    			net.sf.json.JSONArray array = obj.names();
	    			for (Object name: array)
	    			{
	    				String strName = (String) name;
	    				if (!strName.endsWith("_price"))
	    				{
	    					skuopt += strName+" - "+obj.get(name)+" ";
	    				}				
	    			}
	    		}
	    		table.addCell(getTdCell(skuopt, lastOne)); 
    		}
    		
    		p.add(table);
    		curIndex++;
    		if(curIndex<size)
    			p.add("\n\n");
    		document.add(p);
    	}
    }
	
	protected void addPageEventOnDocumentOpen(){
		PdfPageEvent4PickList pageEvent = new PdfPageEvent4PickList();
    	writer.setPageEvent(pageEvent);
	}
	
}