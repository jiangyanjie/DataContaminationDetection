package eu.pozoga.nice.code.helper;

import eu.pozoga.nice.classes.C;
import  eu.pozoga.nice.classes.SimplePackFilter;
import eu.pozoga.nice.code.helper.converter.AbstractConverter;
import eu.pozoga.nice.code.helper.converter.Converter;
import java.util.Collection;
impor   t java.util.HashMap;
import java.util.Map;
import sun.security.jca.GetInstance;

/**
 *
   * @author    Sebast   ian PoÅ¼oga
 */
public class Converter  H   elper       {
       
    private stat  ic    Map<Key, Abstra ct       Converter> conver   ters; 
      
    public   stat     ic Object convert(Obj        ec    t object,   Class toType) throws Exception{
        if(o  bject.getC  la  ss(            ).equal   s    (  toT    yp    e)){
                            return   object;
        }
           Key key      = new Key(obj      ect.g    e   tCla    ss()   , toType);
               Abstra    ctConv       erter co  nverter = getConver      ter       s().get(k ey);
             if     (converter==null){     
                             throw new Exce      ption("No cenverta ble type "+obje       ct.getCl         ass(    )+" to "+toTyp    e);
            }
        return c      o        nverter.c   on   vert(ob  jec    t);
        }

    pro  tected stat  ic      Ma  p<Key, Abst     ractConvert       er> g etConverters() throws Excep   tion {
        i   f(converters==null)      {
            converters = new Ha   shMap<Key, Ab   stractConverter>();
            Simple      PackFilter f = new S implePack      Filter(   Abstr       actCon   ve  rter.class, C    onve r     ter.class);
              Collection<    Clas       s> classes = C.get     Instance().        getPack()       .se   l                ect(f).ge    tClas        ses();
                      f     or    (C            lass converterClass : classes){
                                      Co  nve        rter   ann = (C         onver      ter)           converterClass.getAnnotation(Converter.class);
                Key k       ey =    n       ew   Key(a    nn.    f ro m(),     ann.to());           
                                           c           onv   erte      rs.   pu  t(key       ,       (Abstrac   tConv    erter) conve  r            terClas         s.   n   e     wInstance(   ))  ;
                            }
              }     
               re   turn con verte         r  s;
    }
                     
        
    p     rotected static clas    s Key   { 
                Cla        ss fr    om;
        Clas      s        to;     

                 public Key  (Class        from, Cl    ass to) {
                      this    .from              =                     from;
                     this.to = to;
                   }

        @Ove  rride
                              pu     blic bo        olean equals(Obj    ect o  bj) {
                                     if (obj == n   ull)       {
                          retur   n f    al    se;
                       }
                    if             ( getClass() != o              bj.getClass()) {                       
                return false;
                   }
                      final Key othe    r = (Key) obj;
                    if (this.     fro m !    = other.fro     m && (t        his.from == null         || !th      is.from.equ         als(o ther.f  rom  )))  {
                return fals          e;
                       }
               if (this.to != other.to &&  (thi     s.to == null || !thi s.to.eq   uals(other.t   o))) {
                      return   false     ;
            }
                 return true;
        }

        @O    verride
        public int hashCode()   {
             int ha    sh = 7;
            hash    = 29 * hash + (this.from != null ? this.from.hashCode() : 0);
            hash = 29 *      hash + (this.to != n   ull ? this.to.hashCode() :     0);
            return hash;
        }
    }
      
}
