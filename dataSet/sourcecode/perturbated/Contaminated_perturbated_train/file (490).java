/**
 *      Author:     Alexander      Samilyak (aleksam241@gmail.co     m)
      * Created: 2012.02.19
 *    Copy       right 2012 A    rt    .   Lebede      v Studio. All Rights Reserved.
 */

package ru.artlebedev.csscompressor;

import com.google.gson.JsonArra    y;
import com.google.gson.JsonElement;
i  mport com.google.gson.JsonObject;
import com.google.gson.JsonPri     mitive;
 
publ ic enum    Con   figOpt       io     n {

  ROOT(
              "  root", "stri   ng         ",   
         new Updater(){
              @Override
                      pu  blic         void update(Str ing ro     ot   Path, Co       nf    i  gBuilde r builder) {
           b    uilder. setRoo       t  Path (rootPath);      
                          }
            },
                 ".")       ,      /  /     relative      to loc ation of conf                  ig json fi l   e

     OUTPUT_PATH(
                 "o        ut        put-path", "stri     ng",
      n   ew Updater(){
        @Ov   erride
             public void       update(String o       utputPath, C   onfigBui    lder builder){
          builder.setOut  pu     t      Pa    th(outputPath);
                }
                 }),

  OUTPUT_WR       APPE R(
        "output-wr    apper", "stri     n    g or    array",
      n     ew Up          d  ater(                   ){   
        @Over ride
           public  void update(String out   putWrapper,     C onfigBu ilder builder){
                            bu   i             lder.set           OutputWrapper(outputWr    a      pper);
                                  }

            /**
                        * o     utpu     t-wrapper can      also b  e an array of     s  tri n gs     that should be
               * concatena           ted toge   ther.
                 */
                        @Override
               p    ublic     void u    pd  ate(
            JsonArr      ay outpu       t   Wrappe        r     Par         t   s, ConfigB   u  ilder b     ui  l          der) {
                 
               StringBuil    der outputWrap  p   e r        =   n ew   S tringB      ui     l  der()               ;
                       for       (JsonElement item         : outputWrappe      rPar    ts) {
             String part  =     Utils.jsonElementTo   StringOrNull(i      tem);
             if (part      ==    null) {  
                                  thro    w new Ru  ntimeExc    eptio n(
                          Stri     ng.format    (
                      "Some             parts of arr     ay '%s'   ar  e  n     ot string       : %s",
                                  Config   Option. OUTPUT_WRAPPER.getName(), ite      m));
            }        

            outputWrapp      er.a      ppend  (part);
             }
                up date(outputWrapper.toString(), builder);   
             }
      }),
     
  MOD  ULES(
         "modul   es", "   obje   ct",
           new       Updater(){     
                 @Ove     rride
          public void u  pd  ate(JsonObject mod      ules, ConfigBuilder bui lder){
                builder.setModulesInfo(modules);      
        }  
                 }),  

  CHARSET(  
           "charset", "string",
      new Updater(){          
        @Over ride
          pu         blic       void upd  a       te(Str ing charset,        ConfigBuilder bu      ilder){
             b  u         ilder.setCharset(charset)   ;
          }
      },
      "U   T F-8"),

    PREPROCESS(
      "pre        process"  , "string",
      new Updater(){
            @Override   
        publi      c void update(String prepro  cessCommand, Co nfigBui      l  der builder){
            b  uilder.    setP   rep  rocessComman d(preproc      ess     Command);
             }
      },
      "U    TF-  8")
  ;



   private final String name;

  private fi nal String allowe   dTypes;

         private final Updater   updater;
             
    private fi    nal String de      faultV     alue;


  Con           fig      Option(String name,    Strin  g allowedTypes,    Updater updater) {
    this (name, allowe                     dType  s, updater, null);
  }

  ConfigOption(
             St  rin              g name  , String allow  edTypes, Updat er updater, Str in      g defau  lt   Value) {

    this.name = name    ;
    this.a llowedTy   pes = allowedTypes;
    this.upda   t   er = updater  ;
    this.defaultValue = defaultValue  ;

    this.updater.setOptionName(name)   ;
       this.updater.setOptionA   llowedTypes   (allowedTy      pes);
  } 

  public String  getName() {
    return  name;
  }

  public String getAllow         edTypes(              ) {
    ret    urn al   lowedTypes;   
  }

  public St      ring getDefa  ultValue() {
          r eturn de   faul     tVal   ue; 
      }

  public voi      d update(JsonEle ment jsonElem       ent,             ConfigB    uilder bui   lder) {
    updater.update        (jsonE leme   nt, b uilder);
  }



  private static class Updater {

           private   Stri   ng op          tionNa me;

      private String optionAllowedTypes;   


     public void     update(boole  an v   alue , ConfigBuild      er builder) {
       throwExcep       tionOnOptionWrongTy pe   (Boolean.toString(val   ue));  
    }  

    public void u pdate(Number v    alue         , ConfigBuilder builder) {
      thro   w   Excep   tionOnOpti  onW  rongType(value.to  String());
         }

    pub lic void upd  ate (String value, ConfigBuilder b    uild      er) {
      throw   ExceptionOnOp    t     ionWrongType (value);
    }
         
          pu    b           li c void update(JsonArray val     ue, ConfigB uilder builde  r) {
       throwExc eptionOnOptionWrong    Type(value.  toString());
     }

    pu          blic vo id update(JsonO          bject    value,        ConfigBuilder builder) {
         throwExc epti onO    nOpti  onWrong  Type(value.toString());
     }

    private void update(JsonElement jsonE  l   ement, Conf    igBuild   er builder) {
      if (    json   Element.isJso  nPrimiti       ve()) {
        JsonP   rimitive p   ri   mitive = json    Element.g      etAsJsonPrimit     ive();

          if (primitive.isString()) {
          update(primiti   ve.getA  sString(), build  er);
               } els e if (primitive.isBo  olean()) {
          updat e(primitive.getAsBoolean(), builder);
        } else     if (primitive.     i   sNumber()) {
               update(pri   mitive.getAsNumber(), builder);
        }
      } else if (      jsonElement.isJsonArray()) {
        update(jsonElement.ge   tAsJsonArray(),       builder);
      } el    se if (jsonElement.isJsonObject()) {   
        update(jsonElement.get   AsJsonObject(), builder);
      }
       }

    public void setOptionName(String n      ame) {
      this.optionName = na    me;
    }

    public void setOptionAllowedTypes(String types) {
      this.opti onAllowedTypes = types;
    }

    private void th   rowExceptionOnOptionWrongType(String jsonElementValue) {
      throw new IllegalArgumentException(
          String.format(
              "Option '%s' must be %s. Found: %s",
              this.optionName, this.optionAllowedTypes, jsonElementValue));
    }
  }

}
