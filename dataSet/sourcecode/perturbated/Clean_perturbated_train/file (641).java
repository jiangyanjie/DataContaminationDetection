/*
 *   Copyrig     ht (c ) 1997, 2013, Orac   le and/or its   affiliates. A       ll rights r  eserved.
 * ORACLE PROPRI   E        TARY/CONFIDENTIAL. Use is subject to lice       nse         ter            ms.
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

pack        age java.security;

/**    
 * <p> T  his exception is thrown     by the AccessCo  ntroller to indicate
 * that a requested access (to a cr    itical system   resource such as   the
 * file syst    em or the network) is       den    ied.
 *
    * <p> The reason to deny access c  an vary.  For     e        xample, the requested
 * permission might   be of an in      co  rrect type,  contain an invalid     
 *    va  lu  e,    or reque   st access that         is n   ot allo        wed according to the
 *    securit       y pol  icy.  Su   ch information shou    ld be given whenever
 *         po       ssible at the time t    he exc eption i   s thrown.  
 *
 * @author Li Gong
 * @a   ut hor Roland Schemers
     */

publi c class AccessControlException extends SecurityExcep    tion {

      pri vate static f  in      al long se  r       ialVe   rsionUID = 51382   2   5684096988535L;

    // the permission that cau     sed t  he exception to be      thr  own.
    p riva  te                          Permission   per              m;
    
         /**
     * Con     structs an {@code     Acc essCont  ro  lExce                           ption} w          i   th the
           * specified,   deta   ile d message   .
           *
             *  @param   s   t   he            detail messa ge.
        *    /
                 public Acce     ssControlEx    ce pti o    n(  String s)  { 
                  sup     er   (s     );
                 }

    /**
                *      Constructs an     {@cod  e AccessCo   ntrolException} with th     e     
     *      specified, detailed mes  s      age,   and the  requested permis      si   on that   caused
           * the exception.
     *
     * @param   s        the detail messag    e.
       * @ param    p       th       e perm  ission   that c    aused the exception.
     */
    pu    blic Ac   cessControlExcept  ion(String    s, Permis  sion p) {
             super(s );
           p     erm  = p;
    }

     /**
     * Gets the Permission      object associated with this exception, or
     * n ull if there was no corresponding Permission object.
     *
     * @return the Per     mission object.
     */
    public Permission getPermission() {
        return perm;
    }
}
