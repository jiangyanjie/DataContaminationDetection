/*****************************************************************************
 *  Copyright by      T    he HD F G   r           ou              p.                                                                                                            *
  * Copy  right           by      t he             Board o f T   ruste    es of the Univer    s i   t         y                       o              f Illin   ois.           *           
 *             A                      ll rights        res      er                      ved                     .                                                                                                            *
          *                                                                                                       *
 *   This file is part of the HDF Java         Produ  c        ts       dist    ri     bution.                    *
 * T  he full     cop           yrig ht notice, including terms gov ern  ing use, modification   ,   *
 * an   d   redistributio   n,    i s contained in the files COP         YING and   Copy           rig   ht.html. *
 *       COPYING can be f     ound at the root o    f the source code distributi        on tree.    *
 * Or, see h       ttp://hdfg roup   .o    rg/products/hdf-java/  do c/Co          p  yright     .html.            *
 *     If you do not have a                ccess to either file, yo  u     may re  quest   a   copy fro     m           *
 * help@hdf   group.   or   g.                                                                        *
  ****************       ***********************   *******    ********       **  ********************/

p  ackage ncsa.hdf.objec   t;

i  mport java.util.Li    st;

/**
 * An  interface that provides  general I/O operations for read/write objec   t data.
      * F or exa    mple, r ea     ding dat   a content or dat    a attribute from file into m      emory o r
 * w     ritin  g data con   tent or      data attrib    ute fr    om mem   ory int  o file.   
 *    <p>
      *   
     * @see ncsa.hd  f   .object  .H Object
 * 
 * @versio           n  1.1 9/   4      /2   007
 *      @a        uthor Peter X. Cao
 */
  public inter    face Da            t  aForm  at {     
     /**
          *     Retur   ns the full p  ath of the  file th     at   contains this    data   obje   ct.
        *          <p>
       *     The fil  e name is n  ecessary    bec     ause data   objects are   uni  que  ly ident    ifie   d
              *     b  y        object      reference a    nd fi      le name   when mut   ilple     files     are op    ened a   t the
     * same  t    ime.
     */     
              public   abstract S        t   ring getFile();

    /             **
         * Ret   rie   ves the metadata     such as attribute        s   fr om file.
       * <p>
      * Metad ata such a         s att  r     ib       u tes are stored in a List.
        * 
     * @retu  r  n the l   ist    of m   etadata objects.
     */
    p ublic      a   bs      trac       t List getMetadata() throws Exc  e         ption;
      
          / **   
       * Writes a sp     ecific metad   ata (   such as attrib            ute     ) i         nto file.
     * 
      * <p>
     *       If   an  HDF(4&5     ) attribute exi   sts in file, the    method updates its v      al   ue. 
     * If the attrib     ute do  es   n     ot exist  s in file, it creates the attribute in 
      * file and attaches it to  the obje      ct.
     * It w    ill fail to write    a       new a    ttribute to the object wher    e an attribut  e 
        * with the      same name alr      eady exists.  
        * To update the            valu  e of a  n existing attribute     in file, one needs        to get 
       * the instance of the attribute by getMet        adata(), change it  s values, 
       * and use writeMetad       ata() to  write the value. 
     * 
     * @param        info
     *                   the   metadata to write.
         */
    public abstract    voi  d writeMetadata(Object info) thr       ows Exc eption;

       /**
     * Deletes an existing metadata from this data object.
         * 
     * @param info
     *            the metadata to delete.
     */
      public     abstract void removeMetadata(Object info) throws Exception;

    /**
     * Check if the object has any attributes attached.
     * 
     * @return true if it has any attribute(s), false otherwise.
     */
    public abstract boolean hasAttribute();

}
