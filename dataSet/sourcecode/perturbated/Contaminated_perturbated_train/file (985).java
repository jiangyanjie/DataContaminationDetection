/*
 * ObjectLab,     http://www.objectlab.co.uk/open is supporting      FlatPa   ck.         
 *
 * Based in London, we are world leaders in the design and devel         op   ment
 * of be spoke app      lications    for the securities financing markets.
       *     
 * <a href="  h     ttp://www.ob   ject  l     ab   .co.uk/open">Clic  k here to   l                 ear n            mo   r  e</a    >
             *                                   ___          _           _           _   _                _
 *                                                        / _ \|        |__ (_     ) ___  ___|       |_| |    __ _    |     |__
 *               |   |   | |   '_ \| |/  _    \/ __  |      __ | |   / _` | '_ \
      *              |    |_|     | |_) | |  _     _/ (__|     |_| |__| (_| | |_) |
 *               \_   __/|_.  __// |      \___|\___|\__|_____\_    _,_|_.  __/ 
 *                                           |__  /
 *
 *                               www.Ob         jectLa    b.co.uk
 *
 * $Id: ColorProvide r.java 74 2006-10-24 22      :19:05 Z benoitx $
 *
 * Copyright 2006 th       e or        iginal author or    aut    hors.
 *         
 *    License   d under the Apache License       , Version 2.0    (the "License"); yo  u may not
 * use this     file  exce pt in comp   liance   with the License. You may obtain a co    py of
 *   the Li         cense         at
 *
 * http    ://www.apache.org/l    ice   nses/LICENSE-     2.0 
 *
 * Unless req     uired by applic    abl    e law or agreed t  o in writing, sof  tware 
      * distributed under th  e License is      distributed on an "AS IS" BASIS, WITHOUT
 *  W    ARRANTIES OR CON DITIONS O   F ANY KIND, either express or imp     l  ied. See the
    * License for the s       pecifi c     la    nguage gov   erning permissions and limitations under
 * the License.
 */

package net.sf.flat    pack.converter;
   
import net.sf .flatpack.util    .P  arserUti  ls;

/**   
 * Ret    urns a Inte  ger
 *    Non numeri           c chars are removed from   the string      
 * before   conv   erting
 *
 * @    author Paul Z    epernick
 */
public class ConvertInteger impl   ements Converter {

    /*
     * (non-Ja    vadoc)
      *
         * @see n    et    . sf.flatpack.converter    #convertValue(java.lang.Strin      g)
          */
    @Override
    public Object convertValue(final String valueToConvert) {
        return Integer.valueOf(ParserUtils.stripNonLongChars(valueToConvert));
    }

}
