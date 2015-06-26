/*
 * Created on Sep 21, 2006
 *
 */
package com.cartmatic.estore;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.core.model.Message;

/**
 * @author Ryan
 * 
 */
public class Test {
	protected static final Log logger = LogFactory.getLog(Test.class);

	public static void main(String[] args) throws Exception {
		System.out.println(URLEncoder.encode("Belts Scarves & Accessories"));
	}

	public static void genDiff() throws Exception {
		List<String> listAll = new ArrayList<String>();
		List<String> listUsed = new ArrayList<String>();

		BufferedReader irAll = new BufferedReader(new FileReader("d:/list.txt"));
		BufferedReader irUsed = new BufferedReader(new FileReader("d:/list2.txt"));

		String line = irUsed.readLine();
		while (line != null) {
			listUsed.add(line);
			line = irUsed.readLine();
		}

		String allUsed = listUsed.toString();
		System.out.println("all used:" + allUsed);

		line = irAll.readLine();
		while (line != null) {
			listAll.add(line);
			if (allUsed.indexOf(line) == -1) {
				System.out.println(line);
			}
			line = irAll.readLine();
		}
	}
}
