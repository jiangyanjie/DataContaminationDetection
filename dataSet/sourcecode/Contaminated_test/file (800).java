package phms_se.process.helper;

import java.math.BigDecimal;

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import phms_se.process.helper.HelperMethods;

import phms_se.gui.ManageEmployeePage;
import phms_se.gui.PatientProfilePage;
//Several JOptionPanes
public class DeleteConfirmation {
	private static JPanel MyPanel;
	private static JTextField prescID;
	private static JLabel prescIDL;
private static JLabel subtotalLabel;
	
	private static JLabel copayLabel;
	private static JLabel totalLabel;
	private static JComboBox<String> paymentMethod;
	public static boolean Confirm(ManageEmployeePage manEmp) {
		String name = manEmp.getNameText().getText();
	int n = JOptionPane.showConfirmDialog(
            null,
            "Are you sure you would like to delete " + name,
            "Confirmation",
            JOptionPane.YES_NO_OPTION);
	 if (n == JOptionPane.YES_OPTION) {
         return true;
       }
       else {
          return false;
       }
}

	public static boolean Confirm(PatientProfilePage pProfileP) {
		prescIDL=new JLabel("Prescription ID");
        prescID = new JTextField(10);
        MyPanel = new JPanel();
        MyPanel.add(prescIDL);
        MyPanel.add(prescID);
        
	int n = JOptionPane.showConfirmDialog(
            null,MyPanel,
            "Remove Prescription", JOptionPane.YES_NO_OPTION);
	 if (n == JOptionPane.YES_OPTION) {
         return true;
       }
       else {
          return false;
       }
	}
		public static JTextField getPrescriptionID(){
			return prescID;
		}
		public static boolean Refill(PatientProfilePage pProfileP) {
			prescIDL=new JLabel("Prescription ID");
	        prescID = new JTextField(10);
	        MyPanel = new JPanel();
	        MyPanel.add(prescIDL);
	        MyPanel.add(prescID);
	        
		int n = JOptionPane.showConfirmDialog(
	            null,MyPanel,
	            "Refill Prescription", JOptionPane.YES_NO_OPTION);
		 if (n == JOptionPane.YES_OPTION) {
	         return true;
	       }
	       else {
	          return false;
	       }
	}
		/*public static boolean CheckoutWindow(BigDecimal total){
		
		totalLabel= new JLabel("Total: "+HelperMethods.currencyFormat(total));
		
		 JPanel myPanel = new JPanel();
	
		myPanel.add(totalLabel);
		
		
		int n = JOptionPane.showConfirmDialog(
	           null,myPanel,
	           "CheckOut", JOptionPane.YES_NO_OPTION);
		if (n == JOptionPane.YES_OPTION) {
	         return true;
	       }
	       else {
	          return false;}
		
	}*/
	
}