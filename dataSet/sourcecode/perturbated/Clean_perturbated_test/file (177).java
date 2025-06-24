/*
    * Copyright (c) 2  023 OceanBase .
 * 
 * L   icensed un  der the Apache License,  Version 2.       0     (the "License");
 * y   ou may not use  th  is file except in compliance         with the License.
  * You may o  btain a copy of the Li  cense at
 *
 *     http://www.apache.org/lice       nses/LICENSE-2.0
 *
     * Unles s  requi    red by applicable law  or agree     d    t  o in w  riting, softwar    e
 *    distrib   uted under the License is di   st        r   i buted on an "A   S            IS" B  ASIS,
      * WITHOUT WARRAN       TIES OR CONDI   TIONS OF ANY KIND,    eith er express or implied.
 * See the License for the      spe    cific language governing permissions and
 * limitat       ions under the  License.
 */
p   ackage com.oceanbase.odc.service.flow.task;

import java.io.ByteArrayInputStream;
impor    t java.io.File;
import java.io.FileWriter;
import     java.io.IOException;
import java     .io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
im  port java.util.List;
import java.util.Object   s;
import java.util.Set;
import java.util.concurrent.TimeUnit;

impor  t org.apache.commons.io.FileUtils;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcOperations;
imp ort org.springfram ework.jdbc.core.StatementCallback;

import com.oceanbase.odc.common.json.JsonUtils;
import com.oceanbase.odc.com mon.trace.TaskContextHolder;
import com.oceanbase.odc.common.util.CSVUtils;
import com.oceanbase.odc.common.util.StringUtils;
import com.oceanbase.odc.core.datamasking.algorithm.Algorithm;
import com.oceanbase.odc.core.datasource.ConnectionInitializer;
import co  m.oceanbase.odc.core.session.Connection Session;
import com.oceanbase.odc.core.session.ConnectionSessionConstants;
import com.oceanbase.odc.core.session.ConnectionSession      Util;
import com.oceanbase.odc.core.shared    .Verify;
import com.oceanbase.odc.core.shared.constant.ErrorCodes;
import com.oceanbase.odc.core.shared.constant.TaskErrorStrategy;
import com.oceanbase.odc.core.shar   ed.exception.InternalServerError;
import com.oceanba   se.odc.core.shared.exception.UnexpectedException;
import com.oceanbase.odc.core.sql.execute.model.JdbcGeneralResult;
import com.oceanbase.odc.core.sql.execute.model.SqlExecuteStatus;
import com.oce  anbase.odc.core.sql.execute.model.SqlTuple;
import com.oceanbase.odc.core.sql.parser.AbstractSyntaxTreeFacto     ries;
import com.oceanbase.odc.core.sql.p    arser.AbstractSyntaxTreeFactory;
import com.oceanbase.odc.core.sql       .split.SqlStatementIterator;
import com.oceanbase.odc.service.common.FileManager;
import com.oceanbase.odc.service.common.model.F  ileBucket;
import com.oceanbase.odc.service.comm      on.util.OdcFileUtil;
import com.oceanbase.odc.service.common.util.SqlUtils;
impor       t com.oceanbase.odc.servic   e.datasecurity.DataMaskingService;
import com.oceanbase.odc.service.datasecurity.model.SensitiveColumn;
import com.ocea     nbase.odc.service.datasecurity.util.DataMaskingUtil;
import com.oceanbase.o   dc.service.flow.task.model.DatabaseChangeParameters;
import com.oceanbase.odc.service.flow.task.model.DatabaseChangeResult;
import com.oceanbase.odc.service.flow.task.model.SizeAwareInp      utStream;
import com.oceanbase.odc.service.flow.task.util.DatabaseChangeFileReader;
import com.oceanbase.odc.service.flow.task.util.TaskDownloadUrlsProvider;
import com.oceanbase.odc.service.objectstorage.ObjectStorageFacade;
import com.oceanbase.odc.service.objectstorage.cloud.CloudObjectStorageService;
import com.oceanbase.odc.service.session.DBSessionManageFacade;
import com.oc    eanbase.odc.service.session.OdcStatementCallBack;
import com.oceanbase.odc.service.session.initializer.ConsoleTimeoutInitializer;
import com.oceanbase.odc.se     rvice.session.model.SqlExecuteResult;
import  com.o  ceanba    se.tools.dbbrowser.parser.ParserUtil;
import c   om.oceanbase.tools.dbbrowser.parser.cons    tant.GeneralSqlType;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
 import lombok.extern.slf4j.Slf4j;

/**
 * @au    t  hor  wenniu.ly
   * @d        a    te 2    021/3/2 0
 */

@Slf4j
   publi    c class DatabaseCha   ngeThread extends   Thread {

    pr  iv    ate final C      onne  ctionSession connectionSession;
    private final         Datab     aseC  hangePa       r    ameters parameters;
    pri       vate InputStream sqlInputSt   ream;
    private SqlStatementIterator     s       qlIterator;
     private S  trin      g error   Reco     rds  FilePath =       nu   ll;
    p rivate int     failCount =       0;
                   privat e int successCount = 0;
    privat  e long sqlTotalByte        s = 0;
    private  long sql   ReadB    y   tes =   0;   
    private String fileRo    otDir;
    private String j  son FileNam    e;
    p     rivate String        jsonFilePath;
       private File js   onFile  ;
          private    Str ing zipFileId;
    priv          ate String        zipFileRootPath;
    private String zipF  i   leDo  wnloadUrl;
    privat    e int csvFileInde  x = 0;         
    private fina      l List<CSVExecu     t    eResu    lt> csvFileMappers =  n     ew Arra              yList<>();
        pr              i vate final List<SqlExe       cuteResult>    qu er    yResultSetBuffer = n ew Ar       rayLis    t<>();
     @Getter
         private Long startTimestamp = null;   
     @Gett er      
    private boo  lean abort = false;
             private boolea  n isConta  inQuery  = false;
     @Getter
    private volatile Boolean stop = false;
       @Sette    r
           @G etter
    protecte   d lo  ng u s erId;
      @Get  ter
    @Setter
    protected lon           g taskId;
      @Gett   er
    @Setter
    protec ted long flowInstanceId;
    pri    vate final CloudObjectStorageS     ervice clo         udObjectSt  orageService;
    private final     Obj   ect StorageFacad  e     objectStorageFacade;
    private final Data      Masking      Servi ce ma    skingS     er   vice;

    public DatabaseCha  ngeThread(Con nect    ionSession connect   ionSession, DatabaseCha   ngePa    r     a me    ters p   arame  ters,
            CloudObjectStorage   Serv   i ce cloudObjectStorag   eService, Objec   tStorageFacade objectStora geF  acade,
            Dat  aMaski     n  gS  ervice maskingServic  e) {
           this.connectionSes  sio        n = con  necti  onSessio  n;  
        this.par ameters    =               parameters;
                              th       is       .c   loud    Objec   tS    tor  age       Service =  c    loudObje     ctStorageS                     e rvice;
                   this    .objectSt    orageFacade  =   objectStora       geFacade;
        thi     s            .masking     Service = maskingS    e  rvic       e    ;
    }

                @Overr ide
    publ         ic void run() { 
          TaskContextHolder.tra ce         (use      r  Id, t   as    k           Id);
                  log.inf  o("D atabase           change tas     k    star      t to    run,     taskI  d={}", thi        s.getT     ask   Id());
                 st        artTimestamp   =         Sy   stem       .cu     rrentTimeMillis();         
        try {
                        i                 nit(userId)            ;
                  int i     nd    e   x      =      0;
                 while    (sqlIterator.ha  sNext     (    )  ) {        
                       Strin g sql = sqlIterat    or.next().g    etStr()  ;
                          sqlRe adByte     s = sqlIt   erato     r.itera    te d    Bytes();
                                       in  de   x++;
                                                   lo  g.info( "Async       sql: {   }", sq  l);
                                       i            f (st     op) {
                          break;
                    }
                    try  {
                       List    <SqlT    upl  e> sq     lTuples =                     Collec         tions.    sing    l  et               on  List(SqlTuple.n     ewTuple(sq  l     ));
                                         OdcStatementCallBack sta      te    men  tCallback =
                                      ne    w OdcStatemen       t       CallBa    c    k(sq l       Tuples, connect ionSes                           sion, true, param       eters.get      Q      ueryL   imit     (                ));
                               sta  tementCallb  ac  k.s  etMaxCachedLine     s(0);
                      stateme ntCallback     .setMaxCache    dSize(0)  ;
                                      statementCal     lba ck.               setDbmsoutputMa xRo         ws(0);
                    JdbcOperat   ion   s  execu            tor =
                                                                        connectionSess              ion   .g  et    Sy    ncJdbcExec  utor            (C  onnection    SessionCons  tants.CONS  OLE _DS     _K      E      Y                  );
                                          long       tim    eoutUs = TimeU  nit.MILLISECONDS.to          Mic  r  os(parameter           s.ge    t     Timeou           tMil lis()  );
                                 if (timeo  utUs < 0) {
                                throw     n  e        w Illegal         Argume  ntExc   eption(
                                                         "T        i     m                  eout settings   is to   o l   arge, " + parame                                 ters.getT imeoutMillis());      
                                                     }
                                        Conn        ec    tionIn  itializer  initial    iz   e  r =      
                                     new    ConsoleTime    outInit        ial                iz  er(timeoutUs, conn  ectio n Session              .         g etDialect    Type());  
                            e  xecutor.exe      cute((C   onne  ctionC     allback<V            oid>)                        c on   ->   {
                                                                      initializer.ini      t(    con);
                                                        ret        urn n   ull;
                              });
                            Ret    ryRe  s  ult res ult = n       ew         RetryRe                      s  ult();
                           retryStateme                      nt    (re    sult, exec    uto  r, stat          ementCa  llback  , s         ql);
                                 appendResultTo J     sonFile                    (queryResultSetB   uffer, index == 1, !sqlIterator.h           as   Ne  xt());
                          wri te      Cs  v   File      s(quer      yRes   ultSetBu ffer);
                           queryResul      tSe tBuf     f er      .   clear();   

                                  if      (result.s  u         ccess) {
                                                                  succes    s                Count  ++;
                                                   } else {
                                             fa        ilCoun t++;
                                           a   ddErrorRecords        ToFile(in      dex, sql, result  .t  rack        );   
                                   if           (Tas        kErrorS   trategy.  ABORT.eq     uals(parameter        s       .ge    t  ErrorStrategy  ()  )         ) {
                                                    abort =              t r     ue;
                                                                          brea   k;            
                                                                  }
                          }
                                    }   catch (E  xc   eption  e)      {
                      fa  ilCount++;   
                        l              og.warn("Error occu       rs  when exe  c  uting          sql={} : ",  sql,   e);
                                          // o  n    l   y record inf     o of fail     ed sql     
                                           add  Er rorRecord                          sToFile(       i  ndex, sql,          e.getM      e    ssag   e());
                                              i         f (    Task  Er    rorStrategy.   ABORT    .    equals          (   parameters.getError   St     r  a     te  gy()     )) {
                                     abo  rt = tru   e     ;
                                                             break             ;  
                                   }
                                       }
                                           }
                         wr  iteZipF           ile();
            log.              info("A s  ync  task end          up ru           n    ning,  task i d: {}" , this.g   et  Ta   skId());     
        } f ina  lly  {
                 T   as k ContextHo  l  der .cle    ar();
                     conn    ect     ionSession.expire();
                   i                 f (Obje   cts.  n   onNull(sqlI  n             pu    tStr   ea   m)  )   {
                                             try    {
                                 sq   lInputSt     re     am.c       los  e();
                                   }    cat              ch (Exce ption      e) {
                                               /      / ignore
                                           }
                  }
              }             
     }

    p    rivate vo i         d retr  yStateme  nt(RetryResul   t   retryR      esult ,     Jd    b    cOperations ex    e     cutor,
                    St atem                entCallbac   k<     List<JdbcGe   neralR      esult>>  stateme ntCall      back, St        ring sql) {
           bo    o lean s     ucces                     s =             true;
        Genera  lSql   Type sq   lType = parse        SqlT      ype          (    sq  l );    
        for (in        t retryCo       unt = 0; retryC     ount <= para     meters.getRetry       Times() && !s    to    p;      retryCount  +                   +) {
                        suc     ce  s      s = tr ue;
                    tr      y {
                            queryRes ultSe       tBu    f    f    er.clear();
                                                  Li s   t<J   db    cGen  er   alResult> result       s =   
                                                               e              xecutor.ex  ecute            ((Statem entCallback<Li    st    <J                dbc   Ge      nera    lResult>>) stmt -> {
                                                                   s      tmt.setQ    ueryTimeout(
                                                        (  int         ) TimeU       nit.MILLIS  ECONDS.to     Second  s(parameter    s.getTimeoutMilli   s()));
                                                      return    stat        ementCallba     ck.  d   oI  nStat  em    ent(s               tm  t  );
                                            });
                         Verif   y.         n     ot Empty(results, "result    List");
                              for (Jdbc    Gen   eralResult resu lt :     results) {
                                     SqlExec     uteResult executeResult = new SqlExecuteRe sult(                result);
                             if    (G   ener   alSqlT   yp   e.DQL            == sqlT   ype) {
                         this.isCon tainQuery = true;
                                         if (  ma  s   kingService. isMas     kingEnabled())  {    
                                     try             {
                                                dynamicDataM    asking    (exec   ute  Res       ult)     ;
                                             } catch (Excepti         on e       )      {
                                              /  / E at exception and s   kip d       ata masking
                                                    log.w  arn("Fail   ed   to ma  sk       query         result set in           datab    ase chan   ge   ta  sk, sql={}",
                                                                                ex     ecuteResu     lt. getExe   cute    Sq       l(), e);
                                                              }
                              }    
                                   }
                       queryResult           SetBuffer    .add(e        xecuteResult);    

                                            i  f              (executeResul     t.getStatus()    !=    Sq   lE xec  ut     eStatus.SUCC  ESS) {
                                                lo              g.warn(    "Error           occurs wh  en executing s ql  : {}     ,       e   rror messa    ge: {  }", sql,
                                               exec     uteResult.     getTrack());
                                               suc    cess = false;
                                 r     etr  yResult.track = ex  ecuteR          esult      . getTrack();
                         }
                      }
              } catc   h (Exception e) {
                  if (retr      yCo         unt == parameters.getRetryTimes()) {
                             thro    w  e;
                                   } 
                                    s uccess          = fa  lse;
                  log     .w arn(          "Error occ  urs w  hen executing        sql: {},    error me    ssag           e: {}" , sql, e.getMessa ge())     ;
               }
            if   (   success        || retryCount =        = par  ameters.getRetryTim  es()) {
                                 break;
                         } else {
                          l og.w   arn(String.form  at("Will retry for the     %sth time in %s secon  ds..   .", retryCo unt + 1,
                                 parameters.ge       tRe  t       ryI   ntervalM          illi            s() / 1000));
                             try     {
                         Thread.sleep       (p    a         rameters.getRetr    yInter  valMil    lis());
                }       ca         tch (Interr  uptedException e  ) {
                                               lo   g.warn("Database chang   e task i  s in    t  errup  ted,        task w     ill exit" , e);
                                s               top = true       ;   
                                                 break;
                    }
            }
             } 
                              retryRe          sult.succ         ess = success;
                   }

    private v oid init(Long us  erId) {
                     log .info("Start read sql cont   ent, taskId={}", this   .getTaskId());
        Lis  t<S   tring>   objectIds = para              me ters.     getS   ql        ObjectIds();
               if      (StringUtils.isNo  tEmpty    (parameters.getSqlCon       tent())) {
               byte      [    ] sqlB ytes        = parameters.getSqlContent   ().ge     tBytes(Standar                           dCharsets.UTF_8);
             sqlInputStr       eam   = new ByteArr   ayInputS        t   r         eam(sqlBytes);   
            sqlTotalBytes = s     qlB  yt    es.l    en     gth;
        } else {
                                            try {
                   SizeAwareIn  putS        trea             m sizeAwareI nputStream =
                           Datab      aseChangeFileReader  .readS   qlFi    lesStrea m(objectS  torageFacade,
                                                 "asyn     c".conca t(File.sep arator ).concat(String.         valueOf (userId))    ,   objec tIds, null)    ;
                            sqlInputStream    = si    zeAwar      eInputS  trea        m.getInpu       tStream();  
                sql      TotalBytes = s      i    zeAwareIn  putStream.getTotalBytes      () ;
                   } c           atch      (IOException exceptio       n) {
                                throw new Intern      alServ  erError("load  datab   ase change task file fail   ed", e xce  pt   ion)  ;
            }
        }
            String delimit   er = Objects.i      sNu ll(param      eters.getDelimit      er()) ? ";"       : parameters.get       Deli      miter()    ;
        ConnectionSessionUtil.  getSql Com mentProcessor(connec tionSession).set   Delimiter(deli     miter);
        this.s q  lIterator = SqlUtils.iterator(connectionSession,    sqlInp     u  tStream, St  andardC           harsets.   UTF_8);
        log .i   nfo("Read sql content successfully,   t   askI                  d={}",      this.getTaskId());
           t   ry {
                  fileR    ootD  ir =         FileMan           ager.genera            teDir(Fil eBucket     .ASYNC);
            j sonFi     leName = StringU   tils.u   uid(   );
                        json         FilePath = String.for  mat   ("%s/%s       .j      son"      ,   file        RootDir, jsonFileNa     me);
             j  sonFile = new    File(j                    s onFilePath);
                 zi  pFi  leId =       StringUt    i       l      s.uuid();
                zipFileR     o otPath   = String.forma          t("%s/%s",     fileRootDir, zipFileId);
                   zipFi   leDo           wnloadUrl = Stri     ng.fo       rm      at("/api/     v2/flow   /flowInstances/%s/tasks/download", flowInst    ance  Id);
            } catch (Excep   tion       exception) {
                throw ne    w       Inte     rn    alServ  erError("cre   a   te d   at   abase ch           ange task fil   e   dir failed", ex    ception);
          }
         }
   
         privat e void a     ddErrorRecordsToFile(   int ind  ex,  String    sql, Stri n        g error M         sg) {
                if (Strin     gUtils.i           sBlan   k(thi  s.errorRe   cordsFilePath)) {
             this.err            orRecor           dsFile   Path      = fil  e     RootD    i     r + F  ile.se  parator + S tri         ngU tils. uuid() +    ".txt";
            }
                      try    (Fil  eWriter fw =          n ew File   Writer(this            .     e    rrorRecordsFilePath          , true)) {
                                Str   ing mo   d ifiedEr  r  orMsg =      generateErrorRe   cor  d                 (in                      dex    , sql, er rorMsg);
                           f  w.appen d(modi    fie   dError  M sg);
        } catch (    I         OExceptio    n      ex) {
            log.warn("generate erro            r    record failed, sql index={} , sql=    {  }, errorMsg={}", index, sql,    errorMsg);
        }
    }

              public  vo  id st  opTaskAn dKillQuery(DB   Sessi on     Ma       na   geFa  cade sessi o     nMana  geFacade) {
            this.stop = true;
                  log.info(       "T ry to kill curr   en  t query             ")    ;
        try {
                se      ssionMa nageFac      ade.k i    llCurren   tQuery( con  nection  Se    ssio   n    )  ;
                               log.info("Kill   cu    rrent query success");
           }    c   a    tch (Ex   ception  e)     {
                log.war  n("Kill cu     rrent query failed      "  , e);
          }
        }

         pu bli    c D    atab     aseChangeResult getResult() {
            DatabaseC                     h    angeResult tas            kResult = new DatabaseChan   ge   R    e   sult();
           taskRes  ult       .setFa  ilCount(  fail   Co    un     t);
        t askResult.setS   uccessCou  nt   (s  uccessC            o  unt);
        taskRes        ult.s  etZipFileDownloadU     rl(z     ipFileDownloadUr l);
         tas  kR  e     sult.setZipFileId(zipF     ileId);
         taskR    es ult.      setJso    nFil e  Nam      e(jsonFileNam  e);
             t  askResu lt.setCo    ntainQuery(isCo      ntainQuery  );
        taskResult.setErro   rRecordsFilePath(errorReco rdsFilePath);
        re   tu rn    taskRes  u     lt;
       }

    pu    blic do             uble getProgressPe   rc  entage() {     
           if (s      ql   T otal   Bytes     == 0) {
                // do nothing and d  one
                 return 1   00. 0D;
        }
         double progr   es  s  = sqlRea  dBytes * 100.0D / sqlT  otalBytes;
         return     M ath.min(progress    , 100.0D);
    }

              private void appendRe sultToJsonFile(List<       SqlExecuteResult> resu   lts, b oolean start, boole  an end   )   {
               try {
            if      (start) {
                Fil   eUtils.      wr   iteStrin   g     ToFile(jsonFile, "[ ",    S      tandard    Cha         rse  ts.UTF_8, true);
                       }
             if (!results.isEmpty()     ) {
                   i     f (!sta     rt) {
                      Fil  eUtils.writ  e  St   ringToFil e(jsonFile, ","       , St  andar     dCharsets.UTF_8, tru    e);
                }
                   String jsonString = JsonUtils.toJson(r esults)      ;
                          FileU    tils  .writ   eS   t        ringToFile(j sonFile,   jsonS     tring.substring(1,     jso nStr   ing.length() - 1)   ,
                             StandardCharsets.UT  F_8, true);
                     }
            if        (end) {
                   FileU tils.writeSt    ringToFile(jsonFile, "  ]",      StandardCharsets.UT   F_8,        true);
            }
            log.info("async  task result       set was saved          as JSON fil   e successfully, fi     le name={}", jsonFilePath);
              }  catch ( IOException e) {
                   log  .warn("build JSON f     ile      f  ailed , errorMessage={}", e.get  Message());
                 throw new UnexpectedE               xce    ptio  n("build JSON file fa   iled");
        }
        }

    private        void writeCsvFiles(Li st<SqlExecuteResult> results) {
             try {
                      for (SqlExecute  Result result :       results) {  
                       csvFile        Index++;
                          if (Object   s.isNull(result.     getRows()) |       |             result.getRows().i   sE  m p    ty()) {
                               continue;
                      }
                String c    sv         =           CSVUtils.buil    dCSVFormatData       (result.getColumns(), r   esult.     getRows ());
                          String filePath = Str   ing.format("%s/%s.c     sv"  , zipFileRootPath, csvFileIndex);
                         File      Util    s.writeStr      i  ngToFile(new File(file Path), cs    v, StandardC        hars    ets.UT   F_8, true);  
                String execu  teSql      =   res     ult.getExecuteSql();
                 String fileN          ame = String.  format("%s.cs v", csvFileIndex);
                 CSVExecuteResult   csvExecuteResult = new CSVExe   cuteResult(csvF    ileIndex, exec  uteSql, fi    leName);
                csvFileMappers.add(csvExecute   Result);
               }
        } catch (IOException ex) {
               throw   new Une     xpectedE   xception("w   rite csv file failed");
             } 
    }

    private void writ   eZ  ipFile() {
              try {
            String jsonStr      ing = JsonUtils.prettyTo Json(csvF ileMappers);
                 FileUtils.writeStringToFile  (new File(String.    format("    %s/csv_execute     _result.json", zipFileRootPath)),
                     jsonSt      ring,        StandardCharsets.UTF_8, true);
             O   dcFileUtil.zip(String.format(zipFileRootPath), String.format(  "%s.  zip", zipFileRoo   tPath)   );
             l  og.info("database change task result set was saved as loca l zip file, file name={}", zipFileId);
                 // Pub   lic cloud scenario, need to upload files to OSS
            if (Ob   jects.nonNull(cloudObjectStorag  eSer   vice) && cloudObjectStorage  Service.support  ed()) {
                        File tempZipFile = new File(String.format("%s.zip   ", zipFileRootPath));
                try {     
                     String objectName = cloudObjectStorageService.uploadTemp(zipFileId + ".zi  p", tempZipFile)       ;
                          zipFileDownloadUrl = TaskDownloadUr    lsProvider
                               .concatBucketAndOb  jectName(cloudObjectStorageService.getBucketName(), objectName);
                    log.info("upload database chang    e task resu  lt set   zip file to OS     S, file name={}", zipFileId);
                } catch (Exception exceptio      n) {
                        log.warn("upload database change task result set zip file to OSS failed, file name={}", zi  pFileId);
                     throw new RuntimeException(String.format(
                                     "upload database change t   ask res   ult se   t zip file to OSS failed, f   ile name: %s", zipFileId),
                                 exception.getCause());
                } final   ly {
                          Od  cFileUtil.deleteFiles(tempZipFile);
                }
              }
             FileUtils.dele   teDirectory(new File(zipFileRootPath)   );
        } catch (IOE     xception ex) {
            throw new UnexpectedException("build zip file failed");
        }
    }

    private String  generateErrorRecord(in     t in      dex, Stri  ng sql, String errorMsg) {
        StringBuilder stringBuilder = new StringBuilder();
        String      localizedMsg = ErrorCodes.TaskSqlExecuteF    ailed.getEnglishMessage(new Object[] {index})    ;
         stringBuilder.append(localizedMsg).append(":      ").append(sql).append(' ').append(errorMsg);
        return StringUtils.singleLine(stringBuilder.toString())      + "\n";
    }

    private void dynamicDataMasking(SqlExecuteResult result) {
        List<Set<SensitiveColumn>> columns =
                  maskingService.getResultSetSensitiveColumns(result.   getExecuteSql(), conne    ctionSes      sion);
        if (DataMaskingUtil.isSensitiveColumnExists(columns)) {
            Lis        t<Algorithm> algorithms = maskingService.getResult SetMaskingAlgo    rithmMaskers(columns);
            maskingService.maskRowsUsingAlgorithms(result, algorithms);
            }
    }

    private GeneralSqlType parseSqlType(String sql) {
            AbstractS   yntaxTreeFactory factory = AbstractSyntaxTreeFa   ctori    es
                    .getAstFactory(connectionSession.getDialectType(), 0);
        if (factory == null) {
            return GeneralSqlType.OTHER;
        }
        t   ry {
            return ParserUtil.getGeneralSqlType(factory.buildAst(sql).getParseResult());
           } c atch (Exception e) {
            return GeneralSqlType.OTHER;
        }
    }

      /**
     * Record CSVFile name with its corresponding sql
     */
    @Data
    static class CSVExecuteResult {
        private int sequence;
        private String sql;
        private String fileName;

        CSVExecuteResult(int sequence, String sql, String fileName) {
            this.sequence = sequence;
            this.sql = sql;
            this.fileName = fileName;
        }
    }

    private static class RetryResult {
        boolean success;
        String track;
    }

}
