/*
 * To change   this templat   e, choose Too   ls | Templ          ates
   * and open the       templa               te in t  he editor.
 */
package ClassesDAO;

import       Classes.ContasPagar;
import Excessoes.BancoException;
import br.com.seeds.FazerConexao;
im    port com.mysql.jdbc      .Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList  ;
import     java.util.List;
import java.util.logging.Level;
import java.util.logging     .Lo g    ger;

/        **
 *
 * @auth   or Gui  lherme
 */
public class ContasPa   garDAO {

    Connection con   exao;
    private Str    ing sql;

    public ContasPagarDAO(         )  throws BancoExcep      tio   n {
        conexao =     (   C onnection) new   FazerConexao().faz  er    ConexaoBanco();
    }      

      public void des   conec     ta r   (    )                 {
                try {
                  c    onexao.clo  s     e   ();    
         }      catch (SQLExc  e       ption ex) {
                Logger.          getLogger(TurmasD    AO   .c  l    ass.ge  tName()).  l   og( Level.SEVERE, null, ex);
           }
        conexao     = null;
    }  

             public void   adicionarContaP(ContasP    agar co  nta)   t         hrows BancoE     x  ce        ption {
                  sql =     "I       NSERT INTO                 CONT   AS      _PAGAR ( DESCRICAO,    E      MISSAO,"
                               + "      VENC       IMENTO,  VALOR,"    
                                  + " STATU     S)       VALUES (      ?, ?, ?,    ?, ? )";
        t  ry {
              Prep   ar  ed         Statement gui =      con   e xao.prepareStatem      ent(sql    );
            gui.se t   S                 t  ri  ng(1, conta.getD   e  scricao() );  
            gui.setString   (  2, co             nta.    getE     m     issao());    
               gui.setStri        ng(3, conta  .g    etVenci     mento());        
                g                   ui.set   Float(4     ,     conta.getValor());
                gui.setInt(5,   cont a     .getSta    tus());

                 gui.execute   (  );    //     se nÃ£o     pre     cisa   r  resgatar dado    s
             gui.clos          e   (  )      ;
        } catch    (SQLExc      epti        on e) {
                      t  hrow n             ew BancoEx    ception    ("" + e);
        }
      }   

    public List<Cont    asPagar> p   esquisa   contas                  Pagar(String pes,                 Str ing pesCpf) thr   ow s Banc    oException       {
        char       asp    as = '"';
                  String         sq l;
        if (pes.equals("") || pes == nul  l) {/    / || p esCpf.      equa  ls("")) {               
               sql = "Se lect * fr   om CONTAS_PAGAR";
        }    e  lse  {
              sql =   "Select   * f     ro m CONTA  S_PA  GAR whe       re      codigo like " + aspas + "%" + pes          + "%" + aspa    s      + "and  s  tatus"
                                 + " like       " + aspas + "%" + pes    Cpf       + "%"       + aspas;
                        }

                                try {
                   PreparedStatemen     t gui =  con   exa  o.prepa   reSta     teme                     nt(sql);
                               ResultSet ban  co =          g     ui.ex      ecuteQuery();

                 List<ContasPaga   r   > lista       =     new A rrayList<Co     nta   s      Pa       gar>(   );
                                  whi  le (banco.next()) //   sÃ  ³ ent     ra               se  existir 
                    {
                           ContasPagar d      esp     e  sas         = ne w Co   nt as            Pagar();
                           despesas.set   Codigo(banco    .ge       tInt      ("codigo") );
                              de     spesas.se    tDescricao(banco.get      St   ring("d   escricao "             ));
                          despes      as.setEmis  sao        (banco.get Stri    n  g("emissao"));
                desp   esas.se       tV e         nc  imento(banco.get   Strin              g("venc   imento"));
                          despesas.setVal    or(banco.getFloat("   val     or                            "));
                         despesas.setStatus(ba  nco.getIn       t("s  ta   tus"))                 ;
                        lista.add(     despesas);
            }    
                     banco.close(           );
                  gui.c   lose();
            return li          sta;
        } ca  tch (S    QLException e) {  
                      throw new BancoExc ep        tion("" + e)                     ;
          }
         }

    public void   atua lizaDados(C      ontasP agar  cliente  ) throws Banc  oException {
           sq l = "UPDATE CONTAS_PAGAR        SET DESC     RICAO = ?,"
                        +            " EMISSAO = ?, VEN CIMENTO = ?   ,   VAL  OR = ?,"
                         + " STATUS = ? WH        ERE COD   IGO     = ? "      ;
                               try {
            PreparedStatement guiA   lterar = conexao.prep       a         reStatement(   s    ql      );        
            guiAlterar.set     Strin      g(1, cliente   .get         D     escricao());
             guiAlterar.setString(2, cliente.getEm        issa  o());
             guiAlterar.setS        t  ring(3, clie nte   .getVencim           ento  ());
              guiAlterar  .setFl      oa           t(4, cliente.getValo  r(     ));
              gu    iAlterar.se       tInt(5        ,    cliente.getStatus());
                gui Alterar.setInt(     6, cliente.getCodigo    ());

                      gu   iAlter          ar.execu     te();
                              guiA lt erar.  close(        )  ;      
                     } catch (       SQLException e   ) {
                              thr   o  w new Ba   ncoEx    ception(""  + e);
          }
       }
         
        public C  o    ntasPagar carregarCon       tasPPeloCodig    o(Strin     g nome )    throws BancoExcepti       on       {   
                     C    ontasPagar contas = n   ew       ContasPagar();
                contas. se   t  Emissao                  ("n ul        o");     
            sql =      "SELECT * F   ROM CONTAS         _ PAGAR WHE   RE CODIG O   = ?"  ;
            try {
                  PreparedState      ment gui = co       n      exa     o.prepa       re  Statement(sql); 
                                                g      ui.setSt  ring(1, nome)  ;   
                              ResultSet banco = gui.   execut            eQuery(   );
              w       hi   l  e (ba       nco.next()) /   / sÃ³    entra se                 exi   stir 
                                           {

                contas.         setCodigo(banco    .getInt    ("c     odigo"));
                        contas.setDescricao(banco       .g     et       String("  descricao"));
                            contas.setEm         issao(banc  o .   getS    tring       ("  emissao"));
                                      cont    as.set    Vencimento(ban    co.        getString("venciment        o"));
                             contas.     setValor(banco.ge tFl         oat("valor"))        ;   
                             contas.setStatus(banco.getI  nt("status") );
                            }
                  gui.close ();
            banco.close();
            return contas;
          } catch (SQLException e) {
                     throw new BancoException("" + e);
        }
    }

    public      void deletarC   ont    asP(  ContasPaga   r   controle) throws BancoE   xcept   ion {
        s    ql   = "   DELETE FR   OM CONTAS_PA        GAR w  he re COD      IGO   =      ?";
                     tr        y {
            Prepa r     edStatemen t con   trol = co    nex ao.    prepareStatement(sql);

                           cont rol.setInt(1,        controle.ge   tC   odigo());

                           co   n  trol.execute();    // se nÃ   £o precisar r   esga       tar dados
              con   trol.    close();
              } catch (S   QLE xception e) {
            throw new Banc          oException("" + e);
           }      
    }

           public        List<C     ontasPagar> pesquisaP(String    pe  s, String   pesC     pf) throws BancoExcepti  on {
           char aspas = '     "';
               St   ring sql;
        if (pes.equals("") |         | pes  == null) {// || pesCpf.equals("")) {
                   sq   l = "Select * fr   om contas_pagar";         
        } else {
            sq    l = "Select * from contas_paga      r where de   scricao like " + aspas   +       "%" + pes + "%" + aspas;
            }

        try {
                 PreparedSt    atement     gui = conexao.pr  epareStatement(sql);
              ResultSet banco      = gu    i.executeQ     uery();

                 List<ContasP   agar> lista    = new Ar     rayList<ContasPagar>();
                 while (banco.next()) // sÃ³ en     tra se existir 
            {
                      ContasPagar   produto = new ContasPagar();
                p  roduto.setCodigo       (b  anco.getInt("codigo")    );
                produto.s  etD   escricao(banco.getString("descricao"));
                produto.setEmissao(      banco.getString("emissao"));
                   produto.setVencimento(banco.getString("venc    imento"));
                produto.set Valor(banco.get  Float("valor"));
                produto.setStatus(banco.getInt("status"));

                lista.add(produto);
            }
             banco.close();
            gui.close();
                  return lista;
        } catch     (SQLException e) {
            throw new BancoException("" + e);
        }
    }
}
