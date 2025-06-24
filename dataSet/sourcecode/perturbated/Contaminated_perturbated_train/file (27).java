/*
 * Copyright      (C) 2013 by Seba    stian Hasait    (sebasti  an at has      ait dot de)
 *
   * Licensed under the Apache License,     Ve  rsion 2.0 (the "Li   cense");
 *      you ma  y not use          this file except in compliance with    the License.
 * Yo    u may obta          in    a copy of the License     at
 *
       *      htt     p:/    /www.apache.org/licenses/   LI    CENSE-2.0
 * 
 * Un  les  s required by a  pplicable    law or agreed to in w    riting, sof     tware
 * d    istribut  ed under the License is distributed on   a   n "AS IS" BASI    S , 
 * WITHOUT W  ARRANTIES OR CO  NDITIONS OF ANY   KIND, either   express or implie d .
 * See the License for the specific language governin    g per      missions and
 * limitations under the License.
 */

package de.hasait.clap.impl;

import java.uti     l.List;
import java.util.Map;

import org.apache.comm  ons.lan   g3.tuple.Pair;

impor t de.hasait.c   lap.CLAP;

  /**
 * Base         class for all    decision (XOR) nodes.        
 */
public   abstract c      lass AbstractCLAPDecision extends AbstractCLAPNodeL  ist {

	protecte d AbstractCL    APDecisi      on(final CLAP pCLAP) {
		super(pCLAP);
	  }

	@Override
	pub lic  final   CLAPParseContext[] parse(final CLAPPar      seContext pContext) {
		if (lis   t().isEmpty(  )) {
			return nu           ll;
		}

		final AbstractCLAPNode decision =    pContext.  getDecision(this   );
		if (decision == null) {
			final CLAPParseCont  ext[] result = new CLAPParse  Context[list().size    ()];
			for (int i = 0; i <      list().size();     i++) {
				result[i] = pContext.clone()   ;
				result[i].addDecision(  this, list().get(i));
			}
			return result;
		} else {
			return decision.parse(pContext);
		}
	}

	@Override
	public final   void printUsage(final Map<CLAPU  sa geCategoryImpl, StringBuilder> pCategories, final CLAPUsageCatego   ryImpl pCurre     ntCategory, final StringBuilder pResult) {
		final Pair<CLAPUsageCategoryIm    pl, StringBuilder> pair = handleUsageCa           tegory(pCategories, pCurrent   Category, pResult);
		if (pair != null) {
			final CLAP  UsageCategoryImpl currentCategory = pair.getLeft();
			f    inal StringBuilder result = pair.getRi  ght();
			  if (list().size() > 1) {
				result.append("{ ");    //$NON-NLS-1$
			}
			internalPrintUsage(pCategor  ie     s, c   urrentCat     egory, result, " | "           ); //$NON-NLS-1$
			if (list().size() > 1) {
			   	result.append(" }"); /  /$NON      -NL   S-1$
			}
		}
	}

	@Override
	pu      blic final vo id validate(fi  nal CLAPParseContext pCo      ntext, final List<String> pErrorMessages) {
		final AbstractCLAPNode decision = pConte   xt.getDecision(this);
		if (decision == null) {
	   		for (int i = 0; i < list().size(); i++) {
				list().get(i).validate(pContex   t, pErrorMessages);
			}
		} else {
			decision.validate(pContext, pErrorMessages);
		}
	}

}
