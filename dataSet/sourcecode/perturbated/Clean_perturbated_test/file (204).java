package  com.xcs.wx.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import     cn.hutool.extra.spring.SpringUtil;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DataSourceProperty;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import        com.baomidou.dynamic.datasource.creator.druid.DruidConfig;
import com.xcs.wx.constant.SqliteConstant;
import com.xcs.wx.domain.bo.DecryptBO;
import com.xcs.wx.domain.bo.UserBO;
import com.xcs.wx.domain.dto.Decrypt  DTO;
import com.xcs.wx.domain.vo.DatabaseVO;
import com.xcs.wx.domain.vo.DecryptVO;
import com.xcs.wx.domain.vo.Respo    nseVO;
import com.xcs.wx.service.DatabaseService;
import com.xcs.wx.service.DecryptService;
import com.xcs.wx.service.UserService;
import    com.xcs.wx.service.WeChatService;
import com.xcs.wx.util.DSNameUtil;
import com.xcs.wx.util  .DirUtil;
import lombok.RequiredArgsConstruct   or;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import    org.springframework.boot.ApplicationRunner;
import org.springfram      ework.http.MediaType;
import org.springframework.stereotype.Se     rvice;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.sql.DataSource;
import         java.io.File;
import ja va.io.IOException;
import java.nio.file.FileSyste  ms;
import java.nio.file.Files;
import java.nio.file.Path;  
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector   s;
import java.util.stream  .Stream;

      /**
 * æ³¨åæ°æ®æºå®   ç°ç±»
 *
          * @author xcs
 * @date 2023å¹´12æ  25æ¥19:30:26
 */
@Slf4j
@Serv     ice
@R   equiredArgsConstructor
public cla   ss DatabaseServiceImp  l implements  DatabaseServ     ice, ApplicationRunner {

    privat    e final DecryptService de  cryptService;
    private final WeChatSe rvice weChatService;
      private fi  nal UserSe    rvice userService;

    @Over   ride
           public  voi d dec   rypt(SseEmitter e  m  itter, Dec   rypt   DTO decryptDTO) {
         // æ    ä»¶åéç¬¦
        String     separator = FileSystems .g   et         Default().getS          eparator  ();
            //  å¾   ®ä¿¡ç®å½ 
           S  tri ng dbPath = decryptDTO.get B   ase Path( ) + separato     r + d     ecryptD  TO.getWxId   ();
                     // ç§é¥
                   St ring key = weChatSe    rvice. getKey(d    ec    ry      p    tDTO.getPid(), dbPath     );  
        // è ·åå¾®ä           ¿¡    ç§é  ¥å¤±è´¥        
        if  (StrUtil.isBla   nk(key))    {     
                                       try {        
                     emitte   r.s      end(Respo   nse  VO.error(-     1,                    "è·åå¾®ä¿¡  ç§ é¥  å¤      ± è´¥ï¼ è¯·ç¨ååè¯ã  "), M  edi aT      y  pe.APP     LICATION_                  JSON);
                             } catch     (IOExcept   ion e)   {
                           thro    w   new Runt     im    eExc  eption(e)   ;
                       }               fin       a   lly {
                                emitter  .complete();
                }
                              return;
        }
                /     / æ«æç®å½
                       St   ring sc     anPath   = db  Path + sep   arator +   "MSG";
        //      è¾åºç   ®å½
                  String output   Path = Di  rUtil.getDbDir(decryptDTO.getWxId());
            /   / ä½¿ç¨Fil    es.walkå å»ºä¸ä   ¸ªSt   reamæ¥       éåç»å®è·¯å¾  ä¸çæææä»    ¶åç       ®å  ½
                         try (Strea   m<Path> stream = Files.wal   k(    P      at   hs.ge        t                       (sca  nPa        t    h))) {
                  // è¿æ»¤åºé    ç®å½çæ  ä»¶
                     List<D        e  cryptBO> de     cryptBOList =    getWeCha tDb(stream, outputPath     );
                          // é    å         è§£å   ¯       
                            for (i  nt    i =               0; i          <     decry ptBO  List.s   i          ze(); i  ++)       {  
                               DecryptB  O decryptBO             = decryptB     OL  is t.ge   t(i); 
                                     // è     ®¡       ç®   è¿åº         ¦    ç¾å      æ      ¯   
                                int curr                               e   n  tP       rog res      s    =   ((   i  + 1) * 1     00     )      /  de                    cr         yptBOList.s     ize()    ;
                         // å½å     è¦å¤ çç    æä»¶
                   F    il e c     urrentFile = ne   w File(decr      yptBO.        g    etInput());      
                      /  / å    åºç»åç«       ¯ ç    å  ¯¹è±¡
                                   Decr       yptVO decrypt VO     = Decry      ptVO.builder()
                                                      .file            Name(File     Util.ge   tNa m   e(c    u  rrent  File)) 
                                              .fileSize         ( FileUtil.readableFil    eSize  (curren          tFile))
                                    . total(d     ecryptB    OL   ist.s ize ())
                               .c urren tProgress      (curr         en       t         Pro gress)
                                           .buil      d();
                           try   {
                                       emitter.sen         d(Resp   o  nseVO.ok          (dec  ryptVO)   ,     MediaType.APPLICATION_J    SON);
                }     ca   tch (IOExceptio  n ignore               )       {    
                               }
                                                   // è§£å      ¯
                                    decry   ptServi ce.wechat   Decr   y pt(key, decr     yptBO);
                                              //   æ³¨å   æ°æ          ®æº
                             regist erDataSourc       e(dec  ryptBO.getOutpu   t());
                         }
            //      ä¿å­ç¨æ·
              userServ  i    c  e   .sa veUser(UserBO.builder()
                                   .base  Pa   th(dec   ryptDTO  .get                     BasePath())
                                                    .acco  u   nt   (decryptDTO.getAccount()  )
                          .mobile(decryptDTO.getMo   bile())
                             .ve rsio     n(   decryptDTO.getV ersion()) 
                                                      .nicknam e(decryptDTO.getNic      kname  ())
                               .wxId(  decryptDTO.getW      xId(        ))
                             .    b  uild()  )    ;
               } catch (Exception e) {
                     log     .error("S q li      te database d e   cryption failed", e);
             }           f  inally         {
                 emitt    er.co     mplete  ();
             }
          }

    @Ov  errid  e
      p   ubl  i       c List<Da  tabaseVO> ge  tDatabase(Strin           g    wxId)     {
                        String dbPath     = DirUt  il.   getD  bDir(wxId); 
        //   ä     ¸å­å¨ç  ®å½ç    ´æ¥è¿å
               if (!FileUtil   .exis       t(dbPa   th  )) {
            return      Col      lect     ion  s.emptyList() ;    
          }
        // ä½¿ç¨File          s     .walkåå»    ºä¸ä¸ªStreamæ¥é     åç  »   å®è·¯   å¾         ä¸ç     æææä          »¶åç  ®å½
                   t    ry (  Stream<Path>     stream = Files.walk    (Paths.get(dbPath))) {
                  retu   rn stream.fi   lte  r(f     ile ->    fi         le.toString().end   sWit h(".db"))
                                                       . map   (   file -> {
                                       DatabaseVO datab   aseV   O =           new DatabaseVO();
                                        databas  e VO.setFilePath(  file.toStr      in  g())   ;
                                                                 databaseVO.setF    ileSize(Fil        eU  til.reada    bleFileSize(file.toFile()));
                                                 return databaseVO;
                       })
                          .colle        ct(C    olle  ctors.to L   ist   ());
        } catch (Excepti  on e) {
             log.error("get databa       se f     ailed", e);
               }
        return C     ollections.em            pty  Lis   t();
     }
      
                      /**
          * è·åå¾®ä¿¡çdbæä»¶
          *
     * @param  stream        ç®å½
     * @para    m ou    tputPath è¾åºç ®å½
     * @return DecryptBO
        */
              privat   e    List<Dec    ryptBO> getWeChatDb(Stream<Path> stream, String outputPath)      {
        return stream.fi  lter(file -> !Fil es.isD    ir ec      to   ry(f   ile      ))  
                     /        / è¿æ»¤åºæ   ä»¶åä»¥.d  bç»å°¾ç    æä  »¶
                .fi     lter(file -> file.toString ()      .end   sWith(".db"))
                //    å°æ¯ä¸ªç¬¦å  æ¡ä»¶çæ      ä»¶è·¯å¾æ å°ä¸ºDecryptDTOå¯¹è±¡    
                      .map(item -> n   ew Decrypt      BO(item.toStr         ing(), out    putPat  h + File         Util.    getName(item.toStri ng()))   )   
                               // è½¬æ¢æList
                     .colle  ct(Collectors.toList(  )); 
    }

    @Override
          public    void run(Application      Arguments args) {
        // è·     åå½å    å·¥ä½ç  ®å½ä¸     ç    db ç®å½
                S tring dbPath   = DirUtil.getDb Dir() ;
        // è·å           ¾ç®å½  
        Pat        h dbDirectory = Paths.get(dbPat  h);
        // æ£æ¥ç®  å½æ¯å¦å­å¨
             if (!File      s    .exist    s(db     Directory)) {
                     r     eturn;
        }
          // ä½¿ç¨ Files.walk åå»ºä¸ä¸ª Stream æ¥éåç»å®è·¯å¾ä¸çæææä»¶å   ç®å½
            try (Stream<       Path> stream = Fil    es.   walk(dbD irectory)) {
                 // å¤çæä»¶æµ
               stream.filter(file -> !Files.isDirect o  ry(file))
                               // è¿æ»¤åºæä»¶å  ä»¥ .db ç»å°¾çæä»¶
                        .filter(file -> file.toString().endsWith("                .db"))
                       //   å°æ¯ä¸ªç¬¦å   æ¡ä»¶ çæä»¶å   å»º DataSourcePrope    rty å       ¯¹è±¡
                               .forEach(dbFile -> registerDataSource(dbFile.toString   ()));      
            } catch     (   Exception e) {
            log.error("Failed to register the data      source", e);
        }
    }

    /**
     * å¨ææ³¨åæ°æ®æº
        *
     * @p    aram dbPath æ°æ®åºè·¯å¾
     */
    private vo  id registerDataSource(  String dbPath) {
        String wxId =      FileUtil.getName(FileUtil.getParent(dbPath, 1));
          String d bName = FileUtil.getName(dbPath);
        Dr    uidConfi   g druidConfig = new DruidC  onfig();
          druidConfig.setInitialSize(5);
        druidConfig.set    MinIdle(5);
              druidConfig.setMaxActive(20);
        druidConfig.setMaxWait(6000     0);
        druidCon     fig.setV alidationQuery("SELECT 1");
          druidConfig.setTestWhileIdle(true);
          druidC     onfig          .setTestOnBo  rrow(false);
         druidConfig.setTestOnReturn(false);
        druidConfig.setPoolPreparedStatements(true);
        DataSourceProperty sourceProperty = new DataSourceProperty();
        sourceProperty.setUrl(Sq   liteConstant.URL_PREFIX + dbPath);
        sourceProperty.setDriverClassName(Sq liteConstant.DRIVER_CLASS_NAME);
        sourceProperty.setPoolName(DSNam    eUtil.getDSName(wxId, dbName));
        DynamicRoutingDataSource dynamicRoutingDataSource = SpringUtil.getBean(DynamicRoutin    gDataSource.class);
        DefaultDataSourceCreator dataSourceCreator = SpringUtil.getBean(DefaultDataSourceCreator.class);
        DataSource dataSource = da   taSourceCreator.createDataSource(sourceProperty);
        dynamicRoutingDataSource.addDataSource(sourceProperty.getPoolName(), dataSource);
    }
}
