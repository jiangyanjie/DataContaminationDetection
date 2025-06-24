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
import org.json.JSONException;
import org.json.JSONObject;

public class DAOTestQuestion {
    
    public String registryTestQuestion(String testName, String sentence) {
        
        String resultado = "No se pudo realizar la Operación";
        
        String[] arr = sentence.split("/");
        String idTest;
        String[] idQuestion = new String[arr.length];
        
        
        
        DAOTest DAOt = new DAOTest();
        idTest = DAOt.getIdtest(testName);
        
        DAOQuestion DAOq = new DAOQuestion();
        
        for (int i = 0; i < arr.length; i++) {
            
            idQuestion[i] = DAOq.getIdQuestion(arr[i]);
            System.out.println("idQuestion en la posicion " + i + " Es " + idQuestion[i]);
        }
        
        int rs[] = new int[idQuestion.length];
        String query;
        boolean error = false;
        
        if (idQuestion != null && idTest != null) {
            
            
            for (int i = 0; i < idQuestion.length; i++) {
                query = "INSERT INTO TestQuestion(idQuestion, idTest) VALUES (\"" + idQuestion[i] + "\",\"" + idTest + "\")";
                
                rs[i] = DbConnection.runSqlUpdate(query);
                System.out.println("El RS en " + i + " fue " + rs[i]);
                if (rs[i] == 0) {
                    error = true;
                    System.out.println("No se pudo registrar la pregunta " + arr[0]);
                }
                
            }
            if (error == true) {
                
                resultado = "Registro con Errores";
            } else {
                resultado = "Registro Completo";
            }
            
        }
        
        return resultado;
        
        
    }
    
    public String GetTestQuestion(String idTest) {
        
        String result = "No se pudo realizar la Consulta";
        String query = "SELECT Question.idQuestion, Question.sentence, Question.optionA, Question.optionB, Question.optionC, Question.optionD FROM Question, TestQuestion WHERE TestQuestion.idTest = \"" + idTest + "\" AND TestQuestion.idQuestion = Question.idQuestion ";
        
        System.out.println( "La sentencia fue = " + query );
        
        JSONObject js = new JSONObject();
        JSONArray idQuestions = new JSONArray();
        JSONArray Sentences = new JSONArray();
        JSONArray OptionsA = new JSONArray();
        JSONArray OptionsB = new JSONArray();
        JSONArray OptionsC = new JSONArray();
        JSONArray OptionsD = new JSONArray();
        
        ResultSet rs = DbConnection.runSqlStatement(query);
        try {
            
            while (rs.next()) {
                
                idQuestions.put(rs.getString("idQuestion"));
                Sentences.put(rs.getString("sentence"));
                OptionsA.put(rs.getString("optionA"));
                OptionsB.put(rs.getString("optionB"));
                OptionsC.put(rs.getString("optionC"));
                OptionsD.put(rs.getString("optionD"));                       
                
            }
            
            js.put("idQuestions", idQuestions);
            js.put("Sentences", Sentences);
            js.put("OptionsA", OptionsA);
            js.put("OptionsB", OptionsB);
            js.put("OptionsC", OptionsC);
            js.put("OptionsD", OptionsD);
            
            result = js.toString();
            
            
        } catch (JSONException ex) {
            Logger.getLogger(DAOTestQuestion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAOTestQuestion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
        
        
    }
}
