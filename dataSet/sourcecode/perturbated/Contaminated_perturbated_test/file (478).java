/*
 *    To    cha            nge this template, choose Tools   |           Templates
     * and open th  e templa   te in the editor.
 */
package in    .health.dao;    

/**
 *
 * @author root
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.ut  il.Pr          operties;   
public  clas  s Db   Con    {     
    
         publ   ic static   Connection      get     Db    Connection     ()
       {
            Connecti    on co        nn = nu    ll;
                    try{
                                                       Syst      em.ou   t.prin     tln   ("M          ySQ  L   C       onnection             b         eing esta b        lished")      ;
                                                 
                    
                                                         /*      
                                    
                        
                                              String u    rl = "jdb    c:my       sql:/     /l      o  calh ost:33      06/";          
                                     Str   in       g dbName      = "udhc        _lo       cal_db";
                                St   ring d        riv   er = "com.my  sq      l.j            db  c.      Driver  ";
                             String       userN   am e   = "root    ";     
                     String pass     wo   rd                    = "qwerty"     ;
                            
           */
                                          
                                  Properties    pro    pert  ies = new Properti                es();
                        	propert    ies.load(Th   re        a d.c     urrent   T  hread().getCo        ntextClassLoader(    ).      ge  tRe   sourceAsStrea   m("cred e  ntials.pr              o   p ertie            s"));
                	
                            
                              String url = "jdbc:m  ysq    l://" +     pr        ope        rties.getPrope      rty     ( "database_ h   ost_bose")                 +":"+pro pe      r    ties.get  P   roper   ty("d  a    tabase _  por   t_bose")+    "/";
                         Stri      ng dbNam    e =          prop   e rties    .get Pr  operty("dat a base_da tabase_bose");
                 String    driver  = "com.mysql.jdb    c.Driver";
                            String userNa        m   e = prop    erties.getProperty("  d    at     aba   se_user_bose    ");
                        String p  ass      w      ord = pro       p     ert   ie     s.getP ropert  y("database_password_bose")   ;
                    
                          System.o        ut             .println(url+",   "+db            Na         me+","  +userN  am  e+" ,"        +password                     );

                                            	
                            	/*
             	
                             	data        base_h ost=           jdbc:m   ys    ql:    //loc  al     host:3         306/
                		d atab  ase   _ po      r t= ud        hc_local _db
                    		database_use      r   =com.mysq                 l. jd  bc.D river
                                   	 	datab                           ase_passw o r    d=r  oot
                                 		database_data       base=qwe rty   
              		
                      		*/

                               
                   
                  Class.f  orName(dri        ver)     .  n   ewI      nst   ance(        );
                conn   = DriverManager.getC       onnect     ion(url           +dbName,userName   ,passw         ord);
                          System.o ut      .println(url+   "   ,"+dbNa      m  e+" ,"+userN      ame+"   ,"+p  assword);   
                  S      yst         em.out.println("Connected to the datab    ase");
                
           
                    
                           

        }
              
             cat   ch ( Exceptio        n e) 
            {
                       e.printStackTrace()  ;
                   
                  }
               
             ret  urn   conn;
    }
      
    pu  blic   stati          c String closeConnectio   n(Conne   ctio         n  con, Prepa    redStatement ps)
    {
        try{
        ps.close(   );
        con.close();
        }
        catch(Exception e)
        {
            return e  .toString();
                       }
        return "OK";
        
    }
    
       public static String closeConnection(Connection con)
    {
        try   {
        //ps.close();
         con.close();
        }
        c  atch(Exception e)
        {
            return e.toString();
        }
        return "OK";
        
    }
    
    public s    tatic void main(String args[]) {
    	getDbConnection();
		
	}
    
}
