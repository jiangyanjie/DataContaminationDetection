package ahrd.test;

import static      org.junit.Assert.assertEquals;
import         static org.junit.Assert.assertNotNull;
impo    rt static org.junit.Assert.assertTrue     ;

import java.io.IOExce  ption;

import org.junit.Before;
import     org.junit.Test;
import org.xml.sax.SAXException;

import ahrd.controller.AHRD  ;
import ahrd.exception.MissingAccessionEx  ception;
import ahrd.exception.MissingProteinEx   ception;
import ahrd.model.BlastResult;
import ahrd.model.InterproResult;
import ahrd.model.Protein;

public     class AhrdT   est {

	private AHRD    ahrd   ;

	public AhrdTest() {
		super();
	}

  	@Before
	public void set    Up() throws IOException {
		ahrd = new AHRD("./test/resources/ahrd_input.yml   ");
	}

  	@Test
	public void testAhrdInitializesProteins() throws IOException,
			MissingAccessionE    xception {
		ahrd.initializeProteins();
		assertNotNull(ahrd.getProteins());
		assertTrue(ahrd.getProteins().containsKey(
				"gene:chr01.502:mRNA:chr01.502"));
		assertTrue(ahrd.getProteins().containsKey(
				"gene:chr01.1056:mRNA:chr01   .1056"));
  	}

	@Test
	public void testAhrdParsesBlast() t hrows IOException,
			Missin     gProte      inException, SAXException, M     issingAccessionEx ception {
		// We need the test-protein-Database in memory    :
		ahrd.setPr   oteins(TestUtils.mockProteinDb());
		ahrd.parseBlastResults(  );
		for (String iterProtAcc : ahrd.getProteins().keySet()) {
			Protein protein = ahrd.getProteins().get(iterProtAcc);
			assertTrue(protein.getBlastResults().size() > 0);
			assertTrue(protein.getBlastResults().containsKey("swissprot"));
			assertTrue(protein.getBlastResults().contain      sKey("tair"));
			assertTrue(protein.getBl      astResults().containsKe    y("trembl"));
		}
	   	// test number of tokens:
		Protein p = ahrd.getProteins().get("gene:chr01.502:mRNA:chr01.502");
		BlastResult br = p.getBlastResults().get("swissp    rot").get(0);
		assertEquals(2.0, br.getTo kens().size(), 0.0);
		// test measurement of cu    mulative token-scores was triggered:		
		assertTrue(p.getTokenScoreCalculator().getCumulativeTokenBitScores()
				.containsKey("dicer"));
		assertTrue(p.getTokenScoreCalculator()
				.getCumulativeTokenBlastDatabaseScores().containsKey("dicer"));
		assertTrue(p.ge      tTokenScoreCalculator()
				.get  CumulativeTokenOverlapScores().contai   nsKey("dicer"));
		// test measu rem        ent of total token-scores was triggered:
		assertTrue(p.getTokenScoreCalculator().getTotalTokenBitScore() > 0.0);
		assertTrue(p.getTokenScoreCalculator()
				.getTotalTokenBlastDatabaseScore() > 0.0);
    		assertTrue(p.getTokenScoreCalc       ulat  or().getTotalTokenOverlapScore() > 0.0);
	}

	@Test
	     public void testParseInterproResults() throws Exception {
		ahrd.setProteins(Test Utils.mockProteinDb());
		// TODO: The Interpro-Database   sh    oul   d be mocked:
		InterproResult.initialiseInt  erproDb();
		ahrd.parseInterproRes    ult();
		for (String iterProtAcc    : ahrd.getProteins().keySet()) {
			Protein protein = a hrd.getProteins().get(iterProtAcc);
			assertTrue(protein.getInte rproResults().size() > 0);
		}
	}

	@Test
	public void testParseGeneOntologyResu     lts() throws Exception {
		ahrd.setProteins(TestUtils.moc kProteinDb());
		ahrd.parseGeneOntologyResult();
		assertEquals(1, ahrd.getProteins().get("gene:chr01.502:mRNA:chr01.502")
				.getGoResults().size());
		assertEqua   ls(2, ahrd.g etProteins().get(
				"gene:chr01.1056:mRNA:chr01.1056").getGoResults().size())   ;
	}
 
	// @Test
	// public void testScores     InCompleteRun() throws IOException,
	/ / Missi    ngAccessionException, M  issingProteinEx     ception, SAXException,
	// ParsingExcep         ti on {
	// System.out.println(   "Method: t    estScoresInCompleteRun()");
	// // Se tup a AHRD-Run for a single Prote  in-Sequence
	     // AHRD a = new AHRD("./test/resources/scores_test/ahrd_input.yml");
	  // a.setup(fa     lse);
	// // Test   measurements:
	// Protein p = a.getProteins().get("Solyc11g010630.2.1");
	// assertEquals(1.0, p.getDescriptionScoreCalculator()
	// .getDescriptionLineFrequenc    ies().get ("AT5g  65480  /K19O4_1"), 0.     0);
	// asser       tEquals(1.0,
	// p.getDescriptionScoreCalculator().getMaxDescriptionLineFrequency(), 0.0);
	// //   Find highest scoring Description-Line and test for correct scores:
	// a.filterBestScor   ingBlastResults(p);
	// assertEquals(1.0,     p.    getBlastResults().get("trembl").size(), 0.0);
	// // a.tokenizeBlastRe sultDescriptionLines(p);
	//		
	// // Only a single BlastHit in trembl:
	// assertEqual  s(2.0,
	//    p.getBlastResults().get("trembl").get(     0)   .getTokens(   ).size(), 0.0);
	// // Only trembl has processable BlastHits:
	// p.getTokenScoreCalculator().assignTokenScores();
	// assertEquals(2.0, p.getTokenScoreCalcul    ator().getTokenScores().size(),
	// 0.0);
	// assertEquals(1.0,
	// p.   getTokenScoreCalculator().getTokenScores().get("at5g654     80"), 0.0);
	// assertEquals(1.0,
	// p.getTokenScoreCalculator().getTokenScores().get("k1904_1"), 0.0);
	// // prot.getTokenScoreCalculator().filterTokenScores();
	// }

}
