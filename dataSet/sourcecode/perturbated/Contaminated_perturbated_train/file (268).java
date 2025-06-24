package aglosh2014.appspot.com;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class  Academy   
{                      
            private   Stri ng a  cademy  _  name;
          public ArrayList    <Circle>     circles; /   /list      of circ      l     es   in ac   ademy
                 private ArrayList<User> us      ers;
     
                  public Academy    (S t                          ri     ng          academy        _name)
               {
                                                      t       his     .academy_name         =aca     d  emy_n ame;
  
                           users=new ArrayList<     >();
                  ci  rc   le        s=new A  rrayList<>     ();
                     }

            publ  ic Str  ing ge   t_aca  de    my _name    ()           
            {
                  r  eturn academy_name;
           }

                          p  ub   lic int  g  et_num_  of_use  rs_i    n_           acad emy()
        {
                     retur    n users.s          ize();
        }   

            public int get_num       _of  _circles_in_   aca demy()
           {        
                    return   circ les.s     ize   ();
        }   

          publ      ic    Circle[] get        _circles_in_acad         emy()    //retu       rn   s      an array of     the ci       rcles (as o                  bj      ects)
                                 {     
                                             Circl     e[] a  rray = ci    rcles.t        oArray(new C            ircle[  circl  e s.      s        ize()]);        
                          retur     n array   ;   
        }

         pu                 blic      String ge  t_cir    cles_ in _academ  y_as      _    st ring() //sepa   rat              e d by comma " ,"  retur     ns:   c       ircle1_   n              ame  , ci rcle_        id1,  ...      
                     {
                            Str         ing lis   t="";
             
                                             for (in  t       i=0; i  <circle s.siz  e();       i++)
                   {
                                            if(i!=0)
                                                                                          li  st          +=","; //add comma     

                                            list+=circles.      g    e    t(   i).g      et _c     ircle_name(      )   ; //ad ds  circl  e name t        o string

                                                  lis    t+=  ","   +                circ              les.get(i).get_circle   _i  d(); //   a   d    d c ircle id
                              }

                 return     list;
             }


                       public U  ser        ge t_use        r_b  y_id(int id   )
        {
                           f     or(i     n  t i=0; i<      users.   siz       e(); i++)
                                    {
                                                if          (user   s.get (i).get_id(    )         ==id)
                                       re   t    u          rn u ser    s    .ge    t(i);
                                 
                    }

                             retu     rn          null;
                              }


               public St   ring get_  c    i  rcl    es_in_sp     ec    _year  (int year   ) //returns     c   i  rcle  1_name, circle1_id, ...  
                     {
                         St           r         ing circles=" ";

                                    for(in  t i=0; i           <this.c    ircle    s.      size()        ; i++)
                                                      if(t   his.circ   l    es.get    (i   ).     get_cir   cle_year(     ) =    =ye     ar){
                                           circ  les+=     thi s.circ   les. g      et    (i).get_ci   rcle  _name() + ","; //add name     to string
                                                ci   rcles+=  t     h    i s.circles.g           et(     i)      .get_  circle_id() +  ",   ";     /      /add   i  d to        string
                                                    }    

                            circles=cir       cles.subs    tring(0,        circle   s.len  gth                ()-  1); /     /rem   ove    last    ','

                                           return c  irc   l      es;
             }
     
         public   int user_login   (int user_id,           String       passw     ord) //r       eturns user-typ       e, oth   erwise -1 
            {
                                  fo       r(  int  i    =     0;     i<      us er s.size(); i++)
                       {   
                                                                               if(    users.get(i     ). get_i  d()==user_id)
                                                        if       (us     ers           .get   (i).vali d_password  (p      ass             word))    
                                                         ret    u     rn users    .ge      t(  i).get _   user_typ    e();
                                                            }

                          retur      n -  1;
        }

                   public String ge t_user  _na    me_b   y_id(int use   r_id)
               {
                    User user=get_u       ser_b         y_id(us  er_id)  ;
  
                                if(use  r!=     null)
                                             retur     n  us       er.        ge  t_na   me();

                      return   nu   ll;
                      }

                       publ     ic String  get_ course   s_name_lis   t_    in_circle  (   i    nt circl        e_id) //return       s name & id,    cou  rs   e1     _n   ame     ,    course1_id
               {
                   int circle_ i              ndex      =ge       t_circle_in    dex _in_array(circle_id);

                            i       f(ci   rcle_index==-1    )
                                      re              turn null;

                         String courses = circles.g  et(circl       e_  index).get     _co urs es_in_  ci      rcl     e_as_string   ();  

                     return c ourses;

                               }

            public             int[] get_students   _i    d_in_course(int circle_id  , int course_  id)
                    {
                               int circle_index=get_cir c l e_i               nde   x_in_ar    ray   (circl    e_id);
    
                        S        y    stem.out.print                       ln(    c     ir          cle_id + " " + course_i   d     + " " +     circle_i  ndex);

                                   if(circl    e_index==-1)
                                           r   et    urn  nu     l     l ;
     
                   r         eturn     cir   cles. get(circle_i   ndex).get_s    tuden   t_id_list_i    n_course(cour  se_id);
                 }   
                 
        public Student[] get_  students_arra y_in_course(int ci    rcle_id, int            cours  e_id )
          {  
                   int c   ircle_index=get   _circle_index_in_arr   ay(    circ le    _id        )      ;

                            System.ou t.print ln    (circle_id    +   "           " + course_id + " " +cir         cle_i                    nde x);

                             if    (   circle_index ==-1)
                                  return null;           

                  retur  n circles.get(ci rc   le_index).get_stu  dent_li  s   t  _in_course(course_id);
          }
                    
        public     Stri     ng get_    st      udents_array_by_   typ   e(int user_type)
                   {
        		String  list=    "";   
                      		fo    r (Use    r          user:users)
             	  	   {
                 			    if        (use r!=null&   &   us   er    .user_     type=   =u      ser         _ty  pe   )
                 	    		{
          				 list+=us   er.name    +    "   ,";
                 				
              			}
            		}
                 		return list;
        }
         p    ublic Str  ing ge  t_   u  sers_na     mes_and_id   _by_type(      in  t u        ser_   type)
        {
        		St ring                 li   st="    ";
                       		     for   (User   use r:  users)
                  		{
        			      if(u se    r   !=  n     ul            l  &&user.user_type==    u   ser_t     ype  )  
          			{
         		     		      l       i st  +=u         ser.  name + ","+user.user_id+      ",";   
            	   			
               			}
                		 }
               		return list ;
        }
        pu  b        lic S  tring get_studen     ts_array()
            {     
             		S  tring list     =""  ;
        		for  (Us er user:users)
                		{
           			i         f(user!           =null)            
                 			      {
                     				 list+=    us   er         .na  me +  ",";
        				
          			}
        		}
            		r        eturn list  ;
        }
 
                           private in   t get_circ      le_index_in_  array(int circle_id)         /              /re  t   urn -1      i    f n ot               found
                   {          
                              for(int i=  0; i<circles.size(     );    i++)
                                      if  (circle _i    d==circles.ge   t(i).      get_circle_i  d())
                                                  r         eturn i;

                  ret    urn -1;
        }    

                public     int add_new_cir     cle    _t    o_a  cademy(int         circle_id  , String c   i      rc   le_na  me, int cir    cle_year) //ret       urn      -      1    if no room
        {
                         i    nt circle_index=get_c         ircle  _inde       x_i   n   _a     r r   ay(ci              r        c le_id);               

                           if(   cir   c              l e_   index!=-1)
                        r   eturn 0;        //circle exist

                                 this   .cir  cles.a         d       d  (new Cir    cl e   (      circle_id, circ   le_nam  e,               circ         le_year));

                                            r   etur    n circles.siz  e( )-  1  ; //cou  rse a  dded
                 }
             
               pu     blic  i          nt      add_new_course_to_ c      ircle(in      t circle_id, Stri ng         cour      s   e_     name,        in          t co             urs  e_id, Lec t    u       rer lect   urer,    Ch     e    ck             er checker)
               {
                   int   ci  rcle_ind  e       x=get_circle_inde    x_i   n_a         rray(c    ircle_i        d);

                  i  f(circle_index    ==-1)
                                                                return 0; //c  ircle doesn't    e   xist

                     return thi    s       .cir  cle    s.get(circle_index).add   _new_     course_to_circle(c our       se_name, cou  r   se        _id,   lectu  rer, check          er       )     ;
                       }
            
             p                     ublic int add_new_cours  e_to_c i  rc    le_ by_ids(int circle_id    , S  t     ri     ng   co u     rse_name, int cour            s        e_id, int le   cturer_id,            int     checker_id)
            {
                                //check            if lecturer and    checker are vali       d
                                 Use   r     checker =     ge   t_user_by    _i         d(            c   hec    ker_id);
                   User  le           ct   u                    rer               = get   _use  r_by_id(le      cturer    _id)  ;
                   
                               //  if          inval        id lecturer      
                        i  f(le   cture   r       == null || le      c    turer.user_type!=Use   r.LEC      TURE   R)
                            r         eturn -2   ; //invalid        l ecturer
                
                         //if invalid chec ker
                                                      if(checke            r ==   null || checke   r.u ser_ty          pe!=User.CHECK     ER)            
                                                return   -3; //i       nv     ali d ch        eke r
                                               
                                     retur    n          add_n     ew_cour      se       _to_circle(circle_id, cours                       e_    n            ame, c ours   e_     id ,    (  Lecture    r)lectu  r     e        r, (Checker    )   c      he cker)  ;
                          }

                  pu  blic int add_    new_stude    nt_to_         co   urse(int     ci rcl    e_id, in   t     cou rse_id,     Student    student  )
          {     
                        i     n    t          ci   rcle_index=g    et_circl     e_i   n   dex_i   n_arra   y  (circle_id   );

                             if(circ    le    _   in          dex        ==   -1)
                                                     ret ur  n      0     ;          //cir   c   l  e do   esn   't       exist

                         ret      u   rn this.circles.ge  t(circle_index).add_new_student    _to_course(c    ourse  _i    d,        stu           dent);
               }     
   /*        
              public      in   t  add_  n  e    w    _stu    d      en              t_to_co                 urse_     by_id(int       cir   c           le  _id, int     cours          e_id,           int     s  tu d  e            nt _id)
                        {    
                                     int  c     irc  le_index=g       et_   circle_in   de      x_in_  array(c       irc     le_id)     ;

                         if(    cir   cle_inde         x==-1    )
                            return 0   ; //c         ircle doesn't exis     t  

                                 
                                }  
*    /
               public Lectur er     add               _n      e w_lectu  re      r   (in   t i d, S        tri     ng n   ame, String passwo    rd)
            {
                                        use             rs.add(ne   w  Lectur er(id, name             , passw  ord))     ;

                                   return (Lect        ure                       r   )u       sers.      g  et(   u  sers.  size()-  1);
        }
   
                  publ    ic Admin ad       d_n                   ew_admin        (int       id, St         ring n        am      e,     Strin g        p   a ssw             ord)
                { 
                     users.       add(ne         w Admi     n(id   ,   name, p                 asswo    r     d));    

                   retu  rn        (Admi  n)user       s.ge                   t(users.si      ze         ()-1) ;
          }       

                   public            Stud  ent add  _new_s     tudent(i  nt              id, St r     i  n   g          n  ame, String passwo rd    )
                         {
                            users.       ad d(new Student(id, nam   e, pa  ssw     ord));
     
                                ret     urn (Stud   ent)users.get           (use rs.si    ze()-    1);
                               }    


        p      ublic Checker ad  d_new            _                       checker(int id, St            rin                    g na            me , String         passw          or   d)
                    {
                            u          ser  s.ad      d(new              Checker   (id, name,    password  ))      ;

                   ret   urn        (Ch   e        cker)u         sers.ge     t    (u    ser      s.size()          -1      );
                               }

                                  public int[        ]  get_cir  cl e     s_yea    rs ()
                               {  
                     if(               circl  es.isE       mpty( ))
                                              retur    n nul                       l;
     
                                   int years[         ]        =n   ew int      [circ l        es.s          i z e    ()]       ;
                        int   ne    x   t         _      yea   r _ind          e      x=0;
                                    b  o     olean is_exi  st                  =fals  e;

                                      f    or(in          t i=0                 ; i<   cir  cl   e    s.size    (); i++)      
                                      {
                                                     int     new      _year  =ci  r      cle    s    .     get(     i).ge   t_ci   rcle_year(); //  get year

                                                                          for   (in t j        =0;   j<next       _year_index;    j++) //check if ye ar 
                                             {
                                                                      if(n      e    w_ y     ear==years[  j   ]) /        /     i      f     year   alread y       exis      t 
                                                                  {
                                                                                                                               is_exi    st=tru    e;
                                                       br    eak;
                                                                         }
                                                                                      }

                                         if(is_ex          ist =    =fa lse)
                                                          {  
                                                             y         ears[next_year   _inde            x         ]=ne  w_     y   ear    ;
                                                                                                               next_y  ear_in   dex++;     
                                                    }
                               else
                                          is_exist=fals      e;       
                                               }

                                    i     nt      new     _yea  rs  []   =    new int                     [next_year_index]  ;

                for(int i=0; i<   n   ext_year_index; i++)
                                new_ye a   rs[ i]=  year  s[i]; 

                              A            rr      ays .sort(new    _ye   ars);

                                        return  new _yea rs;
        }    

             public String get          _circl        es_years_as_s    tring        ()
                            {
                       int   ye a rs[]  =get                       _cir  c     les_years();

                    if( ye    ars==null)
                                                          return    null;

                                        St     ri  ng years_as_stri ng="   "          ;

                        fo   r   (i        nt i=0; i<yea rs.length; i++)
                                                         y  ears_as_   string+=ye    ars           [i] + "," ;

                                                             years_as_   str ing = y ears_as_s          t     rin g.substring(0,         year     s_as_     st    rin             g.len     g     th   ()-1);
    
                             return   years_as           _string;
        }

        pub    lic String    ge      t_user      _data     _by_i       d(int     user_id)           /  /stopped    here
                   {  
                                Us  er user = get_u   ser_by     _             id(     user_id);

                                     String     user_d  ata="";

                                 A   rrayL  ist<Inte     ge     r>      years=g     e t_user_ye  ars(user_i   d)  ;
                   Arra  yList<Circle> circles=user.user       _       circ  les;
                             ArrayList<Co    ur    s    e  > cou                        rses=use r.user_courses    ;
     
                      //add years, circle & courses to   data
                                            for(int       y     ear: year      s)      / /fo r each yea     r
                       {
                        user_data+    =year   + "["; //add y    ear

                                        for(    Circ  le circle: circl     e   s)   //          for each    circ   le     in use r c   ircle        s
                                         {
                                                 if(circle.get_circle    _year ()!=ye   ar) //if circle not     in y     ear   
                                                            br   e  ak;

                                      //add circ   le
                                                     user   _da     ta            +=circle.   get_   circl       e_      nam e()    + "," + circle.       ge       t_circle_id()  + "("; //add   circle

                                                               f   or   (Cour  s   e   course:    co   urses) /          /for each cou    rse i n circle
                                          {
                                                                    if     (!c       ircle.courses.contains(co urse)    )    //if c           ourse is not   in   circle
                                                       break;

                                                                            //add co     urse
                                                                     user_data+=course.get  _ course_na   me() + "," + course   .get_course_id()    + " |";
                                            }

                                                user_     dat  a=use   r_da           ta.substrin  g(0, use    r_data. len   gth() -1); /  /   r      em      ove     la s  t "|"
                                       u   se     r_  d  ata+=")"; //end of circle 
                                                     }
  
                                  user_data+="]"; /   /end of year
                    }
   
                      return u  ser_da       ta;   
            }

          pu blic    Ar rayList<Integer> get_us   er    _yea       rs(int   use    r_id  )
        {
                    User u  ser = get_user_by_id(u  ser_id);
                   
                           if(u  ser==n   ull )
                                     return nul         l;        
                
                   ArrayList<Cir   cl         e>  circles=user.user_circle     s;

                     Array         List          <Integer> y       ears=new ArrayLis  t<>();

                //add years
                for(Circle circl    e:  circ    les) //list o   f all    years
                         {
                                    if(!ye           a  rs.    c   o    ntains(ci   rcle.get_cir    cle_year     ())) //if n       o       t in    years  list   
                        {
                                                 years.add(circle.get  _circle_year())   ; //add year to            ye        ars li st
                                 }
                   }

                           //so    rt yea rs
                    Collec   tions.   sort( years);

                         retur      n years;
               }   
        
        public String ge     t_user_years_  as_s  tring(in   t user_id)
        {
                  ArrayList<Integer>       years=  get_user_years(user_id)    ;   
                    
                         if(years==      null)
                          r    eturn "";    
                       
                       String user_years   ="";
                
                 for(int year: years)
                {
                            user  _years+  =y   ear + ","          ;
                }
                 
                if(!user_years   .  is    Empty())
                            user_years=user_year   s.sub    string(0, user_years.length()     -1);
                     
                return user_years;   
         }
        
           public String get_user_         circle    s_by_year(in  t user_id, int y             ear)
              {
                User user = get_user_by_id(user_     id);
                
                if(    user==null)
                             return "   ";
                       
                              Arr      ayList<Circle> circ   les=u    ser.user_ci    rcles;

                  String user_circles="";
                
                for(Circle circ le: circles    ) //for each    circl      e      in u    se  r circle  s
                {
                            if(circle.get_circle_year()==ye    ar) //if circle in year
                                 {
                          //add    circle
                        user_circles+=c    ir   cle.get_circle_name() + "," + circle.get_c     ircle_id() + ","; //add circle
                        }
                      }
                   
                     i    f(!user_circles.isEmpt y())
                         user_circles=user_circles.substring(0, user_circles.length()-1); //remove last comma

                   return u ser_circles;
             }
        
        p     ublic String ge   t_user_courses_  in_circle_by_year(int user_id, int year, int circle_i d)
        {
                User user =     get_user_by_id(user_id   );
                
                if          (user==null)
                             return "";

                   Array   L     ist<Circle> circl es=user.user_    circles;
                Arr ayLi  st<Cours   e> courses=user.user_cour   ses;

                String user_courses="";
                
                for(Circle circle: circle      s  ) //for each circle in user circles
                       {
                        i     f(circle.get_circle_year()=     =year && circle.get_  circle_id()==circle_id) //if circle in year
                        {
                                  for(Course course: courses)
                                {
                                                 use  r_courses+=course.get_course_name() + "," + course.get_course_id() + ",";
                                   }
                                  
                                     break;
                        }
                }
                  
                if(!user_courses.   isEmpty())
                                       user_courses=user_courses.substring(0, user_courses.length() - 1); //remove last   comma
                   
                return u    ser_courses;
        }

            public void remove_circle_by_id(int circle_id)
        {
        	
        	for(Circle circle: circles)
        	{
        		if(circle!=null&&circle.get_circle_id()==circle_id)
        		{
        			
           			//circle.remove_all_courses();
        			if(cir     cles!=null)
        				circles.remove(circle);
        			break;
             		}
        	}
        }
        
     
        public void remove_course(int course_id, int circle_id)
        {
        	for( Circle circle: circles)
        	{
        		if(circle!=null&&circle.get_circle_id()==circle_     id)
        		{
        			circle.remove_course_by_id(course_id);
        			break;	
        		}
        	}
       	}
}
