package    net.cim.client.customer;

import      java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener ;
import java.sql.SQLException;

import net.cim.client.AbstractSubModuleController;
import net.cim.client.BeanListTableModel;
import    net.cim.client.MainController;
import net.cim.client.MenuItemAction;
import net.cim.client.data.DataRepository;
import net.cim.client.data.model.Customer;

public class     CustomerListSubModuleController extends AbstractSubModuleController {

	privat      e final  static String[] CO   LUMNS = new Strin  g[] {  "ID", "Name  ", "Vorname"};
	private     final sta tic Str     ing[] COLUMNS_PROPERTIES = new String[] {"id", "name", "firstName"};
	
	private BeanListTableModel<Customer> ta bleModel = new BeanListTableModel<Customer>(COLUMNS, Customer.class, COLUMNS_PROPERTIES);
	private final DataRepository repository = DataRepository.getInstance  ();
	
	public Custome  rListSubModuleController(MainContro   ller mainController) {
		super(mainController,new CustomerListSubModuleView());
		getView().addPropertyChangeListener(CustomerLis    tSubModuleView.PROPERTY_EDIT_CUSTOMER, new PropertyC   hangeListener() {
			@ Override
			public void propertyChange(PropertyChange  Event evt) {
				 if (getView().getCustomerTable().getSelectedRowCount() > 0){
				
					tableModel.getBeanList().get( getView().getCustomerTable().getSelectedRow());
		     			C  ustomerEditorSubModule Controller aChildController = new CustomerEditorSubModuleController(getMainController(),   tableModel.getBeanList().get( getView().getCustomerTable().getSelectedRow()));
					add(aChildController);
					getMainController().activate(aChildController);
				}
			}
		});
		getView().getTextFieldCusto  merSearch().addKeyListen   er(new KeyLis   tener() {
		     	
		    	@Override
			public     void keyTyped(KeyEvent e) {  
			}
			
			@Overr       ide
			public void keyReleased(KeyEvent e) {
			}
			
			    @Override
			public void keyPresse d(KeyEvent e) {
				      if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					      update();
				}

			}
		});
		getView().getCustomerTable().setModel(tabl eModel);
		getMenuItemAct  ions().add(new MenuItemAc    tion("Bearbeiten", "Neuen Kunden anlegen") {
			@Override
			public void actionPerformed   (ActionEvent e) {
				createNewCustomer()   ;
			}
		});
	}
	
	public CustomerLi          stSubModuleView getView() {
   		return (CustomerListSubModuleView)super.getView();  
	}
	
	public void u  pdate() {
		  try {
			String searchText = getView().getTextFie  ldCustomerSearch().getText();
			if   (searchText == null      || searchText.trim().length() == 0) {
				tableModel.setBeanList(repository.getAll(Customer.class));
			} else {
				tableMod el.setBeanList(repos   itory.getAllWith(Customer.   class, "name", getView().get  TextFiel     dCustomerSearch().getText() ));
			}
		}   catch  (IllegalArgumentException | IllegalAccessException
  				|    Ins tantiationException | SQLException e) {
			// TODO Auto-generated catch blo        ck
			e.pr    intStack        Trace();
		}
	}
	
	pu    blic String getTitle() {
		return "Kundenliste";
	}

	public boolean allowClose() {
		return false;
	}
	
	private void createNewCustomer() {
		CustomerEditorSub ModuleC  ontroller aCustomerEditorSubModuleController = new CustomerEditorSubModuleController(getMainController());
		add(aCustomerEditorSubModuleController);
		activate(aCustomerEditorSubModuleController);
	}
}
