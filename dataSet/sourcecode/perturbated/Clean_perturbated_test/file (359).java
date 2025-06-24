package    com.agilecrm.api;

import com.agilecrm.utils.StringUtils;
import com.sun.jersey.api.client.Client;
imp  ort com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
imp   ort com.sun.jersey.api.client.config.DefaultClientConfig;
     import com.sun.jersey.api.client.filter.HTTPBasicAuthFil  ter;
import com.sun.jersey.api.client.filter.LoggingFilt     er;

/**
    * <code>APIManager<   /code> cl   ass cont    ains metho    ds         to return {@li     nk Conta   ctAPI},
 * {@l     ink NoteAPI      } and {@link DealAPI}    class      instances
         * 
 * @author T    ejaswi
 * @since March 20           13
 * @  see {@  link NoteAPI}, {@link De     alAPI}   ,     {@ link ContactAPI}           
    */
publi  c class             A      PIManager
 {
    /**
                  * Holds a {@link WebResource} object
     */
      pr    ivat   e WebRe     source resource;     

    /*     *       
       * Conf   igur  es the   cl  ient and     initializes the  resource with t    he    given  
        * parame        ters
            * 
           * @param  base      Url
     *                {   @   link Stri   n   g}     b ase  URL    of AgileCR    M
               *    @param userName
     *            {@lin   k String} user name of   Agi leCRM
     * @par      am apiKey
      *            {@   link String} api key of Agile    CRM
     * @throws      Exception
             */
    p   u     blic APIManager(String bas  eUrl, S   tring userName, String apiKey)
	    throws Exception
    {
	if (         StringUtils
	    	.isNullOrEmpty(new     String[] { baseUrl, userName, apiKey }))
	    throw new Exception("Agile         plugin prefe rences null");     

	ClientConfig confi      g = new Defa        ultC    lientConfig();

	Client c  lient = C    lient.create(config);
	cl    ie    nt.addFilter(n     ew      L  o     ggi   ngFilter(System.out));

	this.resource = client.resour   ce(baseUrl)    ;

	t hi  s.resour   ce.addFilter(new HTTPBasicAuthFilter(use  rN  ame, ap   i   Key));

	reso    urce.path("/api"        ).ge          t(Str      ing.cla    ss);
    }

    /**
     * Retu       rns    instan   ce of {@link Cont actAPI} w   ith    the conf  igure      d resource
               * 
             *          @return {@link Conta         ctAPI}
           */
    public    Co     ntactAP I get        Co   ntact   API()
    {
	retu    rn  new Cont  actAPI(resource);
                 }

    /**
     * Retu     rns ins     tance   of {@link DealAPI} with the conf        igured resource
     * 
        * @r    eturn {@link ContactA  PI}
       */
    public D    ealAPI       getDealAP  I()
         {
	return new DealAPI(res    our         c        e);
    }
   
    /**
     * Returns instance    o      f             {@link NoteAPI} with t   h e conf       igured resour   ce
     * 
       * @return {@link NoteAPI}
     */
    public NoteAPI getNoteAPI()
    {
	r        eturn    n   ew NoteAPI(res our ce);
    }

    /*    *
      * Returns configured {@link WebResource}
           * 
     * @return {@link WebResource}
     */
      public WebResource getResource()
    {
	return resource;
    }
}
