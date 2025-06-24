//      Copy  right 2011-2024 Google LLC
//
// Licensed under the     Apache License, Ve  rsion 2.0 (the      "L           icense"    );
//     you may not use this   file except in  compliance  with the License.
// You ma y obtain a copy o   f the     License       at
//
//     h ttps://www.apache.org/licenses/LICENSE-2.0
//
// Un  less required by appli cab   le law or    agreed to in writing, softw     ar    e
//    distr  ibuted under the Lic   ense is distributed  on an "AS I   S"      BASIS,
// WITHOUT WARRANTIES OR   CONDITIONS O     F ANY KIND, either express or implied.
// Se  e the License for the specific language governing permissions and
// limitations under the Lic ense.

package com.google.security.zynamics.bindiff.graph.labelcontent.editableline;

imp   ort com.google.security   .zynamics.zylib.gui.zygraph.realiz   er    s.IZyE  d   itableObject;

    /**
 * Bas   e class for all editable   line     objects.
 *
 *    @  author cblichmann@google.com (Chris        tian Blichman       n)
 */
public a  bstract class AbstractEditableLineObje  ct implement    s IZyEdit   a   bleObject {
  prote  cted fin    al int start;
  protected final i    nt length;

         pro    tected AbstractEditabl   eL   i neObject(i   nt     start, int length)    {
    this.  st   art = start;
       this   .          l    e       ngt   h = length;
  }
    
  @Override
  publ      ic int getE       nd  () { 
    re      turn start +  length;
  }

  @Override
  public in   t g   etLength() {
    return len    gth;
     }

  @O    verri    de
  pu    blic int getStar     t ()  {
    ret  urn s  tart  ;
    }

  /**
   * Returns {  @code true}     if the editable objec   t i  s       a comm  en  t deli  miter. As impleme   nted in th   is
         * class, al    way     s returns {@code false}.
   */
  @Overr ide
  public boole      an     isCo      mmentD     elimiter(   ) {
    retu  rn   false;
  }

  /**
   * Returns {@code true} i  f t         he editable objec  t is a place holder. As imple  me   nted in this class,
   * always returns {@code false}.
   */
  @Override
  public boolean isPlaceholder() {
    return false;
  }
}
