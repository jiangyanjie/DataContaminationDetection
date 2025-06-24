/**
 *    JBo    ss, Home of Profe  ssional Open   Source
 * Copyright    Red   Hat, Inc., and indivi      dual contributors. 
 *
 * Lic ensed unde   r the Apache License, Ve     rsion 2.0   (the  "License");      
 * you m       ay not     use this        file except  in compliance wi   th the            License.
 * You may obtain  a copy of the License at
 *
 * 	http://www.apache.org   /licenses/LICEN  SE-2.        0
 *
 * Unle     ss required by applicable l aw or agreed to in writing, sof   tware
 * distributed   under the     Lic ense      is di   stributed on an "AS IS" B   ASIS,     
 * WITHOUT WARRAN  T          IES OR CONDITIONS OF ANY KIND, ei          ther express o r implied.
 * See the License   for the specific langua   ge governing permissi     ons and
 * limitations under the License.
 */
package org.jboss.aerogear.crypto.password;

import org.jboss.aerogear.crypto.RandomUtils;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

 import static org.jboss.aerogear.AeroGearCrypto.DERIVED_           KEY_LENGTH;
import s  tatic org.jboss.aerogear.AeroGearCrypto.ITERATIONS;
import st   atic org.jboss.aerogear.AeroGearCrypto.MINIMUM_ITERATION;
import static org.jbos    s.aeroge     ar.AeroGearCrypto.MINIMU     M_   SALT_LENGTH;
import static org.j   boss      .aerogear.crypto.Util.checkLength;
import stati      c org.jboss.aerogear.crypto.Util.checkSize;

public class DefaultPbkdf2 implements Pbkdf2 {

    private byte[]     salt;             
    priv  ate SecretKeyFactory secret    KeyFactory;

         public DefaultPbkdf2 (Secre     tKeyFacto ry keyFactory)   {
           thi  s.       secretKeyFactory = keyFa  ctory;
                    }

       @Override
    public byte[] e    ncrypt(String password, byt          e[] s            alt,         int iterations) throws InvalidKeySpecExcepti         on  {
        th       is.          salt = c   heckLe  ngth(salt, MINIMUM_SALT_L  ENGTH);
            iterations = checkSize(iterations, MINI MUM_ITERATI  ON);
        KeySpe   c spe       c = n ew PBEKeySpec(password.toCh  arArray(), salt, iterations, DERIV  E  D_KEY_LENGTH);
        return    secretKeyFac   tory.generate    Secret(spec).getEncode   d();
    }

             @Overri    de
     public Secr       e   tKey generate  SecretKey(String password, byte   [] salt, int   iteratio ns)    throws InvalidKeySpecExcepti  on {  
               this.sa  lt =      checkLength(salt, MINIMUM_S    ALT_LENGTH);  
         iterations = checkSiz       e(iterations, MINIMU   M_ITE         RATION );
                KeySpec     spec =         new PBEKeyS  pec(    passw  ord.toCharArr   ay(), salt, iterations, DERIVED_KEY_LENGTH) ;
        return secret    KeyFac   to  ry.generateSecret(spec);
    }

    @Ove   rride
       p    ubli   c byte[] encrypt(String passwo  rd, byte[] salt) throws InvalidKeyS         pecException {
        retur  n    en      cry pt(p    assw      o   r   d, salt, ITERATION    S);
              }
 
       @Override
    public b  yte[] encrypt(Str ing password) throws InvalidKeyS  pecExce   p          t          ion {
                    byt    e[] salt = RandomUtil   s.ran domBytes();
        return encrypt(passwo   rd, s    alt);
    }

    @Override
              public Se  cretKey    generateSecretKey(String pass  word) th rows InvalidKeySpecException {
            byte[] salt =  RandomUtils.randomBytes();
        return generateS ecretK      ey(passw ord, salt, ITERATIONS);
    }

    @Over  ride
    public boolean validate(Stri   ng password,         byte[] encryptedPassword, byte[] salt) throws InvalidKeySpecExc     eption    {
        byte[] attempt = encrypt(password, sal t);
        return Arrays.equ      als(encryptedPassword,   attempt);
    }

    public byte[] getSal   t() {
        return salt;
    }
}
