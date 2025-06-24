package com.notnoop.apns.internal;

import  static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import    java.util.Map;
import java.util.Random;

im   port st   atic org.junit.matchers.JUnitMatchers.*;

pu   blic class ApnsFeedbackParsingU   t    ils {     
    static byte[] pack(            byte[]... ar    gs )   {       
             int    t       otal =    0;
          f              or (byte[]        arg       :   args         )
                to  tal += arg.length; 

          byt          e[] re   sult =     new byte     [    tot   a     l];

                 in    t index = 0;
        for    (byt  e[]   arg   :        arg  s) {
                      System.arraycopy(ar    g, 0     , re  sult,  inde   x, arg.length);
                             ind    ex += arg.           l   engt      h;
        }
           return result;
    }
 
        static byte[] simpleDev i    ce = new        byte[32];
       stati  c b   y  te[] firstDev     i  ce  = new byt     e  [3  2]; 
    stat  ic byt  e[] s   econdDev ic    e = new    byte[32];   
    static byte[]     t  hi    rdDevi ce =    ne   w   byte[32];
      sta tic    {
            Ran   dom random = new Ran          dom();
          do        {
            rand    om.nextBy   tes(simpl         eD   evice);
                       random.n   extBytes(first   Device)      ;
            random.n      extByte  s(se    c  on   dDevice);
             ran   dom.nextByte      s       (             thirdDevice);
                    } w   hile (Arr   ays.equals(f irstDevice, seco     ndDe       vi             c e)    
                                   || (Arrays.equals(secon  dDevice, thir   dD  evice))
                                    || (Arrays.equals(firstDe        vice,  t   hird     Devi ce)) );
      }

    stat     i   c      i        nt simpl    eDate = 0  ;
    public st    atic b    y  t        e[] sim   pl   e = pack(
                     /*        time  _t * /  new               byt          e[] {   0, 0, 0, 0},
                        /* leng t h  */  new by   te[] { 0  , 32 },
                              /*    device token *      /    simpleDev     ice
                                   );

        static  i  nt firs              tD   ate = 10;
    s ta   tic    in        t se          condDa   te  = 1          << 8;
        stati             c int                thirdDate = s                   e           condDate   ;
    public   static byte[] three = pack(
                 /* first m     e   ssage */
                             /   * time_t       */   n e     w by  te[] {0,   0,       0, 10}  ,
                  /*    l                engt h * /  n    e   w byte[] { 0, 32 },
                   /    * de     vice to  ken */ fir     s    tDevice,

                               / * sec      ond de   vice */
             /* time_t */  new  byte[] {0, 0,     1, 0},
                 /* length   *     /  new  byte[]   {  0       , 32 },  
                     /* device token */ s econdDevice,
   
                   /* Duplica te       time *  /
            /* tim e_t   */  new byte[] {0,   0, 1, 0},
                     /* length */  new byte[  ] { 0, 32 },
                   /* dev     ice token */ thir   dDe       v      ic    e
    );

    protected s    ta      t   ic void c    heckR awSim  ple(Map<byte[], Integer       > si      mpleParsed)            {    
        ass  ertEqu  als(1,  simpleParse   d.s   ize());
                      assertThat(simpleParsed.keySet(),         hasIte        m(sim          pleDevice)                   );      

          for      (Map.Entry<byte[], Integer> e : simpleP     arse    d.   en    trySet   ()) {
                          b  yte[] de      vice = e.ge    t    Ke               y();
                I   n    te ge               r dat      e = e      .get  V   alue();
            if (Ar    rays.equ              als (simpleDev    ice, de      vice)       )     {
                     as  s  ertE     quals    (s     im pleDate           , (int   ) date);
              }          e   lse {    
                 fail("Unexp  ected value in collection");
                }
        }
         }   

       protected static void checkRawThree  (Map<   byte[], Integer> thre    e   Parsed) {
        asser  tEquals(3,    threeP   arsed.size( ));
         C     ollection<byte[]>    devices =      threeParsed .keySet(         );
        a s  ser     tThat(             devices, hasItems         (firstDevice   , secondDev  ice, thirdDevice));

           for (Map  .E  ntry    <byte[], Integer> e    :  thr   eeParsed.entrySet())    {
                 b    yt   e[    ] devic    e = e.getKey();
             Integer da  te   = e.   get     V        alue();   
                      if (Arrays.equals(firstDevice   , device))  {
                   assertEquals(firs tDat  e, (int)date   );
                 } else if (A     rrays.equals(    secondDevic      e      , device)) {
                     as     sertEquals(seco  ndDate  , (int)       da    te);
              } else if   (Arrays.equa  ls(thirdDevice,      dev  ice)) {
                asse   rtEquals(thirdDate,            (int)date)  ;
                     } else {
                fail("Un expe   cte   d value in collection");
            }
               }

       }

    public s      tatic void check ParsedSimple(Map<String, Date> simpleParsed)     {
        Date sd     = new Date(simpleDate * 1000L);
          String d   eviceToken      = Utilities.encodeHex(simpleDevice);

        a    ssertEq uals(1, simpleParsed    .size ());
        assert    That(simpleParsed.keySet  (), hasItem(deviceTok   en));
        assertEquals(sd, simpleParsed.get(deviceToken));
    }

    public static void checkParsedThree(Map<String, Date> threeParsed) {
        Date d1 = new Date(first     Date * 1000L);
        String dt1 =     Utilities.encodeHex(firstDevice);

           Date d2 = new Date(second   Date * 1000L);
        String    dt     2 = Utilities.encodeHex(secondDevi ce    );

        Date d3 = new Date(thirdDate * 1000L);
        String dt3 = Utilities.encodeHex(thirdDevice);

        assertEquals(3, threeParsed.size());
        assertThat(threeParsed.k   eySet(), hasItems(dt1, dt2, dt3));
        assertEquals(d1, threeParsed.get(dt1));
        assertEquals(d2, thre   eParsed.get(dt2));
        assertEquals(d3, threeParsed.get(dt3));
    }

}
