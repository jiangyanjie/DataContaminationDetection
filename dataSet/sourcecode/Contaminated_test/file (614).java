package pl.ais.commons.domain.security.crypto;

import pl.ais.commons.domain.security.DecryptableValue;
import pl.ais.commons.domain.security.Decryptor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.crypto.Cipher;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Objects;
import java.util.Optional;

/**
 * {@link Decryptor} implementation based on Java Cryptography Architecture (JCA).
 *
 * @author Warlock, AIS.PL
 * @see <a href="http://docs.oracle.com/javase/7/docs/technotes/guides/security/crypto/CryptoSpec.html">Java
 * Cryptography Architecture (JCA)</a>
 * @since 1.1.1
 */
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public final class Decipherer implements Decryptor<byte[]> {

    private final Key key;

    private final Optional<AlgorithmParameterSpec> params;

    private final String transformation;

    /**
     * Constructs new instance.
     *
     * @param transformation the name of the transformation.
     *                       See the Cipher section in the <a href="http://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html#Cipher">Java
     *                       Cryptography Architecture Standard Algorithm Name Documentation</a>
     *                       for information about standard transformation names.
     * @param key            the encryption key
     */
    public Decipherer(@Nonnull final String transformation, @Nonnull final Key key) {
        this(transformation, key, Optional.empty());
    }

    /**
     * Constructs new instance.
     *
     * @param transformation the name of the transformation.
     *                       See the Cipher section in the <a href="http://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html#Cipher">Java
     *                       Cryptography Architecture Standard Algorithm Name Documentation</a>
     *                       for information about standard transformation names.
     * @param key            the encryption key
     * @param params         the algorithm parameters
     */
    public Decipherer(@Nonnull final String transformation, @Nonnull final Key key,
                      @Nonnull final AlgorithmParameterSpec params) {
        this(transformation, key, Optional.of(params));
    }

    private Decipherer(@Nonnull final String transformation, @Nonnull final Key key,
                       @Nonnull final Optional<AlgorithmParameterSpec> params) {

        Objects.requireNonNull(transformation, "Transformation is required.");
        Objects.requireNonNull(key, "Key is required.");
        Objects.requireNonNull(params, "Params are required");

        this.key = key;
        this.params = params;
        this.transformation = transformation;
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    @SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
    public byte[] decrypt(@Nullable final DecryptableValue<byte[]> input) {
        final byte[] result;
        if (null == input) {
            result = null;
        } else {
            Cipher cipher;
            try {
                cipher = Cipher.getInstance(transformation);
                if (params.isPresent()) {
                    cipher.init(Cipher.DECRYPT_MODE, key, params.get());
                } else {
                    cipher.init(Cipher.DECRYPT_MODE, key);
                }
                result = cipher.doFinal(input.getEncryptedValue());
            } catch (GeneralSecurityException exception) {
                throw new RuntimeException(exception);
            }
        }
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object object) {
        boolean result = (this == object);
        if (!result && (object instanceof Decipherer)) {
            final Decipherer other = (Decipherer) object;
            result = Objects.equals(transformation, other.transformation) && Objects.equals(key, other.key)
                && Objects.equals(params, other.params);
        }
        return result;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(transformation, key, params);
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new StringBuilder().append("Decipherer using transformation ")
                                  .append(transformation)
                                  .toString();
    }

    /**
     * Creates a copy of this decipherer with given algorithm parameters applied.
     *
     * @param params the algorithm parameters to be used
     * @return newly created decipherer instance with given algorithm parameters applied
     */
    @SuppressWarnings("hiding")
    public Decipherer withParams(@Nullable final AlgorithmParameterSpec params) {
        return new Decipherer(transformation, key, Optional.ofNullable(params));
    }

}
