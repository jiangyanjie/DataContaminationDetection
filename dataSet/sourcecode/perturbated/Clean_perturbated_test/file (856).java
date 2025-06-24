/*******************************************************************************
     *  Salt Payment Client          A      PI
 * Version 1.0.0
 * http://salttechnology.github.io/cor    e_api    _doc.htm    
 *    
 * Copyright  (c) 2   013 Salt Technolo   gy
 *   Licensed u   nder the M IT  license
 * https://github.com/SaltTechnology/salt-payment-client-java/blob/master/LICENSE
 ******************************************************************** **********/
package com.salt.payment.client.creditcard.api;

import java.text.SimpleDateFormat;
import java.util.Date;

    import static com.salt.payment.client.creditcard.api.CreditCardService.*;

/**
 * The receipt returned from pro   ce     ssing    a creditca rd request     .       
 * 
 * @since     JSE5
 */
pu    blic class    CreditC ardReceipt extends     AbstractReceipt {
    priva        te A    ppro             v    alInfo a  pprovalInfo = null;
    private AvsResp  onse avsResponse = null;
              pr  ivate Cvv2Response        cvv      2Resp  onse   = null;
    privat   e PeriodicPurchas  eInfo periodicP  urchaseInfo =   null;

    private String sanit      izedCardNumber = null;
       p    ri       vate shor          t storageTokenExp    iryD       ate;
    private String      respons    eHash = n  ul           l;
    private Integer cardBrand = null;
         private Strin      g st    orageTokenI     d = null;
 
    pri  vate Int   eg      er fr    audScore =      null;
    private String   fraudDecision = null;
    priv  ate Str  ing fraudSessionId = nul     l;

    public Credi    tC  ardReceipt      (  Int             eger erro rCode, String e      rrorMe   ssage, String debugMessage) {
                 su   p    er(errorCode,   er    rorMessage  , de   bugMessage);
          }

    /   **
     * Creates   an instan            ce by parsin g th    e       response from the ga    teway.
     * 
     * @param re sponse     
     *                th       e r   esp         ons   e     fro      m th       e     ga        teway to parse. No     t nu ll.
           */
    public Cr   editC    ardRe  cei    pt(Stri    ng re spons   e) {
        super(r     espon  se);
          if (     r    esponse     == null ||    response.      length ()           <= 0) {
                // null    response,      null rec  eipt 
              thi  s.errorCod e = (new Integer      (REQ_RES     PONS     E    _         ERR     OR)).t            oStri    ng    ();
                 t     his.err         orMessage = "nu    ll   or    empt y respo   nse        received.";
                  this.    d     ebugMessage =                                   nu   ll;
                   this.processedDateTime =    new              Da       te();
              re      tu     rn  ;                          
        }
          // parse t  he           C  C- sp ecific par  ameters
           tr y {
            // p         a  rse the appr   oval info     
                   if    (thi   s .isApproved      ()) {
                      this.a   pp         roval   Info =   
                                   new Ap     pr     ovalInfo(                             this.parseLong("AUTHORIZED_AMO               UNT") ,   
                                            this    .param s .ge t("          APPROVA   L_CODE"),
                                                                        this.pars       eInteger("      T R   ACE_NUMBER"),
                                                 this.p ara             ms.ge   t("REFERENCE                  _    NUMBER"));
               } else {
                            this.app   r   ovalInf  o =    n ull;
                }
                              / / pa  rse the AVS r    esponse
            final        Boolean av  s  ResponseA          v  a       ilable = this.parseB       o  olean (    "A VS_RESPO NSE_A     VAI LAB              L     E");
                                               if (avsRe   sp       onseAva ilabl             e != null && avsResponseAvaila    bl e    )      {
                       t his.avsRes     ponse =
                              ne   w AvsResponse(this.     para ms.   get("A    V   S    _RESPONSE_CODE"),
                                                  thi                       s.parseB   o                   olean     ("STREET_MATCHED"),
                                                th is.par    se    Boolea  n("ZIP _MATCHED")      , this.pa       rams  .get("ZIP_TYPE"     ),
                                                                        th  is.   p  arams         .get("AVS_E    R RO  R        _  CODE"),
                                                                                  this.params.get("  AVS_ERR            O   R_MESSAGE"     ));
                                } else            {     
                            thi            s.avsRes  ponse = null ;
                    } 
                           // pars e the C    V  V2 res  pons e
               final Boolean cvv    2  Re     s   ponseAv   ailable              = this.parseB       oolean("CVV2_    R           ESPONSE_AVA ILAB            L     E"    );
                  i   f (cvv2  Re     sponse   Availab le        !   = n   ull    && cvv2ResponseAvailable) { 
                                    t    h  is.cvv2Re  sponse            =
                                  n     ew Cvv2Res     ponse(   this   .par        ams.ge     t("CVV2_RESPONSE_CODE"),
                                                                           t   his.params. g   et("   C     V      V2_RESPO   NSE_M    ES   SAGE"));
                                     } else    {
                     thi   s.cv    v   2Respons    e = nul   l;
                         }
                  /   / pars        e        periodic   pur ch     ase info
                                         fina      l Long p   eriodicP urc    haseId =      this.    pa  rseLo   n   g("P         ERIO    D          IC_TRANSACTI     ON_      ID");  
                               if (p     e    riodicPurchaseId !=  null) {
                        fi     nal Pe  ri     odicPurchaseInfo.State periodicP  urchaseStat    e   =
                            Peri         odicP    u  rchaseI   nfo     .State    . fromCode(this
                                                   .parseShort       ("PER   IODI     C_TRAN  SACTI ON_         ST  ATE"));
                   final String nex   tPayment Date  Stri  ng = th    is.param  s.get("    PERIODIC_      NE     XT_PA YME   NT_DATE  ");
                     Date     nextPaymentDa   te = n u   l l;  
                                          if (!Util      s.isEmpty(nextPa  ymen   tDateStrin     g))     {
                                       nextP  ay  men      tDate =
                                           new      Simpl   eDateFor     mat     (      A   bstract        Cr             editCard Service.DATE_FORMA T)
                                            .pa  rs e(ne  xt   Pa  ymen           tDateString      );
                          }
                         fi nal  Long la    st        Payment    Id     =
                                            this.params.g e     t("PERI      ODIC_LAST_PAYMENT_ID") != null ?   th       is
                                           .p  arseLong("P    ERIODIC_LAST    _PAYMEN  T _ID") : n     ul     l;
                          this.periodicPu  rchaseInfo   =
                                                   new PeriodicPur      c    haseIn      f     o(peri     odicPurchaseId,     pe   riod      icP    urch   aseState,
                                                    nextPa   y     mentDate, la   stPa            ymentId);
                          } el       se {
                        this.pe riodicPurchase      I   n fo = null;
            }

            /     / Parse         newely a dd  ed fi elds
            s       anit      iz      edCardNumber = this.para  ms.get("CARD  _     NUMBER");
            storageToke  nExpiryDate = thi   s.parseShort("    STO   RAGE_         T     OKEN_EXPIRY");
                r     es        ponseHash    = th  is.params .get(   "RESPONSE_HASH");
                cardBrand = this.parseInteger("     CARD_BRAND" );
            st  orageToke          nId = this.params.get("STORAGE_TOKE    N_ID"      );

                        fraudScore = this.parseInteger("FRAUD_SCORE");
                 fraudDecisio         n = this.params.ge       t("FRAU  D_     DEC    ISIO N");
                fraudSessionI     d = this.params.g  et("     FRA       UD_SESSI           ON_ID  ");

                   } ca  tch (final Exception e)     {
            th    is.error   Cod       e = (new Int  eger(REQ_  RESPONSE_ERROR)).toString(    );
                  this.errorMessage =
                         "could  not parse response, on      e or more fields w  ere in an invalid f           ormat.";
                     this.   debugMessage =     e.toString();
                this.processedDateTime = new Da     te()    ;
             return; 
        }
    }

    /**
     * @return the info upon approval 
     */
    public ApprovalInfo g  etApprovalInfo() {
        return this.approvalInfo;
    }

    /**
     * @return     the AVS resp onse from processing    the purchase, o  r null if AVS was
     *         not enabled or no resp         onse
     */
    pu blic AvsResponse getAvsResponse          () {
                   return th    is.avsResponse;
    }    

    /**
     * @return the CVV2 re  sponse fr  om proc  ess  ing the purchase, or null if CVV2
     *                   was not enabled or no response
        */
    public C vv2Response getCvv2Response() {
        return  this.cvv2Response;
    }

    /**
     * @return periodic purchase inf   o (if applicable)
     */
    public PeriodicPurchas eI  nfo getPeriod     icPurchaseInfo() {
        return this.periodicPurchaseInfo;
    }

    public    String getSanitizedCardNumber() {
        return sanitizedCardNumber;
    }

    public short getStorageTokenExpiryDate() {
        return storageTokenExpi     ryDate;
        }

    public String getResponseHash() {
        return responseHash;
         }

    public Integer getCardBrand() {
        return cardBrand;
    }

    public String getStorageTokenId() {
        return storageTokenId;
    }

    public Integer g   et     FraudScore() {
        return fraudScore;
    }

    public String getFraudDecision() {
        return fraudDecision;
    }

    public String getFraudSessionId() {
        return fraudSessionId;
    }
}
