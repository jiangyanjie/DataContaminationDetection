/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassesDAO;

import Classes.Acesso;
import Classes.Usuario;
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
 * @author Guilherme
 */
public class AcessoDAO {

    Connection conexao;
    private String sql;

    public AcessoDAO() throws BancoException {
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

    public int gerarCodigoAcesso() throws BancoException {
        int codigo = 0;
        sql = "SELECT MAX(CODIGO) as codigo FROM ACESSO";

        try {
            PreparedStatement stm = conexao.prepareStatement(sql);
            ResultSet gui = stm.executeQuery(); // se tiver que puxar algum dado do banco
            while (gui.next()) {
                codigo = gui.getInt("codigo"); // puxo o valor da tabela alunos codigo do banco de dados
            }
            gui.close();
            stm.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
        return codigo + 1;
    }

    public int gerarUltimoAcesso() throws BancoException {
        int codigo = 0;
        sql = "SELECT MAX(CODIGO) as codigo FROM ACESSO";

        try {
            PreparedStatement stm = conexao.prepareStatement(sql);
            ResultSet gui = stm.executeQuery(); // se tiver que puxar algum dado do banco
            while (gui.next()) {
                codigo = gui.getInt("codigo"); // puxo o valor da tabela alunos codigo do banco de dados
            }
            gui.close();
            stm.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
        return codigo;
    }

    public void adicionarAcesso(Acesso acesso) throws BancoException {
        sql = "INSERT INTO ACESSO (DATA, HORA, DESCRICAO,"
                + "  CODIGO_USUARIO, CODIGO) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, acesso.getData());
            gui.setString(2, acesso.getHora());
            gui.setString(3, acesso.getDescricao());
            gui.setInt(4, acesso.getUsuario());
            gui.setInt(5, acesso.getCodigo());
            gui.execute(); // se não precisar resgatar dados
            gui.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public List<Acesso> pesquisaAcesso(String pes, String pesCpf) throws BancoException {
        char aspas = '"';
        String sql;
        if (pes.equals("") || pes == null) {// || pesCpf.equals("")) {
            sql = "Select * from acesso";
        } else {
            sql = "Select * from acesso where data like " + aspas + "%" + pes + "%" + aspas
                    + "and codigo" + " like" + aspas + "%" + pesCpf + "%" + aspas;
        }


        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            ResultSet banco = gui.executeQuery();

            List<Acesso> lista = new ArrayList<Acesso>();
            while (banco.next()) // só entra se existir 
            {
                Acesso acessoss = new Acesso();
                acessoss.setCodigo(banco.getInt("codigo"));
                acessoss.setData(banco.getString("data"));
                acessoss.setHora(banco.getString("hora"));
                acessoss.setDescricao(banco.getString("descricao"));
                acessoss.setUsuario(banco.getInt("codigo_usuario"));

                lista.add(acessoss);
            }
            banco.close();
            gui.close();
            return lista;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public Acesso carregarAcessoPeloCodigo(StringBuffer nome) throws BancoException {
        Acesso acessoss = new Acesso();
        acessoss.setData("nulo");
        sql = "SELECT * FROM ACESSO WHERE CODIGO = ?";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, nome.toString());
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                acessoss.setCodigo(banco.getInt("codigo"));
                acessoss.setData(banco.getString("data"));
                acessoss.setHora(banco.getString("hora"));
                acessoss.setDescricao(banco.getString("descricao"));
                acessoss.setUsuario(banco.getInt("codigo_usuario"));
            }
            gui.close();
            banco.close();
            return acessoss;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public Usuario carregarUsuarioPeloNomeUser(StringBuffer nome) throws BancoException {
        Usuario user = new Usuario();
        user.setNome("nulo");
        sql = "SELECT * FROM USUARIO WHERE USUARIO = ?";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, nome.toString());
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                user.setCodigo(banco.getInt("codigo"));
                user.setNome(banco.getString("nome"));
                user.setLogin(banco.getString("usuario"));
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
