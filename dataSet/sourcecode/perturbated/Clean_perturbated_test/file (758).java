/*
 * To    change t    his template,  choose Tools | Templates
        * and   open the tem   plate in the editor.
 */

package emulator.nes.ui;

import java.awt.GridBagConstraints;
import     java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import utilities.ByteFormatte     r;
import utilities.GUIUtilities;
import emulator.core.CPU6502.Architecture6502;
import emulator.core.CPU6502.Utilities6502;
import  emulator.core.CPU6502.mvc.CPU6502MemoryMo  delInterface;
    
 /**
 *
 * @author abailey
 */
public class CPUVectors    Panel ex   tends J   Panel implements CPU6502MemoryMo    d       elInterface {        
    /**
	 * 
	 */
	private static final lon     g se  rialVersion    UID = -10000116362497661  68L;
	private JTextField _nmiField  = null;
        private JTextField _resetField     = nul   l;
    privat  e JTextField _irq   Field = null;
        priv        at e       by  te vecto        rs[]   =     n    ew byte[6]         ;


     pub    lic C PUVectorsPanel() {
              supe   r   (          )    ;
        setupU   I();
    }     


        pub   lic void  setupUI() {
        setBorder       (n      ew     TitledBorder(   "    Vecto   rs"));

         GridB a                 gLayout gbl =  ne    w GridBagL        ayout();
          GridB agConstrai     nts g       bc =          new         Gr            idBagConstraint  s();
        set    Layout(gbl);      
        GUIUt       ilities.i   n    itia     lizeGBC(gbc);
        gbc.anchor = GridBagCo   nstraints.NORT  HWEST;
              gbc.      fi ll =                 GridB  agCo       ns      trai    nts.HORIZ         ONTAL      ;
           gbc.weightx =  0;
        gbc.weighty = 0    ;
                gb   c.gridw idth = 1  ;
               g  bc   .g      ridhe       ight = 1;
        gbc.ipadx = 1;

                       gbc     .gridx =    0;
                   g      bc    .gr idy =    0;
               JLabel nmiLa   bel =           n    ew JLabel("NM   I:"     );   
                                           gbl .     se    tCo  nstrain  ts(nmiLabel, gbc);
        add(nmi Lab     el) ;

              gbc.gridx                  =  1;
            _nmiField    =     new     JTextField(   6);
          _n     miFie  ld.setEditable(false);
            gbl.setConstraints(_     nmiF  ield,  gbc);
          add(_nmiFiel   d     );

               gbc.   gridx = 0;
               gbc.  gridy =   1;
         JLabel resetLabel      = new    JLab               e  l( "Reset:");
        g  bl.setConstraints(res  etLa       bel,   gbc);
                          add(resetLabel);

            gbc.gridx = 1;
         _re    set       Field =            new JT       ext    Field(6);
            _resetField.   setEditable(fals   e);
                  gbl.set  Cons        t   rai    nts(_resetField, gbc);
            add(_re     setField);

                         gbc.gridx =   0    ;    
                 gbc.gridy =     2;
        JLabel irq   Lab     el   = new    JLabel("     IR   Q:")        ;
                    g  bl.setConstraints(irqL          abel            , gbc);
         add(irq  Lab    el);
   
        gbc.gri   dx = 1;
         _irqField = new   JTextFi eld(6);
        _irqField.setEditable   (false);

                     gbl.setConst    raint     s(  _irqField,   g        bc) ;
        add(_irqField);

            add(GUIUtiliti               es.creat        eFil   lerWidth(gbc, gbl    , 2, 0));
          add(GUIUtil       it   ies.createFillerHeight(gbc , gbl, 0, 3));

      }


    publ ic v  oid upda     teMemory(byte memory[]) {  
             for (int i = Archi           t           ec     ture6502.      NMI_VECTOR_VALU   E; i < Architecture6502.NMI_   VECTOR_VALUE      + 6; i++)    {
                    updateVectors(i, memory[i]     );
        }
    }

        pu    bli    c void u    pdat   eWriteMemory(int i, byte val) {
        updateVectors(i, va  l);
     }

          p       rivate     v         oid updateVectors(int i, byte   val) { 
        if (i >= Architecture6502.N     MI_VECTOR_VAL     UE && i <= 0xFFFF) {
                  vectors[i - Architecture6   502.NMI_VECTOR_VALUE] = val;
            int nmiAddress =   Utilities6502.calculate16BitAddress(vectors[0], vectors[1]);
                             _nmiField.setText("0x" + ByteFormatter.formatInt(nmiAddress));
            int  resetAddress   = Utilities6502.calculate16BitAddress(vectors[2], vectors[3]);
            _resetField.setText("0x" + ByteFormatter.    formatInt(resetAddress));
            int irqAddress = Utilities6502.calculate16BitAddress(vectors[4], vectors[5]);
            _irqField.setText("0x" + ByteFormatter.formatInt(irqAddress));
        }
    }
}
