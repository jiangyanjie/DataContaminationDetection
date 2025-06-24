/*
    * To cha  nge this templa   te,      c  hoose Tools | Templates
 * and op    en the templ  ate in    the edit   or.
 */
 package ClassesDAO;

import Classes.Contrato;
import Excessoes.BancoException;
imp   ort br.com.seeds.FazerCone   xao;
import       com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSe      t;
import java.sql.SQLException;
  import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import           java.util.logging.Logger;

/**
 *
        * @  author Guilherme 
 */
public class ContratoDAO    {

    Connection conexao    ;
             pr   ivate String sql;

    pub  lic Cont ratoDA O() throws Exceptio      n {
         conexao = (Conne  ction) new FazerCo           nexa o().faz e       r       ConexaoBanco()    ;
    }

       pub                     lic voi d desconectar()      {       
            tr    y {   
             conexao.clos    e       ();
          }        catch (SQLE  x   ceptio n      e     x) {
               Logger.getLogger(Tur masDAO.class.ge      t      Name()).    lo    g(Level.S       E VE                        RE, null    ,  ex);
        }
          conexao =    null;
    }

    p   ublic int ger      a        rCodigoContrato   () throw      s Except  ion {    
          in   t codig      o = 0;
                   sql  = "S   ELECT M AX   (      CO  DIGO         ) as codi       go FROM     CO NTRATO";
     
                  try {
                      Prepared  Sta         tement   stm = conexao.pr   epareSt atement(sq     l)        ;
                   ResultSet    gui = stm.exe  cuteQuer      y();    // se tiv       er que p        uxar      al gum dado                                   do    banco
              wh    il      e (gui.                  nex    t()) {
                c   odigo = g   ui    .   getInt("co  dig    o"   ); // puxo o valo r da             t  abela alunos codigo       do ba   nco    de d      ados 
            }  
                          gui.close  ()    ;
                  stm. close();
        } ca tch (SQLExce   ption e    )    {
                  throw new   Excep  tion("" + e);
         }
            retur    n   codigo + 1;
             }
  
             public v     oid adici  ona   r     C  ontr      a     t o(Cont    rato aluno)      t   hrows Ex  c            e       pti             on {
           sql    = "INSERT                IN  TO CONTRATO    (CODIGO, CODIGO_ALUNO, LOCALIDADE    ) VALUES (?, ?, ?)";
                   try {  
                          Pr   ep ared State      me  nt gui      = conexao.p  repare Sta     te    ment(sq  l);
            gui.s      etInt(1,    aluno.getCodigo(      ));
                          g   ui.s  etIn   t(2,    alun            o.getCodigo_aluno());
            gui.se      tInt(3, aluno.getLoca    li      dade          ());

                           gu      i.     ex   ecu   te      (     ); //     se n  Ã£o precis   ar resgatar dados
                 gui  .close();
           } catch (SQLE  x               cept   i        on e    )       {
            t  hr     ow new Excep  tion("" + e);
               }
    }

    public Cont     r     ato ca    rregarCo  ntra     toPe  loCod      igo ( String         n      ome)   th     rows BancoException   {
                                Contrato  co       nt   ract       = n  ew Contrato  ( );
                 //con       tra      ct.setLoc      alida  de("nulo");
                               sql = "   SELEC  T * FROM      CONTRATO W  H   ERE C            O    DIGO = ?";
        tr           y {
                   Prepar   edSta      tement   gui = conexao.prepareStatement(sql     );
                              gui   .  setString(1, nome   .toString());
            Res ultSet ba        nco     = gui.exe cute           Que  ry(  );  
                                       wh ile    (banco.next())  /   / sÃ³ entra    se e     x   is     tir 
              {
                     contra  ct.setC odigo(b  an     co.getInt(    "                    c        odi     go"));
                         contrac      t.setCodigo      _alu       no(banco.getInt("codigo_alun        o    "));
                             contract.setLocalidade(   banco.getIn     t("lo calid ade"      ));   

                       }
                     gu      i.clos     e();
                    banco.                clo           s  e();
             r     etur  n con  tra     ct;
        }   cat    ch (  S   QLEx  cepti     on e) {
                                  th  row new        B  anc     oException(""     + e);
                }
             }

             publ    ic Lis    t<C ont rato> pesquisaCont rato(StringBu    f        fer pes,   Str     ingBuffer pesCpf) t          hrows Ban     c        oExc    epti       on {
        char asp     as    = '"'        ;
        String sql;       
               if (!pes.equals("")) {//   || pe sCpf.equ    als("")) {
                        //if          (p    es  .e   quals("")               || pes ==         null   )    {  // |    | pe s Cpf.equal s(""))      {
                       sql =       "Select       * from contrato";
          } else {
                       s  ql = "  Se     lect * from con        trato where codig o like " +  a spas +   "%" + p    es          + "%" +   a spa s   + "and codigo_al   uno"
                                        + " li    ke"         + aspa       s   + "%" +       p   esCpf +   "  %"     + aspas;
           }
                             try {
                 Prepa  redStatement gui              = conexao     .pr   epa      reStatement(sql);
               ResultSet           banco = gui.e                  xecuteQuery    ();

                  List<C     ontr ato  > lista = new         Arra yList< Contrato>();
                   while (banco        .next(    )) // sÃ³    entra se existir 
                          {
                            Contrato fornece      r   = n    ew    Contrato();
                                 fo     rnecer.setCodi    go(banco.getI  nt(       "codigo"));
                              forn     ecer.setCodi go_aluno(banco.getInt(        "cod igo_aluno"));
                fornecer.setLocalidade(ban  co.getI nt("localidade"));

                                   lista.add(       for      necer);
                 }
                b anco.close();   
            gui.close();
                re   turn list       a;
        }     ca    tch (S QLE  xception e) {
              throw new Banco    Exception("" + e);              
            }
    }

                 public List<Contr   ato> pesquisa   contasReFiltroContrato(String pes, String    pesCpf) throw   s Ex   ception {
             char        asp    as = '          "';
             String sql; 
        sql = "Select * from contrato where codigo_aluno =" + pesCpf + "";
      
        try {
            PreparedStatement gui = conexao     .prepareSta  tement(sql);
                 Resu  ltSet ban  co     = gui.  e   xecuteQuery();

            List<Contrato> l    ista = new     ArrayLi       s      t<C   ontrato>     ();
               whi    le (banco.nex  t()) // sÃ³ entra s  e existi   r 
             {
                    Cont     rato despesas = new      Contrato();
                despesas.setCod igo(ba     n  co  .getInt("codigo"));
                       despesas.setCodigo_aluno(banco.getInt("codigo_alu no"));
                despesas.setLocalidade(b  anco.getInt("LOCALIDADE"));
                lista.add(despesas    );
               }
            ba nco.close();
            gui.close();
            return lista;
        } catch (SQLEx   ce    ption e) {
            throw new Exception("" + e);
        }     
    }

       public void deletarContra    t    o(Contrato controle) throws BancoException {
        sql = "DELETE FROM CO  NTRATO where CODIGO = ?";
        try {
            PreparedStatem   ent control = conexao.prepareStatement(sql);

            control.setInt(1, controle.getCodigo());

              control.execute(); // se nÃ£o precisar resgatar dados
            control.clo  se();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }
}
