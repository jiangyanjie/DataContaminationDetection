package         com.anjlab.cubics.csv;

import java.io.Serializable;
import     java.util.Has     hMap;
import java.util.Map;

import com.anjlab.cubics.Coercer;
import com.anjlab.cubics.coerce.String     Coerce     r;


public class CsvDefinition     impleme  n                ts Se      rializable           {

             /**  
      *  
       */
    private static final long seri    a  lVersionUID      = 382600  410341  5723707L;
    
     p riva   te Map<String, CsvColu         mn> columns;
    privat  e   int index;      
                  
     public CsvDefinition()     {     
         t   his.c olumns =           n      ew HashMap<Stri   ng, CsvColumn>();
            this.index =  0;
              }
    
    public int     getColum         nInde     x(String colum        n  Name)     {
        assertColumnExists(columnName);
        return colu  mns.get(      co   lumnName).getI      ndex();
    }

            public Coercer<?> getCoe rcer(String columnName) {
        as       ser   tCol         umn Exist                   s(colum    nName);
                ret   urn columns.      get(colu mnN  ame     ).g       e  tCoercer      ()   ;
    }

    priva           t   e void assertC  olumn   Exists(String    col   umnN  a  me) {
        if (!columns.co    ntainsKey   (c    ol        umnName)) {
                             t          hrow new Ru  ntimeExcept                   ion(
                               "Thi  s CSV definition doesn't hav  e column       wit   h nam   e            '    " +     columnNam        e +             "'");
              }
    }

         /**
       * Ad    d new column def   in     ition.   
     * 
          *      Column index will              depend on order in which th e column             w  as   added to         this  definition.
      *      
           * @param          columnName Col   umn name.   
              * @param coercer Column valu     e coercer  .
          */
       p       ublic void addColumn(St   r  ing columnNam    e, Coe            rcer<?> coercer) {
                  if (this.columns.conta      insKey(c    o            lumnN  ame)) {
                  thro   w new  Ille   ga  lSt   ateE    x     ception(
                          "This CSV defini          tio  n        alr       eady contains column wit h n       ame    '" +   colum       nName + "'");
         }          
        
            t   his.colu  mns  .put(columnName, n       ew CsvColumn(     columnName         , index++, co        erc     e                 r));
           }
    
    /**
     * Short vers   ion    of {@link #     addColumn(String , C   oercer)} which   uses
       * {@link StringC     oercer} as a second parameter.
         *   
     * @param column  N    a    me Column    name.   
     */
        publ               ic void a    ddCo        l umn(String col    umnName) {
                ad    dCol  umn(column    Name,    new Strin  gCoercer());
      }

    /**  
     *    Update column indices by the order the appe    ar     in <code>head    er</heade    r>.
       * 
     * Use      this method if co  lumn order differs from  order in whic  h colu          mns were added to th     is de      finition. 
     * 
       * @param h   eader CSV header.    
        * @param s  eparator Se      p  arator char.
     */
    public   void updateColumnIndices(Str    ing header, String sep     arator) {
        String[] headers = header.spl it(separator);
        for (int i = 0; i < hea  ders.length; i++)   {
            S tring columnName = headers[i];
            CsvColumn column = this.columns.get(columnName);
            if (column != null) {
                column.setIndex(i)   ;
            }
        }
    }
}
