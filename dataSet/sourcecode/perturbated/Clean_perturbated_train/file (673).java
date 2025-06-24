/*
  * Copyright (c) 1999, 2013,    Oracle      and/o     r its af     filiates.    All rig   hts reserved.
 * ORACLE PROPRIETARY/CONFIDENTI  AL. Use is subje    ct   to license terms.
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
     * /

pack  a         ge javax.access   ibility;     

/**
 * Class AccessibleTable describ es a u   ser-  interfa  ce compo nent tha    t
      * p resents data in a two-dimensional table format.
 *
 *             @aut    h   o       r             Lyn    n Mons a    nto
          * @since 1.3
 */
pub        lic interface Acc es       sible Table {
    
       /**
             * Retu  rns the   ca ptio     n fo        r   the table.
                          *
     *     @ re            turn the caption   for the table  
     */
    publ   ic Acces       sible       get   Accessible      Captio n();

    /*      *
     * Sets t  he capti     on for th  e table    .     
         *
     * @p  aram a th    e capti on f                    or the table
         */      
      public v    oid s  etAcc  ess      ibleCa  ption(A   ccessible a  );
 
    /**
     * Return s the su  mma    ry descript      ion of the table.
          *   
             * @return the sum   mary de    scription     o  f the tab le
     */
      public Accessib  le getAccessibleSumm       ary();

              /   **
                     * Sets the summa    ry de scrip   tion of the table
            *
       * @param a t         he s u   mma   ry d  escript    ion of    the table
     */
       public void setAccessibleSum   mary(Accessible a)     ;

          /**
                    * R eturns the     numbe    r of rows i   n the        table.
           *
     * @retu   rn t   he n   u   mb e  r o           f rows in th  e tabl   e
     */    
    public int get  Acc    essibleRowCo unt                  (   );    

         /*   *
        * Returns the number of    c    olumns in the      table.
     *
     * @return t he number  of columns in         t he   table
                             */
    publ  ic i   nt getAccessibleColu mnCount()      ;

    /**
     * R   eturn   s the Ac    cessible at a specified row        and column  
     * i   n     th        e   table.          
         *
     * @pa   ram r ze  ro-based row o  f the ta   ble
     * @p a    ram    c  zero-based c       olu       m n of the t  abl  e
        * @return th   e Accessi bl e at the      speci   fied row and column
        *   /   
    public Acce    ssible getAccessibleAt( i  nt    r, int c);

          /    **
     * Return        s the    number of         rows oc      cu  pi     ed b             y the   Accessible              at
     * a   specified row and c  olumn in the t   able.  
       *
     *      @param r zer      o-ba    sed row of the table
     * @param c z  ero-    based   colu  mn of the tab       l    e
        * @  return  the number of  rows occ   upied by   th  e    Accessible      a t a
        * given sp      ecified (row, column   )
     */
    p ubl   ic int getA  cc  ess  ibleR  owEx  tentAt(    int  r, i  nt      c);

    /  **    
     *    Returns     the      number of columns occupied by the Ac  cess      ible     at   
                * a speci fied row and column          in       the tab      le.
     *
               *   @param r zero-bas    ed row of the table
     * @param c ze  ro-ba sed column of the tabl   e
           * @r         eturn the num  be   r of colum  ns occu       pied by the Accessible at a       
              * given   specified r    ow    and col        umn
       */
    public int get            AccessibleColumn ExtentAt(int r,    int c);

    /**
     *      Returns the row h     eaders   as       an     Access  ible              T         able.
           *
     * @ret          ur   n an Access   ibleTable rep     resent     ing the    row
           * headers
     */
    pub       lic Acce  s    si       bleTab  le g et   Accessi      bleRowHea       der(     );

          /**
             * Sets the row headers.
            *
     * @       p   aram table an AccessibleTable represe   n      ting the    
       *       row headers
     */
         public v   oid     set Ac        cess  ibl          eRowHeader(           Accessible  Table t       ab  le);

    /   **
     *    Returns th           e column h      e aders as an    AccessibleTable.
           *
     * @return an     AccessibleTable represe   ntin   g      th   e  column
        *    headers
        */
    public AccessibleT       a  ble getAccessibleColumnHeader( );
       
    /**
     *     Sets the column headers.
          *
                 * @param table an Acc     e ssi     bleTab le representing the
       * colum          n   h   eaders
     */
    pu   blic void setAccessible  C      olumnHeader     (AccessibleTable ta     ble);
   
       /**
      *  Returns t  he descripti  on of           the specified row in t he    table.
     *
     * @p  aram     r zero-based    row of the ta     ble
     *         @return t      he description of the           row
     */
    public Accessible getAccessib   leRowDescriptio       n(int r);

    /      **
        * S                  ets th  e descriptio        n    text          o   f t he specified           row o      f the table   .
        *
     * @pa       ra     m r zero-ba  sed r ow of   t    he tabl        e
     * @param a       the description   o        f the row 
       */
    pu   blic v oid s    etAcces         sibleRowDescription(  int r, Accessible a);  

    /**
        * Returns the description text of th e specified co   lumn i n th  e t  able.
     *
        *       @param c zer                 o-based co lumn        of       the tab le
           *   @retu rn the text description of   the      co  lumn
     */
    pu    blic Accessible    getAcces  s         ibleColumnD  escription(int   c);

    /**
     *   Sets t     he      descrip    tion tex    t of t     he spec            ified column in the t a           ble.
        *          
        * @para  m         c   zero-bas  ed column of the table
      * @pa      ram a   the tex    t    description of the c  olumn
     */
          pu  blic          v  oid setAccessibleC     olumnDe scription(int c, Access ible a)        ;

    /**
     * Returns a boolean value           indicatin     g whet   her t   he accessible at
     * a specified row and column is sel   ecte  d.
               *
          * @param r zero-bas        ed row of the table
     *          @           p    aram c zero-based column of the table
              *  @return the boolean    value true if the accessible at the
       * row and column  is selected. Ot     herwise    , the boolean value     
     * false
     */
                 public boolean isAccessibleSelected   (int r, int c);

       /**
                 * R  eturns a bool   ean val  ue indicating whet   her the specified    row
     * is selected.
       *
     * @param r zero-based row of the ta    ble
     * @return t       he boolean value true if the specified row is selected     .
       * Otherwise, fals e.
     */   
    public bo    olean isAccessibleRowSelected(int r); 

      /**
         * Returns a boolean value indica     ting whether the specified column 
     * is selected.
     *
     * @param c z     ero-based column of the      tab    le
        * @return the boolean value   true if the specified column is selected.
     * Otherwise, false.
     */
    public      boolean         isAccessibleColumnSelected(int c);

      /**
     * Returns the selected rows in a table.
     *
         * @return       an array of selected rows where each element is a
     * zero-based row of the table
     */
    public int [] getSelectedAccessibleRows();

    /**
     * Returns the selected columns in a table.
     *
     * @return an array of selected columns where each element is a
     * zero-based column of the table
     */
    public int [] getSelectedAccessibleColumns();
}
