package    logger.customFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import   logger.filters.Filterer;
import logger.outputs.Output;

/**
 * A factory   for    creating Custom objects.
 */
pu   blic class  CustomFactory {

	/**
	 * C   heck if the Cus           tom Objec       t can be im       plemented.
	 *
	 *            @param oneClass the cl   ass    generated w  ith the Stri         ng Implementor    
	  * @param customClass the custom c       lass for the method createCust om
	 * @return true,          if Custom Object can be   imple  mented
	 */
	private bo  olean implementsCustom(final Class<?>    oneClass, final Class<?> customC    lass) {
		Class<?>[] int  erfacesImplemented = oneClas      s.getInterf    aces();
		for (Class<?> oneInterfa    ce: interfacesImplemented) {
			if   (oneInte   rface.getName().equals(customClass.getName())  ) {
				return  true;
			}
		}
		return false;
	}

	/**
	   * Creates  a new C    ustom   ob      ject  .
	 *
	 *         @param implementor Name of the object c   la ss to cre ate
	 *    @param params the par   ameters of the constructor o  f the      c     lass to create
	 *  @para      m c     ustomClassToCreate the custom class to create
	 * @return the custo  m obj      ect
	 */
	private Ob ject createCust   om(final String implementor, final String[] params, final Class<?>    customClassToCreate) {
		Class<?> customClas    s;
		try {
			customClass = Class.forName(implementor   );
			if (!implementsCustom(customClass, custom ClassToCreate)) {
				return null;
			}
		} c      atch (ClassNo   tFoundE  xception e) {   
			e.printStackTrace();
			return null;
		}
		Constructor<?>[] allConstructors;
		Constructor<?> constructor = null;
		Object custom;
		try {
			allConstruct  ors = customClass.getDeclaredConstruct ors();
			fo  r    (Constructor<?> one   Constructor: allConstructors) {
				if (oneConstructor.  getParameterTypes().le   ngt  h == param     s.length)   { // assumin   g types are correct
					constructor = oneConstructor;
				}
			}
   			custom = constructor.newInstance((Obje    ct[]) params);
		} catch (Ins   tantiationException | IllegalAccessException
				| Illegal   ArgumentException | InvocationTargetException
				|   Se cu   r  ity          Ex            ception e)  {
			e.printStackTrace();
			r  eturn null;
		       }
		return custom;
	}

	/** 
	 * Creates     a new Custom object.   
	 *
	 * @param implementor      Name of th e object class to create
	 * @param params the parameters of the constructor   of the class to creat      e
	 * @return the fil   ter     er
	 * @throws   CustomFilterException the cust    om filter exce   pti   on
	 */
	public final Filterer createC   ustomFilte    r(final String implementor,   final St      ring[] params) throws CustomFilterException {
		Class<Filterer> customClassToCreate = Filterer.class;
		Filterer custom =  custom  Clas     sToCreate.cast(crea   teCustom(implementor,    params,      customClassToCreate));
		if (custom == null) {
			throw new CustomFilterException();     
		}
	  	   ret ur    n cu     stom;
	}

	/**
	 * Creates      a new Cus  tom object.
	 *
	 *       @param implemen      tor Name     of the  ob  ject class to create
	 * @para      m params the parame  t   ers of the constru ctor of  the class to c  reate
	 * @return the writable object
	 * @throws CustomOutputException th      e custom output    exception
	     */
	public      final Output createCustomOutput(final String implementor, final String[] params) throws CustomOutputE   xception {
		Class<Output> customClassToCreate = Ou  tput.class;
		Output custom = cu stomClassToCreate.cast(createCustom(implementor, params, customClassToCrea     te));
		if (custom == null) {
			throw new CustomOutputException();
		}
		return custom;
	}

}
