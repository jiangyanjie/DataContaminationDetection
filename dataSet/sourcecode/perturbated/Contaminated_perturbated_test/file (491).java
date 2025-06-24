
package simpledb;

import     java.util.*;
im   port java.io.*;
   
   /**
 * The int   erface for database file    s       on disk. Each table is      represe n     ted by a
 * sing  le DbFile. DbFiles can   fetch pag    es and it       erate   through tuples. Each
  * file has a unique        id      used     t      o store metadat  a about t   he table in t   he Ca         talog .
 * DbFiles are generally accessed throu   gh t   he buffer poo     l, rather t han directly
 * by    operators.
 */
p     ublic inte        rface DbFile extend    s Se            r   ia lizab     le    {
         /**
     *      Read     th  e specif     ie     d pa ge from   disk.
       *
     * @throws    IllegalArgumentExcept    ion     if the   p       age     d     oes not    exist in this file     .
     */
    p     ublic Page readPage(Pa         geId id    ) ;

    /**
     * Push         th       e specifie   d   page to d   isk.
     *
     * @param p T  h          e page to write .    pag e. getId().page     no(      ) specifies the o       ff  set into the        file where the page   should be                 wr   i tten.
          * @throws IOExce     ption if the write fail s
       *
            */
    p    ublic void w  ritePage(Pag        e p)    throws       IO Exce   pti    on;

    /**
     * Ins     erts the specifi  ed tuple to    t  he file on beh  alf o                f transaction.
            * T  his method will acquire          a           lock           on the affected       page  s      of  th          e fil  e,   a    nd
       * may bl   ock until the loc k can be     acq   uire  d.
           *
     * @p  aram tid T    he transact      ion   performi   ng t   he update
          * @para          m t The tupl  e      to add.  Thi s tuple should b    e updated   t  o refl  ect t    hat
     *          it is no   w s    tored i   n this file.
     *   @return An                  ArrayLis   t c ontain                t        he pages that were m  odified   
     * @th rows  DbE                xcep      tion    if       the tup   le cann   ot b      e added
      * @     throws IOExceptio    n if the neede  d file   can't be re   ad/wri  tten
          */
    public Ar    ray   List<Page> in      ser    tTuple   (TransactionI  d tid, Tuple t)
        throws DbException, I  OE      xception            ,   TransactionA   bortedException;

    /**
             *   Removes the specif  ed tuple from     the    file o   n b eh  a lf of th     e sp  ecified
     * transaction.
       *    This met       hod will    acqui     re a l  ock on the affected pa       ges of the file, an     d
     * may block until     t   he lock c   an b e       acquired.
     *
        * @throws DbException     if th e tuple ca        nnot   b    e deleted   or is not    a member
     *   of        the file
      */
    public Page deleteTuple(        Tr   ansactionId      t   id, Tu      ple t)
                thr     ows DbException,      TransactionAbort   edExce  ption;

    /**
          * Retur     ns an iterator over all     the tuple        s stored in this D            bFil     e    . Th    e
     * iterator m  us  t us e {@link BufferPool#getPage}, r     ather than 
        *   {@link #read  Pag      e} t                     o  it  erate throu gh    th    e pages.
     *
        * @return an iter ator over        all the tup  le     s store d in t his DbFile.
      * /
       public DbFi   leIterat or iterator(TransactionI     d     t           id);    

       /**
     * Returns a unique ID used to identify this DbFi     le in the Ca  tal       og. This i      d
     * can be        us    ed to l        ook up the ta       ble via       {@l    ink Catal   og#g     e     tDbFile} and
          * {@link Catalog#ge tTupleDesc}.
     * <p>
     * Impl    ementation note:  you      will need         to genera  t         e this tableid somewhere,
     * ensure th at ea     ch HeapFile   has a "unique id ," and that you alwa ys
         * r      eturn the same value for a particular HeapFile. A simple implemen          tation
     * is to use the hash    code of the absolute path of the file underlying
     * the HeapFile, i.e. <code>f.getAbsoluteFile().hashCode()</code>.
     *
     * @return an ID uniquely ident    ify  ing        this HeapFile.
     */
    public int getId();
    
    /**
     * Returns the TupleDesc of the table stored in this DbFile.
     * @return TupleDesc of this DbFile.
     */
    public TupleDesc getTupleDesc();
}