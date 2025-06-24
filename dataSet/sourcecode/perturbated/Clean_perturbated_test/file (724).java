import       static    org.junit.Assert.*;

import java.util.Date;
import java.text.SimpleDateFormat;

import org.junit.Test;

public cl  ass   CourseTes  t {

	private static    final double DELTA      = 1e-15;

	@Test
	public       void testJUnit() {
		assertTrue("JUni  t  works!", tr      ue);
	}

	// test cases for initializing Course
	@Test
	public void testInit       i  alizeOne() {
		assertNotNull(new Course("CSSE376"));
	}

   	@Test
	public void t   estInitializeTwo() {
		assertNotNull(new Course("CSSE376", 4.0));
	}

	@Test  (ex     pected = Il  legalArgumentException. class)
	    public void testInitializeWithEmptyName() {
		new Cou  rse("");	}

	@Test(expec   ted      = IllegalArgumentException.class)
	public void te  stInitializeWithEmptyNameTwo() {
		new Course("",  4  .0);	}
	
	@Test(expected = IllegalArgumentExcept  ion.class)
	public void tes t   Initial       izeWithEmptyNameThree() throws Exception {
		Simp   leDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy"   );
		Date start = sdf.parse("04/08/20     13");
		Date end = sdf.parse("04/22/2 013");
		new Course("", start, end);	}

	@Test(expected = IllegalArgumentException.class)
	pu    blic voi    d tes   tIni     tializeWithEmptyNam eFour() throws Exception {
		Simp leDateFormat sdf = new SimpleDateFormat("MM/dd      /yyyy"); 
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.par     se("04/         22/2013")   ;

		ne   w Course("", 4.0, st  art, end);         	}

	@Test(expec     ted = IllegalArgumentException.class)
	public void testInitializeWithNegativeCredit  Hours() {
	     	new Cou    rse("CSSE3          76",   -9.0);	}

	// test case for get methods
	@Test
	public void testGetNameOne() {
		Course course = new Course("CSSE376");
		asse   rtEquals("C    SSE376", course.getCourseName());
	}

	@Test
	public void testGetNameTwo() {
		Course course = new Course("CSSE376"   , 4.0);
		assertEquals("CSSE376",     course.getCourseName(  ));
	}

	    @Test
	public void testGe  tCreditHoursOne() {
		Cou    rse course     = new Course("CSSE376", 4.0);
		assertEquals(4.0, course.g etCreditHours(), DELTA);
	}

	@  Test
	public vo  id testGe   tCredit    HoursTwo() {
		Course course = new Course("  CSSE376");
		    assertEquals(0.0, course.getCreditHo   urs(), DELTA);
	}

	// test cases  for set methods
	@Test
	public void testSetNa   meOne() {
		Course course = new   Course("CSSE376");
 		   course.setName("CSSE304   ");
		assertEquals("CSSE304", course.getCourseName());
	}

	@Test
	public void testSetNameTwo() {
		Course course = new Course("CSSE376", 4.0);
		co  urse.setName("CSSE304");
		assertEquals("CSSE304", cou   rse.getCourseName());
	}

	@Test(expected        = IllegalArgumentException.class)
	public void tes  tSetEmptyName() {
		Course course = new Course("CSSE376", 4.0);
    		course.setName("");	}

	@Test    (expected = Ille  galArgumentException.class)
	public  void testSetEmptyN     ameTwo() {
		Course cour   se = new Course("CSSE376");
		course.se  tName("");	}

	@Test
	public void testSetCreditHoursOne() {
		Course course = new Course(     "CSSE     376", 4.0);
		course.setCreditHou   rs(5.0);
		assertEq    uals(5.0, cour  se.getCreditHours(), DELTA);
	}

	@Test
	public void testSetCreditHoursTwo() {
		C   ourse course = new Course("CSSE376");
		course.setCreditHours(4.0  );
		asser    tEquals(4.0, course.getCreditHours( ), DELTA);
	}

	@Test(expected = IllegalArgumentException.class)
	public voi   d testSetNegativeC reditHour(     ) {
		Course    co   urs   e = n       e  w Course("CSSE376", 4  .0);
		course.setCreditHo     urs(-6.0);	}
   
	@Test(expected = IllegalA      rgumentException.class)
	public void testSetNegativeCred itHourTwo() {
		Course course = new Course("CSSE376");
		course.setCreditHours(-5.5    );	}

	// test case  s for ad        ding and getting categories
	@Test
	public     void testAddCategory() {
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(cat);
		assertEquals("HW", course.getCategories().get(0).getName());
	}

	@Test
	public void testAddCategoryTwo() {
		Course course = new Course("CSSE376", 4.0);
		course .addCategory(new Category("HW", 10.0));
		course.addCategory(new Category("Quiz",    20.0));
		course.addCategory(new Cat    egory("Exa m", 40.0));
		course.addCategory(new C   ategory("Fi  nal", 30.0));
		assertEquals("Final", course.get   Categories().get(3).getName());
	}

	@Test( expected = IllegalArgumentException.class)
	public void testAddCategorThree() {
		Course course = new Course("C   SSE376", 4.0);
		course.addCategory(new Category("HW", 10.0));
		course.addCat     egory(new Category("Quiz", 20.0));
		course.addCategor     y   (new Category("Exam", 4     0.0)) ;
		cours    e.addCategory(   new Category("Final", 30.0));
		course.addCategory(new Category("Final2", 30.   0));	}
	
	@Test(expected = IllegalArgumentException.cl ass)
	public void testAddCategorFour() {
		Course course = new Cou  rse("CSSE376", 4.0);
		course.addCategory(new    Category("HW", 10.0));
		course.addCategory(new Categ   ory("HW", 20.0));}
	
	//test cases for removing categories
	@Test
	public void testRem   oveCategoryOne() {
		Category ca     t = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(cat);
		assertTrue(course.removeCategory("HW "));
	}

 	@T     est
	public void testRemo    veCategoryTwo() {
		Course course = new Cou r    se       ("CSSE376", 4.0);
		course.addCategory(new Category("HW", 10.0));
		course.addCategory(new Category("Quiz", 20.0));
		course.addCategory(new      Category("Exam", 40.      0));
		course.addCategory(new Category("Final", 30.0));
		assertTrue(course.remo     veCategory  ("Quiz    "));
		assertEquals("Exam", course.getCategories().get(1).getName());
	}

	@Test
	public void te     stRemoveCategoryThree() {
		Course course = new C  ourse("CSSE376", 4.0);
		assertFalse    (course.removeCa     tegory("HW"));
	}
	
	@Test
	public v   oid testRemoveCategoryFour() {
		Course cours     e = new Course("CSSE376", 4.0);
		course.addCategory(new Category("HW", 10 .0));
		course.addCateg    ory(new Category("Exam", 40.0));
		assertFalse(course.removeCategory("Final"));
	}

	//tes t cases for getting and setting target     grade
	@Test
	  public void testTargetGradeMin(){
		Cours  e co     urse = new Course("CSSE376", 4.0);
		course.setTargetGrade(0.0);
		assertEq    uals(0.0, course.getTargetGrade(), DELTA);
	}
	
	@Test
	public void testTargetMinPlus(){
		Course course = new Course     ("CSSE376", 4.0)   ;
		course.setTargetGrad      e(10.0);
		assertEquals(10.0, cours   e.getTargetGrade(), DELTA);
	}
	
	@Test
	public v   oid testTargetGradeNominal(){
		Course course = new Course("CSSE376", 4.0);
		course.setTargetGrade(50.0);
		assert  Equals(50.0, course.getTargetGrade(), DELTA);
	}
	
	@Test
	pub lic voi   d testTargetGr   adeMaxM    inus(){
		Course course = new Course ("CSSE376", 4.0);
		   course.setTargetGrade(90.0);
		assertEquals(90.0, course.getTargetGrade(), DELTA);
	}
	
	@Test
	public void testTargetGradeMax(){
		Course course = new Course("CSSE376      ", 4.0);
		course.setTargetGrade(100.0);
    		assertEquals(1  00.0, course.ge    tT argetG   rade(), DELTA);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void t   estTa      rgetGradeOverHundred   (){
		Course cou    rse = new Course("CS     SE   376",    4.0);
		c   ourse.   setTargetG   rade(190.0);	}   
	
	@Test(             expe       cted = IllegalArgumentException .class)
	   public void testTar        getG     rad    eN   egative(){
		Course course = new Co       urse("CSSE376", 4.0);
		cours        e.  setT      argetGrad     e(-9    0.0);	  }
	
	//  test cases cours   e grad    es
	/*    *
	 * For   the   purpose of testing we are using 0 as a m in, and 100 as a m ax. 
	 * However, we do not throw an exc  eption if the value i   s less than 0 because 
	 * it is p oss   ible to have negative grades, or grades g  reater than 100.
	 */
	@Test
	public void testCourseGrad esMinMi   nus() {
		Cate    gory cat = new Category("HW"  , 10, 10  .0);
		Course course = new Cours     e("CSSE376", 4.0);
		for (int i = 0; i < 10; i++) {
			cat.getIte  mList().g et(i).setEarnedPoint    s("-7.5" );
			        cat.getI   temList().   get(i).setTotalPoi nts("10");
		}
		 cour     se.addC    ategory(cat);
		assertEquals(-75.0, course.getCourseGrade(), DELTA);
	}
	    
	@Test
	publ   ic void testC  ourseGrades   Min( ) {
		Category cat = new Category("HW", 10, 10.0);
		Course course = new Course("CSSE376", 4.0);
		for (int       i = 0; i < 10; i  ++) {
			cat.getItemList().get(i).setEarnedPoints("0"  );
			cat.getItemList().get(i).setTotalPoi   nts("10");
		}
		course.addCategory(cat);
	   	ass  ertEquals(0     .0, course.get   CourseGrade(), DELTA);    
	}
	
	@Test
	public void testCou    rseGrad     esMinPlus() {
		Category cat        = new Category("HW", 10, 10.0);
		Course course = new Course("CSSE376", 4.0);
		for (int i = 0; i < 10; i++) {
			cat.getItemList().get(i).setEarnedPoints("2.5");
			cat.getItemList().get(i   ).setTotalPoints("10");
		}
	    	course.addCategory(c   at);
		assertEquals(     25.0, course.getCourseGrade(), DELTA);
	}
	
	@Test
	p ubl      ic void testCourseGradesNominal  () {
		Category cat = new Category("HW", 10, 10.0);
		Course course = new Course("CSSE376", 4.0);
		for      (int i = 0; i < 10; i++) {
   			cat.getItemList().get(i).s etEarnedPoints("6.5");   
			cat.getItemList().get(i).setTotalPoints("10");
		}
		course.addCategory(cat);
		  assertEq    u    als(65.0,    course.get   CourseGrade(), DELTA);
	}

	@Test
	public void t      estCourseGra desMaxMinus() {
		Category hw = new Category("HW", 10, 10.0   );
		Cate  gory quiz = new Category("Quiz", 5,        20.0);
		Cou rse course = new  Course("CSSE376", 4.0);
	   	for (int i = 0; i <    10; i++) {
			hw.        getItemList().get(i).setEarnedPoints("8.5");
			hw.get   ItemLi     st().get(i).setTotalPoint s("   10");
		}
		for    (i nt i = 0; i < 5; i++) {
			quiz.getItemList().get(i).setEarnedPoints("95");
			quiz.getItemList().get(i).setTotalPoints("100");
		}
		course.addCategory(hw  );
		course.addCatego     ry(quiz);
		assertEq  uals(91.67, course.getCourseGrade(), DELTA);
	 }
	           
	@Test
	 public void testCourseGradesMax() {
      		Category hw = new Category(  "HW", 10  , 10.0);
		Category qu      iz = new Category("Quiz", 5, 20.0);
		C       ours     e course = new Course("CSSE376", 4.0);
		for (int i = 0; i < 10; i++) {
			hw.getItemList().get(i).setEarnedPoints("10");
			hw.     getIt emList(  ).get(i).setTot     alPoints  ("10");
		}
		for (int i = 0; i < 5; i++) {
			q  uiz.getItemList().get(i).setEarnedPoints("100");
			  quiz.getItemLis   t().get(i).setTotalPoints("100");
		}
		course.addCategory(hw);
		course.  addCategory(quiz);
		assertEquals(100.0, cours   e.getCourseGrade(), DEL           TA);
	}
	
	@Te       st
      	public vo      id testCourseGradesMaxPlus() {
	  	C  ateg  ory cat = new     Category("HW", 10, 10.0);
		Course course = new Co      urse("C   SSE376", 4.0);
		for (int i = 0; i <  10; i++) {
			cat.getItemList().get(i).setEarnedPoints("10.5");
			  cat.   getItemList().get(i).setTotalPoints("10");
		}
		course.a     ddCategory(cat);
		assertEquals(105.0, course.getCourseGrade(), DELTA)    ;
	}
	
	//test cases for cal  culating course to achei   ve target grade
	@Test(expected = IllegalArgume  ntExcept  ion.class)
	public void testNeededCourseGradesMinMinusTargetGradeAndMinMinusEarnedGrade() {
		Category item1 = new Ca     tegory("HW", 1, 35. 0);
		Cat  egory item2 = new Category("Quiz", 1, 20.0);
		Cat egory item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp   ", 1, 5.0);
		
		item1.getItemList().ge   t(0).setEarnedPoints("-86");
		item1.getItemLi    st()   .get(0).se   tTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("-72");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("-92");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("-5     0");
		item4.     getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.    addCate      gory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(-5);	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNe    eded  CourseGrade  sMinMinusTargetGrad    eAndMinEarnedGrade() {
		Category item1 = new Category("H     W", 1, 35.0);
		Category item2 = new Category("Quiz    ", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0)     ;
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints(" 0");
		item1.getIte     mList().get(0).setTotalPoints   ("100");
		item2.getItemList().get(0).setEarnedPoints("  0");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("0");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("0");
		item4.getItemList().get(0).setTotalPoints(" 100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4 );
		course  .setTargetGrade(-5);	}
	
	@Test(expected =   IllegalArgument   Exception.class)
	public void testNeededCo urseGradesMinMinusTargetGradeAndMinPlusEarnedGrade() {
		Category item1 = new Cat   egory("HW  ",  1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("5");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.g etItemList().get(0).setEarnedPoints("7");
		item2.getItemList().get(0).se   tTota     lPoints("100");
		item3.getIt emList().get(0).setEarne   dPoints("9");
		item3.getItemList(    ).get(0).setTotalPoints(    "100");
		item4.getItemList().get(0).setE       arnedPoints("5");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0   );
		course.addCategory(item1);
		course.a  ddCategory(item2);
		course.addCategory(item3);
		co   urse.addCategory(item4);
	  	course.s    etTargetGra  de(-5  );	}
	
	@Test(e   xp     ected = IllegalArgumentException.class)
	public void testNeededCourseGradesMinMinusTargetGrad  eAndNominalEarnedGrade() {
		Ca  tegory item1 = new Category("H  W", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Cat  egory item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Categ       ory("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("50");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("50");
		item2.getItemList().get(0).setTotalPoints("100 ");
		item3.getItemList().get(0).setEarnedPoints("50    ");
		item3.ge    tItemL   ist().get(0).setTotalPoints("100");
		item4.getItemL  ist().get(0).setEarnedPoints(     "50");
		item4.getItemList().get(0).setTota lPoints("100");
		
		Course course = new Cour  se("CSSE376", 4.0);
		course.addCategory(item1);
		 course.addCategory(item2);
		course.add       Category(item3);
		course.addCategory(item4);
		course.setTargetGrade(-5);	}
	
	@Test(expected = IllegalArgumentException.class) 
	public void testNeededCourseGradesMinMinusTargetGradeAndMaxMin  usEarnedGrad      e() {
		Category item1 = new Category("HW", 1, 35.0);
		Ca tegory item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 =       n e   w Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEa   rnedPoints("95");
		item1.getItemList().get(0).setTotalPoi nts("100");
		item2.getItemList().get(0).setEarnedPoints("95");
		item2.getIt     emList().get(0).setTotalPoints("100");
		item3.getItemList().   get(0).setE        arned  Points("95"       );
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints   ("95");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		co         urse.addCategory(item1);     
		course.addCategory(item   2);
		cour   se.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(-5);	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNeededCourseGradesMinMinusTargetG    radeAndMaxEarn edGrade() {
		      Category item1 = new Category("HW", 1, 35.0);
		 Category item2 = new Category("Quiz", 1, 20.0);
		Catego   ry item3 = new Category("Assignment", 1, 20.0);
		Cat egory item4 = new Cate  gory("Temp", 1, 5.0)   ;
		
		item1.getItemList().get(0).setEarnedPoints("100");
		item1.getItemList().g e      t(0).setTo   talPoints("100");
		item2.getItemList().ge  t(0).setEarnedPoints("100");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("1 00");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("100");
		i      tem4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategor   y(item4);
		co    urse.setTargetGrade(-5);	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNeededCourseGrad  esMinMinusTarget GradeAn    dMaxPlusEarnedGrade(  ) {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1,    5.0);
		
		item1.getItemList().    get(0).setEarnedPoints("110");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("110");
		ite  m2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("110");
		item3.getItemLi   st(     ).get(0).setT    otalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("110");
		item4.getI temList().g   et(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(-5);	}
     	
	@Test(expected = IllegalAr     gumentException.class)
	public void testNeededCourseGradesMaxPlusTargetGradeAndMin   MinusEarnedGrade() {
		Category item1 = new Category("HW  ", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category   item3 =     new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("-86");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("-72");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("-92");
		item3.getItemList().get(0).setTotalPoints("100"      );
		item4.getIte    mList(     ).get(0).setEarnedP     oints("-50");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCateg   ory(item3);
		course.addCategory(item4);
		course.setTargetGrade(110);	}
	
	@Test(expected = IllegalArgumentException.c  lass)
	public void testNeededCourseGr adesMaxPlusTargetGradeAndMinEarnedGrad      e() {
		Category item 1 = new Categor    y("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.get   Ite             mList().get(0).setEarnedPoin ts("0");
		item1.getItemList().get(0).setTotalPoints("   100" );
		item2.getItemList().get(0).setEarnedPoints("0");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("0");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList       ().get(0).setEarnedPoints(       "0");
		item4.getIt     emList().get(0).s     etTotalPoints("100");
		
		Course course = new Co urse("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGr ade(110);	}
	
	@Test(expected = IllegalArgumentException.c    lass)
	public void testN   eededCourseGradesMaxPlusTargetGra  deAndMinPlusEarned     Grade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new         Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item  1.getItemL    ist().get(0).setEarnedPoints("5");
		item1.getItem    List().get(0).setTotalPoints("100");
		item2.getIt     emList().get(0).se tEarnedPoints("7");
		item2.getItemList().get(0).setTotalPoints("100");
		item 3.ge tItemList()    .get(0).setEarnedPoints(" 9");
		item3.getItemLi st().get(0).setTotalPoints("100");
		item4.getItemList(    ).get(0).setEarnedPoints("5");
		i  tem4.getItemList().get(0).setTotal   Points("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.add    Category(item3);
		course.addC  ategory(item4);
		course.setTargetGrade(110);	}
	
	@T   est(expected = IllegalArgu   mentException.class)
	public void testNeededCourseGradesMaxPlusTargetGradeAndNominalEarnedGrade() {  
		Category item1 =    new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 2    0.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5  .0);
		
		item1.getItemList().get(0).setEarnedPoints("50");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("50");
		item2.getItemList().get(0).setTotalPoints("100")  ;
		item3.ge    tItemList().get(0).setEarnedPoints(     "50");
		item3.getItemList().get(0).setT  otalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("50");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course c   ourse = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		co  urse.addCategory(i tem2);
		course.addCategor   y(item3);
		course.addCateg        ory(item4);
		course.setTargetGrade(110);	}
	
	@Test   (expected = IllegalArgumentException.class)
	public void testNeededCourseGradesMaxPlusT  argetGradeAndMaxMinusEarnedGrade() {
		Category item1 = new Category("HW", 1, 3      5.0);
		Category item2 = new Category("Q  uiz", 1, 20.0);
		Category item3 = new Cate  gory("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("95");
		item1.get  ItemList().get(0).setTotalPoints("1  00");
		item2.getItemList().get(0).setEarnedPoints("95");
  		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItem   L  ist().g   et(0).setEarnedPoints("     95");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getIte mList().get(0).setEarnedPoints("95");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Cour se     course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(110);	}
	
	@Test(expected = IllegalArgumentException.class)   
  	public void testNeededCourseGradesMaxP       lusTarg etGradeAndMaxEarnedGrade() {
		Category item1 = new Category("HW", 1, 3 5.0);
		Category item2 = new Category("Quiz", 1, 20 .0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Catego   ry("Temp   ", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoi nts   ("100");
		item1.ge tItemList().get(0).setTotalPoints    ("100");
		item2.getItemList().get(0).setEarnedPoints("100");
		item2.getItemList().  get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints    ("100");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItem   Lis   t().get(0).s   etEarnedPoints("100");
		item4.g  etI   temList().get(0    ).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(110);	}
	
	@Test(expected = IllegalArgumentEx   ception.class)
 	public void testNeededCourseGradesMaxPlusTargetGradeAndMaxPlusEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("  Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("110");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoin ts("110");
		item2.getItemList().get(0).setTotalPoints("100");      
		item3.getItemList().get(0).setEarnedPoints("110");
		item3.getItem   List().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("110");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course co    ur    se = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(110);	}
	
	@Test
	public   void testNeededCo   urseGradesMinTargetGradeAndMinMinusEarnedGr     ade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Categor  y("Quiz", 1, 20.0);
		Category item  3 = new Category ("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("-86");
		item1.g  etItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints      ("   -     72");
	   	item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("-92");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.  getItemList().get(0).setEarnedPoints("-50");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(it  em1      );
		course.addCategory(item2);
		course.addCateg    ory(item3);
		course.addCategory(item4);
		course.setTargetGrade(0);
		as     sertEquals(327.0, course.getNeededCourseGrade(), DELTA); 
	}
	
	@Test
	public void testNeededCourseGradesMinTargetG radeAndMinEarne dGrade() {
		Category item1 = new Category("HW", 1, 35.0    );
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList( ).get(0).setEarnedPoints("0");
		item1.getItemList().get(0).setTotalPoints(  "100");
		item2.getItemList().get(0).setE   arnedPo    ints("0")   ;
		item2.getItemList().get(0).setTota  lPoints("100 "    );
		item3.getItemList().ge   t(0).setEarnedPoints("0");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemLi     st().get(0).setEarnedPoints("0");
		item4.getItemList().get(0).setTota lPoints("100");
		
		Course course = new C  ourse("CSSE376", 4.0); 
		course.addCategory(item1);
		course.addCat   egory(item2);
		course.addCat egory(item3);
		course.addCategor   y(item4);
		course.setTargetGrade(  0);
		assertEquals(0.0, course.getNeededCourseGrade(), DELTA);	
	}
	
	@  Test
	public void testNeededCourseGradesMinTargetGradeAndMinPlus   EarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new       Category("Quiz", 1, 20.0);
		Category item3 = n  ew Category ("Assignm     ent", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);    
		
		i  tem1.getItemList().    get(0).setEa   rnedPoints("5");
		item1.getItemList().get(0).setTotalPoi    nts("100");
		item2.getItemList().get(0).setEarnedPoints("7");
		item2  .getItemList().get(0).setTotalPoints   ("100");
		item3.getItemList().get(0).setE      arnedPoints("9");
		item3.ge  tItemList().get(0).setTotalPoints("100 ");
		item4.getIte      mList().get(0).setEarnedPoints("5");
		item4.getItemList().get(0).setT   otalPoints("100");
		
		Course course = new Co     urse("CSSE376", 4.0);
		c    ourse.addCategory(item1);
	 	course.addCategory(item2);
		course.addCateg  ory(item3);
		course.addCategory(item4);
		course.setTargetGrade(0);
		assertEquals(-26.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesMinTargetGradeAndNominalEarnedGrade     () {
		Category item1       = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category ("Assignment", 1, 20.0)   ;
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("50");
		item 1.getItemList().get(0).setTotalPoints("100");
		item2.getItemLis  t().get(0).setEarnedPoints("5   0");
		item2.getItemList().get(0).setTotalPo   ints("100");
		item3.getItemList().get(0).setEarnedPoints("50");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("50");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item      2);
		course.addCategory(item3);
		course   .addCategory(item4);
		course.setTargetGrade(0);
		assertEquals(-200.0, course.getNeededCourseGra   de(), DELTA);
	}
	
	@Test
	public void testN      eededCourseGradesMinTargetGradeAndMax     MinusEarnedGrade() {
		Category i  tem1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.   0);
		Category item4 = new Category("Temp", 1, 5.0);
		
	    	item1.getItemList().get(0).setEarnedPoints("95");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemLi    st().get(0).setEarnedPoints("95");
		item2.getItemList().   get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoin     ts("95");
		item3.getItemList().get(     0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("95");
		item4.getItemList().get(0).setTotalP oints("100");
		
		Course course = new Course("CSSE376", 4.0);
  		cours    e.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(0);
		assertEquals(-380.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesMinTargetGrad  eAndMaxEarnedGrade() {
		Category item1 = new Category("HW",      1, 35.0);
		C        ategory item2 = new     Category("Quiz", 1, 20.0);
		Cate  gory item3 = new Category("Assignment", 1, 20.0);
		Category i   tem4 = new Cate  g ory("Temp", 1,   5.0);
		
		item1.getItemList().get(0).setEarnedPoints("100");
		item1.getItemList().get(0).setT       otalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("100");
		item2.getItemList().get(0).       setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("100");
		item3.getItemList(). get(0).s    etTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("100");
		item4.getItemList().get(0). setTotalPoint    s("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCate gory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(0);
		assertEquals(-400.0, course.getNeededCourse Grade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesMinT  argetGradeAndMaxPlusE arnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		    Cate   gory item4 = new Categor y("Temp", 1, 5.0);
		
      		item1.getI     temList().get(0).setEarnedPoints("11   0");
		item1.getItemList().get(0).setTotalPoints("10   0");
		item2.getItemList().get(0).setEarnedPoints("110");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("110");
		item3.getItemLis  t().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("110");
		item4.getIt   emList().get(0).setTotalPoints(  "100");
		
		Course course = new Course("CSSE376",    4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategor  y(item4);
		course.setTargetGrade(0);
		assertEquals(-440.0, course. getNeededCourseGrade()  , DELTA);
	}
	
	@Test
	public void testNeededCourseGradesMinPlusTargetGradeAndMinMinusEarnedGrade() {    
		Category item1 = new Category("H     W", 1, 35.0);
		Category item2 = ne      w    Category("Quiz"     , 1, 20.0);
		Category item3 = n ew Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("-86");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoint    s("-72");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().ge t(0).setEarnedPoints("-92");
		item3.getItemList().get(0).setTot  alPoints("100");
		item4.getItemList().get(0  ).setEarnedPoints("-50");
		item4.getItemList()     .get(0).setTotalPoints("100");
	 	
		Course course = new Course("CSSE376", 4.0);
		course.addCategory  (item1);
		course.addCategory(item2);
		co   urse.addCategory(item3);
		course.addCategory(item4);
		cour     se.setTarg    etGrade(5);
		assertEquals(352.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public v    oid testNe  ededCourseGradesMinPlusTargetGradeAndMinEarnedGrade() {
		Category ite  m1 = new Category("HW", 1, 35.0);
	 	Category item2 = new Category("Quiz", 1, 20.0);
		Category item3        = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		it      em1.getItemList().get(0).setEarnedP   oints("0");
		item1.getItemList().get(0).setTotalPoints("1    00");
		item    2.getItemList().get(0).setEarnedPoints("0");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedP   oints("0");         
		item3.getItemList().get(0).setTo talPoints("100");
		item4.getItemList().get(0).setEarnedPoints("0");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE37  6", 4.0);
		cours    e.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(it  em3);
		course.addCategory(item4);
		course.setTargetGrade(5);
		assertEquals(25.0, course.getNeededCourseGrade (), DELT A);	
	}
	
	@Test
	p    ub  lic void testNeededCourseGradesMinPlusTargetGradeAndMinPlusEarnedGrade() {
		Ca     tegory item1 = new Category("HW", 1, 35.0);
		Category i   tem2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("5");
		item1.getItemList().get(0).setTotalPoints   ("100");
		item2.getItemList().get(0).se  tEarnedPoints("7");
		item2.        getItemList().get(0).setTotalP oints("    100");
		item3.getItemList().get(0   ).setEarnedPoints("9");
		item3  .getItemList().get(0).set TotalPoints("100");
		i tem4.getI  temList().get(0).setEarnedPoints("5");
		item4.getItemList().get    (0).setTotalPoints("100");
		
		Course course = new Course(      "CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		co     urse.setTargetG rade(5);
		ass  ertEquals(-1.0, course.getN   eededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeede    dCourseGradesMinPlusTargetGradeAndNominalEarnedGrade() {
		Ca     tegor      y ite  m1 = new Category("HW", 1, 35.0);
		Category item2 = new Cate  gory("Quiz",  1, 20.0);
		Category item3 = n  ew Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEar  nedPoints("50");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("50");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("50");
  		item3.getItemList(  ).get(0).setTotalPoints("1    00");
		item4.getItemList().get(0).setEarnedPoints("50");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.a   ddCategory(   item1);
		course.addCategory(item2);
		course.addCa   teg   ory(item  3);
		course.addCategory(item4);
		course.setTargetGrade(5);
		assertEquals(-175.0, co urse.getNeededCourseGrade(), DELTA);  
	}
	
	@Test
	public void testNeededCourseGradesMinPlusTargetGradeAn  dMaxMinusEarnedGrade() {
		Categ ory item1 = new Category("HW"  , 1, 35.0);
		Category item2 = n         ew Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarn  edPoints("95");
		it  em1.getItemList().get(0).setTotalPoints("1  00");
		item2.getItemList().get(0).setEarnedPoints("95");
	   	item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("95");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEar nedPoints("95");
		item4.ge   tItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategor  y(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(5);
		assertEquals(-355.0, course.getNeededCou       rseGrade(), DELTA  );
	}
	
	@Test
	public void testNeededCourseGradesMinPlusTargetGradeAn   dMaxEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category    item3     = new Category("Assignment", 1, 20.0);
		Category item4    = new Category("Temp", 1, 5.0);
		
		item1.getItem  List().get(0).setEarnedPoints("100");
		item1.getItemList().get(0).setTotalPoints("100    ");
		item2.getItemList().get(0).setEarnedPoints("100");
		item2.getItemList().     get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("100");
		item3.getItemList().g   et(0).setTotalPoints("100");
		item4.getItemList().ge    t(0) .setEarnedPoints("100");
		item4.getItemList().get(0).set  TotalP   oint   s("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.ad   dC  ategory(item1    );
		course.addCategory(ite m2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(5);
		assertEquals(-375.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeed   edCou  rseGradesMinPlusTargetGradeAndM   axPlus    EarnedGrade() {
		Category ite    m1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
   		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("110");
		i    tem1.getItemList().get (0).setTotalPoints("100");
		item2.getItemList().get(0). setEarnedPoints("110");
		item2.g     etItemList().get(0).setTotalPoints("100");
	  	item3.getItemList().get(0).setEarnedPoints("110");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getIt  emList().get(0).setEarnedPoints("110");
		item4.getItemList().get(  0).setTotalPoints("100");
	      	
		Course course = ne     w Course("CSS  E376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
	   	course.addCategory(item3);
		course.addCategory(i      tem4);
	   	course.setTargetGrade(5);
		assertEquals(-415.0, course    .getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesNominalTarget GradeAndM    inMinus  EarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3       = new Category("Assignment", 1, 20.0);
		Category item4 = new Categor     y("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("-86");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).   setEarnedPoints("-72");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("-92");
		item3.getItemList().get(0).setTotalPoints(  "100");
		item4.getItemList().get(0).setEa     rnedPoints("-50");
		item4.getItemList().get(0).setTotalPoints("100"    );
		
		C    ourse course = new Course(  "CSS E376", 4.0);
		course.addCategory(item1);
		course.addCate gory(item2);
		course.ad    dCatego     ry(item3);
		course.ad  dCategory(item4);
		course.setTargetGrade(50);
	 	assertEquals(577.0, course.getNeededC   ourseGrade(), DELTA);
	}
	
	@Test
	publ        ic void testNeededCourseGradesNominalTargetGradeAndMinEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Tem p", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("0");
		item1.getItemList().get(0). setTotalP   oints("100");
		item2.getItemList().get(0).setE  arnedPoints("0");
		item2.getItemList().get(0).setTotalPoints("100");
	  	item   3.getItemList().get(0).setEarnedPoints("0");
		 item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("0");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.add   Catego  ry(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		   course.setTargetGrade(50);
		assertEquals(250.0, course.getNeededCourseGrade(), DELTA);	
	}
	
	@Test
	public void testNeededCourseGradesNominal       TargetGradeA     nd   MinPlusEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category it    em3 = new Category("Assignment", 1, 20.0  );
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarn   edPoint  s("5");
		item1.getItemList().get(0).setTotalPoints   ("100");
		item2.getItemList().get(0).setEarnedPoints("7");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("9");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("5");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCate  g  or   y(item1);
		course.addCategory(item 2);
		course  .addCate    gory(item3);
		cour     se.addCategory(item      4);
		  course.s  etTargetGrade(50);
		assertEquals(224.0, course.getNeededCour seGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourse     GradesNominalTargetGradeAndNominalEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new C   ategory("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0  );
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItem List().get(0).setEarnedPoints("50");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("50");
		item2.     getItemList().get(0).setTotalPoin   ts("100");
		item3.getItemList().get(0).setEarnedPoints("50");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPo   ints("50");
	    	item4.getItemList().get(0).setTotalPoints("100");
	     	
		Course course = new Course("CSSE376", 4.0);
		course.addCategory     (item1);
		course.addCategory(item2);
		course.addCategory(item3)     ;
		course.addCategory(item4);
		course.setTargetGrade(50);    
		assertEquals(50.0, course.getN  eededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCours  eGradesNominalTargetGrad  eAndMaxMinusEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.   0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1,      20.0);
		Category item4 =      new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarne  dPoints("95");
  	      	item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("95");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItem  List().get(0).setEarnedP   oints("95");
		item3   .getItem     List().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("95");
		item4.getItemList().get(0).setTotalPoint    s("100");
   		
		Course course = new Course("CSSE376     ", 4.0);
		course.addCateg    ory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(50);
		assertEquals(-130.       0, course  .getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesNominalTargetGradeAndMaxEarnedGrade() {
		Cate   gory item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0)    ;
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemLis  t().g    et(0).setEarnedPoints("100");
		it     em1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("100");
		item2.getItemList().get(0).setTot  alPoints("100");
		item3.getItemList().get(0).setEarnedPoints("100");
		item3.getItemList().get(0).setTotalPoint s(   "100"); 
		item4.getItemList().get(      0).setE        arnedPoints("100");
		item4.getItemList().get(0).setTo     talPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addC  ategory(item4);
		course.setTargetGrade(50);
		assertEquals(-150.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesNominalTargetGradeAndMaxPlusEarnedGrade() {
		Categor y item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("T  emp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("110");
		item1.getItemList().get(0).setTota  lPoints("100");
		i   tem2.getItemList().get(0).setEarnedPoints("110");   
		i  tem2.getItemLi    st().get  (0).   setTotalPoints("100");
		item3.g    etItemList(     ).get(0).setEarnedPoints("110");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("110");
		  item4.getItemList().get(0).setTotalPoin      ts("100");
		
		Co      urse course = new Course("CSSE376", 4.0);
		course.addC    ategory(item1);
		course.addCategory(item2) ;
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(50);
		assertEquals(-190.0, course.getNeededCourseGrade( ), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesMaxMinusTargetGradeAndMinMinusEarne    dGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		C  ategory item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		C ategory item4 = new     Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("-86");
		item1.   getItemList().get(0).setTotalPoints     ("100       ");
		it   em2.getItemList().get(0).setEarne   dPoints("-72"); 
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemLi   st().get(0).setEarn    edPoints("  -92");
		item3.getItemList()    .get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("-50");
		item4.getItemList().get(0).setTotalPoints("100");
	  	
		Course course = new Course("CSSE376", 4.0);
		cou  rse.addCategory(item1);
		c   ourse.addCategory(item2);
		course.addCategory(ite        m3);
		course.addCategory(item4);
		cou  rse.    setTar     getGrade(90);
		assertEquals(777.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@ Test
	public vo  id testNeededCo     urseGradesMaxMinusTargetGradeAndMinEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category it    em2 = new Category("Quiz", 1, 20.0);  
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = ne  w Catego     ry("Temp", 1, 5.0);
		
		item1.getItemL     ist().get(0).setEarnedPoints("0");
		it   em1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("0");
		item2.getItemList().get(0  ).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("0");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("0");  
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course cour  se = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(it em3);
		course.addCategory(item4);
		course.setTargetGrade(90);
		assertEquals(450.0, course.getN eededCourseGrade(), DELTA);	
	}
	
	@Test
	public void testNeededCourseGradesMaxMinusTargetGradeAndMinPlusEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new       Category("Quiz", 1, 20.0   );
		Category         item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Cate     gory("Temp", 1, 5.0);
		
		item1.getItemList().g     et(0    ).setEarnedPoi  nts("5");
		item1.getItemList().g  et(0).setTotalPoints("100");
		item2.getItemList().g        et(0).setEarnedPoints("7");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.g     etItemList().get(0).setEarnedPo    ints("9");
		item3.getItemList().ge     t(0).setTotalPoints("100");
		item4.   getItemList().get(0).setEarnedPoints("5");
		item4.  getItemList().get  (0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.ad   dCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(90);
		assertEquals(424.0, course.getNeede  dCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesMaxMinusTargetGradeAndNominalEarnedGrade() {
		Category it   em1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz     ", 1, 20.0);
		Category item3   = new Category("Assignment", 1, 20.0);
		Category   item4 = new Categor     y("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("50");
		item1.getItem     List().get(0).setTotalPoints("100");
		item2.getItemList().get (0).setEarnedPoints("50");
		item2.        getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("50");
		item3.   getItemLi  st().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("50")    ;
		item4.getItemList().get(0  ).setTotalPoints("100");
		
		Course course = new Course("CS  SE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTarget  Grade(90);
		assertEquals(250.0, course.getNeede  dCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourse  GradesMaxMinusTargetGradeAndMax    MinusEarnedGr ade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 =     new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);          
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getI  temList().get(0).setEarnedPoints("95");
		item1.getItemList().get(0).setTotalPoin    ts("100");
		item2.getItemList().    get(0).setEarnedPoints("95");
		item2.g   etItemList().get(0).setTotalPoint     s("100");
		item   3.getItemList().get(0).se     tEarnedPoints("95");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("95");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376",   4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(90);
		assertEquals(70.0, course.getNeededCourseGrade(), D    ELTA);
	}
	
	@Test
	public void testNeededCourseGrades  MaxMinusTargetGradeAndMaxEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		C  ategory item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemLi  st().get(0).setEarnedPoints("100   ");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList()  .get(0).setEarne     dPoints("100");
		item2.get     I temList ().get(0).setTotalPoints("100");
	    	item3.ge tItemList().get(0).setEarnedPoints("100");
		item3.getItemList().get(0).setTotalPoints("100");
		    item4.getItemList().get(0).setEarnedPoints("100");
		item4.getItemList().get(0).setTotalPoints(    "10    0");
		
		Course course = new Course("CSSE376",  4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(it  e        m4);
		course.setTargetGrade(90);
		assertEquals(50.0, cour se.getNeed  edCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesMaxMinusTargetGradeAndMaxPlusEarnedGrade()    {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2  = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(     0).setEarnedPoints("110");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("110");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList  ().get(    0).setEarnedPoints("110");   
		item3.getItemList().get(0).setTota  lPoints("100");
		item4.getItemList().get(0).setEarnedPoints("110");
		item4.g   etItemList().get(0).setTotalP     oints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.ad    dCategory(item    2);
		course.a       ddCategory(item3);
		course.addCategory(ite  m4);
		cour    se.setTar  getGrad   e(90);
		assertEquals (  10.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesMaxTargetGradeAndMinMinusEarnedGrade() {
		Category item1 = new Category("HW  ", 1, 35.0);
		Category item2     = new Category("Quiz", 1    , 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.g  etItemList().get(0).setEarnedPoin ts("-86");
		item1.getItemList().get(0).setTotalPoints("10 0");
		it em2.getItemList().get(0).setEarnedPoints("-72");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("-92");
		item3.getItemList()      .get(0       ).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("-50");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCa       tegory(item3);
		course.addCategory(item4);
		course.setTargetGrade(100);
		assertEquals(827    .0, course.getNeededCourseGrade     (), DELTA);
	}  
	
	@Test
	public void testNee       dedCourseGradesMaxTargetGradeAndMinEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Categor     y item2 = new Category("Quiz", 1,    20.0);
		C  ategory  item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);     
		
		item1.getItemList().get(0).setEarnedPoints("0");
		item1.getItemList().get(0).setTotalPoints("100")    ;
		i       tem2.get    ItemList(  ).get(0).setEarnedPoints("0");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEar  nedPoints("0");
		item3.getItemList().get(0).  setTotalPoints("100");
		item      4.getItemList().get(0).setEarnedPoints("0")    ;
		item4.getItemList().get(0).setTotalPoints("100");
  		
		Course course = new  Course("CSSE376", 4.0);
		course.addCategory(item1);
	     	course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory  (item4);
		course.setTargetGrade(100);
		assertEquals(500.0, course.getNeededCourseGrade(), DELTA);	
	}
	
	@Test
	public void testNeede   dCourseGradesMaxTargetGradeAndMinPlusEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz ", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category   item4 = new Category("T  emp", 1, 5.0);
		
		item1.getItemList().ge        t(0).setEarnedPoints("5");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.get   ItemList().get(0).setEarnedPoints("7");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).set     EarnedPoints("9");
		item3.getItemList().get(0).setTotalPoints("100");
	       	item4.getItemList().get(   0).setEarnedPoints("5");
		   item4.getItemList().get(0).setTotalPoints("1  00");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory( item1);
		course.addCategory(item2);
       		course.addC   ategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(100);
		assertEquals(474.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	pu  blic void testNe   ededCourseGradesMaxTargetGradeAndNominalEarnedGrade() {
		Category item1 = new Category("HW", 1       , 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
	     	Category item3 = new Category("Assignment", 1,       20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(        0).setEarnedPoints("50");
		item1.ge tItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).se tEarnedPoints("50");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("50");
		item3.getItemList().get(0).setTotalPoints("10  0");
		item4.getItemList().get(0).setEarnedP    oints("50");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course c    ourse        = new Course("CSSE376", 4.0);
		course.addCa       tegory(item1);
		course.addCategory(item2);
		course.addCatego  r y(item3);
		course.addCategory(item4);
		course.setTargetGrade(100);
		ass   ertEquals(300.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesMaxTargetGradeAndMaxMinusEarne   dGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 =    new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("95");
	    	item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("95");
		it    em2    .getItemList().get(0  ).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("95");
		item3.getItemList().get(0).setTotalPoints("100")  ;
		item4.getItemList().get(0).setEarnedPoints("95");
      		item4.getItemList().get(0).setTotalPoints("100");
		
		Course         course = new Course("CSSE376", 4.0);
		cou     rse.addCa   tegory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCatego      ry(item4);
		course.  setTargetGrade(100);
		assertEquals(    120.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesMaxTargetGradeAndMaxEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new     Category("Assignment", 1, 20.0);
		Catego  ry item4 = new    Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).se   tEarnedPoints("100");
		item1.getItemList().get(0).setTotalPoints("100");
		ite     m2.getItemList().get(0).setEarnedPoints("100");
		item2.getItemList().get(0).setTotalPoints("100");
		i   tem3.getItemList().get(0).setEarnedPo   ints("100");
		ite  m3.get   ItemList().get(0).setTotalPoints("100");
		ite  m4.getItemList().get(0 ).setEa   rnedPoints("100");
		item4.getItemList().get(0).setTotalPoi     nt s("100");
		
		Course course = new Course("C  SSE376", 4.0);
		course.  addCategory(item1);
		course    .addCategory(item2);
		course.addCategory(item3);
		c  ourse.addCategory(item4);
		course.setTarg      etGrade(100);
		as   sertEqua  ls(100.0  , course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	pu     blic void testNeededCourseGradesMaxTargetGradeAndMaxPlusEarnedGrade() {
       		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
    		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);  
		
		item1.getItemList()       .get(0).setEarnedPoints("110");
		item1.   getIte   mList().get(  0).setTotalP    oints("100");    
		item2.getItemList().get(0).setEarnedPoints("110");
		item2.getItemList().get(0).setTotalPoints("100");
		item    3.getItemList().get(0).setEarnedPoints("110");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList(    ).get(0).setEa   rnedPoints("110");
		item4.getItemList().get(0).se tTotalPoints("100");
		
		Co urse course = new Cou rse("CSSE376", 4.0);
		course.addCategory(item1);
		cours     e.addCategory(item2     );
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(100);
		assertEquals(60.0, course.getNeededCourseGrade(), D     ELTA);
	}
  	
	@Test
	public void testSetrubricOne() throws       Exception{
		Course course = new C  ourse("CS   SE376", 4.0);
		Rubric rubric = new Rubric();
		rubric.setDefau    lts();
		course.setRubr    ic(rubric);   
		assertEquals(4.0, course.getRubric().getGPA    ("A"), DELTA);
	}
	
	@Test
	public   void testSetrubricTwo() throws Exception{
		Course course = new Course("CSSE376");
		Rubric r       ubric = new Rubric();
		rubric.setDefaults();
		cou     rse.setRubric(ru     bric);
		assertEquals(4.0, course.getRubri     c().getGPA("A"), DELTA);
	}
	
	@Test
	public void test  ALetterGradesM     inMinus() throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course  course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPo ints("89.99");
		cat.getIt       emList().get(0).setTotalPoints("100");
	 	course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rub     ric);
		assertEquals("A", course.getLetterGrade()  );
	}
	
	@Test
	public void testALetterGradesMin() throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.g etItemList().get(0).setEarnedPoints("90.0");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("A", co   urse.getLetterGrade());
	}
	
	@Test
	public void testALetterG radesMinPlus   () throws Exception {
		C    ategory cat = new Catego    ry("HW", 1, 10.0);
	  	Course course = new Course("CSSE376", 4.0);
		c    at.getItemList().get(0).setEarnedPoints("90.99");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
	   	rubric.setDefau  lts();
		course.setRubric(r ubric);
		assertEquals("A", course.getLetterGrade());
	}
	
	@ Test  
	public void   testALetterGradesNominal() throws Exception {
		Category cat = new Categor     y("HW", 1, 10.0);        
		Course course     = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedP   oints("95.00");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubr ic = new Rubric(     );
		           rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("A", course.getLet     terGrade()    ) ;
	}

	@Test
	public void testALetterGradesMaxMinus()throws Exception {
		Category cat = new Category("HW", 1, 10.     0);
		Course course = new Course("CSSE376", 4.0);
		cat    .getItemList().get(0).setEarnedPoints("99.99");
		cat.getI   temList().get(0) .setTotalPoints("100");
		cou   rse.addCateg   ory(ca    t);
		Rubric rubr    ic = n    ew Ru  bric();
		rubric.setDefaults();
		course.setRubric     (rubric);
		assertEquals("A", course.getLetterGrade());
	}
	
	@Test
	public void testALetterGradesMax()throws Excepti  on {
		Category cat = new Category("HW",    1, 10.0 );
		Course course = new Course("CSSE3  76", 4.0);  
		cat.ge tItemList().get(0).setEarnedPoints("100");
		     cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Ru   bric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		      assertEquals("A",     course.g  et  LetterGrade());     
	}
	
	@Test
	pub     lic void testALetterGradesMaxPlus()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Cour  se course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoint  s("100.99");
		cat.getItemLi st().get(0).setTotalPoints(    "100");
		course.addCate    gor   y(cat);
		Rubric rubric = new Rubric();
		rubric.s      etDefaults(     );
		course.setRubric(  rubric);
		assertEquals("A", course.getLetterGrade());
    	}
	
	@Test
	public void testBPlusLetterGradesMinMinus()throws Exception {
		Category cat =     new Category("HW", 1, 1    0.0);
		Course course = new Course("CSSE376", 4.0);
		    cat.getItemList().ge       t(0).setEarnedPoints("84.99");
		cat.getItemList().get(0).setTotal Points("100");
		cou rse.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("B+", course.getLetterGrade());
	}
	
	@Test
	pu    blic      void testBPlusLetterGradesMin() throws Exception{
		Category cat = new Category("HW", 1, 10       .0);
		Course cour     se = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("85.0");
		cat.getItemList().get(0).setTotalPoints("100");
	           	course.addCategory(cat);
		Rubric rubri  c = new Rubric();
		rubric.setDefaults();
		cour      se.setRubric(rubric);
		assertEquals("B+", course.getLetterGrade());
	}
	
	@Test
	public void testBPlusLetterGra      desMinPlus()thro  ws Exception {
		Ca  tegory cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("85.99");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		R    ubric rubric = new Rubric();
		rubric.setDefaul    ts  ();
		course.setRubric(rubric);
		assertEquals("B+", course.getLetterGrade());
	}
	
	@Tes     t
	public void testBPlusLetterGradesNomin        al()thr   ows Exception {
		Category cat = new Category("HW",   1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		    cat.getItemLis       t().get(0).setEarnedPoints("87.00");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults     ();
		course.   setRubric(rubric);
  		assertEquals("B+", course.getLetterGrade());
	}

	@Test
	public void testBPlusLetterGradesMaxMinus()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("88.99");
		cat.getItemList().get  (0).setTotalPoi   nts("100");    
		course.addCategory(cat);
		Rubr  ic rubric = new Rubric();
		rubric.setDe     faults();
		course.setRubric(rubric) ;
		assertEquals("B+", course.getLetterGrade());
	}
	
	@Test
	publi      c void testBPlusLetterGradesMax()throws Except     ion {
		Category cat   = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(  0).setEarnedPoints("89");
		cat.getItemList().get(0).setTotalPoints(   "100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric)      ;
		assertEquals("B+", course.getLetterGrade())     ;
	   }
	
	@Test
	public void testBPlusLetterGradesMaxPlus()th   rows Exception {
		Cat   egory cat = new Category("HW", 1, 10.         0);
		Course course = new Course("CSS  E376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("89.99");
		cat    .getItemList().get(0).setTotalPoint  s("100");
		course.addCategory(cat);
		Ru   bric ru bric = new Rubric();
		rubric.setDefaults();
		cou rse.setRubric(rubric);
		assertEquals("A", course.getLetterGrade());
	}
	
	@Test
	public void testBLet     terGradesMinM    inus()throws Exception {
		Category cat       = new Ca   tegory("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("79.99");
		cat.getI    temList().get(0).setTotal   Points("100");
		course.addCategory(cat); 
		Rubric rubric =   new Rubric();
		rubric.setDefaults();
		course.setR    ubric(rubric);
		as     ser  tEquals("B", course.getLett    erGrade());
	  }
	
	@Test
	public void testBLetterGradesMin()throws Exception {
		Category ca t = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList    ().get(0).setEarnedPoints("80.0"   );
		cat.getItemList().get(0).setTotalPoints("100");
	     	course.addCategory(cat);
		Rubric rubric = new Rub   ric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("B", course.getLetterGrade(   ));
	}
	
	@Test
	public void test       BLetterGradesMinPlus()throw   s Exception {
		Catego  ry cat = new Category("HW", 1, 1  0.0)      ;
		Course course = new Course("CSSE376   ", 4.0);
		cat.getItemList().get(0).setEarnedPoints("80.99");
		cat.getItemList().get(0)  .setTotalPo    ints("100")   ;
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubri  c(rubric)   ;
		assertEquals("B", course.getLetterGrade());
	}
	
	@Test
	public void testBLetterGradesNominal()throws   Exception {
		Category cat = new Category("H  W", 1, 10.0);
		Cou   r    se course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("82.00");
		cat.getItemList().get(0).setTotalPoints("100");
		c      ourse.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults()   ;
		course.setRubric(rubric);
		assertEquals("B", course.getLetterGrade());
	}

	@     Test
	public void testBLetterGr       adesMaxMinus()throws Exception {
		Category cat = new Catego   ry("HW", 1, 10.0);
		Course course  = new Course("CSSE376", 4.0);
		ca     t.getItem    Li   st().get(0).se  tEa   rnedPoints("83.99");
		cat.getItemList().ge  t(0).setTotalPoints("100"    );
		       course.addCategory(cat);
		Rubric rubric = n    ew Rubric();
       		rubric.setDefa     ults();
		course.setRubric(rubric);
		assertEquals("B",    course.getLetterGrade  ());
	}
	
	@Test
	publ     ic void testBLetterGradesMax()thr ows Exception {
		Category cat = new Category("HW", 1, 10.0);
		  Cou     rse course = new Course("CSSE376", 4.0);
		cat.g  etItemList().get(0).setEarnedPoi nts("84");
		cat.getItemList(   ).get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new  Rubric();
		rubric.setDefaults();
		  course.setRubric(rubric);
		assertEquals("B", course.getLetterGrade(  ));
	}
	
	@Test
	public void testBLetterGradesMaxPlus     ()throws   Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE37        6", 4.0);
		cat.getItemList  ().get(0).setEarnedPo  ints ("84.99");
		cat.getItemList().get(0).setTotalPoints("100");
		course.ad  dCa      t    egory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric (rubric);
		assertEquals("B+", course.getLetterGrade());
	}
	
	@Test
	public void testCLetterGradesMinMinus()throws Excep   tion {
		Category cat = new Category("HW", 1, 10.0);
		Course course    = new Course("CSS           E 376", 4.0);
		cat.getItemList().get(0).setEa  rnedPoints("69.99");
		cat.getItemList().get(0)  .setTotalPoints("100" );
		course.addCategory(cat);
		Rubric rubric = new Rubric ();
		rubric.setDefaults();
		    course.setRubric(r       ubric);
		assertEqual  s("C", co    urse.getLetterGrade());
	}
	
	@Test
	public void testCLetterGradesMin()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course      ("CSSE376", 4.0);
		cat.ge     tItemList().get(0).setEarne   dPoints("70.0");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setR   ubric(rubric);
		assertEquals("C", course.ge     tLetterGr     ade());
	}
	
	@Test
	public void testCLetterGradesMinPlus()th   rows Exception  {
		Category cat = new Category("HW", 1, 10.0  );
		Cours   e course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setE   arnedPoints("70.    99");
		cat.getItemList().get(0).setTotalPoints("100")  ;
		course.addCategory(cat);
 		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("C", course.getLetterGrade());
	}
	
	@Test
 	public void testCLetterGrades   Nominal()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376"  , 4.0);
		cat.getItemList().get(0).s   etEarnedPoints("72.00");
		cat.getItemList().get(0    ).setTotalPoints("100");
		course.addCategory(ca    t);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("C", course.getLetterG     rade());
	}

	@Test
	public void      testCLe  tterGradesMaxMinus()  throws Exception {
		Category cat = new Ca   tegory("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("73.99");
		cat.getItemList().get(0).s      etTotalPoints("100");
		course .addCategory(cat);
		Rub  ric r     ubric = new Rubric();
		rubric.setDefaul ts();
		course.setRubric(rubric);
		assertEquals("C  ", course.getLetterGrade());
	}
	
	@Test
	public void testCLetterGradesMax    (   )throws Exception {
		Category cat = new Categor y("HW"    ,    1, 10.0);
		Course      course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("74");
		cat.getItemList().get(0).setTotalPoin     ts("100");
		course.addCategory(cat);
		Rubric rubric = new Ru bric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("C", course.getLetterGrade());
	}
	
	@Test
	p    ublic void testCLetterGradesMaxPl     us()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course   course = new Course("CSSE376", 4.0);
		cat.get    Ite mLis    t().get(0).setEarnedPoints("7      4.99");
		cat.getItemList()       .get(0).setT   otalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();    
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("C+", cours    e.getLetterGrade());
	}
	
	 @Test  
	public void testCPlusLetterGradesMinMinus()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("74.99");
		cat.ge    tItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Ru    bric rubric = new R     ubric();
		rubric.setDefaul    ts();
		course.setRubric(rubric);
		assertEqu als("C+", course.getLetterGrade());
	}
	
	@Test
	public  void testCPlusLetterGradesMin()throws E   xception {
		Category cat = new Category("HW", 1, 10.0   );
		Course course = new Co   urse("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("75.0");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat );
		Rubric rubric = new Rubric();
		ru  bric   .setDefaults();
		course.setRubric(rubric);
		assertEq uals("C+", course.getLetterGrade());
	}
	
	@Test
	public void testCPlusLetter         Gr         adesMinPlus()throws Exception {
		Category cat = new Category("HW", 1,    10. 0);
		Course course = new Course("CSSE376", 4.0);
		cat.ge     tItemList().get(0).setEarnedPoints("75.99");
		cat.getItemList().get(0).setT    otalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("C+", cou   rse.getL   etterGrade());
	}
	
	@Test
	public void testCPlusLetterGradesNominal()throws Exception {
		Category cat = new Category("HW", 1, 10  .0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPo  ints("77.00");
		cat.g   etItemList    ().get(0).setTotalPoints("100");
		course.a    ddCategory(cat);
		Rub  ric rubric = new Rubric();
		rubric     .setDefa       ults();
		course.setRub       ric(rubric);
		ass    ertE  quals("C+", course.getL etterGrade());
	}

	@Test
	public void testCPlusL    etterGradesMaxMinus()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Co   urse course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("78  .99");
		cat.ge     tItemLi     st().g    et(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(ru   bric);
		assertEquals("C+", course.g   etLetterGrade()  );
	}
  	
	@Test
	  publi   c void testCPlusLetterGradesMax() throws Exc  eption {
		Category cat = new Category("HW", 1, 10.   0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("79");
		cat.getItemList().get  (0).se    tTotalPoin  ts("100");
		co   urs e.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("C+", course.getLetterGra   de());
	}
	
	@Test
	public void testCPlusLetterGrad    esMaxPlus()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("79.99");
		ca        t.getItemList().      get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubri    c = new Rubric     ();
		rubric.setDefaults();
		course.setRu   bric(rubric);
		assertEquals("B", course.getL     etterGrad    e());
	}
	
	@Test
	public void testDPlusLetterGradesMinMinus() throws      Exception{
		Categor  y cat = new Category("HW", 1, 10.0);
		C   ourse course = new Course ("CSSE376", 4.0);
		cat.getIt emList().get(0).setEarnedPoints("64.99");
		cat.getItemList().get(0).set   TotalPoints("100");
		      co urse.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		c  ourse.setRubric(rubric);
		ass       ertEquals("D+", course.getLetterGrade());
	}
	
	@Test
	public void testDPlusLetterGradesM    in()throws Exception {
		Category cat = new Categ   ory("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("65.0");
		cat.getItemList().get(0).setTotalPoints("100");            
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);        
		assertEquals("D+", course.getLetterGrade    ());
	}
	
	@Test
	public vo id testDPL usLetterGradesMinPlus()throws Exception {
		Cat  egory cat = new Category("  HW",   1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("65.99");
		cat.getItemList().get(0).      setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Ru   bric();
		rubric.setDefaults();
		course.se     tRubric(rubric);
		assertEquals("D+", course.getLetterGrade(      ));
	   }
	
	@Test
	public void testDPlusLetterGradesNominal()throws Exception {
		Ca    tegory cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("67.00");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory  (ca     t);
		Rubric rubric = new Rub  ric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("D+",    co       urse.getLetterGrade());
	}
  
	@Te  st
	public void testDPlusLetterGrades  MaxMinus()thr ows Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course =     new Course("   CSSE376     ", 4.  0);
		cat.getItemList().get(0).setE   arn ed       Points("68.99");
		cat.getItemList().get(0).setTotalPoints("100");
	  	course.addCategor     y(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("D+", cou  rse.getLetterGrade());
	}
	
	@Test
	public void testDPlusLetterGradesMax()throws Exce    ption   {
		Category    cat = ne    w Category("HW", 1, 10.0);
		Course        course =     new Course("CSSE376", 4.0);
		cat.getIte    mList().get(0).setEarnedPoints("69");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(c      at);
		Rubric rubri       c = new Rubric();
		rubric.setDefaults();
		course.setR ubr  ic(rubric);
		assertEquals("D+",    course.getLett erGr  ade());
	}
	
	@Test
	public void testDPlusLetterGr       adesMaxPlus()th   rows Exception {
		Category cat = new Category("HW", 1,    10.0);
    	    	Course course = new Course("CSSE376", 4.0);
		cat.getItemLis  t().get(0).setEarnedPoints("69.99");
		cat.getItemList().get(0).  setTotalPoints(    "100");
		course.addCategory(cat);
		Rubric rubr   ic = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEqual s("C", course.getLetterGrade());
	}
	
	@Test
	p    ublic void testDLetterGradesMinM  inus()throws Exception {
		Category cat = new Category("HW", 1,     10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPo  ints("5    9.9   9");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		cour se.setRubric(rubric);
		ass ertEquals("D", course.getLetterGrade());
	}
	
	@Test
	pub      lic void testDLetterGradesMin()throws Exception {
		Category c  at = new Category("HW", 1, 10.0);
		Course course    = new    Course("CSSE376", 4.0);
		  cat.getItemList().get(0).setEarnedPoints    ("60.0");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategor y( cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("D", cou  rse.getLetterGrade());
	}     
	
	@Test
	public void testDLetterGradesMinPlus()throws Exception {
		C   a    tegory cat = ne     w Category("HW", 1, 10.0);
		Co  urse course = new Course("CSSE376", 4.0);
		cat.getItemLis    t().get(0).setEarnedPoints("60.99");
		cat.getItemL  ist().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		cours     e  .setRubric(rubric);
		a   ssertEquals("D", course.getLetterGrade());
	}
	
	@Test
	public  void testDLetterGradesNominal()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("62.00");
		cat.g   etItem    L    ist(   ).get(0).  setTotalPoints("100");
		  course.addCategory(cat);
		 Rubric rubric = new Rubric();
		ru   bric.set    Defaults(   );
		course.setRubric(rubric);
		assertEquals("D", course.getLetterGrade());
	}

	@Test
	public void testDLetterGrades MaxMinus()throws   Exception {
		Category cat = ne   w Categ    ory("HW", 1, 10.0  );
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get    (0).  setEarnedPoint   s("63.99");
		cat.getItemList().get(0).setTot        alPoints("100");
		course.addCategory(cat);
		Rubric rubric = new     Rubric();
		rubric.setDefa   ults();
		course.setRubric(rubric);
		assertEquals(  "D" , course.getLetterGrade());
	}
	
	@Test
	public   void testDLetterGradesMax   ()throws Exception      {
		Category cat = new Catego   ry("HW", 1, 10.0);
		Course course = new Course("CSSE37   6", 4.0);
		cat.getItemList().get(0).setEarnedPoints("64" );
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric(  );
		rubric.setDefaults();
		course.setRubric(rubric);
		a      ssertEquals("D", c  ourse.getLetterGrade());
	}
	
	@Test
	public void testDLetterGradesMaxPlus()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course cour se = new     Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("64.99");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertE quals("D+", course.getLetterGrade());
	}
	
	@Test
	public void testFLetterGradesMinMinus()throws Exception {
		Category cat = n        ew Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("-89.99");
		cat.getItem   List().get(0).setTotalPoints("100");
		course.add Category(cat);
		Rubric      rubric = new Rubric();
		rubri   c.setDefaul    ts();
		course.setR    ubric(rubric);
		assertEquals("F", course.getLetterGrade());
	}
	
	@Test
	public void testFLetterGrade  sMin()throws   Exception {
		Category    cat = new Category("HW",       1, 10.0);
		Course cour se = new Course("CSSE376",   4.0  );
		cat.getItemLis    t().get(0).setEarnedPoints("0.0");
		cat.getIt  emList().get(0   ).setTotal  Points(" 100");
		course.addCategory(cat);
		Rubric    rubric = new Rubric();
		rubric.setDefaults();
		course.  setRubric(rubric);
		assertEqu  als("F", course.getLetterGrade());
	}
	
	@Test
	public voi  d testFLetterGradesMinPlus() throws Exception{
		Categ    ory cat = new Category("HW", 1, 10.0);
		Course course = new Co  urse("CSSE376", 4.0);
		cat.get     ItemList().get(0)    .setEarnedPoints("0.99");
		c at.getItemList().get(0).setTotalPoints("100");
		   course.addCat  ego   ry(cat);
		Rubric   rubric = new Rubric();
		rubri c.se tDe  f aults();
		course.setRubric(rubric);
		assertEquals("F", course.getLetterGra   de());
	}
	
	@Test
	public void t       estFLetterGradesNominal()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Cou      rse course =           new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarn   edPoints("30.00");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
	 	    Rubric rubric = new Rubric      ();
		rubric.setD   efaults();
		course.setRubric(r  ubric);
		assertEquals("       F", course.getLetterGrade());
	}

	@Test
	public void testFLetterGradesMaxMinus()throws Exc   eption {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.     getItemList().get(0).  setEarnedPoints("54.99");
		cat.getItemLi   s    t().get(0).s    etTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new  Rubric();
		rubric.setDefaul  ts();
		course.setRub   ric(rubric);
		a     ssertEquals("F", cou  rse.getLetterGrade());
	}
	
	@Test
	public void testFLetterGradesMax()throws Exceptio  n {
		Category cat = new Category("HW", 1, 10.0);
   		Course cou  rse = new Course("CSSE376", 4.0);      
		cat.getItemList().get(0).setEarnedPoints("59");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCatego         ry(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(r    ubric);
		assertEquals("F", cours  e.getLetterGrade());
  	}  
	
	@Test
	public void testFLetterGradesMaxPlus ()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Cour  se   cour       se = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("59.99");
		cat.   getItemList().get(0)   .setTotalPoin    ts("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();    
		rubri  c.setDefaults();
		course.setRubric(rubric);
		assertE   quals("D", course.getLett erGrade());
	}
	
	@Test
	public void testALette rGPA()throws Excepti   on{
		Cate       gory cat = new Category("HW", 1, 10.0) ;
		Course course   = new   Course("CSSE376", 4.0);
		cat.getItemList(  ).get(0).setEarnedPoints("95");
		cat.getItemList().get(0).setTotalPoint s("1 00");
		co   urse.addCategory(cat);
		Rubric rubric = new Rubric();
		rub      ric.setDefau lts();
		cour se.setRubric(rubr      ic);
		assertEquals(4.0, course.getCourseGPA(), DELTA);
	}
	
	@Test
	public     v     oid testBPlusLetterG PA()throws Ex         ception{
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.ge    tIte  m      List().get(0).setEarne        dPoints("88");
		cat.getIte   mList().get(0).setTotalPoints("100");
		course.addCategory(cat)     ;
		Rubric    rubric = new R     ubr  ic();
		rubric.setDefaults();
		course.s      etRubric(rubric);
		assertEquals(3.5, co  urse.getCourseGPA(), DELTA);
	}
	
	@Test
	public void testBLette     rGPA()throws Exception{
		Category cat = ne     w Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("82");
		cat.getI   temList().get(0).setT   otalPoints("1  00");
		cour  se.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRub    ric(rubr  ic);
		assertEquals(3.0, course.getCourseGPA(), DELTA);
	}
	
	@Test
	publi  c void testCP   lusLetterGPA()t      hrows Exception{
		Category cat = new Category("HW", 1, 10.0);
		Cours   e course =   new C ourse("CSSE376", 4.0);
		cat.getItemList().get(0).setEar    nedPoints("78");
		cat.getItemList().ge  t(0).setTotalPoints("100");
		course.ad      dCategory(cat);
		Rubric rubr     ic = new R  u bric();   
		rubric.setDefaults();
		course.setRubric(rubric);
		ass ertEquals(2.5, course.getCou r   seGPA(),     DELTA);
	}
	
	@Test
	public void testCLetterGPA()throws Exception{
		Category cat  = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("73");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric ru     bri  c = ne       w Rubric();
		rubri  c.setDefaults();
		course.setRubric( rubric);
		assertEquals(2.0, course.getCourseGPA(    ), DELTA);
	}
	
	@Test
	publi   c void testDPlusLe   tter     GPA()throws      Exception{
		Category cat = new Category("HW"     , 1, 10.0);
		C   ourse course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoi  nts("68");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rub    ric = new Rubric();
		rubric.setDefaults();
		course.setRubric(r   ubric);
		assertEquals(1.5, course.getCourseGPA(), DELTA);
	}
	
	@Test
	public void test DLetterGPA()throws Exception{
		Category cat = new Categ          ory("HW    ", 1, 10.   0);
		Course course = new Course( "CSSE3  76", 4.0);
		cat.getItemList().get(0).setEarnedPoints("62");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.set Rubr    ic(rubric);
		assertEquals(1.0, course.getCourse    GPA(), DELTA);
	}
	
	@Test
	public void testFLetterGPA  ()throws Exception{
		Category        cat = new Category("HW", 1, 10.0);   
		Course course = new Course("CSSE376", 4.0);
		cat.getItemLi  s t().get(0).setEarnedPoints ("40      ");
	     	cat.getItem     List().get     (0).s          etTotalPoints("100");
		course.addCategory(cat);
		Rubric rubri            c = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals(0.0,   course.getCours   eGPA(), DELTA    );
	}
	
	@Test
	public void testSetAndGetDateOne() throws Exception {
		Course course = new Course(  "CSSE376");
   		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy"   );
		Date d = sdf.parse("04/08/2013");
		course.setStartDate(d);   
		assertEquals(d, course.getStartDate());
	}
	
	@T      est
	public void testSetAndGetDateTwo() throws Exception {
   		Course course = new Course("CSSE376");
		SimpleDateFormat    sdf = new SimpleDateFormat("MM/d  d/yyyy");
		Date d = sdf.parse("04/08/2013"      );
		course.setStartDate(d);
		Date temp = sdf.parse("04/22/2013");
		course.setEndDate(temp);
		assertEqu       als(d, course.getStartDate());
            	       }
	
	@Test(expected = IllegalArgumentExcepti   on.class)
	public void testSetAndGetDate   Three() throws Exception {
		Course course = new Course("CSSE376");
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date d = sdf.parse("04/08/2  013");
		course.   setStartDat    e(d);
     		Date temp = sdf.par   se("04/05/2013"   );
		course.setEndDate(temp);}
	
	@Test
	public void        testSetAndGetDateFour() throws Exception {
		Course course = new Course("CSSE376");
		SimpleDateFormat sdf = new Simple       DateFormat("MM/dd/yyyy");
		Date d = sdf.par    se("04/08/2013");
	  	course.setEndDate(d);
     		assertEquals(d, course.getEndDate());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetAndGetDateFive() throw      s Exception {
		Course course = new Course("CSSE376");
		SimpleDateFormat sdf = new SimpleD  ateFor  m       at("MM/dd/yyyy");
		Date d = sdf.parse("04/08     /2013");
		course.   setEndDate(d);
		Date temp = sdf.parse ("04/22/2013");
		course.setStartDate(temp);	}
	
	@    Test
	public void testSetAndGetDateSix() throws Exception {
		Course cour  se = new Course("CSSE376");
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date d =  sdf.parse("04/08/2013");
		course.setEndDate(d);
		Date temp = sdf.pars     e("04    /05/2013");
		course.setStartDate(temp);
		assertEquals(d, course.getEndDate());
	}
	
	@Test
	pu     bli    c void testConstructorDat    eOne() throws Exce       ption {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date d = sdf.parse("04/08/2013");
		Date       temp = sdf.par   se("04/22/2013");
		Course course = new Course("CSS     E376", d, temp);
		assertEquals(d, course.getStar       tDate());
	}
	
	              @Test(expected = IllegalArgumentException.class)
	public void testConstructorDateTwo() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("M    M/dd/yy yy");
		Date d = sdf.parse("04/08/2013");
		Date temp = sdf.parse("04/22/2013        ");
		@SuppressWarnin      gs("unused")
		Cours  e course = new Course("CS  SE376", temp, d);}
	
	@Test
 	public void testCon   structorDateThr ee(  ) throws Excepti  on {
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date d =     sdf.parse("04/08/2013");
		Dat e temp = sdf.parse("04/05/2013");
		Course   co urse = new Course("CSSE       376", temp, d);
		assertEquals(d,  course.g      etEndDate());
	}
	
	@Test
	public   void testConstructorDateFour() throws Exception {
		Sim pleDateForm at sdf =       new SimpleDateFormat("MM/dd    /yyyy");
		Date d = sdf.parse("04/08/2013");
		Date temp = s     df.parse("04/22/2013");
		Course course = new Course("CSSE376", 4   .0, d, temp);
		assertEquals  (d, course.getStartDate());
	}
	
	@Su    ppr    essWarnings("unused")
	@Test(expected = Illeg     alArgumentException.class)
     	public void t   es  tConstructorDateFive() throw   s     Exception {
		SimpleDateFormat sdf =    new       SimpleDateFormat("MM     /dd/yyyy");
		Date d = sdf.parse("04/08/2013");
		Date temp    = sdf.parse("04/22/2013");
		Course course = new Course("CSSE376",    4   .0, t    emp, d);}
	
	@Test
	public vo id testConstructorDateSix() throws Exception {
		
		SimpleDateFormat sdf = new     SimpleDateFormat("MM/dd/yyyy");
		Date    d     = sdf.pars     e("04/08/2013");
		Date temp = sdf.parse("04/05/2013");
		Course course = new Course("CSSE376", 4.0, tem    p, d);
		assertEquals(d,  course.getEndDate());
	}
	
	@Test(expected = IllegalArgumentException.cla ss)
	public void testFrequencyCal     culatorAssignment  MinLimitMinM    inus()throws Exception{
		Category cat = new Category("HW", 1     0.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    		        Date start = sdf.parse("04/08/2013");
	  	Date e   nd = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/    05/2013" );
		
 		course.addCategory(cat);
		course.setStartDa   te(start);
		course.setEnd  Date(en    d);
		
		assertEquals(0, course.getItemFrequency(start, limit)  .get("HW").intValue());	}
	
	@Test
	public void testFrequency  CalculatorAssignmentMinLimitMin()thro  ws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		      SimpleDat    eFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date start    = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2   013");
		Da    te limit = sdf.parse("04/   08/2013   ");
		
		c      ourse.addCategory(cat   );
		course.setStartD  ate(start);
		course.setEndDate(end         );
		
		assertEquals(0, course.getItemFrequency(start, limit).ge    t("HW   ").intValue());
	}

	@Test
	publi  c void testFrequencyCalculatorAssignmentMinLimitMinPlus()throws   Exception{
		Category c     at = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4      .0);
		SimpleDate     Format sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date start = sdf.parse("04/08/2013");
	   	Date end = sdf.parse("04/22/2013");
		Date lim   it = sdf.parse("04/09/2013");
		
		course.addCategory(cat);
		course.setStartDa te(start);
		course.setEndDate(e    nd);
		
		ass  ertEquals(0, course.getItemFrequency(start, limit).get("HW").intValue());
	}
	
	@Test
 	public void testFrequ encyCalculatorAssignmentMinLimitNomi   nal()throws Exception{
		Category cat = ne w Category("HW", 10.0);
		Cours  e course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new     SimpleDateF     ormat("MM/dd/yyyy ");
		Date start = sdf.p  arse("04/08/2013 ");
		Date end = sdf.parse("04/22/2013");
		Date limit = s  df.parse("04/15/2013");
		
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(0, course.getItemFrequency(start, limit).get("HW").intValue());
	}
	
	@Tes    t
	public void testFrequencyCalcu   latorAssignmentMinLimitMaxMinus()throws Except  ion{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateForm  at sdf = new SimpleDateFormat("MM/dd/y   yyy"          );
		Date start = sdf.parse("04/08/2013");
	      	Date   end = sdf.parse("04/22/2013");
		Date limit = sdf.parse(  "04/21/2013");
 		
		course.addCateg  ory(cat);
		cours     e.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(0, course.getItemFrequency(start, limit).get("HW").intValue());
	}
	
	@Tes t
	p  ublic    void testFrequencyCalculatorAssignm      entMinLimitMax()throws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Co   urse("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/y  yyy");
		Date start = sdf.parse("04/08/2013");
		Date      end = sdf.pa      rse("04/22/2013");
		Date limit    = sdf.parse("04/22/2013");
		
		c    ourse.addCategory(cat);
		course.setStartDate(sta rt);
		course.setEndDate(end);
		
		ass  e         rtEqual    s(0, course.getItemFrequency(start, limit).get("HW")    .intValue());
	}
	
	@Test(expected = IllegalArgumentException.class)
	 public void testFrequencyCalculato  rAssignmentMinLi   mitMaxPlus()thro   ws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CS     SE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date start = sdf.parse("04/08/2013  ");
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf       .parse(  "04/23/2013");
		
		       course.addCategory(cat );
 		course.setStartDate(start);
		course.setEnd      Date(end);
		
		assertEquals(0, course.getItemFrequency(start, limit).get("HW").intValue());	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testFrequencyCalculator       A    ssignmentMinPlusLimitMinMinus()throws Excep   tion{
		Cat  egory cat = new Cate  gory("HW", 10.0);
	  	Course co         urse = new Course("CSSE376", 4.0);
	 	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy")    ;
		Date    start = sdf.pars     e("04/08/2013");
		Date end = sdf.parse(  "   04/22/2013");
		D ate limit = sdf.parse("04/05/20 13");

		Item it = new Item(    "HW1", sdf.parse("04/08/2013"));
		 
   		cat.addItem(it);
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(1, course.getItemF   requency(start, limit).get("     HW").intValue());	}
	
	@Test
	public void testFrequencyCalculatorAssignmentMinPlusLimitMin()throws Exception{
		C      ategory cat = new Category("HW", 10.0);
		Course c     ourse = new Cours    e      ("CS SE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/       yyyy");
		Date start = sdf.parse("04/08/2013");
		Date     e        nd = sdf.pars     e("04/22/2013");
		Date limit = sdf.parse("04/08/2013");

		Item it = new Item("HW1",    sdf.pa       rse("04/08/2013"));
		
		cat.addItem(it);
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDa   te(end);
		
		assertEquals(1, course.ge    tItemFrequency(start, limit).  get("HW").intValue());	}
	
	@Test
	public void testFrequencyCal  culatorAssignmentMinPlusLimitMinPlus()throws Exce  ption{
		Category cat = new Category("HW", 10.0);
		Cou   rse course = new Course("CSSE376"  , 4.0);
		SimpleDateF     ormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Da    te start = sdf.par      se("04/08/2013"      );
		Date end = sdf.parse("04/22/   2013");
		Date limit = sdf.parse("04/09/201      3");

		Item it = n    ew I  te   m("HW1", sdf.parse("04/08/2013"));
		
		cat.addItem(it);
		course  .addCategory(cat);
		course.setStartDate(start)   ;
          		course.setEnd D     ate(end);
		
		assertEquals(1, course.getItemFrequency(st  art, limit).get("  HW").intValue());	}
	
	@Test    
	public void testFrequencyCalculatorAssignmentMinPlusLimitNominal()throws Exception{    
		Category cat =    new Category("HW", 10.0);
		Course co urs   e = new Course("CSSE376", 4.0);
		Simple   DateFormat sdf = new SimpleDateF         ormat("MM/dd/yyyy");
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
 		Date limit = sdf      .parse("04/15   /201   3");

		Item it = n   ew Item("HW1", sdf.parse("04/08/2013"));
		
		cat.addItem(it);
		course.addCategory(c  at);
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(1,   course.    getItemFrequency(start,    limit).get("HW").intValue());	}
	
	@Test
	pub   lic void t   estFrequencyCalculatorAssignme   ntMinPlusLimitMaxMinus()throws Exception{
		Category cat = new Category("HW", 10.0);
		Course c  ourse = new Course("CSSE376", 4  .0);
		SimpleDateFormat sdf = new SimpleDateFormat ("MM/dd/  yyyy");
		Date start =     sdf.     parse("04/08/2013");
		Date end = sdf.pa     rse("04/22/    2013");      
		   Date limit = sdf.parse("04/2        1/2013");

		Item it = new Item("HW1", sdf.parse("04/08/2013"));
		
		cat.ad  dItem(it);
		course.addCat   egory(cat);
		course.setStartDate(start);
		course.s    etEndDa    te(end);
		 
		assertEquals (1, course.getIt   emFrequency(start, limit).get("HW").intValue());	}
	
	@Test
 	publ      ic void testFrequencyCalculatorAssignmentMinPlusLimitM      ax()throws  Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date start      = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		Date l    imit = sdf.parse("04/22/2013"   );

		Item it = new Item("HW1", sdf.parse("04/08/2013"));
		
		cat.addItem(it);
		course.addCategory(cat);
		course     .setStar  tDa     te(start);
		course.setEndDate(end);
		
		assertEquals(1, course.getItemF  requency(start, limit).get(   "HW").intValue());	}
	
	@  Test(expected = IllegalArgumentException.class)
	publi   c void testFrequencyCalcula   torAssignmentMinPlusLimitMaxP  lus()throws Exception{
		Category cat = new Category     ("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf =      new SimpleDateFormat("MM/dd    /yyyy");
		Date start = sdf.parse("04/08/2013");
		Dat      e end =      sdf.parse("04/22/20      13");
		Date limit = sdf.parse("04/23/2013");

		Item it = new Ite   m("     HW1", sdf.parse("04/08/2013"));
		
		cat.addItem(it);
		course.addCategory(cat);
		course.setStar        tDate( start);
		course.setEndDate(end);
		
		assertEquals(1, course.getItemFrequency(start, lim it).get("HW").intValue());	}
	
	@Test(expected   = IllegalArgumentException.class)
	public void testFrequen  cyC    alculator  AssignmentNominalLimitMinMinus()throws Exception{
		Category cat = n   ew Category("HW", 10.0);
		Co        urse course = new Course("CSSE376", 4.0);
		SimpleDateFormat s    df = new SimpleDateFormat("MM/dd/yyyy");
		String[]    date = {"4/08/2013", "4/09/2013", "4/10/201  3", "4/11/2013", "4/12 /2013", "4/1   3/2013", "4/14/2013"}; 
		Date start = sdf.parse("04/08/2013");
		D  ate end = sdf.parse("04/   22/2013");
 		Date lim  i t = sdf.parse("04/05/2013");
		
		for(int i=0; i<10; i++){
			int rand = (int) Math.random() * 7;
			Item it = new Item("HW"+i, s df.parse(  date[rand])      );
			cat.addIte       m(it);
		}   
   		
		course.addCategory(cat);
		c   ourse.setStartDate(start);
		course.setEndDa   te(end);
		
		ass ertEquals(10, course.getItemFrequ   ency(start, limit).get("HW").intValue());	}
	
	@Test
	public void testFrequencyCalcu   lator       Assig       nmentNominalLimitMin()throws Exception{
		Cat   egory cat = new Category("HW", 10.0);
		Course cours   e = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new Sim  pleDateFormat("MM/dd/yyyy");
		String[] date = {"4/08/2013"}; 
		Date start        = sdf.parse("04/08/201    3");
		Date end = sdf.parse("04    /22/2013");
		Date limit = sdf.parse("04/08/2013");
		
		fo    r(int i=0; i<10; i     ++){
	    		Item it =    new Item("HW"+i, sdf.parse(date[0  ]));
			c  at.addItem(it);
		}
		
		course.addCategory(cat);
		co    urse.setStartDate(start);
		     course.setEndDate(end);
		  
		  assertEquals(10, course.getItemFrequency(start, limit).get("HW").i  ntValue());
	}
	
	@Test
	public void testFrequencyCalcula       torAssignmentNominalL imitMinPlus(     )throws Exception{
		Category cat = new Cate       gory("HW", 10.0);
		Course course = new Course("CSSE37    6", 4.     0);
  		SimpleDat           eFormat sd   f = n         ew SimpleDateFormat("MM/d  d/yyyy");
		Stri   ng[] date = {"4/08/2013", "4/09/2013"};    
		Date start = sdf.parse("04/08/2013");
		Date     end = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/09/2013");
 		
		for(int i=0; i<10; i++    ){
			int rand = (in t) Math.random() * date.length;
			It      em it   = new Item("HW"+i, sdf.parse(   date[ran   d]));
			cat.addItem(it);
		}
		
		course.addCategory(cat);
		course.set     StartDate(start);
		course.setEndDate(end);
		
		assertEquals    (10,    course.getItemFrequency(start, limit).get("HW").intValue());	
	}
	
	@Test
	publ    ic      void testFrequencyCalculatorAssignmentNominalLimitNominal()throws Excep    ti   on{
		Category cat = new Cat    egory("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new Si mpleDate   Format  ("MM/dd/ yyyy");
		String[]      date      = {"4/08/2013", "4/09/2013", "4/10/2013", "4/11/2013", "4/12/         2013", "4/13/2013"   , "4/14/2013"}; 
		Da     te start = sdf.parse("04/08/2013");
		Date end =          sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/15/2013");
   		
		for(int i=0; i<10; i++){
		     	int rand = (int) Math.random    () * date.length;
			Ite  m it = new Item("HW"+i,      sdf.pa rse(date[rand]));    
			c   at.addItem(it   );
		}
		 
		course.addCat   egory(cat);    
		course.setStartDate(start);
		course.setEndDate(end)  ;
		
		assertEquals(10, course.getItemFre     quency(s     tart, limit).get("HW") .intValue());
	}
	
    	@Test
	public void testFrequencyCalculatorAssignmentNominalLimitMaxMinus()   throws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376  ", 4.0);
		SimpleD    ateForm    at sdf = new SimpleD   ateFormat("MM/dd/yyyy");
		String[] date = {"4/08/2013", "4/09/201  3", "4/10/2013", "4/11/2013     ", "4/12/    201   3", "4/13/2013", "4/14/   2013",
				"4/15/2013", "4/16/2013", "4/17/2013", "4/18/2013", "4/19/2013", "4/20/2013", "4/21/2013"}; 
		Date start =      sdf.pa  rse("04/08/2013");
		Date end = sdf.parse("04/22/20     13"  );
		Date limit = sdf.parse("04/21/2013");
		
		for(  int i=0; i<10; i++){
			int rand = (int) Math.random() * date.length;
			Item it = new Item( "HW"+i,    sdf.p      arse (date[rand]));
			cat.addItem(it);
		}
		
		course.addCategory(cat)   ;
		course.setStartDate(start);
		course.setEndDate(e  n    d);
		
		assertEqua     ls(10, course.getI      temFrequency(start, limit).get("HW").intValue());
 	}
	
	@Test
	public void testFrequenc yCalculatorAssignmentN     ominalLimitMax()throws Exception{
		Category cat = new Category("HW", 10.0);
		Cou  rse course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String[] date = {"4/08/2013",      "4/09/2013", "   4/10/2013", "4/11/2013", "4/12/2013", "   4/13/2013", "4/14/2013",
				"4/15/2013", "4/16/2013", "4/  17/2013", "4/18/2013", "4/19/2013", "4/20/2013", "4/21/2013", "4/22/2013"}; 
		Date start = sdf.parse("04/08/2013"      );
		      Date end = sdf.parse("04/22/2013");
	    	Date limit = sdf.parse("04/22/201    3");
	     	
		for( int i=0; i<10; i++   ){
			int rand = (int) Math.random() *  date.l ength;
       			Item it = new Item("HW"+i    , sdf.parse(date[rand]));
			cat.addIt    em(it);
		}
		   
   		course.addCategory(cat);
		co   urse.setStartDate(start);
		   cour se.setEndDate(end)  ;
		
	      	assertEquals(10,      course.getItemF   requency(start, limit).  get("HW")  .intValue  ());	}
	
	@Test(expected = Ill     egalArgumentException.class)
	public void te     stFrequencyCalculatorAssignmen    tNominalLimitMaxPlus()throws Excep        tion{
		Category cat = new Category("HW", 10.0);
		Course course = new Course         ("CSSE376", 4.0);
		SimpleDateFormat sdf      = new SimpleDateFormat("   MM/dd/yyyy");
		String[] date = {"4/08/2013", "4/09/2013", "4/10/2013", "4/11/2013", "4/12/2013   ", "4/  13/2013", "4/14/2013",
				"4/15/2013", "4/16/2013",      "4/17/2013", "4/18/2013", "4/19/2013", "4/20   /2013", "4/21/2013", "4/22/2013"}; 
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("0  4/2      2/2013");
		Date limit = sdf.parse("04/23/2013");
		
		for(int i=0; i<10; i++){
	 		int rand = (int) Math.random() *    date.le   ngth;
			 Item it = new Item("HW"+i, sdf.parse(date[rand]));
			cat.addItem(it);
		}
		
		course.addCa    tegory(cat);
		co      urse.setStartDate(start);
		course.setEnd  Date(end);
		
		assertEqua  ls(10, course.getItemFrequency(s   tart, limit).get("HW").intValue());	}

	@Test(expected = IllegalArgumentException.class)
	public void testFrequencyCalculatorAssignmentMaxMinusLimit    MinMinus()throws Except ion{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFo   rmat("MM/dd/yyyy");
		String[] date  = {"4/08/2013", "4/09/2013", "4/10/    2013", "4/11/2013 ", "4/12/2013", "4/13/2013", "4/14/2013"}; 
		  Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf.   parse("04 /05/2013");
		
		for(int i=0; i<19; i++){
			int r     and = (int) Math.random() * 7;
			Item it = new Item("HW"+i, sdf.parse(date[rand]));
			cat.addItem(it);
		}
		     
		co    urse.addCategory(cat);
		course.setStartDate       (start);
		course.setEndDate(    end)    ;
		
		assertEquals(19, course.getItemFrequency(start, limit)    .get("   HW").intValue());	}
	
	@   Test
	publi    c vo  id testFrequencyCalculat  orAssignmentMaxMinusLimitMin()throws Exception{
		Category      cat = new Cate  gory("HW", 10.0);
		Course course = new Course("CSSE376", 4.0     );
		S    imple DateFormat sdf = new SimpleDateForma     t("MM/dd/yyyy");
		String[]   date = {"4/08/2013"}; 
		Date star   t = sdf.parse("04/08/2013");
		Date     end = sdf.parse("04/22/2013");
	   	Date limit = sd  f.parse("04/08/2013");
		
		for(int i=0; i<19; i++){
			Item it   = new It em("HW"+i, sdf.parse(date[0]));
			cat.addItem(it);
		}
		
		course.addC  ategory(cat);
		course.setStart Date (start);
		course.setEndDate(end);
		
		assertEquals(19, course.getItemFrequency(start, limit   )   .get("HW").   intValue());
	}
	
	@Test  
	public void te  stFrequencyCalculatorAssignme ntMaxMinusL  imitMinPlus()throws Exception{
		Category cat =    new Category("HW", 1     0.0);
		Cou   rse course = new Course("CSSE376", 4.0   );
		Simpl     eDateFormat sdf = new Simp      leDateFormat("M         M/dd/yyyy");
		String[] date = {"4   /08/2013", "4/09/2013"};  
		Date start   = sdf.parse("04/08/2013");
		Date end = sdf.parse ("04   /22/2013");
		Date li     mit = sdf.parse("04/09/2013");
		
		for(int i=0; i<19; i++){
			int rand = (int) Math.random() * date.leng  th;
			Item it =   new Item(  "HW"+i, sdf.parse(date[rand]));
			cat.addItem(it);
		}
		
		course.addCategory(cat);
		c  ourse.s   etStartDate(start);
		course.se       tEndDate(end);
		
		assertEquals(19, course.ge  tItemFrequency    (start, limit).get("HW").intValue());	
	}
	
	@Test
	public void testFrequencyCalculatorAssignmen        tMaxMin  usLimitNomin     al()throw     s Exception{
		Category cat = new Category("HW", 10.0);
		Course  course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	   	String[] date = {"4/08/2013"        , "4/09/2013", "4/10/2013", "4/11/2013", "4/12/20   13",      "4/13/2013", "4/14/   2013"}; 
		Date st     art = sdf.parse  ("04/08/2013");
		 Date end = sdf.parse("04/22/2013");
		Dat  e limit = sdf.parse("04/15/2013");
		
		for(int i=0; i<19; i++){
			int rand = (int) Math.  random() * date.length;
			Item it = new Item("HW"+i, sd     f.  parse(date[rand]));
			cat.addItem(it);
		}
		
		course.addCatego ry(cat);
		course.setStar tDate(start);
		course.setEndDate(end);
		
		assertEquals(19, cours   e.getItemFre   quency(start, limit).get("HW").intValue());
	}
	
	@Te   st
	public void testFr   equencyCalculato  rAssignmentMaxMinusLimitMaxMinus()throws E        xception{
		Category cat = new Category("HW", 10.0);
		Course course = new Co urse("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String[] date = {"4/08/2013", "4/09/2013", "4/10/2013", "4/    11/2013", "4/12/2013", "       4/13/201     3     ", "4/14/2013",
				"4/15/2013", "4/16/2013", "4/17/2013", "4/18/2013", "4/19/2013"        , "4 /20/2013", "4/21/2013"}; 
		Dat  e start = sdf.parse("04/08/2013");
		Date end =  sdf.pa  rse("04/22/2013");
		Date limit = sdf.parse("04/21/2013"); 
		   
		for(int i=0; i  <19; i+       +){
			int rand = (int) Math.random() * date.length;
			Item it    =     new Item("HW"+i, sdf.parse(date[rand]));
			cat.addIt   em(it);
		}
		
		course.a   d   dCategory(cat);
		course.setSta  rtDate(sta r     t);
		cour     se.setEndDate(end);
		
		assertEquals(19, course.getItemFrequency(sta  rt, limit  ).get("HW").intValue());
	}
	
	@Test
	public void testFrequencyC        alculator  AssignmentMaxMinusLimitMax()throws Exception{
		Category cat = new Category("H   W", 10.0);
		Course course = new Cou     rse("CSSE376", 4.0);
		Simpl   eDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String[] dat        e = {"    4/08/2013", "4/09/2013", "4/10/2013",    "4/11/2013", "4/12/2013", "4/13/2013", "4/14/2013",
				"4/15/20   13", "4/16/2   013    ", "4/17/2013", "4/18/2013"    , "4/19/2013", "4/2   0/2013", "4/21/2013   ", "4/22/2013"}; 
		Date start = sdf.parse("04/0  8/2013");
		Date end = sdf.parse("04/22/201     3");
		Date limit    = sdf.parse("04/22/2013");
	   	
		for(int i=0; i<19; i++){
			int rand = (int) Math.random(  ) * date.length;
			Item it   = new Item("HW  "+i, sdf.parse(date[rand]));
			c     at.addItem(it);
		}
		
    		course.addCategory(cat);
		cour se.setStartDate(start);
		course.    setEndDate(end);
		
		assertEquals(19,     course.g      etItemFrequency(start, limit).g          et      ("HW").intValue());	}
	
	@Test(expected = Illegal   ArgumentException.class)
	public void testFrequencyCalculatorAssignmen    tMaxMinusLimitMaxPlus()  throws Exceptio n{
		Categor     y cat = new Category("HW"       , 10.0);
		Cour   se course   = ne     w C     ourse("CSSE376", 4.0);
		SimpleDateFormat sdf =     new Simple  DateFormat("MM/dd/yyyy");
		String[] date = {"4/08/2013", "4/09/2013", "4/10/2013", "4/11/2013", "4/12/2013", "4/13/2013", "4/14/2013", 
				"4/1  5/2013", "4/16/2013", "4/1  7/2013", "4/18/2013", "4/19/2013", "4/20/2013", "4/21/2013", "4/22/2013"}; 
		Date start = sdf.parse("04/08/2013")  ;
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/23/2013");
		
		   for(int i=0; i<19; i++){
			int rand = (int) Math.random() * date.length;
			Item it = new It  em("HW"+i, sdf.parse(date[rand]));
			cat.addItem(it);
		}
		
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEnd     Date(end);
		
		assertEquals(19, course.getItemFre quency(start, limit).get("HW").intValu  e());}
	
	@Test(expected = IllegalArgument   Exception.class)
	public void testFrequencyCalculatorAssignmentMaxLimitMinMinus()throws Exception{
		Cate   gory cat = new Category( "HW", 10.0);
		Cour   se course = new Course("CS    SE376", 4.0);
		Si   mpleDateF     orma  t sdf = new SimpleDateFormat(  "MM/dd/yyyy");
		String[]   date = {"4/0   8/2013", "4/09/2013", "4/10/2013", "4/11/2013", "4/12/2013", "4/13/2013", "4/14/2013"}; 
		Date start = sdf.parse("04/08/2013");
        		Date e nd = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/05/2013");
		
		for   (int i=0; i<2    0   ; i++){
			int rand = (int) Math.random() * 7;
			Item it = new Item("HW"+i, sdf.parse(date[rand]));
			cat.addItem(it);
		}
		
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
	
		assertEquals(20, course.getItemFrequen   cy(start, limit).get("HW").intValue());}
	
	@Test
	public void testFrequencyCalcula  torAssign      mentMaxLimitMin()throws Exce  ption{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String[]  date = {"4/08/2013"}; 
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf.par se("04/08/20  13");
		
		for(int i=0; i<20; i++)      {
			Item it = n      ew   Item("HW"+i, sdf.     p    arse(date[0]));
			cat.addItem(it);
		}
		
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
  		assertEquals(20, course.getItemFrequency(start, limit).get("HW").intValue());
	}
	
	@Test
	public void te   stFrequencyCalculatorAssignmentMaxLimitMinPlus(      )throws Excepti    on{
		Category cat = new Category("HW", 10.0);
	 	Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String[] date = {"4/08/2013", "4/09/2013"}; 
		Date   start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
	     	Date limit = sdf.parse("04/09/2013");
		
		for(int i=0; i<20; i++){
			int rand = (int) Math.random() * date.length;
			Item it =      new Item("HW"+i, sdf.parse(date[rand]));
			cat.addItem(it);
		}
		
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
		   assertEqual  s(20, course.getItemFrequency(start     , limit).get("HW").intValue());	
	}
	
	@Test
	public              void testFrequencyCalculatorAssignmentMaxLimitNominal()throws Exception{
		Category cat =   new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String[] date = {"4/08/2013", "4/09/2013", "4/10/2013", "4/11/2013"  , "4/12/2013", "4/13/2013", "4/14/2013"}; 
		Date start = sdf.parse("04/08/20     13");
		Date      end = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/15/2013");
		
		for(int i    =0; i<20; i++){
			int rand = (int) Math.random    () *   date.length;
			Item it = new Item("HW"+i, sdf.parse(date[rand   ]));
			cat.addItem(it);
		}
		
		cou    rse.addCategory(cat);
		course.setStartDate(  start    );
		course.setEndDate(end);
		
		assertEquals(20, course.get    ItemFrequency(start, limit).get("HW"   ).in   tValue());
	    }
  	
	@Test
	public void testF     requencyCalculatorAssignmentMaxLimitMaxMinus()throws Excepti  on{
		Category cat = new Category("HW"    , 10  .0);
		Course c     ourse = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String[] date        = {"4/08/2013", "   4/09/2013", "4/10/2013", "4/11/2013", "4/12/2013", "4/13/2013", "4/14/2013",
				"4/15/2013", "4/16/2013", "4/17/2013", "4/18/2013", "4/19/2013", "4/20/2013", "4/21/2013"}; 
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/21/2013");
		
		for(int i=0; i<20; i++){
			int rand = (int) Math.random() * date.length;
			Item it = new Item("HW"+i, sdf.parse(date[rand]));
			cat.addItem(it);
		}
		
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(20, course.getItemFrequency(    start, limit).get("HW").intVa    lue());
	}
	
	@Test
	public void testFrequencyCa  lculatorAssignmentMaxLimitMax() throws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String[]   date = {"4/08/2013", "4/09/2013", "4/10/2013", "4/11/2013", "4/12/2013", "4/13/2013", "4/14/2013",
				"4/15/2013", "4/16/2013", "4/17/2013", "4/18/2013", "4/19/2013", "4/20/2013", "4/21/2013", "4/22/2013"}; 
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/22/2013");
		
		for(i  nt i=0; i<20; i++){
			int rand = (int) Math.random() * date.length;
			Item it   = new Item("HW"+i, sdf.parse(date[rand]));
			cat.addItem(it);
		}
		
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(20, course.getItemFrequency(start, limit).get("HW").intValue());
	}
	
	@Test(expected = IllegalArgumentEx     ception.class)
	public void testFre    quencyCalculatorAssignmentMaxLimitMaxPlus() throws Exception{
		Category cat = new Category("HW"  , 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String[] date = {"4/08/2013", "4/09/2013", "4/10/2    013", "4/1    1/2013", "4/12/2013", "4/13/2013", "4/14/2013",
				"4/15/2013", "4/16/2013", "4/17/2013", "4/18/2013", "4/19/2013", "4/20/2013", "4/21/2013", "4/22/2013"}; 
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/23/2013");
		
		for(int i=0; i<20; i++){
			int rand = (int) Math.random() * date.length;
			Item it = new Item("HW"+i, sdf.parse(date[rand]));
			cat.addItem(it);
		}
		
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(20, course.getItemFrequency(start, limit).get("HW").intValue());}
	
	@Test
	public void testResettingItemDate() throws Exception{
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376",   4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy"); 
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		
		course.addCategory(cat);
		course.getCategories().get(0).getItemList().get(0).setUpdateDate(sdf.parse("4/05/2013"));
		
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(start, course.getCategories().get(0).getItemList().get(0).getUpdateDate());	}
	
	@Test
	public void testResettingItemDateTwo() throws Exception{
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy"); 
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(end, course.getCategories().get(0).getItemList().get(0).getUpdateDate());	}
}
