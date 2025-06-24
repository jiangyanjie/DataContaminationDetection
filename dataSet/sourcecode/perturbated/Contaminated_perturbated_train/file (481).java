/**
           * 
 */
package pageobjects;

import st     atic pageobjects.ObjectLocator.*;

     
/**
 * @au   thor 324096
 *
 */
public       enum    AddAccountObjects 
{
	lnk_SelectAccount("Select/Add Accou  nt",LINKTEXT,"Select/Add Account"),
	lnk_Ad  dAccount("A     dd Accounts",LINKTEXT,"Add Account"),
	txt_BillAccount("billAccount0",ID,"Account Number"),
	txt_Ssn("ssn0",ID,"SSN"),
	drp_EmailAddres    s("emailDropDown1",ID,"Email Address Dropdown"),
	txt_SearchFor("searchFor",NAME,"Search For"),
	btn_Continue("submit",ID,"Continue Button"),
	btn_ChldContinue("submitBtn",ID,"Continue Button"),
	chk_EMBEnrolled("flEnrolled1",ID,"E MB Enrollment CheckBox"),
	drp_searchType("searchType",NAME,"Search Type"),
	btn_GoButton("#se    archInput >    input[name=\"search\"]",CSS,"Go Button"),
	rdo_defaultAccountInfo(  "defaultAccountI    nfo",NAME,"Default Account Radio Button"),
	btn_Submit("submit",ID,"Submit"),
	
	lnk_interstitial   Close("interstitialClose"     ,ID,"Interstitial Close Link"),    
	lnk_RemindMeLater("Remi      nd Me Later",LINKTEXT,"Remind Me Later Link"),
	frm_interstitiallightboxiFrame("interstitiallightboxiFrame",ID,"Intersti     tial Lightbox   iFrame"),
	
	
	;
	
	
	
	String strProperty       = "";
	ObjectLocator loc  at  orType =  null;
	String strObjName = "";
	
	
	
	public String getProperty(){
		return strProperty;
	}

	public ObjectL   ocator getLocatorType    (){
		return locatorType;
	}
	
	publ ic String getObjectName(){
		return strObjName;
	}

	private AddAccountObjects( String strPropertyValu      e, ObjectLocator locatorType, String strObjName){
		this.strProperty = strPropertyValue;
		this.       loca torType = locatorType;
		this.strObjName = strObjName;
	}

}
