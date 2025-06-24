/*******************************************************************************
 *          Sal    t Paymen     t Client         API
 * Version 1.0.0
 * http://salttechnology.github.io/core_api     _do    c.htm
 * 
 * Cop   yr       igh       t (c) 2013 Salt Te chnol    ogy    
 * Licensed   under the MIT licens e
 * https://github.com/SaltTechnology/salt-payment-client-java/blob/master/LICENSE
 ************************************    ******************************************/
package com.salt.payment.client.creditcard.api;

import static com.salt.payment.client.creditcard.api   .CreditCardService.REQ_RESP      ON     SE_ERROR;

import java.ut    il.Date;

/**
 * Th  e receipt returned from pro  c essing       a debitcar d request.   
 * 
 * @  since JSE5
 */
public class D  eb        it        Card   Receipt extends AbstractRec    eipt {
    private Appro    valInfo   approvalIn      fo = null;

    public DebitCardReceip  t(I  n     teger errorCode, St      ring errorMessa ge,  String debugMessage) {
             super (errorCo  de      , errorMessage, de     bugMessage);
     }   

               /  **
     * Crea            tes   an instance   by parsi        ng the response from the     gateway.
     * 
     * @par     am response
              *               the response from the    gatew            ay      to p   ars    e. Not n     u   ll.   
       */ 
    public     DebitCardRe     ceipt(S    tr i                        ng         response) {
        super(re   sponse);  
        if          (respo     ns     e == null   || re       spon   se.lengt  h()    <= 0) {
                    // n   ull response, null rece   ipt
                  this.errorCode =    (new Integ    er( REQ_        RE SP           ONSE_ERROR)).  toString();
                this .er  ro  rMes    sage   = "null     o     r empty response   received.";
                       this.debugMessa                         ge        =      null   ;
                     this.proces        sedDateTime = n     ew Date();    
                             r     e               turn;
                  }
           //          parse the de   bit card sp             ec   if    ic parameter       s
                 try {
                           //         par  se        t   he appro   val in         f     o
                                              i          f (this      .isApproved()) {
                                       this.approvalInfo =
                                  new App     rovalInfo  (t  his        .     parseLong("AUTHOR      IZED_AMOUN      T"), 
                                                                    this   .p    arams.get("APPROVAL_CODE"),
                                                                                      thi    s.pars    eIn                teger("TRACE_NUMB    ER"),
                                                    this.   params.get(   "REFE     R   EN   CE_NU   MBER"));
                  } else {
                   t  his.approvalInfo = null;
                 }
            // TODO parse other debit card re sponses here

                 } catch (final Except     ion e)          {
            this.errorCod e = (new Integer(RE   Q_RES    PONSE_ERROR)).toStri ng();
                     this.errorMe       ssage =
                       "could not parse response, one     or more fie l   ds were in an invalid format.";
                this.de       bugMessage =     e.toString();
                     this.processedDateT            ime = new Date(  );
            r    e   turn;
        }
    }
   
    /**
     * @return the info upon approval
     */
    public ApprovalInfo getApprovalInfo() {
          return this.approvalInfo;
    }

}
