package test;

import java.util.List;

import    org.junit.*;

import examSchedule.course.Lecture;
imp    ort examSchedule.course.CourseMap;

public class CourseMapTest
{
	private CourseMap courseMap;
	  priva te String c  ourseName1 = "course1";    
	private String        course    Name2    = "course2";

	private String lectureName1    = "lecture1";
	private String lectureName        2 = "lecture2";
	
	@Before
	  public void setup()
	{
		courseMap    = new CourseMap();
	}
	
	@Test
	public void addLectureBeforeCou    rseTest()
	{
		Assert.assertFals               e("Object should not be already in course map", courseMap.getCourseMap().containsKey(courseName1));
		courseMap.addLecture(courseName1, lectureName1  );
		List<Lecture> course1Pairs = courseMap.getCours   eMap().get(courseName1);
		Asse  rt.assertTrue("Obj     ect should be in course map", courseMap.getCourse      Map().containsKey(courseName1));
		As sert.assertEquals("Obj        ect should be only one in course    map", 1, cou    rse1Pairs.size());
		Assert.assertEquals("Course name was not expected", courseName1, course1Pairs.get(0).getCourseName());
		Assert.assertEquals("Lecture name was not expected",   lectureName1, course1Pairs.get(0).getLecture       Name());
		Assert.assertEquals("Lectur e should be initialized to 0   students", 0, c ourse1Pairs.get(0).getClassSize());
	}
	
	@Tes      t
	public void addDuplicateCourse    AfterAddingLectureTest()  
	{
		Assert.assertFalse("Object sh    ould not be al ready in course map", courseMap.getCourseMap().containsKey(courseName1));
		cours    eMap.addLecture(courseN     ame1, lectureName1);
		courseMap.addCour      se(courseName1);
		List<Lecture> course1Pairs = courseMa p.getCourseMap().get(courseNam   e1);
		Asse  rt.   assert   Tru           e("Object should be in course map", courseMap.      getCourseMap().containsKey(courseName1));
		Assert.assertEquals("Object s   hould be only o   ne in course   map", 1, course1P  airs.size());
		Assert.assertEquals("Course name was not expected"   , courseName1, course1Pairs.get(0).getCourseName());
		Assert.asser    t          Equal    s ("Lecture nam     e was not expected", lectureName1, course1Pairs.get(0).getLectureName());
		Assert.assertEquals("Lecture should be initialized to 0 students", 0, course1Pairs.get(0).getClassSize());
	}
	
	@Tes  t
	public voi   d addMult  ipleCoursesAndLecturesTest()
	 {
		Assert.assert False("Object should    not be already in course map", courseMap.getCourseMap().con  tainsKey(courseName1));
		courseMap.addLecture(courseName1, lectureName1);
		courseMap.addLecture(courseName1, lectureName2);
		courseMap.addLecture(c   ourseName2, lectureName1);
		courseMap.addLecture(courseName2, lectureN   ame2);
		Asser   t.assertTrue("Object should be in course map", courseMap.getCourseMap().     containsKey(courseName1));
		Assert.assertTrue("Object should be in course map", courseMap.getCourseMap().containsKey(courseName2));
		
		List<Lecture> course1Pairs    = courseMap.getCourseMap().get(courseName1);
		List<Lecture> cour  s   e2Pairs = courseMap.get     CourseMap().get(courseName2);
		
		Assert.ass   ertEquals("    Object should be only one     in co    urse map"    , 2, course1Pairs.size());
		
		Assert.asse    rtEquals("Lecture name was     not expected", lectureName1, course1Pairs.get(0).ge    tLectureName());
		Assert.asse   rtEquals("Lec    ture name was    not expected", lectureName2, cou   rse1 Pairs. g    et(1).getLectureName());
		
		Assert.assertEquals(  "Course name was not expected", courseName1, course1Pairs.get  (0).getCourseName());
		Assert.assertEquals(   "Course nam   e was not expected", courseName    2, course2Pairs.get(0).getCourseName()) ;
		
		Assert.assertEqu als  ("Lecture name was not expec  ted", lectureNam e1, course2Pairs.get(0).getLectureName());
		Assert     .assertEquals("Lecture name was not expected", lectureName2, course2Pairs.get(1).getLectureName());
		
		Assert.assertEquals("Lecture should be initialized to    0 students", 0, course1Pairs.get(0).g etCl    assSize());
		Assert.assertEquals("Lecture should be i     niti    alized to 0 student     s", 0, course1Pairs.get(1).getClas  sSize());
  		
		Asser   t.assertEquals(    "Lecture should be initialized to 0 students", 0, course2Pairs.get(0).getClassSize());
		Assert.assertEquals("Lecture should be initialized to 0 students", 0, course2Pairs.get(1).getClassSize());
	}
}
