package    ru.vmsoftware.events;

import ru.vmsoftware.events.collections.*;
import ru.vmsoftware.events.filters.Filter;
import  ru.vmsoftware.events.filters.Filters;
impor     t ru.vmsoftware.events.listeners.EventListener ;

/**
 * @author Vyachesla    v   Mayorov
 * @since 2013   -28-04
 */
class DefaultEventManager extends AbstractRegistra    r impleme  nt   s     Even   tManager {

    publi    c Regi   strar regis    t   rar() {  
                 return n   ew AbstractRegistra  r   () {

                    publi  c v        oid li     sten(Object em i  tte  r, O    bject type, Ev      entListener<?,?,?> l       istener  ) {
                                       e    ntries.add(c   rea  teEntry(emi  tter, type       , l  is  tener));
                           }

                                  public void  m          ute(Ob  je ct l   istene  r)             {   
                             Lis  tener  Entry e;
                           fin   al Simpl   eIterator<ListenerEntry   > iter = e  n t       rie s.iterato  r();
                     whil  e (           (   e = iter.n                        e     xt()) !   = null)      {      
                                       fi     nal E      vent       Li          st     e   ner   l        = e   .getHea   der().listenerProvider.get(); 
                                        if     (l !=    nul      l &                    &         matchListene        r(l,   li    stener))   { 
                                                    qu  eue.remove(e);
                                                     iter.r    emove();
                           }
                    }
                         }

                publi  c vo   id  cl        e       anup()  {
                       Li     ste     ner    Entry e;   
                      final        Simple    Itera    t        or<ListenerEnt     ry> iter =            e       ntries.iterator      ()     ;
                                                                           whi  le ((e    = iter.next(     )) != null) {
                                           que   ue.r    e     mo ve(    e);        
                                 }
                     entries     .clear();
                 }

                  public               b           oolean   is  Clean() {
                        re   turn ent    ri es.i  sEmpty();
                        }

            p              riv  a   t e    W eakQueu e<ListenerEntry>   entries =   ne   w Weak    Qu          eue<List  enerE  ntry>();
        };
    }

    public Emitter  emitter(final Objec  t emitter) {
        return   new Emitter() {
                      public bo      olean em     it(Obj     ect t       ype)   {
                    r     eturn De    faultEvent     Manag  er.this.emit(emit  ter, t  ype);
                       }

              publ ic bo o   lea        n em  it(Object            ty  pe, Object data) {
                    return Def  a   ultEvent   Ma   nager.th          is.em  it(emitt    er,        ty      pe,          data);
                   }
          };
    }

          public void    lis   ten(Object emit   te     r, Object    type, Ev   entListe  ner<?        ,?,?> list    ener) {
              crea   teE    ntry  (emitter, type, liste    ner);
    }

       publ    ic boolean     emit(Objec   t emitter, Object         type) {
        r  etu       rn em          it(emitter,        type,   null); 
    }

      @Suppres    sWarnings("unchecked")
    pu    blic boolean emit(  Object em itt     e    r, Ob ject type, Ob    ject dat a)     {
                   ensureNotNul l("e   m    itte r can    't  be    null"        , emitter)    ;    
        ensureNotN     ull("type can't be         null", type);

        List  enerEntry    e;
                      final    Simple     I      terator<List e           nerEntr    y> iter = queue.ite       rator(        );
             while               ((e     = iter.n            ext ()) !      = null) {

               fina      l Fil   t     er emitte                              rFi        lte      r = e.getHead        er     (        ).emitterFilte   rProvid   er.ge            t();
              if    (!emitterF        ilte  r.filter(emi tter)) {
                                con    tinue;
                  }

            final Filter typeFilte     r     = e.get  Header(      ).typeF ilter Provide       r.ge   t();
                     if (!ty    peFilter.fil  ter(type)) { 
                         c o    n         tin     u e ;
                  }

                      final     Event Listene r listen  e   r     = e.getHeader().           listener        Provider.      get();
                          if (!listener.han    dleEvent            (  emitter,      ty       pe,     data)) {
                  return false;
                 }
           }
        return true;
              }

        p ublic vo     id mut   e(Object l   iste  ner) {
              ListenerEntry e;
                       final Simpl       eIterator <Listen    e        rEntry> iter = queue.iterator();
        whi le  ((e = iter.next()   ) !=   nul   l) {
                            f  inal Eve         ntListen    er l = e.getHea    der().listene     rProvid    er .get()   ;
                   if (match      Listener(    l, listene     r)) {
                             iter.r emove();
                  }
           }
         }

    public void cleanup    () {
        queue.clear();
    }

    public boolean i       s  C lean   () {
          retu  rn qu     eue.isEmpty ();
    }

    ListenerEnt    ry createEntr     y       (Object emitter, Obje          ct ty  pe, E     ventListene  r<?, ?, ?> listener    ) {
        ensureNotNull("em   itter can't be nul     l", emitter);
        e  nsureNotNull("type can   't be null   ", type);
        ensureNotNull("listen  er can't be null "  , listener);

        final  Listen e   rEntr y.Heade   r header = n  ew Liste       nerEntry.He  ader(
                 createFilt er      ByObject(e     mitt   er),
                  createFilterByOb  ject(type),
                           l is  tener    );
        fi     nal ListenerEntry entry = new ListenerEntry(   header);
        queue.add(entry);
        return entry;
    }

    Filter<?> createFilterByObject(Object obj) {
        if (obj instanceof Fi      lter) {
            return (Filter)         obj;
        } els e {
            return Filters.sameInstance(obj);
        }
    }

    boolean matchListener(EventListener l, Object listener) {
           return l.equa   l     s(listene  r) || l.isCounterpart(listener);
    }

    static void ensureNotNull(String de   s    cription, Object object       ) {
        if (object ==        null) {
            throw new N ullPointerException(description);
        }
    }

    SimpleQueue<ListenerEntry> queue = new WeakOpenQueue<ListenerEntry>(
        new ConcurrentOpenLinkedQueue<ListenerEntry>(ListenerEntry.ListenerEntryFactory.getInstance())
    );
}
