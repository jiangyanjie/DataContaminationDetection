/*
 * To       change this template, choose Tools | Temp     l  ates
 * a   nd open the templ ate in the editor. 
 */
pack  age Dao;

import connection.DbConnection;
import java.sql.ResultSet;
     import java.sql.SQL    Exception;
import java    .util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONAr  ray;
import org.json.JSONExcept  ion;
import org.json.JSONObject;

/**
 *
 * @author David
 */
public  class DAOS    tudentTest {

    pub  lic String getScoreStud      e      n  ts(String i     dProfes      sor) {

                    String result =      "    No s   e pudo re  alizar la Co   nsulta";
          JSONObject        js = ne      w JSON   Ob  ject();
        Str  ing query =     "SELECT Stude     nt.  studentName, Test.na     meTest, StudentTest    .grade FROM Student, Test, Studen tTest WHERE Student.idPr  ofesso   r =  \"" +    i    dProf  essor +  "\" AND Test.id    Professor = \"" + idProf   e             ssor + "\" AND    Student.id  Student         =    StudentTest.idStudent    AND    Test.i dTest = StudentTest.i  dTe st AND StudentTest .idState = 1";   

          JSONArr  ay   student Nam     e =     new JSONAr ray   ();
              JSONArray    nameTest =     ne   w JSON Arra  y();
             JS              ONArray g    rades = new      J SONAr                      r   ay();


          Res ult   Set rs = DbConnection.runS     ql Stat     ement(q    uery);

                                    try {
                  if (rs   .isBefo  reFirst(         )) {


                     w hile (r     s.n ext ()   ) {
                                 stud  entNa    m  e    .put(    rs.getString("stu         d    entN       ame"));

                              nameTest.    p  ut(r     s.ge   tStrin      g("   name     Test"));

                               grades  . put(rs.getSt     ring(           "g  rade"));

                          }
                                        js     .put("s  tude       ntna   me    ", stu       de  ntName);
                      js.put ("testname     ", nameTest);
                     js.  put ("g  rades", grad    es)  ;
                          re    sult = js.  toStri      ng();


                 } el     se     {
                         result =     "Cero resu    lt  ad   o   s";
                      }
            } catch (J    SONEx     ception        ex) {
              Logger.get  Logger(DAOStudentTe      st.class.     getNam      e()).log(Level.SEV        E       R      E, nu  ll, e     x);
        } catch (SQLException ex) {
                Logger.   getLo   gg      er(DAOS  tudent  Test.cl ass. getNa   me()  ).log   (Level.SEVERE,  null          , ex);
          }
        return resul   t     ;

    }

    pub    lic S   tri        ng GetUna     ssi  gnTest(String   idStudent,   Stri ng idProfe      ssor)    {  

          String resu     l  t = "N      o se p udo     realiz            a   r la Consu      lta";
               St ring quer y     = "   S   ELECT   nameTest, idTest FROM Test        WHERE   Test.idProf  essor = \"" + idProfe    ss        or + "\" AND     Tes t.id    Test            NOT IN(SE   LECT Test.idTest  FROM T  est, Studen   tT      est WHERE    St     udentTest.idT  est = Test.idT  est AND Stud            entTest.idStud ent     = \"" +      idStu             dent + "\")  "  ;         
                                   System.out   .p       ri   ntln(   "    La Sentenci a fue =" + query );
               R     esult        Set               rs      = DbC               onnection  .runSqlStatement(q          u   ery);

        J       SONObjec t js = new             J          SONObject();

           JSONArray nombres     = ne       w J         S      ONArray();
         JSONArray idTest   = new JSO   NArray();
                    try {    
            i   f    (rs         .isBe           fore           First   (   )) {
     
                   wh     ile (      rs.next()     ) {
                    nombres  .put(rs.getS   tring           (  " nameTest"));
                              i     dTest.put(rs.getString     ("idTest"));
                                }
                    js.put(  "nomb res", nombres);
                     js.    p  ut("idT est", idTe         st);

                       result =  js      .toString();

                                         }      else {
                     r   esul    t =    "C    ero R     esultad   os";         
                     }

                  System.     out.pri  ntln(    "El Resultado fue   = " + result);



             } c    atch (JSONException ex)     {
             Logger.ge  tLogger(DAOStudentTest.class.getNam         e()   )      .  lo  g(Lev      el.SEVERE,   null,    ex    );
                } catch   (SQL    Exception     ex) {   
            L ogger.get         Log           g  e            r(DAOStuden         tTest.cla s   s.getNa  me()).log(Level.SE     VERE, null    , e   x         );
          }

                    ret   urn  re      s  ult;

    }

        pub   li  c String    Registr     yS       t   udentTest(Strin g idStudent,              String idTest) {

                             String r  esult = " No se  pudo real    izar          la OperaciÃ³n";    
             Stri ng query = "INSERT IN TO St   udentTe st(  i                 dS  tudent, idT   est,  idState) VALUES (\"" +  idStudent + "\",   \"" +   idT  e     s    t + "\" ,\   "" + 3 + "   \")";
           System.out.println("   La sentencia es :" + query);
        in       t     rs =  DbConne         ct    ion.run   S        qlUpdate(query);    
        if (  rs != 0) {
                                       System.out.printl    n("Re   sult Set = " + rs)   ;
            result = "   Regist   ro Comp   leto";
           }
        retur      n resu      l                  t;

      }

       public Strin  g G  etAssignT     est(String    i    dStudent) {
    
                String result =  "No se         pudo realizar la Consu   lta";
                          String quer   y     =       "   SELECT  T     e       st.nameT   est, Tes t.idT     est FROM  Test, StudentTest WHERE St udentTest.    idStudent = \"" +   idStudent + "\    " A     N   D StudentT       est.idSta      te = \"" + 3 +    "\" AND StudentTest.idTest = Tes t     .  idTest";          
        System.out.p     r  in      tln("   La sentenica   e    s: " + q     uery);
                R       esultSet rs = DbConnect    ion.    ru  nSqlStat        ement(q     ue     ry);

                       J SONObject j   s = n   ew JSONObject(     );

        JSONArr  ay nombres = new JSONArray();
         JSON   A        rra      y             idTest = ne       w JSO  NA      rray();


        tr                   y {

            if (rs.isBefor    eFi      r  st()     ) {
    
                        w    hile (rs.next      ()) {
       
                             nombr    es.p   ut(rs    .getString(     "   n    ame   Te  st"));
                                    i   dTest.    p    ut(rs.   getString("idTest") );

                                         }
                                        js.put("        no      mbres", nombres);
                     js.put("id   Test",            idTest);

                                result =      js.toString();  
             

            } else    {       
                          result = "Ce   r  o Resulta  dos";
                  }

            System.out.   println   ("    El Res  u ltado      fue = " + result);


          } catc     h (   JS ONE    xcepti     o    n  ex) {
                           Logg   er.get      Logger(  DA     OS tudent  Te       st.class.get N   ame())     .log(L  evel.SEV      ERE, nul         l, ex);
           }     catch (SQLException ex   ) {
                         Logger.g   etLogger(        DAOS  tudentTe   st.cla   ss.ge   tName()).log(Leve     l.SEVERE, null, ex);  
               }
        ret  urn result ;     
    }

             public Stri              ng G    et       MyS    co        re   (String idSt            udent)        {

         String r esul      t =      "No     se pud             o realizar la  Con     sul    ta";
                St     ring query = "SE   LEC  T Te       st.n    ameTest        , StudentTest.grade FROM Tes     t,     StudentTes    t W HERE Stude  ntTest.idStudent = "   + idSt    ude   nt +     " AN D  St      uden    tTest.idState =    \"" + 1 + "\" A   N                D                    St      u  dentTe           st.   idTest = Test.idTest";
           JSONArray Notas = new JSONArr  ay();
            JS  ONA  rray Test = new JSONArray()      ;      
                       JSON   O       bject js = new JSONObj     ect( )  ;
          int       leng th;
    

              ResultSet rs = DbCo nnection.runSqlStatement(quer   y);
          try {
                                  if (rs.isB   eforeFi  r   st()) {
                   while (  r      s.next    ()        ) {
                    Notas.  put(rs.getString                          ("grad  e") );

                       T     est.put(r     s.getSt   ring("nameT      es    t"));

                 }

                    js.put("testname", Test);
                                 js.put("gr   ades", No       t    as);
                res  ult = js.toS  tring();


            } else {
                           result     =         "  Cero resultad  os";
            }
               }     catc   h (JSONException ex) {
               Log              ger.ge    tLogger(DAO StudentTest.class.getName(  ))  .log(L   evel.SEVERE,      null, ex);
        } c  atch (SQLException    ex) {
                    Logger.get     Logger    (DAO  StudentTes t.class.getName()).log(   Level.SEVERE, null, ex);
        }
        return result;

    }

    public    String GetCom  ple t     eTest(String idStudent ) {

               St         r in      g result =  "   No se pudo realizar la Co  nsulta";
          Stri    ng query = "    SELECT     Test.    nameTe  st FROM    Test,     Stude   ntTes   t WHERE StudentTest      .idStudent = "   + idStud    ent + "   AN   D StudentTest.idState  = 1 AN    D StudentTe st.   idTest = Test.idTest";
        System.out.  println("La sentenc       ia fue: " +    query    );
            ResultS       e        t rs =        DbConnection.runSqlS       tatement(query);

          try {
             if (rs        .next())  {
                       result = rs.getString("name        T   es t");

                     while     (rs.next()) {
                    result = result     + "," + rs.getString("nameTest");
                   }

            } else {
                result = "Cero resultados";
                      }
        } cat    ch    (SQLException ex) {
             Logger.getLogger(DAOStudentTe   st.class.getName()).log(Level.SEVER    E, null , ex)    ;
        }

        return result;

    }

    public Strin  g De   leteAssignT     est(    String i  dStudent, String    idTest)        {

         St    ri  ng     result = "No se pudo   realizar la Con        sulta";
              String query = "DEL    ETE FROM StudentTest WHERE   Stude ntTest.i  dStudent = \"" + idStudent +     "\" AND    StudentTest.idTest = \"" + idTest + "\"";
              System.out.println("La Sentencia fue = "+ qu    ery)     ;
        int rs = DbCo  nnection.runSqlUpd   ate(query);
        if (rs == 0) {
            result = "No se   pudo realiza r la OperaciÃ³n";
        } else {
            result = "D   elete Exitoso";
        }
        return result;

    }

    public String UpdateStudentTest(String i  dStudent, String idTest, double grade) {

        String re   sult =      "No se pudo realizar la Oper    acion";
        String query = "UPDAT    E StudentTest SET idState= 1, grade=\"" + grade + "\" WHERE idStudent = " + idStudent + " AND idTest = \"" + idTest + "\"";
        int rs = DbConnection.runSqlUpdate(query);
        if (rs != 0) {
            result = "Registro Completo";
        }
        return result;
    }
}
