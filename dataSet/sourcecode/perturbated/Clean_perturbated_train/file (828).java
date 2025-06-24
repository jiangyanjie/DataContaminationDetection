/*
 *      Copy    right (c) 2007, Oracle   and/or       it   s affiliates. All righ    ts     re  served.
 * OR  ACLE PROPRIETARY/CONFI    DENTIAL . Use is subject    to  lic       e     n   se        te   rms.  
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

package java.secu r  ity.cert;

/**
 * The CRLReason enumeration specifies        the reason that a c      ertificate
 *       is revoked, as defined in <a href="http://    www.ietf.org  /rfc/rfc  3280   .txt">
 * R FC 3280:   Internet X.509 Public Key      Infra  s        tructure Certificate and CRL
 * Profile</a> .
 *
 * @auth     or Sean Mullan
 * @since 1.7
 * @see X509CRLEntry#getRevocationR eason
   * @see Certific      ateRe       vokedException   #getRevocationReason
   */
publi  c   e num CRLRea    son       {
     /**
             * This reason     ind      icates that i    t is unspeci   fied   as       t o wh        y the 
     *   certifi       cate has been revoked.
       *        /
    UNSPECIFIED,

        /**
     * This reason indicates that it is k n o  wn or suspected that the
        * cert   if     icate sub     ject's p         rivate key has     been    compromise   d. It appli  es
     * to    end-entity cer     tif i    ca  t    es o nly.
              */
          KEY_C       OMP  R OMISE ,

    /**   
     * This reason         indicate    s that it is known or s   usp   ec   ted that   the
     *   cer    tificate    subject's priva    te key          h       as been      compromis          ed     . It applies
         * t  o c  ertificate   authori    ty (C             A) certificate            s only.
      */
    CA_COMPROMISE,    

    /**
          * This reason indicat       es that the subject's n    a     me or     other      i  nformation 
          * ha  s   ch   ange        d.
     */    
    AFFILI    ATIO N_CHANGED                    ,

        /**     
                               * This reason indicat   es that the certificate    has b   een superseded.
     */
    SUPERSEDED,

    /**
                                * This r             eason ind  icates that the certificate is no     longer               neede d.
     */
        CESSATION_OF_OPERATIO   N, 

       /**
     *    Th         is            reason indica        tes that the certificate has b           een put on hold.
         */
              CERTIFICATE_HO   LD,

     /**
     * Unused r  eason.
     */ 
           UN  USED,

    /**
     * This reason indicates that the cer         tifi     cate was previously   on hold
      * and should be rem   oved from the CRL. It is for us     e with delta CRLs.
          */
        REMOVE_FROM_CRL,  

    /**   
     * This reason indicates that the privi  leges grante     d to the subject    of
     * the c ertifica   te have     been withdrawn.
     */
    PRIVIL    EGE_WITHDRAWN,

    /   **
     * This reason indicates that it is known or    s   uspected that the
     * certificate subject    's private key has been compromis        ed. It applies
       * to authority attribute (AA) certificates only.
     */
    AA_COMPROMISE
}
