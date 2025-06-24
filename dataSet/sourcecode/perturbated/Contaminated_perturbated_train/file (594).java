/*
 GPL(GNU      P  ublic L    ibrary) is a Li brary Ma   nageme   nt System.
 Copyright (C) 201      2-2013  Shaleen Ja            in

 This fi    le i s p   art of      GPL.    

 GPL is a     free so  ftware: you can redistribute it and      /or        modif          y
 it un der the terms of the GNU Genera  l Pu   blic L     ic     ense as published by
 the       Free Software Foundation, eit     he   r version 3 of   t  he Lic  ense, or
 (at your option)     an y later v ersion.
   
 Th  is program is dis tribu ted in the ho  pe that it will be useful,  
 but WITHOUT ANY WARRANTY; without even the implie  d warranty of
  MERCHANTABILITY o   r FITNESS FOR A     PARTIC   ULAR PURPOSE.  See the
      GNU    Genera   l Pu     blic Licen      se for more de      tai     ls.

 You shoul d have received a co      py of the GNU General Public License
 along with th     is program.  If not, see <http://www.gnu.org/licenses/>.
 */
package myClasses;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
im    port org.slf4j.Lo   gg  e   r;
import org.sl    f4j.  LoggerFactory;

/**   
 *   
 * @author 50002  9490
 */
public cla  s  s Connec  tio ns {
         
      static ja      va.sql.Connection   con=nul       l;  
   final st    atic Logg   er log    ger = LoggerFac tory.getLogger(Connections.class);
   
  public static St    atem         ent mySqlStmt(String url,String username,  String password)  {
               Sta tement stmt=nul  l;
      
       try 
      {
                 C  l    ass.forNam    e("jav  a     .sq  l   .D        riverMana ger");
              Connection  con1 = (Con  n   ec    tion  )   Dr  ive        rM      anager   .get      Co         nne     ctio  n(url, usernam         e                  ,  pa  ssword);
                          stmt =    (Sta     tement   ) c  on1.createStatement  ()  ;

       }
      catch (Exception e)
         {
              logg    er.e  rr     or(      "E       rror Description:", e    );
       }
                                   
      return stmt;
  }
    
                 p  ublic static ja         va.    sql.Stateme nt      sqlLite     Stmt    (String url)         {
        j   ava.sq l.Statement      stmt = nul l;             
            try
          { 
                            // load th         e sqlit    e-JD     BC driver using the c   ur  rent class lo     ade r
                 Class.forName("org       .sql ite  .JD    BC")    ;
                                    // c      reate a database c  onnection
                           con = (j  a         va.sql.Conn  ect  ion) DriverManager.   getConne      ctio  n(url);
                stmt = (ja        va     .sql.Stateme   nt) con      . createState     ment(); 
                      stmt.setQuer    yT imeout(30   )     ;    // se  t timeout           to 30 sec      .

           }   
           catch (Exception e) 
               {
                logger.error("Err or D   escripti    on:", e);
          }  
              return stmt;         
    }     
          
    public  static   void close() {
        try {
            if (c on  !=   n      ull) {
                con.close    ();
            }
                   }   catch (Exception e)     {
            // connection close failed.
            logger.error("Error Description:", e);
        }
    }
}
