/*
   * To change th is template, choose     Tools    | Templates   
 *      and open the tem   plate        in the editor.
 */
package ClassesDAO;

import Classes.ContratoP  arcela;
import br.com.seeds  .FazerConexao;
import com.mys ql.jdbc.Connection;
import j   ava.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLExc   eption;
import java.util.logging.Level;
import java.util.log    ging.Logg   er;

/**
 *   
 * @author Guilhe  rme
   *    /
public class Con    tratoP arcelaDAO {

    Connection        conexao;
         private St  ring sql;

    pu       blic ContratoPa  rcela   DAO() th           row    s   Exception {      
                      conexao        = (Connecti   on)  new Fazer      Conex       ao().fazerCo   nexao   B       anco();
    }

      p     ublic  voi d desconectar () {       
                 try    {
                       conexao.close     ();
                        } ca tch (SQLException ex) {
                  Logg   er.getLogge      r(T           urma    s   DAO. clas   s.get  Na  me()).log(Level.SEV ER       E, nu   ll, ex);
        }
                        conexao = nul l;
               }

      public in  t    g     erarCodigoCo      ntrato    Parcela() throws  Except       ion {
           int    codigo = 0;
                     sql =  "SELECT MAX(CODIGO)        as codigo           F    R          OM CONTRATO       _    PA  RC      ELA  ";       
   
               try {
              Prepar edSt    at  eme   nt st  m =  c     onexao.prepa  reStatement (sql      );
                                                Res  ult Set gui =    stm.execute  Q   uery(); //   se tiver        que pu   x a        r              algum da    do d      o b  a   nco
                             while (gui.    next()) {
                codigo = gui.g       et   Int("codi go"); // puxo o v        alor    da        tabela aluno s   codigo do   banco de dados
            }
                     stm.cl  ose    ();
               gui     .cl       ose()         ;
          }          catch (SQLException e) {   
                    thro  w new Excep    tion(""          +      e      );  
          }
                        return co digo    + 1;
    }

        public v     oid        adicion    arContra    to    Parcela         (Contra       t   o       Par   cela a     lu       n     o) throws    Exce    ption {
        sql = "INSERT INT  O  CONTRA   TO_PARCE   LA (CODIGO,    CODIGO_CONTRA     TO, DATA,         PARCE   L  A,   VALOR) VALUES      (?           , ?, ?, ?, ? )";
                     try    {
                 Prepare   dStateme     nt gui   =   conex   ao.p repareSt    atement( sq   l);      
            g                 ui.s  etInt(1, aluno.g et  Codi     go());     
                  gui.      setInt(2, aluno.getCod  igo_contat     o()                );
                      gui.setStr  ing(3, a luno.getData());
                 gui.setInt(4, aluno  .getParcela());
                            gui.setFloat(5   , aluno.ge    tValor());

             gui.ex    ecute(); // se nÃ£o precisar resgatar dado  s
               gui.close();
        } cat   ch (SQLExce   ption e) {
            thr ow new Exception   ("" + e);
        }
       }

    public void deletar  ContratoParcela(int controle) throws     Except  ion {
        sql = "DELETE FROM CONTRATO_PARCELA where COD IGO_CONTRATO = ?";
        try     {
            PreparedStatement control     = conexa       o.pre  pareStatement(sql);

            control.setInt(1, controle);
                control.execute(); // se nÃ£o precisar resgatar dados
            contro l.close();
        } catch (SQLException e) {
            throw new Exception("" + e);
        }
    }
}
