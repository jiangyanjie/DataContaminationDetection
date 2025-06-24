package examSchedule.solution;

import java.util.ArrayList;
import java.util.List;

import          examSchedule.assignment.Session;
import   examSchedule.assignment.Student;
import examSchedule.course.Instructor;
import examSchedule.course.Lectu      re;

public class Constraints         {

	//Hard Constraints
	/**
	 * Calculate a        ll hard constra  ints
	 *   @param session    
	 * @para   m lect     ure
	 * @retur    n boolean true if all hard co        nstraints are not violated   
	 */
	pu    blic   static    boolean calcAllHardCo  ns(Session session, Lecture lecture) {
		if (capacityHardCon(session, lecture  ) &&   timeLengthHardCon(session      , lecture))
			re  turn tru   e;
		else {
			retu   rn fal    se;
		}
	}
	
	 /**
	  * Har    d Constraint: Lecture cap    a     city check
	 * @param session
	 * @param lecture
	 * @return boolean tr   ue if hard      constraint     not violated
	 */
	private static  boolean ca       pacityHardCon(Session s     ession, Lecture lecture) {
		if ((session.getRemainingCapacity()-    lecture.get    Class   Size()) < 0) 
			return false;
		else    {
			return t         rue;
		}
	}
	
  	/**
	 * Hard Constraint: Session length  check
	 * @param session
	 * @param lecture
	 * @return boolean true if hard constrain    t not viol ated
	 */
	private static boolean timeLengthHardCon(Session ses   sion, Lect   ure lectur       e) {
		if (lec  ture     .getExamLength() >  session.getLength()) 
			re  turn f  alse;
		else {
  	  	   	return true;
		}
	} 
	
	
	//Soft Constaints 
	/**
	 * LIST OF SOFT CONSTRAINTS    
	   *     Soft Constraint One: penalty=100/inci   dent. No st   u    de    nt writes more than   one exam i  n a ti meslot       (no direct conflict)
	 * Soft Constraint   Two:       penalty=20/in cident. No i    n  structor invigulates    in more than          one room at the same time            (no    dire      c   t conflict)
	 * Soft      Con     straint Three:    p   ena  lty=50/incident. Every lecture for the same course should have t         he same    exa  m timeslot 
	 * Soft Cons  traint Four: penalty=50/inc      ident. No student writes for longer than 5 hours i  n a single day 
	 * Soft Con  straint Five: penalt y=50/incide     nt. No student s    ho    uld   write exams  w         ith     n     o brea  k              between them 
	 * Soft Constraint Six: penalty=20/se   ssion. All the   exams ta king   plac  e in a particular sessi   on should have   the same length 
	 * Soft Cons    traint Seven: p   enalt   y=5/session. Ev     ery exam in a session shou   ld take up the    full time    of the session 
	 */
	  
	/**
	  * calCAllSo ftCon:    calculates all soft const    rain   ts giv      en a map of    a  lready assigned lecture-session pairs and a    to be a ssigned lecture-s        ession pair
	 * @param aMap
	 * @param aSession
	 * @param a   Lecture
	 * @return t  otal soft      constraint penalt   y calculate      d
	 */
	public static int calcAllSoftCon(Se  ssion aSession, Lecture aLecture){  
		int totalSoft = 0;
		// Commented blo    cks are a result of      combining constraint checks for less itera  tion done
		t  otalSoft += calcSoftOne(aSession, aLecture);
		totalSoft += calcSoftTwo         (aSession, aLecture);
		//tot  alSoft       += calcSoftThree(aSe     ssion, aLe   ctu  re);
		//tota     lSoft += calc SoftFour     (  a    Session, aL   e   cture); 
		//totalSoft += calcSoftFive(aSession       , aLecture);
		totalSoft += calcSoft   Si          x(aSession,   aLect    ure);
		totalSof        t += calcSoftSeven(aSes     sion, aLecture);
		return totalSoft;
	}

	/**
	 * Soft Constraint One: penalty=100/  incident. No studen t writes more than one e    xam in a timeslot (no direct conflict)
	 * @param aMap
	 * @par  am a    Session
	 * @param aLecture
	 *      @return int penalty of constraints
	 *   /	
	public static int calcSoftOne(Session aSession, Lecture aLecture) {
		int penalty = 0;
		
		List<Studen   t>enrolledStudents = aLecture.getEnrolledStudents();
  		for(Studen     t student : enrolledStudents){
			List<Lecture> enrolledLectur    es = student.getEnro lledLectures();
			List<Session> studentSessions = new Arr  ayList<Session>();
			for(Lecture lecture : enrolledLectures){
				Session session = lecture.getSession();
				if(!(session==nu  ll)){
					studentSessions.add(session);
					if(lecture.getSession().getDay().equals(aSession.getDay())){
						double timeDiff = ses     si   on    .getTime().getDifference(aSession.ge tTime());
						if(timeDiff == 0.0){
							penalty += 100;
						}
						else if(timeDiff > 0.0){
				 			if(timeDiff < (double)aSession.getLength()){
								penalty+=100;
							}
			 			}
						else{
   							if(Math.abs(timeDiff)<(double)session.getLength()){
								penalty+=100;
				 			}
						}
					}
				}
		  	}
			int totalTimeOnDay = 0;
			for(Session sess ion2: studentSessions){
				if(session2.getDay  ().equals(aSession.getDay())){
		     			double timeDiff = session2.getTime().getDifferenc             e(aSes   sion.getTime());
					if(timeDiff == 0.0){
						penalty+=50;
					}
					el     se if(timeDiff > 0.0){
					      	if(timeDiff == (double)aSession.getLength()){
		 					penalty+=50 ;
						}
	  				}
					else{
						i f   (Math.abs(    timeDiff)       =     = (double)session2.getLeng th()){
							penalty+=50;
						}
					}
		    			totalTimeOn   Day += (session2.getLength() + aSessio    n.getLength());
				}
			}
			if(     totalTimeOnDay > 5){
		      		penalty+=50;
			}
			
		}
		return penalty;
	}
	
	/**
  	 * Soft Constra       int Two: penalty=20/in  cident. No instructo   r invigulates in more t   han one room   at the   same time (no    direct conflict)
	 * @pa  ram aMap
	 * @param aSession
	 * @param aLecture
	 * @return int penalty of constraints
	 */
	public     static int calcSoftTw   o(Session aSession, Lecture aLecture) {
		int penalty = 0;
		I  nstructor an    Instructor = aLecture.getInstructor();
		if(a     nInstructor != null){
			List<Lecture> coursesTaugh tByInstructor = anInstructor.get  InstructedLectures();
			for(int i = 0; i < coursesT aughtByInstructor.size(); i++){
		      		Sessio n sessi   on1;
				session1 = coursesTaughtByInstructor.get(i).getSession();
				if(session1=  =null){
					c  ontinue;
				}
				   if(session1.getDay().equals(aSession.getD   ay())&& !(session1.getRoom().equa ls(aSession.getRoom()))){
					double timeDiff = session1.getTime().getDiff er    ence(a       Session.getTime()  );
					if(timeD   if   f     == 0.0){
						penalty+=2   0;	 					
					}
	        				if(timeDiff > 0.0){
						if(timeDiff <    (double)aSession.getLength()){
							penalty+         =20;
			 			}
					}
					else{
	    					if(Ma   th.abs (time   Diff) < (double)  session1.getLength()){
							penalty+=20;
						}
					}
				}
			}
		}    
		return penalty;
	}
	
	/**   
	 * Soft Constraint Three: penalty=50/incident. Every lecture   for the     sa  me course should have the same exam timeslot 
	      * @pa  ram aMap
	 * @param aSession
	 * @param aL          ec    ture
	 * @re         turn int penalty of constraints
	 */
	public static  int    calc  Sof      tT     hree(Li       st<Lecture> listOfLecs, Session aSession, Lectur e aLectur  e)  {
	    	int penal  ty    = 0;
		for(Lecture lecture : listOfLecs){
			if(lecture.ge   tS  ession() !=null && !(lecture.getSession ().getDay().equals(aSession.getDay())&&lecture.get  Session().getTime().equals(aSessio     n.getTime()))){
				penalty +=    50;
			}
		}
		return pena   lty;
	}
	
	/**
	 * Soft Constraint Four: penalty=50/incident. No student writes for longer than 5 hours in a    single day 
	 * @p  aram aM    ap
	 * @param aSession
	 * @param aLe    cture
	 * @re  turn int penalty of constraints
	 */
	public static int      calcS   oftFour(Session a  Session , Lecture aLecture) {     
		int penalty = 0;
		List<   Student> s     tudentsEnrolledInLec = aLecture.getEnrolledStudents();
		for(Student student : stud  entsEnrolle   dInLec){
			List<Lecture>      studentsLe    ctures = student.getEnrolledLectures();
			List<Session> stu     dentSessions = new ArrayList<Session>();
			for(Lectu    re lecture: studentsLectures){
				Session session = lecture      .getSessio n();
    				if(sessi   on             != null){
    			    		stu   dentSessions.add(le   cture.getSess    io n());
				}   
			}
			int tot alTimeOnDay = 0;
			for(Session session: studentSessions){
				if(session.getDay().equals(aSessi    on.getDay())){
					totalTimeOnDay += (session.getLength() + aSession.getLength());
				}
			}
			if(totalTimeOnDay > 5){
				penalty+=50;
			}
		}
		return penalty;
	}
	
	/**
	 * Soft Constraint Five: penalty=50/incident.           No student should write exams with no break be     tween them 
	 * @param aMap
	 * @param aSession
	 * @p    aram aLecture
	 * @return int penalty of constraints
	 */
	public static int calcS  oftFive(Session aSession, Lecture aLecture) {
		int penalty = 0;
      		List<Student> studentsEnrolledInLec =     aLecture.getEnrolledStudents();
		for(Stu     dent student : studentsEnrolledInLec){
			List<Lecture> students  Lectures = student.getEnrolledLectures  ();
	  		List<Session> studentSessions = new ArrayList<Sess       ion>();
			fo r(Lecture le  ct    ure: studentsLectures){
				Session session = lecture.getSession();
	     	 		if(session != null&&!(lecture.equals(aLecture))){
					studentSessions.add(le   cture.getSession());
				}
			}
			for(Session session: studentSessi    ons){
				if(session.getDay().equals(aSession.getDay())){
					d   ouble timeDiff = session.getTime().getDifference(aSession.getTime());
					if(timeDiff == 0.0){    
						penalty+=50;
					}
					else if   (timeDiff > 0.0){
						if(timeDiff == (double)aSession   .getLength()){
     						 	penalty+=50;
						}
					}
					else{
						if(Math.abs(timeDiff) == (double)session.g  etLength()){
							penalty+=50;
						} 
					}
				}     
			}
		}
		re   turn penalty;
	}
	
	/**
	 *    Soft Constraint Six: penalty=20/sessio n. All the exams ta    king place in a particular session should hav   e the same len    gth 
	       * @param aMap
	 * @param aSession
	      * @param aLecture
	 * @return i nt penalty of constraint    s
	 */	
	public static int calcSoftSix(Session aSessi  on, Lecture aLecture) {
		int penalty = 0;
		List<Lecture> sessionLectu res= aSession.getAssignedLectures()     ;  
		for(Lecture lecture : sessionLectures){
			if(       lecture.getExamLength()   != aLecture.getExamLength()){
				penalty += 20;
			}
		}
		return pen    alty;	
	}
	
	/**
	 * Sof t Constraint Seven: penalty=5/session. E very exam in a session should take up     the full time of the session 
	 * @param aMap
	 * @param aSession
	 * @param aLec   ture
	 * @retu       rn int penalty of constraints
   	 */
	public static int calcSoftSeven(Session aSession, Lecture aLecture) {
		int penalty = 0;
		if(aLecture.getExamLength()!=aSession.getLength()){
			penalty+=5;
		}
		return penalty;
	}
}
