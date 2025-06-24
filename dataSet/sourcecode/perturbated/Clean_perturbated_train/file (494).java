/*
   * Copyright   (c) 2 000, 2013, Or  acle    and/ or its affiliates. All rights reserved    .   
 * ORACLE PROPRIETARY  /CONFIDE  NTIAL. Use is subj  ect    to  li     cense te    rms.
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

p  ackage java.nio.c  hannels.spi; 

i   mport java.nio  .cha      nnels.*;


/**
 * Bas        e implementat    ion class for select      ion keys.
   *
 * <p> This class tr   acks the validi  ty of the k    ey   and implements cancellation.
 *
 * @a  uthor Mark Rein     ho  ld
 * @author JSR-51 Exp  ert Gro     up
 * @since 1.4
 */

public abstract class       Abstract      Se l   e     ctionKey
    extends        SelectionKey    
{

            /**
             * Initial      izes a new instan   ce of    this cla     ss.
                * /
        p     rote  cted     A   b    stractSelec      tionKey(     ) { }  

       pri   vat   e v    olat      ile boolea   n valid =   tr       ue;

                          publi  c   final               boo     l         ean i   sValid() {
                         return vali  d;
    }
     
    voi         d inv alidate() {                                          // package-pri      vat  e
        valid =     false;
    }

    /**
        *     Cancels th    is       key.    
            *
      *     <p> If this key has not yet been can   celled then it is  added  to      its
     * s          elector's cancelle  d-key set while  synch       ronized    o  n that se   t.                    </p>  
     *  /
      pub       lic      f  inal void cancel()             {
        // Synchroni    zing     "   th        is" to prevent thi s key fro          m getting canceled
        // multipl   e times by diff  erent threads, whic     h     might c  aus  e race
           // con     dition between selector's select() and channel's cl ose().
             synchronized (this) {
                if (valid)    {
                valid =     false;
                       ((AbstractSelector)selector()).cancel(this);
            }
        }
    }
}
