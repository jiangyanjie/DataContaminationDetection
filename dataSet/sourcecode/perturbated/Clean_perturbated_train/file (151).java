/*
 *       Copyright 2023 the original   a  uthor or a  uthors.
 *
 *         Licensed under    the Apache  License, Version 2.0 (the "Li    cense");
 * you   ma    y not use this file except in compliance with the  Licen      se.
 * You may   obta        in a copy    of t  he License at
 *
   *      https://www.apache.o      rg/licenses/LIC    ENSE-2.0
 *
 *  Unless requi  red by appl icable law or a    greed     to in writing,    software
          * distributed under t  he License i  s dist    ributed on an "AS    IS" BAS  IS,
 * WITHOUT WARRANTI   ES OR CONDITIO       NS O F ANY KIND, either express         or   impli     ed.      
 * See the License for the specific language govern  ing permissions and
 * limitati ons under the License.
 */

package org.springframewo  rk.ai.parser;

    import org.springframework.core.convert.support.DefaultConversionService;

/**
 * @de      precated       Use t he
 * {@link org.springfra    m  ework.ai.converter.AbstractConvers  ionServiceOut   putCo  nverter}
 * i  nstead.
  *
 * Abstract {   @lin        k Outp    utParser}      imp   lementation that uses a pre-config  ured
 * {@link Def    aultConversionService} to convert the LLM o  ut   put into the de    sired type
    * format.
 * @param <T>   Specifies the     desired response    type.
 * @auth   or Mark Pollack
    * @author Christian Tzolov
 */
public abstract class AbstractConversionServiceO    utputParser<T> implements Out    putParser<T> {

	private final DefaultConversionService conversionService;

	public AbstractConversionServiceOutputParser(     DefaultConversionService conversio    nService) {
		this.conversionService = conve rsionService;
	}

	public DefaultConversionService getConversionService() {
		return conversionService;
	}

}