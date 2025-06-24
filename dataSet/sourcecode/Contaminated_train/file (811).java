/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassesDAO;

import Classes.ContasR;
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
public class ContasRDAO {

    Connection conexao;
    private String sql;
    static ResultSet rs;

    public ContasRDAO() throws Exception {
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

    public int gerarCodigoContaR() throws Exception {
        int codigo = 0;
        sql = "SELECT MAX(CODIGO) as codigo FROM CONTAS_RECEBER_CONTRATO";

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

    public void adicionarContaR(ContasR conta) throws Exception {
        sql = "INSERT INTO CONTAS_RECEBER_CONTRATO (CODIGO, CODIGO_CLIENTE, CODIGO_TURMA, PARCELA, DATA_EMISSAO,"
                + " DATA_PAGAMENTO,  DESCONTO, VALOR,"
                + " TOTAL, VALOR_PAGO, JUROS, CODIGO_CONTRATO, LOCALIDADE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?, ?, ? )";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setInt(1, conta.getCodigo());
            gui.setInt(2, conta.getCodigo_cliente());
            gui.setInt(3, conta.getCodigo_turma());
            gui.setInt(4, conta.getParcela());
            gui.setString(5, conta.getData_emissao());
            gui.setString(6, conta.getData_pagamento());
            gui.setDouble(7, conta.getDesconto());
            gui.setDouble(8, conta.getValor());
            gui.setDouble(9, conta.getTotal());
            gui.setDouble(10, conta.getValor_pago());
            gui.setDouble(11, conta.getJuros());
            gui.setInt(12, conta.getCodigo_contrato());
            gui.setInt(13, conta.getLocalidade());

            gui.execute(); // se não precisar resgatar dados
            gui.close();
        } catch (SQLException e) {
            throw new Exception("" + e);
        }
    }

    public List<ContasR> pesquisacontasRe(StringBuffer pes, StringBuffer pesCpf) throws Exception {
        char aspas = '"';
        String sql;

        //sql = "Select * from contas_receber_contrato where data_pagamento =%" + pesCpf + "%";
        //sql = "Select * from contas_receber_contrato where data_pagamento like " + aspas + "%" + pes + "%" + aspas + "";
        sql = "Select * from contas_receber_contrato where data_pagamento like " + aspas + "%" + pes + "%" + aspas + "|| data_pagamento"
                + " like" + aspas + "%" + pesCpf + "%" + aspas;

        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            ResultSet banco = gui.executeQuery();

            List<ContasR> lista = new ArrayList<ContasR>();
            while (banco.next()) // só entra se existir 
            {
                ContasR despesas = new ContasR();
                despesas.setCodigo(banco.getInt("codigo"));
                despesas.setCodigo_cliente(banco.getInt("codigo_cliente"));
                despesas.setCodigo_turma(banco.getInt("codigo_turma"));
                despesas.setParcela(banco.getInt("parcela"));
                despesas.setData_emissao(banco.getString("data_emissao"));
                despesas.setData_pagamento(banco.getString("data_pagamento"));
                despesas.setDesconto(banco.getDouble("desconto"));
                despesas.setValor(banco.getDouble("valor"));
                despesas.setTotal(banco.getDouble("total"));
                despesas.setValor_pago(banco.getDouble("valor_pago"));
                despesas.setJuros(banco.getDouble("juros"));
                despesas.setCodigo_contrato(banco.getInt("codigo_contrato"));
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

    public List<ContasR> pesquisacontasReFiltro(String pes, String pesCpf) throws Exception {
        char aspas = '"';
        String sql;
        //if (pes.equals("") || pes == null) {// || pesCpf.equals("")) {
        //  sql = "Select * from contas_receber_contrato";
        //} else {
        //JOptionPane.showMessageDialog(null, pesCpf +" Contratos aqui");
        sql = "Select * from contas_receber_contrato where codigo_contrato =" + pesCpf + "";
        //}


        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            ResultSet banco = gui.executeQuery();

            List<ContasR> lista = new ArrayList<ContasR>();
            while (banco.next()) // só entra se existir 
            {
                ContasR despesas = new ContasR();
                despesas.setCodigo(banco.getInt("codigo"));
                despesas.setCodigo_cliente(banco.getInt("codigo_cliente"));
                despesas.setCodigo_turma(banco.getInt("codigo_turma"));
                despesas.setParcela(banco.getInt("parcela"));
                despesas.setData_emissao(banco.getString("data_emissao"));
                despesas.setData_pagamento(banco.getString("data_pagamento"));
                despesas.setDesconto(banco.getDouble("desconto"));
                despesas.setValor(banco.getDouble("valor"));
                despesas.setTotal(banco.getDouble("total"));
                despesas.setValor_pago(banco.getDouble("valor_pago"));
                despesas.setJuros(banco.getDouble("juros"));
                despesas.setCodigo_contrato(banco.getInt("codigo_contrato"));
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

    public ContasR carregarContasContratoPeloCodigo(StringBuffer nome) throws Exception {
        ContasR despesas = new ContasR();
        despesas.setData_emissao("nulo");
        sql = "SELECT * FROM CONTAS_RECEBER_CONTRATO WHERE CODIGO = ?";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, nome.toString());
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                despesas.setCodigo(banco.getInt("codigo"));
                despesas.setCodigo_cliente(banco.getInt("codigo_cliente"));
                despesas.setCodigo_turma(banco.getInt("codigo_turma"));
                despesas.setParcela(banco.getInt("parcela"));
                despesas.setData_emissao(banco.getString("data_emissao"));
                despesas.setData_pagamento(banco.getString("data_pagamento"));
                despesas.setDesconto(banco.getDouble("desconto"));
                despesas.setValor(banco.getDouble("valor"));
                despesas.setTotal(banco.getDouble("total"));
                despesas.setValor_pago(banco.getDouble("valor_pago"));
                despesas.setJuros(banco.getDouble("juros"));
                despesas.setCodigo_contrato(banco.getInt("codigo_contrato"));
                despesas.setLocalidade(banco.getInt("LOCALIDADE"));
            }
            gui.close();
            banco.close();
            return despesas;
        } catch (SQLException e) {
            throw new Exception("" + e);
        }
    }

    public ContasR carregarContasContratoPeloCodigoParcelaUm(StringBuffer nome) throws Exception {
        ContasR despesas = new ContasR();
        despesas.setData_emissao("nulo");
        sql = "SELECT * FROM CONTRATO_PARCELA WHERE CODIGO_CONTRATO = ? AND PARCELA = 1";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, nome.toString());
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                despesas.setCodigo(banco.getInt("codigo"));
                //despesas.setCodigo_cliente(banco.getInt("codigo_cliente"));
                //despesas.setCodigo_turma(banco.getInt("codigo_turma"));
                //despesas.setParcela(banco.getInt("parcela"));
                //despesas.setData_emissao(banco.getString("data_emissao"));
                despesas.setData_pagamento(banco.getString("data"));
                //despesas.setDesconto(banco.getDouble("desconto"));
                //despesas.setValor(banco.getDouble("valor"));
                //despesas.setTotal(banco.getDouble("total"));
                //despesas.setValor_pago(banco.getDouble("valor_pago"));
                //despesas.setJuros(banco.getDouble("juros"));
                //despesas.setCodigo_contrato(banco.getInt("codigo_contrato"));
                //despesas.setLocalidade(banco.getInt("LOCALIDADE"));
            }
            gui.close();
            banco.close();
            return despesas;
        } catch (SQLException e) {
            throw new Exception("" + e);
        }
    }

    public void atualizaDados(ContasR cliente) throws Exception {
        sql = "UPDATE CONTAS_RECEBER_CONTRATO SET VALOR_PAGO = ?, DESCONTO = ?,"
                + "DATA_PAGAMENTO = ?, TOTAL = ? , DATA_EMISSAO = ? WHERE CODIGO = ? ";
        try {
            PreparedStatement guiAlterar = conexao.prepareStatement(sql);
            guiAlterar.setDouble(1, cliente.getValor_pago());
            guiAlterar.setDouble(2, cliente.getDesconto());
            guiAlterar.setString(3, cliente.getData_pagamento());
            guiAlterar.setDouble(4, cliente.getTotal());
            guiAlterar.setString(5, cliente.getData_emissao());
            guiAlterar.setInt(6, cliente.getCodigo());

            guiAlterar.execute();
            guiAlterar.close();
        } catch (SQLException e) {
            throw new Exception("" + e);
        }
    }

    public void atualizaDadosCliente(ContasR cliente) throws Exception {
        sql = "UPDATE CONTAS_RECEBER_CONTRATO SET CODIGO_CLIENTE = ?,"
                + " LOCALIDADE = ? WHERE CODIGO_CONTRATO = ? ";
        try {
            PreparedStatement guiAlterar = conexao.prepareStatement(sql);
            guiAlterar.setInt(1, cliente.getCodigo_cliente());
            guiAlterar.setInt(2, cliente.getLocalidade());
            guiAlterar.setInt(3, cliente.getCodigo_contrato());

            guiAlterar.execute();
            guiAlterar.close();
        } catch (SQLException e) {
            throw new Exception("" + e);
        }
    }

    public ResultSet SelecionandoConta(int Codigo) throws BancoException {
        sql = "SELECT * FROM contas_receber_contrato where Codigo='" + Codigo + "'";
        try {
            rs = conexao.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
        return rs;
    }

    public void deletarParcelasContrato(int controle) throws BancoException {
        sql = "DELETE FROM CONTAS_RECEBER_CONTRATO where CODIGO_CONTRATO = ?";
        try {
            PreparedStatement control = conexao.prepareStatement(sql);

            control.setInt(1, controle);

            control.execute(); // se não precisar resgatar dados
            control.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }
}
