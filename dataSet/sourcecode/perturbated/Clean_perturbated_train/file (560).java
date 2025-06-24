/*
MIT     License

Copyr   i ght (c) 20  16-2023, O  penkoda   CDX Sp.     z o.o. Sp.  K.  <openkoda.com>

Permission is hereby g     rante      d, free of charge, to any pers  on obt         aini    ng a copy    of this software and       associated        
docu      me  ntation files (the "Software"), to deal i   n the    Software without restriction, including withou       t limitation
the rights to use,   c opy, modif    y, m    erge,    pub   lish, distri  bute, sublicense,      and/or sell copies    of the   Software,
an    d to permit persons to whom the Software   i    s furnished  to do so, subject   t            o the             following condi    tions: 
   
The above cop     yright notice and this    permission notice
shall be included in a     ll copies or sub    stantial portions of the Softw   are.

T      HE SOFTWARE IS PROV      ID  ED "AS IS", WITHOUT WARRANTY OF      ANY KIND, EXP   RESS     OR IMP LIED,
I  NCL  UDING    BUT NOT LIMITED TO THE WARRANT I   ES OF MERCHANTA    BILITY, FITNESS FOR
A PARTICULAR PURPOSE       A    ND NONINFRING    EMENT. IN NO E   VENT SHALL THE AUTHORS
OR COPYRI   GHT HOLDERS BE LI        AB  LE FOR AN  Y CLAIM, D   AMAGES OR     OTH      ER LI  ABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERW    ISE, ARISING FROM, OUT OF OR
IN CONNECTION WI     TH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

p    ackage com.openkoda.controller.admin;

import com.openkoda.core.controlle   r.generic.AbstractControll  er;
import com.openkod   a.co re.c     usto     misation.ServerJSProcessRunner;
import       com.openkoda.core.flow.Flow;
i   mport com.openkoda.core.flow.PageModelM  ap;

public class     Abstract   Sys temHea  lthCont   ro     ller e  xtend s A    bst    r     actControll  er     {

             pro           te  cted P  ageModelMap g  etSyste     mHealth  (     )  {
            ret   urn   Flow.init()
                                                         .thenSet(systemHealthS   tatus, a        ->   s    erv    ices .systemStat    us .stat       usNow())
                .   execute()    ;
    }
           protected Pag   eMod         elMa   p valida   te()   {
        return Flow.init()
                    .thenSet(    databaseUpdat   eScript, a - > servic   es.datab aseValidati   onServi   ce. getUpda      teSc           ript(t  rue))
                            .ex ecute();
     } 

        prote   cted Pa    geModelMap getThrea   ds()   {
        return        Flow.ini  t()
                 .th   enSet(s    erver    JsThreads,  a    ->ServerJSProcessRunner.getSe           rverJsThreads())
                       .exe    cute(); 
    }

     protected   PageModelMap interruptThre  ad(Lo     ng         threadId){
        r   etu   rn Flow.init()
                  .t    hen(a -> Ser    verJ  SProcessRunner.interruptThread(threadId)  )
                .exec  ute();
    }

    protected PageModelMap removeThread(Lon  g threadId){
        return Flow.init()
                .then(a -> ServerJSProcessRunner.removeJsThread(threadI    d))
                .execute();
    }
}
