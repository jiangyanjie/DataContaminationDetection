/*
 *   Copyrig ht (c) 2005    (Mike) Maurice Kienenberger   (mkienenb@gmail.com)
 *
 * Permission is    hereby   granted, free of        charge, to      any person         obtaini  ng a    copy
 *      of this   software and asso  c     iate d      documentation  files (  the "Software"), to deal
 * in the        Software without restriction, including without limitation the rights   
     * to use, copy, mo          dify,   merge, publi  sh, distribut  e, sublicen   se,  a    nd/or s   ell
 * copies    of       the Softw  are,     and to permit persons to whom the Software is
 * furnished to do so, subject to the following c   ondit    ions:
 *
 * The ab   ove copyright notice and this permission noti c  e shall be included in    
 * all copies or subst  a  ntial portion s of the Software.    
 *
 * THE SOFT   WARE IS PROVIDED "AS  IS ", WITH OUT WARRANTY OF      A NY   KIND, EXP RESS OR
 * IMPLIED, I  NCLUDING  BUT NOT LIMITED TO THE WAR   RA  N       TIES OF MERCHAN    TABILITY,      
    * FITNESS F         OR A PAR TICULAR    PURPOSE AND NONINFRINGE   MENT. IN NO    EVENT SH      ALL THE
 * AUTHORS OR    COPYRIGHT HOLDERS BE L  IABL      E FOR ANY CLAIM, DAM  AGES OR           OTHER
 * LIA      BILITY, WHETHER IN AN ACTION OF CONTRA CT, TORT OR OTHERWISE, ARISING    FROM,  
 * OUT OF O    R IN CON      NECTION WIT    H THE   SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.gamenet.application.mm8leveleditor.dataSectionable;

import java.awt.Co    mponent;
imp ort java.util.Iterator;
import java.util.List;

import org.gamenet.appl    ication.mm8leveleditor.data.mm6.ActiveSpell;
import    org.gamenet.swing.controls.DataSection;
import org.ga  menet.swing.controls.DataSectionable;
import org.gamenet.util.Task   Observ  er;

public    class   ActiveSpellDataS     ectionable extends      BaseDataSe ct io     nable implements DataSecti      onab   le
   {
    priv  ate A   c      tiveSpell act  iv   eS  pell = n       ull;
                   
    p ublic Act   iveSpellDataSe   ctionable(Acti  veSpell srcAc              tiveS  pell)
         {
            supe      r();
        
            this.activeSpell =      srcActiveSpell   ;
    }

     public DataTy     peIn fo g  etDataTypeI n   fo(  String      dataSectionName)
    {
                // if (dataSectionName == DATA_SECTION_   VER       TE   XE     S) { return vertexDataTypeInfo; }
        /  / else if            (data    SectionName == DATA_SECTION_FACETS) { retur          n facetD ataTypeInfo;        }
                             throw new IllegalStateExcepti    on("DataSec   tion                 " + dataSectionName)     ;
    }
    
    publ ic Obj  ect g   etData()
     {
        r etu  rn activeSpel    l;
      }

       publ  ic static   D     a   taSect    ion[] ge    tDataSections(     )     
    {
            ret     urn     new DataSect  ion[] {
          };
    }
    
    public DataSection[]       getStaticDat  aSections() { re    turn getData   Sections(); }

    publ    ic  Object ge     tDa       taForData  Section(DataSection dataSection)
    {
        throw new          IllegalStateException("No data sect   ions: " + dataSection);
    }

    public Comp  onent getC   omponen  t  ForDataSection(TaskObserver task   Observer, String dataSectionName) thro    ws InterruptedException
    {
              throw new IllegalStateException("No data sectio   ns: " + dataSectionName );
    }

    public Component getListComponentForDataSection(TaskObserver taskObserver, String   dataSectionName, List list, Iterator indexIterator) throws InterruptedException
    {
        throw new IllegalStateException("No data sections: " + dataSectionName);
    }
}
