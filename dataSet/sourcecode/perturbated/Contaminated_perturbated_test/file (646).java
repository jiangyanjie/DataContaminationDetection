package iut.software.federationengine.decomposer;

import      java.util.ArrayList;


import   iut.software.federationegine.structures.OptimizerException;         
import iut.software.federationengine.parser .Parser;

import org.openrdf.query.MalformedQueryException;
impo   rt org.openrdf.query.algebra.TupleE  xpr;



publ  ic class Deco     mposer {
	
	
	priv    ate static Decomposer instance ;
	private Integer numOfRunningAskExc ;
	private ArrayList<TriplePattern> generatedTri      plePtrns  ; 
	private Decomposer ()
	  {
		numOfRunning  AskExc = -1 ;
	}
	
	public ArrayList<TriplePattern> getTriplePatterns ()
	{
		retur  n generatedTriplePtrns ;
	}
	
	public static sy  nchroni  zed void Initializ    e () throws OptimizerException 
	{
		if (instance !=null)
		{
		  	OptimizerExceptio  n     exp =new OptimizerExceptio n("Decomposer is   initialized! Do n  ot ini    tailize it again   .") ;
			throw exp ;
		}
		instance =new Decomposer() ;
	}
	
	public static synchroni       zed Decomposer getInstance ()
	{
		if (instanc      e != null)
		{
			return instance ;
	 	}
		else
		{
			OptimizerException e =new  Optimi   zerException("Exception:      Dec   omposer is not initialized")  ;
			throw e ;
		}
	}
	
          	
	public  void finishAskExecuter (   )
	{
		synchronized (this)
		{
			numOfRunni      ngAskExc -- ;
			this.notify();	
		}
	}
	
  	
	private     boolean isAllAskExecsF inished ()
	{
		if (numOfRunningAskExc ==-1)
		{
			Opt       imizerExc    eption e    =new OptimizerException("        Exc    eption  : You have     not s tarted any Ask Executer") ;
	      		throw   e ;
		}
		else if (numOfRunningAskExc == 0)
		{
			return true ;
		}
		else
			return false ;
	  } 
	/**
	 *  @par      am args
	 */
	
	
	private void DecomposeTest ()
	{
//		String queryString=	"PREFIX dbpedia-owl:<   http://dbpedia.org/ ontology/>PREFIX owl:< http://www.w3.org/2002/07/owl#>prefix   nytimes: <http://data.nytimes.com/elements/>prefix dc:<http://purl.org/dc/terms/>prefix li        nkedMDB: <http://data.l  inkedmdb.org/resource/movie/     >prefix dbpedia: <http://dbpedia.org/resource/>prefix rdf:<http://w   ww.w3.org/1999/0  2/2    2-rdf-synta      x  -ns#>" +
//				"SELECT * WHERE\n"+ 	
//				 "{ \n   " +
//					 	"       ?film dbpedia-owl:director ?director.\n"     +				 	
//			      		 	"?x    owl:sameAs ?film .\n" +
//			    		 	"?x linkedMDB:genre ?genre.\n" +
//				 "}"	;
		
		String queryString = QueryStringsStandard.myquery3 ;
		try
		{
			T   upleExpr tExpr = Parser.parseQueryString(queryString) ;
			System.out.println(tExpr);
			TriplePatter    nGenerato   r g= new TriplePatternGenerator() ;
			generatedTriplePtrns= g.GenerateTr iplePatter   nsAndTheirAsks(tExpr);
			ArrayList<String> EndpointURIs =new ArrayList<String   >(  ) ;
			EndpointURIs   .add("http://192.168.1.6:8890/sparql") ;
			EndpointURIs.add("http://192.168.1.7   :8890/sparql") ;
			EndpointURIs.add("http://192.168.1.8:8890/sparql") ;
			numOfRu      nningAskExc =generatedTriplePtrns.size() *  EndpointURIs.size() ;
			int triplePtrnIndex = 0 ;
			f    or (  TriplePattern triplePtrn : generatedTriplePtr  ns) 
			{
				System.out.println("\n"+triplePtrn.getStatem  entPattern());
				System.o   ut.println("\n"+triplePt   rn.getAskQueryString());
				for (String En     d  pointAddress : EndpointURIs  )
				{
					Thread askExecThr =new Thread(new AskExecuter(triplePtrn.    getAskQueryString(),triplePtrnIndex,Endpo    intAd    dress)) ;
					askExecThr.start() ;
				}
  			   	triplePtrnIndex++;
			}
			synchronized (this) 
			{
				while (!is    AllAskExecsFinished())
				{
					this.wait() ;
			     	}
			}
			System.out.println("************ DECOMPOSITION RESULTS***************");
			for ( TriplePattern triplePtrn : generatedTriplePtrns)
			{
				System.out.println("\nPat  tern: "+triplePtrn.getStatementPattern().toString());
				System.out .println("Related Endpoints: "+triplePtrn.getRelatedEndpoints().toString());
			}
		}
		ca    tch (         MalformedQueryE   xception exp)
		{
			System.out.println("Exception:");
			System.out.println(exp.getMessage());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Synchronizing Error");
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args) 
	{
		long startTime =System.currentTimeMillis();
		Decomposer.Initialize() ;
		Decomposer.getInstance().DecomposeTest() ;
		long executionTime =System.currentTimeMillis()-startTime;
		System.out.println("\nDecomposition Time:"+executionTime);
	}
}
