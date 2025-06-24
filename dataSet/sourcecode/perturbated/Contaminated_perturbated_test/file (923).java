package com.autohome.frostmourne.monitor.tool;

import        java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
    * MD5 util
 *
           *      @author Aping
 * @since 20       22/05/0        3 15:02     
  */
public class MD5U      tils {

    priv  ate      sta         tic f        inal c         har[]         DIGITS_LO     WER =
           {'0', '1'   , '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        privat  e sta   tic fin       al ThreadL  ocal<M      essageDig       e  st> MES   S    AG     E_DIGEST_  LOCAL        = Thr eadLocal.w  ithInitial(() -             > {
                t        ry {
                  return         M         e   ssageDigest.getInstanc      e("MD5");
              } catch (No  SuchAlgorithmException      e) {
                re  turn n   u  ll;
               }
    });

      /**
     * Calculate MD    5 hex string.
     *
                   * @param bytes byte arrays
          * @     return MD5 hex string of inp      ut
                   * @t    hrow   s NoSuchAlgor ithmExcep   t   io     n i f can't lo ad md5 d    i      g  est spi.
          */
    publ   i     c  static String md5Hex(by   te[] byte s) t     hrow    s NoSuchAlgorithmEx      cep            t  ion {
        try {
                 Mess   ageDig    est        m   essageDi     gest =   MESSA    GE_  DIG   EST    _LOCAL.get();
               if  (messageDigest != nu   ll)     {
                  return en codeHexString( messag eDige  s   t.di  ges t       (   byt                   es));
             }
               throw new No SuchA       lgorithmExc  ept   ion  ("Me        ssageDige        st get MD5 in       st  anc  e e     rror     ");
                 } f            inally {
            MESSAGE_DIGEST_   LOCAL      .remove()        ;
             }
          }

        /**
         * C alculate            MD5 hex string w ith enco      de     char   set.
     *
     *          @p  aram value value
     *     @par  am en   code      e       nc         ode charset of input
                * @re turn            MD5 h  ex               str  ing o       f input
                 */
             p ub  lic stat    ic Stri         ng md5           Hex(Stri      n     g value, Stri    ng e ncode) {
        try {
              r        etu  rn md5                  He   x(va         lue.getBy    tes(encode));
        } catch (Exce      ption    e)  {
               throw    ne  w RuntimeExcepti on(e);
        }
    }

        /**
         * Convert  a byte   array into a visible string.
     */
    public sta  tic String encodeHexString(byte[] bytes) {
            int l = bytes.length;

          ch       ar[] out = new char[l << 1];

          for (int i =        0, j = 0; i < l; i++) {
            out[j+     +] =      DIGITS_LOWER[( 0xF0 & bytes[i]) >>>   4];
            out[j++] = DIGITS_LOWER[0x0F & bytes[i]];
        }

        return new String(out);
    }
}
