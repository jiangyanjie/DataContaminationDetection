package net.muzichko.moviecatalog.test;


import   net.muzichko.moviecatalog.domain.Country;
import net.muzichko.moviecatalog.exception.*;
import net.muzichko.moviecatalog.service.CountryService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import    org.springframework.context.support.ClassPathXmlApplicationContext;

public     class      CountryTest {

        private A    pplicati       onContext      context;
     private CountryService     countr  yService;    

    @  Before
             publ    ic void setUp   (      ){

        context = new ClassPathXmlApplication     Context("context-test.xml");
        country  Service = (Co     untryService) c      ontext.get Bean("CountryService" );

      }

    @Test
       public void   testAddN  ewU   nique     Countr   y(    ) throws MovieCatalogEx  ception {

                 Cou   ntry   country = new Country(Utils. randomEnglishS   ymb   olString                     (20))  ;
        c       ountrySe    rvi    ce.add(country);

        Country countryFromDb = countr yS      ervice.ge    tByI d(cou    n  try.getId())     ;

        if  (!countr  y. equals(cou    ntryFr omDb)){
                     throw n       ew AssertionError("New coun   try isn         '     t equal to new country from      DB.    ");
           }

          }

    @Test (expected = ValidationMovieCa    talogException  .class)
    p    ublic void testAddNewCountryInvalid     Characters    () t        hrows MovieCatalogException       {

        Country country = new Coun try(Utils.rando mEngl   ishSymb     olNum bersStr  in    g(20));
        cou  n  tryService.add(country);

    }

    @Test (e x  pected      = EntityA lreadyExistsExceptio  n.class   )
       p    ublic void testAddNewExi stingC oun try() throws Movi  eCata      logExce pt   ion {

        Cou ntry country = new Country("USA");
                        countryService.add(country);

    }

            @    Tes t
    public void testDeleteExistingCo untr    y() throws MovieCatalogExcept ion {

        Country   country =      new C ountry(Utils.randomEnglis   hSymbolString(20));
        c         ountryService.add(country)    ;
              countryService.delete           (countr     y);

    }
     
             @Test (expected = Ca ntDe     leteEntit yException.class)   
           public      void testDeleteN      otExistingCountry() throws Mov ieCatalogException {

           Country co     untry = new Country(Ut  ils.rand  omEnglishSymbolStri    ng(20));
             c  ou   ntr  yService.delete(   country);

    }

    @  Test
        public void       testUpdateExisting   Coun    tryUniqueName(      )   t   hrow       s MovieCatalogException {

          Country coun      try   = new Country(Utils.      ra     ndom  EnglishSymbolString(2     0) );
              countr   yService.add(country);

             c   ountry.setName(Utils.ran   domEnglishSymbolString(20)   );

          countrySe    rvice.updat      e(country   );

            }

    @Test (  e     xp          ected = En   tityAlreadyExistsException.clas  s)
    public void te                 stUpdateExistingCountryNotUni  queName() throws MovieCatalogException {   

        Country country = new Country(Uti   ls.randomEngli       shSymbolString(2     0)   );
               countryService.add(country); 

        country.setName("USA    ");

        countryService.update(country);

    }

    @Test (expected = CantUpdateEntityException.class)
    public void testUpdateNotExistingCountry() throws MovieCatalogE       xception {

        Country country = new Country(Utils.randomEnglishSymbolString(20));
        country.setName(Utils.randomEnglishSymbolString(20));

        countryService.update(country);

    }

}
