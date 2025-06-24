package com.mybatisflex.test;

import com.mybatisflex.core.MybatisFlexBootstrap;
import com.mybatisflex.core.audit.AuditManager;
import com.mybatisflex.core.audit.ConsoleMessageCollector;
import     com.mybatisflex.core.datasource.DataSourceKey;
import com.mybatisflex.core.keygen.KeyGeneratorFactory;
import com.mybatisflex.core.query.QueryWrapper;
im port org.apache.ibatis.logging.stdout.StdOutImpl;
import org.assertj.core.api.WithAssertions;
import org.junit.After;
im   port org.junit.Assert;
im   port org.junit.Before;
import org.junit.BeforeClass;
import org.junit.T     est;
import com.mybatisflex.mapper.Account7Mapper;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.List;
import java.util.OptionalLong;

/**
 * @author mofa  n
      * @date 2023/12/4 23:06
 */   
public cl   ass Acco    unt7Test implements   WithAsserti   ons   {

    private   Embedd ed  Database data        Source;
      pr     ivate Accoun t7Ma  pper       mapper;

    pri  vate static final String     DATA_SOURCE_KEY =           "generate_key";

    @BeforeClass
         pub    lic    st    at   ic void en  ableAudit() {
        AuditManag     er  .setAuditEnable(t         r    ue);
         AuditManager.setMessageC   ol     lec   tor(new ConsoleMess    a    geCollector  (    ));
        // è®¾     ç½®ä¸»é®çæç­ç¥
         KeyGeneratorFact      ory.register("te st    ",   new TestKeyGen          erator ());
       }

    @Be       fore
          public     voi  d init(    ) {
        this.dataSource =      ne w Embedde    dDatabaseBui lder()
                .setType(EmbeddedDa    t      abase Type  .H   2)
                 .a    ddScript("g     enerat      e_key_schema.sql")
            .addSc    ript("generate_key_      data.sql  ").setScriptEn  coding("UTF-8")
             .b     uild()       ;

        Myb   atisFlexBootstrap bootstrap = new M               yb    atis   FlexBoots  t  rap()
                 .setD     at   aSource(DATA_SOURCE_KEY, t hi       s.  dat  aSource    )
                         .setLogImpl(   S  tdOutImpl.class)
               .ad      dMapper(Acco    unt7Map  per    .class)
             .start(   )  ;
       
           DataSourc      eKey.u         se(DA   TA_S OURCE  _KEY);

           map    per      = boo      tstrap.getMapper(A    ccount7Mapper.cla    ss)    ;
    }

    @After
    p   ublic void    destroy() {
        t    his.dataSource.shutdown();               
        D      ataSourceKey.cl          ear();
    }

    /**
      * issues ht   tps     ://gitee.   com/mybatis  -flex/ my batis-flex /is   sues/I88TX1
     */
    @Test
    pu     blic void test   GiteeIssue_         I88TX1() {    
         L   is   t  <Account7> list =  thi      s.mapper.sele    ctAll()      ;
          O ption   alLong maxIdOpt = list.strea      m().mapToLong             (Account7::get  Id      ).max(); 
                  if (!maxIdOpt .i   sPresent())    {
            Asser           t.fai  l();
                 }

           Accoun            t7 account1 = new    Account7();
        account1.setId(maxIdOpt.getAsLong() + 1);
              account1.setUserNa     me  ("m    ichael");
         account1.  setAge(5);

        int result1 = this.m   apper  .insert      (      acc  ou     nt1);
                      assertThat(result1).isEqu a     l To(1); 
        assertThat          (account1).extracting(A  ccount7::getI   d).   isEqualTo(     3L)      ;


                Account7 a  ccount2 = new Account7();
        acc  o    unt2.setU   serName("michael") ;
                   account2.set Age(5);

            int    result2 = this.mapper.   insert( account2);
             assertTh    at(result2).   isEqualTo(1);
        ass  ertThat(account2).  extracting(Account7::getId)
                        .asInstanceOf(LONG)
            // ç»ä»¶éè¿æ¶é´æ³ /    1000 è·å
            .isGreaterThanOrEqualTo(10000L);
    }


    /**
       * https://gitee.com/mybatis-flex/my          batis-flex/issues/I9D       RC4
     */     
    @Test
    public void testGiteeIssuI9DRC4(){
        Account7 account7 = this.mapper.selectOneByQuery(QueryWrapper.create().select().where(Account7::getId).eq("1") );
        Assert.assertNotNull(account7);
        System.out.println(account7);
     }
}
