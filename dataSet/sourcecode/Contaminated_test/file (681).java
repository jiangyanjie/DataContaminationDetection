package pl.ais.commons.domain.security;

import pl.ais.commons.domain.stereotype.ValueObject;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Default {@link DecryptableValue} implementation.
 *
 * <p>
 * Instances of this class are serializable, if, and only if, applicable decryptor is serializable.
 * </p>
 *
 * @param <T> defines the type of unencrypted value
 * @author Warlock, AIS.PL
 * @since 1.0.2
 */
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
@ValueObject
public final class DefaultDecryptableValue<T> implements DecryptableValue<T>, Serializable {

    /**
     * Identifies the original class version for which it is capable of writing streams and from which it can read.
     *
     * @see <a href="http://docs.oracle.com/javase/7/docs/platform/serialization/spec/version.html#6678">Type Changes
     * Affecting Serialization</a>
     */
    private static final long serialVersionUID = -4691786449141212409L;

    /**
     * Decryptor applicable for encrypted value decryption.
     *
     * @serial
     */
    private final Decryptor<T> decryptor;

    /**
     * Lock used for decryption synchronization.
     *
     * @serial
     */
    private final ReentrantLock lock = new ReentrantLock();

    private transient T decryptedValue;

    /**
     * Encrypted value.
     *
     * @serial
     */
    private byte[] encryptedValue;

    /**
     * Constructs new instance.
     *
     * @param decryptor      decryptor which will be used to decrypt value
     * @param encryptedValue encrypted value, which will be enclosed by the created instance
     */
    protected DefaultDecryptableValue(@Nonnull final Decryptor<T> decryptor,
                                      @Nonnull final byte[] encryptedValue) {
        super();

        // Validate constructor requirements, ...
        Objects.requireNonNull(decryptor, "Decryptor is required.");
        Objects.requireNonNull(encryptedValue, "Encrypted value is required.");

        // ... and initialize this instance fields.
        this.decryptor = decryptor;
        this.encryptedValue = encryptedValue.clone();
    }

    /**
     * Returns factory creating {@link DefaultDecryptableValue} instances using provided decryptor.
     *
     * @param decryptor decryptor which will be used by decryptable values created by returned factory
     * @return factory creating {@link DefaultDecryptableValue} instances using provided decryptor
     */
    public static <T> DecryptableValueFactory<T, DefaultDecryptableValue<T>> factory(
        @Nonnull final Decryptor<T> decryptor) {
        return new Factory<>(decryptor);
    }

    /**
     * Decrypts the value.
     *
     * @return decrypted value
     */
    @Nonnull
    @Override
    public T decrypt() {
        lock.lock();
        try {
            if (null == decryptedValue) {
                decryptedValue = decryptor.decrypt(this);
            }
            return decryptedValue;
        } finally {
            lock.unlock();
        }
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(final Object object) {
        boolean result = (this == object);
        if (!result && (object instanceof DefaultDecryptableValue)) {
            final DefaultDecryptableValue other = (DefaultDecryptableValue) object;
            result = Objects.equals(decryptor, other.decryptor) && Arrays.equals(encryptedValue, other.encryptedValue);
        }
        return result;
    }

    /**
     * @return the encrypted value
     */
    @Nonnull
    @Override
    public byte[] getEncryptedValue() {
        return encryptedValue.clone();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return (31 * (527 + decryptor.hashCode())) + Arrays.hashCode(encryptedValue);
    }

    private void readObject(final ObjectInputStream stream)
        throws IOException, ClassNotFoundException {

        // Perform default de-serialization, ...
        stream.defaultReadObject();

        // ... validate the object state, ...
        if (null == decryptor) {
            throw new InvalidObjectException("Decryptor is required.");
        }
        if (null == encryptedValue) {
            throw new InvalidObjectException("Encrypted value is required.");
        }

        // ... and defensive copy encrypted value
        this.encryptedValue = encryptedValue.clone();
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new StringBuilder().append("Decryptable value with hashCode: ")
                                  .append(encryptedValue.hashCode())
                                  .append(", serviced by ")
                                  .append(decryptor)
                                  .toString();
    }

    private static class Factory<T> implements DecryptableValueFactory<T, DefaultDecryptableValue<T>> {

        private final Decryptor<T> decryptor;

        /**
         * Constructs new instance.
         *
         * @param decryptor decryptor which will be applied to decryptable values created by this factory
         */
        protected Factory(@Nonnull final Decryptor<T> decryptor) {
            super();

            // Validate constructor requirements, ...
            Objects.requireNonNull(decryptor, "Decryptor is required.");

            // ... and initialize this instance fields.
            this.decryptor = decryptor;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public DefaultDecryptableValue<T> decryptableValue(final byte[] representation) {
            return new DefaultDecryptableValue<>(decryptor, representation);
        }

    }

}
