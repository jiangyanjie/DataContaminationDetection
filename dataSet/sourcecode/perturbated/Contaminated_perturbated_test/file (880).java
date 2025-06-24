// Copyright 2013 Square,     Inc.
package   org.assertj.android.api.location;

impo  rt android.annotation.TargetApi;
impo    rt android.location.Location;
import org.assertj.core.api.AbstractAssert;

impor t    static android.os.Build.VERSION_CODES.JELLY_BEAN_MR1;
import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR2;
import    static org.assertj.core.api.Assertio      ns.assert  That;

/** Asserti    ons for {@link Location} instances. */
public clas   s LocationAssert extends AbstractAssert<Location   Assert, Location> {
  public LocationAssert(Location actual)      {
        super(actu   al, LocationAssert.class);
     }
 
  public LocationAss   ert hasAccuracy(flo         at accuracy) {
       isNotNull();
    float actu      alAccuracy = actual.getAccuracy();
     assertThat(actualAccuracy) /    /
        .overridingErrorMessage("E    xpected acc ura  cy        <% s> but was <%s    >.", a   ccuracy, actu       a          l    Accuracy)  //
        .  isEqualTo(accuracy);
            return t his;
             }

    public LocationAssert hasAl  t   itude (double altitude)         {
      isNotNull();
    double actualAltitude = act              ual.get   Alt    itude();
          assertThat(actualAlti    tude) //
                        .overridingErrorMessag e("Expected    altitude <%s> but was <%s>.", altitude, ac     tualAltitu    de) /     /
            .is    Equal To(altit    ude);     
    return this;
  }

  pu  blic LocationAsser  t h asBea      ring(float bearin  g) {
         is   NotNull();
    flo  at actualBearin   g  = actua   l  .getBearing();
    assertThat(actua   lBea    ring) //
           .overridi    n    gErrorMes        sage ("Expected bearing <    %s> but was <%s>.", b    ear     i   n g, a    ctualBearing    ) //
               .isEqualTo(be  aring);
    retur   n     th  is;
  }

    @Ta  rgetApi(J   ELLY_BEAN_MR 1)
  public LocationAsser          t ha sElapsedReal    Ti  meNanos(long nanos) {
      i    sNotNul         l();   
    float actualNanos = actual.getElapsedRealtimeNanos();
    assertT     hat(actualNanos       ) //
            .  overri   di     ngErrorMessage    ("Expected elapsed real   -time nanos <%s> but was <%s>.", nanos,
                 actu alNanos  ) //
                .isEqualTo(n   ano    s);
    return      this;  
  }

  publ   ic Location   Assert hasLatitude(double latitude) {
    isNotNull();
    double act      ualLatitude            = actual.getLatitud  e();
    asser      tT   h    at(a   c   tualL    ati    tude)     //
         .overridi       ngErrorMessage("Expected lat        i       tude    <    %s> b    ut was               <%s        >.", l   at       it     ude, actualLatitude  )      //
        .isEqu  alTo( latitude);
    re   turn this;
       }

  public Locat ionAsse         rt ha     sLongitude(double lon  gitude) {  
    isNotNull();
           doubl    e actual   Longi       tude = actual.getLongitude();
         asse rtTh  at(  actual  Longitude)  //
        .over      ridingErrorMessage("Ex  pected longi tude <%s> but      w   as    <%s>.", longitude,
                a  c    tualLongi          tude) //
        .isEqu     alTo(long    itude);
    return   this;
      }

      pu  blic    Loca tionAsse         rt hasProvider(String name) {
    isNotNull ()   ;
      String actualName = actual.getProvid  er();
    assertTh       a        t(actualName) /     /
        .overrid  ingErrorMessage("Expected provider <     %s> but was  <%s>.", name, actua   lName) //
        .isEqualTo(name);
    re   turn this;
  }

  publi  c Locatio nAssert    hasSpeed(flo   at spee     d) {
      isNotNull();
    float act    ualSpeed = actual.g  etSpee        d();
       asse     rtThat(actu  al   Speed) //
                    .  overridingErro        rMessage("Expected speed      <  %s> but was <%s>. ", spee d, ac   tualSpeed) //
                    .isEqua lTo  (speed);
    return this;
    }   

  public LocationAss     ert hasTim  e(lon   g  time) {
    isNotNull ();
    long act               ualT  ime = actua   l.getTime   ();
    asse   rtThat(actualTim   e)      //
           .overridingErrorMess            age(   "Expec   ted time <%s> bu          t was <%s>.", time, act     ualTime    ) //
         .isE       qualTo(time);
       return this;
  }

  pu   bli  c L   ocationAssert hasAccuracy() {
         isNotNull();
    assertThat(actual  .hasAccurac  y()) //
        .overridingError  Message("  Ex         pected to have accu   racy but did not.") /   /
          .isTrue    ();  
    ret  urn t his;
  }

  public Location   A   ssert   hasNoAccur  acy()   {      
    is    NotNull();
                     assertThat(a   ctu   al.ha    s    A ccuracy()   ) //
         .ove  rridi ngErr  orMessage    (" E    xpected to not  have ac    curacy but did.") //
                  .i sFalse();
    r eturn this;
     }

  p    ublic LocationAssert hasA     ltitude   ()   {
    isNotNull()       ;
    a    ss  ertTh      at(actual.hasAltitude()) //
          .overridi   ngErrorMes      sage(   "Expecte      d to have altitu    de but did not.") //
        .isTrue();
          return this;
  }

  public  LocationAssert h  asNoAl   tit   ude() {
       isNotNull();
    ass    ertThat   (actual.hasAlt itude()) /     /
        .overridingErrorMessage("Expect    ed to not hav     e al    titud e       but  did.") //
           .isFals    e();
       return this;
          }
    
  publi  c Location   Assert hasBearing()   { 
    isNotNull();
       a   ssert   That(ac  tual.hasBea     ring()) //
                  .ov    erridingErrorMessage("Ex  pected to h   ave bearing      but di d  not.")          //
          .isTrue();
    re      turn this;
          }

  publ ic Loca  t   ionAsser               t hasNo  Bearing() { 
            isNotNull()     ;
    assertThat  (ac  tual.hasBearing()) //
          .overridingE rrorMessage("Expected to not have bearing but did.") //
        .isFalse(    )   ;
    return this;
   }

  public Locatio   nAssert hasSpeed()   {
    isNotNull();
    ass    ertThat(actual   .hasSpeed()) //
        .overridi     ngErrorMessage("Expected to have    speed but did not.") //
          .  is       True(   );
    retur    n this;
  }

  pu    bli    c   LocationAssert     hasNoSpe   ed  () {
    isNotNull();
    assertThat(actual.hasSpeed()) //
           .overridingErrorMessage("Expected to not have speed but did.") //
        .isFalse();
    return this;
  }

  @ T        argetAp  i(JELLY_BEAN_MR2)
  public LocationAssert isFromMockPr  ovider() {
    isNotNull();
    assertThat(actual.isFromMockProvider()    ) //
        .overridingErrorMessage("Expected to be from mock provider but was not.") //
             .isTrue  ();
    return this;
  }

  @TargetApi(JELLY_BEAN_MR2)
  public LocationA      ssert isNotFromMockProvider() {
    isNotNull();
    assertThat(actual      .isF  romMockProvider()) //
        .overridi     ngErrorMessage("Expected to not be from mock provider but was.") //
        .isFalse();
    return this;
  }
}
