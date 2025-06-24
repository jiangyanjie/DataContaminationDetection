package managers;

import entities.ContactEntity;
import entities.CustomerEntity;
import exceptions.DeleteException;
import exceptions.ExistException;
import facades.ContactEntityFacadeLocal;
import facades.CustomerEntityFacadeLocal;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author zen9
 */
@Stateful
public class CustomerManagerBean implements CustomerManagerRemote {

    @EJB
    private ContactEntityFacadeLocal contactEntityFacade;
    @EJB
    private CustomerEntityFacadeLocal customerEntityFacade;

    public void createCustomer(String id, String name, String password, String address) throws ExistException {
        if (this.customerEntityFacade.find(id) != null) {
            throw new ExistException("Customer exists");
        }
        CustomerEntity customer = new CustomerEntity();
        ContactEntity contact = this.createContact(address);
        customer.create(id, name, password, contact);
        this.customerEntityFacade.create(customer);
        System.out.println("here is your customer!");
    }

    public void deleteCustomer(String id) throws DeleteException {
        CustomerEntity customer = this.customerEntityFacade.find(id);
        if (customer == null) {
            throw new DeleteException("Customer does not exists");
        }
        this.customerEntityFacade.remove(customer);
    }

    /**
     * returns customerEntity if login credential is c
     * @param id
     * @param password
     * @return
     */
    public CustomerEntity loginCustomer(String id, String password) {
        CustomerEntity customer = this.customerEntityFacade.find(id);
        if (customer == null) {
            return null;
        }
        if (customer.getPassword().equals(password)) {
            return customer;
        } else {
            return null;
        }

    }
    
    protected ContactEntity createContact(String address) {
        ContactEntity contact = new ContactEntity();
        contact.setAddress(address);
        this.contactEntityFacade.create(contact);
        return contact;
    }

}
