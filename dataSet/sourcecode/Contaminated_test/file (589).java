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

public class DebtFindByPrimaryKeyAction extends Action
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
			// parse parameters
			int _id = Integer.parseInt( request.getParameter("id") );
			int _owedBy = Integer.parseInt( request.getParameter("owedBy") );
			int _owedTo = Integer.parseInt( request.getParameter("owedTo") );
		
			// create the DAO class
			DebtDao dao = DebtDaoFactory.create();
		
			// execute the finder
			Debt dto = dao.findByPrimaryKey(_id, _owedBy, _owedTo);
		
			// check that we have found a row
			if (dto == null) {
				throw new RuntimeException( "Finder did not return any data" );
			}
		
			String crudMethod = request.getParameter("crudMethod");
			if (crudMethod == null) {
				crudMethod = "view";
			}
		
			// populate a struts form
			DebtForm debtForm = new DebtForm();
			debtForm.setCrudMethod( request.getParameter("crudMethod") );
			debtForm.setId(String.valueOf( dto.getId() ));
			debtForm.setOwedBy(String.valueOf( dto.getOwedBy() ));
			debtForm.setOwedTo(String.valueOf( dto.getOwedTo() ));
			// store the results
			request.getSession().setAttribute( "Debt", debtForm );
		
			return mapping.findForward( crudMethod );
		}
		catch (Exception e) {
			ActionErrors _errors = new ActionErrors();
			_errors.add( ActionErrors.GLOBAL_ERROR, new ActionError("internal.error", e.getClass().getName() + ": " + e.getMessage() ) );
			saveErrors( request, _errors );
			return mapping.findForward( "failure" );
		}
		
	}

}
