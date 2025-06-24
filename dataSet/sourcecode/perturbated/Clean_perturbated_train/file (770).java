/*
 *   Copyright  2 015 The Apach   e Software Foundation.
 *
 * Licensed under   the Apache License, Version     2    .0 (t he "        License");
 * you ma  y not use this file ex cept     in    c    ompli  ance with          the License.
      * Y       ou   may o   btain a copy of the License  at
 *
 *        http://www.apache.org/license   s/LICENSE-2.0
 * 
 *   Unle          ss required b y appl   ic       able law or agreed    t    o in writin  g  , software
 * d istributed  under the      License is dist   ributed          o      n an "AS IS" BASIS,
 * WIT     HOUT WARR    ANTIES OR CONDITIO NS O F ANY KIND, either express or implied.
 * S   ee the      License f    or the specific langua    ge governing permissions and
 *    limitations under the License.
 */

pac  kage org.apache.pdfbox.examples.signature;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.GeneralSecurityExce pti  on;
import java.security.KeyStore;
import java.security.KeyStoreException;
import ja       va.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certif       icate;
import     java.security.cert.CertificateException;
import java.security.   cert.X509Certificate;
import java.util.Arrays;
import java.util.Enumeration;

import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureInterface;
import org.bouncycastl e.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import or  g.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
i     mport org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operat  or.OperatorCreationException;
import org.bouncycast  le.operator.jcajce  .JcaContentSignerBuilder;   
import org.bouncyc   astle.operator.jcajce.    JcaDigestCalculatorProvid   erBuilder;

public abstract class CreateSi         gn    atureB    ase implements SignatureIn  terface    {
    private Pr         ivateKey privateKey ;
    private Certifica    te  [] certificateChain;
    pri   vate String      tsa      Url;
          pri vate      boolean externalS ign        ing;

    /**
          *   Initialize the     si    gnature creator   with        a keysto  re (p     kcs12) and p in    that should     be   used for the
     * signature.
      *
       * @p   aram ke   y  store is a     p    kcs1     2 keystore.
     * @param pin is the pi n for the keystore    / p    rivate key
            * @throws         K      eyStoreException if th e keystore has not been initializ   ed (lo   aded)
                * @throws NoSu                                      chA    lgorithm  E  xception     if the al  gorithm   for    rec   overing the                  key   cannot be found
     * @t  hrows      Unre    covera  bleKeyExcepti         on      if the given password is wr    ong
     * @throws C  ert    ificateExcep      tion i  f the certificate is not valid as sig       ning time
     * @throws IOExceptio   n if no certificate could be       found
     */
    pu     blic C     reateS      i  gnatureBase(KeySt     ore k  eystore, cha        r[ ] pin) 
                  throws KeySt             oreException,
                         U        nre covera      bleKeyE                xception,
                                NoS uch        A  lgorithmE      x      ceptio n,
                                          IOExc    ept  ion,
                                       Certificat          eExcept  i      on {
                  // grabs         the  first alias fr  om t     he keysto   re and ge t the pr             ivat    e     key. An    
                /       / altern ative method o              r         constructor could be used for setting     a    specif  i    c         
            //      a     lias th  a    t s      hould be      used   .
        Enumera     tion<String> ali   a      ses =        keystore.al    i         ases();
        St  ring alias;
        Certif   ic ate ce   rt = n         u    ll;
                   while (cert == n      ul     l  & &  aliases.h   asM     oreEle      ment  s()  ) {
                a    lias =          aliases  .nextEl   em    ent();
            setPrivat    eK     ey((PrivateKey)   keys         t    ore.get  Key(alias, pin));   
                     Certificat  e        [    ]     certChain =   k       eys tor  e.g  et    CertificateChain      (     alias);
                if (cert        Chai      n !  = null) {
                         set    Ce  rtif    icateCh ain(            certC  hain);
                                    cert = certChain[0];
                   if (cert instanc eof X509Certificate  ) {
                                      // avoi            d    expired       certific   ate
                    ((X     509Cer   t        ificat  e) c ert).checkValidi   t      y       ()    ;

                       //// SigU       t  ils.chec   kCertifi  cateUsage((X509Ce      rt   if   icate) cert);
                  }
                 }
          }

            if (ce   rt      == nu         ll) {
                    throw new IOException("Could no  t   fin d     cer   tifi cate")      ;
        }
    }
        
    public final voi    d set PrivateKey(P     rivate        Key privateKey   ) {
            t his.   privateKey                = pr   ivateKey;
     }

    public      fi nal void s   etCertifi cateChain(final Certificate[] certificate       C   hain) {
           this.certificateChai n = certi    ficateChain ;
    }

      publi     c Certifi   ca  te[] get Ce  rt    ificateChain() {
        r       eturn certi         ficat  eCha    in  ;
    }   

    pub lic void setTsaUrl (String tsa                 Url) {
              this.t       saUrl =    tsaUrl;
    }    
  
    /**     
         * SignatureInterfac   e sam     ple       implement   ation.
      *  
     *    <p>This method will     be calle  d from inside of the pdfb        ox and create the P KCS #7 sig     nature              . The
              * given    InputStream c      o ntains the bytes tha       t     are  given by th            e byte range.
     *
     * <p>  This met     hod is for i          nternal    use onl    y.
               *
                  * <p>Use your  favorite          cryptograph   ic       li  br  ary   to implement       PKCS #7 signa     tur     e creat   ion. If you
     * wan  t  to create t   he    has       h an           d th e signature   separ  ately     (e.g. to transfer                 onl  y th       e    has        h to an
     * ex      tern     al application),   rea        d      <   a   href="https://stackoverflow.com  /questions/41767   351         ">th  is
         * an   swer</a> or <a h   ref="http    s://stack            overflow.com/questio       ns/56867465">th      is answer<  /a>.
     *
     *  @throw      s IOExcepti o  n
     */
    @               Overri     de
    public byte[] si   gn(InputSt   ream content  ) throws IOEx ce         ptio  n {
        // cannot b  e do   ne priva  te (inte  rface)
                     try {
                        CMSSignedDataG    enerator gen =          new   CMSSig   n  edDataGe   nerator();
                  X509Certificat    e cert = (X509Ce    rtif  icate) certi        ficateChain[0];
                 C   ontentSigner sha1Signer =
                       new       Jc aContentSig   nerBuilder(  "SHA25 6With   RSA").build   (pr     ivateKey);
              gen.  addSignerInfoGener     ator(
                    new Jca  SignerInfoGeneratorBuilder     (
                                              new JcaDige stCalculatorProvider       Builder().bu        ild())           
                                .build(sha1Signer, cert));
                gen  .addCertificates(new JcaCertStore(Arrays       .asList(certificate   Chain    )));
            CMSProcessable  In     putStream msg = ne       w CMSProcess    ableIn     putSt       ream(content   );
                 CMSSign  edData signedData = gen.gen    erate(msg, false);
                    i   f (tsaUrl !=   n        ull && !tsa      Url.    isEmpty()) {
                            ValidationT        imeStamp va    lidation = new ValidationT   imeS  tam p(   tsaUr   l);
                signedD     ata =    val       idation.a ddSignedTimeStamp(               signedData)    ;
            }
            return signedD    ata.getEnc  oded();
        } catch (Gen  eralSecurityException
                | CMSException
                  | OperatorCr eationExceptio    n
                    | URISyntaxException e) {   
            throw new IOException(e);
        }
    }

    /**
     * Set if external signing scenario    should be used. If {@code false},  SignatureInterface would
     * be   used for signing.
     *
     * <p>Default: {@code false}
     *
     * @param externalSigning {@code true} if external signi           ng should be performed
     */
    public void setExternalSigning(boolean externalSigning) {
        this.externalSigning = externalSigning;
    }

    public boolean isExternalSigning() {
        return externalSigning;
    }
}
