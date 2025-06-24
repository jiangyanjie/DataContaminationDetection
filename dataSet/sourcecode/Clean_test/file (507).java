package hoofdmenu.nieuwprofiel;

import spel.ToonSpelbord;
import utils.Task;

import javax.swing.*;

/**
 * Created by Bart on 10-4-2014.
 */
public class ControleerNieuwProfiel extends Task {

    private JFrame jfSpelFrame;
    private String strSpelerNaam1;
    private String strSpelerNaam2;
    private String strTypeNaam1;
    private String strTypeNaam2;
    private int [] spelData = {
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
    };

    public ControleerNieuwProfiel(JFrame jfSpelFrame, String strSpelerNaam1, String strSpelerNaam2, String strTypeNaam1, String strTypeNaam2) {

        this.jfSpelFrame = jfSpelFrame;
        this.strSpelerNaam1 = strSpelerNaam1;
        this.strSpelerNaam2 = strSpelerNaam2;
        this.strTypeNaam1 = strTypeNaam1;
        this.strTypeNaam2 = strTypeNaam2;
    }

    public void run() {

        boolean validate = validate();

        if(validate == true)
        {
            execute();
        }
    }

    private boolean validate() {

        boolean validate = false;

        /*
            Hier wordt de validatie van het nieuwe profiel uitgevoerd.
            Hierbij wordt gekeken of de namen niet gelijk zijn, of ze niet beide met hetzelfde spelen, etc.
            Pas daarna wordt de execute uitgevoerd.
         */

        if(strSpelerNaam1.isEmpty() || strSpelerNaam2.isEmpty() || strTypeNaam1 == null || strTypeNaam2 == null) {
            JOptionPane.showMessageDialog(null, "Niet alle velden zijn ingevuld!", "Oops.. een foutje", JOptionPane.INFORMATION_MESSAGE);
        } else if(strSpelerNaam1.equals(strSpelerNaam2)){
            JOptionPane.showMessageDialog(null, "De profielnamen moeten verschillend zijn.", "Oops.. een foutje", JOptionPane.INFORMATION_MESSAGE);
        } else if(strTypeNaam1.equals(strTypeNaam2)){
            JOptionPane.showMessageDialog(null, "Het is niet mogelijk om biede met een '"+ strTypeNaam1 + "' te spelen", "Oops.. een foutje", JOptionPane.INFORMATION_MESSAGE);
        } else {
            validate = true;
        }

        return validate;
    }

    private void execute() {

        //De validate is goedgekeurd. Het spel kan worden geladen. De namen en de typen worden doorgegeven. De speldata is hier ook leeg.
        ToonSpelbord toonSpelbord = new ToonSpelbord(jfSpelFrame, strSpelerNaam1, strSpelerNaam2, strTypeNaam1, strTypeNaam2, spelData, strSpelerNaam1, strTypeNaam1, 0);
        toonSpelbord.run();
    }

}