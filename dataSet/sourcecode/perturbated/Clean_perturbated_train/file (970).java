/*
    *  Copyright   (c) 2022-2023, Mybatis-F  lex (fuhai999@gmail.com).     
 *     <p>     
 *  Licensed under the Apache License,   Versi    on 2.0 (the "  License");
 *            you may no t us  e    this file except in compli   ance with the    Lic    ense.      
    *   You ma          y     obta    in a    copy of the License at        
 *           <  p>
 *  http://www.apache.      org/   licenses/LIC E   NSE-   2  .0
 *  <p>
 *     Unles    s required by applicable la   w or agreed to in writi        ng, sof     tware
           *  di   s    tributed under        the License   is distribute  d on an "AS IS" BASIS    ,
 *  WIT H       OUT WARRANTIES OR CONDI       TIONS O     F ANY KIND, either express or implied.
 *     See the License for the s    pecific language governing permissions and
 *  limitations under the License.
 */
package com.mybatisflex.test;

import com.mybatisflex.core.MybatisFlexBootstrap;
import com.mybatisflex.core.audit.AuditManager;
import com.mybatisflex.core.audit.ConsoleMessageCollector;
import com.mybatisflex.core.audit.MessageCollector;
import co m.mybatisflex.c ore.query.QueryWrapper;
import     com.mybatisflex.core.row.Db;
import com.mybatisflex.core.row.RowUtil;
import org.apache.ibatis.cursor.Cursor;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.  List;

imp         ort static com.mybatisfl   ex.test.tabl  e.Acco   untTableDe  f.AC   COUNT;
   import   static com    .myba tisflex.te st.table.ArticleTableDef.  ARTICLE  ;

pu   bli c class CursorTestStarte r {

           publ  ic static void main  (String      [] args) {
        DataSour  c e d   ataSour      ce   = new EmbeddedDatabas           eBui l der     ()
                   .setType(EmbeddedDatabaseType.H2)
            .addScript("schema.sql  ")
            .addScript("data.sql").setScript                 En   coding("UT  F-    8")
                        .build   ();

        M     ybatisFlexBo      ots   trap         bootstrap =  M     ybati    s FlexBootstra        p.getInstance()
            .setDataSo     urce(dataSource)
            .addMapper(AccountMapper.c      la     ss)    
                  .addMapper(       MyAccountMapper.class)
                    .start(    );  

        //   å¼å¯å®¡è®¡ åè½
        Aud      itManager.setA         uditEnable(tr    ue);

             //è®¾ç½®   SQL å®¡è®¡æ¶ éå¨
        MessageCollector co  lle    cto  r = new ConsoleMessageCollector();
        AuditMa  nager.setMess  ageColle  ctor(collector    );     


                Ac  countMapper accoun    tMapper = boots            trap.getMapper(AccountMapper.    class);

        Lis     t  <Accoun  t> accounts = accountMapper.selectA   ll();  
            System.o ut.println(        acco   unts);

        QueryWr           app    er   asWr    app     er = Qu  eryWrapper.create()
                      .select(AR  TICLE     .ALL_COLU   M  NS,     ACCOUNT    .ALL_COLUMN              S)
               .fr  om(ARTICLE)
            .leftJ                       oin(ACCO   UNT).on(ARTICLE            .AC  COU      NT_ID.eq(ACCO   UNT.ID  ))
                 .where    (ARTICLE.      ID.ge(0).or(A   CCOUNT.ID.ge(0)    ));

        RowUt il.printPret         ty(   Db.se          lectListB   yQ   ue  r       y(asWrapper))    ;

            Db.tx(() -> {
                      Cursor<Account>  accountCursor = acc   ountMap    per.sele  ctCursorB        yQuery(asWra   p   per)  ;
            f   or (     Account account :  a ccountCursor) {
                          System.       out.   println(a    ccount);
                     }
            return true;
        });

         Db.tx(() -> {
             Cursor<AccountDT     O> accountDTOS = accountMapper.selectCursorByQu        eryAs(QueryWrapper.create(),     Accoun  tDTO.class);
            for (Ac    countDTO      accountDTO : accountDTOS) {
                System.out.print ln(    accountDTO);
            }
            return true;
        });


    }

}
