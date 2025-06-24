package webstore.bb;

import java.io.Serializable;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Conversation;
import javax.inject.Inject;
import webstore.core.IProductCatalogue;
import webstore.core.IReservationRegistry;
import webstore.core.JPAStore;
import webstore.core.Product;
import webstore.core.Reservation;

/**
 * Base class for EditProduct, DelProduct, AddReservation.
 *
 * @author hajon rewritten by Jonas Ha
 */
public abstract class ConversationalBase implements Serializable {

    private Product product;
    private Long id;
    private String name;
    private int quantity;
    private String price;
    @Inject
    private JPAStore jpa;
    @Inject
    private Conversation conversation;

    /**
     * Setter method for the for the edit and delete product
     *
     * @param id for the product
     */
    public void setSelected(String id) {
        if (conversation.isTransient()) {
            conversation.begin();
        }
        Product p = jpa.getProductCatalogue().find(Long.valueOf(id));
        this.product = p;
        this.id = p.getId();
        this.name = p.getName();
        this.quantity = p.getQuantity();
        this.price = String.valueOf(p.getPrice());
    }

    /**
     * Setter method for the reservation
     *
     * @param id1, id2 for the reservation and product
     */
    public void setSelectedR(String id1, String id2) {
        if (conversation.isTransient()) {
            conversation.begin();
        }
        Reservation r = jpa.getReservationRegistry().find(Long.valueOf(id1));
        Product p = jpa.getProductCatalogue().find(Long.valueOf(id2));

        this.id = r.getId();
        this.product = p;

    }

    @PreDestroy  // Must have for back button etc.
    public void destroy() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
    }

    /**
     * Method to be executed when doing an action in edit, del or add
     * reservation
     *
     * @return String of the coutcome which redirect you to a specifik jsf page
     */
    public String actOnSelected() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
        return execute();
    }

    // Implemented by subclasses
    protected abstract String execute();

    /**
     * Getter for the productCatalogue
     *
     * @return IProductCatalogue
     */
    protected IProductCatalogue getProductCatalogue() {
        return jpa.getProductCatalogue();
    }

    /**
     * Getter for the reservationRegistry
     *
     * @return IReservationRegistry
     */
    protected IReservationRegistry getReservationRegistry() {
        return jpa.getReservationRegistry();
    }

    /**
     * Returns the id
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the quantity
     *
     * @return quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity
     *
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Returns the price
     *
     * @return price
     */
    public String getPrice() {
        return price;
    }

    /**
     * Sets the price
     *
     * @param price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * Returns the product
     *
     * @return product
     */
    public Product getProduct() {
        return product;
    }
}
