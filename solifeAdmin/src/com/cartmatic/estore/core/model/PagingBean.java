/*
 * Created on 2006-1-21
 * 
 */

package com.cartmatic.estore.core.model;


/**
 * How to use: 1.(optional) call setItemsPerPage if want to use non-default
 * settings 2.set requested page by calling setCurrentPage 3.pass on to DAO
 * 5.DAO count all and call setTotalItems, the bean will then call the recalc
 * (Note: the bean is changed and BA/BO share the same changes) 6.DAO call
 * getFirstResult and get the coresponding records 7.DAO return the resulting
 * list Note: step 5 and 6 must be in the same transaction
 * 
 * @author Ryan
 * 
 */
public class PagingBean {
	/**
	 * TODO, read from config. And user can set preferrence.
	 */
	private static int						defItemsPerPage		= 10;

	private static int						rewindFastForwardBy	= 10;

	private int								currentPage			= 1;

	private int								firstResult;

	private int								itemsPerPage = defItemsPerPage;

	private int								numberOfItems;

	private int								numberOfPages;


	public void forward(int aRewindFastForwardBy) {
		int newPageNumber = currentPage + aRewindFastForwardBy;
		if (newPageNumber > this.numberOfPages) {
			currentPage = this.numberOfPages;
		} else {
			currentPage = newPageNumber;
		}
	}

	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * 0 base, starting from 0
	 * 
	 * @return
	 */
	public int getFirstResult() {
		return firstResult;
	}

	/**
	 * Alias for spring-hibernate
	 * 
	 * @return
	 */
	public int getMaxResults() {
		return itemsPerPage;
	}

	public int getNumberOfItems() {
		return numberOfItems;
	}

	public int getNumberOfPages() {
		return numberOfPages;
	}

	
	public void next() {
		forward(1);
	}

	public void previous() {
		rewind(1);
	}

	/**
	 * Calculate and then cleanup
	 * 
	 */
	private void recalc() {
		

		numberOfPages = (int) Math.ceil((double) numberOfItems / (double) itemsPerPage);
//		if (currentPage > numberOfPages) {
//			currentPage = numberOfPages;
//		}
		firstResult = (currentPage - 1) * itemsPerPage;


	}

	public void rewind() {
		rewind(rewindFastForwardBy);
	}

	public void rewind(int aRewindFastForwardBy) {
		int newPageNumber = currentPage - aRewindFastForwardBy;
		if (newPageNumber < 0) {
			currentPage = 0;
		} else {
			currentPage = newPageNumber;
		}
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	
	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}


	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	public void setNumberOfItems(int numberOfItems) {
		this.numberOfItems = numberOfItems;
		recalc();
	}
}
