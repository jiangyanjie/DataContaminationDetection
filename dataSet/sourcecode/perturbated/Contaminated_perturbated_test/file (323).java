package com.moblima.util;

import com.moblima.dataaccess.*;
import com.moblima.model.*;
import   com.moblima.businesslogic.*;

impo  rt java.util.ArrayList;
impo   rt java.util.Calendar;
import java.util.Date;
import java.util.List;

publ ic class dataGenera tor {
	private    CinemaDAO cinemaDAO;
	private MovieDAO     movieDAO;
	privat e SeatDAO seatDAO;  
	   private CineplexDAO cineplexDAO;
	p rivate MovieGoerDAO movieGoe    rDAO;
	private PublicHolidayDAO publicHolidayDAO;   
	private ShowTimeDAO showTimeDAO;
	      private Mov ieTicketDAO movieTicketDAO;
	private TicketPriceBL       ticketPriceBL;
    	private MovieGoerBL    movieGoerBL;
	private BookingDAO bookingDAO;
	private StaffBL staffBL;
	private Transac  tionDAO transactionDA   O;
	
	List<Cineplex> cineplexes;
	List<Cinema> cinemas;
	Lis   t<Seat> seats;
	List<Movie> movies;
	List<MovieGoer> movieGoers;
	List<ShowTime> showTimes     ;
	List<PublicHolid    ay> publicHolidays;
	List<MovieTicket> movieTick  ets; 
	
	publ   ic dataGenerator(){	
		cinem    aDAO = CinemaDAOImpl.getInstance();
		movieDAO = MovieDAOImpl.getInstance();
		seatDAO = SeatDAOImpl.getInstance();
		cineple  xDAO = CineplexDAOImpl.getInstance();
		movieGoerDAO = MovieGoerDAOImpl.getInstance();
		publicHolidayDAO = PublicHolidayDAOImpl.getInstance();
		showTimeDAO = ShowTimeDAOImpl.getInsta nce()    ;
		movieTicketDAO = MovieTicketDAOImpl.getInstance();
		ticketPriceBL  = new TicketPriceBL();
		bookingDAO = BookingDAOImpl.getInstance();
		movieGoerBL = new MovieGoerBL();
		  staffBL = new StaffBL();
		transactionDAO   =    TransactionDAOImpl.getInstance();
		   
		cineplexes = new Array    List<Ci neplex>();
		cinem   as = new ArrayList<Cinema>();
 		seats = new ArrayList<Seat>();
		movi es = new ArrayL ist< Movie>();
		movieGoers = new ArrayList<MovieGoe r>    ();
		showTimes = new Arra      yList<ShowTime>();
		publicHolidays = new ArrayList<PublicHoliday>();
		movieTicket   s = new ArrayList<MovieTicket>();
	}
	
	public void createCi  nep lexes(){
		staffB  L.createStaff("root", "toor");
		Cineplex      cineplex1 =    new Cineplex("Catha   y Cineplex West Mall", "Bukit Ba  tok");
		Cineplex cinepl ex2 = new Cineplex("Catha        y Cineplex JE  M"  ,"Jurong      Gate   way Road");
		Cineplex cineplex3 = new Ci    neplex("Cathay Cineplex AMK Hub","Ang M   o Kio Ave 3");
		Cineple   x ci   nep     lex4 = new Cineplex("Cathay Cineplex AMK Hub - Platinum Suite","Ang Mo Kio Ave 3");
		cineplexes.add(cineplex1);
	    	cineplexes.add(cineplex2);
		cineplexes.add(cineplex3);
		cineplexes.add(cineplex4);
		for(Cineplex cineplex: cineplexes){
			cineplexDAO.createCineplex(cinepl  ex);
		}
	}
	
	pub    lic voi d createCinemas(){   
		if(cinep  lexes==null){
			System.out.pri     ntln("please create cineplexes      first.");
			return;
		}
		
		for(int j  =0;j<cineplexes.size();j++)  {
			Cineplex cineplex     = cineplex       es.get(j);
			
			if(j ==  cineplex  es.size()-1) {  
				Cinema cinema = new Cinema("PS" + 1, "Platinum Suite",   CinemaClass.PLATINUM   _MOVIE_SU    ITES, cin  eplex);
				cinemaDAO.createCinema(cinema);
				break;
			}
			
		 	for(int i=0;i<2;i++){
				Cinema cinema =  new Cinema( cineplex.getLocation().substring(0, 2) + (i+1), "Hall " + (i+1 ) , CinemaClass.NORMAL, cineplex);
				cinemaDAO.createCin        ema(cinema);
			} 
		}
	}
	
	public void createSeats(){
    		if(cin    emas==null){
			System.out.println("Please create Cinemas first.");
			return;
		    }
	     	for(Cinema cinema: c   inema DAO.getCinemas()){
			SeatType sea   tType;
			for(int i     =0;i<6;i++) {
				for(int j=0;j<8;j++) {
					if    (i < 4) {
						seatT  ype = SeatType.SINGL   E;
					}
	       				else {
						   seatType = SeatType.COUPLE;
					}
					
					Seat seat = new Seat(j+1, (char)((int)'F'-i)+"", seatType, cinema);
				  	seatDAO.createSeat(seat);
				}
			}
			
			cinema.setNoOfSeatRow(6);
			c  inema.setNoOfSeatColu mn(8);
			cinemaD  AO.updateCinem     a(cinema);
    		}
	}
	
	pub        lic void createMovies(){
		Movie movie1 = new Movie("The Hunger Games: Catching   Fire", MovieRating.TBA, MovieType.DIGIT      AL,true, Mo    vieStatus.COMING_SO  ON, "English ","Jennif  er Law rence, Josh Hutcherson,    Liam Hemsworth" , "  Fr   a     ncis Lawr ence", "21 Nov 201 3", "TBA", "THE HUNGER GA     MES: CAT   CHING FIRE  beg  ins as Katniss     Ever  deen ha   s return  ed home safe after winning the 74th Annual Hun   ger      Games along wit   h fellow tribute  Peet a Me             llark. Winning means that they must turn ar ound     and leave their family and            close frien  d  s  , embarking on a 'Victor's Tour' of the districts. Along the way Katniss senses that a re      bellion is s   immering, but the Capitol    is still very mu   ch in control as President   Snow pre   pares the 75th Annual       Hunger Games (The Quarter Quell) -    a    competiti      on that could change Pa  nem forever. The novel on whic   h the film is based is t  he second      i  n a trilogy that has    over 50 million copies in print in  the U.S. alone");
		movieDAO.      createM     ovie(movie1);  
		Mo   vie movie2 = new Mo vie(     "Ho  mefront", MovieRa     ting.TBA, Mo   vieType.DIGITAL,false, MovieStatus.COMING_SOON    , "     English with Chinese Subtitles", "Jason Statham, Ja mes Fran  co, Wino  na  Ryder, K     ate Bosworth"  , "Gary Fleder",   "5    Dec 2013", "TBA",    "HOMEFRONT star      s Jas on S tatham as form   er drug enforcement agen      t, Ph il Broker, a famil   y man who moves off the gri   d with his       daughte    r, to    a seem  ingly quiet bayo   u backwater to escape his troubled past. However,     BrokerÕs world soon becomes anything but quiet once he discov    ers that an underbelly of drugs and violenc       e riddles the small tow       n. Soon, a    so ciopa   t hic methamphetamin e king   pin, G   ator    Bodin     e (James Fra nco) puts Br     oker and his daug    hter in harmÕs w ay forcing Broker back into     action in order to save his     family an d the town   .");
		movieDAO.c   reateMovie(mov  ie2);
		Movie mov     i   e3 = new Movie("End    er's Game"    , MovieRating.PG, MovieType.DIG     ITAL   ,true, MovieStatus.NOW_SHOWING,"English w   i       th Subtitles", "Asa Butterfield, Har  rison Fo  rd, Ben K    in   gs   ley, Hailee Steinf     eld,          Viola Davis, Abigail Breslin", "Gavin Hood", "07 Nov 2013", "114 mins", "In  the near   fu ture, a hostile alien     r    a  ce called    the Formic s have attacked Earth.   If not  for the legendary heroics of International Fleet  Com     mander Mazer Rackham, al  l      would have been    lost. In pre      paration for      the next    attac k, the hig     hly esteemed Colonel Hyrum Graff and the     Inte  rna tional    Military   are train    ing   only the  be   st young min    ds      to  find the future Mazer.     Ende   r Wiggin, a shy but strategically brillian     t boy, is rec   ruited to join the elit      e. Arrivin   g at Battle School, Ender quic   kly and       easi     ly masters increasingly difficult challenges and simulations, distinguishing himself and winning respect amongst his peers. Ender     is soon ordained by Graff as the military's next great  hope, resulting   i     n his p    rom   otion     to Comm  an    d School. O nce there, he's t      ra ined by Mazer Rackham himse lf to lead his       fellow soldiers into an   epic battl   e that will determine th e  fu ture of Earth and   save the human race.");
		movieDAO.cre    ateMovie(movie3);
		Mov                   ie movie4 = new Movie     ("Baby Blues",       Mo   vieRating.PG13   , MovieType.DIGITAL,      fa   lse, MovieStatus.NOW_SHOWING, "Ma       ndarin      with Chinese and E   nglish subtitles", "Mandar  in with    Chinese a   nd  Engli      sh subtitles",     "L   eong Po-C   hih"       , "07       N  ov 2013"  , "      92 mins    "  , "When a y oung couple, blogger Tia     n Qing and song-w    riter Hao move s into a hom  e, the     y find    a mysterious doll that chan  g es their lives foreve     r. Not long after,   T    ian Qing becomes pregnant with twins but as H ao is always busy wi   t h    his work, he comes home late      every night. When Tian Qing suffers a fal    l and        ends up giving birt  h to only one child  ,      she beg   i ns to show signs of depression and weird behaviour..");
		movieDAO.createMovie(movie4);
		Movi    e   movie5 = new  Movi   e("Marv els Thor The Dark World(Digital) "       , MovieRating.PG13, MovieType.DIGITAL,true, Mov ieSta  tus.NOW_SHOWING,     "English   with Chinese Subtitles", "Chris Hemsw      orth, Natalie Por  tman  , Anthony Hopkins", "Alan Taylor", "31 Oc  t 2013", "112 mins"      , "When J    ane Foster is targeted by the de   nizens        of the dark world of Svartalfheim, Thor sets o   ut  on a ques  t to protect her at all costs.");
		movieDAO.crea  teMovie(movie5);
        		Movie movie6 = new      Movie("Marvel  s Thor The Dark World(3D) ", MovieRati   ng.PG13, MovieType._3D,false, Movie    Status.NOW_SHO WING,     "English with Chinese Subtitles", "Chris Hemsworth, Natalie P      ortman, Anthony Hopkins", "Alan Taylor", "31 Oct 2013", "1   12   mi      ns", "When Jane Foster is       targeted by the denizens of the da      rk world of Svartalfheim,      Thor sets out on a quest to protect her at all costs.");
		movieDAO.createMovie(movie6);
		  
		movie   s.add(movie1);
	   	mo   vies.ad   d(movie2);
		movies.add(movi e3);
		movies.add(movie4);
		movies.add(movie5);
		movies.add(movie6);
	}
	
	public void createMovieGoe      rs(){
		int yr,mth,day;
		Calendar calendar = Calendar.getInstance(); 
		
		day = 1;
		mth     = 2;
		yr = 1988;
		calendar.set(yr,mth,d    ay,00,00,00);
		Date da   te = c   alendar.getTime();
		MovieGoer  movieGoer1 = ne  w MovieGoer("John", "98712354", "johnny_boy@hotmail.com",date);
		movieGoerDAO.createMovi   eGoer(movieGoer1);
		
		day = 2;
		mt     h = 2;
	     	yr = 1990  ;
		calendar.set(yr,mth,day,00,00,0   0);
		date = calendar.getTime        ();
		M ovieGoer movieGoer2 = new MovieGoe  r("Jane", "99711234", "jane_girl@hotmail.com",date);
		movieGoerDAO.createMovieG  oer(movieGoer2);
		
		day = 3;
		mth = 3;
		     yr = 1991;
		calendar.set(yr,mth,day,00,00,00);
		date = calendar.getTime();
		MovieG  oer movieGoer3 = new MovieGoer(      "Max", "96547776", "max_hot@hotmail.com",date);
		movieGoerDAO.createMovieGoer(movieGoer3);
		
		day = 4;
		mth = 5;
		yr = 1996;
		calendar.set(yr,mth,day,0  0,00,00);
		date = cale   ndar.getTime();
		MovieGoer  movieGoer4 = new MovieGoer("lynners", "98234   5     54", "Aw     hat_mi  racl  e@hotmail.com",date);
		movieGoerDAO.createMovieGoer(mo  vieGoer4);
		
		day = 20;
		mth = 5;
		yr = 1992;
		calendar.set(yr,mth,day,00,00,00);
		date = calendar.getTime()     ;
		MovieGoer mo vieGoer5 = new MovieGoer("linus", "982345 54", "whatsap-_miracle@hotmail.com",date);
		movieGoerDAO.createMovieGoer(movieGoer5);
		
		da   y = 12;
		mth = 12;
		yr = 1953;
		calendar.set(      yr,mth,day,00,00,00);
		date = calendar.getTime();
		    MovieGoer movieGoer6 = new MovieGoer("syndicate", "98234554", "boners_miracle@hotmail.com",date);
		movieGo erDAO.crea   teMovieGoer(movieGoer6);
		
	}
	
	public void createSho wTim    es(){
		int mth,day,hr;
		/////////////////////////////////////1st day//////////   /////////  /////////////////
		mth = 11;
		day=    24;
		hr = 8;
		Calendar calendar = Calendar.getInstance();
		Date date = null;
		
		
		Cineplex cineplex = cineplexDAO.getCineplexes().get(0);//1st cineplex
//		mth = 11;
//		day=13;
//		hr = 8;
		
		Cinema cinema = cineplex.getCinemas().get(0)   ;//for 1st cinema
		for(hr=8;hr<22;hr+=3){
			calendar.set(2013,m       t     h,day,   hr,00,00);
			date = calendar.getTime();
			Sho   wTime s howTime = new ShowTime(cinema, movies.get(0), date);
			showTimeDAO.createShowTime(showTime);
		}
		
		cinema = cineplex.getCinemas().get(1);//for 2   nd cinema
		for(hr=8;hr<22;hr+=3){
			        calendar.set(2013,mth,day,hr,00,00);
			date = calendar.getTime();
			ShowTi    me showTime = new ShowTime(cinema, movies.get(1), date);
			showTimeDAO.createShowTime(showTime);
		}
		
		///////////     ///////////////////////////2nd cin   eplex////////////////////////////
		cineplex = c    ineplexDAO.getCineplex  es().get(1);//2nd c     ineplex
		
		cinema = cineplex.getCinemas().get(0);
		for(hr=8;hr<22;h     r+=3){
			calen    dar.set(2013,mth,day,hr,00,00);
			date = calendar.getTime();
			ShowTime showTime = new S    howTime(cinema, movies.get(2), date);
			showTimeDAO.creat   eShowT   ime(show     Time);
		}
		
		cinema = cineplex.getCinemas().get(1);//for 2nd cinema
		for(hr=8;hr<22;hr+=3){
			calendar.set(2013,mth,day,hr,00,00);
			date = calendar.getTime();
			ShowTime showTime = new ShowTime(cinema, movies.get(3), date);
			showTimeDAO.createShowTime(showTime);
		}
		////////////////////////////////////
		//3rd cineplex////////////////////// //////
		c   ineplex = cineplexDAO.getCineplex     es().get(2);//2nd cineplex
		cinema = cineplex.getCinemas().get(0);
		for(hr=8;hr<22;hr+=3){
		calendar.set(2013,mth,day,hr,00,00);
		date = calendar.getTime();
		ShowTime      showTime = new ShowTime(cinema, movies.get(4), date);
		showTimeDAO.createShowTime(showTi      me);
		}
		
		cinema = cineplex.getCinemas().get(1);//for 2nd cinema
		for(hr=8;hr<22;hr+=3){
		calendar.set(2013,mth,day,hr,00,00);
		date =   calendar.getTime();
		Sh  owTime showTime = new ShowTime(cinema, movies.get(5), date);
		showTimeDA   O.createShowTime(showTime);
		}
		//////////////////////////////////////4th   cineplex////////////////////////////
		cineplex = cineplexDAO.getCine    plexes().get(3);
		
		cinema = cineplex.getCinemas().get(0);
		for(hr=8;hr<22;hr+=3){
		cal     endar.set(2013,mth,day,hr,00,00);
  		date = calendar.getTime();
		ShowTime showTime = new ShowTime(cinema, movies.get(0),    date)  ;
		sho wTimeDAO.createShowTime(showTime);
		}
		
	   	//////////////////////////  /////      //////2nd day/////////////////////////    ///////////
		mth = 11;
		day=25;
		hr = 8;
		
		
		cineplex = cineplexDAO.getCineplexes().get(0);//1st cineplex
		
		cinema = cineplex.getCinemas().get(0);//for 1st c  inema
		for(hr=8;hr<22;hr+=3){
		calendar.set(2013,m      th,day,hr,00,00);
		date = calendar.getTime();
		ShowTime sh      owTime = new ShowTime(cinema, movies.get(0), date);
		showTimeDAO.createShowTime(showTime);
		}
		
		cine        ma = cineplex.getC     inemas().get(1);//for 2nd cinema
		   for(hr=8;hr<22;hr+=   3){
		calendar.set(2013,mth,day,hr,00,00);
		date = calendar.getTime();
		ShowTime showTime = new ShowTime(cinema, movies.ge  t(1), date);
		showTimeDAO.createShowTime(showTime);
		}
		
		////////////////  //////////////////////2nd cineplex// //////////////////////////
		cineplex = cineplexDAO.   getCineplex   es().get(1);//2nd cineplex
	   	
		cinema = cine  plex.getCinemas().get(0);
 		for(hr=8;hr<22;hr+=3){
		calendar.s  et(2013,mth,day,hr,00,00     );
		date = calendar.getTime();
		ShowTime showTime = new Sho  wTime(cinema, mo    vies.get(2), date);
		showTimeDAO.createShowTime(showTime);
		}
		
		cinema = cineplex.getCinemas(    ).get(1);//for 2nd cin  ema
		for(hr=8;hr<22;hr+=3){
		calendar.set(2013,mth,day,hr,00,00);
		date = calend     ar.getTime();
		ShowTime showTime = new Show   Time(cinema, movies.get(3), date);
		showTimeDAO.createShowTime(showTime);
		}
		////////////////////////////////////
		//3rd cineple  x/////////          ///////////////////
		cineplex = cineplexDAO.getCineplexes( ).    get(2)  ;//2nd cineplex
		cinem      a = cineplex.  getCinemas().get(0);
		for(hr=8;hr<22;hr+=3){
		cal  endar.set(2    013,mth,day,hr,00,00);
		date  = calendar.getTime();
		ShowTime showTime = new ShowTime(cinema, movies.get(4)   , date);
		showTim   eDAO.createShowTime(showTime);
		}
		
		cine ma = cineplex.getCinemas().get(1);//for 2nd cinema
		for(hr=8;hr<22;hr+=3){
		calendar.set(2013,mth,day,hr,00,00);
		date = calendar.getTime();
		ShowTime showTime = new S    howTime(cinema, movies.get(5), date);
		showTimeDAO.createShowT               ime(s   howTime);
		}
	   	///   //////////////////   /////////////////4th cineplex////////////////////////////
		cineplex = cineplexDAO.getCineplexes().get(3);
		
		cinema = cineplex.getCinemas().get(0);
		for(hr=8;hr<22;hr+=3){
		calendar.set(2013,mth, day,hr,00,00);
		date = calenda r.getTime();
		ShowTime showTi   me = new ShowTi      me(cinema, movies.get(4     ), date);
		showTimeD AO.  createShowTime(show     Ti     me);
		}
	}
	
	  
	public void createBookingMovieTickets(){
		int ticketID =1;
		int   bookingID=1    ;
		int mth = 1, days=23, yr=  2013, hr=12;
		List<MovieTicket> movieTickets = new ArrayList<MovieTicket>( );
		
		//1st bo       oking
		MovieGoer movieGoe  r =  movieGoerDAO.getMovieGoerByID(1);
		Calendar calendar = C    ale         ndar.getInstance();
		calendar.set(2013,mth,days,hr,00,00);
		Date date = calendar    .getTime     ();
		Tic    ketType ticketType = movieG   oerBL.getT   icketType(movieGoer);
		ShowTime showTime = showTimeDAO.getSho   wTime(1);
		String seatNo =     "A01";
		TicketPri    ce ticketPrice = new TicketPrice();
		float price = tick    etPriceBL.getPrice(showTime, movieGoer);   
		MovieTicket mov    ieTicket = new MovieTicket(ticketTyp  e, showTime, seatNo,price)   ;
		 mo    vieTickets.add(movieTicket);
		movie    Tic     ketDAO.createMovieTicket(movieTicket);
  		ticketID++;
		//m    ake booking
      		Booking booking = n     ew Boo   king(BookingStat  u s.PENDIN   G, date, null,movieGoer, movieTickets);
		bookingDAO.createBooking(booking);
		movieTickets = new ArrayList<MovieTicket>();
		
		
		//second booking
		bookingI   D++;
		mth =1;
		day s = 12;
		yr = 2013;
		hr = 17;
		calendar.set(2013   ,mth,   days,     hr,00,00);
		date            = calendar.getTime();
		//1st movieGoer (his own ticket)
		mov   ieGoer = movieGoerDAO.getMovieGoerByID(2);
		ticketType = movieGoerB   L.getTicketType(movieGoer);
		showTim   e = showTimeDAO.getShowTime(1);
		seatNo = "A03";
		price =ti cketPriceBL.getPrice(showTime, movieGoer);
		movieTicket = new  MovieTicket(ticketType, showTime    , se  atNo, p   rice)  ;
		movieTicketDAO.createMovieTicket(movieTicket);
		movieTickets.add(movieTicket);
		ticketID++;
		//(his grandp  a ticket)
		tic  ketTy    pe = TicketTy pe.SENIOR;
		seatNo = "A04";
		price = ticketPriceBL.getPrice(showTime, movieGoer);
		m ovieTicket = new MovieTicket(ticketTy      pe, showTime, seatNo, price);
		movieTicketDAO.createMovieTicket(movieTicket);
		movieTickets.add(movieTicket);
		ticketID++;
		//make booki ng
		booking = new Booking(Book  ingStatu   s.PENDING, date, null,movieGoer, movieTickets);
		bookingDAO.createBo  oking(b ooking);
		movieTicke  ts = new ArrayList<MovieTicke t>();
		
		//third booking
		bookingID++;
		mth =2;
		day s = 1;
		yr = 2013;
		hr     = 8;
		calendar  .set(2013,  mth,days,hr,00,00);
		date = calendar.getTime();
		//1st movie    Goer (his own ticket)
		movieGoer = movieGoerDAO.getMo vieGoerByID(3);
		ticketType = movieGoerBL.get      TicketType(movieGoer);
		showTime = showTimeDAO.getShowTime(2);
		seatNo =     "B0    3";
		price = ticketPriceBL.get      Price(showTime, movieGoer);
		movieTicket = new MovieTicket(ticketType, showTime, seatNo, price);
		movieTicketDAO.createMovieTicket(movieTicket);  
		movieTickets.add(mo  vieTi    cket);
		ticketID++;
		//(his friend ticket)
		ticket   Type = TicketType.STUDENT;
		seatNo = "C0  4";
		price = ticketPriceBL.getPrice(s  howTim      e,      movieGoer)   ;
		movieTicket = new MovieTicket(ticketType, showTime,    seatNo, price);
		m   ovieTic ketDAO.createMovieTicket(movieT     icket);
		movieT   ickets.add(movieTicket);
		ticketID++;
		//make booking
		booking = new Booking(BookingStatus.PENDING, da   te, null,movieGoer, movieTickets);
		bookingDAO.createBooking(booking);
		movieTickets = new ArrayList<MovieTicket>();
		
     		
  		//     4th booking
		bookingID++;
		mth =12;    
		  days = 2      6;
		yr = 2012;
		hr = 8;
		calendar.set(2013,mth,days,hr,00,00);
		date = calendar.getTime();
		//1st movieGoer (his own      ticket)
		movieGoer = movieGoerDAO.getMovieGoerByID(  4);
		ticketType = movieGoerBL.getTicketType(m   ovieGoer);
		showTime = showTimeDAO.getShowTime(12);
		sea    tNo = "B   03";
		price = t     icketPriceBL.getPr       ice(showTime, movieGoer);
		movieTicket = new MovieTicket(ticketType, showTime, seatNo, price);
		m     ovieTicketDAO.createMovieTicket(movieTicket);
		movieTickets.add(movieTicket);
		ticketID+  +;
	    	//(his grandpa ticket)
		ticketType = TicketType.SENIOR;
		seatNo = "B04";
		price = ticketPriceBL.getPrice(showTime, mov   ieGoer);
		   movieTick      et = new MovieTicket(ticketT  yp    e, sho     wTime,       seatNo, price);
		movieTicketDAO.createMovieTicket(movieTicket);
		movieTickets.add(mov   ieTicket);
		ticketID++;
		//(his grandpa ticket)
	   	ticketType = TicketType.SENIOR;
		seatNo = "B05"        ;
		price = ticketPriceBL.getPrice(showTime, movi    eGoer);
		movieTicket = new MovieTicket(ticketType,        showTime, seatN  o, price);
		movieTicketDAO.createMovieTick  et(movieTicket);
		movieTicke ts.add(movieTicket);
		ticketID++;
  		//make booking
		booking = new Book      ing( BookingStatus.PENDING, date, null,movieGoer, movieTickets);
		bookingDAO.createBo   oking(booking);
		movieTickets = new ArrayLis  t<MovieTic    k   et>();
		
		//5th booking
		booki ngID++;     
		mth =2;
		days = 1;
		yr    = 2013;
		hr = 8;
		calendar.set(2013,mth,days,hr,0   0,00);
		date = cale   ndar.getTime();
		//1st movieGoer (his own ticket)   
		movieGoer = movieGoerDA    O.getMo   vieGoerByID(5);
		ticketType = movieGoerBL.get TicketType(movieGoer);
		showTime = showTimeDAO.getShowTime(12);
		seatNo = "C06";
		price = ticketPri   ceBL.getPrice(showTime, movieGoer);
		movieTicket = new MovieTicket(ticketType, show   Time, seatNo, price);
		movieTicketDAO.createMovieTicket(movieTicket);
		movieTickets.add(movieTicket);
	  	ticketID++;
		/    /make booking
		booking = new Booki   ng(BookingStatus.PENDING, d  ate, null,movieGoer, movieTickets);
    		bookingDAO.createBooking(booking);
		movieTickets = new   Ar rayList<MovieTicket>();
		
		//6th booking
		bookingID++;
		mth =1;
		days = 12;
		yr = 2013;
		hr = 17;
		calendar.set(2013, m       th,days,hr,00,00);
		date = calendar.getTime( );
		//1st movieGoer       (his    own ticket)
		movieGoer = mo  vieGoerDAO.g   etMovieGoerByID(6);
		ticketType = movieGoerBL.getTicketType(movieGoer);
		showTime     = showTimeDAO.getShowTime(1);
		seatNo = "C03";
		price = ticketPriceBL.getPrice(showTime, movieGoer);
		movieTicket = new MovieTicket(ti      cketType, showTime, seatNo, price);
		movieTicketDAO.createMo   vieTicket(movieTicket);
		movieTickets.add(movieTicket);
		ticketID   ++;
		//(his  grandpa ticket)
		ticketType = TicketType.NORMAL;
		seatN    o = "C04";
		price = ticketPriceBL.get   Price(showTime, movieGoer);
		movieTicket = ne     w MovieTicket(ticketType, showTime, seatNo, price);
		movieT      icketDAO.createMovieTicket(movieTicket);
		movieTickets.add(movieTicket);
		ticketID++;
		//make booking
		booking = new Booking(BookingStatus.PENDING, date   , null,movieGoer, movieTickets);
		bo  okingDAO.createBooking(booking);
		mo    vieTicke  ts =    new ArrayList<MovieTicket>();
  		
	}
   	
	pub   lic void createTransa      ctions(){
		int TID=0;
		float total=   0;
		int mth=10,days=13,hr=8;
		Calendar calendar = Calendar.getInstance();
		calendar.set(2013,mth,days,hr,00,00);
		Date date     = calendar.getTime();
//		List<TransactionMethod> method = ne      w ArrayList<TransactionMethod>(); 
//		method.add(TransactionMethod.CUSTOM);
//		method.add(Tr  an  sactionMethod.MASTER);
//		method.add(TransactionMethod.PAYPAL);
//		metho   d.add(TransactionMethod.VIS      A);
		
	   	for(Booking booking: bookingDAO.getBoo   kings()){
			calendar.set(2013,mth,days,hr,00,00);
			date = calendar.getTime();
			for(MovieTicket movieTicket: booking.getMovieTickets()){
				total+=movieTicket.getPrice();
			}
			Transaction transaction = new Transaction(TID + " ",total ,date   , TransactionMethod.CUSTOM);
			transactionDAO.createTransaction(transactio     n);
			booking.setTransaction(transaction);
			booking.setStatus(BookingStatus.ACCEPTED    );
	   		bookingDAO.updateBooking(booking);
		    	TID++;
			total=0;
			hr+=2;
		}
		
	}
	
	
//	public v     oid createPublicHolidays(){
//		SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy H:mm");
//		Date date = null;
//		try {
//			date = sdf.parse(3 + "/" + 1+  "/" + 2013 + " " + "00:00");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		PublicHoliday publicHoliday1 = new PublicHoliday("Happy kiddy day", date);
//		publicHolidays.add(publicHoliday1);
//		publicHolidayDAO.createPublicHoliday(publicHoliday1);
//	}
	
//	public List<Cineplex> getCineplexes(){
//		return cineplexes;
//		
//	}
//	
//	public List<Cinema> getCinemas(){
//		return cinemas;
//	}
	
	public List<Movie> getMovies(){
		return movies;
	}
	
	
	
	
	
	
}
