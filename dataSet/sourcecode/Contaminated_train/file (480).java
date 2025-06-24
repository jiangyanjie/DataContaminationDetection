package webstore.bb;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import org.primefaces.context.RequestContext;
import webstore.core.JPAStore;
import webstore.mb.Account;

/**
 * Backing Bean for the Add Product Page
 *
 * @author Jonas Ha
 */
@Named("addAccount")
@RequestScoped
public class AddAccountBB {

    @Inject
    private JPAStore jpa;
    @NotNull(message = "Required")
    private String username;
    @NotNull(message = "Required")
    private String password;

    /**
     * @author Josef Haddad
     * Changed so that you can enter uppercase and lettercase
     * and it will be the same.
     * Add user account method
     */
    public void add() {
        Account u = new Account(username.toLowerCase(), password);
        jpa.getAccountRegistry().add(u);
        RequestContext.getCurrentInstance().reset("form-addUser:addUser");
    }

    /**
     * Method to get all user accounts from the registry (database)
     *
     * @return A List of all the user accounts from the database
     */
    public List<Account> getAll() {
        return jpa.getAccountRegistry().getAll();
    }

    /**
     * Removes user account from registry (database)
     *
     * @param index for the Entity in database
     */
    public void remove(Long id) {
        jpa.getAccountRegistry().remove(id);
    }

    /**
     * Returns the username
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the password
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
