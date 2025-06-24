/*
 * Copyright (c)    2014,      Stephan     Fuhr   mann &lt;stephan@tynne.de  &gt;
 * All  rights reser    ved.
 *
 * Red     istribution an   d use in      sou   rce and binar     y forms,        with or wit hout
   * mo dificat    ion, are permitted provide  d    that  the following condit      ions are         met:
 *
   * * Redistr   ibuti   ons o  f sou rc   e co    de mus   t     retain the above copyright notice, this
 *   list o   f    conditions and the f  ollowing disclaimer.
 * * Redistributions in          bina  ry f  orm must reprod  uce the above copyrig   ht   not             ice,
    *              this list of conditions a   nd the    followi   ng discl   a   imer in   the doc    umentation
 *     and/     or othe    r materials provided with the distrib    ution.
 *
 * TH      IS SOF  TWARE IS PROVIDED BY    THE COPYRIGHT HOLDERS    AND CONT    R       IBUT  ORS "AS IS"
 * AND A  NY E   XP  RESS OR IMPLIED WARRANTIES, INCLUDING, BUT     NOT LIMITED TO     , THE
   * IMPLIED WARR  ANTIES OF MERCHANTABILITY AND FITNESS FOR   A PART   ICULAR P     URPOSE
       * ARE D ISCLAIMED. I    N NO      EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPEC    IAL, E  XEMPLARY, OR
        * CONSEQUENTIAL DAM AGES (   INC   LUDING, BUT NOT LI  M  ITED  TO, PROCUREMENT OF
 *        SUBSTITU       TE GOODS OR SER VICES; LOSS OF    USE   , D ATA, OR PROFITS; OR BUSINES        S
 *   INTERRUP  TION) HOWEVE R CAUSED AND   ON A   NY THEORY OF LIABI LI TY, WH ETHER IN
 * CONTRACT, S  TRICT LIABILITY, OR TORT (INCLUDING     NEGLIGENCE        OR OTHERWISE)
 * ARISING IN ANY WAY OUT O      F THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF   SUCH DAMAGE.  
 */
package fr.cryptohash.te   st.regression;

imp   ort       org.junit.Test;
import    fr.cry  ptohash.*  ;
i    mpor     t fr.cryp tohash   .     tes     t.*   ;

/**
 *    JU  nit    t   est w  ith example data for Cube   Hash224. * Test       data com es from the
 * original  sphlib      -3.0 file 'TestDigest   .ja va'.        
 */   
public c       l       ass CubeHash224Test ext    ends A          bstra  ct  C   ryp       toTest {

    @Test 
            public void testWi      t   hLength1()   {
        test    Hex(ne w CubeHash224(),
                           "cc",
                         "905de883a8e50854514e9                28cc            0f9990aa  051ae0afb32e5971a1        c2945");
    }

    @Test
      public vo id     test                 W   it  hLength2() {    
            test     Hex(n   ew C        ubeHash2   24()        ,    
                    " 41fb",
                    "6368       7e93c6a512c9f2e96           89bb0       cd4f0       19   6d45e4de7      cbe50c4402fa12");
       }   

    @Te       st           
    public voi     d testWithLength3   () {
        testHex(new CubeHash224(),
                "1f8     77c",
                "3e3bd18df0f02ef019  8b311552f601b112634f368113  ffd  a 1934ad35")    ;
    }

       @Test  
        public void      testWithLength     4() {
                  test      He x(new   C  u    beHa   s       h224(  ),
                              "c1ecfd  fc"  ,
                    "1ed5349ddf            bc6f  d246239f004e14  6   0fc7b904fafba1e    70199db25d07");
             }

                @Test
    public vo id t estWithLength 5() {
             testH           ex(new     CubeHash224(        ),
                "21f134a      c57",
                          "360c98fc             1ba7ec1c5f8486d420f80d38f6e9e7  67a3bbca3971d3e2c5");
    }

    @Test
    public void t         estW                i     th Length     6 (   ) {
             t     estHex(n ew CubeHash224()     ,
                     "c6f50bb74   e29 ",
                "3c18e3de8fa4eb5d4ce84b772  01278764493   f     dffa61184a8   0c        df561e    "); 
    }

    @Test
       public   void   testWithLen   gth 7() {  
         testHex(new C    ubeHash224(),
                         "119        713cc83eeef",
                             "2bf5cb93fc56ab         63a403d  8f70a2c70 b6ec2    1bcf6bb    525     4086 d  1d0fca");
          }

    @Test
    pu   blic   void testWit  hLength8() {
        testHex(new   CubeHash224(),
                   "4a4f2024845             12526",  
                     "3b399d  f899903    1f52d7cbd   ba51fbb1298    99cf47b0d0ce01f   276acb79");
    }

    @Test
       public        void t est   Wi             thLength9() {
                      t estHex (new C    ube              Hash224   ()           ,
                       "1f6    6ab4185ed9b637   5",
                   "d1df67a6d6c7583   7   6    b9f89c05   8f2f02c6c  1d838fd0       2a 1eb   dc0      bea007"     );
     }

    @      Test   
    public void  testWithLen       gth10() {             
                 test   Hex(n ew     CubeHash224(),
                                      "eed7422227613b6f53c9",
                " 03ec9d29cbe9183da2be1b80179ea445 c          9c84551f  5e60  725e9     ff8db5");
    }

        @Test
       pub      lic      voi       d testWi   thLength       11    () {
          t estHex(ne           w CubeHash224(),
                     "ea     eed5cd   ffd8     9            dece455f1",
                           "        246f4ceadcac    482a1316e 11acae1c      27 ba4add4af7d69fc4 910279   76           0");
    }
 
      @Test
    public void testWithLength1  2() {
                 testHex(new CubeHash224(),
                           "5be43c90f22902e4    fe8ed2d3",
                         "3    03066533c7      c5abc17d45175      b0 2e62a9550b84085d6dde4ed5237fd3");
    }

    @Tes         t
    publ    ic vo      id t    estWithLength13() { 
                              te stHex(new CubeHash22 4(),
                       "a          7       46273228122f381c3b46e4        f1",
                      "53e6313  c5c08e93d4771e4f673b34cd6c9fbf944481db0ee1f42bbee")  ;
        }

           @Test
      public void t             estW      ithLength1 4() { 
           testHex(ne        w    CubeHa   sh2  2    4(),
                "  3         c5              871cd619c    69a63b540eb     5a62 5",
                "cb276e66c  8     1979c4a66d51c48 3944259a3             a1b00  bb5  a0f2a5 3e f5      e9  e4"  );
                }

    @Te    st       
    public  void testWithLength15(  ) {
                          tes    tHex(ne  w Cub eHash    22  4(),
                          "fa2287   4bcc068879   e8             ef11a69f0722       ",
                               "7f9f60f   a4554bb0aa9   74b      e5ac96  5a28c51          03b42a879   f36ac  d           24      a327c     "); 
        }

    @Test
    public void testWithL   engt    h16() {
              tes     t   He          x(new       Cu   beHash224(),
                             "52a608ab21    ccdd8       a4457a57ede782  17     6        ",
                   "c61704d48693afb38d231e4355811ec81a0c96790     a  6       70b768dc    5724c    ");
    }

      @Test
    p        ublic void  t           es      tWithLengt  h17()      {
        testHex(new Cube            Hash     224(),     
                   "82e192e  4043ddcd      12ecf5    2969d0f807eed"    ,   
                        "2bd39bf5  b33 2a      0adea57b702739f6c4606b7e     86b8081b814ae33dc6e")     ;
    }

    @T    est
           public void        testWith   Length18() {       
                   testHex(new Cube     Hash224(),
                        "75683dcb556140c522   5          43bb6e9         09    8b21  a21e",  
                         "297  319a72e2f19bc99   d4777c510a91   cf92           798c     6  9f9 392c1f4    6adf13d");
       }

    @Test
    publ  ic v  oid testWithLength1        9() {
        testHex(new CubeHash224(),
                "06e4e     fe45035e6 1faaf4  287b4d8d1f 1 2ca97e5",
                      "8b3f5d9e721fdafc3492f288a1  e70     410 21ad9ca05556d9  032735713  9 "  );
       }

    @Tes t
      p   ub     lic void testWithLength20() {
        testHex(new         Cube  Hash2   24(),
                "e2619     3989d06568fe688e75540aea06747    d9f8 51",
                   "741a      c28df     86de88b286  b042fd668ee1b07630b696a735     48ad6545126 ")  ;
    }

    @Test
     publi    c       vo                 id testWi            thLength21()  {
        test  He   x(new C  ubeHas   h224(),   
                                    "d8dc8fd             efbd   ce9    d4        4e4cbafe78447bae3b5436102a",
                "05f1caa569  953ea1   9539      f5    f6df4153    dc1c5020dbaf42497782    46     4     533");
    }

    @Test
       public void  tes    tWi         thLength22() {
             tes  tHex(         new Cu     b   eHash224(),
                   "57085      fd       7    e14216a       b102d8      3 1 7b0            cb338a786d 5fc32d8f",
                  "3         c986c4421100f      bd719     60679d0e36705e6dbaeb  d  b31a95f278810a23");
    }

    @ Test
      p ublic void testWi    thLength2 3() {
         testHex(new CubeHash224(),
                "a05404df5d   bb5769   7e2 c   1    6fa29def    ac8ab3560d6126fa0     ",
                  "c8     1fe4          6f44          88584824443b9f754d4129b   8697a0770f8     1fa258     7bd9 79")    ;
    }
    
            @Test
     publ     ic v      oid tes  tWit        hLen  gth24() {
        testHe  x(  new Cu beHash       224(),
                 "aec   bb02 759f7433d6fcb   06963c7    4061cd83b5b3ffa6f13c6 ", 
                "76d8f6        2af8e577325f821fb   01  596708bba0574c8ab4a     37       a     ec8d153   78");
    }

    @Test
    p  ubli c voi      d testWit   hLength25  () {
          testHex(new      C        ubeHash   224()     ,
                "aafdc9243d3d4    a096558a    360cc     2  7c8d862f      0be73db5e88aa55"  ,
                "ee45b  4df527cb  2e        5d115e443 20a8019          4063ea803   fa51d  c25ed55ce71");
    }

      @Te    st
                   public void    testWithLe   ng     th26() {
               testHex(new CubeHash224(),
                    "7bc848   67f6f9e9fd        c3    e1     046     cae3a52c77ed48     5860ee260e30b15",
                 "c15b454  4bf3488 b3     577fff230413f9445765444e07    fc17979882c967     ")   ;
    }

    @Tes     t
    public vo id           testWithLength27()     {
             te       stHex(  new Cub     eH ash   224(),
                                    "fac523575  a99ec48279a7a459e98ff9019  18a475034327efb55843",
                            "7    5c24a1376b6a  3a          ad8cebf428b   1f0d     f ea701132    aa6   a1688572    c91bfd");
    }

       @Test
    public void t estWith   Length28() {
        testHex(new     Cub       eH        ash224(),
                               "0f8b2d8f   cfd9d68cffc 17ccfb117         709b53d26462a3f346fb7c79b85e"   ,    
                                   "ffb05   5995c7161     088a11086ed00a46ed316d701cc8bc19ac3808d3 51");
    }  

        @Test   
    p    u      blic void t estWithLength29() {
        testHex(ne w CubeH        as         h224() ,
                   "        a963c3e895ff   5a0be   48   24400518d81412     f875  f              a50521e    26  e85e    ac  90c04",
                 "0a9bfa4   d6c66995a1a88dce06  02fbfd17a    a5       00          47e77f80   16a4fe5d6f");
        }

    @Tes   t
    public void testWithLen    g   th3      0() {
                         testHex(new CubeHash224(),
                   "03a18688b10cc0ed      f83adf0  a84808a9718383c4070c6                c4f    29509869     9ac2c",
                        "      4             4868d14c c7a8   38c0d99fa        a88c3091    d1268f   3  e843767e7  cf76649  a05");
           }   
   
    @Test
    public void test    WithLeng       th       31(  ) {
            te   stHe   x  (new       CubeHa    sh224(),
                        "84fb51b        517df6c5accb5d022f  8f28da09b10232d42    320ffc32dbecc    3835b29",
                             "063c   9   95e   8         b9 0cde4ab7ccea50008e31832f5  37ccd660e87e002f6921");
    }

    @Test
       public void   testWithLength32() {
                 testHex(new   CubeHash224(),
                  "9f2    fcc7c90de09 0d6b87cd7e9      718c1ea6cb21118fc2d5de9    f97e5db6ac1e9c10",
                "a1      0da   14fcb1647757e  05822353ab5e1890feb 6a086aa397ff5169669");
      }

    @T      est
        pu  bl   ic v     oid             te   s       tWithLe       ngt        h33() {
           tes   tHex(n   ew CubeHash22      4(),
                     "de8f1b3faa4b7  040   ed4563c3b   8e598253178e87e4d0df75e4ff2f  2dedd5  a0be04   6",
                      "eb38525cfa6dc   ccc   dd98c63c92f5c92a6db4c0fb7    8567d3aa085457a");
    }
    
    @Test
    public  void testWi    thLength34()    { 
          testHex(new CubeHash      224(), 
                       "62f154ec394d0bc75    7d045c798c8b87a00 e0655d0481a7    d2d9fb58d93aedc676b5a 0",
                            "2d95516   6ee6be2       ce1033281ce37f    3c217b7e0e    55988089     50e9650797");
    }

    @Test
    publ    ic void test    WithLength35()  {
                        testHex(n ew C  ubeHas       h224(), 
                  "b2    d  cfe9ff19e2b23   ce7da2 a4207d3e5ec7c6112a8a22aec9675a886378  e14e5bfba   d4e",
                  "8c13de4e01b49bbff7a62   3a     41cf309b0b4  e385cffe80b26f3d9980c   e"            );  
    }

    @T    est
    public void tes tWithLength36() {
        testHex(new        CubeHash224(),
                                         "47f5697ac8c31409c086882734  7a613a3562041c633cf1f1f       86865a576e02835ed2c2492"   ,
                                   "e1aa  8b5        178230fbfa  e5b68ecd4c5      3 ad868baae    e73f60caedd4d33   27b")      ;
      }

          @Test
        public void        testWithL     en  gth37() {
        testHex(new CubeHas h22    4(),
                    "51   2a6d292e67ecb2fe486   bfe       9 2660953a75484   ff4c4f2ec    a2b            0af0edc  dd433       9c6b2ee   4e542",
                             "293b6a7c     32ff7cd11 6414d52     cc4b181b33e118  a8ab0d9fa341bd     6  3d3  ");
        }

    @Tes t
    public void te   stWithLength38        ()   {
             testHex(ne         w CubeHash224(),
                           "973c    f2b4dcf0bfa872b41194cb05bb4e16760a1840d8        34330180    257  6197ec19e2a1     493d8f4fb",
                "c1     9e3   e7b6  e4805  69734    1b       5151   3 917a aea2b1 e 56d   27769998666044d2" );
    }

    @Test
    public voi     d                 testWithLength 39()      {
        t            estHex(new Cube Hash224(),
                "80beebcd2e3f8a9451d44999  61c9731ae6  67cdc2   4ea     020ce3b9aa4bbc0a7f79e3 0a9     34467da4b0",
                   "7259afa7b57332cb40b6d5600112665d343  6b6b351  685   6a53d71a88   3"); 
             }

    @Test
    public      void testWithLength40() {
               testHex(n  ew Cub    eHa    sh224() ,
                "       7ab aa12e c2a7347674e444140ae0fb659d0   8e 1       c66decd8d6eae925fa451d65f3        c03    08e29446b8ed3",
                     "7c2ec7dbd   ff295     1782220794     0b39c22f9339acf5e700a34a53df  2        4f0");
    }

    @T est
       public vo  id te    stWithLength41() {
        testHex(      n ew    CubeHash224(),
                         "c 88dee9927679b8af422abcbacf283b904ff31e1cac58c      7819809f65            d5807d4672  3    b20f67ba      610c2b7",
                    "0f441 c  c2        d97899ed20b95e      0a4593ebfa7a6c      631a35ec357edc5194c4");
    }

    @Test
           publi   c void te stWithLen   gth4 2() {
              testHex(   new Cu  beHash224()      ,
                        "    01e43fe350fce   c450ec9b102053e6b5d56   e09896e0ddd9074f    e138e6038210       270c83     4ce6    ead   c2bb86bf6",  
                           "b1  8510a7 b8b ce2b8d0709af4028bcbb 460c2e3fe183105bbc4307ede  ");
    }

    @Test
    public void     testWithL     eng    t       h          43()      {
        te   stHex(new CubeHash224(),
                          "337023 37     0a4      8b6   2ee4354   6f17c4e  f 2bf8d     7ecd1    d49f9      0bab604b839c2   e 6 e5bd21540   d29ba27ab8e309a4    b7",
                              "6ed6e3d3bf451    aaf3b45f9e73e6525   03  2d81a88  7523069e90 1da31d  c");
    }

    @Test
    public          void   test    WithLength44() {   
        tes     tHex(new C     ube     Hash224   (),   
                           "689    2540f96  4c8c74         bd2db02  c0 ad884510     cb38afd44    38af31fc91275   6f3efec6      b32b58ebc38f c2a  6b      91359   6a8",
                "9bb9e52acf86ea98a9         ae8 6   096d596c38   bec69c   12eb9eb     0     da4545              7873");
          }

    @Test
           publ  i    c void tes  tWithLength45() {
        testHex(new    Cu    beHash224(  ),
                      "f5961dfd2b1ffff   da4ffbf305  60c165bfedab8c  e0   be525845deb8dc   61004b7db38467            205 f5dcfb34a2acfe96c0",
                      "06c6b    3335b609177a03    397ee05       60bca b719f3b8662c4327a e578c454");
    }

       @Test
       public vo  id testWithL    engt   h46() {
        testHex(   new   CubeHash224(),   
                "ca0   61a2eb         6ceed8881ce2057    172d869d73a1951e63    d57261384b80  ceb54   51e77b   06cf0f5a0ea15c     a907ee1c27eba",
                            "a850 54a6d38   23b1   3a2375b 22e966410a27e      fa07e9   c6edd198344ccad")   ;
     }   

            @T  e     st
    public vo  id testWithLen    gt       h47()           {
        testHex(new    Cu  beHash224(),
                   "1743  a77251d69        242750c4f1140532cd3c33f9b5ccdf75    14e8584   d4  a5f9f bd730   bcf84    d0d472    6364b9b     f95ab251d9bb",
                   "e1e47f9         e2528cc5ae  4de5668   dc3cee333c2cbdc594b57b882f9d   695c");
    }

    @      Test
    p    ublic       vo   id testWithLength    48() {
               test     Hex(new CubeHa    sh224(),
                 "d8f aba1f5194c4db5f176 fabf ff856924     ef627a37cd08cf55608bba8f1e324d7c7f157298eabc4dce7d89ce516           2    49  9f    9",
                       "c5ed9b03ea2c8f421705e8529b56a981bd6543699  10ba05f4ce23303");  
    }
   
    @Test
    public void t     estWithLength49() {
        t  e stHex(new C      ubeHash224(),
                    "    be9684be70340860373c   9c482ba517e   899fc81baaa12e5c6      d77279   75d1d41ba8bef788cdb   5cf4606c9c1c7f61aed59f97d",
                       "18e841f6a9  feb503ea005ced9b77e6294c97f749   9895f244f88feaeb");
    }

    @T   est
       public void t   estWithLength50() {
        testHex(new CubeHash224(),
                 "7e15d2b9ea74ca60f66c8dfab377d9198b7b16 de   b6a1ba0ea3c7ee2042f89d3786e779  cf053c7  7785aa9e692f821f14a7f51",
                           "1fa73aea4   7d21  a9823       4608580c96e5276d6f784522921a81   8552c914   "         );
    }

    @Test
         public void testWi    thLength51() {   
        testHex(n    ew CubeHash224(),
                "9a21  9be43713bd578015e     9   fda66c0f2d83  cac563b776ab9f38f3e4f7ef229cb443304fba401efb2bdbd7ece93   910   2298651c86",
                    "5f953b5ab6b13b3390fbfea519cfb90ceb2812ee75d63b96    d2f0af5c");
    }

          @Test
    public void test   Wit  hLength52(   ) {
        testHex(new Cub  eHash224(),
                   "c8f2      b693b   d0d75e     f99caebdc22adf4   088a95a3542f637203e283bbc326878 0e78    7d68        d28c     c3897452    f6a22aa85         73ccebf245972a",
                    "d4   4c    80c9a151d968      4722ddb44300f9274fd4926e      bcd27a04d5403380");
    }
  
         @Test
    publ ic v   oid test    WithLength53() {
        testHex(new Cu     beHash224(),
                 "ec0f99711016c6a2a07ad80d16427506ce6f441059fd269442baaa28c6ca037b 22eeac49d5d894    c0 b   f66219f2 c0       8e9d0e8ab21de52",
                "7d813b732b9cc38849ab20a99d3f5ce87ef2d6c2e6a8d5ca0977da78");
    }
   
    @Tes   t
    public void testWithLength54    () {
          te  stH    ex(new CubeHash224(),
                  "0dc4    5181337ca32a8222fe7a3bf42fc9f89744259cff6535 04d6051fe84b1a7ffd20cb47d469    6c e212a686bb9b  e9a8ab1c697b    6d6a33",
                       "   4a489baa0291ff2bf 87714d2d32d2fa929d537  cd24ba22fa0709e2d0");
           }  

    @Test
    public void   testWithLength55() {
        testHe    x(new C  u      beHash224(),
                    "de286ba4206e8b005714f80fb1cdfaebd  e91d29f84603e4a3ebc046  86f99a4     6c9e880b96c57482558  2e8812a26e5a857ffc6579f63742f",
                "b27e76ba8a9515489e50cf9f9cfcc2b2d8fde0    476b9b5823371ed3a8");
    }

    @Test
    public v oid     tes  tWithLength56() {
        testHex(  new CubeHash224   (),
                   "eebcc18057252cbf3f9c070f1a7321    33    56d5d4bc19ac2a411ec8cdeee7a  571e2e20eaf61fd0c33a0ffe   b297ddb77a97f0a415347db66bcaf",
                "bda3c685246895f5af   8     369b08c716e6ed2a   08ab67fc503502c7667ca");
    }

    @Test
    publ ic void testWithLength57() {
        testHex(new Cu  beHash224(),
                  "416b5cdc9fe9  5     1b  d361bd7ab  fc120 a5054758eb a88fdd68fd84e39d    3b09ac25497d36b43cbe7b85    a6a3cebda8db4e5549c3ee51bb6fcb6ac1e",
                  "7b5c969a0fcdf6   f6fb859e1cdd81fa8f27988   24b6cc4d2f1960fb20f");
    }

    @Test
          public void testWithLength58() {
         testHex(new CubeHash224(),
                     "5c5faf66f32e0f8311c32e8da8284a4ed60891a5a7e50fb2956b3cbaa79fc66ca376460e100415401fc2b8518c64502f187ea14bfc9503759705",
                  "a61a91ee00462e92b25   8a2a9b     fbda5  16fcdab93928e446f7dc2952d4");
    }

    @Test
    public void testWi thLength59() {
        testHex(new CubeHash224(),
                  "7167e1e02b e1a7ca69  d788666f823a e4eef39271f3c26a5cf7cee05bca83161066dc2e217b330df821103799df6d74810eed363adc4ab99f36046a",
                "aaf236c93c328c74cee26f81ede14771f9d6bb8cbf  a3f437913cf673");
    }

    @Test
    pu     blic voi  d te     stWit    hLength60() {
        testHex(new CubeHash224(),
                  "2fda311dbba27321c5329510fae6948f03210b76d43e7448d1689a063877b6d14c4f6d0eaa96c150051371f7dd8a4119f7da5c483cc3e6723c01fb7d",
                "3df6922980874737c830301530db923c202fef966543988b3b021ff3");
    }

    @Test
    public void testWithLength61() {
        testHex(new Cub    eHash224(),
                "95   d1474a5aab5d2422aca6e481187833a6212bd2d0f91451a67dd786dfc91dfed51b35f47e1deb8a8ab4b9cb67b70179cc26f553ae7b569969ce151b8d",
                "5cfbd4f7b47ce9bddb0c7e4479ca48533187d1349cbe7fd0ae9cb1ff");
    }

    @Test
    public void testWithLength62() {
        testHex(new CubeHash224(),
                   "    c71bd7941f41df044a2     927a8ff55b4b467c33d089f0988aa253d294addbdb32530c0d4208b10d9959823f0c0f0734684006df79f7099870f6bf53211a88d",
                  "16ee4093f8461773b91e205d5238306a87672e2e7611c002799b5adb");
    }  

    @Test
    p    ublic void testWithLength63() {
        testHex(new CubeHash224(),
                "f57c64006d9ea761892e145c99df1b24640883da79d9ed5262859dcda8c3c32e05b03d984f1ab4a2302  42ab6b78d368dc5aaa1e6d3498d53371           e84b0c1d4ba",
                  "b4e33f3aab86c5d32ae4c1a6fb104afd57febd7c7ec80aba23922d8f");
    }

    @Test
    public void testWithLength64() {
        testHex(new CubeHash224(),
                "e926ae8b0af6e53176dbffcc2a6b88c6bd765f939d3d178a9bde9ef3aa131c61e31c1e42cdfaf4b4dcde579a37e150efbef5555b4c1cb40439d835a724e2fae7",
                "06a2a08f2ca14ca233b98cb195c6fc284ce6ef026961ca2278178040");
    }
}
