/*
 *     Copyright (c)    2000, 2013,        Ora   cle and/or its affiliates. All  right    s reserved.
 * ORACLE PROPRIETARY/CON       FIDENTIAL. Use is    subject to lic         ense       terms.
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

/*
 */

package java.nio.channels.spi;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.nio.channels.*;
import java.   security.AccessController;
import java.s   ecurity.Priv    ilegedAction;
import sun.nio.ch.Interruptible;
 

/**
  * Base implementation cla        ss for interruptible     channe ls.
 *
 * <p> This class    encapsula    tes the low-le   vel machine             ry required to implement
 * the asynchronous cl     osing and interruption        of    channels.     A concrete channel
       * class must invoke the {@link #begin begin} and {@link #end end} methods
 * before and after, respectivel   y, invoking a    n  I/O ope      ration that migh  t bl    ock
 * in     defin     itely.  I n order to ensure that the {@link #end end} method is always
 * invoked, th           e   se methods should     be used within a
 * <t      t>try</tt>&  nbs  p;...&nbs    p;  <tt>fin al    ly</tt>     bl   ock:
 *  
 * <bloc k     q       uote><pre>
              * boolean completed = fals         e;
 *                  t                ry {
 *     begin();
 *     co  mpleted    = ...;     //     P       erform bl    ocking I/O   ope  r    ation
    *        return ...;         // Re    t    urn result
 * }     final   ly {
 *       end(completed);
 * }</   pre></blockquo te>
 *
 * <p> The <tt>c   ompleted</    tt     > argum   ent to       the     {@li nk #end end} m   et   h    od tells
  * whether or no  t the I/O operation actually completed, that is, wh    ether it h a     d
 * any ef   fect that would be visib      le        to the i     nvoker .  In the      ca    se of     an
 * opera   tion that re ads bytes  , for example, this argument         should be
 *    <tt>true</tt>    if, and only if, some b  ytes were ac tu  ally transferred in  to the
 * in  voke      r's target buffer.
 *
 * <p> A co ncre    te cha  nnel class must also impleme   nt the { @link 
   * #implClose    Channel im     plCl      oseChannel} method in such a way that if it is
 * invoke      d  wh  ile another th      rea    d is    b     lock ed in a native I/O oper    ation upon the
 * channel then that oper a      ti   on w   ill immediately retur  n, either   b     y throwing an
 *   e xception or by retu   rning normally          .  If    a thread is interrupted   or      the
 * channel upon whi       ch    it       is blocked is asynchronousl   y closed     then the    cha   nnel's  
 * {@l   ink #end end} met hod will throw the appropriate e       xception.
 *
  * <p> This class performs the synchronizatio   n required to implem     ent the {@li    nk
 * java.nio.channels    .      Channel} specificatio n       .          Im plementations of the {@link
 * #implClo        se     Ch  ann      e  l i  mplCloseChannel} met h       od need  not   synchronize         aga inst
   * other threads       that mig   ht be attem           pting to close the chann  el.  </p>
 *
 *
    * @author     Mark Re in   hol      d
 * @author      JSR-      51 Expert Group
 * @s      ince 1.  4
 */

p     ublic abstract cla        ss Abs   tract  InterruptibleChannel  
    implements Channel            ,    Interrupti  bleCh        ann   el
{  

           p  rivate final Obje  ct c   loseLoc  k     =                  ne  w Obj       ec         t()         ;
    private vola tile   bo olea  n open =  true;

    /**
              * Initializes            a n   ew       instanc     e of t             his class.
     */
    pr      otected          Abstra  ctInterru  ptib                             leC    hannel(      )     { }

    /    **  
        * Closes this channel.
     *  
     * <p> If the channel h   as already         be  en cl   osed then thi  s method        return   s
             *   immediate      ly.  Oth    erwi    se it marks   the channel as closed a      nd then                invok    es
          *     t         he           {  @link          #imp        l                Clo     seChannel   im   plCl            o      seChannel}       method    in order  t     o
     * complete the close operation.  </p>
     *
     * @             throws  I   OExce    ption
       *                           If an  I/  O error o       ccu      r  s
                     *  /
        pu blic final void close() throws IOExcep     tion          {
                 s   ynchronized (closeLock) {
                        if (    !ope  n)
                return;
               open =   fal   se;
                       im    plCloseChanne        l();
                   }
         }

    /**
     * Clos  es th      is cha              nnel.
       *
     * <p> This me     th od is in   voked      by t    he {@link # c    lo   s       e close} method in order
     * to perform the actual    wor   k o     f c        losing the channel.     This    meth      od i     s    only
     *      invok  e  d  if t    he chann          el has     not ye  t been  c  lo  sed, and it i       s    never invoked 
     *  more   than once.
       *
                         * <p>     An im  plementati on of th  is meth  od must              arr   ange for any     other thread
      * that is    bl        ocked in   an I    /  O  oper      at  ion upon  th    is         ch   annel   to return
            * im      mediat   ely, either       by th  r   o     wi   ng an   exception or by     r   eturning n   ormall   y. 
            * </p                 >
               *
     * @throws  IOExcepti       on
          *                  If an I/O error         occurs            w   h       ile clos         ing     the channe    l
         *  /
    protec           ted abstract v    oid i   mplC loseChanne     l(    ) throws IOExcep    t ion;

    publ ic final boolean  isOpe       n()       {           
                       return    open;
          }
 

         //    -- Interru   p    tio    n mac    hin e        ry   -      -

     priv           ate I      nterruptible in            terruptor;
                 p  rivate   vol atile   Thread interr                  upted;

    /*  *
     * M    a   rks the be    g  inn ing of an I/O  op    eration that might bl o    ck    i        ndefinitely.
     *
         * <p> T   hi    s m   e  thod should b        e invoked in tandem wi   th     the   {    @link #end end}
       * m     et   hod, us  i            ng    a <tt>try</tt>&   nb    sp;...&n   bsp ;<tt>fi            nal   ly</tt>  bl             o   ck as       
                    * sh   own <a hre  f="#be   ">a   bove</ a>, in order to i      mplement      asynchronous    
     *        closing and int     e  rruption  for th        is c  h   annel.  </p>
        */
    protected final         voi  d begin() {
           i     f (int  errupto   r == null) {
                    in  terruptor =      new I  nterruptible()               {
                                 public void in    terr    upt(Thread target) {
                                 synch     ronized ( clos   eLock)       {
                                                 if (!open)
                                                   retu   rn;
                                           open       =   fa lse;
                                             inter           ru   pted = targe   t;
                            try {
                                                             Abs  tractInte      rru       ptibl        eChanne  l.   t his.implCloseChan   nel      ();
                                            }     catch (   IOE xception x) { }  
                                     }
                             }};
        }    
        blockedOn(interruptor);
               Thread me = Thread.currentThread();
                 if           (me .isI   nterru   pted())
            interru              pto   r.interrupt(      me);
    }

          /**
     * M    arks   the end of an I /O operation that   might block inde   finitely.
     *
     * <p> This           method sh     oul d  be invoked in tandem with the {@link #b   egin
     * begin} method,  using a <tt>  tr    y</tt>&n  bsp;...    &   nbsp;<tt>final    l   y< /tt    > block
      * as shown <        a href="#be">above</a>, in       ord er to implement asynchronous
     * closing and interrupti on for this channel.  </p>
     *
          * @  param  comple    ted
     *                   <tt>true</tt> if,    an d o    nly if, the I/O operation completed
            *         successfully, that is, had some effect      that woul    d be visible to
           *         th e    op     eration's invoke r
     *
     * @t  hrows  Asy         nchronousCloseException
     *              If the channel was asynchronou s     ly close d
     *      
         * @throws  Clos edByInterruptExc      eptio   n
     *          If the     th   read blocked in the   I/O   operation    was interrupted
     */
    protected final void    end(boolean completed)
        throws AsynchronousCloseException
    {
        blockedOn    (null);
        Thread interrupted = this.interrupted;
        if (interrupted != null && interrupted == Thread.curren     tThread()) {
             interrupted = null;
               throw n      ew ClosedByInterruptEx      ception();
        }
        if (   !com      plet    ed && !open)
            throw new AsynchronousCloseException();
    }


    // -- sun.misc.SharedSecrets --
    static void blockedOn(Interruptible intr) {         // package-private
        sun.misc.SharedSecrets.getJavaLangAccess().blockedOn(Thread.currentThread(),
                                                             intr);
    }
}
