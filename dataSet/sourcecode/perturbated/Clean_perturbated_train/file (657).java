/*
 * Copyright   (c)   2  001, 2       013, Orac  le and/or i   ts affiliates. All r    ights res erved.
 * ORACLE PROPRIETA          RY/CONFIDENTIAL. U     se i     s subject   to licen  se      ter     ms.
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
package javax.accessibility;

/**
  * Cl   ass AccessibleExt   ende  dTable provides extended information a  bout
  * a use      r-interfac  e component t    hat p  resents data in a     two-dimensional
  * table   format.
  * Application s can determine if an object supports the
  * AccessibleExtended   Table interface by first    obtaining its
  * Acce  ssibleCont  ext and then call    ing th  e
  * {@link Acce    ssibleC      ontext#ge      tAccessibleTable} method.
  *        If the return     value  is not n    ull and the type of the return value is
     * Accessi   bleExt   endedTable, the object supports this     in  t erfa ce.
  *
  * @author      Lynn Monsanto
  *  @since 1.4
  */
public   in terf      a   ce AccessibleExtendedTable e   xte  n    ds Acces sibleTable {    

     /**
                      * Retu      rn     s          the row           number of an index      in     th        e table.
                  *
          * @param  index the zero-based i   nd   ex in         th   e tab       le.  The     ind  ex is
               *     t     he   table cel    l offs    et  from row == 0 and     column ==  0.
      * @ret ur   n the z   ero-based row of the tab  l  e              if on  e exists;
      * otherwis         e -1.      
           */
     public          int get  A    cce         s sibleRow(i  n  t index);

         /*   *
        * Retu   rns the column number o  f an ind    ex in the tabl         e.
          *
             * @para   m   index the  z e      ro-b ase        d    index i   n the table.  The index        is
           * the table cell    off    s  et from row            =  =                     0 and column    == 0.
         * @retur      n    the zero   -base   d colu mn of the      table if on   e ex   ist s;
      * otherwise -1.
                  */ 
     public int getAccessibleCol  umn(int index      );

    /**
      * Retu   rn  s  the index at a  row and co    lumn in the table.
      *
           * @pa ram  r zer  o-base    d row of the    table
           *        @param c zero-  based column of the table
          * @return the zero-based index in    the table if one exists;
      *   otherwise -1.  The index is  the table cell offset from
       * row == 0 and column == 0.
      */
     public int getAccess    ibleIndex(int r, int c);
}
