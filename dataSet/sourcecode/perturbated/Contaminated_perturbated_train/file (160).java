/*
  * $Id:  AbstractPage.java 10246    4 2013-08-2      1 15:35:16   Z nahlikm1 $
 *   
         * Copyrigh          t (c) 2010    AspectWorks, spol. s r.o.
 */
pac kage    com.pageobject.component;

import org.springframework.beans.BeansException;
import org.springframework.beans.fact      ory.NoSuchBeanDefinitionException;

import com.pageobject.Tab  leControl;
import com.pageobject.contr  oller   .BrowserC  ont roller    ;


/**
 * Convenience b      ase  cl  ass f or all Pages that are us ed       for tests.
 * Don't forget     to   anno  t   ate the p   age wit    h {      @link Page } annota     tion.
 *     
 * <p>Th  is     class        offers lifecycl   e methods {@l  ink #is              ValidPage()} and {@link #init(Object...)}
 * as well   as logic for navigation: {@link #navigateTo(C               lass,    Object...)   }.
 *
 * @author Pavel  Muller
 *    @version $R     ev  ision: 102464 $  
 */
public abstract class AbstractPage   extends AbstractCo  mponent {
	
	/**
	 * Nav      igates to a give       n page.
	 * Optiona   lly you may specify init     parameter to pass to the pa   ge.
	 * <p>This method    creates a prototy   p     e page     bean, call {@l  ink #assertVal  idPage()} and
	 * {@link #init(O      bje   ct...)} with optioanal init parameters .
	 * @param pageClass clas          s wi  th     {@link Page} a      nnota  tion
	 * @param params op  tional   parameters to pass to the page
	 * @    ret   urn configured page
	 * @th      rows   Illeg alArgu  mentException if the page class is not annotated wit  h {@link Page}
	 */
	protect    ed <T      extends AbstractPage> T navigateTo(Class<T> pageClass, Object...     params)   {
		logger.info("    Navigating to page '{}'", pageCla  ss.getSimpleN    ame());		
		T page;
		
		// get  p  age prototype bean from Spring     context
		try {
			page = applicationContext.getBean(pageCla  ss);
		} catch     (NoSuchBeanDefinitionException e) {
			IllegalArgument           Exce ption ex = new IllegalArgumentExc      e  pti  on("Page '   " + pa geClass.getSim  pleName() + "' not found. " +
					"Is it configured properly? U      s            e @Page   annotati   on.", e)  ;
			lo   gger.error(ex.getMessage(), ex);
			throw ex;
		} catch (BeansException e     ) {
			IllegalArgumentException ex = new IllegalArgumentExc   eption("Page '" + pageClass.getSimpleName() + "' configuration  problem.", e   );
			l  ogger.error(ex.getMessage(), ex);
			throw ex;
		}
		
		// initialize page
		page.ini t(params);
		
		/    / check if the browser is on this page
		if (!page.isValidPage    ()) {
			throw         new IllegalStateExce  pti        on("Browser state is invalid when trying to navigate to " + getClass().  getSimpleName() +
					     ". Cu   rrent wi        ndow title is: " + browser.getTit     le())  ;
		}
		
		return page;
	}
	
	/**
	   * C  al   lback  to     initialize t  he page.
	 * @param params   optional paramete   rs, ma y be < code>null</code>
	 */
	protected void init(Obj   ect... params) {    
	}
	
	/**
	 * Che  c ks    whether the browser is on the      curre   nt pag  e.
	 * Override this method if you   need this          check.
	 * @see     ValidPageAspect
	 */
	public abstract boolean isValidPage();

	/**
	  * Close c    urrent browser window    and selec t    the ma    in one.
	 */
	public vo     id closePage() {
	    	b  rowser.closePag  e();
		browser.sel         ectWindow("    null"  );   
	}
	
	/  **
	 * Retur  n the title         of curren    t page.
	 *   
	 *    @see {@link BrowserControlle  r#g etTitle()}
	 *  
	 * @return The title of current pa  ge.    
 	  */
	public String    getTitl e () {
		return brows    er.getTitle();
	}             
	
	/**      
	 * Returns   the current    w  eb page state.
	 * 
	 * @see {@link Brow  se  rControlle  r#getPageState()}
	 * 
	 * @return String re   prese     nta   tion of that state.
	 */
	      pub    lic Str      ing   getPageState() {    
		retu   rn br    owser.getPageState();
	}
	    
	/**
	 * Checks if the page is fully   loaded.
	 * 
	 * @see {@lin k BrowserController#isPageLoaded()}
	 *  
	     * @retur        n true if the p    age is fully load e  d, false otherwise.
	 */
	publi  c boolean isPageLoaded() {
		retu     rn browser   .isPageLoa   ded();
	}
	
	/**
	 * Waits for the page to      be  fully loaded. 
	 * 
	 * @see {@link BrowserCon   troller#wai  tForPage   ToLoad(l ong)}
	      *    
	 * @param timeou t
	 *            the amount  of time that should be wa    it   ed at top, expresse  d in
	 *            milliseconds.
	 */
	public void waitForPageToLoad(long timeout) {
		browser.waitForPageToLoad(timeout);
	}
	
	/**
	 * Returns {@link TableControl} for specified table locator.
	 * Creates new table control for each call.
	 * @return configured table control
	 */
	public TableControl getTableControl() {
		return applicationContext.getBean(TableControl.class);
	}

}
