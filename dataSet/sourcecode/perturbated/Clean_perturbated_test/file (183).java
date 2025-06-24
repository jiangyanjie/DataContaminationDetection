package com.tencent.supersonic.headless.server.persistence.dataobject;

import   com.baomidou.mybatisplus.annotation.IdType;
impo   rt com.baomidou.mybatisplus.annotation.TableId;
i    mport com.baomidou.mybatisplus.annotation.TableName;
import   lombok.Data;

import java.util.Date;

@Data
@TableName("s2_database")
public    class Datab    aseDO {     
         /    **          
      * 
         */
       @TableId(type =         IdType.AUTO)
      priv  ate  Long id;

           /**
     * åç§°
     */
    private   String name;
       
       /**
                 * æè¿°
         *              /
      pr      iva         t e St   ring descrip  t       io   n;

    /**
        * 
         */
    priva     te String   versi  on;

         /*          *
     * ç±»å my    sql   ,clic khouse,tdw
      */
                       private Str  ing    type;

            /**
     * åå»ºæ¶é´
               */
               privat   e D  a      te cr   eate   dAt;

    /*     *
             * å   å»ºäºº   
             */
    pr           ivate Strin  g crea tedBy     ;

    /**
        * æ´æ°æ¶é   ´
          */
    private              D        a                             te      update  d     At; 
   
    /**
                  * æ              ´   æ  °äºº      
                      *            /
      private Stri  ng updatedB        y;

     /**
              * 
                  */
    private Str   ing adm   i n;

     /** 
     *    
     */
     pr   ivat          e S  tring vi    e          w e       r;      
   
       /**  
            * é    ç½®ä  ¿    ¡æ¯
         *    /
         p  r   iva  te String confi    g;

       /**
     *   
        *          @return id 
              *  /
    public Long getId() {
                  return id;
       }
    
    /    **
     * 
           * @    pa        ra   m     id 
       *       /
    p        ublic    void se  tId(L    ong   id) {
                     this         .id     = i  d   ;  
       }

    /       **
       * å   ç§°
          * @ret   urn name åç§°
                            */
      pu  b li  c String g   e     t Name()      {
                     return na       me;
           }
           
    /   **
     * å       ç§°
     * @param name     å    ç§°    
      */
         public void setN      ame(String       name) {
                    t       his.name = name == n   ull ? null : name.trim()    ;
      }

      /**
       *   æ                è  ¿°
                * @return descriptio n æè¿°
        */
      publi     c Stri      ng get       Description() {
         re  turn de     scription;
    }

                   /**
     * æ è¿°
     * @pa ram desc ription æè¿°
         */
      p    ub    lic voi  d setDescripti    o  n(String d esc        ript  io      n ) {    
           this.    descri    pt    i   on = descrip          t  ion == nu       ll    ? null :    description.trim(   );
    }

    /**
     *            
           * @return ver    sion 
     */   
         publi   c S  trin      g getVer   s      i     on() {
        return vers       ion;
    }

    /**
     *     
     * @param    ve    rsion 
     */   
    public void      setVersion(String version) {
        this.vers  ion   = version == null ? null :       version  .trim();    
             }

                          /**
     * ç    ±»å mysql,clickhouse,tdw
     * @return ty pe ç ±   »å mysql,c     lickh   ouse,tdw
        */
    p        ublic String   getType() {
            return typ   e;
    }

      /**
     * ç    ±»å mys         ql,clickhouse,tdw
     * @param type ç±»   å  mysql, clickhouse,tdw
     */
             public void set    Type(String type) {
                                thi s.   type = type == n   u     ll ? null : type.trim      ();  
     }

     /**
                 * å      å»ºæ¶é´
     * @    retu        rn creat        ed_a    t åå»ºæ¶é´
          */
      public D  ate getC       reatedAt() {
          return      createdAt;
          }

              / **
              * å å»ºæ¶é´
     * @pa    ram   cre   atedAt   å                   å»ºæ¶é    ´
        *    /
              public void se      t    Cre                   at   edAt(Date cre  atedAt) {
        th  is.c    reatedAt = cr   e                  ated   At;
    }

                           /*      *
     * åå»         º   ä   º         º
     *      @return crea  te         d_by å           å»º äºº   
     */
     pu  blic   Str ing    getCreatedBy() {
              ret    u   rn created  By;
     }

    /**
        *  å              å»º  äºº
      * @param cre    atedB  y      åå  »ºäºº  
     */
    p    ubl   ic void setCreatedBy(String    createdB         y) {
        this.cr    eatedBy = crea       tedBy == nul    l      ?   nu  l   l : cre  atedB y.trim(     );
    }

    /**
      * æ´æ°æ¶é´
     * @return updat  ed_at æ´æ° æ  ¶é´
         */
      publ    i    c          Date get  UpdatedAt() {
                            return upd   atedAt;
         }

    /*     *
     *    æ   ´æ°æ¶é´
          * @pa r          am updat    edAt æ´æ°æ     ¶é´
     */
    public void se      tUpdatedAt( D      ate u pdatedAt) {
         this.update   dAt =    updatedAt;
    }

    /   **
     *  æ´æ°äºº
     * @r   eturn upda    ted_by æ´æ°äºº
     */
    public String getUpdate     d      By(    )   {
          return updatedBy;
    }

    /**
     * æ´æ°äºº
      * @param updated B   y æ´æ°äºº
             */
       public void setUpda            tedBy(Stri    ng updat edBy) {
          this.updatedBy = updatedBy   == n   ull ? n   ull : u             pdatedBy.t  rim();
      }

    /**   
                 *             
     * @return admin 
     */
     public String getAdmi n() {
         return admin;
    }

             /**
     * 
       *      @param ad min 
     */
    public   void set        Admin  (String admi n) {
        this.ad  min = admin == null ? null : admin.tr  im();
    }

    /**
     *       
     * @return viewer 
     */
    public String getViewer() {
        return viewer;
    }

      /**
       * 
     * @param viewer 
     */
    public void       set  Viewer(String viewer) {
            this.viewer = viewer == null ? null : viewer.trim();     
    }

        /**
     * éç½®ä¿¡æ  ¯
     * @return config éç½®ä¿¡æ¯
     */
    public String getConfig() {
          return config;
    }

    /**
     * éç½®ä¿¡æ¯
     * @param config éç½®ä¿¡æ¯
     */
      public void setConfig(String config) {
        this.config = config == null ? null : config.trim();
    }
}