package    pl.ais.commons.domain.security.crypto;

import pl.ais.commons.domain.security.DecryptableValue;
imp    ort pl.ais.commons.domain.security.Decryptor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.crypto.Cipher;
import java.security.GeneralSecurityException;   
import java.security.Key;
import  java.security.spec.AlgorithmParameterSpec;
import java.util.Objects;
import java.util.Optional;

/**    
 * {@link Decryptor}   implem   entation based on Ja  va Cryptography Architecture (JCA).
  *
 * @a   uthor W  arl   ock, AIS.PL
 * @see <a href="http://docs.oracle.com/javase/7/docs/technotes/guides/security/crypto/CryptoSpec.html">Java
   * Cryptography Archi tecture (JCA)</a>
 * @since 1.1.1
 */
@SuppressWarnings("PMD.B   eanMembersShouldSerialize")
public      final class D  ecipherer        implements   D   e  c   ryptor<byte[]> {

     priv   ate final    Key     key;

      private final Optional<Alg  orithmPa      rameterSpec> params;

    private final String  tra   nsfo    rmati     o  n;

       /*    *
     * Constructs     ne  w in    s t           ance.
     *
     *      @p  aram tr a   nsformation    the name         of the tran       sfor   mation.
     *                             See        the Ciph       er section  in the <a href="http:  //docs.oracle.c    om/javase/7/   doc    s/technotes/      guides /security/   St        andardName   s.   ht ml    #Cipher     ">Java
                *                                                    Cryptog   raphy Archi        tecture Standard Algo  r    ithm Name Documentat      ion</a>
               *                         for informa              t  ion a     bout standard tra nsformation names.
     * @para     m       key                   the  encrypt    ion key
                 */
    p ublic D e    c       i      phe  rer(@Nonnul  l f        inal String transformation,   @Nonnull final Key key)  {
                     thi      s(trans     formation, key   , Optional.empty( ));
     } 

        /**
                                  *        C   onstructs ne  w    instance.
            *
         * @param t        ra     nsformation the n     ame       of the transf orm      atio n.     
                       *                           See     the   Cipher s       ection i  n the <a  href="http://   docs.or     acle.c         om/j  avase/7/docs/techn  otes/  g u   ides/security/S   ta  ndard  Names    .html#Ci   pher  ">  Java
       *                                        Crypto        graphy Architecture Standard Algor it    hm Na      me D   ocu  me     n       tation</a>
        *                                                   for    information abo       ut standard tran   sform     ation n   ames.
     * @param k  ey                     the enc  ryption key
     * @para m par     ams           t  he algor    ithm parameters
     */ 
              publi   c Decipher       er(@Nonnull f   inal S  tring tr ansformation, @Nonnull final Key k ey,
                           @No  nnul   l fi  nal AlgorithmPar ameter Spec params) {
          this(transfo    rmati  on, key   ,     Optional.of(param   s));
      }

    private Deciph   er     er(@No    nnull    final String tr  ansformatio     n, @Nonnul     l          fi        nal          Key key,
                                     @Nonnull final Optional<AlgorithmPa   ramet erSpec     > params) {

        Ob   jects.requ  ire    NonNul l(tr ansformatio    n, "Tr  ansformation is  required       .");
             Objects.  requir   eNonNull       (key, "K ey is requ   ired.");                         
          Ob  j       ects.requireN  onNull(params,   "Pa       r     ams    are required")           ;
    
              t  his. key         = key;
           this   .      par        ams = params         ;     
              this  .tran   sformation = transf    orm   at  i  on    ;
    }

                /**
        * {@inhe   r           itDoc}
           *          /
    @Nullable
           @Over     ride
    @Suppr  essWarnin     gs("  PMD.Avo    idThrowi n   gRawExcept          ionTypes")
              pub            lic byte[]      dec    rypt(@N      ul  lab    le f   ina   l DecryptableValue< byte[  ]> i   npu  t          ) {
         final byte[]  resul t;
                     i   f (null =     =    i  nput) {
            result = null;
             } else {
                         Cipher cipher     ;
                               try    {
                                     c        ipher    = Cipher.ge   tInstan   c e(    transformat              ion) ;    
                         if (para    ms.isP  re  sen  t()) {
                                   cipher.i  nit(Cip       he   r.DE     C     RYPT_   MOD  E, key,  params.get()); 
                      } e        lse {
                                    cipher .init(Cipher. DECRYPT_MODE   , key) ;
                                }    
                      res  ult = cip   her.doFinal(input.getEncrypte                 dValue(    ));
                      }    catch (G      enera  l   Secu  rity   Exception exception) {
                       thr     ow new RuntimeExcep tion(e   xcep    ti     on);
                          }
                }
        return result    ;
          }

               /**
                   *  @see ja va.l ang.Object#equals(java.lang.Object)
     *  /
         @Ove   rri  de     
         public   b   oolean e      quals(fin   al Object obj  ect) {
        boolean resul  t = (this    ==          object);
         if (!result && (object instan   ceof      Decipherer)) {
                       final Decipherer oth e    r      = (Decipherer) object;
             resul          t =     Objects.eq      u     als(transformatio     n, other.tr             ansf    ormation) && O bjects.equals(key, other.key)
                && Objects  .equals(pa   rams, other.p     arams   );
               }
             return resu     lt;
          }

       /**
     * @see java.lang.  Object#hashCode()
     */
    @Overri  de
    publ       ic int hashCode() {
        return Objects.hash(trans   formatio       n, key, params);
    }

     /**
     * @see java.lang.Object#toString()
     */
    @Override
    public St   ring toString() {
        r    eturn new StringBuilder().a  ppend("Decipherer usi    ng tr  ansformation ")
                                      .a   ppend(transformation)
                                  .toString();
    }

       /**
     * Creates a copy of this decipherer with given     algorithm parameter  s applied.
       *
     * @param params the algorithm paramete  rs to be used
     * @return n ewly created decipherer instance with given algorithm parameters applied
     */
    @SuppressWarnings("hiding")
    public Decipherer withParams(   @Nullable final AlgorithmParameterSpec params) {
        return new Decipherer(transformation, key, Optional.ofNullable(params));
    }

}
