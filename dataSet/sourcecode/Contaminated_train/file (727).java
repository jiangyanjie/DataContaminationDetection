package test;

import java.util.ArrayList;
import java.util.List;

import examSchedule.assignment.Assignment;
import examSchedule.assignment.Room;
import examSchedule.assignment.Session;
import examSchedule.assignment.Student;
import examSchedule.course.Instructor;
import examSchedule.course.Lecture;
import examSchedule.date.Time;
import examSchedule.solution.Constraints;

import org.junit.*;

public class ConstraintTests {
	

	Lecture lecture1 = new Lecture("CPSC433", "L01");
	Lecture lecture2 = new Lecture("CPSC413", "L02");
	Lecture lecture3 = new Lecture("GLGY209", "L01");
	Lecture lecture4 = new Lecture("PSYC383", "L02");
	Session session1 = new Session("S1");
	Session session2 = new Session("S2");
	Session session3 = new Session("S3");
	Session session4 = new Session("S4");
	
	@Test
	// Testing if SC triggers when a single student has 2 exams at 6:00 and is in exams for 6 hours
	public void SoftCon1failtest1() {
		Student student = new Student("Bob");
		Time time = new Time.Builder("6:00").build();
		session1.setTime(time);
		session1.setDay("M");
		lecture1.setSession(session1);
		session1.setLength(3);
		student.enroll(lecture1);
		
		session2.setTime(time);
		session2.setDay("M");
		session2.setLength(3);
		student.enroll(lecture2);

		
		int violation = Constraints.calcSoftOne(session2, lecture2);
		Assert.assertEquals("Soft constraint violation is incorrect", 200, violation);
	}
	
	@Test
	// Testing if SC triggers when a single student has 1 exam at 6:00, another at 6:30
	public void SoftCon1failtest2() {
		Student student = new Student("Bob");
		Time time = new Time.Builder("6:00").build();
		session1.setTime(time);
		session1.setDay("M");
		session1.setLength(3);
		lecture1.setSession(session1);
		student.enroll(lecture1);
		
		Time time2 = new Time.Builder("6:30").build();
		session2.setTime(time2);
		session2.setDay("M");
		student.enroll(lecture2);

		
		int violation = Constraints.calcSoftOne(session2, lecture2);
		Assert.assertEquals("Soft constraint violation is incorrect", 100, violation);
	}
	
	@Test
	// Testing if SC triggers when:
	// Student 1: Exams at 6:00 and 6:30
	// Student 2: Exams at 5:00 and 6:30
	// Both have exams for longer than 5 hours a day
	public void SoftCon1failtest3() {
		Student student = new Student("Bob");
		Time time = new Time.Builder("6:00").build();
		session1.setTime(time);
		session1.setDay("T");
		session1.setLength(3);
		lecture1.setSession(session1);
		student.enroll(lecture1);

		Student student2 = new Student("Fred");
		Time time2 = new Time.Builder("5:00").build();
		session2.setTime(time2);
		session2.setDay("T");
		session2.setLength(3);
		lecture2.setSession(session2);
		student2.enroll(lecture2);
		
		Time time3 = new Time.Builder("6:30").build();
		session3.setTime(time3);
		session3.setDay("T");
		session3.setLength(3);
		
		student.enroll(lecture3);
		student2.enroll(lecture3);
		
		int violation = Constraints.calcSoftOne(session3, lecture3);
		Assert.assertEquals("Soft constraint violation is incorrect", 300, violation);
	}
	
	@Test
	// Testing if SC passes when a student has two non-conflicting exams
	public void SoftCon1passtest1() {
		Student student = new Student("Bob");
		Time time = new Time.Builder("9:00").build();
		session1.setTime(time);
		session1.setDay("M");
		session1.setLength(3);
		lecture1.setSession(session1);
		student.enroll(lecture1);
		
		Time time2 = new Time.Builder("13:00").build();
		session2.setTime(time2);
		session2.setDay("M");
		session2.setLength(1);
		student.enroll(lecture2);

		int violation = Constraints.calcSoftOne(session2, lecture2);
		Assert.assertEquals("Soft constraint violation is incorrect", 0, violation);
	}
	
	@Test
	// Testing if SC trigger when a student has two back to back exams
	public void SoftCon1failtest4() {
		Student student = new Student("Bob");
		Time time = new Time.Builder("9:00").build();
		session1.setTime(time);
		session1.setDay("M");
		session1.setLength(3);
		lecture1.setSession(session1);
		student.enroll(lecture1);
		
		Time time2 = new Time.Builder("12:00").build();
		session2.setTime(time2);
		session2.setDay("M");
		session2.setLength(1);
		student.enroll(lecture2);

		int violation = Constraints.calcSoftOne(session2, lecture2);
		Assert.assertEquals("Soft constraint violation is incorrect", 50, violation);
	}
	
	@Test
	// Single instructor supervising two different rooms at 9:00
	public void SoftCon2failtest1() {
		Instructor instructor = new Instructor("John");
		instructor.addInstructedLecture(lecture1);
		lecture1.setInstructor(instructor);
		Time time = new Time.Builder("9:00").build();
		session1.setTime(time);
		session1.setDay("M");
		Room room = new Room("ST148");
		room.setCapacity(250);
		session1.setRoom(room);
		lecture1.setSession(session1);
				
		lecture2.setInstructor(instructor);
		instructor.addInstructedLecture(lecture2);
		session2.setTime(time);
		session2.setDay("M");
		Room room2 = new Room("ST140");
		room2.setCapacity(250);
		session2.setRoom(room2);

		int violation = Constraints.calcSoftTwo(session2, lecture2);
		Assert.assertEquals("Soft constraint violation is incorrect", 20, violation);
	}
	
	
	@Test
	// Single instructor supervising two different rooms at 9:00 and 9:30
	public void SoftCon2failtest2() {
		Instructor instructor = new Instructor("John");
		instructor.addInstructedLecture(lecture1);
		lecture1.setInstructor(instructor);
		Time time = new Time.Builder("9:00").build();
		session1.setTime(time);
		session1.setDay("M");
		session1.setLength(3);
		Room room = new Room("ST148");
		room.setCapacity(250);
		session1.setRoom(room);
		lecture1.setSession(session1);
				
		lecture2.setInstructor(instructor);
		instructor.addInstructedLecture(lecture2);
		Time time2 = new Time.Builder("9:30").build();
		session2.setTime(time2);
		session2.setDay("M");
		Room room2 = new Room("ST140");
		room2.setCapacity(250);
		session2.setRoom(room2);

		int violation = Constraints.calcSoftTwo(session2, lecture2);
		Assert.assertEquals("Soft constraint violation is incorrect", 20, violation);
	}
	
	
	@Test
	// Two instructors supervising two different rooms at 9:00 and 9:30 and 10:00 and 10:30 respectively
	// Single instructor supervising two different rooms at 9:00 and 9:30
	public void SoftCon2failtest3() {
		Instructor instructor = new Instructor("John");
		instructor.addInstructedLecture(lecture1);
		lecture1.setInstructor(instructor);
		Time time = new Time.Builder("9:00").build();
		session1.setTime(time);
		session1.setDay("M");
		session1.setLength(3);
		Room room = new Room("ST148");
		room.setCapacity(250);
		session1.setRoom(room);
		lecture1.setSession(session1);
				
		lecture2.setInstructor(instructor);
		instructor.addInstructedLecture(lecture2);
		Time time2 = new Time.Builder("9:30").build();
		session2.setTime(time2);
		session2.setDay("M");
		Room room2 = new Room("ST140");
		room2.setCapacity(250);
		session2.setRoom(room2);
		
		Instructor instructor2 = new Instructor("Bill");
		instructor2.addInstructedLecture(lecture3);
		lecture3.setInstructor(instructor);
		Time time3 = new Time.Builder("10:00").build();
		session3.setTime(time3);
		session3.setDay("M");
		session3.setLength(3);
		Room room3 = new Room("ST100");
		room3.setCapacity(250);
		session3.setRoom(room3);
		lecture3.setSession(session3);
				
		lecture4.setInstructor(instructor2);
		instructor2.addInstructedLecture(lecture4);
		Time time4 = new Time.Builder("10:30").build();
		session4.setTime(time4);
		session4.setDay("M");
		Room room4 = new Room("ST102");
		room4.setCapacity(250);
		session4.setRoom(room4);

		int violation = Constraints.calcSoftTwo(session2, lecture2) + Constraints.calcSoftTwo(session4, lecture4);
		Assert.assertEquals("Soft constraint violation is incorrect", 40, violation);

	}
	
	@Test
	// Testing if SC passes when an instructor is supervising two exams on different days
	public void SoftCon2passtest1() {
		Instructor instructor = new Instructor("John");
		instructor.addInstructedLecture(lecture1);
		lecture1.setInstructor(instructor);
		Time time = new Time.Builder("9:00").build();
		session1.setTime(time);
		session1.setDay("M");
		Room room = new Room("ST148");
		room.setCapacity(250);
		session1.setRoom(room);
		lecture1.setSession(session1);
		
		
		lecture2.setInstructor(instructor);
		instructor.addInstructedLecture(lecture2);
		session2.setTime(time);
		session2.setDay("T");
		Room room2 = new Room("ST140");
		room2.setCapacity(250);
		session2.setRoom(room2);

		
		int violation = Constraints.calcSoftTwo(session2, lecture2);
		Assert.assertEquals("Soft constraint violation is incorrect", 0, violation);
	}
	
	@Test
	// Testing if SC passes when an instructor is supervising two back to back exams
	public void SoftCon2passtest2() {
		Instructor instructor = new Instructor("John");
		instructor.addInstructedLecture(lecture1);
		lecture1.setInstructor(instructor);
		Time time = new Time.Builder("9:00").build();
		session1.setTime(time);
		session1.setDay("M");
		session1.setLength(3);
		Room room = new Room("ST148");
		room.setCapacity(250);
		session1.setRoom(room);
		lecture1.setSession(session1);
		
		
		lecture2.setInstructor(instructor);
		instructor.addInstructedLecture(lecture2);
		Time time2 = new Time.Builder("12:00").build();
		session2.setTime(time2);
		session2.setDay("M");
		Room room2 = new Room("ST140");
		room2.setCapacity(250);
		session2.setRoom(room2);

		
		int violation = Constraints.calcSoftTwo(session2, lecture2);
		Assert.assertEquals("Soft constraint violation is incorrect", 0, violation);
	}
	
	@Test
	// Testing if two lectures for the same course having different exam times causes this SC to be triggered
	public void SoftCon3failtest() {
		session1.setDay("M");
		Time time = new Time.Builder("9:00").build();
		session1.setTime(time);
		lecture1.setSession(session1);
		List<Lecture> listofLecs = new ArrayList<Lecture>();
		listofLecs.add(lecture1);
		
		Lecture lecture2 = new Lecture("CPSC433", "L02");
		Time time2 = new Time.Builder("17:00").build();
		session2.setTime(time2);
		session2.setDay("M");
		int violation = Constraints.calcSoftThree(listofLecs, session2, lecture2);
		Assert.assertEquals("Soft constraint violation is incorrect", 50, violation);
	}
	
	@Test
	// Testing if two lectures for the same course having different exam times but ending at the same time causes this SC to be triggered
	public void SoftCon3failtest2() {
		session1.setDay("M");
		Time time = new Time.Builder("9:00").build();
		session1.setTime(time);
		lecture1.setSession(session1);
		session1.setLength(2);
		List<Lecture> listofLecs = new ArrayList<Lecture>();
		listofLecs.add(lecture1);
		
		Lecture lecture2 = new Lecture("CPSC433", "L02");
		Time time2 = new Time.Builder("8:00").build();
		session2.setTime(time2);
		session2.setDay("M");
		session2.setLength(3);
		int violation = Constraints.calcSoftThree(listofLecs, session2, lecture2);
		Assert.assertEquals("Soft constraint violation is incorrect", 50, violation);
	}
	
	@Test
	// Testing if SC passes when two lectures for the same course have the same exam time
	public void SoftCon3passtest() {
		session1.setDay("M");
		Time time = new Time.Builder("9:00").build();
		session1.setTime(time);
		lecture1.setSession(session1);
		List<Lecture> listofLecs = new ArrayList<Lecture>();
		listofLecs.add(lecture1);
		
		Lecture lecture2 = new Lecture("CPSC433", "L02");
		session2.setTime(time);
		session2.setDay("M");
		int violation = Constraints.calcSoftThree(listofLecs, session2, lecture2);
		Assert.assertEquals("Soft constraint violation is incorrect", 0, violation);
	}
	
	@Test
	// Testing if SC passes when two lectures for the same course have the same exam time but different lengths
	public void SoftCon3passtest2() {
		session1.setDay("M");
		Time time = new Time.Builder("9:00").build();
		session1.setTime(time);
		session1.setLength(3);
		lecture1.setSession(session1);
		List<Lecture> listofLecs = new ArrayList<Lecture>();
		listofLecs.add(lecture1);
		
		Lecture lecture2 = new Lecture("CPSC433", "L02");
		session2.setTime(time);
		session2.setDay("M");
		session2.setLength(2);
		int violation = Constraints.calcSoftThree(listofLecs, session2, lecture2);
		Assert.assertEquals("Soft constraint violation is incorrect", 0, violation);
	}
	
	@Test
	// Testing if SC is violated when student has two exams of length 6 in total on the same day 
	public void SoftCon4failtest1() {
		Student student = new Student("Bob");
		lecture1.setExamLength(3);
		session1.setLength(3);
		session1.setDay("M");
		lecture1.setSession(session1);
		student.enroll(lecture1);
		
		session2.setLength(3);
		session2.setDay("M");
		student.enroll(lecture2);

		
		int violation = Constraints.calcSoftFour(session2, lecture2);
		Assert.assertEquals("Soft constraint violation is incorrect", 50, violation);
	}
	
	@Test
	// Testing if SC is violated when two students have two exams of length 6 in total on the same day 
	public void SoftCon4failtest2() {
		Student student = new Student("Bob");
		lecture1.setExamLength(3);
		session1.setLength(3);
		session1.setDay("M");
		lecture1.setSession(session1);
		student.enroll(lecture1);
		
		
		Student student2 = new Student("Joe");
		lecture3.setExamLength(3);
		session3.setLength(3);
		session3.setDay("M");
		student2.enroll(lecture3);

		lecture2.setExamLength(3);
		session2.setLength(3);
		session2.setDay("M");
		
		student.enroll(lecture2);
		student2.enroll(lecture2);
		
		int violation = Constraints.calcSoftFour(session2, lecture2);
		Assert.assertEquals("Soft constraint violation is incorrect", 50, violation);
	}
	
	@Test
	// Testing if SC passes if a student has a total of 5 exam hours in a day
	public void SoftCon4passtest() {
		Student student = new Student("Bob");
		lecture1.setExamLength(3);
		session1.setLength(3);
		session1.setDay("M");
		lecture1.setSession(session1);
		student.enroll(lecture1);
		
		
		session2.setLength(2);
		session2.setDay("M");
		student.enroll(lecture2);

		int violation = Constraints.calcSoftFour(session2, lecture2);
		Assert.assertEquals("Soft constraint violation is incorrect", 0, violation); 
	}
	
	@Test
	// Testing if SC passes if a student has a total of 4 exam hours in a day
	public void SoftCon4passtest2() {
		Student student = new Student("Bob");
		lecture1.setExamLength(3);
		session1.setLength(2);
		session1.setDay("M");
		lecture1.setSession(session1);
		student.enroll(lecture1);
		
		
		session2.setLength(2);
		session2.setDay("M");
		student.enroll(lecture2);

		int violation = Constraints.calcSoftFour(session2, lecture2);
		Assert.assertEquals("Soft constraint violation is incorrect", 0, violation); 
	}
	
	@Test
	// Test if SC triggers when student has a 3 hour exam at 8 and then another exam at 11
	public void SoftCon5failtest(){
		Student student = new Student("Bob");
		Time time = new Time.Builder("8:00").build();
		session1.setTime(time);
		lecture1.setExamLength(3);
		session1.setLength(3);
		session1.setDay("M");
		lecture1.setSession(session1);
		student.enroll(lecture1);
		
		Time time2 = new Time.Builder("11:00").build();
		
		session2.setTime(time2);
		lecture2.setExamLength(3);
		session2.setLength(3);
		session2.setDay("M");
		student.enroll(lecture2);
		
		int violation = Constraints.calcSoftFive(session2, lecture2);
		Assert.assertEquals("Soft constraint violation is incorrect", 50, violation);
	}
	
	@Test
	// Test if SC triggers when two students have exams right before another exam at 11 (one that starts at 9 and one that starts at 8)
	public void SoftCon5failtest2(){
		Student student = new Student("Bob");
		Time time = new Time.Builder("8:00").build();
		session1.setTime(time);
		session1.setLength(3);
		session1.setDay("M");
		lecture1.setSession(session1);
		student.enroll(lecture1);
		
		
		Student student2 = new Student("Sam");
		Time time2 = new Time.Builder("9:00").build();
		session2.setTime(time2);
		session2.setLength(2);
		session2.setDay("M");
		lecture2.setSession(session2);
		student2.enroll(lecture2);
		
		Time time3 = new Time.Builder("11:00").build();
				
		session3.setTime(time3);
		session3.setLength(3);
		session3.setDay("M");
		student.enroll(lecture3);
		student2.enroll(lecture3);
		
		int violation = Constraints.calcSoftFive(session3, lecture3);
		Assert.assertEquals("Soft constraint violation is incorrect", 100, violation);
	}
	
	@Test
	// Testing if SC passes when a student doesn't have back to back exams
	public void SoftCon5passtest(){
		Student student = new Student("Bob");
		Time time = new Time.Builder("8:00").build();
		session1.setTime(time);
		lecture1.setExamLength(3);
		session1.setLength(3);
		session1.setDay("M");
		//lecture1.enrollStudent(student);
		lecture1.setSession(session1);
		student.enroll(lecture1);
		
		Time time2 = new Time.Builder("12:00").build();
		
		session2.setTime(time2);
		lecture2.setExamLength(3);
		session2.setLength(3);
		session2.setDay("M");
		//lecture2.enrollStudent(student);
		student.enroll(lecture2);
		
		int violation = Constraints.calcSoftFive(session2, lecture2);
		Assert.assertEquals("Soft constraint violation is incorrect", 0, violation);
	}
	
	
	@Test
	// Testing if the SC triggers when two lectures assigned to a single session have different lengths
	public void SoftCon6failtest1() {
		
		session1.setLength(3);
		lecture1.setExamLength(2);
		lecture2.setExamLength(3);
		session1.addLecture(lecture1);
		lecture1.setSession(session1);
		int violation = Constraints.calcSoftSix(session1, lecture2);
		Assert.assertEquals("Soft constraint violation is incorrect", 20, violation);
	}
	
	
	@Test
	// Testing if the SC triggers when two lectures assigned to a single session have different lengths
	public void SoftCon6failtest2() {
		session1.setLength(3);
		lecture1.setExamLength(3);
		lecture2.setExamLength(2);
		session1.addLecture(lecture1);
		lecture1.setSession(session1);
		int violation = Constraints.calcSoftSix(session1, lecture2);
		Assert.assertEquals("Soft constraint violation is incorrect", 20, violation);
	}
	
	@Test
	// Testing if the SC passes when two lectures assigned to a single session have the same length
	public void SoftCon6passtest() {
		session1.setLength(3);
		lecture1.setExamLength(3);
		lecture2.setExamLength(3);
		session1.addLecture(lecture1);
		lecture1.setSession(session1);
		int violation = Constraints.calcSoftSix(session1, lecture2);
		Assert.assertEquals("Soft constraint violation is incorrect", 0, violation);
	}
	
	@Test
	// Test if SC triggers when a 2 hour exam is scheduled into a 3 hour session
	public void SoftCon7failtest() {
		session1.setLength(3);
		lecture1.setExamLength(2);	
		int violation = Constraints.calcSoftSeven(session1, lecture1);
		Assert.assertEquals("Soft constraint violation is incorrect", 5, violation);
	}
	
	@Test
	// Test if SC passes when a 3 hour exam is scheduled into a 3 hour session
	public void SoftCon7passtest() {
		session1.setLength(3);
		lecture1.setExamLength(3);	
		int violation = Constraints.calcSoftSeven(session1, lecture1);
		Assert.assertEquals("Soft constraint violation is incorrect", 0, violation);
	}
	
	//Assignment assignment2 = new Assignment();
}
