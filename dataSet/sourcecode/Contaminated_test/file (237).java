/*
 * Universidad EAFIT
 * Ing. de Sistemas
 * 
 * Proyecto Integrador 2
 * 
 * Name: Ar-Machine Project
 */
/**
 *
 * @author Erika Gomez
 * @author Sebastian Jimenez
 * @author David Sttivend
 * @author Ernesto Quintero
 */
package Dao;

import connection.DbConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;

public class DAOTag {

    public String registryTag(String nombreTag, String idProfessor) {

        String result = "El registro no se pudo realizar la Operación";


        if (idProfessor != null) {

            String query = "INSERT INTO Tag(tag,idProfessor) VALUES (\"" + nombreTag + "\",\"" + idProfessor + "\")";
            System.out.println("La Sentencia es : " + query);

            int rs = DbConnection.runSqlUpdate(query);
            System.out.println("RESULT SET = " + rs);

            if (rs != 0) {
                result = "Registro Completo";
            }
        }
        return result;
    }

    public String getIdTag(String tagName) {

        String result = "No se pudo realizar la Consulta";
        String query = "SELECT * FROM Tag WHERE tag = '" + tagName + "'";


        ResultSet rs = DbConnection.runSqlStatement(query);
        try {
            if (rs.next()) {
                result = rs.getString("idTag");
            } else {
                result = "Cero resultados";
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOTag.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String getTag(String idProfessor) {

        String result = "No se pudo realizar la Consulta";
        String query = "SELECT tag FROM Tag WHERE idProfessor = \"" + idProfessor + "\"";

        ResultSet rs = DbConnection.runSqlStatement(query);

        JSONArray ja = new JSONArray();

        try {

            if (rs.isBeforeFirst()) {

                while (rs.next()) {

                    ja.put(rs.getString("tag"));


                }
                result = ja.toString();

                System.out.println("El resultado fue : " + result);


            } else {
                result = "Cero Resultados";
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOTag.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }
}