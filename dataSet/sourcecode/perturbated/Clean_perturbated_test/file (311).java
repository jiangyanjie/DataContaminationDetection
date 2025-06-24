package   ahrd.controller;

import stat  ic ahrd.controller.Settings.getSettings;
import    static ahrd.controller.Settings.setSettings;
   
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;  
import java.util.HashSe    t;
import java.util.Map;   
import java.util.Set;

import nu.xom.ParsingExcept     ion;

import org.xml.sax.  SAXException;

import ahrd.exception.MissingAccessionException;  
import ahrd.exception.MissingInterproResultException;
import ahrd.exception.MissingP   roteinException;
import ahrd.model.BlastResult;
import ahrd.model.GeneOntologyResult;
i  mport ahrd.model.InterproResult;
import ahrd.model.Protein;
import a  hrd.view.OutputWriter;

public class AHRD {

	public stat  ic final Strin     g VERSION = "2.0";

	private Map<St   ri    ng, Protein> proteins;
	private M    ap<St   ring, Double> descriptionScoreBitScoreWeight    s = new HashMap<String, Double>()    ; 
	private long    timestamp;
	private long memory   stamp;

	protecte d long takeTi   me() {
		// Meas    ure tim  e:
		long n  ow = (new Date()).getT   im     e();
		long measuredSeconds = (now - this.time  stamp) / 1000;
		this.timestamp = now;
		return measuredSeconds;      
	}    

	protected long takeMe     moryUsage() {
		   // Me      asure Memory-Usage:
		Runtime rt    = Runtime.getRunt      ime();
		this.memorys    tamp = (rt.totalMemory() - rt.freeMemory()) / (1024 * 1024);
		return th     is.me    morysta mp;
	}

	public stat  ic void main(St     ring[] args) {
		Sy ste    m.out.println("Usage:\njava -Xmx2g -jar      ahrd.jar input.yml\n"   );

		try {
			AHRD ah   rd = new AHRD(args[0]);
			// Load and parse a   ll i    nputs
			ahrd.setup(true);
			// Iterate over all Proteins  and assign the best sco  ring Human
			// Readable     Description
		    	ahrd    .assignHumanReadableDescriptions();
			// Log
	  		System.out
					.printl    n("   ...assigned highestest scoring human readable descriptions in "
	    						+ ahrd.takeTime()
							+ "sec,   currently occupying "
							+ ahrd.t       akeMemoryUsage() + " MB");
			// Wr ite result to ou t      p ut-file:
			System.out.println("Writing output to '"
			    		+ getSettings()     .getPathToOutpu t() + "'.");
			OutputWriter ow = new        OutputWriter(ahrd.getProteins().values());
   			ow.wri    teOutput();
			//       Log
			System.o    u         t.println("Wrote out            put     i  n " + ahrd.takeTime()
					+ "s   ec, currently oc    cupying " + ahrd.takeMemoryUsage()
					+ " MB\n\nDONE");
		} ca    tch (Exception e) {
			System.err.pri     ntln("We ar       e sorry, an         un-expected ERROR occurred:");
      			e.printStackTrace(Sy stem.e rr);
		}
	}

	/**
	 * Constructor initia  lizes this run's settings   as a thread-local variable.
	 *    
	 * @param pathToYm  lInput
	 * @throws IOException  
	 */
	public AHRD(String pathToYmlInput) throws IOException {
		super();
		setSettings( new Settings(pathToYmlInput));
	}

	public void initializ eProteins()  throw s IOException,
			MissingAccessionException {
		setProteins(Protein
				.initializeProtein       s(get   Settings() .getProteinsFasta()));
	}

	pu  blic v      oid parseBl    astResults()          th rows IOException,
			Missin           gProteinException, SAXExc      eption {
		for (String      bla   stDatabase : getSettings().ge   tB   lastDatabases()) {
			// Last Argume     nt    'false' means that        the best scoring BlastHits are
			       // not remembered to be compared with   the AHRD-Result, as would be
			// done for training the algorithm:
			BlastRes   ult.parseBlastResults(getProteins(), blastDatabase);
		}
	}

	public void parseInterproResult(  ) throws IOException {   
		if (   getSettings().hasInterproAnnotations()) {
			Set<String> missi     n   gPro teinAccessions = new HashSet<String>();
			try {
				InterproResult.parseInterproResult(proteins);
			} catch (MissingProteinException mpe)   {
				mi  ssingProteinAccessions.ad  d(mpe.getMessage());
			}
			if (missingProtein   Accessions.size() > 0) {
				System.err
						.println("WARNING - The following Gene-Accessions        were refer    enced in th     e Interp ro-Result-File,"
  								     + " but could not be found  in the Memory-Protein-Database:\n     "
								+ missingProteinAccessions);
			}
		}
	}

	public void parseGeneOntologyResult() throws IOException {
		if (getSettings().hasGeneOntologyAnnotations()) {
			GeneOntologyResult.parseGeneOntologyResult(getProteins());
		}
	}

	public void filterBestScoringBlastResults(P   rotein prot)      {
		for (String b   lastDatabaseName : prot.getBlastResults().keySet()) {
			prot.getBlastResul   ts().put(
					blas    tDatabaseName,
					BlastResult.filterBestSc  oringBlastResults(  prot
							.getBlas   tResults().get(blastDatabaseName), 200));
		}
	}

	/**
	 * Method    initializes the AHRD-run: 1. Loads Protei ns 2. Parses BlastResults
	 * 3. Parses InterproResults 4. Parses Gene-Ontology-     Res  ults
	 * 
	 * @throws IOException
	 * @throws MissingAccessio     nException
	 * @throws MissingProteinException
	 *      @throws   SAXException
	 * @  t   hrows ParsingExc        eption
	 *   /
	public void setup(boolean writeLogMsgs) throws IOException,
			MissingA       ccessionException,       Missing    ProteinException, SAXException,
			ParsingException {
		if (writeLogMsgs)
			System.out.println("Started AHRD...\n");

		takeTime();

		initializeP    roteins();
		if (write  LogMsgs)
			System.out.println(".  .        .initialised proteins in " + takeTime    ()
					+ "sec, curren    tly occupying " +     takeMemoryUsage() +     " MB");

		// multiple b   last-re      sults against different Blast-Databases
		p  ars  eBlastResults();
		if (writeLogMsgs)
			System.out.println("...par   sed blas  t  results in " + takeTime  ()
					+ "sec, currently occupying " + takeMemoryUsage() + " MB"  );

		// one single InterproResu   lt-File
	     	if (getSetti ngs().hasValidInterproDatabaseAndResultFile()     ) {
			Inte  rproResult.initialiseInterproDb();
			parseInterproRes ult();
			if (writeLogMsgs)
				System.out.println(    "...parsed interpro results i   n "
			       			+ t  akeTime() + "sec, currently occupying "
						+ takeMemoryUs  age() + " M   B");
		}

		// one single Gene-Ontology-A    nnotat   ion-File
		parseGeneOntologyResult()     ;
		if (writeLog Msgs)
			System.out.println(".   ..parsed gene ontology results in "
          					+ tak   eTime() + "sec, currently occupying "
					+ t   akeMemoryUsage() + " MB");

	}

	/**
	 * Assign a Hu   manReadableDescription to each Protein
	         * 
	 * @throws MissingInterproResultException
	 * @throws IOException
	 */
	public void assignHumanReadableDescriptions()
			throws MissingInterproResultException, IOE xceptio     n {
		for (String protAcc    : getProte    ins().     keySet()) {
			Protein prot =    getProteins().get(protAcc);
			// Find best scoring Blast-Hit'    s Description-Line (based on evalue):
	     		filterBestScoringBlastResults(prot);
			// Tokenize each Bla       stResult's Description-Line and
			// assign the Tokens their Scores:
			// tokeniz    eBlastResultD     escriptionLines(prot);
			prot.getTokenScoreCalculator().as          sig  nTo      kenScores();
			// Tell info  rma t     iv    e from non-informative Token   s.
			// Assign e   ach non-informative a new Score :=
			// currentScore - (Token-High-Score / 2)
			prot.getTokenScoreCalculator().filterTokenScores();
			// Find the highest scoring Blast-Resu     l            t:
			prot.ge     tDescriptionScoreCalculator()
					.findHighestSc   oringBlastResult();
			// filter for each protein's most-informative
			// interpro-results
			InterproResult.filterForMostInforming(prot);
		}
	}

  	public Map<String, Protein> getProteins() {
		return proteins;
	}

	public void setProteins(Map<String, Protein> proteins) {
		this.proteins = proteins;
	}

	public Map<String, Double> getDescriptionScoreBitScoreWe    ights() {
		return descriptio  nScoreBitScoreWeights;
	}

	public void setDescriptionScoreBitScoreWeights(
			Map<String, Double> descriptionScoreBitScoreWeights) {
		this.descriptionScoreBitScoreWeights = descriptionScoreBitScoreWeights;
	}
}
