package learning_words.gui.listeners.edit_cards;

import learning_words.gui.EditCard;
import learning_words.gui.data.SelectWordTableModel;
import learning_words.logic.Utils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteWordButtonListener implements ActionListener {

    private JTextField motherLangTextField;
    private JTextField translationTextField;
    private JLabel statusLbl;
    private JTable wordsTable;

    public DeleteWordButtonListener (JTextField motherLangTextField, JTextField translationTextField, JLabel statusLbl, JTable wordsTable) {
        this.motherLangTextField = motherLangTextField;
        this.translationTextField = translationTextField;
        this.statusLbl = statusLbl;
        this.wordsTable = wordsTable;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure to delete card?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            try {
                Connection conn;
                Statement stat;
                conn = DriverManager.getConnection(Utils.DB_URL);
                stat = conn.createStatement();
                stat.execute("DELETE FROM word WHERE id = "+ EditCard.getSelectedCardId() +"");
                reloadWordsTable();
                motherLangTextField.setText("");
                translationTextField.setText("");
                statusLbl.setText("Status: Card was successfuly deleted!");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

    }

    private void reloadWordsTable () {
        wordsTable.setModel(new SelectWordTableModel());
        wordsTable.getColumnModel().getColumn(2).setMaxWidth(0);
        wordsTable.getColumnModel().getColumn(2).setMinWidth(0);
        wordsTable.getColumnModel().getColumn(2).setResizable(false);
        wordsTable.getColumnModel().getColumn(2).setPreferredWidth(0);
    }
}
