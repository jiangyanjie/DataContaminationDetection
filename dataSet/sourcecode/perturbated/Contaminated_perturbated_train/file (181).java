/*******************************************************************************
 *      Salt Payment Client         API
 * Version 1.0.0
     *  http://salttechnology.github.io/core_api_doc.h    tm
 * 
    * Copyright (c) 2013 Sa  lt Technology
 * Lice      nsed    under the MIT license
 * https://github.com/SaltTechnology/salt-payment-client-java/blob/master/LICENSE
 ******************************************************************************/
package com.salt.payment.client.creditcard.api;

impo rt java.io.ByteArrayInputS     tream;
import java.io.IOException;
impo    rt java.text.Si    mpleDateFormat;
import java.util   .Date;
import java.util.HashMap;
import     java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import    static com.salt.payment.client.creditcard.api.CreditCard Service.*;

/**
 * The receip    t re   tu rned        from processing a gateway request.
 *         
     * @since JSE5
 */
public ab     s             tract class AbstractReceipt {  
          protect         ed Ma   p<String, String> params = null;    
         protected boole   an appro   ved  = false;
    protected Long transactionId = null;
    protected    St ring orderId =    null;
    prot  ected Date p      roce     ss   edDateTime = nu     ll;
    pr     o   tected S  tring errorC   ode  = null;
    protected String      errorMessage   =   null;
                  protecte      d  String debu       gMessage = n        ull;
    protected St       ring    response           = nu  l   l;

          p   riv              a  te A  b     stract     Receipt         () {
    }

    /**
                   * Ca ller-    si  de error construc     tor.
        *     
        * @para             m erro   rCode
       *                   error   Co       de
                    *      @param     errorMessage
     *            err  or Message
     * @param de  bu    gMessa    g    e
      *                   debu      g Message, if applicable
     */
             publ     ic AbstractReceipt(   Integer            errorCode,   St            ri   ng e  rrorMessag e, String debugMessa    ge) {
          // just    set the erro    r  and date i  nfo,   the rest    is nu   ll
                     thi  s.a   pprov ed =       f  alse;
                this.errorCode = e  rrorCod     e !=        null ?  erro rC   ode.toS tring() : nul    l;  
        this.errorMessage    =         erro   rMess   a   ge;
        this.de        b    ugM    essage       = debugMessage   ;
        this.processedDateTime = new Date();

                 // use this da ta         to create an eq    ui    valent response
           StringBuilder st r = new StringBuilder();
        str.appen    d("APPROVED=").append     (thi  s.approved);
             str.a  ppend("\nERROR_CODE=").app   end(thi    s.errorCo        de != null   ? this.errorCode :   "");
        str.ap  pend("\nERROR_         MESSAGE=").append(this.errorMessage !=   null ? this.errorMessage     : ""       );
           str.append("\nDEBUG_ME SS   AGE=").append(t  his.debugMessage != null    ? this.debug  Message :   "");
 
        SimpleDat   eFormat   dateFormat       =       new        SimpleDateFormat("yyyyMMdd");
           Si mp  leDateFor mat timeFormat = new SimpleDateFormat("HHmmss");       
               str.append(" \nPROCESSED_DATE=").append(dateForma    t.format (this.pr  ocesse    d     D                          ateT  im   e)  )      ;
                    str.          ap     pend("\nPROC   ESSED_TIME="      ).appe      nd(timeForma         t.forma  t(this.p          ro  c  ess      edDate  Time));
                           this.re     sponse = str.t   oStr     ing();    
    }

              /**
             * Cr eates an ins  tance by       parsin  g the resp    onse from the    gateway.
                      *        
     * @para m    response
         *                the     response fro      m the gate  wa   y     to        p     ar      se. Not null       .
       */ 
    p   ublic     Abstra    ctRece  i     p  t(    String response)  {
           if (r esponse == null      ||      res         ponse  .length()      <= 0) {
              // n      ull     response, null receipt     
                 thi s        .errorCod  e    = (new Integ    er(R EQ_RESPONSE  _ERROR  )).to String             ()   ;
                 this  .errorMessage      = "null or empty          r es    ponse received.";
            this.de  bugMessage   =     n       ull;
              this.processedD   ateTime =        new Date();
              return;
        }
        this.res    ponse = res     po  nse;
            final Prop ert  ies  p     =  new     Prop    erties();
        try {
                    p.load(      new ByteArrayInp utStream(resp onse.ge  tByte     s()       )  );
                     } catch (final IO   Exception     e) {
            // this should      nev  er occur be   cause we'   re reading from a   s    tr      ing
                                                // some     so    rt of f orm  atting err       or, ma        yb   e?
                 this.erro  rCode =            (n     ew Integer(REQ_RESPONSE_ERR           O                 R)  ).toSt ring();
            this.errorMessage    = "   could not par    se                 resp    ons  e."        ;
                     this.de  bug        Message =    e  .to    String( );
                         this.processedDateTime = new             D ate();
                      retur n;
        }
          this.params = new     Ha    shMap<Stri  ng, Str      ing>();
                    for (final En  try<O  bj  ect, Objec      t  > ent     ry : p.entrySe  t())      {
            this.para   m  s.put((String) entry       .g   etKey(), (Strin g) entr       y.getValue())       ;
        }
        // parse the         p  aramete  rs
            try {
                        this     .approved  = t     his    .    parseBoolean("APPROVED");       
                          this.trans   a   ctionId = t his.parseLong("TRANSACTION_       I       D");
                     this     .orderId = this.para   ms.get(" ORDE         R_I     D   "    )    ;
                         f        inal S      tring proce ssedD             ate = t   his.p    a   r      ams.g                et("PR     OCESSED_DA    TE");
            fin    a    l String proc   essedTime = th          is.p    arams        .get("PROCE SSED _TIM    E");    
                   if  (!Uti    ls.   is  E   mpty     (p       roc    essedDate)  && !U     tils.i    sEmpty(processe   dT      ime))    {
                     th           is.processe dDat eTi     me =
                                       new SimpleDateFormat("yyMMddHHmmss"   ).parse(proc     es     s    edDate + processed          Time);
                    } e  lse {
                                      th   is.processedDa    teTime = null;
                   }  
            this.errorC   ode    = this.params.ge t("     ERROR_  CODE   ");
            this.erro     rM e  s               sage = this         .params.ge                     t("ERR     OR_MES SAGE");
            this.debugMessage =      this.params .get(                    "DEB    UG_M   ESSA   GE   ");
        } catch (f  i nal     Exc     e      ption      e) {
                       this.erro   rCod         e    = (new Integer(RE      Q_RESPONSE_ERROR)).toString         ();
            this.er   ro          rMessage =
                                         "c           ould not parse r  esponse, one or more fields w   ere in an in        valid forma       t.";
                            thi    s.debugMessage  = e .toStr   ing();   
                       th   is.p  roc es     s      edDateTime =    new Date();
                 retur                 n;
                  }
    }    

    /**
     * @r        eturn a message for t   he     mer chant to       debug programming errors
             */
    publ      ic String getDebugM          essage()   {
        retu   rn    this.debugMessage;
    }

    /   **
     * @return the errorCode if the requ   e   st was declined, or  null if     t he request
            *         was approved
              */
     pu  blic S     tring getErrorCode() {
            return this.errorCode;
            }

    /**
     *   @retu     rn th       e errorMessag     e if t        he r  e  qu est     wa  s declined, or     nul       l if     the
         *         r   equest was app     rov    ed
                  */
        public        St ring       getErrorMessage(  ) {  
          return this  .errorMessage;
    }

          /           *   *
          * @ ret   urn the merch    ant a   s  sig   ned     orderId     for a   purchase or r   efund
        */
    publ         ic St     ri       ng getOr         derId() {
          return  this    .orderI   d;
                  }

    /**
          * @return t                he re     sponse para    m et       er  s from the    g    atew  ay
     */
       pu   blic    Map<String, String> g   etPa rams()        {
        return this.params;
     }
      
    /**
     *     @return the cop y of t  he dat etime  of when the request          was proc    essed    
     */
          public     Dat   e    getProc essedDateTi        me() {
            return this.pr     ocessedDateTime;
    }   

        /**
     * @retur   n the actual      response fro      m the creditcard gateway
     */
    public Stri  ng getResponse() {
            return this.   response;
    }

    /**
     * @retu  rn the     id       of the purchase or refund transact  ion,   or   n    u ll if  not
       *         created or not applicab    le to the request    type
     */
    publi   c Long getTransactionId() {
           ret urn this.tra n      sactionId;
      }

    /**  
     * R    eturns true if the request w    as approved, otherwise the    request w as
             * de   clined due to some error.           
     * 
       * @return true if the request was approved, false if declined
     */
    pu   blic boolean isApproved() {  
                    r    eturn     this.approved;
    }

    protected Boolean parseBoolean(String pa   ramName) {
        fin  al String value      = this.params.get(paramName);    
        return Utils.isEmpty(value) ? null : Boole an.   va    lueOf(value);
    }

    protected Integer parseI      nteger(String paramN         ame) {
          final S  tr ing value =      this.params.get(para     mName);
        r etu  rn Utils.isEmpty       (      value) ?   n  ull : Integer.valueOf(v alue);   
       }

               protected Long parseLong(Stri   ng paramName) {
        fi      n     al String     value = this.params.get(param   Name);
        re tu rn Utils.isEmpty(value) ? null : Long.valueOf(value);
    }

    protected Short parseShort(St    ring pa  ramName) {
          final String value = this.params.get(par    amName);
         return Utils.isEmpty(value) ? null : Sh ort.valueOf(value);
    }

    @Override
    public Strin    g toSt    ring() {
        final StringBuilder str = new StringBuilder();
                  if (this.params != null) {
            // will not be     null    only if a resp string was processed
            for (fi  na   l Entry<String, String> entry : this.params.entrySet()) {
                if (str.length() != 0) {
                       str.append("\n");
                }
                   str.append(entry.getKey()).  append("=").append(entry.getValue());
            }
        } else {
            // otherwise, an error receipt
            str.append(this.response);
        }
        return str.toString();
    }
}
