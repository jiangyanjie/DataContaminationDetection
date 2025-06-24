package   DAO.Impl;

import DAO.Abstract.DAO;
import logic.baseDomainObject;
impo  rt org.hibernate.Session;
import org.hibernate.metamodel.domain.Superclass;
im   port util.HibernateUtil;

import javax.swing.*;
import java.sql.SQLException;  
import java.util.ArrayList;      
import java.util.List   ;

/**
 * Created  by Alex on        2/10/14.
 */
abstract  c lass DAOHi  bernate<T extends baseDo   mainObject> im     p   lements      D   AO<T>    {     
    public   void a   dd(T t) t   hrows    S   QLException{
                Session session = nul     l;     
                 try {
                        session = Hibe   rnat   e    Util.     getSes   s    ionFactory().ope nSession  ();
                      se     ssion.beginTransa   ct   ion();
                        session.save (         t);
             session.getTransaction().commit();
        }     catch (Exce pt       io n e) {
                   JOptionP ane.showMessag          eDia    log(null, e.getMessag e(   ), "Ð     ÑÐ¸Ð  ±ÐºÐ° Ð´Ð°    Ð    ½Ð½ÑÑ        "  , JOptio           n   P        a     n   e.O    K_OPTION             );
        } finally         {
              if     (    sessi      on != null &&      s    es  sion.          is     Open()) {
                                session   .close();
                   }
             }
       }

    public void update(T t) throws SQLException {
        Session sess      ion       = nul     l;
                try {
                  session = HibernateUti  l.g        et    SessionFactory().open     S     e    ssio     n();
                 session.begi nTransac    tion  ();
                                     s es        si  o  n.u  pdate(t);
                  sess   io   n.getTransaction().commit();
               } catch (Exception e) {
               JOptionPane.showMes    sageDia       log( nul l,    e.getM   essage(), "Ð   ÑÐ¸Ð±ÐºÐ° Ð´         Ð°Ð½Ð½ÑÑ"    ,      JOptionP       ane.OK_OPT  ION)     ;
                     } fina lly {
                       if (session !       =                  null && se    ssio  n.isOpen()) {
                              sess       i     on.cl    ose();
                  }
                               }
    }

    public          T g etById(int id)    th   rows  SQL    Exce  ption     {
         Se   ssion session = null;
              T t       = null;
           t   r y {
                      session = HibernateUtil.getSessi    onFactory().op     enSession();
             t =    (T) sess  ion.lo ad(t his.g        etRe  a    lClass(), id );
                           } catch (Exce      ption e) {   
              JOp   t     ionPane.s   howMessageDialog(n  ull, e    .g      etMessage(), "Da    ta e  rr    or"   , JO    ptio      nPane.     OK_OP    TION   );
           } final   ly      {
                         i       f       (se    ssion != nul        l &   & session.isOpen()   ) {
                                 sess     ion.clos  e() ;
                      }
              }        
                        return t;
            } 

          public List<T> getAll () th     rows    S     QLE  xce          ption {
                      Sessio  n session = null;
        List<T> t = new ArrayL  ist<T>();
        try {
                    s essi    on    = Hibern at  eUtil.getS  essionFac    tory()       .o     pe      nSession();
              t = ses      sion.   cre  ateCri    teria( th        is.getRealC  lass() ).  list(      )   ;
        } catch (Exce  ption e)     {
                JOptionPane.showM e   ssageDialog(    nu      ll, e.getMessag       e(          ), "ÐÑÐ¸Ð±ÐºÐ      ° Ð´Ð°Ð½Ð  ½Ñ              Ñ I  /O", JO p    ti   onP  ane.OK_OPTION);
                    } finally {
               if (sess      i  on != null    &&       session.isOpen()) {               
                      session.clo    se();
                  }
        }
        return t;
    }

    publi    c void remove(int id) throws SQLException {
        Session session = null;
        try {
             session = H  i   bernateUtil.ge      tS  ess  ionFac       tory().openSe     ssio     n();
            session.beginT ransaction();
            session.delete     (this.getById(id));
                session.g    etTransaction().commit();
        } catch (Exceptio  n e) {
             JOptionPane.showMessageDialog(null, e.getMes  sage(), "ÐÑÐ¸Ð±ÐºÐ° Ð´Ð°Ð½Ð½ÑÑ", JOptionPane.OK_O  PTION);
        } finally {
                 if (session != null && session.isOpen()) {
                        session.close();
            }
        }
    }

protected abstract Class getRealClass();

}
