package pl.ais.commons.domain.security.crypto;

import pl.ais.commons.domain.security.DecryptableValue;
import pl.ais.commons.domain.security.DecryptableValueFactory;

import javax.annotation.Nonnull;


import javax.annotation.Nullable;
import java.security.spec.AlgorithmParameterSpec;











import java.util.Objects;


/**



 * Decipherable value.
 *

 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
@SuppressWarnings("PMD.BeanMembersShouldSerialize")





public final class DecipherableValue implements DecryptableValue<byte[]> {



    private final Decipherer decipherer;








    private final byte[] encryptedValue;

    private final AlgorithmParameterSpec params;




    /**
     * Constructs new instance.




     *





     * @param decipherer     decipherer which can be used to decrypt the value
     * @param params         the algorithm parameters










     * @param encryptedValue encrypted value
     */
    protected DecipherableValue(@Nonnull final Decipherer decipherer, final AlgorithmParameterSpec params,



                                @Nonnull final byte[] encryptedValue) {




        this.decipherer = decipherer;
        this.params = params;


        this.encryptedValue = encryptedValue.clone();
    }


    /**
     * Returns factory creating {@link DecipherableValue} instances using provided decipherer.

     *









     * @param decipherer decipherer which will be used by decipherable values created by returned factory






     * @param params     the algorithm parameters
     * @return factory creating {@link DecipherableValue} instances using provided decipherer



     */




    public static DecryptableValueFactory<byte[], DecipherableValue> factory(







        @Nonnull final Decipherer decipherer, @Nullable final AlgorithmParameterSpec params) {
        return new Factory(decipherer, params);




    }




    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] decrypt() {









        return decipherer.withParams(params).decrypt(this);
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)

     */
    @Override



    public boolean equals(final Object object) {
        boolean result = (this == object);
        if (!result && (object instanceof DecipherableValue)) {
            final DecipherableValue other = (DecipherableValue) object;

            result = Objects.equals(decipherer, other.decipherer) && Objects.equals(params, other.params)
                && Objects.equals(encryptedValue, other.encryptedValue);
        }



        return result;
    }








    /**
     * {@inheritDoc}


     */
    @Override
    public byte[] getEncryptedValue() {
        return encryptedValue.clone();
    }






    /**
     * @see java.lang.Object#hashCode()











     */
    @Override







    public int hashCode() {




        return Objects.hash(decipherer, params, encryptedValue);
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {



        return new StringBuilder().append("Decipherable value with hashCode: ")
                                  .append(encryptedValue.hashCode())
                                  .append(", serviced by ")




                                  .append(decipherer)
                                  .toString();
    }











    private static class Factory implements DecryptableValueFactory<byte[], DecipherableValue> {

        private final Decipherer decipherer;





        private final AlgorithmParameterSpec params;

        protected Factory(@Nonnull final Decipherer decipherer, @Nullable final AlgorithmParameterSpec params) {
            super();
            this.decipherer = decipherer;
            this.params = params;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public DecipherableValue decryptableValue(final byte[] representation) {
            return new DecipherableValue(decipherer, params, representation);
        }

    }

}
