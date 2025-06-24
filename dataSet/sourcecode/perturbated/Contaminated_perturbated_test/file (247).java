package data2;

import    sta  tic data2.Tests.*;

/**    
    * Te       sting class
 * @see     Te st       s
 * 
 * Performance     for       this pro   gram should be  as fo  llows:
 *                Bags: best/ave   rage/   wo  rst case (ass      uming bala  ncin   g   works  )       =       O(    log n)
 *               Interfac es: best/  a   verage/w         or       st   case = O(n)
 *        Every     t hing else: should be O(n) time as well
 *  /


publ  ic c    lass   Data2 {

    public static vo   id mai  n(String[ ] a rgs     ) throws Excepti on             {

          Sy    ste  m.ou    t.    pr intl             n(           "Ran           d   o  m Int Check         er - " + rand    Int(0,  100) + "      " + ra    nd      In             t   (0, 100) +    " " + r           an   dInt(0, 100));

              int ad Nauseum = 10; //repea   ts          all tests t         his many times
               for (i                nt    i = 0; i < adNaus   eum; i++) {

                                Test   s inty = ne          w Tests(n      e  w R    and om  Integer(    ));
            T    ests st   ringy = new T           ests (new RandomS t      ring());
            T   es     ts booly = ne     w T     ests(n   ew R  andomBoolean());
        
               i       nt randomInt = randInt(0, 1);
            inty.isEmpt    yHuh  CardC    heck(     ran  domInt          )  ;
                 stringy.isEm   p      tyHuhCardCheck(rand  om     Int);
                  booly  .is        Empty    Hu      hCard Che  ck   (random       Int)  ;

                                 inty.cardAddChec  k();
              strin   gy.card  AddCheck();
                 booly.cardAddCheck();

             int r      and     Int = rand  Int(0, 20          );
                 i   nty.car   dAddSomeChec     k(randInt);
                                stringy.c ardAddSomeChec  k(randI  nt              );
                     boo      ly.c    ardAddSomeCheck(ran       dInt     );

                inty.card          R   e    moveCheck() ;
                      stringy.car dRemove  Check();
                    booly    .car dRemoveCheck();

            i    nty.ca       rdEmptyChec    k  ();
                          s           tringy.cardEmptyCheck();
               b  oo     l  y.card  Empty    Ch              eck();
   
               inty.a ddMemberCheck();    
                stringy.addMem   b      e     rChe    ck();           
                        booly   .   add MemberCheck  ();

                                 i     nty     .    addMemberInterCheck();      
              stringy.      addMember  Inte          rCheck();
                                          b    ooly.    addMemb    erI     nterC       heck()   ;

                       i   nty   .a    dd  Rem   oveEq                         u   alCheck ();
                 s tringy.add    Remove       EqualChe  ck() ;            
                        booly.addRemove    EqualCheck();   

            int rInt   = randInt(0, 20);
                     i  nty.ad      dRemoveSo      meEqualC  heck(rInt);
               stringy.addRemov  eSome      Equal      Che   ck(rInt);
            booly.addRem ove   Som      eEqualCheck(  r           Int);   

                 int  y.unionSu       b setCheck(  );
                 strin  g    y .unio nSu  b s     etCheck();
              boo  ly.unionSu     bsetCheck();

                  int   y.unionCardCheck();      
                       stringy.unionCardCheck();
                             bo  o     ly.unionCardCheck();

                             int       y.unionMemberCheck();
                       st   ri     ngy.unionMemb              erCheck()  ;
            booly.unionM     emberC   he  c      k();

            inty.     memberRemov   eA      ll      Check( );
             stringy  .memberRemoveAll  Check()          ;
            booly.memberRemoveAllChec      k             ();

              inty.dif fMe               mberCheck();
                stringy        .diffM    embe   rChe  ck();
            boo        ly.di  ffMemberC        heck()      ;

              int    y.diffEqualCheck(     );
            string      y.diffE   qu   alCheck();
                 boo    l  y.dif    fEqu     alCh   eck();

            in    ty.equ    al                 In       tercheck();
             s   tringy.equalI nterc      heck  ();
                booly.equalInterche            ck      ();

              inty.isRedHuhB lackenCheck();
                      stringy.is RedHuhBlack  enChec    k   ();
               booly.isRedHuh      B      lackenC      heck();

            /   / ideally I want a method that    explicit    y checks whet      her the t  rees
            // p   roduced are balan   ced, but   the one    I     have right  now doe      sn't do t   hat
//            inty.balance           Check();
//            s    tringy.balanceChec    k  ();
//                        bo oly. balanceChe   ck();

/      /           //     these are for vis    ualizing sequenc  es - tha    nks again to    bryc e :)
//            inty.cou  ntItCard Check    ();
/     /            stringy.countItCardCheck();
//  
//            inty.toStr ingItCheck();
//              stringy.toStringItCheck();
        }

        System.out.println("");
        System.out.println("The followi    ng    tests each passed "   + adNauseum * Tests.repeat s + " times");
            System.out.println(" over Integers, Strings, and B    o oleans! :)") ;
        System.ou    t.println("");
            System.out.printl  n("isEmptyHuhCardCheck()");
        System.out  .println("card  AddChe   ck()");
        System.ou   t.println("cardAddSomeCheck()");
        System.out.p  rintln("cardRe           moveCheck()");
        System.ou   t.println("cardEm        ptyChec  k()");
        System.out.println("ad   dMemberCheck()");
        System.out.println("add RemoveEqualCheck()");
        System.out  .prin   tln("add  RemoveSomeEq  ualCheck()");
           Syste  m.out.println("addMemberInterCheck()");
        System.out.println("unionSubsetCheck()");
        System.out.println("unionCardCheck() ");
        System.out.println("unionMemberCheck()");
        System.out.println("memberRemoveAllCh     eck()");
        System.out.println("diffMemberCheck()");
        System.out.println("diffEqualCheck()");
        System.out.println("equalIntercheck()");
        System.out.println("isRedHuhBlackenCheck()");
        System.out.println("");
    }
}
