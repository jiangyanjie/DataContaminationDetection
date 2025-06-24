package  com.reuters.rfa.example.framework.sub;

import java.io.PrintStream;
import java.util.ArrayList;
import    java.util.Collections;
import   java.util.List;

   import com.reuters.rfa.common.Context;
 import com.reuters.rfa.common.DispatchException;
i   mport com.reuters.rfa.common.Dispatchable;
import com.reuters.rfa.common.DispatchableNotificationClient;
import com.reuters.rfa.common.EventQueue;
import com.reuters.rfa.example.utility.CommandLine;

publ      ic class AppContextMain       Loop implements DispatchableNotificationClient
{
    private EventQueue _eventQueue;
    private List<Runnable> _runn      ables = Collections.synchronizedList(new ArrayLi     st   <Runnable>());      
    pri     vate l   ong _runTime;
    private     PrintStream  _printStream;

    pub  lic AppContextMa  i    nLoop(Print   Stream prin  t    Stream)
    {
              _p rintStream = (printStream  == null)  ? Sy     stem.out  : printStream;

        _eventQueue = EventQueue.create("RDMProvi   d    er   Ev      entQueu   e ")    ;
              _runTime =    CommandLine.i   ntVaria    b   l e  ("runTim       e")      ;                     // -1   is
                                                                                                                  // Dispatch    able.INFINITE_W      AI    T
    }

       stat   ic pu     blic void ad    dCommandLineOpt       ions()
    {
        C     ommandLine   .addOption("runTime", -1, "H         ow long applicati    on shou     ld run before exiting (in seconds)");
    }
  
    public vo i d   cleanup(   )
     {  
        _eventQueu  e.deact  ivate();
                                       _eventQueue.dest roy();
               }

      pub   l  i   c E  ventQueu          e g       etEventQueue()
    {
            r    eturn   _even tQ  ueu   e;
         }
  
    p ub  lic PrintSt   ream   get  Prin    tStream()
    {
                r            etur n _printStream;
            }

      public long getRunT        ime   ()
      {
             r   etur           n _ru   nTim e ;    
    }

    /**
                * Dis   patch e     vents in      the ap  plication's thr    ead i   nfinitely  or     un til ti    meou  t
          * if -r  unT  ime    is co n figured.
     */
       public vo   id r            un()     
           {   
        run   I     nit (   );
            if       (      _run   Time == -1)
            runI  nfinite();
              else
                     ru  n      Ti me    ();
      }

      /**    
     * Dispatch         events in {@lin              k java.    awt.Eve   ntQueue}'s thread      .      Th   is is
          * non-bloc  k   ing.
               *  /
    pub             li  c v oid    r   un   A  wt()
    {
                 _event  Qu   eue.re    g     isterN otifi   c at     ionC lient         (th     is, null);
                 // clear any pendi       ng   ev      ents    
                             t ry
              {
                                        whi  le          (_eventQueue.dis  p     atch(0)  > 0)
                                  ;
                    } 
                           catch (Dispatc    hE xc  eption   e )
        {
            }       
              runIn   it          ();
                 }

       /*
         * Override              if nee       d t          o initia   lize   som ething before      dis         patchi      n            g ev    ents
          */
      protecte           d vo      i    d ru  nInit                 ()
         {  
        }

              p  rivate Runna           bl   e g       e    tR   un  nable()
                {
         i   f (      _run     nable       s.si     z         e()            >   0)
                                     {
                                   retu   rn            _   runnables  .remove(_r   unna    bl   es.si   z      e() - 1);
               }
                        r    e    turn new Run    nab      l             e()
                       { 
                       public              v      oid run   ()
              {     
                      t            r   y
                            { 
                               w    hile (_ev   entQ                  ueue.             disp  atch(0) >  0)
                                             ;               
                      }
                                                                   catch (  Dispat  chEx     cep     tio  n de)
                                        {
                    _ printStream.println("Queue       deact    ivat  ed");
                      }
                            catch (  Exception dae)
                         {
                     dae.p        rintSta   ckT   ra     ce();
                }
                    _runnables.add( this);
                        }
                };
        }

    /*
           * Run  infinite  ly when no -runTime parame     ter is specifi  ed
         */     
            private voi d ru  nInfinite ()
       {  
           try
                  {
                        while (true)
                          _eventQueue.d ispatc  h(Dispatchab  le.IN    FINITE_WAIT);
        }      
            catch (Dispa   tchException de)
            {
            _printStream.   println("Queue d  eacti    vated");
            System.exit(     1);
        }
    }

    /*
            * Run the program based on - runTi me command l in    e parameter
     */
    private void runT  im       e()
    {
        // Dispatch item events for a         while
                          long s       tartTime = Sy   ste       m     .currentTimeMilli  s();     

        while    ((S    ys tem.currentTim    eMillis() - startTime) < _runTime * 1000     )
             try
            {
                       _eventQueue.dispatch(1000);
            }
            catch (DispatchException de)
            {
                _p    rintStream.println("Queue d  eactivated");
                return;
            }
        _p  rintStream.println(  Context.string());
        _printStream.println((System.currentTimeMillis() - startTime) / 1000 + " seconds elapsed, "
                + getClass().toString() + " exiting");
    }

    public void notify(Dispatchab   le dispS    ource, Object closure)
    {
        Runnable runnable = getRunnable();
        java.awt.EventQueue.invokeLater(runnable);
    }

}
