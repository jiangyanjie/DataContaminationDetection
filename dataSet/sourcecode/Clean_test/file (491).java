/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassesDAO;

import Classes.ContratoParcela;
import br.com.seeds.FazerConexao;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Guilherme
 */
public class ContratoParcelaDAO {

    Connection conexao;
    private String sql;

    public ContratoParcelaDAO() throws Exception {
        conexao = (Connection) new FazerConexao().fazerConexaoBanco();
    }

    public void desconectar() {
        try {
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(TurmasDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        conexao = null;
    }

    public int gerarCodigoContratoParcela() throws Exception {
        int codigo = 0;
        sql = "SELECT MAX(CODIGO) as codigo FROM CONTRATO_PARCELA";

        try {
            PreparedStatement stm = conexao.prepareStatement(sql);
            ResultSet gui = stm.executeQuery(); // se tiver que puxar algum dado do banco
            while (gui.next()) {
                codigo = gui.getInt("codigo"); // puxo o valor da tabela alunos codigo do banco de dados
            }
            stm.close();
            gui.close();
        } catch (SQLException e) {
            throw new Exception("" + e);
        }
        return codigo + 1;
    }

    public void adicionarContratoParcela(ContratoParcela aluno) throws Exception {
        sql = "INSERT INTO CONTRATO_PARCELA (CODIGO, CODIGO_CONTRATO, DATA, PARCELA, VALOR) VALUES (?, ?, ?, ?, ? )";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setInt(1, aluno.getCodigo());
            gui.setInt(2, aluno.getCodigo_contato());
            gui.setString(3, aluno.getData());
            gui.setInt(4, aluno.getParcela());
            gui.setFloat(5, aluno.getValor());

            gui.execute(); // se não precisar resgatar dados
            gui.close();
        } catch (SQLException e) {
            throw new Exception("" + e);
        }
    }

    public void deletarContratoParcela(int controle) throws Exception {
        sql = "DELETE FROM CONTRATO_PARCELA where CODIGO_CONTRATO = ?";
        try {
            PreparedStatement control = conexao.prepareStatement(sql);

            control.setInt(1, controle);
            control.execute(); // se não precisar resgatar dados
            control.close();
        } catch (SQLException e) {
            throw new Exception("" + e);
        }
    }
}
