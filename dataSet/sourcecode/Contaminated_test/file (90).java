package net.cim.client.customer;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;

import net.cim.client.AbstractSubModuleController;
import net.cim.client.BeanListTableModel;
import net.cim.client.MainController;
import net.cim.client.MenuItemAction;
import net.cim.client.data.DataRepository;
import net.cim.client.data.model.Customer;

public class CustomerListSubModuleController extends AbstractSubModuleController {

	private final static String[] COLUMNS = new String[] {"ID", "Name", "Vorname"};
	private final static String[] COLUMNS_PROPERTIES = new String[] {"id", "name", "firstName"};
	
	private BeanListTableModel<Customer> tableModel = new BeanListTableModel<Customer>(COLUMNS, Customer.class, COLUMNS_PROPERTIES);
	private final DataRepository repository = DataRepository.getInstance();
	
	public CustomerListSubModuleController(MainController mainController) {
		super(mainController,new CustomerListSubModuleView());
		getView().addPropertyChangeListener(CustomerListSubModuleView.PROPERTY_EDIT_CUSTOMER, new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (getView().getCustomerTable().getSelectedRowCount() > 0){
				
					tableModel.getBeanList().get( getView().getCustomerTable().getSelectedRow());
					CustomerEditorSubModuleController aChildController = new CustomerEditorSubModuleController(getMainController(), tableModel.getBeanList().get( getView().getCustomerTable().getSelectedRow()));
					add(aChildController);
					getMainController().activate(aChildController);
				}
			}
		});
		getView().getTextFieldCustomerSearch().addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					update();
				}

			}
		});
		getView().getCustomerTable().setModel(tableModel);
		getMenuItemActions().add(new MenuItemAction("Bearbeiten", "Neuen Kunden anlegen") {
			@Override
			public void actionPerformed(ActionEvent e) {
				createNewCustomer();
			}
		});
	}
	
	public CustomerListSubModuleView getView() {
		return (CustomerListSubModuleView)super.getView();
	}
	
	public void update() {
		try {
			String searchText = getView().getTextFieldCustomerSearch().getText();
			if(searchText == null || searchText.trim().length() == 0) {
				tableModel.setBeanList(repository.getAll(Customer.class));
			} else {
				tableModel.setBeanList(repository.getAllWith(Customer.class, "name", getView().getTextFieldCustomerSearch().getText() ));
			}
		} catch (IllegalArgumentException | IllegalAccessException
				| InstantiationException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getTitle() {
		return "Kundenliste";
	}

	public boolean allowClose() {
		return false;
	}
	
	private void createNewCustomer() {
		CustomerEditorSubModuleController aCustomerEditorSubModuleController = new CustomerEditorSubModuleController(getMainController());
		add(aCustomerEditorSubModuleController);
		activate(aCustomerEditorSubModuleController);
	}
}
