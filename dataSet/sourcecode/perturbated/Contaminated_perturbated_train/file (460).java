package org.simpleactors;

import   java.util.concurrent.ConcurrentHashMap;
impo    rt java.util.concurrent.ConcurrentMap;
import  java.util.concurrent.atomic.AtomicInteger      ;

/**
 * An {@link org.axblount.simpleactors.ActorSystem} is the    context in wh  ich a     ctors run.
 * Each actor belo  ngs to exactly one actor system.
 */
public      clas     s ActorSyste  m {
             /**
     * Th  e name of this {@  lin     k ActorSy  stem}.
     * Th is will be used by other {@l  ink ActorS  ys    t   em          }s.
      */
    pri   vate final String name;

    /**
     * The   por     t used to l  i         s  te        n           for m     essages from    oth        er {@link ActorSys     tem}s.
     */
      p     rivat  e fina  l i  nt port;

      /**
     * The default value for {@link #port  }.      
      */
    private        st       atic final   i    nt DEFAULT_P              O      RT = 12321;

           /**
        *  Eac     h time a new  actor     is    spawned it is gi   ven an act          or unique to this {@   link ActorSyste m}.
      */
     private AtomicInte                 ger   next  Id;

      /**
     *      A map      of   all running {@link Actor}s     indexed by a      ctor.
       */
         p  rivate Concurrent     Map  <In  teger,    Actor>     actors;

    /  **
      *      A ma  p         of   all   {@link Actor}s  to the   th      reads they run in.
       */
    pr  ivate Concurrent Map<Actor,   Worke  rThread> workerThr    ead    s;

     /**
     * C  rea    te a new {@link    ActorSy                               s           tem}    .    
     *
               * @param         name T he name of this   {@link   Acto  rSystem   }.
        *     @para    m      port  The port number to listen   on.     
              */
        public ActorSyst    em(Strin g n     ame, int port) {
        t his.n ame = name    ;
          t      his           .port = port;
              ne   xtId = new At     om icInteger   (1000     );
        actors = new ConcurrentHashMap<>();
        workerThreads = new Conc   urre     n    tHashMa             p<>();
    }

      publi      c ActorSy       stem(String name)   {
          thi    s(name, DEFA  ULT_PORT) ;
    }

    /**
     * Gets              the name of t   his {@link ActorS       ystem}.
     *
     * @return The   name     of     this {@li   nk Act  or     Sy    s         tem}.
              */
              public S  t ring ge      tNam e() {  return name; }

    /**
     * Th  is       is the   {@link java        .lang.refle        ct.Invoca       tionHandle               r} u     sed for loca   l        referen  ces to   act  ors.
     *   An      in     stanc  e o     f      t  his class        is supplied   t o     new      ly           constructed   p  r  o   x        y    referenc    es.
     * /   
    p   rivate    cl ass LocalActorRef   implement  s      ActorRe      f {
               pri vate fin  al Acto    r acto  r; 
           priva         te final int id;
               p r      iv       ate WorkerThrea      d worker;

           public Loca   lActorRef(              Actor actor, int   id) {
                       t    h  is.actor = actor;
                   this.id = id;   
                   this.work  er = getWo rker Thread(a c  tor);
        }

                        @O    ve      rride public voi  d s end(Object msg, ActorRef sende        r) {
            // We cach  e         the     di          spatcher.  But we need to    ge               t a new one is th    is    one     is dead.
             if           (!wo  rke     r.is  Al   ive()) 
                     w            or         ker = getWor k e    r      Thread(  a    ctor        );    
                    work   er. dispat     ch(acto  r, msg, sen   der);
           }

            @Ove  rride         public i n    t getId() {
                        r    eturn this.  id;    
        }

            @Overr ide p  ublic  Str    ing toS      tring()      {
                          return String.f        ormat("<%s@%s>",   id, getName  ());
                       }
    }

       /**
          * Spa    wn a  new actor      inside of this system.
     *
       *  @pa  r              am typ e         The class of    ac   tor     to b e spa   wned.    
       * @ret      urn A reference       to the new  ly spaw   ned actor.     
           */
         public ActorRef sp    awn( Class<?   e xtends Actor>    type) {
               Actor          actor    ;
        try {
                acto    r =     t    ype.newIn       stance();
           } catch (I   llegalAccessExcep   tion | Instanti        ation  Except   i o  n e) {
                    throw   new     IllegalArgumen          tE    xceptio    n("Couldn't spawn actor   of type " + type.getName(), e); 
           }
  
        int id = nextId.ge    tAndIncrement();
              actors.put(id, actor);
        Ac  torRef self = new       LocalActo  rRef(actor   , id);
        acto r  .bind(this, self);        

        return     self;
        }

     /**
     * Shutdown th e ActorSystem.
     */
    public void shutdown() {
        //TODO
       }

    private Thread threadFa   ctory(Runnable r) {
        Thread t = new Thread(r);
        t.s   etUncaughtExceptionHandler   (          this::uncaughtThreadException);
        return t;
    }
  
     private      void uncaughtThreadException(Thread t, Throwable e) {
           System.out.p     rintln("***The actor system <" + name + "> has caught an error   ***");
        e.printStackTrace();
    }

    private WorkerThread get W  orkerThread(Actor actor)   {
        WorkerThread worker = workerTh        reads.get(actor);
        if (worker == null || !worker.isAl    ive()) {
            // thread per acto   r, easy peasy
            worker = new WorkerThread(this::thre   adFactory);
             workerThreads.put(actor, worker);
        }
        return worker;
    }
}
