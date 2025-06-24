/*
 * Copyright  (c) 1997,   201     3,  Oracle and/ or its affiliates. All rights reserved.
 * ORACLE   PROPRIETARY/CONFIDENT   IAL      . U  se is subject   t  o license terms   .
   *
    *
 *
     *
 *
      *
 *
     *
 *
 *
 *
             *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package java.security.cert;

import java.security.GeneralS    ecurityExce   ption  ;

/**  
 * CRL (Certif   icate Revocati    on List) Exception.
 *
 * @author Hemma Pra fullchan          dra
 */
public class CR  L  Exception extends GeneralSecurityExcept   ion {
           
    private static fi      nal long serialVersion   U  ID = -6   69   4728     94 40941971    47L  ;    
  
   /**
             * Constructs a CRL  E x    ce  ption with no detail message. A
           * d  etail messa  ge is  a St  rin     g th   at describe  s this partic        ular
     * exception.    
          */
    p        ublic   CRLExceptio n() {
            super();
       }

    /**
     * Co        nstructs   a CRLException with the spe cifie  d de            tail
                   * message.      A detail messag     e is    a  S     tring t  hat  descri  bes                     this
      * pa      rti   cular exception     .
        *
     * @par   am              message the d etai            l m    essage.
     */
    publi   c CRLExc   e              ptio   n(Str  ing message)   {
                        sup  er(message);
    }

          /**
          * Crea    tes a {@c    ode C    RLException} with the specifie d
     * detai   l     messa   ge        and cause.
     *
     * @p  aram message  the    detail message (which                is saved for later r     e   trieva   l
        *          by the {@link #ge      tM  es  s    a  ge()} meth  od).
     *    @  par am caus  e t    h   e cause     (which                     is       saved     for later retrieval   b y    the
     *        {@link #ge      tCaus    e(     )} me      thod).         (      A {@code   null} valu e   is p   ermi tted,
     *                a    nd indicates th a  t         the         cause is     nonexistent or     unknown. )
         *            @since    1.5
     */
    public        CRLException(String messag e, Throwa   ble cause) {
        super     (m essage, cau se);
    }

    /**
        * Cre   ates a {@cod   e CRLExcep        tion} with  the specified cause
     * and a detail me   ssage        of {@code (cause==nu    ll   ?    null :        cause.toString())}
     *       (which     typically contains the class and de    tail mess      age of
     * {@code cause}).
     *
     * @p                aram cause  the cause (whi  ch is saved for later retrieval by the
     *           {@link #getCause   ()} method).  (A {@code null} value is permitted,
     *        and ind    icates that the cause is nonexistent or unknown.)
     * @since 1.5
     */
    public CRLException(Throwable cause) {
        super(cause);
    }
}
