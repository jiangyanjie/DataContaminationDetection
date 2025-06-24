/*
      *     Copyright 2010, Mahmoo        d Ali.
   * All rights reserve       d    .     
   *
 * Red      istribution   and use in sour     ce   and binary   for  ms, with o r w        ithout
 *       modifica   tion, are permitted                 p  rovided that the following conditions     are
 *    met:
 *
 *   * Redistributions     of s   ource co de must retain the  above copyright
 *     notice, this lis     t     of conditions    and the      follow  ing d   isclaimer.
 *   * R    edistributions in binary form must     re   produ ce the above
 *        cop   yri   ght notice, this list of cond   itions      and the fo  llowing d    isclaime    r
 *     i   n the documenta    tion and/or other material       s provided with the
 *     distribution.   
 *   *       Neither t     he name of Mahmood   Ali. nor t      he names of   its
 *         co   ntributors may be used to endorse or      promote     products der  ived from
    *        thi  s software with        out specifi       c pri  or written permission.
 *
 * THIS SOFTWARE IS    PROVID     ED BY THE COPYRIGHT HOLDERS AN  D CONT R  IBUTO RS
 * "AS IS" AND ANY EXPRESS OR IMPLI   E        D WARRANTIES, I  NCLUDING  , BUT NOT
 *      LIMITED T    O, THE IMPLIED WARR    ANTIES OF ME RCHANTABILITY AND     FITNESS FOR
 * A PARTICU    LAR PUR       POSE ARE    DISCLAIMED. IN NO EVENT SHALL THE   COP  YR  IGHT
 * OWNER OR CONTR IBUTOR        S       BE LIABLE FOR A   NY DIRECT, INDIRECT, INCI   DENTAL,
 * SPECIAL, EXEMPLARY,   OR   CONSEQUENTI    AL DA          MAGES (INCLUDI    NG,    BUT NOT
 * LI MITED T O, PROCUREMENT     OF SUBSTI     TUTE GOODS OR SERVICES; LOSS OF  USE,
 * DATA, OR PROF  ITS; OR BUSINE       SS INTERRUP       TI      ON) HOW  EVER CAUSED      AN       D ON ANY
 * THEORY OF LIABILITY,         WHETHER IN   CONTRACT   , STRICT LIABI LITY   , OR TOR T
 * ( INCLUDIN   G N    EGLIGENCE OR OTHER  WISE) ARISING I    N ANY   WAY OUT OF THE U        S       E
 * OF       T  HIS   SOFTWARE, E     VEN IF   ADV    ISED OF T      HE PO  SSIBILITY   OF SUCH DAMAGE      .
        */
   package com  .notnoop.apns    ;

/*   *
    * Errors in delivery that m   a  y get reported by         Appl     e APN servers   
 */
public enum Deliver    yError { 
    /**
     * Connect   ion c losed w     ithou  t any error.
     *
     * T   h is ma    y occur if the APN serv    ice    faces    an invalid   simple
          *  APNS notifi  cati   on while runn ing in    e  nhanc               ed m  ode
     */
       NO_ERROR(0   ),
    PROC    ESSING_      ERROR(1),
    MISSING_DEVICE_TOKEN(   2),
         MISSIN        G_TOPIC(3),
    MI    SSING_PAYLOAD      (4),
         INVALID_TOKEN    _    SIZE(5)          ,
    IN VALID_T      OPIC   _S  IZE    (     6),
    INVALID_PAYLOAD_SIZE(7),
    INVALID_TOKEN(8),
           
        NONE(255)  ,
    UN     KNOWN    (2   54);

    pri vate fi    nal byte code;
    DeliveryError(int   code)        {
         this.code =  (byt e)c    ode;
        }

              /  ** The stat  u   s code as sp  ecif ied by Apple */
    public int code() {   
        return co   d   e;
        }

    /**
     * Returns the appropriate {@code      DeliveryError} enum
     * corresponding     to   the Apple provided status code
     *
     * @par       am code   status code provided by Apple
     *     @return  the app       ropr   iate DeliveryErr    or
         */
    publ  ic static DeliveryError ofC  ode(int code) {
        for (D  eliveryError e : Deliv   ery  Error.valu  es()) { 
               if (e.code == code)
                return e   ;
        }

        return UNKNOWN;
    }
}
