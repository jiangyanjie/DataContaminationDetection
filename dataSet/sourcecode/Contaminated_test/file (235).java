/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import connection.DbConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import logic.GradeCalc;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author David
 */
public class DAOStudentAnswer {

    public String DoTest(String js) {
        String result = "No se pudo realizar la Operación";
        DAOQuestion DAOq = new DAOQuestion();

        JSONArray Corrects = null;
        String query;
        String testName;
        String idTest;
        String idStudent;
        int rs;
        boolean error = false;

        try {
            JSONObject convert = new JSONObject(js);

            JSONArray idQuestion = convert.getJSONArray("questions");
            JSONArray answer = convert.getJSONArray("answers");

            testName = convert.getString("testName");
            idTest = new DAOTest().getIdtest(testName);

            idStudent = convert.getString("idStudent");

            for (int i = 0; i < idQuestion.length(); i++) {

                query = "INSERT INTO StudentAnswer( StudentAnswer.idTestQuestion, StudentAnswer.idStudent, StudentAnswer.idTest, StudentAnswer.answer) VALUES (\"" + Integer.parseInt(idQuestion.getString(i)) + "\",\"" + idStudent + "\",\"" + idTest + "\",\"" + (char)answer.getInt(i) + "\")";
                rs = DbConnection.runSqlUpdate(query);
                if (rs == 0) {
                    error = true;
                }

            }

            Corrects = DAOq.getCorrectAnswers(idQuestion);
            String resultGrade;

            if (Corrects != null) {

                GradeCalc gc = new GradeCalc();
                Double grade = gc.CalculateGrade(answer, Corrects);

                if (grade != -1) {

                    DAOStudentTest DAOst = new DAOStudentTest();
                    resultGrade = DAOst.UpdateStudentTest(idStudent, idTest, grade);

                    if (resultGrade.equals("No se pudo realizar la Operacion")) {
                        error = true;
                    }

                }

            }

            result = "Registro Completo";
            if (error) {
                result = "Registro con Errores";
            }

        } catch (JSONException ex) {
            Logger.getLogger(DAOStudentAnswer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;

    }
}
