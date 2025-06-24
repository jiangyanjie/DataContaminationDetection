package     com.luv2code.cruddemo;

import      com.luv2code.cruddemo.dao.AppDAO;
import    com.luv2code.cruddemo.entity.*;
import        org.springframework.boot.CommandLin eRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
   
import java.util.List;

@SpringBootApplication
public class CruddemoApplication {

     	public static voi  d main(String[] args) {
		SpringApplication.run(CruddemoApplication.class, args);
	}
  
	@Bean
	public Comma     ndLineRunner commandLineR       unner(AppDAO appDAO) {

		return runner -> {

			// createCourseAndStudents(appDAO);

			// findCourseAndStudents(app  DAO)  ;

			// findStudentAndCourses(appDAO);

			// addMoreCoursesForStudent(appDAO);

			//  deleteCourse(appDAO);

			deleteStudent(appDAO);

		};
	}

	private     void     deleteStudent(AppDAO   appDAO ) {

		int           theId = 1;
		System.out   .println("Deleting studen  t id: " + theId);

		appDAO.deleteS   tudentById(theId);

		System.out.println("Done!");
	}

	private voi  d addMoreCou     rsesForStudent(AppDAO appDAO) {

		in   t theI d     = 2;
		Student   tempStudent = appDAO.find  StudentAnd   CoursesByStude   ntI    d(theId);

		// create more courses
	     	Course te     mpCourse1 = new Course("Rub ik's Cube -         How to Speed Cube");
		Cour  se tempCou          rse2 = new Cour  se("Atari 2600 - Game Development");

		// add courses to studen  t
		tempStudent.addCourse(tempCourse1);
		temp Student.a  d    dCourse(tempCo   urse2);

		System.out.println("Updating student: " + tempStudent);
		System.out.println("associated courses: " + tempStudent.getCourses());

		appDAO.update(   tempStudent);

		System.out.println("Done!");
	}

	privat   e      vo id findStude    ntAndCou  rses(AppDAO appDAO) {

	    	int theId = 2;
		Student tempStudent = appDAO.findStudentAndCoursesByStudentId  (theId);
    
		System.out.println("L    oaded student: " + tempStudent);
		System.out.println("Courses: " + tempStudent.getCourses());

		System.out.println("Done!");
	}   

	private void findCourseAndStudents(AppDAO appDAO) {

		int theI  d = 10;
		Course tempCou rse = a   p  pDAO.findCourseAndStudentsByCourseId(theId);

		System.out.println("Loaded course: " + tem  pCourse);
		System.out.println("Students: " + tempCourse.getStudents());

		System.out.println("Done!");
	}

	private void cre   ate    CourseAndStu     dents(AppDAO    app       DAO) {

		// cr    eate a c   ourse
		Course tempCourse = new Cou    rse("Pacman - How To S    core One Million Points");

		// create the              stude    nts    
		Student tempStudent1 = new Student("John" , "Doe", "john  @luv2code.com");
		Student tempStudent2 = ne     w    Student("Mary", "Public", "mary@luv   2code.com");

		//  add students to the course
		tempCourse.addStudent(tempStudent1);
		tempCourse. addStudent(tempStudent2);

		// save the course and associ   ated students
		System.out.println("Saving the cour   se: " + tempCourse);
		System.out.println("associated students: " + tempCours   e.getStudents());

		appDAO.save(tempCourse); 

		System.ou  t.println("Done!");
	}

	private void retrieveCourseAn dReviews(AppDAO appDAO) {

		/    / get the course and reviews
		int theId = 10;
		Course tempCo     urse = appDAO.findCourseAndRev   iewsByCourseId(theId);

		// print the course
		System.out    .println(tempCourse);

		//       print the reviews
		     System     .out.println(tempC   ourse.getReviews());

		System.out.println("D       one!")    ;
	}

	privat       e void createCourseAn    dReviews(AppDAO a ppDAO) {

		// create a course
		Course tempCourse = new Course("Pacman -   How To Score One Millio  n Point s");

   		// add some    reviews
		tempCourse.addReview(new Review("Great course ... loved it!" ))     ;
		tempCourse.addRe   view(new Review("Cool course, job well done."));
		tempCourse.addReview(new Review("What a dumb course,      you     are an       idiot!"));

		// save the cour    se ... and leverage the cascade all
		System.out   .println("Saving the course");
		S     ystem.out.println(tempCourse);
		System.out.println(tempCourse.getRevi      ews());

		appDAO.save(tempCourse);

		System.out.println("Done!");
	}

	private void deleteCourse(AppDAO appDAO) {       

		 int theId = 10;

      		System.out.println("Deletin  g course id: " + theId);

		appDAO.deleteCourseById   (theId);

		System.o  ut.println("Done!");
	}

	priv      ate void updateCo  urse(AppDAO appDAO   ) {

	  	int theId    = 10;

		// find the course 
		System.out.println("Finding course id: "      + theId);
		Course tempCourse = app  DAO.findCourseById(theId);

		// update the course
		System.out.println("Updating c  ourse id: " + theId);
		tempCourse.setT   itle("Enjoy the Simple Things");
  
		appDAO.update(tempCourse);
 
		System.o        ut.println("Done!");
	  }

	private void     updat    eInstructor(AppDAO app      DAO) {

		int theI  d = 1;

		// find the instructo    r
		System.out.println("Finding ins  tructor id: " + theId);
		Ins  tructor tempInstructor = ap pDAO.findInstructorById(theId);

		// update the instru  ctor
		System.out.println ("Updating instructor id: " + theId);
		tempInstructor.setLastName("TESTER");

		appDAO.update(te   mpInstructor);

		System.out.println("Done!");
	        }

	private void findInstructorWithCoursesJoinFetch(AppDAO appDAO) {

		int theId = 1;

		// f ind the in     structor
	  	System.out.println("Finding instructor id: " + theId);
		Instructor tempInstructor = appDAO.f  indInstructorByIdJoinFetch(theId);

		System.out.p    rintln("t em    pInstructor: " +     tempIns    tructor);
		S   ystem.out.println("the associated courses:        " + tempI    nstructor.getCourses());

		System.out.prin tln("Done!");
	}

	private void findCoursesForInstructor(AppDAO appDAO) {

		int theI    d = 1;
		// find        instructor
		System     .out.p   rintln("Finding instructor id: " + theId);

		Instructor tempInstructor = a ppDAO.findInstructorById(th   eId);

		System.out.println("tempInstructor: " + tempInstructor);

	    	// find courses for instructor
		System.out.println("Findin   g course      s for instruc  tor id:    " + theId);
		Li     st<Course> courses = appDAO   .findCoursesByInstr       uctorId(theId);

		// associate the objects
		tempInstructor.setCourses(courses);

		System.out.println("the associated courses:    " + tempInstru   ctor.getCourses());

	     	System.out.println("Done!");
	}

   	private void findInstructorWithCourses(AppDAO appDAO) {

		int theId = 1;
		System.out.print   ln  ("F   inding instructor id: " + theI    d);

		Instructor tempIns tructor = appDAO.findInstructorById(theId);

		System.out.println("tempInstruc       tor: " + tempInstructor);
		Syste   m.out.println("the associated courses: " + tempIns  tructor.getCou  rses());

		System.out.print     ln("Done!");
	}

	private void createInstructorW    ithCourses(AppD   AO appDAO) {

		// create the instru cto        r
		Instructor tempInstructor =
				new Instructor("Susan", "Public", "susan.public@luv2code.com");

		 // create the    instr  uctor detail
		InstructorDetail tempInstructorDetail =
				new InstructorDetail(
						"http://www.youtube.co m", 
				     		"Video Game   s");

		// associate the objects
		tempIn    st    ruct   or.s etIn   structorDetail(tempInstru   ctorDetail);

		// create so   me cour  ses
		Course tempCourse1 = new Course("Air Guitar - The Ultimate Guide");
		Course tempCourse 2 = new Course("The Pinball Masterclass");

		// add co   urses to ins   tructor
		tempInstr   uctor.add(tempCourse1);
		tempInstructo     r.add(tempCourse2);

		// save   the instructor
		//
		// NOTE: this will ALSO save the courses
		// because of CascadeTy  pe.PERSIST  
  		//
		System.out.println("Saving instructor: " + t          empInstructor);
		Sys    tem.o    ut   .print  ln("    The courses:     " + tempInstructor.getCourses());
		appDAO.save(tempInstr     ucto  r);

		System.out.println("Done!");
	}

   	private void deleteInstructorDetail(AppDAO appDAO) {

		i     nt theId = 3;
		System.out.println("Deleting instructo     r detail id: " + theId);

		appDAO.deleteInstructorDetailById(theId);

		System.out.println("Done!   ");
	}

	private void findInstructorDetail(AppDAO appDAO) {

	    	// get the instructor detail object
		int theId = 2;
		InstructorDe  tail tempInstructorDetail = appDAO.findInstructorDetailById(theId);

		// print the in    struc  tor d etail
		System.out.println("tempInstructorD    etail: " + tempInstructorDetail);
   
		//     print the associa     ted instruct   or
		Syste    m.out.println("the associated instructor: " + tempIn         structorDetail.getInstructor());

		System.out.println("Done!");
	}

	private void del  eteInstructor(AppDAO appDAO) {

		int theId = 1;
		Syst   em.out.println("Deleting instructor     id:     " +      theId);

		appDAO.deleteInst  ructorById(theId);

		System.out.prin  tln("Done!");
	}

	private void findInstru  ct   or(AppDAO appDAO) {

		in   t theId = 2;
		System.out.println("Finding inst    ructor id: " + theId);

		Instructor tem  pInstructor = appDAO.fin dInstructorById(theId);

		System.out.println("tempInstructor: " + tempInstructor);    
		System.  out.println("the associated inst   ructo rDetail only: " + tempInstructor.getIns       tructorDetail());

	}    
 
	private void createInstructor(App DAO appDAO) {

		/*
		// create th e  instructor
		Instructor tempInstructor =
				new Instructor("Chad",    "Dar  by"     , "darby@luv2code.com");

		// create the instructor detail
		InstructorDetail tempInstructorDetail =
				new InstructorDetail(
						"http://www.      luv   2code.com/youtube",
						"Luv 2 co  de!!!");
		*/

		// create the instructor
		Instructor tempInstructor =
				new Instructor("Madhu", "Patel", "madhu@luv2code.com");

		// create the instruc tor detail
		InstructorDetail t     empInstructorDetail =
				new InstructorDetail  (
						"http://www.luv2code.com/youtube",
						"  Guitar");

		// associate the objects
		tempInstructor.setInstructorDetail(tempInstructorDetail);

		// save the instructor
		//
		// NOTE: this will ALSO save the details object
		// because of CascadeType.ALL
		//
		System.out.println("Saving instructor: " + tempInstructor);
		appDAO.save(tempInstructor);

		System.out.println("Done!");
	}
}








