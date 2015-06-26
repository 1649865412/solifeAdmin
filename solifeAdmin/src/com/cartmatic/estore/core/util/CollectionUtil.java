/*
 * 2006-11-2
 * 
 * TODO
 */

package com.cartmatic.estore.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class CollectionUtil {
	public static void addAllNotExists(Collection c1, Collection c2) {
		for (Iterator iterator = c2.iterator(); iterator.hasNext();) {
			addNotExists(c1, iterator.next());
		}
	}

	public static void addNotExists(Collection<Object> c, Object obj) {
		if (!c.contains(obj)) {
			c.add(obj);
		}
	}

	public static List getDifferenceOfTwoList(List originList, List targetList) {
		List diff = new ArrayList();
		for (Iterator it = originList.iterator(); it.hasNext();) {
			Object obj = it.next();
			if (!targetList.contains(obj)) {
				diff.add(obj);
			}
		}
		return diff;
	}
}
