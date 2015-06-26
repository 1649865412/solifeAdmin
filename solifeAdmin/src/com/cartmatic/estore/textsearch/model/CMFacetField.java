package com.cartmatic.estore.textsearch.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CMFacetField<B>
{
	private String name;

	private List<Count> values = null;
	
	private  Number before;
	
	private  Number after;
	
	private final B start;
	
	private final B end;
	
	public CMFacetField(String name,B start){
		this.name=name;
	    this.start = start;
	    this.end = start;
	}
	
	public CMFacetField(String name,B start,B end,Number before,Number after){
		this.name=name;
		this.before=before;
		this.after=after;
	    this.start = start;
	    this.end = end;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Count> getValues() {
     return values;
    }
	
	 public B getStart() {
	    return start;
	  }

	  public B getEnd() {
	    return end;
	  }
	  
	  public Number getBefore() {
	    return before;
	  }

	  public Number getAfter() {
	    return after;
	  }


	/**
    * Insert at the end of the list
    */
   public void add( String name, long cnt )
   {
     if( values == null ) {
       values = new ArrayList<Count>( 30 );
     }
     values.add( new Count( this, name, cnt ) );
   }
   
   public void add( Number startNum,Number endNum, long cnt )
   {
     if( values == null ) {
       values = new ArrayList<Count>( 30 );
     }
     values.add( new Count( this, startNum,endNum, cnt ) );
   }
   
   public void add( Date startDate,Date endDate, long cnt )
   {
     if( values == null ) {
       values = new ArrayList<Count>( 30 );
     }
     values.add( new Count( this, startDate,endDate, cnt ) );
   }
   
   
	
	public static class Count implements Serializable 
	   {
	     private String name = null;
	     private long count = 0;
	     
	     private Number startNum;
	     private Number endNum;
	     
	     private Date startDate;
	     private Date endDate;
	     
	     // hang onto the FacetField for breadcrumb creation convenience
	     private CMFacetField cff = null;
	     
	     public Count( CMFacetField ff, String n, long c )
	     {
	       name = n;
	       count = c;
	       cff = ff;
	     }
	     
	     public Count( CMFacetField ff, Number startNum,Number endNum, long c )
	     {
	       this.startNum = startNum;
	       this.endNum = endNum;
	       count = c;
	       cff = ff;
	     }
	     
	     public Count( CMFacetField ff, Date startDate,Date endDate, long c )
	     {
	       this.startDate = startDate;
	       this.endDate = endDate;
	       count = c;
	       cff = ff;
	     }
	     
	     public String getName() {
	       return name;
	     }
	     
	     public void setName( String n )
	     {
	       name = n;
	     }

	     public long getCount() {
	       return count;
	     }
	     
	     public void setCount( long c )
	     {
	    	 count = c;
	     }
	     
	     public CMFacetField getFacetField() {
	       return cff;
	     }
	     
	     @Override
	     public String toString()
	     {
	       return name+" ("+count+")";
	     }

		public Number getStartNum() {
			return startNum;
		}

		public Number getEndNum() {
			return endNum;
		}

		public Date getStartDate() {
			return startDate;
		}

		public Date getEndDate() {
			return endDate;
		}
	    
	   }
}
