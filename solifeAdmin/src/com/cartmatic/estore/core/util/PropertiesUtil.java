
package com.cartmatic.estore.core.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Faith Yan extract properties contents by rules(you can change isNeed
 *         method)
 */
public class PropertiesUtil {
	private final static String		eStoreOriginProperties		= "D:\\workspace\\eStore\\common\\ApplicationResources_en.properties";

	private final static String		eStoreTargetProperties		= "D:\\workspace\\eStore\\common\\ApplicationResources_en_temp.properties";

	private final static boolean	NEED_SEPARATE_LINE			= true;

	private final static String		storeAdminOriginProperties	= "D:\\workspace\\StoreAdmin\\common\\ApplicationResources_en.properties";

	private final static String		storeAdminTargetProperties	= "D:\\workspace\\StoreAdmin\\common\\ApplicationResources_en_temp.properties";

	public static void main(String[] args) throws Exception {
		PropertiesUtil pu = new PropertiesUtil();
		System.out.println("===== begin gen eStore properties =====");
		int ret = pu.genEStore();
		System.out.println("result = " + ret);
		System.out.println("===== end gen eStore properties =====");
		System.out.println("===== begin gen StoreAdmin properties =====");
		ret = pu.genStoreAdmin();
		System.out.println("result = " + ret);
		System.out.println("===== end gen StoreAdmin properties =====");

	}

	private boolean addSeparateLine(String key, String prevKey) {
		if (!key.equals(prevKey)) {
			return true;
		}
		return false;
	}

	private boolean allIsBlank(String value) {
		char[] cs = value.toCharArray();
		for (int i = 0; i < cs.length; i++) {
			if (cs[i] != ' ') {
				return false;
			}
		}
		return true;
	}

	private List analyseData(Map data) {
		List analysed = new ArrayList();
		for (Iterator it = data.keySet().iterator(); it.hasNext();) {
			String key = it.next().toString();
			String value = data.get(key).toString();
			if (this.isNeed(value)) {
				analysed.add(this.buildLine(key, value));
			}
		}
		Collections.sort(analysed);

		return analysed;
	}

	private String buildLine(String key, String value) {
		return key + "=" + value;
	}

	private List convertToList(final Map map) {
		List list = new ArrayList();
		String pervKey = "";
		for (Iterator it = map.keySet().iterator(); it.hasNext();) {
			String key = it.next().toString();
			if (NEED_SEPARATE_LINE && this.addSeparateLine(key, pervKey)) {
				list.add("");
			}
			list.add(this.buildLine(key, map.get(key).toString()));
			pervKey = key;
		}
		Collections.sort(list);
		return list;
	}

	private int countBlanks(String value) {
		String temp = value;
		int blanks = 0;
		int start = 0;
		while (true) {
			start = indexOfBlank(temp);
			if (start > -1) {
				blanks++;
			} else {
				break;
			}
			temp = temp.substring(start + 1);
		}
		return blanks;
	}

	private String[] extractLine(String line) {
		String[] sKeyAndValue = new String[2];
		sKeyAndValue[0] = line.substring(0, line.indexOf("="));
		sKeyAndValue[1] = line.substring(line.indexOf("=") + 1);
		return sKeyAndValue;
	}

	public int genEStore() throws Exception {
		Map map = this.readProperties(eStoreOriginProperties);
		List list = this.analyseData(map);
		return this.writeProperties(list, eStoreTargetProperties);
	}

	public int genProperties(String originFile, String targetFile)
			throws Exception {
		Map map = this.readProperties(originFile);
		List list = this.analyseData(map);
		return this.writeProperties(list, targetFile);
	}

	public int genStoreAdmin() throws Exception {
		Map map = this.readProperties(storeAdminOriginProperties);
		List list = this.analyseData(map);
		return this.writeProperties(list, storeAdminTargetProperties);
	}

	private int indexOfBlank(String value) {
		if (value.indexOf(" ") > -1) {
			return value.indexOf(" ");
		}
		if (value.indexOf("  ") > -1) {
			return value.indexOf("  ");
		}
		return -1;
	}

	private boolean isAnotationOrEmpty(String line) {
		if (line == null || line.equals("") || allIsBlank(line)
				|| line.startsWith("#")) {
			return true;
		}
		return false;
	}

	private boolean isNeed(String value) {
		if (value == null || value.equals("")) {
			return false;
		}
		if (value.length() > 20 && this.countBlanks(value) > 1
				&& value.indexOf("successfully.") < 0) {
			return true;
		}
		return false;
	}

	private Map merge(final Map mapFrom, final Map mapTo) {
		Map mapRet = new HashMap();
		for (Iterator it = mapTo.keySet().iterator(); it.hasNext();) {
			String key = it.next().toString();
			if (mapFrom.containsKey(key)) {
				mapRet.put(key, mapFrom.get(key).toString());
			} else {
				mapRet.put(key, mapTo.get(key).toString());
			}
		}
		return mapRet;
	}

	public int mergeEStore() throws Exception {
		Map mapFrom = this.readProperties(eStoreTargetProperties);
		Map mapTo = this.readProperties(eStoreOriginProperties);
		Map mapMerge = this.merge(mapFrom, mapTo);
		List list = this.convertToList(mapMerge);
		return this.writeProperties(list, eStoreOriginProperties);
	}

	public int mergeProperties(String fromFile, String toFile) throws Exception {
		Map mapFrom = this.readProperties(fromFile);
		Map mapTo = this.readProperties(toFile);
		Map mapMerge = this.merge(mapFrom, mapTo);
		List list = this.convertToList(mapMerge);
		return this.writeProperties(list, toFile);
	}

	public int mergeStoreAdmin() throws Exception {
		Map mapFrom = this.readProperties(storeAdminTargetProperties);
		Map mapTo = this.readProperties(storeAdminOriginProperties);
		Map mapMerge = this.merge(mapFrom, mapTo);
		List list = this.convertToList(mapMerge);
		return this.writeProperties(list, storeAdminOriginProperties);
	}

	private Map readProperties(String fileName) throws Exception {
		Map map = new HashMap();
		FileReader origin = new FileReader(fileName);
		BufferedReader br = new BufferedReader(origin);
		String line = br.readLine();
		while (line != null) {
			if (this.isAnotationOrEmpty(line)) {
				line = br.readLine();
				continue;
			}
			String[] kv = this.extractLine(line);
			map.put(kv[0], kv[1]);
			line = br.readLine();
		}
		br.close();
		origin.close();
		return map;

	}

	private int writeProperties(List data, String fileName) throws Exception {
		try {
			FileWriter fw = new FileWriter(fileName);
			BufferedWriter bw = new BufferedWriter(fw);
			for (Iterator it = data.iterator(); it.hasNext();) {
				String line = it.next().toString();
				bw.write(line);
				bw.newLine();
			}
			bw.flush();
			fw.close();
			return 1;
		} catch (IOException e) {
			return 0;
		}
	}

}
