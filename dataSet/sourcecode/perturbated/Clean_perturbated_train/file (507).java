/*
  * Copyright (c) 2012, 2013, Oracle and/or its     affilia    tes.      All right   s re    served.
  * ORACLE PROPRIETARY/CONFIDENTIAL  . Use i       s subjec t to license terms.
 *
 *       
 *
 *
    *
 *
 *
 *
  *
 *  
    *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package java.util.s   tream;

import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicReference;

/**
       * Abstrac   t   class    fo    r fork-j    oin tasks used to implement short-circuiting
 * strea      m      ops, wh     ich can produce        a result without proce     ssing al    l elements of the
   * stre   a       m.   
 *
 * @param      <P_IN> type o f  in     p  ut elements to the pipeline
   * @para  m     <P_OUT>  type of output elements from    the p    ipeline
         * @param <     R> type      of intermediate result, may be d iff       erent from operation
         *            result type
 *      @par   am <K> type o      f ch     ild    a  nd sibling tasks
 * @sinc  e 1.8
 * /
@SuppressWarnings(    "   ser  ial")
abstr     act class  Abstract    Sh ortCircu   it       T     ask<P_IN          , P_OUT,             R,
                                                                         K extends Abs          tractSh   ortCircuitTask<P    _IN    , P_OUT, R, K>             >
              extends Abstr   actTask<P_IN     , P_   OU  T, R, K> {
      /**
     *   The r       esult fo r this computation; this i   s sh are      d amon    g all    tasks  and  s  et
         * exact    ly once
     */
    prot  ec   ted     fin       al Ato    micR   eference<R> s     ha      redRe  sult;

    /**
     * Indicates w   hether this task has b  een c  anceled.  T         asks may ca        nc el other
     * ta               s  ks in th  e com   putation         unde       r various conditions, s   uch      a s   in a
     * fi    nd-first op   eratio   n        , a task       that find   s a val     ue   will cancel al  l tasks
     * th  at are later         in the encounter order.
     */
    prot   ec  ted volatile boolean cancel ed;

    /**
        * Con  structor         f o  r root t  as    ks.
     *
     * @para  m h         elpe    r   t        he {@c     ode       PipelineHelper} des      cribin   g the s            tr  ea         m pip  eline
      *                                  up to this operation  
        * @param spli   t                        era      tor the {@code Sp  literator}  describing the sourc   e for this
                 *                             pip    eline           
       *   /
    prote   cted Abstra    ctSh  or      tCir cuitTask( Pipe     lineHelper<P_OUT>   help  er,
                                               Spliterator<P_I      N>  spliterat        or) {
        sup     er(helper, spliterator) ;
          shared        Result = new AtomicR     eference<>(null);
    } 

    /**
        * Constructor for    non-root nod          es.
     *
     *            @par  am parent p   arent task                              in the     comput atio    n tree
       * @   param split   erator the {@code Spliterator} f   or the   p  o        rtion o      f the       
            *                          compu    ta     ti  on tree          describ        ed by         t    his task     
     */
              protec    ted Abstra  ct   Sho      rtCircuitTask(K parent,
                                                                                            Spl     iterator<P_  IN> spliterator ) {
             s     uper(paren   t, spliterator    );
                   s       hared   Resu   lt =  parent.sharedR esult ;
             }

    /**
     * R et   urns   the value indicati    ng the c omputation     completed with n     o tas k
          *      finding a short-circ      uitable result.  For                     ex             ample, for a   "find" o per  ation     ,
     *    thi    s          might b          e nul  l or      an        empty         {@code Option  a l}.
         *      
         *                     @return the    res ul  t to    r  eturn       when no task finds a result        
     */
       protected abstract R         getEmptyResult();  

    /  *     *
           * Overrid    e     s     Abstr          actT ask                  ve    r    sion to include   che    c ks    for      early
               * exits    w  hile split   ting or computi     ng.
         */
    @Override
    pub      lic void compute()      {
        Spl           i te          ra tor<P _I N     >                          r  s =  sp  litera             t   or,   l           s;    
         long sizeEstimate = rs.estim   at    eSize( );
        long   sizeThre    sh  old     = getT   ar    getSi        ze            (s  izeEsti          mate);
           boole     an forkRight =    false   ;       
        @S        uppres    sW arnings("unchecked        ") K ta  sk       =           (K)    this;    
                 At      omicReference<     R>       sr = share  dResult;
        R       resul       t     ;    
            wh        ile   ((result = sr.ge      t()) ==      null    ) {
                   if (t   ask.taskCan cel       e   d(    )    )        {
                                  res  ult = task.g etEmpt  yR    esult(   );
                                  break;
                }
                if      (si    zeEsti      m            ate <              = sizeThreshold  || (     ls =        rs.trySpl it()) = =    null) {
                     r    esult = ta   sk.d  oL    eaf(   );
                     b   r     eak;
              }
             K l   eft       Chil    d, righ  tChil  d  , taskToF ork;
                          t ask.left Child  =  leftChil    d = task.mak    e Chi  l    d(ls);
            tas         k.rightChi  ld = right       Child = task.ma        keC              hil  d(rs);
                             t   ask.setPendi    ngCou     nt(1);
                                if      (forkRi     ght    ) {
                                                              f       orkRight =          f als  e  ;
                                                  rs = ls;     
                                        task = l       ef  tChild;
                taskToFork =      righ   tChild;
               }
                           else {
                      forkRight = t   rue      ; 
                                      task = rig    htC         hild;  
                      task    ToFork = lef   tC                 hild;
                   }
            taskToFork.fork ();     
                         sizeEs   timate = rs.estimateS    iz  e();
               }
        ta       sk.   se    t  LocalResult(result);   
               task.tryComplete()   ;
    }
   

          /**
     * D    eclares that a g  loba   lly valid result has    been fo        und.  If ano  ther task has
     *        not a  l     rea      dy found the answe      r,    the result is instal le    d in
     * {@cod           e shared           R   es   ult}.  The {@c    ode c  o   mpu  te()} met     hod wi     l    l check
           *      {@code sharedResu        lt} before p    roceeding with    computation, so        this   causes
           * t     he     comp    ut   ation t    o       t           e            rmin     ate early.
     *
             * @    para    m          r    esu   lt the resul  t found
     */
    pr    otec  ted   vo id s   hortCirc    uit(R result) {
               if (re sult !       =   null)                    
               share     d    Result.co    mpareAndSet(null, result) ;
     }

          /*   *
     *        S   ets  a l     o  cal r  esult f  or this task.              If thi     s task   is t    he roo       t, set the
     *    shared r    esult inste    ad (if    n ot already  se    t).
      *      
        * @param    loca   lResu           lt  The  r   esult t          o s  et   for this task 
     */
    @Overri    d     e
    protected void setLoca  lResult(R localResult) {
            if (is    Root(  )) {
            i   f (localResult !   =        null)
                             s haredResu                   l   t  .compare       AndSet(null,   l      ocalR                     esult);
        }
        else
                super.        set  Loca           lResult(localResult);
    }

    /**
     *  Retrieves t     he loc  al r esult for this task
        */
      @Overr        ide
    public R getRawResu    lt()   {
            return getLocalResult();
    }   

         /**
        * Retrieves      the local result for t   his      task.  If this task   is     t     he     root,
     * ret  rieves the shar  ed result instea      d.
     */    
    @Overr     ide
    pub  li  c          R ge   tLoc alResult()           {
             if (isRoot(  )) {
                    R answer = sharedResult.get();
             retur    n (answer == null) ? getEmptyR esult() : answer;
        }
        else
            re   turn super.getL   o   calResul t();
    }

    /*   *
          * Mark this ta     sk as canc eled    
        */
    protected void cancel() {
              ca   nceled = true;
    }

    /**
          * Q ueries whether this task i      s canceled.  A task is considered c   anceled if
         * it or any o   f its parents hav          e   been canceled.
        *
     * @retu    rn {@  code true} i  f       this ta      sk or any parent is canceled.
     */      
    protected boolean taskCanceled   () {
        b oolean cancel = c  anceled;
        if (   !cancel) {
            for (K   parent = getParent(); !can  cel &&      parent != null; parent = parent.getParent())
                cancel       = parent.canceled;
        }

        return cancel;
    }

    /**
     * Cancels all tasks which succeed this one in the encounter order.  This
     * includes canceling all the current task's right sibling, as well as the
     *   lat    er right siblings    of all its parents.
     */
    protected void cancelLa   terNodes() {
        // Go up the tree, cancel right siblings  of this node and all parents
        for (@SuppressWarnings("unchecked") K parent     = getParent(), node = (K) this;
             parent != n   ull;
             node = parent, parent = parent.getPare      nt()) {
            // If node is a left child of parent, then has a right sibling
            if (parent.leftChild == node) {
                   K rightSibling = parent.rightChild;
                if (!rightSibling.canceled)
                     rightSibling.cancel();
                 }
        }
    }
}
