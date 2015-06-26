package com.cartmatic.estore.content.model;

import com.cartmatic.estore.core.util.UrlUtil;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * User: fire
 * Date: 2007-7-13
 * Time: 11:03:40
 */
public class ColumnRepository implements Serializable{
    private ColumnComponent rootColumn;
    private List columnList = new ArrayList();

    public ColumnComponent getRootColumn() {
        return rootColumn;
    }

    public void setRootColumn(ColumnComponent rootColumn) {
        this.rootColumn = rootColumn;
    }

    public List getColumnList() {
		return columnList;
	}



	public void setColumnList(List columnList) {
		this.columnList = columnList;
	}

	public List getSelectedColumnList(String url){

		List selectedList=new ArrayList();
		int selectedIndex=-1;
		ColumnComponent smc=null;
		for(int i=0;i<this.columnList.size();i++){
			 smc= (ColumnComponent) columnList.get(i);
			if(smc.getUrl().equals(url.trim())){


				selectedIndex=i;
				break;
			}
		}
		for(;selectedIndex>-1;selectedIndex--){
			 smc= (ColumnComponent) columnList.get(selectedIndex);
			if(smc.getLevel()==1){
				break;
			}
		}

		if(selectedIndex!=-1){
		 int selectedMenuLevel=smc.getLevel();
		 selectedList.add(smc);
		 int nextLevel=0;
		 while(selectedIndex+1< columnList.size()){
			 ColumnComponent nextMenu= (ColumnComponent) columnList.get(selectedIndex+1);
			 nextLevel=nextMenu.getLevel();
			 if(selectedMenuLevel==nextLevel)
				 break;
			 selectedList.add(nextMenu);
			 selectedIndex++;
		 }
		}else{
			selectedList= columnList;

		}

		return selectedList;
	}
    public String getSelectedColumnIdByUrl(String url){
        String rootColumnUrl=rootColumn.getUrl();
           rootColumnUrl=rootColumnUrl.replace("_dy","");
        if(rootColumn.getUrl().equals(url.trim()))
           return rootColumn.getId();
        if(rootColumnUrl.equals(url.trim()))
           return rootColumn.getId();
        int selectedIndex=-1;
        ColumnComponent smc=null;
        if(rootColumn.getCode().equals("infos")&&url.contains("doActionshowContent")){
             String columnCode = UrlUtil.getParamFromUrl(
                    url.trim(), "columnCode");
            for(int i=0;i<this.columnList.size();i++){
                smc= (ColumnComponent) columnList.get(i);
                String smcCode=smc.getCode();
              if(smcCode.equals(columnCode)){
                 selectedIndex=i;
				 break;
			   }
            }

        }

      else{
		for(int i=0;i<this.columnList.size();i++){
			 smc= (ColumnComponent) columnList.get(i);
             String smcColumnUrl=smc.getUrl();
             smcColumnUrl=smcColumnUrl.replace("_dy","");
               if(smc.getUrl().equals(url.trim())){
            	selectedIndex=i;
				break;
			   }
              if(smcColumnUrl.equals(url.trim())){
            	selectedIndex=i;
				break;
			   }

        }
     }
        for(;selectedIndex>-1;selectedIndex--){
			 smc= (ColumnComponent) columnList.get(selectedIndex);
			if(smc.getLevel()==1){
				break;
			}
		}
		if(selectedIndex!=-1){
	       return smc.getId();
		}else{
		   return "-1";

		}
    }
    public String getSelectedColumnIdByCatPath(String url,String categoryPath){

		int selectedIndex=-1;
		ColumnComponent smc=null;
		for(int i=0;i<this.columnList.size();i++){
			 smc= (ColumnComponent) columnList.get(i);
			if(categoryPath!=null&&!categoryPath.equals("")){
				if(smc.getCategoryPath().equals(categoryPath.trim())){
	            	selectedIndex=i;
					//break;
				}

			}
			else{
			   if(smc.getUrl().equals(url.trim())){
            	selectedIndex=i;
				break;
			   }
			}
		}
		for(;selectedIndex>-1;selectedIndex--){
			 smc= (ColumnComponent) columnList.get(selectedIndex);
			if(smc.getLevel()==1){
				break;
			}
		}
		if(selectedIndex!=-1){
	       return smc.getId();
		}else{
		   return "1";

		}
	}
    public Integer getCurrentLevel(String url,String categoryPath){
		int selectedIndex=-1;
		ColumnComponent smc=null;
		for(int i=0;i<this.columnList.size();i++){
			 smc= (ColumnComponent) columnList.get(i);
			if(categoryPath!=null&&!categoryPath.equals("")){
				if(smc.getCategoryPath().equals(categoryPath.trim())){
	            	selectedIndex=i;
					//break;
				}

			}
			else{
			   if(smc.getUrl().equals(url.trim())){
            	selectedIndex=i;
				break;
			   }
			}
		}
		for(;selectedIndex>-1;selectedIndex--){
			 smc= (ColumnComponent) columnList.get(selectedIndex);
			if(smc.getLevel()==1){
				break;
			}
		}
		if(selectedIndex!=-1){
	       return smc.getLevel();
		}else{
		   return 0;

		}
    }
}
