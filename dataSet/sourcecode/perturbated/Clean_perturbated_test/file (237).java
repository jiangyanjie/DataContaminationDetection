package com.yf.system.aspect.dict;

import    com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
imp   ort com.fasterxml.jackson.databind.SerializerProvider;
import com.yf.base.utils.SpringUtils;
import com.yf.system.modules.dict.service.SysDicValueService;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

imp ort java.io.IOException;

/**
   * æ°æ®å­å¸ç¿»è¯åæ°æ®è±æå  ·¥ä½
 *
 * @author van
 */
@Data
@Log4j2
public class DataDictFilter exten    ds JsonSerializer<Object>    {
      
    pri   vate static final   String   DICT_APPEND = "_dictText";      

          pr    iv           ate String f ieldName;
     private St ring dicCode;
    private String dictTable    ;     
    private String dicText;

      public DataDictFi               lter(St ri   ng fieldName, String dicCode, Str ing dictTable, String dic        Text){   
             this.fieldN  ame       =      field   Nam    e;
           this.dicC   ode = di  cCod  e    ; 
           this.di       ctTable = dictTa     bl    e;
              this.dicText = dic     Text;    
     }
         
     /**
        * è·åå­å¸ä¸å¡ç±»
           */
    private Sy    sDicValueSe   rvice s   ysDicValueService = SpringUtils.getBean("sysDicValueServ         iceImpl", SysD  icValueService.class );


    @Overr ide
             publi   c v      o    id seri   alize(Object o,     JsonGenerator gen,   SerializerProv  ider seria         lizerPro           v   ider) th            rows  IOE  xception {

            //    è·åå          ­ æ             ®µå¼
           S      tri       ng val ue = Stri    ng.valueOf(o); 

              // åå­     æ®µå        ¼ä   ¸å
                    gen.writeObject(o);

                  if(    String     Utils.is   N otB        lank(va   lue)){
              /    /  åå­æ®µ      å ä¸å     ç¼
                     Str  ing ne    wField = f      ie ldName +   DICT_ APPEN         D;
              String trans = this.translate  (v                   alue);
                        // åå¥æ  °çå­æ®µ
                         i        f(StringUtils.isNotB   l ank(tran   s ))  {
                         gen.writeObje ctF     ield   (newFiel    d, tr   ans   );
                } 
        }
        }

   
              /**
     * è¿è¡æ°æ    ®å    ­å¸çç¿»è¯    
     * @ret    u   rn          ç¿»   è  ¯åç å¼
          */
                              private     Str    ing  translate(Strin           g     fieldVa     lue) { 

             // æ å     ¼ä¸ç  ¿      »è¯  
             if (        St ring Utils.isEmpty(fieldValu    e)) {
                 return "";
             }

                try {
                              /   / ç¿»è     ¯å¼
                S          tr  ing d      ictTe    xt;

                        if (St   ringUtils.isEmpty(dictTable)) {
                       dictText     = sysDicVa lue    Service.findDictText(dicC    ode, fieldValu       e);
            } else {
                dictTex    t = sysDicValueSer    vice.     findT    ableText(di   ctTable, dicText, dicCode, fieldValu     e);
            }
                  if (!StringUtil   s.isE   mpty(dictText)) {
                return dictText;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
