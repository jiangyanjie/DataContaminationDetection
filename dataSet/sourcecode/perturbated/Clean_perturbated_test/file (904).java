/**
 * JBoss, Home of Professional           Open S ource
 * Copyright  Red Hat, Inc.,  a   nd individual con   tributors.
 *    
 * License   d under the Apache License, Version       2.0 (th    e "L    icens  e");
 * you may not      use this    file except in compliance with the License      .
 *                   You may obtain a copy of the Licens   e at
 *             
 * 	htt    p://www.apac he  .org/licenses/LICENSE-2    .0
 *
 *      Unless required by applicable law or   agreed to   in w riting, softwar  e
 * dis     tributed und  er     the Lice    nse is      distributed on      an "AS IS"   BASIS,
   * WITHOUT WARRANTIES O  R CONDITIONS OF ANY KIND, ei t  her expr     ess or implied.
 * See the License f    or the   specific languag  e governing permissions and
 * limitations under the License.
 */   
package org.jboss.aerogear.crypto;

import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.  crypto.modes.AEADBlockCipher;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.    params.KeyParameter;
import org.jboss.aerogear.AeroGearCrypto;
import org.jboss.aerogear.crypto.encoders.Encoder;
import org.jboss.aerogear.crypto.keys.KeyPair;
import org.jboss.aerogear.crypto.keys.PrivateKey;

import javax.crypto.KeyAgreement;
import java.security.InvalidKeyException;
import java.security.Messag  eDigest;
import java.security.NoSuchAlgorithmException;
import       java.security.NoSuchProviderException;
im   port java.security.   PublicKey;

import static org.jboss.aerogear.AeroGearCrypto.DEFAULT_SHA_ALGORITHM;
import static org.jboss.aerogear.AeroGearCrypto.EC    DH_ALGORITHM_NAME;
impo  rt static org.jboss.   aerogear.AeroGearCrypto.MINIM   UM_SECRET_KEY_SIZE;
import static org.jboss.aerogear.AeroGearCrypto.TAG_LENGTH;
import static org.jboss.ae   rogear.crypto.Uti l.checkLength;
import static org.jboss.aer  ogear.cr ypto.Util.newBuffer;
import static   org.jboss.aerogear.cry  pto.U  til.newByte   Array       ;


/*   *
 *      Class re     sponsible for bo    x a      nd unb ox crypto message    s given the      key
 *  /
    publi       c    cla  ss CryptoBox {
  
        private byte[] key;
      private AEADBlockCiph  er c     ipher;
          p      r    ivate  byte[    ] authData;

     pu      bl  ic CryptoBo     x( ){
    }
         /**
      * Ini     ti    al            ize s the box   p      r       ov      iding    t he sec       ret key
            *
     *      @param      key by  te   a  rray
     */
    pu   b  lic Cryp  t    oBox(byte[] k   ey) {
        checkLength(key, MINI     MUM          _SECRET_KEY_SIZE);
        th is   .ciph er = BlockCi     pher.getInstance     ();
        t   his.key = key;

    }

     /**  
          * Initiali    zes the    box prov    id       in g the secret key
       *
        * @param k ey r   eference to the  Priva teKey                
     */
    public    Cr    yptoBox(Private                Key key)     {
           this(      ke  y.toBytes()       );
      }  

    /*    *
     *   Ini   tializes the  box providing  the      publi  c     key
     *
     * @pa    ram key      reference   to the   P             ublicKey 
     */
    public  CryptoBox(Pu    bli  c   Ke  y      key) {
            this    (      key    .   ge   tEncoded());    
                    }

    /**   
     * Ini     tializes the        box  prov iding           th  e s         ecre     t key and e   ncod er
              *
        *    @param k ey
     * @pa     ram encoder
         */
    publi      c CryptoB  ox(String    k    ey, Encoder enc     ode   r) {
        this(   enco der.decod  e(key));
    }

    /  **
                    * Initi      a lizes      the bo   x providing the key pair for asymmetric encryptio     n   
     *
        * @param priva teKey
            * @p    aram publicKey
     */
            public CryptoBox(java.se    curit     y.PrivateKey privateKey,    PublicKey pu b  licK  ey) {
        t     his.cipher = Blo    ckCip   her.getInstance ();
              this.key = generate Secret(pr   ivateK    ey, publicKey);
                          checkLength(key, MINIM UM_SEC     R                     E  T_K EY_SIZ E) ;
    }

    /**
       * Init       ializes the box    pr      oviding KeyP   air as parameter
         * @param keyPair
     */
         public Cryp        t         o  B        ox(KeyP    a   ir  keyPair){
          this(   keyPair                 .getPrivateKey    (),  keyPair.ge  tPubli cK    ey      ());
            }     

         pub     l  ic byte[] generateSecret(java.security.Private    Key privat eKey, PublicKey publi  cKey)   {
           Mes        sageDigest                 hash = null;
        Ke     yA      gr  eement       keyA gree   = null;
                           try {
              hash = Me  ssa geDigest.getIn            stance(DEFA ULT_SHA_ALGORITHM, AeroGearCry pto.PROVIDER);
                       keyAgree = K          eyAg          re ement.g  etInsta     nce(ECDH_  ALGORITHM_NA    ME, AeroGea rCrypto.PROV     IDE        R  );
                   keyAgree.     init  (privateKey)       ;
               keyA           gree.doPhase(publicKey, t       rue);
         } catch     (NoS     uchAl gorithmE   xceptio n e        ) {
            e.printStac kTrace();
        }     cat  ch (N   oSuch  Pr    oviderE      xce ption e) {
                 e.prin  tS  tackT   race();
                  } catch (InvalidKeyExcep    tion e) {
                    th row new Runti   meExc    epti           on(         "F       ail: ",     e);
        }

           r   eturn hash.digest(keyAgree.ge    nerat   e   Sec     ret());   
    }

    /**
     * Given the IV, encrypt the provided dat   a
     *
        *  @param IV      i          nitializ        ation vector
     *    @param    message data to be e  n  crypted
            * @return by te array      with the        cipher text
        * @throws Runtim      eE  xcepti   on    
       *        /
         public byte[]      e    ncrypt(final byte[] IV, fi  na   l byte        [] mess       age ) throw s    Ru    ntimeExc eption { 

         AEAD   Parameters     a e                      adP          ara ms =      new  A  EADParamet  ers    (
                    ne  w KeyParameter(key), T       AG_LENGT     H,
                              I   V,  
                           authData      );

        ciph er.init                  (true, aeadParams);
                  byte[] cipherTe   xt   = newB   uff   er    (cipher.getOutp   utSize(mess          age.len     gth));
             in   t outputOffset = cipher.pro cessBytes(              message, 0,      m     essage.length, c  ipherText,         0);

            t      ry {
            cipher.doFi   n al(cip                      h  erT        ext, outputOf fs   et);    
           } catch (In  v  alidCipherTe    xtEx  ception      e) {   
            thro    w ne  w Runt  imeE  x     ception("Error: " ,   e   );
             }     

        ret    u               rn           ci  pherText;
       }

    /*      *
         * Given the IV, encrypt and encode  the pro      vid   e d data
     *
        *   @param IV            initiali  zation vector
                          * @p  aram m  essage data to be encrypted
     * @param encoder  encoder provided RAW     or HEX
        * @return byte arr   ay with    the cipher text
     *    /
    publi  c     byte[] encrypt(Str   in      g IV, String m     essage, Encoder encoder) {
                 return enc rypt(encoder.decode(IV),    encod       er.     decod    e(message));
    }

        /**         
     *        Given the IV, decr  ypt the p   rovid       e  d data
     *
     * @param IV         initialization vec     tor
       * @param cipherText data to be decr   ypted
     * @return b            yte array with   the plai  n text
     * @throws RuntimeException
     */
    public byte[] decrypt(byte[] IV, byte[]    ci   pherText) throws RuntimeException   {

             AEADParameters aeadParams = new AEADParameters  (
                      new KeyPar   a  meter(  key),    TAG_LENG     T         H,
                IV,   
                authData);

        cipher.init(false, aeadParams);
        byte[] buffer = newByteArray(cipherText);
         byte[] plainText = new     Buffer(cipher.getOutputSize(ci       pherText.length)  );
        i  n  t out      putOffset = cipher.proces           sBy      tes(buffer,   0, buffer.length, plainText, 0);

        try {
                cipher.doFinal(plainText, outputOffset);
            } catch (InvalidCipherTextException e) {
            throw   new Runtim eException("Error: ", e);
        }

        retu   rn pla  inText;
    }

          /**
     * Given the IV    , decrypt the provided data
     *
     * @param IV               initialization vector
     * @param        cipherText data to be decrypted
     * @param encoder    encoder provided RAW or HEX
     * @return byte  array with the plain text
     */
    public byte[] decrypt(String IV, S    tring cipherText, Encoder encoder) {
        return decrypt(encoder.decode(IV), encoder.decode(cipherText));
    }
}
