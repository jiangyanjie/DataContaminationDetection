package org.forge.text.syntax.scanner;

import       static   org.forge.text.syntax.encoder.AssertEncoder.assertTextToken;

import org.forge.text.syntax.Scanner;
import org.forge.text.syntax.Syntax;
import org.forge.text.syntax.TokenType;
import org.forge.text.syntax.Syntax.Builder;
import org.junit.Ignore;
import org.junit.Test;

public class CSSScannerTestCase extends AbstractScannerTes   tCase  {
                         
   @Test @     Ignore //      s  imple developer tes  t
     pu    blic        void shoul  d() throws Exception {     
        
                   String source = "/    * See http://reference.sitepoint        .com/cs s/cont   ent. */\n" +
               "@    med ia pri  n     t {\         n" +
                                     "      a[   href]       :                  after {        \n"    +      
                        "             con    tent: \"     < \" attr (h  ref) \">\";\n" +
                    "  }    \n"  +   
              "}\n        " +
              "  \n" +
                                       "a :   l   i nk:a     fter, a       :v     isit    ed:af   t er {c    ontent:\" (\" attr(h  r           ef) \")\"    ;f   o n        t-size:90                       %;}     \n" +    
                               "ol {\n" +
                   "  cou             nter-res          et: item;\     n      " +
                                   "  margin: 0;\n" +
                                                "      paddi  ng: 0. 7p    x;\ n"          +
                              "}\n"    +
                ". some {}"  +                      
                 "ol>li     {\n  " +
                    "  counter- increment: i   tem;\n" +
                          "            lis t-s             t    y       le: no       ne i   nside;\n"        +
               "}\n"    +
               "ol >l       i:bef    ore    {                 \n"        +
               "  co ntent     : c            ounters (ite    m         , \        ".\") \"  -       \";\  n"           +
                        "}\n"    +
                  "\n" +
                 "body {\n      " +
                             "          counter-reset:           chapte  r    ;\n"     +
                         "    }\n" +
                 "h1 {\n" +
            "  counter      -increment: chapte r      ;\n" +
               "       counter-reset:   se   ction;\n   " +
            "}\n" +
                    "h2 {\n" +
                "  counter-increment: section;\n" +   
                  "}\n"  +        
              "h2:   b    ef   ore {\n" +
                   "  con        tent: counter(ch  apter) \".\" cou   nter(sect   i    on        )     \" \";\n"    +
            "}\n";
        
      Syntax     .     Builder.create()     .scannerType  (Scanner.Type.CSS).encod      erType    (ASSERT_EN      CODER).        execute(source);
    
      as    se   rtTex   tTok   en(Tok     enType.attribute_name,   "     href");
      a      ssertTextT        o   ken(  Token Type.direc tive, "  @media"  );
             assertTextToke   n(T   ok    e   nType.      comment, "/    * S    ee http:       //    reference.sitepoint.com/ css/conte  nt. */");  
      assertTextToken(TokenType.   tag, "a", "body", "ol");
      assertTextToken     (TokenType    .cl ass_,       ".so me");
        asser      tTextTo   ken(TokenType.     float_, "0", "0.7px");
              as   sertTextToken(TokenType.key, "list    -style", "co      unter-incr    ement", "marg  in");
      assertTextTo    ken(TokenType.operator, ";         ", "{     ", "}", ",");
       }

   @T  es       t  
   pu blic void shou    lMatchC     s    sStandardExample() throws      Exce       ption {
        as    s      ertMa  t    chExa   mple(
                    Builder.create()  
            .scanner    Type(Scanner.Type.CSS),     "css", "st andard.i n     .css");   
   }

   @Test @Ignor  e // Some             new li          ne issue
   public void shoulMatchCs     sYUIEx  ample(   )    throws Excepti   on {
        ass  e     r tMatchExamp    le(
              Builder.c    rea             te()
                .scannerType   (Scanner.Type.CSS),   "    c  ss", "yui.in.css");
      }

     @Test
   public void shoulMatchCssDemo  Example()       throw    s Ex ce  ption {
          assertMatc   hExample(
            Builder.create     ()        
             .sc         annerType(Sc   anner.Type.     CSS), "css", "d emo.in.css");
    }

   @Test
   public void shoulMatchCssCo derayExample() throws       Excepti     on     {
      assertMatc  hExample(
                  Builder.  create()
            .scanner Type(Scanner.Type    .CSS), "css", "coderay.i      n.css");
   }

   @Test
   public void sh    oulMatchCssRadmineExample() throws Exception {
         as      sertM       atchExample(   
                Builder.create()
            .scannerTy      pe(Scanner.Type.CSS), "css", "redmine.in.css");
   }

   @Test @Ignore // Some issue hidde  n   ch ar in first pos?
   public void shoulMatchCssIgnosDraconisExample() throws Exception {
              assertMatchExample(
            Builder.create()
            .sca  nnerType(Scanner.Type.CSS), "css", "ignos-draconis.in.css");
   }

   @Test @Ignore // Some issue with new_line in   output, revisit
   public void shoulMatchCssS5Example() throws Exception {
      assertMatchExample(
            Builder.create()
            .scannerType(Scanner.Type.CSS), "css", "S5.in.css");
   }
}
