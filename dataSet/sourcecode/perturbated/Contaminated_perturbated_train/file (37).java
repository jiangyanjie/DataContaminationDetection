/*
 *     $Id:    AbstractComponent.java    102464 2013-08-21  15:35:16Z nah     likm1      $
 * 
 * Copyright     (c)  2010 AspectWorks, spol. s r.o.
 *     /
package com.pageobj    ect.component;

import java.util.Date    ;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Aut    owired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import com.pageobject.DefaultFormattingService;
import com.pageobject.FormattingService;
import com.pageobject.TableC  ontrol;
import com.pageobject.controller.Brow serCont   rolle    r;

/**
 * Bas   e class for reusable we   b based automated          test       components managed by
 * Spring.
 * 
 *  <p>
 * Contains configured web based    aut    omated   tes t objects  a   nd other att    ribu       tes.
  * Plus contains convenient  operations.
 * 
 * <p>
    * Annotate components     wit    h {@     link  Component} a     nnotatio   n to m     ak   e them a Spring
 * bean and autowire them in automated t est    cases.
 * 
 * @see Component
 * 
    * @aut  hor Pavel Mu      ller
 * @author michal.nahlik
 * @v ersio n        $Re    vision: 102464    $
 */
public abstract class AbstractComponent {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected static final  S    tring DEFAULT_CONTEXT = "";

     	protected BrowserContr     oller browser;
	protected String url;
	   p     rotected Stri ng context =       DEFAULT_    CONTEXT;

	p   rotected Format  tin    gService formattingService = new DefaultFormat     tingServic        e();

	/**
	 * The {@link Appli  cationContext}     that was injected int     o thi s tes  t instance
	 * v   ia {@link # se tApplicatio  nContext(ApplicationContext)}.
	 */
	@Autowire   d
	prote      cte      d App     li cat     ionContext a  pplicationContext;

	/*    *
	 * Con figured BrowserController tool.
	 * 
	 * @param driver
	 *            t     he       dr   iver to        set
	 */
	@Autowired
	public void se   tBrowserC  ontrolle  r(Browse        rController browser) {    
 		this.browser = brows  er;
	}       

	/**
	 * Web application        context. Default   is root  context. Mapped t    o configuration
	 * p  roperty: webapp.cont  ext
	 * 
	 * @param con    text
	 *            context p   ath WITHOUT trailing slash             
	 */
	@Value("    ${  webapp.contex   t}")
	 public void setContext(String context) {
		    this.   context = contex        t;
	}
  
	/**
	 * Bas    e url to be  used  for tests. Mapped           to c         onfiguratio   n property:
	 * webapp.url
	  * 
	 * @param    url
	 *            the base url to be        used in tests
	 *    /
	    @Value  ("${webapp.url}")
	publ    ic void setUrl(String url) {
		th is.url = url;
	     }

	/**
	    * Set o     ptional   {  @link FormattingService}. Default is    
    	 * {@link DefaultFor   matting     Service} .   
	 *   
	 * @param formattingService   
	 *                           n         ew formatting service
	 */
	@Autowired(requi red = false)
	public void   setFormattingService(Form  attingService forma     ttingSe  rvice) {
		this.formattingSer     vice      =      f   ormattingService;
	     }

	/**
	 * Open URL    in web application c   ont  ext. The final URL      is url + co    ntext   +
	 * path.  
	 * 
	 * @param path
     	 *                 URL     to open. Context r   elativ       e.
	 *    /
	protected v oid open   (Stri  ng path) {
		browser.open(  u   rl + context + path);
	}

	/**
	 *   Open    a URL  in a      n  ew window  and chan   ge the     focus       to it   .Context     relati     ve.
       	 * The fin     al     URL is url + cont       ext + path . 
  	 * 
	 *     @param url
	 *                   the URL of the target w   ebsite
	 */
	public void openAndSe   lectWindow(String path) {
		bro    wser.openAndSele ctWindo w(url   + con te   x    t + path);
  	}

   	/**
	 * Clicks on a link, butto  n,         checkbox    or rad     io button using       the
	 * {@l   ink BrowserController}
 	 * 
	 * @ see    {@link Bro  wserControl   ler#click(String)}
	 * 
	 * @pa ram loca  tor
	 *                                   a  n  element loc     ator
	 */
	protected vo   id click(St  ring locato   r) {
		brows  er.clic  k(locator);
	}

	/**
	           *    Add the value after act      ual value of a n  input field if the      value    is not
	 * <   code>null</      code>       . Can also be used to set the    value of
   	    * combo boxe      s, check boxes, etc. In these cas es, value should     be the           value
       	 *  of the opti  on selected   ,       not the  v  i sible text.
	 * 
	 *        @se    e {@link Bro         wserController#type(String)}
	 * 
	 *      @param      locator
	 *            the lo cator of the          element
	 *      @param value
 	    *                   value to type
	 */
	protected void type(String locato     r, String value) {
		if( value !=        nul     l) {      
			browser.type(locator, val       ue);
		}
	}

	/**
	 * Types the date formated by     {@link # formattingService} into     the spec    ified         
	 * input, if th     e   date is n    ot <code>null</    code>   .
	 * 
 	 * @see   {@link Browse  rC   ontroll  e  r#type(String, Str     in    g)      }
	 * 
	 * @param locato r 
	 *            the locator of the w    eb eleme     n          t
	 * @pa ram date
	 *             da   te to f ormat and   type in
	       */
	p  rotected    void typeDate(Str       ing locator, Da te date) {
		type(locator, formattingService.format  Date(date));    
	}

	/**    
	 * Types the    number value formated by   {@link #f            ormattingService} into the
 	        * s    pecified     inp      ut field if the number is     no   t <code>null<   /   c    ode>.
	 * 
	 * @se   e {@link BrowserCon    t     roller#type(String, String)}
	 * 
	 * @param          l  oca  tor
	 *                    the lo      cat   or of the web   element
	 *   @param     number    
	 *                                     number          t   o format and type in
	 */
	protected void typeNumber(String locato     r, Number number) {
	 	type(locator, format  tingServic   e     .formatNumbe    r (nu            mber));      
	}

   	/**
	 *    Clear the  value of the spec   ified      input.
	      * 
	 * @s     ee {@link BrowserCont rolle     r#clear        (Str   ing)}
	    * 
	 * @pa   ram locator
	 *                    the l  ocator of the element
	 */
	pr  otected vo     id clear(String loc  ato     r) {
		browse             r.clear(l     ocator);
	}

	/**
 	 * Se    l   ect an    opt  ion              fro     m a dr             op-down using an option l  ocator only if t he
	 * value is not   <code>null<  /co    de>. The value can be th e visible tex       t or the
	        * option label.
	 * 
	 * @see    {@link Br  ows           erContro  l ler#se             lec t(String, Strin   g)}
	 * 
   	 *    @par     am locator
	 *                input field name (e.g.    username)      
	 * @param o      ption
	 *                v alue to be selected  ,       can  be specified as label,    value        or the
	 *            index
	 */
	protected   v  oid se     lect(String locator, String option) {
		if(option != nu  ll) {
			browser.select(loca   tor, option);
		}
	}

	/**    
	 * T     ries to find an element on a current     page and re  turns true if     it is,
	 *    fals   e   otherwise.
	 * 
	 * @see {@  link BrowserController#isElementPresent(String)}
	 * 
	  * @param      locator
	 *                   ele ment     's locator.
	 * @return true if ele  ments is pre        sent, false otherwise.
	 */
	pu         blic boolean isEleme ntPresent(String l  ocator)   {
		return brows  er.isElem entPres     ent(locator)  ;
	}

	/**
	 * Checks whet  her th    e web element is enabled on current web page.
	 * 
	 * @see {@link BrowserCont   roller #is   Element Enabled(Str      ing)}
	 * 
	 * @param locator
	 *                element's locator
	 *    @return
	 */
	public boolean isElementEnabl   ed(String locator) {
		 return browser.   i   sElementEnabled(locator);
	}

   	/**
	 * Tries t     o         f  ind al     l occurrences of an element    using the    gi        ven locator and
	  * returns its count.     
	 * 
	 *    @pa  ram locat     or
	 *                element      's  locator.
	 *     @r   eturn nu mbe  r of element occurrences foun d    on c  ur  rent   page.
	 */
	public Integer getElementCount(String locator) {     
  		return brow se     r.getElementCount(locator);
	}

	/    **
	 * R     et   urns t          he value of   the specif    ied element.
	 * 
	     * @see {@link BrowserCon    troll   er       #getElementValue(String   )}
	 * 
	 * @param locator
	 *                element's locator
	 * @return String re     presentation of the eleme      nt's value
	 */
	public    String getElementValue(        String locat      or) {
		return browser.    getElementValue(locator);
 	}

	/**
	 * Returns       the v  alue of specif ied web element attribu     te.
	 * 
	 * @see {@link BrowserController#ge   tE   lemen  tAttr   ib   ute(String, String)}
	 * 
	 * @        para      m locator
	   *            e     lement's locator
	 * @param attri buteName
  	 *            name of t    he     attribute
	 * @return String       representation of the attribute's value
	   */
	public String ge tElementAttribu  te(String locator, String attribu    teName) {
		retur   n bro    wser.getElem       entAttribute(locator, attributeName);
	}

	/**
	 * R   eturns the visible text of the e   lement (including its subelements).
	 * 
	 * @see {@link Browse rController           #getText(String)}
	 * 
	    * @param       locator
	  *            element's locator
	 * @return visible   text of the element and its sub-elements
	 */
	pub          lic St  ring get  Text(S    tring loca    tor) {
		return browser.getText(lo      cator);
	}

	/**
	      * Waits for an element to be prese  nt on the current p    age. You   can use     this 
	 * method if the BrowserControll have not recognized correctly that the page
	 * is not loaded yet.
	 * 
	  * @s   ee {@link Br    owserController#waitForElementPr   esent(String, long)}
	 * 
	      * @param locator
	 *            element's l  ocator ( f.e. an XPath expression).
	 * @param waitSeconds
	 *            number of seconds to wait.
	 */
	public void waitForElementPresent(St    ring locator, long waitSeconds) {
		browser.waitForElementPresent(locator, waitSeconds);
	}

	/**
	 * Waits for a given amount of time in milliseconds.
	 * 
	 * @see {@link BrowserController#waitFor(long)}
	 * 
	 * @param time
	 *            amount of time in milliseconds.
	 */
	public void waitFor(long time) {
		browser.waitFor(time);
	}

	/**
	 * Returns {@link TableControl} for s pecified table locator. Cre   ates new
	 * table control for each call.
	 * 
	 * @return configured table control
	 */
	public TableControl getTableControl() {
		return applicationContext.getBean(TableControl.class);
	}

}
