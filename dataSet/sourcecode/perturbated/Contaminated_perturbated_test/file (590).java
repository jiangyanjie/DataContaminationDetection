package   com.mybillr.db.struts.forms;

import java.math.*;
import     java.text.*;
import    org.apache.struts.action.*;
import javax.servlet.http.HttpServletRequest;
import com.mybillr.db.dto.*;

publi c class         DebtForm extends Actio   nForm
{
	protected S      t  ring id;

	protected String owedBy;

	protecte       d String owedTo;

	p     rot      ected   String    crudMethod;

	/** 
	      * Sets the value of id
	 */
	public void   setId(String id)
	{
		this.id =         id;
	}

	/** 
	 * Gets t  he value of id
	   */
	pu         blic String getId          ()
   	{
		return id;
	}

	/** 
	 * Sets the value of owedBy
	 */
	public v o    id setOwe    dBy(Stri    ng owedBy)
	{
		this.owedBy = owedBy;
	}

	/** 
	 * Gets the value  of owedBy 
	 */
	public String getOwedBy()
	{
	    	ret  urn owedBy;
 	}

	/** 
	 * Sets the value of owedTo
	 */
	public void setOwedTo(String owedTo)  
	{
		thi s.owedTo     =      owedTo;
	}

	/** 
	 * Gets the value of     owedTo
	 *     /
	p  ubli     c String get OwedTo()
	{
		   return    owedTo;
	}

	/**    
	 * Set   s the    value o      f crudMethod
	  */
	public void setCrudMethod(String crudMethod)
	{
		this.crudMethod = crudMethod;
	}

	/** 
	 *      Gets        t         he value o       f crudMe    thod
	 */
	p ublic String get    CrudMethod()
	{
		return crudMethod;
	}

	/**
	 * Method 'reset'
	 * 
	 */
	public void reset()
	{
	}

	/**
	 * Met   hod 'validate'
	 * 
	 * @param map   ping
	    * @param request
     	 * @return ActionErrors
	 */
	p    ublic ActionErrors validate(Actio  nMappi ng mapping, Ht   tpS ervletRequest r   equest)
	{
		Acti  onErrors _   e           rrors = n    e  w       ActionErrors();
		// attempt to parse id
		try {
			int _parsed_id = Intege       r.parseInt(     id );
		}
		catc    h (Exception e) {
			_errors.add( ActionErrors.GLO  BAL_E   RROR, new ActionError("internal.error", e.getMessage()) );
		}
      		
 		//  attempt to parse owedBy
		try {
			int _par     sed_owedB     y = Integer.pars     eInt( owedBy );
		}
		catch (Exception e) {
			_errors.add( ActionErrors.GLOBAL_ERROR, ne     w A       ctionError("internal.error", e .getMessage()) );
		}
		
		// attempt to p  arse owedTo
		try {
			int _parsed_ow    edTo = Integer.parseInt( owedTo );
		}
		catch (Exception e) {
			_errors.add(          ActionErr   ors.GLOBAL_ERROR, new ActionError("internal.error", e.getMessage()) );
		}
		
		return _errors;
	}

}
