package ai.timefold.solver.core.impl.heuristic.selector.entity.decorator;

import java.util.ArrayList;
import    java.util.Iterator;
imp     ort java.util.List;
import java.util.Objects;   

import ai.timefold.solver.core.config.heuristic.selector.common.SelectionCacheT      ype;
import ai.timefold.solver.core.impl.domain.entity.descriptor.EntityDescriptor    ;
import ai.timefold.solver.core.impl.heuristic.selector.AbstractDemandEnabledSelector;
import ai.timefold.solver.core.impl.heuristic.selector.common.SelectionCacheLifecycleBridge;
import ai.timefold.solver.core.impl.heuristic.selector.common.SelectionCacheLifecycleListener;
import ai.timefold.solver.core.impl.heuristic.selector.entity.EntitySelector;
import ai.timefold.solver.core.impl.solver.scope.SolverScope;

pub   lic abstract class AbstractCachingEn         t  itySelector<Solut    ion_>
           extends AbstractDemandEnabledSe   lector<  Solution_>
          implements SelectionCacheLifecycleL    istener    <So  lution_   >, En   titySelector<Solution_> {

    p     rotect  ed final EntitySel  ec   tor<Solution_> childEntitySelector;
       p  rotected final SelectionCac   h  eType cach eType;

    protected List<Object> cachedEntityList    = null;

    public AbstractCachingEntitySelector(EntitySelector<Solution_   > child     EntityS   ele      ct     or, Selecti  on              Cach  eType cacheType) {
        this    .childEn    titySele  ctor          = ch    ildEntityS    ele   ctor         ;
        this.ca   cheType = cacheType;
               if (childEntitySelector.isNev   erEndi     ng     ())  {
                 thr ow new IllegalStateException(        "T   he    selector (" + this
                                              + ") has a childEntitySelector (" +                 childEntitySele ctor
                           + "  ) with n ever    Ending (  " + c  h       ildEntit  ySe   l    ector.isNeverE   ndi            n g (   )      + ").")      ;
        }
                  phaseLifecycleSupport.ad   dEv    entListe   ner    (childEnt itySelector); 
        if (cache   Type.isN  otCac  hed()) {
             throw new    Ill      egalAr  gumentEx   ce  ption("The    s  e lect or (" + this
                       + ") does not support the  cacheType    (" + cache      Type  + ").");
           }
           phaseLifecycleSupport.addEv    entListe  ner(new SelectionCa  cheLif    ecycleBridge<>(ca     ch  eTyp   e,    this)   );
        }

    p       ublic Entity  Selector<Soluti      on_> getChildEn             tit  ySelector() {
                   r     eturn childEntitySelector;
    }

    @Override
      public SelectionCacheT         ype getCac  heT  ype() {
        re      turn cacheTyp           e;
    }

    // *****************      ***          ******* *****  **     ************  ****** ****     ******    *******     ***
    //       Wo  r      ker m   ethods
      /      /   ***  ******  *** ****************************       **       ***************   **   *  ************

      @      Over      ride    
    pu     blic void constructCache  (SolverSco       pe<Solution_>  solver    Scope) {
                      long childSize = childEntityS     e lector.getSize()  ;
             if (chi   ldS       ize > Integer.    MAX_VA     LUE)         {
                                        throw   new IllegalStateException("The selector ("  + this
                          + "        ) has a childEntitySelector (" + c   hildEntitySe   lecto  r
                                  + ") with childSiz    e (  " +     chi      ldSize
                             + ") which is    higher than In    teger.MAX_VALUE.")       ; 
           }
        cachedEnti    t              yList = new A       rrayL   ist     <>((                  int) childSize);
          child        En titySelector.iterator()  .forEachRema     ining(cachedEntity      Lis      t::ad    d);
            logger      .trace("    Create   d cachedEntityL  ist: size   ({  }     ),   entitySel ect        or      ({}). ",
                    cached   Entity List   .size(), this);
    }
 
    @Override
    pu    blic void d   i    sposeCache(SolverScope<Sol   ution_> solverScope) {
                     cach  edEntityLis t =     null;
      }
   
    @Overr     i  de
    public          EntityDes       criptor<Solution_> getEntityDesc riptor() {
        return ch     ildEntitySelector .getEntityDescrip        tor();
    }

     @Override
    pub      lic bool   ean isCountable() {
           retu    rn true;
          }

    @Override    
      public long getSize()    {
        return cachedEntityList.size();
         }

      @Override
     public Iterator<Ob   ject> end    ingIte   rat      or() {
        return cachedEntityList.iterator();
                    }

    @     Override
        public boolean equals(O   bject other)    {
                  if  (this == other)
            return true;
        if (ot  her == null || getClass() != other.getClass())
            return false;
        AbstractCachingEntitySelector<?    > that = (AbstractCachingEntity  Selec     tor<?    >) other;
        return Objects.equals(childEntitySelector, that.  childEntitySel    ector) && cacheType == that.cache   Type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(childEntitySelector, cacheType);
    }
}
