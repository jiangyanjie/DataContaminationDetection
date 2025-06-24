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
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class DAOStudent {

    String studentName;
    String studentLastName;
    String studentEmail;
    String studentUser;
    String studentPassword;
    String studentId;

    public String LogInStudent(String usr, String passw) {
        String resultado = null;
        try {
            String logIn = "SELECT studentName, studentLastName, idStudent FROM Student WHERE studentUser = '" + usr + "' AND studentPassword = '" + passw + "'";
            ResultSet rs = DbConnection.runSqlStatement(logIn);
            if (rs.next()) {
                studentName = rs.getString("studentName");
                studentLastName = rs.getString("studentLastname");
                studentId = rs.getString("idStudent");
                resultado = studentName + " " + studentLastName + " " + studentId;
                return resultado;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOProfessor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    public String registryStudent(String name, String user, String lastName, String email, String password, String professor) {
        String idProfessor = null;
        String resultado = "No se pudo realizar la Operación, verifique que selecciono su profesor asignado";

        DAOProfessor DAOp = new DAOProfessor();
        idProfessor = DAOp.searchProfessor(professor);

        if (idProfessor != null) {

            String query = "INSERT INTO Student(studentName, studentUser, studentLastName, studentEmail, studentPassword, idProfessor) VALUES (\"" + name + "\",\"" + user + "\",\"" + lastName + "\",\"" + email + "\",\"" + password + "\", " + idProfessor + " )";
            System.out.println("La sentencia es : " + query);

            
            int rs = DbConnection.runSqlUpdate(query);
            System.out.println("RESULT SET = " + rs);

            if (rs != 0) {
                System.out.println("Result Set = " + rs);
                resultado = "Registro Completo";
            }
        }
        return resultado;
    }

    public String getStudents(String idProfessor) {

        String result = "No se pudo realizar la Consulta";
        String query = "SELECT studentName, idStudent FROM Student WHERE idProfessor = \"" + idProfessor + "\"";
        
        ResultSet rs = DbConnection.runSqlStatement(query);
        JSONObject js = new JSONObject();
        JSONArray nombreEstudiantes = new JSONArray();
        JSONArray idEstudiantes = new JSONArray();
        try {

            while (rs.next()) {

                nombreEstudiantes.put(rs.getString("studentName"));
                idEstudiantes.put(rs.getString("idStudent"));
            }
            js.put("studentName", nombreEstudiantes);
            js.put("idStudent", idEstudiantes);

            result = js.toString();
            System.out.println("El Resultado fue : " + result);

        } catch (JSONException ex) {
            Logger.getLogger(DAOStudent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAOStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String GetIdStudent(String studentName) {

        String result = "No se pudo realizar la consulta";
        String query = "SELECT idStudent FROM Student WHERE studentName = \"" + studentName + "\"";
        System.out.println("La Sentencia fue =" + query);
        ResultSet rs = DbConnection.runSqlStatement(query);
        try {
            if (rs.next()) {
                result = rs.getString("idStudent");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String GetIdProfessor(String idStudent) {

        String result = "No se pudo realizar la consulta";
        String query = "SELECT idProfessor FROM Student WHERE idStudent = \"" + idStudent + "\"";
        System.out.println("La Sentencia fue =" + query);
        ResultSet rs = DbConnection.runSqlStatement(query);
        try {
            if (rs.next()) {
                result = rs.getString("idProfessor");
            } else {
                result = "Cero resultados";
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
