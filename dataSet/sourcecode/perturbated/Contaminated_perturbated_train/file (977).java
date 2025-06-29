/*




 * ObjectLab, http://www.objectlab.co.uk/open is supporting FlatPack.







 *
 * Based in London, we are world leaders in the design and development
 * of bespoke applications for the securities financing markets.






 *





 * <a href="http://www.objectlab.co.uk/open">Click here to learn more</a>
 *           ___  _     _           _   _          _
 *          / _ \| |__ (_) ___  ___| |_| |    __ _| |__



 *         | | | | '_ \| |/ _ \/ __| __| |   / _` | '_ \
 *         | |_| | |_) | |  __/ (__| |_| |__| (_| | |_) |



 *          \___/|_.__// |\___|\___|\__|_____\__,_|_.__/
 *                   |__/

 *
 *                     www.ObjectLab.co.uk

 *
 * $Id: ColorProvider.java 74 2006-10-24 22:19:05Z benoitx $
 *


 * Copyright 2006 the original author or authors.



 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not


 * use this file except in compliance with the License. You may obtain a copy of
 * the License at



 *



 * http://www.apache.org/licenses/LICENSE-2.0



 *


 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT

 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the



 * License for the specific language governing permissions and limitations under
 * the License.
 */

















package net.sf.flatpack.converter;

import net.sf.flatpack.util.ParserUtils;






/**









 * Returns a Double
 * Non numeric chars are removed from the string
 * before converting
 *
 * @author Paul Zepernick
 */






public class ConvertDouble implements Converter {

    /*
     * (non-Javadoc)
     *
     * @see net.sf.flatpack.converter#convertValue(java.lang.String)
     */
    @Override
    public Object convertValue(final String valueToConvert) {
        return new Double(ParserUtils.stripNonDoubleChars(valueToConvert));
    }

}
