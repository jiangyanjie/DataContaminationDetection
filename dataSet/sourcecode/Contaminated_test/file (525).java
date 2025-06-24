package com.sale.db.dao.object;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * User: shalabh.kulshreshtha
 * Date: 8/11/13
 * Time: 5:23 AM
 */
@Entity
@Table(name = "SALE_PRODUCT_OFFER")
public class DBSaleProductOffer implements Serializable {
    private static final long serialVersionUID = -1550464414658848696L;

    private Integer offerId_;
    private DBSale sale_;
    private DBProduct product_;
    private DBBrand brand_;
    private String offer_;
    private Date startDate_;
    private Date endDate_;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OFFER_ID", unique = true, nullable = false)
    public Integer getOfferId() {
        return offerId_;
    }

    public void setOfferId(Integer offerId) {
        offerId_ = offerId;
    }

    @ManyToOne(targetEntity = DBSale.class)
    @JoinColumn(name = "SALE_ID", referencedColumnName = "SALE_ID")
    public DBSale getSale() {
        return sale_;
    }

    public void setSale(DBSale sale) {
        sale_ = sale;
    }

    @ManyToOne(targetEntity = DBProduct.class)
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")
    public DBProduct getProduct() {
        return product_;
    }

    public void setProduct(DBProduct product) {
        product_ = product;
    }

    @ManyToOne(targetEntity = DBBrand.class)
    @JoinColumn(name = "BRAND_ID", referencedColumnName = "BRAND_ID")
    public DBBrand getBrand() {
        return brand_;
    }

    public void setBrand(DBBrand brand) {
        brand_ = brand;
    }

    @Column(name = "OFFER", nullable = false, length = 1024)
    public String getOffer() {
        return offer_;
    }

    public void setOffer(String offer) {
        offer_ = offer;
    }

    @Column(name = "START_DATE", nullable = false)
    public Date getStartDate() {
        return startDate_;
    }

    public void setStartDate(Date startDate) {
        startDate_ = startDate;
    }

    @Column(name = "END_DATE", nullable = false)
    public Date getEndDate() {
        return endDate_;
    }

    public void setEndDate(Date endDate) {
        endDate_ = endDate;
    }
}
