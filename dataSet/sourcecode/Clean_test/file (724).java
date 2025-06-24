import static org.junit.Assert.*;

import java.util.Date;
import java.text.SimpleDateFormat;

import org.junit.Test;

public class CourseTest {

	private static final double DELTA = 1e-15;

	@Test
	public void testJUnit() {
		assertTrue("JUnit works!", true);
	}

	// test cases for initializing Course
	@Test
	public void testInitializeOne() {
		assertNotNull(new Course("CSSE376"));
	}

	@Test
	public void testInitializeTwo() {
		assertNotNull(new Course("CSSE376", 4.0));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInitializeWithEmptyName() {
		new Course("");	}

	@Test(expected = IllegalArgumentException.class)
	public void testInitializeWithEmptyNameTwo() {
		new Course("", 4.0);	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInitializeWithEmptyNameThree() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		new Course("", start, end);	}

	@Test(expected = IllegalArgumentException.class)
	public void testInitializeWithEmptyNameFour() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy"); 
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");

		new Course("", 4.0, start, end);	}

	@Test(expected = IllegalArgumentException.class)
	public void testInitializeWithNegativeCreditHours() {
		new Course("CSSE376", -9.0);	}

	// test case for get methods
	@Test
	public void testGetNameOne() {
		Course course = new Course("CSSE376");
		assertEquals("CSSE376", course.getCourseName());
	}

	@Test
	public void testGetNameTwo() {
		Course course = new Course("CSSE376", 4.0);
		assertEquals("CSSE376", course.getCourseName());
	}

	@Test
	public void testGetCreditHoursOne() {
		Course course = new Course("CSSE376", 4.0);
		assertEquals(4.0, course.getCreditHours(), DELTA);
	}

	@Test
	public void testGetCreditHoursTwo() {
		Course course = new Course("CSSE376");
		assertEquals(0.0, course.getCreditHours(), DELTA);
	}

	// test cases for set methods
	@Test
	public void testSetNameOne() {
		Course course = new Course("CSSE376");
		course.setName("CSSE304");
		assertEquals("CSSE304", course.getCourseName());
	}

	@Test
	public void testSetNameTwo() {
		Course course = new Course("CSSE376", 4.0);
		course.setName("CSSE304");
		assertEquals("CSSE304", course.getCourseName());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetEmptyName() {
		Course course = new Course("CSSE376", 4.0);
		course.setName("");	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetEmptyNameTwo() {
		Course course = new Course("CSSE376");
		course.setName("");	}

	@Test
	public void testSetCreditHoursOne() {
		Course course = new Course("CSSE376", 4.0);
		course.setCreditHours(5.0);
		assertEquals(5.0, course.getCreditHours(), DELTA);
	}

	@Test
	public void testSetCreditHoursTwo() {
		Course course = new Course("CSSE376");
		course.setCreditHours(4.0);
		assertEquals(4.0, course.getCreditHours(), DELTA);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetNegativeCreditHour() {
		Course course = new Course("CSSE376", 4.0);
		course.setCreditHours(-6.0);	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetNegativeCreditHourTwo() {
		Course course = new Course("CSSE376");
		course.setCreditHours(-5.5);	}

	// test cases for adding and getting categories
	@Test
	public void testAddCategory() {
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(cat);
		assertEquals("HW", course.getCategories().get(0).getName());
	}

	@Test
	public void testAddCategoryTwo() {
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(new Category("HW", 10.0));
		course.addCategory(new Category("Quiz", 20.0));
		course.addCategory(new Category("Exam", 40.0));
		course.addCategory(new Category("Final", 30.0));
		assertEquals("Final", course.getCategories().get(3).getName());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddCategorThree() {
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(new Category("HW", 10.0));
		course.addCategory(new Category("Quiz", 20.0));
		course.addCategory(new Category("Exam", 40.0));
		course.addCategory(new Category("Final", 30.0));
		course.addCategory(new Category("Final2", 30.0));	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddCategorFour() {
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(new Category("HW", 10.0));
		course.addCategory(new Category("HW", 20.0));}
	
	//test cases for removing categories
	@Test
	public void testRemoveCategoryOne() {
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(cat);
		assertTrue(course.removeCategory("HW"));
	}

	@Test
	public void testRemoveCategoryTwo() {
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(new Category("HW", 10.0));
		course.addCategory(new Category("Quiz", 20.0));
		course.addCategory(new Category("Exam", 40.0));
		course.addCategory(new Category("Final", 30.0));
		assertTrue(course.removeCategory("Quiz"));
		assertEquals("Exam", course.getCategories().get(1).getName());
	}

	@Test
	public void testRemoveCategoryThree() {
		Course course = new Course("CSSE376", 4.0);
		assertFalse(course.removeCategory("HW"));
	}
	
	@Test
	public void testRemoveCategoryFour() {
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(new Category("HW", 10.0));
		course.addCategory(new Category("Exam", 40.0));
		assertFalse(course.removeCategory("Final"));
	}

	//test cases for getting and setting target grade
	@Test
	public void testTargetGradeMin(){
		Course course = new Course("CSSE376", 4.0);
		course.setTargetGrade(0.0);
		assertEquals(0.0, course.getTargetGrade(), DELTA);
	}
	
	@Test
	public void testTargetMinPlus(){
		Course course = new Course("CSSE376", 4.0);
		course.setTargetGrade(10.0);
		assertEquals(10.0, course.getTargetGrade(), DELTA);
	}
	
	@Test
	public void testTargetGradeNominal(){
		Course course = new Course("CSSE376", 4.0);
		course.setTargetGrade(50.0);
		assertEquals(50.0, course.getTargetGrade(), DELTA);
	}
	
	@Test
	public void testTargetGradeMaxMinus(){
		Course course = new Course("CSSE376", 4.0);
		course.setTargetGrade(90.0);
		assertEquals(90.0, course.getTargetGrade(), DELTA);
	}
	
	@Test
	public void testTargetGradeMax(){
		Course course = new Course("CSSE376", 4.0);
		course.setTargetGrade(100.0);
		assertEquals(100.0, course.getTargetGrade(), DELTA);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testTargetGradeOverHundred(){
		Course course = new Course("CSSE376", 4.0);
		course.setTargetGrade(190.0);	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testTargetGradeNegative(){
		Course course = new Course("CSSE376", 4.0);
		course.setTargetGrade(-90.0);	}
	
	// test cases course grades
	/**
	 * For the purpose of testing we are using 0 as a min, and 100 as a max. 
	 * However, we do not throw an exception if the value is less than 0 because 
	 * it is possible to have negative grades, or grades greater than 100.
	 */
	@Test
	public void testCourseGradesMinMinus() {
		Category cat = new Category("HW", 10, 10.0);
		Course course = new Course("CSSE376", 4.0);
		for (int i = 0; i < 10; i++) {
			cat.getItemList().get(i).setEarnedPoints("-7.5");
			cat.getItemList().get(i).setTotalPoints("10");
		}
		course.addCategory(cat);
		assertEquals(-75.0, course.getCourseGrade(), DELTA);
	}
	
	@Test
	public void testCourseGradesMin() {
		Category cat = new Category("HW", 10, 10.0);
		Course course = new Course("CSSE376", 4.0);
		for (int i = 0; i < 10; i++) {
			cat.getItemList().get(i).setEarnedPoints("0");
			cat.getItemList().get(i).setTotalPoints("10");
		}
		course.addCategory(cat);
		assertEquals(0.0, course.getCourseGrade(), DELTA);
	}
	
	@Test
	public void testCourseGradesMinPlus() {
		Category cat = new Category("HW", 10, 10.0);
		Course course = new Course("CSSE376", 4.0);
		for (int i = 0; i < 10; i++) {
			cat.getItemList().get(i).setEarnedPoints("2.5");
			cat.getItemList().get(i).setTotalPoints("10");
		}
		course.addCategory(cat);
		assertEquals(25.0, course.getCourseGrade(), DELTA);
	}
	
	@Test
	public void testCourseGradesNominal() {
		Category cat = new Category("HW", 10, 10.0);
		Course course = new Course("CSSE376", 4.0);
		for (int i = 0; i < 10; i++) {
			cat.getItemList().get(i).setEarnedPoints("6.5");
			cat.getItemList().get(i).setTotalPoints("10");
		}
		course.addCategory(cat);
		assertEquals(65.0, course.getCourseGrade(), DELTA);
	}

	@Test
	public void testCourseGradesMaxMinus() {
		Category hw = new Category("HW", 10, 10.0);
		Category quiz = new Category("Quiz", 5, 20.0);
		Course course = new Course("CSSE376", 4.0);
		for (int i = 0; i < 10; i++) {
			hw.getItemList().get(i).setEarnedPoints("8.5");
			hw.getItemList().get(i).setTotalPoints("10");
		}
		for (int i = 0; i < 5; i++) {
			quiz.getItemList().get(i).setEarnedPoints("95");
			quiz.getItemList().get(i).setTotalPoints("100");
		}
		course.addCategory(hw);
		course.addCategory(quiz);
		assertEquals(91.67, course.getCourseGrade(), DELTA);
	}
	
	@Test
	public void testCourseGradesMax() {
		Category hw = new Category("HW", 10, 10.0);
		Category quiz = new Category("Quiz", 5, 20.0);
		Course course = new Course("CSSE376", 4.0);
		for (int i = 0; i < 10; i++) {
			hw.getItemList().get(i).setEarnedPoints("10");
			hw.getItemList().get(i).setTotalPoints("10");
		}
		for (int i = 0; i < 5; i++) {
			quiz.getItemList().get(i).setEarnedPoints("100");
			quiz.getItemList().get(i).setTotalPoints("100");
		}
		course.addCategory(hw);
		course.addCategory(quiz);
		assertEquals(100.0, course.getCourseGrade(), DELTA);
	}
	
	@Test
	public void testCourseGradesMaxPlus() {
		Category cat = new Category("HW", 10, 10.0);
		Course course = new Course("CSSE376", 4.0);
		for (int i = 0; i < 10; i++) {
			cat.getItemList().get(i).setEarnedPoints("10.5");
			cat.getItemList().get(i).setTotalPoints("10");
		}
		course.addCategory(cat);
		assertEquals(105.0, course.getCourseGrade(), DELTA);
	}
	
	//test cases for calculating course to acheive target grade
	@Test(expected = IllegalArgumentException.class)
	public void testNeededCourseGradesMinMinusTargetGradeAndMinMinusEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("-86");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("-72");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("-92");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("-50");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(-5);	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNeededCourseGradesMinMinusTargetGradeAndMinEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("0");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("0");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("0");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("0");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(-5);	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNeededCourseGradesMinMinusTargetGradeAndMinPlusEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("5");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("7");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("9");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("5");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(-5);	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNeededCourseGradesMinMinusTargetGradeAndNominalEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("50");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("50");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("50");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("50");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(-5);	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNeededCourseGradesMinMinusTargetGradeAndMaxMinusEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("95");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("95");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("95");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("95");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(-5);	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNeededCourseGradesMinMinusTargetGradeAndMaxEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("100");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("100");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("100");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("100");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(-5);	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNeededCourseGradesMinMinusTargetGradeAndMaxPlusEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("110");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("110");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("110");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("110");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(-5);	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNeededCourseGradesMaxPlusTargetGradeAndMinMinusEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("-86");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("-72");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("-92");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("-50");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(110);	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNeededCourseGradesMaxPlusTargetGradeAndMinEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("0");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("0");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("0");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("0");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(110);	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNeededCourseGradesMaxPlusTargetGradeAndMinPlusEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("5");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("7");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("9");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("5");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(110);	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNeededCourseGradesMaxPlusTargetGradeAndNominalEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("50");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("50");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("50");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("50");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(110);	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNeededCourseGradesMaxPlusTargetGradeAndMaxMinusEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("95");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("95");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("95");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("95");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(110);	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNeededCourseGradesMaxPlusTargetGradeAndMaxEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("100");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("100");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("100");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("100");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(110);	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNeededCourseGradesMaxPlusTargetGradeAndMaxPlusEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("110");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("110");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("110");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("110");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(110);	}
	
	@Test
	public void testNeededCourseGradesMinTargetGradeAndMinMinusEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("-86");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("-72");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("-92");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("-50");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(0);
		assertEquals(327.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesMinTargetGradeAndMinEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("0");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("0");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("0");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("0");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(0);
		assertEquals(0.0, course.getNeededCourseGrade(), DELTA);	
	}
	
	@Test
	public void testNeededCourseGradesMinTargetGradeAndMinPlusEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("5");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("7");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("9");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("5");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(0);
		assertEquals(-26.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesMinTargetGradeAndNominalEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("50");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("50");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("50");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("50");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(0);
		assertEquals(-200.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesMinTargetGradeAndMaxMinusEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("95");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("95");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("95");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("95");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(0);
		assertEquals(-380.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesMinTargetGradeAndMaxEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("100");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("100");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("100");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("100");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(0);
		assertEquals(-400.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesMinTargetGradeAndMaxPlusEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("110");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("110");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("110");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("110");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(0);
		assertEquals(-440.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesMinPlusTargetGradeAndMinMinusEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("-86");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("-72");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("-92");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("-50");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(5);
		assertEquals(352.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesMinPlusTargetGradeAndMinEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("0");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("0");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("0");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("0");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(5);
		assertEquals(25.0, course.getNeededCourseGrade(), DELTA);	
	}
	
	@Test
	public void testNeededCourseGradesMinPlusTargetGradeAndMinPlusEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("5");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("7");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("9");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("5");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(5);
		assertEquals(-1.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesMinPlusTargetGradeAndNominalEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("50");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("50");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("50");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("50");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(5);
		assertEquals(-175.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesMinPlusTargetGradeAndMaxMinusEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("95");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("95");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("95");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("95");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(5);
		assertEquals(-355.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesMinPlusTargetGradeAndMaxEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("100");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("100");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("100");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("100");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(5);
		assertEquals(-375.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesMinPlusTargetGradeAndMaxPlusEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("110");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("110");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("110");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("110");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(5);
		assertEquals(-415.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesNominalTargetGradeAndMinMinusEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("-86");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("-72");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("-92");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("-50");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(50);
		assertEquals(577.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesNominalTargetGradeAndMinEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("0");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("0");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("0");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("0");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(50);
		assertEquals(250.0, course.getNeededCourseGrade(), DELTA);	
	}
	
	@Test
	public void testNeededCourseGradesNominalTargetGradeAndMinPlusEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("5");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("7");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("9");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("5");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(50);
		assertEquals(224.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesNominalTargetGradeAndNominalEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("50");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("50");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("50");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("50");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(50);
		assertEquals(50.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesNominalTargetGradeAndMaxMinusEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("95");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("95");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("95");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("95");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(50);
		assertEquals(-130.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesNominalTargetGradeAndMaxEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("100");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("100");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("100");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("100");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(50);
		assertEquals(-150.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesNominalTargetGradeAndMaxPlusEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("110");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("110");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("110");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("110");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(50);
		assertEquals(-190.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesMaxMinusTargetGradeAndMinMinusEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("-86");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("-72");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("-92");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("-50");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(90);
		assertEquals(777.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesMaxMinusTargetGradeAndMinEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("0");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("0");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("0");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("0");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(90);
		assertEquals(450.0, course.getNeededCourseGrade(), DELTA);	
	}
	
	@Test
	public void testNeededCourseGradesMaxMinusTargetGradeAndMinPlusEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("5");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("7");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("9");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("5");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(90);
		assertEquals(424.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesMaxMinusTargetGradeAndNominalEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("50");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("50");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("50");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("50");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(90);
		assertEquals(250.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesMaxMinusTargetGradeAndMaxMinusEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("95");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("95");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("95");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("95");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(90);
		assertEquals(70.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesMaxMinusTargetGradeAndMaxEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("100");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("100");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("100");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("100");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(90);
		assertEquals(50.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesMaxMinusTargetGradeAndMaxPlusEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("110");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("110");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("110");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("110");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(90);
		assertEquals(10.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesMaxTargetGradeAndMinMinusEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("-86");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("-72");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("-92");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("-50");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(100);
		assertEquals(827.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesMaxTargetGradeAndMinEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("0");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("0");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("0");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("0");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(100);
		assertEquals(500.0, course.getNeededCourseGrade(), DELTA);	
	}
	
	@Test
	public void testNeededCourseGradesMaxTargetGradeAndMinPlusEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("5");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("7");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("9");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("5");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(100);
		assertEquals(474.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesMaxTargetGradeAndNominalEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("50");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("50");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("50");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("50");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(100);
		assertEquals(300.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesMaxTargetGradeAndMaxMinusEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("95");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("95");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("95");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("95");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(100);
		assertEquals(120.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesMaxTargetGradeAndMaxEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("100");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("100");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("100");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("100");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(100);
		assertEquals(100.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public void testNeededCourseGradesMaxTargetGradeAndMaxPlusEarnedGrade() {
		Category item1 = new Category("HW", 1, 35.0);
		Category item2 = new Category("Quiz", 1, 20.0);
		Category item3 = new Category("Assignment", 1, 20.0);
		Category item4 = new Category("Temp", 1, 5.0);
		
		item1.getItemList().get(0).setEarnedPoints("110");
		item1.getItemList().get(0).setTotalPoints("100");
		item2.getItemList().get(0).setEarnedPoints("110");
		item2.getItemList().get(0).setTotalPoints("100");
		item3.getItemList().get(0).setEarnedPoints("110");
		item3.getItemList().get(0).setTotalPoints("100");
		item4.getItemList().get(0).setEarnedPoints("110");
		item4.getItemList().get(0).setTotalPoints("100");
		
		Course course = new Course("CSSE376", 4.0);
		course.addCategory(item1);
		course.addCategory(item2);
		course.addCategory(item3);
		course.addCategory(item4);
		course.setTargetGrade(100);
		assertEquals(60.0, course.getNeededCourseGrade(), DELTA);
	}
	
	@Test
	public void testSetrubricOne() throws Exception{
		Course course = new Course("CSSE376", 4.0);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals(4.0, course.getRubric().getGPA("A"), DELTA);
	}
	
	@Test
	public void testSetrubricTwo() throws Exception{
		Course course = new Course("CSSE376");
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals(4.0, course.getRubric().getGPA("A"), DELTA);
	}
	
	@Test
	public void testALetterGradesMinMinus() throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("89.99");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("A", course.getLetterGrade());
	}
	
	@Test
	public void testALetterGradesMin() throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("90.0");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("A", course.getLetterGrade());
	}
	
	@Test
	public void testALetterGradesMinPlus() throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("90.99");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("A", course.getLetterGrade());
	}
	
	@Test
	public void testALetterGradesNominal() throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("95.00");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("A", course.getLetterGrade());
	}

	@Test
	public void testALetterGradesMaxMinus()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("99.99");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("A", course.getLetterGrade());
	}
	
	@Test
	public void testALetterGradesMax()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("100");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("A", course.getLetterGrade());
	}
	
	@Test
	public void testALetterGradesMaxPlus()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("100.99");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("A", course.getLetterGrade());
	}
	
	@Test
	public void testBPlusLetterGradesMinMinus()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("84.99");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("B+", course.getLetterGrade());
	}
	
	@Test
	public void testBPlusLetterGradesMin() throws Exception{
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("85.0");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("B+", course.getLetterGrade());
	}
	
	@Test
	public void testBPlusLetterGradesMinPlus()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("85.99");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("B+", course.getLetterGrade());
	}
	
	@Test
	public void testBPlusLetterGradesNominal()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("87.00");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("B+", course.getLetterGrade());
	}

	@Test
	public void testBPlusLetterGradesMaxMinus()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("88.99");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("B+", course.getLetterGrade());
	}
	
	@Test
	public void testBPlusLetterGradesMax()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("89");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("B+", course.getLetterGrade());
	}
	
	@Test
	public void testBPlusLetterGradesMaxPlus()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("89.99");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("A", course.getLetterGrade());
	}
	
	@Test
	public void testBLetterGradesMinMinus()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("79.99");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("B", course.getLetterGrade());
	}
	
	@Test
	public void testBLetterGradesMin()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("80.0");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("B", course.getLetterGrade());
	}
	
	@Test
	public void testBLetterGradesMinPlus()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("80.99");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("B", course.getLetterGrade());
	}
	
	@Test
	public void testBLetterGradesNominal()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("82.00");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("B", course.getLetterGrade());
	}

	@Test
	public void testBLetterGradesMaxMinus()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("83.99");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("B", course.getLetterGrade());
	}
	
	@Test
	public void testBLetterGradesMax()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("84");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("B", course.getLetterGrade());
	}
	
	@Test
	public void testBLetterGradesMaxPlus()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("84.99");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("B+", course.getLetterGrade());
	}
	
	@Test
	public void testCLetterGradesMinMinus()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("69.99");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("C", course.getLetterGrade());
	}
	
	@Test
	public void testCLetterGradesMin()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("70.0");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("C", course.getLetterGrade());
	}
	
	@Test
	public void testCLetterGradesMinPlus()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("70.99");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("C", course.getLetterGrade());
	}
	
	@Test
	public void testCLetterGradesNominal()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("72.00");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("C", course.getLetterGrade());
	}

	@Test
	public void testCLetterGradesMaxMinus()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("73.99");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("C", course.getLetterGrade());
	}
	
	@Test
	public void testCLetterGradesMax()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("74");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("C", course.getLetterGrade());
	}
	
	@Test
	public void testCLetterGradesMaxPlus()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("74.99");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("C+", course.getLetterGrade());
	}
	
	@Test
	public void testCPlusLetterGradesMinMinus()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("74.99");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("C+", course.getLetterGrade());
	}
	
	@Test
	public void testCPlusLetterGradesMin()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("75.0");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("C+", course.getLetterGrade());
	}
	
	@Test
	public void testCPlusLetterGradesMinPlus()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("75.99");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("C+", course.getLetterGrade());
	}
	
	@Test
	public void testCPlusLetterGradesNominal()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("77.00");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("C+", course.getLetterGrade());
	}

	@Test
	public void testCPlusLetterGradesMaxMinus()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("78.99");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("C+", course.getLetterGrade());
	}
	
	@Test
	public void testCPlusLetterGradesMax()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("79");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("C+", course.getLetterGrade());
	}
	
	@Test
	public void testCPlusLetterGradesMaxPlus()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("79.99");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("B", course.getLetterGrade());
	}
	
	@Test
	public void testDPlusLetterGradesMinMinus() throws Exception{
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("64.99");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("D+", course.getLetterGrade());
	}
	
	@Test
	public void testDPlusLetterGradesMin()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("65.0");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("D+", course.getLetterGrade());
	}
	
	@Test
	public void testDPLusLetterGradesMinPlus()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("65.99");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("D+", course.getLetterGrade());
	}
	
	@Test
	public void testDPlusLetterGradesNominal()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("67.00");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("D+", course.getLetterGrade());
	}

	@Test
	public void testDPlusLetterGradesMaxMinus()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("68.99");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("D+", course.getLetterGrade());
	}
	
	@Test
	public void testDPlusLetterGradesMax()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("69");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("D+", course.getLetterGrade());
	}
	
	@Test
	public void testDPlusLetterGradesMaxPlus()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("69.99");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("C", course.getLetterGrade());
	}
	
	@Test
	public void testDLetterGradesMinMinus()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("59.99");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("D", course.getLetterGrade());
	}
	
	@Test
	public void testDLetterGradesMin()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("60.0");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("D", course.getLetterGrade());
	}
	
	@Test
	public void testDLetterGradesMinPlus()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("60.99");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("D", course.getLetterGrade());
	}
	
	@Test
	public void testDLetterGradesNominal()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("62.00");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("D", course.getLetterGrade());
	}

	@Test
	public void testDLetterGradesMaxMinus()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("63.99");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("D", course.getLetterGrade());
	}
	
	@Test
	public void testDLetterGradesMax()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("64");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("D", course.getLetterGrade());
	}
	
	@Test
	public void testDLetterGradesMaxPlus()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("64.99");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("D+", course.getLetterGrade());
	}
	
	@Test
	public void testFLetterGradesMinMinus()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("-89.99");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("F", course.getLetterGrade());
	}
	
	@Test
	public void testFLetterGradesMin()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("0.0");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("F", course.getLetterGrade());
	}
	
	@Test
	public void testFLetterGradesMinPlus() throws Exception{
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("0.99");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("F", course.getLetterGrade());
	}
	
	@Test
	public void testFLetterGradesNominal()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("30.00");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("F", course.getLetterGrade());
	}

	@Test
	public void testFLetterGradesMaxMinus()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("54.99");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("F", course.getLetterGrade());
	}
	
	@Test
	public void testFLetterGradesMax()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("59");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("F", course.getLetterGrade());
	}
	
	@Test
	public void testFLetterGradesMaxPlus()throws Exception {
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("59.99");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals("D", course.getLetterGrade());
	}
	
	@Test
	public void testALetterGPA()throws Exception{
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("95");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals(4.0, course.getCourseGPA(), DELTA);
	}
	
	@Test
	public void testBPlusLetterGPA()throws Exception{
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("88");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals(3.5, course.getCourseGPA(), DELTA);
	}
	
	@Test
	public void testBLetterGPA()throws Exception{
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("82");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals(3.0, course.getCourseGPA(), DELTA);
	}
	
	@Test
	public void testCPlusLetterGPA()throws Exception{
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("78");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals(2.5, course.getCourseGPA(), DELTA);
	}
	
	@Test
	public void testCLetterGPA()throws Exception{
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("73");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals(2.0, course.getCourseGPA(), DELTA);
	}
	
	@Test
	public void testDPlusLetterGPA()throws Exception{
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("68");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals(1.5, course.getCourseGPA(), DELTA);
	}
	
	@Test
	public void testDLetterGPA()throws Exception{
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("62");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals(1.0, course.getCourseGPA(), DELTA);
	}
	
	@Test
	public void testFLetterGPA()throws Exception{
		Category cat = new Category("HW", 1, 10.0);
		Course course = new Course("CSSE376", 4.0);
		cat.getItemList().get(0).setEarnedPoints("40");
		cat.getItemList().get(0).setTotalPoints("100");
		course.addCategory(cat);
		Rubric rubric = new Rubric();
		rubric.setDefaults();
		course.setRubric(rubric);
		assertEquals(0.0, course.getCourseGPA(), DELTA);
	}
	
	@Test
	public void testSetAndGetDateOne() throws Exception {
		Course course = new Course("CSSE376");
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date d = sdf.parse("04/08/2013");
		course.setStartDate(d);
		assertEquals(d, course.getStartDate());
	}
	
	@Test
	public void testSetAndGetDateTwo() throws Exception {
		Course course = new Course("CSSE376");
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date d = sdf.parse("04/08/2013");
		course.setStartDate(d);
		Date temp = sdf.parse("04/22/2013");
		course.setEndDate(temp);
		assertEquals(d, course.getStartDate());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetAndGetDateThree() throws Exception {
		Course course = new Course("CSSE376");
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date d = sdf.parse("04/08/2013");
		course.setStartDate(d);
		Date temp = sdf.parse("04/05/2013");
		course.setEndDate(temp);}
	
	@Test
	public void testSetAndGetDateFour() throws Exception {
		Course course = new Course("CSSE376");
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date d = sdf.parse("04/08/2013");
		course.setEndDate(d);
		assertEquals(d, course.getEndDate());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetAndGetDateFive() throws Exception {
		Course course = new Course("CSSE376");
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date d = sdf.parse("04/08/2013");
		course.setEndDate(d);
		Date temp = sdf.parse("04/22/2013");
		course.setStartDate(temp);	}
	
	@Test
	public void testSetAndGetDateSix() throws Exception {
		Course course = new Course("CSSE376");
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date d = sdf.parse("04/08/2013");
		course.setEndDate(d);
		Date temp = sdf.parse("04/05/2013");
		course.setStartDate(temp);
		assertEquals(d, course.getEndDate());
	}
	
	@Test
	public void testConstructorDateOne() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date d = sdf.parse("04/08/2013");
		Date temp = sdf.parse("04/22/2013");
		Course course = new Course("CSSE376", d, temp);
		assertEquals(d, course.getStartDate());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorDateTwo() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date d = sdf.parse("04/08/2013");
		Date temp = sdf.parse("04/22/2013");
		@SuppressWarnings("unused")
		Course course = new Course("CSSE376", temp, d);}
	
	@Test
	public void testConstructorDateThree() throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date d = sdf.parse("04/08/2013");
		Date temp = sdf.parse("04/05/2013");
		Course course = new Course("CSSE376", temp, d);
		assertEquals(d, course.getEndDate());
	}
	
	@Test
	public void testConstructorDateFour() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date d = sdf.parse("04/08/2013");
		Date temp = sdf.parse("04/22/2013");
		Course course = new Course("CSSE376", 4.0, d, temp);
		assertEquals(d, course.getStartDate());
	}
	
	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorDateFive() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date d = sdf.parse("04/08/2013");
		Date temp = sdf.parse("04/22/2013");
		Course course = new Course("CSSE376", 4.0, temp, d);}
	
	@Test
	public void testConstructorDateSix() throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date d = sdf.parse("04/08/2013");
		Date temp = sdf.parse("04/05/2013");
		Course course = new Course("CSSE376", 4.0, temp, d);
		assertEquals(d, course.getEndDate());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testFrequencyCalculatorAssignmentMinLimitMinMinus()throws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/05/2013");
		
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(0, course.getItemFrequency(start, limit).get("HW").intValue());	}
	
	@Test
	public void testFrequencyCalculatorAssignmentMinLimitMin()throws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/08/2013");
		
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(0, course.getItemFrequency(start, limit).get("HW").intValue());
	}

	@Test
	public void testFrequencyCalculatorAssignmentMinLimitMinPlus()throws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/09/2013");
		
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(0, course.getItemFrequency(start, limit).get("HW").intValue());
	}
	
	@Test
	public void testFrequencyCalculatorAssignmentMinLimitNominal()throws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/15/2013");
		
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(0, course.getItemFrequency(start, limit).get("HW").intValue());
	}
	
	@Test
	public void testFrequencyCalculatorAssignmentMinLimitMaxMinus()throws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/21/2013");
		
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(0, course.getItemFrequency(start, limit).get("HW").intValue());
	}
	
	@Test
	public void testFrequencyCalculatorAssignmentMinLimitMax()throws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/22/2013");
		
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(0, course.getItemFrequency(start, limit).get("HW").intValue());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testFrequencyCalculatorAssignmentMinLimitMaxPlus()throws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/23/2013");
		
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(0, course.getItemFrequency(start, limit).get("HW").intValue());	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testFrequencyCalculatorAssignmentMinPlusLimitMinMinus()throws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/05/2013");

		Item it = new Item("HW1", sdf.parse("04/08/2013"));
		
		cat.addItem(it);
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(1, course.getItemFrequency(start, limit).get("HW").intValue());	}
	
	@Test
	public void testFrequencyCalculatorAssignmentMinPlusLimitMin()throws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/08/2013");

		Item it = new Item("HW1", sdf.parse("04/08/2013"));
		
		cat.addItem(it);
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(1, course.getItemFrequency(start, limit).get("HW").intValue());	}
	
	@Test
	public void testFrequencyCalculatorAssignmentMinPlusLimitMinPlus()throws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/09/2013");

		Item it = new Item("HW1", sdf.parse("04/08/2013"));
		
		cat.addItem(it);
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(1, course.getItemFrequency(start, limit).get("HW").intValue());	}
	
	@Test
	public void testFrequencyCalculatorAssignmentMinPlusLimitNominal()throws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/15/2013");

		Item it = new Item("HW1", sdf.parse("04/08/2013"));
		
		cat.addItem(it);
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(1, course.getItemFrequency(start, limit).get("HW").intValue());	}
	
	@Test
	public void testFrequencyCalculatorAssignmentMinPlusLimitMaxMinus()throws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/21/2013");

		Item it = new Item("HW1", sdf.parse("04/08/2013"));
		
		cat.addItem(it);
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(1, course.getItemFrequency(start, limit).get("HW").intValue());	}
	
	@Test
	public void testFrequencyCalculatorAssignmentMinPlusLimitMax()throws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/22/2013");

		Item it = new Item("HW1", sdf.parse("04/08/2013"));
		
		cat.addItem(it);
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(1, course.getItemFrequency(start, limit).get("HW").intValue());	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testFrequencyCalculatorAssignmentMinPlusLimitMaxPlus()throws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/23/2013");

		Item it = new Item("HW1", sdf.parse("04/08/2013"));
		
		cat.addItem(it);
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(1, course.getItemFrequency(start, limit).get("HW").intValue());	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testFrequencyCalculatorAssignmentNominalLimitMinMinus()throws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String[] date = {"4/08/2013", "4/09/2013", "4/10/2013", "4/11/2013", "4/12/2013", "4/13/2013", "4/14/2013"}; 
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/05/2013");
		
		for(int i=0; i<10; i++){
			int rand = (int) Math.random() * 7;
			Item it = new Item("HW"+i, sdf.parse(date[rand]));
			cat.addItem(it);
		}
		
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(10, course.getItemFrequency(start, limit).get("HW").intValue());	}
	
	@Test
	public void testFrequencyCalculatorAssignmentNominalLimitMin()throws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String[] date = {"4/08/2013"}; 
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/08/2013");
		
		for(int i=0; i<10; i++){
			Item it = new Item("HW"+i, sdf.parse(date[0]));
			cat.addItem(it);
		}
		
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(10, course.getItemFrequency(start, limit).get("HW").intValue());
	}
	
	@Test
	public void testFrequencyCalculatorAssignmentNominalLimitMinPlus()throws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String[] date = {"4/08/2013", "4/09/2013"}; 
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/09/2013");
		
		for(int i=0; i<10; i++){
			int rand = (int) Math.random() * date.length;
			Item it = new Item("HW"+i, sdf.parse(date[rand]));
			cat.addItem(it);
		}
		
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(10, course.getItemFrequency(start, limit).get("HW").intValue());	
	}
	
	@Test
	public void testFrequencyCalculatorAssignmentNominalLimitNominal()throws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String[] date = {"4/08/2013", "4/09/2013", "4/10/2013", "4/11/2013", "4/12/2013", "4/13/2013", "4/14/2013"}; 
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/15/2013");
		
		for(int i=0; i<10; i++){
			int rand = (int) Math.random() * date.length;
			Item it = new Item("HW"+i, sdf.parse(date[rand]));
			cat.addItem(it);
		}
		
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(10, course.getItemFrequency(start, limit).get("HW").intValue());
	}
	
	@Test
	public void testFrequencyCalculatorAssignmentNominalLimitMaxMinus()throws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String[] date = {"4/08/2013", "4/09/2013", "4/10/2013", "4/11/2013", "4/12/2013", "4/13/2013", "4/14/2013",
				"4/15/2013", "4/16/2013", "4/17/2013", "4/18/2013", "4/19/2013", "4/20/2013", "4/21/2013"}; 
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/21/2013");
		
		for(int i=0; i<10; i++){
			int rand = (int) Math.random() * date.length;
			Item it = new Item("HW"+i, sdf.parse(date[rand]));
			cat.addItem(it);
		}
		
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(10, course.getItemFrequency(start, limit).get("HW").intValue());
	}
	
	@Test
	public void testFrequencyCalculatorAssignmentNominalLimitMax()throws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String[] date = {"4/08/2013", "4/09/2013", "4/10/2013", "4/11/2013", "4/12/2013", "4/13/2013", "4/14/2013",
				"4/15/2013", "4/16/2013", "4/17/2013", "4/18/2013", "4/19/2013", "4/20/2013", "4/21/2013", "4/22/2013"}; 
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/22/2013");
		
		for(int i=0; i<10; i++){
			int rand = (int) Math.random() * date.length;
			Item it = new Item("HW"+i, sdf.parse(date[rand]));
			cat.addItem(it);
		}
		
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(10, course.getItemFrequency(start, limit).get("HW").intValue());	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testFrequencyCalculatorAssignmentNominalLimitMaxPlus()throws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String[] date = {"4/08/2013", "4/09/2013", "4/10/2013", "4/11/2013", "4/12/2013", "4/13/2013", "4/14/2013",
				"4/15/2013", "4/16/2013", "4/17/2013", "4/18/2013", "4/19/2013", "4/20/2013", "4/21/2013", "4/22/2013"}; 
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/23/2013");
		
		for(int i=0; i<10; i++){
			int rand = (int) Math.random() * date.length;
			Item it = new Item("HW"+i, sdf.parse(date[rand]));
			cat.addItem(it);
		}
		
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(10, course.getItemFrequency(start, limit).get("HW").intValue());	}

	@Test(expected = IllegalArgumentException.class)
	public void testFrequencyCalculatorAssignmentMaxMinusLimitMinMinus()throws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String[] date = {"4/08/2013", "4/09/2013", "4/10/2013", "4/11/2013", "4/12/2013", "4/13/2013", "4/14/2013"}; 
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/05/2013");
		
		for(int i=0; i<19; i++){
			int rand = (int) Math.random() * 7;
			Item it = new Item("HW"+i, sdf.parse(date[rand]));
			cat.addItem(it);
		}
		
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(19, course.getItemFrequency(start, limit).get("HW").intValue());	}
	
	@Test
	public void testFrequencyCalculatorAssignmentMaxMinusLimitMin()throws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String[] date = {"4/08/2013"}; 
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/08/2013");
		
		for(int i=0; i<19; i++){
			Item it = new Item("HW"+i, sdf.parse(date[0]));
			cat.addItem(it);
		}
		
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(19, course.getItemFrequency(start, limit).get("HW").intValue());
	}
	
	@Test
	public void testFrequencyCalculatorAssignmentMaxMinusLimitMinPlus()throws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String[] date = {"4/08/2013", "4/09/2013"}; 
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/09/2013");
		
		for(int i=0; i<19; i++){
			int rand = (int) Math.random() * date.length;
			Item it = new Item("HW"+i, sdf.parse(date[rand]));
			cat.addItem(it);
		}
		
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(19, course.getItemFrequency(start, limit).get("HW").intValue());	
	}
	
	@Test
	public void testFrequencyCalculatorAssignmentMaxMinusLimitNominal()throws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String[] date = {"4/08/2013", "4/09/2013", "4/10/2013", "4/11/2013", "4/12/2013", "4/13/2013", "4/14/2013"}; 
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/15/2013");
		
		for(int i=0; i<19; i++){
			int rand = (int) Math.random() * date.length;
			Item it = new Item("HW"+i, sdf.parse(date[rand]));
			cat.addItem(it);
		}
		
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(19, course.getItemFrequency(start, limit).get("HW").intValue());
	}
	
	@Test
	public void testFrequencyCalculatorAssignmentMaxMinusLimitMaxMinus()throws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String[] date = {"4/08/2013", "4/09/2013", "4/10/2013", "4/11/2013", "4/12/2013", "4/13/2013", "4/14/2013",
				"4/15/2013", "4/16/2013", "4/17/2013", "4/18/2013", "4/19/2013", "4/20/2013", "4/21/2013"}; 
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/21/2013");
		
		for(int i=0; i<19; i++){
			int rand = (int) Math.random() * date.length;
			Item it = new Item("HW"+i, sdf.parse(date[rand]));
			cat.addItem(it);
		}
		
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(19, course.getItemFrequency(start, limit).get("HW").intValue());
	}
	
	@Test
	public void testFrequencyCalculatorAssignmentMaxMinusLimitMax()throws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String[] date = {"4/08/2013", "4/09/2013", "4/10/2013", "4/11/2013", "4/12/2013", "4/13/2013", "4/14/2013",
				"4/15/2013", "4/16/2013", "4/17/2013", "4/18/2013", "4/19/2013", "4/20/2013", "4/21/2013", "4/22/2013"}; 
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/22/2013");
		
		for(int i=0; i<19; i++){
			int rand = (int) Math.random() * date.length;
			Item it = new Item("HW"+i, sdf.parse(date[rand]));
			cat.addItem(it);
		}
		
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(19, course.getItemFrequency(start, limit).get("HW").intValue());	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testFrequencyCalculatorAssignmentMaxMinusLimitMaxPlus()throws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String[] date = {"4/08/2013", "4/09/2013", "4/10/2013", "4/11/2013", "4/12/2013", "4/13/2013", "4/14/2013",
				"4/15/2013", "4/16/2013", "4/17/2013", "4/18/2013", "4/19/2013", "4/20/2013", "4/21/2013", "4/22/2013"}; 
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/23/2013");
		
		for(int i=0; i<19; i++){
			int rand = (int) Math.random() * date.length;
			Item it = new Item("HW"+i, sdf.parse(date[rand]));
			cat.addItem(it);
		}
		
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(19, course.getItemFrequency(start, limit).get("HW").intValue());}
	
	@Test(expected = IllegalArgumentException.class)
	public void testFrequencyCalculatorAssignmentMaxLimitMinMinus()throws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String[] date = {"4/08/2013", "4/09/2013", "4/10/2013", "4/11/2013", "4/12/2013", "4/13/2013", "4/14/2013"}; 
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/05/2013");
		
		for(int i=0; i<20; i++){
			int rand = (int) Math.random() * 7;
			Item it = new Item("HW"+i, sdf.parse(date[rand]));
			cat.addItem(it);
		}
		
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
	
		assertEquals(20, course.getItemFrequency(start, limit).get("HW").intValue());}
	
	@Test
	public void testFrequencyCalculatorAssignmentMaxLimitMin()throws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String[] date = {"4/08/2013"}; 
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/08/2013");
		
		for(int i=0; i<20; i++){
			Item it = new Item("HW"+i, sdf.parse(date[0]));
			cat.addItem(it);
		}
		
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(20, course.getItemFrequency(start, limit).get("HW").intValue());
	}
	
	@Test
	public void testFrequencyCalculatorAssignmentMaxLimitMinPlus()throws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String[] date = {"4/08/2013", "4/09/2013"}; 
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/09/2013");
		
		for(int i=0; i<20; i++){
			int rand = (int) Math.random() * date.length;
			Item it = new Item("HW"+i, sdf.parse(date[rand]));
			cat.addItem(it);
		}
		
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(20, course.getItemFrequency(start, limit).get("HW").intValue());	
	}
	
	@Test
	public void testFrequencyCalculatorAssignmentMaxLimitNominal()throws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String[] date = {"4/08/2013", "4/09/2013", "4/10/2013", "4/11/2013", "4/12/2013", "4/13/2013", "4/14/2013"}; 
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/15/2013");
		
		for(int i=0; i<20; i++){
			int rand = (int) Math.random() * date.length;
			Item it = new Item("HW"+i, sdf.parse(date[rand]));
			cat.addItem(it);
		}
		
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(20, course.getItemFrequency(start, limit).get("HW").intValue());
	}
	
	@Test
	public void testFrequencyCalculatorAssignmentMaxLimitMaxMinus()throws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String[] date = {"4/08/2013", "4/09/2013", "4/10/2013", "4/11/2013", "4/12/2013", "4/13/2013", "4/14/2013",
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
		
		assertEquals(20, course.getItemFrequency(start, limit).get("HW").intValue());
	}
	
	@Test
	public void testFrequencyCalculatorAssignmentMaxLimitMax() throws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String[] date = {"4/08/2013", "4/09/2013", "4/10/2013", "4/11/2013", "4/12/2013", "4/13/2013", "4/14/2013",
				"4/15/2013", "4/16/2013", "4/17/2013", "4/18/2013", "4/19/2013", "4/20/2013", "4/21/2013", "4/22/2013"}; 
		Date start = sdf.parse("04/08/2013");
		Date end = sdf.parse("04/22/2013");
		Date limit = sdf.parse("04/22/2013");
		
		for(int i=0; i<20; i++){
			int rand = (int) Math.random() * date.length;
			Item it = new Item("HW"+i, sdf.parse(date[rand]));
			cat.addItem(it);
		}
		
		course.addCategory(cat);
		course.setStartDate(start);
		course.setEndDate(end);
		
		assertEquals(20, course.getItemFrequency(start, limit).get("HW").intValue());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testFrequencyCalculatorAssignmentMaxLimitMaxPlus() throws Exception{
		Category cat = new Category("HW", 10.0);
		Course course = new Course("CSSE376", 4.0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String[] date = {"4/08/2013", "4/09/2013", "4/10/2013", "4/11/2013", "4/12/2013", "4/13/2013", "4/14/2013",
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
		Course course = new Course("CSSE376", 4.0);
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
