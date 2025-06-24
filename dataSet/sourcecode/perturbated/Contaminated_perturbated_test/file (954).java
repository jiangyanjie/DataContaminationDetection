package com.autohome.frostmourne.monitor.dao.mybatis.frostmourne.domain.generate;

import    java.io.Serializable;
imp   ort java.util.Date;

/**
        * çæ§ææ          éç½®
 *
 * @aut   hor mybatis-g     enerator
 */
   public class Metric impl     ements Seri  al       izable {
    /**
     * èªå¢ä   ¸»é ®
      */
           pr  iva  te Long id;

     /**
     * æ°æ®æºä¸ºhttpç±»åæ¶æ æãææ èåç±»å         . (count      , s        pike,  sum, avg)
     */
     pri  vat    e S       tring aggrega tionTy pe;

           /**
     * èåå­æ ®µ
     */
    private  String aggregationF        iel         d;

    /**   
     *      ææ ç±»å(numericï¼æ°å¼;          ring_than: ç¯      æ¯; sam      e_time: åæ¯   ; o   bject: å¯¹è   ±¡)
           */
    priva          t e Strin     g metricType;

                /**
      * çæ§I          D       
               */
     private     L    ong alar   m   Id;

          /**    
          * è§å   ID
            */
                      private Long rule               I  d;

    /  **
      * æ°æ®æºid     
     */ 
       p rivate Lo ng dataSourceId;

     /  ** 
        * æ°æ®åid
        */
       priv ate Long dataN  ameId;

            /**
     * çæ§æ°   æ®å    ã(httpï¼è¡¨ç¤º  é          æhttpæ°æ  ®; å¶ä»d   ata_n        ameå³èdata_nam    eè¡¨)
          */   
    priv  ate    String dataName;

    /**
     * æ   ¥  è¯¢è¯­å¥
        */
         pri    vate Str      ing queryStr   in     g;

    /**
          * htt    pæ°  æ®çæ§ï¼postæ°æ®å         å®¹
     */
    private String post  Data;

    /   *          *
            * é   å å±æ§JSONæ ¼å¼    
     */   
    privat   e Stri  ng proper     ties;

                 /**
                  * å å»º  äºº
     */
        priva    te String cre        at             o      r;

      /**
          * åå» º  æ¶é´
     */
        p  ri            vat  e    Date      crea   teAt;

    /**
            * åæ¡¶   ç±»å   ãter    ms: å­æ®µå¼åç»; date  _histogr    am  : æ   ¶é´å             ç»   
     * /
    p    rivat  e String b   ucketTyp   e;

       /**
           *   åæ¡¶å­æ   ®µ
     *   /
    p    rivate St     ring bucket       Field;

    pri      vate static final long seri alVersionUID   = 1L;

           p   ublic Long  g     etId() {
             retu rn id;
    }

       public voi   d se     tId(    Long id) {
           this.i  d = id;
    }

         public String getAggregatio    nTyp e() {
           return      aggregationType;
          }

    publ            ic void setAggregat  ionType(Strin    g aggregati  onType) {     
               this.aggregationType = a ggre    gation     Type         ==    nul      l      ? null : aggre  g   ation      Type.trim();   
    }

    public S    tr   i  ng getAggreg   ationField() {
           return ag   gregationField;
              }  

    public voi                     d setAgg    regationField     (String aggregation       F  ield)    {    
                     this.aggregationField  = aggregati    onFiel   d    == null ?   null  : aggregationField.   tr     im();
         } 

    pu          bli        c Str ing getMet          ricType() {
           return met     ricType     ;
    }

         public vo     i   d   setMetric              Ty pe(String metri    cType) {
           this.metricType = metricTyp  e == null ? nul  l :    m etricType.trim();
    }

      publ   ic L            ong getAlar mId(  ) {
               return alarmId;     
    }

        publ   ic   void se tA       l    armId(Long    alarmId) {
                   this.  alarmI d = al armId;
    }

    pu      blic L        ong getR        uleId() {
          ret     urn ru    l    e       Id;
    }    

    public      void se  t RuleI d(L    ong ruleId    )   {
                  this .ru     leId   = ruleId;
                   }

             public Long        getD   ataSourceId() {
        return data     Source    Id;
    }

    p     ublic void setDat     aSourceId(Long data   So  urce   Id) {
                    this.   da  taSourceId  = dataSou  rc   eId;
    }
      
    public Long  getDataNa    meId() {
             return da  taNameId;
    }

    public  void setData   NameId(Long   dataNameId) {   
              th  is.    dataNameId =    dataNameId;
    }

    public S  tring    get              Dat  aNam     e() {
                  ret   urn dataName;
     }

    public void setDataName(String da taName        ) {
            this.   dataN   ame = dat   aN ame == null ? null     :    dataName.trim();
    }

        public    String getQueryStrin     g() {
                 return q          ueryString;
     }

        publ i   c void setQueryString(St ring qu  eryString)    {
         this.qu     erySt           ring = querySt    rin g == null ? null : queryStr   in  g.t        rim(  );
          }

    pub  lic S       tring getPostData()     {     
                          return postData;
    }
    
    public void setP  ostDa      ta(    Str  ing p         ostData) {
        th  is    .    postData =   postData;
    }

    public String          getProp      er        ties() {
           ret  urn pr  opert   ies;
      }

    public    void  s                  etPro  perties(S  tring properti    es)  {
          this.pro    per   ties = properties ==      null      ?         nul      l    : properties.t rim()    ;
      }

           public Strin     g getCreator() {
        return creator;
    }

    public v      oid      setCreator(S   tri   ng creator) {
                       this.creator = cr    eator   ==   null ? n   ull : c  reat     or.trim();
    }

    pu    blic Dat   e   getCrea    teAt()               {
                 return c re  ateAt;   
             }

    public void setCr       eateAt(Date createAt) {
                 this.createAt = create  At;
    }

    public  S     tring    getB     u    c   ketType() {
        return          bucketType;
    }

      public void setBucketT     ype(String bucketType) {
         this.bucketType = bucketType     == null ? null : buck etT ype  .tr    im()      ;
               }

     pub   lic Stri  ng getBu  cketFiel   d() {
              return     bucketField;
    }

        public void setB   ucketField(String bucket      Fi     eld) {     
           this.   bucket F ield = buck etFi  eld =    = null ? null : b         ucketF ield.trim();
    }

     @Override
           public b  oolean   equals(Object that  ) {
         if (this ==       that) {
            r    etu       rn true;
        }
          if (that     == null) {
               return false;
               }
                    if  (getCla ss    () != that.get Class()) {
                    return     false;    
        }
        Metric other    = (Metric) t hat  ;
                      retu    rn (this.getId() == nul  l ?     other.getId() == null      : this.g    e      tId().equals(other.getId()))   
                         &    & (this.getAggregationType() == null ? other.getAggre   gat     i   onType() == null     : this.getAgg reg      ationType(). equa   ls(other.getAggregationType  ()))  
                    && (this.getAggreg   ationField      () == null ? other.getAggregationField() ==   null :  this.getAggregationFiel              d()      .equals(other.getAggregationFiel d()))
            && (this.getMetricType () ==           n  ull ? o    ther.getMetricType(  ) == null : t his.getMetricType().equals(oth    er.get M    etricType()   ))
                     && (thi       s.ge tAlarmId(     ) == null ? other.getAlarmId() == null :        t h    is.getA  larmId().e      qual    s    (othe   r.getAlarmI      d()))
                             &&     (this.get   RuleId() =  = null ? other.getRuleId() == null : this.getRuleId().eq      uals(other.getRu  leId()))
                     && (this.g        etDataSourceId() == n     ull ? other.getDataSou  rceId() == null : t   his.ge       tDataSo   urceId().e  quals(other.getDataSource    Id())     )
                      && (this.getDa     taNa   meId(      ) == nu    ll ? other.getData     NameId() =   = null   : this.                   getD ataNa  meId()     .equals(other.getData   NameId()) )    
            &  & (this.getDa   taNam          e() ==      null   ? o       ther.         getDataN   am    e       () =   = null : t     h      is.getDataName().equals(other.getData  Name()))   
            &   & (this.getQueryString( )      =    = null ? other.ge  tQue   ryString() == n  u ll : t     his.getQueryString().eq     uals(other.getQueryString()))
            && (this    .getPostData() == null ? other.getPo stData() ==   null :    this.getPos tData().eq             uals(other.getPos tDat      a()))
             && (this.getProperties() =      = null ? other.getPrope   rties() == null :    this.getPrope   rti  e   s().equa  ls(other.getProperties( )))
                        && (this.getCrea    tor  () == n    ul   l ? other.getCreator() == null : this.getCreato   r().equals(other.get           Cre           ator()))
            &&    (this.g    et   CreateA  t  () ==    null    ? other.getCr eateAt ()    == null : t     his.getCr    eateAt().equals   (other.getCrea    teAt()))
               && (this.getBucketType() == null ? oth  e  r.getBucketType() =   = null :          this.getB       ucketType().e   quals(other.getBucketType()))
                               && (this.ge    tB     ucketField() == n ull ? other.get   BucketField() == null : this.getBucke          tField().equals(other.ge       tB ucketField()));
    }

    @Override
    public in t hashCo de() {
          final int prime = 31;
        int resul t = 1;
             result = prime * result +        ((getId() == null) ? 0 : getId().has    hCode());
        result = prime * resul   t + ((get           Aggregati  onType()   =    = null) ?  0 : getAggregationType().hashCode());
               result    = prime * result + (( getAggreg     ationField()        == null) ? 0 : getAggregationField().    h       ash    Code());
        result = prime * result + ((getMetr  icType() == n    ull) ? 0 : getM   etricTy   pe(  ).ha shCode());
              result = prime * r      esult +  ((getAla    rmId() == null) ? 0 : getAlarmId().hashCode());
        result = pri      me * result +       ((getRuleId() ==   null) ? 0 : getRuleId().    hashCode());
         result = prime * result + ((getDataSourceId()   == null) ? 0 : getDataSourceI       d().hashCode());
        result = prime * result + ((getDataNameId() ==      null) ? 0 : getDataNameId().hashCode());
        result       = prime  * result + ((getDataName() == null) ? 0 : getDa   taName().hashCode());
        result = prime * result + ((getQueryString() == null) ? 0 : getQue     ryString().hashCode());
        result = prime * result + ((getPostData() == null) ? 0 : getPostData().hashCode());
        result = prime * result + ((getProperties() == null) ? 0 : getProperties().hashCode());
        result = prime * result + ((getC reator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getCreateAt() == null) ? 0 : getCreateAt().hashCode());
        result = prime * result + ((getBucketType() == null) ? 0 : getBucketType().hashCode());
        result = prime * result + ((getBucketField() == null) ? 0 : getBucketField().hashCode());
        return result;
    }
}