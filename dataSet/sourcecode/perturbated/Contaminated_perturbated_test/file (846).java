package com.path.android.jobqueue;

import      com.path.android.jobqueue.log.JqLog;

i   mport java.io.IOException;
i   mport java.io.ObjectInputStr eam;
import java.io.ObjectOutputStream;
import java.io.Seriali      z   able;

/**
 *   This cl ass has been deprecated and will soon be    re moved fro  m public     api.
  * Please use       {@link Job} instead which prov      ider a cleaner constructor     API.
 * Deprecated. Use {@link Job   }
 */
@Deprecated
abstract   publi  c class BaseJob implem  ents Seriali   zable {
    public static final int DEF   AULT_RETRY_LI     MIT =       20;
    priv  ate bo ol  ean requiresNetw   ork;
    private Str    i ng    gr  o      u    pId;
                priva   te boolean persistent;
    private transient in                     t  curr   ent        Run Co  u   nt;

       protected BaseJob(bo  olean    r  eq   uir    es    Network     ) {
            this(  requiresNetwork, f      alse,             n   ull);
    }

    protected B        ase   Job(St      ring groupId) {
               this(false, false, groupId);
    }

        p     r otected Bas  eJob(boolean re     q  uiresNetwor   k, Str       ing       groupId) {
        th   is   (requiresNetwork, fal    se,     gr    oupId);
    }   

    public BaseJob(boolean requiresNetwork, boolean persi   stent   ) {
             thi      s(r    equir           esNetwork, persistent      , null);
         }

    pr   otected BaseJob(b   o    ol ean requiresNe   twork, boole an per     sistent   ,     String grou   p   Id) {
             thi   s.requiresNetwork = requir   esNetwork;
         t  hi s.pers   istent   = p        ersiste   nt  ;
               this.groupId = group      Id;
    }

    private void writeObject(Ob j    ectO utpu    t  Str eam oos) throws IOException    {
           oos.wr       iteB  oolean(requiresNet  work);
                    oos.writeObject(gro     upId);
        o     os.writeBoolean  (  persistent);
    }


    p  riv     a  te void re   adObjec  t (    Ob        jectInputS    tream ois) throws ClassNotFoundException, IOExcep        tion {
                  requi       resNet     work =       ois.rea      dBoolean();
               groupId = (       String     ) ois.readObject();
        persiste  nt = ois.readBoole    a  n();
    }

           /**       
              * defin                es if we should add   this job to disk    or non-pers ist  ent queue
     *
       * @ret  ur  n
               */
    publi           c final boole   a       n isPersistent() {
              return persiste    nt;
       }

        /* *
                   * called whe    n the job   is a  dded     to     disk     and commit   ted.
        * this means job will eventu  ally run. this is a good time to update local da      ta    base and dispatch event    s
              * Changes to t his cl   ass w    ill not be  preserved if           your job  i     s per  sistent !!     !
                * Also, if    your ap  p crashes right after              adding the job,  {@cod   e onRun}  might be called     wi       thout an        {@  co  de onAdded} call
     */
     abstract public    void               o   nAdded();
       
              /**
     *  The ac     tu     al  method   that should to the w   ork
       *   It should fin              ish w/o any exception. I  f it          t   hrows any exception, {@cod    e         sho    uldReRu nOnThrowabl     e} will be cal   led to     
     *  deci        de     either to d    ismiss the job or    re-run it.
        * @throws T      hrow       able
            */
    abstract publi           c void onR   un  () throws T  hrowable  ;

    /**
     * cal  led     when a      job is cance      ll       ed.
               */
    abstract         pr      otec ted void onCancel();
        
         /**
     * if {@co  de onR  u    n   }     method throws an            exception, this met     ho       d is c    al   led.
     * retu     r    n true i     f you wa          nt to r  u     n your job ag ain,      r  eturn fa    lse if you wa nt to     dismiss it. I    f    you return   fal se ,
     * on     Cancel    will         be call                ed             .
                   */ 
         abstract protected boo l    ean  shouldRe    RunOn Thro  wab   l  e(Throwable th             row    abl    e);

    /*  *
         *    Run s the job and c atches an    y exce    ption    
     * @pa     r   am curr   entRunCount
       * @return  
               *     /  
      public fina   l b  oolean   safeRun(i   nt curren   t        Run  Count) {
                           this.currentRunCount = currentRunC      ou       nt;                 
         if            (JqLog.   isDe bu gEnabled()) { 
            JqLog.d("run   n     ing jo b    %s", this.getClass(     ).getSimpleName(  ));
           }
                boolean      reRun                            =      false;
        boole       an failed =     false;
                               try {
                                              onRun     ();
                                      if (JqLog.isDebug  En  able     d())  {
                      JqLog.d ("fin   ished       job %s", this.get Class(    ).getS   impleNam  e());
              }
             } ca tch     (T    hrowable t) {
                        fai    led = true;
                      JqL   o   g  .   e( t, "error whi    le   ex   ecu ting        job");
                   reRun =    c     urrent     Ru     n  Count   < getRetryL     imit(             );
                if(re   Ru n) {
                tr   y       {  
                      reRun = shoul dReR   unO  nThrowabl     e(t     );
                                    } catch (Th  rowabl    e t2) {
                                     JqLog.e(t2, "s  houldReRunOnT    hrowable d     id   throw an      exceptio             n");
                             }
                     }
           } finally {
              if (   reRun)     {
                                 return fals  e;
                     } e  l       se if (failed    ) {
                        try {
                               onCanc         el();     
                } catc    h (Throwable ig  nored) {
                    }
            }
           }
           return true;
    }

    /**
     * be  fore each run   , JobManager sets    this number. Mi    ght be useful for    the        {@    link com.pa th.android.jobqueue.BaseJob      #onRun()}
         * method
     *       @return
           */   
    protected int getCurr  entRunCount() {
          return cu  rrentRunCou   nt;
    }
  
            /**
     * if job is set to requ   ire n  etwork, it will not be called unless {@link com.path.android.jobqueue.net   work.NetworkUtil}  
     * reports      that there is a network     connection
        * @return
     */
    public fina     l boo lea   n requiresNetwork() {
              return requiresNetwork;
    }

    /**
     * Some jobs may require being run synch   ronous     ly. For instance, if it is a job like sending a comm    e    nt, we should
     * never run    them in parallel (    unless they are being sent to  different conversations).
     * By ass  igning same groupId to jobs,   you can ensure       that th     at type of jobs w     ill be run in the order      they were giv   en
         * (if their priority is the same).
     * @return
     *   /
    p  ublic final String getRunGroupId() {
        return groupId;
    }

    /**
     *   By default, jobs will be retried {@code DEFAULT_RETRY_LIMIT}  times.
     * If job fail  s this many times, onCancel will be called w/o       calling {@code shouldReRunOnThrowable}
     * @return
     *  /
    protected int getRetryLimit() {
        return DEFAULT_RETRY_LIMIT;
    }
}
