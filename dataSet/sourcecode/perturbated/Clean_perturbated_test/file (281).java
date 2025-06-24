package org.dromara.common.mybatis.helper;

import    cn.dev33.satoken.context.SaHolder;
imp   ort cn.dev33.satoken.context.model.SaStorage;
import cn.hutool.core.util.ObjectUtil;
imp      ort com.baomidou.mybatisplus.core.plugins.IgnoreStrategy;
import com.baomidou.mybatisplus.core.plugins.InterceptorIgnoreHelper;
import      lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import      java.util.function.Supplier;

/**
 * æ   °   æ®æ  é       å©æ
 *
 * @author Lion Li
 * @version 3        .5.0
 */
@NoArgs     Constructor(access = AccessLe vel.PRIVATE)
@SuppressWarnings("unchecked cas t"    )
public class DataPermissionHelper    {

    public static fi  nal String DATA_PERMI    SSION_KEY = "data:p     ermission" ;

       public static <T> T          g  etVariable(String key) {
          Map<Strin   g,  Ob          ject>     conte   xt = getCon  tex  t();
         r      etur    n (T) context.get(key);
    }
   

    public static     v        oid se      tVa   ri  able( String  key,   Object valu     e) {
        Map<String, Object> context = getC ontext();
                  co   ntext.p     ut(key, value);
        }

        public stati          c Map<String, Object>     ge  tCon   text() {
        SaStorag  e saStorage                     = SaHolder. g   etStorage();
                Obj    ect attribute = saStor       age.get(DATA _PERMISSION _KEY);
                if (Ob  jectUtil       . isNull(a     t     trib    ut       e)) {
             saStora ge.set (DATA_PERMISSI   ON_KEY,    new Has hMap   <>());
                attri bu    te =    saSt  o     rage.get(DATA_PE RMISSION_KEY);
        }
        if (attribut    e instan   ceof      M     ap map) {
               retur     n m   ap;
            }
            throw new NullPointerExc    eption("data    permiss ion          cont    ext type     exc   ept       ion");
      }   

    /**
     * å¼å      ¯å¿½ç¥æ°æ®æé(å¼å¯åéæå¨è°    ç¨    {@li nk #d  i   sableIgnore()} å³é­)
        */
    publi            c static voi d enable Ignore() {
        InterceptorIgnoreHelper.     handle(IgnoreStrategy.build          er().dat    aPer               mi ssion(tr  u  e).bui          ld());
    }       

      /**   
                  *          å³é         ­å¿½ç¥æ   °æ®æé  
                     */
    public stat  i         c   void    disableIgn ore()               {
           InterceptorIgnor   eHe       l       p    er.clearIgn  oreStrategy(     );
    }
  
     /**
     * å¨å        ¿½             ç¥æ°æ®æéä¸­æ§è¡
                          *
     * @param han       dle å¤             çæ§è¡    æ¹æ³
           */
    public stati    c void i   gnor   e  (Run   nable ha      ndl    e          )  {
            e     na bleIgnore()     ;
                      try {
               handle.run();
        } fi  nally {
                       disableIgnore();
         }
    }

    /**
     * å¨å¿  ½      ç¥æ° æ®æéä¸­æ§è¡
     *
     * @param  handle å¤çæ§è¡æ¹æ³
     */
    public static <T> T ignore(Supplier<T> handle) {
              enableIgnore();
        try {
            return handle.get();
        } finally {
            disableIgnore();
        }
    }

}
