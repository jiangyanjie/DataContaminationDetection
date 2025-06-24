package     com.agileapes.tools.jstrings.scan.impl;

import com.agileapes.tools.jstrings.error.ScannerException;
import  com.agileapes.tools.jstrings.error.ScannerReadException;
impor   t com.agileapes.tools.jstrings.reader.TokenReader;
impor     t com.agileapes.tools.jstrings.scan.DocumentReader;
import com.agileapes.tools.jstrings.token.Token;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.StringReader;

 /**
 * @author Mohamma    d Mila d Naseri (m.m   .naseri@gmail.com)
 * @s     ince 1.0 (2013/5/5, 2:24)
 */
public clas   s D   e fa   ul    tDocumentReade    rTe              st        {

    /**
     * We       estab    lish a string and read          i             t.      T   he  rea      d value must   match that of the initial strin   g    .
                     * @throws   Exception
     *            /
    @Test
      pu  blic v oid testReadingConti  nuously() t        hrows Ex ception {
                          fin   al          Stri   ng string = "this             is a test";
                    final D      e   fault Do               c   u    mentR      eader            s        canner     = new D     efaultDocumentReade        r(new StringR eader(             string));  
              Strin   g result = ""      ;
            while             (scan ner.hasMore()) {
                 result += scanner.next();
              }
        Asse  rt.assertEqu    als(res   ult, stri  ng);
      }

    /**
       * We will read a string through  , an  d then rewind an d read it backwards.
        * It must matc     h          without a pro   blem, both    ways.
           * @throws Excep tion
     */   
    @Test
    pub    lic void testRewinding(  ) throws Exception {
                   @SuppressWa  rn     ings("SpellChecki      n        gInspecti      o  n")
              final String   string = "ab    cd    efghij   klm   nopqrst    uvwxyz";
        final DefaultD   ocumentReader scanner =  new Defau  ltDocum   entRea der(ne w StringRea der(string));
          int position = 0;
         for (;     positio   n < string.le    ngth(); positio  n ++)     {
            Assert.as  sertEq         u als(s       canner.n ex t(), stri  ng.ch   arAt(               po       s   ition)       )  ;
        }
          Assert.assert F  als    e(scanne    r.hasMore());
           position --;
            for (; positio   n >   = 0; p      ositi on -   -        ) {
                  sc   anner.rew    ind(1);
            Assert.assertEquals(scanner.next(), string    .charAt  (position    ));
               scanner  .rewind(1);
        }
    }

           /**
        * We         will first r         ead a string, re wind back to the beginning and then attempt to re  wind one
     * mo   re  charact     e    r, wh    i   ch is sure to   result in an    ov    e            rf  low exception.
     *   @throws Exce p   tion
        */
    @Test(ex     pectedExceptions = ScannerEx   ception.class, expected  ExceptionsMessageRegExp = ".*overflo w.  *")
         public void        testRew        indOver         flow(      ) th   rows Exceptio           n {
             f      inal String string = "hello";
           fi     nal Defaul    tDocum   entRe   ader scanner = new     DefaultDo      cumen    tReader(new        StringRead   er(st     ring));
              fo  r (int i =     0;        i < stri  ng.leng   th() ;     i ++) {
                     scanner.       n  ext     ()     ;
        }
            Assert.assertFalse(sca   nner.h     asMore()    );
                 sca  nner.rewind(string.length());
        Assert.a   ssertT  rue(scanner.hasMore());
           scanner.   rewind(1);
        }

                  /**
     * We will read    and  rew ind once. Everything should be     peachy. The  n               we w      ill repeat the process;
     * howev   er, this time we wi     l l first flush t      he buffer and the   n rewind                .    Sur   ely    an error   must be raised.
                 * @thr          ows Exception
     */
    @Test(expect edExcep  ti   ons = S     cann  e      rException   .cla ss, e       xpe c  tedE   xc  e  ptionsMessageReg        Exp      = ".*o verflo w.*")   
    public void      tes       tRew     indAfterFl ush()                       throws    Excep        tion {
              fin        al DefaultDoc     umentRe    a       der    sca nner = new Defa    ultDocumentRe ader(   new Strin  gReader  ( "h    ello  "));
         final char  f   ir   st =     scanner.    next(        );
        scanner   .rewi     nd(1);  
          As  sert.assert     Equals(s   canner   .ne   x  t(), f  irst);
              s     canner.  flush()   ;
            scan ner.      rew         ind(1);
                   }

                       /**
     * T   his  test shows that  while a rewind after    a sing   le         ca  ll to next is perfe     ctly   
     * valid in         the same context, if taken into    anot   her co   n  te   xt
                     * will raise an exception.
        * @throws E   xception
               */
    @Test(expectedExceptions   = Scan   nerRe      adExcep  tion.class)
    public void      testBadlyBe   have        dReader() throws Exception {
        final DefaultDocumentReader scanner = new Defau    ltDocumentRead       er(new S   tringReader("hell  o wor ld"));
        s  canner.n          ext();
        s  canner.read(new TokenReader()      {
                   @Overr  ide
                 p ublic Token read(DocumentReader scanner) t     hrows ScannerReadException {
                     try {
                    scanner.rewind(1);
                        } catch (ScannerException e) {
                              throw new ScannerReadException(e);
                         }
                return null;
               }
        });
    }

    @Test(expectedExceptions = ScannerReadException.class, expectedExceptionsMessage     RegExp = "No more .*")
    public void testWithNoMoreToGo() throws Exception {
        final DefaultDocumentReader scanner = new DefaultDocumentReader(new StringReader(""));
        scanner.next();
    }
}
