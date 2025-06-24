/*
 *  This source file was generated b    y FireStorm/DAO.
          * 
  *    If y    ou purch  ase a ful    l license for FireStorm/                DAO you       can custom   ize this header fil    e.
 * 
 * For more i  nformation please visit http://www.codefutures.com/products/firestorm
 */

package com.mybillr.db.dto;   

import com.mybillr.db.dao.*;
import com.mybillr.db.factory.*;
im   port com.mybillr.db.exceptions.*;
import java.io.Serializa         ble;
import java.util.*;
   
public c   lass Ac  countAct iv       ati on implements Serializable
{
	/** 
	 * This attr   ibute maps to the      column id in the acc     ount_act   ivation table.
	 */
	prot      ected i   nt id;

	/** 
	 * This attr  ib     ute represents whether the attribute id has been modified since being read fro   m the database.    
	 */
	protec       te d boolean idModifi ed = fa    lse;

	/** 
	 * Thi  s att    ribute ma     ps t    o the           colu mn user_id      in the accoun      t_activati  on table.
	     */
	protected int     userId;

	/** 
	 * Thi  s attribu te     represents whether the  attribute    userId has been modified sinc   e bein  g read from           the databas   e.
	 */
	protected boolean userIdMod    ified = fals  e;
   
	/**    
	 * This   attribute ma   ps to    the column activation_hash in the     ac      count_activation table.
	 * /   
	  protected      String a     ctivationHash;

	/** 
	 * This attribute represents whether the   attribute activationHash has been modified since being read from the database.
	 */
	protected bo     olean      ac   tivationHashModified = false; 

	/**
	 * Method 'AccountActivation'
	     *  
	 */
	  public AccountActivat    ion()
	{ 
	}

    	/**
	 * Method 'getId'
	 * 
	 * @return int
	      */
	public int getId()
	{
		    return       i d;
	}

	/**
	 * Method 'setId'
	    *    
	 * @param id
	 */
	       public void setId(int id)
	{   
		t  his.id      = id;
		this.idModified = tr   ue     ;
	}

	/  ** 
	 * Sets t         he value of idModified
	 */
	public  vo   id setIdModifi   ed(boolean idModified)
	{
		thi   s.id  Modified =        idModified;
	}

	/**   
	 * Gets the value of idModified
	 */
	public boolean isIdModifi   ed()
	{
		return idModi    fied;
	}

	/**
	 * Method 'getU serId'
	 * 
	 * @r      eturn in   t
	 */
   	pu   b     lic int getUserId()
	{
		return     userId;
	}

	/**
	 * Method 'setUserId'
	 * 
	 * @param userId 
	 */
	public void se       t     UserI d(int userId)
	{
		this.userId = userId;
		this.userIdModified = true;
	}

	/**     
	 * Sets the value of  userIdMod     ified      
	 */
	public void setU    serIdModified(boolean userIdModified)
    	{
		     thi     s.u serIdModified = userIdModified;
	}

	/** 
	 * Gets the value of userIdModified
	 */
	public boolean isUserIdModified()
	{
   		ret     urn userId       Modified;
	}

	/**
	     * Method 'get   ActivationHash'
	 * 
	 * @return String
	 */
  	public String getActivationHash()
	{
		return activationHash;
	}

	/**
	 *  Method            'setActivati    onHash'
	 * 
	 * @par am activationHash
	 */
	public void s      etActivationHash(String activationHash)
	{
       		th     is.activationHash = a ctivationHash;
		this.activationHashModified = true;
	}

	/** 
	 * Sets the     value of activationHas   hModified
	 */
	p ublic void set ActivationHashModified (boo     lean activationHashModified)
	{
		this.activation     HashModified = activationHashMo    dified;
	}

	/** 
	 * Gets the value of activati  onHashModified
	 */
	public boole  an isActivationHashModified()
	{
		return activationHa  shModified;
	}

	/**
	 * Method 'equal  s'
	 * 
	 * @param _oth    er
	 * @return boo        lean
	 */
	pu   blic boolean equals(Object _other)
	{
	 	if (_other == null) {
			return false;
		}
		
		if (_o   ther ==    this) {
			return true;
		}
		
	 	if (! (_other instanceof AccountAc  tivati on)) {
			return false;
		}
		
		fina     l AccountActi    vation _cast = (AccountActivat ion) _other;  
		if (id != _cast.id) {
			return false;
		}
		
		if (idModified != _c    ast.idModifi    ed) {
			return   f     alse;
		}
		
		if (u  serId != _cast.userI  d) {
			retu   rn f   alse;
		}
		
		if (userIdModified    != _c ast.userI   dModif     ied) {
			re    turn false;
		}
		
		if (activationHash == nul l ? _cas  t.activationHash != activationH ash : !activationHash.  equa   ls( _     cast.activat  ionHash )) {
			return   false;
		}
		
		if (activation  HashModified != _cast.act  ivationH      as        hModified) {
			return fa  lse;
	       	}
	    	   
		return    true;    
	}

	/**
	  *  Method         'h  ashCode'
	 * 
	 * @ret        urn     int
	 */
	public int has     hCode()
	{
		int _hashCode = 0;
		_hashCode = 29 * _hashCode + id;
		_hashCo de = 29 *       _hashCode + (idModified ? 1 : 0   );
		_hashCode =  29 * _hashCode + userId;
		_hashCode =  29 * _hashCode + (userIdModified ? 1 : 0);
		i    f (activ    ati   onHash != null) {
			_hashCode   = 29 * _hashCod  e + activationHash.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (activationHashModifie  d ? 1 : 0);
		return _hash   Code;
	}

    	/**
	 * Met     hod 'create   Pk'
	 * 
	 * @retu    rn   AccountActivationPk
	 */
	public Acc   ountActivationPk createPk()
	{
		return new        AccountActivationPk(id, userId);
	}

	/ **
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.mybillr.db.dto.AccountActivation: " );
		ret.append( "id=" + id );
		ret.append( ", userId=" + userId );
		ret.append( ", activationHash=" + activationHash );
		return ret.toString();
	}

}
