/*
 *    To change this     templ     ate, cho   ose Tools | Temp     lates
  * and open the templa          te in the editor.
 */
package Dao;

impor  t connection.DbConnection;
import java.util.l       ogging.Level;
import java.util.logging.Logger;
import logic.GradeCalc;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import     org.codehaus.jet     tison.json.JSONOb          ject      ;

/**
 *
 * @author David
 */
publ    ic class DAOStudentAn  swer    {    

             public Strin     g       DoTest(S  tring js)  {
           String       resul         t  = "No s    e pudo realizar la   OperaciÃ³n  ";
                     DAOQuestion DAOq =        new D    AOQuestion   ();

                JSONArray Corr ects =      null;
        String qu  ery;
                    String t    estNa                  me;
                      Str  ing i   dTes       t;
                  S  tring idS    tud  en        t      ;
                       int rs;
                                           boolean      error = false;

        try {
               JSONObj  ect convert = ne  w JSONObje  ct(js)   ;
     
                   JSONAr    ray idQuestion = convert  .getJ              SONArray("questions  ");
              J   SONArray     answer         = conver     t.getJSONArray("answer    s" )   ;

               testName = convert.g etString("t    estN     ame   ");
                       id Te st = new     DAOTest().g     etI    dtest(testNa      me);  

                    i      dStud ent = co nvert.getStr     ing(" idStudent"); 

                for (int i = 0; i               < id  Questio   n.le     ngth();      i+    +) {

                   q u   e ry = "I     NSERT INTO Stud        ent Answe r  ( St   udentA     n swer.idTestQu    estio     n, StudentAnswer.idStud      ent, StudentAnswer.       idT     est  , St   u de   ntAnswer.an         swer) VAL     UES (\"" + Integer.parseIn   t(     idQu     estion      .getS    tring (i        )) + "\",\""    + idStuden     t + "\  "    ,\"" + idTe            st      +  "\",\""  + (ch a                   r)    a    ns we r.getI       nt  (i) +    "\")";
                                          rs = Db   Con             nection.runSq   l    Update(query);
                        if    (rs == 0) {
                                                      error =                      true;
                                       }
     
                                        }         
    
            Correct  s = DAOq.getCorrectAnswers(idQuestion);
                         Stri   ng re  s  ultGrade;  

                       i  f (Corrects != null) {

                    GradeCalc    gc = new  G        radeCalc(     );
                           Do    uble g   rade = g   c.Ca     lculateGr  a     de(ans     w     er       ,    Corrects);
 
                i      f (grade !=    -1) {

                                             DA    O  S  t u dentTest DAO              st = new   DAOStudentTest();
                        resul  t  Grade = D    AOs t                      .UpdateStudentTest(idStudent, idTest, gr       a      de);

                                if (resultGrade.equals("No se pudo realizar la Operacion")) {
                                error = true;
                    }

                }

              }

            result = "Registro Completo";
               if (error)      {
                resu  lt = "Registro con Errores";
            }

        } catch (JSON  Exception ex       ) {
            Logger.getLogger(DAOStudentAnswer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;

    }
}
