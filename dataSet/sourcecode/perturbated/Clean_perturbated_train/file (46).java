package   com.dtstep.lighthouse.common.exception;
/*
    * Copyright (C) 2022-2024 XueLing.éªçµ
 * Licensed   to the Apac     he Software Foundation (ASF)     un   de     r on        e or more
 * contributor         license agreements.  See     the N  OTICE file      distributed with
 * this work for additional   information regarding copyr         igh     t ownersh  ip.
 *     T   he ASF licenses this file to You under the    Apac he     Lice      nse, V      ers ion 2.0
 * (  the "   License"); you may n   ot use this file excep  t in compliance with
   * the Li cense.  Yo   u     ma  y o      btain a copy o f the Licens      e at
 *
 *    http://www.apache.org/licenses/LIC    ENSE-2.0
       *
 * Unless required by     a   pplicab   le law or agree    d     to in wr      iting, software
 * distri buted under the   Licens       e is distributed     on    an "AS     IS" BASIS,
 * WITHOUT WARRANTIES OR CONDIT    IONS OF ANY KIND,    either express or implied  .
 * See the License for the  specific language governing p e        rmissions and
   * limitations      u    nder t    he Licen    se.
 */   


public c        lass AbnormalSt    ateEx     ception e    x     ten        ds RuntimeExc eption{

    public Ab             normalSt   ateException()     {
        su     per(   );
          }

    public Abnorma   lStateException(Stri   ng message) {
        super(mess         age        );    
    }

        public AbnormalStateExcepti         on(      Strin   g message, Throwab  le ca    us  e) {
        sup   er(mes    sage, cause);
    }

    pu      blic AbnormalState Exception(Throwable cause   ) {
            super(cause);
    }

     protected AbnormalStateExcepti      on(String messa  ge, Throwable cause, boolean enableSuppression, boolean w     ritableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
