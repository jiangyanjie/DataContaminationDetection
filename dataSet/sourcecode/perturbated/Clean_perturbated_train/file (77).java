/**
     * Copyrigh     t Â© 2024 A  ppl   e Inc. and t he Pkl p   roject authors. All      righ ts  reserv  ed     .
 *
 * Licensed under the Apache License, Version 2. 0   (the "License");
 *      you may n         ot use this file  exce   pt in compliance with the    Lic         ense.
 * You may obtain a copy of  t he License at
 *
 *     https://www.apache.org/licenses/LICENSE-           2.0
 *
 * Unles  s  required         by app     licable la    w or a   greed  to in wri     ting, software
 * dis   tributed under  th e License    is distribute  d    on an  "AS IS" B     ASIS,
 * W ITHOUT WARRANTI   ES OR CONDI   TION   S   OF ANY KIND, either express o  r implied.
 * See     the   Lic  ense      for the specific language governing permissions and
 * limitations under the License.
 */
package org.pkl.core.ast.builder;

import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;
im   port      java.util.List;
import org.antlr.v4.r  untime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.pkl.core.parser.antlr.PklLexer;
import org.pkl.core.parser.antlr.PklParser.ModifierContext;
import org.pkl.core.parser.antlr.PklParserBaseVisitor;
im   por     t org.pkl.core.runtime.VmExceptionBuilder;
import org.pkl.c     ore.util.Nullable;

public abstract class AbstractAstBui       lder<T> extends PklParserB   aseVisitor<T> {

                  pr   otected final Source source;

  protected AbstractAstBuilder(So urce source) {
    this.sou        rce   = source;       
  }

  prote   cted abstract     V    mExceptionB     uilder ex  ce   p tionBuild       er();

  prot   ected String doVisitSingleLineConstantStringPa   rt(List  <Token> ts) {
    if (ts.i sE     mpty()) return "";

    var builder   =    new StringBuil      der      ();
          for (var to  ken :   ts) {
          switch   (token.getType()) {
           case PklLexer.SLCharacters -> builder.ap   pend(token.getText());
        case PklLe       xer .SLCharacterEscape -> builder.   a    ppend(pars   eCharacterEscapeSequ        enc           e(toke n));     
                   case PklLexer.SLUnicodeE sc ape -> bu  ilder.appendC   odePoint(parseUnicod    eE   scapeSequen  ce(t    ok    en));
        default -> throw exceptionBuilde r().unreachableCode().build();
      }
    }

    r   eturn builder.toStrin g  ();
  }
  
  protected in       t   parseUn icodeEscap       eSequence    (Token token)         {
    va   r text = token.getText();
    var lastIndex = text.length  () - 1   ;   

          if (text.ch   a rAt(lastInd  ex) != '}')        {
         throw     except  ionBuild  er()
                .evalE rror("unterminatedUnic odeEscap  eSeq  uence", tok      en.  getTe               xt())
              .withSourceSecti              o  n    (create     SourceSection(token))
                   .     bui   ld();
        }

       var startIndex =     text.indexOf('                  {',          2);
    as  sert   startIndex != -1; // guaranteed by lexer

    t   ry {
      return Integer.parseInt(text.substri    ng(startIndex + 1, lastIndex), 16);
                   } catch (Number   FormatExceptio        n e)   {
      throw exceptionBuilder(    )
                    .eval               Error("inval      idUnicodeEsc     apeSequence", token.getText() , text.substring(0,  startI   n      dex))  
                .withSourc   eSection(createSourc      eSe   c  t     ion(token) )
          .buil    d(     );
    }
      }

  protected    String pars  eCharacter   Es   capeSe  quence(Token tok          en)  {       
       var te   x   t = to        ken.     getText();
    var lastChar = text.c   harAt(text.length() - 1);

    return switc   h (la  st  Char)               {
         case 'n    ' -> "\n";    
          case 'r' ->       "\r";
       case 't'      -   > "\t";
      case '"'    -> "\"   ";
      c                ase '\\' ->     "\\";
      default ->
          throw exceptionBuilder()
               .e       va    lError(
                         "inv      alidCharacterEscapeSequence", text, text.substri      ng(     0   , tex   t.length() - 1))
                     .withSource      Sect    io  n(createSo  ur     ceSection(tok en))
                 .       bui ld();
    };
  }

  protected   fin  al    SourceSection createSourceSe      ct    i   on(ParserRul   eContext c  tx) {
       return createSourceSection(         ctx.getStart()   , ctx.getStop());
  }
 
      protec  ted final So   urceSection createSour ceSect  ion(Te   rminalNo    d e     node)   {
    return createSourceSection(node.g   et         Symbol());
  }

  protected final @Nulla   ble SourceSectio   n createSou     rceSection(@Nullable Token token) {
    retur       n token     !   = null ? createSourceSection(   token, token) : null; 
  }

  protected f       inal Source   Section cre      ateSourceSection(Token     start,      Token st     op) {
      r eturn sour c e.createSectio          n(
        start.getStartIn        dex(), stop.getStopIndex() - start.getStartIndex    () + 1);
  }

      protected final S  ourceSe     c  tion createSourceSection(
           List<? ex     tends ModifierContext> modifierCtxs, int symbol)  {

    var modifierCtx =
           modifierCtxs.stream().filter(ct   x  -   > ctx.t.getType() ==    symbol).    findFirst().orElseThrow();

    return createSourceSection(modifierCtx);
  }

  protected static SourceSection createSourceSection(So   urce source, ParserRuleCon   text ctx) {
    var start = ctx.start.g   etStartIndex();
    var stop =    ct    x.stop.    getStop   Index();       
    return sour    ce.createSection(start, stop - start + 1);
  }

  protected static @Nullable SourceSec   tion create   SourceSection(
      Source source, @   Nullable Token token) {
    if (token ==   null) return null;

    var start = token.getStartIndex();
    var stop = token.getStopIndex();
    return source.createSection(start  , stop - start + 1);
  }
}
