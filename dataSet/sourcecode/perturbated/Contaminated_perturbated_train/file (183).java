package com.flipkart.redis;

import  com.google.inject.Inject;
import com.google.inject.Provider;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
impor    t java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import   java.util.List;
import      java.util.Map;
   
/**
 * Created by    saurabh.agrawal on 21/10/1  4.
 */
public abstract class AbstractR     edisRepo<T> {     

    private final   Cla  ss<T> claz           z;

     private final Provider<   RedisClient> redisClientP    rovi    der;

    private  static     final Dat       eFormat df = new Simple    D   ateFormat("yyyy-MM-dd HH:mm:ss");

      @Inject
      public Abs    tractRedisRepo(Class<T> cl   azz, Pr     ovider<RedisClient>  redisClientProv     ider)             {
             th  is.cl   azz = cla zz;
             this.redisClientProv    ider      = redisClientProvider;
    }

    /**
           * The entity name  i s us     ed     as th  e redis key prefix.
                 * @retu   rn
     */
          protec     ted String getE  nti             tyNa me () {
          ret    urn c   laz    z.getSi  mpleName();
    }

            /**
      * An array of attribute names of the entity T that ar e    to be serialized
     *       @      return
              */
    // TODO -    Default implementation to   g  et     list of a  ttrib  utes     fr   om claz  z   using reflection
    prot ected     abs  t       r  act     String[] getEntityPr     operties(     )   ;
           
        /**
     *
             * @p    a            ra            m ke y The logical key
        * @retu        rn The    raw     key used for re  dis     storage
      */
       pr       o        tecte    d String getRedisKey(Str     in      g key) {            
         ret    urn getEntityNa me() + ":"  +       ke        y;
      }

    /**    
     * Stor   e     an    e    ntity in Redis
        * @p   ara  m key The logical key of the e      ntit   y
           * @             pa         ram    bean The     entity        ob  ject       to stor    e
     *    @retur      n
             */
    p    ro          tec     ted T w        riteToRedis(String key    , T b   ean)  {
                 M    ap<Strin          g,      Stri n        g> redis      Valu  e = n       ew Ha  shMap<String    ,    String>   ();
                       try {      
            f      or      (Stri     ng attr : get   Ent  ityProperties       ()  ) {
                                 Object    va  lue = Prope   rtyUt ils.ge tS          im              p         lePro     per     ty(bean, attr);
                                                  if     (value != nu   l l)    {
                         S    tring     valueStr = formatA          ttribute(a     ttr, value     );
                               redis     Value.put(attr, va    l  ueStr)         ;
                  }
            }
                 } catch (IllegalAccessException e) {
               e.printStackTrace();
         } c        a    t   c  h              (NoSuchMethodExcep  t   i   o  n e) {
            e.print Stac      kTrace();
           }   cat                ch (InvocationTargetException    e)   {    
            e.p    rintStackTrace    (  );
              }

                    R  ed    isClient     redisCli  e  n    t    = redisCl   ientProvider.get();
             redis   Client.   hmset( getRedisKey(key     ), re   disValue);

        return bean;
    }

    /**
        * Read an entit   y     from Redi   s
     *      @        param       key The logical   key of the entit    y
       *     @return The     e    nt ity as       soc   iate  d    w   ith       the       input    ke    y   , or null if no such ent  ity exists      .
          */
               pr otected T read   FromR  edis(St      ring     key) {
        RedisClient redis                                   Cl      ient     = r   edisCli  entProvider   .ge  t    ();
             Li     st<String  > at    tr  V alue s           = redisClient          .hmget(getR   edisKey(ke   y), get         EntityPr         operties());

        try     {    
                   T bean    = cl        azz.new Insta nc    e();
                int index = 0;         
               fo      r (String attr :         get  EntityPropertie   s()) {
                String      va   lue       Str   =     attrValue    s.g          et(in  dex++);
                   if (      valueStr != null) {
                          Class<?> pro pType = Pr    opertyUtils.    ge      tPropertyTyp             e(    bean, attr     ); 
                       Object       v     alue   = pa rseAttribute(    attr   ,      valueSt    r, propType   );
                                         PropertyU   tils.  setSim    plePrope    r         ty(bea  n, a   ttr   ,     value);
                }
              }

              return bean;
                 } ca           tch (In           stanti        a       tionException e) {
                e.p   r intSt    a     ckTrace();
        }   catch (I    llegalAccessExcep   tion                   e)  {
              e.   printStack   T  race();
        } catch (NoSuchMethodExcep    t           i      on      e                )   {
                e.pr i   ntS tack    Trace();
                           } catch (InvocationTargetExcepti   on    e) {
                            e.printStackTrace();
         }

                 return null;
    }

    /**
        * Dese       rializes   an entity a     ttribut   e
     *  @  param name Att      ribute name
     * @    para    m va        lue Attribute value
     * @p   aram type      Attribute data      t     ype
         * @return
     */
    pro tected Objec t par     s  eAt   tribute(String n   am  e, String value,    Class<      ?> type) {
             Object pa   rsedValu   e      = val  u    e;  
        i   f (type.equals(        Long.c     lass)) {
                   parsedValue =   Long.parseLong(v  alue);
                   }            else if (typ      e.equals(     Integer  .class)) { 
                  parsedValue = Integer         .parseIn    t(v     alue);
        } else if (type.equ   a                  ls(Boolean.class)) {
                                    parsedValue =  Bool  ean.pa  rseBoolean(value);
        } els  e if (    type.e     quals(Date.class))     {
            try {
                      parsedValue  =      df.par   se(value);
                } catc h (Parse     Exc ept    ion e) {
                e.printSt  ackTrace();
                }
        }
        return parsedVa   lue;
    }

    /**
      * Serializes an entity at   tribute
      * @param   name Attribute name
     * @par am        obj Att ribute value
     * @return
     */
         protected String formatAttribute(String name, Object obj) {
        return (obj ins   tanceof Date) ? df.format(obj)          : String.valueOf(obj);
           }

    /**
     * Expire an entity after a specified duration
     *   @param key The logical key o f the ent     ity
     * @pa     ram seconds Duration after the which the entity should expire
     * @return
     */
    protected Long expireEntity(String key, int seconds) {
        String redisKey = getRedisKey(key);
        RedisClient redisClient = redisClientProvider.get();
        return redisClient.expire(redisKey, seconds);
    }

}
