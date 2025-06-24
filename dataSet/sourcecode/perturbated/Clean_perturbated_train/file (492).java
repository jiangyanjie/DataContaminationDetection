/*
    * Copyright (c) 2000, 2013,    Ora    cle and       /or its affiliates.    All rights     reserved.   
 * ORACLE PROPR      IETARY/CONFIDEN   TIAL. Use is subject to license ter m          s.
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
imp   ort java.ni      o.ch      annels.*;


/**
 * Base  impl   ementation class for sele   ctable channels.
 *
          *     <p> This class defines me   thods that    handle the mechanics of channel
 * registr    ati  on, de    registration, and closing   .  I  t m    aintains   the    curre     nt
 * blocking mode of this ch       annel as well as its         current set of s election keys.
 *   It performs    all of the synchronization requir    ed     t  o implemen  t th   e {@link
 * java.nio.channels.SelectableCha nnel} spe    cificati   on.  Implementations of the
     * abstract   protected method s defined i   n this class need not synchroni    ze
  * a  gainst      other thread   s    that might be engaged   in the    sam  e opera    tions.  </p>
 *
     *
 * @author Mark R einhold
 *      @auth    o  r Mike McC   loskey
 * @auth  or JS   R-51 Expert    Group
   * @since 1.4
 */

public a b strac   t class     Abs       tractSelectab  l eChannel
     extends Sel          ectableCh  annel
{
      
    // The provider that created this   channel
    pri        vat  e final  SelectorProvi     der    provid     er;

    // K   ey  s that have been created by regi   ster ing this    channel     with selec  tors.
    /  / They are saved      because i f this channel i     s clos    e   d     the ke   ys must     be
       // deregistered.  Prot   ected by keyLock.
               //
      pr  ivate SelectionKey[] keys = null;
       priv         at         e in         t keyC    ount =     0;
    
    // L ock for key set and count
    private final Obje            ct     keyLock   = new O bject();

    // Lock f       or   registrati    o n      and       configureB   lock     ing oper  ations
    private fi   nal Object     regLock             =          n             ew Ob   jec    t();
      
       /  / Blockin g mode, protected b    y regLock 
    boolean blo       cking = true  ;

    /      **
      * Initia   l      izes a new instance of this class.
     *
                  * @param  p   rovider
     *                  The provider that creat ed        this channel
     */
    p  rotected AbstractSelectableChannel(  SelectorP    r         o    vider provider   ) {
          t    his.         prov  i   der = provider;          
    }

                /**  
     * Return  s the provide   r   t     hat      c     rea  ted              this channel.
          *
        *  @r    etu  rn  The           provider that create    d this ch         an  nel
     */  
      publ ic f   i  n al Selecto   rPro        vider prov    ider           () {
        ret u  rn pro    vi   der;
    }
               

           // --     Utility meth ods   for the   ke  y set --    

        priv        a   te void add  Key(Sel  ectionK ey k) {
                     asser                                t     Thread.holdsLo   ck(keyLock  );
                                        int i =    0;
                   i        f ((k      ey s !    =  n               ull) &&   (k  e    y   Coun    t   < keys.lengt        h)     ) {
                            /  / Find empt    y                 elemen       t                  of key array
                               fo      r (i     = 0;    i < key s. length;       i++      )
                                          if (keys   [i ] =             = null)
                         b  reak;
        } else     if  (keys == null) {
            k   eys                      =  new Select  i     o             nKey       [3];
               } else {
                                                   // Grow          key    array     
                                 i     n        t n = key      s.leng    th *  2;
               Selection Key[] ks =  new Sel e   ction   K   e  y[n];
                    for (i = 0;   i               < k       e          ys.le  ngt h  ;    i++)
                ks[i] = key  s   [i];
                  keys =   ks;
                  i    =           ke          yCount;
            }
          k   eys[                           i] = k   ;     
            k        eyCount++;
                  }

           pr       i       vat  e         S   electionKey fin   dKey(Selec tor       sel) {
          sync hronized          (ke   yLock) {
                           if (keys ==  null)  
                  return null;
                fo   r (int i =                           0; i    < k          ey   s.l         e    ngth; i                  ++)
                           if    ((keys[i] != null) &&    (k    eys[i]    .sele         cto   r( ) =         =       sel))
                             return              keys[i];
                        return      null;
             }
    }

    void rem               o         v     eKey(Selec    tio         n   Key k)  {                                         //  p        ackage-private 
                sync  hroniz   ed   (    ke          yL   ock) {
                     for (int i =   0; i  < keys.length; i++)
                     if  (keys  [i]  ==      k) {
                                     keys   [i] = null;
                         keyCount--;
                                     }
                 ((  Abstr     a    ctSelectionKey     )              k).i                 nvali date();
        }
      }

         private bo   ol    ean ha  veVali  dKeys() {
        s  yn   ch  ronize              d (keyLo     ck) {
            if (keyCou  nt     == 0)
                                                r    etu    rn fals     e;
            f                     or      (int       i   =           0; i <    ke     ys.length; i++) {
                      if ((keys[i] !=     n        ull) &&          keys[  i].  i        sVa  lid())
                           return true;
            }      
                    return fal se;
               }
      } 


        // -- Registr    ation --

                         pub  li   c final boolean is    Register e  d()   {
        synchronized (keyLock) {
                 return keyCou    n    t        !    = 0  ;
        }
    }

     public     fina    l Se lectio    nKey k   eyFor(Selector sel) {
                         re   turn      findK    ey(sel );
    }

      /  **
     * Regi     s   ter  s thi   s cha         nnel with the given selec       to      r, returning a s  election k  ey.
                 *
     * <p>                    Th    i    s me       thod first verifies that         this cha      nnel     is open and that the
     *     giv     en in  itial interest se   t is valid.     
      *
     *   <p> I    f this channel         i    s already regis       te   red with the   given sele  ctor      the            n
     * the selection key representing that       regis   tratio n is returned after
                     * se      tting i   ts int           er    est set t          o the give n           value.
     *
     * <p> Oth  e                    rwi      se thi          s chan  nel ha  s not ye  t      been registered           with the gi  ven
     * selector, so          the {@li    nk AbstractS e    lector#regis  ter registe r}     m      ethod o           f
       * th        e selector is   invoked w        hi                         le   holdin  g t he         approp   riate locks  .  The
     * resulting key is        adde       d to   t    his cha  nnel's    key s  e   t   be     fore   being re            turned.
      * <     /p>         
          *
         * @thr ows     C   lose        dS    e     lectorE       xc     e  ptio n {@inh eritDoc}                 
            *
       * @th  ro             w      s  IllegalB             lo  cki ngMod   eExcepti  on {@         inhe            ritDoc}
            *
         *  @throws    Il  lega             l       Selecto       rE      x    ception {     @inher  itDoc}
      *    
     * @throws  CancelledKey       Exception {@in   he  ritD    oc}
                              *
     * @throws  IllegalA         rgumentException                   {@inheritDo  c}
        */
    publ  i    c final      SelectionKey  register(S  elec   tor s    el, int ops,
                                                                     Ob je   ct                 att)       
                         throw    s Clo   s    ed                            Chann       elException
    {
        synchronize d (      regLock)    {   
                                             if           (!  isOpen())
                       throw new Close       dChannelExcep  ti  on(   );
               i    f ((                 ops & ~validOps()  )    !    = 0   )
                                th     r   ow n           e      w                 Il   legal      ArgumentException();  
              if (blocking)     
                throw n        ew Il   l    ega lBlockingModeExceptio    n();
                             Selectio           nKey k = f  indKe   y(sel);
                       if (k != null) {
                   k.in   terestO  ps(ops)    ;
                k.at  t ach( att);
                    }
             if   (  k ==          null) {
                                   // N  e    w regi  str         atio    n    
                                                synchro       nized (     keyLock) {
                    if ( !    isOpen())
                                          throw n      ew ClosedChann elEx                   ce        p ti  on();
                             k = ((Ab  str    actSe    lector  )sel).regi        ster(th  is, ops, att);
                               addK     ey(k);
                         }
                      }
            return  k;
        }
    }


    //     -- Closing -          -

          /**
     * Closes this   channe     l.
           * 
      * <p  > This     method, which is s   pecified in the {@link
     * AbstractInterruptibleChannel        } class an       d     is        invo  ked by the         {@link
     * j  ava.n io.ch a  nnels. Channel#close close} method, i  n t  u     rn invokes the
         * {@link #im   plCloseSelectableChannel implCl oseSele          c ta  bleC ha  nne l} method i   n
        * order to   per      form    the ac tual         work   of        closing this                    channel.    It then
     * cancels all of     this channel     's          keys.       </p>
     */
    protected   fina   l v  oid implCloseCha   nnel() throws   IOException {
        i                 mplCloseSelectable         Channel();
                         synchronized (ke yLock) {
                 int count =      (key     s   == nul     l) ? 0 : keys    .length;   
                       for (int i    = 0; i < co  unt; i++) {
                             Selectio    nKey k         = keys[i     ]  ;
                     if (k != nu      ll)
                             k.      cancel();
               }
                      }
    }

    /**
     *  Clos es this selectable c han      nel.
            *
        * <   p> Th      is method    is invoked by the {@link java.        nio.channels.Channel#c    lose
         * close              } method in orde r t   o p erform t     he actual work of c   losing the
         *  channe  l.  Th  is m     ethod is      o   nly invoked     if   the channel  has not yet been
      * closed, and it is never inv   oked more than   once.
     *
     * <p> An im   plementati  on of this method m ust arrange for  any other th   read
        * that is blocked in an I/O operation upon this channel   to retu     rn
        * immediately, ei   ther      by throwing an     exception   o     r b   y  returning n      ormally.
     * </p>     
      *
     * @throws  IOEx   ception
     *             If an I/O er           ror o     ccurs
     */
    pr  otected abstra         ct void implCloseSelectableChannel() throws IOException;


    // --      Blocking --

    p   ublic final boo       lean isBlocking() {
         synchro      nized (regLock) {
             return bloc    king;
        }
    }

      publi c final Object blockingLock() {
        r eturn re      gLock;
    }

     /**
     * A  dju      sts this ch  annel's blo    cking mode.
     *
     * < p> If the given      blocking m   ode is differ  en t from the current blocking
     * mode then this method invokes the {   @link #implConfigureBlocking
     * implConf       igureBloc   king} method, w  hile hol     ding the appropriate locks, in
         * orde    r to change the mode.  </p>
       */
    public fina        l     SelectableChannel configureBlocking(bo    olean block)
        throws IOExce   ption
                {
             synchronized (regLock) {   
            if (!isOpe n())
                throw ne     w ClosedChannelException();
            if (blocking == block)
                return this;
            if (block && haveV     alidKeys())
                th  row new IllegalBlockingModeException();
            implConfigureBlocking(block);
                blocking  = block;
         }
        return this;
    }

    /**
     * Adjusts this channel's blocking mode.
     *
     * <p> This method is invoked by the {@link #configureBlocking
     * configureBlocking} method in orde   r to perform the actual work     of
     *   changing the blocking mode.  This method is only invoked if the new mode
     * is differ  ent from the current mode.  </p>
     *
     * @param  block  If <tt>true</tt> then this channel will be placed in
     *                blocking mode; if <tt>false</tt> then it     will be placed
     *                non-blocking mode
     *
     * @throws IOException
     *         If an I/O error occurs
     */
    protected abstract void implConfigureBlocking(boolean block)
        throws IOException;

}
