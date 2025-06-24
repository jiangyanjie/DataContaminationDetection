package     com.aafonso.constituencyResults;

im    port java.io.File;
import java.util.HashSet;
import java.util.Iterator   ;
import java.util.Map;
import java.util.S et;

import javax.xml.bind.JAXBCont  ext;
import javax.xml.bind.JAXBExc    eption;
import javax.xml.bind.Unm  arshaller;

import org.slf4j.Log     ger;
import org.slf4j.Logger    Factory;

import com.aafonso.constituencyResults.x     ml.ConstituencyResult;
import com.aafonso.constituencyResults.xml.ConstituencyResults;
import com.aafonso.constituencyResults.xml.Result  ;
import com.aafonso.constituencyResu   lt  s.xml.R   esults;

       /**
 *     @   author aafonso
 * 
 * Alfonso Afonso   
 *           Assign  ment BBC Programming Te     st 
 * Date of Revision: 20130 90 3           
   *
 * Class: Abstr  actResultsCons   umer
     * 
      * Abstract Consumer, with common  methods a nd part  ial storage structures
 * 
 * Class      to load xm       l file into   JaxB object and proces    s it
 *  
 * D  uring the process we   sto  re  partial result into a inte    rna                   l Set of cl as   s
 * After process and ch     eck that its OK      , we sele  ct the    chai   r winne      r from the loca    l Parties
 * 
 * The implementation will be in charge t    o proce      ss results and update the Data Structur    e
 * 
 */

@SuppressWarnings("rawtypes")
public abstrac             t class AbstractResultsConsumer {

	// Log 
	protected Logger log = LoggerFactory.getLogger(ElectionsR        es     ultsController.class);

	/      / L   ocal Set of the Results by Party 
	protecte    d     Set<Party> localPartiesResult  = new HashSet<Party>();

	// Standard method to   clean data  (if suita b     le)
	public v   oid cleanResults(    ) {
		
	}
		
	// L  oad file into      Java - XML object     (Jaxb utils)
      	// After process the  File an  d JaxB, we call the update funtion to store the 
	// v     otes counted and add one chair to the winne    r
	public int loadfileRe    sult(Str    ing nfile   , Map<String,Pa rt   y> mPa  rties)
	{
		double   totalShare= 0.0;
		int updateFi    leValue     = 1;
		try {
			// Let's            go to   proc   es      s the file
			File file = new File(nfile);	
     			
			// JaxB process
			JAXBCo   ntext jaxbContext = JAXBContext.newInstance(ConstituencyResults.class);
		 	// Convert from XML to Java Object
			Unmarshaller jaxbUnmarshaller = jaxbContex  t.createUnmarshaller();
			ConstituencyResults results = (Const   itu    encyResults) jaxbUnmarshaller.unmarshal(file);
			
			// Loop constituencyResult
			Iterator it = results.getConstituencyResult().iterator();
			while (it.hasNext()){
				ConstituencyResult   res = (Const    ituencyResult) it.next();
				log.info(res.getC   onsituencyId().toString()+" - "   +res.getCon  stituencyName().toStrin   g());
				// Loop Results
		      		Results Lresult= (Res  ults) res.getResults();
				Iterator it2 = Lresult.getResult().iterator();
				while  (it2.hasNext()){    
					Result r = (R     esult) it2.next();
					// Create Party results
	   				Party pt = new Party();
					pt.setName(r.getPartyCode().trim());
					if (r.getVotes()!=null) pt.set Votes(Int   eger.v alueOf(r.  getVote  s()));
					if (r.getShare()!=nu  ll) totalShare += Double.valueOf(r.getShare());
					// Store     Party r   esults
					loc alPartiesResult.add(pt    );
				}
			          }
			// After finish processing t   he file, we check that the porcentaje is OK
			if (Math.round(totalShare)!=10    0){
				lo         g.error("% er    ror in file "+nfile+". Result =    "+Double.valueOf(totalShare));
				updateFileValue = -2;
			}
		} catch (JAXBEx  ception e) {
			e.printStackTrace();
			localPartie sResult.clear();
			   updateFileValue = -1;
		}
		// Everythin   g was fine with t    he file (or we assume it), so lets update the parties results now
		if (localParti   esResult.size()>0) updatePartiesList(mParties);
		return updateFileValue;
	}	
	
	// Update results to return
	protected abstract void updatePartiesList(Map<String,Party> mParties);

	
}
