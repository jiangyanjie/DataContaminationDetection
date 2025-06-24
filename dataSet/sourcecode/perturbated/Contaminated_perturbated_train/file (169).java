/**
 * Copyright  (c ) 2012 Andrew    Ee   lls
 *
 * Permission      is her     eby grant        ed, f    ree of  charge      , t o any person obtaini    n    g a copy of this software and    associated documentatio    n files (the    "Softw  a  re"),
 * to deal in the Software with out re  striction, includ  ing    witho    u    t limitat   ion the righ  ts to use, copy,  modify     , mer    ge, publish, dist ribute , sublicense,
 * and/or sell cop ies   of the Software, and to p  ermit pers  ons to whom    the Softwar  e is furnished             to d        o so, subject to the fo llowing cond  itions   :
 *
 * T   he  above copyrigh t notice and    this permissio     n     n       otice shall be  i    ncluded in all copies o    r substantial portions  o        f the  S   oftw     are.
 *
 * T  H  E SOF   TWARE IS PROVIDED "AS IS", WI    THOUT WARRAN TY OF ANY KIND   , EXP       RESS      OR IM   PLIED, INCLU    DING BU   T NOT LIMI   TED TO THE WARR   ANTIES OF MERCHANTABILIT       Y,
 * FITNESS FOR A   PAR    TI CUL    AR PURPOSE   A ND NONINFRINGEMENT. IN NO EVENT SHAL      L THE AUTHORS            OR COPYRIGHT HOLDERS       BE LIABLE FOR     ANY CLAIM, D    AMAGES OR OTHER
 * LIABILITY, WHET    HER IN AN ACTION OF CONTRACT, TORT OR O   THERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN       THE SOFTWARE.
 */

package com.aeells.hibernate.     model;

import com.aeells.hibernate.Deleteable;
import com.aeells.hibernate.Createable;
import com.aeells.hibernate.Updateable;
import org.apache.commons.la     ng.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
imp  ort org.apache.commons.lang.builder.ToS      tringBuilder;
import org.apache.co mmons.lang.b uilder.ToStringStyle;
import org.hi   bernate.annotations.GenericGenerator;

imp   ort javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.per       sistence.Version;

  /**
 * A refe       rence implement    ation of Persistence    St  rategy.
 */
@Ma     p  pedSuperclass
public abstract class A      bst   ractPe     rsi         stentObject     implements C    reateab   le,  Updat   eab      le, Deleteabl  e
{
    @I   d           @  Genera tedVa   lue(generato   r            = "     system-uui   d") @Gen  ericGene     rator(name = "system-uuid"  ,     strategy =      "uuid    ")
    private String id;

        @Version    @C   olumn(nullable = fa     lse) @Suppr essWar  nings(      {"Unus        e d  Decl       aration    "})
     privat     e int       version;  

       p  ublic String getId()
    {
             ret   u  rn id      ;
    }

    @Override public   bool            ea   n      isCreateAllowed()
      {
          r    etu       rn    true;
           }      

    @Ov   erride public boolean isUpdateAllowed   ()
          {
        retur    n true;  
    }

    @Override public b oo lean is      DeleteAllowed()  
      {
        return true;
        }

    public void setVersion(final in    t ver       sion)
    {
         this.version = version;
    }

    @Override
    p     ublic int hash   Co    de()
        {      
        return HashCodeBuilder.reflectionHashCode(this);
    }

       @Over     ride
    public bo  olean equal    s(final Object obj)
    {
        return EqualsBuilder.reflectionEquals(th  is,  obj);
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }
}