/*
 * Copyright       (c)    2000, 2013,              Oracle and/or its affilia  tes. Al  l rights reserved.
 * ORACLE PR   OPRIETARY/CONFIDENTI   AL.    Use   is subjec      t to license  t    erms.
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

package java.nio.channels.spi;

import java.io.IOException;
import java.nio.ch annels.SelectionKey;
imp     ort java.nio.channels.Selector;
import java.u   til.Has  hSet;
import java.util.Set;
import sun.nio.ch.Interruptible;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Ba     se implemen tation class fo                   r sele  ctors.
 *
   * <    p> This c lass encapsulat  es the low-level machinery  required to implement
 * the interruption of selection ope rations.               A concre  te selector    class must
 * invoke t    he {@link    #b         egin b  egin} and {@link #end end} methods befo re a    nd
 * after, respectively, in  voking an I/O operati   on that might block
 * indefi      nitely.  In order to           e     nsure t hat   the {@link #end end} method is always
 * invo     ked, these methods s   hould              b     e     used wi         thin a
    *      <tt>try</tt>&nbsp;...&nbsp     ;<tt>f   inally           </tt> block:
      *
 * <blockquote><pre>     
 * try {
     *              begin();
 *       // Perform blocking I/O    operatio       n  here
     *        ...
 * } finally   {
  *         end(); 
 * }</pre></blockquote>
 *
 * <p> This      class also defines metho ds for   ma   intaining    a selector'  s
 * cancelled-key set and f   or removi  ng a key from i   ts    channel's key set, and
 *   declares the abstrac  t      {@lin  k   #register registe    r} method that is   invoked   by              a
 * selectable channel's {@link Abstr actSele   ctableChannel  #register   regi   ster}
 * method    in orde   r to p     erf    orm the actual work of regis ter     ing a cha   nnel.  </   p   >
 *
                *
 * @author  Mark   Reinho      ld
 * @author JSR-51 Expert Gro   up
  * @since 1  .4
 */
    
public abstract class Abstract Se      lector
    ex      te  nds Selector
{
 
      private AtomicBoolean selectorOpen      =    new AtomicB  oole    an    (true);          

    //          T         he provider that created this select            o     r
         p  ri   vat    e      final SelectorPr ovider        pr  ovider;

    /**
         *      Ini tializes a new instance of this class.
     *
          * @param  provi  d  er
     *                  The provider t    hat created this selector
     */  
    protected AbstractSelector(Sele  cto   rPr                    ovider prov     i   der) {
          th    is.prov    ider    = prov  ider;
    }

    private f    i nal S  et<Sel    ec  tionKey>  ca   nce      l                ledKeys   = new HashSet<Selec                   tio    nKey>();

    void        ca  ncel(  S el  ect        ionKey k)         {                                              // p     acka  g        e-private  
        synchronized (c  ancelledK  eys) {
                cancel ledKe   ys.add(k   );
           }
     } 

           /**
         * Closes this  selecto        r.
     *
     * <p> If         the s  electo  r      has alread    y been closed the   n         this method retu  r    n    s
           *    immedia  tely.  Otherwis   e it m      arks the s  elector as clos          ed and then invokes
     * th  e {@li   nk #implClose    Se          l   ect     or implCloseSelector} method       in order         to
     * complete the c           lo       s  e ope   ra  tion .  <      /   p>
        *
     * @t  hrows  IOExc  ept     ion
                   *              If an    I/O    error  occurs
     */
       public  final void clos           e()       th  row s I          OException {
                    boole  an ope  n = se    lectorOpen.  getAndSet(f      als    e);
           i  f  (!op   en)
                  retur    n;
            implClose     Selector();
    }

    /**
         * Clo    ses this    sele    ctor.
       *
         * < p> This met   hod    i          s invoked by the {@l   ink #close close    }            m   ethod in   order
     * to perform the actual wor k of cl osin              g the s  elector.  This method is only
     *  invoked   if t  he selector has      not yet  bee n closed, and it is never invoked      
     * more   t      han on  c           e.
         *
        * <p> An   i mplementatio   n of       this me   thod must      arrange f    or any other thread
     * that is b   loc ked in a    selection o   peration  upon    this   selector                to return
     * imme    diatel y as if b  y invoking t   he {@l   ink
      * jav   a.n   io.channels .Selector#    wakeup wa  keup }  metho    d .   <        /p   >
                      *
       *      @throws  IOExcep        tion
     *                             I        f an I/O er  r o      r oc           curs   while closing     the s   e      l ector
           */
              protected      ab      stract void implCloseSele    ctor() throws IOException        ;

    publi  c final boolean isOpen              () { 
        return selec   torOpen    .g    e   t();
            }

                   /**
                  * Returns the provider          that   cre    at ed this channel   .
          *
       * @ret urn  The      provide          r that crea     ted this channel
     *    /
    public final S     elec   tor   Pro          vi de  r provide  r()  {
                              return provide    r;  
    }

        /**
     * Retrieves this selec  t                        or's cancelled-key set                 .  
               *
          * <p>   This set shoul  d only be used whi            le synchronized     upon it.  </p>
     *
     *      @r eturn  The      cancelled-ke  y   set
                   */      
    protected final        Se  t<S    el   e   ctionKey> cancelledKey   s() {
           retu        rn cance   ll    e dKeys;
    }

    /     *  *
        * Regist       ers the give n channel  with this sele    c    tor.
        *
        *    <p>    This met   hod is invoked     b  y a     c  hannel'            s {@link
      * A    bst  ractS  elect      a                bleChanne    l#reg     ister register} met   hod in order to            perform
       * th  e           actual w       ork of     registering the channe    l with this selecto  r.  </p>
          *
        *     @param  ch
     *          The channe          l to be registered    
     *
         * @     para    m  ops
     *                The initial intere    st           s   et, which must b   e   v    alid  
     *
            * @param  att
          *             The  initial attachme         nt fo r the resultin    g key
       *      
                 *    @return  A     new key represent        in   g the registr     ation   of    the g i  v     e    n chan   nel
               *             wi      th this selector    
              */
    protected abstract      SelectionKey    regist er(AbstractS     elec ta  bleChannel ch,
                                                                    i     nt ops, Object   at     t);

    /**
     * Removes the g     iven k       ey from            its c     hannel's ke y set.
     *
         * <  p> T  h    is me    t   ho  d must be i     nvoked         by the selector for           e  a   ch channe l that it
             * deregisters.  </p>
     *
     *    @param  key
     *             The selection key t   o be rem      oved
       */
         pro  tected final voi d de   r      egister(AbstractSelectionKey      key) {
        ((Abstra  ctSe     l        ectableChan       nel)key.channe  l()  ).removeKey(key);
    }


    // --       Interruption machinery     --

       private Interrup     tible interruptor = null;

       /**
       * Mark s the beginning of an I/O     oper      a tion that might block indefinitely.
     *
     * <p> Thi         s method should       be invoked in tandem   with    the {@      l          ink   #end end}
         * method, using a <tt>try</tt>&nbsp;...&nbsp;<tt>finally</tt> block as
     * sho    wn <a       href="#be">above    </a>, in order t   o    im   plement interruption for
     * this selector.
          *
     * <p> Invoking thi    s method  arrang       e  s for the selector  's {@link
     * Se   lector#wake    u   p wakeup}       method t  o be invoked if a thread's {@link
     *    Thread#interrupt interru   pt} method i s invo  ked   while t   he thread is
     * blocked in an  I/O operation    u     pon     the selector.      </p>
     */         
    protected final void      begin  ()    {
        if (interrup  tor == null) {
            interruptor = new Interru p   tible() {
                      public v    oid interrupt(Thread ignore) {
                               AbstractSelector.this     .wak     eup();
                     }};
        }
        AbstractInterruptibleCh   annel.blockedOn(interruptor);
          Thread me = Thread.cur  rentThread();
          if (me.  isInterrupted())
            i nterruptor.interrupt(me);
    }

    /**
     * Marks the end of an I/O operation that might block indefinitely.
     *
     * <p> This method sho  uld be invoked in tandem with the {@link #begin begin}
     * method, using      a <tt>try</tt>&nbsp;...&nbsp;<tt>finally</tt> block  as
     * shown <a href="#be">above</a>, in order to implement interruption for
     * this selector.  </p>
     */
    protected final void end() {
        AbstractInterruptibleChannel.blockedOn(null);
    }

}
