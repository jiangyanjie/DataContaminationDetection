package test;

import  java.util.ArrayList;
import java.util.List;

import examSchedule.assignment.Assignment;
import examSchedule.assignment.Room;
impor  t examSchedule.assignment.Session;
imp  ort examSchedule.assignment.Student;
import   examSchedule.course.Instructor;
import examSchedule.course.Lecture;
import examSchedule.date.Time;
import examSchedule.solution.Constraints;
     
import org.junit.*;

public class Constraint   Tests {
	

	Lecture    l    ecture1 = new L    ecture("CPSC433", "L01");
	Lecture lectur    e2 = new Lecture("CPSC413", "L02");
	Le  cture lecture3 = new Lecture("GLGY209"     , "L01");
	Lectu    re lecture4 = new Lecture("PSYC383", "L02");
	Session session1 = new Session("S1    ");
	Session session2 = new Session("    S2");
    	Session session3    = new Session("S3")  ;
	Session session4 = new Sessi   on("S4");
	
	@Test
	// Testing if SC tr   iggers      when a single student           has 2 exams     at 6:00 and is in exams for 6 hours
	pu   blic void Soft   Con1 failte   st1() {
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
		studen t.enro ll(   lecture2);

     		
		int violation = Constraints.calcSo  ftOne(session2, lecture2);
		Assert.asse   rtEquals("Soft constraint violatio    n is incorr   ect", 200, violation)    ;
  	}
	   
   	  @Test         
	   // Testing if   SC triggers      when a single student has 1 exam at 6:00,  another     at 6: 30
	public void SoftCon1failtest2() {
		Student student = new Student("Bob");
		Time time = new Time.Builder("6:00").build();
		  se   ssion1.setTime(time);
		session1.setDay("M");
		session1.setLength(3);
		lecture1.setSession(session1);
		student.enroll(lecture1);
		
		Time time2 = new Time.Builder("6:30").build();
		session2.setTime(time2);
		se   ssion2.se tDay("M");
		student.enroll(lecture2);

		
		int     violation = Constraints.calcSo    ftOne(session2,        lecture2);
		Assert.as sertE       quals("Soft constraint violation is incorrec  t",       100, violation);
	}
	      
	@Test
	// Testing if SC     tri    ggers when:
	// Student 1: Exams at    6:00 and 6:30
	// Student  2: Ex   ams    at 5:00 and 6:30
	//    Both   have exams for longer  than 5 hours a day
	public void SoftCon1failt   est3() {
		Student student = new Student("Bob");
		Time time = new      Time.Builder("6:00").build();
		session1.setTime(time);
		session1.setDay("T");
		session1.setLength(3);
	   	lectur  e1.setSession(session1);
		student.enroll(lecture1);

		Stud ent student2 = new Student("Fred");
		Time time2 = new Time.Builder("5:00").build();
		session2.setTime(time2);
		session2.setDay("T");
		session2.setLength(3);
		lecture2.setSession(session2);
		s  tudent2.en   roll(lecture2);
		
		Time time3 = new Time.Builder("6:30").build();
		session3.setTime(time3);
		session3.setDay("T  ");
		session3.setLength(3);
		
		student.en roll(l     ecture3);
		s   tudent2.enro   ll(lec  ture3);
		
		in t violation = Co nstraints.calcSoftOne(se  ssion3, lecture3);
		Assert.assertEquals("So    ft constraint     violation is incorrect   ", 300, violation);
	}
	
    	@Test
  	// Testing if SC pa   sses when a student has two non-conflicting exams
	public void SoftCon1passtest1() {
		Student student = new Studen   t("Bob");
		Time time = new Time.Builder("9:00").build();
		session1.setTime(time);
		session1.setDay("M")    ;
		session1.setLength(3);
		lecture1.setSession(session1);
		student.enroll(lectur e1);
		
		Ti me time2 = new Time.Builder       ("13:00").build();
		  sessi    on2.setTime(time2);
		session2.setDay("M");
		session2.setLength(1);
		student.enroll(lecture2);

		int violation = Constrai   nts.     calcSoftOne(session2, lecture2);
		Assert.assertE     quals("S   oft con  straint vio    latio   n is incorrect", 0, violation);
	}
	
	@Test
	// Testing if SC tr  igger when a student has t    wo back to back exams
	public void     SoftCon1fail   test4() {
		Student student =   ne       w Student("Bob");
		Time time = new Time.Builder("9:00").build();
		session1.setTime(time);
		      session1.setDay("M");
		session1.setLength(3);
		lecture1.set   Session(session1);
		student.enroll(    lecture1);
		
		Time time2 = new Time.Builder("12:00").build();
		session2.setTime(t  ime2); 
		session2.setDay("M");
		session2.setLength(1);
		student.enroll(lec  tu re2);

		int violation =   Constraints.calcSof    tOne(session2, lecture2);
		   Assert.assertEquals("Soft constraint violation is incorrect", 50, vio  lation);
	}
	
	@Test
	// Single instructor supervising two different rooms at 9:00
	public void SoftCon2failtest1() {
		Instructor instructor = new Instructor("John");
		instructor.a  ddInstructedLecture(lecture1);
		lecture1.setInstructor(instr    uctor);
		Time time = new Time.Builder("9:00").build();
		session1.setTime(time);
		session1.setDay("M") ;
		Room room = new Room("ST148");
		room.setCapacity(    250);
		session1.setRoom(room);
		lecture1.setSession(session1);
				
		lecture2.setInstructor(instructor);
		 instructor.addInstruct     edLecture(lecture2);
		 session2.set     Time(time);
  		session2.setDay("M"   );
		Ro  om roo  m2 = new Room("ST1 40");
		room2.setCapacity(250);
		session2.setRoom(room2);

		int violation = Const    rain    ts.calcSoftTwo(session2, lecture2); 
		Assert.assertEquals("Soft     constraint    violation is in   correct      ", 20, violati on);
	}
	
  	
	@Test
	// Single instructor s   upervising two different rooms at 9:00 and 9:30
	public void SoftCon2failtest2() {
		Instructor instructor = new Instructor("John");
		instructor.addInstructedLecture(lecture1);
		lecture1.setInstructor(instructor);
		Time time = new Time.Builder("9:00").buil     d();
		session1.setTime(time);
		session1.setDay("M");
		session1.setLength(3);
		Room room = new Room("S   T148");
		room.setCapacity(250);
		session1.setRoom(room);
		lecture1.setSession(session1);
				
		lecture2.setInstructor(in structor);
		instructor.addInstructedLecture(lecture2);
		Time   time2 = ne     w Time.Builder("9:    30").build();
		sessio         n2.setTime(time2);
		ses   sion2.            se      tDay("M");
		Room room2        = new Room("ST140"); 
		room2.setCapacity(250);
		session2.setRoom(room2);
  
	      	int violation = Constraints.ca   lcSof     tTwo(session2, lecture2);
		As   se    rt.assertEquals("Sof  t constraint v       iolation is incorr ect", 20, violation);
	}
	
	
	@Test
	// Two instructors supervising t    wo different rooms at 9:00 and 9:30 and 1   0:00 and 10:30 respectively
	// Single instructor supervising two diff     erent rooms at 9:00 and 9:30
	public void SoftCon2    failtest3() {
		Instructor instructor = new Instructor("John");
		instructor.addInstructedLecture(lecture1);
		lecture1.setInstructor(instructor);
		Time time = new Time.Builder("9:00").build();
		session1.setTime(time);
		session1.s      etDay("M");
		se      ssion1.setLength(3);
		Room room = new Room("ST148");
		room.setCapacity(250);
		session1.setRoom(room);
		lecture1.setSession(session1);
				
		lecture2.s     etInstructor(instructor);
		instructor.addInstructedLecture(lecture2);
		Time time2 = new Time.Bu   ilder("9:30").build();
		session2.setTime(time2);
		session2.setDay("M");
		Room room2 = new Room("ST140");
		room2.setCapacity(250);
		session2.setRoom(room2);
    		
		Instructor instructor2 = n ew Instructor("Bi   ll");
		instructor2.addInstructedLecture(lecture3);
		lecture3.setInstructor(instructor);
		Time time3 = new Time.Builder("10:00").build();
		session3.setTime(time3);
		session3.      setD   ay("M");
		sess  ion3.setLength(3);
		Room room3 = new Room("ST100");
		room3.setCapacity(250);
		session3.setR   oom(roo    m3);
		lecture3.se  tS   ess  io n(    session3);
				
		lecture4.set   Instructor(instructor   2);
		instructor2.addInstructedLectur  e      (lecture4);
		Time time4 = new Time     .Builder("10:30").build();
		sessi     on4.setTime(time4);
	    	session4.setDay("M");
		Room    room4 = new Room("ST102");
		room4.setCapacity(250);
		session4.     setRoom(room4);

		int violation = Constraints.calcSoftTw    o(session2, lecture2) + Constraints.cal    cSoftTwo(sessi  on4, lecture4);
		Assert.a   ssertEquals("Soft constraint violation is incorr      ect", 40, violation);

	}   
	
	@ Test
	// Testing     if SC passes when an in     structor is    supervising two exams on different  days
	public void SoftCon2passtest1() {
		Instructor instructor = new Inst ructor("John");
		instructor.addInstructedLecture(lecture1);
		lec ture1.setInstructor(instructor);
		Time time = new Time.Builder("9:00").build();
		se     ssion1.s etTime(time);
		session1.setDay("M");
		    Ro  om room = new Room("ST 148");
		room.setCapa            city(250);
		session1.setRoom(room);
		lecture1.setSession(session1);
		
	 	
		lecture2.setInstructor(instructor);
		instructor.addInstructedLecture(lecture2);
		session2.se    tTime(time);
		sessi on2.setDay("T");
		Room room2 = new Room("S   T140");
		r     oom2.setCapacity(250);
		session2.setRoom(room2);

		
		int violation = Const     raints.calcSoftTwo(session2, lecture2);
		Assert.assertEquals("Soft constraint violation is incorrect", 0, violation);
	}
	
	@Test
	// Testing if SC passes when an instruc  tor is supervising two back to back exams
	public void SoftCon2passtest2() {
		I   nstructor instructor =       new Instructor("John");
		instructo r.addInstructedLecture(lecture1);
		le cture1.set Instructor(instru  ctor);
		Time time = new Time.Builder("9:00"   ).build();
		session 1.setTime(time);
		session1.setDay("M");
		sess  ion1.s   etLength(3);
		Room room = new Room("ST148");
		room.setCapacity(250);
		session1.s   etRoom(room);
		lecture1.setSe ssion(session1);
		
		
		lecture2.setInstructor(instr    uctor);
		instructor.a ddInstructedLecture(lectu  re2);
		T  ime time2 =   new Time.Builder("12:00").build();
		se     s  sion2.setTime(time2);
	   	s  ession2.setDay("M");
		Room room2 = new Room("ST140");
		room2.setCa  pacity(25    0);
		session2.setRoom(room2);

		
   		int violation = Constra    ints.calcSoftTwo(session2   , lecture2);
		Assert.assertEquals("Soft constr     aint viol      ation is inc     orrect", 0, violation);
	   }
	
	@Test
	// Te       sting if two lectures for the same course having different exam times  causes this SC to be triggered
	public void SoftCon3failtest() {         
		session1.setDay("M")  ;
		T    ime time = new Time.Builder("9:00").build();
		sessio  n1.setTime(time);
		lecture1.setSess        ion(session1);
		List<Lecture> li        stofLecs = ne   w ArrayList<Lecture>();
		listofLecs.add(lecture 1);
		
		Lecture lecture2 = new L      e  c       ture("CPSC433", "L02");  
		Time time2 = new Time.Builder("17:00").build();
		session2.setTime(time2);
		session2.setDay("M");
		int violation = Con   straints.calcSoftThre  e(list  ofLecs, session2,   lecture2);
		Assert.assert     Equals("Soft constraint violation is incorrect",   5   0, v    iolation); 
	}
	  
	@Test
	// Testing if two lectures for the same co       u  rse having different exam times but ending at the same     time causes this SC to be triggered
	public void SoftCon  3failtest2() {
		sessi on1.setDay("M");
		Time time = new Time.Builder("9:00").build();
		session1.setTime(time);
		lecture1.se     tSessi     on(s     ession1);
		session1.se  tLength(2);
		List<Lecture> l     istofLecs = new ArrayList<Lecture>();
		listofLec     s.add(lecture1);
		
		Lectu       re lecture2 = new Lecture("CP SC433", "L02");
		Time t ime2 = new Time.Builder("8:00"   ).build(     );
		session2.se   tTime(time2);
		ses   sio   n2.setDay("  M");
		session2.se    tLength(3);
		int violation = Constraints.calcSoftThree(listofLecs, session2, lecture2)    ;
		Assert.assertEquals("So    ft constraint violation is incorre     ct", 5     0, viola tion);
	}
	
             	@Test
	/  /     Testing    if SC passes when two lectu   res for the same course        have the same exam time
	public void SoftCon3passtest() {
		session1.setDay("M");
		Time tim e    = new Time      .Builder("9:00").build();
		s     essio  n1.setTime(time);
		lecture1.setSession(sessio    n1);
		List<Lec     ture> listofLecs = new ArrayList<Lectur   e>();
		listofLecs.add(lecture1);
		
		Lecture lecture2 = new Lecture("CPSC433", "L02");
		session2.setTime(time);
		session2.setDay("M");
		int viola     tion = Constraints.calcS oftThree(listofLecs, session2, lect    ure2);
		Assert.assertEquals("Soft con   straint violation is incorrect", 0, v   io lation)           ;
	}
	
	@Test
	// Tes ting if SC passes when two     lectures for the same c    ourse have the same exam time but different lengths
	public void SoftCon3passtest2() {
		session1.setDay("M");
		Time time = new Time.Builder("9:00").bu      ild();
		session1.se      tTime(time);
		session1.setLength(3);
		lecture1.setSession(session1);
		List<Lecture> listofLecs = new Arra   yList<Lecture  >();
		listofLe     cs.add(lecture1);
		
		     Lect ure lec ture2 = new Lect   ure("CPSC433", "L02");
		session2.setTime(t       im      e);
		ses sion2.setDay("M");
		session2   .setLeng     th(2);
		int violation = Constraints.calcSoftThre     e(listofLecs, sessi     on2, lecture2);
		Assert.assertEquals("Soft constraint violation is incorrect",   0, violation);
	}
	
	@Test
	// Testing if SC is      violat  ed when student has two exams o f length 6 in total on the same day 
	public void SoftCon4failtest1() {
		Student student = new Student("Bob");
		lecture1.setExamLength(3);
		session1.setLength(3);
		session1.setDay("M");  
		lectu  re1.setSession(session1);
		student.enroll(le cture1);
		
		session2.setLength(3);
		session2.setDay("M");
		student.enroll(lecture2);

		
		i   nt violation = Constraints .calcSo  ftFour(session2, lecture2);
		Assert.assertEquals("Soft constraint      violation i s     incorrect", 50, violation);
	}
	
	@Test
	// Testing if SC is violat ed w hen two    students have two exams of       length 6 in total on the same day 
	  public     void SoftCon4failt  est2() {
		Student student = new Student("Bob");
       		lecture1 .setExamLengt    h(3);
		session1.setLeng    th(3);
		session1.setDay("M");
		l  ecture1.setSession(session1);
		student.enroll(lecture1);
		
		  
		Studen   t  student2 = new S  tudent("Joe");
		lecture3.setExamLength(3);
		ses   sion3.setLength(3);
		   session3.setDay("M")   ;
		stu    dent2.enroll(lecture3);

		lecture2.setExamLength(3);
		session2  .setLength(3);
		session2.setDay("M");
		
		stud    ent.enroll(lecture2);
		student2 .enroll(lecture2);
		
		int violation = Constraints.calcSoftFour(session2, lecture2);
  		Assert.assertEquals(  "Soft const       raint       viola t ion is incorrect", 50, violation);
	}
	
	@Test
	// Testing if SC passes i   f    a student has a total of 5 exam hours in a day
	public void SoftCon4passtest() {
		Student student =   n ew Student("Bob")  ;
		lecture1.setExamLength(3);
		session1.setLength(3);
    		session1.s    etDay("M");
		lec   tur          e1.setSes sion(session1);
		student.enroll(lecture1);
		
		
		session  2.set Length(2);
		session2.setDay("M");
		student.enroll(lecture2);

		int   violation =  Constraints.calcSoftFour(session2, lecture2);
		Assert.assertEquals("Soft constraint violation is incorrect", 0, violatio   n); 
	}
	
	@Test
	// Test ing if SC passes if a student has a total    of 4 exam ho    urs in a day
	p      ublic void Sof   tCon4passtest2() {
		Student student = new Student("Bob");
		lecture1.setExamLength(3);
		session1.s  etLength(2);
		session1.setDay("M")  ;
		lecture1.setSession(session1);
		st    udent.enroll(lecture1);
		
		
	  	session2.setLe    ngth(2);
		session2.setDay("M");
		student.enroll(  lecture2);

    		i  nt violation = Constraints.calcSoftFour(session2, lecture2);
		Assert.ass       ertEquals("Soft constraint vi    olation is incorrect", 0, violati     on); 
	}
	
	@Test
	// T  est if SC triggers when student has a 3  hour exam at 8   and    then another exam at 11
	public void SoftCo     n5failtest(){
		Student student = ne  w Student("Bob");
		Time ti    me = new Time.Builder("8:00").build();
		session1.setTime(time);
		lecture1.setExamLength(3);
		session1        .setLength(3);
		session1.setDay("M");
		lecture1.setSession(session1);
		student.enroll(lecture1);
		
		  Time time2 = new Time.Builder("11:00").build(     );
		
		session2.setTime(time2);
		lecture2.setExamLength(3);
		sessi        on   2.setLength(3);
		session2.setDay("M");  
		student.e           nroll(lecture2);
		  
		int violation = Constraints.calcSoftFive(session2, lecture2);
		   Assert.assertE     quals("Soft constraint      violation i   s incorrect", 50    , vio   lation);
	}   
	
	@Test
	// Test if SC triggers when two stude  nts have exams right before another exam      at 11 (one that starts at 9 and one that starts at 8)
	public void SoftCon5failtest2(){
		Student stu   den    t = new Student("Bo       b");
		Time time = new Time.Builder("8:00").build();
		session1.setTime(time);
		sess    ion1.setLe      ngth(3);
		session1.set     Day("M")       ;
		lecture1.  se    tSession(session1);
		student.enroll(lecture1);
  		
		
		Student student2 = new Student("Sam");
		Time time2 = new Time.Builder("9:00").build();
		session2.setTime(time2);
		session2.setLength(2  );
		session2.setDay("M");
		lecture2.setSess      ion(session2);
		student2.enroll(lecture2);
		
		Time time3   = new    Time.Builder("11:00").build();
				
		session3.setTime(time3);
		session3.setLength(3);
	     	session3.setDay("      M");
		student.enroll(l  ecture3);
		student2.enrol    l(     lecture3);
      		
		in   t violation = Constraints.calcSoftF    iv    e(session3, lecture3);
		Assert.assertEquals("Soft constraint violation is incorrect", 100, violation);
	}
	
	@Test
	// Testing if SC passes when a student doesn't have back to back exams
	public void S oftCon5passtest(){
		Student student = new Student("Bob");
		Time time = new Time.Builder("8    :00").build();
		session1.set   Time(time);
		lecture1.setExamLength(3);
		session1.setLength(3);
		session1.setDay("M");
		//lectu  re1.enrollStu  dent(student);
		lecture1.setSession(session1);
		student.enroll(lecture1);
		
		Time time2 =   new Time.Build er("12:00").build();
		
		session2.setTime(time2);
		lecture2.setExamLength(3);
		session2.setLength(3);
		session2.setDay("M");
    		//l   ecture2.enrollStudent(stud  ent);
		student.enr    oll(lecture2);
		
		int violation = Constraints.calcSof  tFive(session2, lec   ture2);
		Assert.as  sertEquals("Sof   t constraint violation is incorrect", 0, violation);
	}
     	
	
	@Test
	// Te   sting if the SC triggers when two lectures a  ssigned to a single session have diffe rent lengths 
	public void Soft    Con6failtest1() {
		
		session1.setLength(3   );
		lecture1.setExamLength(2);
		lecture2.se   tExamLength( 3);
		sess  ion 1.addL    ecture(lecture1);
		lec    ture1.setSes     sion(sess   ion1);
		int violation = Constra     in     ts.calcSoftSix(session1, lecture2);
		Assert.assertEquals("Soft constraint violation is incorrect", 20  , viol    ation);
	}
	
	
	@Test
	// Testing if the SC triggers when             two lectures assigned to a si  ngle session h   ave     diffe   rent  lengths
	publ  ic void SoftCon6fa       iltest2() {
		session1.setLength(3)    ;
		lectu       re1.setExamLength(3);
		l  ecture2.setExamLength(2);
		s   ession1.ad dLecture(lecture1);
		lecture1.setSession(session1);
		int violation = Constraints.calcSoftSix(session1, lecture2);
		Assert.assertEquals("Sof  t constraint violation is i    ncorrect", 20, violation);
	}
	
	@Test
	// Testing if the SC passes when two lecture    s assigned to a single session have t   he same length
	public void SoftCon   6passtest() {
		session1. setLength(3);
		lecture1.setExamLength(3);
		lecture2.setExamLe   ngth(3);
		session1.addLecture(lecture1);
		lecture1.setSession(session1);
		int violation = Constra ints.calcSoftSix(session1, lecture2);
		Assert.assertEquals("Soft constraint violation is incorrect", 0, violation);
	}
	
	@Test
	// Test if S   C triggers when a 2 hour exam is scheduled into a 3 hour session
	public void SoftCon7failtest() {
		session1.setLength(3);
		lecture1.setExamLength(2);	
		int violation = Constraints.calcSoftSeven(session1, le     cture1);
		Ass  ert.assertEquals("Soft constraint violation is incorrect", 5, vi    olation);
	}
	
	@Test
	// Test if SC passes when a 3 hour exam i       s scheduled into a 3 hour session
	public void SoftCon7passtest() {
		session1.setLength(3);
		lecture1.setExamLength(3);	
		int violation = Constraints.calcSoftSeven(session1, lecture1);
		Assert.assertEquals("Soft constraint violation is incorrect", 0, violation);
	}
	
	//Assignment assignment2 = new Assignment();
}
