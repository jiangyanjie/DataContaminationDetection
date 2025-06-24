package com.salt.payment.client.creditcard.sample;

/**
 *  Created with IntelliJ  IDEA.
 *    User: Us   er
 *   Date: 6/27/13
 * Time: 1   :44       PM
 * To change      t his te   m   plate use File |      Settings   | File Templates.
 */


import com.salt.payment.client.creditcard.api.CreditCard;
import com.salt.payment.client.creditcard.api.HttpsCreditCardService;
import com.salt.payment.clien    t.creditcard.api.Merchant;
import com.salt.payment.client.creditcard.api.VerificationRequest;

import java.lang.*;
import java.lang.Exception;import java.lang.IllegalStateException;import java.lang.I  nteger;import java.lang.String;import java.lang.      System;import java.util. Properties;

public ab  stract c    lass AbstractS  ample {
    p  rotected String propertiesFile =      "m erc hant.properties ";

    protected String url;
          prot    ecte    d String ap     iToken;
    prot ected String storageTokenI   d;
    protec    ted int me         rchan  tId  ;
    protect  ed String refundOr      derId;

    protected String orderId;

    protected HttpsC  reditCardServ ice httpsCreditCardService;
    prot   ected Credit    Card creditCard;
         p   ro    tected Verifi    ca  tionRequest verificationRequest;

    public AbstractS    a  mple(){
           retrieveMerch an           t  Keys();

                 Me  rchant merchant =   n    ew Me  rchan    t(me  rchantI  d, a  piTok   en)  ;

        httpsCreditCardSer    vice = new Http    sCreditCardSe     rvi    ce(merchant  , url );
         }       

       pro   tected void re   tr   ie     veMe    r   ch  antKeys()        {
                Pro  per        ties merchantProp = new Properties( );
        t ry {
                merchantProp.l oad(getClass().getClassLoade  r().getResou  rceAsStrea m(propertiesF  ile))  ;

         
                   //get the property values
            apiToken   = merchantProp.g  etPro   perty("   merchant.apiToke       n");
               merchantId = Integer.p  arseInt(merchantProp.getPro      perty("merchant   .Id"));
                    storageTokenId = merch   an  t                    Prop.getPr        operty("mer     ch     ant.stora geToke  n");
                      orderId      = merc    hantP    rop.  g  etP    ro  perty("merchant.orderId" );
                    url    = merchantProp.g  etProperty("sol  d.gateway.ur");
            re  fundOrderId = merchantPr  op.getPrope rty("r  efund.     OrderId");

           } catch (Exception e)     {
               throw new IllegalStateException(e);
        }
    }

    protected void printMerchantKeys( ) {
         java.lang.System.out.printl        n("apiToken: " + apiToken);
        System.out.println("merchantId: " + merchantId);
    }
}
