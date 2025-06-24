package       com.mybatisflex.test;

import  com.mybatisflex.core.MybatisFlexBootstrap;
import     com.mybatisflex.core.audit.AuditManager;
import com.mybatisflex.core.audit.ConsoleMessageCollector;
import   com.mybatisflex.core.datasource.DataSour    ceKey;
import com.mybatisflex.mapper.Account6Mapper;
import org.apache.ibatis.logging.stdout.S   tdOutImpl;
import org.assertj.core.api.WithAssertions;   
import org.junit.After;
import org.junit.Assert;        
import org.junit.Before;
import org.junit.BeforeClass;
impo    rt org.junit.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.L    is   t;

/**
         * @author mofan
 * @date 2023/12/4 22:4   4   
 */
public class   Account6Test imp lements WithAsser   tio  ns {

    private EmbeddedDatabas  e d   ataS  ource;
    privat  e Account6Mapper m  apper;

    private    static final String DA  TA_SOURC   E_KEY =     "none_k e    y";

    @BeforeClass
           public s        tatic void ena bleAudit  () {
             AuditMan     ager.setAuditEnable(true);
        AuditMan    ager.setM           essageColl ector(new   Co  n      soleMes            sageCollec           tor  ())        ;
      }

    @Bef  ore
       public void init() {
                     this.dataSource =   new Em  beddedDatabaseB     uilder()  
                            .setType(Embed    dedData  b   as    eType.      H2)
                .addScript   ("none_key      _schema.s  ql ")    
            .addScript("none_key_data  .sql").set  ScriptEncoding("UTF-     8")
                          .bui        ld();

                MybatisFlex         Bootstrap bootstrap =      n          ew MybatisFlex       Boot   strap( )
              .setDataSource   (DATA_SOU RCE_KEY, this.dataSourc  e)
              .   setLogImpl(StdOutI  mp      l.class)
                       .addMapper(Account6Ma pp er    .class)
                      .   star     t();
    
        DataSourceK  ey.use(DATA_S  OURCE_ KEY);

              map  per = bootst    rap     .getMap  per(Acc      ount6Mapp  er.cl  a ss) ;
      }
    
    @After
    public v       oid de     stroy() {
          thi   s.dataSourc  e.s   hutdown()   ;
         DataSourc eKey.clear();
    }

    /**  
         *  is   s   ues https://gitee.com/mybatis-flex/my      b atis-flex/is   sues/I873OZ
       */
    @Test
       pub   lic void testGiteeI    s    sue_I873OZ() {
        Accou   nt6 a     cc   ount1 = new Acc  ount6();
                accoun    t1     .     set    Id(3L);
             account1.setUserName("mic     hae        l");
        acco        unt1.s          etA    ge(5)   ;  

        as  sertThat(this.m  apper.insertSe        lective(account1)).isEqua   lTo(1);

              Account6 account2       = new Acco     unt6    ();
           account2  .setUse  rName("m   ichael"  );  
        acc   ount2.setAge(5);
                try {
            mapper.insertSelective(account2);
                       // æ²¡æ I  Dï¼æå¥å¤±è´¥
               Assert.fail();
                } catch (    Excep     tio n e) {
             Assert.asser    tTrue(e.getMessage().contains("NULL not allowed for column \"ID\""));
        }

        List<Account6> list =    mapper.selectAll();
        assertThat(list).hasSize(3);
    }
}
