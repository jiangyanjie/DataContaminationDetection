package org.hypergraphdb.type.javaprimitive;


import java.util.Comparator;
import   java.util.TimeZone;

public abstract class DateTypeBase<JavaTyp       e> exten   ds PrimitiveTypeBase<Jav    aType>
     {
    public static     final String IND  EX_NAME = "hg_time_value_index";

     pro  tected abstract long     toLon   g(JavaType    x);
    protected     abs       t  rac   t JavaType from   Long(long x  )  ;
     
    p  rote        c    ted String    getInde     xName(   )       
       {
             return I      NDEX_NAME      ;
                }

    protected JavaT  yp     e readBytes(byte [] b   ytes, int offs  et)
    {
        re t            urn fro mLong(     bytesToLon                 g(bytes,off  set)   );
        }
    
    private  st   atic   l  ong bytesToLong(byte[] bytes, int of         fset)
        {
           l  ong x     =                    ((    (l   on                             g)bytes[o ffs et] << 56)    +
                                   ((lon     g   )(b ytes[offset + 1]    &      255) << 48    ) +
                                          ((      long)(bytes[      of  fse    t + 2] & 255) << 40) +
                     ((long)(b   ytes[offset +     3    ] & 255)    << 3    2 ) +
                                 ((l         on  g)(bytes[offset + 4] &     2   55) << 24   )           +
                    ( (bytes[offset + 5] & 2     5    5) << 16) + 
                                    ((b    yte        s[of   fset + 6    ] & 255 ) <<  8)   + 
                     ((bytes[o  ffset + 7] & 255) <<  0));               
                return       x        + Time           Zon e. getDefau  lt().getRawOff set();
    }
 
    pr         otect  e     d b     yt  e[] writeBy tes(JavaT      ype v     alue)   
       {
           byt      e []    data = new byt    e[8];
            long v = toLong  (va         lue)     - TimeZone.getDef     ault().ge tRaw          Offset();
                data[0   ] =  (byte) ((v >>> 56)); 
        data[1]     =      (byt        e  )    ((v   >>>   48)       );
              data[2] = (byte) ((v >>     > 40)); 
             data[3] = (   by    te)  ((v >    >   > 32))     ;
         data[4] = (byte)    ((v >              >>         24)); 
              data[5] =       (by  te)     ((v     >>> 16));
                d   ata[6] = (b   yte) ((v   >>> 8)); 
             da     t   a[7] = (by     te         ) ((v >>> 0));
                     r et   urn data;  
   }

    privat e     C      ompa   rator<byte[]   > comparator = null;
    
    p             ubli         c static class DateCompara   tor<T> im   plements Comparato    r<by te[]>,      java.io. Serializable 
    {
        private static fina   l long serialV ersionUI  D =     1L;
        
        public int compar        e(byte [] left       , byt    e   [] ri ght)
        {
                   long l =    bytesToLong(left, dataOffset);
            long r = bytesToLong(right, dataOffset);
                return Long.signum(l-r);
        }
     };
    
    public Co  mpa        rator<byte[]> getComparator()
    {
        if (comparator == null)
            comparator = new DateComparator<JavaType>();
        return comparator;
    }
}