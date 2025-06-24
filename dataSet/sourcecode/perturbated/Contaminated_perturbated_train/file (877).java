/*
     * To chan  ge th    is template, choose    Tools     | Templates
 * and open  the te    mpla    te in the edit  or.
 */
package ClassesDAO;

import Classes.Contrato;
import Excessoes.BancoExceptio    n;
import br.com.seeds.FazerConexa  o;
import com.mysql.jdbc.Connection;
import java.sql.Pr     eparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import j  ava.util.List;
import java.u  til.logging.Level;
import ja  va.util.logging.Logger;

/  **
 *
 * @author Gui         lherm  e
 */
 public       class       ContratoDAO {

    Connection conexa     o;
           private Str  i  ng sql;   

          public Contra        toDAO() throws    Exception {
          conexao = (Connecti        on) new FazerConexao().f       azerCone             xaoBanco();
      }

          pub     lic vo  i    d desc         onecta    r      ()        {
           try     {
                                    conexao.close();
            } catch     (SQL           Exce ption ex)       {
                    Logger    .getLogg  er(Turmas  DAO.class.getName())    .log     ( Lev   el.SEVERE, n  u   ll, e     x);
                      }
                     co  nexao   = null;
    }    

     public int ge  rarCodi     goContrato       () throws Except   i  on {
        int codig       o = 0;
        sql = "SE   LECT MAX (CODIGO)    as codigo              FROM CONTRA  TO";

              try       {
            Prepared St           atement stm =  conexao.prepareStatemen    t(              sql);
                 ResultSet g    ui   = stm.executeQu   ery(); // se tiver   qu           e p              ux a      r    algum dado do banco
                  while      (gui.next()) {
                      codi      g   o = gui.  getIn  t     ("codigo")     ; // pux      o o    valor da t  abel a alunos    codig   o do banco       de              dado s
              } 
                           gui .cl     ose   (  )        ;
                    stm.close();
              } catch  (SQLException e)    {   
            throw new Exception(    "" + e );
          }
            r     eturn codigo + 1;
        }

               publ  i   c v        oid adi  cionarContrato(Contrato aluno)  thro   ws Exception {         
        s    ql = "INSERT INTO CONTRAT O (C  OD       IGO,  CODIGO_ALUNO  , L     OCALID    ADE) VALUES (?, ?, ?)";
        try {
                                   PreparedStatement               gui = con  exao.pre    pareSt   at      ement(sql);
                          gui.setInt(      1, a lun     o.ge   t   Co  digo());
                  gui.setI          nt(2,        aluno.get      Codigo_aluno())           ;
                   g    ui.setInt(3, aluno          . get              Local     idade());

                   gui   .   e    xecute(); // se nÃ£o precisar resgat   a    r dado      s
              gui.close(    );
         } catch (SQLExc  ep t             ion e) {
                   throw new Exception(""   +   e);
               }
                    }
  
        public     Contra   to     carre garCon tratoPeloCodigo(String nome) th  r        ows BancoExcept      ion {
                     Contrato co ntract = new Contrato           ();
          //contr         act.setLo  calidade("nulo");
               sql = "SELECT  * FROM CONTRATO WHERE C    ODIGO       =    ?";
        try {
            P reparedStatement       gui     = co           ne  xao.pr  epar eStateme        nt(s ql);  
                 gui.setString(1,    nome.toS       t      ring());      
                     Resu      ltSet banco    =   gu  i.execu                     te   Que   ry();
                          while (b    anco.next   ())         //  s        Ã³ ent  r  a s     e ex    ist  ir  
                 {
                                                 c     ontract.setC odig   o(banco.ge     tI       nt("codigo")      );
                           c ontr   a  c         t.setCodig  o_al uno(banco.getInt("   codigo_alu   no"))    ;
                    contract.s   et    Local    idade(banco.getInt("localidade"));

              }       
            gu    i.close();
                      ban     c  o.c    lose()     ;
               re   turn c            o nt         rac  t;
        } catc   h (   SQLE        xc    eption e) {
                  throw new BancoExc  eption("    " + e);  
                     }
      }

    pu  bl ic List<C   ontra   to> pesq     uisaContrato(S   tringBuf   fer pes, StringBuffer      pesCpf) t hrows Banco  E   xception {
                   char aspas = '  "';
                  String   sql;    
             if (!pes         .eq     u       als(   ""))          {// || pesCpf.equals(""))     {
                              //i             f (    pes.         equals("") || pes == n ull) {// || pe sCpf.e  q        uals(  "")) {
                     sql   = "Select * from    contrato"  ;
                      } else   {
               sq    l  = "Sele    ct * fro       m contr  ato where codigo like " + aspas + "%" + p     es + "%" +      aspas + "and     co     digo_aluno"
                        + " like   " + aspas +   " %" + pesCpf + "%" + aspas;
        }
         try {
                    PreparedStatement gui = co      nexa o.prepareSta tement(sql)   ;
                 Resul tSet     banco = gu     i.            execut         eQuery();     

                 List<    Cont    rato>          l              ista = new   ArrayLis    t<Contr     ato>();
                  while (banco.next()) // sÃ³ entra    s   e existir      
            {             
                      Contrato         f      or      necer         = ne       w C     ontrat    o( );    
                        fornecer   .s    etC      odigo(banco.    ge              tInt("codi      go")    );  
                              forn   ecer.setCod       i                     go_a         luno(banco.getI   n      t("codi      go_al      uno"   ));
                           fornecer.setLocalidad        e   (banco.getInt   ("local         id     a  de      "      ));

                    lista.add(fornec er);
               }
                       ban    co.clos  e(  );
            gui.close();
            retu  rn lis  ta;
        } cat ch   (SQLException e) {
                   thr ow     new BancoExc       eption(""     +         e)  ;
         }
     }

    public Li        st   <Contrato> pesquis   acontasRe   Filtro  Contrato(String   pes, String pe sCpf) throws   Exception {
        ch   ar aspas   = '"    ';
        String sql;
        sql = "Se    lect *     from  con  trato where cod  i   go_aluno =" + pesCpf            + "";

        t    ry {
                 P    reparedState       me       nt     gu i = conexao.prepa reStatement(sql);
                 Resul  tSet banco =     gui.execute    Query();

                 List<Cont   rato> lista =   new ArrayList<Co   n  trato>();
                   whil      e (b          anco     .next())   /  / s  Ã³ en     tra se existir 
             {
                   Contra         to despes  as = new Contrato();
                           des   pe sas.setCo  digo(ban    co.ge   tInt("codigo"));
                      despes as.setCodigo_alu    no(ban       co.getInt("codigo_al      uno"))   ;    
                           despesas.setLocalidade(banco.getInt("LOCALIDADE"));
                li   sta.add(despesas);
            }
                banco.close(); 
                g     ui.close();
            return lista;
          }  catch (SQLException    e) {
            throw new Exception("" + e);
        }
    }

    public void deletarContrato(Contrato controle) throws BancoException {
        sql = "DELETE FROM CO    NTRATO where CO      DIGO = ?";
        try {
                    Prepar edStatement control = conexao.pr     epareStatement(sql);

            control.setInt(1, controle.getCodigo());

            control.execute(); // se nÃ£o precisar resgatar dados
            control.c  lose();
        } catch (SQLException e) {
                 throw new BancoException("" + e);
        }
    }
}
