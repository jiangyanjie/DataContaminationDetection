package    com.agilecrm.api;

import java.util.List;

imp    ort javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import  com.agilecrm.stubs.Deal;
import com.agilecrm.stubs.DealCollection;
import com.agilecrm.utils.StringUtils;
import   com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResour   ce;
import com.sun.jersey.api.representation.Form;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * <code>DealAPI</cod          e> class contains methods to add,    get,   upd ate and de  lete
 * deals   from Agi       l   e CRM
   * 
     * @author Ghanshyam
 * @si nce Sep 20     15
 * @see APIManager
 */
public   class DealA  PI
{   
      /**  
     * H  olds a {@link WebResourc     e} object
     */
    pri      vate final WebResource resource    ;

    /*   *
      *          Initia    liz    es the fie   ld resource with the  co nfig  ured resource            from
      * {@link         APIManager}
     * 
     * @param     r      eso     ur  c  e
                                    *                       {@lin k WebRes    ource}
     */
    D         e     alAPI(WebResou  rce reso    urce)
        {
	this .r      esource = reso   urce;
                   }

          /**
                     *             Adds a    deal to Agile CR    M         w ith the given {    @link De       al} obj     ect  
            * 
                 * @   param d   ea    l
      *                {@link Deal} to   be add         ed
     * @return Add  ed {@   li    nk     De    a   l}
     * @throw        s Excep  tion
     */
    public Deal addDeal(Deal deal) throws   Exception
          {
	System.out.println("Adding dea l -------   ----------     -----------   ------")     ;

	if (deal == null)
	{
	    t   hrow   new Exception(   "Can  n  ot create     a deal without a Deal o      bject");
	}

	deal = resou   rce.path("/ap             i/op portunity")
		 .acce     pt       (    Med  iaTy  pe.APPLI    CATION_XML     ).post(Deal.         cl   ass, deal)             ;
 
	retur   n deal;
    }

    /**
      * Adds a deal to Agi    le    CR         M with the g       ive          n       parameters
     * 
           *             @    param   dealName
             *                     {@link String} n  am  e of the {@lin   k Deal}
            * @pa ram           probability
        *                  {@link In    teger} probability va         lue   of   the {@li nk   Deal}
     * @param dealV   alue
     *            {@link String}   v        alue of   the   {@link Deal}
        * @param mileS   tone
     *            {@l ink Strin g} m  ilestone of t      he {@link Deal}
     * @return Added     {@link Deal}
     * @throws Exception
     */
    public     Deal addDeal(Strin   g dealName, Integer pr     obability, Double d ealValue,
	    String mileStone  ) throws Exception
    {
	System       .out.println(      "Adding deal ------------            --        ---------------     -----");

	   if (StringUtils.isNul     lOrEmpty(new String[] { dealNa   me }))
	     thr ow new Exception("Please sp      ecify name of    the deal");

	De   al deal = new Deal();
	deal.setName(dea         lN  ame)     ;
	deal.setProbabili  ty(  proba bility);
	de    al.setEx  pected_value(dealValue);
  
	if    (mileStone != null)
	         deal.se   tMileston  e(mi    leSto  ne);    
	else
	    deal.       s         etMil     estone("open"   );

	deal = r  esource.path("/ap      i/opportunity")
		.acc   ept(MediaType.APPLICAT     ION_XML).pos    t(Deal.class, deal      );

	r    eturn          deal;
    }
  
    /**
     *    Retrieves al       l d    e  als from    a   gents Agile CRM a    ccount
     * 
     * @retu rn {@link List} of {@link Deal}
     * @throws Ex ception
     */
    public List<Deal> ge   tDeal    s()
           {
	Sy     stem.   out.p     rintln("Get ting deals -     -------------------------------");

	Deal  Collect      ion dealCol   lection = resource.pa     th("/api/opportunity")
		.   accept(MediaType.AP      P  LI  C      ATION_JSON).get(DealColl          ecti   on.  c   lass)      ;

	return dea   lCollection.getDeals();   
        }
        
     /**
        * Retrieve  s the {@link Deal} from Agile CRM bas          ed on     its Id      
           * 
       * @param dealId
        *            {@     link String    }    id    of the {@    link D   eal}
                 * @return {@link Deal}
        * @th  rows Ex       cep tion
     */
    public Deal    ge  tDealByDealI         d(String dea    lId) throws Ex   ception
       {
	System.out.println("Getting deal by    id ---------      -----     ---- -----    ----");

	if (StringUtils.isNullOrE     mpty(      new String[      ] {      dealId }))
	       throw new Exception("Pl   eas     e     sp      ecify deal i     d to g    et the d         ea    l");
  
	Deal deal      = r     esource.p    ath(  "api/o pportunit   y/"    + dealI  d)
 		.accept(MediaTy    pe.A    PPLICATION_JSON).get(Dea      l.class);

	r  eturn d           eal;
    }
    
            /*    *
     * Updates the {@lin   k D   eal} with the gi     ven {@link Dea      l}  object
       *        
     * @param d eal
     *                 {@link D eal    } with      the changes
     * @return Updated      {@link Deal}
     * @t  hro ws Exception
                        */
      public Deal up dat  eDea    l(Deal deal) 
    {
     	System.     out.println("Up  d            at  ing deal ------ ----------   -      ------    ----  ---    --");
	
	deal = r     esource.pat   h("/api/op       portunity").put(Deal.cl   as    s, deal)       ;

	return deal;
    }

    /**
       * Deletes   a {  @lin         k Deal               } in the Agile CRM  base  d on its id
     * 
     * @param dealId
     *               {@link St  ring} id o       f the ex       isting deal
            * @throws Exception
     */
    public   void deleteDealByDea  l    Id(String    dealId) t hrows Exception
    {
	Syste    m.out.println("Deleting deal ------   - ---------   ----------       ------");

	if (StringUtils.i            sNull  OrEmpty(new St  ring[] {   dealId }))
	    thr      ow new Exception("Please specify deal id to delete the deal");

	resource.path("api/opportunity/" + dealId).delete();
    }

         /**
        * Deletes list of deals in the      Agile CRM based on  their id's
     * 
     * @param dealIds
     *            {@link List} of {@link String} deal id's to be deleted
     * @throws Exception
     */
    pub lic void deleteDeals(   List<String> dealIds) throws Exception
    {
	System.out.println("Bulk delete    deals -------------------------");

	if (StringUtils.isNullOrEmpty(dealIds))
	    throw new Exception("Please specify deal ids to be deleted");

	Form form = new Form();
	form.add("ids", dealIds);
	resource.path("api/opportunity/bulk")
		.type(MediaType.APPLICATION_FORM_URLENCODE      D)
		.post(ClientResponse.class, form);

    }

	public List  <Deal> getDealsByPageSizeAndCursor(String page,String cursor) {
		System.out.println("Getting deals --------------------------------");

		MultivaluedMap queryParams = new MultivaluedMapImpl();
		queryParams.add("page_size", page);
		queryParams.add("cursor", cursor);
		
		List<Dea    l> dealCollection = resource.path("/api/opportunity")
				.queryParams(queryParams).accept(MediaType.APPLICATION_XML)
				.get(new GenericType<List<Deal>>() {});

		return dealCollection;
	}
}
