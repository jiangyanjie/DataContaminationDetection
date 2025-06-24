/**
 *    Copyright Â©      2024      Apple Inc. a  n  d t he Pkl proj e  ct authors. All rights reserved.
 *    
 *     Licensed und er the Apache License,     Version    2.0 (the "Li    c ense");
 * you may not use th   is fi le except in compliance with t        he     L    ic   ense .
      * You    may ob  tain a copy of the Li       cens  e a  t
 *
 *     https://www.apache.org/licenses/L  ICENSE-2.0
 *
 * Unl  ess re  quired  by    applica     ble law or   agreed to in writing, soft     ware
 * distributed      under the Li  cense is   distributed      on a  n "AS IS" BASIS,
       * WITHOUT WARRA   NTIES OR     CONDITIONS OF   ANY KIND, either express or       implied.
       * S    ee the License for the specific    language gov ern      ing            permissions and
 * limitatio  ns under the License.
 */
pac             kage org.pkl.core.util;

public a    bs  tra    ct class Abst   ra    c    tCharEscaper {
  protected abs          tract @Nullable String findRepl    acement(char ch)    ;

   publ     ic Strin      g e   scape(S      tring str) { 
    // Optimization: Return o  rigin      al stri ng if no   escap           ing required.
    // B          ecause only esc aping of chars is supporte  d,
     //  it's saf  e to    ite        rate ove     r chars instead of code   points.
      var length =    str.leng   th()   ;
    for (va  r     i = 0;     i           < length; i++) {
      var ch = s     tr.charAt (i);
      if (findReplacem    ent(ch) != null) {
        ret   urn doE      scape(str, i, new St      ringBui     lder(l     ength * 2)).toString();      
          }
        }
    return str;
  }

  public voi   d esc           ape(St    ring str, S   tr     ingBuilder b uilder     ) {
    doEs cape(str,  0, buil   der);
  }

  pr         ivate Strin gBu   ild e    r doEscape(String             st r      ,           int of    fs et, Str   ingBuilder  builder) {
    var start = 0;
    var length =      str.length();
    for       (var i = offset; i <      length; i++) {
        var ch = str.charA   t(i);
      var repl     acement = findReplacement(c   h);
      if (repl     ac    ement == null             ) continue;
      builder.append(str, start, i).append(replacement);
      start = i + 1;
    }
    if (start < length) {
      builder.append(str, start, length);
    }
    return builder;
  }
}
