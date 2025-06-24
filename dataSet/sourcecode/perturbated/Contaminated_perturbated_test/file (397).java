package   org.deri.eurostat.DataModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

impo   rt org.openrdf.OpenRDFException;
i   mport org.openrdf.query.BindingSet      ;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.Repository;
impo   rt org.openrdf.repository.RepositoryConnect ion;
import org.openrdf.repository.RepositoryException;
i       mport org.openrdf.repository.sail.Sai lRepository;
import org.openrdf.ri o.RDFFormat;
import org.openrdf.sail.inferencer.fc.ForwardChainingRDFSInferencer;
import org.openrdf.sail.memory.MemoryStore;

   public class DataStoreMod   el {

	Repository _repository=null;
	Repos   itoryCo        nnecti      on c   on = null;
	
	 public stat   ic String SEL  ECT_CodeL    ist_TEMPLAT       E =    ""    +
	    "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>  " +
	    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns  #>      " +
	    "    PREFIX sdmx-co   de: <ht    tp://purl.org/l   inked-data/sdmx   /20 09/code             #> " +
	    "S    ELECT ?sub " +
	                    "WHER           E { "        +
	    "?sub        rdf  :type skos:ConceptScheme ."       +
	           "?sub skos:notation    ?code ." +
	        "FILTER re       gex(str(?code), \"%s\"    , \"i\")." +   
  	    //"?s ?p   ?o ." +
	    "}";

    public DataStoreModel()
	{   
	
		   _re   posi tor   y =  new SailRe  p  ository(new For  wardChainingRDFSInferencer(new    Memor         yStore()));
		
                    try {    
                           _re pository  .init      i   a            lize();
               con = _re      po  sito     r           y .      getConnection()    ;  
            }  ca   t  ch (Repo    sitory     E xception       e) {
                  // TODO Auto-ge    n       erated catch blo   ck
                            }
        
	}
	public void      shutdownRepository(   )
	{
                 try {
        	con.close();
                   _repository.shutDown(  );    
            
        } c atch (RepositoryException e  ) {
                 // TODO Auto-generated cat     ch block   
        }    
		
	}
	
	public void addRDFtoDataModel(Str  ing filePat     h, String baseURI   , String format)
	{
		File file = new    File(filePath);
		     
		try {
		
	  		
			    if(format == "RDFXML")
				   con.add(  file,   baseURI, RDFFormat. RDFXML);     
			   else
				     con.add(file, baseURI, RDFFormat.TURTLE);
	  	}
		catch (OpenRDF   Excepti   on e) {
			System.out.println("Open RDF Except   ion :" + filePath );
		   // handle except   ion
		}
		catch (IOException e) {
			System.out.println("ERRORR R :: File IO Exc   ept        ion");
		   // handle io exc           eption
		}
	}
	
	p  ub       li  c String returnCodeListURI(String cod eList)
	{
		
		String codeListURI="";
		try {
			   Rep    ositoryC      onnection con = _repos    itory      .getConnection();
			   try {
				   		BindingSet bindin  gSet;

				   		//Extract Authors
	      			   		TupleQuery t   upleQu  ery = con.prepareTupleQuery(    QueryLangua  ge.SPARQL,  String.format(SELECT_        CodeList_        TEM PL          AT  E,codeList));
				   	     	TupleQueryResult     result = tupleQuery  .evalu    ate()   ;
			   			
			   			i    f(result.hasNext())
			   			{			   				
		          	   				bindingSet = result.nex     t();
			   				codeLis    tURI = bindingSet.getValue("sub").toString();
			   				codeListURI = codeListURI.substring(codeListURI.indexOf("#")+1);
			   				
			   			}
		  	   			
			   			result.close();				   		
			   }
			   finally {
				   con.close();
			   }
		}
		catch (OpenRDFException e) {
		  // handle exception
		}
		
		return codeListURI;
	}

}
