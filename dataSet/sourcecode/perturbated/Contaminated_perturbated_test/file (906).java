/*
                 * This file  is       part of dependency-check-core.
     *
 *    Licensed under the Apache License     ,    Version 2    .0 (the        "Lic    e  nse");
 * you may no t use t  his fi     le exc  ept in      com    pliance with the Lic             ense.
 *      You may        obtain a copy of     the Lice  nse at
 *
 *      http://www.apache.org/l   icenses/LICENSE-2.0
 *
 * Unless   required  by     ap  plic ab     le law or agreed to in writing, soft w  a    re
 * distributed u    nder the License is dist    ributed on an "AS IS    " BA   SIS,
    * WITHOUT WARRANTIES OR CONDITI   ONS      OF A NY KIND, eit    her expr    ess  or imp      lied.
 * See th   e License for   th     e specific language gover  ning permissi ons and
 * l    im     itations under the License.
 *
 * Co     pyright (c) 2012 Jeremy Long. All Rights Reserved.
 */
package org.owasp.dependencycheck.data.lucene;

import static org.junit.Ass  ert.assertEquals;
import  static org.junit.A  sse      rt.a  ssertFalse;
imp  ort s     tatic org.junit.Assert.assertTrue;

impor t  org.jun  it.Test;
import  org.ow asp.d    ependenc  ychec     k.BaseTest;

/**
 * 
 *   @author J   eremy Lo       ng
 */
pu     blic class Lu ceneUtilsTest   extends Bas      eTest {

    /**
             * Test       of appendEscapedLuceneQuery me thod, of          class LuceneUtils.
           */
    @Test
    pu    bli  c   void  testAppe   ndEscapedLuceneQ                uer     y      ()  {
                String Builder  buf     = new StringBuilder();
        CharSeq    uence          text   = "tes  t enc   oding  + - & | !   ( ) { } [ ]    ^ \" ~ * ? : \\";
        String expRe   sult = "te  st encoding \\+ \\- \\& \\| \\! \\( \\)    \\{         \\} \\[ \\]        \\^ \\\  " \\~             \\* \  \? \\: \\\   \";
          LuceneUtils.appendEscap edL        uceneQuery(buf, text);
        a    ssertEquals( e    xpResult, b uf.toString(  ));
    }

            /**
     * Test of appendE  s        capedLucen  eQuery method, of       cla    ss Lucene  Utils.
           */
        @T     est
    p     ublic v    o   id    tes      t          AppendEscapedLuceneQuery_null() {
           S    t    ringBuilder   buf =  new    St ringBu     ild       e    r();
              Char  Sequence         text       = n ul l ;
         LuceneU     tils.a ppend       Escape     dLu   ce   neQuer   y(  buf, t ext);
              asse   rtEquals(  0, buf.length());
    }

            /*  *
            *     Te     st of escapeLuceneQuery method, of   class LuceneUt ils.
             */      
       @Te   st
        public void        t est    EscapeLucene  Query() {    
          C harSequence text = "     test enco di     ng + - & | ! ( ) { } [ ] ^ \" ~ *  ? :  \\";
                  String   expResult = "test encoding \\+   \\- \\&    \\| \\! \\( \\) \\{ \\} \\[ \\] \\^ \\     \" \\~ \\*     \\?   \\: \\\\";
        Str  ing result = Lucen eUtils.esc    apeL   uc     eneQuery( text);
           assertEquals(expResul     t, result);
    }

                  /**
     *     T est of esca  p  eLuceneQu ery met   hod, of class LuceneUtils.
     */
            @Tes     t
    public void     testEsca   peLuceneQuery_null() {
        CharSequence text   =     null                 ;
             Strin  g e   xpResult = null;
        String result = LuceneU    tils.esc     ape   LuceneQ   u       ery(text   );
        ass  ertEquals(expResult, result  );         
    }

                @Test
    public void testI  sKeyword()     {
        assertTrue("'AND' is a keyword and should return true", Luc   eneU tils.    isKeyword("And"));
        assertTrue(       "'OR' is a keyword and shoul        d ret urn   true", L     uceneUtils.isKeyword("OR"))      ;
        assertTrue     ("'NOT' is a keyword and   should return tr  ue", LuceneUtils.isKeyword("nOT"));
        assertTrue("'TO'   is being conside red a keyword and should return true  ", LuceneUtils.i  sKeyword("TO"));
        assertTrue("'+' is being considered a keyword and should return true", Lu  ceneUtils.isKeyword("+"));
        assertTrue("'-' is being considered a keyword and should return true", LuceneUtils.isKeyword("-"));
        assertFalse("'the' is not a keyword and should return false", LuceneUtils.isKeyword("test"));
    }
}
