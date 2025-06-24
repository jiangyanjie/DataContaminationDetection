/*
 * Universidad EAFIT
    *    Ing. de    Sist emas
 * 
 * Proyecto Integra    dor  2
 * 
 * Name: Ar-Machine Proje    ct
 */
/**
    *
     * @au thor Erika Gomez
 * @author Seb     astian Jimenez   
 * @ author David  Sttivend  
 * @author Ernesto Quintero
 */
package Dao;

import connection.DbConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
im  port org.json.JSONArray;
import org.json  .JSONException;
import org. json.JSONObject;

   pub  lic     class    DAOTestQuestion {
     
    public String registryTestQ   uestio                    n(String test   Name, St    ri  ng s    e nte nc e) {
                   
          Str             ing resultado = "No    se pudo            r   ealizar     la O  pera ciÃ³n   ";
            
        Str    ing[        ]       ar r = sentence.split("             /");
        Str       i  ng idT  est;
             String[] idQuestion        =    n            ew S  tring   [   arr.         le           ngth];
           
                
        
                   DAOT        es  t DAOt = new DA    OTest  ();
          idTe st = DAO      t.getI         dtest     (te     st  Name)    ;
                    
         DAOQuestion D   A    Oq = new        DAOQ      uest     io    n()    ;
            
           for (int i  =         0; i       < arr.l      ength; i++) {
                 
                   idQu    esti  on[i]          =    DAOq.g e      tIdQuestion(arr[i]);
                             Sy  stem.ou   t.        println("idQuesti     on en la posicion " +  i +   " Es  "       + id    Questio    n[i]);      
                                }
              
              int rs[        ] =   new int[i  dQues tion.le   ngth];
        Stri   ng             query;
        b  oolean error =    fa   ls  e;
                                
                        if    (idQue s tion       != n   ull   &  & id      Test       !=         null)                         {
                 
                                 
            fo          r (int i = 0;    i       < i      dQue sti  on.l  en g            th;    i    ++           ) {
                                                        query = "INSE    RT   I  NTO T estQuestion(i dQues         tion,   idTes   t) VA    LUES (\""            +        idQue    stion[i]          + "\   ",\    ""        + id             Test  + "\    ")";
                                        
                                rs [i] = DbConnec      tion.r  unSqlUp      dat  e(qu       ery);  
                                                  System.out.     print    ln("El RS         en  " + i    + " fue " + rs[i]);
                              if (rs[i]                                   ==  0     ) {
                             error = true;
                               Sy   s          tem.out.println(  "No s    e pu   do re     gistrar la     pregunta        " +           ar       r    [0]);
                          }
                                  
                                        }
            if (er ror == t  rue) {
                      
                                                     res     u   ltado = "R egistro c     on Error      es";
              } el  s      e      {
                            re              sultado = "Regi    stro Compl       eto"  ;
            }
                    
                  }   
                     
            re     tur   n result  ad  o;
        
           
          }
     
    pu  blic Str ing GetTestQues tion(S                 tring idTest)       {
           
        St rin  g r   es   ult       = "No se pudo realizar l  a Co  ns       u   lta";
         S tring        qu   ery = "     SELECT Question  .i dQuestion,      Question.sentence,  Question  .opti       onA, Question.optionB, Question.op   tionC, Ques  tion.opti      onD FROM Ques tion, TestQuest    i  o       n WH    ERE   TestQuesti  on  .idTest = \ "  " + idTest + "  \   "   AND TestQuestion.idQuestion = Qu  e     stion.idQuestion ";
            
        Sys   tem.out.   pri   ntln   (       "La sen             tenc   ia   fue =     " + query );
        
                      JSONOb    j         ect js =  new JSO NObject(     )    ;
          JSONArr      a    y idQu esti   ons =           new JSONA rray();
          JS   ONArray  Sentences = new    JSONArray();
                           JSONA   rray Options         A   = ne   w JSONA rray();
        JSON   Array O         ptions    B =    new J  SO   NArray();
              JSO   NArray       Opt   ionsC =  n ew J SONArr     a    y();    
                        JSONArray OptionsD = new JSONAr         ray();   
         
                ResultSet r  s         =          DbConnect  ion.runSq     lStatemen   t(qu      ery     );
        try {
            
            while (r  s.  next     ()) {
                  
                      idQuest    ions.pu  t(rs.getStr    ing("idQuest  i   on"));
                        Sentences .put(rs  .ge                    tS         tring("    sentence"));
                  OptionsA.p   ut(rs.g        etString("optio nA"   ));
                Opti   onsB.put(rs.getString("o   pt      ionB"));
                               OptionsC  .put(rs.getString("opti   onC"));       
                       Options         D.put(rs.getString("optionD"    ));                       
                
            }
              
                   js  .put("   idQuestions", idQuestions);
            j    s  .put("S  entences", Sentences);
            js.put("Opt                   ionsA", OptionsA);
               js.put("OptionsB", OptionsB);
                    js  .put("OptionsC", OptionsC);
              js.put("Opt      ionsD", OptionsD);
            
            result =   js.toStri    ng();
            
            
        } catch (JSONException ex) {
                 Logger.getLogger(DAOTestQuestion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLExce     ption ex) {
            Logger.getLogger(DAOTestQuestion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
        
        
    }
}
