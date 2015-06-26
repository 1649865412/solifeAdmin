package com.cartmatic.extend.supplier.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.supplier.TbCatPropValueRefer;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.ItemCat;
import com.taobao.api.domain.ItemProp;
import com.taobao.api.domain.PropValue;
import com.taobao.api.request.ItemcatsGetRequest;
import com.taobao.api.request.ItempropsGetRequest;
import com.taobao.api.request.ItempropvaluesGetRequest;
import com.taobao.api.response.ItemcatsGetResponse;
import com.taobao.api.response.ItempropsGetResponse;
import com.taobao.api.response.ItempropvaluesGetResponse;

public class TaoBaoApiHelper {
	private Logger logger=Logger.getLogger(TaoBaoApiHelper.class);
	final static TaoBaoApiHelper taoBaoApiHelper=new TaoBaoApiHelper();
	private String url="http://gw.api.taobao.com/router/rest";
	
	private String appKey="21349448";
	private String appSecret="9b0f4bf28590e3b20a18f91beb737c7d";
	
	private ConfigUtil configUtil;
	
	private String getAppkey() {
		if(configUtil!=null){
			configUtil.getTaoBaoAppKey();
		}
		return appKey;
	}
	
	private String getAppSecret() {
		if(configUtil!=null){
			configUtil.getTaoBaoAppSecret();
		}
		return appSecret;
	}

	private void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public static void main(String[] args) throws Exception {
		TaoBaoApiHelper taoBaoApiHelper=TaoBaoApiHelper.getInstance();
		taoBaoApiHelper.setConfigUtil(null);
		String name=taoBaoApiHelper.getCatFullName(new Long(50010850));
		System.out.println(name);
		System.out.println(taoBaoApiHelper.getItemPropName(new Long(50010850), new Long(1627207)));
		System.out.println(taoBaoApiHelper.getPropValueName(new Long(50010850), new Long(1627207), new Long(28338)));
	}
	
	private TaoBaoApiHelper(){
		configUtil=ConfigUtil.getInstance();
	}
	
	public static TaoBaoApiHelper getInstance(){
		return taoBaoApiHelper;
	}
	
	public String getCatFullName(Long cId){
		try {
			List<String>catNameList=new ArrayList<String>();
			ItemCat itemCat= getItemCat(cId);
			while(itemCat!=null){
				if(itemCat.getParentCid()==null||itemCat.getParentCid()<0){
					break;
				}
				catNameList.add(0,itemCat.getName());
				itemCat=getItemCat(itemCat.getParentCid());
			}
			if(catNameList.size()>0){
				String names=StringUtils.join(catNameList,">");
				return names;
			}
		} catch (ApiException e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
		}
		return "";
	}
	
	private  ItemCat getItemCat(Long cId) throws ApiException{
		TaobaoClient client=new DefaultTaobaoClient(url, getAppkey(), getAppSecret());
		ItemcatsGetRequest req=new ItemcatsGetRequest();
		req.setFields("cid,parent_cid,name,is_parent");
		req.setCids(cId+"");
		ItemcatsGetResponse response = client.execute(req);
		List<ItemCat> itemCats=response.getItemCats();
		if(itemCats!=null&&itemCats.size()>0){
			return itemCats.get(0);
		}else{
			return null;
		}
	}
	
	public  String getItemPropName(Long cId,Long pid){
		try {
			ItemProp itemProp=getItemProp(cId, pid);
			if(itemProp!=null){
				return itemProp.getName();
			}
		} catch (ApiException e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
		}
		
		return "";
	}
	
	private  ItemProp getItemProp(Long cId,Long ipId) throws ApiException{
		TaobaoClient client=new DefaultTaobaoClient(url, getAppkey(), getAppSecret());
		ItempropsGetRequest req=new ItempropsGetRequest();
		req.setFields("pid,name");
		req.setCid(cId);
		req.setPid(ipId);
		ItempropsGetResponse response = client.execute(req);
		List<ItemProp> itemProps=response.getItemProps();
		if(itemProps!=null&&itemProps.size()>0){
			return itemProps.get(0);
		}else{
			return null;
		}
	}
	
	
	public  String getPropValueName(Long cId,Long ipId,Long ipvId) {
		try {
			PropValue propValue=getPropValue(cId, ipId,ipvId);
			if(propValue!=null){
				return propValue.getName();
			}
		} catch (ApiException e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
		}
		return "";
	}
	
	private  PropValue getPropValue(Long cId,Long ipId,Long ipvId) throws ApiException{
		TaobaoClient client=new DefaultTaobaoClient(url, getAppkey(), getAppSecret());
		ItempropvaluesGetRequest req=new ItempropvaluesGetRequest();
		req.setFields("name");
		req.setCid(cId);
		req.setPvs(ipId+":"+ipvId);
		ItempropvaluesGetResponse response = client.execute(req);
		
		List<PropValue> propValues=response.getPropValues();
		if(propValues!=null&&propValues.size()>0){
			return propValues.get(0);
		}else{
			return null;
		}
	}
	
	public List<TbCatPropValueRefer> strToTbCatPropValueRefer(String sellCatePropsStr){
		List<TbCatPropValueRefer> tbCatPropValueReferList=new ArrayList<TbCatPropValueRefer>();
		//tb_sell_catProps 淘宝销售属性组合,机选择性选项属性
		//39.900000:1580:m062176:1627207:30156;20509:28383;39.900000:1500:m062176:1627207:90554;20509:28383;39.900000:1600:m062176:1627207:28320;20509:28383;39.900000:1570:m062176:1627207:3232480;20509:28383;
		String sell_cateProps_temp_arr[]=sellCatePropsStr.split(";");
		for (String sell_cateProps_temp : sell_cateProps_temp_arr) {
			String temp_arr1[]=sell_cateProps_temp.split(":");
			Long tbCatPropId = null,tbCatPropValueId = null;
			if(temp_arr1.length==2){
				tbCatPropId=new Long(temp_arr1[0]);
				tbCatPropValueId=new Long(temp_arr1[1]);
			}else if(temp_arr1.length>=5){
				tbCatPropId=new Long(temp_arr1[temp_arr1.length-2]);
				tbCatPropValueId=new Long(temp_arr1[temp_arr1.length-1]);
			}
			TbCatPropValueRefer tbCatPropValueRefer=new TbCatPropValueRefer();
			tbCatPropValueRefer.setTbCatPropId(tbCatPropId);
			tbCatPropValueRefer.setTbCatPropValueId(tbCatPropValueId);
			tbCatPropValueReferList.add(tbCatPropValueRefer);
		}
		return tbCatPropValueReferList;
	}
	
}
