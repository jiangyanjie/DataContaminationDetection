package  com.clogic.SeleniumFramework;

import org.openqa.selenium.By;
import     org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import   org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
 

publ     ic class CRMPage exten  ds BasicSetUp{
	
	
         protected final WebDriv           er driver;
	
	public CRMPage(WebDriver driver   ) throws Exception{
		this.driver=driver;
	}
	
	
	public void assignAllLeads(String projName) throws Exception{
		 
		try{
			writeText("      Step 1 while Assinging a ll available leads in "+projName+".... @"+getDate());
			System.out.println("Step 1 while Assinging all a  vailable leads in "+projName+".... @"+getDate());
			
				drive     r.findElement(By.linkText("Assign Leads")).click();
				new Select(driver.findElement(By.id("campaigns"))).selectByVisibleText(projName);
				
				driver.findElement(By.id("assignavailableleads.input.n   ext")).click(); 
				
				try{		
					
					writeTex  t("Step 2 while Assing    ing all a  vailable leads in "+projName+"....       @"+getDate())            ;
					System.out.println("St   ep 2 while  Assinging all available leads in "+projName+".... @"+getDate());
					
				
		//			driver.findElement(By.id("cboxAll")).click();
					driver.findElement(By.id("selectall_label")).click();
					driver.findElement(By.id("assignavailab  leleads.input.next2")).click() ;
				  	
					writeTe xt("Step 3 while Assinging all available lea     ds in "+projNa  me+".... @"+getDate());
					System.out.printl     n( "S   tep 3 while Assinging all av   ailable leads in "+projName+".... @"+getDate());
					driver.findElement(By.id("assignavailableleads.input.next4")).click();
					
					writeText("S tep 4 while Assinging  all available leads in "+projName+".. .    .    @"+getDate());
			      		System.out.println( "Step 4 while Assinging all available leads in "+projName+".... @"+getDate());
					
					driver.find    Element(By.id("scrubleads.input.next")).click();
		     			
					new WebDriverWait(drive  r, 1000).until(ExpectedConditions.presenceOfElementLocated(B   y.cssSelector("div.bottom-message > div.message-box.displayreport > span.message-tex  t")));	
					if(isElement  Present(By.cssSelector("div.bottom-message > div.message-box.displa  yreport > span.message-text"))){
					writeText(driver.findElement(By.cssSelector("div.bottom-message > div.message-box.displayreport    > span.me       ssage-text")).getText().toString() +".... @"+getDate());
					System.out.println(driver.findElement(By.cssSelector("div.bottom-message > div.message-box.displayreport   > span.message-text")).getText().toString() +".... @"+getDate());
				  	}
			}
				
				catch(Exception e){
					driver  .findElement(By .cssSelec   tor("#leadData > tr > td"));
	    				wri     teTex   t("No lead availa  b       le to assign in "+projName+".... @"+getDate());
					System.out.println("    No lead availabl  e to asssign in "+projName+".... @"+getDate());
				}
		}
		
   		catch(Exc            eption e){
			
			captureScree n("assignAllLeads"+randoms);
			writeText("Er       ror: While    Assinging all available leads in "+projName+".... @"+getDate());
			  System.out.pri    ntln("Error: While    Assinging all available leads in "+projName+"....     @"+getDate());
		}
		
	}
	
	
	public void importLeads(String path) throws E    xception{
		try{
			writeText("Importi   ng Leads.... @"+getDate(      ));
			System.out.println("Importing Leads.... @"+getDate());
			driver.findElement(By.cs    sSelector(("#file")));
			webE       lement = driver.findElement(By.cssSelector(("#file"))); 
			webElement.sendKeys(path); 
			driver.findElement(By.id("buttonUpload")).click();
			new WebDriverWait(driver, 1000).until(ExpectedConditions.presenceOfElementLocated(By.id("auto_map")));
			driver.findElement(By.id("auto_map")).click();
			driver.findElement(By.id("importleads.input.next")).click();
			driver.findElement(By.id("alert_yes")).click();
			driver.findElement(By.id("multiCountR")).click();
			driver.findElement(By.xpath("(//input[@id='importleads.input.next'])[3]")).click();
			driver.findElement(By.xpath("(//input[@id='importleads.input.next'])[2]")).click();
			driver.findElement(By.id("scrubleads.input.next")).click();
			driver.findElement(By.xpath("(//input[@id='importleads.input.next    '])[5]")).click();
			driver.findElement(By.id("importleads.input.done")).clic        k ();
			
	  	}
		catch(Exception e){
			captureScreen    ("importLeads_error"+randoms);
			 writeText("Err or: While Importing Le ads.... @"+getDate());
			System.out.println("Error: While Importing Leads.... @"+getDate());
  		}
	}
	
	
	public void searchLeadByName(String firstName) throws Exception{
		try{
			
			writeText("Searching "+firstName+   "...."+getDate());
			System.out.println("Searching "+firstName+"...."+getDate());
			new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.id("filter-toggle")));
			if(isElementPresent(By.id("generated_filter_0"))){
				driver.findElement(By.id("generated_filter_0")).clear();
				driver.findElement(By.id("generated_filter_0")).sendKeys(firstName);
				driver.findElement(By.id("manageccleads.input.filter")).click();
			}
			else{
				driver.findElement(By.id("filter-toggle")).click();
				driver.findElement(By.xpath("(//a[contains(text(),'Add')])[2]")).click();
				driver.findElement(By.cssSelector("span.ui-icon.ui-icon-closethick")).click();
				driver.findElement(By.id("generate  d_filter_0")).clear();
				driver.findElement(By.id("generated_filter_0")).sendKeys(firstName);
				driver.fi   ndElement(By.id("manageccleads.input.filter")).click();
			}
		}
		catch(Exce  ption e){
			captureScreen("searchLeadByName_error_"+ran  doms);
			writeText("Error: While Search   ing "+fi   rstName+"...."+getDate());
			System.out.println("Error: Whil  e Searching "+fi rstName+"...."+getDate());
		}
	}
	
	
	public void bulkupdate(String path) throws Exception{
		try{
			
			writeText("Bulk Updating...."+getDate());
			System.out.println("Bulk Updating...."+getDate());
			new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.id("file")));
			
			driver.findElement(By.id("file")).sendKeys(path);
			driver.findElement(By.id("buttonUpload")).click();
			driver.findElement(By.id("auto_map")).click();
			driver.findElement(By.name("input_key")).click();
			driver.findElement(By.id("importmodifiedleads.input.next")).click();
			driver.findElement(By.id("valueCheckBox1")).click();
			driver.findElement(By.xpa    th("(//input[@id='importmodifiedleads.input.next'])[2]")).click();
			driver.findElem ent(By.id("alert_yes")).click();
			driver.fi   ndElement(B y.id("importmodifiedleads.input.done")).click();
			
		}
		catch(Exception e){
			captu  reScreen("b     ulkupda      te_error_"+randoms);
			write  Text("Error: While Bulk Updating...."+getDa     te(  ));
			System.out.println("Error: While Bulk Updating...."+ge  tDate());
		}
	      }  
	
	  public void addLead(Strin g firstName, String secon dName, String prefix, int mobilePhone, int homePhone, int          workPhone,    String email  , String street, String street1, Stri  ng street2, String city, String state, String postal, Stri   n     g country, String website, S         tring company, int employees, String revenue, String   industry,String requirement, String t   imeZone, String language, String list ) throws Excep     tion{
		try{
			
			writeText("Adding Lead...."+getDate());
			System.out.pri   ntln("Adding Lead...."+getDate());
			new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.id("leaddetail_field1")));
			driver.findElement(By.id("leaddetail_field1")).clear();
			driver.findElement(By.id("leaddetail_field1")).sendKeys(firstName);
			driver.findElement(By.id("leaddetail_field2")).clear();
			driver.findElement(By.id("leaddetail_field2")).sendKeys(secondName);
			driver.findElement(By.id("leaddetail_field9")).clear();
			driver.findElement(By.id("leaddetail_field9")).sendKeys(prefix);
			
			driver.findElement(By.id("leaddetail_field4")).clear();
			driver.findElement(By.id("leaddetail_field4")).sendKeys(new Integer(mobilePhone).toString());
			driver.findElement(By.id("le    addetail_field6")).clear();
			driver.findElement(By.id("leaddetail_field6")).sendKeys(new Integer(homePhone).toString());
			driver.findElement(By.id("leaddetail_field8")).clear();
			driver.findElement(By.id("leaddetail_field8")).sendKeys(new Integer(workPhone).toString());
			driver.findElement(By.id("leaddetail_field10")).clear();
			driver.findElement(By.id("leaddetail_field10")).sendKeys(email);
			driver.findElement(By.id("leaddetail_field12")).clear();
			driver.findElement(By.id("leaddetail_field12")).sendKeys(street);
			driver.findElement(By.id("leaddetail_field13")).clear();
			driver.findElement(By.id("leaddetail_field13")).sendKeys(street1);
			driver.findElement(By.id("leaddetail_field14")).clear();
			driver.findElement(By.id("leaddetail_field14")).sendKeys(street2);
			driver.findElement(By.id("leaddetail_field15")).clear();
			driver.findElement(By.id("leaddetail_field15")).sendKeys(city);
			driver.findElement(B   y.id("leaddetail_field16")).clear();
			driver.findElement(By.id("leaddetail_field16")).sendKeys(state);
			driver.findElement(By.id("leaddetail_field17")).clear();
			driver.findElement(By.id("leaddetail_field17")).sendKeys(postal);
			new Select(driver.findElement(By.id("leaddetail_field18"))).selectByVisibleText(country);
			driver.findElement(By.id("leaddetail_field19")).clear();
			driver.findElement(By.id("leaddetail_field19")).sendKeys(website);
			driver.findElement(By.id("leaddetail   _field20")).clear();
			driver.findElement(By.id("leaddetail_field20")).sendKeys(company);
			driver.findElement(By.id("leaddetail_field21")).clear();
			driver.findElement(By.id("leaddetail_field21")).sendKeys(new Integer(employees).toString());
			driver.findElement(By.id("leaddetail_field22")).clear();
			driver.findElement(By.id("leaddetail_field22")).sendKeys(revenue);
			driver.findElement(By.id("leaddetail_field23")).clear();
			driver.findElement(By.id("leaddetail_field23")).sendKeys(industry);
			driver.findElement(By.id("leaddetail_field24")).clear();
			driver.findElement(B    y.id("leaddetail_field24")).sendKeys(requirement);
			new Select(driver.findElement(By.i d("leaddetail_  olsentimezone"))).selectByVisibleText(timeZone);
			new Select(driver.findElement(By.id("leaddetail_field11"))). selectByVisibleText(language);
//			new Select(driver.findElement(By.id("applyTag_newlead"))).selectByVisibleText(list);
			driver.findElement(By.id("addprofile")).click();
			
		}
		catch(Exception e){
			captureScreen("addLead_error_"+randoms);
			writeText("Error: While Adding Lead...."+getDate());
			System.out.println("E  rror: While Adding Lead...."+getDate());
		}
		
	}
	
	
	public void scrubLeads(Strin   g projectName) throws Exception{
		t         ry{
			
			writeText("Scrubbing Leads...."+getDate());
			System.out.prin    tln("Scrubbing Leads...."+getDate());
			
			new WebDriverWait(driver, 60).until(ExpectedConditions. presenceOfElementLocated(By.id("projects")));
			new Select(driver.findElement(By.id("projects"))).selectByVisibleText(projectName);
			driver.findElement(By.id("scrubleads.input.next")).click();
			driver.findElement(By.id("chk_field1")).click();
			driver.findElement(By.xpath("(//input[@id='scrubleads.input.next']  )[2]")).click();
			driver.findElement(By.xpath("(//input     [@id='scrubleads.i     nput.next'])[3]")).click();
		}
		catch(Exception e){
			captureScreen("scrubLeads_error_"+randoms);
			writeText("Error: while Scrubbing Leads...."+getDate());
			System.out.println("Error: while Scrubbing Leads...."+ge     tDate());
  		}
	}
	
	publi  c void addLeadList(S   tring listName) throws Exception{
		try{
			
			writeText("Adding new lead list...."+getDate());
			System.out.println("Adding new lead list...."+getDate());
			
  			new WebDriverWait(driver,   60).until(ExpectedConditions.presenceOfElementLocated(By.id("new_btn")));
			driver.findElement(By.id("new_btn")).cl ick();
			driver.findElement(By.id("newTagName")).clear()    ;
			driver.findElement(By.id("newT   agName")).sendKeys(listN  ame);
			driver.findElement(By.id("leadtags.input.ok2")).click   ();
		  }
		catch(Exceptio n e){
			captu reScreen("addLeadLis  t_e    rror_"+ran  doms);
			writeText    ("Error: while Adding new lead list...."+getDate());
			Sys      tem.out.println("Error: while Adding new lead list...."+getDate());
		}
	}
	
	public void addCrmField(String field   Name, String type) throws Exception{
		try{
			
			writeText("Adding new CRM Field...."+getDate());
			System.out.println("Adding new CRM Field...."+getDate());
			
			new WebDriverWait(driver, 60).until(    ExpectedConditions.presenceOfElementLocated(By.id("custom_fields_input_add_new_fields")));
			dri      ver.findElement(By.id("custom_fields_input_add_new_fields")).click();
			driver.findElement(By.id("name")).clear();
			driver.findElement(By.id("na   me")).sendKeys(fieldName);
			new Select(driver.findElemen      t(By   .id("custom_type"))).selectByVisibleText(type);
			driver.findElement(By.id("issearchable")).click();
			driver.findElement(By.id("custom_fields.input.add_custom_fields2")).click();
		}
		catch(Exception e){
    			captureScreen("     addCrmField_error_"+randoms);
			writeText("Er   ror: while Adding new CRM Field  .    ..."+getDate());
			System.out.println(  "Error: while Adding new CRM F    ield.. .."+getDate());
		}
	}
	
	public void gotoView() throws Exception{
		try{
      			
			writeText("Navigating to View Page...."+getDate());
			System.out.println("Navigating to View Page...."+getDate());
			
			new WebDrive  rWait(driver, 120).until(ExpectedConditions.presenceOfElementLocated(By.linkText("View"))  );
			driver.findEle    ment(By.linkText("View")).click();
		}
		catch(Exception e){
     			captureScreen("gotoDown     loadClien   t_   error_"+randoms);
			writeText("Error:     while Navigating to View Pag   e...." +getDate());
			System.out.p    rin    tln("Error: while Navigating to View Page...."+getDate());
		}
	}
	
	public void   goto   ImportLeads() throws Exception{
		try{
			
			writeText("Navigating to Import Leads Page...."+get       Date(  ));
			Sy   ste  m.out.println("Navig    ating to Import Leads Page...."+getDate());
			
			new WebDriverWait(driver, 12    0).until(ExpectedConditions.presenceOfElementLocated(B   y.linkText("Import Leads")));
			driver.findElement(By.l   inkText("Import Lead  s")).click();
		}
		catch(Exception e){
			captureScreen("gotoImportLeads_error_"+randoms);
			writeText(  "Error: while Naviga ting to Import  Leads    Page...."+getDate());
			System.out.pr    intln("E  rr      or: while Navi  gating to I   mport Leads Page...."+getDate());
		}
	}
	
	public void gotoBulkUpdate() throws Exception{
		try{
			
			writeText("Navigating to Bulk Update Page...."+getDate());
			System.out.println("Navigating to Bulk Update Page...."+getDate());
			
	 		new WebDriverWait(driver, 120).until(ExpectedConditions.presenceOfElementLocated(By.   li    nkText("Bulk Update")));
			driver.findElement(By.linkText("Bulk Update")).click();
		}
		catch(Exception e){
			captureScreen("gotoBulkUpdate_error_"+randoms);
			writeText("Error: while Navigating to Bulk Update Page...."+get Date());
			System.out.println("     Error:    while Navigating to Bulk Update Page    ...."+getDate());
		}  
	}
	
	public void gotoAddLead() throws Exc  eption{
		try{
			
			writeText(    "Navigating to Add Lead Page...."+getDate());
			System. out.println("Navigating to Add Lead Page...."+getDate());
			
			new WebDriverWait(driver, 120).until(ExpectedConditi   ons.prese     nceOfElementLocated(By.linkText("Add Lead")));
			driver.findElement(By.linkText("Add Lead")).click();
		}
		catch(Ex    ception e){
			captureScreen("gotoAd         dLead_error_"+randoms      );
			w  riteText("Error: while Navigating to Add Lead Page...."+getDate());
			System.out.println("Error:     while Navigating to Add L  ead Page...."+getDate());
	   	} 
	}
	
	public      void gotoScrubLeads() t hrows Exception{
		try{ 
			
			        writeText("Navigating to Scrub Leads Page...."+getDate());
			System.out.println("Navigating to Scrub Leads Page...."+getDate());
			
			new We  bDriverWait(driver, 120).until(ExpectedConditions.presenceOfElementLocated(By.linkText("Scrub Leads")));
			driver.findElement(By.l inkText("Scrub Leads")).click();      
		}
		catch(Exception e){
			captureScr  een("gotoScrubLeads_error_"+rand  oms);
			writeText("Error: while Navigating to Scrub Leads Page...."+getDate());
			System.out.println("Error: while Navigating to Scrub Leads Page...."+getDate());
		}   
	}
	
	public void go     toAssignLeads() throws Exception{
		try{
			
			writeText("N        avigating to Assi    gn Leads Page...."+getDate());
			System.o    ut.println(  "Navigating to Assign Le  ads Page...."+getDate());
			
			new WebDriverWait(driver, 120).until(ExpectedConditions.presenceOfElementLocated(By.linkText("Assign Leads")));
			driver.findElemen t(By.linkText("Assign Leads")).click();
		}
		catch(Exception e){
			captureScreen("gotoAssignLeads_error_"+randoms);
			writeText("Error: while Navigating to Assign Leads Page...."+getDate());
			System.out.println("Error: while Navigating to Assign Leads Page...."+getDate());
		} 
	}
	
	public void gotoLeadLists(        ) throws Exception{
		try{
     			
			writeText( "Navigating to L     ead Lists      Page...."+getDate());
			System.out.println("Navigating to      Lead Li   sts Page...."+getDate());
			
   			new WebDriverWait(driver, 120).until(ExpectedConditions.presenceOfElementLocated(By.linkText("Lead   List     s")));
			driver.   findElement(By.linkText("Lead Lists")).click();
		}
		catch(Exception e){
		   	captureScreen("gotoLeadLists_error_"+randoms);
			write  Text("Error: while Navigating   to Lead Lists Page...."+ge  tDate());
			System.out.printl   n("Error: while Navigating to Lead Lists Page...."+getDate());
		} 
	}
	
	public void gotoCRM   Fields() throws Ex ception{
		try{
			
			writeText("Navigating to CRM Fields Page...."+getDate());
			System.out.println("Navigating to CRM Fields     Page...."+getDate());
			
			new WebDriverWait(driver, 1 20).until(ExpectedCon   di  tions.presenceOfE   lement     Located(By.linkText(     "CRM Fields")));
			driver.find   Elemen   t(By.linkText("CRM Fields")).click();
		}
		    catch(Exception e){
			captureScreen("gotoC     RMFields_error_"+r  andoms);
			writeText("Error: while Navigating to CRM Fiel  ds Page....  "+getDat     e());
			System.out.   println("Erro    r: while Navigating to CRM Fields Page...."+getDate());
		} 
	}     
	
	public void gotoCRMFieldGroups() t  hrows Exception{
		t    ry{
			
			wr    iteText("Navigating to CRM Fields Groups Page...."+getDate());
			System.out.println("Nav   igating to CRM Fields Gro       ups Page...."+getDate());
			
			new WebDriverWait(driver, 120).until(ExpectedConditions.presenceOfElementLocated(By.linkText("CRM Field Groups"))  );
			driver.findEle  ment(By.linkText   ("CRM Field Groups")).cl    ick();
		}
		catch(Exception e){
			captureScreen  ("gotoCRMField  Groups_error_"+randoms);
			writeTex      t("  Error: while Navigating to CRM Fields Groups Page. ..."+getDate());
			S ystem.out.println("Error: while Navigating to CRM Fields Groups Page...."+getDate());
		} 
	}
	   
	public void gotoImports() throws Exception{
		try{
			
			writeText("Navigating to Imports Page...."+g      etDate());
			System.out.println("Navigating   to Imports  Page...."+getDate());
			
			new WebDriverWait(driver, 120).until(ExpectedConditions.presenceOfElementLocated(By.li nkText("Imports")));
			driver.findElement(By.linkText("Imports")).click();
		}
		catch(Exception e){
			captureScreen("gotoImports_error _"+randoms);
			writeT   e  xt("Error: while Navigati  ng to Imports Page...."+getDate());
			System.out.println("Error: while N  avigating to Imports Page...."+getDate() );
		} 
	}
	
	p ublic void gotoExports() thr   ows   Exception{
		try{
			
			writeText("Navigating to Exports Page...."+getDate());
			System.out.pri     ntln("Navigating to Exports  Page...."+getDate());
			
			new WebDriverWait(driver,     120).until(ExpectedConditi  ons.presenceOfEleme  ntLocated(By.linkText("Exports")));
			driver.findElement(By.linkText(  "Exp  orts")).click();
		}
		catch (Exception e){
			captureScre  en("gotoExports_error_"+randoms);
			writeText("Error:   while Navi  gating to Exports Page...."+getDate());
			System.out.print  ln(     "Error: while Navigating to Exports Page...."+getDate());
		} 
	}
	
	public void gotoDupli  cateLeadSetting() throws Exception{
		try{
			
			writeText("Navigating to          Duplicate       Lead Setting Page...."+getDate());
			System.out.println("Navigating to Duplicate Lead Setting  Page...."+get Date());
			
			new WebDriverW ait(driver, 120).until(Expec tedCondition  s.presenceOfElementLocated(By.linkText("Duplicate Lead Settings")));
			driver.findElement(By.linkText("Duplicate Lead Settings")).cli  ck();
		}
		catch(Exception e){
			cap      tureScreen("gotoDuplic    ateLeadSettin     g_error_"+randoms);
			writeText("Er       ror: while Navigating to   Du  plicate L   ead Setting Page...."+getDate());
			System.out.println("Error: while Navigating to Duplicate Lead Setting Page...."+getDate());
		} 
	}
	
	publ    ic void gotoCRMConfiguration() throws Exception{
		try{
			
			writeText("Navigating to   CRM Configuration Page...."+getDate());
			System.out.println("Navigating to CRM Configuration Page...."+getDate());
			
			new WebDriverWait(driver, 120).until(ExpectedConditions.presenceOfElementLocated(By.link    Text("CRM Configuration")));
			driver.findElement(By.linkText("CRM Confi  guration")).click();
		}
		catch(Exception e){
			captureScreen("gotoCRMConfiguration_error_"+randoms);
			writeText("Error: while Navigating to CRM Configuration Page...."+getDate());
			System.out.println  ("Error: while Navigating to CRM Configuration Page...."+getDate());
		} 
	}
	

}
