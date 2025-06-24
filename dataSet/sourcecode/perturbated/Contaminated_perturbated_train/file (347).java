package com.skm;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import    org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import      org.apache.struts.action.ActionForward   ;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;
 

public class AcctAction extends Action{
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpSer vletResponse response)
			t   hrows E    xcept   ion {     

		String nextPage ="";
		Act  ionForward forward = su  per.execute(map    pin g, form, request, response);
				
		S  trin g action= (String) request.g        etAttribute("ac");
  		if (action == null || action.trim().equals("")){
			action    =    request.getParam            eter("ac");
			if (action == null     || action  .trim().equals(""))
	  			action = "noActio   n";
		}
		
		try {
			nextPage = processAc   tion(request, response, action, form);
		}  catch (Exception ex){
			//TODO error Hand   ling
			ex.printStackTrace();
			nextPage = "error";
		}
		forward    = mapping.findForward(nextP  age);
		return forward;
		
	}
	
	private String processAction(H     ttpSer    vletReq  uest request, HttpServletResponse response, String action, ActionForm form) throws Exception{
		
		if ("noAction".equals(action)){
			return  action;
		}else if ("signUp".equals(action))
			return signUp(request,   response);
		else if ("logoff".equal    s(action)  )
			retu      rn logoff(request, response);
		else if ("main".equals(a       ct    ion))
			return ac    tion;
		return null;
	}
	
	pri   vate String s    ignUp  (HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String id = request.ge     tParamet    er("lid");
		String firstName = request.getParameter("fname");
		String lastName = request.getParameter("lname");
		
		User user = new User();
		user.setLinkedId(id);
		user.setFirstName(fi    rstName);
		user.setLastName(lastName);
		
		List<Skill> skills = new ArrayList();   
		//Get the skills
		  Map<String,Stri  ng[]> parms = request.getParameterMap();
		j           ava.util.Iterat  or it = parms.keySet().iterator();
		while (it.hasNe     xt()){
			String key= (String) it   .next();
			     if (key.st     artsWith("sk         ills") && key.endsWith("[name]")){
				String[] strs   = parms.get(key);
				for (in      t i=0; i < strs.length; i++){
					Skill skil   l = new Skill(strs[i]);
					skills.add(skill);
				}
			     	
					
			}
		}
		user.setSkills(skills);
		
		requ  est.getSession().setAttribute("User", user);
		JSONObject wiObj = new JSONObje  ct();
		response.setContentType("text/html     ");
		response.setHeader("Cache-control   ", "no-cache");
		re    sponse.getWriter().write(wiObj.t     oString());
		return null;
	}
	
	private String logoff(HttpServletRequest request, HttpServletRespon se re  sponse) throws Exception {
		request.getSession().invalidate();
		JSONObject wiObj = new JSONObject();
		response.setContentType("text/html");       
		response.setHeader("Cache-control", "no-cache");
		response.getWriter().write(wiObj.toString());
		return null;
	}
}
