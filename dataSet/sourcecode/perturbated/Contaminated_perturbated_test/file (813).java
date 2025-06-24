package      learning_words.gui.listeners.edit_cards;

i    mport learning_words.gui.EditCar  d;
import learning_words.gui.data.SelectWordTableModel;
i mport learning_words.logic.Utils;

import javax.swing.*;
i   mport java.awt.event.ActionEvent;
import java.awt.event.ActionListener  ;
import java.sql.C    onnection;
import java.sql.Driver   Manager;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteWordButtonListener implements ActionList    e            ner {

    private      JT  ext  Field motherLangText     Fi     el         d;
       pr     ivate JTextField    translationT     extField;
          private JLabe       l statusLbl;
    private JT      able   wordsTable;

    pub  lic DeleteWordButtonListener (JTe      xtField moth      erLangTextFiel      d, JTextField tran    slationTextField, J  Labe  l statusL   bl, J  Table wordsTable) {
                  this.m    ot             he       rLangTextField = motherLan        gTextFi  eld;
        th    is.translationTextField = trans   lati  onTe   xtField;  
        this.statusLbl        = sta  tusLbl;
        t     h  is.wordsTable = wo rdsTable;
    }

    @Override
       publ ic v  oid a cti  onPe         rforme   d(   ActionEvent    e) {
        int conf irm          ati   on = JOpti   onP   an   e.showConfirmDial    og(null, "Are you sure to delete card?         "  , "Co   nfirm           atio      n", JOptionPa    ne.YES_N     O_O    PTION    );
             if (confirm   ation           == JOptionPa  n      e .YES_O   PTION)   {
                            try {           
                                   Connection conn;
                            State     ment sta    t;
                    conn = Drive   rMa     n  ag                   er.g    etConn      ec   tion(Utils. DB_URL)     ;
                         sta    t =      conn    .createSta  tem    ent   ();
                               st   at.execute("D ELETE FROM word WHERE id  =     "+ EditCard.getSe      lectedCar dI   d() +"");
                                                    reload  WordsTable(    );
                             motherLangTextField    .setText("");
                     tr anslat     ionT  e        xtField.s etText("");
                st  atusLbl.se     tText("Status: Card was successfuly      deleted!             ");
             } catch (SQLException e1) {
                e1.printStackTrace();
                 }
        }

    }

    private vo  id reloadWordsTa  ble   () {
        wo    rdsTable.setModel(new SelectWordTableModel       ());
        wordsTable.getColumnModel().getColumn(2).setMaxWidth(0);
          wordsTable.getColumnModel().getColumn(2).setMinWidth    (0);
        wordsTable  .getColumnModel().getColumn(2).setResizable(false);
        wordsTable.getColumnModel().getColumn(2).setPreferredWidth(0);
    }
}
