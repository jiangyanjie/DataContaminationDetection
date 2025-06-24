






package creditcard.controller;

import java.util.Iterator;












import java.util.List;
import java.util.Observable;

import javax.swing.JFrame;



import javax.swing.JPanel;






import javax.swing.table.DefaultTableModel;

import creditcard.ChargeDialog;





import creditcard.CreditCardUI;




import creditcard.DepositDialog;
import creditcard.GenerateReportDialog;
import creditcard.PersonalCCAccountDialog;

import framework.helper.IAction;




import framework.model.Account;
import framework.model.Customer;
import framework.view.ActionButton;




import framework.view.FwActionListener;
import framework.view.TableView;







import framework.view.UI;






public class CreditCardTablePane extends TableView{


	private CreditCardUI parentWindow;
	public CreditCardTablePane(){
	 	super();











	}
    public CreditCardTablePane(CreditCardUI parentWindow) {
		super(parentWindow);






		// TODO Auto-generated constructor stub



		this.parentWindow = parentWindow;
	}





	@Override
    public JPanel createActions() {
        JPanel actions = new JPanel(new java.awt.GridLayout(6, 1));
        ActionButton addCreditCradAccountActionButton = new ActionButton();




        ActionButton generateBillActionButton = new ActionButton();
        ActionButton depositButton = new ActionButton();


        ActionButton chargeButton = new ActionButton();
        ActionButton exitButton = new ActionButton();

        addCreditCradAccountActionButton.setText("Add Credit Card Account");

       // addPersonalAccountButton.setHandler(new AccountHandler(AccountHandler.personal));

        addCreditCradAccountActionButton.addActionListener( new FwActionListener(new IAction(){



            	@Override
				public void performAction() {




            	   






            		new PersonalCCAccountDialog("Add Credit Card Account" , parentWindow.getController());





				}	



        	}));
        actions.add(addCreditCradAccountActionButton);






        generateBillActionButton.setText("Generate Bill");
        //addCompanyAccountButton.setHandler(new AccountHandler(AccountHandler.company));
        generateBillActionButton.addActionListener(new FwActionListener(new IAction(){







        	@Override


			public void performAction() {
        		new GenerateReportDialog("Generate Bill" ,parentWindow.getController());
			}	



    	}));
         actions.add(generateBillActionButton);






         depositButton.setText("Deposit");
        //depositButton.setHandler(new AccountHandler(AccountHandler.DepositDialog));
        depositButton.addActionListener(new FwActionListener(new IAction(){



        	@Override
			public void performAction() {
        		new DepositDialog("Deposit into Account", parentWindow.getController());



			}	
    	}));
        actions.add(depositButton);




        chargeButton.setText("Charge");






       // withdrawButton.setHandler(new AccountHandler(AccountHandler.WithdrawDialog));
        chargeButton.addActionListener(new FwActionListener(new IAction(){
        	@Override
			public void performAction() {
        		new ChargeDialog("Charge for Account",parentWindow.getController());
			}	












    	}));
        actions.add(chargeButton);








        exitButton.setText("Exit");  
        exitButton.addActionListener(new FwActionListener(new IAction(){




        	@Override



			public void performAction() {




        		System.exit(1);





			}	
    	}));





        actions.add(exitButton);

        return actions;
    }



	@Override
	public DefaultTableModel createModel() {



		   return new DefaultTableModel(
	                new Object[][]{},
	                new String[]{











	            "Name", "CC Number", "Exp Date", "Type", "Balance"




	        });
	}
	@Override
	






	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		updateTableModel(arg);
	}

    public void updateTableModel( Object arg) {
        DefaultTableModel aModel = createModel();
     
        	Object[] objects = new Object[8];
        	Iterator<Customer> customers = ((List) arg).iterator();
        	//populating the tablemodel
        	while (customers.hasNext()) {

	        	Customer customer = customers.next();
	        	objects[0] = customer.getName();





	        	objects[1] = customer.getCcNumber();
	        	objects[2] = customer.getExpDate();
	        	objects[3] = customer.getType();
	        	objects[4] = customer.getTotalAmount();
//	        	objects[5] = customer.getStreet();
//	        	objects[6] = customer.getStreet();
//	        	objects[7] = customer.getTotalAmount();
	        	aModel.addRow(objects);
        	}
        	//binding the jtable to the model

              this.viewerTable.setModel(aModel);
              this.getSelectedCustomer();
    }
    
   /* public List getSelectedTableItems(){
        int[] rows = viewerTable.getSelectedRows();
        for(int i=0; i< rows.length; i++){
          viewerTable.getModel().getValueAt(rows[i],0);
        }
      
    	return null;
    }
    */
    
    	
	
}
