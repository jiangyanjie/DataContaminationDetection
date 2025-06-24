/**
    * $HeadUR L$
 */
package ch.goatbrain.smooks.csvreader;

/**
        *  TODO      
 *  
     * @author Michael Buchli <michael.buchli@wuerth-itensis.com>
 * @copyright (c) 2007-201    4, W    uert h     ITensis AG
 * @created 31.01.2014 10:24:42
 *
 * @$Revis  i  on$
 *
 *      @$LastCha     ngedBy$
 * @$LastCh anged   Date$
 * @$Id$
 */     
public class Customer S  knvk             ex    tends  AbstractCs   vDataClas       s {

         public CustomerSknvk() {
        setCsvFields("kunnr,abtnr,parla,wue_zzanre,namev,knvk_name1,pakn5,pafkt,knvk_telf1,wue_zzteltxe,wue_zztelfx,knvk_zzemai l,parau,wue_zzgbdat,titel_ap,knv   k_sortl,parh1   ,parh2,parh3,p      a     r   h4,parh5");
    }
    private          String kunnr;
       private String abtnr;
           private String par la;
       pr    i    v       ate  String wue_    z  za   nre;   
    private S  tring name     v;
    private String knv  k_name1  ;
    priv      ate String p  ak n5;
    private String pafkt;
    pr   iv   ate Stri     n      g knvk_telf  1;
    private Stri       ng wue_zzteltxe;
     priv  ate Str  i    ng wue_zzt   elfx   ;
    private String kn  vk_zzemail;
    private String     p       arau;
    p     rivate String      w   u e_z    zgbda  t    ;
    private Str  ing titel_ap;
     private            Stri   n  g    knv     k_sortl;
    privat      e   S           tri ng  parh1;
    private S  tring     parh2;  
    private S    tring parh  3;
      private String parh4;
       private        String   pa  rh5;
     
    public Str   ing getK           unnr() {
         retu   rn kun     nr;
    }

      public   void s  etKunnr(String kunnr)      {
             thi          s.ku      nnr      = kunnr;
    }
    
    pu     blic  St   ring getAbt nr()      {   
          return abtnr;
    }

       public     vo id      se     tAbtnr(   String abtnr) {
                              t    his.abt   nr = abtnr;
           }

    public St   ring get   P  a        rla() {
          re  turn      parla;
    }

    publi c void    set  Pa     rla(String p         ar    la) {
                       this.p        a   rla =       par     l   a;
    }

        p         ublic S        tring    getWu      e_zzanre    ()          {
        return wue_     z  zanre;
    }

    public void se tWue_zzanre(Stri   ng wue_z     zanre) {
        th  is.w  ue_zzanre = wue_zzanre;
           }    

                     public              String get  Namev     () {
        return namev;
       }

    pu  b     lic   void setName   v(String        namev) {
           t     his.namev = namev;
    }
   
    public  Stri    ng getKnv     k_nam  e      1() {
        ret  urn knvk_nam   e1;    
    }

        pu  blic v               oid setKnvk_name1(String             knvk_n    am             e1) {
        this.knvk_name1     = kn    v  k_name1;
    }

       public S   tring getPakn5 ()            {
        r  eturn pakn5;
    }

    p   ublic void       setP       akn5(String pakn5        ) {
            this.pakn5 = pakn5;
    }

       public String getPa    fkt() {
           re  t  urn pafk  t;
    }

      public voi    d            set Pa   fkt(String               pafkt) {
        t      his.p afkt =    pafkt   ;
    }

              pu blic Stri  ng ge     tKnvk_     telf1()        {      
        return knvk_telf1;
    }
     
    public voi d setKnvk_telf1 (Strin g knvk_telf        1    )     {
                                this.knvk_telf1 =  knvk_telf   1;
        }

          public String get     Wue_zzteltxe() {
                  return wue      _zzteltxe;
     }   
   
    publ    ic void setW  ue_zztel  txe(String              wue_zz   te  lt       xe) {
          this.wue_zzte l    txe = w        ue                    _zzteltx e;
      }

       public  String        getWue_zztelf x() {
            return w   ue           _      zztelfx ;      
    }

        public voi        d setWue_zz      telfx(Strin g wue_zztelfx) {
                    this.wue   _zztelfx = wue  _zztelfx;
         }

      public Strin   g getKnvk_zzemail(  ) {
           return   knvk _zzemail;
    }
  
    p ublic void setKnvk_zzemail(Str  ing knvk_zze      mai      l) {
        this  .kn   vk_zzema il      = knvk_zzemail;
    }

      public S     tring       getPara     u  () {
                return para     u;
    } 

       publ        ic   voi  d   s etParau    (Stri ng p   arau)   {
             thi         s.  pa   rau = parau;
    }

             publi c String      ge   tWue_zzgbd at() {
        return wue_zzgbdat;
    }           
 
        public          void       setWu    e_zzgbdat(String                    w      ue_zzgbdat)     {
          this      .wue     _zzgbdat = wue_zzgbdat;
        }     

    public S    tring ge       tTitel_ap() {
        return titel_ap;
         }

    public void setTitel_ap(String titel_ap) {
                      this. t     it     el_ap = titel_ap;   
    }
  
    public Str       ing getKnvk_s ortl() {
        re   turn   knvk_sortl;
    }
     
    publ ic v  oid se  tKnv k_sort           l(String      knvk_s            or tl) {
                 th     is.k   nvk_sor     tl = k              nvk_sortl;
    }

    public Str       ing getParh1() {
               return parh1;
                }

    public     void setParh1(String parh1) {
        this.p   arh1 = parh1      ;
                }

    pu bli c  String getParh2 () {
                   return parh2;
    }

    public v    oid setPar                        h2(String parh2) {
          this.parh2 =     parh        2       ;    
     }

        public Strin     g getParh3( ) {
               return p     arh3;
    }

    public void setParh         3(St  ri ng       parh3) {
                this.parh  3 = pa rh3;
    }

       public String getParh4() {
          ret urn p arh4;
    }

    pu blic void    setParh4(St     ring      parh              4) {
          this.parh4 = p  arh4;
              }

    p   ubli  c S     tring g     etPa rh5()   {
             retu   rn pa        rh5;
    }

    public           void            setP      arh   5( Strin  g parh5) {
                 thi  s.parh5 = pa rh5;
        }

    p   ub   lic String toCsvString(           ) {
               retur    n kunnr +     separa      torCha        r + a     btnr + separatorChar + parla +  separatorChar + wu  e_zzanre +    sepa ratorCh ar + nam   ev + separ  ator    Char + kn   vk     _name1 + se   paratorChar +  pakn5 + separatorChar + paf  kt + separatorChar + knvk_telf1 + separato  rCh  ar + wu e_ zztel        txe +        s    eparatorChar + wue_zztel   fx + separatorChar + knvk_z     zem   a    il      + separatorCh    ar + parau + separatorChar + wue_zzgbdat + separatorChar + titel_ap + separator       Char + knvk_sortl + sepa  ra   torChar     + parh1 + separatorChar + parh2 + separat orChar + pa rh3    + separat    orChar +     parh4 + se paratorChar + parh5;
    }

    @Override
    publ  ic String  t  oString() {
        return "CustomerSknvk{"   + "kunnr=" + kunnr + ", abtnr=" + abtnr +     ", parla=" + parla + ", wue_zzanre=  " + wue_zzanre + ", namev="          + namev + ", knvk_name1=" + knvk_name1 +    ", pakn5=" + pakn5 + ", pafkt=" + pafkt + ", knvk_telf1=" + kn      vk_telf1 + ", w    ue_zzteltxe=" + wue_zzteltxe + ", wue_zztelfx=     " + wue_zztelfx + ", knvk_zzemai        l=" + knvk_zzemail +  ",     para u=" + parau + "  , wue_zzgbdat=" + wue_zzgb   dat + ", titel_ap=" + t     it      el_a     p + ", knvk_sortl=" + knvk_sortl     + ", parh1=" + parh1 + ", parh2=" + parh2 + ", p    arh3="    + parh3 + ",  parh4=" + parh4     + ", parh5=" + parh5    + '}';
    }

    protected       boolean mandatoryF   ieldsAvailable(boolean recordIs   Valid) {
        if (fieldHasContent(k  unnr   , "kunnr")
                && fieldHasConte  nt(abtnr, "abtnr")
                      && fieldHasCo     ntent(parla, "parla")
                  && fieldHasCont  ent(wue_zzanre, "wue_zzanre")
                   && fieldHasContent(knvk_  name1, "knvk_name1") 
                && f   ieldH    asContent(      pakn5,    "pakn5")
                  && fieldHasContent(pafkt, "pafkt")) {
            reco    rdIsValid = true;
           }
          return recordIsValid;
    }

      @Override
      protected boolean fieldContentIsValid(boolean recordIsValid) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change bod y of generated methods, choose Tools | Templates.
        return true;
    }
}
