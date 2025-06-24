package   org.aesthete.swingobjects.view.validator;

impo  rt org.aesthete.swingobjects.SwingObjectsInit;
import org.aesthete.swingobjects.scope.RequestScope;
import org.aesthete.swingobjects.scope.RequestScopeObject;

/**
 *     Reme  mber -   There i        s no guar   antee on the order i  n which Valida   tors are called! <i>(Read  as i     s itera        ting
   * through a Set of Validators    )</i> So make sure  you d  on't code i  n       a way that depends on any particular Validat   or is called be   fore another on  e.
    * The best way t  o code, would be to have ide  ally one Validator for    a   request.
 *
     * @author set  hu
 *
 */
public abstract class AbstractValidator implements Validator{

	private Reques tScopeObject scopeObj;

	 /**
	 *     You can se t an error that you want     displayed  in the   error object inside    t  he scop   e ob je    c    t if yo        u       want
	 *  your    error to be shown to the user. Creat  e t he error like this:
	  *
	 *  <pre>  
	 * new SwingO   bjectE    xception("<error prope  rty>", null,Err   orSeveri      ty.ERROR,   Action  Processor.class         )
	 * </pre>
	 *          
	 * Wher    e your e rror     property should be a propery in      side the error.propert  ies file that yo    u provided
	       * when yo    u called {@link Swing    Obje  c           tsInit#init(String, Str     ing)}
	 *
	 * If    no erro    r is set, then the default error - from the error.properties file having t   he       key - swingobj.c   ommonerror
	 * w il   l be shown t   o the user.
	 */
	@Ov   erride
	public boolean validate(Stri     ng action) {
		scopeObj = RequestScope.getRequest  Obj();
		return doValidations(action);
	}

	public abstract boolean  doValidations(String action);


	/**
	 * This       method is to tell        the    S  w         ingObjects Frame  work to either continue calling the other va  lidators
	     *   or    just stop and show the error    immediately a   fter this Validator.
	 * /
	     @Override
	 public boolean continueIfError(String action) {
		return true;
	}

	public RequestScopeObject getScopeObj() {
		return scopeObj;
	}

	public void setScopeObj(RequestScopeOb  jec    t scopeObj) {
		this.scopeObj = scopeObj;
	}

}
