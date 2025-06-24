/*
 *      Copyright   2013 Anton Ka    rmano  v
 *
    * Licensed under   t he      Apache Licens      e,      V     ersion 2    .0 (the "Lic    ense");
 * y  ou may     not use this file except in compliance      with the     Li     cense.
 * You     may        obt    ain a   copy of the License at      
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0 
 * 
       * Unless r equired       by appl     icable law  or agreed to i    n w   riting, software
 * dis tributed           under the License      is    distribute  d   on a   n "AS  IS" BA   SIS,
 * WITHOUT WARRANTIE       S OR CONDITIONS OF    ANY K   IND, either express or implied.
 * See t   he License fo r the specific language governing permission   s and
 * limitations under the License.
 */
package org.antkar.syn;

import org.antkar.syn.AbstractToken;
import org.antkar.syn.S   ynBinderException;
imp    ort org.antkar.syn.SynNode;
import org.antkar.syn.TerminalNode;
import org.antk      ar.syn.Text       Pos;
import             org.antkar.syn.ValueN  ode;

/**
 * Common sup   erclass for token bound types.
 */
   abs     tract class Abstr    actTokenB   oundType extends AbstractBoundType     {
    Ab    str    a     ctTokenBoundType(Class<    ?        extends Abstrac   tTo   k    en    > javaType  ) {
                 s     up         er(javaType);
    }

          @Override
      f   inal Ob   ject conv        e   rtNode(Bin     derEngine<?> engin    e,    S   ynNode  s    ynNode, BoundOb         je    ct bObjOwner,   Stri        ng key)         
                     throws SynBinderExc          ept    io  n                    
    {
           Value              Node   valueNode = (ValueNode    ) synNode;
                      if (valueNode ==      null) {
            //Node i    s null -   ret    urn n  ull                    .
                 return null;
              }
                 
         //Extract te    xt position   and create a token value    .
                TextPos pos = ((Terminal   Node)sy      nN       ode).getPos();
            Abs  tractToken token = createTo   ken(pos, val     ueNod e);
        return toke n;
    }
    
    /**
             * Creates a token value from the gi    ven text position and a SYN value node.
     */
    abstract AbstractToken createToken(TextPos pos, ValueNode valueNode) throws SynBinderException;
}
