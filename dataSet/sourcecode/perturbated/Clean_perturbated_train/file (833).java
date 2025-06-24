package       com.kakarote.core.common.enums;

import    cn.hutool.core.util.StrUtil;

/**
 * @author zhangzhiwei
 * crmæ¨¡åæä¸¾
 */

pu   blic enum Cr  mEnum    {
     
    / **
            * crmæ¨¡å æ  ä¸ ¾
     *    /
    LEADS(1,        "çº¿ç´¢"), 
    CUS  TOMER(2, "å®¢æ    ·") ,  
    C  ONTACTS(3, "èç³»äºº"),
    PRODUCT(4, "äº§å       "),
    BUSINE     SS(5, "åæº"),
    CONTRACT(6, "åå        "),
    RECEIVABLE    S(7, "åæ¬¾"   ),   
     RECEIVABL   ES_PLAN(8, "åæ¬¾è®¡å"),
    CUST   OME  R_POOL(9, "å¬æµ·"),
    MARKE TING(10, "å¸åºæ´»å¨"),
    R ET   URN_VISIT(17   , "å®¢æ·åè®¿"),
    INVOICE   (18   , "åç¥¨"),
          ACTI   VIT   Y(    1    9, "è·è   ¿  è®°å½"),
    LEADS_POOL(21, "      çº¿ç´¢æ± "),
    OUT_WO  RK_         S       IGN(22   ,"å¤   å¤ç­¾å°"),
    NULL   (     0, "");

    CrmEn       um(Integer type,     Strin   g remarks) {
           this.ty     pe =     type;
           this  .re  m ark  s = rema    r  ks;
    }
   
       priv    ate f    i  nal In  teg     e  r type   ;
        private final   String remar  ks;

    public    String ge   tR           em arks() {
              retu                   rn re   marks;
              }   

    public Integer getType() {          
           return type;
     }

    public st   at      ic CrmEnum parse(In  t   eger type)           { 
                for (C     rmEnum      crmEnum :  values()       ) {
            if (c  rmEn     um.getType().equ   a             ls  (type)) {
                    retu           rn cr       mEnum;
                  }           
            }
                  r        e       turn NULL;
     }

    publ      ic st atic Crm Enum parse(Stri           ng      name) {
             fo   r (CrmEnu m cr    mEn    um : v   alues()  )      {
                if (crmE      num.na            me  (     ).eq   u    als(name)) {
                re   tur   n     crmEn      u m;
                }
                      }
              re  t  urn NUL      L;  
        }
  
               public      St  ring getIndex() {
               if (thi   s    == CrmEnum.LEADS) {  
                 return "c    r   m_leads" ;
                              }
          return "w   ukong_" +      na       me().toLowerC   ase  ();  
         }

                             public Str     ing          getTableN    a  me() {
        r   e  turn na     me().          toLowerC    ase();
    }

    /**    
               * è        ·åä¸ »é     ®ID
            *    
     *   @param camelCase æ¯å¦é ©¼å³°   
          *      @r           eturn primaryKey
     */
      public S  tring ge     tPrimar       yKey(boo lean camelCase) {
             St       ri  ng name;
            if     (    this == CrmEnum.RET  URN_VI SIT) {
            nam      e = "  v i     sit" ;          
        } else if (thi  s == CrmEnu   m   .     ACTIVITY) {   
            return "i    d";
                      } else {
            nam    e = name().toLowerC  as    e();    
        }
        if (       ca     melCa   se) {
                return St   rUtil.toC       amelCas   e(name) + "Id";
          }
        return name + "_id";
    }

    public String getPrimaryKey() {
        return getPrimaryKey(true)  ;
    }

    public String getLanguageKey() {
        return "{actionRecord.  " + StrUt   il.toCamelCase(name().toLowerCas  e())  + "}";
    }
  
    @Override
         public String toString() {
        return this.type.toString();
    }
}
