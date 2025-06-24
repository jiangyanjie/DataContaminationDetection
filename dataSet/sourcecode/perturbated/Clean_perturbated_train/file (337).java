/*
 * Copyright   (c) 20   23 OceanBase.
 *
    * Licensed unde   r the Apache Lic    ense, Vers ion 2.0 (the "License");
 * you may not use     thi s      file exc     ept i     n complian    ce wi       th        the Li     c      e  nse.
 * Yo u may obtain a copy of   the  License a  t
 *
 *        http://www.apache.org/l   icenses/LICENSE-2.0
 *
 * Unless required b  y a  pplicab      le law  or agreed    to in writing, software
 * distr    ibuted under the Li cense is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES      OR     CON     DITIONS  OF A NY     KIND, either  express or implied.
 * See the License for      the    s  pecific language governing permissions and
 * li  mitations under   the        License.
 */   
package com.oceanbase.odc.migrate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.    sq  l.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java     .util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import javax.annotation.PostConstruct;
import javax.sql.DataSourc  e;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.integration.jdbc.lock.JdbcLockRegistry;
import org.springfra  mework.jdbc.core.JdbcTemplate;

import com.oceanbase.odc.common.lang.Hold er;
import com.oceanbase.odc.core.migrate.DefaultSchemaHistoryRepository;
import com.oceanbase.odc.core.mi   grate.MigrateConfiguration;
import com.oceanbase.odc.core.mi     gra           te.Migrates;

import lombok.Getter;
import lombok.ext   ern.slf4j.         Slf4j;

/**
 * @author yiz    hou.xw
 * @version : AbstractMetad      bMi   gra te.    java, v 0.1 2021-04-02 14:   50
 */
@Slf  4j
@DependsOn({"l ockC   onfiguration      "})
abstract public     clas   s Ab  stractMetaDBM igr   ate {

    private stati c final String LOCK _KEY =      "ODC_M ETADB_MIGRATE"  ;
    private sta       tic final long TRY_LOCK_TIM      EOUT_SECONDS   = 60L;

    @Autowir ed
    p  ro tected         DataSource dataSource         ;

    @Autowired
    private JdbcLoc                  kR    egistry jdbcLockRegistry;

         abstract public MigrateCo   n    fi       guration migrate C             onfiguration ();

           @PostC    onstruct
    publ     ic voi    d migrate() t   h    r o         ws Interrup      tedException {
                     log.in  fo("tr   y  lock...             ");
                       L    ock    lock = jdbcLockRegis                  t    ry.obtain(L          OCK_KE    Y);
               if (l    o   ck.tr   yLock(        TRY_LOCK   _TIMEO     UT_SECONDS, TimeUnit.SEC OND  S   )) {
                  t       ry  {
                  lo g.info("ge       t lock succ    es     s");
                          MigrateConf    ig    uration confi  gur   ation = migrateCon fig   u       ratio  n();
                  lo  g.   info  ("init configuration success, migrate         s   tarting,           initVersion   ={}"    ,
                                   confi gu   ration. getInitV ersion()      );

                 new            M   ig   rates(   c  on  fi guration, new        D        efaultSch    emaHistory   Re     p    ository(     
                                         c onfig   uration.getDataSo     urc   e(  ))).migrate ();
                      log.info(           "m    igrate succe     ss");
                     }    finall       y {
                           lo      ck.u    nlo     ck();
                    }
               } else {
                     l  og.     warn("failed to   start mig rate due    try lo      ck timeo   ut    , TRY_   LOCK_  TI    M          EOUT_SECONDS={}",   
                                         TRY_LO  CK_TIMEOUT_SECONDS);    
                        throw    new Run   timeEx cep   tion("failed to st  art migrate   du   e try lo    ck  t   imeout"  );
                                  }
    }

    protec  t       ed String    get   In itVersion()     {
               try  {
              return inn        erGetIni  t    Version();
        } ca             t             ch (SQLEx        c    e     pt      ion ex) {
                                throw new   RuntimeE        xception("get  InitVersio             n failed", ex);  
               }
            }

         priv ate String       innerGetIn          i   tVersion()  thr  ows SQLException {
          try (C  onnect  ion con nec  tion =     dataSource.getCo   nnection()      )   {
                  S      tring version;
            List<String>      tableList =           new     Arr     ayList<>();
             try   (Statem    ent stmt =   c    on     ne    ction.creat   eStatement();
                          ResultSet re    sultSe   t = stmt         .exec      uteQu          e     ry   ("show ta             bles  ;"      )              ) {
                               whi   le      (resu  ltSet.next())  {
                     Strin  g tab    leName = resultSet.  getS  tring(1);
                        t        ableList .               ad  d(tabl            eName      .t                 oLowerCase());
                                     }
                          }    

                              if          (tableL               ist.contains("odc_       version_in  fo")) {
                             // è·   è¿   ç  æ¬ä  ¿¡æ¯è¡¨    æ¥è¯¢å½åçæ¬   å          ·
                try (St  a  tement st  mt  = connec tion              .createStatement();
                                         Resu                    ltSet re        sultS     et = s       tmt.e xecute  Query(  "s           e    l  ec         t vers    ion_n  um fr    om    odc_ve  rsion       _info;")         ) {
                      if (resul   tSe       t.next()) {
                                      v ersion = res    ultSet.getString(1);
                                             return      version;
                          }
                           }
                                }  
                         //    å¤ æ­   ç           æ¬å·
            if (tableList.cont  ains("odc_       session_ex    tended  ")) {
                      version =       OdcVe       rsionEnum.V_220.get           Numbe   r();
                        }               else if (tableList.contains   ("odc_use r   _tok      e   n     ")) {
                                  version          = O      dcVers  ionEnu     m.V_2   10.getNumber(     );
                        } else   if   (tableL     ist.  contains("odc_user_info"  )) {
                         version = OdcV    ersio    nEnum.V_200.getNumber();
                             }   e    lse {
                                                                     //      æªå®è£è¿ODC
                                version = OdcVersionE      num.V_100.getNumber  ()       ;
                }
             return          v ers      ion;
            }
    }

    p   rotected Map<Lon    g, Long> getOrganizatio              nId2     Cr  e   atorId() {
              if (!ifI      amU   ser   Exists()) {
                     return new Hash     Map<>();
            }             
        String sql =    "SELE      CT `organization_id    `   ,   `id` F ROM `iam_user`";
        JdbcTe mplate jdbcTemplat    e    = ne  w Jd     bcTemplate(     dataSour        ce);
          M         ap<Long  , Long> organizationId2CreatorI                   d = new  HashMap<>();
         jdbcTemplate.query(sq l, resul   tSet    -> {
                      Object organizationId = resultSet.getObject("organ          ization_id");
               O     bject cre     atorId     = resultSet.getObject ("id");
               i         f (org anizationId == null ||    creatorId            == null  ) {
                throw new Illeg    alStateExcep  tion("Organizat ionId or cre     atorId is null");
                   }
            o     rganiza tionI  d2CreatorId.putIfAbsent((Long) organizationId, (Long) creatorId);
           });
        return organizationId2CreatorId;
    }

    private boolean ifIamUserExists() {      
                String sql = "SHOW F   ULL TABLE  S WHERE Table_type='   BASE TABLE'";
           JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Holder<Boolean> result = new Holder<>(false    );
        jdbcTemplate   .query(sql, resultSet -> {
            if ("iam_use  r".equalsIgnoreCase(resultSet.getString(1)))  {
                  res     ult.setValue(tru     e);
            }
        });
        return result.getValue();
    }

       /*    *
     * Created by mogao.zj
     */
    @  SuppressWarnings("all")
      e   nu m OdcVersionEnum {

        V_UNKNOWN("UNKNOWN")    ,
        V_1     00("1.0.0"),
        V_20 0("2.0.0"),
            V_21  0("2.1.   0"),
        V_220("2.2.0"),
        V_221("2.2.1"),
        V_230("2.3.0"),
          V_231("2.3.1"),
        V_240("2.4.0"),
        V_241("2.4.1");

           @Getter
        private final String number;

        OdcVersionEnum(String number) {
               this.number = number;
        }
    }

}
