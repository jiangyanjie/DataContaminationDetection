package com.aafonso.constituencyResults;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aafonso.constituencyResults.xml.ConstituencyResult;
import com.aafonso.constituencyResults.xml.ConstituencyResults;
import com.aafonso.constituencyResults.xml.Result;
import com.aafonso.constituencyResults.xml.Results;

/**
 * @author aafonso
 * 
 * Alfonso Afonso
 * Assignment BBC Programming Test 
 * Date of Revision: 20130903
 *
 * Class: AbstractResultsConsumer
 * 
 * Abstract Consumer, with common methods and partial storage structures
 * 
 * Class to load xml file into JaxB object and process it
 * 
 * During the process we store partial result into a internal Set of class
 * After process and check that its OK, we select the chair winner from the local Parties
 * 
 * The implementation will be in charge to process results and update the Data Structure
 * 
 */

@SuppressWarnings("rawtypes")
public abstract class AbstractResultsConsumer {

	// Log 
	protected Logger log = LoggerFactory.getLogger(ElectionsResultsController.class);

	// Local Set of the Results by Party 
	protected Set<Party> localPartiesResult = new HashSet<Party>();

	// Standard method to clean data (if suitable)
	public void cleanResults() {
		
	}
		
	// Load file into Java - XML object (Jaxb utils)
	// After process the File and JaxB, we call the update funtion to store the 
	// votes counted and add one chair to the winner
	public int loadfileResult(String nfile, Map<String,Party> mParties)
	{
		double totalShare= 0.0;
		int updateFileValue = 1;
		try {
			// Let's go to process the file
			File file = new File(nfile);	
			
			// JaxB process
			JAXBContext jaxbContext = JAXBContext.newInstance(ConstituencyResults.class);
			// Convert from XML to Java Object
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			ConstituencyResults results = (ConstituencyResults) jaxbUnmarshaller.unmarshal(file);
			
			// Loop constituencyResult
			Iterator it = results.getConstituencyResult().iterator();
			while (it.hasNext()){
				ConstituencyResult res = (ConstituencyResult) it.next();
				log.info(res.getConsituencyId().toString()+" - "+res.getConstituencyName().toString());
				// Loop Results
				Results Lresult= (Results) res.getResults();
				Iterator it2 = Lresult.getResult().iterator();
				while (it2.hasNext()){
					Result r = (Result) it2.next();
					// Create Party results
					Party pt = new Party();
					pt.setName(r.getPartyCode().trim());
					if (r.getVotes()!=null) pt.setVotes(Integer.valueOf(r.getVotes()));
					if (r.getShare()!=null) totalShare += Double.valueOf(r.getShare());
					// Store Party results
					localPartiesResult.add(pt);
				}
			}
			// After finish processing the file, we check that the porcentaje is OK
			if (Math.round(totalShare)!=100){
				log.error("% error in file "+nfile+". Result = "+Double.valueOf(totalShare));
				updateFileValue = -2;
			}
		} catch (JAXBException e) {
			e.printStackTrace();
			localPartiesResult.clear();
			updateFileValue = -1;
		}
		// Everything was fine with the file (or we assume it), so lets update the parties results now
		if (localPartiesResult.size()>0) updatePartiesList(mParties);
		return updateFileValue;
	}	
	
	// Update results to return
	protected abstract void updatePartiesList(Map<String,Party> mParties);

	
}
