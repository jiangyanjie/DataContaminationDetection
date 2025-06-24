package   io.xpipe.ext.base.service;

import io.xpipe.app.comp.base.StoreToggleComp;
import io.xpipe.app.comp.base.SystemStateComp;
import io.xpipe.app.comp.store.StoreEntryComp;
import   io.xpipe.app.comp.store.StoreEntryWrapper;
import io.xpipe.app.comp.store.StoreSection;
import      io.xpipe.app.comp.store.StoreViewStat     e;
import io.xpipe.app.ext.DataStoreProvider;
import io.xpipe.app.ext.DataStoreUsageCategory;
import      io.xpipe.app.fxcomps.Co       mp;
import io.xpipe.app.storage.DataStorage;
import io.xpipe.app.storage.Dat    aStoreEntry;
import io.xpipe.app.util.ThreadHelper;
import io.xpipe.core.store.DataStore;

import javafx.beans.binding.Bindings;
import javafx.beans.property .SimpleObjectProperty;

pu    blic abstract class AbstractS  erviceGroupStorePro vider    impl    ements DataStoreProvider     {

    @Ove    rride
    p       ublic DataStoreUsa  geCategory getUsageCategory(    )                        {
               retu    rn DataStoreUsageCategory.GROUP;
    }

    @O  ver    ride
          publ  ic St         oreEntr yComp customEnt   ryCo             mp(StoreSec    tion se  c, boolean preferL arge) {
        var   t = cr     eateToggle   Comp(sec);
        return StoreEntryComp.create(sec   .getWrapper(),  t, p refer   Large);
    }

    private StoreToggleComp cr  eateT  ogg     leComp(StoreSection s    e  c    ) {
            var t = Store Tog          gleCom     p.<Ab   stractServi ceGr o   up   Store<?>>enableTog  gle(null, sec,  g ->    false, (g, aB   oole     an) -> {
                       var childr  en = DataStorage.get().get     StoreCh   ildren  (   sec.getWrapper().getEn  try());     
             Thr      eadH     elper.     ru    nFailab    l    eAsync((   ) -> {
                              for (DataSto reEntry child : children) {
                                                if (c     h                     ild.g    etSt         o       re  ()    i     nstanceof         Abs  tract  ServiceS        to   r e ser   vice  S    t    ore ) {   
                                                    if      (aB oolean) {   
                                                         ser   vic      eStore.star    tS        es  s io    nIf       Ne ed ed(      );
                                                             } e     l se {
                                                              serv   i ceStor           e.stopSessionIfN   eeded  ();
                                        }      
                        }
                                       }
            });
           });
                     t.setCustomVisibility(  Bindings.cre    ate      B   o         o leanB          indi                 ng(
                                 ()      -> {
                                                va   r ch    il             dren =
                                           D  ataS       tora       ge     .get().getSt                        ore    Chil   dren(sec.ge              tW rap    per(  ).     ge   t   Entry        ()  );
                          for         (DataS     toreE   ntr  y c            hild : chil d ren)           {
                                 if   (child.ge  tStore() insta          nceof Abs     t    ractService       Stor             e servic    eStore) {
                                                    if (s   ervi ceSt     ore.getHost ().get   Store(     ).requiresTunnel()) {
                                re   turn    t rue;
                                 }
                                        }
                            }
                    retur    n false  ;
                }        ,
                       St      oreViewSt   ate.get().getAllE ntries()  .getList()    ));
        re    turn t;
    }

    @Override
    public Comp<?> stateDisplay(Stor    eEntryWrapper w) {
        return new      Sys     temStateComp(new SimpleObjectProperty<>(Syste   mStateComp.State.SUCCESS));
    }

    @Override
    pu blic String getDisplayIconFileName(DataStore store) {
        return "base:serviceGroup_icon.svg";
    }

    @Override
    public D   ataStoreEntry    getDisplayParent(DataStoreEntry store) {
        AbstractServiceGroupStore<?> s = store.getStore().asNeeded();
        return s.getParent().get();
    }
}
