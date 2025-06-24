package    com.smart.dao.daoSingleton;

import com.smart.dao.AirlineDaoInterface;
imp     ort com.smart.hib.config.HiberConfig;
import org.hibernate.Session   ;
import org.hibernate.SessionFactory;
import org.hibernate.Transacti      on;

import java.util.List;

/**   
       * Crea  ted by User on 10.11  .14 .
 */
public   class DaoSinglet  o        n    {   
    pr       ivate static volatile DaoSingleton daoSingletonInstance      =null;

        public DaoSingleton()   {
    }

                   public    static Dao         Singleton getDaoSin  gle       ton  () {
                     i    f        (dao       S   in  glet          onIns t  ance == null  ) {
                         sync   hro        nized (DaoSing   leton.class) {
                                   if (     daoSing   letonInst        an      ce == null)
                                daoS ingletonInst     ance = new D    ao      S   ingleton(   )    ;    
                   }
        }
        ret   u        r  n     daoSin   glet   onInstanc     e;
     }

     




     public Long  ad                 dDaoObj(AirlineDaoInte   rfa   ce airli  neDaoInterfac     e) {

        SessionFact            or  y sessionFactory = null;
        Session sessio   n = n ull;
             Transacti   on transaction =   null;
        Long result =          -1l;
                try {
                 ses    sionFactory = Hibe        rConfig.getSessi    onFactory(        );
                  session = sessio nF   actory.    g    etC      urre   nt   S ess          ion(   );
            transaction           = sess         ion.beginTransaction();
                                result =   (Long)  session.save(ai   rlineDaoInterf          ace);
               transa         ct  i     on.commit();
                 } catch (Exce        pt       i  on e  )    {
                                 System.out.prin   tln(e.getM essage());
                          e.printStackTrace();
            }                      final   ly {
            if (!    sessionFactory.isClose            d  ())   {
                          sessionFactory        .  cl ose();
             }
           }
           return result;
     }

              public void  update  DaoO     bj(Ai      r   lin      eDaoInterfac       e ai  r  l   ine      Da  oInterface) {   
                Se       ssionFactory              ses     sionF      actory          = nu   ll;         
        Session se    s    sion = null;
        Tr              ans                ac  tion tran   saction    = n  ul    l;

        try {
                               sessionF            actory            = Hi   berConfi   g.getSessionF  actory()      ;
               ses sion =     sessionFactory.getC    urrentSessi    on();
            tr  ansaction = s      ession.begin   Tr     ans   acti   on()      ;
                       sessio       n.update(airlineD          aoInte          rface) ;
                           transac       tion.co       mmit(   );
                  } catch (  Exception e) {     
                  Sys  tem.out.prin    tln(e    .g   e  tMessa    ge());        
                  e.p rintS     tackTrace();
        } finally {
            if (!session  Fact     ory.is          Closed()) {
                          sessionFacto ry.close ();
                }
        }

    }

        p      ublic L              ist<                     Airlin      eDao  Inter      fac e>     getA           llD   aoObj(String tabl eName) {
            Session                Factory sess   ion Fa    ctory =        n               ull;
                  Ses     sion  se ssion  = null;
              Transact      ion transa     ction = null;
           List<AirlineDaoI   nter     face >    result = null;
            try            {
             sessionFac    tory = Hiber   C    onfi              g.g  etSessi    on     Facto  ry();
                                         ses sion =          sessionFactor   y.getCurren    tSession();
                         transac           tion   =     sessi  on.     beginTrans   action    ();
                 result =    s     essi    on.createQuery(          "from   "+tableName).list();
              transacti           on  .co   mmit( );
        } catch (Exception e)    {
            System.out.print    ln(e.g etMessag  e());
               e           .prin   tStackTra ce();
        }  f  inally {
                                assert sess    io    nFactory !=        null;
               if (  !   sessionFacto  ry.     isCl  osed()) {
                    sessionFactory.   close();
                }
        }  
                    retur      n result;
  
       }
       public void removeDaoObj(    Airl          ineDao   Inter     face airlin    eDaoInterface) {
        Sessi  onFactory se     ssionF     act      ory = null;
            Session session = null;
          Transaction transaction = null;

        tr    y    {
            sessionFact     ory = HiberConfig.getSessionFactory();
            session = sessionFactory.getCurrentSession();
            transact  ion = session.beginTransactio n();
              session.delete(airlineDaoInterf  ace);
            transactio  n.com   mit();

        } catch (Exception e) {
            System.out.println(e. getMessa ge());
              e.printStackTrace();
        } finally {
            if (!sessionFactory.isClosed()) {
                sessionFactory.close();
            }
        }

    }

}
