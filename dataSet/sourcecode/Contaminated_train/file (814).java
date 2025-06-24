/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassesDAO;

import Classes.ContasReceber;
import Excessoes.BancoException;
import br.com.seeds.FazerConexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Guilherme Augusto
 */
public class ContasReceberDAO {

    Connection conexao;
    private String sql;

    public ContasReceberDAO() throws BancoException {
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

    public void adicionarContaR(ContasReceber conta) throws BancoException {
        sql = "INSERT INTO CONTAS_RECEBER ( DESCRICAO, EMISSAO,"
                + " VENCIMENTO, VALOR,"
                + " STATUS) VALUES ( ?, ?, ?, ?, ? )";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, conta.getDescricao());
            gui.setString(2, conta.getEmissao());
            gui.setString(3, conta.getVencimento());
            gui.setFloat(4, conta.getValor());
            gui.setInt(5, conta.getStatus());

            gui.execute(); // se não precisar resgatar dados
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public List<ContasReceber> pesquisacontasReceber(String pes, String pesCpf) throws BancoException {
        char aspas = '"';
        String sql;
        if (pes.equals("") || pes == null) {// || pesCpf.equals("")) {
            sql = "Select * from CONTAS_RECEBER";
        } else {
            sql = "Select * from CONTAS_RECEBER where codigo like " + aspas + "%" + pes + "%" + aspas + "and status"
                    + " like" + aspas + "%" + pesCpf + "%" + aspas;
        }

        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            ResultSet banco = gui.executeQuery();

            List<ContasReceber> lista = new ArrayList<ContasReceber>();
            while (banco.next()) // só entra se existir 
            {
                ContasReceber despesas = new ContasReceber();
                despesas.setCodigo(banco.getInt("codigo"));
                despesas.setDescricao(banco.getString("descricao"));
                despesas.setEmissao(banco.getString("emissao"));
                despesas.setVencimento(banco.getString("vencimento"));
                despesas.setValor(banco.getFloat("valor"));
                despesas.setStatus(banco.getInt("status"));
                lista.add(despesas);
            }
            banco.close();
            gui.close();
            return lista;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public void atualizaDados(ContasReceber cliente) throws BancoException {
        sql = "UPDATE CONTAS_RECEBER SET DESCRICAO = ?,"
                + " EMISSAO = ?, VENCIMENTO = ?, VALOR = ?,"
                + " STATUS = ? WHERE CODIGO = ? ";
        try {
            PreparedStatement guiAlterar = conexao.prepareStatement(sql);
            guiAlterar.setString(1, cliente.getDescricao());
            guiAlterar.setString(2, cliente.getEmissao());
            guiAlterar.setString(3, cliente.getVencimento());
            guiAlterar.setFloat(4, cliente.getValor());
            guiAlterar.setInt(5, cliente.getStatus());
            guiAlterar.setInt(6, cliente.getCodigo());

            guiAlterar.execute();
            guiAlterar.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public ContasReceber carregarContasRPeloCodigo(String nome) throws BancoException {
        ContasReceber contas = new ContasReceber();
        contas.setEmissao("nulo");
        sql = "SELECT * FROM CONTAS_RECEBER WHERE CODIGO = ?";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, nome);
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // só entra se existir 
            {

                contas.setCodigo(banco.getInt("codigo"));
                contas.setDescricao(banco.getString("descricao"));
                contas.setEmissao(banco.getString("emissao"));
                contas.setVencimento(banco.getString("vencimento"));
                contas.setValor(banco.getFloat("valor"));
                contas.setStatus(banco.getInt("status"));
            }
            gui.close();
            banco.close();
            return contas;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public void deletarContasR(ContasReceber controle) throws BancoException {
        sql = "DELETE FROM CONTAS_RECEBER where CODIGO = ?";
        try {
            PreparedStatement control = conexao.prepareStatement(sql);

            control.setInt(1, controle.getCodigo());

            control.execute(); // se não precisar resgatar dados
            control.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public List<ContasReceber> pesquisaR(String pes, String pesCpf) throws BancoException {
        char aspas = '"';
        String sql;
        if (pes.equals("") || pes == null) {// || pesCpf.equals("")) {
            sql = "Select * from contas_receber";
        } else {
            sql = "Select * from contas_receber where descricao like " + aspas + "%" + pes + "%" + aspas;
        }


        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            ResultSet banco = gui.executeQuery();

            List<ContasReceber> lista = new ArrayList<ContasReceber>();
            while (banco.next()) // só entra se existir 
            {
                ContasReceber produto = new ContasReceber();
                produto.setCodigo(banco.getInt("codigo"));
                produto.setDescricao(banco.getString("descricao"));
                produto.setEmissao(banco.getString("emissao"));
                produto.setVencimento(banco.getString("vencimento"));
                produto.setValor(banco.getFloat("valor"));
                produto.setStatus(banco.getInt("status"));

                lista.add(produto);
            }
            banco.close();
            gui.close();
            return lista;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }
}
