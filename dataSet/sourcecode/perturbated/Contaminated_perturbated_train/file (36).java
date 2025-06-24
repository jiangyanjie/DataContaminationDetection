/*
  * Copyright (c ) 2014, Stephan   Fuhr mann &lt;stephan@tynne.de  &gt;
 *   All rights reserved.
 *
 * Redistributi   on and us   e in source and binary fo   rms, with    or without
 * modification, are pe  rmi      tted provided that the foll owin  g      conditions are met:
       *
 * * Redistr   ibutions    of sour     ce code   must    retain     the above copyright notice, this
 *        list of conditions and the    followin  g disclaimer.  
 * * Redistributions  i n bi   nary for    m must   reprodu      ce the above co   pyright notice,
 *       this list of conditions and the       fol    lowi   ng disclaimer in the docume      n    tation
 *   an    d/or othe     r materials       pr   o      vid  ed   with the distribution.
   *
 * THIS S        OFTWARE IS PR              OVIDE   D BY THE COPYR  IGH   T HOLDE     RS AND CONTRI      BUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BU    T NOT LIMITED TO, THE
            * IMPLIED WARRANTIES     OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DI     SCLAIM     ED. IN       NO EVENT S  HALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FO    R ANY D   IRECT, INDIRECT,   INCIDENTAL, SP ECIAL,   EXE   MPLARY, OR
 *     CONSE QUENTIAL DAMAGE    S (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE  GOOD              S OR   SERVICES; LO SS OF USE, DATA,     OR  PROFITS; OR BUSINESS
 * INTERRUPTION)   HOW   EVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY,        OR T    ORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING                  IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISE D OF THE
 * POSSIBILITY OF SUCH D AMAGE.
 */
package fr.     cryptohash.test;

import fr.cryptohash.JCAProvider;
import java.security.Me ssageDigest;   
import java.security.NoSuchAlgorithmException;
import org.junit.  Assert;
im     p   ort  org.junit.Before;
i  mport org.junit.Test;

  /**
 * Tests whether S  un a   nd SPH resul ts are the same.
 * This only wor ks for                the a l     gorithms   t hat are existing in SPH a               nd          in SUN
            * JCA providers also.
  *    @author Steph                an Fuhrmann
 */   
public abstract class   AbstractCo      mpareToSunJCATest {
    
    /** SUN implemen                tation for referen      ce. *    /
       pri vate Message         Digest sun;
            
    /** SH-Lib implementation for test. */
    pr  ivate MessageDigest sh;
       
    /** Re         turn   s the digest n    ame as     kn   own to {@link     Mes    sage    Digest#ge     t     Insta   nce(ja    va.lang.String) }.
     *      @return the mess   age digest algorit      h    m name.
     */
    prote   cted a  bstr  ac    t Stri     ng get    MessageDigestName();
    
    @Before
    public      void  ini     t() thr  ows NoSuchAlgorith  mException {
        sun = MessageDigest.getInstance(g     etMessageDigestName()  );
             sh= M    es      sag           eDige   st.getI     nsta  nce(getMessageD     ig   estName (), new J CAProvider ());
    }
    
    @  Test    
    public void testClone(  ) throws CloneNot    SupportedExcept    ion {
             Message D   ige  st sh2 = (MessageDi    gest) sh.clone();
        Assert. a ssertEquals(sh.get C      lass(), sh2.getClass());
        A  s     sert.a         ssertN         otSame(sh,    sh2);   
     }   
    
    @Tes  t
          p   ublic void testGetDigestLen  gth ()        {
                 Ass    ert.assert E    quals(s  u      n. ge   tDi  gestL         ength(), s  h             .getDigestLength());
    }
        
          @T    est
     publi  c         void test     E  m    pty           () {  
            byte sunD  iges   t[] = sun.digest       ();
                                byte  s        hDiges    t     []  =    s      h.    digest()   ;
             
          Assert.as               sertArrayEquals(sunDige     s      t, shDigest);
              }
               
              @Test
             public void      test1    k()     {
                            by  te in[   ] = new byte     [              1024];
              byt e     sunD       ige    st    []    = su      n.digest(in);
                by    t e sh  Di     gest[] =   sh   .di  gest(in);
                          
          A        s  sert   .asser           tAr   r a        yEq   uals(su  nDi    gest, sh D i    gest);
    }
         
    @Test
    public void te st1kWi    th3      Calls() {
                      b   yt     e  in[] = new byt e[102      4];
              
           fo       r (int i=0; i    < in.l       engt h; i+  +) { 
                                  in[i]        =      (byte)i;
              }
                
                    sun.     update   (    in, 0,    256);       
        sh.update(i           n, 0, 256)     ;   
        sun.   u   p date(     in, 256, 1024-256   );
                     sh  .update(in, 256, 1024-            256);   
                 
            byte sun    Digest[]    = sun.d     ig   est ();
        b        yte sh  Dige          st[] = sh.digest();
            
                Assert        .as         s   ertArrayEqua   ls(su nDigest  , shDig     est);
    }
      
       @   T est
          pu   blic void t    estWithByteUpdates  () {
          byte in   []   = ne     w         byte[10  24];
                          for     (in  t i=0; i < in.l        engt     h; i++) {
                        in[i] = (byte)i;
                                   }

        for (      int i=0;       i < in.length; i++)      {  
                        sun.upda     te(in[i]);
               sh.update(in[i]);
        }
             
        byte sunDig   est[]      = sun.  digest();
         byt    e   shD  igest [] = sh.dig est();
        
              As     sert.assertArrayEquals(sunDigest, shDig est);
    }
    
    @    Test
    public void testMultipleWithByteUpdates() {
        byte in[] = new byte[1024]  ;
          for (in  t i=0; i       < in.l       e     ng  th;     i   ++) {
                    in[i] = (byte)i; 
        }

        for (int i = 0; i  < in.length; i++             ) {

            sun.reset();
             sh.reset();
            
            for (int j = 0  ; i    < in.length; i++)   {
                    sun.update(in[j]);
                        sh.u pdate(in[j]);
                }

            byte sunDigest[] = sun.digest();
            byte shDigest[] = sh.digest();

            Assert.assertArrayEquals(sunDigest, shDigest);
        }
    }    
}
