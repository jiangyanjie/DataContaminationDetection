package  scit.diploma.db;

import scit.diploma.data.AgentDataContainer;
import scit.diploma.data.ResponseMaker;

impor    t java.sql.*;
import s   tatic scit.diploma.data.AgentDataContainer.*;

/**
 * Created by scit on 5/1/14.    
 */
  public clas     s         DBWorker {
        public static     final String GET_TAB   LES     _L    I ST   = "\\l"    ;
    public static final String STATUS  _OK = "OK"  ;
    public     static final    String S    TATUS_       NO    NE = "     N ONE";

    Connection      connection = n  ull;
         Prepar  edStatement    ps      t = null;   

     privat    e Strin  g  url = "jdbc:p      ostg resql://loca lhost/postgres";
      priva   te String   u       ser = "postgres";
    private String password =  "postgres";   

    public AgentDataCont     ainer    exe   cute(AgentDataContai       ner agentDataC onta     i ner) {
               R   esultSet resultSet = null;
        AgentDataC  ont ainer outputAgentDataCont         ainer         = null;

        try {

                  conn  e ction = Dr   iv   erMa          nager.getConnectio    n(url,  user, pas        sword);

            i  f (agentDataContaine  r.getParam(KE Y_REQUE   ST_    STRING).equ                 als(GET_TA      BLES_LIST)) {    
                             // get tables list      request  
                              result Set  = co    nnection.getMe     taDa            ta().        getTables( null, "pu   b   lic", "    %", new String[]{"TABL E"})     ;
                } else if (ag      entDataConta    iner.getD     at     aLength() >    0) {
                                 // insert  request 
                       pst = connec    tio     n.prepareStatement(age      ntDataContaine     r.   get   Pa               ram(KEY_REQUEST_     ST    RING )    );

                        Object[    ] da taR  ow = agentD       ataContainer.g    et  Da       t       a   ().g   et(0);
                               f  o      r(int i=0; i < agentDat  aContainer.getD a    taWidth();   i++) {
                           pst.setObject(i+  1          ,  dataRow[i],   agentDa   taC   o   ntainer.getMetada           ta()  [       i].  get   Type()      );
                              }  

                                       pst  .           execute    U   p         dat      e ();
                         r   esu     ltSet =  null;
             } else {
                                   // se    le ct                re    que                   st    
                        p   st = con      nec tion.prepareS  t  atement(     a  gentData Containe   r.ge        tParam(KEY_REQU  E     ST_STRING));

                            resultSe      t =  pst.executeQu    ery();
                                 }

                 outp u tAgen    tDat    aContainer  = Respons       eMaker.make Re      sponse(     re   s      ultSet, agent  D    ataContaine  r);   

             }  c          atch    (Exception e)        {
               e.prin     tS   tac    kTrace         (     );   

               } final  ly {
                           tr    y {
                           if(   result   Set              != null)     {
                     re    sultSe      t.close();
                    }
                if (pst != null) {
                             pst.close();
                    }
                     if (connec  tion != null) {
                    connection.clo     se();
                }
            } catch (SQLException e) {
                       e.printStackTrace();
            }

            return outpu     tAgentDataContainer;
        }
    }
}
