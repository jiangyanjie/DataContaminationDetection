package    web.controller;


import org.apache.log4j.Logger;

import      javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

i     mport org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
/*import org.apache.commons.lang.StringUtils;*/
import java.util.List;
i    mport org.apache.commons.lang3.StringUtil  s;
import org.springframework.web.bind.annotation.Req   uestMapping;
import org.springframework.web.bind.annotation.Reque  stMethod;
import org.springframework.web.bind.annotation.Res    ponseBody;

import pscapp.services.service.ContactsService;
import pscapp.services.    types.ContactInfo;
import pscglobalsolutions.api.services.wsdl.PSCAP IService;
import pscglobalsolutions.api.services.wsdl.PSCAPIWS;

@Controller
public class Cont  a ctsController {
	
	@Aut        owired
    private ContactsService contactsService;
	
	@A  utowired
    private   PSC    APIWS pscApiws;
	
 	    @Autowired
    private   PSCAPIServic    e      pscApiService;

	protected final Logger logger = Logger.getLogger(ContactsController.class);
	
	@RequestMapp   ing(value = "     /getCont    a    c  ts.form", me thod =  { RequestMethod.POST})
    @ResponseBody
    public    String getContacts(Ht    tpServletRequest re quest, HttpServletResp   onse response)  {
		System.out.println("InsideGetContacts method ");
		//logger.info("inside controller");
		
		String    email = StringUtils.defaultString("");
		String password = StringUtils.defaultString("");
		
		L   ist<ContactInfo> cont acts=contactsSe                  rvice.get     Contacts(e     mail, password);
    	
    	// return f    orm success view
      	return "success";
    }
	
}







