package net.cim.client.customer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import net.cim.client.AbstractSubModuleController;
import net.cim.client.MainController;
import net.cim.client.data.DataRepository;
import net.cim.client.data.model.Customer;

public class CustomerEditorSubModuleController extends
		AbstractSubModuleController {

	private final DataRepository repository = DataRepository.getInstance();
	private Customer customer;

	public CustomerEditorSubModuleController(MainController mainController) {
		this(mainController, new Customer());
	}
	
	public CustomerEditorSubModuleController(MainController mainController, Customer customer) {
		super(mainController,new CustomerEditorSubModuleView());
		getView().addPropertyChangeListener(
				CustomerEditorSubModuleView.PROPERTY_SAVE_INVOICE,
				new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						getCustomer().setName(getView().getTextFieldName().getText());
						getCustomer().setFirstName(getView().getTextFieldFirstName().getText());
						getCustomer().setPhone(getView().getTextFieldPhone().getText());
						getCustomer().setFax(getView().getTextFieldFax().getText());
						getCustomer().setEMail(getView().getTextFieldEMail().getText());
						repository.save(getCustomer());
					}
				});
		setCustomer(customer);
	}

	public CustomerEditorSubModuleView getView() {
		return (CustomerEditorSubModuleView) super.getView();
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
		getView().getTextFieldName().setText(getCustomer().getName());
		getView().getTextFieldFirstName().setText(getCustomer().getFirstName());
		getView().getTextFieldPhone().setText(getCustomer().getPhone());
		getView().getTextFieldFax().setText(getCustomer().getFax());
		getView().getTextFieldEMail().setText(getCustomer().getEMail());
	}

	public void update() {
	}

	public String getTitle() {
		return "Kundeninformationen";
	}

}
