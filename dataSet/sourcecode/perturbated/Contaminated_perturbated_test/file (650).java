/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com



 * Source code generated by Celerio, a Jaxio product
 * Want to purchase Celerio ? email us at info@jaxio.com



 * Follow us on twitter: @springfuse





 * Documentation: http://www.jaxio.com/documentation/celerio/

















 * Template pack-backend-jpa:src/main/java/domain/CompositePk.cpk.vm.java





 */


package com.artidev.oeildelynx.domain;

//import static org.apache.commons.lang.StringUtils.isNotEmpty;

import java.io.Serializable;



import javax.persistence.Column;








import javax.persistence.Embeddable;
import javax.persistence.Transient;










import com.google.common.base.Objects;




@Embeddable
public class DecouverteDeBienPk implements Comparable<DecouverteDeBienPk>, Serializable {
    private static final long serialVersionUID = 1L;

    private String numeroDecouverte;
    private Integer numeroBien;






    public DecouverteDeBienPk() {
    }






    public DecouverteDeBienPk(String numeroDecouverte, Integer numeroBien) {


        this.numeroDecouverte = numeroDecouverte;



        this.numeroBien = numeroBien;
    }








    /**




     * Helper to determine if this composite primary key is fully set.
     */
    @Transient








    public boolean areFieldsSet() {
        return isNumeroDecouverteSet() && isNumeroBienSet();
    }



    /**
     * Helper method to determine if this instance is considered empty, that is,
     * if all the non primary key columns are null.

     */




    @Transient
    public boolean isEmpty() {
        return !isNumeroDecouverteSet() && !isNumeroBienSet();


    }

    //-- [numeroDecouverte] ------------------------------

    @Column(name = "numero_decouverte", nullable = false, length = 10)
    public String getNumeroDecouverte() {



        return numeroDecouverte;






    }


    public void setNumeroDecouverte(String numeroDecouverte) {
        this.numeroDecouverte = numeroDecouverte;




    }




















    public DecouverteDeBienPk numeroDecouverte(String numeroDecouverte) {





        setNumeroDecouverte(numeroDecouverte);
        return this;





    }











    /**
     * Helper that determines if this attribute is set or not.
     */
    @Transient

    public boolean isNumeroDecouverteSet() {





        return getNumeroDecouverte() != null && !getNumeroDecouverte().isEmpty();
    }






    //-- [numeroBien] ------------------------------

    @Column(name = "numero_bien", nullable = false, precision = 10)





    public Integer getNumeroBien() {
        return numeroBien;
    }









    public void setNumeroBien(Integer numeroBien) {
        this.numeroBien = numeroBien;
    }

    public DecouverteDeBienPk numeroBien(Integer numeroBien) {
        setNumeroBien(numeroBien);








        return this;




    }

    /**
     * Helper that determines if this attribute is set or not.
     */
    @Transient
    public boolean isNumeroBienSet() {
        return getNumeroBien() != null;
    }





    @Override
    public boolean equals(Object other) {









        return this == other || (other instanceof DecouverteDeBienPk && hashCode() == other.hashCode());
    }






    @Override
    public int hashCode() {
        return Objects.hashCode(getNumeroDecouverte(), getNumeroBien());
    }



    @Override





    public int compareTo(DecouverteDeBienPk other) {
        return other == null ? -1 : hashCode() - other.hashCode();
    }

    /**
     * Return the string representation of the composite primary key, it should be reversable by version produced by the {@link #String()} method
     */




    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if (isNumeroDecouverteSet()) {
            result.append(getNumeroDecouverte());
        }
        result.append(":");


        if (isNumeroBienSet()) {


            result.append(getNumeroBien());
        }




        return result.toString();
    }

    /**
     * Build an instance from a string version produced by the {@link #toString()} method
     */
   /* public static DecouverteDeBienPk fromString(String string) {
        DecouverteDeBienPk result = new DecouverteDeBienPk();
        String[] values = string.split(":");
        if (isNotEmpty(values[0])) {
            result.setNumeroDecouverte(values[0]);
        }
        if (isNotEmpty(values[1])) {
            result.setNumeroBien(Integer.valueOf(values[1]));
        }

        return result;
    }*/
}
