package framework.view;

/*
 * abstract class in Fraework for show the GUI of personal Account and Company Account
 */

import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import framework.controller.FwController;

public abstract class AccountDialog extends JFrame {

	  protected FwController controller;
	  private JPanel actionsPanel;
	  private JPanel extraFields;
     // private FinancialCompany controller;
	
      public AccountDialog(String title , FwController controller) {
          super(title);
       //   this.controller = l;
          this.controller = controller;
          initComponents();
          
      }

      private void initComponents() {
         
          actionsPanel = createActions();
          extraFields=extraFields();
          
          getContentPane().setLayout(null);
          
//          checkinButton=new JRadioButton("Checkings");
//          checkinButton.setBounds(20, 10, 100, 20);
//          checkinButton.setSelected(true);
//          
//          savinButton=new JRadioButton("Savings");
//          savinButton.setBounds(20, 30, 100, 20);
//          checkinButton.setActionCommand("1");
//          savinButton.setActionCommand("2");
//          
//          groupJButtons = new ButtonGroup();
//          groupJButtons.add(checkinButton);
//          groupJButtons.add(savinButton);
          
          getContentPane().add(optionButtons());
          
//          getContentPane().add(checkinButton);
//          getContentPane().add(savinButton);
          
          nameLabel=new JLabel("Name");
          nameLabel.setBounds(20, 60, 100, 20);
          getContentPane().add(nameLabel);
          
          namefField=new JTextField();
          namefField.setBounds(80, 60, 120, 20);
          getContentPane().add(namefField);
          
          streetlJLabel=new JLabel("Street");
          streetlJLabel.setBounds(20, 80, 100, 20);
          getContentPane().add(streetlJLabel);
          
          streetField=new JTextField();
          streetField.setBounds(80, 80, 120, 20);
          getContentPane().add(streetField);
          
          cityJLabel=new JLabel("City");
          cityJLabel.setBounds(20, 100, 100, 20);
          getContentPane().add(cityJLabel);
          
          cityField=new JTextField();
          cityField.setBounds(80, 100, 120, 20);
          getContentPane().add(cityField);
          
          stateJLabel=new JLabel("State");
          stateJLabel.setBounds(20, 120, 100, 20);
          getContentPane().add(stateJLabel);
          
          stateField=new JTextField();
          stateField.setBounds(80, 120, 120, 20);
          getContentPane().add(stateField);
          
          zipJLabel=new JLabel("Zip");
          zipJLabel.setBounds(20, 140, 100, 20);
          getContentPane().add(zipJLabel);
          
          zipField=new JTextField();
          zipField.setBounds(80, 140, 120, 20);
          getContentPane().add(zipField);
          
          emailJLabel=new JLabel("Email");
          emailJLabel.setBounds(20, 160, 100, 20);
          getContentPane().add(emailJLabel);
          
          emailField=new JTextField();
          emailField.setBounds(80, 160, 120, 20);
          getContentPane().add(emailField);
          
//          extraFields().setBounds(20, 160, 200, 200);
          getContentPane().add(extraFields);
          
          
          getContentPane().add(actionsPanel);
          
//          
//          getContentPane().add(actionsPanel);

          
          
          setSize(300, 300);
          setVisible(true);
      }
      
      public abstract JPanel createActions();
      public abstract JPanel extraFields();
      public abstract JPanel optionButtons();
      
      public ActionListener getListener() {
          return null;//controller;
      }
      
//      public String getAccType()
//      {
//    	  return savinButton.isSelected()?"Saving":"Checking";
//    	  
//      }
      
//      protected JRadioButton savinButton;
//      protected JRadioButton checkinButton;
//      protected ButtonGroup groupJButtons;
      protected JLabel nameLabel;
      protected JTextField namefField;
      protected JLabel streetlJLabel;
      protected JTextField streetField;
      protected JLabel cityJLabel;
      protected JTextField cityField;
      protected JLabel stateJLabel;
      protected JTextField stateField;
      protected JLabel zipJLabel;
      protected JTextField zipField;
      protected JLabel emailJLabel;
      protected JTextField emailField;
}
