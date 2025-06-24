package projetvelov.accesBD;



import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class AccesBDInfo {

    private Connection connexionBD;

    public AccesBDInfo() {
        this.connexionBD = ConnexionOracleFactory.creerConnexion();
    }
    private static AccesBDInfo instance = null;

    public static AccesBDInfo getInstance() {
        if (instance == null) {
            instance = new AccesBDInfo();
        }
        return instance;
    }

    public void VerifierConnexion(String pseudo, String mdp) throws SQLException {
        try {
            String sql = "select * from connexion where identifiant = ? and motdepasse = ?";
            PreparedStatement preparedStatement = connexionBD.prepareStatement(sql);
            preparedStatement.setString(1, pseudo);
            preparedStatement.setString(2, mdp);
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            System.out.println(result);
            
            if (result == null) {
                JOptionPane.showMessageDialog(null, "L'identifiant ou le mot de passe est incorrect.", "Erreur Connexion", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Connexion réussie.", "Erreur Connexion", JOptionPane.ERROR_MESSAGE);
            }
            result.close();
            preparedStatement.close();
        } catch (SQLException | HeadlessException e) {
            System.out.println(e.getMessage());
        }
    }
}