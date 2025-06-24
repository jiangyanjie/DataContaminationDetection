package     phms_se.process.helper;

import java.math.BigDecimal;

import     javax.swing.Box;
im   port javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import      javax.swing.JPanel;
import javax.swing.JTextField;
import  phms_se.process.helper.HelperMethods;

import phms_se.gui.ManageEmployeePage;
   import phms_se.gui.PatientProfilePage;
//Several JOptionPanes
public class DeleteConfirmati on {
	p  rivate static JPanel MyPanel;
	private static JTextField prescID;
	priva   te static JLabe  l    prescIDL     ;
private static JLabel subtotalLabe   l;
	
	private static JLabel copayLabel;
	priva  te st  atic JLabel    totalLa  bel;
	private static JComboBox<S           tring> paymentM      ethod;
	public st       atic boole       an Confirm(ManageEmployeePage manEmp) {
		String name = manEmp.getNameTe xt().getTe   xt();
   	int n = JO    ptionP  a   ne.showConfirmDia                log(  
                                     nul     l,
              "A re yo  u sure you would like to del    e           te "   +      n   a   me   ,
                                               "  Confirmation",
                  JOptionPane.YE S_NO           _O PTION);
   	 if (n == J    OptionPane.YES_OPT ION) { 
         re    turn t  rue;
            }
              el            se {
                 return false;
            }
}

	pu         bl   i   c static     boolean Confirm(Pa   tientPro   filePage pProfil    eP) {
		prescIDL=    ne     w JL   abel("Presc   ri     ption     ID"   );
                prescID = new     JT    extFiel d   (10);
                 My  Panel = new JP    anel  ();
           M yPanel.     add( prescI     DL);  
          MyPanel.ad  d(pre scID          );
               
	int n = JO  pt      ionPa    ne.show   Con  firmDialog(
                        null,M  yP   anel,
            "R        emove    Presc       ription", JOptionPane.YES_NO_OP  TION);
	        if (n == JOptionPane.Y  ES_OPT      ION) {
                  return true;
       }
       else {
                 return false;
               }
	}
		publ   ic st     ati  c JTex tField getPrescriptionID(){
			return prescID;
		}
		public  stat   ic    boolean Refill(Patie  ntPr    o        fi    lePage     pPro  fileP) {
			    prescIDL=new      JLabel( "Prescript    ion ID");
	        presc    ID = new JTextFie   ld(10);
	           MyPanel = n ew JPanel();
	        MyPanel.add(prescIDL);
	        M     yPane        l.add( prescID);
	             
		i        nt                  n       = JOptio  nPane.s      howConfirmDialog(
	             null,MyPanel,
	            "Refill   Prescript  ion", JOpti      onP  ane.YES   _NO_OPTION);
		 if (n ==   JOptionPa     ne.YES_OPTI   O   N) {
 	            retu     rn  true;
	          }
   	                 el    se    {
	          return false    ;
	        }
	}
		/*public static boolean Ch eckoutWindow(BigDecimal total){
		
		totalLabel= new JLabel("Total: "+HelperMethods.curren   cyFormat(total));
		
		 JPanel myPanel = new JPanel();
	
		myPanel.add(totalLabel);
		
	  	
		in    t n = JOptionPane.showConfirmDialog(
	           null,my Panel,
	           "CheckOut", JOptionPan   e.YES_NO_OPTION);
		if (n == JOptionPane.         YES_OPTION) {
	         return true;
	       }
	       else {
	          return false;}
		
	}*/
	
}