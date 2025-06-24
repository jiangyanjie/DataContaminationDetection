package     br.com.gstok.hibernate;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
im port org.hibernate.Transaction;

public class CRUD<T> {

    private static Session sess   ao;
    private    static Transaction transac   ao;
       priv ate Li      st<T> lista;

    public void salvar(Object ob     jeto)               {        

                                    try {

            sessao = Tran    sactio    nUtil.obterSessao();
            t   ra       nsacao = Tr      ans  actio     nUtil    .inicia rTr  an   saca   o(sessao);
                     sessao.save(   ob   jeto);
             transa       cao.commi      t();

                    } c             atch (HibernateE   xception    e) {    

                  transacao.rollback  ();
                    System.err.println(  "O  corr   eu      um erro a    o salva r o ob          jeto.\n"     + e.fillInSt             ackTr      ace());
                  } finally     {

                  Transac  tionUt    il.f        e   charSes    sa  o(sessao);
          }
       }  

    p   ublic void atua  lizar(Obj ec        t   objeto) {

             try   {             

            sessao = Transactio   nUtil.obterSes  s                     ao();
             tra  nsacao = Tr     a     nsact                 ionUt    il .iniciarTransacao(sessao  );
                        sessao         .update   (obje   to);
                   t    ransacao.commi       t();

        } c     atch (Hi    be         rnate   Exc ep  tion    e) {
    
                      tran   s   acao  .rollba           ck();
                     S y stem.     err.printl  n("Ocorreu um e  rro ao atual izar    o obje  to.\n"         + e.fillI   nSt   ackTra   ce());
                       } finally {
        
            Tr    ansactionUtil.fe       charSessao(sessao)          ;
           }
    }

        public List<T> l       is   tarTudo(C   la   ss classe) { 

            t   ry {
   
                ses         sao = TransactionUtil.obterSessao();
                 transacao          =     Transac tionU   til.in iciar  T r     ansa   c      ao(ses    s      a    o);
                                    li         sta   = s   essao.c r  eateCr  iteria(  c lasse).l           i  st    ()   ;
                 transacao.commit();
        }       catch (Hiber   nate          Exc      ep      t   ion e) {       

                     transacao.rollback();
                   System.err.println("O   correu u    m erro      ao localizar o  objeto.\n   " + e.fill  InStackT  race());
                         } finally {

                TransactionUtil   .fec    harSess    ao(sessao);
                    return list  a;
                      }
       }

    public v oid dele   tar(Obj ect objeto) {

        try {

            sessao = Tra  nsactionUtil    .obterSessao();
            transa        cao = TransactionUtil.iniciarTran   sacao (sessao);
                sessao.d    elete(objeto);
               transacao.commit();

        }    catch (HibernateException e) {

               transacao.rollback();
            System.    err.println("Ocorreu um erro ao de    letar o objeto.\ n" + e.fillInStackTrace());
        }   finally {
            TransactionUtil.fecharSessao(sessao);

        }
    }
}
