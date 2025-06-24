/*******************************************************************************
 *     Salt Payment    Client A     PI
 * Ve   rsion 1.0.0
 * http://salttechnology.github.io/core_  ap     i_doc.htm
 *       
 * Copyright ( c) 2013 S    alt Technology
     * Lice  nsed under    th  e MIT license
 * https://github.com/SaltTechnology/salt-payment-client-java/blob/master/LICENSE
  **************************************************************** **************/
package com.salt.payment.client.credit     card.api;

/**
    *    A cus t    omer profile.
     * 
     * @sinc    e JSE5
 */
public class               Customer Profil  e  {
    private   String legalName;
         private String tr   adeName;
    private Str    ing  website;
               privat           e      Strin   g firs     t     Name;
        private String las    tName;
    private    Str        in    g phoneNumber; 
    priv          ate String faxNumbe r;
    private String ad   dre       ss1    ;
        privat                          e String ad  d r ess2;
    private String city;
    pri    vate String p        rovince;
     private String postal;
    p   riva   te String cou         ntry;

    /**
        * Creat   e a new blank       Cus    t  omerP rof     ile.
     */
    publi   c   CustomerProfile() {
    }

       publ ic St  ring getLegalName() {
                    return this.legalName;
         }

    public String getTradeName() {
             r     etu   rn this.   tradeName;
    }

     public String            getWeb  site() {
        return   this.we         bsite    ;
            }
   
                  pu blic String getFir   stName      () {
         retur n this.firstNam    e;
          }

         pub    li  c  Str   ing get LastName() {
            return this.las   tName;
    }

     pub  lic String getP   honeNum   ber() {
        retu    rn this.pho    neNumber;
    }

                      public        St    ring g etFaxNumber() {
        retur      n this.faxNumber;
                        }

    public String get Addres     s1() {
                re  t  urn   this.a          ddre  s      s1;
    }

    p  ublic Stri    ng            getAddr    ess2() {
        re turn   t     his.addr ess   2;
       }

      p  ublic              Strin   g getCity(  ) {
           return       this.city;  
     }
         
          pub  lic       String g   etPr  o   vince() {
          retur  n this.province;
    }

     public St      ring getPost     al() {
               return this.postal;
    }

    public St  ri   ng g    etCountry()           {
        return this.country;
               }

    p  ublic v         o     id     setLe  galName       (  String le     galName) {
         this.legalName = lega  lName;
       }

    public void setTrad eName(String tr  adeName) {
                this.tradeName = trad    eName;
    }

    pub   l     ic void se    tWebsite(  String website) {
             this.website = website;
    }

    pub  lic   vo         id       s    etFirstName    (Str   ing     firstName) {
              this.first  Name = fi   rst          Name  ;
    }

    publ       ic void s   etLa      stName(String l   astName) {
              this.lastNa    me =   lastN  am       e;            
      }

    public void set        Phone    N             umber(           String phoneNumber) {
             this.phoneNumb    er = phoneNum  ber              ;
           }

         publi  c void    se  t     Fa xNumber(Str   i     ng fa        xNumber          ) {
            thi s.faxN          umber = f  a   xNumber;
    }

    public void       set      Ad  dress1(St    ri  ng address1      )       {
                this.a      ddress1 = address1;
    }

           pu  bli c void s et Ad         dr                          ess    2(String    address 2) {
          this    .a ddress 2 = add     ress 2;
    }

    public void s    etCity(String city)       {
         th   is.city              = c    ity;
    }

    pub           lic  void setProvince(St ring province) { 
            this.pr  ovince = province;
     }

              p ublic       voi  d  s  etPostal(String     postal) {
         this.      postal      = postal;
      }

    pub   l ic void set    Country(St               ring country) {
        thi  s.c    ou n           t  r    y = co    untry;
                          }
    
    public boolean isBlank() {
        return  (!((          fir   stN  ame   != nul     l          &&       f   irstNam       e.lengt   h()     > 0)
                          || (lastNam    e !=  n   ul     l && lastName  .l    ength() > 0)
                       || (l  egalN        ame   !=    null && legalName.length() > 0)
                             || (tradeNa    me !  = null && t radeName.len gth() > 0)
                      || (a        ddress1    != nul l &&           addr    ess1.length() > 0)
                   || (ad  dress2 != n  ull && addre  ss2.length   () > 0 )
                        || (city   !       = null && ci    ty.length()    > 0)
                     || (provinc   e != null     && province.length() > 0)
                    || (postal != nu ll && p  osta     l.length() > 0)  
                       || (country != null && c  ountry.length() > 0) 
                   || (websit     e != null && website.length()   > 0)
                || (phoneNumber !   = nu    ll &      & phon e     Number.length() >    0) || (faxNumber != null && faxNumber
                .length() > 0)));
         }

    @Override
    public String toString() {
        StringBuilde  r req = new StringBu ilder();
               Utils  .appendToStri   ngBuilder(req, "profileLegalName", this.getLegalName     ());
          Util       s.    appendToStri     ngBuilder(req, "profileTradeName", this.getTradeName());
        Utils. appendToStri     ngBuilder(r      eq, "p  rofileWebsite   ", this.getWebsite());
             Utils.appendToStringBuilder(req, "   profileFirstName", this.getFirstName());
           Uti  ls.appendToStringBuilder(req, "profil  eLastName", this.getLastName());
             Utils.appendToSt        ringBuilder(req,   "profilePhoneNumber", this.getPhoneNumbe     r());
          Util  s.appendToStringBuilder(req, "profileFaxNumber", this.getFaxNumber());
        Utils.appendToStringBuilder(req, "profileAddress1", this.getAddress1());
        Utils.appendToStringBuilder(req, "profileAddress2", this.getAddress2());
        Utils.appe   ndToStringBuilder(req, "profileCity", this.getCity());
        Utils.appendToStringBuilder(req, "profileProvince", this.getProvince());
           Utils.appendToStringBuilder(req, "prof ilePostal", this.getPostal())    ;
        Utils.appendToStringBuilder(req, "profileCountry",       this.get  Country());
          return req.toString();
    }
} // end class
