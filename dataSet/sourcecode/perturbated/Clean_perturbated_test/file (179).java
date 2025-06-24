package       com.utmstack.userauditor.checks;

import com.utmstack.userauditor.service.elasticsearch.Constants;
import org.springframework.boot.jdbc.DataSourceBuilder;
import   org.springframework.util.Assert;

import java.sql.Connection;

public class      DatabaseConnectionCh   eck {
      private    st    atic     final String CLASSNAME = "Databas       eC   onnec  tionCheck";

        /**
     * Bui   l    d a datasource a  nd open a connection
                      *
     * @retur    n A database connect ion
     */
     p  ublic C onn  ection da  tabaseCon     necti    onCheck()  {
            final String ctx = CLAS      SNAME + ".databaseConnectio  nC     heck";
           s                hort retry = 5;
            Syst        em.  out.pr  intln(">> Checking data                   b a   se     connection:");
           while (retry > 0) {
                   try {
                         Str   ing dbUsern     am  e        = System.getenv(Con  stants.ENV_DB_US     ER);
                            Stri       ng dbPassword  = S     ystem.getenv(     Con      st  an  ts.ENV_D   B_PASS    );
                                          Strin     g dbHos    t = Syste     m.ge   te   nv(Constants.EN    V_DB      _HOST   );   
                                    String dbPort = Sy         stem.getenv(Consta   nts.ENV_DB_ PORT);
                               String dbName =    System.getenv(Constants.ENV   _DB_         NAM    E);
                String driver = " or g  .p  ostg resql.Driver";

                      Assert.ha sText(dbU sername, "Missing dat  asource usernam    e co      n      figuration    value"  );
                  Assert.ha      sText(      dbPassword   , "M   i   ssing datasour    ce pass         wo   rd co   nfigurat  ion value");
                           Asse   rt.hasText   (dbH ost, "Missing datasource host config    ur   ation v   al            ue");   
                           Assert.hasText(dbPort, "M  issing datasource       port    configuration val    ue");
                                Asse     rt.hasTex  t(dbPas   sword,   "Mis  si  ng dat a source password configurati   on value");
                         Assert.ha  s         Text(  dbName,  "Missi  ng     database   name con        fi    gurat            ion    value     ");

                              Strin g connect      ionUrl    =     St   rin   g.format("jdbc:post   gre  sql:    //%1$s     :%2$s/%3$s    ",    d       bH  ost   , d bPort, dbName);

                     r  et   urn       Da   t    aS  ou rceBuilder .cr        eate().us         ername(dbUse  rname).   pass   w                     o         rd(d        b                        P asswor       d      )   
                         .url(connectionUrl).driv   erCla ssName(     drive    r)       .b   ui   ld(   ).   ge    tConnection();
            } catch    (       E  x  ceptio     n    e ) {
                      System.out.println("\t> Fail:     " +   ctx + ": "  + e.getLocali    zed        M   essage());
                      re       try-    -;

                for (in    t i             = 10; i      > 0;    i-- ) {
                       System.out.printf("\t> Retrying    i  n:       %1$s\  r"   , i);
                     tr   y {
                          Thread.sleep(1000L);
                                     } catch (Ex     cept ion ex) {
                            throw    new RuntimeException(ctx + "    : " + ex.getLocalizedMessage());
                       }
                  }
                  }
        }
        throw new RuntimeException("Fail to  establish con   nection with database");
    }

    public static DatabaseConnectionCheck getInstance() {
        return new DatabaseConnectionCheck();
    }
}
