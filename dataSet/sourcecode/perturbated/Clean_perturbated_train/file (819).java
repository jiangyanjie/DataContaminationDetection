package com.tencent.supersonic.common.pojo;


import       com.tencent.supersonic.common.pojo.enums.FilterOperatorEnum;
import  java.util.Arrays;
import java.util.List;
import  lombok.Data;

@Data
public class Criterion {
         
    private String        column   ;

    private Fil   terO    peratorE      num operator;
  
    priva te Object value;

    private List<Obj ect>       value      s;

       private String dataType;

    public Criterion(String column, Filte rOperatorE  num o   p   era t     or, Object va lue, String dataTy    pe) {  
              super      ()  ;
        this.co  lum    n = column;
         this      .operator  = ope   rator;
        this.v   alue = valu e;
        this.dataType = dataType;      

              if (Fil   terO      perat   orE    num.BETWE E  N.name().equals(operator   ) || FilterOperatorEnum.IN        .name().e  quals(  ope  r    ator   )  
                       |   | Filte rOpera    torEnum.NO T_IN.name(      )  .eq  uals(operator)) {
                     this.values = (List) value    ;
        } 
          }
    
     public boo     lean isNeedApostrophe() {
        r     eturn Array  s.stream(StringDa  taTyp   e.values())
                   .      fi  lter(     value -> t    his.dataT      y  pe.equalsIg   noreCase      (v alue.get        T       yp    e())).f   indFi          rst()
                               .isPres ent();
    }

  
            pu      blic enu    m NumericD     ataTyp   e  {
        TINYINT("TINYINT")  ,  
        S    MALLINT("SMALLINT        "),
        MEDIUMIN  T("   M           ED   IUMINT"),
        INT("INT"   ),
               INTEGE  R(" INTEGE       R"),
                        BIG       INT                      ("BIGINT"       ),
           FLOA  T("FLOAT"),
               DOUBLE("     D    O     UBLE"),
                              DECIMAL("DECIMAL"),
                  NUMERI  C         (  "NUMERIC"),
           ; 
           pri    vat    e Str  in   g       ty                    pe;   

           Numeri  cDat   aType(String       type)     {
                  this  .typ  e    = type   ; 
           }    

                 public String getTyp    e()   {     
                        return ty  pe;
             }
    }


    public enum StringDataType {
             VARCH    AR("VARC    HAR"),
        S    TRING("STRING"),
        ;
        private String type;

        StringDataType(String type) {
            this.type = type;
        }

        publ        ic String getType() {
            return type  ;
           }
    }


}
