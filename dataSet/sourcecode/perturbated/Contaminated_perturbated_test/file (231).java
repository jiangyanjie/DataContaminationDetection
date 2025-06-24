/*
    * Universidad EAFIT   
 * Ing. de   Sistemas
 *    
   *    Pro   yecto         Integrador 2
     * 
    * Name: Ar-Machin  e     Project
 */
/**
 *
        * @author Erika Gomez
 * @author Sebastian Jimenez    
 * @a  uthor David      Stti v          end
 * @author Ernesto Quintero
 */
package Dao;

import connection.DbConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.ut    il.logging.Level;
import java.util.logging.Logger;
import org.codeha   us.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettiso n.json. JSONObject;

pub   lic class DAOQuestion {

    pu blic      String registryQues            tio     n(  St       ring sentence, String opcionA, S       tring opcionB, Stri   ng opcionC, Strin    g opcionD, String corre   ct     ,       Stri    ng idProfess      or, Str   ing    t     agName) {
         
        String resu ltad    o = "N          o s       e   pu d      o realizar la Opera ciÃ³      n";
        S  tring idTag;

        DAOTag DAOt = n    e  w DA OTag      ();
        idTag = DA  Ot.getIdTa   g(tagName); 

           if (idTag != null) {

            String        query =  "INSE   RT I   NTO Ques    tion(sentenc e , idProfe   ssor        , idTag , opti     onA ,  optionB , optionC , optionD , cor    rect) VALUES ( \"" + sentence +     "   \",\"" + i     d Professor +          "\",\""   + id    Tag + "\",         \"" + opci    onA +        "\",\""  +  opcio      nB + "\",\     ""      + opci  onC      + "\",\"" + op    cionD + "\",\"" +     corr  ect + "\                     ")";
            System.  ou    t.println("La   Sentenci     a e  s :     " + quer y    );


             i        nt         rs = DbConnection.runSq    lUpdat   e    (   quer       y);
                   Sys         tem      .out.p  rintln   ("          RE   SUL S ET  = " + rs);

                    if (r s   != 0) {

                            resul tado = "Regi  stro Comp   leto";
                   }
        }
               r      eturn resultado;
    }

    pub       lic Str       i   n  g getIdQu        esti    on(String nombre) {

                  String re        sult = "No s     e pudo realiz      ar         la     Co     nsulta"   ;
                     Str   ing    query = "SELECT id    Question FRO  M Qu       esti on WH  ERE s   e          ntence = \""     + n   ombre + "\"";
     
        ResultSet rs = DbCon    nection.runSql      S   tatement(  query );             
              try {
                  if (rs.next()) {
 
                  result = rs.getString("i   dQuestion")  ;
                                          } els      e {
                 r esult      = "Cero                    resultados";
                            }
           } cat  c h     (SQLE        xcep        tion ex     ) {
               Logger.getL      ogger(DAOQuestion.      c     las        s.g     et  Name()).log(Level    .SEV     ERE,    null, e    x);
             }

                return re     sult;
          }
    
     public       Str   i  ng ge    tQuestion(   String idPr            ofessor) {

           String     r   esu   lt           =        "N        o se         pudo re     aliz  ar la Consulta";
        St ring query = "SELECT       sentence           FROM   Question   WHERE idProfessor = \      "    " +     idProfessor           + "\"";
         JSONArray          ja = ne      w JSONArray();
        JSONObje        c t js =     new JS     ON   O     b jec    t();

              ResultSet   r        s = DbC onnection.r  un  SqlStatement(  quer  y   );
   
                                      try   {


            while (rs.n            ext(  )  ) {
        
                             ja          .put(r         s.g      etStri ng("s      ent  enc          e"))     ;

               }
                             if (ja.   l   ength(  ) >            0) {

                              js.pu t("se                    nt  ence", ja);    

                    r     esult = j         s     .toString();
                }   els  e {
                                resu  lt = "Cero R  es  ultados   ";
                   }  

              } c    atch   (JSONException ex) {
                              Log g     er.getLog    ger(DAOQues tion.class.getName()).log(Level. SE   VERE, n    ull, ex);
             } catch     (SQLException ex) {
                  Logge   r.get   Log ger(  DA  OTag.class.getNa      me()).   log(Level.SEVE   RE, null, ex)      ;
             }


        retur   n result;

     }

    public     JSONArra     y g etCorrectAnswers(JSONArray idQuestio   n) {

           JSONArray result = new JSONArray();
          String      query;
        ResultSet rs;
            t     ry   {
             for (int  i    =      0; i < idQue stio    n.leng   th(); i++) {

                         que ry   =      "SELECT       correct FROM Question  WHERE       idQuestion =         \" " +  idQuestion.get(i) +       "  \"  ";
                rs = DbConnection.run  SqlStatement(query);
                  if (rs.next()) {
                    resul    t.put(rs.getS  tring   ("correct")     );
                }

             }
            System.out.print ln("     EL JSONArray idQuestion = " + idQuestion.toString());
                     System.out.println("EL JSONArray      Result = " + result. toString());

        } catch (SQ    LException ex) {
                 Logger.getLogger(DAOQuestion.class.ge tName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(DAOQuestion.class.getName()).log(Level.SEVERE, null, ex);
            }

        return result;

    }
}