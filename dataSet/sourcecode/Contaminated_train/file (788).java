package web.controller;


import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
/*import org.apache.commons.lang.StringUtils;*/
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pscapp.services.service.ContactsService;
import pscapp.services.types.ContactInfo;
import pscglobalsolutions.api.services.wsdl.PSCAPIService;
import pscglobalsolutions.api.services.wsdl.PSCAPIWS;

@Controller
public class ContactsController {
	
	@Autowired
    private ContactsService contactsService;
	
	@Autowired
    private PSCAPIWS pscApiws;
	
	@Autowired
    private PSCAPIService pscApiService;

	protected final Logger logger = Logger.getLogger(ContactsController.class);
	
	@RequestMapping(value = "/getContacts.form", method = { RequestMethod.POST})
    @ResponseBody
    public String getContacts(HttpServletRequest request, HttpServletResponse response)  {
		System.out.println("InsideGetContacts method ");
		//logger.info("inside controller");
		
		String email = StringUtils.defaultString("");
		String password = StringUtils.defaultString("");
		
		List<ContactInfo> contacts=contactsService.getContacts(email, password);
    	
    	// return form success view
    	return "success";
    }
	
}







