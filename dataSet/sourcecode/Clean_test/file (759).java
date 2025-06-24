package com.gmail.andersoninfonet.dao;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import com.gmail.andersoninfonet.conexao.ConnectionFactory;
import com.gmail.andersoninfonet.modelo.Cera;
import com.gmail.andersoninfonet.modelo.Limpeza;


public class Cq8900DAO {

	private Connection con;
	private Statement stm;

	
	public void conectar(){
		try {
			con = new ConnectionFactory().getConnection();
			stm = con.createStatement();
			System.out.println("conectado");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void fechar(){
		try {
			stm.close();
			con.close();
			System.out.println("desconectado");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public List<Cera> getListaCera(){
		conectar();
		try{
		
		ResultSet result = stm.executeQuery("select c_id, modelo, cor, quantidade " + " from cera where modelo='XEROX 8900'");
		
		List<Cera> lista = new ArrayList<Cera>();
		
			while(result.next()){
				Cera temp = new Cera();
				
				temp.setcId(result.getLong("c_id"));
				temp.setModelo(result.getString("modelo"));
				temp.setCor(result.getString("cor"));
				temp.setQuantidade(result.getLong("quantidade"));
			
				lista.add(temp);
			}
		
		result.close();
		
		return lista;
		
		}catch(SQLException e){
			throw new RuntimeException(e);
		}finally{
			fechar();
		}
	}
	
	public void atualizarCera(Cera cer){
		conectar();
		System.out.println(cer.getCor() + "dao1");
		try {
			stm.executeUpdate("update cera set quantidade='" + cer.getQuantidade() + "' where c_id='" + cer.getcId() + "';");
			System.out.println(cer.getCor() + "dao2");
			System.out.println("editado ...");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally{
			fechar();
		}
		
	}
	
	
	public List<Limpeza> getListaLimpeza(){
		conectar();
		try{
		
		ResultSet result = stm.executeQuery("select l_id, modelo, patrimonio, serie, situacao " + " from limpeza where situacao='NOVO' and modelo='XEROX 8900'");
		
		List<Limpeza> lista = new ArrayList<Limpeza>();
		
			while(result.next()){
				Limpeza temp = new Limpeza();
				
				temp.setlId(result.getLong("l_id"));
				temp.setModelo(result.getString("modelo"));
				temp.setPatrimonio(result.getString("patrimonio"));
				temp.setSerie(result.getString("serie"));
				temp.setSituacao(result.getString("situacao"));
			
				lista.add(temp);
			}
		
		result.close();
		
		return lista;
		
		}catch(SQLException e){
			throw new RuntimeException(e);
		}finally{
			fechar();
		}
	}
	
	public List<Limpeza> getListaLimpezaUso(){
		conectar();
		try{
		
		ResultSet result = stm.executeQuery("select l_id, modelo, patrimonio, serie, situacao " + " from limpeza where situacao='EM USO' and modelo='XEROX 8900'");
		
		List<Limpeza> lista = new ArrayList<Limpeza>();
		
			while(result.next()){
				Limpeza temp = new Limpeza();
				
				temp.setlId(result.getLong("l_id"));
				temp.setModelo(result.getString("modelo"));
				temp.setPatrimonio(result.getString("patrimonio"));
				temp.setSerie(result.getString("serie"));
				temp.setSituacao(result.getString("situacao"));
			
				lista.add(temp);
			}
		
		result.close();
		
		return lista;
		
		}catch(SQLException e){
			throw new RuntimeException(e);
		}finally{
			fechar();
		}
	}
	
	public List<Limpeza> getListaLimpezaUsado(){
		conectar();
		try{
		
		ResultSet result = stm.executeQuery("select l_id, modelo, patrimonio, serie, situacao " + " from limpeza where situacao='USADO' and modelo='XEROX 8900'");
		
		List<Limpeza> lista = new ArrayList<Limpeza>();
		
			while(result.next()){
				Limpeza temp = new Limpeza();
				
				temp.setlId(result.getLong("l_id"));
				temp.setModelo(result.getString("modelo"));
				temp.setPatrimonio(result.getString("patrimonio"));
				temp.setSerie(result.getString("serie"));
				temp.setSituacao(result.getString("situacao"));
			
				lista.add(temp);
			}
		
		result.close();
		
		return lista;
		
		}catch(SQLException e){
			throw new RuntimeException(e);
		}finally{
			fechar();
		}
	}
	
	public void adicionaLimpeza(Limpeza lim){
		conectar();
		System.out.println(lim.getPatrimonio().toUpperCase());
		
		try{
			
			stm.execute("insert into limpeza (modelo,patrimonio, serie, situacao) values ('" + lim.getModelo().toUpperCase() + "','" + lim.getPatrimonio().toUpperCase() + "','" + lim.getSerie().toUpperCase() + "','" 
					  + lim.getSituacao().toUpperCase() + "')");
			System.out.println(lim.getPatrimonio().toUpperCase());
		}catch(SQLException e){
			throw new RuntimeException(e);
		}finally{
			fechar();
		}
	
	}
	
	public void exlcuir(long lId){
		conectar();
		System.out.println(lId + "1");
		System.out.println("tentando exlcuir...");
		try {
			
			System.out.println(lId + "2");
			stm.execute("delete from limpeza where l_id='" + lId +"';");
			System.out.println(lId + "3");
			System.out.println("excluido");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}finally{
			fechar();
		}
	}
	
	public void atualizar(Limpeza lim){
		conectar();
		System.out.println(lim.getPatrimonio() + "dao1");
		try {
			stm.executeUpdate("update limpeza set  patrimonio='" + lim.getPatrimonio().toUpperCase() + "',serie='" + lim.getSerie().toUpperCase() +
								 "',situacao='" + lim.getSituacao().toUpperCase() + "' where l_id='" + lim.getlId() + "';");
			System.out.println(lim.getPatrimonio() + "dao2");
			System.out.println("editado ...");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally{
			fechar();
		}
		
	}
	
}
