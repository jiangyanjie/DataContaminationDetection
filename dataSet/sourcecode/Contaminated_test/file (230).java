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

public class DAOProfessor {

    String professorName;
    String professorLastName;
    String professorEmail;
    String professorUser;
    String professorPassword;
    int professorId;

    public String LogInProfessor(String usr, String passw) {
        String resultado = null;


        String query = "SELECT professorName, professorLastName, idProfessor FROM Professor WHERE professorUser = '" + usr + "' AND professorPassword = '" + passw + "'";
        System.out.println("La sentencia es : " + query);


        ResultSet rs = DbConnection.runSqlStatement(query);
        try {
            if (rs.next()) {

                professorName = rs.getString("professorName");
                professorLastName = rs.getString("professorLastname");
                professorId = rs.getInt("idProfessor");
                resultado = professorName + " " + professorLastName + " " + professorId;

                return resultado;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOProfessor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    public String registryProfessor(String name, String user, String lastName, String email, String password) {
        String resultado = "No se pudo realizar la Operación";

        String query = "INSERT INTO Professor(professorName, professorUser, professorLastName, professorEmail, professorPassword) VALUES (\"" + name + "\",\"" + user + "\",\"" + lastName + "\",\"" + email + "\",\"" + password + "\")";
        System.out.println("La sentencia es : " + query);
        int rs = DbConnection.runSqlUpdate(query);

        System.out.println("RESULT SET = " + rs);
        if (rs != 0) {
            System.out.println("Result Set = " + rs);
            resultado = "Registro Completo";
            return resultado;
        }
        return resultado;
    }

    public String searchProfessor(String name) {

        String resultado = null;
        String query = "SELECT * FROM Professor WHERE professorName = '" + name + "'";



        ResultSet rs = DbConnection.runSqlStatement(query);
        try {
            if (rs.next()) {

                resultado = rs.getString("idProfessor");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOProfessor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    public String getProfessors() {

        String result = "No se pudo realizar la Consulta";
        String query = "SELECT * FROM Professor";

        ResultSet rs = DbConnection.runSqlStatement(query);
        try {
            if (rs.next()) {
                result = rs.getString("professorName");

                while (rs.next()) {

                    result = result + "," + rs.getString("professorName");


                }
                System.out.println("El resultado fue : " + result);
            } else {
                result = "Cero resultados";
            }


        } catch (SQLException ex) {
            Logger.getLogger(DAOTag.class.getName()).log(Level.SEVERE, null, ex);
        }




        return result;
    }
}