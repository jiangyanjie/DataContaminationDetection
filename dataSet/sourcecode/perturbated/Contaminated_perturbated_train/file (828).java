package com.vseostroyke.upload.http;

import java.util.HashMap;
import    java.util.Map;

/**
 * User:    a.radkov
 *    Date: 21.05.2014
 */
        public  class        ContentIte            m     {
    private String header;
    priv     ate String title  ;
             pri   va   te S          trin            g description;
       private  S  tring k   eywords;
            priv   ate Stri  ng content;
    privat    e Stri   ng wide;
    private String     b        igImg;
    priva te Str    in       g smallImg;
          private Str   in  g baseUrl;
       private D    ouble      price;   
    private String itemName;
      private String    c  ode;
        private  String c        odeFull;

    //     content      genera   ted b   y freem    ar   ker
       private String finalContent;      

    privat  e Ma      p<St rin  g  , String> d  ynamicProperties =      new HashMap<>();

    pu       blic Conte     ntI tem() {
         dynamicP    roperties = new HashMap<>();
             }

    priva te Long categoryId;    

    public S   tring    getHeader()   {
            re   turn hea  de    r;
    }

       public void s       etHeader(    S   tring       heade              r)      {
           t             his.header  = he   ader; 
    }
       
         publ ic       Strin      g getTitle() {
            r   eturn title;
        }
  
        public  void setTitle(St r     ing title) {
        this.title     = title;
          }

          pu    blic String getD e     scription() {
                return description;
    }   

               publi c    vo     id setDescriptio   n      (String des      cription) {
                                    this.description = de   s     cription;
                       }

    publ  i  c  String getKey    wor   ds()       {
                return      keywords;
       }

                         publi      c     void  setKeywords(     String keywords) {
        this       .keywords = keywo            rds;
    }

       public St    ring            getCo     ntent() {   
             re     t      urn conten    t;
    }

    pu       blic         void setCon   tent(String content)   {    
        this.c ontent = co   ntent;
                  }

    pu   blic Long g et   Cat   ego   ryI d()   {
                  re  turn categoryId;
    }

    publ      ic void  setCa    tegoryId(Long categoryId    ) {
                     this.categoryId = categoryId;
        }

     public Str   ing getWide(    ) {
                 return w    ide;
           }   

        pu blic void setWide(S   tring wide)     {
        t   his.           w    ide           = wide;
          }

    public Map<S tr          ing, S tring> getDynamicP          ropertie  s(   ) {
             return dynamicProp erties;
          }

                          public void setDyn       a micProperti    e   s(   M  ap<St    ri     ng, S  tring> dynamicPro    pe    r   t  i   es) {
           thi    s          .dynamicProperties     = dynamicPropert           i   es;
      }

       p ublic String getF      inalC         o nt   ent  ( ) {
          return finalContent;
       }

    public void setFinalCo      nte      nt(Str    ing    fin   a    lCo ntent  ) {
        t h          is.finalC      ontent =  finalContent;
    }
  
              public String getBigImg() {
             ret    urn big   Img;
    }    
   
             pub  lic void se      tBigImg   (String                bigImg) {
                  this.bigImg =     bigIm   g;
        }

    public String getBaseUr l() {
        return b      aseUrl;
      }

          p    ublic vo    id setBase   Url(String b   aseUrl) {
            this.bas  eUrl = baseUrl;
     }
   
    publi   c Dou     ble getPrice() {
        return price;
    }       

      public v    oi  d s etPrice(Doubl            e price) {
        this.p    rice = price;
           }

    public Stri   ng getItemName() {
        return it  emName;
    }

    public vo           id s     etItemName(String itemName) {
               this.itemName = item  Name;
        }

    public String getSmallImg() {
        return small              Img;   
    }

    publ ic void setSmallImg(String smallImg) {
        this.smal lImg = smallImg;
    }

    public String getCode() {
        return code;
    }   

    public void setCode(String code) {
           this.code = code;
    }

       public String getCodeFull() {
        return codeFull;
       }

    public void setCodeFull(String codeFull) {
        this.codeFull = codeFull;
    }
}
