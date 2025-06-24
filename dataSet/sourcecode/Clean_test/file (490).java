/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassesDAO;

import Classes.Contrato;
import Excessoes.BancoException;
import br.com.seeds.FazerConexao;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Guilherme
 */
public class ContratoDAO {

    Connection conexao;
    private String sql;

    public ContratoDAO() throws Exception {
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

    public int gerarCodigoContrato() throws Exception {
        int codigo = 0;
        sql = "SELECT MAX(CODIGO) as codigo FROM CONTRATO";

        try {
            PreparedStatement stm = conexao.prepareStatement(sql);
            ResultSet gui = stm.executeQuery(); // se tiver que puxar algum dado do banco
            while (gui.next()) {
                codigo = gui.getInt("codigo"); // puxo o valor da tabela alunos codigo do banco de dados
            }
            gui.close();
            stm.close();
        } catch (SQLException e) {
            throw new Exception("" + e);
        }
        return codigo + 1;
    }

    public void adicionarContrato(Contrato aluno) throws Exception {
        sql = "INSERT INTO CONTRATO (CODIGO, CODIGO_ALUNO, LOCALIDADE) VALUES (?, ?, ?)";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setInt(1, aluno.getCodigo());
            gui.setInt(2, aluno.getCodigo_aluno());
            gui.setInt(3, aluno.getLocalidade());

            gui.execute(); // se não precisar resgatar dados
            gui.close();
        } catch (SQLException e) {
            throw new Exception("" + e);
        }
    }

    public Contrato carregarContratoPeloCodigo(String nome) throws BancoException {
        Contrato contract = new Contrato();
        //contract.setLocalidade("nulo");
        sql = "SELECT * FROM CONTRATO WHERE CODIGO = ?";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, nome.toString());
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                contract.setCodigo(banco.getInt("codigo"));
                contract.setCodigo_aluno(banco.getInt("codigo_aluno"));
                contract.setLocalidade(banco.getInt("localidade"));

            }
            gui.close();
            banco.close();
            return contract;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public List<Contrato> pesquisaContrato(StringBuffer pes, StringBuffer pesCpf) throws BancoException {
        char aspas = '"';
        String sql;
        if (!pes.equals("")) {// || pesCpf.equals("")) {
            //if (pes.equals("") || pes == null) {// || pesCpf.equals("")) {
            sql = "Select * from contrato";
        } else {
            sql = "Select * from contrato where codigo like " + aspas + "%" + pes + "%" + aspas + "and codigo_aluno"
                    + " like" + aspas + "%" + pesCpf + "%" + aspas;
        }
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            ResultSet banco = gui.executeQuery();

            List<Contrato> lista = new ArrayList<Contrato>();
            while (banco.next()) // só entra se existir 
            {
                Contrato fornecer = new Contrato();
                fornecer.setCodigo(banco.getInt("codigo"));
                fornecer.setCodigo_aluno(banco.getInt("codigo_aluno"));
                fornecer.setLocalidade(banco.getInt("localidade"));

                lista.add(fornecer);
            }
            banco.close();
            gui.close();
            return lista;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public List<Contrato> pesquisacontasReFiltroContrato(String pes, String pesCpf) throws Exception {
        char aspas = '"';
        String sql;
        sql = "Select * from contrato where codigo_aluno =" + pesCpf + "";

        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            ResultSet banco = gui.executeQuery();

            List<Contrato> lista = new ArrayList<Contrato>();
            while (banco.next()) // só entra se existir 
            {
                Contrato despesas = new Contrato();
                despesas.setCodigo(banco.getInt("codigo"));
                despesas.setCodigo_aluno(banco.getInt("codigo_aluno"));
                despesas.setLocalidade(banco.getInt("LOCALIDADE"));
                lista.add(despesas);
            }
            banco.close();
            gui.close();
            return lista;
        } catch (SQLException e) {
            throw new Exception("" + e);
        }
    }

    public void deletarContrato(Contrato controle) throws BancoException {
        sql = "DELETE FROM CONTRATO where CODIGO = ?";
        try {
            PreparedStatement control = conexao.prepareStatement(sql);

            control.setInt(1, controle.getCodigo());

            control.execute(); // se não precisar resgatar dados
            control.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }
}
