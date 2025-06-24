package pl.ais.commons.domain.security;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

/**
 * Base class for cryptographic services.
 *
 * <p>
 * This implementation delegates the encryption/decryption work to predefined {@link Encryptor} / {@link Decryptor}.
 * </p>
 * <p>
 * Note that this class overrides {@link #hashCode()} method, but still uses default {@link Object#equals(Object)}
 * method. You should override this method yourself, when extending this class. Consider using {@code equivalentOf}
 * method to achieve this task.
 * </p>
 * <p>
 * Although this class is not serializable itself, subclasses of it can be made serializable using
 * <em>Serialization Proxy Pattern</em> - see: <em>Effective Java, Second Edition</em> by Joshua Bloch
 * (ISBN-13: 978-0-321-35668-0)
 * </p>
 *
 * @param <T> defines the type of unencrypted value
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
@SuppressWarnings({"PMD.BeanMembersShouldSerialize", "PMD.OverrideBothEqualsAndHashcode"})
public class CryptographicServiceSupport<T> implements Decryptor<T>, Encryptor<T> {

    private final Decryptor<T> decryptor;

    private final Encryptor<T> encryptor;

    /**
     * Constructs new instance.
     *
     * @param decryptor to be used as decryption delegate
     * @param encryptor to be used as encryption delegate
     */
    protected CryptographicServiceSupport(@Nonnull final Decryptor<T> decryptor, @Nonnull final Encryptor<T> encryptor) {
        super();

        // Validate constructor requirements, ...
        Objects.requireNonNull(decryptor, "Decryptor is required.");
        Objects.requireNonNull(encryptor, "Encryptor is required.");

        // ... and initialize this instance fields.
        this.decryptor = decryptor;
        this.encryptor = encryptor;
    }

    /**
     * Decrypts given value.
     *
     * <p>This implementation delegates the decryption process to the {@code decoder}.</p>
     */
    @Override
    @Nullable
    public T decrypt(@Nullable final DecryptableValue<T> decryptable) {
        return decryptor.decrypt(decryptable);
    }

    /**
     * Encrypts given value.
     *
     * <p>This implementation delegates the encryption process to the {@code encoder}.</p>
     */
    @Override
    @Nullable
    public DecryptableValue<T> encrypt(@Nullable final T encryptable) {
        return encryptor.encrypt(encryptable);
    }

    /**
     * Verifies if this instance is equivalent of given {@code object}.
     *
     * @return {@code true} if decryptor/encryptor pair used by this instance and given {@code object} are the same,
     * {@code false} otherwise
     */
    @SuppressWarnings("rawtypes")
    protected boolean equivalentOf(final CryptographicServiceSupport service) {
        return Objects.equals(decryptor, service.decryptor) && Objects.equals(encryptor, service.encryptor);
    }

    /**
     * @return the decryptor
     */
    public Decryptor<T> getDecryptor() {
        return decryptor;
    }

    /**
     * @return the encryptor
     */
    public Encryptor<T> getEncryptor() {
        return encryptor;
    }

    /**
     * Returns a hash code value for the object.
     *
     * <p>This implementation computes hash code based on {@code decryptor} and {@code encryptor} hash codes.</p>
     */
    @Override
    public int hashCode() {
        return Objects.hash(decryptor, encryptor);
    }

    /**
     * Returns a string representation of the object.
     *
     * <p>
     * This implementation includes both {@code decryptor} and {@code encryptor} string representations in the result.
     * </p>
     */
    @Override
    public String toString() {
        return new StringBuilder().append("Cryptographic service using decryptor ")
                                  .append(decryptor)
                                  .append(" and encryptor ")
                                  .append(encryptor)
                                  .toString();
    }

}
