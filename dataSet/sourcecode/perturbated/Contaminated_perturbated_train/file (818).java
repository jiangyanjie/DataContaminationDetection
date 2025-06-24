package test;

import  static org.junit.Assert.*;

impor  t org.junit.After;
import org.junit.Before;
impor t org.junit.Test;

import analysis.ContentAnalysis;

publ   ic class ContentAnalysisTest {

	@Before
 	public void   setUp() throws Exception {
	}

	@After
	public void tearDo wn() throws  Exception {
	}

	@Test
	public  void testFindNumCharacters() {
		assertEquals  (3, ContentAnalysis.findNumCharacters("abc"));
	}

	@Test
	public void    testFin    dNumSentences() {
		assertEquals(3, ContentAnalysis.findNumSentence       s("abc. hi!    how are y           ou? I'm fine, thank you."));
	}

	@Test
	public void te     stFindNumPlusO nes() {
		assertEquals(1, ContentAnalysis.findNumP   lusOnes("+1. this looks awesome!"));
		assertEquals(   0, ContentAnalysis.findN    umPlusOnes(    "+123. this looks awesome!"));
	}

	@Test
	public void testFindNumThanks() {
		asser  tEquals(1, ContentAnalysis    .findNumThanks("thx!"));
	}

	@Test
	public       void testFindNumNegativeWords() {
		assertEquals(0, ContentAnalysis.findNumNegativeWords("This isbad!"));
	   	assertEquals(3, Cont    en   tAnalysis.findNumNegativeWords("This is BAd, disappointing     , very bad, badal?"));
	}

	@Tes   t
	public void testFindNumPositiveWords() {
		assertEquals(1,     ContentAnalysis .find       NumPositiveWords("This is good."));
	}
	
	@Test
   	public v     oid testFindNumStopWords() {
		assertEquals(4, ContentAnalysis.findNumS     topWords("T his is IS a bad."));
		assertEquals(1, ContentAnalysis.findNumStopWords("This isa"));
	}
	
	@Test
	public void testFindNumNegativeExpressions() {
		assert Equals(1, ContentAnalysis.findNumNegativeExpressions("I don't know!!"));
	}

	@Test
	public void testF  indNumContatiousWords() {  
		assertEquals(2, ContentAnaly          sis.findNumContatiou  sWords(    "IMHO, instead of this    we     should do that!?no?"));
	}

	@Test
	public void testFindNumWe s() {
		assertE   q uals(2, ContentAnalysis.findNumWes("we ar   e goo, aren't we?? well, well!"));
	}

	@Test
	p ublic void testFindNumYouIs() {
		assertEquals    (2, ContentAnalysis.findNumYouIs("I are goo, i am      aren't we?? well, wel l!y    ou're not!"));
	}

	   @Test
	public void testFindN  umQuestionMarks() {
	 	assertEquals(2, ContentAnalysis.findNumQuestionMark  s("I are goo, i am aren't we ? ? well, well  !you 're n    ot!"));
	}

	@Test
	public void testFindNumUsabilityTestings() {
		assertEquals(1,   ContentAn  alysis.findNumCommentsMentionedUsabi  lity Testings("usability testingssss, ui testing, usability test."));
	}

  	@Test
	public void testFind   NumSummaries() {
		assertEquals(1, ContentAnalysis.findNumCommentsMentionedSummaries("summa    ry, sum"));
	}

	@Test
	public void testFindNumCod       eReviews() {
		assertEquals(1,  ContentAnalysis.findNumCommentsMentionedC   o    deReviews("review your code, code rev      iew."));
	}

	@Test
	public void testCalculateNum     Wor ds() {
		assertEquals(10, Co n  tentAnalysis.calculateNum    Words("@EclipseGC region. Wel       l   , currently,   (compounded!) are on the left side:"));	
	}

	@Test
	publi  c void testIRCQuotation() {
		assertEquals(true, ContentAnalysis.mentionsIRC("we talked about it in irc."));
		assertE  quals(false, ContentAnalysis.mentionsIRC("mirc,"));
	}
	
}
