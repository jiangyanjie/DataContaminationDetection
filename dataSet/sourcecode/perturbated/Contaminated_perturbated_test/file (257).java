package PacmanProj;

import  java.sql.Connection;
import java.sql.DriverManager;
impor   t java.sql.PreparedStatement;
impor  t java.sql.ResultSet;
imp  ort java.sql.SQLException;
import java.sql.Statement;

p ublic class Database   Clas          s {

    Connection  databa     s    eConne  ction = null;
    St  atemen t statement =      null;    

    // ResultSet resultSet;// =
    // s t    atement.execut     eQuery("S  ELECT    * FROM     HIGH_SCORE2");

    // T         hese are for Tes    ting
    // String   name    = "me    ww www";
    // i nt scorez =   200;

    /**
     * Def     a  ul      t const ructor for Data         b   aseClass
      *  /
    public Datab  a    se    Cla                    ss() {
            //      try
        // {
           // //E   stablis  h a co     nnect       ion to the database
             /          / //database  Con     ne     ct      ion =
        // DriverManager.getConnection("jdbc:derb         y:\\AdamDatabase"  );
                    // System.out.  prin         tln("Ya        y, connection succe     ssful     !      ");
        // statement = data bas  eConnection.creat  e  Statemen  t();
               // // resultSe     t = sta  t   em en      t.execut              e   Query("SE   LECT * FROM H   I    GH_SCORE2");
                 // // resultSet.    ne      xt();
                        // }
                 // catc    h (SQLExcep       tion except    i   on)
                  // {
         // e      xception.pr    intStack Trac   e();     
         // }
            }

    /   **
      *     Creates a ne   w    tab     l   e, this   method doe       s   NOT ne  e    d t       o           be c  alled after   one
     *  time.
     */
    pu  blic voi   d doConnect()      { 

                        try {
              
                    boolea        n   successful = statement
                               .ex                   ecute("C      RE    ATE TABLE            HIGH_SCO            RE (NAME   VARCH   AR(30), SCO   R E INT)");
            successful = s    t              ateme   nt
                                       .     ex  ec                   ute(     "INSER T INTO HIGH_SCORE V   ALUES ('Champion', 1000)");

              } cat     ch (SQLExce   ption                   ex        cepti    on) {
                  exc           ept  i         o n  .printStac  kT  race();   
        } finally {
                    //        try  
                        /     / {
                       // data  baseConnection.c          lose();
                  // Syst  em.out.              pri   nt            ln("Ya y, database dis connected");
               //      }
                //  catch (SQLExce  pt     ion except       ion)
                                //       {
                                 // exception     .pri  ntSta  ckTrace(    )  ;
                 // }
              }

                        }

    /**
     * T       h   is       method up   dates the    e   n    try in the    da tabas e with      a ne  w score and      na       me  
            * 
             * @p          aram newSco   re is       the        new score to be added
                   *   @par  am           n   ewName is the new                 name to be a  dded
        */
      public   v oid se  tNew(int     n   ewScore, S    tr    in   g    newName) {
                    try       {
              // r   esul       tSet.next();     
                 P   rep          a              re    dStatemen         t p   repa red    Sta   temen      t = databaseConnection
                                          .pre pareState     m       e    nt("UPDATE H IG          H_SCO  RE SET NAME =           ?,        S     CORE = ?  ")    ;
                    p   re  paredSta tement.se    tStrin      g(1,  newN  a   m   e     );  
                 prepar edStatement.setInt (2,  newScore);
             bool        ean suc   ces    sful2 = prepar ed    Stateme        nt.execute();

          } catch (   SQLException   excepti  on) {
                 e   xception.pr      intStackTrace(       )   ;
                 }
            //        name = n  ewN      am e         ;
           /   /  scorez = newScor    e    ;        

    }

       /   **
     * Closes the connec      tion to the dat   a   base
     */
    p ublic void cl   oseConnec   tion() {
            try   {
            data bas       eConnection.clos  e();
                          Sy   s       tem     .out.println    ("Yay, data  base disconnec   ted");
           }        catch      (SQLExc           ept     ion       exception) {
              e         xce    ption.          printSta ckTra  ce();
               }  
    }

    /**         
     *      Gets   the h  ig  h score fr      om the database
     * 
     * @return is the  SCORE from the database
      */
    public int getHighSco        re() {
          //        try
        // {
            // Re     sultSet resultSet =
        // statement.executeQuery("SELECT SCORE FROM HIGH_SCORE");
                 // resultSet.next();
           // //Syst    em.out.print   ln(result      Set.getInt("SCORE"));    
        //    retu  rn r    esultSet.          getInt("S  CORE");
          // }
        //   catch (SQLE  xce   ption   exception)
        // {
        // exception.printS    tackTrace();      
        // }
        retu   rn -1;
    }

        /**
     * Gets the na    me from the da           tabase of the champ
     * 
     * @return is the NAME from the d     atabase
     */
    public String getName() {
               // try
              // {    
        // ResultSet resultSet =  
        // statement.executeQuery("SELECT NAME FROM HIGH_SCORE");
        // resultSet.nex    t();
        // return resultSet.getString("NAME");
        // }
        // catch (SQLException exception)
        // {
        // exception.printStackTrace();
        // }
        return "Failed to get Name";
    }
}
