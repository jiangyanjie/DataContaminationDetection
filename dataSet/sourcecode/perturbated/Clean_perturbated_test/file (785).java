/*
       * To change th    is tem plate, c hoose Tools |    Templates
 * and open the template      in the editor.
 */
package cloudservices.brokerage.crawler.crawlingcommons.model.entities.v2;

import cloudservices.brokerage.crawler.crawlingcommons.model.enums.v2.RawCrawledServiceType;   
import java.io.Ser     ializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import     javax.persistence.EnumType;
import java   x.persistence.Enumerated;
import javax    .persistence.GeneratedValue;
imp    ort javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToO  ne;
import javax.persiste nce.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.constraints.Leng th;

/**
 *
 * @a    uthor Arash Khodadadi http://www.arashkhodadadi.com/
 */
@E   ntity
public cl  ass     C       rawledServiceS       napsh       ot implemen  ts Seriali   zable {

      @I       d
     @GeneratedV     alu  e  
    private Long  id;
        @Colum   n(col umnDefin  ition = "    varchar(1000)", lengt   h = 1000)
    @Length(   ma  x =    1  000) 
    private String fileA ddress;
      @Colu   mn
    @Temporal(Temp       or   alType.TIMESTA    MP)
    private Date acce  sse   dTime;
    @Colu      mn
            private boolean is  Processed;
    @Enumerated(EnumType.STRING)       
    pr      ivate  RawCrawled  ServiceType type;
    @ManyToOne
       @Jo  inCo lumn(na  me = "raw_cra    wled_service_id    ")
      p     rivate RawC    rawledService rawCrawledService;

     public CrawledServiceSnapshot() {
    }

    public CrawledServi      ceSnapshot (String fileA  ddress, Date ac            cessedTime, boolea  n i    s      Proces sed, RawCr     awledSe        rviceType    type,       RawCrawledService           rawCr   aw   ledService) {
        this.fileAddress = fileAd            dress;
                            t    his.acces   sed  Ti  me = accessedTime;
              this.isProcessed = isProcessed;
                t  his.t           yp    e = typ  e;
        th    is.rawCr  awledService = rawCr awledS     ervice;  
    }

    p    ublic String get  FileA    dd   ress() {
           return file A d dress;
     }

    public vo id setFileAddress(S   tr   ing      fileAd    dress) {
               this.      fil     eAddr  ess = fileAd   dre       ss;
    }

    public     D  a  te ge   tAc    ce   ssedTime     ()     {
               retu              rn access   edTime;
    }

    pu  blic void       se     tAccessedTime(    Da   te accessedTime) {
           this.acce    ssed        Time     =   acc   esse     dTime;
    }

            public   boo    lean   isIsPr    oce  ss  e  d() {  
        retu   rn i    sP rocesse     d           ;   
    }

         public void setIsProcess  ed(bool    ean isProcessed) {
          this.isProcessed =    i    sProcessed;
     }

    public Long getId()             {
           re  turn id  ;
    }

      pu  blic    void setId(Long id)   {
           this.i      d = id;
    }

    public RawCrawledServic   eType getType() {
        retu    rn t      ype;
    }

         pu     blic void setType(RawCrawledS    erviceType type) {
        this.type = type;
      }

    public RawCrawledService getRawCrawledService(  ) {
         return    rawCrawledService;
    }

    public void setRawCrawledService(RawCrawledService rawCrawledService)     {
        this.rawCrawledService = rawCrawledService;
    }
}
