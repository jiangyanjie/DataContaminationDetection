/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uniriotec.dasbuch.dao;

import br.uniriotec.dasbuch.entity.Cliente;
import br.uniriotec.dasbuch.entity.Endereco;
import br.uniriotec.dasbuch.entity.Livro;
import br.uniriotec.dasbuch.entity.ReciboTransporte;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniel Gribel
 */
public class DasbuchDAO {
    public static String dbUrl = "jdbc:postgresql://localhost:5432/dasbuch";
    public static Connection conn = null;
    
    @SuppressWarnings("UseSpecificCatch")
    private static void abrirConexao() {
        try {
            Class.forName("org.postgresql.Driver").newInstance();
            conn = DriverManager.getConnection(dbUrl, "postgres", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void fecharConexao() {
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void persistir(ReciboTransporte reciboTransporte, Livro livro, int livrariaId, String notaFiscal) {
        abrirConexao();
        Integer idEnderecoRetirada = checarExistenciaEndereco(reciboTransporte.getEnderecoRetirada());
        if(idEnderecoRetirada != null) {
            reciboTransporte.getEnderecoRetirada().setId(idEnderecoRetirada);
        }
        Integer idEnderecoEntrega = checarExistenciaEndereco(reciboTransporte.getEnderecoEntrega());
        if(idEnderecoEntrega != null) {
            reciboTransporte.getEnderecoEntrega().setId(idEnderecoEntrega);
            reciboTransporte.getCliente().setEndereco(reciboTransporte.getEnderecoEntrega());
        }
        checarExistenciaCliente(reciboTransporte.getCliente());
        checarExistenciaLivro(livro);
        Integer idPedidoTransporte = persistirPedidoTransporte(reciboTransporte, livro, livrariaId, notaFiscal);
        if(idPedidoTransporte != null) {
            reciboTransporte.setNumeroDoPedidoTransporte(idPedidoTransporte);
        }
        fecharConexao();
    }
    
    private void checarExistenciaCliente(Cliente cliente) {
        List<String> resultado = new ArrayList<String>();
        String cpf = null;
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM cliente WHERE cli_cpf = ?");    
            stmt.setString(1, cliente.getCpf());
            stmt.setMaxRows(1);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                cpf = resultSet.getString("cli_cpf");
                resultado.add(cpf);
            }
            if(resultado.isEmpty()) {
                cpf = persistirCliente(cliente);
            }
            else {
                if(resultSet.getInt("cli_id_endereco") != cliente.getEndereco().getId()) {
                    //@TODO update no endereco do cliente
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void checarExistenciaLivro(Livro livro) {
        List<String> resultado = new ArrayList<String>();
        String isbn = null;
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM livro WHERE liv_isbn = ?");    
            stmt.setString(1, livro.getIsbn());
            stmt.setMaxRows(1);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                isbn = resultSet.getString("liv_isbn");
                resultado.add(isbn);
            }
            if(resultado.isEmpty()) {
                isbn = persistirLivro(livro);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private Integer checarExistenciaEndereco(Endereco endereco) {
        List<Integer> resultado = new ArrayList<Integer>();
        Integer idEndereco = null;
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM endereco WHERE end_id = ?");    
            stmt.setInt(1, endereco.getId());
            stmt.setMaxRows(1);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                idEndereco = resultSet.getInt("end_id");
                resultado.add(idEndereco);
            }
            if(resultado.isEmpty()) {
                idEndereco = persistirEndereco(endereco);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idEndereco;
    }
    
    private String persistirCliente(Cliente cliente) {
        ResultSet rs = null;
        String cpf = null;
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO cliente("
                    + "cli_nome, "
                    + "cli_cpf, "
                    + "cli_id_endereco, "
                    + "cli_email, "
                    + "cli_telefone)"
                    + "VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setInt(3, cliente.getEndereco().getId());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getTelefone());
            
            stmt.executeUpdate();
            
            rs = stmt.getGeneratedKeys();
            if(rs != null && rs.next()) {
                cpf = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cpf;
    }
    
    private String persistirLivro(Livro livro) {
        ResultSet rs = null;
        String isbn = null;
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO livro("
                    + "liv_isbn, "
                    + "liv_titulo, "
                    + "liv_comprimento, "
                    + "liv_largura, "
                    + "liv_altura, "
                    + "liv_peso, "
                    + "liv_editora)"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            
            stmt.setString(1, livro.getIsbn());
            stmt.setString(2, livro.getTitulo());
            stmt.setDouble(3, livro.getComprimento());
            stmt.setDouble(4, livro.getLargura());
            stmt.setDouble(5, livro.getAltura());
            stmt.setDouble(6, livro.getPeso());
            stmt.setString(7, livro.getEditora());
            
            stmt.executeUpdate();
            
            rs = stmt.getGeneratedKeys();
            if(rs != null && rs.next()) {
                isbn = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isbn;
    }
    
    private int persistirEndereco(Endereco endereco) {
        ResultSet rs = null;
        int idEndereco = -1;
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO endereco("
                    + "end_rua, "
                    + "end_numero, "
                    + "end_complemento, "
                    + "end_bairro, "
                    + "end_cidade, "
                    + "end_estado, "
                    + "end_cep)"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            
            stmt.setString(1, endereco.getLogradouro());
            stmt.setString(2, endereco.getNumero());
            stmt.setString(3, endereco.getComplemento());
            stmt.setString(4, endereco.getBairro());
            stmt.setString(5, endereco.getCidade());
            stmt.setString(6, endereco.getEstado());
            stmt.setString(7, endereco.getCep());
            
            stmt.executeUpdate();
            
            rs = stmt.getGeneratedKeys();
            if(rs != null && rs.next()) {
                idEndereco = rs.getInt(1);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idEndereco;
    }  
    
    private Integer persistirPedidoTransporte(ReciboTransporte reciboTransporte, Livro livro, int livrariaId, String notaFiscal) {
        ResultSet rs = null;
        Integer idTransporte = null;
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO pedido_transporte("
                    + "ped_id_nota_fiscal, "
                    + "ped_isbn, "
                    + "ped_endereco_retirada, "
                    + "ped_endereco_entrega, "
                    + "ped_data_registro, "
                    + "ped_data_retirada, "
                    + "ped_data_entrega, "
                    + "ped_custo, "
                    + "ped_id_livraria, "
                    + "ped_id_pedido_livraria, "
                    + "ped_cpf) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            
            stmt.setString(1, notaFiscal);
            stmt.setString(2, livro.getIsbn());
            stmt.setInt(3, reciboTransporte.getEnderecoRetirada().getId());
            stmt.setInt(4, reciboTransporte.getEnderecoEntrega().getId());
            stmt.setDate(5, new Date(reciboTransporte.getDataRegistro().getTime()));
            stmt.setDate(6, new Date(reciboTransporte.getDataRetirada().getTime()));
            stmt.setDate(7, new Date(reciboTransporte.getDataEntrega().getTime()));
            stmt.setDouble(8, reciboTransporte.getCusto());
            stmt.setInt(9, livrariaId);
            stmt.setString(10, reciboTransporte.getNumeroDoPedidoCliente());
            stmt.setString(11, reciboTransporte.getCliente().getCpf());
            
            stmt.executeUpdate();
            
            rs = stmt.getGeneratedKeys();
            if(rs != null && rs.next()) {
                idTransporte = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idTransporte;
    }
    
}
