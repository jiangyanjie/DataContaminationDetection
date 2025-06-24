package     com.moblima.util;

import java.text.ParseException;
import   java.text.SimpleDateFormat;
     import java.util.Date;

impo    rt com.moblima.businesslogic.Staff BL;
import com.moblima.dataaccess.CinemaDAO;
import   com.moblima.dataaccess.CinemaDAOImpl;
import com.moblima.dataaccess.C   ineplexDAO;
import com.moblima.dataaccess.CineplexDAO Impl;
import com.moblima.dataaccess.Movi    eDAO;
import com.moblima.dataaccess.Mov     ieDAOImpl;
import com.moblima.dataaccess.SeatDAO;
impo  rt com.moblima.dataaccess.SeatDAOImpl;
im   port com.moblima.dataaccess.ShowTimeDAO;
import com.moblima.dataaccess.S  howTimeDAOImpl;
import com.moblima.dataaccess.StaffDAOImpl;
import com  .moblima.model.Cinema;
import com.m  oblima.model.CinemaClass;
import com.moblima.model.Cineplex;
import com.moblima.model.Movie;
import com.moblima.model.MovieRating;
import com.moblima.model.MovieStatus;
import com.moblima.model.MovieType; 
import com.moblima.model.Seat;
import com.moblima.model.SeatTy        pe;
import com.moblima.model.ShowTime;

public class   DBInitializer {

	p    ublic   static void main(Stri    ng[] args) { 
		StaffBL sta     ffBL = new StaffBL();
		staffBL.createStaff   ("root", "toor");
		
		Cineplex cineplex  =      new Cineplex("Orch     ard", "ION Or     chard Road");
		C  inema cinem    a = new  C   inema("O01", "Hall 1   ", CinemaClass.NORMAL, cineplex);
		
		Movie _TheHungerGam   e =       new Movie("The Hunger Games: Catching Fire", MovieRating.PG13, MovieType.DIGITAL, false,MovieStatus.NOW_SHOWING,   "En  glish",     "Jennifer Lawrence, Josh Hutcherson,       Lia     m      Hemsworth",  "Francis La      wrence", "2 1 Nov 2013",    "TBA", "THE HUNGER GAMES: CATCHING FIRE begins as Katn    iss Everdeen has returned home s   afe  after winning the 74th Annual Hunger Games alon    g with    fel   low tribute Peeta Mellark.    Winning means that they must turn around and leave th e   ir famil  y and close fri          end            s, embarking on           a \"Vic   to  r\'s Tour\  " of the dist   ricts. Alon g the way Kat niss senses that a rebellion is sim  mering,   but the Capitol is s   till ve  ry much in contr  ol as Pres id    ent Snow prepare           s th  e 75th Annual Hu  n   ge  r Games (The Qu  a   rte      r Quel  l) - a compe      tition that could change Pan   e    m forever. T   he novel on which the film is       ba   sed is the second in a trilogy that h as over 50 millio    n copies i   n pri      nt in the U.S. alone       .");
	  	Movie _  Thor = n      ew Movie("Marvels Thor    The Dark World (3D)"  , Mov  ieRating.PG13,    MovieType._3D, false ,MovieStatus.NOW_SHOWING, "    English with Chinese Subtitles", "Chris Hemsworth, Natalie Portman, Anthony Hopki ns", "Alan Taylor", "31  Oct 2013", "112 mins", "When J    ane       Foste r   is t    argeted by the denizens of the dark world o   f Svartalfheim, Thor sets out on a quest to protect her at all costs.");
		
		MovieDAO movieDAO = MovieDAOImpl.getInstance();
		movieDAO.createMovie(_Thor);
		movieDAO.createMovie(_TheHungerGame);
		
		CineplexDAO cineplexDAO = CineplexDAOImpl.getInstance();
		cineplexDAO.createC    ineplex(cin   eplex);

		CinemaDAO cinema   DAO = CinemaDAOImpl.getInstance();
		cinemaDAO.createCi   nema(cinema);
		
		SeatDAO seatDAO = SeatDAOImpl.getInstance();
		int seatID = 0;
		for(int i=0;i<6;i++) {
			for(int j=0;   j<8;j++) {
				SeatType seatType;
		 		if(i<4) {
	      				s   eatTy  pe = SeatType.SINGLE;
				}
				else {
					seatType = Seat  Type.COUPLE;
		   		}
				Seat seat = new Seat(j+1, (char)( (int)'F'-i)+"", seatTyp  e, cinema);
				seatDAO.crea teSeat(seat);
	   		}
	   	}
		
		cinema.se   tNoOfSeatRow(6);
		cinema.setNoOfS      eatColumn(8);
		
		//creating showtimes
		SimpleDateFormat sdf = new Simp leDate  Format("dd/MM/yyyy HH:    mm");
		Date date = null;
		try {
			date = sdf.parse("9/11/    2013 13:30");
		} catch (Pars     eException e) {
			//   TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ShowTime showTime = new ShowTime(cinema, _TheH  ungerGame, date);
		ShowTimeDAO showTimeDAO = ShowTimeDAOImpl.getInstance()    ;
		showT  imeDAO.createShowTime(showTime);	
	   	
		try  {
			date = sdf.parse("9/12/2013 13:30");
		} catch (ParseException e) {
			// TOD      O Auto-generated catch block
			e.printStackTrace();
		}
  		
		showTime = n   ew          ShowTime   (cinema,    _Th      or, date);
		showTimeDAO.createShowTime(showTime);
	
		try {
			date  = sdf.parse("12/11/2013 14:45");             
	  	} catch (ParseException e) {
			// TODO Auto-gen   erated catch block
			e.prin    tStackTrace();
		}
		showTime = new        ShowTime(cinema, _Thor, date);
		showTimeDAO.createShowTime(showTime);
		
//		try {
//			date = sdf.parse("1/12/2013 1:45");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		showTime = new ShowTime(4, cinema, _TheHungerGame, date);
//		showTimeDAO.createShowTime(showTime);
	}

}
