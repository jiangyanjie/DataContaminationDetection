package com.mybillr.db.struts.actions;

import org.apache.struts.*;
import org.apache.struts.action.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.*;
import java.math.*;
import com.mybillr.db.dao.*;
import com.mybillr.db.dto.*;
import com.mybillr.db.factory.*;
import com.mybillr.db.struts.forms.*;

public class AccountActivationSaveAction extends Action
{
	/**
	 * Method 'execute'
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try {
			// cast the form to the appropriate type
			AccountActivationForm accountActivationForm = (AccountActivationForm) form;
		
			// create the DAO class
			AccountActivationDao dao = AccountActivationDaoFactory.create();
		
			AccountActivation dto = new AccountActivation();
			dto.setId( Integer.parseInt( accountActivationForm.getId() ));
			dto.setUserId( Integer.parseInt( accountActivationForm.getUserId() ));
			dto.setActivationHash( accountActivationForm.getActivationHash());
		
			if (accountActivationForm.getCrudMethod().equalsIgnoreCase("insert")) {
				dao.insert( dto );
			} else if (accountActivationForm.getCrudMethod().equalsIgnoreCase("update")) {
				dao.update( dto.createPk(), dto );
			} else if (accountActivationForm.getCrudMethod().equalsIgnoreCase("delete")) {
				dao.delete( dto.createPk() );
			}
		
			return mapping.findForward( "success" );
		}
		catch (Exception e) {
			ActionErrors _errors = new ActionErrors();
			_errors.add( ActionErrors.GLOBAL_ERROR, new ActionError("internal.error", e.getClass().getName() + ": " + e.getMessage() ) );
			saveErrors( request, _errors );
			return mapping.findForward( "failure" );
		}
		
	}

}
