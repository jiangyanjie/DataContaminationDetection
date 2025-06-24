/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package discountstrategy;

/***********
 *  Used as a abstraction between the database and the types of users that could
 *  be used
 * *********
 * @author schereja
 */
public final class CustomerDatabase implements DatabaseStrategy{
    private String customerName;
    private String customerId;
    private String CUSTOMER_ID_ERROR = "Please enter valid customer ID.";
    private String CUSTOMER_NAME_ERROR = "Please enter a valid customer name.";
    private CustomerStrategy[] customers = {
        new OnlineCustomer("C1011", "Lynn"),
        new OnlineCustomer("C1012", "John"),
        new OnlineCustomer("C1013", "Jill"),
        new OnlineCustomer("C1014", "Jack")
        };
    /*********
     * Constructor for customer database, validation is done with the setId
     * method
     * *******
     * 
     * @param customerId 
     */
    public CustomerDatabase(String customerId){
        setId(customerId);
    }

    @Override
    public String getId() {
        return this.customerId;
    }
    /**********
     * Used to get customer name from database
     * ********
     * ToDo: Add validation for valid ID format
     * ********
     * @param valid customerID
     * @return customer name
     */
    @Override
    public String getName(String id) {
       
        String custName = null;
        for(int i = 0; i<customers.length; i++){
            if (customerId.equals(customers[i].getCustomerId())){
                custName = customers[i].getCustomerName();
            }
        }
        return custName;
    }
    /***********
     * Used to set the ID of a customer if need to enter a new user
     * *********
     * ToDo: Allow only customer IDs to match the naming convention CXXXX
     * 
     * *********
     * @param id 
     * @throws Errors when a none valid ID is used.
     */
    @Override
    public void setId(String id) {
        if(id == null || id.length() == 0){
            throw new IllegalArgumentException(CUSTOMER_ID_ERROR);
        }
       this.customerId = id;
    }
    /**********
     * Used to set the name for the customer
     * ********
     * 
     * @param String name
     * @throws Error if customer name is below 1 for length
     */
    @Override
    public void setName(String name) {
         if(name == null || name.length() < 1){
            throw new IllegalArgumentException(CUSTOMER_NAME_ERROR);
        }
        this.customerName = name;
                }
    /**
     * Still need to program will remove a customer from the database.
     * 
     * 
     * @param Id 
     */
    @Override
    public void removeFromDatabase(String Id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
