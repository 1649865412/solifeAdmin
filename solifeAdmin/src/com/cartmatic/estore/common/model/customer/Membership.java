package com.cartmatic.estore.common.model.customer;

import com.cartmatic.estore.common.model.customer.base.MembershipTbl;

/**
 * Model class for Membership. Add not database mapped fileds in this class.
 */
public class Membership extends MembershipTbl {

  	/**
	 * Default Empty Constructor for class Membership
	 */
	public Membership () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Membership
	 */
	public Membership (
		 Integer in_membershipId
		) {
		super (
		  in_membershipId
		);
	}
	
	/**
	 * Compare this Membership's level with specified Membership.
	 * @param val
	 * @return -1, 0, or 1 as this level is numerically less than, equal to, or greater than val.
	 */
	public int compareTo(Membership val)
	{
	    return this.getMembershipLevel().compareTo(val.getMembershipLevel());
	}

}
