package com.mybillr.db.struts.forms;

import java.math.*;
import java.text.*;
import org.apache.struts.action.*;
import javax.servlet.http.HttpServletRequest;
import com.mybillr.db.dto.*;

public class CurrenciesForm extends ActionForm
{
	protected String id;

	protected String name;

	protected String symbol;

	protected String rate;

	protected String crudMethod;

	/** 
	 * Sets the value of id
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/** 
	 * Gets the value of id
	 */
	public String getId()
	{
		return id;
	}

	/** 
	 * Sets the value of name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/** 
	 * Gets the value of name
	 */
	public String getName()
	{
		return name;
	}

	/** 
	 * Sets the value of symbol
	 */
	public void setSymbol(String symbol)
	{
		this.symbol = symbol;
	}

	/** 
	 * Gets the value of symbol
	 */
	public String getSymbol()
	{
		return symbol;
	}

	/** 
	 * Sets the value of rate
	 */
	public void setRate(String rate)
	{
		this.rate = rate;
	}

	/** 
	 * Gets the value of rate
	 */
	public String getRate()
	{
		return rate;
	}

	/** 
	 * Sets the value of crudMethod
	 */
	public void setCrudMethod(String crudMethod)
	{
		this.crudMethod = crudMethod;
	}

	/** 
	 * Gets the value of crudMethod
	 */
	public String getCrudMethod()
	{
		return crudMethod;
	}

	/**
	 * Method 'reset'
	 * 
	 */
	public void reset()
	{
		name = "";
		symbol = "";
	}

	/**
	 * Method 'validate'
	 * 
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		ActionErrors _errors = new ActionErrors();
		// attempt to parse id
		try {
			int _parsed_id = Integer.parseInt( id );
		}
		catch (Exception e) {
			_errors.add( ActionErrors.GLOBAL_ERROR, new ActionError("internal.error", e.getMessage()) );
		}
		
		// attempt to parse rate
		try {
			long _parsed_rate = Long.parseLong( rate );
		}
		catch (Exception e) {
			_errors.add( ActionErrors.GLOBAL_ERROR, new ActionError("internal.error", e.getMessage()) );
		}
		
		return _errors;
	}

}
