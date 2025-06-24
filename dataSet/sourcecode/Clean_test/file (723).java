package test;

import java.util.List;

import org.junit.*;

import examSchedule.course.Lecture;
import examSchedule.course.CourseMap;

public class CourseMapTest
{
	private CourseMap courseMap;
	private String courseName1 = "course1";
	private String courseName2 = "course2";

	private String lectureName1 = "lecture1";
	private String lectureName2 = "lecture2";
	
	@Before
	public void setup()
	{
		courseMap = new CourseMap();
	}
	
	@Test
	public void addLectureBeforeCourseTest()
	{
		Assert.assertFalse("Object should not be already in course map", courseMap.getCourseMap().containsKey(courseName1));
		courseMap.addLecture(courseName1, lectureName1);
		List<Lecture> course1Pairs = courseMap.getCourseMap().get(courseName1);
		Assert.assertTrue("Object should be in course map", courseMap.getCourseMap().containsKey(courseName1));
		Assert.assertEquals("Object should be only one in course map", 1, course1Pairs.size());
		Assert.assertEquals("Course name was not expected", courseName1, course1Pairs.get(0).getCourseName());
		Assert.assertEquals("Lecture name was not expected", lectureName1, course1Pairs.get(0).getLectureName());
		Assert.assertEquals("Lecture should be initialized to 0 students", 0, course1Pairs.get(0).getClassSize());
	}
	
	@Test
	public void addDuplicateCourseAfterAddingLectureTest()
	{
		Assert.assertFalse("Object should not be already in course map", courseMap.getCourseMap().containsKey(courseName1));
		courseMap.addLecture(courseName1, lectureName1);
		courseMap.addCourse(courseName1);
		List<Lecture> course1Pairs = courseMap.getCourseMap().get(courseName1);
		Assert.assertTrue("Object should be in course map", courseMap.getCourseMap().containsKey(courseName1));
		Assert.assertEquals("Object should be only one in course map", 1, course1Pairs.size());
		Assert.assertEquals("Course name was not expected", courseName1, course1Pairs.get(0).getCourseName());
		Assert.assertEquals("Lecture name was not expected", lectureName1, course1Pairs.get(0).getLectureName());
		Assert.assertEquals("Lecture should be initialized to 0 students", 0, course1Pairs.get(0).getClassSize());
	}
	
	@Test
	public void addMultipleCoursesAndLecturesTest()
	{
		Assert.assertFalse("Object should not be already in course map", courseMap.getCourseMap().containsKey(courseName1));
		courseMap.addLecture(courseName1, lectureName1);
		courseMap.addLecture(courseName1, lectureName2);
		courseMap.addLecture(courseName2, lectureName1);
		courseMap.addLecture(courseName2, lectureName2);
		Assert.assertTrue("Object should be in course map", courseMap.getCourseMap().containsKey(courseName1));
		Assert.assertTrue("Object should be in course map", courseMap.getCourseMap().containsKey(courseName2));
		
		List<Lecture> course1Pairs = courseMap.getCourseMap().get(courseName1);
		List<Lecture> course2Pairs = courseMap.getCourseMap().get(courseName2);
		
		Assert.assertEquals("Object should be only one in course map", 2, course1Pairs.size());
		
		Assert.assertEquals("Lecture name was not expected", lectureName1, course1Pairs.get(0).getLectureName());
		Assert.assertEquals("Lecture name was not expected", lectureName2, course1Pairs.get(1).getLectureName());
		
		Assert.assertEquals("Course name was not expected", courseName1, course1Pairs.get(0).getCourseName());
		Assert.assertEquals("Course name was not expected", courseName2, course2Pairs.get(0).getCourseName());
		
		Assert.assertEquals("Lecture name was not expected", lectureName1, course2Pairs.get(0).getLectureName());
		Assert.assertEquals("Lecture name was not expected", lectureName2, course2Pairs.get(1).getLectureName());
		
		Assert.assertEquals("Lecture should be initialized to 0 students", 0, course1Pairs.get(0).getClassSize());
		Assert.assertEquals("Lecture should be initialized to 0 students", 0, course1Pairs.get(1).getClassSize());
		
		Assert.assertEquals("Lecture should be initialized to 0 students", 0, course2Pairs.get(0).getClassSize());
		Assert.assertEquals("Lecture should be initialized to 0 students", 0, course2Pairs.get(1).getClassSize());
	}
}
