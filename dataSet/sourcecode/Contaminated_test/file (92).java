package sg.edu.nus.iss.vmcs.customer;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;

import sg.edu.nus.iss.vmcs.store.StoreController;
import sg.edu.nus.iss.vmcs.util.WarningDisplay;

public class CustomerPanel extends Dialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9210551434582978969L;


	private static final String VIMTO_SOFT_DRINK_DISPENSER = "VIMTO SOFT DRINK DISPENSER";
	
	private StoreController storeController;
	
	private TransactionController transCtrl;
	private CoinInputBox coinInputBox;
	private DrinkSelectionBox drinkSelectionBox;;
	private Button btnTerminate=new Button("Terminate and Return Cash");
	//private TextField txtNoChangeAvail=new TextField("No Change Available");
	//private WarningDisplay warnNoChange;
	private Label lblCollectCoins=new Label("Collect Coins: ");
	private TextField txtCollectCoin=new TextField("0 C");
	//private Label lblCollectCan=new Label("Collect Can Here: ");
	//private TextField txtCollectCan=new TextField("No Can ");
	private FaultDetectionPanel faultDetection;
	/*public CustomerPanel(Frame owner, TransactionController transCtrl) {
		
		super(owner);
		// TODO Auto-generated constructor stub
		this.transCtrl= transCtrl;
		coinInputBox = new CoinInputBox(transCtrl);
		warnNoChange=new WarningDisplay("No Change Available");
		
	}*/
	
	
	public CustomerPanel(Frame owner, TransactionController transCtrl, StoreController storeController) {
		
		super(owner);
		// TODO Auto-generated constructor stub
		this.transCtrl= transCtrl;
	//	warnNoChange=new WarningDisplay("No Change Available");
		this.storeController = storeController;
		drinkSelectionBox = new DrinkSelectionBox(storeController, this);
		coinInputBox = new CoinInputBox(drinkSelectionBox,transCtrl);

		
	}
	
	
	
	public void initComponent()
	{
			
		setLayout(new BorderLayout()); 
		
		Panel north=new Panel(new BorderLayout());
		Panel center= new Panel(new BorderLayout());
		Panel south = new Panel();
	
		
		Font f=new Font("ARIAL", 0, 18);
		Label header=new Label(VIMTO_SOFT_DRINK_DISPENSER);
		header.setFont(f);
		header.setAlignment(1);
		north.add(header,BorderLayout.NORTH);
		
		//Panel selBox=new Panel(new GridLayout(0,1));
		Panel selBox=new Panel();
	    BorderLayout b=new BorderLayout();
	    selBox.setLayout(b);
	     selBox.setBackground(Color.LIGHT_GRAY);
	   
	    
		Label lbl=new Label("Please select drink:");
		
		
		
		 
		selBox.add(lbl,BorderLayout.NORTH);
		selBox.add(drinkSelectionBox,BorderLayout.CENTER);
		selBox.add(coinInputBox,BorderLayout.SOUTH);
		//selBox.add(drinkSelectionBox);
		//selBox.add(coinInputBox);
		north.add(selBox,BorderLayout.CENTER);
		
		//center.add(drinkSelectionBox,BorderLayout.PAGE_START);
		
		
		Panel nochange=new Panel(new BorderLayout());
	//	warnNoChange.setPreferredSize(new Dimension(10,50));
		
	//	nochange.add(warnNoChange,BorderLayout.NORTH);
		nochange.add(btnTerminate,BorderLayout.CENTER);
		center.add(nochange,BorderLayout.CENTER);
		btnTerminate.addActionListener(new CustomerPanelTerminateButtonListener(transCtrl));
		
		
		Panel coin=new Panel();
		coin.add(lblCollectCoins);
		txtCollectCoin.setPreferredSize(new Dimension(10,30));
	    coin.add(txtCollectCoin);
	    center.add(coin,BorderLayout.SOUTH);
	    Panel bottom=new Panel();
	    
	    bottom.setLayout(new GridLayout(0,1));
	     setFaultDetection(new FaultDetectionPanel(transCtrl));
	     bottom.add(faultDetection,BorderLayout.NORTH);
	    Panel can=new Panel();
	   
	//	can.add(lblCollectCan);
	//	txtCollectCan.setPreferredSize(new Dimension(10,50));
	//	can.add(txtCollectCan,BorderLayout.CENTER);
		bottom.add(can);
		south.add(bottom);
		
		add(north, BorderLayout.PAGE_START);
		add(center, BorderLayout.CENTER);
	    add(south, BorderLayout.PAGE_END);
	    
	    //setMaximumSize(new Dimension(100,100));
	    //setSize(100, 100);
		pack();
		
		final Dialog d=this;
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				d.dispose();
			}
		});
		
		coinInputBox.setEnabled(false);
		this.setVisible(true);
		
		
	}


	public CoinInputBox getCoinInputBox() {
		return coinInputBox;
	}


	public DrinkSelectionBox getDrinkSelectionBox() {
		return drinkSelectionBox;
	}


	public Button getBtnTerminate() {
		return btnTerminate;
	}


	

	public Label getLblCollectCoins() {
		return lblCollectCoins;
	}


	public TextField getTxtCollectCoin() {
		return txtCollectCoin;
	}




	public void setCoinInputBox(CoinInputBox coinInputBox) {
		this.coinInputBox = coinInputBox;
	}


	public void setDrinkSelectionBox(DrinkSelectionBox drinkSelectionBox) {
		this.drinkSelectionBox = drinkSelectionBox;
	}


	public void setBtnTerminate(Button btnTerminate) {
		this.btnTerminate = btnTerminate;
	}




	public void setLblCollectCoins(Label lblCollectCoins) {
		this.lblCollectCoins = lblCollectCoins;
	}


	public void setTxtCollectCoin(TextField txtCollectCoin) {
		this.txtCollectCoin = txtCollectCoin;
	}



	public FaultDetectionPanel getFaultDetection() {
		return faultDetection;
	}


	public void setFaultDetection(FaultDetectionPanel faultDetection) {
		this.faultDetection = faultDetection;
	}


	public TransactionController getTransactionController() {
		return transCtrl;
	}


	public void setTransactionController(TransactionController transCtrl) {
		this.transCtrl = transCtrl;
	}


	public void setCollectCanValue(String name) {
		// TODO Auto-generated method stub
		txtCollectCoin.setText(name);
	}
	
	
	

}
