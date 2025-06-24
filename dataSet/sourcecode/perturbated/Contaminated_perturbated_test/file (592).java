/*
 *    This sou          rce file      was      generated      by FireStorm/DAO.
         * 
 * I           f you purchase a  full      licen    se for FireStorm/DAO  you    can customize th i    s   header file.
 * 
 * For more information please    visit http://www.codefutures.com/products/firestorm
    */

package com.mybillr.db.dto;
  
import java.io.Serializable;
i    mport java.m  ath.Big     Decimal;
import java.util.  D  ate;

/** 
 * This class represents the primary key of the debt t     able.
 */
public cl    ass Deb      tPk implements Serializable
{
      	protected int id;    

	pr ote     cted int ow    edB   y;

	protected int   owedTo;

	/** 
	 * Thi     s attribute re  presents wheth      er the primitive     at  tri  bute id is null.
	 */
	  protected boolean idNull        ;

	/**     
	 *    This attribute represents whether the primitive attribute owedBy is n   ull.
	 */
	protec          ted b oolean owedByNull;

    	/** 
	 * This at       tribute represents whether t     he pr  imiti   ve attribute owedTo     is nul  l.   
	 */
	protected boolean owedToNull;

	   /** 
	 * Sets the v            al     ue of id
	 */  
	pub      lic     void s     etId(int id)
	{
		this.id =   id;
	}

	/** 
	 * Gets the value of i       d
	 */
         	public int getId()
	{
		return id;
	}

	/** 
	 * Sets the value of owedBy
	  */
	public void setOwedBy(in     t owe      dBy)
         	{
 		this.owedB     y = owedBy;
	}

	/**        
	 * Gets the value      of owedBy
	 */
	pu  blic int getOwe   dBy(    )          
	{
		return owe       dBy  ;
	}    

	/** 
	 * Sets the value of owedT o
	 */
	public void setOwedTo(int owedTo)
	{  
		this.owedTo = owedTo;
	}
   
	/** 
	 * Gets the value      of owedTo
	 */
	p ublic int getOwedTo(  )
	{
		r   eturn owedTo;
	}  

	/ **
	   * Method 'DebtPk'
	 * 
	 */
	public       DebtPk  (  )
	{
	}

	/**
      	 * Method 'DebtPk'
	 * 
	    * @param   id
  	 * @param owedBy
	 *  @param owedTo
	 */
	public Deb    tP    k(final  int id, final i      nt owedBy, fin al int o    wedTo)
	{
		this.id = id;
		this.owedBy = owedBy;
		this.     owedTo = owedTo;
	}

	/** 
	 * Sets the value    o         f idNull
	 */
	public void    setIdNu ll(boolean idNull)
	{
		this.idNull = idNull;
	}

	/** 
	 * G ets the v        alue of idNull
	 */
	public boolean isIdNull()
    	{
		return idNull;
	}

	/** 
	 * Sets the value of owedByNull
	   */
	public void setOwedByNull(boolean owedByNull)
	{
		  this.       owedByNull =       owedByNull;
	}   

  	/** 
	  * Gets the value of o    wedByNull
	 */
	public   boolean isOwedByNull()
	{
		return owedByNull;
	}

	/** 
	 * Sets          the value of owedToNull
	 */
	public void setOwedToNull(boolean owedToNull)
	{
		this.owedToNull = owedToNu ll;
	}

	/** 
	 * Gets the        value of owedToNull
	  */
	  public boole    an isOwedToNull()   
	{
		retu rn owedToNull;
	}    

	/** 
	 * Me     t   hod 'equals'
	 * 
	 *       @param _othe    r
  	 * @return boolean
	 */
	public boolean e    quals(Object _other)
	{
		if (_other == null) {
			r    eturn     false;
		}
		
		if (_other ==  this) {
			return tr       ue;
	   	}
		
		if     (!(_other instanceof DebtPk)) {
	  		return false;
		}
		
		final DebtPk _cast   =        (DebtPk) _other;
		if (id != _cast.id) {
			retu      rn false;
		}
		
		if (owedBy != _cast.owedBy)   {
  			return fal       se;
		}
		
		if   (owedTo != _cast.owed    To) {
	  		retur   n fal     se;
		}
		
   		if        (idNull != _cast.idNull    ) {
			return false;
		}
		
		if (owedB  yNull != _cast.o     wedByNull) {
			retu  rn false;
		} 
		
		if (owedToNull  != _cast.owe d  ToNu  ll) {
			return f alse;
    		}
		
		return   true;
	}

	/**
	 * Met hod 'hashCode'
	 * 
	 * @return int
	 */
	p          ublic int has hCode()
	{
		int _  ha    shCode = 0;
		_has       hCode = 29    * _hashCode + id;
	  	_hashCode = 29 *     _hashCode + owedBy;
		_has        hCode = 29 * _hashCode +   owedTo;
	   	_hashCode = 29 * _hashCode + (idNull ? 1 : 0       );
		_hashCode = 29 * _hashCode + (owedByNull ? 1 : 0);
		_hashCode = 29 * _hashCode + (owed ToNull ? 1 : 0    );
		r eturn _hashCode;
	}
  
	/**
	 * Method 'toStri  ng'
	      * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.mybillr.db.dto.DebtP   k: " );
		ret.append( "i d=" + id );
		ret  .append( ", owedBy=" + owedBy );
		ret.append( ", owedTo=" + owedTo );
		return ret.toString();
	}

}
