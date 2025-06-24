package br.com.gstok.configuracao;

import java.io.*;
import java.util.*;

/**
 * Esta classe define a configuração do banco de dados.
 */
public class Configuracao {
    
    private String servidor;
    private String driver;
    private String url;
    private String usuario;
    private String senha;
    private Properties propriedades;
    private FileInputStream arqProperties;

    /**
     * Cria uma configuração para o sistema.
     */
    public Configuracao() {
        
        definirConfiguracao();
    }

    /**
     * Este método define uma configuração para o sistema.
     * 
     * @exception ex.
     */
    private void definirConfiguracao() {
        
        propriedades = new Properties();
        
        try {
            
            arqProperties= new FileInputStream(getClass().getResource("/br/com/gstok/arquivos/confBD.properties").getFile());
            propriedades.load(arqProperties);
            servidor=propriedades.getProperty("servidor");
            
            if(servidor.equals("MySQLInnoDBDialect")) {
                
                driver=propriedades.getProperty("driverMySQL");
                url=propriedades.getProperty("urlMySQL");
                usuario=propriedades.getProperty("userMySQL");
                senha=propriedades.getProperty("senhaMySQL");
            }
            if (servidor.equals("PostgreSQLDialect")) {
                
                driver=propriedades.getProperty("driverPostgreSQL");
                url=propriedades.getProperty("urlPostgreSQL");
                usuario=propriedades.getProperty("userPostgreSQL");
                senha=propriedades.getProperty("senhaPostgreSQL");
            }
            } catch (Exception ex) {
                
                servidor="";
                driver="";
                url="";
                usuario="";
                senha="";
                ex.printStackTrace();
        }
    }

    public String getServidor() {
        return servidor;
    }

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getSenha() {
        return senha;
    }
}