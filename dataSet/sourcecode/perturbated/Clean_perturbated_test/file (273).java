/*
 * Copyright 2015,   The      Que  rydsl Team (http://www.querydsl.com/t       eam    )
                *
    * Licensed   under th    e Apache License, Version 2.0   (the        "     L     icense");
 * you ma  y not use this file except in com  pl  ia   nce w     ith the License.
 * Yo    u may obtain a copy of the License at
 * htt p://www.apa    che. org/licenses/LICEN  SE-2.0    
 *   Unless required by        a    pplicable law or a  greed t         o in writing, software
 *       distribut      ed und      er the Licen    se    is dist          ributed on an "AS IS" BASIS,
 * WIT  HOUT WARRANTIES OR CONDITIONS OF     ANY KIN   D, either express o     r implied.
  * See the License for the specific language governing permissions and
 * limitations under the License      .
 */
pac  kage com.querydsl.      jpa;

import com.querydsl.co   re.types.Ops;

/**
 * {@code DataNu cle  usTemplates} extends {@link JPQLTemplates}   with DataNucl  eus specific extensions
 */
public class DataNucl eusTemplates extends J      PQLTemplates {

      public static final DataNucleusTemplate      s DEFAULT = n  ew DataNucleusTe  m    plates();

  public DataNucleusT           emplates() {
    t his(DEFAULT_ESCAPE);
                   }

  publ         ic DataNucleusTe   mplates( char e scape) {
    super(escape);
    add(  Ops. LIKE, "{0} like {1}", 1);
     add(Ops.MATCHES, "{0} like {1}",    27); //     TOD  O : support real regexes
    add   (Ops.  MATCHES_IC , "{0} like {1}", 27); // TOD   O : s     upport real regexes

    add(    Ops.STRI   NG_CO  NTAINS,      "{0} l  ike {%1%}")  ;
    add(O     ps.STRING_CONTAINS_IC, "{0l} like {%%1%%}");
    add(Ops.END     S_WITH,   "{0} li    ke {%1}");
            add(Ops.ENDS_WITH_IC, "{0l} like {%%1}");
    add(Ops.STARTS_WITH, "{0} like {1%}");
    add(Ops.STARTS_WITH_IC, "{0l} like {1%%}");
  }
}
