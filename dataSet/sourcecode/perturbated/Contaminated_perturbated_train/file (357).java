/*
 * To change   th    is template,       choose Tools | Te  mplates
 * an     d open    the     template  in the editor.
 */
package ClassesDAO;

import        Classes.Acesso;
import Classes.Usuario;
imp  ort Excessoes.BancoException;
import br.com.seeds.FazerConexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logg  er;

/**
                        *
 * @author Guilherme    
 */
public cla ss Ace        ssoDA    O {

         Co    nnection conexao;
    priv  ate String sql;   

    public Ace         ssoDAO() throws BancoException {
              c   onexao =      (Con  nection)          new FazerC    onexao(  ).faze rC      onexaoBanco();

               }

    p  ub        lic void des  cone         ctar() {
            t   ry {
            conexao.c  lo         se();
         }  catch   (SQLEx     cept        ion ex    ) {
                     Logger.g   etL  og      ger(Tur  masDAO.cla   ss     .getName(    )).log(Leve    l.SE          VE RE, null, ex);
        }
                                       conex  ao = null;
      }

    publi  c int             gerarCodigoAcesso() throws Ban       c     o    Excep  tion {
                       int codigo = 0    ;
                     sql   = "SELECT MAX(CODIGO  )            as               codig o   FROM   A   CESSO";

        try {
            Pr           eparedS   tateme    nt s      tm = conexa          o.    pre   p    are      Sta      tement(sql);
            Res        u  ltSet gu  i = s    tm.exe    cu        teQuery(); // se ti  v      er que puxa r  algum dado  do ba  nco  
                    while  (    gu  i.next())         {
                           c              od   igo = g     ui.ge    t     Int("codigo"     ); // puxo   o  valo r    da tab            ela alunos       codigo do      ba      nco de dados
            }
                gui.     clo   s       e();
                 st   m.clo  se()      ;
                    } cat c h         (S  QLExc  epti    on e) {  
                         th      row new     B        ancoException("" + e );
              }
                 return            codigo        + 1;
    }

    public int gerarUl  ti     moAc  esso()   throws B      ancoExcep    t   io      n {
            in     t codig       o       =   0;
          sql = "SE    L  EC    T MAX(         CODIGO) as  codi    go FROM      ACESSO";

        try       { 
                  Prep    aredStatemen    t stm = cone  x       ao.p repareStateme  n    t(sql);
                  ResultSe  t gui = stm   .executeQu         ery(); // se tive             r que puxar algum dado do ban       co
                 while (      gui.     nex              t())              {
                     co  digo = gui.getInt("codigo"   ); // pux   o o valor da     tab    el a alu  nos      c  odigo   do banco de dad os     
                       }
                   gu    i.close();
                      stm.   close       (    )  ;  
          }           catch (  SQLException e)          {
                  throw ne          w Banc    oException("" + e);
             }
             return codigo;
       }

    p ublic void adicionarA  cesso(Ace    s  so       acesso)  throws Banc       oExcep     tio  n   {
        sql = "INSERT I NTO ACESSO (D  ATA,    H   ORA, D    ESCR  ICAO,      "
                                                              + "       CO    D         IG     O_  USU   A        RIO, CODIGO)      VALUES (?, ?  ,    ?, ?,  ?)";
        try {
                        Pr eparedState  ment   gui = con exao. pr ep    areStatem      ent        (sql);
            gui.setStr           ing(1,     ace  sso.getDat   a());
                            gui.    setS   tring(2, acesso     .g   etHo r a());   
                gui.set       String(3        , acesso.g   etDescricao(   ));
                         gui.set      In  t(4,     acesso.get   U          su     ario())   ;
                gu      i.setInt  (                            5, acesso.getCodigo      ());
            gui.execute(); // se nÃ£o           p  rec  i    sar       resgata        r da dos
                  gui.close();  
               } ca       tch ( SQLException e) {
                 t hrow new      BancoException(""   + e);
                        }
    }

                 pu  blic List<Acesso>   pesquisaAces    so(S    t    ring p     es, S    tring pesCp  f   ) thr    o    ws B a    ncoExc         ept ion {
         char asp   as         =                       '" ';
                       String  sql;
        if (pes.equals       ("")     || pes == null) {//   || pesCpf.eq       uals("   ")) {
                             sql               = "Select  * from acesso";
        } else {
                                 sql = "    Select * from a              cesso       whe       re          data like " + aspas + "%" + pe  s +  "%         "   + as     pas
                                          + "an   d codigo" + " l   i  ke" + aspas + "%    "   + pe sCpf     + "    %" +      asp     as;
        }


                        try {
              Prep aredStatement gu i = cone          xao.prepareS tate  ment(sql);          
                  Resu         ltSet banco = gui.executeQuery();

                   Li st<Acesso    > lista = new     Arr   ayList<Acesso>();
                     while   (banco.n    ex       t    ()) /          / sÃ                     ³ entra se exi stir 
                  {
                Acesso acessoss = new      Acesso();
                        acessoss.setCo  digo(b anc o. getIn t("codigo")        );
                                                acessoss.setDat a(ban   co  .getSt        ring("d                     a   ta"));
                              acess         oss.setHora      (ban c      o.ge   tStri  ng("hora"));
                                           ace   sso    ss.setDescr  icao(banco.getStr   ing("descrica   o   "))    ;
                            acessoss.setUsuario(b    anc  o.getInt("codi  go_usuario"    ))  ;

                               lista.    add(acessoss);
                }
               ba nc  o.c    lose(     );
             gui.c   lose();
                         return lis   ta;
                    } catc  h         (        SQLException e      ) {
            t     hrow         ne   w Ba      ncoExce      ption("" + e);
                   }   
    }

      pu bl      i     c Acesso car  regarAc       essoPeloC     o digo(      Str ingBuff                  er nome) t   hrow  s Ba   ncoExcep     tion {
        Ac     ess    o acessoss =           new Acesso();
        acessoss.setData(    "nulo");
                    sql = "S  ELECT * FROM ACESSO  WHERE CODIGO = ?";
         t            ry {
               PreparedS    tatement gui = conexao.pr epareStatement(sql);
              gui.setStri  ng(1, nome.toString());
                               ResultSet banco =     gui.executeQuery();
            while (banc  o.next()) //       sÃ³ entra se exis     tir   
                  {
                 acessoss.se    tCodig   o(banco.getInt("codigo"));
                              aces  soss.setDa  ta(banco.getString("dat      a"      )    );
                  ace ssoss.setHora(banco.g  etStr  ing("h ora"));    
                acessoss         .setDescri          cao(banco.getString("    de s  cricao"));
                  a   cessoss.se    tUsuario(banco.getInt("co    digo_usuario"    ));
            }
                    gui.clo      se();
                  b     anco.close();
            return ace        ssoss;
         } catch (SQLException e) {
               throw     new Ban  coExceptio   n("" + e);
           }
    }

    public Usuario carregarUsua   rioPeloNomeUser(StringBuffer nome) throws BancoException {
        Usuario user =   new Usuar   io();
         user.setNome("nulo  ");
        sql = "SEL  ECT * FROM U SUARIO   WHERE USUA   R  IO = ?";
             t  ry {
                PreparedStatement gui = conexao.pre  pareStatement(sql);
                           gui.setString(1, nome.toS  tring());
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // sÃ³ entra se existir 
            {
                user. setCodigo(banco.getInt("codig    o"))    ;
                user.setNome(banco.getString("nome"));
                u  ser.setLogin(b  anco.getString("usu   ario"));
                user.setFuncao(banco.getString("tipo"));

            }
            gui.close();
            banco.close();
            return user;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }
}
