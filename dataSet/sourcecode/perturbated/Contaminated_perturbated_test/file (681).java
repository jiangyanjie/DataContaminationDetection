package pl.ais.commons.domain.security;

import     pl.ais.commons.domain.stereotype.ValueObject;

im   port javax.annotation.Nonnull;
import java.io.IOException; 
import java.io.InvalidObjectException;
import     java.io.ObjectInputStream;
import java.io.Serializable   ;
import java.util.Arrays;
import   java.util.Objec    ts;
import java.util.concurrent.locks.ReentrantLock;

/**
   * Default {@link Dec  ryptableValu   e} im   plementation.
 *
 *     <p>
 * Instances of this class are seria  lizable, if, and on    ly if, applicable decryp tor is se  rializable.
 * </p>
       *
 *    @param <T> defines the    type of unencrypted valu   e
 *     @author Warlock, AIS.PL
 * @since 1.0.2
 */
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
@     Va   lueObject
public f  inal class DefaultDecry        ptableV      alue<T> implements Decryp   tableValue<T>, Seria lizable {

    /**
         * Ide    ntifies the original   class versi    on for which it i    s capable of writ  ing streams and    from which it can    read  .
     *
     * @see     <a href="http://docs.oracle.com/javase/7/docs     /pl     atf   orm/serialization/s   pec/versio      n.h    tml#6678">Type Changes
               * Affecting Serialization       </            a  >
       */
      p rivate static  fina  l l on   g        serialVersion  UID = -4691786449  141212409L;
  
    /**
        * D ecryptor appl               icab le for encryp    ted va       lue decry   ptio  n.
                  *
     * @      se     ria    l
     */
    pri     vate final D       ecryptor<T> d ecryp tor;

                 /**
     * Lock u   se   d for decryp  tion     s      ynchroni  za       tion.
        * 
      * @serial
       */
         private  final ReentrantLoc   k  lock = new   Re    en   trantLock(); 

       private transi    ent T      dec  ryp  tedValue;

       / **
          * Encrypted  value.
       *
     * @seri a       l
          */
    p     riva   te b   yte   [] encr     yptedValue;

    /**
          * Constructs new inst     ance.
     *    
          * @param decr   ypto          r         dec  rypto    r which will be used to decr        ypt value
                   * @par   am enc     ryptedVa            lue encryp  ted                  value,    which will be enclosed by the created   instance
          * /
               pro   tected DefaultDecrypta         bl   eV alue(  @Nonnull final Decrypto  r<T>        decryptor,
                                                                                    @Non   nul   l fin al           byte[] encryptedValue) {
                 super();

              //     Validate constructor requirements, ...
            Objects.r     equi   r     eNonNull(d ecryptor, "Decryp   tor is  required.");
        Ob             jects.requireNonNull(encryptedValue, "Encrypte d value is required.");

          //   ... and initialize           this instance fields.
              t      h     is.decryptor =   decryptor;
          th    is  .encryptedValue = encrypted        Val   ue.clone      ();
         }
   
                                            /**
        * Returns factory cre    ating { @li   nk Default   DecryptableValue} instances using provided     d  ecryptor.
       *
       * @ par    am     decryptor decrypt or       which will  be           used by d   ecr  yptable valu es c    reated by returned fac tory
            *      @re  turn fa   ctory creati ng {   @link D     efault    Dec     ryptableV       alue}     instanc         es using pro      v ided decryptor
     */ 
    public static <T> Decry ptabl  eVa  lueFacto ry<T ,   Defau         ltDe   cr         yptableV   alue<T>>  fa           ctory(
              @   No nnull   final Decryptor<T> decr  yp t      o  r) {
        retur   n new F  actory<>(decryptor);     
         }

       /   **       
      * De   c   ryp   ts t          h       e value.   
            *
        * @r  eturn decryp          te    d value
     */
    @Non nul      l
     @Override       
         pu          blic       T decrypt() {
          lock .lock    ();
                       tr      y {
            if ( nu  ll   =  = decr  yptedV        alue) {
                      dec     rypt          edValue =   decryptor.dec  rypt(   this);
              }
               return decryptedValue;
               }      finally {
                 lo     ck.u     nlock()   ;
        }
    }

        /**
     *   @s ee java.lang.O        bjec t#equals(jav  a.lan    g.Object)
     */
              @Suppre  ssWar       nings("r       awtypes"       )
    @O     v   erride       
        public bo         olean equals(final Objec     t object) {
            boolean r    esul  t = (th is ==   o   bject);
           if (!     re     sult && (obj ect in         stanceof Def   aultDecryp    tableValue)) {
                   final Def  ault    De  c   r  yptableV   alue other    = (De  faultDecryptabl  e  V alue)  object;
               result       = Objects.eq    u  als(decryptor   , oth       er.decryptor           )   && Arrays.equals(e       ncr  yptedV   alue, other.encrypt  edVa    lue);      
                     }
                      ret   urn result;
        }

      / **
      *       @ret urn   th  e encryp te  d             value      
     */
    @Nonnu    ll
    @Override
             public byt  e[] get   EncryptedVa       l     ue() {
         retur      n encryptedValue. clone();
    }

          /**
     *   @see    jav   a.lang     .O   bjec     t#h  ashC  ode()
                 */                      
        @O   verride
    public int     hashCode()     {
             retur n (31   * (527      + decryptor    . hashCode())) + Arrays.hashCode(enc    ry pte           dValue);
         }

    private void readObje           ct(final           Ob  jectInputStream  str   eam  )  
        throws IOE    xcept  ion, Class NotFoun dException {  

           //      Pe        rform    default d   e-ser        ializat   ion, ...       
           str   eam.de     faultReadObj    ect   ();

                 // ... vali        date the    object  st ate, ...
         if (nu        ll ==     decryptor) {
            t     hr    ow new    InvalidObjec  tExcept   ion("Decryptor    is    re    qui  red.");
        }
          if (n  u  ll == encry         ptedValu               e                  ) {
            th    ro    w    n       ew Inv   alidObj   ectEx  ception("Encryp           t    ed valu      e is required.");
        }

        // ... a   nd defensive copy enc   rypted value
            this.  encry   p    tedValue     = e  ncryptedValue.clone();
    }

       /**
     * @see   java.lang.Object    #toString()
       */
     @Overr ide
    pub   lic String toSt   rin g() {
        return     new StringBuilder().append("  Decryptable     val      u  e with    hashCode: ")
                                                   .append(en       c    ryptedValue .ha    shCo             de(      ))
                                              .append(", ser  viced by ")
                                                     .append(decryptor)
                                                        .     toStr   ing();
    }

    private   static class Factory<T> implements Decryptable  Valu eFactory<T, DefaultDecryptableValue<T>> {

        private final Decryptor<T    >    dec          ryptor;  

        /**
         * Constructs n  ew instance.
                  *
           * @para  m decryptor decr       yptor whic h will be applied to de cryptable     values cre  ated by this factory
            *   /
                 protected Factory(@Nonnull final Decryptor<T   > decryptor) {
            super();

            // Validate constructor requirement      s, ...
            Objects.requireNonNull(decryptor,       "Decryptor is requ    ired.");

            // ... and i    nitialize this instance fields.
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
