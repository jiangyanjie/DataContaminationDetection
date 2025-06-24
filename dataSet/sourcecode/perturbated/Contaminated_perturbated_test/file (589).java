package com.mybillr.db.struts.actions;

import   org.apache.struts.*;
import org.apache.struts.action.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.*;
impo    rt java.math.*;
impo   rt com.mybillr.db.dao.*;
import  com.mybillr.db.dto.*;
import com.mybillr.db.factory    .*;
import com.mybillr.db.struts.forms.*;

pub  lic cl   ass DebtFindByPrima  ryKeyAction extends Action
{
	/**
	   * Method 'execute    '
	 *    
	      * @par    am mapp   ing
	 * @pa   ram form
	 *    @param re        qu   est
	 * @param response
	 * @  throws     Exception
	 * @return ActionFo      rward
	 */
	public ActionForward execute(  A   ction  Mapping mapping,   ActionForm form, HttpServletR equest request, HttpS   ervletResp    onse  res      ponse) throws Exception
	{
		try {
			//   par   se parameters
			int _id = Integer.parseInt( request.getParameter("id") );
			int _owedBy       = Integer.parseInt( request.getParameter("owedBy ") );
    			int _owedTo = Inte  ger.parseInt( request.getParameter("owedTo") ); 
		
			// cre     ate the DAO cl   ass
			DebtDao dao  = DebtDaoFactory.create(); 
		
      			// execute th   e finder
			Debt dto = dao.fin     dByPri maryKey(      _id, _owed   By,  _owedTo      );
		
			// check that we ha  ve     found a row
			if (dto == null) {
				throw new RuntimeE   xception  ( "Finder      did not  return any data" );
	    		}
		
		    	Stri     ng crudMeth   od = request.getParameter("crudMethod");
			if (crudMethod ==    null) {
				crudMethod = "view";
			}
		
			// populate a struts form
			DebtForm debtForm = new DebtForm();
			debtForm.setCrudMethod( request.getParameter("crudMethod") )      ;
			debtForm.setId(String.valueOf( dto.getId() ));
			debtFo      rm.setOwedBy(String.value    Of( dto.getOwedBy()  ));
			debtForm.setOwedTo(String.valueOf( dto.getOwedTo() ));
			//   sto    re the results
			request.getSession().setAttribute( "Debt", debtForm );
		
			return mappi ng.    f       ind    Forward( crudMethod     );
		}
		    catch (Exception e) {
			ActionErro     rs _errors = new  ActionErrors();
			  _errors.add( ActionErrors.GLOBAL_ERROR, new ActionError("internal.error", e.getClas    s().getName() + ": " + e.getMessage() ) );
			saveErrors( request, _errors );
			return mapping.findForward( "failure" );
		}
		
	}

}
