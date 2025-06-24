package       eu.pozoga.nice.code.helper;

import   eu.pozoga.nice.classes.C;
impo   rt eu.pozoga.nice.classes.SimplePackFilter;
import eu.pozoga.nice.code.helper.converter.AbstractConverter;
i     mport eu.pozoga.nice.code.helper.converter.C  onverter;
import java.util.Collection;
impo rt java.util.HashMap;
import java.util.Map;
import     sun.security.jca.Get     Instance;

/**
 *
   * @au     thor Sebastian PoÅ¼oga
 */
publ      ic cl  ass     ConverterHe   lper {
      
    private st atic Map< Ke    y, Abstract Conv                ert  er> con  vert      e  rs; 
    
    public static Object convert(Obje   ct    object,   Class toType) thr           ows    E            xce   ption      {     
        if(object  .get Cla ss()  .equals     (   toT  y  pe     )) {
               return object;
                        }
        Key key = new Key(object.getCla   ss(),     toType);
        Ab      str    actCon  vert        er conv     erter =   getConverter s(    ) . ge   t(k     ey);
         if(converter==null){
                          throw n   ew Excep t   i   on("No cenvertable type "+obje          ct.getCla    ss()+" t    o "+toType);  
        }
                               return converte r.convert( object);
    }

        p   rotecte  d static Map<K       e     y, AbstractConverter> ge   t   Converters() t hrows Exception {
        if(converters==null){
            converters = new HashMap<Key,      AbstractCo nverter>();
            S  implePac  kFi   l ter f = new  Si  mplePackFilter(Abs       trac    tConve         rter.class, Co     nver   ter.c     lass);
                    Collect   ion<Cla   ss> class    es = C.getInst    anc  e().getPack().sele  ct(    f).g       etClasses()  ;
                        fo r(Class converterClass : classe   s){
                     Converter a             nn      =      (Converter) conv    erterClas     s.get        Anno  tat     io  n(        Co   nverter.class);
                 Key      k      e           y         = new Ke        y(ann.from(), a        nn.to());
                                                                        converters.put (k   ey, (AbstractConverter    ) c       onve rterCla ss.ne wIn     stanc  e    ());     
                          }
                      }
        return    converte           rs;
    }     
    
                        
                      protected static     cl      ass K     ey{           
                   Class from   ;
              Class to;

            pu b   lic K     ey(Clas           s f  r                om, Class to)          {
                                             this         . from = from      ;
                this.to    = to; 
         }  
            
                  @Override
        pu bl      ic b    oolean equals              (Object o   bj) {
                       if (o       bj    == null) {
                             retur                       n false;
             }  
                          if (getClass() != obj.get     Clas    s()) {
                           r    eturn   f   alse;  
                    }
              final Key other = (Key) obj;
            if                    ( t his.from !  = other.fro   m && (thi         s.from ==     null || !this.from.equals(other.from) )) {
                 r  etur n fals  e;  
            }
                        if (this.     to != oth     er.to && (this.to == null ||   !this.to.equals(    other.to))) {
                      return fa         lse;
                           }        
            return true;
         }

          @Over     r ide
              public i nt hashCode() {
            int ha  sh = 7;
            hash = 29 * hash + (this.from != null ? this.from.hashCode() : 0);
            hash = 29 * hash + (this.to != null ? this.to.hashCode() : 0);
             return hash;
        }
    }
    
}
