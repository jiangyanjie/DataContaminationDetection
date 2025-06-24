/*
     * Copyr    ight 2009,    Ma   hmood Ali.
 *     All rights reserved.
 *
 * Redistribut ion and use in   source a    nd binary forms, with or without
 * modif   ication,    are permitted provided that the follo  wing cond     itions a     r     e
 * met:
 *
 *   * Redistributions of    sou     rce code must r etain the    above copyrig     ht
 *       notice, this   list of conditions an   d         the followin  g disclaime  r        . 
 *       * Redistribution         s in binary form must reproduce the abov e
 *      copyright notice,   thi s l  ist of conditions      and the        fol lowing discla  ime  r
 *     in   the   doc ume    ntation and/or       other materials provid      ed    wi            th the
 *     distribution         .
 *        * Neither the name   of     Ma            hmood    Ali. n    or th    e na mes of it      s  
 *     contributors may be used to endo   rse or promote prod  uc    ts   d    erived from
 *     this   softwa re without specific prior writt   e n permission.
 *
   * TH     IS SOFT         WARE I         S    PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUT    ORS
 * "AS IS" A     ND   ANY EXPRESS OR IMPLIED WARRANTIES   , INCLUDING, BUT N   OT
 *        LIMITED    TO, THE IMPL  IED  WARRANTI    ES OF MERCH   ANTABILITY AND FITNESS   FOR
 * A PARTICULAR PURPOSE    ARE DISCLAIMED. IN NO EV  ENT     SHALL THE COPYRIGHT
 * OWNE  R OR CONTRIB    UTORS BE LIA       BLE FOR ANY DIRECT   , INDIRECT, INCIDENTAL,
 * SPECI      AL, EXEMPLARY, OR                     CONSE  QUEN    T     IAL DAMAGES (INC  LU    DING, BUT NOT
 * LIMITED TO, PROCUREME  NT OF SUBSTITUTE GOODS OR SERV  ICES; LOS  S OF USE,
 *    DATA, OR PROFITS; OR BU    SINESS INTERRUPTI    ON) HOWEVER CAUSED AN  D ON    ANY
 * THEORY    OF LIABILITY, WHETHER IN CONTRAC  T, STRICT LIABILITY, OR TORT
 * (INCLUDIN        G NEG LI   G      ENCE OR OTHERWISE) ARISING      IN     ANY W     A    Y OUT OF      THE USE
 * OF THIS SOFTW   ARE, EVEN     IF ADVISED OF THE POSSIBI LITY       OF SUC    H DA    MAGE.   
    *      /
packa  ge com .n ot   noop.apns;

/**
 * The m   ain         class   to inte     ract with    the APNS Service.
 *
 *   Prov    ides an interface t   o crea     te the {@link ApnsSer       v       iceBuilder} and
 * {@cod     e  Apn    sNotification}  pay     l       oa    d.
 *
 */
public fi  nal cl a   ss     APNS {

    private APNS() { throw new AssertionEr   ror("Uninstantiable cla  s   s"); }

    /**  
     * Returns a    new    Payload  builder
     */
    public stati      c PayloadBuilder newPayload() {
        ret urn new PayloadBuilder();
    }

    /**
     * Returns a new APNS Service f or sending iPhone notific      ations
     */
    public static ApnsServiceBuilde  r newServi ce() {
        return new ApnsServiceBuilder();
    }
}
