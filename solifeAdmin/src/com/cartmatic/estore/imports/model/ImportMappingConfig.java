
package com.cartmatic.estore.imports.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.cartmatic.estore.imports.handler.ImportClause;
import com.cartmatic.estore.imports.handler.PersistenceHandler;

public class ImportMappingConfig {
	/**
	 * 配置需处理的Column
	 */
	private List<Column>	columns	= new ArrayList<Column>();
	/**
	 * 配置的默认值Column
	 */
	private  List<Column> initData=new ArrayList<Column>();
	/**
	 * 导入的数据文件真实的列头
	 */
	private List<String> realHeaders=new ArrayList<String>();
	/**
	 * 本导入配置处理的对象
	 */
	private String target;
	/**
	 * 本导入配置处理的对象的持久会Handler
	 */
	private PersistenceHandler persistenceHandler;
	
	/**
	 *处理条件 
	 */
	private ImportClause importClause; 
	
	public ImportClause getImportClause() {
		return importClause;
	}

	public void setImportClause(ImportClause importClause) {
		this.importClause = importClause;
	} 

	public void setPersistenceHandler(PersistenceHandler persistenceHandler) {
		this.persistenceHandler = persistenceHandler;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * 配置的默认值Column
	 * @return
	 */
	private List<Column> getInitData() {
		return initData;
	}

	public void setInitData(List<Column> initData) {
		this.initData = initData;
	}

	/**
	 * 配置需处理的Column
	 * @return
	 */
	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
	
	/**
	 * 获取所有真正可处理的Column
	 * @return
	 */
	public List<Column> getAllColumns(){
		//配置的覆盖init的,暂不支持ColumnHeader用split分隔多列的处理
		Set<Column> colums=new HashSet<Column>();
		//tempColums导入文件真正存在的colums
		Set<Column> tempColums=new HashSet<Column>();
		List<Column> tempAllColumns=getColumns();
		for (Column column : tempAllColumns) {
			for (String realHeader : realHeaders) {
				if(checkColumnIsAvailability(column, realHeader)){
					tempColums.add(column);
					break;
				}
			}
		}
		//initColumns真正可用的默认column
		Set<Column> initColumns=new HashSet<Column>();
		//tempInitColumns 配置的默认值Column
		List<Column> tempInitColumns=getInitData();
		for (Column initColumn : tempInitColumns) {
			boolean isInit=true;
			for (Column tempColumn : tempColums) {
				if(initColumn.getAttrName().equals(tempColumn.getAttrName())){
					isInit=false;
					break;
				}
			}
			if(isInit){
				initColumn.setInitData(true);
				initColumns.add(initColumn);
			}
		}
		
		colums.addAll(tempColums);
		colums.addAll(initColumns);
		//result最后需要用上的column,包括有效的默认值column和导入配置真正需处理的有效column
		List<Column> result=new ArrayList<Column>();
		for (Column column : colums) {
			result.add(column);
		}
		//按index排序，index越小的越优先处理
		sort(result);
		return result;
	}
	
	/**
	 * 检查Column是否有效
	 * @param column
	 * @param header
	 * @return
	 */
	private boolean checkColumnIsAvailability(Column column,String header){
		if(column.getColumnHeader().equals(header)){
			return true;
		}
		if(StringUtils.isNotEmpty(column.getColumnHeaderSplit())){
			String headers[]=column.getColumnHeader().split(column.getColumnHeaderSplit());
			for (String string : headers) {
				if(string.equals(header)){
					return true;
				}
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private void sort(List<Column> columns) {
		Collections.sort(columns, new Comparator() {
			public int compare(Object o1, Object o2) {
				Column column1 = (Column) o1;
				Column column2 = (Column) o2;
				if (column1.getIndex() != null && column2.getIndex() != null) {
					return column1.getIndex() - column2.getIndex();
				} else {
					if (column1.getIndex() != null
							&& column2.getIndex() == null) {
						return -1;
					} else if (column1.getIndex() == null
							&& column2.getIndex() != null) {
						return 1;
					} else {
						return 0;
					}
				}

			}

		});

	}

	/**
	 * 导入的数据文件真实的列头
	 * @return
	 */
	public List<String> getRealHeaders() {
		return realHeaders;
	}

	public void setRealHeaders(List<String> realHeaders) {
		this.realHeaders = realHeaders;
	}

	public PersistenceHandler getPersistenceHandler() {
		return persistenceHandler;
	}
}
