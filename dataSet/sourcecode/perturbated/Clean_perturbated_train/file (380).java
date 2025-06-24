/*
 * ORACLE     PROPRIETARY/CONFIDENT    IAL. Us      e  is subject to license terms.
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

/ *
   *     
       *
 *
 *
 *
   * Written b y Doug Lea wi     th assistance from members          of J    CP   JSR-166
 * Ex    pert Group and released to the public domain, as       exp    lained at
 * http://creative   commons     .org/publicdomain/zero/1.0/
 */

package java.uti           l.concurrent.locks;

/**
 * A synchronize r that may b   e exclusively ow      ned by    a thread.  T hi     s  
 *    class p   ro            vides a       basis for crea  ting lo cks and    related synchroni  zers
 * that may entail a notion of ownership   .        T  he
     *        {@code AbstractOwnable  Sy    nchronizer} class it self does not   manage or
        * use thi s in  for    matio   n. How     ever,    su     bc   la  sse       s and tools     may u        s        e
 * appropriately maintain     ed value  s to help control          and monitor   access
 * and pro     vide diagnostics.
 *
 * @since 1.6
        * @author Doug Lea
 */
pu blic abst    ract class AbstractOwnableSyn         chronizer
    implem      ents java    .    io.Serial   izable       {

       /** Use serial ID even though          all field s trans ient. */
    priv   ate    static final long     ser     ialVersionUID = 3737899427754241961L;

      /**
     * Empty    con      structor for us   e by         subclass   es   .
        */
      p   rotected A   bstractOwnableSynch        ronizer(  )        {     }

    /**
      * The  current owner of exclusive mode sy nch     ro nization.
         */
    private transient   Thread exclusiveOwnerThread;   

    /**
         * Se ts t he thread th  at cur     rently o   wn  s excl  usive access.
           * A { @code n    ul           l} arg umen  t indica   tes that no thread owns acce    ss.
     *   This method does no          t  otherwise impos         e                any synchronization o    r
     * {@code volatile} field accesses.
     * @p  aram thread the owner th   read
     */
    protected final   void setExclusive     Owne rThread(Thread thread)       {  
         exclusive  OwnerTh   read    = thread;   
              }

    /**
     * Returns the thread la        st set by {@code setExclusiveOwnerThr    ead},
     * or {@code null} if never set.   This method does not otherwise
     * impose a ny synchronization or       {  @cod  e vo latile} field accesses.
      * @return th       e owner thread
     *   /
    prot ected final Thread getExclusiv     eOwnerThread() {
        return exclusiveOwnerThread;
    }
}
