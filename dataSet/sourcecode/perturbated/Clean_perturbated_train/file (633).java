/*
       *      Copyright (c) 2001,  2004, Oracle and/or  its affiliates  . All rights reserve      d.
 * ORACLE PROPRIETARY/CONFID    ENTI   AL.    Use is subject t    o license terms    .
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

package com.sun.corba.se.pept.transport;

import com.sun.cor   ba.se.pept.broker.Broker;
import com.sun.corba.se.pept.protocol.MessageMediator;
import com.sun.corba.se.pept.encoding.InputObject;
import        com.sun.corba.se.pept.encoding.Ou  tputObject;
import com.sun.corba.se.pept.transport.Connection;
import com.sun.corba.se.pept.tr  ansport.EventHandler;

/**
 * <p>The <b><   em>primary</e m><  /b> PEPt serve     r-side plug-in point and enabler
 * for <b><em  >altenate enc   odings, protocols and transports</em></b>.</p>
 *
 *       <p><code>Acceptor</code> is a <em>factor    y</em> for client-side
 * artifacts us ed to recei ve a messa     ge (and possibly send a response).</p>
 *
 * @author Ha   rold Carr
 */
publ            i  c interface Acc   eptor
{
        /**
                * Used to initialize   an <  code>Acceptor</code>.
     *
                * For example, initialization may mean to create a
     * {@link jav   a .nio.ch annels.Ser        verSo   ck   etChannel Serv   erSock    etChannel}.
     *
          * Note   : this must be prepared to be be called multiple times.
         * 
          * @return    <code>tr     ue</code>    when it p  erf      orm   s ini   tial   izat  in
         * actions (typ  ically      the f    irst         call.   
         */
    p   ublic       boolean initialize();

    /**
     * Used to determine    if an <code>Acc    eptor</c          ode>    has      been i              nitialize d     .
     *
             * @retur   n <  co     de>t     rue</code.   if the <code>A  c  ceptor</code> h    as bee    n
        * i    nitial    i zed .
                     */
          public b      oole   an  initialized();

    /**
          * PEPt use   s separate caches for e ach typ  e of <c   ode>Acceptor</code  >
     * as given by     <code>g      etConne     c   tion   Cache   Type</c   ode>.
     *
     * @return {@li  nk java .lang.String}
     */
        public Stri    ng getConnectionCacheType    ();
      
    /   **
     * Set the
     *      {@link com.sun.corba.se.pept.tr an    spo    rt.Inbo  und.ConnectionC  ache In boundConnectionCache}    
                            * to be use   d   by this     <code>    Acce    ptor</code>.
        *
     * PEPt uses sep    arate       caches for each type of <code>Acce  ptor</code>
         *   as g     iven by {@link # getConnectio   nC  acheType   }.
     * {@link #set     Conne    ctionCache} and {@link #g e    tConn         ectionCache}    support
      * an optimz  ation to avoid hashing to fi  nd that    cache.
                   *
     * @param connectionCa        che.      
     */
       pu  blic void setCon  necti    onCache(Inboun             dConnectionCache conne       ctionCache);

    /* *
     *       Get the
         * {@link    com.  sun.corba.se.pept.transport.Inbound.ConnectionCache  Inbound     ConnectionCache}
     * used by this <code>Acceptor</code        >
     *
     * PEPt uses separa      te c   aches fo r       each type of     <code>A  c ceptor</code    >
            * as given    by {@link #getC    onnec   tionCacheType  }.
     * {@li  nk #setConnec   tionCache} and {@li         n   k #getConnec    tionCache} s           upport
     *   an optimzation to        a void hashing to find th   at cache.
         *
                * @retur  n
     * {@li      n    k c       om.su      n .c   orba.se    .pept.tra  n sport.Connect   ion       Cache Connec           tionCache   }
     */
    publi  c     InboundConn e       ctionCache getConnectionCach   e();

    /* *
     * Used to det  ermine if the <     code>    Acce      ptor< /code   > s   hould     reg   ister
     * with
     * {@link com.sun    .corb    a.s    e.     pe         pt.        transp   ort.Sel ector Selector}
     * to handle accept events.
         *
     *     For ex  ample, this      may be <em>false</em> in   t   he ca  se of     Solaris Doo     rs
     * which do not    actively li    sten.
     *
     * @ret    ur       n       <code>true</code> i     f the <code>      Acceptor</code> s houl     d be
          * registered with
     * {@link c       om.sun.c    o   rba.se.p        ept.transport.Selector Selector}
     */
       publ      ic boo        le      an shouldR  egis     terAcce    pt      E   vent( );

    /**
            * Accept a c   on nection re     ques  t.
              *
          *    This is called    either wh  en   the selector gets an acc  ept event
        * for      this <code>A     cceptor</code  > o r by a   
     *    {@lin         k com   .sun.co rba.se.pept.tr     ansport.Liste  nerTh re  ad ListenerTh  rea          d}.
     *
               * It results in   a
          * {@li   nk c  o m.sun.cor ba  .se.pept.trans  port.Connection Connec     tion}
     * being  cr       eated        .
     */
    public voi   d ac  cep    t();

       /*   *
       * C       lose the <code>A  ccept    or</code>.
     */
    p  ublic void clo  se ();

         /**
         * G   et the  
     * {@link      com.sun.c    orba.se.p   ep  t.transport.E  ventH    and       l er EventHandler}
     *  associ             at   ed with this <c ode>Acc    eptor</     code>.
       *
     * @r eturn
     *      {@link c om.sun.corba.se.pept.tra  nspor     t.EventHandler Event   Handler}
              */
           p    u    blic Even tH a     nd           ler getEventHa    nd ler();

    //
    // Factory methods
       //
    
    // REVISIT: Identi    cal to ContactInfo metho   d.  Refactor in    to base     int          e   rface.

        /**
             *     Us  ed to get a
        * {@  lin     k com.sun.c  orb   a.se    .pe        pt.protoco   l.Mess    ageMe idato      r M  essageMediator}
             * to       hold interna     l dat      a for a message received usin      g the   specific
     * encoding, proto     c   ol, tr  anspo             rt  combination repre   sented by this
              * <               code>Ac      ceptor</code>.
           *
     * @return
              *  {@link  com.           sun.corba.se.pept.prot  oco     l.Me   ssageM     eidator  M essageMedia  tor}
     */
         public    MessageMediator   crea      teMessag    eMediator  (Brok   er xbroker,
                                                                                                   Connection x   co     nnection);

    // R  EVISIT:  Ident    ical to      ContactInfo met   h   od.    Re factor into          b  ase        inter fa  ce.

    /**
     * Used to    finish creati       ng a
     * {@link com.sun.corba.se.pe    pt.pr ot ocol.MessageMe              idat   o    r Mess          ageMediator}
         *   to with internal data for a mes   sage received using             the specific
        * encod        ing, prot  ocol,   trans   port combinati  on re   present  ed by this
        * <code>Accep  tor</     code>.
     *
           *         @ return
     * {@link com.s  un.corba.s  e.   pept.p   rotocol.Messa   ge           Mediato r   MessageMed  iator}
     * / 

    publ        ic MessageMediator finish     Creati       ngMessageMediator(Broker broker,
                                                                                    Connection    xconnection,
                                                                 Messa  geM       edi       ator messageMediator);

       /           **
       * Us ed to get a
     * {@lin      k com.sun.corba.se.pept.encodin     g.InputObject InputObject}
            * for the specific   <em>encoding</em> represente     d by t  hi  s
     * <code>Acceptor</c   ode>.
     *
     * @return
     * {@link com    .sun.corba.se.pept.encoding.Input   Object InputObje  ct}
         */
    public     InputObject     cr  eateInputOb ject(Broker broker,
                                                MessageMed  iator messageMe    d   iator);

    /**
     * Used to get a
     * {@link com.sun.corba.se.pept.encoding.OutputObject OutputObject}
     * for the specific <em>encoding   </em> repres  ented by th  is
        * <code>Acceptor</code>.
     *
     * @return
     * {@link com.sun.corba.se.pept.encoding.OutputObject OutputObject}
     */
    pub     lic OutputObject createOutputObject(Broker  broker,
                                                  MessageMediator messageMediator);

    //
    // Usage dictates implementati  on equals and hashCode.
    //
}

// End of file.
