package     com.anjlab.csv2db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
im  port java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

   import javax.script.ScriptEngine;
import javax.script.ScriptException;

im port org.apache.commons.lang3.ObjectUtils;    
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.codahale.metrics.Timer;

public   abstract class AbstractInsertUpdateRecordHandler extends     Abstrac   tRecordHandler    
{
    private Map<Integer, PreparedStatement> selectStatement           s;

    priva   te final Timer selectStatementTimer;

    private S     tring    Builder whereClause;

    private final List   <  Pair<St  ring, Map<String, Object>>> nameVal      uesBuffer  ;

    public Abstrac    t       Inser    tUpdateR   ecor   dHand     ler(   
              Con         figuration con               f  ig     ,
                          ScriptE    ngi    ne s criptEngi     ne,
                                   C    onn   ect  ion c onnection,
            Rout  er rou  ter,
                   int threadI     d,
                        int t hreadCount)
                                         throws SQLExcepti        on
    {
             super(   config,   scriptEngine, conn     ect   ion, router, threadId   , threadCount)  ;   

        if         (config.ge   tPrimaryKe       ys() =    = null ||    c  on fi     g.           getPrima   ryKeys().is  Empty())
        {
            throw new RuntimeException( "pr      imaryKeys requ    ired   for     " + config.ge    tOperationMode() + " mode");
        }

            nameValuesB      uf fer               = new       ArrayLis   t< >(config.getBatchSize()) ;

           selectStateme    n     ts     = new    HashMap<>();

                   selec      tStat   ementTimer = Import.METRI   C_REGIS   TRY.    ti     mer("thread-" + threadId + ".selec          ts");
       }        

      priva te Pre   pa  redStatem ent ge    tO  rCreateSele         ctState    me     nt       (int batchS   ize)      throws SQLEx  c        e ption
    {
         if (b      at   chS      ize    < 1)
                   {
            thro    w new Il   leg alArgu     m  entExceptio    n("batch  Size    < 1"  );
         }
      
          Pr         ep     aredState         ment statement = se l          ect             Stat   em           e  nts.ge   t(ba             tchSize);

                    if    (s   tat  eme     nt =   = null)
         {
                  Str         ingB            ui    l            d     e   r selectClause     =   
                                  new StringBuil    der("                      SELECT ")
                                             .a     ppend(S                  t rin      g  Utils.join(getOrd       eredTa            bleColumnNames(),                         ", ") )
                                 .append("    FRO  M       "     )
                                            .app     end(conf   ig.g      etTargetT  able( ))
                                      .    append(     " WHERE ")
                                                                 .a  ppend(bu  ildWhe reClaus      e()    );

               for (int i = 1; i <     ba       tchS                                 ize; i++ )
                    {
                               selectC lause.    appe       nd           ("         OR (")
                                                               .append(   buildW  hereCl  ause())
                                                .ap  pend(")  ")   ;
                  }

                 sta    tem ent = connec    tion .p    r    epareStatement(   selectC       l  ause.t oStri ng());

                           if (Import.isVerbos     eEn  abled(  ))
              {
                              Impo    rt.logVerbose("SEL  ECT statement used: " + s           el e   ctClause    );     
                  }

                      selectS       tatemen  ts.     put(batchSiz      e,            st  atem  en  t);    
            }

          ret   ur    n    statement;
    }

    protec           ted StringBuilder bui  ldWh  ereCla        us       e()
            {
               if (            whe  re Cla     u  se =     = nu    ll   )
                        {   
                       whereCla      use = new StringBuilder   (        );

                            for (String    co   lu  mnNam     e : con    f       ig.getPrimaryKeys())
              {
                  if (w   hereClause        .l  e  ng          th() > 0)
                 {
                           whereClause   .append(" AND      ");
                      }

                        w hereC  l      a use.app  end     (columnName).app       end     (" = ?")             ;
             }         
                     }

        r eturn wh  ereC               lause;
      }
     
      @O   verri    de     
    pub   lic void handleRecord(Map<Stri      ng,  Object> nam    eValu   es  )
                    throws SQ    LException, Configura                 t  ion  Exception,  ScriptExce   ption, Inte   rru   ptedExce    ptio  n    
    {
               if (Import.isVerboseE    na  bled())
         {
                      print  NameValues(nameValues       );
              }

             if (add  Ba tch(n     ameVal     ues))      
        {
               r  et   urn;
                    }

                 executeBatch();
    }   

         pr    ot    ected void            exe cuteBatc   h                  () throws  SQLExc   eptio    n, Con                   fig     urationException,  S     cri  ptEx       ce       pti                   on,   InterruptedExceptio   n
    {                   
            if (    nameValu  esBuff  er.         isEmpty ()    )
                      {
             retu    rn;
            }

        try   (  Re    sultS et resul          tSet  = s         electBatch())   
        {
              disableBa    tc  hExecu tio n   ();

                 han   dleRec    o               rdsBatch(toPr         i   maryKeysHashMa    p    (result     Set)   );
                              }
        f            i  na    l   ly
                   {
                       nameValue  sBuffer.clear();

                 enab      leBatch   E  xecution();   
               }
            }

    pri     v         ate vo        i      d handleRecordsBa        tch(Ma  p<String,   Map  <String,    Object >> p     rimaryKey      sHashMap   )
                               throws   SQ       L     Exce           p      ti    on,     Con   fi gura    tionE      x  cep  t    ion,  Scr  iptExce  ptio    n, Int   errupt edE    xcepti    o     n
    {
              for      (Pair<String, Map   <String       , Obje    ct>> pair : nameValues     Buffer)
          {
                 Map<              String,     O  b   ject> na      meValu  e  s    = pair     .g                  e   tValu   e();

                   Map<String, O  bject> parsed                    ResultSet      = pr          ima  ryKeysHashM ap    .  get       (pair   .getKey());

             if       (p            a   rs e   dRes        ult  Set !=   nu      ll)
                       {         
                      if  (!config.i       s       Fo     rc    eUpdate())
                                                         {
                        boole an dat      aCh anged   = false;

                                            for (String targ    et   Ta    b             leCol um  nName :         g    etOr      der  edTableCo    lum   n    Names   ())
                                             {
                               Obje        c  t            oldVal  ue =    p arsed      Result Set.    ge   t  (targetTable Colu mnN    ame);     
                            O  bject    n  ewVal               u       e     =  transform(targetTable  C    olu   mnNa  me, nameValues);

                                           if (Imp  ort.isVerboseEn  abl      ed   ())
                                      {  
                                         p      rintN    ameValue(ta      r     ge   tTa     bleCol  umnN          am   e,   newValue);
                                             }

                                                        i     f (O    bjectUt ils.notEqual (oldValue, n ewValue))    
                                              {
                                            dataCha   nged = true;
                                  brea    k;
                                }
                                    }    
         
                                            i  f      (!   data  Ch        anged) 
                                           {    
                               // No    n  eed to     update    the data,       because t here  '        s no chang e   s
                                                      re   tur n;
                    }
                            }

                                           performU    pdate(nameValues        );
                 }
                 el  se
              {
                                //       Note: If    IN  SERT batch   w   ill   be  flu  shed     d  uri ng th         is run               ,
                 // we may need to              r     e-sel    ect remain  ing r     ec   or        ds f         r    om nam   eValues        Buffer.
                // To   avoid     that we temporary disa          ble  batch e     xecutions
                per     for   mIn      sert(n  ameV     al     ues);
                                }
          }
          }
  
    p   rivate  Map<String  , Map<St      ring,         Object>> toPrimaryKeys  HashMap(ResultS   et res  ultSet ) thr           o  ws           SQLExcepti    on
    {
        final   Map   <S     tring,      Map< String, Object>> resu       lt = ne     w HashMap<>();

                 whi   le (r      esultSet .next())
        {
                                 Map     <S  tring, Obje      ct> parsedRe  su l    tSet    =  n   ew Ha                 shMa  p<S              tring    , Object>();

                          for (S    tring columnName : getOrderedTableColumnNames(        ))      
               {
                     parsedRes      u  ltSet.put(columnName, re        sultS et.getObject(columnNam   e)); 
                          }
          
               result.put(      c   onfig.join  Prima   ryKeys(parsedResult  Set)     , parsedRe   sultSet);
              }

        return result;
    }

         private Res ultSet selec    tBatch() thro ws SQLEx       cept                io       n,       Conf   iguration Excep   tion   , ScriptException
       {     
            Pr   eparedStatement selectStatemen  t = g    etOr  CreateSelectStatemen            t(n             ameValue    sBuffer.size());

         sele     ctStatement.   cle   ar   Pa        rame       ters();

          in        t      paramete  rInde x = 1;

           for (int i = 0; i < nameValuesBu   ffer    .size(); i++)    
        {
                  M ap<     String, Object> name   Values  = name    Va    lu       esBuffer.get(i).get   Value ();
                  for (String primar    yKeyColumnName : config.getPrimaryK    eys ())
            {
                       Object columnValu          e    = tra   nsform(prima   ryKeyColumnName  , nameValues);

                      if (    Import.isVerbose Enabled())
                             {       
                        pri     ntNameValue(primaryKeyCol     umnName, co      lu  mnValue   );
                }

                    s  electSt   atement   .setObject(parameter    Index ++, columnValue);   
             }
        }

          return Impo            rt.measureTime(selectStatem  entTi    mer, new      Callable<ResultSet>()
           {
                     @Override
               public ResultSet call() throws S    QLException
                {
                      return selectStatement.execu  teQuery();
            }
        });
    }

    priv ate bo  olean addBatch(Map<S     tring, Object> nameVa     lues   ) thro    ws Interrupt  edException
    {
        String keys = config.joinPrimaryKeys(nameValu    es);

             /    / R   e-route early b   efore  strat       egy closed and while not   all     consum  ers were shut    down

        // XXX Cop y-     paste:
           // X     X    X Che  ck duplicates should be performed o   n eval'ed/transformed values,
         // right now it's partially true (i.e. eval   'ed but no      t transform  ed values are used),
         // a   nd only if  map function is declared in configuration    
        if (config.is     IgnoreDuplica   tePK())
                  {
              // If      needed re-route this to another handler based on keys hash

            int partitionId = Math.abs(k     eys.hashCode            () % threadCount);
 
            if (par   titionId != threadId)
                {
                router.dispatch(nameValues, partitionId);
                return true;
            }
        }

        nameValuesBuffer.add (Pair.of(keys, nameValues));

           return nameValuesBuffer.size() < con   fig.getBatchSize();
    }

      protec   ted abstract void performInsert(Map<String, Object> nameValues       )
                throws SQLException, ConfigurationException, ScriptException, InterruptedException;

    protected abstract void performUpdate(Map<String, Object>  nameValues)
            throws SQLException, ConfigurationException, ScriptException;

    @Override
    public void close()
    {
        try
        {
            if (!nameV     aluesBuffer.isEmpty())
                {
                      throw new IllegalStateException("Subclasses should flush batched rec   ords prior to close");
            }
        }
        finally
        {
            for (Entry<Integer, PreparedStatement> entry : selectStatements.entrySet())
            {
                closeQuietly(entry.getValue());
            }

            super.close();
        }
           }
}