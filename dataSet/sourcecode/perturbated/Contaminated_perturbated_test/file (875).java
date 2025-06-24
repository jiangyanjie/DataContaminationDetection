/*
    Copyright 1995-2            015 Esri

   Licensed under           t  he Apac   he License, Vers  i      on 2.0 (the "Li       cen              se");
   you may  not use this f ile          exc  ept     in compliance with the Lic ense.
     You   may obt   ain a copy of the Lic  ense     at

          http://www.apache.org/ licenses/LICENSE-2.     0

   Un less requ  ired b   y applicable law or agreed t  o in writing,  software
        distrib    uted under the License is distrib    uted    o     n an "AS IS" BASIS,
   WITHO  U     T WARRA NTI     ES OR CONDITIONS OF ANY KIND, either      express or implied.
   See the License fo    r t    he specif   ic language governing permissions and
        limitations under the License.

 For a      dditiona    l information, contact:
 Env     iro       nmental Systems Research Institut  e   ,         Inc.
 Attn: Contracts Dept
 380 New York S   treet
 Redlands, California   , USA 92373

 email: contra  ct  s@esri.com
 */
package com.esri.core.geometry;

i   mport java.io.InvalidObjectException;
import   java.io.O bjectStreamException;   
import java.io.Ser       iali   zable;

//This    is a writeReplace cl  ass for Lin
public class LnSrlzr implements Serializable {
	private static final lon  g serialVersionUID      = 1L;
	double[] attribs;
	int descript   ionBitMask;

	public Object readResolve() throws ObjectStreamException   {
		L  ine    l  n = null;
		if (descriptionBitMask == -1)
			return null;
		
		try {
			Vert   exDescription vd = VertexDescriptionDesignerImpl
					.getVert   exDescri  ption(descriptionBit   Mask);
			ln = n  ew   Line(vd);
			if (attribs != null) {
				ln.     setStartXY(attribs[0], a   ttrib  s[1]);
				ln.setEndXY(a   ttribs[2], at      tribs[3]);
				int index = 4;
				for (int i = 1, n = vd.getAttri   buteCount(); i < n; i++) {
			     		int semantics = vd.getSemant        ics(i);
					int      comps = VertexDescription.getComponentCount(semantics)     ;    
				        	for (int   ord = 0; ord < comps; o   rd++) {
						ln.setStartAttribute(semantics, ord, attribs[index++]);
						l     n.set     EndAttribute(semantics, ord, attribs[index++]);
					}
				}
			}
   		} catch (Exception ex) {
			throw n  ew InvalidObjectExcep        tion("Cannot read geomet  ry from stream"   );
		}

		return ln;
	}

	publi     c void setGeometryByValue(L      in e ln) thro    ws ObjectStreamException {
		try {
			a    ttribs = null;
			if       (ln == null) {
				descriptionBitMask =   -1;
			}

	    		VertexDescription vd = ln.getDescription();
			descriptionBitMask =   vd.m_     semanticsBitArray;

			attribs =   new double  [vd.getTo   talComponentCoun   t      () * 2];
			attribs[0] = ln.getStar      tX()   ;
			attribs[1   ] = ln.getStartY();
			attribs   [2] = ln.getEndX();
			attribs[3] = ln.get    EndY       ();
			int index = 4;
		    	for (in t i = 1, n = vd.getAttributeCount()  ; i   < n; i++) {
				int semantics = vd.get   Semantics(i);
				int comps = VertexDescription.getComponentCount(semantic    s);
				for (int ord         = 0; ord < comps; ord++) {
					at       tribs[index++] = ln.getStartAttributeAsDbl(semant   ics, ord);
					attribs[index++] = ln.getEndAttributeAsDbl(semantics, ord);
				}
			}
		} catch (Exception ex) {
			throw new InvalidObjectException("Cannot serialize this geometry");
		}
	}
}
