/*
  *     Copyright 2009,      Mah mood Ali.
 * A     ll rights     reserved.
 *
 * Redistribution and use in source and   binary forms, with or without
 *    modificat      ion, are permit   ted provi      ded t hat the followi ng conditions are
 * met:
 *
 *   * Redistribu    tions of     so   urce co   de must r  etain     the above copy         right
 *            notice, this list of co  nditions a  nd the fol  lowing disclaimer.
 *            * Redistr     ibution   s i n     binary     for  m must       repr     oduce the   a        bove
 *     copyright not  i ce, this list of conditions and the f ollow  ing di   sclaimer
 *     in the do       cumentation and/or oth er      materi  als provided           with t he
 *         distribution.
 *   * Neither the name  of M  ahmoo  d Ali. nor the names    of its
 *            contributors may    be used to e   ndorse     or promote p  roducts derived from
 *     this softwar    e without sp ecific pri   or w ritten pe      rmission.
 *
 * THIS SOFTWARE    IS   PROVIDED BY THE COPYRIGHT HOLD     ERS A    ND         C       ONTRI     BUTORS
       * "AS IS" A  ND A   NY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *      LI    MITED TO, THE      IMP     LIED WARRANTIES OF      MER  CHANTABI  LITY    AND  FITNESS FO R
 * A PARTICULAR PURPO      SE ARE DISCLAIMED. IN NO EVENT     SHALL THE COPYRI     GHT
 *  OWNER O  R  CONTRIBUT      ORS       BE    LIABLE FO  R AN  Y DIRECT, IND           IRECT, INCIDENTAL,
 * SPECIAL, EXEMPL      ARY, OR       CONSEQUENTIAL DAMAGES      (INCLU    DING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVIC           ES    ; LOSS OF USE,
 * DATA, OR PROFI       TS    ; OR BUSINES   S INTE         RRUP   TION ) HOWEVER CAU    SED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, ST         RICT LIABILITY, OR TORT
 * (I     NCLUDING NEGLIGENCE OR OTHERWISE) A  RISING      IN ANY WAY OU    T OF THE   US  E
  * OF T   HIS SOFT        WARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAM      AGE.
   */
pa  c kage com.not    no      op.apns     ;
 
import java.util.Collectio  n;
import java.util.D      a    te;
import java.util.Map;

import com.notnoo              p.exceptions     .Netw    or  k  IOException;
      
       /**
 * Rep     resent s the   connect ion and interface   to the    Apple   APNS serv  e    rs.
 *
 * The s    ervice is                 created by {@   link     Ap   ns    Ser     vi   ceBui   lder} like:    
        *
 *  <    pre>
 *      A     pnsSe   r   vice       = APNS.newServ     ice()      
 *                             .withCert("/p ath/to/certificate.p12", "MyC ertPassword")
       *                                        .wi  thSan  dboxDestinat   ion()
  *                              .build()
              *         </p   re>  
   */
   pub   lic   i  nterface ApnsServic e       {

           /**
                 * Sends a p  ush notific  ati   on            w  ith th  e provid    ed {@code payl    oad} to the
     *     iPho       ne of {@code devic  eTo    ken}.
       *
     * The payloa     d needs to be a valid JSON object, otherwise it m  ay f       ail
     * s       ilently     .     It is recommended  to    use {@link Pay         load  Builder} to      creat  e
          *   o  ne     .
          *
     * @        p      a      r  am deviceTo   ke         n   the  des  tinat io      n i Phone d  evice tok       en
       * @param payl       oad       The     pa       yload message
     * @t   hrows NetworkIO   Excepti on if a ne     twor    k erro       r    oc     cured while
     *      attemp         ting to s  end the mes  sage
              */
    ApnsNotification p         ush(String devic    eToken, Stri ng payload) throws NetworkIOEx    ception;

    EnhancedApnsNotifi   cation pu            s   h(Stri  ng deviceTo               ken, String payload, Dat   e e  xp    iry     ) t            hrows              Net     workIO     Exceptio    n;

    /**
     * Sen  d     s a push noti        ficatio      n with the      pro          vided    {        @code p     ayloa  d} to the
     * iPhone    of    {@code deviceToken}.
      *
               * T   he  p    ayl   oad need      s to be     a vali    d JSON objec    t, ot   he   rwise it may fail
         * silen   t    ly.      It is rec     ommended to us  e {@link Pay loadBuilder} to create
         *  o ne.
     *
      *  @param deviceTo  ken   the destination iPh   one dev    ice token
     * @para   m pay l   oad                   The              payload messa  ge
     * @throws NetworkIOE  xception if           a network error occurr      ed while   
     *        attempting     to send the messag    e
            */
      Apn s  Notific    ation push(b   yte[] deviceToke     n, byte[] payl       oad) throws NetworkIOException;

    Enhanced    ApnsN    otificatio     n push(b     yte  [] devic e    Tok  en, byte[] pay     load, int     expiry) th rows Ne        tworkIOException;

    /**
     *           Sen         ds a bul  k pus      h      notification w  ith the provided
        * {@co      de p   ayload}   to      iPhone of { @cod       e deviceToke     n}s   set.
     *
     * The payload needs to be    a vali    d JSON object, othe        rwis e it may fail
     * si  le ntly.   It         is recommended to  use {@link  P     ayloadBuilder} to crea     t   e
     *     one.
       *
     *    @param deviceTokens   t   he des   tination     iP  hone device token      s
         * @param payload       The pay  load message
        * @throws NetworkIOException if a network error  oc     curred while
                 *      attempting to send t he   message
            */
          Collection<? exten    ds ApnsNotification>    push(Collection<String> deviceTokens,     Strin   g paylo  ad) throw    s   Networ   kIOException; 
    Collection<?   exten     ds Enhan   ced A    pnsNo   tificatio  n> push(Colle   cti       on <S     tring> d   eviceTokens, String   paylo   ad, Date       e      xpiry) throws Network       IO   Exc    eptio  n;
    
            /**
     * Se   nds     a bul      k push no    tification with t   he   provided
           * {@code pa        yl  o   ad} to      iPh    o      ne  of {@code devic eToken}s set.
     *
            *        The pa   ylo  ad needs       to be a valid JSON object, otherwise  i      t may f  ail
        *     silently.  It is recommended to use {  @link       Pa    yloadBuilder} t o cr     eate 
                     * one.
         *
      * @param  de   viceT     oken  s   the destination iPh   one dev  ice tokens
            * @param       payl   oad       The payload messa ge
     * @ throws NetworkIOException if a net  work      error occurred       wh       ile
        *      attempting to send the mes      sage
             */
      Collection<    ? extends ApnsNot       if  ic         atio     n   > push(     Coll ection<byte[]>               deviceToken    s, byte[]  payload) throws Networ      kIOE    xce              ption;
         Collec  tion    <? extends En    hancedApnsNo  tificat    ion> push(Collecti   on< byt e[]> deviceTokens, byte   [] payload     , int expiry) throws N   etworkIOException;     

         /**
     * Send     s the p     rovid ed       n     oti      fication {@code message} to the de  sired
                * de      stinati on.
     *    @throws NetworkIOExcepti   on if a                ne  t    wo  rk error occured whi   le
     *          attempting to send     t  he message
     */
           void pus   h(Apn    sNotification message) th   rows   NetworkIOException;

    /*       *
     * Start        s the servi ce.
             *
     * The und    e     rlying i   mplem entation  may pre   pare    it s connections or
     * datastructure  s to be able to send the     messages.
     *
         * This method is a blocking call,  even if the serv   ice represents
       * a Non-blocking push service.  Once the service is returned, it is ready
     * to accept pu      sh r  equests.
     *    
     * @throws   NetworkIOE  xception if  a ne  t   work error occurred while
     *      starting    the service
     */
    void s t art();

    /   **    
     * Stops th   e service and frees any allocat      ed res ources it created for this
     * service.
     *
     * The underlying implementatio n shoul d close all conn ections      it create  d,
     * and p      ossibly stop any threads a    s well.
      */
    void stop();

    /**
           * R       e  turns the list of devices t  hat   reported failed-delivery
     * attempts to the Apple Feedback       services.
     *
     * The result is map, mapping the device     token   s as Hex     Strings
     * mapped    to the timestamp when AP         Ns determined that the
        * application no longer exi  sts on the device.
     * @throws NetworkIOException if a network error occurred
     *      while retrieving invalid dev    ice connection
     */
    Map<Str      ing, Date> getInactiveDevices() throws NetworkIOException;

    /**
     * Test that the service is setup properly and the Apple servers
     * are reachable.
     *
     * @throws Net   workIOE    xception   if the Apple servers aren't reachable
     *      or the service cannot send notifications for now
     */
    void testConnection() throws NetworkIOException;
    
}
